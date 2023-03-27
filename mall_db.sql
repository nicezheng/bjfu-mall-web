/*
 Navicat MySQL Data Transfer

 Source Server         : mall
 Source Server Type    : MySQL
 Source Server Version : 80026
 Source Host           : localhost:3306
 Source Schema         : mall_db

 Target Server Type    : MySQL
 Target Server Version : 80026
 File Encoding         : 65001

 Date: 07/01/2022 20:51:32
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_sika_mall_address
-- ----------------------------
DROP TABLE IF EXISTS `tb_sika_mall_address`;
CREATE TABLE `tb_sika_mall_address`  (
  `add_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `dfault` int NULL DEFAULT NULL,
  PRIMARY KEY (`add_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_sika_mall_address
-- ----------------------------
INSERT INTO `tb_sika_mall_address` VALUES (1000, 9, '小小', '15501080299', '北京市海淀区清华东路35号北京林业大学', 1);

-- ----------------------------
-- Table structure for tb_sika_mall_admin_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_sika_mall_admin_user`;
CREATE TABLE `tb_sika_mall_admin_user`  (
  `admin_user_id` int NOT NULL AUTO_INCREMENT COMMENT '管理员id',
  `login_user_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员登陆名称',
  `login_password` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员登陆密码',
  `nick_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '管理员显示昵称',
  `locked` tinyint NULL DEFAULT 0 COMMENT '是否锁定 0未锁定 1已锁定无法登陆',
  PRIMARY KEY (`admin_user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_sika_mall_admin_user
-- ----------------------------
INSERT INTO `tb_sika_mall_admin_user` VALUES (1, 'admin', 'e10adc3949ba59abbe56e057f20f883e', 'admin', 0);

-- ----------------------------
-- Table structure for tb_sika_mall_carousel
-- ----------------------------
DROP TABLE IF EXISTS `tb_sika_mall_carousel`;
CREATE TABLE `tb_sika_mall_carousel`  (
  `carousel_id` int NOT NULL AUTO_INCREMENT COMMENT '首页轮播图主键id',
  `carousel_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '轮播图',
  `redirect_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '\'##\'' COMMENT '点击后的跳转地址(默认不跳转)',
  `carousel_rank` int NOT NULL DEFAULT 0 COMMENT '排序值(字段越大越靠前)',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '删除标识字段(0-未删除 1-已删除)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int NOT NULL DEFAULT 0 COMMENT '创建者id',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` int NOT NULL DEFAULT 0 COMMENT '修改者id',
  PRIMARY KEY (`carousel_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_sika_mall_carousel
-- ----------------------------
INSERT INTO `tb_sika_mall_carousel` VALUES (2, 'https://newbee-mall.oss-cn-beijing.aliyuncs.com/images/banner1.png', 'https://juejin.im/book/5da2f9d4f265da5b81794d48/section/5da2f9d6f265da5b794f2189', 13, 1, '2019-11-29 00:00:00', 0, '2020-12-29 06:54:04', 0);
INSERT INTO `tb_sika_mall_carousel` VALUES (5, 'https://newbee-mall.oss-cn-beijing.aliyuncs.com/images/banner2.png', 'https://juejin.im/book/5da2f9d4f265da5b81794d48/section/5da2f9d6f265da5b794f2189', 0, 1, '2019-11-29 00:00:00', 0, '2020-12-29 06:54:04', 0);
INSERT INTO `tb_sika_mall_carousel` VALUES (8, 'http://localhost:28089/upload/20220107_17510180.jpg', '##', 0, 0, '2020-12-31 07:38:55', 0, '2022-01-07 17:51:03', 0);
INSERT INTO `tb_sika_mall_carousel` VALUES (9, 'http://localhost:28089/upload/20220107_17511012.jpg', '##', 0, 0, '2020-12-31 07:39:07', 0, '2022-01-07 17:51:11', 0);
INSERT INTO `tb_sika_mall_carousel` VALUES (10, 'http://localhost:28089/upload/20220107_18070990.jpg', '##', 0, 0, '2022-01-07 18:07:11', 0, '2022-01-07 18:07:11', 0);

-- ----------------------------
-- Table structure for tb_sika_mall_goods_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_sika_mall_goods_category`;
CREATE TABLE `tb_sika_mall_goods_category`  (
  `category_id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `category_level` tinyint NOT NULL DEFAULT 0 COMMENT '分类级别(1-一级分类 2-二级分类 3-三级分类)',
  `parent_id` bigint NOT NULL DEFAULT 0 COMMENT '父分类id',
  `category_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '分类名称',
  `category_rank` int NOT NULL DEFAULT 0 COMMENT '排序值(字段越大越靠前)',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '删除标识字段(0-未删除 1-已删除)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int NOT NULL DEFAULT 0 COMMENT '创建者id',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_user` int NULL DEFAULT 0 COMMENT '修改者id',
  PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 129 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_sika_mall_goods_category
-- ----------------------------
INSERT INTO `tb_sika_mall_goods_category` VALUES (15, 1, 0, '家用电器', 100, 0, '2019-09-11 18:45:40', 0, '2022-01-07 16:48:13', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (16, 1, 0, '手机_运营商_数码', 99, 0, '2019-09-11 18:46:07', 0, '2022-01-07 16:49:08', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (17, 2, 15, '电视', 10, 0, '2019-09-11 18:46:32', 0, '2022-01-07 16:51:33', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (18, 2, 15, '空调', 9, 0, '2019-09-11 18:46:43', 0, '2022-01-07 16:51:39', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (19, 2, 15, '冰箱', 8, 0, '2019-09-11 18:46:52', 0, '2022-01-07 16:51:52', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (20, 3, 17, '全面屏电视', 0, 0, '2019-09-11 18:47:38', 0, '2022-01-07 16:56:38', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (21, 3, 17, '4K超清电视', 0, 0, '2019-09-11 18:47:49', 0, '2022-01-07 16:56:50', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (110, 1, 0, '电脑_办公', 0, 0, '2020-12-31 07:31:40', 0, '2022-01-07 16:49:21', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (111, 1, 0, '家居_家装', 0, 0, '2020-12-31 07:32:27', 0, '2022-01-07 16:49:37', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (112, 1, 0, '服装', 0, 0, '2020-12-31 07:32:44', 0, '2022-01-07 16:49:45', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (113, 1, 0, '美妆', 0, 0, '2020-12-31 07:32:51', 0, '2022-01-07 16:49:56', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (114, 1, 0, '男鞋_女鞋_箱包', 0, 0, '2020-12-31 07:33:16', 0, '2022-01-07 16:50:32', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (115, 1, 0, '汽车_汽车用品', 0, 0, '2020-12-31 07:33:39', 0, '2022-01-07 16:50:51', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (116, 1, 0, '食品_生鲜', 0, 0, '2020-12-31 07:33:58', 0, '2022-01-07 16:51:11', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (117, 2, 16, '手机通讯', 0, 0, '2020-12-31 07:47:27', 0, '2022-01-07 16:57:18', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (118, 2, 16, '运营商', 0, 0, '2020-12-31 07:47:45', 0, '2022-01-07 16:57:38', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (119, 2, 16, '数码', 0, 0, '2020-12-31 07:48:11', 0, '2022-01-07 16:59:00', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (120, 3, 117, '游戏手机', 0, 0, '2020-12-31 07:48:38', 0, '2022-01-07 16:59:12', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (121, 3, 117, '5G手机', 0, 0, '2020-12-31 07:49:26', 0, '2022-01-07 16:59:19', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (122, 3, 117, '拍照手机', 0, 0, '2020-12-31 07:49:40', 0, '2022-01-07 16:59:27', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (123, 2, 110, '电脑整机', 0, 0, '2020-12-31 08:17:48', 0, '2022-01-07 17:01:05', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (124, 3, 123, '笔记本', 0, 0, '2020-12-31 08:18:04', 0, '2022-01-07 17:01:46', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (125, 2, 111, '厨具', 0, 0, '2020-12-31 08:18:24', 0, '2022-01-07 17:02:10', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (126, 3, 125, '保温杯', 0, 0, '2020-12-31 08:18:40', 0, '2022-01-07 17:29:31', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (127, 2, 112, '男装', 0, 0, '2020-12-31 08:19:05', 0, '2022-01-07 17:03:20', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (128, 3, 127, '牛仔裤', 0, 0, '2020-12-31 08:19:21', 0, '2022-01-07 17:03:54', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (129, 2, 116, '新鲜水果', 0, 0, '2022-01-07 16:21:25', 0, '2022-01-07 17:04:33', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (130, 3, 129, '苹果', 0, 0, '2022-01-07 16:21:37', 0, '2022-01-07 17:04:46', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (131, 3, 18, '中央空调', 0, 0, '2022-01-07 16:58:11', 0, '2022-01-07 16:58:11', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (132, 3, 18, '移动空调', 0, 0, '2022-01-07 16:58:23', 0, '2022-01-07 16:58:23', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (133, 3, 19, '双门', 0, 0, '2022-01-07 16:58:38', 0, '2022-01-07 16:58:38', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (134, 3, 19, '对开门', 0, 0, '2022-01-07 16:58:43', 0, '2022-01-07 16:58:43', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (135, 3, 19, '三门', 0, 0, '2022-01-07 16:58:48', 0, '2022-01-07 16:58:48', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (136, 3, 118, '中国移动', 0, 0, '2022-01-07 16:59:49', 0, '2022-01-07 16:59:49', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (137, 3, 118, '中国联通', 0, 0, '2022-01-07 16:59:58', 0, '2022-01-07 16:59:58', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (138, 3, 118, '中国电信', 0, 0, '2022-01-07 17:00:23', 0, '2022-01-07 17:00:23', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (139, 3, 119, '数码相机', 0, 0, '2022-01-07 17:00:42', 0, '2022-01-07 17:00:42', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (140, 3, 119, '存储卡', 0, 0, '2022-01-07 17:00:50', 0, '2022-01-07 17:00:50', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (141, 2, 110, '电脑配件', 0, 0, '2022-01-07 17:01:14', 0, '2022-01-07 17:01:14', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (142, 2, 110, '外设产品', 0, 0, '2022-01-07 17:01:28', 0, '2022-01-07 17:01:28', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (143, 2, 111, '生活日用', 0, 0, '2022-01-07 17:02:20', 0, '2022-01-07 17:02:20', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (144, 3, 125, '厨房配件', 0, 0, '2022-01-07 17:02:54', 0, '2022-01-07 17:02:54', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (145, 2, 112, '女装', 0, 0, '2022-01-07 17:03:24', 0, '2022-01-07 17:03:24', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (146, 2, 112, '配饰', 0, 0, '2022-01-07 17:03:32', 0, '2022-01-07 17:03:32', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (147, 3, 127, '衬衫', 0, 0, '2022-01-07 17:03:59', 0, '2022-01-07 17:03:59', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (148, 3, 119, '数码配件', 0, 0, '2022-01-07 17:36:07', 0, '2022-01-07 17:36:07', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (149, 2, 116, '零食', 0, 0, '2022-01-07 17:47:46', 0, '2022-01-07 17:47:46', 0);
INSERT INTO `tb_sika_mall_goods_category` VALUES (150, 3, 149, '薯片', 0, 0, '2022-01-07 17:47:57', 0, '2022-01-07 17:47:57', 0);

-- ----------------------------
-- Table structure for tb_sika_mall_goods_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_sika_mall_goods_info`;
CREATE TABLE `tb_sika_mall_goods_info`  (
  `goods_id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '商品表主键id',
  `goods_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商品名',
  `goods_intro` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商品简介',
  `goods_category_id` bigint NOT NULL DEFAULT 0 COMMENT '关联分类id',
  `goods_cover_img` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '/admin/dist/img/no-img.png' COMMENT '商品主图',
  `goods_carousel` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '/admin/dist/img/no-img.png' COMMENT '商品轮播图',
  `goods_detail_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品详情',
  `original_price` int NOT NULL DEFAULT 1 COMMENT '商品价格',
  `selling_price` int NOT NULL DEFAULT 1 COMMENT '商品实际售价',
  `stock_num` int NOT NULL DEFAULT 0 COMMENT '商品库存数量',
  `tag` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商品标签',
  `goods_sell_status` tinyint NOT NULL DEFAULT 0 COMMENT '商品上架状态 0-下架 1-上架',
  `create_user` int NOT NULL DEFAULT 0 COMMENT '添加者主键id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '商品添加时间',
  `update_user` int NOT NULL DEFAULT 0 COMMENT '修改者主键id',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '商品修改时间',
  PRIMARY KEY (`goods_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10919 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_sika_mall_goods_info
-- ----------------------------
INSERT INTO `tb_sika_mall_goods_info` VALUES (10001, '小米电视EA75 2022款', '小米电视EA75 2022款 75英寸 金属全面屏 远场语音 逐台校准4K超高清智能教育电视机L75M7-EA', 20, 'http://localhost:28089/upload/20220107_17342978.jpg', 'http://localhost:28089/upload/20220107_17342978.jpg', '<p>小米电视EA75 2022款 75英寸 金属全面屏 远场语音 逐台校准4K超高清智能教育电视机L75M7-EA</p>', 3999, 3999, 1000, '小米', 0, 0, '2020-12-31 07:54:03', 0, '2022-01-07 17:34:30');
INSERT INTO `tb_sika_mall_goods_info` VALUES (10002, '海信 65J65G-PRO', '海信（Hisense）65J65G-PRO 65英寸 130%高色域 MEMC运动补偿 AI声控 32GB大储存', 21, 'http://localhost:28089/upload/20220107_17331338.jpg', 'http://localhost:28089/upload/20220107_17331338.jpg', '<p>海信（Hisense）65J65G-PRO 65英寸 130%高色域 MEMC运动补偿 AI声控 32GB大储存</p>', 6999, 6999, 1000, '海信', 0, 0, '2020-12-31 07:54:50', 0, '2022-01-07 17:43:22');
INSERT INTO `tb_sika_mall_goods_info` VALUES (10003, '小米11 Pro 5G', '小米11 Pro 5G 骁龙888 2K AMOLED四曲面柔性屏 67W无线闪充 3D玻璃工艺 12GB+256GB 黑色 手机', 120, 'http://localhost:28089/upload/20220107_17321534.jpg', 'http://localhost:28089/upload/20220107_17321534.jpg', '<p>小米11 Pro 5G 骁龙888 2K AMOLED四曲面柔性屏 67W无线闪充 3D玻璃工艺 12GB+256GB 黑色 手机</p>', 4699, 4399, 90, '小米', 0, 0, '2020-12-31 07:56:19', 0, '2022-01-07 17:32:38');
INSERT INTO `tb_sika_mall_goods_info` VALUES (10004, '荣耀50', '荣耀50 1亿像素超清影像 5G 6.57英寸超曲屏 66W超级快充 3200万像素高清自拍 全网通版8GB+256GB 初雪水晶', 121, 'http://localhost:28089/upload/20220107_17312411.jpg', 'http://localhost:28089/upload/20220107_17312411.jpg', '<p>荣耀50 1亿像素超清影像 5G 6.57英寸超曲屏 66W超级快充 3200万像素高清自拍 全网通版8GB+256GB 初雪水晶</p>', 2599, 2599, 2000, '大狗牌', 0, 0, '2020-12-31 07:57:18', 0, '2022-01-07 17:31:26');
INSERT INTO `tb_sika_mall_goods_info` VALUES (10911, '雄泰智能保温杯', '雄泰（SHINETIME）智能保温杯温度显示316不锈钢大容量商务水杯男女学生便携杯子车载户外带提手 月光白500ML', 126, 'http://localhost:28089/upload/20220107_1730159.jpg', 'http://localhost:28089/upload/20220107_1730159.jpg', '<p>雄泰（SHINETIME）智能保温杯温度显示316不锈钢大容量商务水杯男女学生便携杯子车载户外带提手 月光白500ML<br/></p>', 129, 129, 1000, '雄泰', 0, 0, '2020-12-31 08:21:07', 0, '2022-01-07 17:30:39');
INSERT INTO `tb_sika_mall_goods_info` VALUES (10912, '联想小新Air14', '联想笔记本电脑小新Air14 英特尔酷睿i5 14英寸全面屏办公轻薄本(11代i5-1155G7 16G 512G 高色域)银', 124, 'http://localhost:28089/upload/20220107_17274178.jpg', 'http://localhost:28089/upload/20220107_17274178.jpg', '<p>联想笔记本电脑小新Air14 英特尔酷睿i5 14英寸全面屏办公轻薄本(11代i5-1155G7 16G 512G 高色域)银</p>', 4399, 4399, 1000, '联想', 0, 0, '2020-12-31 08:22:11', 0, '2022-01-07 17:27:44');
INSERT INTO `tb_sika_mall_goods_info` VALUES (10913, '华为智慧屏 SE', '华为智慧屏 SE 55英寸 超薄电视 广色域鸿鹄画质 4K超高清智能液晶电视机 HD55DESA 2+16GB搭载 HarmonyOS 2', 20, 'http://localhost:28089/upload/20220107_1726347.jpg', 'http://localhost:28089/upload/20220107_1726347.jpg', '<p>华为智慧屏 SE 55英寸 超薄电视 广色域鸿鹄画质 4K超高清智能液晶电视机 HD55DESA 2+16GB搭载 HarmonyOS 2<br/></p>', 1899, 1799, 20, '华为', 0, 0, '2021-01-09 05:28:57', 0, '2022-01-07 17:26:36');
INSERT INTO `tb_sika_mall_goods_info` VALUES (10914, 'TCL电视 65T8E-Pro', 'TCL电视 65T8E-Pro 65英寸 QLED原色量子点电视 4K超高清 超薄金属全面屏 液晶京东小家平板电视 以旧换新', 21, 'http://localhost:28089/upload/20220107_17253855.jpg', 'http://localhost:28089/upload/20220107_17253855.jpg', '<p>TCL电视 65T8E-Pro 65英寸 QLED原色量子点电视 4K超高清 超薄金属全面屏 液晶京东小家平板电视 以旧换新<br/></p><p><i></i></p>', 4499, 3999, 1000, 'TCL', 0, 0, '2021-01-09 05:29:35', 0, '2022-01-07 17:25:40');
INSERT INTO `tb_sika_mall_goods_info` VALUES (10915, '小米10S', '小米10S 骁龙870 哈曼卡顿对称式双扬立体声 8GB+256GB 蓝色 旗舰手机', 120, 'http://localhost:28089/upload/20220107_17202275.jpg', 'http://localhost:28089/upload/20220107_17202275.jpg', '<p>小米10S 骁龙870 哈曼卡顿对称式双扬立体声 8GB+256GB 蓝色 旗舰手机<br/></p>', 2699, 2699, 100, '小米', 0, 0, '2021-01-09 05:30:21', 0, '2022-01-07 17:20:32');
INSERT INTO `tb_sika_mall_goods_info` VALUES (10916, 'Redmi K40', 'Redmi K40 骁龙870 三星AMOLED 120Hz高刷直屏 4800万高清三摄 12GB+256GB 幻境 游戏电竞5G手机 小米 红米', 121, 'http://localhost:28089/upload/20220107_17172240.jpg', 'http://localhost:28089/upload/20220107_17172240.jpg', '<p>Redmi K40 骁龙870 三星AMOLED 120Hz高刷直屏 4800万高清三摄 12GB+256GB 幻境 游戏电竞5G手机 小米 红米</p>', 2299, 2299, 100, '小米Redmi', 0, 0, '2021-01-09 05:31:17', 0, '2022-01-07 17:17:28');
INSERT INTO `tb_sika_mall_goods_info` VALUES (10917, '小米电视EA65', '小米电视EA65 2022款 65英寸 金属全面屏 远场语音 逐台校准4K超高清智能教育电视机L65M7-EA', 20, 'http://localhost:28089/upload/20220107_17161488.jpg', 'http://localhost:28089/upload/20220107_17161488.jpg', '<p>小米电视EA65 2022款 65英寸 金属全面屏 远场语音 逐台校准4K超高清智能教育电视机L65M7-EA</p>', 2899, 2699, 197, '小米Redmi', 0, 0, '2021-01-09 06:49:51', 0, '2022-01-07 17:16:21');
INSERT INTO `tb_sika_mall_goods_info` VALUES (10918, '小米电视A55', '小米 Redmi 电视 A55 55英寸 4K HDR超高清 立体声澎湃音效 智能网络教育电视L55R6-A', 21, 'http://localhost:28089/upload/20220107_17144117.jpg', 'http://localhost:28089/upload/20220107_17144117.jpg', '<p>小米 Redmi 电视 A55 55英寸 4K HDR超高清 立体声澎湃音效 智能网络教育电视L55R6-A<br/></p>', 1799, 1699, 100, '小米Redmi', 0, 0, '2021-01-09 06:50:22', 0, '2022-01-07 17:46:23');
INSERT INTO `tb_sika_mall_goods_info` VALUES (10919, '英微（IN&VI）投影幕布', '英微（IN&VI）投影幕布GHZ钛晶抗光硬屏150英寸16:9激光电视超短焦投影仪抗光幕布家用办公4k窄边画框幕布', 148, 'http://localhost:28089/upload/20220107_17410960.jpg', 'http://localhost:28089/upload/20220107_17410960.jpg', '<p>英微（IN&amp;VI）投影幕布GHZ钛晶抗光硬屏150英寸16:9激光电视超短焦投影仪抗光幕布家用办公4k窄边画框幕布<br/></p>', 11999, 11999, 1000, '英微', 0, 0, '2022-01-07 17:41:11', 0, '2022-01-07 17:41:11');
INSERT INTO `tb_sika_mall_goods_info` VALUES (10920, '乐事Lay\'s薯片', '乐事Lay\'s薯片 休闲零食 膨化食品 黄瓜味 75克（10袋装）', 150, 'http://localhost:28089/upload/20220107_1749595.jpg', 'http://localhost:28089/upload/20220107_1749595.jpg', '<p>乐事Lay\'s薯片 休闲零食 膨化食品 黄瓜味 75克</p>', 68, 60, 1000, '乐事', 0, 0, '2022-01-07 17:50:34', 0, '2022-01-07 18:10:17');

-- ----------------------------
-- Table structure for tb_sika_mall_index_config
-- ----------------------------
DROP TABLE IF EXISTS `tb_sika_mall_index_config`;
CREATE TABLE `tb_sika_mall_index_config`  (
  `config_id` bigint NOT NULL AUTO_INCREMENT COMMENT '首页配置项主键id',
  `config_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '显示字符(配置搜索时不可为空，其他可为空)',
  `config_type` tinyint NOT NULL DEFAULT 0 COMMENT '1-搜索框热搜 2-搜索下拉框热搜 3-(首页)热销商品 4-(首页)新品上线 5-(首页)为你推荐',
  `goods_id` bigint NOT NULL DEFAULT 0 COMMENT '商品id 默认为0',
  `redirect_url` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '##' COMMENT '点击后的跳转地址(默认不跳转)',
  `config_rank` int NOT NULL DEFAULT 0 COMMENT '排序值(字段越大越靠前)',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '删除标识字段(0-未删除 1-已删除)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `create_user` int NOT NULL DEFAULT 0 COMMENT '创建者id',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最新修改时间',
  `update_user` int NULL DEFAULT 0 COMMENT '修改者id',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_sika_mall_index_config
-- ----------------------------
INSERT INTO `tb_sika_mall_index_config` VALUES (25, '小米电视A55', 3, 10918, '##', 0, 0, '2020-12-31 08:05:22', 0, '2020-12-31 08:05:22', 0);
INSERT INTO `tb_sika_mall_index_config` VALUES (26, '小米电视EA65', 3, 10917, '##', 0, 0, '2020-12-31 08:05:53', 0, '2020-12-31 08:05:53', 0);
INSERT INTO `tb_sika_mall_index_config` VALUES (27, 'TCL电视 65T8E-Pro', 3, 10914, '##', 0, 0, '2020-12-31 08:06:01', 0, '2020-12-31 08:06:01', 0);
INSERT INTO `tb_sika_mall_index_config` VALUES (28, '华为智慧屏 SE', 3, 10913, '##', 0, 0, '2020-12-31 08:06:12', 0, '2020-12-31 08:06:12', 0);
INSERT INTO `tb_sika_mall_index_config` VALUES (29, '质优价廉 英微投影配件', 4, 10919, '##', 0, 0, '2020-12-31 08:06:31', 0, '2020-12-31 08:06:31', 0);
INSERT INTO `tb_sika_mall_index_config` VALUES (30, '海信 65J65G-PRO', 4, 10002, '##', 0, 0, '2020-12-31 08:06:42', 0, '2020-12-31 08:06:42', 0);
INSERT INTO `tb_sika_mall_index_config` VALUES (31, '小米11 Pro 5G', 4, 10003, '##', 0, 0, '2020-12-31 08:06:50', 0, '2020-12-31 08:06:50', 0);
INSERT INTO `tb_sika_mall_index_config` VALUES (32, '小米电视EA75 2022款', 4, 10001, '##', 0, 0, '2020-12-31 08:06:58', 0, '2020-12-31 08:06:58', 0);
INSERT INTO `tb_sika_mall_index_config` VALUES (33, 'Redmi K40', 5, 10916, '##', 0, 0, '2020-12-31 08:07:15', 0, '2020-12-31 08:07:15', 0);
INSERT INTO `tb_sika_mall_index_config` VALUES (34, '小米10S', 5, 10915, '##', 0, 0, '2020-12-31 08:07:26', 0, '2020-12-31 08:07:26', 0);
INSERT INTO `tb_sika_mall_index_config` VALUES (35, '荣耀50', 5, 10004, '##', 0, 0, '2020-12-31 08:07:41', 0, '2020-12-31 08:07:41', 0);
INSERT INTO `tb_sika_mall_index_config` VALUES (36, '小米11 Pro 5G', 5, 10003, '##', 0, 0, '2020-12-31 08:07:58', 0, '2020-12-31 08:07:58', 0);

-- ----------------------------
-- Table structure for tb_sika_mall_order
-- ----------------------------
DROP TABLE IF EXISTS `tb_sika_mall_order`;
CREATE TABLE `tb_sika_mall_order`  (
  `order_id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单表主键id',
  `order_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '订单号',
  `user_id` bigint NOT NULL DEFAULT 0 COMMENT '用户主键id',
  `total_price` int NOT NULL DEFAULT 1 COMMENT '订单总价',
  `pay_status` tinyint NOT NULL DEFAULT 0 COMMENT '支付状态:0.未支付,1.支付成功,-1:支付失败',
  `pay_type` tinyint NOT NULL DEFAULT 0 COMMENT '0.无 1.支付宝支付 2.微信支付',
  `pay_time` datetime NULL DEFAULT NULL COMMENT '支付时间',
  `order_status` tinyint NOT NULL DEFAULT 0 COMMENT '订单状态:0.待支付 1.已支付 2.配货完成 3:出库成功 4.交易成功 -1.手动关闭 -2.超时关闭 -3.商家关闭',
  `extra_info` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '' COMMENT '订单body',
  `user_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '收货人姓名',
  `user_phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '收货人手机号',
  `user_address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '收货人收货地址',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '删除标识字段(0-未删除 1-已删除)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最新修改时间',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_sika_mall_order
-- ----------------------------
INSERT INTO `tb_sika_mall_order` VALUES (26, '16102790071422022', 9, 880, 0, 0, NULL, 0, '', '', '', '', 0, '2021-01-10 11:43:26', '2021-01-10 11:43:26');
INSERT INTO `tb_sika_mall_order` VALUES (27, '16102798150109323', 9, 120, 0, 0, NULL, 0, '', '', '', '', 0, '2021-01-10 11:56:54', '2021-01-10 11:56:54');
INSERT INTO `tb_sika_mall_order` VALUES (28, '16415501435231281', 12, 8097, 0, 0, NULL, 0, '', '', '', '北京市海淀区北京邮电大学综合服务楼', 0, '2022-01-07 18:09:03', '2022-01-07 18:09:03');

-- ----------------------------
-- Table structure for tb_sika_mall_order_item
-- ----------------------------
DROP TABLE IF EXISTS `tb_sika_mall_order_item`;
CREATE TABLE `tb_sika_mall_order_item`  (
  `order_item_id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单关联购物项主键id',
  `order_id` bigint NOT NULL DEFAULT 0 COMMENT '订单主键id',
  `goods_id` bigint NOT NULL DEFAULT 0 COMMENT '关联商品id',
  `goods_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '下单时商品的名称(订单快照)',
  `goods_cover_img` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '下单时商品的主图(订单快照)',
  `selling_price` int NOT NULL DEFAULT 1 COMMENT '下单时商品的价格(订单快照)',
  `goods_count` int NOT NULL DEFAULT 1 COMMENT '数量(订单快照)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`order_item_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_sika_mall_order_item
-- ----------------------------
INSERT INTO `tb_sika_mall_order_item` VALUES (44, 26, 10002, '小猫牌大型混凝土', 'http://localhost:28089/upload/20201231_15544663.png', 80, 6, '2021-01-10 11:43:26');
INSERT INTO `tb_sika_mall_order_item` VALUES (45, 26, 10001, '小猫牌小型混凝土', 'http://localhost:28089/upload/20201231_15532321.jpg', 40, 1, '2021-01-10 11:43:26');
INSERT INTO `tb_sika_mall_order_item` VALUES (46, 26, 10918, '混凝土商品4', 'http://localhost:28089/upload/20210109_14501710.png', 180, 2, '2021-01-10 11:43:26');
INSERT INTO `tb_sika_mall_order_item` VALUES (47, 27, 10001, '小猫牌小型混凝土', 'http://localhost:28089/upload/20201231_15532321.jpg', 40, 1, '2021-01-10 11:56:54');
INSERT INTO `tb_sika_mall_order_item` VALUES (48, 27, 10002, '小猫牌大型混凝土', 'http://localhost:28089/upload/20201231_15544663.png', 80, 1, '2021-01-10 11:56:54');
INSERT INTO `tb_sika_mall_order_item` VALUES (49, 28, 10917, '小米电视EA65', 'http://localhost:28089/upload/20220107_17161488.jpg', 2699, 3, '2022-01-07 18:09:03');

-- ----------------------------
-- Table structure for tb_sika_mall_shopping_cart_item
-- ----------------------------
DROP TABLE IF EXISTS `tb_sika_mall_shopping_cart_item`;
CREATE TABLE `tb_sika_mall_shopping_cart_item`  (
  `cart_item_id` bigint NOT NULL AUTO_INCREMENT COMMENT '购物项主键id',
  `user_id` bigint NOT NULL COMMENT '用户主键id',
  `goods_id` bigint NOT NULL DEFAULT 0 COMMENT '关联商品id',
  `goods_count` int NOT NULL DEFAULT 1 COMMENT '数量(最大为5)',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '删除标识字段(0-未删除 1-已删除)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最新修改时间',
  PRIMARY KEY (`cart_item_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 94 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_sika_mall_shopping_cart_item
-- ----------------------------
INSERT INTO `tb_sika_mall_shopping_cart_item` VALUES (84, 9, 10918, 2, 0, '2021-01-10 06:36:39', '2021-01-10 16:32:53');
INSERT INTO `tb_sika_mall_shopping_cart_item` VALUES (93, 9, 10003, 4, 0, '2021-01-10 12:10:24', '2021-01-10 12:10:24');
INSERT INTO `tb_sika_mall_shopping_cart_item` VALUES (94, 12, 10917, 3, 1, '2022-01-07 18:08:52', '2022-01-07 18:08:59');

-- ----------------------------
-- Table structure for tb_sika_mall_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_sika_mall_user`;
CREATE TABLE `tb_sika_mall_user`  (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户主键id',
  `nick_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户昵称',
  `login_name` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '登陆名称(默认为手机号)',
  `password_md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'MD5加密后的密码',
  `introduce_sign` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '个性签名',
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '收货地址',
  `is_deleted` tinyint NOT NULL DEFAULT 0 COMMENT '注销标识字段(0-正常 1-已注销)',
  `locked_flag` tinyint NOT NULL DEFAULT 0 COMMENT '锁定标识字段(0-未锁定 1-已锁定)',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tb_sika_mall_user
-- ----------------------------
INSERT INTO `tb_sika_mall_user` VALUES (9, '小小', '15501080299', '5371a9b99928673430664ca123bb599a', '', '北京市海淀区清华东路35号北京林业大学', 0, 0, '2020-12-28 14:56:50');
INSERT INTO `tb_sika_mall_user` VALUES (10, '15501080296', '15501080296', '5371a9b99928673430664ca123bb599a', '', '北京市海淀区清华东路35号北京林业大学', 0, 0, '2021-01-07 06:20:40');
INSERT INTO `tb_sika_mall_user` VALUES (11, '15501080297', '15501080297', '5371a9b99928673430664ca123bb599a', '', '北京市海淀区清华东路35号北京林业大学', 0, 0, '2021-01-07 06:21:08');
INSERT INTO `tb_sika_mall_user` VALUES (12, '1308673..', '13086732406', 'e10adc3949ba59abbe56e057f20f883e', '55555', '北京市海淀区清华东路35号北京林业大学', 0, 0, '2022-01-07 16:26:31');

SET FOREIGN_KEY_CHECKS = 1;
