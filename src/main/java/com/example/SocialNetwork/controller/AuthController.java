package com.example.SocialNetwork.controller;

import com.example.SocialNetwork.dto.UserCreateDTO;
import com.example.SocialNetwork.dto.UserLoginDTO;
import com.example.SocialNetwork.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserCreateDTO userDTO) {
        return authService.register(userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDTO userLoginDTO) {
        return authService.login(userLoginDTO.getIdentifier(), userLoginDTO.getPassword());
    }
}
