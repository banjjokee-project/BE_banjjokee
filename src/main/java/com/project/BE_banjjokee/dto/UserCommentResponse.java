package com.project.BE_banjjokee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;

@Data
@AllArgsConstructor
public class UserCommentResponse {

    private List<UserCommentDTO> comments;

}
