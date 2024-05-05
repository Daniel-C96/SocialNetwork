package com.example.SocialNetwork.service;

import com.example.SocialNetwork.dto.post.CreatePostRequest;
import com.example.SocialNetwork.model.Post;
import com.example.SocialNetwork.model.User;
import com.example.SocialNetwork.repository.PostRepository;
import com.example.SocialNetwork.repository.UserRepository;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PostServiceTest {

    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    private Validator validator;

    private User user;

    private Post validPost;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        user = new User();
        user.setUsername("username123");

        // Mock the Authentication object representing the test user
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(user);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        validPost = new Post();
        validPost.setContent("test123");
        validPost.setId(1L);
        validPost.setUser(user);
    }

    @Test
    void createPost() {
        var invalidRequests = invalidPostRequests();
        for (CreatePostRequest request : invalidRequests) {
            var violations = validator.validate(request);
            assertFalse(violations.isEmpty());
        }

        var validRequest = new CreatePostRequest("this is a valid post");
        var response = postService.createPost(validRequest);

        assertEquals(201, response.getStatusCode().value());
        verify(postRepository, times(1)).save(any());
    }

    List<CreatePostRequest> invalidPostRequests() {
        List<CreatePostRequest> invalidRequests = new ArrayList<>();
        //Empty content request
        invalidRequests.add(new CreatePostRequest(""));
        //Too long request
        invalidRequests.add(new CreatePostRequest("asdaaaaaaasdaaaaaaaaaasdaaaaaaaaaasdaaaaa" +
                "asdaaaaaaaaaaaaaasdaaaaaaaaaasdaaaaaaaaaaasdaaaaaaaaaasdaaaaaaaaaasdaaaaaaaaaasdaaaaaaa" +
                "asdaaaaaaaaaasdaaaaaaaaaasdaaaaaaaaaasdaaaaaaaaaasdaaaaaaaaaasdaaaaaaaaaaaasdaaaaaaaaaaa"));
        //Only spaces request
        invalidRequests.add(new CreatePostRequest("       "));

        return invalidRequests;
    }

    @Test
    void likePost() {
        when(postRepository.findById(any())).thenReturn(Optional.of(validPost));
        doNothing().when(postRepository).addLikeToPost(any());
        when(postRepository.save(any())).thenReturn(validPost);

        var response = postService.likePost(validPost.getId());

        assertEquals(200, response.getStatusCode().value());
        verify(postRepository, times(1)).addLikeToPost(any());
        verify(postRepository, times(1)).save(any());
        verify(userRepository, times(1)).save(any());
    }
}
