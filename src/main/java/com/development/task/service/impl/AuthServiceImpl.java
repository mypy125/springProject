package com.development.task.service.impl;

import com.development.task.service.AuthService;
import com.development.task.web.dto.auth.JwtRequest;
import com.development.task.web.dto.auth.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Override
    public JwtResponse login(JwtRequest loginRequest) {
        return null;
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return null;
    }
}
