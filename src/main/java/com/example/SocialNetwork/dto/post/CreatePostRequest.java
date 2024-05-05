package com.example.SocialNetwork.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostRequest {

    @NotNull(message = "The content of the post is null.")
    @NotEmpty(message = "The content of the post is empty.")
    @Pattern(regexp = "^(?=.*\\S).+$", message = "The content of the post is only spaces.")
    @Size(max = 100)
    @Schema(example = "This is a post 123")
    private String content;
}
