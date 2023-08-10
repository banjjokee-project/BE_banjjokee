package com.project.BE_banjjokee.repository;

import com.project.BE_banjjokee.model.Pet;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PetRepository extends JpaRepository<Pet, Long> {
    @Override
    @EntityGraph(attributePaths = {"user"})
    Optional<Pet> findById(Long id);
    List<Pet> findAllByUserUuid(UUID uuid);
    Pet findByUserUuidAndIsActivated(UUID uuid, Boolean isActivated);
}
