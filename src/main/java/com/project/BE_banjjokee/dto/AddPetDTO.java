package com.project.BE_banjjokee.dto;

import com.project.BE_banjjokee.model.Pet;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddPetDTO {
    @NotBlank
    String name;
    String imgUrl;

    public AddPetDTO(Pet pet) {
        this.name = pet.getName();
        this.imgUrl = pet.getImgUrl();
    }
}
