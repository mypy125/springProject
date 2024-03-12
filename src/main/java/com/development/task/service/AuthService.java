package com.development.task.service;

import com.development.task.web.dto.auth.JwtRequest;
import com.development.task.web.dto.auth.JwtResponse;

public interface AuthService {
    JwtResponse login(JwtRequest loginRequest);
    JwtResponse refresh(String refreshToken);
}
