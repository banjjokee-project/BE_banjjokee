package com.project.BE_banjjokee.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class PostCommentResponse {

    @NotNull
    private Long postId;

    private List<PostCommentDTO> comments;

}
