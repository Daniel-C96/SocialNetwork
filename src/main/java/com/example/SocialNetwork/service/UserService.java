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

    public ResponseEntity<?> followUser(Long id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> userFound = userRepository.findById(id);
        if (userFound.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found.");
        }
        User userFollowed = userFound.get();
        String message = "";
        if (user.getId().equals(userFollowed.getId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You cannot follow your own account");
        }

        boolean alreadyFollowed = user.getFollowing().stream()
                .anyMatch(userPassed -> userPassed.getId().equals(userFollowed.getId()));
        if (alreadyFollowed) {
            userFollowed.getFollowers().remove(user);
            user.getFollowing().remove(userFollowed);
            userRepository.deleteFollower(userFollowed.getId(), user.getId());
            userRepository.deleteFollowing(user.getId(), userFollowed.getId());
            message = "User unfollowed.";
        } else {
            userFollowed.getFollowers().add(user);
            user.getFollowing().add(userFollowed);
            message = "User followed.";
        }

        userFollowed.updateFollowerCount();
        user.updateFollowingCount();
        userRepository.saveAll(List.of(user, userFollowed));

        return ResponseEntity.ok(message);
    }


    public List<UserBasicInformation> retrieveFollowers(Long id) {
        return userRepository.findFollowersByUserId(id);
    }

    public List<UserBasicInformation> retrieveFollowing(Long id) {
        return userRepository.findFollowingByUserId(id);
    }


    public User getOwnUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


}

