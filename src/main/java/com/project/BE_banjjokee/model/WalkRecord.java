package com.project.BE_banjjokee.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
public class WalkRecord extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pet_id")
    private Pet pet;

    private String content;

    private Integer achievement;

    private LocalDate date;

    public WalkRecord(Pet pet, String content, LocalDate date) {
        this.pet = pet;
        this.content = content;
        this.date = date;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAchievement(Integer achievement) {
        this.achievement = achievement;
    }
}
