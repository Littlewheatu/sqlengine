package com.zzu.sqlconfig.controller;

import com.zzu.sqlconfig.entity.SqlTable;
import com.zzu.sqlconfig.service.ExcuteSqlService;
import com.zzu.sqlconfig.util.SqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/safeExcuteSql")
public class SafeExcuteSqlController {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private ExcuteSqlService ess;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @RequestMapping(value = "/selectAllSqlTable", method = RequestMethod.GET)
    public List<SqlTable> selectAllSqlTable() {
        return ess.getAllSql();
    }

    @RequestMapping(value = "/selectSqlTable", method = RequestMethod.GET)
    public SqlTable selectSqlTable() {
        List<SqlTable> sqlResult = getSqlResult();
        String[] paramsList = getParamsList();
        SqlUtil sqlUtil = new SqlUtil();
        SqlTable sqltable = sqlUtil.macheSqlTable(paramsList, sqlResult);
        logger.info("匹配后的sqltable是：" + sqltable);
        return sqltable;
    }

    @RequestMapping(value = "/excuteSql", method = RequestMethod.GET)
    public List<Map<String, Object>> excuteSql() {
        List<SqlTable> sqlResult = getSqlResult();
        String[] paramsList = getParamsList();
        SqlUtil sqlUtil = new SqlUtil();
        String sqlOrigin = sqlUtil.macheSql(paramsList, sqlResult);
        logger.info("匹配后的sql是：" + sqlOrigin);
        //将匹配后的sql中的参数转为？问号，并且得到参数的顺序，从而传入存放参数值的数组中
        String sqlReplaced = sqlUtil.repalaceParams(sqlOrigin);
        logger.info("替换?后的sql语句是" + sqlReplaced);
        //获取参数map
        Map<String, String[]> paramsMap = new HashMap<>(httpServletRequest.getParameterMap());

        String[] paramKeyOrdered = sqlUtil.getParams(sqlOrigin);

        String[] paramValueOrdered = new String[paramKeyOrdered.length];
        for (int i = 0; i < paramKeyOrdered.length; i++) {
            paramValueOrdered[i] = paramsMap.get(paramKeyOrdered[i])[0];
        }

        //打印参数值列表
//        for (int i = 0; i < paramValueOrdered.length; i++) {
//            logger.info("参数值为"+paramValueOrdered[i]);
//        }

        //执行查询
        List<Map<String, Object>> result = new LinkedList<>();
        result = jdbcTemplate.queryForList(sqlReplaced, paramValueOrdered);
//        result =jdbcTemplate.queryForList(sqlReplaced,paramValueOrdered,new int[]{Types.VARCHAR,Types.INTEGER,Types.INTEGER});
//        result = jdbcTemplate.queryForList(sqlReplaced,"found",11,999);
        return result;
    }

    private List<SqlTable> getSqlResult() {
        String SQL_CODE = httpServletRequest.getParameter("sql_code");
        //查询该SQL_CODE下的sql列表
        return ess.getSql(SQL_CODE);
    }

    private String[] getParamsList() {
        Enumeration<String> paramsNames = httpServletRequest.getParameterNames();
        StringBuffer paramListStringBuffer = new StringBuffer();
        while (paramsNames.hasMoreElements()) {
            String arg = paramsNames.nextElement();
            if (arg.equals("sql_code")) {
                continue;
            }
            paramListStringBuffer.append(arg + ",");
        }
        String paramList = paramListStringBuffer.toString();
        if (paramList != null) {
            paramList = paramList.substring(0, paramList.length() - 1);
        }
        return paramList.split(",");
    }

}
