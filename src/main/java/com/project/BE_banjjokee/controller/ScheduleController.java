package com.project.BE_banjjokee.controller;

import com.project.BE_banjjokee.dto.ScheduleDTO;
import com.project.BE_banjjokee.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<String> createSchedule(@RequestBody List<ScheduleDTO> scheduleDTOs, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(scheduleService.createSchedule(scheduleDTOs, userDetails.getUsername()));
    }

}
