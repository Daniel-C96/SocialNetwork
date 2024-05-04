package com.example.SocialNetwork.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.transform.Source;
import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotNull(message = "Username is null.")
    @NotEmpty(message = "Username is empty.")
    // No spaces and is maximum 20 characters length and no @
    @Pattern(regexp = "^(?!.*[@\\s]).{1,20}$", message = "Username is not valid.")
    @Schema(example = "John_12")
    private String username;

    @NotNull(message = "Password is null.")
    @NotEmpty(message = "Password is empty.")
    // 1 upper case, 1 lower case, 1 number, minimum 5 length, maximum 40, and no spaces
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?!.*\\s).{5,40}$", message = "Password is not valid.")
    @Schema(example = "Password123")
    private String password;

    @NotNull(message = "Alias is null.")
    @Pattern(regexp = "^(?!\\s*$)(.{1,20})$", message = "Alias is empty or not a valid alias.")
    @Schema(example = "John1998")
    private String alias;

    @NotNull(message = "Email is null.")
    @NotEmpty(message = "Email is empty.")
    @Email(message = "Email is not valid.")
    @Schema(example = "john321@gmail.com")
    private String email;

    @Schema(example = "this is a description")
    @Size(max = 50, message = "Description too long.")
    private String description;

    @Schema(example = "file")
    private Optional<MultipartFile> file;

}
