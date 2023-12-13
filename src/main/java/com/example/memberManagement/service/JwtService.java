package com.example.memberManagement.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.sql.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public interface JwtService {
    public Claims extractAllClaims(String token);

//    public <T> extractClaim(String token, Function<Claims, T> claimsResolver);

    public String generateAccessToken(UserDetails userDetails);

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    public String extractUsername(String token);

    public String generateRefreshToken(UserDetails userDetails);

    public boolean isTokenValid(String token, UserDetails userDetails);

    public boolean isTokenExpired(String token);

    public Date extractExpiration(String token);

    public String extractType(String token);

    public Key getSignInKey();
}