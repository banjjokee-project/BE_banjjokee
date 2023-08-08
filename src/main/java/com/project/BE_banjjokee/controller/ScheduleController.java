package com.project.BE_banjjokee.controller;

import com.project.BE_banjjokee.dto.ScheduleAllDTO;
import com.project.BE_banjjokee.dto.ScheduleDTO;
import com.project.BE_banjjokee.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<String> createSchedule(@RequestBody List<ScheduleDTO> scheduleDTOs, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(scheduleService.createSchedule(scheduleDTOs, userDetails.getUsername()));
    }

    @GetMapping
    public ResponseEntity<Map<Integer, ScheduleAllDTO>> getSchedule(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(scheduleService.getSchedule(userDetails.getUsername()));
    }
}
