package com.zzu.sqlconfig.service;

import com.zzu.sqlconfig.entity.SqlTable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface ExcuteSqlService {

    List<SqlTable> getAllSql();

    List<SqlTable> getSql(String SQL_CODE);

    SqlTable getSqlTableByParams(HttpServletRequest httpServletRequest);

    List<Map<String, Object>> getResultList(HttpServletRequest httpServletRequest);
}
