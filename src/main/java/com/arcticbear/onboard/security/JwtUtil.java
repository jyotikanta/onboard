package com.arcticbear.onboard.security;

import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class JwtUtil {
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;

    public JwtUtil(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
    }

    public String generateToken(String username){
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(username)
                .issuedAt(now)
                .expiresAt(now.plusSeconds(1200)) //20 minutes
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
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
