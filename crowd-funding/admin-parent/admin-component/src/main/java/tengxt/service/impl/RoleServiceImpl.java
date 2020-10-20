package tengxt.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tengxt.entity.Role;
import tengxt.entity.RoleExample;
import tengxt.mapper.RoleMapper;
import tengxt.service.api.RoleService;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword) {
        // 开启分页
        PageHelper.startPage(pageNum,pageSize);
        // 执行查询
        List<Role> roleList = roleMapper.selectRoleByKeyword(keyword);
        // 封装为pageInfo对象返回
        return new PageInfo<>(roleList);
    }

    @Override
    public int saveRole(Role role) {
        return roleMapper.insert(role);
    }

    @Override
    public int updateRole(Role role) {
        RoleExample example = new RoleExample();
        RoleExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(role.getId());
        return roleMapper.updateByExampleSelective(role, example);
    }

    @Override
    public void removeRole(List<Integer> roleList) {
        RoleExample example = new RoleExample();
        RoleExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(roleList);
        roleMapper.deleteByExample(example);
    }
}
