package com.example.spring.Dto;

import lombok.Data;

@Data
public class AttachmentDto {
    private int ano;
    private int pno;
    private int fileSize;
    private String fileName;
    private String fileOriginName;
    private String storageName;
}
