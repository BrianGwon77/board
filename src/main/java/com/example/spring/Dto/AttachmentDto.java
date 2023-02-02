package com.example.spring.Dto;

import lombok.Data;

@Data
public class AttachmentDto {
    private int ano;
    private int pno;
    private String fileName;
    private String originFileName;
    private String filePath;
    private int fileSize;
}
