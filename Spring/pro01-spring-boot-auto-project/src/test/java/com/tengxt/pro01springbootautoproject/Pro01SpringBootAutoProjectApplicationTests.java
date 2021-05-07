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

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class Pro01SpringBootAutoProjectApplicationTests {

    private Logger logger = LoggerFactory.getLogger(Pro01SpringBootAutoProjectApplicationTests.class);

    @Autowired
    private UsersMapper usersMapper;

    @Test
    void contextLoads() {
        // 如何将字符串反转？ abcdefg  To  gfedcba

        String str = "abcdefg";

        // 1.字符串拼接，把字符串转成字节数组；然后倒序遍历
        /*String reverseChar = "";
        char[] chars = str.toCharArray();
        for (int i = chars.length - 1; i >= 0; i--) {
            reverseChar += chars[i];
        }
        System.out.println(reverseChar);*/

        // 2. 字符串拼接，把字符串遍历出来的单个字符放在字符串最前面
        /*String reverse = "";
        for (int i = 0; i < str.length(); i++) {
            reverse = str.charAt(i) + reverse;
        }
        System.out.println(reverse);*/

        // 3. StringBuilder/StringBuffer 类的 reverse() 方法
        /*StringBuilder builder = new StringBuilder();
        builder.append("abcdefg");
        builder.reverse();
        System.out.println(builder);

        StringBuffer buffer = new StringBuffer();
        buffer.append("gfedcba");
        buffer.reverse();
        System.out.println(buffer);*/

        String a = "a";
        String b = "b";
        String c = a + b;
        String d = new String("ab");

        //System.out.println("(a+b).equals(c)的值是" + (a + b).equals(c));
        System.out.println(a + b);
        System.out.println(c);
        System.out.println("a + b == c的值是" + a + b == c);
        //System.out.println("c == d的值是" + c == d);
        //System.out.println("c.equals(d)的值是" + c.equals(d));


    }
}

