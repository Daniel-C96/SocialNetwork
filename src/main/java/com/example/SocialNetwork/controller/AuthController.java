package com.example.SocialNetwork.controller;

import com.example.SocialNetwork.dto.user.RegisterRequest;
import com.example.SocialNetwork.dto.user.LoginRequest;
import com.example.SocialNetwork.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authorization Controller", description = "Controller to Log in and Register")
public class AuthController {
    @Autowired
    AuthService authService;

    @Operation(description = "This is the endpoint to Sign Up a User.",
            summary = "Sign Up")
    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> register(@RequestBody RegisterRequest request, @RequestParam(value = "file", required = false) MultipartFile file) {
        return authService.register(request, file);
    }

    @Operation(description = "This is the endpoint to Login that provides a JWT when the Login is successful.",
            summary = "Login")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
