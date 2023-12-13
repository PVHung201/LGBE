package com.example.memberManagement.service.serviceImpl;

import com.example.memberManagement.model.dto.AuthenResponse;
import com.example.memberManagement.model.dto.LoginForm;
import com.example.memberManagement.service.AuthenticationService;
import com.example.memberManagement.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;


    @Override
    public AuthenResponse authenticate(LoginForm loginForm) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginForm.getUsername(),
                        loginForm.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = (User) authentication.getPrincipal();
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return new AuthenResponse(accessToken, refreshToken);
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
