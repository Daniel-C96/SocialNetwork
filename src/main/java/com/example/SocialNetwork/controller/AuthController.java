package com.example.SocialNetwork.controller;

import com.example.SocialNetwork.dto.user.RegisterRequest;
import com.example.SocialNetwork.dto.user.LoginRequest;
import com.example.SocialNetwork.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authorization Controller", description = "Controller to Log in and Register")
public class AuthController {
    @Autowired
    AuthService authService;

    @Operation(description = "This is the endpoint to Sign Up a User.",
            summary = "Sign Up")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @Operation(description = "This is the endpoint to Login that provides a JWT when the Login is successful.",
            summary = "Login")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
