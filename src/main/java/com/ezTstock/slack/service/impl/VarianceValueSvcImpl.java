package com.ezTstock.slack.service.impl;

import com.ezTstock.slack.dto.VarianceValueDto;
import com.ezTstock.slack.mapper.VarianceValueMapper;
import com.ezTstock.slack.service.VarianceValueSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VarianceValueSvcImpl implements VarianceValueSvc {

    @Autowired
    private VarianceValueMapper mapper;

    @Override
    public List<VarianceValueDto> selectVarianceValue(String user_name) throws Exception {
        return this.mapper.selectVarianceValue(user_name);
    }
    
    // subject 종류 가져오기
    @Override
    public List<VarianceValueDto> selectServerData(String user_name) throws Exception {
        return this.mapper.selectServerData(user_name);
    }

    // insert에 해당하는 service
    @Override
    public void insert(String subject_name, String user_name, String value, String current) throws Exception {
        this.mapper.insertVarianceValue(subject_name, user_name, value, current);
    }

    @Override
    public void deleteVarianceValue(String subject_name, String user_name) throws Exception {
        this.mapper.deleteVarianceValue(subject_name, user_name);
    }

    @Override
    public void updateVarianceValue(String subject_name, String user_name, String value) throws Exception {
        this.mapper.updateVarianceValue(subject_name, user_name, value);
    }

    @Override
    public void updateVarianceCurrent(String current, String subject_name, String user_name) throws Exception {
        this.mapper.updateVarianceCurrent(current, subject_name, user_name);
    }
}
