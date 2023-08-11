package com.project.BE_banjjokee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCommentRequest {

    private Long commentId;

    private String content;

}
