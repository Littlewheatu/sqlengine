package com.zzu.sqlconfig.util;

import com.zzu.sqlconfig.entity.SqlTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SqlUtil {

    /**
     * 匹配参数列表，返回符合条件的Sql
     *
     * @param paramsList
     * @param sqlTables
     * @return
     */
    public String macheSql(String[] paramsList, List<SqlTable> sqlTables) {
        Arrays.sort(paramsList);
        for (int i = 0; i < sqlTables.size(); i++) {
            String[] sqlParamList = sqlTables.get(i).getPARAMS().split(",");
            Arrays.sort(sqlParamList);
            if (Arrays.equals(paramsList, sqlParamList)) {
                return sqlTables.get(i).getSQL();
            }
        }
        return null;
    }

    /**
     * 匹配参数列表，返回符合条件的Sqltable
     *
     * @param paramsList
     * @param sqlTables
     * @return
     */
    public SqlTable macheSqlTable(String[] paramsList, List<SqlTable> sqlTables) {
        Arrays.sort(paramsList);
        for (int i = 0; i < sqlTables.size(); i++) {
            String[] sqlParamList = sqlTables.get(i).getPARAMS().split(",");
            Arrays.sort(sqlParamList);
            if (Arrays.equals(paramsList, sqlParamList)) {
                return sqlTables.get(i);
            }
        }
        return null;
    }

    /**
     * 未防止sql注入的方法
     *
     * @param paramsList
     * @param paramsMap
     * @param sql
     * @return
     */
    public String unSafeProduceSql(String[] paramsList, Map<String, String[]> paramsMap, String sql) {
        //组装sql
        for (int i = 0; i < paramsList.length; i++) {
            sql = sql.replaceAll("\\$\\{" + paramsList[i] + "\\}", paramsMap.get(paramsList[i])[0]);
        }
        return sql;
    }

    /**
     * 原防止sql注入方法：在所有单引号双引号前加上反斜杠
     *
     * @param paramsList
     * @param paramsMap
     * @param sql
     * @return
     */
    public String produceSql(String[] paramsList, Map<String, String[]> paramsMap, String sql) {
        //防止sql注入:将参数中所有单引号和双引号前加上反斜线\，达到防止sql注入的情况
        for (Map.Entry<String, String[]> entry : paramsMap.entrySet()) {
            String changedValue = entry.getValue()[0].replaceAll("[\'\"]", "\\\\$0");
            paramsMap.put(entry.getKey(), new String[]{changedValue});
        }
        //组装sql
        for (int i = 0; i < paramsList.length; i++) {
            sql = sql.replaceAll("\\$\\{" + paramsList[i] + "\\}", "\\" + paramsMap.get(paramsList[i])[0]);
        }
        return sql;
    }

    /**
     * 将参数中所有单引号和双引号前加上反斜线\
     *
     * @param paramsList
     * @return
     */
    public String[] replaceSingleQuotes(String[] paramsList) {
        for (int i = 0; i < paramsList.length; i++) {
            paramsList[i] = paramsList[i].replaceAll("[\'\"]", "\\\\$0");
        }
        return paramsList;
    }

    /**
     * 在字符串的某个字符前插入字符串
     *
     * @param s
     * @param keyword
     * @param before
     * @return
     */
    public String ReplacementInfo(String s, String keyword, String before) {
        StringBuilder stringBuilder = new StringBuilder(s);
        //字符第一次出现的位置
        int index = stringBuilder.indexOf(keyword);
        while (index != -1) {
            stringBuilder.insert(index, before);
//            System.out.println("前面插入以后，字符串是："+stringBuilder);
            //下一次出现的位置，
            index = stringBuilder.indexOf(keyword, index + before.length() + keyword.length());
        }
        return stringBuilder.toString();
    }

    /**
     * 将sql中${param}替换为？
     *
     * @param sqlOrigin
     * @return
     */
    public String repalaceParams(String sqlOrigin) {
        int leftPoint = sqlOrigin.indexOf("${");
        int rightPoint = sqlOrigin.indexOf("}");
        while (leftPoint != -1 && rightPoint != -1) {
            String tString = sqlOrigin.substring(leftPoint, rightPoint + 1);
            //在 ${ 和 } 字符串前面加上\ ,用以做正则判断
            tString = ReplacementInfo(tString, "$", "\\");
            tString = ReplacementInfo(tString, "{", "\\");
            tString = ReplacementInfo(tString, "}", "\\");
            sqlOrigin = sqlOrigin.replaceAll(tString, " ? ");
            leftPoint = sqlOrigin.indexOf("${");
            rightPoint = sqlOrigin.indexOf("}");
        }
        return sqlOrigin;
    }

    /**
     * 获取sql中有序的参数列表
     *
     * @param sqlOrigin
     * @return
     */
    public String[] getParams(String sqlOrigin) {
        int leftPoint = sqlOrigin.indexOf("${");
        int rightPoint = sqlOrigin.indexOf("}");
        List<String> resultList = new ArrayList<>();
        while (leftPoint != -1 && rightPoint != -1) {
            String tString = sqlOrigin.substring(leftPoint, rightPoint + 1);
            String resultString = sqlOrigin.substring(leftPoint + 2, rightPoint);
            resultList.add(resultString);
            tString = ReplacementInfo(tString, "$", "\\");
            tString = ReplacementInfo(tString, "{", "\\");
            tString = ReplacementInfo(tString, "}", "\\");
            sqlOrigin = sqlOrigin.replaceAll(tString, "?");
            leftPoint = sqlOrigin.indexOf("${");
            rightPoint = sqlOrigin.indexOf("}");
        }
        String[] resultStringArray = new String[resultList.size()];
        for (int i = 0; i < resultList.size(); i++) {
            resultStringArray[i] = resultList.get(i);
        }
        return resultStringArray;
    }
}
