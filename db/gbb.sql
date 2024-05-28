/*
 Navicat Premium Data Transfer

 Source Server         : 本地数据库
 Source Server Type    : MySQL
 Source Server Version : 80035 (8.0.35)
 Source Host           : localhost:3306
 Source Schema         : gbb

 Target Server Type    : MySQL
 Target Server Version : 80035 (8.0.35)
 File Encoding         : 65001

 Date: 28/05/2024 09:13:57
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for biz_data
-- ----------------------------
DROP TABLE IF EXISTS `biz_data`;
CREATE TABLE `biz_data`  (
  `id` bigint NOT NULL COMMENT '主键',
  `type` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据类型',
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '数据值',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据描述',
  `enable` tinyint(1) NULL DEFAULT 1 COMMENT '状态:1=启动,0=禁用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `type, value`(`type` ASC, `value` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '业务数据' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for biz_dict
-- ----------------------------
DROP TABLE IF EXISTS `biz_dict`;
CREATE TABLE `biz_dict`  (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `name_alias` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '别名',
  `tag` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标识',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `enable` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态:1=启动,0=禁用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX ```tag```(`tag` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统字典' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for biz_dict_detail
-- ----------------------------
DROP TABLE IF EXISTS `biz_dict_detail`;
CREATE TABLE `biz_dict_detail`  (
  `id` bigint NOT NULL COMMENT '主键',
  `dict_id` bigint NOT NULL COMMENT '字典编号',
  `label` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `label_alias` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '别名',
  `value` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '具体值',
  `sort` int NULL DEFAULT 1 COMMENT '排序',
  `enable` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态:1=启动,0=禁用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `dict_id,value`(`dict_id` ASC, `value` ASC) USING BTREE,
  UNIQUE INDEX `dict_id,abel_alias`(`dict_id` ASC, `label_alias` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统字典详情' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for biz_user
-- ----------------------------
DROP TABLE IF EXISTS `biz_user`;
CREATE TABLE `biz_user`  (
  `id` bigint NOT NULL COMMENT '主键',
  `mobile` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `account` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录账号',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `nick_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `money` decimal(11, 2) NULL DEFAULT 0.00 COMMENT '金额',
  `money_sign` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '金额校验',
  `photo` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `birthday` datetime NULL DEFAULT NULL COMMENT '生日',
  `type` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '类型',
  `enable` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态:1=启动,0=禁用',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `type`(`type` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '业务用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for csgo_battle_room
-- ----------------------------
DROP TABLE IF EXISTS `csgo_battle_room`;
CREATE TABLE `csgo_battle_room`  (
  `id` bigint NOT NULL COMMENT '编号',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建用户',
  `battle_model` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '对战模式',
  `people_number` int NULL DEFAULT NULL COMMENT '房间人数',
  `room_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '房间价格',
  `status` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '房间状态',
  `secret_hash` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '秘密哈希',
  `secret_salt` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '秘密盐值',
  `public_hash` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公共哈希',
  `client_seed` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户端种子',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `status`(`status` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '对战房间' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for csgo_battle_room_box
-- ----------------------------
DROP TABLE IF EXISTS `csgo_battle_room_box`;
CREATE TABLE `csgo_battle_room_box`  (
  `id` bigint NOT NULL COMMENT '编号',
  `room_id` bigint NOT NULL COMMENT '房间编号',
  `box_id` bigint NOT NULL COMMENT '箱子编号',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '箱子名称',
  `name_alias` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '箱子别名',
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '箱子图片',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '对战房间箱子' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for csgo_battle_room_good
-- ----------------------------
DROP TABLE IF EXISTS `csgo_battle_room_good`;
CREATE TABLE `csgo_battle_room_good`  (
  `id` bigint NOT NULL COMMENT '编号',
  `room_id` bigint NOT NULL COMMENT '房间编号',
  `roll_user_id` bigint NULL DEFAULT NULL COMMENT 'Roll用户',
  `luck_user_id` bigint NULL DEFAULT NULL COMMENT '装备归属用户',
  `round` int NULL DEFAULT NULL COMMENT 'Roll回合',
  `round_number` int NULL DEFAULT NULL COMMENT 'Roll回合点数',
  `box_id` bigint NULL DEFAULT NULL COMMENT 'Roll盲盒编号',
  `good_id` bigint NOT NULL COMMENT '商品编号',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `name_alias` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品别名',
  `good_price` decimal(10, 2) NOT NULL COMMENT '商品价格',
  `good_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品图片',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '对战奖励装备记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for csgo_battle_room_user
-- ----------------------------
DROP TABLE IF EXISTS `csgo_battle_room_user`;
CREATE TABLE `csgo_battle_room_user`  (
  `id` bigint NOT NULL COMMENT '编号',
  `room_id` bigint NOT NULL COMMENT '房间编号',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `user_type` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户类型',
  `nick_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像图片',
  `is_win` tinyint(1) NULL DEFAULT NULL COMMENT '是否赢家',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `room_id,user-id`(`room_id` ASC, `user_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '对战房间用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for csgo_box
-- ----------------------------
DROP TABLE IF EXISTS `csgo_box`;
CREATE TABLE `csgo_box`  (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '箱子名称',
  `name_alias` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '箱子别名',
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片地址',
  `type` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '箱子类型',
  `group` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '箱子分组',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '箱子价格',
  `enable` tinyint(1) NOT NULL DEFAULT 0 COMMENT '状态:1=启动,0=禁用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'CSGO箱子' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for csgo_box_goods
-- ----------------------------
DROP TABLE IF EXISTS `csgo_box_goods`;
CREATE TABLE `csgo_box_goods`  (
  `id` bigint NOT NULL COMMENT '主键',
  `box_id` bigint NOT NULL COMMENT '游戏箱子',
  `good_id` bigint NULL DEFAULT NULL COMMENT '游戏商品',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品名称',
  `name_alias` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品别名',
  `type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品类型',
  `type_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型名称',
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片地址',
  `price` decimal(10, 2) NOT NULL COMMENT '商品价格',
  `rate` decimal(7, 4) NOT NULL DEFAULT 0.0000 COMMENT '获得概率',
  `sort` int NOT NULL DEFAULT 1 COMMENT '排序',
  `enable` tinyint(1) NOT NULL DEFAULT 0 COMMENT '状态:1=启动,0=禁用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'CSGO箱子商品' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for csgo_capital_record
-- ----------------------------
DROP TABLE IF EXISTS `csgo_capital_record`;
CREATE TABLE `csgo_capital_record`  (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `change_money` decimal(11, 2) NULL DEFAULT NULL COMMENT '变更金额',
  `before_money` decimal(11, 2) NULL DEFAULT NULL COMMENT '变更前金额',
  `after_money` decimal(11, 2) NULL DEFAULT NULL COMMENT '变更后金额',
  `type` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '变更类型',
  `source_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '变更来源',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '资金流水' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for csgo_config
-- ----------------------------
DROP TABLE IF EXISTS `csgo_config`;
CREATE TABLE `csgo_config`  (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '配置名称',
  `name_alias` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '配置别名',
  `value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '配置值',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配置描述',
  `enable` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态:1=启动,0=禁用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name_alias`(`name_alias` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '游戏配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for csgo_dream_good_log
-- ----------------------------
DROP TABLE IF EXISTS `csgo_dream_good_log`;
CREATE TABLE `csgo_dream_good_log`  (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户编号',
  `user_nick_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `user_photo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `dream_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '追梦金额',
  `dream_good_probability` decimal(10, 2) NULL DEFAULT NULL COMMENT '追梦概率',
  `dream_good_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '追梦时间',
  `dream_is_win` tinyint(1) NULL DEFAULT NULL COMMENT '追梦成功',
  `box_id` bigint NULL DEFAULT NULL COMMENT '箱子编号',
  `good_id` bigint NULL DEFAULT NULL COMMENT '商品类型',
  `good_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品分组',
  `good_name_alias` varchar(1255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品别名',
  `good_image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品图片',
  `good_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品价格',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'CSGO追梦日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for csgo_goods
-- ----------------------------
DROP TABLE IF EXISTS `csgo_goods`;
CREATE TABLE `csgo_goods`  (
  `id` bigint NOT NULL COMMENT '主键id',
  `item_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '皮肤名称',
  `short_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '皮肤短名',
  `market_hash_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '皮肤市场名称',
  `type` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '类型',
  `type_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '类型名称',
  `exterior` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '外观',
  `exterior_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '外观名称',
  `exterior_color` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '外观颜色',
  `quality` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '类别',
  `quality_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '类别名称',
  `quality_color` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '类别颜色',
  `rarity` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '品质',
  `rarity_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '品质名称',
  `rarity_color` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '品质颜色',
  `price` decimal(10, 2) NULL DEFAULT 0.00 COMMENT '价格',
  `cny_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '人名币价格',
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '图片地址',
  `quantity` int NULL DEFAULT NULL COMMENT '商品数量',
  `status` tinyint(1) NULL DEFAULT 1 COMMENT '状态:1=启动,0=禁用',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `edit_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '编辑时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'CSGO商品表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for csgo_open_box_log
-- ----------------------------
DROP TABLE IF EXISTS `csgo_open_box_log`;
CREATE TABLE `csgo_open_box_log`  (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户编号',
  `user_nick_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `user_photo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `open_box_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开箱时间',
  `box_id` bigint NULL DEFAULT NULL COMMENT '箱子编号',
  `box_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '箱子名称',
  `box_name_alias` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '箱子别名',
  `box_image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '箱子图片',
  `box_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '箱子价格',
  `good_id` bigint NULL DEFAULT NULL COMMENT '商品类型',
  `good_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品分组',
  `good_name_alias` varchar(1255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品别名',
  `good_image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品图片',
  `good_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品价格',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'CSGO开箱日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for csgo_robot
-- ----------------------------
DROP TABLE IF EXISTS `csgo_robot`;
CREATE TABLE `csgo_robot`  (
  `id` bigint NOT NULL COMMENT '编号',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '机器人名称',
  `name_alias` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '机器人别名',
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '机器人头像',
  `take_total` bigint NULL DEFAULT 0 COMMENT '参战总数',
  `win_total` bigint NULL DEFAULT 0 COMMENT '赢的总数',
  `enable` tinyint(1) NULL DEFAULT NULL COMMENT '启动状态',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '机器人' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for csgo_roll
-- ----------------------------
DROP TABLE IF EXISTS `csgo_roll`;
CREATE TABLE `csgo_roll`  (
  `id` bigint NOT NULL COMMENT '编号',
  `create_user_id` bigint NULL DEFAULT NULL COMMENT '创建用户编号',
  `create_user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户名称',
  `create_user_photo` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建用户头像',
  `roll_title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '房间标题',
  `roll_remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '房间描述',
  `roll_type` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '撸房类型',
  `roll_model` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '撸房模式',
  `start_time` datetime NULL DEFAULT NULL COMMENT '房间开始时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '房间结束时间',
  `people_number` int NULL DEFAULT NULL COMMENT '房间人数',
  `join_condition` varchar(768) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '加入房间条件',
  `join_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '加入房间密码',
  `sort` int NULL DEFAULT NULL COMMENT '房间排序',
  `status` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '房间状态',
  `enable` tinyint(1) NULL DEFAULT 0 COMMENT '状态:启用与禁用',
  `create_time` datetime NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '撸房' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for csgo_roll_good
-- ----------------------------
DROP TABLE IF EXISTS `csgo_roll_good`;
CREATE TABLE `csgo_roll_good`  (
  `id` bigint NOT NULL COMMENT '编号',
  `roll_id` bigint NOT NULL COMMENT '房间编号',
  `luck_user_id` bigint NULL DEFAULT NULL COMMENT '装备归属用户',
  `good_id` bigint NOT NULL COMMENT '商品编号',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `name_alias` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品别名',
  `good_price` decimal(10, 2) NOT NULL COMMENT '商品价格',
  `good_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品图片',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `roll_id`(`roll_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '撸房装备' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for csgo_roll_user
-- ----------------------------
DROP TABLE IF EXISTS `csgo_roll_user`;
CREATE TABLE `csgo_roll_user`  (
  `id` bigint NOT NULL COMMENT '编号',
  `roll_id` bigint NOT NULL COMMENT '房间编号',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `user_type` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户类型',
  `nick_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像图片',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `roll_id,user-id`(`roll_id` ASC, `user_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '撸房用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for csgo_storehouse
-- ----------------------------
DROP TABLE IF EXISTS `csgo_storehouse`;
CREATE TABLE `csgo_storehouse`  (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `good_id` bigint NOT NULL COMMENT '商品编号',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品名称',
  `name_alias` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品别名',
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片地址',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品价值',
  `source_type` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '来源类型',
  `source_id` bigint NULL DEFAULT NULL COMMENT '来源编号',
  `source_info` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '来源信息',
  `status` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商品状态',
  `enable` tinyint(1) NULL DEFAULT 1 COMMENT '状态:1=启动,0=禁用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '装备仓库' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for csgo_user_info
-- ----------------------------
DROP TABLE IF EXISTS `csgo_user_info`;
CREATE TABLE `csgo_user_info`  (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `spread_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '推广码',
  `invitation_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邀请码',
  `channel_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '渠道',
  `id_card_name` varchar(48) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证姓名',
  `id_card_no` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号码',
  `steam_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配置值',
  `steam_link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配置描述',
  `last_login_ip` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '最后登录IP',
  `secret_hash` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '秘密哈希',
  `secret_salt` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '秘密盐值',
  `public_hash` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公共哈希',
  `client_seed` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户端种子',
  `round_number` int NULL DEFAULT 0 COMMENT '轮数',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'CSGO用户信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint NOT NULL COMMENT '主键',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父Id',
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单标题',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '请求路径',
  `icon` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图标',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `sort` int NULL DEFAULT 0 COMMENT '排序',
  `type` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '显示:1=菜单,2=按钮',
  `view` tinyint(1) NULL DEFAULT 1 COMMENT '显示:1=显示,0=隐藏',
  `enable` tinyint(1) NULL DEFAULT 1 COMMENT '启用:1=启动,0=禁用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_name`(`name` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统菜单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `enable` tinyint(1) NOT NULL DEFAULT 0 COMMENT '状态:1=启动,0=禁用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统角色' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint NOT NULL COMMENT '角色编号',
  `menu_id` bigint NOT NULL COMMENT '菜单编号',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色-菜单-中间' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint NOT NULL COMMENT '主键',
  `mobile` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机',
  `account` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '账号',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `super_admin` tinyint(1) NOT NULL DEFAULT 0 COMMENT '超管:1=是,0=否',
  `enable` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态:1=启动,0=禁用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `account`(`account` ASC) USING BTREE,
  UNIQUE INDEX `mobile`(`mobile` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `role_id` bigint NOT NULL COMMENT '角色编号',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户-角色-中间' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
