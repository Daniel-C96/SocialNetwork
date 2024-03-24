package com.example.SocialNetwork.projection;

public interface PostBasicInformation {
    Long getId();

    String getContent();

    UserBasicInformation getUser(); // Basic User Projection with less fields

    int getLikes();

}
