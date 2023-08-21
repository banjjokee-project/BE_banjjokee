package com.project.BE_banjjokee.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AddWalkRecordDTO {
    @NotBlank
    String content;
    @NotNull
    LocalDate date;
}
