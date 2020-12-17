package com.tengxt.crowd.test;

import com.tengxt.crowd.entity.vo.PortalProjectVO;
import com.tengxt.crowd.entity.vo.PortalTypeVO;
import com.tengxt.crowd.mapper.ProjectPOMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConnectionTest {

    private Logger logger = LoggerFactory.getLogger(ConnectionTest.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ProjectPOMapper projectPOMapper;

    @Test
    public void testLoadTypeData() {
        List<PortalTypeVO> typeVOList = projectPOMapper.selectPortalTypeVOList();
        for (PortalTypeVO portalTypeVO : typeVOList) {
            String voName = portalTypeVO.getName();
            String voRemark = portalTypeVO.getRemark();
            logger.info("name= " + voName + " remark= " + voRemark);

            List<PortalProjectVO> projectVOList = portalTypeVO.getPortalProjectVOList();
            for (PortalProjectVO portalProjectVO : projectVOList) {
                if (null == projectVOList)
                    continue;
                logger.info(portalProjectVO.toString());
            }

        }
    }

    @Test
    public void test() throws SQLException {
        Connection connection = dataSource.getConnection();
        logger.debug(connection.toString());
    }
}
