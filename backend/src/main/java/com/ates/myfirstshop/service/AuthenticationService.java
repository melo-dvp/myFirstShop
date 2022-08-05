package com.ates.myfirstshop.service;

import com.ates.myfirstshop.exceptions.AuthentificationFailException;
import com.ates.myfirstshop.model.AuthenticationToken;
import com.ates.myfirstshop.model.User;
import com.ates.myfirstshop.repository.TokenRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationService {

    @Autowired
    TokenRepo tokenRepo;

    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
        tokenRepo.save(authenticationToken);
    }

    public AuthenticationToken getToken(User user) {
        return tokenRepo.findByUser(user);
    }

    public User getUser(String token){
        AuthenticationToken authenticationToken = tokenRepo.findByToken(token);
        if(Objects.isNull(authenticationToken)){
            return null;
        }
        return authenticationToken.getUser();
    }

    public void authenticate(String token) throws AuthentificationFailException{
        if(Objects.isNull(token)){
            throw new AuthentificationFailException("token not present");
        }
        if(Objects.isNull(getUser(token))){
            throw new AuthentificationFailException("token not valid");
        }
    }
}
