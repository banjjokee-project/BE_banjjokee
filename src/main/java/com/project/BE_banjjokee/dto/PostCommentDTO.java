package com.project.BE_banjjokee.dto;

import com.project.BE_banjjokee.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostCommentDTO {

    private Long commentId;

    private Long parentId;

    private String writer;

    private String content;

    public PostCommentDTO(Comment comment) {
        this.commentId = comment.getId();

        if (comment.getParent() != null) {
            this.parentId = comment.getParent().getId();
        } else {
            this.parentId = comment.getId();
        }

        this.writer = comment.getWriter().getNickname();
        this.content = comment.getContent();
    }

}
