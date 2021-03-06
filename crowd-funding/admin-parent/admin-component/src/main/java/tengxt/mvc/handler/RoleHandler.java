package tengxt.mvc.handler;

import com.alibaba.druid.util.StringUtils;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tengxt.constant.CrowdConstant;
import tengxt.entity.Role;
import tengxt.service.api.RoleService;
import tengxt.util.ResultEntity;

import java.util.List;

@RestController
public class RoleHandler {

    @Autowired
    private RoleService roleService;

    @RequestMapping("/role/do/remove.json")
    public ResultEntity<String> doRemoveRole(@RequestBody List<Integer> roleList) {
        roleService.removeRole(roleList);
        return ResultEntity.successWithoutData();
    }

    @RequestMapping("/role/do/update.json")
    public ResultEntity<String> doUpdateRole(Role role) {
        roleService.updateRole(role);
        return ResultEntity.successWithoutData();
    }

    @RequestMapping("role/do/save.json")
    public ResultEntity<String> doSaveRole(@RequestParam("roleName") String roleName) {
        if (StringUtils.isEmpty(roleName)) {
            throw new RuntimeException(CrowdConstant.MESSAGE_STRING_INVALIDATE);
        }
        Role role = new Role(null, roleName);
        roleService.saveRole(role);
        return ResultEntity.successWithoutData();
    }


    @PreAuthorize("hasRole('部长')")
    @RequestMapping("/role/page/page.json")
    public ResultEntity<PageInfo<Role>> getPageInfo(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "keyword", defaultValue = "") String keyword) {
        PageInfo<Role> pageInfo = roleService.getPageInfo(pageNum, pageSize, keyword);
        return ResultEntity.successWithData(pageInfo);
    }
}
