package com.example.spring.Dto;

import lombok.Data;

@Data
public class AttachmentDto {
    private int ano;
    private int pno;
    private int file_size;
    private String file_name;
    private String file_origin_name;
    private String storage_name;
    private String image;
}
