package com.project.BE_banjjokee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateScheduleDTO {
    private Long id;
    private String type;
    private String content;
}
