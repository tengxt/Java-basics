package tengxt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tengxt.entity.Menu;
import tengxt.entity.MenuExample;
import tengxt.mapper.MenuMapper;
import tengxt.service.api.MenuService;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService{

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getAll() {
        // 查询了整个表的数据
        return menuMapper.selectByExample(new MenuExample());
    }

    @Override
    public int saveMenu(Menu menu) {
        return menuMapper.insert(menu);
    }

    @Override
    public int updateMenu(Menu menu) {
        MenuExample example = new MenuExample();
        MenuExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(menu.getId());
        return menuMapper.updateByExampleSelective(menu, example);
    }

    @Override
    public int removeMenuById(Integer id) {
        MenuExample example = new MenuExample();
        MenuExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        return menuMapper.deleteByExample(example);
    }
}
