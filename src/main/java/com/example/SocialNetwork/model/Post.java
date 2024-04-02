package com.example.SocialNetwork.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
    private int likeCount = 0; //Count instead of counting usersLiked in case the app needs to scale a lot

    //mappedBy makes usersLiked a child of likedPosts, so to edit usersLiked you have to edit likedPosts in User
    @ManyToMany(mappedBy = "likedPosts")
    @JsonIgnore
    private List<User> usersLiked = new ArrayList<>();

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    @JsonIgnore
    private Post parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> responses = new ArrayList<>();

    @JsonIgnore
    @Transient // This indicates JPA that this field is not in the db
    public int getResponsesCount() {
        return responses.size();
    }

}
