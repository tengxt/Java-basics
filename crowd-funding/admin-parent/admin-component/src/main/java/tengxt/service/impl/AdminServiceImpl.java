package tengxt.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import tengxt.constant.CrowdConstant;
import tengxt.entity.Admin;
import tengxt.entity.AdminExample;
import tengxt.entity.AdminExample.Criteria;
import tengxt.exception.LoginAcctAlreadyInUseException;
import tengxt.exception.LoginAcctAlreadyInUseForUpdateException;
import tengxt.exception.LoginFailedException;
import tengxt.mapper.AdminMapper;
import tengxt.service.api.AdminService;
import tengxt.util.CrowdUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList) {
        // 根据adminId删除旧的关联关系数据
        adminMapper.deleteOldRelationship(adminId);
        // 根据roleIdList和adminId保存新的关联关系
        if (null != roleIdList && roleIdList.size() > 0) {
            adminMapper.insertNewRelationship(adminId, roleIdList);
        }


    }

    public int saveAdmin(Admin admin) {
        // 密码加密
        String pswd = admin.getUserPswd();
        pswd = CrowdUtil.md5(pswd);
        admin.setUserPswd(pswd);

        // 生成创建时间
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = format.format(date);
        admin.setCreateTime(createTime);
        int saveAdmin = 0;
        // 保存
        try {
            saveAdmin = adminMapper.insert(admin);
        } catch (Exception e) {
            // 这里出现异常的话一般就是DuplicateKeyException（因为插入的loginAcct已存在而触发）
            if (e instanceof DuplicateKeyException) {
                // 如果确实是DuplicateKeyException，此时抛出一个自定义的异常
                throw new LoginAcctAlreadyInUseException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
            }
        }
        return saveAdmin;
    }

    public List<Admin> queryAll() {
        return adminMapper.selectByExample(new AdminExample());
    }

    @Override
    public Admin getAdminByLoginAcct(String loginUser, String loginPwd) {
        AdminExample example = new AdminExample();
        Criteria criteria = example.createCriteria();
        criteria.andLoginAcctEqualTo(loginUser);
        List<Admin> adminList = adminMapper.selectByExample(example);
        if (adminList == null || adminList.size() == 0) {
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }

        // 存在多条数据，则抛异常
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

    /**
     * @param keyword  关键字
     * @param pageNum  当前页码
     * @param pageSize 每一页显示的信息数量
     * @return 最后的pageInfo对象
     */
    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        // 利用 PageHelper 的静态方法开启分页
        PageHelper.startPage(pageNum, pageSize);
        // 调用 Mapper 接口的对应方法
        List<Admin> admins = adminMapper.selectAdminByKeyword(keyword);
        // 为了方便页面的使用，把 Admin 的List封装成 PageInfo
        PageInfo<Admin> pageInfo = new PageInfo<>(admins);
        // 返回得到的pageInfo对象
        return pageInfo;
    }

    @Override
    public int removeById(Integer adminId) {
        AdminExample example = new AdminExample();
        Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(adminId);
        return adminMapper.deleteByExample(example);
    }

    @Override
    public List<Admin> queryById(Integer adminId) {
        AdminExample example = new AdminExample();
        Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(adminId);
        return adminMapper.selectByExample(example);
    }

    @Override
    public int updateAdmin(Admin admin) {
        AdminExample example = new AdminExample();
        Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(admin.getId());
        int updateAdmin = 0;
        try {
            updateAdmin = adminMapper.updateByExampleSelective(admin, example);
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                // 如果确实是DuplicateKeyException，此时抛出一个自定义的异常
                throw new LoginAcctAlreadyInUseForUpdateException(CrowdConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQUE);
            }
        }
        return updateAdmin;
    }
}
