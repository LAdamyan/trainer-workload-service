package com.trainer.workload.config.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;

@Slf4j
@Component
public class JwtTokenValidator {
    private final SecretKey secretKey;

    public JwtTokenValidator(@Value("${backend.secret.key}") String backendSecretKey) {
        if (backendSecretKey == null || backendSecretKey.isEmpty()) {
            log.error("[JwtTokenValidator] Backend secret key is not configured or missing in application properties!");
            throw new RuntimeException("Backend secret key is not configured properly.");
        }

        // Decode Base64 representation and create a SecretKey for validation
        this.secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(backendSecretKey));
        log.info("[JwtTokenValidator] Successfully loaded backend secret key for validation.");
    }

    public boolean validateBackendToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secretKey) // Validate token using the loaded secret key
                    .build()
                    .parseClaimsJws(token); // Parse the token claims
            log.info("[JwtTokenValidator] Backend JWT Token is valid!");
            return true;
        } catch (Exception e) {
            log.error("[JwtTokenValidator] Error validating Backend JWT Token: {}", e.getMessage());
            return false;
        }
    }
}
