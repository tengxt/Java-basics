package tengxt.mvc.handler;


import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import tengxt.constant.CrowdConstant;
import tengxt.entity.Admin;
import tengxt.service.api.AdminService;
import tengxt.util.ResultEntity;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
public class AdminHandler {

    private Logger logger = LoggerFactory.getLogger(AdminHandler.class);

    @Autowired
    private AdminService adminService;

    @RequestMapping("/admin/doRegister.json")
    @ResponseBody
    public ResultEntity<String> doRegister(@RequestBody String paramData) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> jsonMap = null;
        try {
            jsonMap = mapper.readValue(paramData, new TypeReference<Map<String, Object>>() {});
        } catch (JsonProcessingException e) {
            logger.error("doRegister method exception" + e.getMessage());
        }

        if (!jsonMap.isEmpty()) {
            String loginUser = (String) jsonMap.get("login-user");
            String loginPwd = (String) jsonMap.get("login-pwd");
            String loginPwdAgain = (String) jsonMap.get("login-pwd-again");
            String loginEmail = (String) jsonMap.get("login-email");
            // 非空判断
            if (StringUtils.isEmpty(loginUser) || StringUtils.isEmpty(loginPwd)
                    || StringUtils.isEmpty(loginPwdAgain) || StringUtils.isEmpty(loginEmail)) {

                return ResultEntity.failed(CrowdConstant.MESSAGE_STRING_INVALIDATE);
            }

            // 判断两次输入的密码是否一致
            if (!Objects.equals(loginPwd, loginPwdAgain)) {
                return ResultEntity.failed("两次输入的密码不一致，请重新输入");
            }

            Admin admin = new Admin(null, loginUser, loginPwd, loginUser, loginEmail, null);
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
                          HttpSession session) {
        // 调用 adminService
        Admin admin = adminService.getAdminByLoginAcct(loginUser, loginPwd);
        // 设置 session
        session.setAttribute(CrowdConstant.LOGIN_ADMIN_NAME, admin);
        return "redirect:/admin/admin/page.html";
    }

    @GetMapping("/admin/login/logout.html")
    public String doLogout(HttpSession session) {
        // 强制 Session 失效
        session.invalidate();
        return "redirect:/admin/login/page.html";
    }

    /**
     * 分页显示&搜索
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @param modelMap
     * @return
     */
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

    /**
     * 单条删除
     * @param adminId
     * @param pageNum
     * @param keyword
     * @param session
     * @returna'd
     */
    @RequestMapping("/admin/page/remove/{adminId}/{pageNum}/{keyword}.html")
    public String removeById(@PathVariable("adminId") Integer adminId,
                             @PathVariable("pageNum") Integer pageNum,
                             @PathVariable("keyword") String keyword,
                             HttpSession session) {
        // 获取登录的用户信息
        Admin admin = (Admin) session.getAttribute(CrowdConstant.LOGIN_ADMIN_NAME);

        // 判断被删除的用户Id和登录用户的Id是否相同
        Integer loginId = admin.getId();
        if (Objects.equals(adminId, loginId)) {
            throw new RuntimeException("操作无效，请重新操作");
        }
        int removeById = adminService.removeById(adminId);
        if (removeById <= 0) {
            throw new RuntimeException("删除数据失败，请联系管理员");
        }
        logger.info(removeById > 0 ? "删除成功" : "删除失败");
        // 为了保持原本所在页面和查询关键字再附加pageNum和keyword参数
        return "redirect:/admin/page/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
    }

    /**
     * 打开修改页面
     * @param adminId
     * @return
     */
    @RequestMapping("/admin/page/update/{adminId}/{pageNum}/{keyword}.html")
    public String showUpdatePage(@PathVariable("adminId") Integer adminId,
                                 @PathVariable("pageNum") Integer pageNum,
                                 @PathVariable("keyword") String keyword,
                                 ModelMap modelMap) {
        List<Admin> adminList = adminService.queryById(adminId);
        Admin queryAdmin = null;
        if (null != adminList && adminList.size() > 0) {
            queryAdmin = adminList.get(0);
        }
        modelMap.addAttribute("admin", queryAdmin);
        modelMap.addAttribute("pageNum", pageNum);
        modelMap.addAttribute("keyword", keyword);

        return "admin-update";
    }

    @RequestMapping("/admin/page/doUpdate.html")
    public String duUpdate(Admin admin,
                           @RequestParam("pageNum") Integer pageNum,
                           @RequestParam("keyword") String keyword) {
        if (Objects.isNull(admin)) {
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }

        int updateAdmin = adminService.updateAdmin(admin);
        if (updateAdmin <= 0) {
            throw new RuntimeException("修改数据失败，请联系管理员");
        }
        logger.info(updateAdmin > 0 ? "修改成功" : "修改失败");
        return "redirect:/admin/page/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
    }


    @PreAuthorize("hasAuthority('user:save')")
    @RequestMapping("/admin/page/doSave.html")
    public String doSave(Admin admin) {
        if (Objects.isNull(admin)) {
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }
        int saveAdmin = adminService.saveAdmin(admin);
        if (saveAdmin <= 0) {
            throw new RuntimeException("创建数据失败，请联系管理员");
        }
        logger.info(saveAdmin > 0 ? "创建成功" : "创建失败");
        // 重定向会原本的页面，且为了能在添加管理员后看到管理员，设置pageNum为整型的最大值（通过修正到最后一页）
        return "redirect:/admin/page/page.html?pageNum=" + Integer.MAX_VALUE;
    }
}
