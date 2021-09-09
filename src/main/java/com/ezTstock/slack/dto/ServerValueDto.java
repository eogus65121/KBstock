package com.ezTstock.slack.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerValueDto {
    private String subject_name;
    private String value;
    private String current;

    public ServerValueDto(String subject_name, String value, String current) {
        super();
        this.subject_name = subject_name;
        this.value = value;
        this.current = current;
    }
}