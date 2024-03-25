package com.example.SocialNetwork.service;

import com.example.SocialNetwork.dto.user.UserCreateDTO;
import com.example.SocialNetwork.dto.user.UserLoginDTO;
import com.example.SocialNetwork.model.Role;
import com.example.SocialNetwork.model.User;
import com.example.SocialNetwork.repository.UserRepository;
import com.example.SocialNetwork.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.SocialNetwork.service.FieldValidatorService.*;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public ResponseEntity<?> register(UserCreateDTO request) {
        try {
            // Verify if the user is valid
            ResponseEntity<?> isValidUserResponse = isValidUser(request);
            if (isValidUserResponse != null) {
                return isValidUserResponse;
            }

            // Save the user if everything is valid
            User user = createUserFromDTO(request);
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully:\n" + request);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }

    private ResponseEntity<?> isValidUser(UserCreateDTO request) {
        // Verify the required fields are not empty
        if (request.getUsername() == null || request.getPassword() == null ||
                request.getEmail() == null || request.getAlias() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing required fields.");
        }

        // Verify if there is a user with that username already
        if (userRepository.findByUsernameIgnoreCase(request.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("That username is already in use.");
        }

        // Verify if there is a user with that email already
        if (userRepository.findByEmail(request.getEmail().toLowerCase()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("That email is already in use.");
        }

        // Validate the username
        if (!isValidUsername(request.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not a valid name.");
        }

        // Validate the email
        if (!isValidEmail(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not a valid email.");
        }

        // Validate the password
        if (!isValidPassword(request.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not a valid password.");
        }

        // Check for description length
        if (request.getDescription() != null && request.getDescription().length() > 50) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The description is too long.");
        }

        // Verify the alias is valid
        if (!isValidAlias(request.getAlias())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not a valid alias.");
        }

        return null; // Return null if all checks pass
    }

    private User createUserFromDTO(UserCreateDTO request) {
        return User.builder()
                .username(request.getUsername())
                .alias(request.getAlias())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail().toLowerCase())
                .description(Optional.ofNullable(request.getDescription()).orElse(""))
                .role(Role.USER)
                .profilePicture("default_profile_picture.png")
                .build();
    }

    public ResponseEntity<?> login(UserLoginDTO request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        request.getIdentifier(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByUsernameOrEmailIgnoreCase(request.getIdentifier())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(jwtToken);
    }
}
