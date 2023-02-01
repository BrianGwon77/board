package com.example.spring.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice("com.example.spring")
public class GlobalCatcher {
    @ExceptionHandler({BadRequestException.class, PasswordNotMatchedException.class})
    public String badRequestExceptionCatcher(Exception ex, Model m) {
        m.addAttribute("ex", ex);
        return "error";
    }
}