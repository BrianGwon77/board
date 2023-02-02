package com.example.spring.Dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CommentDto {
    private int cno;
    private int pno;
    private String writer;
    private String password;
    private int parent_cno;
    private String receiver;
    private Date reg_date;
    private Date updt_date;
    private List<CommentDto> commentDtoList;
}
