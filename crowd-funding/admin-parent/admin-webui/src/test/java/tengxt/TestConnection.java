package tengxt;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tengxt.entity.Admin;
import tengxt.entity.AdminExample;
import tengxt.mapper.AdminMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * RunWith与ContextConfiguration指定xml的作用与
 * ApplicationContext context = new ClassPathXmlApplicationContext("spring-persist-mybatis.xml");类似
 * 前者通过让测试在Spring容器环境下执行，使得DataSource可以被自动注入，后者导入Spring配置文件
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml"})
public class TestConnection {
    @Autowired
    DataSource dataSource;

    @Autowired
    private AdminMapper adminMapper;

    @Test
    public void test01() throws SQLException {
//        ApplicationContext context = new ClassPathXmlApplicationContext("spring-persist-mybatis.xml");
//        DataSource dataSource = context.getBean(DataSource.class);
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

    @Test
    public void testInsert() {
        Admin admin = new Admin(null, "tengxt", "123123", "管理员", "123123@qq.com", null);
        int i = adminMapper.insert(admin);
        System.out.println(i > 0 ? "插入成功。" : "插入失败！");
    }

    @Test
    public void testSelect() {
        AdminExample example = new AdminExample();
        AdminExample.Criteria criteria = example.createCriteria();
        criteria.andEmailIsNotNull();
        List<Admin> adminList = adminMapper.selectByExample(example);
        for (Admin admin : adminList) {
            System.out.println(admin);
        }
    }
}
