package com.example.spring.Controller;

import com.example.spring.Dto.AjaxReturnValue;
import com.example.spring.Dto.AssetDto;
import com.example.spring.Dto.CodeDto;
import com.example.spring.Service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class AssetController {

    @Autowired
    AssetService assetService;

    @Value("${asset.code.status}")
    private String statusCode;
    @Value("${asset.code.useType}")
    private String useTypeCode;
    @Value("${asset.code.location}")
    private String locationCode;

    @GetMapping("/assetInfo")
    public String assetInfo(@RequestParam(defaultValue = "") String no, Model model){

        /** Retrieve code information (status, useType, location, dept) **/
        List<CodeDto> statusCodeList = assetService.selectCodeList(statusCode);
        List<CodeDto> useTypeCodeList = assetService.selectCodeList(useTypeCode);
        List<CodeDto> locationCodeList = assetService.selectCodeList(locationCode);
        List<CodeDto> deptCodeList = assetService.selectDeptList();

        /** Retrieve base64 String of the image of asset **/
        String imageString = assetService.selectImage(no);

        /** Retrieve assetDto **/
        AssetDto assetDto = assetService.selectAsset(no);

        /** Add data into model object sent to view **/
        model.addAttribute("statusCodeList", statusCodeList);
        model.addAttribute("useTypeCodeList", useTypeCodeList);
        model.addAttribute("locationCodeList", locationCodeList);
        model.addAttribute("deptCodeList", deptCodeList);
        model.addAttribute("imageString", imageString);
        model.addAttribute("assetDto", assetDto == null ? new AssetDto() : assetDto);

        return "/asset/asset-info";
    }



    @PostMapping("/update.do")
    public String update(AssetDto assetDto, Model model) {

        int rowCnt = assetService.updateAsset(assetDto);

        if (rowCnt > 0)
            model.addAttribute("message", "업데이트 완료");
        else
            model.addAttribute("message", "업데이트 실패");

        return "/asset/result";

    }
}