package com.example.SocialNetwork.service;

import com.example.SocialNetwork.model.User;
import com.example.SocialNetwork.projection.UserBasicInformation;
import com.example.SocialNetwork.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserBasicInformation> retrieveAllUsers() {
        return userRepository.findAllUserBasicInformationBy();
    }
}
