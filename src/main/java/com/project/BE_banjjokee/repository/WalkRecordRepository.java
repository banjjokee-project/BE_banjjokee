package com.project.BE_banjjokee.repository;

import com.project.BE_banjjokee.model.WalkRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WalkRecordRepository extends JpaRepository<WalkRecord, Long> {
    List<WalkRecord> findAllByPetId(Long id);
}
