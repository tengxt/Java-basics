package tengxt.service.api;

import tengxt.entity.Auth;

import java.util.List;
import java.util.Map;

public interface AuthService {

    List<Auth> queryAuthList();

    List<Integer> getAuthByRoleId(Integer roleId);

    void saveRoleAuthRelationship(Map<String, List<Integer>> map);
}