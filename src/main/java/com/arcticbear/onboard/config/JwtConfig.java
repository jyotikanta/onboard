package com.arcticbear.onboard.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Configuration
public class JwtConfig {
    private static final String BASE64_SECRET = "3iAqzfZD70TSpEgvny7cj53TeEPbfd5LbMXwsSCatqE=";
    private static final byte[] SECRET_KEY_BYTES = Base64.getDecoder().decode(BASE64_SECRET);
    public static final SecretKey SECRET_KEY = new SecretKeySpec(SECRET_KEY_BYTES, "HmacSHA256");

    @Bean
    public JWKSet jwkSet() {
        // Create an OctetSequenceKey (symmetric key) using the secret key
        JWK jwk = new OctetSequenceKey.Builder(SECRET_KEY_BYTES)
                .keyID("myKeyId") // Optionally set a key ID (kid)
                .algorithm(new com.nimbusds.jose.Algorithm("HS256"))
                .build();
        return new JWKSet(jwk);
    }

    @Bean
    public JwtEncoder jwtEncoder(){

        JWKSource<SecurityContext> jwkSource = new ImmutableSecret<>(SECRET_KEY);
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    public JwtDecoder jwtDecoder(){
        return NimbusJwtDecoder.withSecretKey(SECRET_KEY).build();
    }
}
