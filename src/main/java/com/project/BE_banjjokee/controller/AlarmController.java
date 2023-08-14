package com.project.BE_banjjokee.controller;

import com.project.BE_banjjokee.alarm.Alarms;
import com.project.BE_banjjokee.model.User;
import com.project.BE_banjjokee.repository.UserRepository;
import com.project.BE_banjjokee.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class AlarmController {

    private final Alarms alarms;

    private final UserRepository userRepository;

    @GetMapping("/api/v1/alarm")
    public ResponseEntity<SseEmitter> connect(@AuthenticationPrincipal UserDetails userDetails) throws RuntimeException{
        SseEmitter sseEmitter = new SseEmitter();
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("잘못된 접근"));
        alarms.add(user.getUuid(), sseEmitter);

        try {
            sseEmitter.send(SseEmitter.event()
                    .name("connect")
                    .data("connected!"));
        } catch (IOException e) {
            throw new RuntimeException("연결 실패");
        }

        return ResponseEntity.ok(sseEmitter);
    }

}
