package tengxt.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tengxt.constant.CrowdConstant;
import tengxt.entity.Admin;
import tengxt.entity.AdminExample;
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

        // 保存
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

    /**
     * @param keyword 关键字
     * @param pageNum 当前页码
     * @param pageSize 每一页显示的信息数量
     * @return 最后的pageInfo对象
     */
    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {
        // 利用 PageHelper 的静态方法开启分页
        PageHelper.startPage(pageNum,pageSize);

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
        AdminExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(adminId);
        return adminMapper.deleteByExample(example);
    }
}
