package com.example.spring.Service;

import com.example.spring.Dto.QuestionDto;
import com.example.spring.Dto.SurveyDto;
import com.example.spring.Mapper.erp.ErpSurveyMapper;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SurveyServiceImpl implements SurveyService {

    @Autowired
    ErpSurveyMapper surveyMapper;

    @Override
    public SurveyDto findSurvey(int surveyId) {
        SurveyDto survey = surveyMapper.selectSurvey(surveyId);
        return survey;
    }

    @Override
    public List<SurveyDto> findSurveyList() {
        List<SurveyDto> surveyList = surveyMapper.selectSurveyList();
        return surveyList;
    }

    @Override
    @Transactional
    public int addSurvey(SurveyDto surveyDto) {

        /** 기존 Survey 정보가 등록되어 있다면 삭제 **/
        surveyMapper.deleteSurvey(surveyDto.getId());

        /** Survey 정보 등록 **/
        int rowCnt = surveyMapper.insertSurvey(surveyDto);

        /** Question 일괄 등록 **/
        List<QuestionDto> questionList = surveyDto.getQuestionList();

        /** Response 등록 **/
        for (int i=0; i<questionList.size(); i++) {

            QuestionDto question = questionList.get(i);

            Map<String, Object> questionMap = new HashMap<String, Object>();
            questionMap.put("survey_id", surveyDto.getId());
            questionMap.put("question", question);
            surveyMapper.insertQuestion(questionMap);

            Map<String, Object> responseMap = new HashMap<String, Object>();
            responseMap.put("question_id", questionMap.get("id"));
            responseMap.put("responseList", question.getResponseList());
            surveyMapper.insertResponseList(responseMap);

        }

        return rowCnt;
    }

    @Override
    public int deleteSurvey(int surveyId) {
        int rowCnt = surveyMapper.deleteSurvey(surveyId);
        return rowCnt;
    }

}
