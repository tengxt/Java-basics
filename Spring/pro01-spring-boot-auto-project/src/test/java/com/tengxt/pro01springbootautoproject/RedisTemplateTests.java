package com.tengxt.pro01springbootautoproject;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;

import java.util.List;

@SpringBootTest
public class RedisTemplateTests {
    private Logger logger = LoggerFactory.getLogger(RedisTemplateTests.class);

    @Autowired
    private RedisTemplate<Object,Object> redisTemplate;

    @Autowired
    private RedisTemplate<String,String> myStringRedisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void testRedisTemplate(){
        // 获取用来操作String类型数据的ValueOperations对象
        ValueOperations<Object,Object> operations = redisTemplate.opsForValue();

        // 存入数据
        Object key = "good";
        Object value = "morning";
        operations.set(key,value);

        // 获取数据
        Object readValue = operations.get(key);
        logger.debug(readValue.toString());
    }

    @Test
    public void testMyStringRedisTemplate(){
        ValueOperations<String, String> operations = myStringRedisTemplate.opsForValue();

        String key = "good111";
        String value = "morning111";
        operations.set(key,value);

        String readValue = operations.get(key);
        logger.debug(readValue);
    }

    @Test
    public void testStringRedisTemplate(){
        ListOperations<String, String> operations = stringRedisTemplate.opsForList();

        String key = "fruit";
        operations.leftPush(key,"apple");
        operations.leftPush(key,"banana");
        operations.leftPush(key,"orange");

        List<String> stringList = operations.range(key, 0, -1);
        for (String str : stringList) {
            logger.debug(str);
        }
    }
}
