/*
Navicat MySQL Data Transfer

Source Server         : 阿里云
Source Server Version : 50724
Source Host           : 39.105.148.214:3306
Source Database       : xiaomi

Target Server Type    : MYSQL
Target Server Version : 50724
File Encoding         : 65001

Date: 2019-09-10 20:59:03
*/

DROP DATABASE IF EXISTS xiaomi;
CREATE DATABASE xiaomi DEFAULT CHARSET=UTF8;
USE xiaomi;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `cid` int(10) NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `cname` varchar(50) NOT NULL COMMENT '分类名称',
  `state` int(1) DEFAULT NULL COMMENT '分类状态',
  `order_number` int(5) DEFAULT NULL COMMENT '排序字段',
  `description` varchar(100) DEFAULT NULL COMMENT '分类说明',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES ('2', '航天系列', '1', '1', '卫星、航母火箭等', '2018-12-25 09:53:55');
INSERT INTO `category` VALUES ('3', '手机 平板', '1', '10', '各种小米系手机', '2018-12-25 14:20:12');
INSERT INTO `category` VALUES ('4', '家电', '1', '2', ' 电视、冰箱、洗衣机等', '2018-12-25 14:20:42');
INSERT INTO `category` VALUES ('5', '儿童玩具', '1', '33', '各种玩具', '2018-12-25 16:38:14');
INSERT INTO `category` VALUES ('6', '智能产品', '1', '2', '', '2018-12-26 14:46:27');
INSERT INTO `category` VALUES ('7', '图书 内容', '1', '55', '', '2018-12-25 09:53:55');
INSERT INTO `category` VALUES ('8', '移动电源 电池 插线板', '1', '38', '123', '2018-12-26 14:48:16');
INSERT INTO `category` VALUES ('9', '耳机 音响', '1', '34', '', '2018-12-26 14:48:31');
INSERT INTO `category` VALUES ('10', '保护套 贴膜', '1', '35', '', '2018-12-26 14:48:47');
INSERT INTO `category` VALUES ('11', '线材 支架 存储卡', '1', '36', '', '2018-12-26 14:49:04');
INSERT INTO `category` VALUES ('12', '箱包 服饰 鞋 眼镜', '1', '37', '', '2018-12-26 14:49:19');
INSERT INTO `category` VALUES ('13', '米兔 生活周边', '1', '38', '', '2018-12-26 14:49:33');

-- ----------------------------
-- Table structure for product
-- ----------------------------
DROP TABLE IF EXISTS `product`;
CREATE TABLE `product` (
  `pid` int(10) NOT NULL AUTO_INCREMENT COMMENT '产品id',
  `pname` varchar(50) NOT NULL COMMENT '产品名称',
  `color` varchar(50) DEFAULT NULL COMMENT '产品颜色',
  `price` double unsigned NOT NULL COMMENT '产品价格',
  `description` varchar(500) DEFAULT NULL COMMENT '产品说明',
  `pic` varchar(200) DEFAULT NULL COMMENT '产品图片',
  `state` int(5) DEFAULT '0' COMMENT '产品状态',
  `version` varchar(50) DEFAULT NULL COMMENT '产品版本',
  `product_date` datetime DEFAULT NULL COMMENT '产品添加时间',
  `cid` int(10) DEFAULT NULL COMMENT '分类id',
  PRIMARY KEY (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of product
-- ----------------------------
INSERT INTO `product` VALUES ('3', '小浣熊干脆面1', '棕色1', '1', '好吃不贵', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV12ZLaAGbdHAAC_Ifc_ub0903.jpg', '1', '1.1', '2018-12-26 11:25:45', '2');
INSERT INTO `product` VALUES ('4', '神舟五号', '白色', '1000000', '中国骄傲', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV12ZKiATS12AABw3N_cPkQ899.jpg', '4', '神州5号', '2018-12-25 00:00:00', '2');
INSERT INTO `product` VALUES ('9', '小米笔记本Air 13.3', '银灰色', '5399', '高性能轻薄笔记本\r\n设计制图、运行大型 3D 游戏，复杂任务也可以轻松驾驭。\r\n', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV12ZMaAfOUgAABw3N_cPkQ903.jpg', '4', 'air', '2018-12-26 15:22:42', '3');
INSERT INTO `product` VALUES ('10', '九号平衡车', '白色', '1999', '最高车速：	约16km/h\r\n最大扭矩：	整机35Nm*2', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV12ZOuALSxhAAAxJA5ENfk277.jpg', '4', '1', '2018-12-26 15:26:09', '6');
INSERT INTO `product` VALUES ('11', '小米路由器4Q', '蓝色', '199', 'MiNET 一键快连智能设备', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV12ZPuALc-LAAAzMxjtkBo223.jpg', '4', '1.1', '2018-12-26 15:28:15', '6');
INSERT INTO `product` VALUES ('12', '小米6X', '蓝色', '4999', '64GB+64GB，赤焰红、樱花粉限时秒杀，到手价1419元', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV12ZQOAVByMAAMIo5RU2Sg367.jpg', '4', '1.1', '2018-12-26 15:30:48', '3');
INSERT INTO `product` VALUES ('13', '小米电视4A 43英寸 青春版', '白色', '4999', '人工智能语音系统\r\n全高清屏\r\n64位四核处理器\r\n', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV12ZRKAevgXAAAzMxjtkBo896.jpg', '0', '1.1', '2018-12-27 09:27:32', '4');
INSERT INTO `product` VALUES ('14', '小米电视4A 43英寸 老年版', '棕色', '998', '人工智能语音系统', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV12ZRuAd2TCAAB5wAxxa14265.jpg', '0', '1', '2018-12-27 09:29:40', '4');
INSERT INTO `product` VALUES ('15', '小米电视4A 43英寸 少年版', '棕色', '15000', '人工智能语音系统', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV12ZT6ABRz8AAB5wAxxa14940.jpg', '3', '1', '2018-12-27 09:30:22', '4');
INSERT INTO `product` VALUES ('16', '小米电视4A 43英寸 幼儿版', '蓝色', '998', '海量影视内容', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV12ZWCAbM6nAAB5wAxxa14077.jpg', '2', 'air', '2018-12-27 09:31:05', '4');
INSERT INTO `product` VALUES ('17', '小米电视4A 43英寸 旗舰版', '棕色', '5399', '杜比音效', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV12ZOGAR9GaAAB5wAxxa14904.jpg', '3', '1.1', '2018-12-26 15:22:42', '4');
INSERT INTO `product` VALUES ('18', '米家互联网空调', '白色', '998', ' 米家互联网空调（一级能效）', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV12ZW-AVhE6AAAzMxjtkBo022.jpg', '3', '1', '2018-12-27 09:33:45', '4');
INSERT INTO `product` VALUES ('19', '米家互联网空调（升级版）', '白色', '15000', ' 米家互联网空调（一级能效）', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV12ZZ-AEVDlAAAzMxjtkBo381.jpg', '0', '123', '2018-12-27 09:34:33', '4');
INSERT INTO `product` VALUES ('20', ' 米家互联网空调(土豪版)', '蓝色', '15000', ' 米家互联网空调（一级能效', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV12ZdSAT_O7AAAzMxjtkBo436.jpg', '1', '1', '2018-12-27 09:35:27', '4');
INSERT INTO `product` VALUES ('21', '小米手环1', '黑色', '199', '心率、睡眠、记步，健康管理', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV12ZduAXQSnAACwNsyDjng383.jpg', '3', '1.1', '2018-12-27 10:23:07', '6');
INSERT INTO `product` VALUES ('22', '小米手环2', '棕色1', '199', '心率、睡眠、记步，健康管理 / 长达20天续航能力', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV12ZtKAPCtiAACwNsyDjng955.jpg', '0', '123', '2018-12-27 10:23:56', '6');
INSERT INTO `product` VALUES ('23', '小米手环3', '棕色', '998', '长达20天续航能力', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV12ZtmAAfMnAACwNsyDjng397.jpg', '4', '123', '2018-12-27 10:24:23', '6');
INSERT INTO `product` VALUES ('24', '小米手环4', '棕色', '998', '微信、QQ来电等消息内容直接显示', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV12Zv6ARxpqAACwNsyDjng716.jpg', '2', '1', '2018-12-27 10:24:49', '6');
INSERT INTO `product` VALUES ('25', '小米手环5', '蓝色', '998', '50米游泳防水', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV12ZwuAGrgHAACwNsyDjng950.jpg', '1', '1.1', '2018-12-27 10:25:24', '6');
INSERT INTO `product` VALUES ('26', '小米米家行车记录仪1S', '棕色', '998', '索尼 IMX307图像传感器 ', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV12Zx6ALGVxAACwNsyDjng499.jpg', '1', '123', '2018-12-27 10:27:01', '6');
INSERT INTO `product` VALUES ('27', '小米米家行车记录仪2S', '棕色', '998', ' 3D降噪 / IPS大屏 / 本地语音控制', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV12Z0GACu1xAAEzSvr-yA8540.jpg', '2', '1.1', '2018-12-27 10:27:34', '6');
INSERT INTO `product` VALUES ('28', '小米米家行车记录仪3S', '棕色', '998', '小米米家行车记录仪1S', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV12Z0uAF2v9AACwNsyDjng559.jpg', '2', '1', '2018-12-27 10:28:00', '6');
INSERT INTO `product` VALUES ('29', '小米米家行车记录仪4S', '棕色', '15000', '小米米家行车记录仪1S', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV12Z1iAJKFrAAAxJA5ENfk188.jpg', '0', 'air', '2018-12-27 10:28:26', '6');
INSERT INTO `product` VALUES ('30', '小米米家行车记录仪5S', '白色', '998', '小米米家行车记录仪1S', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV12Z2WAKM3DAACwNsyDjng574.jpg', '2', '123', '2018-12-27 10:29:01', '6');
INSERT INTO `product` VALUES ('31', '康师傅', '红色', '12', '122212121', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV12Z2-ATDQbAAAxJA5ENfk780.jpg', '1', '12', '2019-02-20 17:25:47', '13');
INSERT INTO `product` VALUES ('34', 'master', 'red', '5000', '123456', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV12YxSAOnm_AAl7TPOFk20045.jpg', '0', 'a', '2019-09-09 14:35:22', '4');
INSERT INTO `product` VALUES ('36', '小米CC9', '深蓝星球', '1699', '3200万+4800万 前后双旗舰相机\n179g 超轻手感\n4030mAh 超大电量\n6.39\" 三星 AMOLED屏幕\n德国 VDE 低蓝光护眼认证\n第七代光感屏幕指纹识别\n八核性能双重加速\n骁龙710，搭载双引擎 Turbo', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV13DaiASmM8AAA0WlWe4f8882.png', '4', '小米', '2019-09-10 02:44:30', '3');
INSERT INTO `product` VALUES ('37', '小米CC9e', '白色恋人', '1299', '3200万旗舰自拍\n4800万旗舰相机\n全球首发 骁龙665\n6.088英寸\n4030mAh大电量\n屏幕指纹识别\n全曲面玻璃机身\n德国莱茵TÜV护眼认证', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV13Dq-AGlVbAABAW8Ackqk510.png', '4', '小米', '2019-09-10 02:48:32', '3');
INSERT INTO `product` VALUES ('38', '小米CC9 美图手机定制版', '暗夜王子', '2599', '第七代光感屏幕指纹识别\n小爱同学语音助理\n多功能NFC\nSmart PA 外放\nHi-Res Audio\n红外遥控', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV13D2-AfgVXAAAsDodyAgQ607.png', '4', '小米', '2019-09-10 02:51:40', '3');
INSERT INTO `product` VALUES ('39', '小米9', '小米9', '2799', '骁龙855旗舰处理器\n索尼 4800 万像素三摄\n支持超广角、微距拍摄\n小米首款20W无线闪充\n全息幻彩玻璃机身\n全曲面轻薄设计\n第五代极速屏下指纹\n三星 AMOLED 屏幕\n标配27W 有线快充\n蓝宝石玻璃镜片', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV13EDaAa_KNAAA8KdHaDWY040.png', '4', '小米', '2019-09-10 02:54:48', '3');
INSERT INTO `product` VALUES ('40', '小米MIX3', '黑色', '2599', 'DxOMark拍照108分\n磁动力滑盖全面屏\n四曲面陶瓷机身 \n骁龙845旗舰处理器\n包装盒内附赠10W无线充电器', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV13EMeAabWRAABMcPeJtS8948.png', '4', '小米', '2019-09-10 02:56:42', '3');
INSERT INTO `product` VALUES ('41', '小米MIX2s', '陶瓷白', '1799', '骁龙 845+8GB+256GB\n手机中的艺术品', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV13Ek2ACpvlAAA-TkA7Lq0039.png', '0', '小米', '2019-09-10 03:02:58', '3');
INSERT INTO `product` VALUES ('42', 'Redmi Note 8 Pro', '冰翡翠', '1599', '6400万旗舰级全场景四摄\nG90T专业游戏芯片，液冷散热', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV13E6CAM-jTAAA4sNSOy28018.png', '0', '小米', '2019-09-10 03:08:36', '3');
INSERT INTO `product` VALUES ('43', 'Redmi Note 8', '皓月白', '999', '4000mAh超长续航\n6.3英寸水滴全面屏', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV13FW2AIkAUAAA8D6Iuf6Q472.png', '0', '小米', '2019-09-10 03:09:49', '3');
INSERT INTO `product` VALUES ('44', 'Redmi K20 Pro', '夏之密语', '2699', '索尼4800万超广角三摄 \n 8层石墨立体散热', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV13FVeAapeqAAAOJOgBFYE333.jpg', '0', '小米', '2019-09-10 03:10:59', '3');
INSERT INTO `product` VALUES ('45', 'Redmi K20', '冰川蓝', '1799', '骁龙730处理器 \n 前置2000万升降式相机', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV13FayAJR9PAAANQd7uIQg408.jpg', '0', '小米', '2019-09-10 03:17:18', '3');
INSERT INTO `product` VALUES ('46', 'Redmi 红米电视 70英寸', '默认', '3799', '4K画质 细腻如真\nPatchWall智能系统 内置小爱同学', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV13FgmAI6WeAAAIZn_FHIc48.webp', '0', '小米', '2019-09-10 03:18:57', '4');
INSERT INTO `product` VALUES ('47', '小米壁画电视 65英寸', '默认', '6999', '壁画外观\n全面屏设计', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV13FpmARE76AAAYujmC8gM955.jpg', '0', '小米', '2019-09-10 03:21:10', '4');
INSERT INTO `product` VALUES ('48', '小米全面屏电视E55A', '默认', '1799', '全面屏设计\n纤薄机身', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV13FviAQFBAAAAUrJIV5w4978.jpg', '0', '小米', '2019-09-10 03:22:47', '4');
INSERT INTO `product` VALUES ('49', '小米电视4A 32英寸', '默认', '699', '64位四核处理器 \n1GB+4GB大内存', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV13F0OAJatTAABkNe6FfjY520.png', '0', '小米', '2019-09-10 03:24:03', '4');
INSERT INTO `product` VALUES ('50', '小米笔记本Pro 15.6\" 2019款', null, '5499', 'NVIDIA MX250 2G独显 \n72%NTSC高色域全高清屏', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV13F8OAbmwJAABdS5isvwU781.png', '0', '小米', '2019-09-10 03:26:15', '6');
INSERT INTO `product` VALUES ('51', '小米笔记本15.6\" 2019款 独显版', null, '3899', '最高512G高速固态硬盘\n独立数字键盘', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV13GDKAFY7sAABfMPpUAcA556.png', '0', '小米', '2019-09-10 03:28:04', '6');
INSERT INTO `product` VALUES ('52', 'Air 13.3\" 2019款', null, '5299', '轻薄全金属机身\n 9.5小时超长续航', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV13GJqALTwoAABbplxd-aY153.png', '0', '小米', '2019-09-10 03:29:33', '6');
INSERT INTO `product` VALUES ('53', '小米路由器Mesh（两只装）', '白色', '999', '多通道高速混合传输 \n高通4核CPU ', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV13GguAZXhdAAAGQhuISSQ218.jpg', '0', '小米', '2019-09-10 03:31:00', '6');
INSERT INTO `product` VALUES ('54', '米家扫地机器人', '白色', '1299', '高精度激光测距，智能规划路径 \n1800Pa 大风压澎湃吸力', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV13GWiAcEOkAAGaH3rmd3Y256.jpg', '0', '小米', '2019-09-10 03:32:58', '8');
INSERT INTO `product` VALUES ('55', '米家石英表', '黑色', '299', '计步抬腕可见', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV13Gc2AR19bAACVEygk3Bs629.jpg', '0', '小米', '2019-09-10 03:34:44', '8');
INSERT INTO `product` VALUES ('56', '小米小爱音箱 Play', '白色', '99', '智能设备控制\n人工智能语音对话', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV13GpKAGGIpAACiiKEGtmA409.jpg', '4', '小米', '2019-09-10 03:37:40', '9');
INSERT INTO `product` VALUES ('57', '小米简约休闲双肩包', 'yellow', '99', '490g 极致轻便\n创新防水侧袋 ', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV13GtqATu0gAABzkgH17K8880.jpg', '2', '小米', '2019-09-10 03:39:09', '12');
INSERT INTO `product` VALUES ('58', '米家自动洗手机套装', '白色', '99', '免接触更卫生 \n 99.9%有效抑菌', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV13Gy6ARlG7AAPyxov6eeY297.jpg', '1', '小米', '2019-09-10 03:40:32', '5');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `uid` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL COMMENT '昵称',
  `sex` int(1) DEFAULT NULL COMMENT '性别',
  `phone_number` varchar(20) NOT NULL COMMENT '手机号码',
  `area` varchar(100) DEFAULT NULL COMMENT '住址',
  `manager` int(1) NOT NULL COMMENT '权限，普通用户0，管理员1',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `salt` varchar(64) NOT NULL COMMENT '盐池',
  `photo` varchar(255) DEFAULT 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1567682021545&di=d2759facd1069ae1f15cd0940aecba66&imgtype=0&src=http%3A%2F%2Fpic16.nipic.com%2F20110918%2F7211809_130649282176_2.jpg' COMMENT '头像',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `phone_number` (`phone_number`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('2', 'master', '0', '13668309573', '四川', '2', 'master', '30/V5PVWYcTIKwdZVbni/Q==', '49bfc53c-a82b-4405-a921-bc9008b7a66c', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV1zL4uAcB1NAAAkPUeeSN4974.jpg', '2019-09-08 05:44:43');
INSERT INTO `user` VALUES ('19', 'girl', '0', '13668309571', '四川', '0', 'users', 'dhspiCqANR0eXGgWCEck8Q==', '94526b39-b158-4761-8be3-ddcf3dd8ec41', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV13EdKAVRynAAC_Ifc_ub0724.jpg', '2019-09-10 08:18:21');
INSERT INTO `user` VALUES ('22', 'users', '0', '15888762514', '四川', '0', 'admin', '2RI39YegaEPIX7DPNsQdMA==', '330c5cc3-8ffc-4810-9d84-28d2305e19fa', 'http://39.105.148.214:8888/image/M00/00/00/rBgwPV13XRCAFbqhAAMIo5RU2Sg603.jpg', '2019-09-10 08:21:39');
