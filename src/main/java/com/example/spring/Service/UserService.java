//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.spring.Service;

import com.example.spring.Constant;
import com.example.spring.Dto.EmployeeDto;
import com.example.spring.Dto.FormDto;
import com.example.spring.Mapper.erp.ErpUserMapper;
import com.example.spring.Mapper.groupware.GroupwareUserMapper;
import com.example.spring.Mapper.mes.MesUserMapper;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@Transactional(
        readOnly = true
)
@RequiredArgsConstructor
public class UserService {

    /**
        1. Http Request > ServletFilter (Servlet Container)
        2. DelegatingFilterProxy > FilterChainProxy (Spring Container)
           FilterChainProxy - List<FilterChain> (Matchers, List<Filter>)
           사용자의 Request (요청 자원, URL)이 Matchers와 일치한다면 List<Filter> 의 Filter들을 순차적으로 통과시킨다.
    **/

    private final ErpUserMapper erpUserMapper;
    private final MesUserMapper mesUserMapper;
    private final GroupwareUserMapper groupwareUserMapper;

    public EmployeeDto findEmployee(String employeeNo){
        return erpUserMapper.findEmployee(employeeNo);
    }

    public void resetPassword(String employeeNo, int system) {
        if (system == Constant.ERP) {
            this.erpUserMapper.resetPassword(employeeNo);
        } else if (system == Constant.MES)
            this.mesUserMapper.resetPassword(employeeNo);
        else if (system == Constant.Groupware)
            this.groupwareUserMapper.resetPassword(employeeNo);
    }

    public String findAuthenticationCode(String employeeNo){
        return erpUserMapper.findAuthenticationCode(employeeNo);
    }

    public void issueAuthenticationCode(Map<String, String> map){
        erpUserMapper.issueAuthenticationCode(map);
    }

    public void disposeAuthenticationCode(String employeeNo){
        erpUserMapper.disposeAuthenticationCode(employeeNo);
    }

    public boolean isExistOnGroupware(String id) {
        return (groupwareUserMapper.isExist(id) == null) ? false : true;
    }

    public List<FormDto> getFormList() { return groupwareUserMapper.getFormList(); }

}
