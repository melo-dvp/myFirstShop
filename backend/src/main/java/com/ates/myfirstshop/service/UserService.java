package com.ates.myfirstshop.service;

import com.ates.myfirstshop.dto.ResponseDto;
import com.ates.myfirstshop.dto.user.SignInDto;
import com.ates.myfirstshop.dto.user.SignInResponseDto;
import com.ates.myfirstshop.dto.user.SignupDto;
import com.ates.myfirstshop.exceptions.AuthentificationFailException;
import com.ates.myfirstshop.exceptions.CustomException;
import com.ates.myfirstshop.model.AuthenticationToken;
import com.ates.myfirstshop.model.User;
import com.ates.myfirstshop.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    AuthenticationService authenticationService;

    @Transactional
    public ResponseDto signup(SignupDto signupDto) {
        // check if user is already present
        if(Objects.nonNull(userRepo.findByEmail(signupDto.getEmail()))){
            throw new CustomException("user already present");
        }

        String encryptedpassword = signupDto.getPassword();

        try {
            encryptedpassword = hashPassword(signupDto.getPassword());
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }

        User user = new User(
                signupDto.getFirstName(),
                signupDto.getLastName(),
                signupDto.getEmail(),
                encryptedpassword
        );

        userRepo.save(user);

        final AuthenticationToken authenticationToken = new AuthenticationToken(user);

        authenticationService.saveConfirmationToken(authenticationToken);

        ResponseDto responseDto = new ResponseDto("success", "user created successfully");
        return responseDto;
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        return hash;
    }

    public SignInResponseDto signIn(SignInDto signInDto) {

        // find user by email
        User user = userRepo.findByEmail(signInDto.getEmail());

        if(Objects.isNull(user)){
            throw new AuthentificationFailException("user and password are not valid");
        }

        // hash the password
        try {
            if(!user.getPassword().equals(hashPassword(signInDto.getPassword()))){
                throw new AuthentificationFailException("user and password are not valid");
            }
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }

        // if password match
        AuthenticationToken token = authenticationService.getToken(user);

        // retrive the token
        if(Objects.isNull(token)){
            throw new CustomException("token is not present");
        }

        return new SignInResponseDto("success", token.getToken());
    }
}
