package com.ezTstock.slack.service.impl;

import com.ezTstock.slack.dto.UserRequirementDto;
import com.ezTstock.slack.mapper.UserProfileMapper;
import com.ezTstock.slack.service.UserProfileSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileSvcImpl implements UserProfileSvc {

    @Autowired
    private UserProfileMapper mapper;

    // 사용자 존재 확인
    @Override
    public String getUserName(String name) throws Exception {
        return this.mapper.getUserName(name);
    }

    // 사용자 추가
    @Override
    public void insertUserProfile(String name, String requirement) throws Exception {
        this.mapper.insertUserProfile(name, requirement);
    }

    // 사용자 제거
    @Override
    public void deleteUserProfile(String name) throws Exception {
        this.mapper.deleteUserProfile(name);
    }

    // requirement on / off
    @Override
    public void updateUserRequirement(String requirement, String name) throws Exception {
        this.mapper.updateUserRequirement(requirement, name);
    }

    // requirement on인 사용자 출력
    @Override
    public List<UserRequirementDto> selectRequirementData() throws Exception {
        return this.mapper.require_user();
    }
}
