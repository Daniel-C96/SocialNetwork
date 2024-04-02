package com.example.SocialNetwork.dto.post;

import com.example.SocialNetwork.model.Post;
import com.example.SocialNetwork.projection.post.PostBasicInformation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewPostDetails {

    private PostBasicInformation post;
    private List<PostBasicInformation> responses;
    private List<PostBasicInformation> parents;
    
}
