package tengxt.service.api;

import tengxt.entity.Admin;

import java.util.List;

public interface AdminService {
    int saveAdmin(Admin admin);

    List<Admin> queryAll();

    Admin getAdminByLoginAcct(String loginUser,String loginPwd);
}
