package com.example.spring.Dto;

import lombok.Data;
import java.util.List;

@Data
public class SuremResponse {
    private String code;
    private String message;
    private List<Result> results;
}
