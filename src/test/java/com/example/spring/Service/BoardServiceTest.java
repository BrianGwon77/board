package com.example.spring.Service;

import com.example.spring.Dto.SurveyDto;
import com.example.spring.Mapper.erp.ErpSurveyMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardServiceTest {

    @Autowired
    ErpSurveyMapper erpSurveyMapper;

    @Test
    public void selectComment() {
        SurveyDto surveyDto = erpSurveyMapper.selectSurvey(1);
        System.out.println("SurveyDto=" + surveyDto);
    }
}