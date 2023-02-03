package com.example.spring.Dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Data
public class CommentDto {
    private int cno;
    private int pno;
    private int parent_cno;
    private String receiver;
    private String content;
    private String writer;
    private String password;
    private Date reg_date;
    private Date updt_date;
    private String delete_flag;
    private List<CommentDto> replyCommentList;
}
