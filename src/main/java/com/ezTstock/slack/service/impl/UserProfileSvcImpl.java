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

    @Override
    public String getUserName(String name) throws Exception {
        return this.mapper.getUserName(name);
    }

    @Override
    public void insertUserProfile(String name, String requirement) throws Exception {
        this.mapper.insertUserProfile(name, requirement);
    }

    @Override
    public void deleteUserProfile(String name) throws Exception {
        this.mapper.deleteUserProfile(name);
    }

    @Override
    public void updateUserRequirement(String requirement, String name) throws Exception {
        this.mapper.updateUserRequirement(requirement, name);
    }

    @Override
    public List<UserRequirementDto> selectRequirementData() throws Exception {
        return this.mapper.require_user();
    }
}
