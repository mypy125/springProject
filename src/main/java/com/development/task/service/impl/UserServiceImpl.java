package com.development.task.service.impl;

import com.development.task.domain.exception.ResourceNotfoundException;
import com.development.task.domain.user.User;
import com.development.task.repository.UserRepository;
import com.development.task.service.MailService;
import com.development.task.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    @Override
    @Cacheable(value = "UserService::getById", key = "#id")
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new ResourceNotfoundException("User not found"));
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
