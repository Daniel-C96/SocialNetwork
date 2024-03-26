package com.example.SocialNetwork.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    @Column(name = "like_count", nullable = false, columnDefinition = "int default 0")
    private int likeCount = 0;

    //mappedBy makes usersLiked a child of likedPosts, so to edit usersLiked you have to edit likedPosts in User
    @ManyToMany(mappedBy = "likedPosts")
    @JsonIgnore
    private List<User> usersLiked = new ArrayList<>();

}
