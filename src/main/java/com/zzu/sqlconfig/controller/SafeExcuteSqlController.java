package com.zzu.sqlconfig.controller;

import com.zzu.sqlconfig.entity.SqlTable;
import com.zzu.sqlconfig.service.ExcuteSqlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/safeExcuteSql")
/**
 * 安全查询、执行sql
 */
public class SafeExcuteSqlController {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private ExcuteSqlService ess;

    @Autowired
    private HttpServletRequest httpServletRequest;

    /**
     * 查询所有sqltable
     *
     * @return
     */
    @RequestMapping(value = "/selectAllSqlTable", method = RequestMethod.GET)
    public List<SqlTable> selectAllSqlTable() {
        return ess.getAllSql();
    }

    /**
     * 查询匹配参数的sqltable
     *
     * @return
     */
    @RequestMapping(value = "/selectSqlTable", method = RequestMethod.GET)
    public SqlTable selectSqlTable() {
        return ess.getSqlTableByParams(httpServletRequest);
    }

    /**
     * 查询匹配参数的sql执行结果
     *
     * @return
     */
    @RequestMapping(value = "/excuteSql", method = RequestMethod.GET)
    public List<Map<String, Object>> excuteSql() {
        return ess.getResultList(httpServletRequest);
    }

}
