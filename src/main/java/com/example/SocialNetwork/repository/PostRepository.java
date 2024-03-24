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
    //What there is between "find" and "By" gets ignored so "findBy" would work, but it is useful for better naming
    List<PostBasicInformation> findAllPostBasicInformationBy();

    List<Post> findAllPostsByUser(User user);
}
