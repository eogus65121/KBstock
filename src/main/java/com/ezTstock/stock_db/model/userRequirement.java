package com.ezTstock.stock_db.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.annotations.Select;

@Getter
@Setter
public class userRequirement {
    private String name;
        public userRequirement(String name){
            super();
            this.name = name;
        }
}
