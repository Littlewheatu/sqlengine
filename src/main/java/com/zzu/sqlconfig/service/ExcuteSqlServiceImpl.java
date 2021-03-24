package com.zzu.sqlconfig.service;

import com.zzu.sqlconfig.dao.ExcuteSqlMapper;
import com.zzu.sqlconfig.entity.SqlTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExcuteSqlServiceImpl implements ExcuteSqlService{

    @Autowired
    private ExcuteSqlMapper esm;

    @Override
    public List<SqlTable> getSql(String SQL_CODE) {

        return esm.selectSql(SQL_CODE);
    }
}
