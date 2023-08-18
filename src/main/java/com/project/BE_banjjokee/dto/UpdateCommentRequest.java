package com.project.BE_banjjokee.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCommentRequest {

    @NotEmpty
    private Long commentId;

    @NotEmpty
    private String content;

}
