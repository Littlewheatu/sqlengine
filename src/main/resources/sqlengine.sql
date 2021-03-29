/*
Navicat MySQL Data Transfer

Source Server         : zzxt
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : sqlengine

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2021-03-29 10:06:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `username` varchar(200) CHARACTER SET utf8 DEFAULT NULL,
  `password` varchar(200) CHARACTER SET utf8 DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('韦小宝', '222');
INSERT INTO `admin` VALUES ('陈近南', '111');
INSERT INTO `admin` VALUES ('方世玉', '2232');
INSERT INTO `admin` VALUES ('单引号\'', '121');
INSERT INTO `admin` VALUES ('单引号', '121');

-- ----------------------------
-- Table structure for `param`
-- ----------------------------
DROP TABLE IF EXISTS `param`;
CREATE TABLE `param` (
  `sn` int(11) NOT NULL AUTO_INCREMENT,
  `sqltable_sn` int(11) NOT NULL,
  `param_name` varchar(200) CHARACTER SET utf8 NOT NULL,
  `param_type` varchar(100) CHARACTER SET utf8 NOT NULL,
  `param_function` varchar(300) CHARACTER SET utf8 NOT NULL,
  `notes` varchar(500) CHARACTER SET utf8 DEFAULT NULL,
  `rq_rt` int(11) NOT NULL,
  PRIMARY KEY (`sn`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=sjis;

-- ----------------------------
-- Records of param
-- ----------------------------
INSERT INTO `param` VALUES ('1', '1', 'name', '字符串', '姓名', '无', '0');
INSERT INTO `param` VALUES ('2', '1', 'age', '数字', '年龄', '年龄区间0~120', '0');
INSERT INTO `param` VALUES ('3', '1', 'money', '数字', '资产', '无', '0');
INSERT INTO `param` VALUES ('4', '1', 'sex', '字符串', '性别', '无', '1');

-- ----------------------------
-- Table structure for `sqltable`
-- ----------------------------
DROP TABLE IF EXISTS `sqltable`;
CREATE TABLE `sqltable` (
  `sql_code` varchar(300) CHARACTER SET utf8 NOT NULL DEFAULT '0',
  `sql` varchar(200) CHARACTER SET utf8 NOT NULL DEFAULT ';',
  `sn` int(11) NOT NULL AUTO_INCREMENT,
  `params` varchar(200) CHARACTER SET utf8 DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `sql_function` varchar(300) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`sn`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of sqltable
-- ----------------------------
INSERT INTO `sqltable` VALUES ('findUser', 'select * from user where name=\'SIGN_name\' and age=SIGN_age and money=SIGN_money', '1', 'name,age,money', '2021-03-23 10:43:56', '2021-03-23 10:44:00', '查询用户');
INSERT INTO `sqltable` VALUES ('findSNByN', 'select sn from user where name=\'SIGN_name\';', '2', 'name', '2021-03-23 10:46:10', '2021-03-23 10:46:13', '查询用户');
INSERT INTO `sqltable` VALUES ('findUser', 'select * from user where name=\'SIGN_name\';', '3', 'name', '2021-03-24 11:04:24', '2021-03-26 18:32:19', '查询用户');
INSERT INTO `sqltable` VALUES ('serchUser', 'select * from user where name=${name} and age=${age} and money=${money};', '4', 'name,age,money', '2021-03-26 20:30:37', '2021-03-26 20:30:44', 'serchUser');
INSERT INTO `sqltable` VALUES ('safeExcute', 'select * from user where name=${name} and age=${age} and money=${money};', '5', 'name,age,money', '2021-03-26 21:59:31', '2021-03-26 21:59:38', 'safe查询');
INSERT INTO `sqltable` VALUES ('login', 'select * from admin where username=${username} and password=${password};', '6', 'username,password', '2021-03-27 16:33:15', '2021-03-27 16:33:32', '登录验证');
INSERT INTO `sqltable` VALUES ('unsafelogin', 'select * from admin where username=\'${username}\' and password=\'${password}\';', '7', 'username,password', '2021-03-27 16:44:11', '2021-03-27 16:44:14', '不安全登录');
INSERT INTO `sqltable` VALUES ('unsafeuser', 'select * from admin where username=\'${username}\';', '8', 'username', '2021-03-27 16:56:58', '2021-03-27 17:00:34', '查询用户');
INSERT INTO `sqltable` VALUES ('safeuser', 'select * from admin where username=${username};', '9', 'username', '2021-03-29 09:23:04', '2021-03-29 09:23:08', '仅判断用户名登录能否被注入、');

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `name` varchar(100) CHARACTER SET utf8 DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `money` int(11) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `sn` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`sn`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('found', '11', '999', 'boy', '1');
INSERT INTO `user` VALUES ('ming', '20', '58', 'girl', '2');
INSERT INTO `user` VALUES ('found', '11', '999', 'girl', '4');
INSERT INTO `user` VALUES ('found', '11', '999', 'none', '5');
INSERT INTO `user` VALUES ('baby', '55', '999999', 'none', '6');
