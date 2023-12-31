package com.project.BE_banjjokee.controller;

import com.project.BE_banjjokee.dto.ScheduleAllDTO;
import com.project.BE_banjjokee.dto.AddScheduleDTO;
import com.project.BE_banjjokee.dto.UpdateScheduleDTO;
import com.project.BE_banjjokee.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<String> createSchedule(@Valid @RequestBody List<AddScheduleDTO> addScheduleDTOS, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(scheduleService.createSchedule(addScheduleDTOS, userDetails.getUsername()));
    }

    @GetMapping
    public ResponseEntity<Map<Integer, ScheduleAllDTO>> getSchedule(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(scheduleService.getSchedule(userDetails.getUsername()));
    }

    @PatchMapping
    public ResponseEntity<String> updateSchedule(@Valid @RequestBody List<UpdateScheduleDTO> updateScheduleDTOS) {
        return ResponseEntity.ok(scheduleService.updateSchedule(updateScheduleDTOS));
    }

    @DeleteMapping("/{scheduleId}")
    public ResponseEntity deleteSchedule(@PathVariable Long scheduleId) {
        return ResponseEntity.ok(scheduleService.deleteSchedule(scheduleId));
    }

    @DeleteMapping
    public ResponseEntity deleteSchedule(@RequestParam String date) {
        return ResponseEntity.ok(scheduleService.deleteSchedule(date));
    }
}
