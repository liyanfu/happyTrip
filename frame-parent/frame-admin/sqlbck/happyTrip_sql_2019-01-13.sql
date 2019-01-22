/*
SQLyog Ultimate v8.71 
MySQL - 5.7.24 : Database - happytrip
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`happytrip` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `happytrip`;

/*Table structure for table `QRTZ_BLOB_TRIGGERS` */

DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;

CREATE TABLE `QRTZ_BLOB_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_BLOB_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `QRTZ_BLOB_TRIGGERS` */

/*Table structure for table `QRTZ_CALENDARS` */

DROP TABLE IF EXISTS `QRTZ_CALENDARS`;

CREATE TABLE `QRTZ_CALENDARS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `QRTZ_CALENDARS` */

/*Table structure for table `QRTZ_CRON_TRIGGERS` */

DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;

CREATE TABLE `QRTZ_CRON_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_CRON_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `QRTZ_CRON_TRIGGERS` */

/*Table structure for table `QRTZ_FIRED_TRIGGERS` */

DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;

CREATE TABLE `QRTZ_FIRED_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `QRTZ_FIRED_TRIGGERS` */

/*Table structure for table `QRTZ_JOB_DETAILS` */

DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;

CREATE TABLE `QRTZ_JOB_DETAILS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `QRTZ_JOB_DETAILS` */

/*Table structure for table `QRTZ_LOCKS` */

DROP TABLE IF EXISTS `QRTZ_LOCKS`;

CREATE TABLE `QRTZ_LOCKS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `QRTZ_LOCKS` */

/*Table structure for table `QRTZ_PAUSED_TRIGGER_GRPS` */

DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;

CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `QRTZ_PAUSED_TRIGGER_GRPS` */

/*Table structure for table `QRTZ_SCHEDULER_STATE` */

DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;

CREATE TABLE `QRTZ_SCHEDULER_STATE` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `QRTZ_SCHEDULER_STATE` */

/*Table structure for table `QRTZ_SIMPLE_TRIGGERS` */

DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;

CREATE TABLE `QRTZ_SIMPLE_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `QRTZ_SIMPLE_TRIGGERS` */

/*Table structure for table `QRTZ_SIMPROP_TRIGGERS` */

DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;

CREATE TABLE `QRTZ_SIMPROP_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_SIMPROP_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `QRTZ_SIMPROP_TRIGGERS` */

/*Table structure for table `QRTZ_TRIGGERS` */

DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;

CREATE TABLE `QRTZ_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `QRTZ_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `QRTZ_TRIGGERS` */

/*Table structure for table `b_order` */

DROP TABLE IF EXISTS `b_order`;

CREATE TABLE `b_order` (
  `orderId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '购买订单主键Id',
  `userId` bigint(20) DEFAULT NULL COMMENT '购买用户Id',
  `userMobile` varchar(50) DEFAULT NULL COMMENT '购买用户账号',
  `productTypeId` bigint(20) DEFAULT NULL COMMENT '商品类别Id',
  `productTypeName` varchar(100) DEFAULT NULL COMMENT '商品类别名称',
  `productId` bigint(20) DEFAULT NULL COMMENT '购买商品Id',
  `productName` varchar(200) DEFAULT NULL COMMENT '购买商品名称',
  `productImgurl` varchar(200) DEFAULT NULL COMMENT '购买商品图片路径',
  `buyQuantity` int(11) DEFAULT NULL COMMENT '投资份数',
  `buyMoney` decimal(15,4) DEFAULT '0.0000' COMMENT '投资金额',
  `rebateMoney` decimal(15,4) DEFAULT '0.0000' COMMENT '每天返利金额',
  `totalRebatePeriods` int(11) DEFAULT NULL COMMENT '总返利天数',
  `rebatePeriods` int(11) DEFAULT NULL COMMENT '已返利天数',
  `profitMoney` decimal(15,4) DEFAULT '0.0000' COMMENT '收益总金额',
  `startRebateTime` int(11) DEFAULT NULL COMMENT '购买之后几天开始返利,-1不限制.',
  `status` int(1) DEFAULT NULL COMMENT '0:待支付,1:收益中,2:已完成,3:已取消',
  `paymentId` bigint(20) DEFAULT NULL COMMENT '支付方式ID',
  `randomCode` varchar(10) DEFAULT NULL COMMENT '线下支付订单用户转账时填的备注随机码,用于订单匹配',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `createUser` varchar(50) DEFAULT NULL COMMENT '创建人',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `updateUser` varchar(50) DEFAULT NULL COMMENT '修改人',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`orderId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='投资订单表';

/*Data for the table `b_order` */

/*Table structure for table `b_product` */

DROP TABLE IF EXISTS `b_product`;

CREATE TABLE `b_product` (
  `productId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '产品主键',
  `productName` varchar(100) DEFAULT NULL COMMENT '产品名称',
  `productTypeId` bigint(20) DEFAULT NULL COMMENT '产品所属类别',
  `saleMoney` decimal(15,4) DEFAULT '0.0000' COMMENT '售卖金额',
  `saleQuantity` int(11) DEFAULT '0' COMMENT '销售数量',
  `saleVolumes` int(11) DEFAULT '0' COMMENT '已卖数量',
  `rebateMoney` decimal(15,4) DEFAULT '0.0000' COMMENT '每期返利金额',
  `rebatePeriods` int(11) DEFAULT '0' COMMENT '返利期数',
  `rebateTotals` decimal(15,4) DEFAULT '0.0000' COMMENT '返利总额',
  `purchaseRestriction` int(11) DEFAULT '-1' COMMENT '每人购买限制,[-1不限制]',
  `startRebateTime` int(11) DEFAULT '-1' COMMENT '购买之后几天开始返利,[-1不限制]',
  `status` int(1) DEFAULT '1' COMMENT '状态，[0:已下架][1:已上架]',
  `productImgurl` varchar(200) DEFAULT NULL COMMENT '产品图片存放地址',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注描述',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `createUser` varchar(50) DEFAULT NULL COMMENT '创建人',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `updateUser` varchar(50) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`productId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='商品表';

/*Data for the table `b_product` */

insert  into `b_product`(`productId`,`productName`,`productTypeId`,`saleMoney`,`saleQuantity`,`saleVolumes`,`rebateMoney`,`rebatePeriods`,`rebateTotals`,`purchaseRestriction`,`startRebateTime`,`status`,`productImgurl`,`sort`,`remark`,`createTime`,`createUser`,`updateTime`,`updateUser`) values (1,'奔驰01',1,'1000.0000',10,0,'200.0000',10,'2000.0000',-1,-1,1,'http://xxx.xxx.xx/xxx.jpg',1,NULL,NULL,NULL,NULL,NULL),(2,'宝马x6',2,'2000.0000',10,0,'500.0000',5,'2500.0000',-1,-1,1,'http://xxx.xxx.xx/xxx.jpg',2,NULL,NULL,NULL,NULL,NULL),(3,'奥迪a8',1,'5000.0000',10,0,'500.0000',20,'10000.0000',2,-1,1,'http://xxx.xxx.xx/xxx.jpg',3,NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `b_product_type` */

DROP TABLE IF EXISTS `b_product_type`;

CREATE TABLE `b_product_type` (
  `productTypeId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品类别主键ID',
  `productTypeName` varchar(100) DEFAULT NULL COMMENT '类别名称',
  `status` int(1) DEFAULT NULL COMMENT '0:禁用,1:启用',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `createUser` varchar(50) DEFAULT NULL COMMENT '创建人',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `updateUser` varchar(50) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`productTypeId`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='商品类别表';

/*Data for the table `b_product_type` */

insert  into `b_product_type`(`productTypeId`,`productTypeName`,`status`,`createTime`,`createUser`,`updateTime`,`updateUser`) values (1,'共享汽车',1,'2019-01-09 11:11:11','fury',NULL,NULL),(2,'全民福利',1,'2019-01-09 11:11:11','fury',NULL,NULL);

/*Table structure for table `b_welfare` */

DROP TABLE IF EXISTS `b_welfare`;

CREATE TABLE `b_welfare` (
  `welfareId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '福利类型主键ID',
  `welfareKey` varchar(100) DEFAULT NULL COMMENT '福利类型,key存在config表',
  `welfareName` varchar(100) DEFAULT NULL COMMENT '福利名称',
  `welfareValue` varchar(50) DEFAULT NULL COMMENT '达标要求,逗号隔开。',
  `bonusPool` decimal(15,4) DEFAULT NULL COMMENT '奖金池,为空时取订单总金额',
  `status` int(1) DEFAULT NULL COMMENT '状态[0:启用,1停用]',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `createUser` varchar(50) DEFAULT NULL COMMENT '创建者',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `updateUser` varchar(50) DEFAULT NULL COMMENT '修改者',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`welfareId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='福利类型表';

/*Data for the table `b_welfare` */

insert  into `b_welfare`(`welfareId`,`welfareKey`,`welfareName`,`welfareValue`,`bonusPool`,`status`,`createTime`,`createUser`,`updateTime`,`updateUser`,`remark`) values (2,'GLOBAL_BONUS_KEY','全球分红','3',NULL,1,NULL,NULL,NULL,NULL,'每日直推3人'),(3,'GLOBAL_BONUS_KEY','全球分红','6',NULL,1,NULL,NULL,NULL,NULL,'每日直推6人'),(4,'TEAM_LEADERSHIP_AWARD_KEY','团队领导奖','5,3000',NULL,1,NULL,NULL,NULL,NULL,'当日直推5人,当日业绩达3000'),(5,'SPECIAL_CONTRIBUTION_AWARD_KEY','特别贡献奖','35,70,18888',NULL,1,NULL,NULL,NULL,NULL,'累计直推有效会员35人,团队人数70,累计团队业绩18888');

/*Table structure for table `s_config` */

DROP TABLE IF EXISTS `s_config`;

CREATE TABLE `s_config` (
  `configId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '配置主键ID',
  `configKey` varchar(200) DEFAULT NULL COMMENT '配置键',
  `configVal` varchar(200) DEFAULT NULL COMMENT '配置值',
  `configName` varchar(200) DEFAULT NULL COMMENT '配置名称',
  `configType` varchar(200) DEFAULT NULL COMMENT '配置类型',
  `configStatus` int(1) DEFAULT '1' COMMENT '配置开关[0:关闭,1:开启]',
  `createTime` datetime DEFAULT NULL COMMENT '创建日期',
  `createUser` varchar(50) DEFAULT NULL COMMENT '创建者',
  `updateTime` datetime DEFAULT NULL COMMENT '修改日期',
  `updateUser` varchar(50) DEFAULT NULL COMMENT '修改者',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`configId`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='常量配置表';

/*Data for the table `s_config` */

insert  into `s_config`(`configId`,`configKey`,`configVal`,`configName`,`configType`,`configStatus`,`createTime`,`createUser`,`updateTime`,`updateUser`,`remark`) values (1,'WITHDRAW_TIME_RANGE_KEY','-1','提现时间段','withdraw',1,'2019-01-07 17:50:00','fury',NULL,NULL,'提现时间段,-1不限制[00:00,23:59]'),(2,'WITHDRAW_MIN_KEY','-1','最小提现额','withdraw',1,'2019-01-07 17:50:00','fury',NULL,NULL,'最小提现额,-1不限制'),(3,'WITHDRAW_MAX_KEY','-1','最大提现额','withdraw',1,'2019-01-07 17:50:00','fury',NULL,NULL,'最大提现额,-1不限制'),(4,'WITHDRAW_FEE_KEY','-1','提现手续费','withdraw',1,'2019-01-07 17:50:00','fury',NULL,NULL,'最大提现额,-1不限制,如填1为百分子0.01'),(6,'WITHDRAW_SWITCH_KEY','1','提现开关','withdraw',1,'2019-01-07 17:50:00','fury',NULL,NULL,'提现开关[0:关,1:开],关闭之后用户无法提现'),(7,'RECHARGE_TIME_RANGE_KEY','-1','充值时间段','recharge',1,'2019-01-07 17:50:00','fury',NULL,NULL,'充值时间段,-1不限制[00:00,23:59]'),(10,'RECHARGE_FEE_KEY','-1','充值手续费','recharge',1,'2019-01-07 17:50:00','fury',NULL,NULL,'最大充值额,-1无手续费,如填1为百分之1'),(12,'RECHARGE_SWITCH_KEY','1','充值开关','recharge',1,'2019-01-07 17:50:00','fury',NULL,NULL,'充值开关[0:关,1:开],关闭之后用户无法充值'),(13,'CLOUD_STORAGE_CONFIG_KEY','{\"type\":1,\"qiniuDomain\":\"http://7xlij2.com1.z0.glb.clouddn.com\",\"qiniuPrefix\":\"upload\",\"qiniuAccessKey\":\"NrgMfABZxWLo5B-YYSjoE8-AZ1EISdi1Z3ubLOeZ\",\"qiniuSecretKey\":\"uIwJHevMRWU0VLxFvgy0tAcOdGqasdtVlJk','云存储配置','system',1,'2019-01-07 17:50:00','fury',NULL,NULL,'云存储配置信息,保存json格式'),(15,'GLOBAL_BONUS_KEY','1','全球分红开关','welfare',1,'2019-01-07 17:50:00','fury',NULL,NULL,'全球分红开关[0:关,1:开],关闭之后定时任务将停止'),(16,'TEAM_LEADERSHIP_AWARD_KEY','1','团队领导奖开关','welfare',1,'2019-01-07 17:50:00','fury',NULL,NULL,'团队领导奖开关[0:关,1:开],关闭之后定时任务将停止'),(17,'SPECIAL_CONTRIBUTION_AWARD_KEY','1','特别贡献奖开关','welfare',1,'2019-01-07 17:50:00','fury',NULL,NULL,'特别贡献奖开关[0:关,1:开],关闭之后定时任务将停止'),(18,'WITHDRAW_COUNT_KEY','-1','提现次数','withdraw',1,'2019-01-07 17:50:00','fury',NULL,NULL,'提现次数,-1不限制'),(19,'RECHARGE_QRCODE_KEY','http://','充值收款二维码','recharge',1,'2019-01-07 17:50:00','fury',NULL,NULL,'充值二维码收款图片'),(20,'SYSTEM_CUSTOMER_SERVICE_IMG_KEY','http://','客服二维码图片','system',1,'2019-01-07 17:50:00','fury',NULL,NULL,'客服二维码图片'),(21,'SYSTEM_COMPANY_INTRODUCE_KEY','{}','公司介绍',NULL,1,'2019-01-07 17:50:00','fury',NULL,NULL,'公司介绍'),(22,'WELFARE_SWITCH_DAILY_KEY','1','天返福利开关','welfare',1,'2019-01-07 17:50:00',NULL,NULL,NULL,'天返福利开关[0:关,1:开]'),(23,'WELFARE_SWITCH_HOUR_KEY','1','时返福利开关','welfare',1,'2019-01-07 17:50:00',NULL,NULL,NULL,'时返福利开关[0:关,1:开]'),(24,'SYSTEM_SPREAD_DOMAIN_KEY','http://localhost:38888/api','推广域名','system',1,'2019-01-07 17:50:00',NULL,NULL,NULL,'推广域名');

/*Table structure for table `s_log` */

DROP TABLE IF EXISTS `s_log`;

CREATE TABLE `s_log` (
  `logId` bigint(20) NOT NULL AUTO_INCREMENT,
  `userName` varchar(100) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(100) DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `time` bigint(20) DEFAULT NULL COMMENT '执行时长(毫秒)',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `sources` int(1) DEFAULT NULL COMMENT '来源,0:app,1:后台',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`logId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统日志';

/*Data for the table `s_log` */

/*Table structure for table `s_payment` */

DROP TABLE IF EXISTS `s_payment`;

CREATE TABLE `s_payment` (
  `paymentId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '支付方式主键',
  `paymentKey` varchar(200) DEFAULT NULL COMMENT '支付方式Key',
  `paymentName` varchar(100) DEFAULT NULL COMMENT '支付方式名称',
  `status` int(1) DEFAULT NULL COMMENT '状态[0:禁用,1:启用]',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `createUser` varchar(50) DEFAULT NULL COMMENT '创建人',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `updateUser` varchar(50) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`paymentId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='支付方式接口配置表';

/*Data for the table `s_payment` */

insert  into `s_payment`(`paymentId`,`paymentKey`,`paymentName`,`status`,`sort`,`createTime`,`createUser`,`updateTime`,`updateUser`) values (1,'PAYMENT_ALIPAY_KEY','支付宝',1,1,NULL,NULL,NULL,NULL),(2,'PAYMENT_WALLET_KEY','余额',1,2,NULL,NULL,NULL,NULL);

/*Table structure for table `s_schedule_job` */

DROP TABLE IF EXISTS `s_schedule_job`;

CREATE TABLE `s_schedule_job` (
  `jobId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `beanName` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `methodName` varchar(100) DEFAULT NULL COMMENT '方法名',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `cronExpression` varchar(100) DEFAULT NULL COMMENT 'cron表达式',
  `status` tinyint(4) DEFAULT NULL COMMENT '任务状态  0：正常  1：暂停',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`jobId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='定时任务';

/*Data for the table `s_schedule_job` */

insert  into `s_schedule_job`(`jobId`,`beanName`,`methodName`,`params`,`cronExpression`,`status`,`remark`,`createTime`) values (1,'testTask','test','frame','0 0/30 * * * ?',0,'有参数测试','2016-12-01 23:16:46'),(2,'testTask','test2',NULL,'0 0/30 * * * ?',1,'无参数测试','2016-12-03 14:55:56');

/*Table structure for table `s_schedule_job_log` */

DROP TABLE IF EXISTS `s_schedule_job_log`;

CREATE TABLE `s_schedule_job_log` (
  `logId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志id',
  `jobId` bigint(20) NOT NULL COMMENT '任务id',
  `beanName` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `methodName` varchar(100) DEFAULT NULL COMMENT '方法名',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `status` tinyint(4) NOT NULL COMMENT '任务状态    0：成功    1：失败',
  `error` varchar(2000) DEFAULT NULL COMMENT '失败信息',
  `times` int(11) NOT NULL COMMENT '耗时(单位：毫秒)',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`logId`),
  KEY `job_id` (`jobId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务日志';

/*Data for the table `s_schedule_job_log` */

/*Table structure for table `s_sys_dept` */

DROP TABLE IF EXISTS `s_sys_dept`;

CREATE TABLE `s_sys_dept` (
  `deptId` bigint(20) NOT NULL AUTO_INCREMENT,
  `parentId` bigint(20) DEFAULT NULL COMMENT '上级部门ID，一级部门为0',
  `name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `orderNum` int(11) DEFAULT NULL COMMENT '排序',
  `deleteFlag` int(1) DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
  PRIMARY KEY (`deptId`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='部门管理';

/*Data for the table `s_sys_dept` */

insert  into `s_sys_dept`(`deptId`,`parentId`,`name`,`orderNum`,`deleteFlag`) values (1,0,'系统部门',0,0),(2,1,'深圳分公司',1,0),(6,0,'斗球部门',1,0),(7,6,'深圳分公司',0,0),(8,6,'吉隆坡分公司',1,0);

/*Table structure for table `s_sys_menu` */

DROP TABLE IF EXISTS `s_sys_menu`;

CREATE TABLE `s_sys_menu` (
  `menuId` bigint(20) NOT NULL AUTO_INCREMENT,
  `parentId` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(1000) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `spread` int(1) DEFAULT '0' COMMENT '是否展开  0：不展开  1：展开',
  `orderNum` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menuId`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8 COMMENT='菜单管理';

/*Data for the table `s_sys_menu` */

insert  into `s_sys_menu`(`menuId`,`parentId`,`name`,`url`,`perms`,`type`,`icon`,`spread`,`orderNum`) values (1,0,'系统管理',NULL,NULL,0,'larry-xitong',1,0),(2,46,'用户管理','modules/sys/user.html',NULL,1,'larry-yonghuliebiao',0,1),(3,46,'角色管理','modules/sys/role.html',NULL,1,'larry-jiaoseguanli',0,2),(4,49,'菜单管理','modules/sys/menu.html',NULL,1,'larry-lanmuguanli-copy',0,1),(5,47,'SQL监控','druid/sql.html',NULL,1,'larry-uicon_sql',0,1),(15,2,'查看',NULL,'sys:user:list,sys:user:info',2,NULL,0,0),(16,2,'新增',NULL,'sys:user:save,sys:role:select',2,NULL,0,0),(17,2,'修改',NULL,'sys:user:update,sys:role:select',2,NULL,0,0),(18,2,'删除',NULL,'sys:user:delete',2,NULL,0,0),(19,3,'查看',NULL,'sys:role:list,sys:role:info',2,NULL,0,0),(20,3,'新增',NULL,'sys:role:save,sys:menu:perms',2,NULL,0,0),(21,3,'修改',NULL,'sys:role:update,sys:menu:perms',2,NULL,0,0),(22,3,'删除',NULL,'sys:role:delete',2,NULL,0,0),(23,4,'查看',NULL,'sys:menu:list,sys:menu:info',2,NULL,0,0),(24,4,'新增',NULL,'sys:menu:save,sys:menu:select',2,NULL,0,0),(25,4,'修改',NULL,'sys:menu:update,sys:menu:select',2,NULL,0,0),(26,4,'删除',NULL,'sys:menu:delete',2,NULL,0,0),(27,42,'参数管理','modules/sys/config.html','sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete',1,'larry-xitong-pressed',0,2),(29,47,'系统日志','modules/sys/log.html','sys:log:list',1,'larry-10109',0,2),(30,42,'文件上传','modules/sys/oss.html','sys:oss:all',1,'larry-friendLink',0,4),(31,46,'部门管理','modules/sys/dept.html',NULL,1,'larry-Shape',0,3),(32,31,'查看',NULL,'sys:dept:list,sys:dept:info',2,NULL,0,0),(33,31,'新增',NULL,'sys:dept:save,sys:dept:select',2,NULL,0,0),(34,31,'修改',NULL,'sys:dept:update,sys:dept:select',2,NULL,0,0),(35,31,'删除',NULL,'sys:dept:delete',2,NULL,0,0),(41,48,'文章管理','modules/demo/news.html','demo:news:list,demo:news:info,demo:news:save,demo:news:update,demo:news:delete',1,'larry-neirongfabu',0,1),(42,1,'系统设置',NULL,NULL,0,'larry-wsmp-setting',0,2),(46,1,'系统用户管理',NULL,NULL,0,'larry-paikexitong_yonghuguanli',0,0),(47,1,'系统监控',NULL,NULL,0,'larry-shouye-anquanguanli',0,3),(48,0,'功能示例',NULL,NULL,0,'larry-diannao3',0,2),(49,1,'系统菜单',NULL,NULL,0,'larry-caidanguanli3',0,1),(50,49,'图标管理','modules/demo/font.html',NULL,1,'larry-qizhi',0,2),(51,2,'导出',NULL,'sys:user:export',2,NULL,0,0),(53,0,'斗球管理',NULL,NULL,0,'larry-xiaolian',0,1),(54,53,'赛事管理','modules/vsball/match.html','vsball:match:list,vsball:match:info,vsball:match:update,vsball:match:save,vsball:match:delete,vsball:match:select',1,'larry-lanmuguanli-copy',0,0),(58,53,'日志管理','modules/vsball/log.html','sys:log:list',1,'larry-10109',0,5),(59,53,'用户管理','modules/vsball/user.html','vsball:USER:LIST,vsball:USER:info,vsball:USER:UPDATE,vsball:USER:save,vsball:USER:DELETE,vsball:USER:SELECT,vsball:accountChange:LIST,vsball:account:info,vsball:account:recharge,vsball:account:subtract',1,'larry-yonghuliebiao',0,1),(60,53,'帐变管理','modules/vsball/accountChange.html','vsball:accountChange:list',1,'larry-log',0,2),(61,53,'参数管理','modules/sys/config.html','sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete',1,'larry-xitong-pressed',0,6),(62,53,'文件上传','modules/sys/oss.html','sys:oss:all',1,'larry-friendLink',0,7);

/*Table structure for table `s_sys_oss` */

DROP TABLE IF EXISTS `s_sys_oss`;

CREATE TABLE `s_sys_oss` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(200) DEFAULT NULL COMMENT 'URL地址',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='文件上传';

/*Data for the table `s_sys_oss` */

insert  into `s_sys_oss`(`id`,`url`,`createTime`) values (1,'http://7xlij2.com1.z0.glb.clouddn.com/upload/20180516/df5d3a88f24a4738a41cfea44cd3ab4a.jpg','2018-05-16 17:14:11'),(2,'http://7xlij2.com1.z0.glb.clouddn.com/upload/20180516/23f88f611f2c4b8ba21bcfb2191567fe.jpg','2018-05-16 17:26:27'),(3,'http://7xlij2.com1.z0.glb.clouddn.com/upload/20180516/80633330e223409984fc1d04c6c68d00.jpg','2018-05-16 17:34:47');

/*Table structure for table `s_sys_role` */

DROP TABLE IF EXISTS `s_sys_role`;

CREATE TABLE `s_sys_role` (
  `roleId` bigint(20) NOT NULL AUTO_INCREMENT,
  `roleName` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `deptId` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`roleId`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='角色';

/*Data for the table `s_sys_role` */

insert  into `s_sys_role`(`roleId`,`roleName`,`remark`,`deptId`,`createTime`) values (20,'管理员','管理员',1,'2018-05-17 13:56:21'),(21,'超级管理员','系统管理员',1,'2018-05-18 16:12:47');

/*Table structure for table `s_sys_role_dept` */

DROP TABLE IF EXISTS `s_sys_role_dept`;

CREATE TABLE `s_sys_role_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `roleId` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `deptId` bigint(20) DEFAULT NULL COMMENT '部门ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='角色与部门对应关系';

/*Data for the table `s_sys_role_dept` */

insert  into `s_sys_role_dept`(`id`,`roleId`,`deptId`) values (17,21,1),(18,21,2),(19,21,6),(20,21,7),(21,21,8),(32,20,2);

/*Table structure for table `s_sys_role_menu` */

DROP TABLE IF EXISTS `s_sys_role_menu`;

CREATE TABLE `s_sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `roleId` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menuId` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=239 DEFAULT CHARSET=utf8 COMMENT='角色与菜单对应关系';

/*Data for the table `s_sys_role_menu` */

insert  into `s_sys_role_menu`(`id`,`roleId`,`menuId`) values (140,21,1),(141,21,46),(142,21,2),(143,21,15),(144,21,16),(145,21,17),(146,21,18),(147,21,51),(148,21,3),(149,21,19),(150,21,20),(151,21,21),(152,21,22),(153,21,31),(154,21,32),(155,21,33),(156,21,34),(157,21,35),(158,21,49),(159,21,4),(160,21,23),(161,21,24),(162,21,25),(163,21,26),(164,21,50),(165,21,42),(166,21,27),(167,21,30),(168,21,47),(169,21,5),(170,21,29),(171,21,48),(172,21,41),(232,20,53),(233,20,54),(234,20,59),(235,20,60),(236,20,58),(237,20,61),(238,20,62);

/*Table structure for table `s_sys_user` */

DROP TABLE IF EXISTS `s_sys_user`;

CREATE TABLE `s_sys_user` (
  `userId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '后台用户Id',
  `userName` varchar(50) NOT NULL COMMENT '用户名',
  `userPass` varchar(100) DEFAULT NULL COMMENT '密码',
  `salt` varchar(20) DEFAULT NULL COMMENT '盐',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `status` int(1) DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `deptId` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `superFlag` int(1) DEFAULT '0' COMMENT '是否是超级管理员0:不是,1:是',
  `createUser` varchar(50) DEFAULT NULL COMMENT '创建者',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`userId`),
  UNIQUE KEY `username` (`userName`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='系统用户';

/*Data for the table `s_sys_user` */

insert  into `s_sys_user`(`userId`,`userName`,`userPass`,`salt`,`email`,`mobile`,`status`,`deptId`,`superFlag`,`createUser`,`createTime`) values (1,'admin','5f9c50b9d370e553b076ecf20870baab6dff1d061fb15868b62ca17f04b70a16','YzcmCZNvbXocrsz9dm8e','root@douqiu.io','13888888888',1,1,1,NULL,'2016-11-11 11:11:11'),(4,'fury','6194d6030b4ac2971e466424811242976accc16d155b3855de5c678bc31a044e','OyMQaZtwddXlsKgFLaZp','250977428@qq.com','18503078897',1,6,0,NULL,'2018-05-15 21:53:44');

/*Table structure for table `s_sys_user_role` */

DROP TABLE IF EXISTS `s_sys_user_role`;

CREATE TABLE `s_sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `roleId` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='用户与角色对应关系';

/*Data for the table `s_sys_user_role` */

insert  into `s_sys_user_role`(`id`,`userId`,`roleId`) values (9,1,21),(10,4,20);

/*Table structure for table `u_recharge` */

DROP TABLE IF EXISTS `u_recharge`;

CREATE TABLE `u_recharge` (
  `rechargeId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '充值记录表',
  `userId` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `userMobile` varchar(50) DEFAULT NULL COMMENT '用户登录账号',
  `userName` varchar(50) DEFAULT NULL COMMENT '用户账号',
  `parentId` bigint(20) DEFAULT NULL COMMENT '父级ID',
  `groupUserIds` varchar(3000) DEFAULT NULL COMMENT '用户组ID',
  `rechargeMoney` decimal(15,4) DEFAULT '0.0000' COMMENT '充值金额',
  `rechargeFee` decimal(15,4) DEFAULT '0.0000' COMMENT '充值手续费',
  `alipayName` varchar(50) DEFAULT NULL COMMENT '支付宝名称',
  `alipayMobile` varchar(50) DEFAULT NULL COMMENT '支付宝账号',
  `rechargeCode` varchar(10) DEFAULT NULL COMMENT '用户转账充值凭证码',
  `status` int(1) DEFAULT '0' COMMENT '充值订单状态[0:待支付,1:已支付,2:支付异常]',
  `frontRemark` varchar(200) DEFAULT NULL COMMENT '用于显示给前端的备注信息',
  `backRemark` varchar(200) DEFAULT NULL COMMENT '用于显示给后端的备注信息(如果有调用第三方接口的话)',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `createUser` varchar(50) DEFAULT NULL COMMENT '创建者',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `updateUser` varchar(50) DEFAULT NULL COMMENT '修改者',
  PRIMARY KEY (`rechargeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='充值记录表';

/*Data for the table `u_recharge` */

/*Table structure for table `u_recommend` */

DROP TABLE IF EXISTS `u_recommend`;

CREATE TABLE `u_recommend` (
  `recommendId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '前端团队显示主键Id',
  `userId` bigint(20) DEFAULT NULL COMMENT '用户Id',
  `parentId` bigint(20) DEFAULT NULL COMMENT '用户上级Id',
  `groupUserIds` varchar(3000) DEFAULT NULL COMMENT '用户组Id，逗号分隔',
  `recommendNumber` int(11) DEFAULT NULL COMMENT '今日推荐人数',
  `teamAchievement` decimal(15,4) DEFAULT '0.0000' COMMENT '今日团队业绩',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `createUser` varchar(50) DEFAULT NULL COMMENT '创建人',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `updateUser` varchar(50) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`recommendId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='会员每日推荐表';

/*Data for the table `u_recommend` */

/*Table structure for table `u_token` */

DROP TABLE IF EXISTS `u_token`;

CREATE TABLE `u_token` (
  `userId` bigint(20) NOT NULL,
  `token` varchar(200) NOT NULL COMMENT 'token',
  `expireTime` datetime DEFAULT NULL COMMENT '过期时间',
  `updateTime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`userId`),
  UNIQUE KEY `token` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户Token';

/*Data for the table `u_token` */

/*Table structure for table `u_user` */

DROP TABLE IF EXISTS `u_user`;

CREATE TABLE `u_user` (
  `userId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户Id',
  `userName` varchar(50) NOT NULL COMMENT '用户名',
  `userMobile` varchar(20) NOT NULL COMMENT '手机号登录号',
  `userPass` varchar(64) NOT NULL COMMENT '登录密码',
  `alipayName` varchar(50) NOT NULL COMMENT '支付宝名称',
  `alipayMobile` varchar(50) NOT NULL COMMENT '支付宝账号',
  `userLevel` int(11) NOT NULL COMMENT '会员级别',
  `parentId` bigint(20) DEFAULT NULL COMMENT '父级ID',
  `groupUserIds` varchar(3000) DEFAULT NULL COMMENT '用户组ID,逗号隔开',
  `status` int(2) NOT NULL DEFAULT '1' COMMENT '状态[0:禁用,1:正常]',
  `recommendMobile` varchar(20) DEFAULT NULL COMMENT '推荐人手机号',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `createUser` varchar(50) DEFAULT NULL COMMENT '创建人',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `updateUser` varchar(50) DEFAULT NULL COMMENT '修改人',
  `registerType` int(1) DEFAULT NULL COMMENT '注册类型[0:前端注册,1:后台添加]',
  `payPassWord` varchar(64) DEFAULT NULL COMMENT '支付密码',
  `lastLoginTime` datetime DEFAULT NULL COMMENT '上次登录时间',
  `lastLoginIp` varchar(50) DEFAULT NULL COMMENT '上次登录IP',
  PRIMARY KEY (`userId`),
  UNIQUE KEY `mobile_unique` (`userMobile`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='用户表';

/*Data for the table `u_user` */

insert  into `u_user`(`userId`,`userName`,`userMobile`,`userPass`,`alipayName`,`alipayMobile`,`userLevel`,`parentId`,`groupUserIds`,`status`,`recommendMobile`,`createTime`,`createUser`,`updateTime`,`updateUser`,`registerType`,`payPassWord`,`lastLoginTime`,`lastLoginIp`) values (1,'mark','18888888888','8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918','mark','18888888888',1,1,'1,',1,'18888888888','2017-03-23 22:37:41',NULL,NULL,NULL,1,NULL,'2019-01-13 17:33:12','0:0:0:0:0:0:0:1');

/*Table structure for table `u_wallet` */

DROP TABLE IF EXISTS `u_wallet`;

CREATE TABLE `u_wallet` (
  `walletId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '钱包主键',
  `userId` bigint(20) DEFAULT NULL COMMENT '用户Id',
  `balance` decimal(15,4) DEFAULT NULL COMMENT '钱包余额',
  `profitMoney` decimal(15,4) DEFAULT NULL COMMENT '总收益',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `createUser` varchar(50) DEFAULT NULL COMMENT '创建人',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `updateUser` varchar(50) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`walletId`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='用户钱包表';

/*Data for the table `u_wallet` */

/*Table structure for table `u_wallet_change` */

DROP TABLE IF EXISTS `u_wallet_change`;

CREATE TABLE `u_wallet_change` (
  `historyId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '历史记录主键ID',
  `userId` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `userName` varchar(50) DEFAULT NULL COMMENT '用户名称',
  `operatorType` varchar(100) DEFAULT NULL COMMENT '操作类型,提现,充值,分红....',
  `operatorName` varchar(100) DEFAULT NULL COMMENT '操作名称,提现,充值,分红....',
  `operatorMoney` decimal(15,4) DEFAULT '0.0000' COMMENT '操作金额',
  `balance` decimal(15,4) DEFAULT NULL COMMENT '钱包余额',
  `relation_id` bigint(20) DEFAULT NULL COMMENT '关联相关的订单号',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注信息',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `createUser` varchar(50) DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`historyId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='钱包操作记录表';

/*Data for the table `u_wallet_change` */

/*Table structure for table `u_withdraw` */

DROP TABLE IF EXISTS `u_withdraw`;

CREATE TABLE `u_withdraw` (
  `withdrawId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '提现记录表主键ID',
  `userId` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `userMobile` varchar(50) DEFAULT NULL COMMENT '用户登录账号',
  `userName` varchar(50) DEFAULT NULL COMMENT '用户账号',
  `parentId` bigint(20) DEFAULT NULL COMMENT '父级ID',
  `groupUserIds` varchar(3000) DEFAULT NULL COMMENT '用户组ID',
  `withdrawMoney` decimal(15,4) DEFAULT '0.0000' COMMENT '提现金额',
  `withdrawFee` decimal(15,4) DEFAULT '0.0000' COMMENT '提现手续费',
  `withdrawRealMoney` decimal(15,4) DEFAULT '0.0000' COMMENT '实际提现金额',
  `alipayName` varchar(50) DEFAULT NULL COMMENT '用户收款支付宝名称',
  `alipayMobile` varchar(50) DEFAULT NULL COMMENT '用户收款支付宝账号',
  `transferAccounts` varchar(50) DEFAULT NULL COMMENT '付款账号',
  `status` int(1) DEFAULT '0' COMMENT '提现状态[0:待审核,1:已完成,2:转账异常]',
  `frontRemark` varchar(200) DEFAULT NULL COMMENT '用于显示给前端的备注信息',
  `backRemark` varchar(200) DEFAULT NULL COMMENT '用于显示给后端的备注信息(如果有调用第三方接口的话)',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `createUser` varchar(50) DEFAULT NULL COMMENT '创建者',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `updateUser` varchar(50) DEFAULT NULL COMMENT '修改者',
  PRIMARY KEY (`withdrawId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='提现记录表';

/*Data for the table `u_withdraw` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
