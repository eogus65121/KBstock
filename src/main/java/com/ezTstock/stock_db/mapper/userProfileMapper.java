package com.ezTstock.stock_db.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface userProfileMapper {

    @Select("select name from user where name=#{name}")
    String getUserName(@Param("name") String name);

    @Insert("insert into user values(#{name}, #{requirement})")
    void insertUserProfile(@Param("name") String name, @Param("requirement") String requirement);

}
