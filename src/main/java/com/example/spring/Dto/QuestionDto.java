package com.example.spring.Dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuestionDto {
    private int id;
    private String title;
    private List<ResponseDto> responseList;
    public QuestionDto() {
        responseList = new ArrayList<ResponseDto>();
    }
}

