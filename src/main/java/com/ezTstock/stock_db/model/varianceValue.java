package com.ezTstock.stock_db.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class varianceValue {
    private String subject_name;
    private String user_name;
    private String value;

    public varianceValue(String subject_name, String user_name, String value){
        super();
        this.subject_name = subject_name;
        this.user_name = user_name;
        this.value = value;
    }
}
