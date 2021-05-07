package com.tengxt.bootjsoup.scheduled;

import com.tengxt.bootjsoup.service.SendHtmlMail;
import com.tengxt.bootjsoup.service.UrlParese;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 定时任务
 */
@Component
public class SendMailscheduled {
    private static Logger logger = LoggerFactory.getLogger(SendMailscheduled.class);

    @Autowired
    private UrlParese urlParese;

    @Autowired
    private SendHtmlMail sendHtmlMail;

    @Scheduled(cron = "${task.cron.tradeData}")
    public void sendHtmlMailScheduled() {
        logger.info("sendHtmlMailScheduled 定时任务初始化...");
        sendHtmlMail();
    }

    private void sendHtmlMail() {
        List<Map<String, Object>> maps = null;
        try {
            maps = urlParese.getInfo();
            if (maps != null && maps.size() > 0) {
                boolean mailFlag = sendHtmlMail.sendHtmlMail(maps, "1300230407@qq.com");
                if (mailFlag) {
                    logger.info("sendHtmlMail 发送邮件成功...");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
