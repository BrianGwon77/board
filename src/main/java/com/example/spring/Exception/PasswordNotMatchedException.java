package com.example.spring.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST) // 500 -> 400
public class PasswordNotMatchedException extends RuntimeException {
    public PasswordNotMatchedException(String msg) {
        super(msg);
    }
    PasswordNotMatchedException() {
        this("");
    }
}