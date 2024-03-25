package com.example.SocialNetwork.repository;

import com.example.SocialNetwork.model.Post;
import com.example.SocialNetwork.model.User;
import com.example.SocialNetwork.projection.PostBasicInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllPostsByUser(User user);

    List<PostBasicInformation> findAllBy();

    List<PostBasicInformation> findByUser_Id(Long userId);

}
