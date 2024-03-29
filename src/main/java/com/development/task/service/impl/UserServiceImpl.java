package com.development.task.service.impl;

import com.development.task.domain.user.User;
import com.development.task.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Override
    public User getById(Long id) {
        return null;
    }

    @Override
    public User getByUserName(String username) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public User create(User user) {
        return null;
    }

    @Override
    public boolean isTaskOwner(Long userId, Long taskId) {
        return false;
    }

    @Override
    public User getTaskAuthor(Long taskId) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
