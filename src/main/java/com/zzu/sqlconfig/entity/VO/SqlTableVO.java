package com.zzu.sqlconfig.entity.VO;

import java.util.List;

public class SqlTableVO {

    private String sqlCode;

    private String sql;

    private String sqlFunction;

    private String params;

    private List<ParamVO> paramsVoList;


    public String getSqlCode() {
        return sqlCode;
    }

    public void setSqlCode(String sqlCode) {
        this.sqlCode = sqlCode;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getSqlFunction() {
        return sqlFunction;
    }

    public void setSqlFunction(String sqlFunction) {
        this.sqlFunction = sqlFunction;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public List<ParamVO> getParamsVoList() {
        return paramsVoList;
    }

    public void setParamsVoList(List<ParamVO> paramsVoList) {
        this.paramsVoList = paramsVoList;
    }

    @Override
    public String toString() {
        return "SqlTableVO{" +
                "sqlCode='" + sqlCode + '\'' +
                ", sql='" + sql + '\'' +
                ", sqlFunction='" + sqlFunction + '\'' +
                ", params='" + params + '\'' +
                ", paramsVoList=" + paramsVoList +
                '}';
    }
}
