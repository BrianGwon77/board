package com.example.spring.Dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileDto {
    private MultipartFile[] uploadfiles;
    private String[] deleteFiles;
}
