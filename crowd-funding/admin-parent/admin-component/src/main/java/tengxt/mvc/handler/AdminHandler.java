package tengxt.mvc.handler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tengxt.constant.CrowdConstant;
import tengxt.entity.Admin;
import tengxt.service.api.AdminService;

import javax.servlet.http.HttpSession;

@Controller
public class AdminHandler {
    @Autowired
    private AdminService adminService;


    @RequestMapping("/security/do/login.html")
    public String doLogin(@RequestParam("login-user") String loginUser,
                          @RequestParam("login-pwd") String loginPwd,
                          HttpSession session){
        // 调用 adminService
        Admin admin = adminService.getAdminByLoginAcct(loginUser,loginPwd);

        //
        session.setAttribute(CrowdConstant.LOGIN_ADMIN_NAME,admin);
        return "admin-main";
    }
}
