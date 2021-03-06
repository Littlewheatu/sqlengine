package com.zzu.sqlconfig.util;

import com.zzu.sqlconfig.entity.SqlTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            if(sqlTables.get(i).getPARAMS() != null ){
                String[] sqlParamList = sqlTables.get(i).getPARAMS().split(",");
                Arrays.sort(sqlParamList);
                if (Arrays.equals(paramsList, sqlParamList)) {
                    return sqlTables.get(i).getSQL();
                }
            } else{
                if (paramsList.length == 0 || paramsList == null) {
                    return sqlTables.get(i).getSQL();
                }
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
            if(sqlTables.get(i).getPARAMS() != null ){
                String[] sqlParamList = sqlTables.get(i).getPARAMS().split(",");
                Arrays.sort(sqlParamList);
                if (Arrays.equals(paramsList, sqlParamList)) {
                    return sqlTables.get(i);
                }
            } else{
                if (paramsList.length == 0 || paramsList == null) {
                    return sqlTables.get(i);
                }
            }
        }
        return null;
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
