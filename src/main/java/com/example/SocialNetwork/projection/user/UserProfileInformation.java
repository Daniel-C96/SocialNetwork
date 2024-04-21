package com.example.SocialNetwork.projection.user;

import com.example.SocialNetwork.config.Constants;

public interface UserProfileInformation {

    Long getId();

    String getUsername();

    String getAlias();

    String getProfilePicture();

    default String getProfilePictureUrl() {
        return Constants.S3_URL + getProfilePicture();
    }

    String getDescription();

    int getFollowerCount();

    int getFollowingCount();
}
