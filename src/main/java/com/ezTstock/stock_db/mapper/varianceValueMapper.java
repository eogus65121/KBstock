package com.ezTstock.stock_db.mapper;

import com.ezTstock.stock_db.model.varianceValue;
import org.apache.ibatis.annotations.*;

@Mapper
public interface varianceValueMapper {

    @Select("select * from variance_value where user_name=#{user_name}") //variance select
    String selectVarianceValue(@Param("user_name") String user_name);

    @Insert("insert into variance_value values(#{subject_name}, #{user_name}, #{value})") //variance insert
    void insertVarianceValue(@Param("subject_name") String subject_name, @Param("user_name") String user_name, @Param("value") float value);

    @Delete("delete from variance_value where subject_name=#{subject_name} and user_name=#{user_name}") // variance delete
    void deleteVarianceValue(@Param("subject_name") String subject_name, @Param("user_name") String user_name);

    @Update("update variance_value set value=#{value} where subject_name=#{subject_name} and user_name=#{user_name}") // variance value update
    void updateVarianceValue(@Param("subject_name") String subject_name, @Param("user_name") String user_name, @Param("value") float value);
}
