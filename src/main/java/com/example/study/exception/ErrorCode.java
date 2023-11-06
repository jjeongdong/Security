package com.example.study.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    USERNAME_DUPLICATED(HttpStatus.CONFLICT, "");

    private final HttpStatus httpStatus;
    private final String message;
}
