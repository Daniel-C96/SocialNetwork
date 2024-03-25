package com.example.SocialNetwork.controller;

import com.example.SocialNetwork.dto.post.PostCreateDTO;
import com.example.SocialNetwork.projection.PostBasicInformation;
import com.example.SocialNetwork.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {
    @Autowired
    PostService postService;

    @PostMapping("/create")
    public ResponseEntity<PostCreateDTO> createPost(@RequestBody PostCreateDTO postDTO) {
        try {
            postService.createPost(postDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(postDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/posts")
    public List<PostBasicInformation> retrieveAllPosts() {
        return postService.retrieveAllPosts();
    }

    @GetMapping("/posts/{userId}")
    public List<PostBasicInformation> getAllPostsByUserId(@PathVariable long userId) {
        return postService.findPostsByUserId(userId);
    }
}
