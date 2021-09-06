package com.ezTstock.stock_db.controller;

import com.ezTstock.stock_db.mapper.varianceValueMapper;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
public class VarianceValueController {
    public varianceValueMapper mapper;

    public VarianceValueController(varianceValueMapper mapper){ this.mapper = mapper; }

    @GetMapping("/variance/get/user_data") //list 값으로 변경
    public String getVarianceValue(@RequestParam("user_name") String user_name){
        return mapper.selectVarianceValue(user_name);
    }

    @PutMapping("/variance/add/user")
    public void addVariance(@RequestParam("subject_name") String subject_name, @RequestParam("user_name") String user_name, @RequestParam("value") float value){
        mapper.insertVarianceValue(subject_name, user_name, value);
    }

    @PostMapping("/variance/value/update")
    public void updateVariance(@RequestParam("subject_name") String subject_name, @RequestParam("user_name") String user_name, @RequestParam("value") float value){
        mapper.updateVarianceValue(subject_name, user_name, value);
    }

    @DeleteMapping("/variance/value/delete/{user_name}")
    public void deleteVariance(@PathVariable("user_name") String user_name, @RequestParam("subject_name") String subject_name){
        mapper.deleteVarianceValue(subject_name, user_name);
    }
}
