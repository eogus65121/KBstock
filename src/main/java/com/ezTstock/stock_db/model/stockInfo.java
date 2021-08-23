package com.ezTstock.stock_db.model;

import lombok.*;

@Getter
@Setter
public class stockInfo {
    private String name;
    private String code;

    public stockInfo(String name, String code){
        super();
        this.name = name;
        this.code = code;
    }
}
