package com.ezTstock.stock_db.mapper;

import com.ezTstock.stock_db.model.serverModel;
import com.ezTstock.stock_db.model.varianceValue;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface varianceValueMapper {

    @Select("select * from variance_value where user_name=#{user_name}") //variance select
    List<varianceValue> selectVarianceValue(@Param("user_name") String user_name);

    @Insert("insert into variance_value values(#{subject_name}, #{user_name}, #{value})") //variance insert
    void insertVarianceValue(@Param("subject_name") String subject_name, @Param("user_name") String user_name, @Param("value") String value);

    @Delete("delete from variance_value where subject_name=#{subject_name} and user_name=#{user_name}") // variance delete
    void deleteVarianceValue(@Param("subject_name") String subject_name, @Param("user_name") String user_name);

    @Update("update variance_value set value=#{value} where subject_name=#{subject_name} and user_name=#{user_name}") // variance value update
    void updateVarianceValue(@Param("subject_name") String subject_name, @Param("user_name") String user_name, @Param("value") String value);

    @Select("select subject_name, value, current from variance_value where user_name=#{user_name}")
    List<serverModel> selectServerData(@Param("user_name") String user_name);

    @Update("update variance_value set current=#{current} where subject_name=#{subject_name} and user_name=#{user_name}")
    void updateVarianceCurrent(@Param("current") String current, @Param("subject_name") String subject_name, @Param("user_name") String user_name);
}
