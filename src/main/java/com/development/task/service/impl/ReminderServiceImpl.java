package com.development.task.service.impl;

import com.development.task.domain.MailType;
import com.development.task.domain.task.Task;
import com.development.task.domain.user.User;
import com.development.task.service.MailService;
import com.development.task.service.Reminder;
import com.development.task.service.TaskService;
import com.development.task.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class ReminderServiceImpl implements Reminder {
    private final TaskService taskService;
    private final UserService userService;
    private final MailService mailService;
    private final Duration duration = Duration.ofHours(1);

    @Scheduled(cron = "0 * * * * *")
    @Override
    public void remindForTask() {
        List<Task> tasks = taskService.getAllSoonTasks(duration);
        tasks.forEach(task -> {
            User user = userService.getTaskAuthor(task.getId());
            Properties properties = new Properties();
            properties.setProperty("task.title",task.getTitle());
            properties.setProperty("task.description",task.getDescription());
            mailService.sendEmail(user, MailType.REMINDER,properties);
        });
    }
}
