package com.example.SocialNetwork.projection.user;

import com.example.SocialNetwork.config.Constants;
import com.example.SocialNetwork.model.Post;

import java.util.ArrayList;
import java.util.List;

public interface UserBasicInformation {
    Long getId();

    String getUsername();

    String getAlias();

    String getProfilePicture();

    default String getProfilePictureUrl() {
        return Constants.S3_URL + getProfilePicture();
    }
}
