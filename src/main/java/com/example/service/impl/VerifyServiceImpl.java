package com.example.service.impl;

import com.example.service.VerifyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class VerifyServiceImpl implements VerifyService {

    @Resource//JavaMailSender是专门用于发送邮件的对象，自动配置类已经提供了Bean
    JavaMailSender sender;
    @Resource
    StringRedisTemplate template; //导入redis模板
    @Value("${spring.mail.username}")
    String from;

    @Override
    public void sendVerifyCode(String mail) {
        //SimpleMailMessage是一个比较简易的邮件封装，支持设置一些比较简单内容
        SimpleMailMessage message = new SimpleMailMessage();
        //设置邮件标题
        message.setSubject("[xx网站] 您的注册验证码");
        //设置随机验证码
        Random random = new Random();
        int code = random.nextInt(89999)+ 100000;

        template.opsForValue().set("verify:code:"+mail,code+"",3, TimeUnit.MINUTES);
        //设置邮件内容
        message.setText("您的注册验证码为: "+code+"，三分钟内有效，请及时完成注册！如果不是本人操作，请勿略。");
        //设置邮件发送给谁，可以多个，这里就发给你的QQ邮箱
        message.setTo(mail);
        //邮件发送者，这里要与配置文件中的保持一致
        message.setFrom(from);
        //发送邮件
        sender.send(message);
    }

    @Override
    public boolean doVerify(String mail, String code) {
        String string = template.opsForValue().get("verify:code:"+mail);
        if(string == null) return false;
        if(!string.equals(code)) return false;
        template.delete("verify:code:"+mail);
        return true;
    }
}
