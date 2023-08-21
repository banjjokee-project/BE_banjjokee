package com.project.BE_banjjokee.controller;

import com.project.BE_banjjokee.dto.AddWalkRecordDTO;
import com.project.BE_banjjokee.dto.UpdateAchieveDTO;
import com.project.BE_banjjokee.dto.UpdateWalkRecordDTO;
import com.project.BE_banjjokee.dto.WalkRecordAllDTO;
import com.project.BE_banjjokee.service.WalkRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/walkRecord")
public class WalkRecordController {
    private final WalkRecordService walkRecordService;

    @PostMapping
    public ResponseEntity<String> createWalkRecord(@Valid @RequestBody AddWalkRecordDTO addWalkRecordDTO, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(walkRecordService.createWalkRecord(addWalkRecordDTO, userDetails.getUsername()));
    }

    @GetMapping
    public ResponseEntity<Map<Integer, WalkRecordAllDTO>> getWalkRecord(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(walkRecordService.getWalkRecord(userDetails.getUsername()));
    }

    @PatchMapping
    public ResponseEntity<String> updateWalkRecord(@Valid @RequestBody UpdateWalkRecordDTO updateWalkRecordDTO) {
        return ResponseEntity.ok(walkRecordService.updateWalkRecord(updateWalkRecordDTO));
    }

    @PatchMapping("/achievement")
    public ResponseEntity<String> updateAchieve(@Valid @RequestBody UpdateAchieveDTO updateAchieveDTO) {
        return ResponseEntity.ok(walkRecordService.updateAchieve(updateAchieveDTO));
    }

    @DeleteMapping("/{walkRecordId}")
    public ResponseEntity<String> deleteWalkRecord(@PathVariable Long walkRecordId) {
        return ResponseEntity.ok(walkRecordService.deleteWalkRecord(walkRecordId));
    }
}
