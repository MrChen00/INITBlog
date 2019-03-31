package com.zhou.init.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * 发送邮件
 * @author ZHOU
 * @create 2019-02-02 16:39
 */
@Component
public class SendEmail {

    @Autowired
    JavaMailSenderImpl mailSender;

    /**
     * 验证码
     * @return
     */
    public String SecurityCode(String recipient, String title, String message){
        // 验证码
        String securityCode = null;
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            // 生成验证码
            String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
            // 邮件设置
            mailMessage.setSubject(title);
            mailMessage.setText(message + " 验证码 : " + verifyCode);
            // 收件人
            mailMessage.setTo(recipient);
            mailMessage.setFrom("13677495631@163.com");
            mailSender.send(mailMessage);
            // 成功赋值
            securityCode = verifyCode;
        }catch (Exception e){
            e.printStackTrace();
        }

        return securityCode;
    }

}
