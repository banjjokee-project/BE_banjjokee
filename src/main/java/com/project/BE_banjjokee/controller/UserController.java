package com.project.BE_banjjokee.controller;

import com.project.BE_banjjokee.dto.SignUpDto;
import com.project.BE_banjjokee.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    @GetMapping("/user/checkEmailExist/{email}")
    ResponseEntity<Boolean> checkEmailExist(@PathVariable String email) {
        return ResponseEntity.ok(userService.checkEmailExist(email));
    }

    @PostMapping("/signup")
    String signup(@RequestBody SignUpDto signUpDto) {
        userService.createUser(signUpDto);
        return "회원가입 완료";
    }

    @GetMapping("/jwt-test")
    String test() {
        return "정상 작동";
    }

}
