package com.example.spring.Controller;

import com.example.spring.Dto.SearchConditionDto;
import com.example.spring.Dto.VisitDto;
import com.example.spring.Service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/visit")
public class VisitController {

    @Autowired
    VisitService visitService;

    @GetMapping("/list")
    public String list(SearchConditionDto searchCondition, Model model)
    {
        List<VisitDto> visitDtoList = visitService.selectVisitList(searchCondition);
        model.addAttribute("visitDtoList", visitDtoList);
        return "/visit/list";
    } 

    @GetMapping("/register")
    public String register(@RequestParam(required = false, defaultValue = "") String no, Model model)
    {
        VisitDto visitDto;

        if (!no.equals(""))
            visitDto = visitService.selectVisit(no);
        else
            visitDto = new VisitDto();

        model.addAttribute("visitDto", visitDto);

        return "/visit/register";
    }

    @PostMapping("/register.do")
    public String register_do(VisitDto visitDto, Model model)
    {
        int rowCnt = visitService.insertVisit(visitDto);

        String message;

        if (rowCnt > 0)
            message = "방문정보 등록이 완료되었습니다.";
        else
            message = "방문정보 등록이 실패하였습니다.";

        model.addAttribute("message", message);

        return "/visit/result";
    }

    @PostMapping("/update.do")
    public String update_do(VisitDto visitDto, Model model)
    {
        int rowCnt = visitService.updateVisit(visitDto);

        String message;

        if (rowCnt > 0)
            message = "방문정보 수정이 완료되었습니다.";
        else
            message = "방문정보 수정이 실패하였습니다.";

        model.addAttribute("message", message);

        return "/visit/result";
    }

    @PostMapping("/delete.do")
    public String delete_do(String no, Model model)
    {
        int rowCnt = visitService.deleteVisit(no);

        String message;

        if (rowCnt > 0)
            message = "방문정보 삭제가 완료되었습니다.";
        else
            message = "방문정보 삭제가 실패하였습니다.";

        model.addAttribute("message", message);

        return "/visit/result";
    }

}
