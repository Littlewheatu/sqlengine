/*
Navicat MySQL Data Transfer

Source Server         : zzxt
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : sqlengine

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2021-03-24 11:11:55
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `sqltable`
-- ----------------------------
DROP TABLE IF EXISTS `sqltable`;
CREATE TABLE `sqltable` (
  `sql_code` varchar(300) NOT NULL DEFAULT '0',
  `sql` varchar(200) CHARACTER SET utf8 NOT NULL DEFAULT ';',
  `sn` int(11) NOT NULL AUTO_INCREMENT,
  `params` varchar(200) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`sn`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of sqltable
-- ----------------------------
INSERT INTO `sqltable` VALUES ('findUser', 'select * from user where name=\'SIGN_name\' and age=SIGN_age and money=SIGN_money', '1', 'name,age,money', '2021-03-23 10:43:56', '2021-03-23 10:44:00');
INSERT INTO `sqltable` VALUES ('findSNByN', 'select sn from user where name=\'SIGN_name\';', '2', 'name', '2021-03-23 10:46:10', '2021-03-23 10:46:13');
INSERT INTO `sqltable` VALUES ('findUser', 'select * from user where name=\'SIGN_name\';', '3', 'name', '2021-03-24 11:04:24', null);

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `name` varchar(100) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `money` int(11) DEFAULT NULL,
  `sex` varchar(10) DEFAULT NULL,
  `sn` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`sn`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('found', '11', '999', 'boy', '1');
INSERT INTO `user` VALUES ('ming', '20', '58', 'girl', '2');
INSERT INTO `user` VALUES ('zhang', '15', '789', 'girl', '3');
INSERT INTO `user` VALUES ('found', '11', '999', 'girl', '4');
INSERT INTO `user` VALUES ('found', '11', '999', 'none', '5');
