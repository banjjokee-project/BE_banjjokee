package com.project.BE_banjjokee.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AddScheduleDTO {
    private String type;
    private String content;
    private LocalDate date;
}
