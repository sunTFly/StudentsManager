/*
Navicat MySQL Data Transfer

Source Server         : Windows
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : studentsmanager

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2019-05-01 11:38:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_as_class
-- ----------------------------
DROP TABLE IF EXISTS `tb_as_class`;
CREATE TABLE `tb_as_class` (
  `asNo` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `claNo` char(4) DEFAULT NULL,
  `teaNo` char(10) DEFAULT NULL,
  `graNo` char(2) DEFAULT NULL,
  `section` int(11) DEFAULT NULL,
  `week` int(11) DEFAULT NULL,
  PRIMARY KEY (`asNo`),
  KEY `claNo` (`claNo`) USING BTREE,
  KEY `teaNo` (`teaNo`) USING BTREE,
  KEY `graNo` (`graNo`),
  CONSTRAINT `tb_as_class_ibfk_1` FOREIGN KEY (`claNo`) REFERENCES `tb_cla` (`claNo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tb_as_class_ibfk_2` FOREIGN KEY (`teaNo`) REFERENCES `tb_tea` (`teaNo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tb_as_class_ibfk_3` FOREIGN KEY (`graNo`) REFERENCES `tb_gra` (`garNo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_as_class
-- ----------------------------
INSERT INTO `tb_as_class` VALUES ('1', '0608', '0001', '16', '2', '1');
INSERT INTO `tb_as_class` VALUES ('2', '0609', '0002', '16', '3', '1');
INSERT INTO `tb_as_class` VALUES ('3', '0610', '0003', '16', '1', '2');
INSERT INTO `tb_as_class` VALUES ('4', '0611', '0004', '16', '2', '3');
INSERT INTO `tb_as_class` VALUES ('5', '0612', '0005', '16', '1', '4');
INSERT INTO `tb_as_class` VALUES ('6', '0613', '0006', '16', '1', '5');
INSERT INTO `tb_as_class` VALUES ('7', '0608', '0001', '16', '3', '5');
INSERT INTO `tb_as_class` VALUES ('8', '0612', '0005', '16', '2', '4');
INSERT INTO `tb_as_class` VALUES ('9', '0608', '0001', '16', '1', '7');
INSERT INTO `tb_as_class` VALUES ('10', '0609', '0002', '16', '2', '7');
INSERT INTO `tb_as_class` VALUES ('11', '0610', '0003', '16', '3', '6');
INSERT INTO `tb_as_class` VALUES ('12', '0608', '0001', '16', '3', '7');

-- ----------------------------
-- Table structure for tb_back
-- ----------------------------
DROP TABLE IF EXISTS `tb_back`;
CREATE TABLE `tb_back` (
  `backNo` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `stuNo` char(12) DEFAULT NULL,
  `backTime` datetime DEFAULT NULL,
  `backState` int(11) DEFAULT NULL,
  `time` date DEFAULT NULL,
  PRIMARY KEY (`backNo`),
  KEY `stuNo` (`stuNo`) USING BTREE,
  CONSTRAINT `tb_back_ibfk_1` FOREIGN KEY (`stuNo`) REFERENCES `tb_stu` (`stuNo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8 COMMENT='1是未到签到时间 2是签到成功 3是未归';

-- ----------------------------
-- Records of tb_back
-- ----------------------------
INSERT INTO `tb_back` VALUES ('1', '1', null, '3', '2019-04-17');
INSERT INTO `tb_back` VALUES ('2', '201606084203', null, '3', '2019-04-17');
INSERT INTO `tb_back` VALUES ('3', '201606084206', null, '3', '2019-04-17');
INSERT INTO `tb_back` VALUES ('4', '201606084207', null, '3', '2019-04-17');
INSERT INTO `tb_back` VALUES ('5', '201606084211', null, '3', '2019-04-17');
INSERT INTO `tb_back` VALUES ('6', '201606084259', null, '3', '2019-04-17');
INSERT INTO `tb_back` VALUES ('14', '1', null, '3', '2019-04-18');
INSERT INTO `tb_back` VALUES ('15', '201606084203', null, '3', '2019-04-18');
INSERT INTO `tb_back` VALUES ('16', '201606084206', null, '3', '2019-04-18');
INSERT INTO `tb_back` VALUES ('17', '201606084207', null, '3', '2019-04-18');
INSERT INTO `tb_back` VALUES ('18', '201606084211', null, '3', '2019-04-18');
INSERT INTO `tb_back` VALUES ('19', '201606084259', null, '3', '2019-04-18');
INSERT INTO `tb_back` VALUES ('21', '1', null, '3', '2019-04-19');
INSERT INTO `tb_back` VALUES ('22', '201606084203', null, '3', '2019-04-19');
INSERT INTO `tb_back` VALUES ('23', '201606084206', null, '3', '2019-04-19');
INSERT INTO `tb_back` VALUES ('24', '201606084207', null, '3', '2019-04-19');
INSERT INTO `tb_back` VALUES ('25', '201606084211', null, '3', '2019-04-19');
INSERT INTO `tb_back` VALUES ('26', '201606084259', null, '3', '2019-04-19');
INSERT INTO `tb_back` VALUES ('28', '1', null, '3', '2019-04-20');
INSERT INTO `tb_back` VALUES ('29', '201606084203', null, '3', '2019-04-20');
INSERT INTO `tb_back` VALUES ('30', '201606084206', null, '3', '2019-04-20');
INSERT INTO `tb_back` VALUES ('31', '201606084207', null, '3', '2019-04-20');
INSERT INTO `tb_back` VALUES ('32', '201606084211', null, '3', '2019-04-20');
INSERT INTO `tb_back` VALUES ('33', '201606084259', null, '3', '2019-04-20');
INSERT INTO `tb_back` VALUES ('35', '1', null, '3', '2019-04-21');
INSERT INTO `tb_back` VALUES ('36', '201606084203', null, '3', '2019-04-21');
INSERT INTO `tb_back` VALUES ('37', '201606084206', null, '3', '2019-04-21');
INSERT INTO `tb_back` VALUES ('38', '201606084207', null, '3', '2019-04-21');
INSERT INTO `tb_back` VALUES ('39', '201606084211', null, '3', '2019-04-21');
INSERT INTO `tb_back` VALUES ('40', '201606084259', '2019-04-21 21:53:08', '2', '2019-04-21');
INSERT INTO `tb_back` VALUES ('42', '1', null, '3', '2019-04-22');
INSERT INTO `tb_back` VALUES ('43', '201606084203', null, '3', '2019-04-22');
INSERT INTO `tb_back` VALUES ('44', '201606084206', null, '3', '2019-04-22');
INSERT INTO `tb_back` VALUES ('45', '201606084207', null, '3', '2019-04-22');
INSERT INTO `tb_back` VALUES ('46', '201606084211', null, '3', '2019-04-22');
INSERT INTO `tb_back` VALUES ('47', '201606084259', null, '3', '2019-04-22');
INSERT INTO `tb_back` VALUES ('49', '1', null, '3', '2019-04-23');
INSERT INTO `tb_back` VALUES ('50', '201606084203', null, '3', '2019-04-23');
INSERT INTO `tb_back` VALUES ('51', '201606084206', '2019-04-23 11:28:40', '2', '2019-04-23');
INSERT INTO `tb_back` VALUES ('52', '201606084207', null, '3', '2019-04-23');
INSERT INTO `tb_back` VALUES ('53', '201606084211', null, '3', '2019-04-23');
INSERT INTO `tb_back` VALUES ('54', '201606084259', '2019-04-23 12:55:39', '2', '2019-04-23');
INSERT INTO `tb_back` VALUES ('56', '1', null, '1', '2019-04-24');
INSERT INTO `tb_back` VALUES ('57', '201606084203', null, '1', '2019-04-24');
INSERT INTO `tb_back` VALUES ('58', '201606084206', null, '1', '2019-04-24');
INSERT INTO `tb_back` VALUES ('59', '201606084207', null, '1', '2019-04-24');
INSERT INTO `tb_back` VALUES ('60', '201606084211', null, '1', '2019-04-24');
INSERT INTO `tb_back` VALUES ('61', '201606084259', null, '1', '2019-04-24');

-- ----------------------------
-- Table structure for tb_cho
-- ----------------------------
DROP TABLE IF EXISTS `tb_cho`;
CREATE TABLE `tb_cho` (
  `choNo` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `claNo` char(4) DEFAULT NULL,
  `stuNo` char(255) DEFAULT NULL,
  `teaNo` char(10) DEFAULT NULL,
  PRIMARY KEY (`choNo`),
  KEY `claNo` (`claNo`) USING BTREE,
  KEY `stuNo` (`stuNo`) USING BTREE,
  KEY `teaNo` (`teaNo`) USING BTREE,
  CONSTRAINT `tb_cho_ibfk_1` FOREIGN KEY (`claNo`) REFERENCES `tb_cla` (`claNo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tb_cho_ibfk_2` FOREIGN KEY (`stuNo`) REFERENCES `tb_stu` (`stuNo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tb_cho_ibfk_3` FOREIGN KEY (`teaNo`) REFERENCES `tb_tea` (`teaNo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cho
-- ----------------------------
INSERT INTO `tb_cho` VALUES ('1', '0608', '1', '0001');
INSERT INTO `tb_cho` VALUES ('2', '0609', '1', '0002');
INSERT INTO `tb_cho` VALUES ('3', '0610', '1', '0003');
INSERT INTO `tb_cho` VALUES ('4', '0611', '1', '0004');
INSERT INTO `tb_cho` VALUES ('5', '0612', '1', '0005');
INSERT INTO `tb_cho` VALUES ('6', '0613', '1', '0006');
INSERT INTO `tb_cho` VALUES ('7', '0608', '201606084203', '0001');
INSERT INTO `tb_cho` VALUES ('8', '0609', '201606084203', '0002');
INSERT INTO `tb_cho` VALUES ('9', '0610', '201606084203', '0003');
INSERT INTO `tb_cho` VALUES ('10', '0611', '201606084203', '0004');
INSERT INTO `tb_cho` VALUES ('11', '0612', '201606084203', '0005');
INSERT INTO `tb_cho` VALUES ('12', '0613', '201606084203', '0006');
INSERT INTO `tb_cho` VALUES ('13', '0608', '201606084206', '0001');
INSERT INTO `tb_cho` VALUES ('14', '0609', '201606084206', '0002');
INSERT INTO `tb_cho` VALUES ('15', '0610', '201606084206', '0003');
INSERT INTO `tb_cho` VALUES ('16', '0611', '201606084206', '0004');
INSERT INTO `tb_cho` VALUES ('17', '0612', '201606084206', '0005');
INSERT INTO `tb_cho` VALUES ('18', '0613', '201606084206', '0006');
INSERT INTO `tb_cho` VALUES ('19', '0608', '201606084207', '0001');
INSERT INTO `tb_cho` VALUES ('20', '0609', '201606084207', '0002');
INSERT INTO `tb_cho` VALUES ('21', '0610', '201606084207', '0003');
INSERT INTO `tb_cho` VALUES ('22', '0611', '201606084207', '0004');
INSERT INTO `tb_cho` VALUES ('23', '0612', '201606084207', '0005');
INSERT INTO `tb_cho` VALUES ('24', '0613', '201606084207', '0006');
INSERT INTO `tb_cho` VALUES ('25', '0608', '201606084211', '0001');
INSERT INTO `tb_cho` VALUES ('26', '0609', '201606084211', '0002');
INSERT INTO `tb_cho` VALUES ('27', '0610', '201606084211', '0003');
INSERT INTO `tb_cho` VALUES ('28', '0611', '201606084211', '0004');
INSERT INTO `tb_cho` VALUES ('29', '0612', '201606084211', '0005');
INSERT INTO `tb_cho` VALUES ('30', '0613', '201606084211', '0006');
INSERT INTO `tb_cho` VALUES ('31', '0608', '201606084259', '0001');
INSERT INTO `tb_cho` VALUES ('32', '0609', '201606084259', '0002');
INSERT INTO `tb_cho` VALUES ('33', '0610', '201606084259', '0003');
INSERT INTO `tb_cho` VALUES ('34', '0611', '201606084259', '0004');
INSERT INTO `tb_cho` VALUES ('35', '0612', '201606084259', '0005');
INSERT INTO `tb_cho` VALUES ('36', '0613', '201606084259', '0006');
INSERT INTO `tb_cho` VALUES ('37', null, null, null);
INSERT INTO `tb_cho` VALUES ('38', null, null, null);
INSERT INTO `tb_cho` VALUES ('39', null, null, null);
INSERT INTO `tb_cho` VALUES ('40', null, null, null);
INSERT INTO `tb_cho` VALUES ('41', null, null, null);
INSERT INTO `tb_cho` VALUES ('42', null, null, null);
INSERT INTO `tb_cho` VALUES ('43', null, null, null);
INSERT INTO `tb_cho` VALUES ('44', null, null, null);

-- ----------------------------
-- Table structure for tb_cla
-- ----------------------------
DROP TABLE IF EXISTS `tb_cla`;
CREATE TABLE `tb_cla` (
  `claNo` char(4) NOT NULL,
  `claName` varchar(20) DEFAULT NULL,
  `weekNum` int(11) DEFAULT NULL,
  `term` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`claNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_cla
-- ----------------------------
INSERT INTO `tb_cla` VALUES ('0608', '计算机图形学', '17', '2019第一学期');
INSERT INTO `tb_cla` VALUES ('0609', '软件项目管理', '17', '2019第一学期');
INSERT INTO `tb_cla` VALUES ('0610', 'IT新技术', '17', '2019第一学期');
INSERT INTO `tb_cla` VALUES ('0611', '软件体系结构', '17', '2019第一学期');
INSERT INTO `tb_cla` VALUES ('0612', 'hadoop技术', '17', '2019第一学期');
INSERT INTO `tb_cla` VALUES ('0613', '大学生就业', '17', '2019第一学期');

-- ----------------------------
-- Table structure for tb_dep
-- ----------------------------
DROP TABLE IF EXISTS `tb_dep`;
CREATE TABLE `tb_dep` (
  `depNo` char(2) NOT NULL,
  `depName` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`depNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_dep
-- ----------------------------
INSERT INTO `tb_dep` VALUES ('06', '计算机工程学院');

-- ----------------------------
-- Table structure for tb_dor
-- ----------------------------
DROP TABLE IF EXISTS `tb_dor`;
CREATE TABLE `tb_dor` (
  `droNo` char(4) NOT NULL,
  `dorName` varchar(20) DEFAULT NULL,
  `graNo` char(2) DEFAULT NULL,
  PRIMARY KEY (`droNo`),
  KEY `graNo` (`graNo`) USING BTREE,
  CONSTRAINT `tb_dor_ibfk_1` FOREIGN KEY (`graNo`) REFERENCES `tb_gra` (`garNo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_dor
-- ----------------------------
INSERT INTO `tb_dor` VALUES ('1433', '海棠苑3舍433', '16');
INSERT INTO `tb_dor` VALUES ('1439', '海棠苑3舍439', '16');

-- ----------------------------
-- Table structure for tb_gra
-- ----------------------------
DROP TABLE IF EXISTS `tb_gra`;
CREATE TABLE `tb_gra` (
  `garNo` char(2) NOT NULL,
  `garName` varchar(10) DEFAULT NULL,
  `proNo` char(4) DEFAULT NULL,
  PRIMARY KEY (`garNo`),
  KEY `proNo` (`proNo`) USING BTREE,
  CONSTRAINT `tb_gra_ibfk_1` FOREIGN KEY (`proNo`) REFERENCES `tb_pro` (`proNo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_gra
-- ----------------------------
INSERT INTO `tb_gra` VALUES ('16', '2016级2班', '0608');

-- ----------------------------
-- Table structure for tb_home
-- ----------------------------
DROP TABLE IF EXISTS `tb_home`;
CREATE TABLE `tb_home` (
  `homeNo` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `homeAda` varchar(50) DEFAULT NULL,
  `stuNo` char(12) DEFAULT NULL,
  `homeState` int(11) DEFAULT NULL,
  `homeTime` datetime DEFAULT NULL,
  PRIMARY KEY (`homeNo`),
  KEY `stuNo` (`stuNo`) USING BTREE,
  CONSTRAINT `tb_home_ibfk_1` FOREIGN KEY (`stuNo`) REFERENCES `tb_stu` (`stuNo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_home
-- ----------------------------
INSERT INTO `tb_home` VALUES ('1', '重庆市万州区香樟大道688号靠近海棠苑4舍', '201606084259', '1', '2019-04-23 13:05:55');
INSERT INTO `tb_home` VALUES ('3', '重庆市石柱县', '201606084207', '1', '2019-04-10 00:00:00');

-- ----------------------------
-- Table structure for tb_lea
-- ----------------------------
DROP TABLE IF EXISTS `tb_lea`;
CREATE TABLE `tb_lea` (
  `leaNo` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `stuNo` char(12) DEFAULT NULL,
  `leaRea` varchar(255) DEFAULT NULL,
  `leaTime` datetime DEFAULT NULL,
  `backTime` datetime DEFAULT NULL,
  `pass` int(11) DEFAULT NULL,
  `imagePath` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`leaNo`),
  KEY `stuNo` (`stuNo`) USING BTREE,
  CONSTRAINT `tb_lea_ibfk_1` FOREIGN KEY (`stuNo`) REFERENCES `tb_stu` (`stuNo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_lea
-- ----------------------------
INSERT INTO `tb_lea` VALUES ('1', '201606084207', '回家', '2019-03-30 16:06:59', '2019-04-01 08:00:05', '1', null);
INSERT INTO `tb_lea` VALUES ('2', '201606084203', '回家', '2019-03-30 16:06:59', '2019-04-01 08:00:05', '1', '');
INSERT INTO `tb_lea` VALUES ('19', '201606084206', '回家', '2019-04-13 00:00:00', '2019-04-13 23:00:00', '2', '20160608420620190413000000');
INSERT INTO `tb_lea` VALUES ('20', '201606084206', '是真的', '2019-04-12 00:00:00', '2019-04-13 23:00:00', '1', '2016060842062019412000000');
INSERT INTO `tb_lea` VALUES ('21', '201606084259', '反反复复', '2019-04-14 00:00:00', '2019-04-15 23:00:00', '1', '2016060842062019414000000');
INSERT INTO `tb_lea` VALUES ('22', '201606084259', '回家', '2019-04-13 00:00:00', '2019-04-22 23:00:00', '1', '20160608420620190413000000');
INSERT INTO `tb_lea` VALUES ('27', '201606084259', '五一回家', '2019-05-01 00:00:00', '2019-05-04 23:00:00', '1', '201606084259201951000000');
INSERT INTO `tb_lea` VALUES ('28', '201606084259', '回家玩耍', '2019-03-01 08:00:00', '2019-04-05 12:00:00', '0', '20160608425920190301080000');

-- ----------------------------
-- Table structure for tb_pro
-- ----------------------------
DROP TABLE IF EXISTS `tb_pro`;
CREATE TABLE `tb_pro` (
  `proNo` char(4) NOT NULL,
  `proName` varchar(20) DEFAULT NULL,
  `dpeNo` char(2) DEFAULT NULL,
  PRIMARY KEY (`proNo`),
  KEY `dpeNo` (`dpeNo`) USING BTREE,
  CONSTRAINT `tb_pro_ibfk_1` FOREIGN KEY (`dpeNo`) REFERENCES `tb_dep` (`depNo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_pro
-- ----------------------------
INSERT INTO `tb_pro` VALUES ('0608', '软件工程', '06');

-- ----------------------------
-- Table structure for tb_signin
-- ----------------------------
DROP TABLE IF EXISTS `tb_signin`;
CREATE TABLE `tb_signin` (
  `sigNo` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `stuNo` char(12) DEFAULT NULL,
  `sigState` int(11) DEFAULT NULL,
  `claName` varchar(20) DEFAULT NULL,
  `time` date DEFAULT NULL,
  `section` int(11) DEFAULT NULL,
  `teaName` varchar(20) DEFAULT NULL,
  `classRoom` varchar(20) DEFAULT NULL,
  `teaTel` varchar(11) DEFAULT NULL,
  `teaNo` char(10) DEFAULT NULL,
  PRIMARY KEY (`sigNo`),
  KEY `stuNo` (`stuNo`) USING BTREE,
  KEY `claNo` (`claName`) USING BTREE,
  KEY `teaNo` (`teaName`) USING BTREE,
  KEY `tb_signin_ibfk_2` (`teaNo`),
  CONSTRAINT `tb_signin_ibfk_1` FOREIGN KEY (`stuNo`) REFERENCES `tb_stu` (`stuNo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tb_signin_ibfk_2` FOREIGN KEY (`teaNo`) REFERENCES `tb_tea` (`teaNo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=389 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_signin
-- ----------------------------
INSERT INTO `tb_signin` VALUES ('349', '201606084206', '2', '计算机图形学', '2019-04-26', '3', '朱炳丽', '慎思5-427', '17725171416', '0001');
INSERT INTO `tb_signin` VALUES ('350', '201606084206', '1', '大学生就业', '2019-04-26', '1', '任苗苗', '慎思5-427', '123321', '0006');
INSERT INTO `tb_signin` VALUES ('351', '201606084207', '2', '计算机图形学', '2019-04-26', '3', '朱炳丽', '慎思5-427', '17725171416', '0001');
INSERT INTO `tb_signin` VALUES ('352', '201606084207', '1', '大学生就业', '2019-04-26', '1', '任苗苗', '慎思5-427', '123321', '0006');
INSERT INTO `tb_signin` VALUES ('353', '201606084211', '1', '计算机图形学', '2019-04-26', '3', '朱炳丽', '慎思5-427', '17725171416', '0001');
INSERT INTO `tb_signin` VALUES ('354', '201606084211', '1', '大学生就业', '2019-04-26', '1', '任苗苗', '慎思5-427', '123321', '0006');
INSERT INTO `tb_signin` VALUES ('355', '201606084259', '1', '计算机图形学', '2019-04-26', '3', '朱炳丽', '慎思5-427', '17725171416', '0001');
INSERT INTO `tb_signin` VALUES ('356', '201606084259', '1', '大学生就业', '2019-04-26', '1', '任苗苗', '慎思5-427', '123321', '0006');
INSERT INTO `tb_signin` VALUES ('360', '1', '1', 'hadoop技术', '2019-04-25', '1', '唐炜均', '	明辨3-403', '12345', '0005');
INSERT INTO `tb_signin` VALUES ('361', '1', '2', 'hadoop技术', '2019-04-25', '2', '唐炜均', '	明辨3-403', '12345', '0005');
INSERT INTO `tb_signin` VALUES ('362', '201606084203', '1', 'hadoop技术', '2019-04-25', '1', '唐炜均', '	明辨3-403', '12345', '0005');
INSERT INTO `tb_signin` VALUES ('363', '201606084203', '1', 'hadoop技术', '2019-04-25', '2', '唐炜均', '	明辨3-403', '12345', '0005');
INSERT INTO `tb_signin` VALUES ('364', '201606084206', '1', 'hadoop技术', '2019-04-25', '1', '唐炜均', '	明辨3-403', '12345', '0005');
INSERT INTO `tb_signin` VALUES ('365', '201606084206', '1', 'hadoop技术', '2019-04-25', '2', '唐炜均', '	明辨3-403', '12345', '0005');
INSERT INTO `tb_signin` VALUES ('366', '201606084207', '1', 'hadoop技术', '2019-04-25', '1', '唐炜均', '	明辨3-403', '12345', '0005');
INSERT INTO `tb_signin` VALUES ('367', '201606084207', '1', 'hadoop技术', '2019-04-25', '2', '唐炜均', '	明辨3-403', '12345', '0005');
INSERT INTO `tb_signin` VALUES ('368', '201606084211', '1', 'hadoop技术', '2019-04-25', '1', '唐炜均', '	明辨3-403', '12345', '0005');
INSERT INTO `tb_signin` VALUES ('369', '201606084211', '1', 'hadoop技术', '2019-04-25', '2', '唐炜均', '	明辨3-403', '12345', '0005');
INSERT INTO `tb_signin` VALUES ('370', '201606084259', '1', 'hadoop技术', '2019-04-25', '1', '唐炜均', '	明辨3-403', '12345', '0005');
INSERT INTO `tb_signin` VALUES ('371', '201606084259', '1', 'hadoop技术', '2019-04-25', '2', '唐炜均', '	明辨3-403', '12345', '0005');
INSERT INTO `tb_signin` VALUES ('375', '1', '1', '软件体系结构', '2019-04-24', '2', '涂承胜', '明辨1-209', '789', '0004');
INSERT INTO `tb_signin` VALUES ('376', '201606084203', '1', '软件体系结构', '2019-04-24', '2', '涂承胜', '明辨1-209', '789', '0004');
INSERT INTO `tb_signin` VALUES ('377', '201606084206', '1', '软件体系结构', '2019-04-24', '2', '涂承胜', '明辨1-209', '789', '0004');
INSERT INTO `tb_signin` VALUES ('378', '201606084207', '1', '软件体系结构', '2019-04-24', '2', '涂承胜', '明辨1-209', '789', '0004');
INSERT INTO `tb_signin` VALUES ('379', '201606084211', '1', '软件体系结构', '2019-04-24', '2', '涂承胜', '明辨1-209', '789', '0004');
INSERT INTO `tb_signin` VALUES ('380', '201606084259', '1', '软件体系结构', '2019-04-24', '2', '涂承胜', '明辨1-209', '789', '0004');
INSERT INTO `tb_signin` VALUES ('381', '1', '2', '计算机图形学', '2019-04-28', '1', '朱炳丽', '慎思5-427', '17725171416', '0001');
INSERT INTO `tb_signin` VALUES ('382', '201606084206', '1', '计算机图形学', '2019-04-28', '1', '朱炳丽', '慎思5-427', '123321', '0001');
INSERT INTO `tb_signin` VALUES ('383', '201606084207', '2', '计算机图形学', '2019-04-28', '3', '朱炳丽', '慎思5-427', '17725171416', '0001');
INSERT INTO `tb_signin` VALUES ('384', '201606084207', '1', '计算机图形学', '2019-04-28', '1', '朱炳丽', '慎思5-427', '123321', '0001');
INSERT INTO `tb_signin` VALUES ('385', '201606084211', '2', '计算机图形学', '2019-04-28', '3', '朱炳丽', '慎思5-427', '17725171416', '0001');
INSERT INTO `tb_signin` VALUES ('386', '201606084211', '1', '计算机图形学', '2019-04-28', '1', '朱炳丽', '慎思5-427', '123321', '0001');
INSERT INTO `tb_signin` VALUES ('387', '201606084259', '2', '计算机图形学', '2019-04-28', '3', '朱炳丽', '慎思5-427', '17725171416', '0001');
INSERT INTO `tb_signin` VALUES ('388', '201606084259', '1', '计算机图形学', '2019-04-28', '1', '朱炳丽', '慎思5-427', '123321', '0001');

-- ----------------------------
-- Table structure for tb_stu
-- ----------------------------
DROP TABLE IF EXISTS `tb_stu`;
CREATE TABLE `tb_stu` (
  `stuNo` char(12) NOT NULL,
  `stuName` varchar(20) DEFAULT NULL,
  `stuSex` char(2) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `graNo` char(2) DEFAULT NULL,
  `dorNo` char(4) DEFAULT NULL,
  `stuTel` varchar(11) DEFAULT NULL,
  `stuAdr` varchar(55) DEFAULT NULL,
  `stuPw` varchar(20) DEFAULT NULL,
  `stuFeature` text,
  `stuCharHead` varchar(255) DEFAULT NULL,
  `stuState` int(11) DEFAULT NULL,
  PRIMARY KEY (`stuNo`),
  KEY `graNo` (`graNo`) USING BTREE,
  KEY `dorNo` (`dorNo`) USING BTREE,
  CONSTRAINT `tb_stu_ibfk_1` FOREIGN KEY (`graNo`) REFERENCES `tb_gra` (`garNo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tb_stu_ibfk_2` FOREIGN KEY (`dorNo`) REFERENCES `tb_dor` (`droNo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='此graNo只代表一个编号，不代表年级';

-- ----------------------------
-- Records of tb_stu
-- ----------------------------
INSERT INTO `tb_stu` VALUES ('1', 'test', '男', '22', '16', '1439', '123456', '重庆市', '1', null, null, '1');
INSERT INTO `tb_stu` VALUES ('201606084203', '张渊博', '男', '22', '16', '1433', '123', '重庆市万州区', '201606084203', null, null, '1');
INSERT INTO `tb_stu` VALUES ('201606084206', '薛勇', '男', '21', '16', '1439', '123456789', '重庆万州', '1', 'AAD6RAAAdEMy9ZG8BI9bPYw4rL0FGxU9wtk5vkYxxD37SLQ9wBK4vH1oLr1Pyns8DiOXvfoEvrt6\noay9DUWUPYYCtj1iS6Y8ZVMDvSfQ9jzEKzQ9sKMbPUnxqz1vwVa8WdGHOzJXRD3txGw9IyjjPZMW\nlTxe0Ya8LwLAPdm0z7t/p2q68CwdvcZXwT3dzQE9FTR2PUuASL3mVY28zTujPaEknDwUt5y9Jjg6\nvTLIB77177Q8BoQGvQFYDD2Wg7g8xLtOvNVcOrsDwOA9Ic53vVlZeT2vsX482HcxPZV65by/1Jq9\nA6ZpPdPlZb1870O6rRscPRJNJTwHJrA8zXgtPR1uDr5X6Gk9Qt4NvcTfAz60ZCY91mBuPfnvsz2h\nxFm92seTPWknjT19KTK5GrRwvfmLbTxCajg9JT+WvVds0j0zpFG8uifiu1HtZL12JN29WVoEvPnc\nBT7QQnS9s4IMPZWlYD3IgIg91TgAvb7jXb1GE907i3UOvqhOUD0pOIE9886JPZ2L0bwO8bo82w6k\nPKxeszxwQFO9cQKJvYCMET5qYWM8SmFyvQFZQ71RGKk92DXyPLQ9fD2ye5K80I+qPf66h73na2o9\naqSevWBLo7zVDxc9F/J5PQqCf7zdFT++/neBPMcu3b1g0r89N78BPo21PDwF+6m95cvjPE/8ST3x\nY2O7pGzuvEBCxzqh1BA9mFCAPSD7VrzF7YO9yhmkuyoGMb6UG++6iICZPfF3iD2yLb49FcCmu5Op\ndz0ZFOy9kUtRPU6x+LzEvJQ8j0CWPISeVr2+ZEk9oEY2vDi+v72SFZI88IODvYZDp7vFCuo9Y+D3\nO84bnDwjzBS8UmgOvfqMxzxk9U490lHJPE3MaLp9IY09u1xTu5Vh3b2eG968aYYWvRswKD0gaBa9\nu1jvPYUcGzyRjt49owmyPQICBj1X2dW9skW0Pd5AlL3DdXc9V81zvT4AvT2euQm8Z98pPWvPLD0u\nkf08Zk2HvPZF1j3KnoI9kwlkvQaJsz0ed3q9CDAfvZaoZr3j3m49zFd7PEz6vb3hup+90CePPUz0\noD0meLE9Xvskvd0fALz1k3Y8jnlDPcALmb0fBSA7F0zmvMhJJL2IsEE94/0nvP6zKD3OHEa7cIM3\nPM2sqzyOPaK9JjREvThDa72skUq9mQbrPLysgb3cLMu6yRNdvYQ0KTw3x6w8fPIrPghoYzx0n4Y6\ndJW6uxboAzxyURc9LPsEvGrQmT2qArq8Z2UMvXaGsT1TvzU+Dk0MPMTyB72XlS68CFuiPAZWXT0U\nVmI9hDCaPRTI6LwXeYi9nZD5O7yon72ixIo9AmuQPc/sMTuxD0s9eagUPRP4J7wpY5s8IfghPTmA\nmD3w2sa9\n', null, '1');
INSERT INTO `tb_stu` VALUES ('201606084207', '殷豪', '男', '21', '16', '1433', '123456', '重庆市', '1', null, null, '1');
INSERT INTO `tb_stu` VALUES ('201606084211', '徐聪', '男', '21', '16', '1433', '789', '湖北省荆州市', '201606084211', null, null, '1');
INSERT INTO `tb_stu` VALUES ('201606084259', '孙腾飞', '男', '23', '16', '1433', '17725171416', '重庆市石柱', '1', 'AAD6RAAAdEMfGeO8toiCvd3rRb3pzKA8ICqNvZONYrtKNZu9D9qqOlLhgbznezO9Mh3WPVUiiLwS\nNh29kRytPPNMNr5+PBu8+joHvjxU/jww+489XIuavSJXMD22IqE88YSvuSiJfb1FIP086PhVPeoc\nmb0cNte964rpOzWQa72hlda8TDxzONKNUzyekig9iSynvZK3ab2fse4805/uPT69Y71hrIO993Hs\nO4a+jzyFOqO9ikVmPBurYb02H7G9/ZG2PYaTpD179YG9rdoWPWLCAz2XdVU9Y2kVPSz/3TuNXIE9\n3kX4O+Rbd714ANw8eiUjPd27ozx+xuQ9wZFpu2CICj1DO9y8M15pvZs9lr36pX89bopVPYvv3r3o\ne6c8tH2pvSnGBjxf0Xu904TavWDVgjujzEE9tbsuPZ/paj2QawQ9gpGMveNv1jzf37295FyVu/xH\noT1y/9o9TAtAvZTRQjxfvh+8nQy0PRTqgL3rXI89HlpxvfwxMz1opvi9evmePQ9EFz0nT069HmPY\nPDdjZrxOyFc6gtFfvZaiArts+oi9WX72PUmtojyn4QO+RwAaPY8RPj2+dIk7TTILPJEI3j1QtSq9\nbhwiPI9a2L3y8mM8E1h5PQ3YqDwxStk93baYPbZas7ts//I9a0HBvI9Ek71OFbk9nL8YvZ5lpjxH\nJuA84SC6PXJMTzwrvk+9ssAYPrdCJr0A8sQ8IAwXPVgIyz0Z+nK8y8aqvD+Gob0D8bQ9rNeavLM/\nB73NhRK9bhQUPUJu2DrP0ky8iJ6UPZOxIz3IMJ+60RuUvWwMQr0cQhW97Xi8vXWAqr0MbCG9VlUI\nO2ye87vhUFW7kjD2PXbGXT2E+ze9vBZoPLcSCr1jwZy9z2OBPYBm+buI8im+/WjFvUEyDb3JbMS8\nB6gKvizGDj3lCAk9rsq3vZzfZzy2EJY9y4NaPTjuhjxUrTQ9ePxpvYCO/bwLkf48ygIwPakrUT0x\nzW88MJMWvhKGIb3v0Ew9ObPVO8xPmD0ntw49HCgqPuoQa7xt9fe884FTPaUwxryKDdW9ftwGur8L\nsryi6Ju9hfAmO63Duz0Z49q8DAOivJWf3LxYxoK9RTxZvW+Oaz22Enm9d/h6vaROkTxNAo29H695\nuy+ZXb3nS8A9Zyi5vDbi4jzZ2tc9swYBvSBOOz1reY49JiD5vQnvT70jlYO9WT7POl526LtTqkO9\npUVKPMh2VL2t+Ra9EEIEPb5+5b0aJMc81lmevWqjLj6UO347CWhFvWi0RbzKrho9K1uuuw9QeT0v\nUo69zM0QvTzELrxjp529bIIgPUt0mz144jG81C4qPWP2x73eQIU8LsXUPX2FIzwgnQc+gwKvPSYY\nQb3hOBc9\n', null, '0');

-- ----------------------------
-- Table structure for tb_tea
-- ----------------------------
DROP TABLE IF EXISTS `tb_tea`;
CREATE TABLE `tb_tea` (
  `teaNo` char(10) NOT NULL,
  `teaName` varchar(20) DEFAULT NULL,
  `teaSex` char(2) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  `permission` int(11) DEFAULT NULL,
  `depNo` char(2) DEFAULT NULL,
  `teaTel` varchar(11) DEFAULT NULL,
  `teaPw` varchar(25) DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `signstate` int(11) DEFAULT NULL,
  PRIMARY KEY (`teaNo`),
  KEY `depNo` (`depNo`) USING BTREE,
  CONSTRAINT `tb_tea_ibfk_1` FOREIGN KEY (`depNo`) REFERENCES `tb_dep` (`depNo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_tea
-- ----------------------------
INSERT INTO `tb_tea` VALUES ('0001', '朱炳丽', '女', '40', '1', '06', '1', '1', '108.447443', '30.749645', '1');
INSERT INTO `tb_tea` VALUES ('0002', '刘井波', '男', '28', '1', '06', '1', '0002', '108.446954', '30.749419', '0');
INSERT INTO `tb_tea` VALUES ('0003', '刘锋', '男', '35', '0', '06', '1', '1', '108.446954', '30.749421', '0');
INSERT INTO `tb_tea` VALUES ('0004', '涂承胜', '男', '48', '0', '06', '789', '0004', '108.446954', '30.749419', '0');
INSERT INTO `tb_tea` VALUES ('0005', '唐炜均', '男', '35', '1', '06', '12345', '0005', '108.446954', '30.749421', '0');
INSERT INTO `tb_tea` VALUES ('0006', '任苗苗', '女', '25', '1', '06', '123321', '0006', '108.446954', '30.749419', '0');

-- ----------------------------
-- Table structure for tb_week
-- ----------------------------
DROP TABLE IF EXISTS `tb_week`;
CREATE TABLE `tb_week` (
  `weekNo` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `week` int(11) DEFAULT NULL,
  `classroom` varchar(20) DEFAULT NULL,
  `time` time DEFAULT NULL,
  `claNo` char(4) DEFAULT NULL,
  `section` int(11) DEFAULT NULL,
  PRIMARY KEY (`weekNo`),
  KEY `claNo` (`claNo`) USING BTREE,
  CONSTRAINT `tb_week_ibfk_1` FOREIGN KEY (`claNo`) REFERENCES `tb_cla` (`claNo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_week
-- ----------------------------
INSERT INTO `tb_week` VALUES ('1', '1', '明辨2-207', '10:20:00', '0608', '2');
INSERT INTO `tb_week` VALUES ('2', '1', '慎思5-522', '14:20:00', '0609', '3');
INSERT INTO `tb_week` VALUES ('3', '2', '	明辨2-310', '08:20:00', '0610', '1');
INSERT INTO `tb_week` VALUES ('4', '3', '明辨1-209', '10:20:00', '0611', '2');
INSERT INTO `tb_week` VALUES ('5', '4', '	明辨3-403', '08:20:00', '0612', '1');
INSERT INTO `tb_week` VALUES ('6', '4', '	明辨3-403', '10:20:00', '0612', '2');
INSERT INTO `tb_week` VALUES ('7', '5', '慎思5-427', '08:20:00', '0613', '1');
INSERT INTO `tb_week` VALUES ('8', '5', '慎思5-427', '14:20:00', '0608', '3');

-- ----------------------------
-- Procedure structure for back
-- ----------------------------
DROP PROCEDURE IF EXISTS `back`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `back`()
BEGIN
INSERT INTO tb_back (backState, stuNo, time) SELECT
	1,
	stuNo,
	NOW()
FROM
	tb_stu;

END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for leave
-- ----------------------------
DROP PROCEDURE IF EXISTS `leave`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `leave`()
BEGIN
UPDATE tb_stu
SET stuState = 0
WHERE
	stuNo IN (
		SELECT
			stuNo
		FROM
			tb_lea
		WHERE
			TO_DAYS(now()) - TO_DAYS(leaTime) >= 0
		AND TO_DAYS(now()) - TO_DAYS(backTime) <= 0
		AND pass = 1
	);
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for leaveBack
-- ----------------------------
DROP PROCEDURE IF EXISTS `leaveBack`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `leaveBack`()
BEGIN
UPDATE tb_stu
SET stuState = 1
WHERE
	stuNo IN (
		SELECT
			stuNo
		FROM
			tb_lea
		WHERE
			TO_DAYS(now()) - TO_DAYS(backTime) >= 0
	);
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for Noback
-- ----------------------------
DROP PROCEDURE IF EXISTS `Noback`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `Noback`()
BEGIN
	UPDATE tb_back
SET backState = 3
WHERE
	backState = 1;
END
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for signIn
-- ----------------------------
DROP PROCEDURE IF EXISTS `signIn`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `signIn`()
BEGIN
INSERT INTO tb_signin (
	sigState,
	stuNo,
	claName,
	section,
	teaName,
  classRoom,
  teaTel,
	time
) SELECT
	1,
	tb_stu.stuNo,
	tb_cla.claName,
	tb_week.section,
	tb_tea.teaName,
  tb_week.classroom,
  tb_tea.teaTel,
	NOW()
FROM
	tb_stu,
	tb_cho,
	tb_cla,
	tb_week,
	tb_tea,
	tb_as_class
WHERE
	tb_cho.stuNo = tb_stu.stuNo
AND tb_cho.claNo = tb_cla.claNo
AND tb_week.claNo = tb_cla.claNo
AND tb_week.claNo = tb_cho.claNo
AND tb_week.claNo = tb_as_class.claNo
AND tb_cho.claNo = tb_as_class.claNo
AND tb_cho.claNo = tb_week.claNo
AND WEEKDAY(NOW()) +1= tb_week.`week`
AND tb_tea.teaNo = tb_as_class.teaNo
AND tb_as_class.claNo = tb_cla.claNo
AND tb_as_class.section=tb_week.section
AND tb_tea.teaNo=tb_cho.teaNo
AND tb_as_class.graNo=tb_stu.graNo
AND tb_stu.stuState = 1;
END
;;
DELIMITER ;

-- ----------------------------
-- Event structure for Add_back
-- ----------------------------
DROP EVENT IF EXISTS `Add_back`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `Add_back` ON SCHEDULE EVERY 1 DAY STARTS '2019-04-10 00:00:00' ON COMPLETION PRESERVE ENABLE DO CALL back
;;
DELIMITER ;

-- ----------------------------
-- Event structure for Add_leave
-- ----------------------------
DROP EVENT IF EXISTS `Add_leave`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `Add_leave` ON SCHEDULE EVERY 1 DAY STARTS '2019-04-01 08:00:00' ON COMPLETION PRESERVE ENABLE DO CALL `leave`
;;
DELIMITER ;

-- ----------------------------
-- Event structure for Add_leaveBack
-- ----------------------------
DROP EVENT IF EXISTS `Add_leaveBack`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `Add_leaveBack` ON SCHEDULE EVERY 1 DAY STARTS '2019-04-01 08:00:00' ON COMPLETION PRESERVE ENABLE DO CALL leaveBack
;;
DELIMITER ;

-- ----------------------------
-- Event structure for Add_noBack
-- ----------------------------
DROP EVENT IF EXISTS `Add_noBack`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `Add_noBack` ON SCHEDULE EVERY 1 DAY STARTS '2019-04-01 23:50:00' ON COMPLETION PRESERVE ENABLE DO CALL Noback
;;
DELIMITER ;

-- ----------------------------
-- Event structure for Add_signIn
-- ----------------------------
DROP EVENT IF EXISTS `Add_signIn`;
DELIMITER ;;
CREATE DEFINER=`root`@`%` EVENT `Add_signIn` ON SCHEDULE EVERY 1 DAY STARTS '2019-03-31 00:00:00' ON COMPLETION PRESERVE ENABLE DO CALL signIn
;;
DELIMITER ;
