package com.project.BE_banjjokee.service;

import com.project.BE_banjjokee.dto.PetDTO;
import com.project.BE_banjjokee.dto.ScheduleDTO;
import com.project.BE_banjjokee.model.Pet;
import com.project.BE_banjjokee.model.Schedule;
import com.project.BE_banjjokee.model.User;
import com.project.BE_banjjokee.repository.PetRepository;
import com.project.BE_banjjokee.repository.ScheduleRepository;
import com.project.BE_banjjokee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final PetRepository petRepository;

    @Transactional
    public String createSchedule(List<ScheduleDTO> scheduleDTOs, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("찾는 사용자가 존재하지 않습니다."));
        List<Pet> pets  = petRepository.findAllByUserUuid(user.getUuid());

        Pet pet = pets.stream()
                .filter(p -> p.getIsActivated())
                .findFirst().get();

        scheduleDTOs.stream().forEach(scheduleDTO -> scheduleRepository.save(new Schedule(pet, scheduleDTO)));
        return "스케줄 생성 완료";
    }
}
