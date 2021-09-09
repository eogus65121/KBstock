package com.ezTstock.slack.dto;

import lombok.*;

@Getter
@Setter
public class UserProfileDto {
    private String name;
    private String requirement;

    public UserProfileDto(String name, String requirement){
        super();
        this.name = name;
        this.requirement = requirement;
    }
}
