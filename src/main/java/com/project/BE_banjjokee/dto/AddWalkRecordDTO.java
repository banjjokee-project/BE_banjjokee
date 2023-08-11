package com.project.BE_banjjokee.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AddWalkRecordDTO {
    String content;
    LocalDate date;
}
