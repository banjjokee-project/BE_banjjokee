package com.project.BE_banjjokee.service;

import com.project.BE_banjjokee.dto.ScheduleAllDTO;
import com.project.BE_banjjokee.dto.AddScheduleDTO;
import com.project.BE_banjjokee.dto.UpdateScheduleDTO;
import com.project.BE_banjjokee.model.Pet;
import com.project.BE_banjjokee.model.Schedule;
import com.project.BE_banjjokee.model.User;
import com.project.BE_banjjokee.repository.PetRepository;
import com.project.BE_banjjokee.repository.ScheduleRepository;
import com.project.BE_banjjokee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    private final PetRepository petRepository;

    @Transactional
    public String createSchedule(List<AddScheduleDTO> addScheduleDTOS, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("찾는 사용자가 존재하지 않습니다."));
        List<Pet> pets  = petRepository.findAllByUserUuid(user.getUuid());

        Pet pet = pets.stream()
                .filter(p -> p.getIsActivated())
                .findFirst().get();

        addScheduleDTOS.stream().forEach(addScheduleDTO -> scheduleRepository.save(new Schedule(pet, addScheduleDTO)));
        return "스케줄 생성 완료";
    }

    public Map<Integer, ScheduleAllDTO> getSchedule(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("찾는 사용자가 존재하지 않습니다."));
        Pet pet = petRepository.findByUserUuidAndIsActivated(user.getUuid(), true);
        if(pet == null) return null;

        List<Schedule> schedules = scheduleRepository.findAllByPetId(pet.getId());

        int currentYear = LocalDate.now().getYear();
        int year = currentYear;
        if (schedules.size() > 0) {
            Collections.sort(schedules, new Comparator<Schedule>() {
                @Override
                public int compare(Schedule o1, Schedule o2) {
                    return o1.getDate().compareTo(o2.getDate());
                }
            });
            year = schedules.get(0).getDate().getYear();
        }

        Map<Integer, ScheduleAllDTO> yearMap = new HashMap<>();
        while(year <= currentYear + 1) {
            int nowYear = year;
            List<Schedule> collect = schedules.stream()
                    .filter(schedule -> schedule.getDate().getYear() == nowYear).collect(Collectors.toList());
            yearMap.put(nowYear, new ScheduleAllDTO(collect));
            year += 1;
        }

        return yearMap;
    }

    @Transactional
    public String updateSchedule(List<UpdateScheduleDTO> updateScheduleDTOS) {
        List<Long> ids = updateScheduleDTOS.stream()
                .map(updateScheduleDTO -> updateScheduleDTO.getId()).collect(Collectors.toList());
        List<Schedule> schedules = scheduleRepository.findByIdIn(ids);

        Collections.sort(updateScheduleDTOS, new Comparator<UpdateScheduleDTO>() {
            @Override
            public int compare(UpdateScheduleDTO o1, UpdateScheduleDTO o2) {
                return (int)(o1.getId() - o2.getId());
            }
        });

        for (int i = 0; i < updateScheduleDTOS.size(); i++) {
            UpdateScheduleDTO updateScheduleDTO = updateScheduleDTOS.get(i);
            schedules.get(i).change(updateScheduleDTO.getType(), updateScheduleDTO.getContent());
        }

        return "스케줄 수정 완료";
    }

    @Transactional
    public String deleteSchedule(Long scheduleId) {
        Schedule deleteSchedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new RuntimeException("찾는 스캐줄이 존재하지 않습니다."));
        scheduleRepository.delete(deleteSchedule);

        return "스케줄 삭제 완료";
    }

    @Transactional
    public String deleteSchedule(String date) {
        LocalDate localDate = LocalDate.parse(date);
        List<Schedule> deleteSchedules = scheduleRepository.findAllByDate(localDate);
        deleteSchedules.stream()
                .forEach(deleteSchedule -> scheduleRepository.delete(deleteSchedule));

        return "스케줄 삭제 완료";
    }

}
