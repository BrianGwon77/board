package com.example.spring.Dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SurveyDto {
    private int id;
    private String title;
    private String reg_date;
    private String updt_date;
    private String reg_user;
    private String updt_user;
    private String delete_flag;
    private List<QuestionDto> questionList;

    public SurveyDto() {
        questionList = new ArrayList<QuestionDto>();
    }
}
