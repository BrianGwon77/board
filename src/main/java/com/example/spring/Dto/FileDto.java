package com.example.spring.Dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class FileDto {
    MultipartFile[] uploadFiles;
    String[] deletedFiles;
}
