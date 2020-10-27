package tengxt.mvc.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tengxt.entity.Auth;
import tengxt.entity.Role;
import tengxt.service.api.AdminService;
import tengxt.service.api.AuthService;
import tengxt.service.api.RoleService;
import tengxt.util.ResultEntity;

import java.util.List;
import java.util.Map;

@Controller
public class AssignHandler {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthService authService;

    @RequestMapping("/assign/get/tree.json")
    @ResponseBody
    public ResultEntity<List<Auth>> getAuthTree() {
        List<Auth> authList = authService.queryAuthList();
        return ResultEntity.successWithData(authList);
    }

    @RequestMapping("/assign/get/checked/auth/id.json")
    @ResponseBody
    public ResultEntity<List<Integer>> getAuthByRoleId(Integer roleId){
        List<Integer> authIdList = authService.getAuthByRoleId(roleId);
        return ResultEntity.successWithData(authIdList);
    }

    @RequestMapping("/assign/do/save/role/auth/relationship.json")
    @ResponseBody
    public ResultEntity<String> saveRoleAuthRelationship(
            // 用一个map接收前端发来的数据
            @RequestBody Map<String,List<Integer>> map ) {
        // 保存更改后的Role与Auth关系
        authService.saveRoleAuthRelationship(map);

        return ResultEntity.successWithoutData();
    }


    @RequestMapping("/assign/do/assign.html")
    public String saveAdminRoleRelationship(@RequestParam("adminId") Integer adminId,
                                            @RequestParam("pageNum") Integer pageNum,
                                            @RequestParam("keyword") String keyword,
                                            @RequestParam(value = "roleIdList", required = false) List<Integer> roleIdList) {
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
