package com.example.study.controller;

import com.example.study.domain.dto.UserJoinRequest;
import com.example.study.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody UserJoinRequest userJoinRequest) {

        userService.join(userJoinRequest.getUserName(), userJoinRequest.getPassword());
        return ResponseEntity.ok().body("회원가입이 성공 했습니다.");

    }
}
