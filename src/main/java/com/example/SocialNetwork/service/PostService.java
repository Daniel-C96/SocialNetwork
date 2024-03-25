package com.example.SocialNetwork.service;

import com.example.SocialNetwork.dto.post.PostCreateDTO;
import com.example.SocialNetwork.model.Post;
import com.example.SocialNetwork.model.User;
import com.example.SocialNetwork.projection.PostBasicInformation;
import com.example.SocialNetwork.repository.PostRepository;
import com.example.SocialNetwork.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Post createPost(PostCreateDTO request) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Post post = new Post();
        post.setContent(request.getContent());
        post.setUser(currentUser);
        return postRepository.save(post);
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

/*    public List<PostBasicInformationDTO> findPostsByUserId(long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Post> posts = postRepository.findAllPostsByUser(user);
            return createPostBasicInformationDTOList(posts).reversed(); //Reverse to get latest posts first
        } else {
            return null;
        }
    }*/

    //Method to convert the list of Posts to a PostBasicInformationDTO with the only needed fields
/*    private PostBasicInformationDTO createPostBasicInformationDTO(Post post) {
        UserPostInfoDTO userDTO = new UserPostInfoDTO(
                post.getUser().getId(),
                post.getUser().getUsername(),
                post.getUser().getAlias(),
                post.getUser().getProfilePicture()
        );

        return new PostBasicInformationDTO(
                post.getId(),
                post.getContent(),
                post.getLikes(),
                userDTO
        );
    }

    private List<PostBasicInformationDTO> createPostBasicInformationDTOList(List<Post> posts) {
        return posts.stream()
                .map(this::createPostBasicInformationDTO)
                .collect(Collectors.toList());
    }*/

}
