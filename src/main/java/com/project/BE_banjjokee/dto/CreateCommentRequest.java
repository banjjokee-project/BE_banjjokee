package com.project.BE_banjjokee.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateCommentRequest {

    @NotNull
    private Long postId;

    private Long parentId;

    @NotEmpty
    private String content;

}
