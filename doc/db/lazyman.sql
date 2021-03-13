/*
Navicat MySQL Data Transfer

Source Server         : LOCAL
Source Server Version : 50732
Source Host           : 127.0.0.1:3306
Source Database       : lazyman

Target Server Type    : MYSQL
Target Server Version : 50732
File Encoding         : 65001

Date: 2021-03-13 13:04:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_app_user
-- ----------------------------
DROP TABLE IF EXISTS `t_app_user`;
CREATE TABLE `t_app_user` (
  `id` bigint(20) unsigned NOT NULL COMMENT 'ID',
  `nickname` varchar(100) DEFAULT NULL COMMENT '昵称',
  `mobile` char(11) DEFAULT NULL COMMENT '手机',
  `avatar` varchar(255) DEFAULT NULL COMMENT '个性头像',
  `sex` char(1) DEFAULT NULL COMMENT '性别',
  `birth` date DEFAULT NULL COMMENT '生日',
  `state` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '启用状态',
  `union_id` varchar(100) DEFAULT NULL COMMENT '微信统一ID',
  `open_id` varchar(100) DEFAULT NULL COMMENT '微信开放ID',
  `is_deleted` tinyint(1) unsigned NOT NULL COMMENT '刪除标识',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` bigint(20) unsigned NOT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_by` bigint(20) unsigned DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='APP用户信息';

-- ----------------------------
-- Records of t_app_user
-- ----------------------------
INSERT INTO `t_app_user` VALUES ('5343949279904016', null, '18516246325', 'http://qp2g29tsi.hd-bkt.clouddn.com/20210302/2feb620f55c5415fa24251c9bc19c0ca.', null, null, '1', null, '123456', '0', '2021-03-06 12:09:14', '0', null, null);

-- ----------------------------
-- Table structure for t_sms_channel
-- ----------------------------
DROP TABLE IF EXISTS `t_sms_channel`;
CREATE TABLE `t_sms_channel` (
  `id` bigint(20) unsigned NOT NULL COMMENT 'ID',
  `channel_name` varchar(100) NOT NULL COMMENT '通道名称',
  `vendor_code` varchar(50) NOT NULL COMMENT '厂商代码',
  `sms_type` int(11) unsigned NOT NULL COMMENT '短信类型',
  `api_url` varchar(255) NOT NULL COMMENT 'Api地址',
  `access_key` varchar(100) DEFAULT NULL COMMENT 'Access key',
  `secret_key` varchar(100) DEFAULT NULL COMMENT 'Secret key',
  `state` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '启用标识',
  `priority` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '优先级',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '删除标识',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='短信通道';

-- ----------------------------
-- Records of t_sms_channel
-- ----------------------------
INSERT INTO `t_sms_channel` VALUES ('5341572955373840', '欣易辰验证码', 'xinyichen', '1', 'http://113.108.68.228:8001', 'admin', 'Jiayou2020', '1', '0', '18122', '0', '5332937727967504', '2021-03-04 19:51:55', '5332937727967504', '2021-03-06 10:58:50');

-- ----------------------------
-- Table structure for t_sms_task
-- ----------------------------
DROP TABLE IF EXISTS `t_sms_task`;
CREATE TABLE `t_sms_task` (
  `id` bigint(20) unsigned NOT NULL COMMENT 'ID',
  `batch_id` bigint(20) unsigned NOT NULL COMMENT '批次号',
  `sms_type` int(11) unsigned NOT NULL COMMENT '短信类型',
  `template_category` int(11) unsigned NOT NULL COMMENT '模板分类',
  `mobile` varchar(20) NOT NULL COMMENT '接收手机号',
  `content` varchar(4096) NOT NULL COMMENT '短信内容',
  `state` int(11) unsigned NOT NULL DEFAULT '0' COMMENT '发送状态',
  `channel_id` bigint(20) unsigned DEFAULT NULL COMMENT '发送通道ID',
  `send_time` datetime(3) DEFAULT NULL COMMENT '发送时间',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '删除标识',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_mobile` (`mobile`) USING BTREE,
  KEY `idx_batch_id` (`batch_id`) USING BTREE,
  KEY `idx_create_time` (`create_time`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='短信任务表';

-- ----------------------------
-- Records of t_sms_task
-- ----------------------------
INSERT INTO `t_sms_task` VALUES ('5346990309081360', '5346990309048592', '1', '10', '18516246325', '【戎业科技】验证码300631,有效期5分钟', '1', '5341572955373840', '2021-03-08 15:42:44.280', '0', '0', '2021-03-08 15:42:44', '0', '2021-03-08 15:42:44');
INSERT INTO `t_sms_task` VALUES ('5346991679979793', '5346991679979792', '1', '10', '18516246325', '【戎业科技】验证码782530,有效期5分钟', '1', '5341572955373840', '2021-03-08 15:44:07.510', '0', '0', '2021-03-08 15:44:07', '0', '2021-03-08 15:44:08');
INSERT INTO `t_sms_task` VALUES ('5353732747215120', '5353732747198736', '1', '10', '18516246325', '【戎业科技】验证码339633,有效期5分钟', '1', '5341572955373840', '2021-03-13 10:01:29.682', '0', '0', '2021-03-13 10:01:29', '0', '2021-03-13 10:01:30');
INSERT INTO `t_sms_task` VALUES ('5353763348824336', '5353763348807952', '1', '10', '18516246325', '【戎业科技】验证码551300,有效期5分钟', '1', '5341572955373840', '2021-03-13 10:32:37.467', '0', '0', '2021-03-13 10:32:37', '0', '2021-03-13 10:32:37');

-- ----------------------------
-- Table structure for t_sms_template
-- ----------------------------
DROP TABLE IF EXISTS `t_sms_template`;
CREATE TABLE `t_sms_template` (
  `id` bigint(20) unsigned NOT NULL COMMENT 'ID',
  `sms_type` int(11) unsigned NOT NULL COMMENT '短信类型',
  `category` int(11) unsigned NOT NULL COMMENT '模板分类',
  `content` varchar(4096) NOT NULL COMMENT '模板内容',
  `state` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '启用标识',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `is_deleted` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '删除标识',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新人',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='短信模板';

-- ----------------------------
-- Records of t_sms_template
-- ----------------------------
INSERT INTO `t_sms_template` VALUES ('5341610709057808', '1', '10', '【戎业科技】验证码${verifyCode},有效期5分钟', '1', '验证码', '0', '5332937727967504', '2021-03-04 20:30:19', '5332937727967504', '2021-03-06 10:52:17');

-- ----------------------------
-- Table structure for t_sys_config
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_config`;
CREATE TABLE `t_sys_config` (
  `id` bigint(20) unsigned NOT NULL COMMENT '配置ID',
  `config_name` varchar(100) NOT NULL COMMENT '配置名称',
  `config_key` varchar(100) DEFAULT NULL COMMENT '配置key',
  `config_value` varchar(255) DEFAULT NULL COMMENT '配置value',
  `state` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '系统内置',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `is_deleted` tinyint(1) unsigned NOT NULL COMMENT '刪除标识',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` bigint(20) unsigned NOT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_by` bigint(20) unsigned DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统配置';

-- ----------------------------
-- Records of t_sys_config
-- ----------------------------
INSERT INTO `t_sys_config` VALUES ('5339997393715472', '系统皮肤', 'system.skin', 'blue', '1', '系统皮肤', '0', '2021-03-03 17:09:10', '5332937727967504', '2021-03-04 14:52:15', '5332937727967504');

-- ----------------------------
-- Table structure for t_sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_dept`;
CREATE TABLE `t_sys_dept` (
  `id` bigint(20) unsigned NOT NULL COMMENT '部门ID',
  `dept_name` varchar(100) NOT NULL COMMENT '部门名称',
  `leader` varchar(100) DEFAULT NULL COMMENT '部门负责人',
  `phone` varchar(50) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `sort` int(11) DEFAULT '0' COMMENT '部门排序',
  `parent_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '父ID',
  `state` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '启用状态',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `is_deleted` tinyint(1) unsigned NOT NULL COMMENT '刪除标识',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` bigint(20) unsigned NOT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_by` bigint(20) unsigned DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统部门';

-- ----------------------------
-- Records of t_sys_dept
-- ----------------------------
INSERT INTO `t_sys_dept` VALUES ('5333957667406096', '戎业科技', '王龙', '18516246325', null, '0', '0', '1', null, '0', '2021-02-28 13:33:18', '0', null, null);
INSERT INTO `t_sys_dept` VALUES ('5335539032375568', 'IT部', null, null, null, '0', '5333957667406096', '1', null, '0', '2021-02-28 13:33:53', '5332937727967504', '2021-03-04 14:54:18', '5332937727967504');
INSERT INTO `t_sys_dept` VALUES ('5335602208948496', '销售部', null, null, null, '1', '5333957667406096', '1', null, '0', '2021-02-28 14:38:09', '5332937727967504', null, null);
INSERT INTO `t_sys_dept` VALUES ('5335686809747728', '销售1部', null, null, null, '0', '5335602208948496', '1', null, '0', '2021-02-28 16:04:13', '5332937727967504', null, null);

-- ----------------------------
-- Table structure for t_sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_dict`;
CREATE TABLE `t_sys_dict` (
  `id` bigint(20) unsigned NOT NULL COMMENT '字典ID',
  `dict_name` varchar(100) NOT NULL COMMENT '字典名称',
  `dict_type` varchar(50) NOT NULL COMMENT '字典类型',
  `state` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '启用状态',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `is_deleted` tinyint(1) unsigned NOT NULL COMMENT '刪除标识',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` bigint(20) unsigned NOT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_by` bigint(20) unsigned DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统字典';

-- ----------------------------
-- Records of t_sys_dict
-- ----------------------------
INSERT INTO `t_sys_dict` VALUES ('5333957667406096', '系统开关', 'sys_normal_disable', '1', '启用/禁用开关', '0', '2021-02-27 10:45:14', '5332937727967504', null, null);
INSERT INTO `t_sys_dict` VALUES ('5334099509182736', '系统显示', 'sys_show_hide', '1', '是否显示', '0', '2021-02-27 13:09:32', '5332937727967504', '2021-02-27 17:40:48', '5332937727967504');
INSERT INTO `t_sys_dict` VALUES ('5334119200882960', '用户性别', 'sys_user_sex', '1', '系统用户性别', '0', '2021-02-27 13:29:34', '5332937727967504', null, null);
INSERT INTO `t_sys_dict` VALUES ('5339990321545488', '系统是否', 'sys_yes_no', '1', '系统是否', '0', '2021-03-03 17:01:58', '5332937727967504', null, null);
INSERT INTO `t_sys_dict` VALUES ('5340016855875856', '系统通知类型', 'sys_notice_type', '1', '系统通知类型', '0', '2021-03-03 17:28:58', '5332937727967504', null, null);
INSERT INTO `t_sys_dict` VALUES ('5340024925372688', '系统打开关闭', 'sys_open_close', '1', '系统打开关闭', '0', '2021-03-03 17:37:10', '5332937727967504', null, null);
INSERT INTO `t_sys_dict` VALUES ('5341172356448528', '系统成功失败', 'sys_success_fail', '1', '系统成功失败', '0', '2021-03-04 13:04:24', '5332937727967504', null, null);
INSERT INTO `t_sys_dict` VALUES ('5341457055154448', '短信厂商', 'sms_vendor', '1', '短信厂商', '0', '2021-03-04 17:54:01', '5332937727967504', null, null);
INSERT INTO `t_sys_dict` VALUES ('5341458842616080', '短信类型', 'sms_type', '1', '短信类型', '0', '2021-03-04 17:55:50', '5332937727967504', null, null);
INSERT INTO `t_sys_dict` VALUES ('5341595823522064', '短信模板分类', 'sms_template_category', '1', '短信模板分类', '0', '2021-03-04 20:15:10', '5332937727967504', '2021-03-12 21:49:05', '5332937727967504');
INSERT INTO `t_sys_dict` VALUES ('5341633877131536', '短信发送状态', 'sms_send_state', '1', '短信发送状态', '0', '2021-03-04 20:53:53', '5332937727967504', '2021-03-12 21:49:13', '5332937727967504');

-- ----------------------------
-- Table structure for t_sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_dict_data`;
CREATE TABLE `t_sys_dict_data` (
  `id` bigint(20) unsigned NOT NULL COMMENT '字典ID',
  `dict_type` varchar(50) NOT NULL COMMENT '字典类型',
  `dict_label` varchar(100) NOT NULL COMMENT '字典标签',
  `dict_value` varchar(100) NOT NULL COMMENT '数据键值',
  `state` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '启用状态',
  `sort` int(11) DEFAULT '0' COMMENT '显示排序',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `is_deleted` tinyint(1) unsigned NOT NULL COMMENT '刪除标识',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` bigint(20) unsigned NOT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_by` bigint(20) unsigned DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统字典数据';

-- ----------------------------
-- Records of t_sys_dict_data
-- ----------------------------
INSERT INTO `t_sys_dict_data` VALUES ('5333959651115280', 'sys_normal_disable', '启用', 'true', '1', '0', '正常', '0', '2021-02-27 10:47:15', '5332937727967504', null, null);
INSERT INTO `t_sys_dict_data` VALUES ('5333960039989520', 'sys_normal_disable', '禁用', 'false', '1', '1', '停用', '0', '2021-02-27 10:47:39', '5332937727967504', null, null);
INSERT INTO `t_sys_dict_data` VALUES ('5334100581843216', 'sys_show_hide', '显示', 'true', '1', '0', '显示', '0', '2021-02-27 13:10:37', '5332937727967504', '2021-02-27 13:13:11', '5332937727967504');
INSERT INTO `t_sys_dict_data` VALUES ('5334101099544848', 'sys_show_hide', '隐藏', 'false', '1', '1', '隐藏', '0', '2021-02-27 13:11:09', '5332937727967504', '2021-02-27 13:13:15', '5332937727967504');
INSERT INTO `t_sys_dict_data` VALUES ('5334119611400464', 'sys_user_sex', '男', 'M', '1', '0', '男性', '0', '2021-02-27 13:29:59', '5332937727967504', '2021-02-28 17:45:04', '5332937727967504');
INSERT INTO `t_sys_dict_data` VALUES ('5334119857815824', 'sys_user_sex', '女', 'F', '1', '1', '女性', '0', '2021-02-27 13:30:14', '5332937727967504', '2021-02-28 17:45:12', '5332937727967504');
INSERT INTO `t_sys_dict_data` VALUES ('5335786358800656', 'sys_user_sex', '未知', 'U', '1', '2', '未知', '0', '2021-02-28 17:45:29', '5332937727967504', '2021-02-28 17:45:37', '5332937727967504');
INSERT INTO `t_sys_dict_data` VALUES ('5339991262986512', 'sys_yes_no', '是', 'true', '1', '0', '是', '0', '2021-03-03 17:02:56', '5332937727967504', null, null);
INSERT INTO `t_sys_dict_data` VALUES ('5339991529095440', 'sys_yes_no', '否', 'false', '1', '1', '否', '0', '2021-03-03 17:03:12', '5332937727967504', null, null);
INSERT INTO `t_sys_dict_data` VALUES ('5340017787830544', 'sys_notice_type', '通知', '1', '1', '0', '通知', '0', '2021-03-03 17:29:55', '5332937727967504', null, null);
INSERT INTO `t_sys_dict_data` VALUES ('5340018024628496', 'sys_notice_type', '公告', '2', '1', '1', '公告', '0', '2021-03-03 17:30:09', '5332937727967504', null, null);
INSERT INTO `t_sys_dict_data` VALUES ('5340025185141008', 'sys_open_close', '打开', 'true', '1', '0', '打开', '0', '2021-03-03 17:37:26', '5332937727967504', null, null);
INSERT INTO `t_sys_dict_data` VALUES ('5340025404752144', 'sys_open_close', '关闭', 'false', '1', '1', '关闭', '0', '2021-03-03 17:37:40', '5332937727967504', null, null);
INSERT INTO `t_sys_dict_data` VALUES ('5341173102231824', 'sys_success_fail', '成功', 'true', '1', '0', '成功', '0', '2021-03-04 13:05:10', '5332937727967504', null, null);
INSERT INTO `t_sys_dict_data` VALUES ('5341173320565008', 'sys_success_fail', '失败', 'false', '1', '1', '失败', '0', '2021-03-04 13:05:23', '5332937727967504', null, null);
INSERT INTO `t_sys_dict_data` VALUES ('5341458001264912', 'sms_vendor', '牛信', 'nxcloud', '1', '0', '宁波牛信', '0', '2021-03-04 17:54:58', '5332937727967504', null, null);
INSERT INTO `t_sys_dict_data` VALUES ('5341546376364304', 'sms_type', '验证码', '1', '1', '0', '验证码', '0', '2021-03-04 19:24:52', '5332937727967504', null, null);
INSERT INTO `t_sys_dict_data` VALUES ('5341546739679504', 'sms_type', '通知', '2', '1', '1', '通知', '0', '2021-03-04 19:25:15', '5332937727967504', '2021-03-06 10:25:33', '5332937727967504');
INSERT INTO `t_sys_dict_data` VALUES ('5341596549923088', 'sms_template_category', '验证码', '10', '1', '0', null, '0', '2021-03-04 20:15:55', '5332937727967504', null, null);
INSERT INTO `t_sys_dict_data` VALUES ('5341634085257488', 'sms_send_state', '待发送', '0', '1', '0', null, '0', '2021-03-04 20:54:06', '5332937727967504', null, null);
INSERT INTO `t_sys_dict_data` VALUES ('5341634278670608', 'sms_send_state', '发送成功', '1', '1', '1', null, '0', '2021-03-04 20:54:17', '5332937727967504', null, null);
INSERT INTO `t_sys_dict_data` VALUES ('5341634468970768', 'sms_send_state', '发送失败', '2', '1', '2', null, '0', '2021-03-04 20:54:29', '5332937727967504', null, null);
INSERT INTO `t_sys_dict_data` VALUES ('5343844056170768', 'sms_vendor', '创蓝', 'chuanglan', '1', '1', '创蓝253', '0', '2021-03-06 10:22:12', '5332937727967504', null, null);
INSERT INTO `t_sys_dict_data` VALUES ('5343846682509584', 'sms_vendor', '欣易辰', 'xinyichen', '1', '2', '深圳欣易辰', '0', '2021-03-06 10:24:52', '5332937727967504', null, null);
INSERT INTO `t_sys_dict_data` VALUES ('5343847680803088', 'sms_type', '营销', '3', '1', '2', '营销', '0', '2021-03-06 10:25:53', '5332937727967504', null, null);

-- ----------------------------
-- Table structure for t_sys_login_log
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_login_log`;
CREATE TABLE `t_sys_login_log` (
  `id` bigint(20) unsigned NOT NULL COMMENT '日志ID',
  `username` varchar(50) NOT NULL COMMENT '用户名称',
  `ip_addr` varchar(255) DEFAULT NULL COMMENT '登录IP',
  `location` varchar(255) DEFAULT NULL COMMENT '登录地点',
  `os` varchar(255) DEFAULT NULL COMMENT '操作系统',
  `browser` varchar(255) DEFAULT NULL COMMENT '浏览器',
  `state` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '登录状态',
  `remark` varchar(255) DEFAULT NULL COMMENT '登录备注',
  `is_deleted` tinyint(1) unsigned NOT NULL COMMENT '刪除标识',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` bigint(20) unsigned NOT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_by` bigint(20) unsigned DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统登录日志';

-- ----------------------------
-- Records of t_sys_login_log
-- ----------------------------
INSERT INTO `t_sys_login_log` VALUES ('5346878766481680', 'admin', '0:0:0:0:0:0:0:1', null, 'Windows', 'Chrome-72.0.3626.121', '1', '登录成功', '1', '2021-03-08 13:49:16', '0', '2021-03-08 18:14:48', '5332937727967504');
INSERT INTO `t_sys_login_log` VALUES ('5346878845157648', 'admin', '0:0:0:0:0:0:0:1', null, 'Mac', 'Safari-11.0', '0', '图形验证码已失效', '1', '2021-03-08 13:49:20', '0', '2021-03-08 18:14:48', '5332937727967504');
INSERT INTO `t_sys_login_log` VALUES ('5346878951112976', 'admin', '0:0:0:0:0:0:0:1', null, 'Mac', 'Safari-11.0', '1', '登录成功', '1', '2021-03-08 13:49:27', '0', '2021-03-08 18:14:48', '5332937727967504');
INSERT INTO `t_sys_login_log` VALUES ('5346882423095568', 'admin', '0:0:0:0:0:0:0:1', null, 'Mac', 'Safari-11.0', '1', '登录成功', '1', '2021-03-08 13:52:59', '0', '2021-03-08 18:14:48', '5332937727967504');
INSERT INTO `t_sys_login_log` VALUES ('5346883650486544', 'admin', '0:0:0:0:0:0:0:1', null, 'Mac', 'Safari-11.0', '1', '登录成功', '1', '2021-03-08 13:54:14', '0', '2021-03-08 18:14:48', '5332937727967504');
INSERT INTO `t_sys_login_log` VALUES ('5346900684226832', 'admin', '0:0:0:0:0:0:0:1', null, 'Mac', 'Safari-11.0', '1', '登录成功', '1', '2021-03-08 14:11:33', '0', '2021-03-08 18:14:48', '5332937727967504');
INSERT INTO `t_sys_login_log` VALUES ('5347127974330640', 'admin', '0:0:0:0:0:0:0:1', null, 'Windows', 'Chrome-72.0.3626.121', '1', '登录成功', '1', '2021-03-08 18:02:46', '0', '2021-03-08 18:14:48', '5332937727967504');
INSERT INTO `t_sys_login_log` VALUES ('5347128232001808', 'admin', '0:0:0:0:0:0:0:1', null, 'Windows', 'Chrome-72.0.3626.121', '0', '图形验证码已失效', '1', '2021-03-08 18:03:02', '0', '2021-03-08 18:14:48', '5332937727967504');
INSERT INTO `t_sys_login_log` VALUES ('5347128450973968', 'admin', '0:0:0:0:0:0:0:1', null, 'Windows', 'Chrome-72.0.3626.121', '1', '登录成功', '1', '2021-03-08 18:03:15', '0', '2021-03-08 18:14:48', '5332937727967504');
INSERT INTO `t_sys_login_log` VALUES ('5347130684735760', 'admin', '0:0:0:0:0:0:0:1', null, 'Windows', 'Chrome-72.0.3626.121', '1', '登录成功', '1', '2021-03-08 18:05:31', '0', '2021-03-08 18:14:48', '5332937727967504');
INSERT INTO `t_sys_login_log` VALUES ('5347132333359376', 'admin', '0:0:0:0:0:0:0:1', null, 'Windows', 'Chrome-72.0.3626.121', '1', '登录成功', '1', '2021-03-08 18:07:12', '0', '2021-03-08 18:14:48', '5332937727967504');
INSERT INTO `t_sys_login_log` VALUES ('5347211317641488', 'admin', '0:0:0:0:0:0:0:1', null, 'Windows', 'Chrome-72.0.3626.121', '1', '登录成功', '1', '2021-03-08 19:27:33', '5332937727967504', '2021-03-12 21:34:07', '5332937727967504');
INSERT INTO `t_sys_login_log` VALUES ('5350853835669776', 'admin', '0:0:0:0:0:0:0:1', null, 'Windows', 'Chrome-72.0.3626.121', '1', '登录成功', '1', '2021-03-11 09:12:55', '0', '2021-03-12 21:34:07', '5332937727967504');
INSERT INTO `t_sys_login_log` VALUES ('5351045957402896', 'admin', '0:0:0:0:0:0:0:1', null, 'Windows', 'Chrome-72.0.3626.121', '1', '登录成功', '1', '2021-03-11 12:28:21', '0', '2021-03-12 21:34:07', '5332937727967504');
INSERT INTO `t_sys_login_log` VALUES ('5351580506997008', 'admin', '0:0:0:0:0:0:0:1', null, 'Windows', 'Chrome-72.0.3626.121', '1', '登录成功', '1', '2021-03-11 21:32:07', '0', '2021-03-12 21:34:07', '5332937727967504');
INSERT INTO `t_sys_login_log` VALUES ('5351594933551376', 'admin', '0:0:0:0:0:0:0:1', null, 'Windows', 'Chrome-72.0.3626.121', '0', '图形验证码输入错误', '1', '2021-03-11 21:46:48', '0', '2021-03-12 21:34:07', '5332937727967504');
INSERT INTO `t_sys_login_log` VALUES ('5351595061035280', 'admin', '0:0:0:0:0:0:0:1', null, 'Windows', 'Chrome-72.0.3626.121', '0', '图形验证码输入错误', '1', '2021-03-11 21:46:55', '0', '2021-03-12 21:34:07', '5332937727967504');
INSERT INTO `t_sys_login_log` VALUES ('5351595180966160', 'admin', '0:0:0:0:0:0:0:1', null, 'Windows', 'Chrome-72.0.3626.121', '1', '登录成功', '1', '2021-03-11 21:47:03', '0', '2021-03-12 21:34:07', '5332937727967504');
INSERT INTO `t_sys_login_log` VALUES ('5352907307630864', 'admin', '0:0:0:0:0:0:0:1', null, 'Windows', 'Chrome-72.0.3626.121', '1', '登录成功', '1', '2021-03-12 20:01:49', '0', '2021-03-12 21:34:07', '5332937727967504');
INSERT INTO `t_sys_login_log` VALUES ('5352949461090576', 'admin', '0:0:0:0:0:0:0:1', null, 'Windows', 'Chrome-72.0.3626.121', '1', '登录成功', '1', '2021-03-12 20:44:41', '0', '2021-03-12 21:34:07', '5332937727967504');
INSERT INTO `t_sys_login_log` VALUES ('5353742427701520', 'admin', '0:0:0:0:0:0:0:1', null, 'Windows', 'Chrome-72.0.3626.121', '0', '图形验证码输入错误', '0', '2021-03-13 10:11:20', '0', null, null);
INSERT INTO `t_sys_login_log` VALUES ('5353742441103632', 'admin', '0:0:0:0:0:0:0:1', null, 'Windows', 'Chrome-72.0.3626.121', '0', '图形验证码输入错误', '0', '2021-03-13 10:11:21', '0', null, null);
INSERT INTO `t_sys_login_log` VALUES ('5353742448279824', 'admin', '0:0:0:0:0:0:0:1', null, 'Windows', 'Chrome-72.0.3626.121', '0', '图形验证码输入错误', '0', '2021-03-13 10:11:22', '0', null, null);
INSERT INTO `t_sys_login_log` VALUES ('5353742451441936', 'admin', '0:0:0:0:0:0:0:1', null, 'Windows', 'Chrome-72.0.3626.121', '0', '图形验证码输入错误', '0', '2021-03-13 10:11:22', '0', null, null);
INSERT INTO `t_sys_login_log` VALUES ('5353742454833424', 'admin', '0:0:0:0:0:0:0:1', null, 'Windows', 'Chrome-72.0.3626.121', '0', '图形验证码输入错误', '0', '2021-03-13 10:11:22', '0', null, null);
INSERT INTO `t_sys_login_log` VALUES ('5353742457946384', 'admin', '0:0:0:0:0:0:0:1', null, 'Windows', 'Chrome-72.0.3626.121', '0', '图形验证码输入错误', '0', '2021-03-13 10:11:22', '0', null, null);
INSERT INTO `t_sys_login_log` VALUES ('5353744085221648', 'admin', '0:0:0:0:0:0:0:1', null, 'Windows', 'Chrome-72.0.3626.121', '0', '图形验证码输入错误', '0', '2021-03-13 10:13:01', '0', null, null);
INSERT INTO `t_sys_login_log` VALUES ('5353744096411920', 'admin', '0:0:0:0:0:0:0:1', null, 'Windows', 'Chrome-72.0.3626.121', '0', '图形验证码输入错误', '0', '2021-03-13 10:13:02', '0', null, null);
INSERT INTO `t_sys_login_log` VALUES ('5353744106864912', 'admin', '0:0:0:0:0:0:0:1', null, 'Windows', 'Chrome-72.0.3626.121', '0', '图形验证码输入错误', '0', '2021-03-13 10:13:03', '0', null, null);
INSERT INTO `t_sys_login_log` VALUES ('5353744117711120', 'admin', '0:0:0:0:0:0:0:1', null, 'Windows', 'Chrome-72.0.3626.121', '0', '图形验证码输入错误', '0', '2021-03-13 10:13:03', '0', null, null);
INSERT INTO `t_sys_login_log` VALUES ('5353744127885584', 'admin', '0:0:0:0:0:0:0:1', null, 'Windows', 'Chrome-72.0.3626.121', '0', '图形验证码输入错误', '0', '2021-03-13 10:13:04', '0', null, null);
INSERT INTO `t_sys_login_log` VALUES ('5353744137519376', 'admin', '0:0:0:0:0:0:0:1', null, 'Windows', 'Chrome-72.0.3626.121', '0', '图形验证码输入错误', '0', '2021-03-13 10:13:05', '0', null, null);
INSERT INTO `t_sys_login_log` VALUES ('5353744191783184', 'admin', '0:0:0:0:0:0:0:1', null, 'Windows', 'Chrome-72.0.3626.121', '0', '图形验证码输入错误', '0', '2021-03-13 10:13:08', '0', null, null);
INSERT INTO `t_sys_login_log` VALUES ('5353744207560976', 'admin', '0:0:0:0:0:0:0:1', null, 'Windows', 'Chrome-72.0.3626.121', '0', '图形验证码输入错误', '0', '2021-03-13 10:13:09', '0', null, null);
INSERT INTO `t_sys_login_log` VALUES ('5353744211099920', 'admin', '0:0:0:0:0:0:0:1', null, 'Windows', 'Chrome-72.0.3626.121', '0', '图形验证码输入错误', '0', '2021-03-13 10:13:09', '0', null, null);
INSERT INTO `t_sys_login_log` VALUES ('5353744213934352', 'admin', '0:0:0:0:0:0:0:1', null, 'Windows', 'Chrome-72.0.3626.121', '0', '图形验证码输入错误', '0', '2021-03-13 10:13:09', '0', null, null);
INSERT INTO `t_sys_login_log` VALUES ('5353744216539408', 'admin', '0:0:0:0:0:0:0:1', null, 'Windows', 'Chrome-72.0.3626.121', '0', '图形验证码输入错误', '0', '2021-03-13 10:13:09', '0', null, null);
INSERT INTO `t_sys_login_log` VALUES ('5353746339643664', 'admin', '0:0:0:0:0:0:0:1', null, 'Windows', 'Chrome-72.0.3626.121', '1', '登录成功', '0', '2021-03-13 10:15:19', '0', null, null);
INSERT INTO `t_sys_login_log` VALUES ('5353756308783376', 'admin', '0:0:0:0:0:0:0:1', null, 'Windows', 'Chrome-72.0.3626.121', '1', '登录成功', '0', '2021-03-13 10:25:27', '0', null, null);
INSERT INTO `t_sys_login_log` VALUES ('5353756446048528', 'admin', '0:0:0:0:0:0:0:1', null, 'Windows', 'Chrome-72.0.3626.121', '0', '图形验证码输入错误', '0', '2021-03-13 10:25:36', '0', null, null);
INSERT INTO `t_sys_login_log` VALUES ('5353756571762960', 'admin', '0:0:0:0:0:0:0:1', null, 'Windows', 'Chrome-72.0.3626.121', '1', '登录成功', '0', '2021-03-13 10:25:44', '0', null, null);
INSERT INTO `t_sys_login_log` VALUES ('5353758046224656', 'admin', '0:0:0:0:0:0:0:1', null, 'Windows', 'Chrome-72.0.3626.121', '1', '登录成功', '0', '2021-03-13 10:27:14', '0', null, null);
INSERT INTO `t_sys_login_log` VALUES ('5353758154735888', 'admin', '0:0:0:0:0:0:0:1', null, 'Windows', 'Chrome-72.0.3626.121', '0', '图形验证码输入错误', '0', '2021-03-13 10:27:20', '0', null, null);
INSERT INTO `t_sys_login_log` VALUES ('5353758306517264', 'admin', '0:0:0:0:0:0:0:1', null, 'Windows', 'Chrome-72.0.3626.121', '1', '登录成功', '0', '2021-03-13 10:27:29', '0', null, null);
INSERT INTO `t_sys_login_log` VALUES ('5353759915819280', 'admin', '0:0:0:0:0:0:0:1', null, 'Windows', 'Chrome-72.0.3626.121', '1', '登录成功', '0', '2021-03-13 10:29:08', '0', null, null);

-- ----------------------------
-- Table structure for t_sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_menu`;
CREATE TABLE `t_sys_menu` (
  `id` bigint(20) unsigned NOT NULL COMMENT '菜单ID',
  `menu_type` char(1) NOT NULL COMMENT '菜单类型',
  `menu_name` varchar(100) NOT NULL COMMENT '菜单名称',
  `menu_code` varchar(50) DEFAULT NULL COMMENT '菜单编码',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `path` varchar(255) DEFAULT NULL COMMENT '路由地址',
  `component` varchar(100) DEFAULT NULL COMMENT '组件路径',
  `sort` int(11) DEFAULT '0' COMMENT '菜单排序',
  `is_frame` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否外链',
  `is_cache` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否缓存',
  `is_visible` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '是否显示',
  `parent_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '父ID',
  `state` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '启用状态',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `is_deleted` tinyint(1) unsigned NOT NULL COMMENT '刪除标识',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` bigint(20) unsigned NOT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_by` bigint(20) unsigned DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统菜单';

-- ----------------------------
-- Records of t_sys_menu
-- ----------------------------
INSERT INTO `t_sys_menu` VALUES ('5332945038803216', 'M', '系统管理', '', 'system', 'system', null, '1', '0', '0', '1', '0', '1', '', '0', '2021-02-26 17:35:08', '5327553293336672', '2021-03-04 17:09:25', '5332937727967504');
INSERT INTO `t_sys_menu` VALUES ('5332957116924176', 'C', '用户管理', 'sys:user:list', 'user', 'user', 'system/user/index', '0', '0', '1', '1', '5332945038803216', '1', '', '0', '2021-02-26 17:47:26', '5332937727967504', null, null);
INSERT INTO `t_sys_menu` VALUES ('5332959185928464', 'C', '角色管理', 'sys:role:list', 'peoples', 'role', 'system/role/index', '1', '0', '1', '1', '5332945038803216', '1', '', '0', '2021-02-26 17:49:32', '5332937727967504', null, null);
INSERT INTO `t_sys_menu` VALUES ('5332960791970064', 'C', '菜单管理', 'sys:menu:list', 'tree-table', 'menu', 'system/menu/index', '2', '0', '1', '1', '5332945038803216', '1', '', '0', '2021-02-26 17:51:10', '5332937727967504', null, null);
INSERT INTO `t_sys_menu` VALUES ('5333919769231632', 'C', '字典管理', 'system:dict:list', 'dict', 'dict', 'system/dict/index', '3', '0', '1', '1', '5332945038803216', '1', '', '0', '2021-02-27 10:06:41', '5332937727967504', null, null);
INSERT INTO `t_sys_menu` VALUES ('5335533073318160', 'C', '部门管理', 'system:dept:list', 'tree', 'dept', 'system/dept/index', '4', '0', '1', '1', '5332945038803216', '1', null, '0', '2021-02-28 13:27:49', '5332937727967504', null, null);
INSERT INTO `t_sys_menu` VALUES ('5336084539113744', 'C', '岗位管理', 'system:post:list', 'post', 'post', 'system/post/index', '5', '0', '1', '1', '5332945038803216', '1', null, '0', '2021-02-28 22:48:48', '5332937727967504', null, null);
INSERT INTO `t_sys_menu` VALUES ('5339994553434384', 'C', '参数设置', 'system:config:list', 'edit', 'config', 'system/config/index', '6', '0', '1', '1', '5332945038803216', '1', null, '0', '2021-03-03 17:06:17', '5332937727967504', null, null);
INSERT INTO `t_sys_menu` VALUES ('5340010427466000', 'C', '通知公告', 'system:notice:list', 'message', 'message', 'system/notice/index', '7', '0', '1', '1', '5332945038803216', '1', null, '0', '2021-03-03 17:22:25', '5332937727967504', null, null);
INSERT INTO `t_sys_menu` VALUES ('5341076872495376', 'F', '新增', 'system:user:add', null, null, null, '0', '0', '1', '1', '5332957116924176', '1', null, '0', '2021-03-04 11:27:16', '5332937727967504', null, null);
INSERT INTO `t_sys_menu` VALUES ('5341077229830416', 'F', '修改', 'system:user:edit', null, null, null, '1', '0', '1', '1', '5332957116924176', '1', null, '0', '2021-03-04 11:27:38', '5332937727967504', null, null);
INSERT INTO `t_sys_menu` VALUES ('5341077809529104', 'F', '删除', 'system:user:remove', null, null, null, '2', '0', '1', '1', '5332957116924176', '1', null, '0', '2021-03-04 11:28:13', '5332937727967504', null, null);
INSERT INTO `t_sys_menu` VALUES ('5341078368141584', 'F', '重置密码', 'system:user:resetPwd', null, null, null, '3', '0', '1', '1', '5332957116924176', '1', null, '0', '2021-03-04 11:28:47', '5332937727967504', null, null);
INSERT INTO `t_sys_menu` VALUES ('5341079027728656', 'F', '新增', 'system:role:add', null, null, null, '0', '0', '1', '1', '5332959185928464', '1', null, '0', '2021-03-04 11:29:28', '5332937727967504', null, null);
INSERT INTO `t_sys_menu` VALUES ('5341079293116688', 'F', '修改', 'system:role:edit', null, null, null, '1', '0', '1', '1', '5332959185928464', '1', null, '0', '2021-03-04 11:29:44', '5332937727967504', '2021-03-04 11:32:30', '5332937727967504');
INSERT INTO `t_sys_menu` VALUES ('5341082417316112', 'F', '删除', 'system:role:remove', null, null, null, '2', '0', '1', '1', '5332959185928464', '1', null, '0', '2021-03-04 11:32:55', '5332937727967504', null, null);
INSERT INTO `t_sys_menu` VALUES ('5341082912538896', 'F', '新增', 'system:menu:add', null, null, null, '0', '0', '1', '1', '5332960791970064', '1', null, '0', '2021-03-04 11:33:25', '5332937727967504', null, null);
INSERT INTO `t_sys_menu` VALUES ('5341083215462672', 'F', '修改', 'system:menu:edit', null, null, null, '0', '0', '1', '1', '5332960791970064', '1', null, '0', '2021-03-04 11:33:43', '5332937727967504', null, null);
INSERT INTO `t_sys_menu` VALUES ('5341083549810960', 'F', '删除', 'system:menu:remove', null, null, null, '2', '0', '1', '1', '5332960791970064', '1', null, '0', '2021-03-04 11:34:04', '5332937727967504', null, null);
INSERT INTO `t_sys_menu` VALUES ('5341084066152720', 'F', '新增', 'system:dept:add', null, null, null, '0', '0', '1', '1', '5335533073318160', '1', null, '0', '2021-03-04 11:34:35', '5332937727967504', null, null);
INSERT INTO `t_sys_menu` VALUES ('5341084310176016', 'F', '修改', 'system:dept:edit', null, null, null, '1', '0', '1', '1', '5335533073318160', '1', null, '0', '2021-03-04 11:34:50', '5332937727967504', null, null);
INSERT INTO `t_sys_menu` VALUES ('5341084810510608', 'F', '删除', 'system:dept:remove', null, null, null, '2', '0', '1', '1', '5335533073318160', '1', null, '0', '2021-03-04 11:35:21', '5332937727967504', '2021-03-04 11:35:29', '5332937727967504');
INSERT INTO `t_sys_menu` VALUES ('5341085316841744', 'F', '新增', 'system:post:add', null, null, null, '0', '0', '1', '1', '5336084539113744', '1', null, '0', '2021-03-04 11:35:52', '5332937727967504', null, null);
INSERT INTO `t_sys_menu` VALUES ('5341085601218832', 'F', '修改', 'system:post:edit', null, null, null, '1', '0', '1', '1', '5336084539113744', '1', null, '0', '2021-03-04 11:36:09', '5332937727967504', null, null);
INSERT INTO `t_sys_menu` VALUES ('5341085920788752', 'F', '删除', 'system:post:remove', null, null, null, '2', '0', '1', '1', '5336084539113744', '1', null, '0', '2021-03-04 11:36:28', '5332937727967504', null, null);
INSERT INTO `t_sys_menu` VALUES ('5341086608539920', 'F', '新增', 'system:dict:add', null, null, null, '0', '0', '1', '1', '5333919769231632', '1', null, '0', '2021-03-04 11:37:10', '5332937727967504', null, null);
INSERT INTO `t_sys_menu` VALUES ('5341086882857232', 'F', '修改', 'system:dict:edit', null, null, null, '1', '0', '1', '1', '5333919769231632', '1', null, '0', '2021-03-04 11:37:27', '5332937727967504', null, null);
INSERT INTO `t_sys_menu` VALUES ('5341087170494736', 'F', '删除', 'system:dict:remove', null, null, null, '2', '0', '1', '1', '5333919769231632', '1', null, '0', '2021-03-04 11:37:45', '5332937727967504', null, null);
INSERT INTO `t_sys_menu` VALUES ('5341087508840720', 'F', '新增', 'system:config:add', null, null, null, '0', '0', '1', '1', '5339994553434384', '1', null, '0', '2021-03-04 11:38:05', '5332937727967504', null, null);
INSERT INTO `t_sys_menu` VALUES ('5341087811371280', 'F', '修改', 'system:config:edit', null, null, null, '1', '0', '1', '1', '5339994553434384', '1', null, '0', '2021-03-04 11:38:24', '5332937727967504', null, null);
INSERT INTO `t_sys_menu` VALUES ('5341088066453776', 'F', '删除', 'system:config:remove', null, null, null, '2', '0', '1', '1', '5339994553434384', '1', null, '0', '2021-03-04 11:38:39', '5332937727967504', null, null);
INSERT INTO `t_sys_menu` VALUES ('5341088416792848', 'F', '新增', 'system:notice:add', null, null, null, '0', '0', '1', '1', '5340010427466000', '1', null, '0', '2021-03-04 11:39:01', '5332937727967504', null, null);
INSERT INTO `t_sys_menu` VALUES ('5341088753647888', 'F', '修改', 'system:notice:edit', null, null, null, '1', '0', '1', '1', '5340010427466000', '1', null, '0', '2021-03-04 11:39:21', '5332937727967504', null, null);
INSERT INTO `t_sys_menu` VALUES ('5341089083785488', 'F', '删除', 'system:notice:remove', null, null, null, '2', '0', '1', '1', '5340010427466000', '1', null, '0', '2021-03-04 11:39:41', '5332937727967504', null, null);
INSERT INTO `t_sys_menu` VALUES ('5341151637651728', 'M', '日志管理', null, 'log', 'log', null, '8', '0', '1', '1', '5332945038803216', '1', null, '0', '2021-03-04 12:43:19', '5340056513364240', '2021-03-04 14:13:49', '5332937727967504');
INSERT INTO `t_sys_menu` VALUES ('5341153385185552', 'C', '登录日志', 'system:logininfor:list', 'logininfor', 'logininfor', 'system/logininfor/index', '0', '0', '1', '1', '5341151637651728', '1', null, '0', '2021-03-04 12:45:06', '5340056513364240', '2021-03-04 12:54:26', '5332937727967504');
INSERT INTO `t_sys_menu` VALUES ('5341283194011920', 'F', '删除', 'system:logininfor:remove', null, null, null, '0', '0', '1', '1', '5341153385185552', '1', null, '0', '2021-03-04 14:57:09', '5332937727967504', null, null);
INSERT INTO `t_sys_menu` VALUES ('5341413098127632', 'M', '短信管理', null, 'email', 'sms', null, '0', '0', '1', '1', '0', '1', null, '0', '2021-03-04 17:09:18', '5332937727967504', null, null);
INSERT INTO `t_sys_menu` VALUES ('5341414871187728', 'C', '短信通道', 'sms:channel:list', 'component', 'channel', 'sms/channel/index', '0', '0', '1', '1', '5341413098127632', '1', null, '0', '2021-03-04 17:11:06', '5332937727967504', '2021-03-04 17:16:04', '5332937727967504');
INSERT INTO `t_sys_menu` VALUES ('5341416054079760', 'C', '短信模板', 'sms:template:list', 'clipboard', 'template', 'sms/template/index', '1', '0', '1', '1', '5341413098127632', '1', null, '0', '2021-03-04 17:12:18', '5332937727967504', '2021-03-04 17:16:10', '5332937727967504');
INSERT INTO `t_sys_menu` VALUES ('5341417680666896', 'C', '发送记录', 'sms:task:list', 'log', 'task', 'sms/task/index', '2', '0', '1', '1', '5341413098127632', '1', null, '0', '2021-03-04 17:13:57', '5332937727967504', '2021-03-04 17:16:16', '5332937727967504');
INSERT INTO `t_sys_menu` VALUES ('5342934061908240', 'C', '技术文档', null, 'documentation', 'http://localhost:8080/doc.html', null, '99', '1', '0', '1', '0', '1', null, '0', '2021-03-05 18:56:30', '5332937727967504', '2021-03-05 18:58:37', '5332937727967504');
INSERT INTO `t_sys_menu` VALUES ('5342940047425808', 'F', '新增', 'sms:channel:add', null, null, null, '0', '0', '1', '1', '5341414871187728', '1', null, '0', '2021-03-05 19:02:35', '5332937727967504', null, null);
INSERT INTO `t_sys_menu` VALUES ('5342940390523152', 'F', '修改', 'sms:channel:edit', null, null, null, '1', '0', '1', '1', '5341414871187728', '1', null, '0', '2021-03-05 19:02:56', '5332937727967504', null, null);
INSERT INTO `t_sys_menu` VALUES ('5342940753461520', 'F', '删除', 'sms:channel:remove', null, null, null, '2', '0', '1', '1', '5341414871187728', '1', null, '0', '2021-03-05 19:03:18', '5332937727967504', null, null);
INSERT INTO `t_sys_menu` VALUES ('5342941269655824', 'F', '新增', 'sms:template:add', null, null, null, '0', '0', '1', '1', '5341416054079760', '1', null, '0', '2021-03-05 19:03:50', '5332937727967504', null, null);
INSERT INTO `t_sys_menu` VALUES ('5342941832823056', 'F', '修改', 'sms:template:edit', null, null, null, '1', '0', '1', '1', '5341416054079760', '1', null, '0', '2021-03-05 19:04:24', '5332937727967504', null, null);
INSERT INTO `t_sys_menu` VALUES ('5342942400119056', 'F', '删除', 'sms:template:remove', null, null, null, '2', '0', '1', '1', '5341416054079760', '1', null, '0', '2021-03-05 19:04:59', '5332937727967504', null, null);
INSERT INTO `t_sys_menu` VALUES ('5342942866735376', 'F', '删除', 'sms:task:remove', null, null, null, '2', '0', '1', '1', '5341417680666896', '1', null, '0', '2021-03-05 19:05:27', '5332937727967504', null, null);

-- ----------------------------
-- Table structure for t_sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_notice`;
CREATE TABLE `t_sys_notice` (
  `id` bigint(20) unsigned NOT NULL COMMENT '通知ID',
  `notice_title` varchar(100) NOT NULL COMMENT '通知标题',
  `notice_content` text COMMENT '通知内容',
  `notice_type` int(11) unsigned NOT NULL COMMENT '通知类型',
  `state` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '启用状态',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `is_deleted` tinyint(1) unsigned NOT NULL COMMENT '刪除标识',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` bigint(20) unsigned NOT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_by` bigint(20) unsigned DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统通知公告';

-- ----------------------------
-- Records of t_sys_notice
-- ----------------------------
INSERT INTO `t_sys_notice` VALUES ('5340033074790672', '发工资了', '<p><strong>发工资了！！！！！！！！顶顶顶顶</strong></p>', '2', '1', null, '0', '2021-03-03 17:45:28', '5332937727967504', '2021-03-04 14:52:11', '5332937727967504');

-- ----------------------------
-- Table structure for t_sys_post
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_post`;
CREATE TABLE `t_sys_post` (
  `id` bigint(20) unsigned NOT NULL COMMENT '岗位ID',
  `post_name` varchar(100) NOT NULL COMMENT '岗位名称',
  `post_code` varchar(50) DEFAULT NULL COMMENT '岗位编码',
  `sort` int(11) DEFAULT '0' COMMENT '岗位排序',
  `state` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '启用状态',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `is_deleted` tinyint(1) unsigned NOT NULL COMMENT '刪除标识',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` bigint(20) unsigned NOT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_by` bigint(20) unsigned DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统岗位';

-- ----------------------------
-- Records of t_sys_post
-- ----------------------------
INSERT INTO `t_sys_post` VALUES ('5336094356685072', '首席执行官', 'ceo', '0', '1', '首席执行官', '0', '2021-02-28 22:58:47', '5332937727967504', null, null);
INSERT INTO `t_sys_post` VALUES ('5336096562905360', '首席技术管', 'cto', '1', '1', '技术总监', '0', '2021-02-28 23:01:02', '5332937727967504', null, null);
INSERT INTO `t_sys_post` VALUES ('5340058189562128', '销售员', 'sale', '2', '1', null, '0', '2021-03-03 18:11:01', '5332937727967504', '2021-03-04 14:52:25', '5332937727967504');
INSERT INTO `t_sys_post` VALUES ('5350911510348048', 'Java开发', 'java', '3', '1', null, '0', '2021-03-11 10:11:35', '5332937727967504', null, null);

-- ----------------------------
-- Table structure for t_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role`;
CREATE TABLE `t_sys_role` (
  `id` bigint(20) unsigned NOT NULL COMMENT '角色ID',
  `role_name` varchar(100) NOT NULL COMMENT '角色名称',
  `role_code` varchar(50) NOT NULL COMMENT '角色编码',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '角色排序',
  `state` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '启用状态',
  `data_scope` int(11) unsigned NOT NULL COMMENT '数据权限范围',
  `is_admin` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否超管',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `is_deleted` tinyint(1) unsigned NOT NULL COMMENT '刪除标识',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` bigint(20) unsigned NOT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_by` bigint(20) unsigned DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统角色';

-- ----------------------------
-- Records of t_sys_role
-- ----------------------------
INSERT INTO `t_sys_role` VALUES ('5332894561632528', '超级管理员', 'admin', '0', '1', '1', '1', '', '0', '2021-02-26 16:43:47', '5327553293336672', '2021-03-05 18:56:58', '5332937727967504');
INSERT INTO `t_sys_role` VALUES ('5334305691713808', '销售员', 'sale', '1', '1', '2', '0', '销售员', '0', '2021-02-27 16:39:16', '5332937727967504', '2021-03-05 19:00:22', '5332937727967504');
INSERT INTO `t_sys_role` VALUES ('5341094230753552', '技术支持', 'support', '2', '1', '1', '0', null, '0', '2021-03-04 11:44:56', '5332937727967504', '2021-03-05 19:00:35', '5332937727967504');

-- ----------------------------
-- Table structure for t_sys_role_dept
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role_dept`;
CREATE TABLE `t_sys_role_dept` (
  `id` bigint(20) unsigned NOT NULL COMMENT 'ID',
  `role_id` bigint(20) unsigned NOT NULL,
  `dept_id` bigint(20) unsigned NOT NULL,
  `is_deleted` tinyint(1) unsigned NOT NULL COMMENT '刪除标识',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` bigint(20) unsigned NOT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_by` bigint(20) unsigned DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统角色部门数据权限';

-- ----------------------------
-- Records of t_sys_role_dept
-- ----------------------------
INSERT INTO `t_sys_role_dept` VALUES ('5340300886573328', '5334305691713808', '5335602208948496', '0', '2021-03-03 22:17:54', '5332937727967504', null, null);
INSERT INTO `t_sys_role_dept` VALUES ('5340300886573329', '5334305691713808', '5335686809747728', '0', '2021-03-03 22:17:54', '5332937727967504', null, null);

-- ----------------------------
-- Table structure for t_sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_role_menu`;
CREATE TABLE `t_sys_role_menu` (
  `id` bigint(20) unsigned NOT NULL,
  `role_id` bigint(20) unsigned NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) unsigned NOT NULL COMMENT '菜单ID',
  `is_deleted` tinyint(1) unsigned NOT NULL COMMENT '刪除标识',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` bigint(20) unsigned NOT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_by` bigint(20) unsigned DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统角色菜单';

-- ----------------------------
-- Records of t_sys_role_menu
-- ----------------------------
INSERT INTO `t_sys_role_menu` VALUES ('5333073365664016', '5332894561632528', '5332945038803216', '1', '2021-02-26 19:45:41', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5333073365713168', '5332894561632528', '5332957116924176', '1', '2021-02-26 19:45:41', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5333073365713169', '5332894561632528', '5332959185928464', '1', '2021-02-26 19:45:41', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5333073365713170', '5332894561632528', '5332960791970064', '1', '2021-02-26 19:45:41', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5333073365713171', '5332894561632528', '5333919769231632', '1', '2021-02-26 19:45:42', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5334326796009744', '5334305691713808', '5332945038803216', '1', '2021-02-27 17:00:44', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5334326796026128', '5334305691713808', '5332957116924176', '1', '2021-02-27 17:00:44', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5334326796026129', '5334305691713808', '5332959185928464', '1', '2021-02-27 17:00:44', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5334326796026130', '5334305691713808', '5332960791970064', '1', '2021-02-27 17:00:44', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5334326796026131', '5334305691713808', '5333919769231632', '1', '2021-02-27 17:00:44', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5335534172897552', '5332894561632528', '5332945038803216', '1', '2021-02-28 13:28:57', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5335534172897553', '5332894561632528', '5332957116924176', '1', '2021-02-28 13:28:57', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5335534172897554', '5332894561632528', '5332959185928464', '1', '2021-02-28 13:28:57', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5335534172897555', '5332894561632528', '5332960791970064', '1', '2021-02-28 13:28:57', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5335534172897556', '5332894561632528', '5333919769231632', '1', '2021-02-28 13:28:57', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5335534172897557', '5332894561632528', '5335533073318160', '1', '2021-02-28 13:28:57', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5335534360740112', '5334305691713808', '5332945038803216', '1', '2021-02-28 13:29:08', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5335534360756496', '5334305691713808', '5332957116924176', '1', '2021-02-28 13:29:08', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5335534360756497', '5334305691713808', '5332959185928464', '1', '2021-02-28 13:29:08', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5335534360756498', '5334305691713808', '5332960791970064', '1', '2021-02-28 13:29:08', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5335534360756499', '5334305691713808', '5333919769231632', '1', '2021-02-28 13:29:08', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5335534360756500', '5334305691713808', '5335533073318160', '1', '2021-02-28 13:29:08', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5335829230240016', '5334305691713808', '5332945038803216', '1', '2021-02-28 18:29:05', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5335829230289168', '5334305691713808', '5332957116924176', '1', '2021-02-28 18:29:05', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5335829230289169', '5334305691713808', '5332959185928464', '1', '2021-02-28 18:29:05', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5335829230289170', '5334305691713808', '5332960791970064', '1', '2021-02-28 18:29:05', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5335829230289171', '5334305691713808', '5333919769231632', '1', '2021-02-28 18:29:05', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5335829230305552', '5334305691713808', '5335533073318160', '1', '2021-02-28 18:29:05', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5336084955218192', '5332894561632528', '5332945038803216', '1', '2021-02-28 22:49:14', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5336084955218193', '5332894561632528', '5332957116924176', '1', '2021-02-28 22:49:14', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5336084955218194', '5332894561632528', '5332959185928464', '1', '2021-02-28 22:49:14', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5336084955218195', '5332894561632528', '5332960791970064', '1', '2021-02-28 22:49:14', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5336084955218196', '5332894561632528', '5333919769231632', '1', '2021-02-28 22:49:14', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5336084955234576', '5332894561632528', '5335533073318160', '1', '2021-02-28 22:49:14', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5336084955234577', '5332894561632528', '5336084539113744', '1', '2021-02-28 22:49:14', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5339994793984272', '5332894561632528', '5332945038803216', '1', '2021-03-03 17:06:31', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5339994793984273', '5332894561632528', '5332957116924176', '1', '2021-03-03 17:06:31', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5339994793984274', '5332894561632528', '5332959185928464', '1', '2021-03-03 17:06:31', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5339994793984275', '5332894561632528', '5332960791970064', '1', '2021-03-03 17:06:31', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5339994794000656', '5332894561632528', '5333919769231632', '1', '2021-03-03 17:06:31', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5339994794000657', '5332894561632528', '5335533073318160', '1', '2021-03-03 17:06:31', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5339994794000658', '5332894561632528', '5336084539113744', '1', '2021-03-03 17:06:31', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5339994794000659', '5332894561632528', '5339994553434384', '1', '2021-03-03 17:06:31', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5340032435192080', '5332894561632528', '5332945038803216', '1', '2021-03-03 17:44:49', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5340032435192081', '5332894561632528', '5332957116924176', '1', '2021-03-03 17:44:49', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5340032435192082', '5332894561632528', '5332959185928464', '1', '2021-03-03 17:44:49', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5340032435192083', '5332894561632528', '5332960791970064', '1', '2021-03-03 17:44:49', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5340032435208464', '5332894561632528', '5333919769231632', '1', '2021-03-03 17:44:49', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5340032435208465', '5332894561632528', '5335533073318160', '1', '2021-03-03 17:44:49', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5340032435208466', '5332894561632528', '5336084539113744', '1', '2021-03-03 17:44:49', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5340032435208467', '5332894561632528', '5339994553434384', '1', '2021-03-03 17:44:49', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5340032435208468', '5332894561632528', '5340010427466000', '1', '2021-03-03 17:44:49', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341091440509200', '5334305691713808', '5332945038803216', '1', '2021-03-04 11:42:05', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341091440509201', '5334305691713808', '5340010427466000', '1', '2021-03-04 11:42:05', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341091440509202', '5334305691713808', '5341088416792848', '1', '2021-03-04 11:42:05', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341091440525584', '5334305691713808', '5341088753647888', '1', '2021-03-04 11:42:05', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341091440525585', '5334305691713808', '5341089083785488', '1', '2021-03-04 11:42:05', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341094230950160', '5341094230753552', '5332945038803216', '1', '2021-03-04 11:44:56', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341094230950161', '5341094230753552', '5332957116924176', '1', '2021-03-04 11:44:56', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341094230966544', '5341094230753552', '5332959185928464', '1', '2021-03-04 11:44:56', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341094230966545', '5341094230753552', '5332960791970064', '1', '2021-03-04 11:44:56', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341094230966546', '5341094230753552', '5333919769231632', '1', '2021-03-04 11:44:56', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341094230966547', '5341094230753552', '5335533073318160', '1', '2021-03-04 11:44:56', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341094230966548', '5341094230753552', '5336084539113744', '1', '2021-03-04 11:44:56', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341094230966549', '5341094230753552', '5339994553434384', '1', '2021-03-04 11:44:56', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341094230966550', '5341094230753552', '5340010427466000', '1', '2021-03-04 11:44:56', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341094230982928', '5341094230753552', '5341076872495376', '1', '2021-03-04 11:44:56', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341094230982929', '5341094230753552', '5341077229830416', '1', '2021-03-04 11:44:56', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341094230982930', '5341094230753552', '5341077809529104', '1', '2021-03-04 11:44:56', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341094230982931', '5341094230753552', '5341078368141584', '1', '2021-03-04 11:44:56', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341094230982932', '5341094230753552', '5341079027728656', '1', '2021-03-04 11:44:56', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341094230982933', '5341094230753552', '5341079293116688', '1', '2021-03-04 11:44:56', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341094230982934', '5341094230753552', '5341082417316112', '1', '2021-03-04 11:44:56', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341094230999312', '5341094230753552', '5341082912538896', '1', '2021-03-04 11:44:56', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341094230999313', '5341094230753552', '5341083215462672', '1', '2021-03-04 11:44:56', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341094230999314', '5341094230753552', '5341083549810960', '1', '2021-03-04 11:44:56', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341094230999315', '5341094230753552', '5341084066152720', '1', '2021-03-04 11:44:56', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341094230999316', '5341094230753552', '5341084310176016', '1', '2021-03-04 11:44:56', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341094230999317', '5341094230753552', '5341084810510608', '1', '2021-03-04 11:44:56', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341094231015696', '5341094230753552', '5341085316841744', '1', '2021-03-04 11:44:56', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341094231015697', '5341094230753552', '5341085601218832', '1', '2021-03-04 11:44:56', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341094231015698', '5341094230753552', '5341085920788752', '1', '2021-03-04 11:44:56', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341094231015699', '5341094230753552', '5341086608539920', '1', '2021-03-04 11:44:56', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341094231015700', '5341094230753552', '5341086882857232', '1', '2021-03-04 11:44:56', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341094231015701', '5341094230753552', '5341087170494736', '1', '2021-03-04 11:44:56', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341094231015702', '5341094230753552', '5341087508840720', '1', '2021-03-04 11:44:56', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341094231032080', '5341094230753552', '5341087811371280', '1', '2021-03-04 11:44:56', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341094231032081', '5341094230753552', '5341088066453776', '1', '2021-03-04 11:44:56', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341094231032082', '5341094230753552', '5341088416792848', '1', '2021-03-04 11:44:56', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341094231032083', '5341094230753552', '5341088753647888', '1', '2021-03-04 11:44:56', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341094231032084', '5341094230753552', '5341089083785488', '1', '2021-03-04 11:44:56', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341153675215120', '5332894561632528', '5332945038803216', '1', '2021-03-04 12:45:24', '5340056513364240', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341153675215121', '5332894561632528', '5332957116924176', '1', '2021-03-04 12:45:24', '5340056513364240', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341153675215122', '5332894561632528', '5332959185928464', '1', '2021-03-04 12:45:24', '5340056513364240', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341153675215123', '5332894561632528', '5332960791970064', '1', '2021-03-04 12:45:24', '5340056513364240', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341153675215124', '5332894561632528', '5333919769231632', '1', '2021-03-04 12:45:24', '5340056513364240', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341153675215125', '5332894561632528', '5335533073318160', '1', '2021-03-04 12:45:24', '5340056513364240', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341153675215126', '5332894561632528', '5336084539113744', '1', '2021-03-04 12:45:24', '5340056513364240', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341153675231504', '5332894561632528', '5339994553434384', '1', '2021-03-04 12:45:24', '5340056513364240', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341153675231505', '5332894561632528', '5340010427466000', '1', '2021-03-04 12:45:24', '5340056513364240', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341153675231506', '5332894561632528', '5341151637651728', '1', '2021-03-04 12:45:24', '5340056513364240', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341154559983888', '5332894561632528', '5332945038803216', '1', '2021-03-04 12:46:18', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341154559983889', '5332894561632528', '5332957116924176', '1', '2021-03-04 12:46:18', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341154559983890', '5332894561632528', '5332959185928464', '1', '2021-03-04 12:46:18', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341154560000272', '5332894561632528', '5332960791970064', '1', '2021-03-04 12:46:18', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341154560000273', '5332894561632528', '5333919769231632', '1', '2021-03-04 12:46:18', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341154560000274', '5332894561632528', '5335533073318160', '1', '2021-03-04 12:46:18', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341154560000275', '5332894561632528', '5336084539113744', '1', '2021-03-04 12:46:18', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341154560000276', '5332894561632528', '5339994553434384', '1', '2021-03-04 12:46:18', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341154560000277', '5332894561632528', '5340010427466000', '1', '2021-03-04 12:46:18', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341154560016656', '5332894561632528', '5341151637651728', '1', '2021-03-04 12:46:18', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341154560016657', '5332894561632528', '5341153385185552', '1', '2021-03-04 12:46:18', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341283808755984', '5332894561632528', '5332945038803216', '1', '2021-03-04 14:57:47', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341283808755985', '5332894561632528', '5332957116924176', '1', '2021-03-04 14:57:47', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341283808755986', '5332894561632528', '5332959185928464', '1', '2021-03-04 14:57:47', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341283808755987', '5332894561632528', '5332960791970064', '1', '2021-03-04 14:57:47', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341283808772368', '5332894561632528', '5333919769231632', '1', '2021-03-04 14:57:47', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341283808772369', '5332894561632528', '5335533073318160', '1', '2021-03-04 14:57:47', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341283808772370', '5332894561632528', '5336084539113744', '1', '2021-03-04 14:57:47', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341283808772371', '5332894561632528', '5339994553434384', '1', '2021-03-04 14:57:47', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341283808772372', '5332894561632528', '5340010427466000', '1', '2021-03-04 14:57:47', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341283808772373', '5332894561632528', '5341151637651728', '1', '2021-03-04 14:57:47', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341283808772374', '5332894561632528', '5341153385185552', '1', '2021-03-04 14:57:47', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341283808788752', '5332894561632528', '5341283194011920', '1', '2021-03-04 14:57:47', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341284231528720', '5341094230753552', '5332945038803216', '1', '2021-03-04 14:58:12', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341284231528721', '5341094230753552', '5332957116924176', '1', '2021-03-04 14:58:12', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341284231528722', '5341094230753552', '5332959185928464', '1', '2021-03-04 14:58:12', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341284231528723', '5341094230753552', '5332960791970064', '1', '2021-03-04 14:58:12', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341284231545104', '5341094230753552', '5333919769231632', '1', '2021-03-04 14:58:12', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341284231545105', '5341094230753552', '5335533073318160', '1', '2021-03-04 14:58:12', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341284231545106', '5341094230753552', '5336084539113744', '1', '2021-03-04 14:58:12', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341284231545107', '5341094230753552', '5339994553434384', '1', '2021-03-04 14:58:12', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341284231545108', '5341094230753552', '5340010427466000', '1', '2021-03-04 14:58:12', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341284231545109', '5341094230753552', '5341076872495376', '1', '2021-03-04 14:58:12', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341284231561488', '5341094230753552', '5341077229830416', '1', '2021-03-04 14:58:12', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341284231561489', '5341094230753552', '5341077809529104', '1', '2021-03-04 14:58:12', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341284231561490', '5341094230753552', '5341078368141584', '1', '2021-03-04 14:58:12', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341284231561491', '5341094230753552', '5341079027728656', '1', '2021-03-04 14:58:12', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341284231561492', '5341094230753552', '5341079293116688', '1', '2021-03-04 14:58:12', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341284231561493', '5341094230753552', '5341082417316112', '1', '2021-03-04 14:58:12', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341284231577872', '5341094230753552', '5341082912538896', '1', '2021-03-04 14:58:12', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341284231577873', '5341094230753552', '5341083215462672', '1', '2021-03-04 14:58:12', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341284231577874', '5341094230753552', '5341083549810960', '1', '2021-03-04 14:58:12', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341284231577875', '5341094230753552', '5341084066152720', '1', '2021-03-04 14:58:12', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341284231577876', '5341094230753552', '5341084310176016', '1', '2021-03-04 14:58:12', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341284231577877', '5341094230753552', '5341084810510608', '1', '2021-03-04 14:58:12', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341284231577878', '5341094230753552', '5341085316841744', '1', '2021-03-04 14:58:12', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341284231594256', '5341094230753552', '5341085601218832', '1', '2021-03-04 14:58:12', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341284231594257', '5341094230753552', '5341085920788752', '1', '2021-03-04 14:58:12', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341284231594258', '5341094230753552', '5341086608539920', '1', '2021-03-04 14:58:12', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341284231594259', '5341094230753552', '5341086882857232', '1', '2021-03-04 14:58:12', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341284231594260', '5341094230753552', '5341087170494736', '1', '2021-03-04 14:58:12', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341284231594261', '5341094230753552', '5341087508840720', '1', '2021-03-04 14:58:12', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341284231610640', '5341094230753552', '5341087811371280', '1', '2021-03-04 14:58:12', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341284231610641', '5341094230753552', '5341088066453776', '1', '2021-03-04 14:58:12', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341284231610642', '5341094230753552', '5341088416792848', '1', '2021-03-04 14:58:12', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341284231610643', '5341094230753552', '5341088753647888', '1', '2021-03-04 14:58:12', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341284231610644', '5341094230753552', '5341089083785488', '1', '2021-03-04 14:58:12', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341284231610645', '5341094230753552', '5341151637651728', '1', '2021-03-04 14:58:12', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341284231610646', '5341094230753552', '5341153385185552', '1', '2021-03-04 14:58:12', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341284231627024', '5341094230753552', '5341283194011920', '1', '2021-03-04 14:58:12', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341417968304400', '5341094230753552', '5332945038803216', '1', '2021-03-04 17:14:15', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341417968304401', '5341094230753552', '5332957116924176', '1', '2021-03-04 17:14:15', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341417968304402', '5341094230753552', '5332959185928464', '1', '2021-03-04 17:14:15', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341417968304403', '5341094230753552', '5332960791970064', '1', '2021-03-04 17:14:15', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341417968304404', '5341094230753552', '5333919769231632', '1', '2021-03-04 17:14:15', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341417968320784', '5341094230753552', '5335533073318160', '1', '2021-03-04 17:14:15', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341417968320785', '5341094230753552', '5336084539113744', '1', '2021-03-04 17:14:15', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341417968320786', '5341094230753552', '5339994553434384', '1', '2021-03-04 17:14:15', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341417968320787', '5341094230753552', '5340010427466000', '1', '2021-03-04 17:14:15', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341417968320788', '5341094230753552', '5341076872495376', '1', '2021-03-04 17:14:15', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341417968320789', '5341094230753552', '5341077229830416', '1', '2021-03-04 17:14:15', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341417968337168', '5341094230753552', '5341077809529104', '1', '2021-03-04 17:14:15', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341417968337169', '5341094230753552', '5341078368141584', '1', '2021-03-04 17:14:15', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341417968337170', '5341094230753552', '5341079027728656', '1', '2021-03-04 17:14:15', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341417968337171', '5341094230753552', '5341079293116688', '1', '2021-03-04 17:14:15', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341417968337172', '5341094230753552', '5341082417316112', '1', '2021-03-04 17:14:15', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341417968337173', '5341094230753552', '5341082912538896', '1', '2021-03-04 17:14:15', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341417968337174', '5341094230753552', '5341083215462672', '1', '2021-03-04 17:14:15', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341417968353552', '5341094230753552', '5341083549810960', '1', '2021-03-04 17:14:15', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341417968353553', '5341094230753552', '5341084066152720', '1', '2021-03-04 17:14:15', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341417968353554', '5341094230753552', '5341084310176016', '1', '2021-03-04 17:14:15', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341417968353555', '5341094230753552', '5341084810510608', '1', '2021-03-04 17:14:15', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341417968353556', '5341094230753552', '5341085316841744', '1', '2021-03-04 17:14:15', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341417968353557', '5341094230753552', '5341085601218832', '1', '2021-03-04 17:14:15', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341417968369936', '5341094230753552', '5341085920788752', '1', '2021-03-04 17:14:15', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341417968369937', '5341094230753552', '5341086608539920', '1', '2021-03-04 17:14:15', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341417968369938', '5341094230753552', '5341086882857232', '1', '2021-03-04 17:14:15', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341417968369939', '5341094230753552', '5341087170494736', '1', '2021-03-04 17:14:15', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341417968369940', '5341094230753552', '5341087508840720', '1', '2021-03-04 17:14:15', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341417968369941', '5341094230753552', '5341087811371280', '1', '2021-03-04 17:14:15', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341417968369942', '5341094230753552', '5341088066453776', '1', '2021-03-04 17:14:15', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341417968386320', '5341094230753552', '5341088416792848', '1', '2021-03-04 17:14:15', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341417968386321', '5341094230753552', '5341088753647888', '1', '2021-03-04 17:14:15', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341417968386322', '5341094230753552', '5341089083785488', '1', '2021-03-04 17:14:15', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341417968386323', '5341094230753552', '5341151637651728', '1', '2021-03-04 17:14:15', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341417968386324', '5341094230753552', '5341153385185552', '1', '2021-03-04 17:14:15', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341417968386325', '5341094230753552', '5341283194011920', '1', '2021-03-04 17:14:15', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341417968402704', '5341094230753552', '5341413098127632', '1', '2021-03-04 17:14:15', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341417968402705', '5341094230753552', '5341414871187728', '1', '2021-03-04 17:14:15', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341417968402706', '5341094230753552', '5341416054079760', '1', '2021-03-04 17:14:15', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341417968402707', '5341094230753552', '5341417680666896', '1', '2021-03-04 17:14:15', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341418621583632', '5332894561632528', '5332945038803216', '1', '2021-03-04 17:14:55', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341418621583633', '5332894561632528', '5332957116924176', '1', '2021-03-04 17:14:55', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341418621583634', '5332894561632528', '5332959185928464', '1', '2021-03-04 17:14:55', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341418621583635', '5332894561632528', '5332960791970064', '1', '2021-03-04 17:14:55', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341418621600016', '5332894561632528', '5333919769231632', '1', '2021-03-04 17:14:55', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341418621600017', '5332894561632528', '5335533073318160', '1', '2021-03-04 17:14:55', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341418621600018', '5332894561632528', '5336084539113744', '1', '2021-03-04 17:14:55', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341418621600019', '5332894561632528', '5339994553434384', '1', '2021-03-04 17:14:55', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341418621600020', '5332894561632528', '5340010427466000', '1', '2021-03-04 17:14:55', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341418621600021', '5332894561632528', '5341151637651728', '1', '2021-03-04 17:14:55', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341418621600022', '5332894561632528', '5341153385185552', '1', '2021-03-04 17:14:55', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341418621616400', '5332894561632528', '5341283194011920', '1', '2021-03-04 17:14:55', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341418621616401', '5332894561632528', '5341413098127632', '1', '2021-03-04 17:14:55', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341418621616402', '5332894561632528', '5341414871187728', '1', '2021-03-04 17:14:55', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341418621616403', '5332894561632528', '5341416054079760', '1', '2021-03-04 17:14:55', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5341418621616404', '5332894561632528', '5341417680666896', '1', '2021-03-04 17:14:55', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342934528262416', '5332894561632528', '5332945038803216', '0', '2021-03-05 18:56:58', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342934528278800', '5332894561632528', '5332957116924176', '0', '2021-03-05 18:56:58', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342934528278801', '5332894561632528', '5332959185928464', '0', '2021-03-05 18:56:58', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342934528278802', '5332894561632528', '5332960791970064', '0', '2021-03-05 18:56:58', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342934528278803', '5332894561632528', '5333919769231632', '0', '2021-03-05 18:56:58', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342934528278804', '5332894561632528', '5335533073318160', '0', '2021-03-05 18:56:58', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342934528278805', '5332894561632528', '5336084539113744', '0', '2021-03-05 18:56:58', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342934528295184', '5332894561632528', '5339994553434384', '0', '2021-03-05 18:56:58', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342934528295185', '5332894561632528', '5340010427466000', '0', '2021-03-05 18:56:58', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342934528295186', '5332894561632528', '5341151637651728', '0', '2021-03-05 18:56:58', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342934528295187', '5332894561632528', '5341153385185552', '0', '2021-03-05 18:56:58', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342934528295188', '5332894561632528', '5341283194011920', '0', '2021-03-05 18:56:58', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342934528295189', '5332894561632528', '5341413098127632', '0', '2021-03-05 18:56:58', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342934528311568', '5332894561632528', '5341414871187728', '0', '2021-03-05 18:56:58', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342934528311569', '5332894561632528', '5341416054079760', '0', '2021-03-05 18:56:58', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342934528311570', '5332894561632528', '5341417680666896', '0', '2021-03-05 18:56:58', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342934528311571', '5332894561632528', '5342934061908240', '0', '2021-03-05 18:56:58', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861194000', '5334305691713808', '5332945038803216', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861194001', '5334305691713808', '5332957116924176', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861194002', '5334305691713808', '5332959185928464', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861194003', '5334305691713808', '5332960791970064', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861194004', '5334305691713808', '5333919769231632', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861194005', '5334305691713808', '5335533073318160', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861210384', '5334305691713808', '5336084539113744', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861210385', '5334305691713808', '5339994553434384', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861210386', '5334305691713808', '5340010427466000', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861210387', '5334305691713808', '5341076872495376', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861210388', '5334305691713808', '5341077229830416', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861210389', '5334305691713808', '5341077809529104', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861226768', '5334305691713808', '5341078368141584', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861226769', '5334305691713808', '5341079027728656', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861226770', '5334305691713808', '5341079293116688', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861226771', '5334305691713808', '5341082417316112', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861226772', '5334305691713808', '5341082912538896', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861226773', '5334305691713808', '5341083215462672', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861226774', '5334305691713808', '5341083549810960', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861243152', '5334305691713808', '5341084066152720', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861243153', '5334305691713808', '5341084310176016', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861243154', '5334305691713808', '5341084810510608', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861243155', '5334305691713808', '5341085316841744', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861243156', '5334305691713808', '5341085601218832', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861243157', '5334305691713808', '5341085920788752', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861243158', '5334305691713808', '5341086608539920', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861259536', '5334305691713808', '5341086882857232', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861259537', '5334305691713808', '5341087170494736', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861259538', '5334305691713808', '5341087508840720', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861259539', '5334305691713808', '5341087811371280', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861259540', '5334305691713808', '5341088066453776', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861259541', '5334305691713808', '5341088416792848', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861259542', '5334305691713808', '5341088753647888', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861275920', '5334305691713808', '5341089083785488', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861275921', '5334305691713808', '5341151637651728', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861275922', '5334305691713808', '5341153385185552', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861275923', '5334305691713808', '5341283194011920', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861275924', '5334305691713808', '5341413098127632', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861275925', '5334305691713808', '5341414871187728', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861275926', '5334305691713808', '5341416054079760', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861292304', '5334305691713808', '5341417680666896', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342937861292305', '5334305691713808', '5342934061908240', '0', '2021-03-05 19:00:22', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079379728', '5341094230753552', '5332945038803216', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079379729', '5341094230753552', '5332957116924176', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079396112', '5341094230753552', '5332959185928464', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079396113', '5341094230753552', '5332960791970064', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079396114', '5341094230753552', '5333919769231632', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079396115', '5341094230753552', '5335533073318160', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079396116', '5341094230753552', '5336084539113744', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079396117', '5341094230753552', '5339994553434384', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079396118', '5341094230753552', '5340010427466000', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079412496', '5341094230753552', '5341076872495376', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079412497', '5341094230753552', '5341077229830416', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079412498', '5341094230753552', '5341077809529104', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079412499', '5341094230753552', '5341078368141584', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079412500', '5341094230753552', '5341079027728656', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079412501', '5341094230753552', '5341079293116688', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079428880', '5341094230753552', '5341082417316112', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079428881', '5341094230753552', '5341082912538896', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079428882', '5341094230753552', '5341083215462672', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079428883', '5341094230753552', '5341083549810960', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079428884', '5341094230753552', '5341084066152720', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079428885', '5341094230753552', '5341084310176016', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079428886', '5341094230753552', '5341084810510608', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079445264', '5341094230753552', '5341085316841744', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079445265', '5341094230753552', '5341085601218832', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079445266', '5341094230753552', '5341085920788752', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079445267', '5341094230753552', '5341086608539920', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079445268', '5341094230753552', '5341086882857232', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079445269', '5341094230753552', '5341087170494736', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079461648', '5341094230753552', '5341087508840720', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079461649', '5341094230753552', '5341087811371280', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079461650', '5341094230753552', '5341088066453776', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079461651', '5341094230753552', '5341088416792848', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079461652', '5341094230753552', '5341088753647888', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079461653', '5341094230753552', '5341089083785488', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079478032', '5341094230753552', '5341151637651728', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079478033', '5341094230753552', '5341153385185552', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079478034', '5341094230753552', '5341283194011920', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079478035', '5341094230753552', '5341413098127632', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079478036', '5341094230753552', '5341414871187728', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079494416', '5341094230753552', '5341416054079760', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079494417', '5341094230753552', '5341417680666896', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);
INSERT INTO `t_sys_role_menu` VALUES ('5342938079494418', '5341094230753552', '5342934061908240', '0', '2021-03-05 19:00:35', '5332937727967504', null, null);

-- ----------------------------
-- Table structure for t_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user`;
CREATE TABLE `t_sys_user` (
  `id` bigint(20) unsigned NOT NULL COMMENT 'ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `nickname` varchar(100) DEFAULT NULL COMMENT '昵称',
  `avatar` varchar(255) DEFAULT NULL COMMENT '个性头像',
  `mobile` char(11) DEFAULT NULL COMMENT '手机',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `sex` char(1) DEFAULT NULL COMMENT '性别',
  `dept_id` bigint(20) unsigned DEFAULT NULL COMMENT '部门ID',
  `post_id` bigint(20) unsigned DEFAULT NULL COMMENT '岗位ID',
  `state` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '启用状态',
  `is_admin` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否超管',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `is_deleted` tinyint(1) unsigned NOT NULL COMMENT '刪除标识',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` bigint(20) unsigned NOT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_by` bigint(20) unsigned DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户';

-- ----------------------------
-- Records of t_sys_user
-- ----------------------------
INSERT INTO `t_sys_user` VALUES ('5332937727967504', 'admin', '$2a$10$a2xOUEK2SEPmQ7LGu9PGI..Bp6gfONHGhcUz9dn33wx5za96Se.yy', '随缘', 'http://qp2g29tsi.hd-bkt.clouddn.com/20210311/83a368ac0c5f403cb95c120d6c8841fc', '18888888888', '18888888888@163.com', 'M', '5333957667406096', null, '1', '1', '', '0', '2021-02-26 17:27:42', '5327553293336672', '2021-03-12 21:50:29', '5332937727967504');
INSERT INTO `t_sys_user` VALUES ('5335832389583120', 'sale', '$2a$10$VuD.5xA2G6OVwsfOd4jHMeES7TLerYF.GooHuPpj66tglSLJ8QBUS', '销售员', null, null, null, 'M', '5335686809747728', null, '1', '0', null, '0', '2021-02-28 18:32:18', '5332937727967504', '2021-03-04 10:57:18', '5332937727967504');
INSERT INTO `t_sys_user` VALUES ('5340056513364240', 'wanglong', '$2a$10$E3MI/prHfdKGCUPn0wNUtu4xOMhZD0fyoySUbW95FUwXtmSBqDeSm', '王龙', null, null, null, 'M', '5335539032375568', null, '1', '0', 'h', '0', '2021-03-03 18:09:18', '5332937727967504', '2021-03-04 14:54:43', '5332937727967504');

-- ----------------------------
-- Table structure for t_sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user_post`;
CREATE TABLE `t_sys_user_post` (
  `id` bigint(20) unsigned NOT NULL,
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户ID',
  `post_id` bigint(20) unsigned NOT NULL COMMENT '岗位ID',
  `is_deleted` tinyint(1) unsigned NOT NULL COMMENT '刪除标识',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` bigint(20) unsigned NOT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_by` bigint(20) unsigned DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户岗位';

-- ----------------------------
-- Records of t_sys_user_post
-- ----------------------------
INSERT INTO `t_sys_user_post` VALUES ('5341047222173968', '5340056513364240', '5340058189562128', '1', '2021-03-04 10:57:06', '5332937727967504', null, null);
INSERT INTO `t_sys_user_post` VALUES ('5341047418962192', '5335832389583120', '5340058189562128', '0', '2021-03-04 10:57:18', '5332937727967504', null, null);
INSERT INTO `t_sys_user_post` VALUES ('5341047517053200', '5340056513364240', '5336096562905360', '1', '2021-03-04 10:57:24', '5332937727967504', null, null);
INSERT INTO `t_sys_user_post` VALUES ('5341047517053201', '5340056513364240', '5340058189562128', '1', '2021-03-04 10:57:24', '5332937727967504', null, null);
INSERT INTO `t_sys_user_post` VALUES ('5341047946314000', '5332937727967504', '5336094356685072', '0', '2021-03-04 10:57:51', '5332937727967504', null, null);
INSERT INTO `t_sys_user_post` VALUES ('5341095758315792', '5340056513364240', '5336096562905360', '1', '2021-03-04 11:46:29', '5332937727967504', null, null);
INSERT INTO `t_sys_user_post` VALUES ('5341280805994768', '5340056513364240', '5336096562905360', '0', '2021-03-04 14:54:43', '5332937727967504', null, null);

-- ----------------------------
-- Table structure for t_sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_sys_user_role`;
CREATE TABLE `t_sys_user_role` (
  `id` bigint(20) unsigned NOT NULL,
  `user_id` bigint(20) unsigned NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) unsigned NOT NULL COMMENT '角色ID',
  `is_deleted` tinyint(1) unsigned NOT NULL COMMENT '刪除标识',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_by` bigint(20) unsigned NOT NULL COMMENT '创建人',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_by` bigint(20) unsigned DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户角色';

-- ----------------------------
-- Records of t_sys_user_role
-- ----------------------------
INSERT INTO `t_sys_user_role` VALUES ('5332937728672016', '5332937727967504', '5332894561632528', '1', '2021-02-26 17:27:42', '5327553293336672', null, null);
INSERT INTO `t_sys_user_role` VALUES ('5335832390009104', '5335832389583120', '5334305691713808', '1', '2021-02-28 18:32:18', '5332937727967504', null, null);
INSERT INTO `t_sys_user_role` VALUES ('5335847936065808', '5332937727967504', '5332894561632528', '1', '2021-02-28 18:48:07', '5332937727967504', null, null);
INSERT INTO `t_sys_user_role` VALUES ('5340056513822992', '5340056513364240', '5334305691713808', '1', '2021-03-03 18:09:18', '5332937727967504', null, null);
INSERT INTO `t_sys_user_role` VALUES ('5340057765445904', '5332937727967504', '5332894561632528', '1', '2021-03-03 18:10:35', '5332937727967504', null, null);
INSERT INTO `t_sys_user_role` VALUES ('5340058526417168', '5335832389583120', '5334305691713808', '1', '2021-03-03 18:11:21', '5332937727967504', null, null);
INSERT INTO `t_sys_user_role` VALUES ('5341024058802448', '5332937727967504', '5332894561632528', '1', '2021-03-04 10:33:33', '5332937727967504', null, null);
INSERT INTO `t_sys_user_role` VALUES ('5341047221846288', '5340056513364240', '5334305691713808', '1', '2021-03-04 10:57:06', '5332937727967504', null, null);
INSERT INTO `t_sys_user_role` VALUES ('5341047418781968', '5335832389583120', '5334305691713808', '0', '2021-03-04 10:57:18', '5332937727967504', null, null);
INSERT INTO `t_sys_user_role` VALUES ('5341047516725520', '5340056513364240', '5334305691713808', '1', '2021-03-04 10:57:24', '5332937727967504', null, null);
INSERT INTO `t_sys_user_role` VALUES ('5341047946166544', '5332937727967504', '5332894561632528', '0', '2021-03-04 10:57:51', '5332937727967504', null, null);
INSERT INTO `t_sys_user_role` VALUES ('5341095758135568', '5340056513364240', '5341094230753552', '1', '2021-03-04 11:46:29', '5332937727967504', null, null);
INSERT INTO `t_sys_user_role` VALUES ('5341280805749008', '5340056513364240', '5341094230753552', '0', '2021-03-04 14:54:43', '5332937727967504', null, null);
