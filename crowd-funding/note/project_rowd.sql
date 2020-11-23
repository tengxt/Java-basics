/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50730
Source Host           : localhost:3306
Source Database       : project_rowd

Target Server Type    : MYSQL
Target Server Version : 50730
File Encoding         : 65001

Date: 2020-11-23 17:31:28
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for inner_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `inner_admin_role`;
CREATE TABLE `inner_admin_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of inner_admin_role
-- ----------------------------
INSERT INTO `inner_admin_role` VALUES ('17', '245', '474');
INSERT INTO `inner_admin_role` VALUES ('18', '245', '476');
INSERT INTO `inner_admin_role` VALUES ('19', '246', '475');
INSERT INTO `inner_admin_role` VALUES ('20', '246', '477');
INSERT INTO `inner_admin_role` VALUES ('21', '1', '474');
INSERT INTO `inner_admin_role` VALUES ('22', '1', '476');

-- ----------------------------
-- Table structure for inner_role_auth
-- ----------------------------
DROP TABLE IF EXISTS `inner_role_auth`;
CREATE TABLE `inner_role_auth` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) DEFAULT NULL,
  `auth_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of inner_role_auth
-- ----------------------------
INSERT INTO `inner_role_auth` VALUES ('14', '476', '1');
INSERT INTO `inner_role_auth` VALUES ('15', '476', '8');
INSERT INTO `inner_role_auth` VALUES ('16', '476', '4');
INSERT INTO `inner_role_auth` VALUES ('17', '476', '6');
INSERT INTO `inner_role_auth` VALUES ('18', '477', '1');
INSERT INTO `inner_role_auth` VALUES ('19', '477', '3');
INSERT INTO `inner_role_auth` VALUES ('20', '477', '4');
INSERT INTO `inner_role_auth` VALUES ('21', '477', '5');

-- ----------------------------
-- Table structure for t_admin
-- ----------------------------
DROP TABLE IF EXISTS `t_admin`;
CREATE TABLE `t_admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login_acct` varchar(255) NOT NULL,
  `user_pswd` char(100) NOT NULL,
  `user_name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `create_time` char(19) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_acct` (`login_acct`)
) ENGINE=InnoDB AUTO_INCREMENT=250 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_admin
-- ----------------------------
INSERT INTO `t_admin` VALUES ('1', 'tengxt', '$2a$10$tb01imC4tnz4f0ztFvWhtubn6nloi1MT2BZbjcwqaLtq8t1CzVRZq', '管理员1', 'admin@qq.com', '');
INSERT INTO `t_admin` VALUES ('245', 'adminOperator', '$2a$10$tb01imC4tnz4f0ztFvWhtubn6nloi1MT2BZbjcwqaLtq8t1CzVRZq', 'AAOO', 'aaoo@qq.com', null);
INSERT INTO `t_admin` VALUES ('246', 'roleOperator', '$2a$10$tb01imC4tnz4f0ztFvWhtubn6nloi1MT2BZbjcwqaLtq8t1CzVRZq', 'RROO', 'rroo@qq.com', null);
INSERT INTO `t_admin` VALUES ('247', 'admin01', 'admin01', 'admin01', 'admin01@qq.com', null);
INSERT INTO `t_admin` VALUES ('248', 'admin02', 'admin02', 'admin02', 'admin02@qq.com', null);
INSERT INTO `t_admin` VALUES ('249', 'admin03', 'admin03', 'admin03', 'admin03@qq.com', null);

-- ----------------------------
-- Table structure for t_auth
-- ----------------------------
DROP TABLE IF EXISTS `t_auth`;
CREATE TABLE `t_auth` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `title` varchar(200) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_auth
-- ----------------------------
INSERT INTO `t_auth` VALUES ('1', '', '用户模块', null);
INSERT INTO `t_auth` VALUES ('2', 'user:delete', '删除', '1');
INSERT INTO `t_auth` VALUES ('3', 'user:get', '查询', '1');
INSERT INTO `t_auth` VALUES ('4', '', '角色模块', null);
INSERT INTO `t_auth` VALUES ('5', 'role:delete', '删除', '4');
INSERT INTO `t_auth` VALUES ('6', 'role:get', '查询', '4');
INSERT INTO `t_auth` VALUES ('7', 'role:add', '新增', '4');
INSERT INTO `t_auth` VALUES ('8', 'user:save', '保存', '1');

-- ----------------------------
-- Table structure for t_member
-- ----------------------------
DROP TABLE IF EXISTS `t_member`;
CREATE TABLE `t_member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login_acct` varchar(255) NOT NULL,
  `user_pswd` char(200) NOT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `authstaus` int(4) DEFAULT NULL COMMENT '实名认证状态 0- 未实名认证, 1- 实名认证申请中, 2- 已实名认证',
  `user_type` int(4) DEFAULT NULL COMMENT '0- 个人 , 1- 企业',
  `real_name` varchar(255) DEFAULT NULL,
  `card_num` varchar(255) DEFAULT NULL,
  `acct_type` int(4) DEFAULT NULL COMMENT '0- 企业, 1- 个体, 2- 个人, 3- 政府',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_member
-- ----------------------------
INSERT INTO `t_member` VALUES ('1', 'aaa', 'aaa', 'aaa', 'aaa@qq.com', '0', '0', null, '', '2');
INSERT INTO `t_member` VALUES ('2', 'aaa', 'aaa', 'bbb', 'bbb@qq.com', '0', '0', null, null, '2');

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `icon` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES ('1', null, ' 系统权限菜单', null, 'glyphicon glyphicon-th-list');
INSERT INTO `t_menu` VALUES ('2', '1', ' 控 制 面 板 ', 'main.htm', 'glyphicon glyphicon-dashboard');
INSERT INTO `t_menu` VALUES ('3', '1', '权限管理', null, 'glyphicon glyphicon glyphicon-tasks');
INSERT INTO `t_menu` VALUES ('4', '3', ' 用 户 维 护 ', 'user/index.htm', 'glyphicon glyphicon-user');
INSERT INTO `t_menu` VALUES ('5', '3', ' 角 色 维 护 ', 'role/index.htm', 'glyphicon glyphicon-king');
INSERT INTO `t_menu` VALUES ('6', '3', ' 菜 单 维 护 ', 'permission/index.htm', 'glyphicon glyphicon-lock');
INSERT INTO `t_menu` VALUES ('7', '1', ' 业 务 审 核 ', null, 'glyphicon glyphicon-ok');
INSERT INTO `t_menu` VALUES ('8', '7', ' 实名认证审核', 'auth_cert/index.htm', 'glyphicon glyphicon-check');
INSERT INTO `t_menu` VALUES ('9', '7', ' 广 告 审 核 ', 'auth_adv/index.htm', 'glyphicon glyphicon-check');
INSERT INTO `t_menu` VALUES ('10', '7', ' 项 目 审 核 ', 'auth_project/index.htm', 'glyphicon glyphicon-check');
INSERT INTO `t_menu` VALUES ('11', '1', ' 业 务 管 理 ', null, 'glyphicon glyphicon-th-large');
INSERT INTO `t_menu` VALUES ('12', '11', ' 资 质 维 护 ', 'cert/index.htm', 'glyphicon glyphicon-picture');
INSERT INTO `t_menu` VALUES ('13', '11', ' 分 类 管 理 ', 'certtype/index.htm', 'glyphicon glyphicon-equalizer');
INSERT INTO `t_menu` VALUES ('14', '11', ' 流 程 管 理 ', 'process/index.htm', 'glyphicon glyphicon-random');
INSERT INTO `t_menu` VALUES ('15', '11', ' 广 告 管 理 ', 'advert/index.htm', 'glyphicon glyphicon-hdd');
INSERT INTO `t_menu` VALUES ('16', '11', ' 消 息 模 板 ', 'message/index.htm', 'glyphicon glyphicon-comment');
INSERT INTO `t_menu` VALUES ('17', '11', ' 项 目 分 类 ', 'projectType/index.htm', 'glyphicon glyphicon-list');
INSERT INTO `t_menu` VALUES ('18', '11', ' 项 目 标 签 ', 'tag/index.htm', 'glyphicon glyphicon-tags');
INSERT INTO `t_menu` VALUES ('19', '1', ' 参 数 管 理 ', 'param/index.htm', 'glyphicon glyphicon-list-alt');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=478 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES ('474', '经理');
INSERT INTO `t_role` VALUES ('475', '部长');
INSERT INTO `t_role` VALUES ('476', '经理操作者');
INSERT INTO `t_role` VALUES ('477', '部长操作者');
