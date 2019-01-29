package com.zhouwenqi.apihub.service;

import com.zhouwenqi.apihub.core.entity.User;
import com.zhouwenqi.apihub.core.entity.UserVerify;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by zhouwenqi on 2019/1/29.
 */
@Service("mailService")
public class MailService {
    private MimeMessage message;
    private Session session;
    private Transport transport;
    private Properties properties;

    // smtp 主机
    @Value(value = "${app.smtp.host}")
    private String smtpHost;

    // smtp 端口
    @Value(value = "${app.smtp.port}")
    private String smtpPort;

    // smtp 登录用户名
    @Value(value = "${app.smtp.username}")
    private String username;

    // smtp 登录密码
    @Value(value = "${app.smtp.password}")
    private String password;

    /**
     * 初始化email server参数
     */
    private void init(){
        System.out.println("host:"+smtpHost);
        properties = new Properties();
        properties.put("mail.smtp.host",smtpHost);
        properties.put("mail.smtp.port",smtpPort);
        properties.put("mail.smtp.auth",true);

        session = Session.getInstance(properties,null);
        session.setDebug(true);
        message = new MimeMessage(session);
    }

    /**
     * 发送邮件
     * @param emails 邮箱地址列表
     * @param title 邮件标题
     * @param body 邮件内容
     */
    public void send(List<String> emails, String title, String body){
        try {
            init();
            InternetAddress form = new InternetAddress(username);
            message.setFrom(form);

            InternetAddress[] sends = new InternetAddress[emails.size()];
            for(int i=0;i< emails.size();i++){
                sends[i] = new InternetAddress(emails.get(i));
            }
            message.setRecipients(MimeMessage.RecipientType.TO,sends);
            message.setSubject(title);

            Multipart multipart = new MimeMultipart();
            BodyPart bodyPart = new MimeBodyPart();
            bodyPart.setContent(body,"text/html;charset=UTF-8");
            multipart.addBodyPart(bodyPart);
            message.setContent(multipart);
            message.saveChanges();
            transport = session.getTransport("smtp");
            transport.connect(smtpHost,username,password);
            transport.sendMessage(message,message.getRecipients(Message.RecipientType.TO));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 异步启动线程发送
     * @param emails 邮箱地址列表
     * @param title 邮件标题
     * @param body 邮件内容
     */
    public void syncSend(List<String> emails,String title,String body){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                send(emails,title,body);
            }
        });
        thread.start();
    }

    /**
     * 发送注册帐号成功邮件
     * @param user
     */
    public void sendRegisterEmail(User user){
        List<String> emails = new ArrayList<String>();
        emails.add(user.getUid());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String title = "ApiHub 创建帐号成功";
        StringBuffer sb = new StringBuffer();
        sb.append("<div>");
        sb.append("<div style=\"padding:10px\">您已成功创建ApiHub帐号！</div>");
        sb.append("<div style=\"color:#ffffff;background-color:#4a8cf7;padding:10px;\">帐号:"+user.getUid()+"</div>");
        sb.append("<div style=\"font-size:12px;padding:10px;\">注册时间:<span style=\"color:#aaaaaa\">"+format.format(user.getCreateDate())+"</span></div>");
        sb.append("</div>");
        syncSend(emails,title,sb.toString());
    }

    /**
     * 发送验证邮件
     * @param email 邮箱
     * @param userVerify 验证信息
     */
    public void sendVerifyEmail(String email, UserVerify userVerify){
        String tag = "创建帐号";
        if(userVerify.getCodeType()==1){
            tag = "找回密码";
        }
        List<String> emails = new ArrayList<String>();
        emails.add(email);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String title = "ApiHub "+tag+"验证";
        StringBuffer sb = new StringBuffer();
        sb.append("<div>");
        sb.append("<div style=\"padding:10px\">"+tag+"验证码</div>");
        sb.append("<div style=\"color:#ffffff;background-color:#4a8cf7;padding:10px;font-size:18px;\">"+userVerify.getCode()+"</div>");
        sb.append("<div style=\"font-size:12px;padding:10px;\">过期时间:<span style=\"color:#aaaaaa\">"+format.format(userVerify.getExpireDate())+"</span></div>");
        sb.append("</div>");
        syncSend(emails,title,sb.toString());
    }
}
