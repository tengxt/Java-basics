package tengxt.mvc.handler;


import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tengxt.constant.CrowdConstant;
import tengxt.entity.Admin;
import tengxt.service.api.AdminService;
import tengxt.util.CrowdUtil;
import tengxt.util.ResultEntity;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Objects;

@Controller
public class AdminHandler {

    private Logger logger = LoggerFactory.getLogger(AdminHandler.class);

    @Autowired
    private AdminService adminService;

    @RequestMapping("/admin/doRegister.json")
    public ResultEntity<String> doRegister(@RequestBody String paramData) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> jsonMap = null;
        try {
            jsonMap = mapper.readValue(paramData, new TypeReference<Map<String, Object>>() {
            });

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if (null != jsonMap) {
            String loginUser = (String) jsonMap.get("loginUser");
            String loginPwd = (String) jsonMap.get("loginPwd");
            String loginPwdAgain = (String) jsonMap.get("loginPwdAgain");
            String loginEmail = (String) jsonMap.get("loginEmail");
            // 非空判断
            if (StringUtils.isEmpty(loginUser) || StringUtils.isEmpty(loginPwd)
                    || StringUtils.isEmpty(loginPwdAgain) || StringUtils.isEmpty(loginEmail)) {

                return ResultEntity.failed(CrowdConstant.MESSAGE_STRING_INVALIDATE);
            }

            // 判断两次输入的密码是否一致
            if (!Objects.equals(loginPwd, loginPwdAgain)) {
                return ResultEntity.failed("两次输入的密码不一致，请重新输入");
            }

            Admin admin = new Admin(null, loginUser, CrowdUtil.md5(loginPwd), loginUser, loginEmail, null);
            int number = adminService.saveAdmin(admin);

            logger.info(number > 0 ? "添加数据成功" : "添加数据失败");

            if (number > 0) {
                return ResultEntity.successWithoutData();
            }
        }

        return ResultEntity.failed("注册失败, 请稍后再试");
    }


    @RequestMapping("/admin/login/doLogin.html")
    public String doLogin(@RequestParam("login-user") String loginUser,
                          @RequestParam("login-pwd") String loginPwd,
                          HttpSession session){
        // 调用 adminService
        Admin admin = adminService.getAdminByLoginAcct(loginUser,loginPwd);

        //
        session.setAttribute(CrowdConstant.LOGIN_ADMIN_NAME,admin);
        return "redirect:/admin/admin/page.html";
    }

    @GetMapping("/admin/login/logout.html")
    public String doLogout(HttpSession session) {
        // 强制 Session 失效
        session.invalidate();
        return "redirect:/admin/login/page.html";
    }

    @RequestMapping("/admin/page/page.html")
    public String getAdminPage(
            // 传入的关键字，若未传入，默认值为一个空字符串（不能是null）
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            // 传入的页码，默认值为1
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            // 传入的页面大小，默认值为5
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            // ModelMap用于给前端带数据
            ModelMap modelMap) {

        // 从AdminService中得到对应传参的列表
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNum, pageSize);

        // 将得到的PageInfo存入modelMap，传给前端
        modelMap.addAttribute(CrowdConstant.NAME_PAGE_INFO, pageInfo);

        // 进入对应的显示管理员信息的页面（/WEB-INF/admin-page.jsp）
        return "admin-page";
    }
}
