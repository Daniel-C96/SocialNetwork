package com.example.SocialNetwork.controller;

import com.example.SocialNetwork.model.User;
import com.example.SocialNetwork.projection.user.UserBasicInformation;
import com.example.SocialNetwork.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
        return userService.getOwnUser();
    }

    @Operation(summary = "Follow a User from userId",
            description = "The User that provides a token follows/unfollows the userId in the path")
    @PutMapping("follow/{userId}")
    public ResponseEntity<?> followUser(@PathVariable Long userId) {
        return userService.followUser(userId);
    }

    @Operation(summary = "Get all followers from userId")
    @GetMapping("/followers/{userId}")
    public List<UserBasicInformation> retrieveFollowers(@PathVariable Long userId) {
        return userService.retrieveFollowers(userId);
    }

    @Operation(summary = "Get all users that userId is following")
    @GetMapping("/following/{userId}")
    public List<UserBasicInformation> retrieveFollowing(@PathVariable Long userId) {
        return userService.retrieveFollowing(userId);
    }

}
