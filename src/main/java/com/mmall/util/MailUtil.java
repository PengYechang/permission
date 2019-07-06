package com.mmall.util;

import com.mmall.beans.Mail;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import java.util.HashSet;

@Slf4j
public class MailUtil {

    public static boolean send(Mail mail) {

        // TODO
        String from = "815314586@qq.com";
        int port = 25;
        String host = "smtp.qq.com";
        String pass = "ubssdkqiueotbfjd";
        String nickname = "名侦探柯南";

        HtmlEmail email = new HtmlEmail();
        try {
            email.setHostName(host);
            email.setCharset("UTF-8");
            for (String str : mail.getReceivers()) {
                email.addTo(str);
            }
            email.setFrom(from, nickname);
            email.setSmtpPort(port);
            email.setAuthentication(from, pass);
            email.setSubject(mail.getSubject());
            email.setHtmlMsg(mail.getMessage());
            email.send();
            log.info("{} 发送邮件到 {}", from, StringUtils.join(mail.getReceivers(), ","));
            return true;
        } catch (EmailException e) {
            log.error(from + "发送邮件到" + StringUtils.join(mail.getReceivers(), ",") + "失败", e);
            return false;
        }
    }

    public static void main(String[] args) {
        Mail mail = Mail.builder()
                .subject("hello")
                .message("大家好")
                .receivers(new HashSet<String>(){{add("1406264819@qq.com");}})
                .build();
        MailUtil.send(mail);
    }

}

