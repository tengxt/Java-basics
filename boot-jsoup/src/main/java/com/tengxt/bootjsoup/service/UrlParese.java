package com.tengxt.bootjsoup.service;

import com.alibaba.fastjson.JSON;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;

/**
 * 使用 HttpClient GET 请求链接地址，定时任务
 */
@Component
public class UrlParese {

    private static Logger logger = LoggerFactory.getLogger(UrlParese.class);

    private static final String URL_LOF = "https://www.jisilu.cn/data/lof/stock_lof_list/?___jsl=LST___t=";

    private static final int STAUTS = 200;

    @Value("#{'${jisilu.fundId}'.empty ? null : '${jisilu.fundId}'.split(',')}")
    private List<String> fundId;

    public List<Map<String, Object>> getInfo() throws IOException {
        List<Map<String, Object>> retList = new ArrayList<>();
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(URL_LOF);
        // 执行请求，相当于敲完地址后按下回车。获取响应
        CloseableHttpResponse response = httpclient.execute(httpGet);
        // 判断返回状态是否为200
        if (response.getStatusLine().getStatusCode() == STAUTS) {
            // 解析响应，获取数据
            String content = EntityUtils.toString(response.getEntity(), "UTF-8");
            if (!StringUtils.isEmpty(content)) {
                Map jsonObject = JSON.parseObject(content);
                // 获取数据
                List<Map<String, Object>> rowsList = (List<Map<String, Object>>) jsonObject.get("rows");
                if (null != rowsList && rowsList.size() > 0) {
                    for (Map<String, Object> map : rowsList) {
                        String id = (String) map.get("id");
                        for (String ids : fundId) {
                            if (ids.equals(id)) {
                                Map<String, Object> itemMap = new LinkedHashMap<>();
                                Map<String, Object> item = (Map<String, Object>) map.get("cell");
                                // 代码
                                itemMap.put("fundId", item.get("fund_id"));
                                // 名称
                                itemMap.put("fundNm", item.get("fund_nm"));
                                // 涨幅
                                itemMap.put("increaseRt", item.get("increase_rt") + "%");
                                // 溢价率
                                itemMap.put("discountRt", item.get("discount_rt") + "%");
                                // 净值日期
                                itemMap.put("navDt", item.get("nav_dt"));
                                retList.add(itemMap);
                            }
                        }
                    }
                }
            }
        }
        return retList;
    }
}
