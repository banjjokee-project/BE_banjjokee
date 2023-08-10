package com.project.BE_banjjokee.service;

import com.project.BE_banjjokee.dto.AddWalkRecordDTO;
import com.project.BE_banjjokee.model.Pet;
import com.project.BE_banjjokee.model.User;
import com.project.BE_banjjokee.model.WalkRecord;
import com.project.BE_banjjokee.repository.PetRepository;
import com.project.BE_banjjokee.repository.UserRepository;
import com.project.BE_banjjokee.repository.WalkRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WalkRecordService {
    private final WalkRecordRepository walkRecordRepository;
    private final UserRepository userRepository;
    private final PetRepository petRepository;

    @Transactional
    public String createWalkRecord(AddWalkRecordDTO addWalkRecordDTO, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("찾으시는 사용자가 존재하지 않습니다."));
        Pet pet = petRepository.findByUserUuidAndIsActivated(user.getUuid(), true);
        walkRecordRepository.save(new WalkRecord(pet, addWalkRecordDTO.getContent(), addWalkRecordDTO.getDate()));

        return "산책 기록 생성 완료";
    }
}
