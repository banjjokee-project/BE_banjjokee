package com.project.BE_banjjokee.model;

import com.project.BE_banjjokee.dto.AddScheduleDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@ToString(exclude = {"pet"})
@Getter
public class Schedule extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;

    private String type;

    private String content;

    private LocalDate date;

    public Schedule(Pet pet, AddScheduleDTO addScheduleDTO) {
        this.pet = pet;
        this.type = addScheduleDTO.getType();
        this.content = addScheduleDTO.getContent();
        this.date = addScheduleDTO.getDate();
    }
}
