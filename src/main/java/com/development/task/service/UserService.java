package com.development.task.service;

import com.development.task.domain.user.User;

public interface UserService {
    User getById(Long id);
    User getByUserName(String username);
    User update(User user);
    User create(User user);
    boolean isTaskOwner(Long userId, Long taskId);
    User getTaskAuthor(Long taskId);
    void delete(Long id);
}
