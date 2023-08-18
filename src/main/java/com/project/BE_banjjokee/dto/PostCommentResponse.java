package com.project.BE_banjjokee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class PostCommentResponse {

    private Long postId;

    private List<CommentDTO> comments;

}
