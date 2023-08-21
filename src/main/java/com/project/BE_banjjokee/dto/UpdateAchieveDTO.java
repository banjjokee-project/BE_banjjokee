package com.project.BE_banjjokee.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateAchieveDTO {
    Long id;
    @NotNull
    Integer achievement;
}
