package com.ezTstock.stock_db.mapper;

import com.ezTstock.stock_db.model.stockInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface stockInfoMapper {

    @Select("SELECT*FROM stock_info")
    List<stockInfo> getStockInfoList();
}
