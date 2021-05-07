package com.tengxt.bootjsoup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 发送带有 html 的邮件
 */
@Component
public class SendHtmlMail {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String fromName;

    public boolean sendHtmlMail(List<Map<String, Object>> retList, String toMail) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
        MimeMessage message = null;
        try {
            message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromName);
            // 接收地址
            helper.setTo(toMail);
            // 标题
            StringBuffer buffer = new StringBuffer();
            buffer.append("LOF基金实时溢价").append(simpleDateFormat.format(new Date()));
            helper.setSubject(buffer.toString());
            // 带有HTML格式的内容
            String content = getContentTable(retList);
            helper.setText(content, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private String getContentTable(List<Map<String, Object>> retList) {
        StringBuilder sb = new StringBuilder();
        sb.append("<br>");
        sb.append("<html>" +
                "<head>" +
                "<meta charset=\"UTF-8\">" +
                "<title></title>" +
                "<style type=\"text/css\">" +
                "table {" +
                "font-family: 宋体;" +
                "font-weight: bold;" +
                "border-collapse: collapse;" +
                "}" +
                "" +
                "td {" +
                "vertical-align: middle;" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>");
        sb.append("<table border=\"1\" style=\"width: 400px;height: 300px;border: 1px #fffff solid; font-size=14px;" +
                "text-align: center;\">" +
                "<tr>" +
                "<td style=\"background-color: #73dfec; text-align: center;\" colspan=\"5\">LOF 基金实时溢价</td>" +
                "</tr>" +
                "<tr style=\"background-color: #73dfec; text-align: center;\">" +
                "<td>代码</td>" +
                "<td>名称</td>" +
                "<td>涨幅</td>" +
                "<td style=\"color:red;\">溢价率</td>" +
                "<td>净值日期</td>" +
                "</tr>");
        if (null != retList && retList.size() > 0) {
            for (Map<String, Object> map : retList) {
                sb.append("<tr style=\"text-align: center;\">");
                sb.append("<td>" + map.get("fundId") + "</td>");
                sb.append("<td>" + map.get("fundNm") + "</td>");
                sb.append("<td>" + map.get("increaseRt") + "</td>");
                sb.append("<td style=\"color:red;\">" + map.get("discountRt") + "</td>");
                sb.append("<td>" + map.get("navDt") + "</td>");
                sb.append("</tr>");
            }
        }
        sb.append("</table></body></html>");
        return sb.toString();
    }
}
