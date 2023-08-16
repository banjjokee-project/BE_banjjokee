package com.project.BE_banjjokee.service;

import com.project.BE_banjjokee.dto.SignUpDto;
import com.project.BE_banjjokee.model.Role;
import com.project.BE_banjjokee.model.User;
import com.project.BE_banjjokee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Boolean checkEmailExist(String email) {
        if(userRepository.findByEmail(email).isPresent()) return false;
        else return true;
    }

    public Boolean checkNicknameExist(String nickname) {
        if(userRepository.findByNickname(nickname).isPresent()) return false;
        else return true;
    }

    public User createUser(SignUpDto signUpDto) {
        User user = User.builder()
                .uuid(UUID.randomUUID())
                .email(signUpDto.getEmail())
                .password(signUpDto.getPassword())
                .nickname(signUpDto.getNickname())
                .role(Role.USER)
                .build();

        user.passwordEncode(passwordEncoder);
        return userRepository.save(user);
    }
}
