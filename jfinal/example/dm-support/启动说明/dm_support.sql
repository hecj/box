/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50625
Source Host           : localhost:3306
Source Database       : dm_support

Target Server Type    : MYSQL
Target Server Version : 50625
File Encoding         : 65001

Date: 2015-07-23 16:13:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for dbs
-- ----------------------------
DROP TABLE IF EXISTS `dbs`;
CREATE TABLE `dbs` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(1000) DEFAULT NULL COMMENT '数据库',
  `u` varchar(100) DEFAULT NULL COMMENT '用户名',
  `p` varchar(100) DEFAULT NULL COMMENT '密码',
  `uid` bigint(20) DEFAULT NULL COMMENT '创建者id',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='数据源管理';

-- ----------------------------
-- Records of dbs
-- ----------------------------
INSERT INTO `dbs` VALUES ('1', 'jdbc:mysql://db.duomeidai.com:3306/duomeidai?characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull', 'root', 'dmtz@2014', null);

-- ----------------------------
-- Table structure for kc
-- ----------------------------
DROP TABLE IF EXISTS `kc`;
CREATE TABLE `kc` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `userid` bigint(20) DEFAULT NULL COMMENT '用户id',
  `investamountbegin` decimal(18,2) DEFAULT NULL COMMENT '投资金额起',
  `investamountend` decimal(18,2) DEFAULT NULL COMMENT '投资金额止',
  `earningsratebegin` decimal(18,2) DEFAULT NULL COMMENT '年化收益率起',
  `earningsrateend` decimal(18,2) DEFAULT NULL COMMENT '年化收益率止',
  `deadlinebegin` int(11) DEFAULT NULL COMMENT '借款期限起',
  `deadlineend` int(11) DEFAULT NULL COMMENT '借款期限止',
  `projecttype` varchar(255) DEFAULT NULL COMMENT '项目类型',
  `isrebate` int(1) DEFAULT NULL COMMENT '是否使用返利金额(0:是，1:否)',
  `iscashcoupon` int(1) DEFAULT NULL COMMENT '是否使用现金卷(0:是，1:否)',
  `status` int(1) DEFAULT NULL COMMENT '启用状态(0:启用,1:关闭)',
  `createat` bigint(20) DEFAULT NULL COMMENT '创建时间',
  `updateat` bigint(20) DEFAULT NULL COMMENT '修改时间',
  `lasttime` bigint(20) DEFAULT NULL COMMENT '最后一次自动投标时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_autoinvest_userid` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of kc
-- ----------------------------

-- ----------------------------
-- Table structure for m
-- ----------------------------
DROP TABLE IF EXISTS `m`;
CREATE TABLE `m` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `n` varchar(100) DEFAULT NULL COMMENT '名称',
  `aurl` varchar(300) DEFAULT NULL COMMENT 'action路径',
  `uid` bigint(20) DEFAULT NULL COMMENT '创建者id',
  `pid` bigint(20) DEFAULT '0' COMMENT '父菜单id',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=16 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='菜单';

-- ----------------------------
-- Records of m
-- ----------------------------
INSERT INTO `m` VALUES ('1', '数据源管理', '/dbs', null, '0');
INSERT INTO `m` VALUES ('2', '用户', '/u', null, '0');
INSERT INTO `m` VALUES ('3', '菜单', '/m', null, '0');
INSERT INTO `m` VALUES ('4', '查询关联字段', '/qo', null, '0');
INSERT INTO `m` VALUES ('5', '角色', '/r', null, '0');
INSERT INTO `m` VALUES ('6', '查询管理', '/q', null, '0');
INSERT INTO `m` VALUES ('7', '通用查询', '/query', null, '0');
INSERT INTO `m` VALUES ('8', '统计查询', '/statics', null, '0');
INSERT INTO `m` VALUES ('9', '测试菜单1', '/test1', null, '0');
INSERT INTO `m` VALUES ('11', '测试子菜单1', '/test1/test1', null, '9');
INSERT INTO `m` VALUES ('12', '公司注册信息', '/company/', null, '0');
INSERT INTO `m` VALUES ('13', '修改公司信息', '/company/edit', null, '12');
INSERT INTO `m` VALUES ('14', '保存公司信息', '/company/save', null, '12');
INSERT INTO `m` VALUES ('15', '删除公司信息', '/company/delete', null, '12');

-- ----------------------------
-- Table structure for msg
-- ----------------------------
DROP TABLE IF EXISTS `msg`;
CREATE TABLE `msg` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `msg` varchar(4000) DEFAULT NULL COMMENT '内容',
  `fid` bigint(20) DEFAULT NULL COMMENT '发送者id',
  `fu` varchar(100) DEFAULT NULL COMMENT '发送者姓名',
  `tid` bigint(20) DEFAULT NULL COMMENT '接收者id',
  `tu` varchar(100) DEFAULT NULL COMMENT '接受者姓名',
  `ft` datetime DEFAULT NULL COMMENT '发送时间',
  `tt` datetime DEFAULT NULL COMMENT '读取时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=55 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='消息';

-- ----------------------------
-- Records of msg
-- ----------------------------
INSERT INTO `msg` VALUES ('1', 'sdfasdfsdf', '1', 'asdfs', '1', 'asdfsdf', null, '2015-07-07 17:43:22');
INSERT INTO `msg` VALUES ('2', 'sdfasdfsdf', '1', 'asdfs', '1', 'asdfsdf', null, '2015-07-07 17:43:22');
INSERT INTO `msg` VALUES ('3', '阿斯顿发斯蒂芬', '1', 'admin', null, null, '2015-07-02 11:36:34', null);
INSERT INTO `msg` VALUES ('4', null, '1', 'admin', null, null, '2015-07-02 11:37:55', null);
INSERT INTO `msg` VALUES ('5', '阿斯顿发撒旦', '1', 'admin', null, null, '2015-07-02 11:37:58', null);
INSERT INTO `msg` VALUES ('6', '阿斯顿发撒旦', '1', 'admin', null, null, '2015-07-02 11:42:49', null);
INSERT INTO `msg` VALUES ('7', '阿斯顿发撒旦', '1', 'admin', null, null, '2015-07-02 11:47:37', null);
INSERT INTO `msg` VALUES ('8', '阿斯顿发生', '1', 'admin', null, null, '2015-07-02 11:47:50', null);
INSERT INTO `msg` VALUES ('9', 'asdfasdf', '1', 'admin', null, null, '2015-07-02 11:49:47', null);
INSERT INTO `msg` VALUES ('10', '阿斯蒂芬', '1', 'admin', null, null, '2015-07-02 11:55:03', null);
INSERT INTO `msg` VALUES ('11', '阿斯顿发大水', '1', 'admin', null, null, '2015-07-02 11:59:14', null);
INSERT INTO `msg` VALUES ('12', '爱的色放', '1', 'admin', null, null, '2015-07-02 12:05:11', null);
INSERT INTO `msg` VALUES ('13', '阿斯蒂芬', '1', 'admin', null, null, '2015-07-02 12:13:33', null);
INSERT INTO `msg` VALUES ('14', '阿斯蒂芬', '1', 'admin', null, null, '2015-07-02 12:20:52', null);
INSERT INTO `msg` VALUES ('15', null, '1', 'admin', null, null, '2015-07-02 14:17:53', null);
INSERT INTO `msg` VALUES ('16', null, '1', 'admin', null, null, '2015-07-02 14:33:37', null);
INSERT INTO `msg` VALUES ('17', null, '1', 'admin', null, null, '2015-07-02 14:33:45', null);
INSERT INTO `msg` VALUES ('18', null, '1', 'admin', null, null, '2015-07-02 14:52:52', null);
INSERT INTO `msg` VALUES ('19', '阿斯顿发顺丰', '1', 'admin', '6', '开发1', '2015-07-02 14:53:36', null);
INSERT INTO `msg` VALUES ('20', '阿斯蒂芬地方', '1', 'admin', '17', 'admin', '2015-07-02 15:35:56', null);
INSERT INTO `msg` VALUES ('21', 'VVV', '1', 'admin', '20', 'admin', '2015-07-02 15:36:05', null);
INSERT INTO `msg` VALUES ('22', '阿斯顿发斯蒂芬', '4', '客服1', '1', 'admin', '2015-07-02 15:43:09', '2015-07-07 17:43:22');
INSERT INTO `msg` VALUES ('23', 'hello', '4', '客服1', '1', 'admin', '2015-07-02 16:23:33', '2015-07-07 17:43:22');
INSERT INTO `msg` VALUES ('24', 'hello', '4', '客服1', '1', 'admin', '2015-07-02 16:29:33', '2015-07-07 17:43:22');
INSERT INTO `msg` VALUES ('25', 'asd', '4', '客服1', '1', 'admin', '2015-07-02 16:30:45', '2015-07-07 17:43:22');
INSERT INTO `msg` VALUES ('26', 'vvvvvvvvvv', '4', '客服1', '1', 'admin', '2015-07-02 16:31:05', '2015-07-07 17:43:22');
INSERT INTO `msg` VALUES ('27', '阿斯顿vsd', '4', '客服1', '1', 'admin', '2015-07-02 16:32:54', '2015-07-07 17:43:22');
INSERT INTO `msg` VALUES ('28', '：）', '4', '客服1', '1', 'admin', '2015-07-02 16:35:05', '2015-07-07 17:43:22');
INSERT INTO `msg` VALUES ('29', '弄', '4', '客服1', '1', 'admin', '2015-07-02 16:36:21', '2015-07-07 17:43:22');
INSERT INTO `msg` VALUES ('30', '有 ken\r\n\r\n', '4', '客服1', '1', 'admin', '2015-07-02 16:37:07', '2015-07-07 17:43:22');
INSERT INTO `msg` VALUES ('31', '什么啊\r\n', '4', '客服1', '1', 'admin', '2015-07-02 16:43:46', '2015-07-07 17:43:22');
INSERT INTO `msg` VALUES ('32', '阿斯顿发生', '4', '客服1', '1', 'admin', '2015-07-02 16:44:20', '2015-07-07 17:43:22');
INSERT INTO `msg` VALUES ('33', '好吧', '4', '客服1', '1', 'admin', '2015-07-02 16:45:27', '2015-07-07 17:43:22');
INSERT INTO `msg` VALUES ('34', '阿斯顿发撒旦', '4', '客服1', '1', 'admin', '2015-07-02 16:46:12', '2015-07-07 17:43:22');
INSERT INTO `msg` VALUES ('35', '四大发大水', '4', '客服1', '1', 'admin', '2015-07-02 16:47:59', '2015-07-07 17:43:22');
INSERT INTO `msg` VALUES ('36', '阿斯顿发生', '1', 'admin', '35', '客服1', '2015-07-03 09:23:02', null);
INSERT INTO `msg` VALUES ('37', '爱的色放', '1', 'admin', '4', '客服1', '2015-07-03 09:27:54', '2015-07-07 11:42:06');
INSERT INTO `msg` VALUES ('38', '哦哦 好吧', '4', '客服1', '1', 'admin', '2015-07-03 09:28:12', '2015-07-07 17:43:22');
INSERT INTO `msg` VALUES ('39', 'asdfsd ', '1', 'admin', '4', '客服1', '2015-07-03 09:28:49', '2015-07-07 11:42:06');
INSERT INTO `msg` VALUES ('40', 'vvvv', '1', 'admin', '4', '客服1', '2015-07-03 09:28:53', '2015-07-07 11:42:06');
INSERT INTO `msg` VALUES ('41', '阿斯顿vasd', '4', '客服1', '1', 'admin', '2015-07-03 09:29:11', '2015-07-07 17:43:22');
INSERT INTO `msg` VALUES ('42', '阿斯顿发斯蒂芬', '4', '客服1', '1', 'admin', '2015-07-03 10:13:58', '2015-07-07 17:43:22');
INSERT INTO `msg` VALUES ('43', '阿斯顿发撒旦', '4', '客服1', '1', 'admin', '2015-07-03 10:19:02', '2015-07-07 17:43:22');
INSERT INTO `msg` VALUES ('44', '阿斯顿发撒旦', '4', '客服1', '1', 'admin', '2015-07-03 10:19:09', '2015-07-07 17:43:22');
INSERT INTO `msg` VALUES ('45', 'asdfas ', '4', '客服1', '4', '客服1', '2015-07-03 10:41:46', '2015-07-07 11:42:06');
INSERT INTO `msg` VALUES ('46', 'afasdfasdfasdfasdf', '4', '客服1', '4', '客服1', '2015-07-03 10:41:55', '2015-07-07 11:42:06');
INSERT INTO `msg` VALUES ('47', 'asdfasdf', '4', '客服1', '1', 'admin', '2015-07-03 10:42:13', '2015-07-07 17:43:22');
INSERT INTO `msg` VALUES ('48', '嗨  有没有人在线啊', '1', 'admin', '4', '客服1', '2015-07-03 15:09:55', '2015-07-07 11:42:06');
INSERT INTO `msg` VALUES ('49', '有啊', '4', '客服1', '1', 'admin', '2015-07-03 15:10:25', '2015-07-07 17:43:22');
INSERT INTO `msg` VALUES ('50', '谁啊？', '1', 'admin', '4', '客服1', '2015-07-03 15:10:43', '2015-07-07 11:42:06');
INSERT INTO `msg` VALUES ('51', '哥', '4', '客服1', '1', 'admin', '2015-07-03 15:11:05', '2015-07-07 17:43:22');
INSERT INTO `msg` VALUES ('52', '你不要吓我', '1', 'admin', '4', '客服1', '2015-07-03 15:12:38', '2015-07-07 11:42:06');
INSERT INTO `msg` VALUES ('53', '枯', '4', '客服1', '1', 'admin', '2015-07-07 11:41:59', '2015-07-07 17:43:22');
INSERT INTO `msg` VALUES ('54', '仍', '4', '客服1', '1', 'admin', '2015-07-07 11:42:06', '2015-07-07 17:43:22');

-- ----------------------------
-- Table structure for ol
-- ----------------------------
DROP TABLE IF EXISTS `ol`;
CREATE TABLE `ol` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` varchar(100) DEFAULT NULL COMMENT '操作后缀',
  `m` varchar(200) DEFAULT NULL COMMENT '模块前缀',
  `mn` varchar(200) DEFAULT NULL COMMENT '模块名称',
  `oids` varchar(1000) DEFAULT NULL COMMENT '对象id',
  `on` varchar(200) DEFAULT NULL COMMENT '对象名称',
  `tm` datetime DEFAULT NULL COMMENT '执行时间',
  `endtm` datetime DEFAULT NULL COMMENT '结束时间',
  `msg` varchar(1000) DEFAULT NULL COMMENT '执行结果信息内容',
  `uid` bigint(20) DEFAULT NULL COMMENT '操作人id',
  `un` varchar(200) DEFAULT NULL COMMENT '操作人账号',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=234 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='操作日志';

-- ----------------------------
-- Records of ol
-- ----------------------------
INSERT INTO `ol` VALUES ('30', 'staticex', 'query', '信息查询', '4', null, '2015-07-01 14:08:41', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('29', 'staticex', 'query', '信息查询', '4', null, '2015-07-01 14:08:38', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('31', 'logout', '', '主模块', '1', 'admin', '2015-07-01 14:08:43', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('32', 'dologin', '', '主模块', null, 'admin', '2015-07-01 14:08:48', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('33', 'dologin', '', '主模块', null, 'admin', '2015-07-01 15:15:34', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('34', 'dologin', '', '主模块', null, 'admin', '2015-07-01 16:00:07', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('35', 'delete', 'u', '用户', '5', null, '2015-07-01 16:19:49', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('36', 'save', 'u', '用户', null, null, '2015-07-01 16:38:43', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('37', 'save', 'u', '用户', null, null, '2015-07-01 16:39:15', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('38', 'staticex', 'query', '信息查询', '4', null, '2015-07-01 16:47:25', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('39', 'staticex', 'query', '信息查询', '4', null, '2015-07-01 16:47:28', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('40', 'staticex', 'query', '信息查询', '4', null, '2015-07-01 16:47:33', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('41', 'staticex', 'query', '信息查询', '4', null, '2015-07-01 16:47:34', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('42', 'ex', 'query', '信息查询', '1', null, '2015-07-01 16:48:15', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('43', 'ex', 'query', '信息查询', '2', null, '2015-07-01 16:48:30', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('44', 'logout', '', '主模块', '1', 'admin', '2015-07-01 16:51:35', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('45', 'dologin', '', '主模块', null, '客服1', '2015-07-01 16:51:40', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('46', 'staticex', 'query', '信息查询', '4', null, '2015-07-01 16:51:56', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('47', 'dologin', '', '主模块', null, 'admin', '2015-07-01 17:38:30', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('48', 'dologin', '', '主模块', null, 'admin', '2015-07-01 18:10:00', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('49', 'dologin', '', '主模块', null, 'admin', '2015-07-02 09:42:24', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('50', 'dologin', '', '主模块', null, 'admin', '2015-07-02 09:44:06', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('51', 'dologin', '', '主模块', null, 'admin', '2015-07-02 10:02:22', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('52', 'dologin', '', '主模块', null, 'admin', '2015-07-02 11:34:04', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('53', 'dologin', '', '主模块', null, 'admin', '2015-07-02 11:59:07', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('54', 'dologin', '', '主模块', null, 'admin', '2015-07-02 13:55:04', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('55', 'dologin', '', '主模块', null, 'admin', '2015-07-02 15:37:48', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('56', 'dologin', '', '主模块', null, '客服1', '2015-07-02 15:42:19', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('57', 'dologin', '', '主模块', null, 'admin', '2015-07-02 15:54:37', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('58', 'dologin', '', '主模块', null, 'admin', '2015-07-02 16:06:32', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('59', 'logout', '', '主模块', '1', 'admin', '2015-07-02 16:23:07', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('60', 'dologin', '', '主模块', null, '客服1', '2015-07-02 16:23:13', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('61', 'logout', '', '主模块', '1', 'admin', '2015-07-02 16:34:25', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('62', 'logout', '', '主模块', '4', '客服1', '2015-07-02 16:34:28', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('63', 'dologin', '', '主模块', null, 'admin', '2015-07-02 16:34:34', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('64', 'dologin', '', '主模块', null, '客服1', '2015-07-02 16:34:38', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('65', 'dologin', '', '主模块', null, 'admin', '2015-07-02 17:57:56', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('66', 'dologin', '', '主模块', null, 'admin', '2015-07-03 09:22:45', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('67', 'logout', '', '主模块', '1', 'admin', '2015-07-03 09:23:04', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('68', 'dologin', '', '主模块', null, '客服1', '2015-07-03 09:23:07', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('69', 'logout', '', '主模块', '4', '客服1', '2015-07-03 09:25:06', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('70', 'dologin', '', '主模块', null, 'admin', '2015-07-03 09:25:11', null, null, null, null);
INSERT INTO `ol` VALUES ('71', 'dologin', '', '主模块', null, 'admin', '2015-07-03 09:25:17', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('72', 'logout', '', '主模块', '1', 'admin', '2015-07-03 09:27:55', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('73', 'dologin', '', '主模块', null, '客服1', '2015-07-03 09:27:59', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('74', 'dologin', '', '主模块', null, 'admin', '2015-07-03 09:28:30', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('75', 'dologin', '', '主模块', null, 'admin', '2015-07-03 14:52:34', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('76', 'staticex', 'query', '信息查询', '4', null, '2015-07-03 14:52:43', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('77', 'staticex', 'query', '信息查询', '4', null, '2015-07-03 14:52:47', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('78', 'dologin', '', '主模块', null, 'admin', '2015-07-03 14:56:43', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('79', 'staticex', 'query', '信息查询', '4', null, '2015-07-03 14:56:52', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('80', 'dologin', '', '主模块', null, 'admin', '2015-07-03 14:59:16', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('81', 'staticex', 'query', '信息查询', '4', null, '2015-07-03 14:59:21', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('82', 'staticex', 'query', '信息查询', '4', null, '2015-07-03 14:59:54', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('83', 'staticex', 'query', '信息查询', '4', null, '2015-07-03 15:00:02', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('84', 'staticex', 'query', '信息查询', '4', null, '2015-07-03 15:00:07', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('85', 'dologin', '', '主模块', null, 'admin', '2015-07-03 15:07:11', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('86', 'staticex', 'query', '信息查询', '4', null, '2015-07-03 15:07:18', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('87', 'staticex', 'query', '信息查询', '4', null, '2015-07-03 15:07:19', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('88', 'staticex', 'query', '信息查询', '4', null, '2015-07-03 15:07:23', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('89', 'staticex', 'query', '信息查询', '4', null, '2015-07-03 15:08:15', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('90', 'dologin', '', '主模块', null, 'magenming', '2015-07-03 15:09:39', null, null, null, null);
INSERT INTO `ol` VALUES ('91', 'dologin', '', '主模块', null, '客服1', '2015-07-03 15:09:47', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('92', 'dologin', '', '主模块', null, '客服1', '2015-07-03 15:09:57', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('93', 'dologin', '', '主模块', null, '客服1', '2015-07-03 15:10:08', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('94', 'dologin', '', '主模块', null, '客服1', '2015-07-03 15:11:22', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('95', 'dologin', '', '主模块', null, 'admin', '2015-07-03 15:11:33', null, null, null, null);
INSERT INTO `ol` VALUES ('96', 'dologin', '', '主模块', null, '客服1', '2015-07-03 15:11:35', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('97', 'ex', 'query', '信息查询', '2', null, '2015-07-03 15:11:44', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('98', 'dologin', '', '主模块', null, '客服1', '2015-07-03 15:11:45', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('99', 'ex', 'query', '信息查询', '1', null, '2015-07-03 15:12:04', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('100', 'staticex', 'query', '信息查询', '4', null, '2015-07-03 15:12:27', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('101', 'staticex', 'query', '信息查询', '4', null, '2015-07-03 15:12:36', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('102', 'staticex', 'query', '信息查询', '4', null, '2015-07-03 15:12:37', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('103', 'staticex', 'query', '信息查询', '4', null, '2015-07-03 15:12:42', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('104', 'ex', 'query', '信息查询', '1', null, '2015-07-03 15:14:07', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('105', 'logout', '', '主模块', '4', '客服1', '2015-07-03 15:14:25', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('106', 'dologin', '', '主模块', null, 'admin', '2015-07-03 15:14:33', null, null, null, null);
INSERT INTO `ol` VALUES ('107', 'dologin', '', '主模块', null, 'admin', '2015-07-03 15:14:41', null, null, null, null);
INSERT INTO `ol` VALUES ('108', 'dologin', '', '主模块', null, 'admin', '2015-07-03 15:15:00', null, null, null, null);
INSERT INTO `ol` VALUES ('109', 'logout', '', '主模块', '1', 'admin', '2015-07-03 15:15:07', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('110', 'dologin', '', '主模块', null, 'admin', '2015-07-03 15:15:12', null, null, null, null);
INSERT INTO `ol` VALUES ('111', 'dologin', '', '主模块', null, 'admin', '2015-07-03 15:15:24', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('112', 'logout', '', '主模块', '4', '客服1', '2015-07-03 15:15:55', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('113', 'dologin', '', '主模块', null, 'admin', '2015-07-03 15:16:03', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('114', 'dologin', '', '主模块', null, 'admin', '2015-07-03 15:16:20', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('115', 'logout', '', '主模块', '1', 'admin', '2015-07-03 15:17:26', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('116', 'ex', 'query', '信息查询', '3', null, '2015-07-03 15:19:50', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('117', 'logout', '', '主模块', '1', 'admin', '2015-07-03 15:25:53', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('118', 'dologin', '', '主模块', null, '客服1', '2015-07-03 15:26:00', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('119', 'ex', 'query', '信息查询', '2', null, '2015-07-03 15:26:35', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('120', 'logout', '', '主模块', '4', '客服1', '2015-07-03 15:26:54', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('121', 'dologin', '', '主模块', null, 'admin', '2015-07-03 15:26:56', null, null, null, null);
INSERT INTO `ol` VALUES ('122', 'dologin', '', '主模块', null, 'admin', '2015-07-03 15:27:02', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('123', 'ex', 'query', '信息查询', '2', null, '2015-07-03 15:31:18', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('124', 'ex', 'query', '信息查询', '2', null, '2015-07-03 15:31:32', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('125', 'ex', 'query', '信息查询', '1', null, '2015-07-03 15:31:45', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('126', 'ex', 'query', '信息查询', '1', null, '2015-07-03 15:31:46', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('127', 'ex', 'query', '信息查询', '1', null, '2015-07-03 15:31:47', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('128', 'ex', 'query', '信息查询', '1', null, '2015-07-03 15:31:47', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('129', 'ex', 'query', '信息查询', '1', null, '2015-07-03 15:31:47', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('130', 'staticex', 'query', '信息查询', '4', null, '2015-07-03 15:32:59', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('131', 'staticex', 'query', '信息查询', '4', null, '2015-07-03 15:33:22', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('132', 'dologin', '', '主模块', null, 'admin', '2015-07-03 15:34:08', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('133', 'staticex', 'query', '信息查询', '4', null, '2015-07-03 15:36:42', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('134', 'staticex', 'query', '信息查询', '4', null, '2015-07-03 15:37:08', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('135', 'staticex', 'query', '信息查询', '4', null, '2015-07-03 15:37:11', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('136', 'staticex', 'query', '信息查询', '4', null, '2015-07-03 15:37:14', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('137', 'dologin', '', '主模块', null, 'admin', '2015-07-03 15:43:22', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('138', 'staticex', 'query', '信息查询', '4', null, '2015-07-03 17:17:32', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('139', 'staticex', 'query', '信息查询', '4', null, '2015-07-03 17:17:35', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('140', 'staticex', 'query', '信息查询', '4', null, '2015-07-03 17:17:36', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('141', 'staticex', 'query', '信息查询', '4', null, '2015-07-03 17:17:40', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('142', 'dologin', '', '主模块', null, 'admin', '2015-07-06 14:26:36', null, null, null, null);
INSERT INTO `ol` VALUES ('143', 'dologin', '', '主模块', null, 'admin', '2015-07-06 14:26:41', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('144', 'staticex', 'query', '信息查询', '4', null, '2015-07-06 14:26:52', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('145', 'staticex', 'query', '信息查询', '4', null, '2015-07-06 14:26:53', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('146', 'dologin', '', '主模块', null, 'admin', '2015-07-07 11:34:40', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('147', 'ex', 'query', '信息查询', '1', null, '2015-07-07 11:37:20', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('148', 'ex', 'query', '信息查询', '1', null, '2015-07-07 11:37:24', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('149', 'ex', 'query', '信息查询', '3', null, '2015-07-07 11:37:28', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('150', 'ex', 'query', '信息查询', '3', null, '2015-07-07 11:37:28', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('151', 'staticex', 'query', '信息查询', '4', null, '2015-07-07 11:38:02', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('152', 'staticex', 'query', '信息查询', '4', null, '2015-07-07 11:38:05', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('153', 'logout', '', '主模块', '1', 'admin', '2015-07-07 11:40:44', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('154', 'dologin', '', '主模块', null, '客服1', '2015-07-07 11:41:20', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('155', 'staticex', 'query', '信息查询', '4', null, '2015-07-07 11:41:38', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('156', 'logout', '', '主模块', '4', '客服1', '2015-07-07 11:42:10', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('157', 'dologin', '', '主模块', null, 'admin', '2015-07-07 11:42:24', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('158', 'dologin', '', '主模块', null, 'admin', '2015-07-07 14:57:53', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('159', 'dologin', '', '主模块', null, 'admin', '2015-07-07 16:49:25', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('160', 'logout', '', '主模块', '1', 'admin', '2015-07-07 16:49:30', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('161', 'dologin', '', '主模块', null, '客服01', '2015-07-07 16:49:40', null, null, null, null);
INSERT INTO `ol` VALUES ('162', 'dologin', '', '主模块', null, '客服1', '2015-07-07 16:49:47', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('163', 'logout', '', '主模块', '4', '客服1', '2015-07-07 17:04:28', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('164', 'dologin', '', '主模块', null, 'admin', '2015-07-07 17:04:29', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('165', 'save', 'm', '菜单', null, null, '2015-07-07 17:04:50', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('166', 'logout', '', '主模块', '1', 'admin', '2015-07-07 17:05:32', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('167', 'dologin', '', '主模块', null, 'admin', '2015-07-07 17:05:34', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('168', 'logout', '', '主模块', '1', 'admin', '2015-07-07 17:08:52', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('169', 'dologin', '', '主模块', null, 'admin', '2015-07-07 17:08:53', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('170', 'dologin', '', '主模块', null, 'admin', '2015-07-07 17:17:50', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('171', 'logout', '', '主模块', '1', 'admin', '2015-07-07 17:33:30', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('172', 'dologin', '', '主模块', null, 'admin', '2015-07-07 17:33:31', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('173', 'logout', '', '主模块', '1', 'admin', '2015-07-07 17:34:03', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('174', 'dologin', '', '主模块', null, '客服1', '2015-07-07 17:34:10', null, null, '4', '客服1');
INSERT INTO `ol` VALUES ('175', 'dologin', '', '主模块', null, 'admin', '2015-07-07 17:35:59', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('176', 'logout', '', '主模块', '1', 'admin', '2015-07-07 17:43:24', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('177', 'dologin', '', '主模块', null, 'admin', '2015-07-07 17:43:25', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('178', 'dologin', '', '主模块', null, 'wangxuhui', '2015-07-13 11:14:54', null, null, null, null);
INSERT INTO `ol` VALUES ('179', 'dologin', '', '主模块', null, 'admin', '2015-07-13 11:15:03', null, null, null, null);
INSERT INTO `ol` VALUES ('180', 'dologin', '', '主模块', null, 'admin', '2015-07-13 11:15:09', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('181', 'dologin', '', '主模块', null, 'admin', '2015-07-13 12:32:32', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('182', 'dologin', '', '主模块', null, 'admin', '2015-07-13 12:36:38', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('183', 'dologin', '', '主模块', null, 'admin', '2015-07-13 17:56:33', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('184', 'dologin', '', '主模块', null, 'admin', '2015-07-15 10:14:37', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('185', 'dologin', '', '主模块', null, 'admin', '2015-07-15 12:12:06', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('186', 'dologin', '', '主模块', null, 'admin', '2015-07-15 15:29:57', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('187', 'dologin', '', '主模块', null, 'admin', '2015-07-15 15:48:41', null, null, null, null);
INSERT INTO `ol` VALUES ('188', 'dologin', '', '主模块', null, 'admin', '2015-07-15 16:18:52', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('189', 'dologin', '', '主模块', null, 'admin', '2015-07-15 17:05:58', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('190', 'staticex', 'query', '信息查询', '4', null, '2015-07-15 17:06:17', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('191', 'staticex', 'query', '信息查询', '4', null, '2015-07-15 17:06:23', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('192', 'staticex', 'query', '信息查询', '4', null, '2015-07-15 17:06:27', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('193', 'dologin', '', '主模块', null, 'admin', '2015-07-15 17:08:48', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('194', 'staticex', 'query', '信息查询', '4', null, '2015-07-15 17:09:23', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('195', 'staticex', 'query', '信息查询', '4', null, '2015-07-15 17:09:26', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('196', 'staticex', 'query', '信息查询', '4', null, '2015-07-15 17:09:26', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('197', 'staticex', 'query', '信息查询', '4', null, '2015-07-15 17:09:27', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('198', 'staticex', 'query', '信息查询', '4', null, '2015-07-15 17:09:27', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('199', 'staticex', 'query', '信息查询', '4', null, '2015-07-15 17:09:27', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('200', 'staticex', 'query', '信息查询', '4', null, '2015-07-15 17:09:27', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('201', 'staticex', 'query', '信息查询', '4', null, '2015-07-15 17:12:39', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('202', 'staticex', 'query', '信息查询', '4', null, '2015-07-15 17:12:46', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('203', 'staticex', 'query', '信息查询', '4', null, '2015-07-15 17:12:46', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('204', 'staticex', 'query', '信息查询', '4', null, '2015-07-15 17:12:46', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('205', 'staticex', 'query', '信息查询', '4', null, '2015-07-15 17:12:46', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('206', 'staticex', 'query', '信息查询', '4', null, '2015-07-15 17:12:47', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('207', 'staticex', 'query', '信息查询', '4', null, '2015-07-15 17:13:03', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('208', 'staticex', 'query', '信息查询', '4', null, '2015-07-15 17:13:03', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('209', 'staticex', 'query', '信息查询', '4', null, '2015-07-15 17:13:03', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('210', 'staticex', 'query', '信息查询', '4', null, '2015-07-15 17:13:04', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('211', 'staticex', 'query', '信息查询', '4', null, '2015-07-15 17:13:04', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('212', 'staticex', 'query', '信息查询', '4', null, '2015-07-15 17:13:04', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('213', 'staticex', 'query', '信息查询', '4', null, '2015-07-15 17:13:04', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('214', 'staticex', 'query', '信息查询', '4', null, '2015-07-15 17:13:04', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('215', 'dologin', '', '主模块', null, 'admin', '2015-07-15 17:38:41', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('216', 'staticex', 'query', '信息查询', '4', null, '2015-07-15 17:39:01', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('217', 'staticex', 'query', '信息查询', '4', null, '2015-07-15 17:42:42', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('218', 'dologin', '', '主模块', null, 'admin', '2015-07-15 17:44:27', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('219', 'dologin', '', '主模块', null, 'admin', '2015-07-15 17:53:45', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('220', 'dologin', '', '主模块', null, 'admin', '2015-07-15 18:09:41', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('221', 'dologin', '', '主模块', null, 'admin', '2015-07-16 09:32:40', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('222', 'dologin', '', '主模块', null, 'admin', '2015-07-16 09:34:11', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('223', 'dologin', '', '主模块', null, 'admin', '2015-07-16 09:35:11', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('224', 'dologin', '', '主模块', null, 'admin', '2015-07-16 09:47:38', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('225', 'dologin', '', '主模块', null, 'admin', '2015-07-16 10:44:35', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('226', 'dologin', '', '主模块', null, 'admin', '2015-07-16 10:49:18', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('227', 'dologin', '', '主模块', null, 'admin', '2015-07-16 10:59:08', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('228', 'dologin', '', '主模块', null, 'admin', '2015-07-16 11:55:55', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('229', 'dologin', '', '主模块', null, 'admin', '2015-07-16 12:29:36', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('230', 'dologin', '', '主模块', null, 'admin', '2015-07-16 15:12:50', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('231', 'dologin', '', '主模块', null, 'admin', '2015-07-17 09:04:01', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('232', 'dologin', '', '主模块', null, 'admin', '2015-07-17 09:54:04', null, null, '1', 'admin');
INSERT INTO `ol` VALUES ('233', 'dologin', '', '主模块', null, 'admin', '2015-07-23 16:12:55', null, null, '1', 'admin');

-- ----------------------------
-- Table structure for q
-- ----------------------------
DROP TABLE IF EXISTS `q`;
CREATE TABLE `q` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `n` varchar(200) DEFAULT NULL COMMENT '查询名称',
  `dbid` bigint(20) NOT NULL COMMENT '关联数据库id',
  `sql` text NOT NULL COMMENT 'sql语句',
  `stat` int(11) DEFAULT NULL COMMENT '状态 0：默认启用 -1禁用',
  `type` int(11) DEFAULT NULL COMMENT '类型 0：用户查询 1统计内容',
  `file_path` text COMMENT '产生静态统计文件地址',
  `de` text COMMENT '查询描述',
  `clc` int(11) DEFAULT '0' COMMENT '执行次数',
  `ms` int(11) DEFAULT '0' COMMENT '最小执行间隔 分钟',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='通用查询';

-- ----------------------------
-- Records of q
-- ----------------------------
INSERT INTO `q` VALUES ('1', '根据id查询用户基本信息', '1', 'select id,username,enable from t_user where id=?', '0', '0', null, '根据id查询用户基本信息', '15', '0');
INSERT INTO `q` VALUES ('2', '根据手机号查询用户基本信息', '1', 'select id,username from t_user where mobilePhone=?', '0', '0', null, '根据手机号查询用户基本信息', '8', '0');
INSERT INTO `q` VALUES ('3', '查询绑卡状态，根据验卡id和验卡状态', '1', 'SELECT bankName,cardStatus FROM t_bankcard   WHERE   userId=? AND  cardStatus=?', '0', '0', null, '查询绑卡状态，根据验卡id和验卡状态', '34', '1');
INSERT INTO `q` VALUES ('4', '统计90日内用户绑卡情况', '1', 'select userId,cardStatus from t_bankcard where checkTime> DATE_SUB(CURDATE(),INTERVAL 90 DAY) and   cardStatus=?', '0', '1', null, '统计1日内用户绑卡情况', '55', '15');

-- ----------------------------
-- Table structure for qh
-- ----------------------------
DROP TABLE IF EXISTS `qh`;
CREATE TABLE `qh` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `qid` bigint(20) DEFAULT NULL COMMENT '查询id',
  `pms` text COMMENT '查询参数',
  `dt` datetime DEFAULT NULL COMMENT '查询执行时间',
  `t` int(11) DEFAULT NULL COMMENT '类型 0通用查询 1统计查询',
  `edt` datetime DEFAULT NULL COMMENT '查询完成时间',
  `url` varchar(4500) DEFAULT NULL COMMENT '生成文件路径',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=102 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='查询历史';

-- ----------------------------
-- Records of qh
-- ----------------------------
INSERT INTO `qh` VALUES ('2', '3', '[\"1\",\"1\"]', '2015-06-29 16:03:04', '0', '2015-06-29 16:03:07', '');
INSERT INTO `qh` VALUES ('3', '3', '[\"1\",\"1\"]', '2015-06-29 16:03:04', '0', '2015-06-29 16:03:09', '');
INSERT INTO `qh` VALUES ('4', '3', '[\"1\",\"1\"]', '2015-06-29 16:03:02', '0', '2015-06-29 16:03:07', '');
INSERT INTO `qh` VALUES ('5', '3', '[\"1\",\"1\"]', '2015-06-29 16:03:07', '0', '2015-06-29 16:03:17', '');
INSERT INTO `qh` VALUES ('6', '3', '[\"1\",\"1\"]', '2015-06-29 16:03:01', '0', '2015-06-29 16:03:17', '');
INSERT INTO `qh` VALUES ('7', '3', '[\"1\",\"1\"]', '2015-06-29 16:03:06', '0', '2015-06-29 16:03:19', '');
INSERT INTO `qh` VALUES ('8', '3', '[\"1\",\"1\"]', '2015-06-29 16:03:17', '0', '2015-06-29 16:03:19', '');
INSERT INTO `qh` VALUES ('9', '3', '[\"1\",\"1\"]', '2015-06-29 16:03:07', '0', '2015-06-29 16:03:19', '');
INSERT INTO `qh` VALUES ('10', '3', '[\"1\",\"1\"]', '2015-06-29 16:03:02', '0', '2015-06-29 16:03:21', '');
INSERT INTO `qh` VALUES ('11', '3', '[\"1\",\"1\"]', '2015-06-29 16:03:07', '0', '2015-06-29 16:03:28', '');
INSERT INTO `qh` VALUES ('12', '3', '[\"1\",\"1\"]', '2015-06-29 16:03:28', '0', '2015-06-29 16:03:28', '');
INSERT INTO `qh` VALUES ('38', '3', '[\"18904958\",\"1\"]', '2015-06-30 13:20:57', '0', '2015-06-30 13:20:57', '');
INSERT INTO `qh` VALUES ('37', '3', '[\"18904958\",\"1\"]', '2015-06-30 13:18:37', '0', '2015-06-30 13:18:37', '');
INSERT INTO `qh` VALUES ('36', '3', '[\"18904958\",\"1\"]', '2015-06-30 13:17:26', '0', '2015-06-30 13:17:26', '');
INSERT INTO `qh` VALUES ('35', '3', '[\"18904958\",\"1\"]', '2015-06-30 13:11:08', '0', '2015-06-30 13:11:08', '');
INSERT INTO `qh` VALUES ('34', '3', '[\"18904958\",\"1\"]', '2015-06-30 13:11:04', '0', '2015-06-30 13:11:04', '');
INSERT INTO `qh` VALUES ('33', '4', '[\"1\"]', '2015-06-30 11:49:11', '1', '2015-06-30 11:49:11', 'querystatic/genstatics/2015年06月30日11时49分11秒172查询4.json');
INSERT INTO `qh` VALUES ('31', '4', '[\"1\"]', '2015-06-29 18:01:46', '1', '2015-06-29 18:01:46', 'static/genstatics/2015年06月29日18时01分46秒587查询4.json');
INSERT INTO `qh` VALUES ('39', '3', '[\"18904958\",\"1\"]', '2015-06-30 13:27:20', '0', '2015-06-30 13:27:20', '{\"sql_fs\":[\"bankName\",\"cardStatus\"],\"qol\":[{\"f\":\"cardStatus\",\"id\":3,\"n\":\"银行卡状态\",\"qid\":3,\"j\":\"{\\\"1\\\":\\\"已绑定\\\",\\\"2\\\":\\\"申请中\\\", \\\"3\\\": \\\"失败\\\"} \"}],\"q_rl\":[]}');
INSERT INTO `qh` VALUES ('40', '3', '[\"1\",\"1\"]', '2015-06-30 13:54:16', '0', '2015-06-30 13:54:16', '{\"sql_fs\":[\"bankName\",\"cardStatus\"],\"qol\":[{\"f\":\"cardStatus\",\"id\":3,\"n\":\"银行卡状态\",\"qid\":3,\"j\":\"{\\\"1\\\":\\\"已绑定\\\",\\\"2\\\":\\\"申请中\\\", \\\"3\\\": \\\"失败\\\"} \"}],\"q_rl\":[]}');
INSERT INTO `qh` VALUES ('41', '3', '[\"1787832\",\"1\"]', '2015-06-30 13:54:38', '0', '2015-06-30 13:54:38', '{\"sql_fs\":[\"bankName\",\"cardStatus\"],\"qol\":[{\"f\":\"cardStatus\",\"id\":3,\"n\":\"银行卡状态\",\"qid\":3,\"j\":\"{\\\"1\\\":\\\"已绑定\\\",\\\"2\\\":\\\"申请中\\\", \\\"3\\\": \\\"失败\\\"} \"}],\"q_rl\":[]}');
INSERT INTO `qh` VALUES ('42', '3', '[\"1787832\",\"1\"]', '2015-06-30 14:09:40', '0', '2015-06-30 14:09:40', '{\"sql_fs\":[\"bankName\",\"cardStatus\"],\"qol\":[{\"f\":\"cardStatus\",\"id\":3,\"n\":\"银行卡状态\",\"qid\":3,\"j\":\"{\\\"1\\\":\\\"已绑定\\\",\\\"2\\\":\\\"申请中\\\", \\\"3\\\": \\\"失败\\\"} \"}],\"q_rl\":[]}');
INSERT INTO `qh` VALUES ('43', '3', '[\"qew\",\"1\"]', '2015-06-30 14:14:26', '0', '2015-06-30 14:14:26', '{\"sql_fs\":[\"bankName\",\"cardStatus\"],\"qol\":[{\"f\":\"cardStatus\",\"id\":3,\"n\":\"银行卡状态\",\"qid\":3,\"j\":\"{\\\"1\\\":\\\"已绑定\\\",\\\"2\\\":\\\"申请中\\\", \\\"3\\\": \\\"失败\\\"} \"}],\"q_rl\":[]}');
INSERT INTO `qh` VALUES ('44', '3', '[\"1213\",\"1\"]', '2015-06-30 14:14:27', '0', '2015-06-30 14:14:27', '{\"sql_fs\":[\"bankName\",\"cardStatus\"],\"qol\":[{\"f\":\"cardStatus\",\"id\":3,\"n\":\"银行卡状态\",\"qid\":3,\"j\":\"{\\\"1\\\":\\\"已绑定\\\",\\\"2\\\":\\\"申请中\\\", \\\"3\\\": \\\"失败\\\"} \"}],\"q_rl\":[]}');
INSERT INTO `qh` VALUES ('45', '4', '[\"1\"]', '2015-06-30 14:25:14', '1', '2015-06-30 14:25:14', 'querystatic/genstatics/2015年06月30日14时25分14秒047查询4.json');
INSERT INTO `qh` VALUES ('46', '4', '[\"1\"]', '2015-06-30 14:25:16', '1', '2015-06-30 14:25:16', 'querystatic/genstatics/2015年06月30日14时25分16秒857查询4.json');
INSERT INTO `qh` VALUES ('47', '4', '[\"1\"]', '2015-06-30 14:25:18', '1', '2015-06-30 14:25:18', 'querystatic/genstatics/2015年06月30日14时25分18秒452查询4.json');
INSERT INTO `qh` VALUES ('48', '4', '[\"1\"]', '2015-06-30 14:25:25', '1', '2015-06-30 14:25:25', 'querystatic/genstatics/2015年06月30日14时25分25秒783查询4.json');
INSERT INTO `qh` VALUES ('49', '4', '[\"1\"]', '2015-06-30 14:25:32', '1', '2015-06-30 14:25:32', 'querystatic/genstatics/2015年06月30日14时25分32秒021查询4.json');
INSERT INTO `qh` VALUES ('50', '4', '[\"1\"]', '2015-06-30 14:25:56', '1', '2015-06-30 14:25:56', 'querystatic/genstatics/2015年06月30日14时25分56秒708查询4.json');
INSERT INTO `qh` VALUES ('51', '3', '[\"18890393\",\"1\"]', '2015-06-30 15:33:05', '0', '2015-06-30 15:33:05', '{\"sql_fs\":[\"bankName\",\"cardStatus\"],\"qol\":[{\"f\":\"cardStatus\",\"id\":3,\"n\":\"银行卡状态\",\"qid\":3,\"j\":\"{\\\"1\\\":\\\"已绑定\\\",\\\"2\\\":\\\"申请中\\\", \\\"3\\\": \\\"失败\\\"} \"}],\"q_rl\":[]}');
INSERT INTO `qh` VALUES ('52', '3', '[\"188903931\",\"1\"]', '2015-06-30 15:33:26', '0', '2015-06-30 15:33:26', '{\"sql_fs\":[\"bankName\",\"cardStatus\"],\"qol\":[{\"f\":\"cardStatus\",\"id\":3,\"n\":\"银行卡状态\",\"qid\":3,\"j\":\"{\\\"1\\\":\\\"已绑定\\\",\\\"2\\\":\\\"申请中\\\", \\\"3\\\": \\\"失败\\\"} \"}],\"q_rl\":[]}');
INSERT INTO `qh` VALUES ('53', '4', '[\"1\"]', '2015-06-30 15:33:32', '1', '2015-06-30 15:33:32', 'querystatic/genstatics/2015年06月30日15时33分32秒260查询4.json');
INSERT INTO `qh` VALUES ('54', '3', '[\"18890393\",\"1\"]', '2015-06-30 15:34:12', '0', '2015-06-30 15:34:12', '{\"sql_fs\":[\"bankName\",\"cardStatus\"],\"qol\":[{\"f\":\"cardStatus\",\"id\":3,\"n\":\"银行卡状态\",\"qid\":3,\"j\":\"{\\\"1\\\":\\\"已绑定\\\",\\\"2\\\":\\\"申请中\\\", \\\"3\\\": \\\"失败\\\"} \"}],\"q_rl\":[]}');
INSERT INTO `qh` VALUES ('55', '4', '[\"2\"]', '2015-06-30 15:34:29', '1', '2015-06-30 15:34:29', 'querystatic/genstatics/2015年06月30日15时34分29秒510查询4.json');
INSERT INTO `qh` VALUES ('56', '4', '[\"1\"]', '2015-06-30 17:25:13', '1', '2015-06-30 17:25:13', 'querystatic/genstatics/2015年06月30日17时25分13秒402查询4.json');
INSERT INTO `qh` VALUES ('57', '4', '[\"1\"]', '2015-07-01 09:57:58', '1', '2015-07-01 09:57:58', 'querystatic/genstatics/2015年07月01日09时57分58秒144查询4.json');
INSERT INTO `qh` VALUES ('58', '4', '[\"1\"]', '2015-07-01 11:36:44', '1', '2015-07-01 11:36:44', 'querystatic/genstatics/2015年07月01日11时36分44秒201查询4.json');
INSERT INTO `qh` VALUES ('59', '3', '[\"1\",\"1\"]', '2015-07-01 11:36:57', '0', '2015-07-01 11:36:57', '{\"sql_fs\":[\"bankName\",\"cardStatus\"],\"qol\":[{\"f\":\"cardStatus\",\"id\":3,\"n\":\"银行卡状态\",\"qid\":3,\"j\":\"{\\\"1\\\":\\\"已绑定\\\",\\\"2\\\":\\\"申请中\\\", \\\"3\\\": \\\"失败\\\"} \"}],\"q_rl\":[]}');
INSERT INTO `qh` VALUES ('60', '4', '[\"2\"]', '2015-07-01 11:50:13', '1', '2015-07-01 11:50:13', 'querystatic/genstatics/2015年07月01日11时50分13秒970查询4.json');
INSERT INTO `qh` VALUES ('61', '4', '[\"3\"]', '2015-07-01 11:50:16', '1', '2015-07-01 11:50:16', 'querystatic/genstatics/2015年07月01日11时50分16秒414查询4.json');
INSERT INTO `qh` VALUES ('62', '4', '[\"1\"]', '2015-07-01 12:05:40', '1', '2015-07-01 12:05:40', 'querystatic/genstatics/2015年07月01日12时05分40秒577查询4.json');
INSERT INTO `qh` VALUES ('63', '4', '[\"1\"]', '2015-07-01 13:51:12', '1', '2015-07-01 13:51:12', 'querystatic/genstatics/2015年07月01日13时51分12秒948查询4.json');
INSERT INTO `qh` VALUES ('64', '3', '[\"122\",\"1\"]', '2015-07-01 13:51:17', '0', '2015-07-01 13:51:17', '{\"sql_fs\":[\"bankName\",\"cardStatus\"],\"qol\":[{\"f\":\"cardStatus\",\"id\":3,\"n\":\"银行卡状态\",\"qid\":3,\"j\":\"{\\\"1\\\":\\\"已绑定\\\",\\\"2\\\":\\\"申请中\\\", \\\"3\\\": \\\"失败\\\"} \"}],\"q_rl\":[]}');
INSERT INTO `qh` VALUES ('65', '4', '[\"1\"]', '2015-07-01 14:08:38', '1', '2015-07-01 14:08:38', 'querystatic/genstatics/2015年07月01日14时08分38秒713查询4.json');
INSERT INTO `qh` VALUES ('66', '4', '[\"1\"]', '2015-07-01 16:47:25', '1', '2015-07-01 16:47:25', 'querystatic/genstatics/2015年07月01日16时47分25秒919查询4.json');
INSERT INTO `qh` VALUES ('67', '4', '[\"2\"]', '2015-07-01 16:47:33', '1', '2015-07-01 16:47:33', 'querystatic/genstatics/2015年07月01日16时47分33秒623查询4.json');
INSERT INTO `qh` VALUES ('72', '4', '[\"1\"]', '2015-07-03 15:07:18', '1', '2015-07-03 15:07:18', 'querystatic/genstatics/2015年07月03日15时07分18秒458查询4.json');
INSERT INTO `qh` VALUES ('73', '2', '[\"15811372713\"]', '2015-07-03 15:11:44', '0', '2015-07-03 15:11:44', '{\"sql_fs\":[\"id\",\"username\"],\"qol\":[],\"q_rl\":[{\"id\":30000341,\"username\":\"hecj\"}]}');
INSERT INTO `qh` VALUES ('74', '1', '[\"1212\"]', '2015-07-03 15:12:04', '0', '2015-07-03 15:12:04', '{\"sql_fs\":[\"id\",\"username\",\"enable\"],\"qol\":[{\"f\":\"enable\",\"id\":1,\"n\":\"用户状态\",\"qid\":1,\"j\":\"{\\\"1\\\":\\\"启用\\\",\\\"2\\\":\\\"禁用\\\",\\\"3\\\":\\\"黑名单\\\",\\\"4\\\":\\\"锁定\\\"}\"},{\"f\":\"id\",\"id\":2,\"n\":\"主键\",\"qid\":1,\"j\":null}],\"q_rl\":[]}');
INSERT INTO `qh` VALUES ('75', '4', '[\"2\"]', '2015-07-03 15:12:36', '1', '2015-07-03 15:12:36', 'querystatic/genstatics/2015年07月03日15时12分36秒673查询4.json');
INSERT INTO `qh` VALUES ('76', '4', '[\"3\"]', '2015-07-03 15:12:42', '1', '2015-07-03 15:12:42', 'querystatic/genstatics/2015年07月03日15时12分42秒696查询4.json');
INSERT INTO `qh` VALUES ('77', '1', '[\"select id,username,enable from t_user where id=123\"]', '2015-07-03 15:14:07', '0', '2015-07-03 15:14:07', '{\"sql_fs\":[\"id\",\"username\",\"enable\"],\"qol\":[{\"f\":\"enable\",\"id\":1,\"n\":\"用户状态\",\"qid\":1,\"j\":\"{\\\"1\\\":\\\"启用\\\",\\\"2\\\":\\\"禁用\\\",\\\"3\\\":\\\"黑名单\\\",\\\"4\\\":\\\"锁定\\\"}\"},{\"f\":\"id\",\"id\":2,\"n\":\"主键\",\"qid\":1,\"j\":null}],\"q_rl\":[]}');
INSERT INTO `qh` VALUES ('78', '3', '[\"1000045\",\"1\"]', '2015-07-03 15:19:50', '0', '2015-07-03 15:19:50', '{\"sql_fs\":[\"bankName\",\"cardStatus\"],\"qol\":[{\"f\":\"cardStatus\",\"id\":3,\"n\":\"银行卡状态\",\"qid\":3,\"j\":\"{\\\"1\\\":\\\"已绑定\\\",\\\"2\\\":\\\"申请中\\\", \\\"3\\\": \\\"失败\\\"} \"}],\"q_rl\":[]}');
INSERT INTO `qh` VALUES ('79', '2', '[\"18600797582\"]', '2015-07-03 15:26:35', '0', '2015-07-03 15:26:35', '{\"sql_fs\":[\"id\",\"username\"],\"qol\":[],\"q_rl\":[{\"id\":18890388,\"username\":\"ANDY\"}]}');
INSERT INTO `qh` VALUES ('80', '2', '[\"18678734728\"]', '2015-07-03 15:31:18', '0', '2015-07-03 15:31:18', '{\"sql_fs\":[\"id\",\"username\"],\"qol\":[],\"q_rl\":[]}');
INSERT INTO `qh` VALUES ('81', '2', '[\"13601355427\"]', '2015-07-03 15:31:32', '0', '2015-07-03 15:31:32', '{\"sql_fs\":[\"id\",\"username\"],\"qol\":[],\"q_rl\":[{\"id\":18890408,\"username\":\"王超\"}]}');
INSERT INTO `qh` VALUES ('82', '1', '[\"18890408\"]', '2015-07-03 15:31:45', '0', '2015-07-03 15:31:45', '{\"sql_fs\":[\"id\",\"username\",\"enable\"],\"qol\":[{\"f\":\"enable\",\"id\":1,\"n\":\"用户状态\",\"qid\":1,\"j\":\"{\\\"1\\\":\\\"启用\\\",\\\"2\\\":\\\"禁用\\\",\\\"3\\\":\\\"黑名单\\\",\\\"4\\\":\\\"锁定\\\"}\"},{\"f\":\"id\",\"id\":2,\"n\":\"主键\",\"qid\":1,\"j\":null}],\"q_rl\":[{\"id\":18890408,\"username\":\"王超\",\"enable\":1}]}');
INSERT INTO `qh` VALUES ('83', '1', '[\"18890408\"]', '2015-07-03 15:31:46', '0', '2015-07-03 15:31:46', '{\"sql_fs\":[\"id\",\"username\",\"enable\"],\"qol\":[{\"f\":\"enable\",\"id\":1,\"n\":\"用户状态\",\"qid\":1,\"j\":\"{\\\"1\\\":\\\"启用\\\",\\\"2\\\":\\\"禁用\\\",\\\"3\\\":\\\"黑名单\\\",\\\"4\\\":\\\"锁定\\\"}\"},{\"f\":\"id\",\"id\":2,\"n\":\"主键\",\"qid\":1,\"j\":null}],\"q_rl\":[{\"id\":18890408,\"username\":\"王超\",\"enable\":1}]}');
INSERT INTO `qh` VALUES ('84', '1', '[\"18890408\"]', '2015-07-03 15:31:47', '0', '2015-07-03 15:31:47', '{\"sql_fs\":[\"id\",\"username\",\"enable\"],\"qol\":[{\"f\":\"enable\",\"id\":1,\"n\":\"用户状态\",\"qid\":1,\"j\":\"{\\\"1\\\":\\\"启用\\\",\\\"2\\\":\\\"禁用\\\",\\\"3\\\":\\\"黑名单\\\",\\\"4\\\":\\\"锁定\\\"}\"},{\"f\":\"id\",\"id\":2,\"n\":\"主键\",\"qid\":1,\"j\":null}],\"q_rl\":[{\"id\":18890408,\"username\":\"王超\",\"enable\":1}]}');
INSERT INTO `qh` VALUES ('85', '1', '[\"18890408\"]', '2015-07-03 15:31:47', '0', '2015-07-03 15:31:47', '{\"sql_fs\":[\"id\",\"username\",\"enable\"],\"qol\":[{\"f\":\"enable\",\"id\":1,\"n\":\"用户状态\",\"qid\":1,\"j\":\"{\\\"1\\\":\\\"启用\\\",\\\"2\\\":\\\"禁用\\\",\\\"3\\\":\\\"黑名单\\\",\\\"4\\\":\\\"锁定\\\"}\"},{\"f\":\"id\",\"id\":2,\"n\":\"主键\",\"qid\":1,\"j\":null}],\"q_rl\":[{\"id\":18890408,\"username\":\"王超\",\"enable\":1}]}');
INSERT INTO `qh` VALUES ('86', '1', '[\"18890408\"]', '2015-07-03 15:31:47', '0', '2015-07-03 15:31:47', '{\"sql_fs\":[\"id\",\"username\",\"enable\"],\"qol\":[{\"f\":\"enable\",\"id\":1,\"n\":\"用户状态\",\"qid\":1,\"j\":\"{\\\"1\\\":\\\"启用\\\",\\\"2\\\":\\\"禁用\\\",\\\"3\\\":\\\"黑名单\\\",\\\"4\\\":\\\"锁定\\\"}\"},{\"f\":\"id\",\"id\":2,\"n\":\"主键\",\"qid\":1,\"j\":null}],\"q_rl\":[{\"id\":18890408,\"username\":\"王超\",\"enable\":1}]}');
INSERT INTO `qh` VALUES ('87', '4', '[\"1\"]', '2015-07-03 15:32:59', '1', '2015-07-03 15:32:59', 'querystatic/genstatics/2015年07月03日15时32分59秒547查询4.json');
INSERT INTO `qh` VALUES ('88', '4', '[\"3\"]', '2015-07-03 15:33:22', '1', '2015-07-03 15:33:22', 'querystatic/genstatics/2015年07月03日15时33分22秒504查询4.json');
INSERT INTO `qh` VALUES ('89', '4', '[\"2\"]', '2015-07-03 15:37:11', '1', '2015-07-03 15:37:11', 'querystatic/genstatics/2015年07月03日15时37分11秒693查询4.json');
INSERT INTO `qh` VALUES ('90', '4', '[\"1\"]', '2015-07-03 17:17:32', '1', '2015-07-03 17:17:32', 'querystatic/genstatics/2015年07月03日17时17分32秒514查询4.json');
INSERT INTO `qh` VALUES ('91', '4', '[\"2\"]', '2015-07-03 17:17:35', '1', '2015-07-03 17:17:35', 'querystatic/genstatics/2015年07月03日17时17分35秒570查询4.json');
INSERT INTO `qh` VALUES ('92', '4', '[\"3\"]', '2015-07-03 17:17:40', '1', '2015-07-03 17:17:40', 'querystatic/genstatics/2015年07月03日17时17分40秒655查询4.json');
INSERT INTO `qh` VALUES ('93', '4', '[\"1\"]', '2015-07-06 14:26:52', '1', '2015-07-06 14:26:52', 'querystatic/genstatics/2015年07月06日14时26分52秒444查询4.json');
INSERT INTO `qh` VALUES ('94', '1', '[\"123123\"]', '2015-07-07 11:37:20', '0', '2015-07-07 11:37:21', '{\"sql_fs\":[\"id\",\"username\",\"enable\"],\"q_rl\":[],\"qol\":[{\"f\":\"enable\",\"j\":\"{\\\"1\\\":\\\"启用\\\",\\\"2\\\":\\\"禁用\\\",\\\"3\\\":\\\"黑名单\\\",\\\"4\\\":\\\"锁定\\\"}\",\"id\":1,\"qid\":1,\"n\":\"用户状态\"},{\"f\":\"id\",\"j\":null,\"id\":2,\"qid\":1,\"n\":\"主键\"}]}');
INSERT INTO `qh` VALUES ('95', '1', '[\"123123\"]', '2015-07-07 11:37:24', '0', '2015-07-07 11:37:24', '{\"sql_fs\":[\"id\",\"username\",\"enable\"],\"q_rl\":[],\"qol\":[{\"f\":\"enable\",\"j\":\"{\\\"1\\\":\\\"启用\\\",\\\"2\\\":\\\"禁用\\\",\\\"3\\\":\\\"黑名单\\\",\\\"4\\\":\\\"锁定\\\"}\",\"id\":1,\"qid\":1,\"n\":\"用户状态\"},{\"f\":\"id\",\"j\":null,\"id\":2,\"qid\":1,\"n\":\"主键\"}]}');
INSERT INTO `qh` VALUES ('96', '3', '[\"1111\",\"1\"]', '2015-07-07 11:37:28', '0', '2015-07-07 11:37:28', '{\"sql_fs\":[\"bankName\",\"cardStatus\"],\"q_rl\":[],\"qol\":[{\"f\":\"cardStatus\",\"j\":\"{\\\"1\\\":\\\"已绑定\\\",\\\"2\\\":\\\"申请中\\\", \\\"3\\\": \\\"失败\\\"} \",\"id\":3,\"qid\":3,\"n\":\"银行卡状态\"}]}');
INSERT INTO `qh` VALUES ('97', '4', '[\"1\"]', '2015-07-07 11:38:02', '1', '2015-07-07 11:38:02', 'querystatic/genstatics/2015年07月07日11时38分02秒683查询4.json');
INSERT INTO `qh` VALUES ('98', '4', '[\"1\"]', '2015-07-15 17:06:23', '1', null, null);
INSERT INTO `qh` VALUES ('99', '4', '[\"1\"]', '2015-07-15 17:42:42', '1', null, null);
INSERT INTO `qh` VALUES ('100', '4', '[\"1\"]', '2015-07-15 18:09:53', '1', '2015-07-15 18:09:54', 'querystatic/genstatics/2015年07月15日18时09分54秒551查询4.json');
INSERT INTO `qh` VALUES ('101', '4', '[\"1\"]', '2015-07-16 10:33:09', '1', '2015-07-16 10:33:12', 'querystatic/genstatics/2015年07月16日10时33分12秒001查询4.json');

-- ----------------------------
-- Table structure for qo
-- ----------------------------
DROP TABLE IF EXISTS `qo`;
CREATE TABLE `qo` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `qid` bigint(20) NOT NULL COMMENT '关联查询id',
  `n` varchar(100) DEFAULT NULL COMMENT '字段含义',
  `f` varchar(100) DEFAULT NULL COMMENT '关联字段',
  `j` text COMMENT '关联字段对应的json情况',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='查询关联的字段选项';

-- ----------------------------
-- Records of qo
-- ----------------------------
INSERT INTO `qo` VALUES ('1', '1', '用户状态', 'enable', '{\"1\":\"启用\",\"2\":\"禁用\",\"3\":\"黑名单\",\"4\":\"锁定\"}');
INSERT INTO `qo` VALUES ('2', '1', '主键', 'id', null);
INSERT INTO `qo` VALUES ('3', '3', '银行卡状态', 'cardStatus', '{\"1\":\"已绑定\",\"2\":\"申请中\", \"3\": \"失败\"} ');
INSERT INTO `qo` VALUES ('4', '4', '银行卡状态', 'cardStatus', '{\"1\":\"已绑定\",\"2\":\"申请中\", \"3\": \"失败\"} ');

-- ----------------------------
-- Table structure for r
-- ----------------------------
DROP TABLE IF EXISTS `r`;
CREATE TABLE `r` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `n` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `qs` text COMMENT '通用查询权限字符逗号分隔',
  `ms` text COMMENT '菜单权限id字符 逗号分开',
  `mps` text COMMENT '菜单权限路径字符 逗号分隔',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='角色';

-- ----------------------------
-- Records of r
-- ----------------------------
INSERT INTO `r` VALUES ('1', '管理员', '1,2,3,4', '1,2,3,4,5,6,7,8,9,10,12,14', '*');
INSERT INTO `r` VALUES ('2', '客服', '1,2,3,4', '1,7,8,9,10,12,14', null);
INSERT INTO `r` VALUES ('3', '开发', null, null, null);
INSERT INTO `r` VALUES ('4', '市场', null, null, null);
INSERT INTO `r` VALUES ('5', '运营', null, null, null);

-- ----------------------------
-- Table structure for u
-- ----------------------------
DROP TABLE IF EXISTS `u`;
CREATE TABLE `u` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `u` varchar(100) NOT NULL COMMENT '用户名',
  `p` varchar(100) NOT NULL COMMENT '密码',
  `rid` bigint(20) NOT NULL COMMENT '角色id',
  `stat` int(11) DEFAULT NULL COMMENT '状态：0正常 -1禁用',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 CHECKSUM=1 DELAY_KEY_WRITE=1 ROW_FORMAT=DYNAMIC COMMENT='用户';

-- ----------------------------
-- Records of u
-- ----------------------------
INSERT INTO `u` VALUES ('1', 'admin', 'noadmin', '1', '0');
INSERT INTO `u` VALUES ('2', '运营a', '123', '5', '-1');
INSERT INTO `u` VALUES ('4', '客服1', '123', '2', '0');
INSERT INTO `u` VALUES ('6', '开发1', '123', '3', '0');
INSERT INTO `u` VALUES ('7', '开发x', '123', '5', '0');
