package com.example.SocialNetwork.service;

import com.example.SocialNetwork.dto.PostCreateDTO;
import com.example.SocialNetwork.model.Post;
import com.example.SocialNetwork.model.User;
import com.example.SocialNetwork.projection.PostBasicInformation;
import com.example.SocialNetwork.repository.PostRepository;
import com.example.SocialNetwork.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public Post createPost(PostCreateDTO postDTO) {
        Optional<User> user = userRepository.findById(postDTO.getUserId());
        if (user.isPresent()) {
            Post post = new Post();
            post.setContent(postDTO.getContent());
            post.setUser(user.get());
            return postRepository.save(post);
        } else {
            return null;
        }
    }

    public List<PostBasicInformation> retrieveAllPosts() {
        return postRepository.findAllPostBasicInformationBy();
    }

    public List<Post> findPostsByUserId(long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return postRepository.findAllPostsByUser(user).reversed(); //Reverse to get latest posts first
        } else {
            return null;
        }
    }

}
