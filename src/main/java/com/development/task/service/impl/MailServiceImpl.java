package com.development.task.service.impl;


import com.development.task.domain.MailType;
import com.development.task.domain.user.User;
import com.development.task.service.MailService;
import freemarker.template.Configuration;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final Configuration configuration;
    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(final User user,final MailType type,final Properties params) {
        switch (type){
            case REGISTRATION -> sendRegistrationEmail(user,params);
            case REMINDER -> sendReminderEmail(user,params);
            default -> {

            }
        }
    }

    @SneakyThrows
    private void sendReminderEmail(final User user,final Properties params) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,false,"UTF-8");
        helper.setSubject("You have task to do in 1 hour");
        helper.setTo(user.getUsername());
        String cont = getReminderEmailContent(user, params);
        helper.setText(cont,true);
        mailSender.send(mimeMessage);
    }

    @SneakyThrows
    private String getReminderEmailContent(final User user, final Properties properties){
        StringWriter writer = new StringWriter();
        Map<String,Object> mod = new HashMap<>();
        mod.put("name",user.getName());
        mod.put("title",properties.getProperty("task.title"));
        mod.put("description",properties.getProperty("task.description"));
        configuration.getTemplate("reminder.ftlh").process(mod,writer);
        return writer.getBuffer().toString();
    }
    @SneakyThrows
    private void sendRegistrationEmail(final User user,final Properties params) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,false, "UTF-8");
        helper.setSubject("Thank you for registration "+user.getName());
        helper.setTo(user.getUsername());
        String content = getRegistrationEmailContent(user,params);
        helper.setText(content,true);
        mailSender.send(mimeMessage);
    }

    @SneakyThrows
    private String getRegistrationEmailContent(final User user,final Properties properties) {
        StringWriter writer = new StringWriter();
        Map<String,Object> mod = new HashMap<>();
        mod.put("name",user.getName());
        configuration.getTemplate("register.ftlh").process(mod,writer);
        return writer.getBuffer().toString();
    }


}
