package com.example.spring.Dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class PostDto {
    private int bno;
    private int pno;

    @NotEmpty(message="{title.notEmpty}")
    private String title;
    @NotEmpty(message="{content.notEmpty}")
    private String content;
    @NotEmpty(message="{writer.notEmpty}")
    private String writer;

    @Size(min = 4, message = "{password.invalidLength}")
    private String password;
    private int view_cnt;
    private int comment_cnt;
    private Date reg_date;
    private Date updt_date;
    private String mode;
}
