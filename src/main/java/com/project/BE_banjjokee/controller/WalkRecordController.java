package com.project.BE_banjjokee.controller;

import com.project.BE_banjjokee.dto.AddWalkRecordDTO;
import com.project.BE_banjjokee.service.WalkRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/walkRecord")
public class WalkRecordController {
    private final WalkRecordService walkRecordService;

    @PostMapping
    public ResponseEntity<String> createWalkRecord(@RequestBody AddWalkRecordDTO addWalkRecordDTO, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(walkRecordService.createWalkRecord(addWalkRecordDTO, userDetails.getUsername()));
    }
}
