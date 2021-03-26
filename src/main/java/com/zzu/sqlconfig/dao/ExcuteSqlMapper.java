package com.zzu.sqlconfig.dao;

import com.zzu.sqlconfig.entity.SqlTable;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ExcuteSqlMapper {

    List<SqlTable> selectAllSql();

    List<SqlTable> selectSql(String SQL_CODE);
}
