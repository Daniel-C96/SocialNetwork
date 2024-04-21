package com.example.SocialNetwork.service;

import com.example.SocialNetwork.dto.user.RegisterRequest;
import com.example.SocialNetwork.dto.user.LoginRequest;
import com.example.SocialNetwork.model.Role;
import com.example.SocialNetwork.model.User;
import com.example.SocialNetwork.repository.UserRepository;
import com.example.SocialNetwork.security.JwtService;
import com.example.SocialNetwork.service.s3.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.Optional;

import static com.example.SocialNetwork.config.Constants.S3_URL;
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

    @Autowired
    private StorageService storageService;

    public ResponseEntity<?> register(RegisterRequest request) {
        try {
            // Verify if the user is valid
            ResponseEntity<?> isValidUserResponse = isValidUser(request);
            if (isValidUserResponse != null) {
                return isValidUserResponse;
            }

            // Create the user if everything is valid
            User user = createUserFromDTO(request);
            //Check if there is a MultipartFile for the profile picture and it is a picture
            if (request.getFile() != null && request.getFile().isPresent() && Objects.requireNonNull(request.getFile().get().getContentType()).startsWith("image/")) {
                try {
                    String filename = storageService.uploadFile(request.getFile().get(), "uploads/profile-pictures/");
                    filename = "/uploads/profile-pictures/" + filename.substring(filename.lastIndexOf("/") + 1); // Concatenate directory with filename
                    //filename = S3_URL + filename; //Save whole path to the DB
                    user.setProfilePicture(filename);
                } catch (Exception e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while uploading profile picture: " + e.getMessage());
                }
            }
            userRepository.save(user);
            System.out.println("User created: " + user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully.");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }

    private ResponseEntity<?> isValidUser(RegisterRequest request) {
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

    private User createUserFromDTO(RegisterRequest request) {
        return User.builder()
                .username(request.getUsername())
                .alias(request.getAlias())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail().toLowerCase())
                .description(Optional.ofNullable(request.getDescription()).orElse(""))
                .role(Role.USER)
                .profilePicture("/uploads/profile-pictures/default_profile_picture.png")
                .build();
    }

    public ResponseEntity<?> login(LoginRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                            request.getIdentifier(),
                            request.getPassword()
                    )
            );
            var user = userRepository.findByUsernameOrEmailIgnoreCase(request.getIdentifier())
                    .orElseThrow();
            var jwtToken = jwtService.generateToken(user);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(jwtToken);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
