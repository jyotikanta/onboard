package com.arcticbear.onboard.security;

import com.nimbusds.jose.JWSAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private final JwtEncoder jwtEncoder;

    private final JwtDecoder jwtDecoder;

    public JwtUtil(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    public String generateToken(String username) {
        try {
            Instant now = Instant.now();
            JwtClaimsSet claims = JwtClaimsSet.builder()
                    .issuer("self")
                    .issuedAt(now)
                    .expiresAt(now.plus(1, ChronoUnit.HOURS))
                    .subject(username)
                    .claim("scope", "ROLE_USER")
                    .build();

            logger.debug("Attempting to encode JWT with claims: {}", claims);

            JwsHeader header = JwsHeader.with(() -> "HS256").build();
            JwtEncoderParameters parameters = JwtEncoderParameters.from(header, claims);

            Jwt jwt = jwtEncoder.encode(parameters);
            String token = jwt.getTokenValue();

            logger.debug("Successfully generated token: {}", token);

            return token;
        } catch (Exception e) {
            logger.error("Error generating token", e);
            throw new RuntimeException("Error generating JWT token", e);
        }
    }

    public boolean validateToken(String token, String username){
        try{
            Jwt jwt = jwtDecoder.decode(token);
            return jwt.getSubject().equals(username) && !isTokenExpired(jwt);
        }catch (Exception e){
            System.out.println("Exception occured while validating token " + token);
            e.printStackTrace();
            return false;
        }
    }

    public String extractUserName(String token){
        Jwt jwt = jwtDecoder.decode(token);
        return jwt.getSubject();
    }

    private boolean isTokenExpired(Jwt jwt) {
        return jwt.getExpiresAt().isBefore(Instant.now());
    }
}
