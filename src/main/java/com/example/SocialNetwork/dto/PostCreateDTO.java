package com.example.SocialNetwork.dto;

public class PostCreateDTO {

    private String content;
    private Long userId;

    public PostCreateDTO() {
    }

    public PostCreateDTO(String content, Long userId) {
        this.content = content;
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "CreatePostDTO{" +
                "content='" + content + '\'' +
                ", userId=" + userId +
                '}';
    }
}
