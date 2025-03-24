package com.schedulo.schedulo.utils.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JWTHelper {

    private final String secretKey;
    private final Algorithm algorithm;

    public JWTHelper() {
        // Load the secret key from environment variables or a secure source
        this.secretKey = "Hello";
        this.algorithm = Algorithm.HMAC256(this.secretKey);

        if (this.secretKey == null || this.secretKey.isEmpty()) {
            throw new RuntimeException("JWT secret key is not configured in environment variables.");
        }
    }

    public String generateToken(String email) {
        try {
            Instant now = Instant.now();
            Instant expiration = now.plus(4320, ChronoUnit.MINUTES); //3-days

            // Generate the JWT
            return JWT.create()
                    .withSubject(email)
                    .withIssuedAt(Date.from(now))
                    .withExpiresAt(Date.from(expiration))
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException("Failed to generate JWT token", e);
        }
    }

    public DecodedJWT verifyToken(String token) throws JWTVerificationException {
        return JWT.require(this.algorithm)
                .build()
                .verify(token);
    }
}