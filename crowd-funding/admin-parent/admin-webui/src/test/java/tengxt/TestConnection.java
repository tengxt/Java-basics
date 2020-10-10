package tengxt;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import tengxt.entity.Admin;
import tengxt.entity.AdminExample;
import tengxt.mapper.AdminMapper;
import tengxt.service.api.AdminService;

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
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml", "classpath:spring-persist-tx.xml"})
public class TestConnection {
    @Autowired
    DataSource dataSource;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminService adminService;

    @Test
    public void testTx() {
        Admin admin = new Admin(null, "tom", "123123", "汤姆", "tom@qq.com", null);
        int i = adminService.saveAdmin(admin);
        System.out.println(i > 0 ? "插入成功。" : "插入失败！");
    }

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

    @Test
    public void test02() {
        // 获取 Logger 对象
        Logger logger = LoggerFactory.getLogger(TestConnection.class);
        // 等级 DEBUG < INFO < WARN < ERROR
        logger.debug("DEBUG");
        logger.debug("DEBUG");
        logger.debug("DEBUG");
        logger.debug("DEBUG");
        logger.info("INFO");
        logger.info("INFO");
        logger.info("INFO");
        logger.info("INFO");
        logger.warn("WARN");
        logger.warn("WARN");
        logger.warn("WARN");
        logger.warn("WARN");
        logger.error("ERROR");
        logger.error("ERROR");
        logger.error("ERROR");
        logger.error("ERROR");
        logger.error("ERROR");
    }
}
