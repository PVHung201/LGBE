package com.example.memberManagement.controller;

import com.example.memberManagement.model.dto.AuthenResponse;
import com.example.memberManagement.model.dto.LoginForm;
import com.example.memberManagement.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public AuthenResponse login(LoginForm loginForm){
        AuthenResponse authenResponse = authenticationService.authenticate(loginForm);
        return authenResponse;
    }

    @PostMapping(value = "/refresh-token")
    public AuthenResponse refreshToken(HttpServletRequest request){
        AuthenResponse authenResponse = authenticationService.refreshToken(request);
        return authenResponse;
    }
}
