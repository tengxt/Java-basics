package tengxt.service.api;

import com.github.pagehelper.PageInfo;
import tengxt.entity.Admin;

import java.util.List;

public interface AdminService {
    int saveAdmin(Admin admin);

    List<Admin> queryAll();

    Admin getAdminByLoginAcct(String loginUser,String loginPwd);

    PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize);
}
