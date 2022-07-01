/*
 Navicat Premium Data Transfer

 Source Server         : 外网
 Source Server Type    : MySQL
 Source Server Version : 80018
 Source Host           : xxx
 Source Schema         : maildb

 Target Server Type    : MySQL
 Target Server Version : 80018
 File Encoding         : 65001

 Date: 01/07/2022 16:20:08
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for email_draft
-- ----------------------------
DROP TABLE IF EXISTS `email_draft`;
CREATE TABLE `email_draft`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `email_from` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '发件人邮箱',
  `email_user` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发件人',
  `email_to` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '收件人邮箱',
  `email_cc` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '抄送人邮箱',
  `email_bcc` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密送人邮箱',
  `email_subject` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主题',
  `email_type` tinyint(2) NULL DEFAULT NULL COMMENT '邮件类型',
  `del_flag` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除 0 存在 1 删除',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_from`(`email_from`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '邮件草稿箱表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of email_draft
-- ----------------------------
INSERT INTO `email_draft` VALUES (3, 'aaa@yeh.com', 'aaa', 'bbb@yeh.com;ccc@yeh.com', 'ddd@yeh.com', '', '草稿测试', 0, 1, '2022-06-30 17:57:41', NULL);
INSERT INTO `email_draft` VALUES (4, 'aaa@yeh.com', 'aaa', 'bbb@yeh.com;ccc@yeh.com', 'ddd@yeh.com', '', '草稿测试编辑', 0, 0, '2022-06-30 17:57:58', '2022-06-30 17:59:04');
INSERT INTO `email_draft` VALUES (5, 'aaa@yeh.com', 'aaa', 'bbb@yeh.com;ccc@yeh.com', 'ddd@yeh.com', '', '草稿测试', 0, 0, '2022-06-30 18:06:11', NULL);
INSERT INTO `email_draft` VALUES (6, 'aaa@yeh.com', 'aaa', 'bbb@yeh.com;ccc@yeh.com', 'ddd@yeh.com', '', '草稿测试', 0, 0, '2022-06-30 18:10:52', '2022-06-30 18:10:52');

-- ----------------------------
-- Table structure for email_draft_details
-- ----------------------------
DROP TABLE IF EXISTS `email_draft_details`;
CREATE TABLE `email_draft_details`  (
  `id` bigint(20) NOT NULL DEFAULT 0 COMMENT '主键',
  `email_host` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'SMTP服务器域名',
  `email_port` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'SMTP服务端口',
  `email_auth` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否认证 0 否 1 是',
  `email_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '正文',
  `email_files` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '附件地址',
  `del_flag` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除 0 存在 1 删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '邮件草稿明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of email_draft_details
-- ----------------------------
INSERT INTO `email_draft_details` VALUES (3, 'yeh.com', '143', 1, '草稿测试正文', '[]', 1);
INSERT INTO `email_draft_details` VALUES (4, 'yeh.com', '143', 1, '草稿测试正文123', NULL, 0);
INSERT INTO `email_draft_details` VALUES (5, 'yeh.com', '143', 1, '草稿测试正文', NULL, NULL);
INSERT INTO `email_draft_details` VALUES (6, 'yeh.com', '143', 1, '草稿测试正文', NULL, 0);

-- ----------------------------
-- Table structure for email_send
-- ----------------------------
DROP TABLE IF EXISTS `email_send`;
CREATE TABLE `email_send`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `email_from` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '发件人邮箱',
  `email_user` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发件人',
  `email_to` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '收件人邮箱',
  `email_cc` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '抄送人邮箱',
  `email_bcc` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密送人邮箱',
  `email_subject` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主题',
  `email_type` tinyint(2) NULL DEFAULT NULL COMMENT '邮件类型',
  `del_flag` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除 0 存在 1 删除',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_from`(`email_from`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '已发送邮件表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of email_send
-- ----------------------------
INSERT INTO `email_send` VALUES (1, 'aaa@yeh.com', 'aaa', 'bbb@yeh.com;ccc@yeh.com', 'ddd@yeh.com', '', '发送测试', 0, 0, '2022-06-30 18:57:00');
INSERT INTO `email_send` VALUES (2, 'aaa@yeh.com', 'aaa', 'bbb@yeh.com;ccc@yeh.com', 'ddd@yeh.com', '', '发送测试', 0, 0, '2022-06-30 18:59:02');
INSERT INTO `email_send` VALUES (3, 'aaa@yeh.com', 'aaa', 'bbb@yeh.com;ccc@yeh.com', 'ddd@yeh.com', '', '未读测试', 0, 0, '2022-07-01 09:39:31');
INSERT INTO `email_send` VALUES (4, 'aaa@yeh.com', 'aaa', 'bbb@yeh.com;ccc@yeh.com', 'ddd@yeh.com', '', '未读测试2', 0, 0, '2022-07-01 10:15:27');
INSERT INTO `email_send` VALUES (5, 'aaa@yeh.com', 'aaa', 'bbb@yeh.com;ccc@yeh.com', 'ddd@yeh.com', '', '删除测试', 0, 0, '2022-07-01 14:37:54');
INSERT INTO `email_send` VALUES (6, 'aaa@yeh.com', 'aaa', 'bbb@yeh.com;ccc@yeh.com', 'ddd@yeh.com', '', '删除测试2', 0, 0, '2022-07-01 14:52:56');
INSERT INTO `email_send` VALUES (7, 'aaa@yeh.com', 'aaa', 'bbb@yeh.com;ccc@yeh.com', 'ddd@yeh.com', '', '删除测试3', 0, 0, '2022-07-01 14:55:05');
INSERT INTO `email_send` VALUES (8, 'aaa@yeh.com', 'aaa', 'bbb@yeh.com;ccc@yeh.com', 'ddd@yeh.com', '', '删除测试1', 0, 0, '2022-07-01 15:10:41');
INSERT INTO `email_send` VALUES (9, 'aaa@yeh.com', 'aaa', 'bbb@yeh.com;ccc@yeh.com', 'ddd@yeh.com', '', '删除测试2', 0, 0, '2022-07-01 15:12:11');
INSERT INTO `email_send` VALUES (10, 'aaa@yeh.com', 'aaa', 'bbb@yeh.com;ccc@yeh.com', 'ddd@yeh.com', '', '删除测试2', 0, 0, '2022-07-01 15:13:10');

-- ----------------------------
-- Table structure for email_send_details
-- ----------------------------
DROP TABLE IF EXISTS `email_send_details`;
CREATE TABLE `email_send_details`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `email_host` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'SMTP服务器域名',
  `email_port` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'SMTP服务端口',
  `email_auth` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否认证 0 否 1 是',
  `email_content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '正文',
  `email_files` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '附件地址',
  `del_flag` tinyint(1) NULL DEFAULT 0 COMMENT '逻辑删除 0 存在 1 删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '已发送邮件明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of email_send_details
-- ----------------------------
INSERT INTO `email_send_details` VALUES (1, 'yeh.com', '143', 1, '发送测试正文', '[]', 0);
INSERT INTO `email_send_details` VALUES (2, 'yeh.com', '143', 1, '发送测试正文', '[]', 0);
INSERT INTO `email_send_details` VALUES (3, 'yeh.com', '143', 1, '未读测试正文', '[]', 0);
INSERT INTO `email_send_details` VALUES (4, 'yeh.com', '143', 1, '未读测试正文', '[]', 0);
INSERT INTO `email_send_details` VALUES (5, 'yeh.com', '143', 1, '删除测试正文', '[]', 0);
INSERT INTO `email_send_details` VALUES (6, 'yeh.com', '143', 1, '删除测试正文2', '[]', 0);
INSERT INTO `email_send_details` VALUES (7, 'yeh.com', '143', 1, '删除测试正文3', '[]', 0);
INSERT INTO `email_send_details` VALUES (8, 'yeh.com', '143', 1, '删除测试正文1', '[]', 0);
INSERT INTO `email_send_details` VALUES (9, 'yeh.com', '143', 1, '删除测试正文2', '[]', 0);
INSERT INTO `email_send_details` VALUES (10, 'yeh.com', '143', 1, '删除测试正文2', '[]', 0);

SET FOREIGN_KEY_CHECKS = 1;
