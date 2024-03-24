package com.example.SocialNetwork.dto;

import lombok.Data;


@Data
public class PostCreateDTO {
    private String content;
    private Long userId;

}
