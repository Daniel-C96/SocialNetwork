package com.example.SocialNetwork.repository;

import com.example.SocialNetwork.model.User;
import com.example.SocialNetwork.projection.UserBasicInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    //What is between "find" and "By" gets ignored so "findBy" would work, but it is useful for better naming
    List<UserBasicInformation> findAllUserBasicInformationBy();

    User findByEmail(String email);

    User findByUsernameIgnoreCase(String username);

    @Query("SELECT u FROM User u WHERE LOWER(u.username) = LOWER(:identifier) OR LOWER(u.email) = LOWER(:identifier)")
    User findByUsernameOrEmailIgnoreCase(@Param("identifier") String identifier);
}
