package com.example.spring.Mapper.mes;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface MesUserMapper {
    void resetPassword(String employeeNo);
}
