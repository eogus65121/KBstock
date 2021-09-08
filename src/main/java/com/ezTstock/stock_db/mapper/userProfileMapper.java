package com.ezTstock.stock_db.mapper;

import com.ezTstock.stock_db.model.userProfile;
import com.ezTstock.stock_db.model.userRequirement;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface userProfileMapper {

    @Select("select name from user where name=#{name}") // user select
    String getUserName(@Param("name") String name);

    @Insert("insert into user values(#{name}, #{requirement})") //user insert
    void insertUserProfile(@Param("name") String name, @Param("requirement") String requirement);

    @Delete("delete from user where name=#{name}") // user delete
    void deleteUserProfile(@Param("name") String name);

    @Update("update user set requirement=#{requirement} where name=#{name}")  // user on off
    void updateUserRequirement(@Param("requirement") String requirement, @Param("name") String name);

    @Select("select name from user where requirement='on'")
    List<userRequirement> require_user();
}
