package com.zzu.sqlconfig.dao;

import com.zzu.sqlconfig.entity.Param;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ParamMapper {

    List<Param> selectParams();
}
