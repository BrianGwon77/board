package com.example.spring.Dto;

import lombok.Data;

import java.util.Date;

@Data
public class VisitDto {
    private int no;
    private String name;
    private String dept;
    private String contact;
    private String reason;
    private Date enter_date;
    private Date leave_date;
}
