package com.example.SocialNetwork.projection.post;

import com.example.SocialNetwork.projection.user.UserBasicInformation;

import java.time.LocalDateTime;
import java.util.Date;

public interface PostBasicInformation {
    Long getId();

    String getContent();

    UserBasicInformation getUser(); // Basic User Projection

    int getLikeCount();

    int getResponsesCount();

    int getFavCount();

    Date getCreatedAt();
}
