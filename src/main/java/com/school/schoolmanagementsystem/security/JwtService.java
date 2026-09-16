package com.school.schoolmanagementsystem.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET_KEY =
            "schoolManagementSystemSecretKey2026ForJwtAuthentication";

    private static final long EXPIRATION_TIME =
            1000 * 60 * 60; // 1 hour


    private SecretKey getSigningKey() {

        return Keys.hmacShaKeyFor(
                SECRET_KEY.getBytes(
                        StandardCharsets.UTF_8
                )
        );
    }


    public String generateToken(User user) {

        return Jwts.builder()

                .subject(user.getEmail())

                .claim(
                        "role",
                        user.getRole().name()
                )

                .issuedAt(new Date())

                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + EXPIRATION_TIME
                        )
                )

                .signWith(getSigningKey())

                .compact();
    }


    public String extractEmail(String token) {

        return extractAllClaims(token)
                .getSubject();
    }


    public String extractRole(String token) {

        return extractAllClaims(token)
                .get("role", String.class);
    }


    public boolean isTokenValid(
            String token,
            User user) {

        String email = extractEmail(token);

        return email.equals(user.getEmail())
                && !isTokenExpired(token);
    }


    private boolean isTokenExpired(
            String token) {

        return extractAllClaims(token)
                .getExpiration()
                .before(new Date());
    }


    private Claims extractAllClaims(
            String token) {

        return Jwts.parser()

                .verifyWith(getSigningKey())

                .build()

                .parseSignedClaims(token)

                .getPayload();
    }
}