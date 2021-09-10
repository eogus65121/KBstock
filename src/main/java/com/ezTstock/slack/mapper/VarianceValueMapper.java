package com.ezTstock.slack.mapper;

import com.ezTstock.slack.dto.VarianceValueDto;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface VarianceValueMapper {

    @Select("select * from variance_value where user_name=#{user_name}")
    List<VarianceValueDto> selectServerData(@Param("user_name") String user_name);

    @Insert("insert into variance_value values(#{subject_name}, #{user_name}, #{value}, #{current})")
    void insertVarianceValue(@Param("subject_name") String subject_name, @Param("user_name") String user_name, @Param("value") String value, @Param("current") String current);

    @Delete("delete from variance_value where subject_name=#{subject_name} and user_name=#{user_name}")
    void deleteVarianceValue(@Param("subject_name") String subject_name, @Param("user_name") String user_name);

    @Update("update variance_value set value=#{value} where subject_name=#{subject_name} and user_name=#{user_name}")
    void updateVarianceValue(@Param("subject_name") String subject_name, @Param("user_name") String user_name, @Param("value") String value);

    @Update("update variance_value set current=#{current} where subject_name=#{subject_name} and user_name=#{user_name}")
    void updateVarianceCurrent(@Param("current") String current, @Param("subject_name") String subject_name, @Param("user_name") String user_name);
}
