package com.example.SocialNetwork.service;

import com.example.SocialNetwork.dto.user.LoginRequest;
import com.example.SocialNetwork.dto.user.RegisterRequest;
import com.example.SocialNetwork.model.User;
import com.example.SocialNetwork.repository.UserRepository;
import com.example.SocialNetwork.security.JwtService;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private AuthService authService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtService jwtService;

    private Validator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    // Registration Tests
    @Test
    void registerValidUser() {
        RegisterRequest request = RegisterRequest.builder()
                .email("test@example.com")
                .username("username1")
                .alias("alias123")
                .password("Password123")
                .build();

        var violations = validator.validate(request);
        assertEquals(0, violations.size());

        ResponseEntity<?> response = authService.register(request);

        assertEquals(201, response.getStatusCode().value());
        assertEquals("User created successfully.", response.getBody());
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void checkInvalidUserFields() {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        List<RegisterRequest> invalidRequests = createInvalidRegisterRequests();

        for (RegisterRequest request : invalidRequests) {
            var violations = validator.validate(request);
            assertFalse(violations.isEmpty());
        }
        //No need to check the register method because in a real scenario it would not get in the register method
        // after the checks fail
    }

    private List<RegisterRequest> createInvalidRegisterRequests() {
        List<RegisterRequest> requests = new ArrayList<>();

        // Request with empty username and only spaces in alias
        requests.add(RegisterRequest.builder()
                .email("valid@email.com")
                .password("ValidPassword123")
                .alias("   ")
                .build());

        // Request with invalid username (contains space)
        requests.add(RegisterRequest.builder()
                .username("John Doe")
                .email("valid@email.com")
                .password("ValidPassword123")
                .alias("John1998")
                .build());

        // Request with short password (less than 5 characters)
        requests.add(RegisterRequest.builder()
                .username("johndoe")
                .email("valid@email.com")
                .password("pass12")
                .alias("John1998")
                .build());

        // Request with missing email
        requests.add(RegisterRequest.builder()
                .username("johndoe")
                .password("ValidPassword123")
                .alias("John1998")
                .build());

        // Request with invalid email
        requests.add(RegisterRequest.builder()
                .username("daniel123")
                .email("email.wrong")
                .password("ValidPassword123")
                .alias("John1998")
                .build());

        return requests;
    }

    @Test
    void registerWhenUsernameAlreadyExists() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("existingUsername");
        // Simulating userRepository.findByUsernameIgnoreCase returning a non-empty Optional
        when(userRepository.findByUsernameIgnoreCase("existingUsername")).thenReturn(Optional.of(new User()));

        ResponseEntity<?> response = authService.register(request);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("That username is already in use.", response.getBody());
        verify(userRepository, never()).save(any());
    }

    @Test
    void registerWhenEmailAlreadyExists() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("email@gmail.com");
        // Simulating userRepository.findByUsernameIgnoreCase returning a non-empty Optional
        when(userRepository.findByEmail("email@gmail.com")).thenReturn(Optional.of(new User()));

        ResponseEntity<?> response = authService.register(request);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("That email is already in use.", response.getBody());
        verify(userRepository, never()).save(any());
    }

    // Login tests
    @Test
    void correctLogin() {
        LoginRequest request = new LoginRequest("username123", "Password123");

        // Mock user with matching username and password
        User mockedUser = new User();
        mockedUser.setUsername("username123");
        mockedUser.setPassword(passwordEncoder.encode("Password123")); //Encrypt Password

        // Mock userRepository to return mockedUser
        when(userRepository.findByUsernameOrEmailIgnoreCase(request.getIdentifier())).thenReturn(Optional.of(mockedUser));
        // Simulate passwordEncoder.matches returning true (password matches)
        when(passwordEncoder.matches(request.getPassword(), mockedUser.getPassword())).thenReturn(true);

        ResponseEntity<?> response = authService.login(request);
        assertEquals(200, response.getStatusCode().value());

    }

    @Test
    void incorrectLogin() {
        LoginRequest request = new LoginRequest("username123", "wrong_password");

        // Mock user with matching username and password
        User mockedUser = new User();
        mockedUser.setUsername("username123");
        mockedUser.setPassword(passwordEncoder.encode("Password123"));

        // Mock userRepository to return mockedUser
        when(userRepository.findByUsernameOrEmailIgnoreCase(request.getIdentifier())).thenReturn(Optional.of(mockedUser));
        // Simulate passwordEncoder.matches returning false
        when(passwordEncoder.matches(request.getPassword(), mockedUser.getPassword())).thenReturn(false);
        ResponseEntity<?> response = authService.login(request);
        assertEquals(401, response.getStatusCode().value());
    }
}