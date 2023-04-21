package com.example.postcrud.controller;


import com.example.postcrud.dto.UserLoginRequestDto;
import com.example.postcrud.dto.UserLoginResponseDto;
import com.example.postcrud.dto.UserSignupRequestDto;
import com.example.postcrud.dto.UserSignupResponseDto;
import com.example.postcrud.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public UserSignupResponseDto signup(@RequestBody UserSignupRequestDto requestDto){
        return userService.signup(requestDto);
    }

    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody UserLoginRequestDto requestDto, HttpServletResponse response){
        return userService.login(requestDto, response);
    }
}
