package com.example.SocialNetwork.controller;

import com.example.SocialNetwork.dto.post.CreatePostRequest;
import com.example.SocialNetwork.projection.post.PostBasicInformation;
import com.example.SocialNetwork.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
@Tag(name = "Post Controller", description = "Functions related to Posts")
public class PostController {
    @Autowired
    PostService postService;

    @Operation(description = "Creates a Post. It takes the User from the JWT provided.",
            summary = "Create Post endpoint")
    @PostMapping("/create")
    public ResponseEntity<CreatePostRequest> createPost(@RequestBody CreatePostRequest postDTO) {
        try {
            postService.createPost(postDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(postDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
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
}
