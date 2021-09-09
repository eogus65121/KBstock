package com.ezTstock.slack.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequirementDto {
    private String name;
    public UserRequirementDto(String name){
        super();
        this.name = name;
    }
}
