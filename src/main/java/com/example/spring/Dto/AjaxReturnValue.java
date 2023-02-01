package com.example.spring.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AjaxReturnValue {
    String result;
    String msg;
}
