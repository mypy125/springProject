package com.development.task.service.impl;

import com.development.task.domain.MailType;
import com.development.task.domain.user.User;
import com.development.task.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Properties;
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    @Override
    public void sendEmail(User user, MailType type, Properties params) {

    }
}
