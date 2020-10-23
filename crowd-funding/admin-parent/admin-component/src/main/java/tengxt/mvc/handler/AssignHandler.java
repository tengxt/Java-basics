package tengxt.mvc.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tengxt.entity.Role;
import tengxt.service.api.AdminService;
import tengxt.service.api.RoleService;

import java.util.List;

@Controller
public class AssignHandler {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;


    @RequestMapping("/assign/do/assign.html")
    public String saveAdminRoleRelationship(@RequestParam("adminId") Integer adminId,
                                            @RequestParam("pageNum") Integer pageNum,
                                            @RequestParam("keyword") String keyword,
                                            @RequestParam(value = "roleIdList", required = false) List<Integer> roleIdList){
        adminService.saveAdminRoleRelationship(adminId, roleIdList);

        return "redirect:/admin/page/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
    }

    @RequestMapping("/assign/to/page.html")
    public String toAssignRolePage(@RequestParam("adminId") Integer adminId, ModelMap modelMap) {

        // 查询已分配的角色
        List<Role> assignedRoleList = roleService.getAssignedRole(adminId);
        // 查询未分配的角色
        List<Role> unassignedRoleList = roleService.getUnAssignedRole(adminId);
        // 存入模型
        modelMap.addAttribute("AssignedRoleList", assignedRoleList);
        modelMap.addAttribute("UnAssignedRoleList", unassignedRoleList);
        return "assign-role";
    }

}
