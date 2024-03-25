package com.example.SocialNetwork.projection.post;

import com.example.SocialNetwork.projection.user.UserBasicInformation;

public interface PostBasicInformation {
    Long getId();

    String getContent();

    UserBasicInformation getUser(); // Basic User Projection

    int getLikes();

}
