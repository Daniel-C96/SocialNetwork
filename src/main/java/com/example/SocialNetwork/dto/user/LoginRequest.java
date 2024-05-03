package com.example.SocialNetwork.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotNull(message = "Username or Email is null.")
    @NotEmpty(message = "Username or Email is empty.")
    @Schema(example = "Daniel")
    private String identifier;

    @NotNull(message = "Password is null.")
    @NotEmpty(message = "Password is empty.")
    @Schema(example = "Admin1")
    private String password;

}
