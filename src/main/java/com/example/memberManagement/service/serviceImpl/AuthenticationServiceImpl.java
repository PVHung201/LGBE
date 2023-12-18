package com.example.memberManagement.service.serviceImpl;

import com.example.memberManagement.model.dto.AuthenResponse;
import com.example.memberManagement.model.dto.LoginForm;
import com.example.memberManagement.model.entity.Member;
import com.example.memberManagement.model.repository.MemberRepository;
import com.example.memberManagement.service.AuthenticationService;
import com.example.memberManagement.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Autowired
    private final UserDetailsService userDetailsService;

    @Autowired
    MemberRepository memberRepository;


    @Override
    public ResponseEntity<Object> authenticate(LoginForm loginForm) {

        Authentication authentication = null;

        try{
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginForm.getId(),
                            loginForm.getPassword()
                    )
            );
        } catch (Exception e){

                Map<String, String> errorResponse = new HashMap<>();
                errorResponse.put("error", "Please check id or password again");
                return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        AuthenResponse authenResponse = new AuthenResponse(accessToken, refreshToken);
        return ResponseEntity.ok(authenResponse);
    }

    @Override
    public AuthenResponse refreshToken(HttpServletRequest request) {
        final String authenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if(authenHeader == null || !authenHeader.startsWith("Bearer ")){
            return null;
        }
        refreshToken = authenHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        if(username != null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(jwtService.isTokenValid(refreshToken, userDetails)){
                String accessToken = jwtService.generateAccessToken(userDetails);
                String newRefreshToken = jwtService.generateRefreshToken(userDetails);
                return new AuthenResponse(accessToken, newRefreshToken);
            }
        }
        return null;
    }
}
