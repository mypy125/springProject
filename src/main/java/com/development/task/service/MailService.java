package com.development.task.service;

import com.development.task.domain.MailType;
import com.development.task.domain.user.User;

import java.util.Properties;

public interface MailService {
    void sendEmail(User user, MailType type, Properties params);
}
