package com.example.SocialNetwork.controller;

import com.example.SocialNetwork.projection.user.UserBasicInformation;
import com.example.SocialNetwork.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public List<UserBasicInformation> retrieveAllUsers() {
        return userService.retrieveAllUsers();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> retrieveUserById(@PathVariable Long userId) {
        return userService.retrieveUserById(userId);
    }
}
