package com.example.study.service;

import com.example.study.domain.User;
import com.example.study.exception.AppException;
import com.example.study.exception.ErrorCode;
import com.example.study.repository.UserRepository;
import com.example.study.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Value("${jwt.secret}")
    private String secretKey;

    public void join(String userName, String password) {

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
    }

    public String login(String userName, String password) {

        // userName 존재 X
        User selectedUser = userRepository.findByUserName(userName)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, userName + "이 없습니다."));

        // password X
        if (!bCryptPasswordEncoder.matches(password, selectedUser.getPassword())) {
            throw new AppException(ErrorCode.INVALID_PASSWORD, "패스워드를 잘못 입력 하셨습니다.");
        }

        Long expiredMs = 1000 * 60 * 60L;
        return JwtUtil.createJwt(userName, secretKey, expiredMs);
    }
}
