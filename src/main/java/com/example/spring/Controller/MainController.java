//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.spring.Controller;

import com.example.spring.Constant;
import com.example.spring.Dto.*;
import com.example.spring.Service.CertificationService;
import com.example.spring.Service.RestTemplateService;
import com.example.spring.Service.UserService;

import java.util.*;

import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class MainController {

    private static final Logger log = LoggerFactory.getLogger(MainController.class);
    private final UserService userService;
    private final CertificationService certificationService;
    private final RestTemplateService restTemplateService;

    @GetMapping({"/sendSMS"})
    @ResponseBody
    public AjaxReturnValue sendSMS(HttpServletRequest request, String employeeNo) {

        EmployeeDto employeeDto = userService.findEmployee(employeeNo);
        String contact = (employeeDto == null) ? null : employeeDto.getContact();

        /** 입력한 사번이 존재하지 않는 사번인 경우 혹은 휴대전화번호가 등록되어 있지 않은 경우 return false **/
        if (contact == null)
            return new AjaxReturnValue("false", "존재하지 않는 사번입니다.");

        /** 입력 기간이 만료되지 않은 인증 번호가 존재할 경우 return false **/
        String authenticationCode = userService.findAuthenticationCode(employeeNo);
        if (authenticationCode != null)
            return new AjaxReturnValue("false", "잠시 후에 다시 시도해주세요");

        /** 관리자로 등록된 휴대전화번호인 경우 4자리 난수 생성 후 휴대폰 SMS 발송 **/
        Random rand = new Random();
        String numStr = "";

        for(int i = 0; i < 4; ++i) {
            String ran = Integer.toString(rand.nextInt(10));
            numStr = numStr + ran;
        }

        System.out.println("수신자 번호 : " + contact);
        System.out.println("인증번호 : " + numStr);
        String code = this.restTemplateService.surem(contact, numStr);

        Map<String, String> map = new HashMap<String, String>();
        map.put("employeeNo" , employeeNo);
        map.put("authenticationCode", numStr);

        userService.issueAuthenticationCode(map);

        if (!code.equals("200"))
            return new AjaxReturnValue("false", "인증번호 발송에 실패하였습니다.");

        /** 4자리 난수 반환 **/
        return new AjaxReturnValue("success", numStr);

    }

    @GetMapping("/axios")
    @ResponseBody
    public List<String> axios() {
        List<String> stringList = new ArrayList<String>();
        stringList.add("Jackson");
        stringList.add("Amber");
        stringList.add("Daphne");
        return stringList;
    }

    @GetMapping({"/resetPassword"})
    @ResponseBody
    public AjaxReturnValue resetPassword(HttpServletRequest request, int system) {
        String employeeNo = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.resetPassword(employeeNo, system);
        if (system == Constant.ERP || system == Constant.MES)
            return new AjaxReturnValue("true", "비밀번호 초기화가 완료되었습니다" + "<br>" + "아무 비밀번호나 입력하시면 초기화 화면이 나타납니다.");
        return new AjaxReturnValue("true", "비밀번호가 intops1234!로 초기화 되었습니다");
    }

    @GetMapping({"/resetPasswordAdmin"})
    @ResponseBody
    public AjaxReturnValue resetPasswordAdmin(HttpServletRequest request, String employeeNo, int system) {
        userService.resetPassword(employeeNo, system);
        if (system == Constant.ERP || system == Constant.MES)
            return new AjaxReturnValue("true", "비밀번호 초기화가 완료되었습니다" + "<br>" + "아무 비밀번호나 입력하시면 초기화 화면이 나타납니다.");
        return new AjaxReturnValue("true", "그룹웨어 비밀번호가 intops1234!로 초기화 되었습니다");
    }

    @GetMapping({"/login"})
    public String certification(HttpServletRequest request,
                                HttpServletResponse response,
                                @RequestParam(value="error", required = false) String error,
                                @RequestParam(value="exception", required = false) String exception,
                                Model model) {
        Device device = DeviceUtils.getCurrentDevice(request);
        model.addAttribute("authenticationDto", new AuthenticationDto());
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        if (device.isMobile())
            return "login_mobile";
        else
            return "login";
    }

    @GetMapping({"/main"})
    public String main(HttpServletRequest request, HttpServletResponse response) {
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))
            return "main_admin";
        return "main";
    }

    @PostMapping("/getFormList")
    @ResponseBody
    public Object getFormList() throws ParseException {

        /** 결재완료된 기안목록 Load **/
        List<FormDto> formDtoList = userService.getFormList();
        List<FormAjaxDto> result = new ArrayList<FormAjaxDto>();

        for (FormDto formDto : formDtoList) {

            /** BodyContext Decoding **/
            String bodyContext = new String(Base64Utils.decodeFromString(formDto.getBodyContext()));

            /** Parse Json **/
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(bodyContext);

            /** Request Item **/
            String system = (jsonObject.get("Request_Item") == null) ? null : jsonObject.get("Request_Item").toString();

            if (system == null || !system.contains("Approval_Groupware"))
                continue;

            /** Application ID **/
            String id = (jsonObject.get("Application_ID") == null) ? " " : jsonObject.get("Application_ID").toString();

            if (!userService.isExistOnGroupware(id))
                result.add(FormAjaxDto.createFormAjaxDto(formDto.getProcessID(), formDto.getSubject(), id, "Groupware",
                        formDto.getInitiatedDate(), formDto.getCompletedDate()));

        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("data", result);

        Object object = map;

        return object;

    }

    @GetMapping("/formList")
    public String formList(){
        log.info("formList called..");
        return "formList";
    }
}
