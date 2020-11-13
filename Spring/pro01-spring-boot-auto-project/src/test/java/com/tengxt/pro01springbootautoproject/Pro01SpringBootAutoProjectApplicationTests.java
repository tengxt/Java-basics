package com.tengxt.pro01springbootautoproject;

import com.tengxt.pro01springbootautoproject.entity.Users;
import com.tengxt.pro01springbootautoproject.mapper.UsersMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;

@SpringBootTest
class Pro01SpringBootAutoProjectApplicationTests {

    private Logger logger = LoggerFactory.getLogger(Pro01SpringBootAutoProjectApplicationTests.class);

    @Autowired
    private UsersMapper usersMapper;

    @Test
    void contextLoads() {
        List<Users> usersList = usersMapper.selectUsersAll();
        for (Users users : usersList) {
            logger.debug(users.toString());
        }
    }
}
