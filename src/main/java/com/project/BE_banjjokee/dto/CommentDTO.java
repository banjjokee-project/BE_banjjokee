package com.project.BE_banjjokee.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.BE_banjjokee.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CommentDTO {

    private Long commentId;

    private String writer;

    private String content;

    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private List<CommentDTO> children;

    public CommentDTO(Comment comment) {
        this.commentId = comment.getId();
        this.writer = comment.getWriter().getNickname();
        this.content = comment.getContent();
    }

    public void changeChildren(List<CommentDTO> children) {
        this.children = children;
    }

}
