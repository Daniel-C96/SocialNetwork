package com.example.SocialNetwork.service;

import com.example.SocialNetwork.dto.post.CreatePostRequest;
import com.example.SocialNetwork.model.Post;
import com.example.SocialNetwork.model.User;
import com.example.SocialNetwork.projection.post.PostBasicInformation;
import com.example.SocialNetwork.projection.user.UserBasicInformation;
import com.example.SocialNetwork.repository.PostRepository;
import com.example.SocialNetwork.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> createPost(CreatePostRequest request) {
        try {
            User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Post post = new Post();
            post.setContent(request.getContent());
            post.setUser(currentUser);
            post = postRepository.save(post);
            return ResponseEntity.status(HttpStatus.CREATED).body(post);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating the post: " + e.getMessage());
        }
    }


    public List<PostBasicInformation> retrieveAllPosts() {
        return postRepository.findAllBy();
    }

    public List<PostBasicInformation> findPostsByUserId(long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return postRepository.findByUser_Id(user.get().getId());
        } else {
            return null;
        }
    }

    public List<PostBasicInformation> findLikedPostsByUserId(long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return postRepository.findLikedPostsByUserId(user.get().getId());
        } else {
            return null;
        }
    }

    public List<UserBasicInformation> findUsersLikedByPostId(long postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isPresent()) {
            return userRepository.findUsersLikedByPostId(post.get().getId());
        } else {
            return null;
        }
    }

    public ResponseEntity<?> likePost(Long postId) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            Optional<Post> optionalPost = postRepository.findById(postId);
            if (optionalPost.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The post was not found.");
            }

            Post post = optionalPost.get();
            String message = "";

            // Check if the user already liked the post by its ID
            boolean alreadyLiked = post.getUsersLiked().stream()
                    .anyMatch(likedUser -> likedUser.getId().equals(user.getId()));

            if (alreadyLiked) {
                // Remove like if the user has already liked it
                post.setLikeCount(post.getLikeCount() - 1);
                user.getLikedPosts().remove(post);
                userRepository.deleteLikedPostEntry(user.getId(), postId);
                message = "Like removed from the post.";
            } else {
                // Add like
                post.setLikeCount(post.getLikeCount() + 1);
                user.getLikedPosts().add(post);
                message = "Like added to the post.";
            }
            postRepository.save(post);
            //Have to edit user and not post for usersLiked because usersLiked is a child of postsLiked of User
            userRepository.save(user);

            return ResponseEntity.ok(message);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating like status: " + e.getMessage());
        }
    }

}

