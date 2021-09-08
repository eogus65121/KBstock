package com.ezTstock.stock_db.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class serverModel {
    private String subject_name;
    private String value;
    private String current;

    public serverModel(String subject_name, String value, String current){
        super();
        this.subject_name = subject_name;
        this.value = value;
        this.current = current;
    }
}