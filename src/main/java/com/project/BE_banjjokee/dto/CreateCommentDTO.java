package com.project.BE_banjjokee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
public class CreateCommentDTO {

    private Long postId;

    private Set<UUID> uuids;

}
