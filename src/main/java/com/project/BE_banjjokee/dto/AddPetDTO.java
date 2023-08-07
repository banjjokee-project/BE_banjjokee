package com.project.BE_banjjokee.dto;

import com.project.BE_banjjokee.model.Pet;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddPetDTO {
    String name;
    String imgUrl;

    public AddPetDTO(Pet pet) {
        this.name = pet.getName();
        this.imgUrl = pet.getImgUrl();
    }
}
