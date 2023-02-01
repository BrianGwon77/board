//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.spring.Mapper.erp;
import com.example.spring.Dto.EmployeeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface ErpUserMapper {
    void resetPassword(String employeeNo);
    EmployeeDto findEmployee(String employeeNo);
    String findAuthenticationCode(String employeeNo);
    void issueAuthenticationCode(Map<String, String> map);
    void disposeAuthenticationCode(String employeeNo);
}
