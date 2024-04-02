package com.example.SocialNetwork.repository;

import com.example.SocialNetwork.model.Post;
import com.example.SocialNetwork.model.User;
import com.example.SocialNetwork.projection.post.PostBasicInformation;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllPostsByUser(User user);

    List<PostBasicInformation> findAllBy();

    List<PostBasicInformation> findByUser_Id(Long userId);

    PostBasicInformation findPostBasicInformationById(Long userId);

    @Query("SELECT p FROM Post p JOIN p.usersLiked u WHERE u.id = :userId")
    List<PostBasicInformation> findLikedPostsByUserId(@Param("userId") Long userId);

    @Transactional
    @Modifying
    @Query("UPDATE Post p SET p.likeCount = p.likeCount - 1 WHERE p.id = :postId")
    void removeLikeFromPost(@Param("postId") Long postId);

    @Query("SELECT p FROM User u JOIN u.favPosts p WHERE u.id = :userId")
    List<PostBasicInformation> findFavPostsByUserId(@Param("userId") Long userId);


    @Query("SELECT p FROM Post p WHERE p.parent.id = :parentId")
    List<Post> findDirectResponses(@Param("parentId") Long parentId);

}
