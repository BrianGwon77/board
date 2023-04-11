package com.example.spring.Dto;

import lombok.Data;

import java.util.Date;

@Data
public class SearchConditionDto {
    private String name;
    private String dept;
    private Date start;
    private Date end;
}
