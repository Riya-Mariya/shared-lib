

package com.onlinebidding.shared.filter;


import com.onlinebidding.shared.jwt.JwtValidator;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JwtTokenValidationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtValidator jwtValidatorService;

    @Autowired
    private UserDetailsService userDetailsService;
    private JwtDecoder jwtDecoder;
    private static final Logger logger = LoggerFactory.getLogger(JwtTokenValidationFilter.class);

    public JwtTokenValidationFilter(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        var token = jwtValidatorService.extractTokenFromRequest(request);

        if (token != null) {
            try {
                Jwt jwt = jwtDecoder.decode(token);
                String username = jwt.getSubject();
                boolean isExpired = jwtValidatorService.isExpired(jwt.getExpiresAt());
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (userDetails != null && !isExpired) {
                    Authentication authenticationToken = new JwtAuthenticationToken(jwt, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Invalid or expired token");
                    return;
                }
            } catch (Exception e) {
                logger.error("Invalid Token");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Invalid or expired token");
            }
        }

        filterChain.doFilter(request, response);
    }
}


