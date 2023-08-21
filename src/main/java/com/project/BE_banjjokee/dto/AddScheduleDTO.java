package com.project.BE_banjjokee.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AddScheduleDTO {
    @NotBlank
    private String type;
    @NotBlank
    private String content;
    @NotNull
    private LocalDate date;
}
