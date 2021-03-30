package com.zzu.sqlconfig.service;

import com.zzu.sqlconfig.dao.ExcuteSqlMapper;
import com.zzu.sqlconfig.entity.SqlTable;
import com.zzu.sqlconfig.entity.VO.SqlTableVO;
import com.zzu.sqlconfig.util.SqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class ExcuteSqlServiceImpl implements ExcuteSqlService {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private ExcuteSqlMapper esm;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<SqlTable> getAllSql() {
        return esm.selectAllSql();
    }

    @Override
    public List<SqlTable> getSql(String SQL_CODE) {
        return esm.selectSql(SQL_CODE);
    }

    @Override
    public SqlTable getSqlTableByParams(HttpServletRequest httpServletRequest) {
        List<SqlTable> sqlResult = getSqlResult(httpServletRequest);
        String[] paramsList = getParamsList(httpServletRequest);
        SqlUtil sqlUtil = new SqlUtil();
        SqlTable sqltable = sqlUtil.macheSqlTable(paramsList, sqlResult);
        logger.info("匹配后的sqltable是：" + sqltable);
        return sqltable;
    }

    @Override
    public List<Map<String, Object>> getResultList(HttpServletRequest httpServletRequest) {
        List<SqlTable> sqlResult = getSqlResult(httpServletRequest);
        String[] paramsList = getParamsList(httpServletRequest);
        SqlUtil sqlUtil = new SqlUtil();
        String sqlOrigin = sqlUtil.macheSql(paramsList, sqlResult);
        logger.info("匹配后的sql是：" + sqlOrigin);
        //如果没有成功匹配sql，返回
        if (StringUtils.isEmpty(sqlOrigin)) {
            return new LinkedList<>();
        }
        List<Map<String, Object>> result = new LinkedList<>();
        //将匹配后的sql中的参数转为？问号，并且得到参数的顺序，从而传入存放参数值的数组中
        try {
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
            for (int i = 0; i < paramValueOrdered.length; i++) {
                logger.info("参数值为" + paramValueOrdered[i]);
            }

            //执行查询
            result = jdbcTemplate.queryForList(sqlReplaced, paramValueOrdered);
//        result =jdbcTemplate.queryForList(sqlReplaced,paramValueOrdered,new int[]{Types.VARCHAR,Types.INTEGER,Types.INTEGER});
//        result = jdbcTemplate.queryForList(sqlReplaced,"found",11,999);
        } catch (Exception e) {
            logger.error("sql执行有误.");
        }

        return result;
    }

    @Override
    @Transactional
    public Integer addSqlTable(SqlTableVO sqlTableVO) {
        SqlTable sqlTable = new SqlTable();
        //将sqlTableVO的属性复制到sqlTable
       sqlTable.setSQL_CODE(sqlTableVO.getSqlCode());
       sqlTable.setSQL(sqlTableVO.getSql());
       sqlTable.setPARAMS(sqlTableVO.getParams());
       sqlTable.setSQL_FUNCTION(sqlTableVO.getSqlFunction());
       sqlTable.setSQL(sqlTableVO.getSql());
       sqlTable.setCREATE_TIME(new Date());
       sqlTable.setUPDATE_TIME(sqlTable.getCREATE_TIME());
       //向数据库添加数据
        logger.info("sql_code的值：{}",sqlTable.getSQL_CODE());
        logger.info("params的值：{}",sqlTable.getPARAMS());
        return esm.insertSqlTable(sqlTable);
    }

    /**
     * 查询sql_code对应的sqltable列表
     *
     * @return
     */
    private List<SqlTable> getSqlResult(HttpServletRequest httpServletRequest) {
        String SQL_CODE = httpServletRequest.getParameter("sql_code");
        //查询该SQL_CODE下的sql列表
        return getSql(SQL_CODE);
    }

    /**
     * 查询url中传过来的参数列表
     *
     * @return
     */
    private String[] getParamsList(HttpServletRequest httpServletRequest) {
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
        if (paramList != null && paramList.length() != 0) {
            paramList = paramList.substring(0, paramList.length() - 1);
            return paramList.split(",");
        } else {
            return new String[0];
        }
    }
}
