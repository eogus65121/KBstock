package com.ezTstock.stock_db.model;

import lombok.*;

@Getter
@Setter
public class userProfile {
    private String name;
    private String requirement;

    public userProfile(String name, String requirement){
        super();
        this.name = name;
        this.requirement = requirement;
    }
}
