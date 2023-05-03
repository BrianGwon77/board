package com.example.spring.Controller;

import com.example.spring.Dto.AjaxReturnValue;
import com.example.spring.Dto.SurveyDto;
import com.example.spring.Service.SurveyService;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/survey")
public class SurveyController {

    @Autowired
    SurveyService surveyService;

    @GetMapping("/update/{surveyId}")
    public String updateGet(Model model, @PathVariable(value="surveyId") String surveyId) {
        SurveyDto survey = surveyService.findSurvey(Integer.parseInt(surveyId));
        if (survey == null) {
            survey = new SurveyDto();
        }
        model.addAttribute("survey", survey);
        return "/survey/main";

    }

    @PostMapping("/save")
    @ResponseBody
    public String save(@RequestBody SurveyDto survey) {
        System.out.println("Processing Data...");
        surveyService.addSurvey(survey);
        return "success";
    }

    @GetMapping("/list")
    public String select(Model model) {
        System.out.println("Processing Data...");
        List<SurveyDto> surveyList = surveyService.findSurveyList();
        model.addAttribute("surveyList", surveyList);
        return "/survey/list";
    }

    @GetMapping("/conduct/{surveyId}")
    public String conduct(@PathVariable String surveyId, Model model) {
        System.out.println("Processing Data...");
        SurveyDto survey = surveyService.findSurvey(Integer.parseInt(surveyId));
        model.addAttribute("survey", survey);
        return "/survey/conduct";
    }

    @PostMapping("/delete")
    @ResponseBody
    public AjaxReturnValue conduct(@RequestBody String surveyId) {
        System.out.println("Processing Data...");
        int rowCnt = surveyService.deleteSurvey(Integer.parseInt(surveyId));
        AjaxReturnValue res = new AjaxReturnValue();
        if (rowCnt > 0)
            res.setResult("success");
        else
            res.setResult("fail");
        return res;
    }

}
