package com.example.SocialNetwork.controller;

import com.example.SocialNetwork.model.User;
import com.example.SocialNetwork.projection.UserBasicInformation;
import com.example.SocialNetwork.repository.UserRepository;
import com.example.SocialNetwork.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

}
