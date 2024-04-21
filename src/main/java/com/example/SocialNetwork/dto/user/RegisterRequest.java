package com.example.SocialNetwork.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @Schema(example = "John_12")
    private String username;
    @Schema(example = "Password123")
    private String password;
    @Schema(example = "John1998")
    private String alias;
    @Schema(example = "john321@gmail.com")
    private String email;
    @Schema(example = "this is a description")
    private String description;

}
