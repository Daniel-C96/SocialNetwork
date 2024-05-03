package com.example.SocialNetwork.service;

import com.example.SocialNetwork.dto.user.RegisterRequest;
import com.example.SocialNetwork.repository.UserRepository;
import com.example.SocialNetwork.service.cloudinary.CloudinaryStorageService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CloudinaryStorageService cloudinaryStorageService;

    @InjectMocks
    private AuthService authService;

    @Test
    void register_WhenValidRequest_ExceptUserCreatedSuccessfully() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("username");
        request.setPassword("Password123");
        request.setAlias("alias123");
        request.setEmail("test@example.com");

        ResponseEntity<?> response = authService.register(request);

        assertEquals(201, response.getStatusCode().value());
        assertEquals("User created successfully.", response.getBody());
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void login() {
    }
}