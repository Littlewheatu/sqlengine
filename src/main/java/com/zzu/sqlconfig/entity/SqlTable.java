package com.zzu.sqlconfig.entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.util.Date;

@EntityScan
public class SqlTable {

    private int SN;

    private String SQL_CODE;

    private String SQL;

    private String PARAMS;

    private Date CREATE_TIME;

    private Date UPDATE_TIME;

    public int getSN() {
        return SN;
    }

    public void setSN(int SN) {
        this.SN = SN;
    }

    public String getSQL_CODE() {
        return SQL_CODE;
    }

    public void setSQL_CODE(String SQL_CODE) {
        this.SQL_CODE = SQL_CODE;
    }

    public String getSQL() {
        return SQL;
    }

    public void setSQL(String SQL) {
        this.SQL = SQL;
    }

    public String getPARAMS() {
        return PARAMS;
    }

    public void setPARAMS(String PARAMS) {
        this.PARAMS = PARAMS;
    }

    public Date getCREATE_TIME() {
        return CREATE_TIME;
    }

    public void setCREATE_TIME(Date CREATE_TIME) {
        this.CREATE_TIME = CREATE_TIME;
    }

    public Date getUPDATE_TIME() {
        return UPDATE_TIME;
    }

    public void setUPDATE_TIME(Date UPDATE_TIME) {
        this.UPDATE_TIME = UPDATE_TIME;
    }
}
