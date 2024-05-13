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

 Date: 13/05/2024 17:57:48
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
-- Records of biz_dict
-- ----------------------------
INSERT INTO `biz_dict` VALUES (1784138822442606592, 'CS:GO装备类型', 'CS:GO装备类型', 'csgo_good_type', 'CS:GO的装备类型', 1, '2024-04-27 08:33:37', '2024-05-12 12:59:41');
INSERT INTO `biz_dict` VALUES (1785914176081506304, 'CS:GO盲盒类型', 'CS:GO盲盒类型', 'csgo_box_type', 'CS:GO盲盒类型', 1, '2024-05-02 06:08:14', '2024-05-12 12:59:58');
INSERT INTO `biz_dict` VALUES (1785915487250296832, 'CS:GO箱子分组', 'CS:GO箱子分组', 'csgo_box_group', 'CS:GO箱子分组', 1, '2024-05-02 06:13:27', '2024-05-12 13:00:21');
INSERT INTO `biz_dict` VALUES (1789937805052182528, 'CS:GO对战房间状态', 'CS:GO对战房间状态', 'csgo_battle_status', 'CS:GO对战房间状态', 1, '2024-05-13 08:36:42', '2024-05-13 08:36:42');
INSERT INTO `biz_dict` VALUES (1789938223375286272, 'CS:GO资金流水类型', 'CS:GO资金流水类型', 'csgo_capital_type', 'CS:GO资金流水类型', 1, '2024-05-13 08:38:22', '2024-05-13 08:38:22');
INSERT INTO `biz_dict` VALUES (1789939212635439104, '充值支付状态', '充值支付状态', 'third_pay_status', '充值支付状态', 1, '2024-05-13 08:42:18', '2024-05-13 08:45:20');

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
-- Records of biz_dict_detail
-- ----------------------------
INSERT INTO `biz_dict_detail` VALUES (1785658921074905088, 1784138822442606592, '狙击步枪', '狙击步枪', 'csgo_type_sniperrifle', 1, 1, '2024-05-01 13:13:57', '2024-05-01 13:21:15');
INSERT INTO `biz_dict_detail` VALUES (1785659209957593088, 1784138822442606592, '步枪', '步枪', 'csgo_type_rifle', 1, 1, '2024-05-01 13:15:06', '2024-05-01 13:15:06');
INSERT INTO `biz_dict_detail` VALUES (1785659232187404288, 1784138822442606592, '微型冲锋枪', '微型冲锋枪', 'csgo_type_smg', 1, 1, '2024-05-01 13:15:11', '2024-05-01 13:15:43');
INSERT INTO `biz_dict_detail` VALUES (1785659444561793024, 1784138822442606592, '霰弹枪', '霰弹枪', 'csgo_type_shotgun', 1, 1, '2024-05-01 13:16:02', '2024-05-01 13:16:02');
INSERT INTO `biz_dict_detail` VALUES (1785659492204892160, 1784138822442606592, '手枪', '手枪', 'csgo_type_pistol', 1, 1, '2024-05-01 13:16:13', '2024-05-01 13:16:13');
INSERT INTO `biz_dict_detail` VALUES (1785660003217920000, 1784138822442606592, '武器箱', '武器箱', 'csgo_type_weaponcase', 1, 1, '2024-05-01 13:18:15', '2024-05-01 13:18:15');
INSERT INTO `biz_dict_detail` VALUES (1785660038676566016, 1784138822442606592, '机枪', '机枪', 'csgo_type_machinegun', 1, 1, '2024-05-01 13:18:23', '2024-05-01 13:18:23');
INSERT INTO `biz_dict_detail` VALUES (1785660069282402304, 1784138822442606592, '音乐盒', '音乐盒', 'csgo_type_musickit', 1, 1, '2024-05-01 13:18:31', '2024-05-01 13:18:31');
INSERT INTO `biz_dict_detail` VALUES (1785660096927059968, 1784138822442606592, '印花', '印花', 'csgo_tool_sticker', 1, 1, '2024-05-01 13:18:37', '2024-05-01 13:18:37');
INSERT INTO `biz_dict_detail` VALUES (1785660129802014720, 1784138822442606592, '匕首', '匕首', 'csgo_type_knife', 1, 1, '2024-05-01 13:18:45', '2024-05-01 13:18:45');
INSERT INTO `biz_dict_detail` VALUES (1785660162588889088, 1784138822442606592, '工具', '工具', 'csgo_type_tool', 1, 1, '2024-05-01 13:18:53', '2024-05-01 13:18:53');
INSERT INTO `biz_dict_detail` VALUES (1785660193765150720, 1784138822442606592, '标签', '标签', 'csgo_tool_name_tagtag', 1, 1, '2024-05-01 13:19:00', '2024-05-01 13:19:00');
INSERT INTO `biz_dict_detail` VALUES (1785660226061291520, 1784138822442606592, '收藏品', '收藏品', 'csgo_type_collectible', 1, 1, '2024-05-01 13:19:08', '2024-05-01 13:19:08');
INSERT INTO `biz_dict_detail` VALUES (1785660254901325824, 1784138822442606592, '涂鸦', '涂鸦', 'csgo_type_spray', 1, 1, '2024-05-01 13:19:15', '2024-05-01 13:19:15');
INSERT INTO `biz_dict_detail` VALUES (1785660286341828608, 1784138822442606592, '手套', '手套', 'type_hands', 1, 1, '2024-05-01 13:19:22', '2024-05-01 13:19:22');
INSERT INTO `biz_dict_detail` VALUES (1785660332483366912, 1784138822442606592, '探员', '探员', 'type_customplayer', 1, 1, '2024-05-01 13:19:33', '2024-05-01 13:19:33');
INSERT INTO `biz_dict_detail` VALUES (1785660367572914176, 1784138822442606592, '布章', '布章', 'csgo_tool_patch', 1, 1, '2024-05-01 13:19:42', '2024-05-01 13:19:42');
INSERT INTO `biz_dict_detail` VALUES (1785660396299702272, 1784138822442606592, '通行证', '通行证', 'csgo_type_ticket', 1, 1, '2024-05-01 13:19:49', '2024-05-01 13:19:49');
INSERT INTO `biz_dict_detail` VALUES (1785914365664047104, 1785914176081506304, '对战盲盒', 'battle_box', '2', 2, 1, '2024-05-02 06:09:00', '2024-05-13 08:58:25');
INSERT INTO `biz_dict_detail` VALUES (1785914432743550976, 1785914176081506304, '普通盲盒', 'ordinary_box', '1', 1, 1, '2024-05-02 06:09:16', '2024-05-13 08:58:16');
INSERT INTO `biz_dict_detail` VALUES (1785915850271502336, 1785915487250296832, '新手推荐', '新手推荐', '1', 1, 1, '2024-05-02 06:14:54', '2024-05-02 06:14:54');
INSERT INTO `biz_dict_detail` VALUES (1785915927870320640, 1785915487250296832, '经典专区', '经典专区', '2', 1, 1, '2024-05-02 06:15:12', '2024-05-02 06:15:12');
INSERT INTO `biz_dict_detail` VALUES (1785915989832773632, 1785915487250296832, '一九系列', '一九系列', '3', 1, 1, '2024-05-02 06:15:27', '2024-05-02 06:15:27');
INSERT INTO `biz_dict_detail` VALUES (1788919727128383488, 1785914176081506304, '追梦盲盒', 'dream_box', '3', 3, 1, '2024-05-10 13:11:14', '2024-05-13 08:58:42');
INSERT INTO `biz_dict_detail` VALUES (1789881716986191872, 1789875901894037504, '12', '12', '1', 1212, 1, '2024-05-13 04:53:50', '2024-05-13 04:53:50');
INSERT INTO `biz_dict_detail` VALUES (1789881812570185728, 1789881787966398464, '1', '1', '1', 1, 1, '2024-05-13 04:54:13', '2024-05-13 04:54:13');
INSERT INTO `biz_dict_detail` VALUES (1789935500965806080, 1785914176081506304, 'ROLL房盲盒', 'roll_box', '4', 4, 1, '2024-05-13 08:27:33', '2024-05-13 08:58:56');
INSERT INTO `biz_dict_detail` VALUES (1789935678535860224, 1785914176081506304, '随机饰品盲盒', 'random_good_box', '5', 5, 1, '2024-05-13 08:28:15', '2024-05-13 08:59:16');
INSERT INTO `biz_dict_detail` VALUES (1789938357668511744, 1789937805052182528, '等待中', '等待中', '1', 1, 1, '2024-05-13 08:38:54', '2024-05-13 08:38:54');
INSERT INTO `biz_dict_detail` VALUES (1789938419773571072, 1789937805052182528, '对战结束', '对战结束', '2', 2, 1, '2024-05-13 08:39:09', '2024-05-13 08:39:09');
INSERT INTO `biz_dict_detail` VALUES (1789938512572547072, 1789938223375286272, '充值到账', 'pay_ok', '1', 1, 1, '2024-05-13 08:39:31', '2024-05-13 09:11:57');
INSERT INTO `biz_dict_detail` VALUES (1789938632168931328, 1789938223375286272, '开箱', 'open_box', '2', 2, 1, '2024-05-13 08:40:00', '2024-05-13 09:11:40');
INSERT INTO `biz_dict_detail` VALUES (1789938668034424832, 1789938223375286272, '对战', 'battle', '3', 3, 1, '2024-05-13 08:40:08', '2024-05-13 09:12:08');
INSERT INTO `biz_dict_detail` VALUES (1789938749236150272, 1789938223375286272, '追梦', 'dream', '4', 4, 1, '2024-05-13 08:40:27', '2024-05-13 09:12:14');
INSERT INTO `biz_dict_detail` VALUES (1789938885899157504, 1789938223375286272, 'roll房', 'roll_room', '5', 5, 1, '2024-05-13 08:41:00', '2024-05-13 09:12:24');
INSERT INTO `biz_dict_detail` VALUES (1789939361151549440, 1789939212635439104, '发起订单', '发起订单', '1', 1, 1, '2024-05-13 08:42:53', '2024-05-13 08:42:53');
INSERT INTO `biz_dict_detail` VALUES (1789939443531874304, 1789939212635439104, '待付款', '待付款', '2', 2, 1, '2024-05-13 08:43:13', '2024-05-13 08:43:13');
INSERT INTO `biz_dict_detail` VALUES (1789939528399421440, 1789939212635439104, '付款成功', '付款成功', '3', 3, 1, '2024-05-13 08:43:33', '2024-05-13 08:43:33');
INSERT INTO `biz_dict_detail` VALUES (1789939602579881984, 1789939212635439104, '付款失败', '付款失败', '4', 4, 1, '2024-05-13 08:43:51', '2024-05-13 08:43:51');
INSERT INTO `biz_dict_detail` VALUES (1789947949215395840, 1789938223375286272, '后台充值', 'admin_pay', '0', 0, 1, '2024-05-13 09:17:01', '2024-05-13 09:17:35');

-- ----------------------------
-- Table structure for biz_user
-- ----------------------------
DROP TABLE IF EXISTS `biz_user`;
CREATE TABLE `biz_user`  (
  `id` bigint NOT NULL COMMENT '主键',
  `mobile` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `account` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录账号',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `nick_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `money` decimal(11, 2) NULL DEFAULT 0.00 COMMENT '金额',
  `money_sign` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '金额校验',
  `photo` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `birthday` datetime NULL DEFAULT NULL COMMENT '生日',
  `type` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '类型:1-普通,2-测试',
  `enable` tinyint(1) NOT NULL DEFAULT 1 COMMENT '状态:1=启动,0=禁用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '业务用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of biz_user
-- ----------------------------
INSERT INTO `biz_user` VALUES (1785884456325554176, '18612344321', 'xutao', 'xutao', '张三丰', 7410.49, NULL, NULL, NULL, NULL, '1', 1, '2024-05-02 04:10:09', '2024-05-13 09:37:48');

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
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '对战房间' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of csgo_battle_room
-- ----------------------------

-- ----------------------------
-- Table structure for csgo_battle_room_box
-- ----------------------------
DROP TABLE IF EXISTS `csgo_battle_room_box`;
CREATE TABLE `csgo_battle_room_box`  (
  `id` bigint NOT NULL COMMENT '编号',
  `room_id` bigint NOT NULL COMMENT '房间编号',
  `box_id` bigint NOT NULL COMMENT '箱子编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '对战房间箱子' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of csgo_battle_room_box
-- ----------------------------

-- ----------------------------
-- Table structure for csgo_battle_room_good
-- ----------------------------
DROP TABLE IF EXISTS `csgo_battle_room_good`;
CREATE TABLE `csgo_battle_room_good`  (
  `id` bigint NOT NULL COMMENT '编号',
  `room_id` bigint NOT NULL COMMENT '房间编号',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `good_id` bigint NULL DEFAULT NULL COMMENT '商品编号',
  `good_price` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品价格',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '对战房间获取装备记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of csgo_battle_room_good
-- ----------------------------

-- ----------------------------
-- Table structure for csgo_battle_room_user
-- ----------------------------
DROP TABLE IF EXISTS `csgo_battle_room_user`;
CREATE TABLE `csgo_battle_room_user`  (
  `id` bigint NOT NULL COMMENT '编号',
  `room_id` bigint NOT NULL COMMENT '房间编号',
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `nick_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `image_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像图片',
  `is_win` tinyint(1) NULL DEFAULT NULL COMMENT '是否赢家',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `room_id,user-id`(`room_id` ASC, `user_id` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '对战房间用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of csgo_battle_room_user
-- ----------------------------

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
-- Records of csgo_box
-- ----------------------------
INSERT INTO `csgo_box` VALUES (1785913961282809856, '经典.v1', '经典.v1', '', '1', '1', 1.68, 1, '2024-05-02 06:07:23', '2024-05-10 12:54:42');
INSERT INTO `csgo_box` VALUES (1785970046895190016, '经典.v2', '经典.v2', '', '1', '1', 2.68, 1, '2024-05-02 09:50:15', '2024-05-10 12:54:48');
INSERT INTO `csgo_box` VALUES (1785979605680451584, '经典.v3', '经典.v3', '', '2', NULL, 12.00, 1, '2024-05-02 10:28:14', '2024-05-10 13:12:17');
INSERT INTO `csgo_box` VALUES (1788920165978411008, '追梦盒子', '追梦盒子', NULL, '3', NULL, NULL, 1, '2024-05-10 13:12:58', '2024-05-10 13:27:31');
INSERT INTO `csgo_box` VALUES (1789936400073588736, 'Roll箱子.v1', 'Roll箱子.v1', NULL, '4', NULL, NULL, 0, '2024-05-13 08:31:07', '2024-05-13 08:31:07');
INSERT INTO `csgo_box` VALUES (1789936726130393088, '随机饰品箱子', '随机饰品箱子', NULL, '5', NULL, NULL, 0, '2024-05-13 08:32:25', '2024-05-13 08:32:25');

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
  `rate` decimal(6, 4) NOT NULL COMMENT '获得概率',
  `sort` int NOT NULL DEFAULT 1 COMMENT '排序',
  `enable` tinyint(1) NOT NULL DEFAULT 0 COMMENT '状态:1=启动,0=禁用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'CSGO箱子商品' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of csgo_box_goods
-- ----------------------------
INSERT INTO `csgo_box_goods` VALUES (1785962254281011200, 1785913961282809856, 553489420, '猎头蟹符文胸章', 'Headcrab Glyph Pin', 'csgo_type_collectible', '收藏品', 'https://img.zbt.com/e/steam/item/730/SGVhZGNyYWIgR2x5cGggUGlu.png', 31.75, 25.0000, 48, 1, '2024-05-02 09:19:17', '2024-05-12 06:03:30');
INSERT INTO `csgo_box_goods` VALUES (1785962488423837696, 1785913961282809856, 22318, 'AWP | 响尾蛇 (久经沙场)', 'AWP | Pit Viper (Field-Tested)', 'csgo_type_sniperrifle', '狙击步枪', 'https://img.zbt.com/e/steam/item/730/QVdQIHwgUGl0IFZpcGVyIChGaWVsZC1UZXN0ZWQp.png', 1.49, 25.0000, 2, 1, '2024-05-02 09:20:13', '2024-05-12 06:03:32');
INSERT INTO `csgo_box_goods` VALUES (1785962524759093248, 1785913961282809856, 553466025, '裹手（★） | 套印 (久经沙场)', '★ Hand Wraps | Overprint (Field-Tested)', 'type_hands', '手套', 'https://img.zbt.com/e/steam/item/730/4piFIEhhbmQgV3JhcHMgfCBPdmVycHJpbnQgKEZpZWxkLVRlc3RlZCk=.png', 1527.00, 25.0000, 1, 1, '2024-05-02 09:20:22', '2024-05-12 06:03:34');
INSERT INTO `csgo_box_goods` VALUES (1785962558816841728, 1785913961282809856, 22371, 'MAG-7（StatTrak™） | 炽热 (略有磨损)', 'StatTrak™ MAG-7 | Heat (Minimal Wear)', 'csgo_type_shotgun', '霰弹枪', 'https://img.zbt.com/e/steam/item/730/U3RhdFRyYWvihKIgTUFHLTcgfCBIZWF0IChNaW5pbWFsIFdlYXIp.png', 17.94, 25.0000, 8, 1, '2024-05-02 09:20:30', '2024-05-12 06:03:37');
INSERT INTO `csgo_box_goods` VALUES (1785970382737305600, 1785970046895190016, 22313, 'AWP | 响尾蛇 (略有磨损)', 'AWP | Pit Viper (Minimal Wear)', 'csgo_type_sniperrifle', '狙击步枪', 'https://img.zbt.com/e/steam/item/730/QVdQIHwgUGl0IFZpcGVyIChNaW5pbWFsIFdlYXIp.png', 4.19, 50.0000, 1, 1, '2024-05-02 09:51:35', '2024-05-12 06:03:40');
INSERT INTO `csgo_box_goods` VALUES (1786783590645542912, 1785970046895190016, 22315, 'FN57 | 耍猴把戏 (久经沙场)', 'Five-SeveN | Monkey Business (Field-Tested)', 'csgo_type_pistol', '手枪', 'https://img.zbt.com/e/steam/item/730/Rml2ZS1TZXZlTiB8IE1vbmtleSBCdXNpbmVzcyAoRmllbGQtVGVzdGVkKQ==.png', 15.40, 50.0000, 2, 1, '2024-05-04 15:42:59', '2024-05-12 06:03:43');
INSERT INTO `csgo_box_goods` VALUES (1788919839984521216, 1785979605680451584, 22313, 'AWP | 响尾蛇 (略有磨损)', 'AWP | Pit Viper (Minimal Wear)', 'csgo_type_sniperrifle', '狙击步枪', 'https://img.zbt.com/e/steam/item/730/QVdQIHwgUGl0IFZpcGVyIChNaW5pbWFsIFdlYXIp.png', 4.19, 10.0000, 1, 1, '2024-05-10 13:11:41', '2024-05-12 06:03:46');
INSERT INTO `csgo_box_goods` VALUES (1788919985774333952, 1785979605680451584, 1244771908120920064, '印花 | donk（全息）| 2024年哥本哈根锦标赛', 'Sticker | donk (Holo) | Copenhagen 2024', 'csgo_tool_sticker', '印花', 'https://img.zbt.com/e/steam/item/730/U3RpY2tlciB8IGRvbmsgKEhvbG8pIHwgQ29wZW5oYWdlbiAyMDI0.png', 100.00, 90.0000, 2, 1, '2024-05-10 13:12:15', '2024-05-12 06:03:52');
INSERT INTO `csgo_box_goods` VALUES (1788920696742416384, 1788920165978411008, 22322, 'AUG | 鹰翼 (崭新出厂)', 'AUG | Wings (Factory New)', 'csgo_type_rifle', '步枪', 'https://img.zbt.com/e/steam/item/730/QVVHIHwgV2luZ3MgKEZhY3RvcnkgTmV3KQ==.png', 100.00, 1.0000, 1, 1, '2024-05-10 13:15:05', '2024-05-12 10:29:54');
INSERT INTO `csgo_box_goods` VALUES (1788920759296266240, 1788920165978411008, 22329, 'AWP | 蠕虫之神 (略有磨损)', 'AWP | Worm God (Minimal Wear)', 'csgo_type_sniperrifle', '狙击步枪', 'https://img.zbt.com/e/steam/item/730/QVdQIHwgV29ybSBHb2QgKE1pbmltYWwgV2Vhcik=.png', 11.71, 1.0000, 1, 1, '2024-05-10 13:15:20', '2024-05-12 06:03:57');
INSERT INTO `csgo_box_goods` VALUES (1788920784910880768, 1788920165978411008, 22371, 'MAG-7（StatTrak™） | 炽热 (略有磨损)', 'StatTrak™ MAG-7 | Heat (Minimal Wear)', 'csgo_type_shotgun', '霰弹枪', 'https://img.zbt.com/e/steam/item/730/U3RhdFRyYWvihKIgTUFHLTcgfCBIZWF0IChNaW5pbWFsIFdlYXIp.png', 17.94, 1.0000, 2, 1, '2024-05-10 13:15:26', '2024-05-12 06:03:58');
INSERT INTO `csgo_box_goods` VALUES (1788924019843338240, 1788920165978411008, 1229937172015165440, '廓尔喀刀（★） | 狩猎网格 (久经沙场)', '★ Kukri Knife | Safari Mesh (Field-Tested)', 'csgo_type_knife', '匕首', 'https://img.zbt.com/e/steam/item/730/4piFIEt1a3JpIEtuaWZlIHwgU2FmYXJpIE1lc2ggKEZpZWxkLVRlc3RlZCk=.png', 1530.00, 1.0000, 1, 1, '2024-05-10 13:28:17', '2024-05-12 06:02:55');
INSERT INTO `csgo_box_goods` VALUES (1788924065607389184, 1788920165978411008, 1229680692190351360, '廓尔喀刀（★） | 人工染色 (破损不堪)', '★ Kukri Knife | Stained (Well-Worn)', 'csgo_type_knife', '匕首', 'https://img.zbt.com/e/steam/item/730/4piFIEt1a3JpIEtuaWZlIHwgU3RhaW5lZCAoV2VsbC1Xb3JuKQ==.png', 2044.50, 1.0000, 1, 1, '2024-05-10 13:28:28', '2024-05-12 06:02:53');
INSERT INTO `csgo_box_goods` VALUES (1788924102349492224, 1788920165978411008, 1229501951999660032, '廓尔喀刀（★） | 森林 DDPAT (略有磨损)', '★ Kukri Knife | Forest DDPAT (Minimal Wear)', 'csgo_type_knife', '匕首', 'https://img.zbt.com/e/steam/item/730/4piFIEt1a3JpIEtuaWZlIHwgRm9yZXN0IEREUEFUIChNaW5pbWFsIFdlYXIp.png', 2019.50, 1.0000, 1, 1, '2024-05-10 13:28:37', '2024-05-12 06:02:41');
INSERT INTO `csgo_box_goods` VALUES (1788924195282685952, 1788920165978411008, 1229332382678716416, '廓尔喀刀（★） | 都市伪装 (久经沙场)', '★ Kukri Knife | Urban Masked (Field-Tested)', 'csgo_type_knife', '匕首', 'https://img.zbt.com/e/steam/item/730/4piFIEt1a3JpIEtuaWZlIHwgVXJiYW4gTWFza2VkIChGaWVsZC1UZXN0ZWQp.png', 1570.00, 1.0000, 1, 1, '2024-05-10 13:28:59', '2024-05-12 06:02:41');
INSERT INTO `csgo_box_goods` VALUES (1788924243110334464, 1788920165978411008, 1229249142655021056, 'M4A1 消音型（StatTrak™） | 黑莲花 (略有磨损)', 'StatTrak™ M4A1-S | Black Lotus (Minimal Wear)', 'csgo_type_rifle', '步枪', 'https://img.zbt.com/e/steam/item/730/U3RhdFRyYWvihKIgTTRBMS1TIHwgQmxhY2sgTG90dXMgKE1pbmltYWwgV2Vhcik=.png', 512.79, 1.0000, 1, 1, '2024-05-10 13:29:10', '2024-05-12 06:02:40');
INSERT INTO `csgo_box_goods` VALUES (1789535017633681408, 1788920165978411008, 22316, '新星 | 惊惧骷髅 (略有磨损)', 'Nova | Rising Skull (Minimal Wear)', 'csgo_type_shotgun', '霰弹枪', 'https://img.zbt.com/e/steam/item/730/Tm92YSB8IFJpc2luZyBTa3VsbCAoTWluaW1hbCBXZWFyKQ==.png', 79.90, 1.0000, 1, 1, '2024-05-12 05:56:10', '2024-05-12 05:56:10');
INSERT INTO `csgo_box_goods` VALUES (1789573833329319936, 1788920165978411008, 22321, 'AUG | 力矩 (崭新出厂)', 'AUG | Torque (Factory New)', 'csgo_type_rifle', '步枪', 'https://img.zbt.com/e/steam/item/730/QVVHIHwgVG9ycXVlIChGYWN0b3J5IE5ldyk=.png', 100.00, 1.0000, 1, 1, '2024-05-12 08:30:25', '2024-05-12 08:30:25');
INSERT INTO `csgo_box_goods` VALUES (1789573836303081472, 1788920165978411008, 22321, 'AUG | 力矩 (崭新出厂)', 'AUG | Torque (Factory New)', 'csgo_type_rifle', '步枪', 'https://img.zbt.com/e/steam/item/730/QVVHIHwgVG9ycXVlIChGYWN0b3J5IE5ldyk=.png', 100.00, 1.0000, 1, 1, '2024-05-12 08:30:25', '2024-05-12 08:30:25');
INSERT INTO `csgo_box_goods` VALUES (1789573838815469568, 1788920165978411008, 22321, 'AUG | 力矩 (崭新出厂)', 'AUG | Torque (Factory New)', 'csgo_type_rifle', '步枪', 'https://img.zbt.com/e/steam/item/730/QVVHIHwgVG9ycXVlIChGYWN0b3J5IE5ldyk=.png', 100.00, 1.0000, 1, 1, '2024-05-12 08:30:26', '2024-05-12 08:30:26');
INSERT INTO `csgo_box_goods` VALUES (1789573839117459456, 1788920165978411008, 22321, 'AUG | 力矩 (崭新出厂)', 'AUG | Torque (Factory New)', 'csgo_type_rifle', '步枪', 'https://img.zbt.com/e/steam/item/730/QVVHIHwgVG9ycXVlIChGYWN0b3J5IE5ldyk=.png', 100.00, 1.0000, 1, 1, '2024-05-12 08:30:26', '2024-05-12 08:30:26');
INSERT INTO `csgo_box_goods` VALUES (1789573840224755712, 1788920165978411008, 22321, 'AUG | 力矩 (崭新出厂)', 'AUG | Torque (Factory New)', 'csgo_type_rifle', '步枪', 'https://img.zbt.com/e/steam/item/730/QVVHIHwgVG9ycXVlIChGYWN0b3J5IE5ldyk=.png', 100.00, 1.0000, 1, 1, '2024-05-12 08:30:26', '2024-05-12 08:30:26');
INSERT INTO `csgo_box_goods` VALUES (1789573840459636736, 1788920165978411008, 22321, 'AUG | 力矩 (崭新出厂)', 'AUG | Torque (Factory New)', 'csgo_type_rifle', '步枪', 'https://img.zbt.com/e/steam/item/730/QVVHIHwgVG9ycXVlIChGYWN0b3J5IE5ldyk=.png', 100.00, 1.0000, 1, 1, '2024-05-12 08:30:26', '2024-05-12 08:30:26');
INSERT INTO `csgo_box_goods` VALUES (1789936547914416128, 1789936400073588736, 22316, '新星 | 惊惧骷髅 (略有磨损)', 'Nova | Rising Skull (Minimal Wear)', 'csgo_type_shotgun', '霰弹枪', 'https://img.zbt.com/e/steam/item/730/Tm92YSB8IFJpc2luZyBTa3VsbCAoTWluaW1hbCBXZWFyKQ==.png', 79.90, 1.0000, 1, 1, '2024-05-13 08:31:43', '2024-05-13 08:31:43');

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
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '游戏配置' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of csgo_config
-- ----------------------------
INSERT INTO `csgo_config` VALUES (1785670528161107968, '11', '11', '11', '12312', 0, '2024-05-01 14:00:04', '2024-05-02 10:50:06');

-- ----------------------------
-- Table structure for csgo_goods
-- ----------------------------
DROP TABLE IF EXISTS `csgo_goods`;
CREATE TABLE `csgo_goods`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键id',
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
) ENGINE = InnoDB AUTO_INCREMENT = 1249655954089721857 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = 'CSGO商品表' ROW_FORMAT = DYNAMIC;

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
-- Records of csgo_robot
-- ----------------------------
INSERT INTO `csgo_robot` VALUES (1789971100813094912, '对战机器人.v1', '对战机器人.v1', NULL, 0, 0, 1, '2024-05-13 10:49:01', '2024-05-13 10:51:04');
INSERT INTO `csgo_robot` VALUES (1789971482985492480, '对战机器人.v2', '对战机器人.v2', NULL, 0, 0, 1, '2024-05-13 10:50:32', '2024-05-13 10:50:32');
INSERT INTO `csgo_robot` VALUES (1789971511485788160, '对战机器人.v3', '对战机器人.v3', NULL, 0, 0, 1, '2024-05-13 10:50:39', '2024-05-13 10:50:39');
INSERT INTO `csgo_robot` VALUES (1789971531463258112, '对战机器人.v4', '对战机器人.v4', NULL, 0, 0, 1, '2024-05-13 10:50:43', '2024-05-13 10:50:43');
INSERT INTO `csgo_robot` VALUES (1789971551721746432, '对战机器人.v5', '对战机器人.v5', NULL, 0, 0, 1, '2024-05-13 10:50:48', '2024-05-13 10:50:48');
INSERT INTO `csgo_robot` VALUES (1789971571850211328, '对战机器人.v6', '对战机器人.v6', NULL, 0, 0, 1, '2024-05-13 10:50:53', '2024-05-13 10:50:53');

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
  `enable` tinyint(1) NULL DEFAULT NULL COMMENT '状态:1=启动,0=禁用',
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
  `user_type` char(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户类型',
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
-- Records of csgo_user_info
-- ----------------------------
INSERT INTO `csgo_user_info` VALUES (1785884456325554177, 1785884456325554176, '1', '1', '1', '1', '1', '1', '1', '1', '1', '629705d55cc386d773f44aa544560a4db63df0704faabe08b95a56fe3a791d6b', 'b3483496927f17679d236879ad1dc8cc1f4f237865a63e055abddd940302e553', 'ffb41ae1e0b3fa47a25b68bd2eb8fb53b2efa67e426451cfe9324bf405294268', 'e49153671182b77e21830e8118bd55a48613a16a7db0014d0e49da1f42bfb31c', 321, '2024-05-04 16:34:07', '2024-05-13 09:34:15');

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
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1781177021253853184, 1782632576656936960, '系统管理', 'system', '/sys', 'Grid', NULL, 3, '1', 1, 1, '2024-04-19 04:24:29', '2024-04-27 09:56:15');
INSERT INTO `sys_menu` VALUES (1781185579064270848, 1781177021253853184, '系统用户', 'sys_user', '/sys/user', 'User', '/views/sys/sys_user/index.vue', 1, '1', 1, 1, '2024-04-19 04:58:29', '2024-04-27 14:22:09');
INSERT INTO `sys_menu` VALUES (1781185633078517760, 1781177021253853184, '系统角色', 'sys_role', '/sys/role', 'WindPower', '/views/sys/sys_role/index.vue', 2, '1', 1, 1, '2024-04-19 04:58:42', '2024-04-27 14:26:09');
INSERT INTO `sys_menu` VALUES (1781185772438462464, 1781177021253853184, '系统菜单', 'sys_menu', '/sys/menu', 'Operation', '/views/sys/sys_menu/index.vue', 3, '1', 1, 1, '2024-04-19 04:59:15', '2024-04-27 14:26:36');
INSERT INTO `sys_menu` VALUES (1782632576656936960, NULL, '根节点', 'root', '/csgo/mock', NULL, '/views/index.vue', 0, '1', 1, 1, '2024-04-23 04:48:20', '2024-05-03 08:09:55');
INSERT INTO `sys_menu` VALUES (1784130139495161856, 1782632576656936960, '业务中心', 'business', '/biz', 'Coffee', '', 1, '1', 1, 1, '2024-04-27 07:59:07', '2024-04-29 04:12:36');
INSERT INTO `sys_menu` VALUES (1784133451854471168, 1784130139495161856, '用户列表', 'biz_user', '/biz/user', 'Baseball', '/views/biz/biz_user/index.vue', 3, '1', 1, 1, '2024-04-27 08:12:17', '2024-04-27 08:13:33');
INSERT INTO `sys_menu` VALUES (1784138694512140288, 1784130139495161856, '字典管理', 'biz_dict', '/biz/dict', 'Baseball', '/views/biz/biz_dict/index.vue', 3, '1', 1, 1, '2024-04-27 08:33:07', '2024-04-29 04:48:18');
INSERT INTO `sys_menu` VALUES (1784141935689916416, 1782632576656936960, 'CS:GO管理', 'csgo', '/csgo', 'Shop', NULL, 2, '1', 1, 1, '2024-04-27 08:45:59', '2024-05-02 06:29:22');
INSERT INTO `sys_menu` VALUES (1784143103384150016, 1784141935689916416, '游戏配置', 'csgo_config', '/csgo/config', 'Baseball', '/views/csgo/csgo_config/index.vue', 1, '1', 1, 1, '2024-04-27 08:50:38', '2024-04-27 08:55:44');
INSERT INTO `sys_menu` VALUES (1784143757322280960, 1784141935689916416, '游戏商品', 'csgo_goods', '/csgo/goods', 'Baseball', '/views/csgo/csgo_goods/index.vue', 2, '1', 1, 1, '2024-04-27 08:53:14', '2024-05-06 02:15:23');
INSERT INTO `sys_menu` VALUES (1784798480094277632, 1782632576656936960, '欢迎', 'welcome', '/welcome', 'Clock', '/views/welcome.vue', 0, '1', 0, 1, '2024-04-29 04:14:52', '2024-04-29 04:53:18');
INSERT INTO `sys_menu` VALUES (1785913697947627520, 1784141935689916416, '游戏盲盒', 'csgo_box', '/csgo/box', 'Baseball', '/views/csgo/csgo_box/index.vue', 3, '1', 1, 1, '2024-05-02 06:06:20', '2024-05-06 02:15:35');
INSERT INTO `sys_menu` VALUES (1786306018119835648, 1784141935689916416, '模拟器', 'csgo_mock', '/csgo/mock', 'Baseball', '/views/mock/index.vue', 7, '1', 1, 1, '2024-05-03 08:05:17', '2024-05-13 10:46:42');
INSERT INTO `sys_menu` VALUES (1787303990072832000, 1784141935689916416, '资金流水', 'csgo_capital', '/csgo/capital', 'Baseball', '/views/csgo/csgo_capital_record/index.vue', 5, '1', 1, 1, '2024-05-06 02:10:52', '2024-05-06 02:16:04');
INSERT INTO `sys_menu` VALUES (1787305491184852992, 1784141935689916416, '背包数据', 'csgo_storehouse', '/csgo/storehouse', 'Baseball', '/views/csgo/csgo_storehouse/index.vue', 4, '1', 1, 1, '2024-05-06 02:16:50', '2024-05-06 02:17:02');
INSERT INTO `sys_menu` VALUES (1789970794586959872, 1784141935689916416, '机器人', 'csgo_rebot', '/csgo/rebot', 'Baseball', '/views/csgo/csgo_robot/index.vue', 6, '1', 1, 1, '2024-05-13 10:47:48', '2024-05-13 10:47:48');

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
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1783047863042641920, '超人', '什么都能操作', 1, '2024-04-24 08:18:32', '2024-04-24 08:18:32');
INSERT INTO `sys_role` VALUES (1784228088275329024, '普通人', '一般般权限', 1, '2024-04-27 14:28:20', '2024-04-27 14:28:20');

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
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1783047863042641920, 1781177021253853184);
INSERT INTO `sys_role_menu` VALUES (1783047863042641920, 1781185579064270848);
INSERT INTO `sys_role_menu` VALUES (1783047863042641920, 1781185633078517760);
INSERT INTO `sys_role_menu` VALUES (1783047863042641920, 1781185772438462464);
INSERT INTO `sys_role_menu` VALUES (1783047863042641920, 1782632576656936960);
INSERT INTO `sys_role_menu` VALUES (1783047863042641920, 1784130139495161856);
INSERT INTO `sys_role_menu` VALUES (1783047863042641920, 1784133451854471168);
INSERT INTO `sys_role_menu` VALUES (1783047863042641920, 1784138694512140288);
INSERT INTO `sys_role_menu` VALUES (1783047863042641920, 1784141935689916416);
INSERT INTO `sys_role_menu` VALUES (1783047863042641920, 1784143103384150016);
INSERT INTO `sys_role_menu` VALUES (1783047863042641920, 1784143757322280960);
INSERT INTO `sys_role_menu` VALUES (1783047863042641920, 1784798480094277632);
INSERT INTO `sys_role_menu` VALUES (1783047863042641920, 1785913697947627520);
INSERT INTO `sys_role_menu` VALUES (1783047863042641920, 1786306018119835648);
INSERT INTO `sys_role_menu` VALUES (1784228088275329024, 1782632576656936960);
INSERT INTO `sys_role_menu` VALUES (1784228088275329024, 1784130139495161856);
INSERT INTO `sys_role_menu` VALUES (1784228088275329024, 1784133451854471168);
INSERT INTO `sys_role_menu` VALUES (1784228088275329024, 1784138694512140288);
INSERT INTO `sys_role_menu` VALUES (1784228088275329024, 1784798480094277632);

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
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (1783047676299644928, '18612344321', 'admin', 'admin', 1, 1, '2024-04-24 08:17:48', '2024-04-25 10:04:38');
INSERT INTO `sys_user` VALUES (1784772346405236736, '18612312312', 'xutao', '123', 0, 1, '2024-04-29 02:31:01', '2024-04-29 05:56:26');
INSERT INTO `sys_user` VALUES (1785521460059389952, '111', '111', '111', 0, 1, '2024-05-01 04:07:44', '2024-05-01 04:07:44');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint NOT NULL COMMENT '用户编号',
  `role_id` bigint NOT NULL COMMENT '角色编号',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户-角色-中间' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1783047676299644928, 1783047863042641920);
INSERT INTO `sys_user_role` VALUES (1784772346405236736, 1784228088275329024);

SET FOREIGN_KEY_CHECKS = 1;
