package com.example.SocialNetwork.service;

import com.example.SocialNetwork.dto.post.CreatePostRequest;
import com.example.SocialNetwork.dto.post.ViewPostDetails;
import com.example.SocialNetwork.model.Post;
import com.example.SocialNetwork.model.User;
import com.example.SocialNetwork.projection.post.PostBasicInformation;
import com.example.SocialNetwork.projection.user.UserBasicInformation;
import com.example.SocialNetwork.repository.PostRepository;
import com.example.SocialNetwork.repository.UserRepository;
import com.example.SocialNetwork.service.s3.S3StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private S3StorageService s3StorageService;

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

    //Lists the posts that were liked by a user
    public List<PostBasicInformation> findLikedPostsByUserId(long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            return postRepository.findLikedPostsByUserId(user.get().getId());
        } else {
            return null;
        }
    }

    //Lists the users that have liked a post
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
            // Check if the user already liked the post by its ID
            boolean alreadyLiked = post.getUsersLiked().stream()
                    .anyMatch(likedUser -> likedUser.getId().equals(user.getId()));

            if (alreadyLiked) {
                removeLike(postId, post, user);
                return ResponseEntity.ok("Like removed from the post.");
            } else {
                addLike(post, user);
                return ResponseEntity.ok("Like added to the post.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating like status: " + e.getMessage());
        }
    }

    private void removeLike(Long postId, Post post, User user) {
        //post.setLikeCount(post.getLikeCount() - 1); This does not work, I don't know why.
        postRepository.removeLikeFromPost(postId); //Method created to solve the problem
        post.getUsersLiked().remove(user);
        postRepository.save(post);

        user.getLikedPosts().remove(post);
        //Method created to delete entry from liked_posts table because it does not auto-update with JPA
        userRepository.deleteLikedPostEntry(user.getId(), postId);
        userRepository.save(user); // Update user entity

    }

    private void addLike(Post post, User user) {
        //post.setLikeCount(post.getLikeCount() + 1);
        postRepository.addLikeToPost(post.getId());
        post.getUsersLiked().add(user);
        postRepository.save(post);

        user.getLikedPosts().add(post);
        userRepository.save(user);
    }

    public ResponseEntity<?> favPost(Long postId) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Optional<Post> optionalPost = postRepository.findById(postId);
            if (optionalPost.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The post was not found.");
            }

            Post post = optionalPost.get();
            boolean alreadyFaved = user.getFavPosts().stream()
                    .anyMatch(favedPost -> favedPost.getId().equals(post.getId()));
            if (alreadyFaved) {
                removeFav(postId, post, user);
                return ResponseEntity.ok("Post removed from favorites.");
            } else {
                user.getFavPosts().add(post);
                userRepository.save(user);
                postRepository.addFavToPost(post.getId());
                return ResponseEntity.ok("Post added to favorites.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating favorite status: " + e.getMessage());
        }
    }

    private void removeFav(Long postId, Post post, User user) {
        user.getFavPosts().remove(post);
        userRepository.deleteFavPostEntry(user.getId(), postId);
        postRepository.removeFavFromPost(post.getId());
        userRepository.save(user);
    }

    //Finds all post faved by the User that provides the token since favs are private
    public List<PostBasicInformation> findFavPostsByUserId() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return postRepository.findFavPostsByUserId(user.getId());
    }

    public ResponseEntity<?> respondPost(CreatePostRequest request, Long id) {
        Optional<Post> postResponded = postRepository.findById(id);
        if (postResponded.isPresent()) {
            try {
                User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                Post parentPost = postResponded.get();
                Post responsePost = new Post();
                responsePost.setContent(request.getContent());
                responsePost.setUser(currentUser);
                responsePost.setParent(parentPost);
                parentPost.getResponses().add(responsePost);
                parentPost.updateResponsesCount();
                postRepository.save(responsePost);

                return ResponseEntity.status(HttpStatus.CREATED).body(responsePost);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating the post: " + e.getMessage());
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The post being responded to does not exist");
    }

    //Method to view details of a post using the ViewPostDetails DTO
    public ViewPostDetails viewPost(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post mainPost = optionalPost.get();
            ViewPostDetails viewPostDetails = new ViewPostDetails();

            // Fill basic main post information
            viewPostDetails.setPost(postRepository.findPostBasicInformationById(mainPost.getId()));

            // Fill responses List
            List<Post> directResponses = postRepository.findDirectResponses(mainPost.getId());
            List<PostBasicInformation> directResponsesInfo = directResponses.stream()
                    .map(post -> postRepository.findPostBasicInformationById(post.getId()))
                    .collect(Collectors.toList());
            viewPostDetails.setResponses(directResponsesInfo);

            // Fill parents List
            List<PostBasicInformation> parentsInfo = new ArrayList<>();
            fillParents(mainPost, parentsInfo);
            viewPostDetails.setParents(parentsInfo);

            return viewPostDetails;
        } else {
            return null;
        }
    }

    private void fillParents(Post post, List<PostBasicInformation> parentsInfo) {
        if (post.getParent() != null) {
            PostBasicInformation parentInfo = postRepository.findPostBasicInformationById(post.getParent().getId());
            parentsInfo.add(parentInfo);
            fillParents(post.getParent(), parentsInfo);
        }
    }
}

