package com.nowcoder.community.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class MailClient {

    private static final Logger logger = LoggerFactory.getLogger(MailClient.class);

    @Autowired
    private JavaMailSender javaMailSender;
    /*
        JavaMailSender类介绍，发送邮件的核心类，首先导入jar包后，这个类就由spring进行接管，可以采用@Autowired注入
        里面有两种基本方法，分别是创建邮件，和发送邮件
        MimeMessage createMimeMessage();
        void send(MimeMessage mimeMessage) throws MailException;
    * */

    //发件人
    @Value("${spring.mail.username}")
    private String from;


    public void sendMail(String to, String subject, String content) {//发给谁？邮件主题？邮件内容？
        try {
            //创建邮件模板类
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            //使用MimeMessageHelper填充邮件
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            //设置发件人
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(content,true);//允许支持html邮件
            javaMailSender.send(mimeMessageHelper.getMimeMessage());
        } catch (MessagingException e) {
            logger.error("发送邮件失败:" + e.getMessage());
        }
    }
}
