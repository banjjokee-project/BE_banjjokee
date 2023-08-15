package com.project.BE_banjjokee.repository;

import com.project.BE_banjjokee.model.WalkRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WalkRecordRepository extends JpaRepository<WalkRecord, Long> {
    List<WalkRecord> findAllByPetId(Long petId);

    @Query("select w from WalkRecord w where w.pet.id = :petId and MONTH(w.date) = :month and w.achievement <> null")
    List<WalkRecord> findAllByPetIdAndDateAndAchievementNotNull(Long petId, int month);
}
