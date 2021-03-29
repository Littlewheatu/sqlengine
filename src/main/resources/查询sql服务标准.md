## 查询sql服务标准

### 服务简介

本服务旨在提供更加直观的数据库查询服务，减免java对数据的封装、解析和格式转换等过程，避免对于每种查询均层层通过Controller、Service、Dao的过程。我们通过将sql参数化配置的形式，抽离执行sql的过程，不需要因为sql的改变而改动服务代码。

### 执行流程

```
①配置人员添加SQL信息至数据库（sqltable表）
②前端人员通过url进入服务，参数中含有识别目标sql的sql_code以及目标sql所需的各参数
③java服务收到请求，根据参数中的sql_code查询sqltable列表（存在相同sql_code但参数列表不同的情况）
④java服务根据除sql_code的其他参数匹配sql
⑤java服务将占位符替换，组装sql
⑥java服务执行sql，返回查询结果
⑦前端人员将返回结果展示
```

### 数据库存储结构

sqltable

| 字段名       | 类型     | 长度 | 是否主键 | 允许空值 | 含义                | 备注                                   |
| ------------ | -------- | ---- | -------- | -------- | ------------------- | -------------------------------------- |
| sn           | int      | 11   | 是       | 否       | 序列号              | 自增                                   |
| sql_code     | varchar  | 300  | 否       | 否       | 标识目标sql的接口名 |                                        |
| sql          | varchar  | 500  | 否       | 否       | 配置的sql语句       | 需要传入的参数使用${paramName}形式填入 |
| params       | varchar  | 200  | 否       | 是       | 参数列表            | 用英文逗号隔开，如name,age,money       |
| sql_function | varchar  | 500  | 否       | 否       | sql功能描述         |                                        |
| create_time  | datetime |      | 否       | 否       | 创建时间            |                                        |
| update_time  | datetime |      | 否       | 是       | 修改时间            |                                        |

param

| 字段名         | 类型    | 长度 | 是否主键 | 允许空值 | 含义                 | 备注                         |
| -------------- | ------- | ---- | -------- | -------- | -------------------- | ---------------------------- |
| sn             | int     | 11   | 是       | 否       | 序列号               | 自增                         |
| sqltable_sn    | int     | 11   | 否       | 否       | 对应的sqltable序列号 |                              |
| param_name     | varchar | 100  | 否       | 否       | 参数名               |                              |
| param_type     | varchar | 50   | 否       | 否       | 参数类型             |                              |
| param_function | varchar | 500  | 否       | 是       | 参数功能描述         |                              |
| notes          | varchar | 500  | 否       | 是       | 备注                 |                              |
| rq_rt          | int     | 11   | 否       | 否       | 请求参数或返回参数   | 0表示请求参数，1表示返回参数 |

### 使用示例

| 序号 | url                                                          | 作用                                 | 结果                                                         | 说明                                     |
| ---- | ------------------------------------------------------------ | ------------------------------------ | ------------------------------------------------------------ | ---------------------------------------- |
| 0    | http://127.0.0.1:8080/safeExcuteSql/selectAllSqlTable        | 查询sqltable下所有记录及其对应的参数 | sqltable下所有记录及其对应的参数                             |                                          |
| 1    | http://127.0.0.1:8080/safeExcuteSql/selectSqlTable?sql_code=findUser&&name=found&&money=999&&age=11 | 查询参数对应的某条sqltable           | {<br/>    "sn": 1,<br/>    "sql_CODE": "findUser",<br/>    "sql": "select * from user where name='SIGN_name' and age=SIGN_age and money=SIGN_money",<br/>    "sql_FUNCTION": "查询用户",<br/>    "update_TIME": "2021-03-23T10:44:00.000+00:00",<br/>    "params_LIST": [<br/>        {<br/>            "sn": 1,<br/>            "rq_RT": 0,<br/>            "sqltable_SN": 1,<br/>            "notes": "无",<br/>            "param_TYPE": "字符串",<br/>            "param_FUNCTION": "姓名",<br/>            "param_NAME": "name"<br/>        },<br/>        {<br/>            "sn": 2,<br/>            "rq_RT": 0,<br/>            "sqltable_SN": 1,<br/>            "notes": "年龄区间0~120",<br/>            "param_TYPE": "数字",<br/>            "param_FUNCTION": "年龄",<br/>            "param_NAME": "age"<br/>        },<br/>        {<br/>            "sn": 3,<br/>            "rq_RT": 0,<br/>            "sqltable_SN": 1,<br/>            "notes": "无",<br/>            "param_TYPE": "数字",<br/>            "param_FUNCTION": "资产",<br/>            "param_NAME": "money"<br/>        },<br/>        {<br/>            "sn": 4,<br/>            "rq_RT": 1,<br/>            "sqltable_SN": 1,<br/>            "notes": "无",<br/>            "param_TYPE": "字符串",<br/>            "param_FUNCTION": "性别",<br/>            "param_NAME": "sex"<br/>        }<br/>    ],<br/>    "create_TIME": "2021-03-23T10:43:56.000+00:00",<br/>    "params": "name,age,money"<br/>} |                                          |
| 2    | http://127.0.0.1:8080/safeExcuteSql/excuteSql?sql_code=safeExcute&&name=found&&money=999&&age=11 | 无sql注入情况，能匹配到sql，正常访问 | [<br/>    {<br/>        "name": "found",<br/>        "age": 11,<br/>        "money": 999,<br/>        "sex": "boy",<br/>        "sn": 1<br/>    },<br/>    {<br/>        "name": "found",<br/>        "age": 11,<br/>        "money": 999,<br/>        "sex": "girl",<br/>        "sn": 4<br/>    },<br/>    {<br/>        "name": "found",<br/>        "age": 11,<br/>        "money": 999,<br/>        "sex": "none",<br/>        "sn": 5<br/>    }<br/>] | 能够匹配到sql并正确执行                  |
| 3    | http://127.0.0.1:8080/safeExcuteSql/excuteSql?sql_code=safeuser&username=' or '8'='8 | sql注入，能匹配到sql，异常访问       | []                                                           | 单引号sql注入，能匹配到sql，不能注入成功 |