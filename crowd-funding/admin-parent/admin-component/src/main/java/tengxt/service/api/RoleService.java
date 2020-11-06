package tengxt.service.api;

import com.github.pagehelper.PageInfo;
import tengxt.entity.Role;

import java.util.List;

public interface RoleService {
    PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword);

    int saveRole(Role role);

    int updateRole(Role role);

    void removeRole(List<Integer> roleList);

    List<Role> getAssignedRole(Integer adminId);

    List<Role> getUnAssignedRole(Integer adminId);

    List<Role> queryAssignedRoleList(Integer adminId);
}
