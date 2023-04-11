package com.example.spring.Controller;

import com.example.spring.Dto.QuestionDto;
import com.example.spring.Dto.ResponseDto;
import com.example.spring.Dto.SurveyDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/survey")
public class SurveyController {

    @GetMapping("/add")
    public String add(Model model) {

        List<ResponseDto> responseList = new ArrayList<ResponseDto>();

        ResponseDto response1 = new ResponseDto(1, "전혀 그렇지 않다", 1);
        ResponseDto response2 = new ResponseDto(2, "가끔 그렇다", 2);
        ResponseDto response3 = new ResponseDto(3, "보통이다", 3);
        ResponseDto response4 = new ResponseDto(4, "자주 그렇다", 4);
        ResponseDto response5 = new ResponseDto(5, "항상 그렇다", 5);

        responseList.add(response1);
        responseList.add(response2);
        responseList.add(response3);
        responseList.add(response4);
        responseList.add(response5);

        List<QuestionDto> questionList = new ArrayList<QuestionDto>();

        QuestionDto question1 = new QuestionDto(1, "1번 문항입니다", responseList);
        QuestionDto question2 = new QuestionDto(2, "2번 문항입니다", responseList);
        QuestionDto question3 = new QuestionDto(3, "3번 문항입니다", responseList);
        QuestionDto question4 = new QuestionDto(4, "4번 문항입니다", responseList);
        QuestionDto question5 = new QuestionDto(5, "5번 문항입니다", responseList);

        questionList.add(question1);
        questionList.add(question2);
        questionList.add(question3);
        questionList.add(question4);
        questionList.add(question5);

        // Dummy Survey Instance..
        SurveyDto survey = new SurveyDto(1, "설문조사 1번", "2023-03-31", "2023-03-31", "관리자", "관리자", "N", questionList);

        model.addAttribute("survey", survey);

        return "/survey/main";

    }

    @PostMapping("/save")
    public String save(@RequestBody SurveyDto survey) {
        System.out.println("Processing Data...");
        return "/survey/main";
    }

}
