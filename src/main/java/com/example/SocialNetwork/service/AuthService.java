package com.example.SocialNetwork.service;

import com.example.SocialNetwork.dto.UserCreateDTO;
import com.example.SocialNetwork.model.User;
import com.example.SocialNetwork.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;

import java.util.Optional;

import static com.example.SocialNetwork.service.FieldValidatorService.*;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public ResponseEntity<?> register(UserCreateDTO userDTO) {
        try {
            // Verify if the user is valid
            ResponseEntity<?> isValidUserResponse = isValidUser(userDTO);
            if (isValidUserResponse != null) {
                return isValidUserResponse;
            }

            // Save the user if everything is valid
            User user = createUserFromDTO(userDTO);
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully:\n" + userDTO);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
        }
    }

    private ResponseEntity<?> isValidUser(UserCreateDTO userDTO) {
        // Verify the required fields are not empty
        if (userDTO.getUsername() == null || userDTO.getPassword() == null ||
                userDTO.getEmail() == null || userDTO.getAlias() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing required fields.");
        }

        // Verify if there is a user with that username already
        if (userRepository.findByUsernameIgnoreCase(userDTO.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("That username is already in use.");
        }

        // Verify if there is a user with that email already
        if (userRepository.findByEmail(userDTO.getEmail().toLowerCase()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("That email is already in use.");
        }

        // Validate the username
        if (!isValidUsername(userDTO.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not a valid name.");
        }

        // Validate the email
        if (!isValidEmail(userDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not a valid email.");
        }

        // Validate the password
        if (!isValidPassword(userDTO.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not a valid password.");
        }

        // Check for description length
        if (userDTO.getDescription() != null && userDTO.getDescription().length() > 50) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The description is too long.");
        }

        // Verify the alias is valid
        if (!isValidAlias(userDTO.getAlias())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not a valid alias.");
        }

        return null; // Return null if all checks pass
    }

    private User createUserFromDTO(UserCreateDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setAlias(userDTO.getAlias());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEmail(userDTO.getEmail().toLowerCase());
        user.setDescription(userDTO.getDescription() != null ? userDTO.getDescription() : "");
        return user;
    }

    public ResponseEntity<?> login(String identifier, String password) {
        User user = userRepository.findByUsernameOrEmailIgnoreCase(identifier.toLowerCase());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found.");
        }
        if (passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Correct login.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Wrong password.");
        }
    }
}
