package com.project.BE_banjjokee.model;

import jakarta.persistence.*;
import lombok.ToString;

@Entity
@DiscriminatorValue("PET")
public class PetImage extends Image{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;

    public static PetImage createPetImage(String key, Pet pet) {
        PetImage image = new PetImage();

        image.setKey(key);
        image.setUrl(pet.getImgUrl());
        image.setPet(pet);

        return image;
    }

    private void setPet(Pet pet) {
        this.pet = pet;
    }


}
