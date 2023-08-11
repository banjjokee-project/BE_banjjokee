package com.project.BE_banjjokee.service;

import com.project.BE_banjjokee.dto.AddWalkRecordDTO;
import com.project.BE_banjjokee.dto.UpdateAchieveDTO;
import com.project.BE_banjjokee.dto.UpdateWalkRecordDTO;
import com.project.BE_banjjokee.dto.WalkRecordAllDTO;
import com.project.BE_banjjokee.model.Pet;
import com.project.BE_banjjokee.model.User;
import com.project.BE_banjjokee.model.WalkRecord;
import com.project.BE_banjjokee.repository.PetRepository;
import com.project.BE_banjjokee.repository.UserRepository;
import com.project.BE_banjjokee.repository.WalkRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

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
                .orElseThrow(() -> new RuntimeException("찾는 사용자가 존재하지 않습니다."));
        Pet pet = petRepository.findByUserUuidAndIsActivated(user.getUuid(), true);
        walkRecordRepository.save(new WalkRecord(pet, addWalkRecordDTO.getContent(), addWalkRecordDTO.getDate()));

        return "산책 기록 생성 완료";
    }

    public Map<Integer, WalkRecordAllDTO> getWalkRecord(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("찾는 사용자가 존재하지 않습니다."));
        Pet pet = petRepository.findByUserUuidAndIsActivated(user.getUuid(), true);
        if(pet == null) return null;

        List<WalkRecord> walkRecords = walkRecordRepository.findAllByPetId(pet.getId());

        int currentYear = LocalDate.now().getYear();
        int year = currentYear;
        if (walkRecords.size() > 0) {
            Collections.sort(walkRecords, new Comparator<WalkRecord>() {
                @Override
                public int compare(WalkRecord o1, WalkRecord o2) {
                    return o1.getDate().compareTo(o2.getDate());
                }
            });
            year = walkRecords.get(0).getDate().getYear();
        }

        Map<Integer, WalkRecordAllDTO> yearMap = new HashMap<>();
        while(year <= currentYear + 1) {
            int nowYear = year;
            List<WalkRecord> collect = walkRecords.stream()
                    .filter(walkRecord -> walkRecord.getDate().getYear() == nowYear).collect(Collectors.toList());
            yearMap.put(nowYear, new WalkRecordAllDTO(collect));
            year += 1;
        }

        return yearMap;
    }

    @Transactional
    public String updateWalkRecord(UpdateWalkRecordDTO updateWalkRecordDTO) {
        WalkRecord walkRecord = walkRecordRepository.findById(updateWalkRecordDTO.getId())
                .orElseThrow(() -> new RuntimeException("찾는 산책 기록이 존재하지 않습니다."));
        walkRecord.setContent(updateWalkRecordDTO.getContent());

        return "산책 기록 수정 완료";
    }

    @Transactional
    public String updateAchieve(UpdateAchieveDTO updateAchieveDTO) {
        WalkRecord walkRecord = walkRecordRepository.findById(updateAchieveDTO.getId())
                .orElseThrow(() -> new RuntimeException("찾는 산책 기록이 존재하지 않습니다."));
        walkRecord.setAchievement(updateAchieveDTO.getAchievement());

        return "성취도 수정 완료";
    }
}

