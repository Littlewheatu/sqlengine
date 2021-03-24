package com.zzu.sqlconfig.service;

import com.zzu.sqlconfig.entity.SqlTable;

import java.util.List;

public interface ExcuteSqlService {

    public List<SqlTable> getSql(String SQL_CODE);
}
