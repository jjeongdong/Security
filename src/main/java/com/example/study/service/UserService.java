package com.example.study.service;

import com.example.study.domain.User;
import com.example.study.exception.AppException;
import com.example.study.exception.ErrorCode;
import com.example.study.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public String join(String userName, String password) {

        // userName 중복 확인
        userRepository.findByUserName(userName)
                .ifPresent(user -> {
                    throw new AppException(ErrorCode.USERNAME_DUPLICATED, userName + "은 이미 있습니다.");
                });

        // 저장
        User user = User.builder()
                .userName(userName)
                .password(bCryptPasswordEncoder.encode(password))
                .build();

        userRepository.save(user);
        return "SUCCESS";
    }
}
