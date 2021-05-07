package com.tengxt.bootjsoup;

import com.tengxt.bootjsoup.service.SendHtmlMail;
import com.tengxt.bootjsoup.service.UrlParese;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@SpringBootTest
class BootJsoupApplicationTests {

    @Autowired
    private UrlParese urlParese;

    @Autowired
    private SendHtmlMail sendHtmlMail;

    @Test
    public void test3(){
        List<Map<String, Object>> maps = null;
        try {
            maps = urlParese.getInfo();
            System.out.println(maps);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sendHtmlMail.sendHtmlMail(maps, "1300230407@qq.com");
    }

    @Test
    void test2(){
        try {
            List<Map<String, Object>> maps = urlParese.getInfo();
            System.out.println(maps);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void contextLoads() {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://www.jisilu.cn/data/lof/stock_lof_list/?___jsl=LST___t=");
        CloseableHttpResponse response = null;
        String result = "";
        try {
            // 执行请求，相当于敲完地址后按下回车。获取响应

            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                // 解析响应，获取数据
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println(content);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                // 关闭资源
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 关闭浏览器
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
