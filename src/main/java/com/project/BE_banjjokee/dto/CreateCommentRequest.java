package com.project.BE_banjjokee.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class CreateCommentRequest {

    @NotEmpty
    private Long postId;

    private Long parentId;

    @NotEmpty
    private String content;

}
