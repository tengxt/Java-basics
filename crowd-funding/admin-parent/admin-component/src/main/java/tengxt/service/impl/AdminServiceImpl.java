package tengxt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tengxt.constant.CrowdConstant;
import tengxt.entity.Admin;
import tengxt.entity.AdminExample;
import tengxt.exception.LoginFailedException;
import tengxt.mapper.AdminMapper;
import tengxt.service.api.AdminService;
import tengxt.util.CrowdUtil;

import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    public int saveAdmin(Admin admin) {
        return adminMapper.insert(admin);
    }

    public List<Admin> queryAll() {
        return adminMapper.selectByExample(new AdminExample());
    }

    @Override
    public Admin getAdminByLoginAcct(String loginUser, String loginPwd) {
        AdminExample example = new AdminExample();
        AdminExample.Criteria criteria = example.createCriteria();
        criteria.andLoginAcctEqualTo(loginUser);
        List<Admin> adminList = adminMapper.selectByExample(example);
        if (adminList == null || adminList.size() == 0) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        if (adminList.size() > 1) {
            throw new RuntimeException(CrowdConstant.MESSAGE_ERROR_NOT_UNIQUE);
        }

        Admin admin = adminList.get(0);
        if (null == admin) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        String userPswdDB = admin.getUserPswd();

        // 将表单提交的明文密码加密
        String userPswdFrom = CrowdUtil.md5(loginPwd);

        // 对密码进行比较
        if (!Objects.equals(userPswdDB, userPswdFrom)) {
            // 比较结果不一致则抛出异常
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        return admin;
    }
}
