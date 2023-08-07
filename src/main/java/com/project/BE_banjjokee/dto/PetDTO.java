package com.project.BE_banjjokee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PetDTO {
    Long petId;
    String name;
    String imgUrl;
    int goalQuantity;
}
