package com.example.SocialNetwork.dto;

public class UserCreateDTO {

    private String username;
    private String password;
    private String alias;
    private String email;
    private String description;

    public UserCreateDTO() {
    }

    public UserCreateDTO(String username, String password, String alias, String email, String description) {
        this.username = username;
        this.password = password;
        this.alias = alias;
        this.email = email;
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "UserCreateDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", alias='" + alias + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
