package com.project.BE_banjjokee.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateWalkRecordDTO {
    Long id;
    @NotBlank
    String content;
}
