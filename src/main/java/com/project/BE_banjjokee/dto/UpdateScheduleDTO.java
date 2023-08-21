package com.project.BE_banjjokee.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateScheduleDTO {
    private Long id;
    @NotBlank
    private String type;
    @NotBlank
    private String content;
}
