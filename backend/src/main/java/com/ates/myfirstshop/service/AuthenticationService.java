package com.ates.myfirstshop.service;

import com.ates.myfirstshop.exceptions.AuthentificationFailException;
import com.ates.myfirstshop.model.AuthenticationToken;
import com.ates.myfirstshop.model.User;
import com.ates.myfirstshop.repository.TokenRepo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.Date;
import java.util.Objects;

@Service
@AllArgsConstructor
public class AuthenticationService {

    @Autowired
    TokenRepo tokenRepo;

    @Getter
    PublicKey publicKey;


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

    private Date extractExpiration(String token) throws ExpiredJwtException{
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody().getExpiration();
    }

    private String extractClaim(final String token, final String name){
        final Claims result = Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
        return result.get(name, String.class);
    }

    public String extractUserName(final String token){
        return extractClaim(token, StandardClaimNames.PREFERRED_USERNAME);
    }

    public boolean isTokenExpired(String token){
        try{
            return extractExpiration(token).before(new Date());
        } catch (ExpiredJwtException expiredJwtException){
            return true;
        }
    }

    public Boolean successfullAuthentication(String token){
        if(!isTokenExpired(token)){
            return true;
        }else{
            return false;
        }
    }
}



