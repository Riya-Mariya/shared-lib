package com.onlinebidding.shared.jwt.impl;

import com.onlinebidding.shared.jwt.JwtValidator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class JwtValidatorImpl implements JwtValidator {

    @Override
    public String extractTokenFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7); // Remove "Bearer " prefix
        }
        return null;
    }

    public boolean isExpired(Instant expiredAt) {
        return expiredAt.isBefore(Instant.now());
    }
}
