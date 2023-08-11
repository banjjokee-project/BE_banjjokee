package com.project.BE_banjjokee.dto;

import com.project.BE_banjjokee.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserCommentDTO {

    private Long commentId;

    private Long postId;

    private String content;

    public UserCommentDTO(Comment comment) {
        this.commentId = comment.getId();
        this.postId = comment.getPost().getId();
        this.content = comment.getContent();
    }

}
