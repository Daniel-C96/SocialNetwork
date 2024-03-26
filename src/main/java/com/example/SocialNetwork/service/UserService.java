package com.example.SocialNetwork.service;

import com.example.SocialNetwork.model.User;
import com.example.SocialNetwork.projection.user.UserBasicInformation;
import com.example.SocialNetwork.projection.user.UserProfileInformation;
import com.example.SocialNetwork.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserBasicInformation> retrieveAllUsers() {
        return userRepository.findAllUserBasicInformationBy();
    }

    public ResponseEntity<?> retrieveUserById(Long id) {
        Optional<UserProfileInformation> user = userRepository.findUserBasicInformationById(id);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found.");
        }
        return ResponseEntity.ok(user);
    }

    public User getOwnUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

