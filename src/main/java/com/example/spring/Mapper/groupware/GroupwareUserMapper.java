//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.example.spring.Mapper.groupware;
import com.example.spring.Dto.FormDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GroupwareUserMapper {
    void resetPassword(String employeeNo);
    String isExist(String id);
    List<FormDto> getFormList();
}
