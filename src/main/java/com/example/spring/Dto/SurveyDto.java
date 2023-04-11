package com.example.spring.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class SurveyDto {
    private int id;
    private String title;
    private String reg_date;
    private String updt_date;
    private String reg_user;
    private String updt_user;
    private String del_flag;
    private List<QuestionDto> questionList;
}
