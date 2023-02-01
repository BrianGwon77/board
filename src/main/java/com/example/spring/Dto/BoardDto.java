package com.example.spring.Dto;

import lombok.Data;

import java.util.Date;

@Data
public class BoardDto {

    // 게시판 번호.
    private int bno;

    // 게시판 제목
    private String title;

    // 게시판 설명
    private String explaination;
    
    // 게시판 생성일
    private Date reg_date;
    
    // 게시판 수정일
    private Date updt_date;

    // 생성자
    private String creater;

    // 수정자
    private String updater;

    // 삭제여부
    private String delete_flag;

}
