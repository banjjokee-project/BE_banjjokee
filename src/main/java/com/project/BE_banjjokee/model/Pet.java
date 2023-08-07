package com.project.BE_banjjokee.model;

import com.project.BE_banjjokee.dto.AddPetDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@ToString(exclude = {"user"})
@Getter
public class Pet extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uuid", referencedColumnName = "uuid")
    private User user;

    private String name;

    private String imgUrl;

    private Boolean isActivated;

    public Pet(User user, AddPetDTO addPetDTO) {
        this.user = user;
        this.name = addPetDTO.getName();
        this.imgUrl = addPetDTO.getImgUrl();
        this.isActivated = false;
    }

    public void changeActivate() {
        this.isActivated = !this.getIsActivated();
    }


    public void change(AddPetDTO addPetDTO) {
        this.name = addPetDTO.getName();
        this.imgUrl = addPetDTO.getImgUrl();
    }
}
