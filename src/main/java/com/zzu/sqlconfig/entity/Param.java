package com.zzu.sqlconfig.entity;

import org.springframework.boot.autoconfigure.domain.EntityScan;

@EntityScan
public class Param {

    private int SN;

    private int SQLTABLE_SN;

    private String PARAM_NAME;

    private String PARAM_TYPE;

    private String PARAM_FUNCTION;

    private String NOTES;

    private int RQ_RT;

    public int getSN() {
        return SN;
    }

    public void setSN(int SN) {
        this.SN = SN;
    }

    public int getSQLTABLE_SN() {
        return SQLTABLE_SN;
    }

    public void setSQLTABLE_SN(int SQLTABLE_SN) {
        this.SQLTABLE_SN = SQLTABLE_SN;
    }

    public String getPARAM_NAME() {
        return PARAM_NAME;
    }

    public void setPARAM_NAME(String PARAM_NAME) {
        this.PARAM_NAME = PARAM_NAME;
    }

    public String getPARAM_TYPE() {
        return PARAM_TYPE;
    }

    public void setPARAM_TYPE(String PARAM_TYPE) {
        this.PARAM_TYPE = PARAM_TYPE;
    }

    public String getPARAM_FUNCTION() {
        return PARAM_FUNCTION;
    }

    public void setPARAM_FUNCTION(String PARAM_FUNCTION) {
        this.PARAM_FUNCTION = PARAM_FUNCTION;
    }

    public String getNOTES() {
        return NOTES;
    }

    public void setNOTES(String NOTES) {
        this.NOTES = NOTES;
    }

    public int getRQ_RT() {
        return RQ_RT;
    }

    public void setRQ_RT(int RQ_RT) {
        this.RQ_RT = RQ_RT;
    }

    @Override
    public String toString() {
        return "Param{" +
                "SN=" + SN +
                ", SQLTABLE_SN=" + SQLTABLE_SN +
                ", PARAM_NAME='" + PARAM_NAME + '\'' +
                ", PARAM_TYPE='" + PARAM_TYPE + '\'' +
                ", PARAM_FUNCTION='" + PARAM_FUNCTION + '\'' +
                ", NOTES='" + NOTES + '\'' +
                ", RQ_RT=" + RQ_RT +
                '}';
    }
}
