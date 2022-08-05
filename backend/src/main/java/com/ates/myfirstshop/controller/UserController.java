package com.ates.myfirstshop.controller;

import com.ates.myfirstshop.dto.ResponseDto;
import com.ates.myfirstshop.dto.user.SignInDto;
import com.ates.myfirstshop.dto.user.SignInResponseDto;
import com.ates.myfirstshop.dto.user.SignupDto;
import com.ates.myfirstshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseDto signup(@RequestBody SignupDto signupDto){
        return userService.signup(signupDto);
    }

    @PostMapping("/signin")
    public SignInResponseDto signIn(@RequestBody SignInDto signInDto){
        return userService.signIn(signInDto);
    }



}
