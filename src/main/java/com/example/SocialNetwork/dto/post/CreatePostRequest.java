package com.example.SocialNetwork.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;


@Data
public class CreatePostRequest {

    @Schema(example = "This is a post 123")
    private String content;
}
