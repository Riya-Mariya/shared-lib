package com.onlinebidding.shared.jwt;

import jakarta.servlet.http.HttpServletRequest;

import java.time.Instant;

public interface JwtValidator {
    String extractTokenFromRequest(HttpServletRequest request);

    boolean isExpired(Instant expiredAt);
}
