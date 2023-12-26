package com.example.memberManagement.service;

import com.example.memberManagement.dto.AuthenResponse;
import com.example.memberManagement.dto.LoginForm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    ResponseEntity<Object> authenticate(LoginForm loginForm);

    AuthenResponse refreshToken(HttpServletRequest request);
}
