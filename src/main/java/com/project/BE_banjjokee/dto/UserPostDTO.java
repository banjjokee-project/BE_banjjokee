package com.project.BE_banjjokee.dto;

import com.project.BE_banjjokee.model.Post;
import lombok.Data;

@Data
public class UserPostDTO {

    private Long postId;

    private String content;

    public UserPostDTO(Post post) {
        this.postId = post.getId();
        this.content = post.getContent();
    }

}
