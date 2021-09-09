package com.ezTstock.slack.service;

import com.ezTstock.slack.dto.VarianceValueDto;

import java.util.List;

public interface VarianceValueSvc {

    List<VarianceValueDto> selectServerData(String user_name) throws Exception;

    void insert(String subject_name, String user_name, String value, String current) throws Exception;

    void deleteVarianceValue(String subject_name, String user_name) throws Exception;

    void updateVarianceValue(String subject_name, String user_name, String value) throws Exception;

    void updateVarianceCurrent(String current, String subject_name,String user_name) throws Exception;
}
