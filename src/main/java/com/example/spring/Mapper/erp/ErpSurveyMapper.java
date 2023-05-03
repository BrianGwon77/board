package com.example.spring.Mapper.erp;

import com.example.spring.Dto.QuestionDto;
import com.example.spring.Dto.ResponseDto;
import com.example.spring.Dto.SurveyDto;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.lang.Integer;
import java.util.Map;

@Mapper
public interface ErpSurveyMapper {
    public int insertSurvey(SurveyDto survey);
    public int deleteSurvey(int surveyId);
    public SurveyDto selectSurvey(int surveyId);
    public List<SurveyDto> selectSurveyList();
    public int insertQuestion(Map<String, Object> questionMap);
    public int insertResponseList(Map<String, Object> responseMap);

}
