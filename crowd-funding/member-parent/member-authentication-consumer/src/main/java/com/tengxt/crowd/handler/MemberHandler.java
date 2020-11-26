package com.tengxt.crowd.handler;

import com.tengxt.crowd.MySQLRemoteService;
import com.tengxt.crowd.RedisRemoteService;
import com.tengxt.crowd.config.ShortMessageProperties;
import com.tengxt.crowd.entity.po.MemberPO;
import com.tengxt.crowd.entity.vo.LoginMemberVO;
import com.tengxt.crowd.entity.vo.MemberVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tengxt.constant.CrowdConstant;
import tengxt.util.CrowdUtil;
import tengxt.util.ResultEntity;
import tengxt.util.VerifyCode;
import tengxt.util.VerifyCodeUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Controller
public class MemberHandler {

    private Logger logger = LoggerFactory.getLogger(MemberHandler.class);

    // 图形验证码文本存入 Session 中的 KEY
    private static final String SESSION_VERIFY_CODE_KEY = "SESSION_VERIFY_CODE_KEY";

    @Autowired
    private RedisRemoteService redisRemoteService;

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    // 自动注入对象，对象的属性从application.yml文件中获取
    @Autowired
    private ShortMessageProperties shortMessageProperties;


    /**
     * 登录
     * @param loginAcct 用户名
     * @param loginPswd 密码
     * @param modelMap
     * @param session
     * @return
     */
    @RequestMapping("/auth/do/member/login.html")
    public String doMemberLogin(
            @RequestParam("loginAcct") String loginAcct,
            @RequestParam("loginPswd") String loginPswd,
            ModelMap modelMap,
            HttpSession session) {
        // 非空校验
        if (StringUtils.isEmpty(loginAcct) || StringUtils.isEmpty(loginPswd)) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_STRING_INVALIDATE);
            return "member-login";
        }

        // 远程方法调用，通过loinAcct，得到数据库中的对应Member
        ResultEntity<MemberPO> queryResultEntity = mySQLRemoteService.getMemberPOByLoginAcctRemote(loginAcct);

        // 判断-查询操作是否成功
        String queryResult = queryResultEntity.getResult();
        if (ResultEntity.FAILED.equals(queryResult)) {
            // 查询失败，返回登陆页面
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_LOGIN_FAILED);
            return "member-login";
        }

        // 查询操作成功，则取出MemberPO对象
        MemberPO memberPO = queryResultEntity.getData();
        // 判断得到的MemberPO是否为空
        if (null == memberPO) {
            // 为空则返回登陆页面
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_LOGIN_FAILED);
            return "member-login";
        }

        // 返回的MemberPO非空，取出数据库中的密码（已经加密的）
        String userPswd = memberPO.getUserPswd();

        // 使用 BCryptPasswordEncoder，比对表单的密码与数据库中的密码是否匹配
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean matches = passwordEncoder.matches(loginPswd, userPswd);
        // 判断-密码不同
        if (!matches) {
            // 返回登陆页面，存入相应的提示信息
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_LOGIN_FAILED);
            return "member-login";
        }

        // 密码匹配，则通过一个LoginMemberVO对象，存入需要在session域通信的用户信息（这样只在session域放一些相对不私秘的信息，保护用户隐私）
        LoginMemberVO loginMember = new LoginMemberVO(memberPO.getId(), memberPO.getUserName(), memberPO.getEmail());

        // 将LoginMemberVO对象存入session域(因为session会放入redis，因此LoginMemberVO必须实现序列化)
        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_MEMBER, loginMember);

        // 重定向到登陆成功后的主页面
        return "redirect:http://localhost/auth/to/member/center/page.html";
    }

    /**
     * 注册
     *
     * @param memberVO
     * @param modelMap
     * @param session
     * @return
     */
    @RequestMapping("/auth/member/do/register.html")
    public String doMemberRegister(MemberVO memberVO, ModelMap modelMap, HttpSession session) {

        // 获取图形验证码
        String verifyCode = memberVO.getVerifyCode();
        // 通过key寻找value（验证码）
        String verifyCodeVal = (String) session.getAttribute(SESSION_VERIFY_CODE_KEY);

        // 非空校验
        if (StringUtils.isEmpty(verifyCode)
                || StringUtils.isEmpty(verifyCodeVal)
                || StringUtils.isEmpty(memberVO.getLoginAcct())
                || StringUtils.isEmpty(memberVO.getUserPswd())
                || StringUtils.isEmpty(memberVO.getUserName())) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_STRING_INVALIDATE);
            return "member-reg";
        }

        // 获取该用户
        ResultEntity<MemberPO> queryResultEntity =
                mySQLRemoteService.getMemberPOByLoginAcctRemote(memberVO.getLoginAcct());
        // 判断该用户是否存在
        String queryResult = queryResultEntity.getResult();
        if (ResultEntity.SUCCESS.equals(queryResult) || !StringUtils.isEmpty(queryResultEntity.getData())) {
            // 该用户存在，则返回保存操作的ResultEntity中的message，存入request域的message
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, "该账号已存在，请重试。");
            // 回到注册页面
            return "member-reg";
        }

        // 判断Session中的验证码与输入的验证码（转小写）是否一致
        if (!Objects.equals(verifyCode, verifyCodeVal.toLowerCase())) {
            // 失败，返回主页页面
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, CrowdConstant.MESSAGE_CODE_INVALID);
            return "member-reg";
        }

        // 验证码一致，清除session中的验证码
        session.removeAttribute(SESSION_VERIFY_CODE_KEY);

        // 密码加密
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String formPwd = memberVO.getUserPswd();
        String encode = bCryptPasswordEncoder.encode(formPwd);

        // 将加密后的密码放入MemberVO对象
        memberVO.setUserPswd(encode);

        //  执行保存
        MemberPO memberPO = new MemberPO();
        BeanUtils.copyProperties(memberVO, memberPO);
        ResultEntity<String> saveResultEntity = mySQLRemoteService.saveMemberRemote(memberPO);

        // 判断保存是否成功
        String saveResult = saveResultEntity.getResult();
        if (ResultEntity.FAILED.equals(saveResult)) {
            // 保存失败，则返回保存操作的ResultEntity中的message，存入request域的message
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE, saveResultEntity.getMessage());
            // 回到注册页面
            return "member-reg";
        }

        // 全部判断成功，跳转到登录页面
        return "redirect:http://localhost/auth/to/member/login/page.html";
    }


    // 发送图形验证码
    @ResponseBody
    @RequestMapping("/auth/member/send/verify/code.json")
    public void sendVerifyCode(HttpServletRequest request, HttpServletResponse response) {
        // 生成验证码对象
        VerifyCodeUtils verifyCodeUtils = new VerifyCodeUtils();
        try {
            //设置长宽
            VerifyCode verifyCode = verifyCodeUtils.generate(80, 40);
            String code = verifyCode.getCode();
            //将VerifyCode绑定session
            request.getSession().setAttribute(SESSION_VERIFY_CODE_KEY, code);
            //设置响应头
            response.setHeader("Pragma", "no-cache");
            //设置响应头
            response.setHeader("Cache-Control", "no-cache");
            //在代理服务器端防止缓冲
            response.setDateHeader("Expires", 0);
            //设置响应内容类型
            response.setContentType("image/jpeg");
            response.getOutputStream().write(verifyCode.getImgBytes());
            response.getOutputStream().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 发送验证码
    @ResponseBody
    @RequestMapping("/auth/member/send/short/message.json")
    public ResultEntity<String> sendShortMessage(@RequestParam("phoneNum") String phoneNum) {

        // 调用工具类中的发送验证码的方法，可以从配置文件中读取配置的接口信息
        ResultEntity<String> sendResultEntity = CrowdUtil.sendCodeByShortMessage(
                shortMessageProperties.getHost(),
                shortMessageProperties.getPath(),
                shortMessageProperties.getAppCode(),
                phoneNum,
                shortMessageProperties.getSign(),
                shortMessageProperties.getSkin());

        // 判断-发送成功
        if (ResultEntity.SUCCESS.equals(sendResultEntity.getResult())) {

            // 得到ResultEntity中的验证码
            String code = sendResultEntity.getData();

            // 将验证码存入到redis中（设置TTL，这里设置为5分钟）
            ResultEntity<String> redisResultEntity = redisRemoteService.setRedisKeyValueWithTimeoutRemote(
                    CrowdConstant.REDIS_CODE_PREFIX + phoneNum, code, 5, TimeUnit.MINUTES);

            // 判断存入redis是否成功
            if (ResultEntity.SUCCESS.equals(redisResultEntity.getResult())) {
                // 存入成功，返回成功
                return ResultEntity.successWithoutData();
            } else {
                // 存入失败，返回redis返回的ResultEntity
                return redisResultEntity;
            }
        } else {
            // 发送验证码失败，返回发送验证码的ResultEntity
            return sendResultEntity;
        }
    }
}
