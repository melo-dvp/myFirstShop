package com.ates.myfirstshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.AuthenticationException;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Configuration
public class PublicKeyConfiguration {
    @Bean
    public PublicKey generatePublicKey(final PublicKeyServiceClient publicKeyServiceClient) throws NoSuchAlgorithmException, InvalidKeySpecException, AuthenticationException {
        var keyFactory = KeyFactory.getInstance("RSA");
        final String key = publicKeyServiceClient.requestKeyFromKeycloak();
        var pubKeySpecX509EncodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(key));
        return keyFactory.generatePublic(pubKeySpecX509EncodedKeySpec);
    }
}
