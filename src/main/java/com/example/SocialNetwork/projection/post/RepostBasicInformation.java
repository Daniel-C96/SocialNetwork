package com.example.SocialNetwork.projection.post;

import com.example.SocialNetwork.projection.user.UserBasicInformation;

import java.time.LocalDateTime;

public interface RepostBasicInformation {
    Long getId();

    PostBasicInformation getPost(); // Basic Post Projection

    UserBasicInformation getUserRepost(); // Basic UserRepost Projection

    LocalDateTime getRepostedAt();
}
