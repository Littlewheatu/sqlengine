package com.zzu.sqlconfig.util;

import com.zzu.sqlconfig.entity.SqlTable;

import java.util.Arrays;
import java.util.HashMap;
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

    public SqlTable macheSqlTable(String[] paramsList, List<SqlTable> sqlTables){
        //匹配参数列表
        Arrays.sort(paramsList);
        for (int i = 0; i < sqlTables.size(); i++) {
            String [] sqlParamList = sqlTables.get(i).getPARAMS().split(",");
            Arrays.sort(sqlParamList);
            if(Arrays.equals(paramsList,sqlParamList)){
                return sqlTables.get(i);
            }
        }
        return null;
    }

    public String produceSql(String[] paramsList,Map<String,String[]> paramsMap, String sql){
        //防止sql注入:将参数中所有单引号和双引号前加上反斜线\，达到防止sql注入的情况
        for (Map.Entry<String, String[]> entry : paramsMap.entrySet()) {
            String changedValue = entry.getValue()[0].replaceAll("[\'\"]","\\\\$0");
            paramsMap.put(entry.getKey(),new String[]{changedValue});
        }
        //组装sql
        for (int i = 0; i < paramsList.length; i++) {
            sql = sql.replaceAll("\\$\\{"+paramsList[i]+"\\}","\\"+paramsMap.get(paramsList[i])[0]);
        }
        return sql;
    }

    //将参数中所有单引号和双引号前加上反斜线\
    public String[] replaceSingleQuotes(String[] paramsList){
        for (int i = 0; i < paramsList.length; i++) {
            paramsList[i] = paramsList[i].replaceAll("[\'\"]","\\\\$0");
        }
        return paramsList;
    }

    //在字符串的某个字符前后插入字符串
    public String ReplacementInfo(StringBuilder stringBuilder, String keyword, String before, String rear) {
        //字符第一次出现的位置
        int index = stringBuilder.indexOf(keyword);
        while (index != -1) {
            stringBuilder.insert(index, before);
            stringBuilder.insert(index + before.length() + keyword.length(), rear);
            //下一次出现的位置，
            index = stringBuilder.indexOf(keyword, index + before.length() + keyword.length() + rear.length() - 1);
        }
        return stringBuilder.toString();
    }
}
