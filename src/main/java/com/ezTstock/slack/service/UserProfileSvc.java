package com.ezTstock.slack.service;

import com.ezTstock.slack.dto.UserRequirementDto;

import java.util.List;

public interface UserProfileSvc {

    String getUserName(String name) throws Exception;

    void insertUserProfile(String name, String requirement) throws Exception;

    void deleteUserProfile(String name) throws Exception;

    void updateUserRequirement(String requirement, String name) throws Exception;

    List<UserRequirementDto> selectRequirementData() throws Exception;

}
