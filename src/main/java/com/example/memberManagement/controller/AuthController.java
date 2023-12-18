package com.example.memberManagement.controller;

import com.example.memberManagement.model.dto.AuthenResponse;
import com.example.memberManagement.model.dto.LoginForm;
import com.example.memberManagement.model.entity.Member;
import com.example.memberManagement.model.repository.MemberRepository;
import com.example.memberManagement.service.AuthenticationService;
import com.example.memberManagement.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    private final MemberRepository memberRepository;

    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(LoginForm loginForm){
        ResponseEntity<Object> authenResponse = authenticationService.authenticate(loginForm);
        return authenResponse;
    }

    @GetMapping("/{id}")
    public Member getId(@PathVariable(name = "id") String id){
        return memberRepository.findMemberById(id);
    }

    @PostMapping(value = "/refresh-token")
    public AuthenResponse refreshToken(HttpServletRequest request){
        AuthenResponse authenResponse = authenticationService.refreshToken(request);
        return authenResponse;
    }
}
