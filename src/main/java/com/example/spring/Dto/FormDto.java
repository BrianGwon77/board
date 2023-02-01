package com.example.spring.Dto;

import lombok.Data;

@Data
public class FormDto {
    String processID; /** process ID **/
    String subject; /** 기안제목 **/
    String initiatorName; /** 기안 상신자 **/
    String bodyContext; /** 기안 본문 **/
    String initiatedDate; /** 기안 상신일 **/
    String completedDate; /** 기안 완료일 **/
}
