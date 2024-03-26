package com.example.SocialNetwork.repository;

import com.example.SocialNetwork.model.User;
import com.example.SocialNetwork.projection.post.PostBasicInformation;
import com.example.SocialNetwork.projection.user.UserBasicInformation;
import com.example.SocialNetwork.projection.user.UserProfileInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //What is between "find" and "By" gets ignored so "findBy" would work, but it is useful for better naming
    List<UserBasicInformation> findAllUserBasicInformationBy();

    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameIgnoreCase(String username);

    @Query("SELECT u FROM User u WHERE LOWER(u.username) = LOWER(:identifier) OR LOWER(u.email) = LOWER(:identifier)")
    Optional<User> findByUsernameOrEmailIgnoreCase(@Param("identifier") String identifier);

    Optional<UserProfileInformation> findUserBasicInformationById(Long id);

    @Query("SELECT u FROM User u JOIN u.likedPosts p WHERE p.id = :postId")
    List<UserBasicInformation> findUsersLikedByPostId(@Param("postId") Long postId);
}
