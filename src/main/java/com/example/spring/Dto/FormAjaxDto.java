package com.example.spring.Dto;

import lombok.Data;

@Data
public class FormAjaxDto {

    private String processID;
    private String subject;
    private String applicationID;
    private String system;
    private String initiatedDate;  /** 상신일 **/
    private String completedDate; /** 완료일 **/

    public static FormAjaxDto createFormAjaxDto(String processID, String subject
                                                ,String applicationID, String system
                                                ,String initiatedDate, String completedDate)
    {
        FormAjaxDto formAjaxDto = new FormAjaxDto();
        formAjaxDto.setProcessID(processID);
        formAjaxDto.setSubject(subject);
        formAjaxDto.setApplicationID(applicationID);
        formAjaxDto.setSystem(system);
        formAjaxDto.setInitiatedDate(initiatedDate);
        formAjaxDto.setCompletedDate(completedDate);

        return formAjaxDto;
    }

}
