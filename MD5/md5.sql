/*
Navicat MySQL Data Transfer

Source Server         : 172.18.85.77_3306
Source Server Version : 50045
Source Host           : 172.18.85.77:3306
Source Database       : md5

Target Server Type    : MYSQL
Target Server Version : 50045
File Encoding         : 65001

Date: 2013-06-18 11:55:25
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `information`
-- ----------------------------
DROP TABLE IF EXISTS `information`;
CREATE TABLE `information` (
  `id` int(11) NOT NULL auto_increment,
  `username` varchar(20) default NULL,
  `password` varchar(50) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of information
-- ----------------------------
INSERT INTO `information` VALUES ('1', '1', 'c4ca4238a0b923820dcc509a6f75849b');
