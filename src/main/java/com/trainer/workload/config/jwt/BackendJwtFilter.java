package com.trainer.workload.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;
@Slf4j
@Configuration
public class BackendJwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenValidator  jwtTokenValidator;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws java.io.IOException, jakarta.servlet.ServletException {

        log.info("[BackendJwtFilter] Intercepting request for validation...");
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            log.info("[BackendJwtFilter] Token extracted: {}", token);

            if (!jwtTokenValidator.validateBackendToken(token)) {
                log.error("[BackendJwtFilter] Token validation FAILED. Unauthorized access.");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Backend JWT Token");
                return;
            }
            log.info("[BackendJwtFilter] Token validation SUCCESSFUL. Proceeding with request.");
        } else {
            log.warn("[BackendJwtFilter] Authorization header is missing or invalid.");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization Header Missing");
            return;
        }

        chain.doFilter(request, response); // Proceed with the request if validation succeeds
    }
}
