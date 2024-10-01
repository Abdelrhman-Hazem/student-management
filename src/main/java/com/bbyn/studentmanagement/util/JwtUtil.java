package com.bbyn.studentmanagement.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${com.bbyn.student-management.jwt.secret}")
    private String secret;

    @Value("${com.bbyn.student-management.jwt.valid-minutes}")
    private int validMinutes;

    public String generateToken(String studentId) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(studentId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * validMinutes))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Long extractStudentId(String token) {
        return Long.parseLong(extractAllClaims(token).getSubject());
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}

