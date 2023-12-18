package com.example.memberManagement.service;

import com.example.memberManagement.model.dto.AuthenResponse;
import com.example.memberManagement.model.dto.LoginForm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface AuthenticationService {
    public ResponseEntity<Object> authenticate(LoginForm loginForm);

    public AuthenResponse refreshToken(HttpServletRequest request);
}
