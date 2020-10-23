package tengxt.service.api;

import tengxt.entity.Menu;

import java.util.List;

public interface MenuService {
    List<Menu> getAll();

    int saveMenu(Menu menu);

    int updateMenu(Menu menu);

    int removeMenuById(Integer id);
}
