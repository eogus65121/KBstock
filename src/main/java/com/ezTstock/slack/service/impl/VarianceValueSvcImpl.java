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
    
    // 종목명, 변동값 가져오기 >> 전체값으로 가져오기
    @Override
    public List<VarianceValueDto> selectServerData(String user_name) throws Exception {
        return this.mapper.selectServerData(user_name);
    }

    // 실시간 알림 종목 추가
    @Override
    public void insert(String subject_name, String user_name, String value, String current) throws Exception {
        this.mapper.insertVarianceValue(subject_name, user_name, value, current);
    }

    // 실시간 알림 종목 삭제
    @Override
    public void deleteVarianceValue(String subject_name, String user_name) throws Exception {
        this.mapper.deleteVarianceValue(subject_name, user_name);
    }

    // 실시간 알림 변동값 수정
    @Override
    public void updateVarianceValue(String subject_name, String user_name, String value) throws Exception {
        this.mapper.updateVarianceValue(subject_name, user_name, value);
    }

    // 실시간 현재가 수정
    @Override
    public void updateVarianceCurrent( String subject_name, String user_name, String current) throws Exception {
        this.mapper.updateVarianceCurrent(subject_name, user_name, current);
    }
}
