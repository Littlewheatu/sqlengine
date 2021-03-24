package com.zzu.sqlconfig.util;

import com.zzu.sqlconfig.entity.SqlTable;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SqlUtil {

    public String macheSql(String[] paramsList, List<SqlTable> sqlTables){
        //匹配参数列表
        Arrays.sort(paramsList);
        for (int i = 0; i < sqlTables.size(); i++) {
            String [] sqlParamList = sqlTables.get(i).getPARAMS().split(",");
            Arrays.sort(sqlParamList);
            if(Arrays.equals(paramsList,sqlParamList)){
                return sqlTables.get(i).getSQL();
            }
        }
        return null;
    }

    public String produceSql(String[] paramsList,Map<String,String[]> paramsMap, String sql){
        //组装sql
        for (int i = 0; i < paramsList.length; i++) {
            sql = sql.replaceAll("SIGN_"+paramsList[i],paramsMap.get(paramsList[i])[0]);
        }
        return sql;
    }
}
