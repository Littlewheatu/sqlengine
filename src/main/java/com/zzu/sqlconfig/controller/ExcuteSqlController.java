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
@RequestMapping("/selectSql")
public class ExcuteSqlController {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private ExcuteSqlService ess;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/getSql",method = RequestMethod.GET)
    public List<Map<String, Object>> getSql(HttpServletRequest httpServletRequest){

        String SQL_CODE = httpServletRequest.getParameter("sql_code");
        //查询该SQL_CODE下的sql列表
        List<SqlTable> sqlResult = ess.getSql(SQL_CODE);
        //匹配参数列表
        Enumeration<String> paramsNames= httpServletRequest.getParameterNames();
        StringBuffer paramListStringBuffer = new StringBuffer();
        logger.info("开始匹配-------------");
        while(paramsNames.hasMoreElements()){
            String arg = paramsNames.nextElement();
            if(arg.equals("sql_code")){
                continue;
            }
            paramListStringBuffer.append(arg+",");
        }
        String paramList = paramListStringBuffer.toString();
        if(paramList!=null){
            paramList = paramList.substring(0, paramList.length() - 1);
        }
        String [] paramsList = paramList.split(",");

        SqlUtil sqlUtil = new SqlUtil();
        String sqlOrigin = sqlUtil.macheSql(paramsList,sqlResult);
        logger.info("匹配完成-------------");
        logger.info("匹配后的sql是："+sqlOrigin);
        List<Map<String, Object>> result = new LinkedList<>();
        if(sqlOrigin!=null){
            //获取参数map
            Map<String,String[]> paramsMap = httpServletRequest.getParameterMap();

            //组装sql
            logger.info("开始组装-------------");
            String sqlLater = sqlUtil.produceSql(paramsList,paramsMap,sqlOrigin);
            logger.info("组装后sql为："+sqlLater);

            //执行sql
            result = jdbcTemplate.queryForList(sqlLater);
        }

        return result;
    }

}
