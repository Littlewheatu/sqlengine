package com.zzu.sqlconfig.util;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class SqlUtilTest {

    @Test
    public void testProduceSql(){
        String [] paramList = new String[]{"zhang\"dsan'","123's42s452s","'hahahh\"aha"};
        SqlUtil sqlUtil = new SqlUtil();
        paramList = sqlUtil.replaceSingleQuotes(paramList);
    }

    @Test
    public void testProduceSql2(){
        String [] paramList = new String[]{"name","age","money"};
        Map<String,String[]> paramsMap = new HashMap<>();
        paramsMap.put("sql_code",new String[]{"serchUser"});
        paramsMap.put("name",new String[]{"'found"});
        paramsMap.put("money",new String[]{"999"});
        paramsMap.put("age",new String[]{"11"});
        String sql = "select * from user where name='${name}' and age=${age} and money=${money}";
        SqlUtil sqlUtil = new SqlUtil();
        System.out.println("转义后的sql为："+sqlUtil.produceSql(paramList,paramsMap,sql));
    }
}
