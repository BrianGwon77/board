package com.example.spring.Service;

import com.example.spring.Dto.SurveyDto;

import java.util.List;

public interface SurveyService {

    public SurveyDto findSurvey(int surveyId);
    public List<SurveyDto> findSurveyList();
    public int addSurvey(SurveyDto surveyDto);

    public int deleteSurvey(int surveyId);

}
