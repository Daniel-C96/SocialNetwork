package com.example.SocialNetwork.controller;

import com.example.SocialNetwork.model.User;
import com.example.SocialNetwork.projection.user.UserBasicInformation;
import com.example.SocialNetwork.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "User Controller", description = "Functions related to Users")
public class UserController {

    @Autowired
    UserService userService;

    @Operation(summary = "Retrieves all Users ")
    @GetMapping("/users")
    public List<UserBasicInformation> retrieveAllUsers() {
        return userService.retrieveAllUsers();
    }

    @Operation(summary = "Retrieves a User from userId",
            description = "Retrieves the User with the userId in the Path")
    @GetMapping("/{userId}")
    public ResponseEntity<?> retrieveUserById(@PathVariable Long userId) {
        return userService.retrieveUserById(userId);
    }

    @Operation(summary = "Get full own User info")
    @GetMapping("/user")
    public User getOwnUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
