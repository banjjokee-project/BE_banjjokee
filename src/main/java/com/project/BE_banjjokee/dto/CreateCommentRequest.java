package com.project.BE_banjjokee.dto;

import lombok.Data;

@Data
public class CreateCommentRequest {

    private Long postId;

    private Long parentId;

    private String content;

}
