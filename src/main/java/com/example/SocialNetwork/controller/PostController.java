package com.example.SocialNetwork.controller;

import com.example.SocialNetwork.dto.post.CreatePostRequest;
import com.example.SocialNetwork.dto.post.ViewPostDetails;
import com.example.SocialNetwork.projection.post.PostBasicInformation;
import com.example.SocialNetwork.projection.user.UserBasicInformation;
import com.example.SocialNetwork.service.PostService;
import com.example.SocialNetwork.service.s3.S3StorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
@Tag(name = "Post Controller", description = "Functions related to Posts")
public class PostController {
    @Autowired
    PostService postService;

    @Autowired
    private S3StorageService s3StorageService;

    @Operation(description = "Creates a Post. It takes the User from the JWT provided.",
            summary = "Create Post endpoint")
    @PostMapping("/create")
    public ResponseEntity<?> createPost(@RequestBody CreatePostRequest postDTO) {
        return postService.createPost(postDTO);
    }

    @Operation(summary = "Retrieves all Posts ")
    @GetMapping("/posts")
    public List<PostBasicInformation> retrieveAllPosts() {
        return postService.retrieveAllPosts();
    }

    @Operation(description = "Retrieves all the Posts from the userId provided on the path.",
            summary = "Retrieves all posts from userId")
    @GetMapping("/posts/user/{userId}")
    public List<PostBasicInformation> getAllPostsByUserId(@PathVariable long userId) {
        return postService.findPostsByUserId(userId);
    }

    @Operation(description = "Retrieves all the Posts liked by the user with the userId provided on the path.",
            summary = "Retrieves all liked posts from userId")
    @GetMapping("/posts/user/{userId}/likes")
    public List<PostBasicInformation> findLikedPostsByUserId(@PathVariable long userId) {
        return postService.findLikedPostsByUserId(userId);
    }

    @Operation(description = "Retrieves all the the Users that liked the post with the postId provided on the path.",
            summary = "Retrieves all Users that liked the postId")
    @GetMapping("/posts/{postId}/user_likes")
    public List<UserBasicInformation> findUsersLikedByPostId(@PathVariable long postId) {
        return postService.findUsersLikedByPostId(postId);
    }

    @Operation(description = "Likes the post on the path with the User that provided the JWT.",
            summary = "Likes a Post with postId")
    @PutMapping("/posts/{postId}/like")
    public ResponseEntity<?> likePost(@PathVariable long postId) {
        return postService.likePost(postId);
    }

    @Operation(description = "Adds a post to favorites to the User that provided the JWT.",
            summary = "Adds a Post to favorites")
    @PutMapping("/posts/{postId}/fav")
    public ResponseEntity<?> favPost(@PathVariable long postId) {
        return postService.favPost(postId);
    }

    @Operation(description = "Retrieves the posts in favorites of User that provided the JWT since they are private.",
            summary = "Lists posts in favorites")
    @GetMapping("/posts/user/favs")
    public List<PostBasicInformation> findFavPostsByUserId() {
        return postService.findFavPostsByUserId();
    }

    @Operation(description = "Respond a post on the Path.",
            summary = "Respond a post on the Path")
    @PostMapping("/posts/{postId}/respond")
    public ResponseEntity<?> respondPost(@PathVariable long postId, @RequestBody CreatePostRequest request) {
        return postService.respondPost(request, postId);
    }

    @Operation(description = "View full post info on the postId in the Path.",
            summary = "View full post info")
    @GetMapping("/posts/{postId}")
    public ViewPostDetails viewPost(@PathVariable long postId) {
        return postService.viewPost(postId);
    }

}



