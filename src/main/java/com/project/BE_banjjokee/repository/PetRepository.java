package com.project.BE_banjjokee.repository;

import com.project.BE_banjjokee.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findAllByUserUuid(UUID uuid);
}
