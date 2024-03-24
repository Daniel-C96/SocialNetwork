package com.example.SocialNetwork.projection;

import com.example.SocialNetwork.model.Post;

import java.util.ArrayList;
import java.util.List;

public interface UserBasicInformation {
    Long getId();

    String getUsername();

    String getAlias();

    String getProfilePicture();

}
