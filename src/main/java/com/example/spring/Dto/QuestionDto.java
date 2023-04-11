package com.example.spring.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class QuestionDto {
    private int id;
    private String title;
    private List<ResponseDto> responseList;
}

