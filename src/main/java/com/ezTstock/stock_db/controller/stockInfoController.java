package com.ezTstock.stock_db.controller;

import com.ezTstock.stock_db.mapper.stockInfoMapper;
import com.ezTstock.stock_db.model.stockInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class stockInfoController {

    private stockInfoMapper mapper;

    public stockInfoController(stockInfoMapper mapper){
        this.mapper = mapper;
    }

    @GetMapping("/stock_info/all")
    public List<stockInfo> getStockInfoList(){
        return mapper.getStockInfoList();
    }
}
