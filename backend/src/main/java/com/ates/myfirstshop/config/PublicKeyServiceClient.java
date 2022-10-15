package com.ates.myfirstshop.config;

import com.ates.myfirstshop.exceptions.AuthentificationFailException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@Slf4j
public class PublicKeyServiceClient{
    @Value("${keycloak.auth-server-url}/realms/${keycloak.realm}")
    private String publicKeyUrl;

    public String requestKeyFromKeycloak() throws AuthentificationFailException {
        try{
            var url = new URL(publicKeyUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("accept", "application/json");
            var responseStream = connection.getInputStream();
            var mapper = new ObjectMapper();
            PublicKeyResponse response = mapper.readValue(responseStream, PublicKeyResponse.class);
            responseStream.close();
            return response.getPublicKey();
        }catch(IOException e){
            throw new AuthentificationFailException("Could not extract public key from Keycloak!");
        }

    }

}
