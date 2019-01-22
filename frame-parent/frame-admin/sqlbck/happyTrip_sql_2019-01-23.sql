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

insert  into `QRTZ_CRON_TRIGGERS`(`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`CRON_EXPRESSION`,`TIME_ZONE_ID`) values ('FrameScheduler','TASK_1','DEFAULT','0 0/3 * * * ?','Asia/Shanghai'),('FrameScheduler','TASK_2','DEFAULT','0 0/30 * * * ?','Asia/Shanghai');

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

insert  into `QRTZ_JOB_DETAILS`(`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`,`DESCRIPTION`,`JOB_CLASS_NAME`,`IS_DURABLE`,`IS_NONCONCURRENT`,`IS_UPDATE_DATA`,`REQUESTS_RECOVERY`,`JOB_DATA`) values ('FrameScheduler','TASK_1','DEFAULT',NULL,'io.frame.modules.job.utils.ScheduleJobUtils','0','0','0','0','��\0sr\0org.quartz.JobDataMap���迩��\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap�����](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap�.�(v\n�\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap���`�\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0\rJOB_PARAM_KEYsr\0io.frame.dao.entity.ScheduleJob\0\0\0\0\0\0\0\0L\0beanNamet\0Ljava/lang/String;L\0\ncreateTimet\0Ljava/util/Date;L\0cronExpressionq\0~\0	L\0jobIdt\0Ljava/lang/Long;L\0\nmethodNameq\0~\0	L\0paramsq\0~\0	L\0remarkq\0~\0	L\0statust\0Ljava/lang/Integer;xr\0io.frame.dao.base.BaseEntity�kv���\n\0	L\0	beginTimeq\0~\0\nL\0endTimeq\0~\0\nL\0idsq\0~\0	L\0mapq\0~\0L\0\npageNumberq\0~\0L\0pageSizeq\0~\0L\0sortNameq\0~\0	L\0	sortOrderq\0~\0	L\0tokenq\0~\0	xppppppppppt\0testTasksr\0java.util.Datehj�KYt\0\0xpw\0\0X���0xt\00 0/30 * * * ?sr\0java.lang.Long;��̏#�\0J\0valuexr\0java.lang.Number������\0\0xp\0\0\0\0\0\0\0t\0testt\0framet\0有参数测试sr\0java.lang.Integer⠤���8\0I\0valuexq\0~\0\0\0\0\0x\0'),('FrameScheduler','TASK_2','DEFAULT',NULL,'io.frame.modules.job.utils.ScheduleJobUtils','0','0','0','0','��\0sr\0org.quartz.JobDataMap���迩��\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap�����](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap�.�(v\n�\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap���`�\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0\rJOB_PARAM_KEYsr\0io.frame.dao.entity.ScheduleJob\0\0\0\0\0\0\0\0L\0beanNamet\0Ljava/lang/String;L\0\ncreateTimet\0Ljava/util/Date;L\0cronExpressionq\0~\0	L\0jobIdt\0Ljava/lang/Long;L\0\nmethodNameq\0~\0	L\0paramsq\0~\0	L\0remarkq\0~\0	L\0statust\0Ljava/lang/Integer;xr\0io.frame.dao.base.BaseEntity�kv���\n\0	L\0	beginTimeq\0~\0\nL\0endTimeq\0~\0\nL\0idsq\0~\0	L\0mapq\0~\0L\0\npageNumberq\0~\0L\0pageSizeq\0~\0L\0sortNameq\0~\0	L\0	sortOrderq\0~\0	L\0tokenq\0~\0	xppppppppppt\0testTasksr\0java.util.Datehj�KYt\0\0xpw\0\0X�w�`xt\00 0/30 * * * ?sr\0java.lang.Long;��̏#�\0J\0valuexr\0java.lang.Number������\0\0xp\0\0\0\0\0\0\0t\0test2pt\0无参数测试sr\0java.lang.Integer⠤���8\0I\0valuexq\0~\0\0\0\0x\0');

/*Table structure for table `QRTZ_LOCKS` */

DROP TABLE IF EXISTS `QRTZ_LOCKS`;

CREATE TABLE `QRTZ_LOCKS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `QRTZ_LOCKS` */

insert  into `QRTZ_LOCKS`(`SCHED_NAME`,`LOCK_NAME`) values ('FrameScheduler','STATE_ACCESS'),('FrameScheduler','TRIGGER_ACCESS');

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

insert  into `QRTZ_SCHEDULER_STATE`(`SCHED_NAME`,`INSTANCE_NAME`,`LAST_CHECKIN_TIME`,`CHECKIN_INTERVAL`) values ('FrameScheduler','fury1548174861550',1548175002353,15000);

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

insert  into `QRTZ_TRIGGERS`(`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`JOB_NAME`,`JOB_GROUP`,`DESCRIPTION`,`NEXT_FIRE_TIME`,`PREV_FIRE_TIME`,`PRIORITY`,`TRIGGER_STATE`,`TRIGGER_TYPE`,`START_TIME`,`END_TIME`,`CALENDAR_NAME`,`MISFIRE_INSTR`,`JOB_DATA`) values ('FrameScheduler','TASK_1','DEFAULT','TASK_1','DEFAULT',NULL,1548175140000,1548174960000,5,'WAITING','CRON',1547450885000,0,NULL,2,'��\0sr\0org.quartz.JobDataMap���迩��\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap�����](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap�.�(v\n�\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap���`�\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0\rJOB_PARAM_KEYsr\0io.frame.dao.entity.ScheduleJob\0\0\0\0\0\0\0\0L\0beanNamet\0Ljava/lang/String;L\0\ncreateTimet\0Ljava/util/Date;L\0cronExpressionq\0~\0	L\0jobIdt\0Ljava/lang/Long;L\0\nmethodNameq\0~\0	L\0paramsq\0~\0	L\0remarkq\0~\0	L\0statust\0Ljava/lang/Integer;xr\0io.frame.dao.base.BaseEntity�kv���\n\0	L\0	beginTimeq\0~\0\nL\0endTimeq\0~\0\nL\0idsq\0~\0	L\0mapq\0~\0L\0\npageNumberq\0~\0L\0pageSizeq\0~\0L\0sortNameq\0~\0	L\0	sortOrderq\0~\0	L\0tokenq\0~\0	xppppppppppt\0testTasksr\0java.util.Datehj�KYt\0\0xpw\0\0X���0xt\0\r0 0/3 * * * ?sr\0java.lang.Long;��̏#�\0J\0valuexr\0java.lang.Number������\0\0xp\0\0\0\0\0\0\0t\0testt\0framet\0有参数测试sr\0java.lang.Integer⠤���8\0I\0valuexq\0~\0\0\0\0\0x\0'),('FrameScheduler','TASK_2','DEFAULT','TASK_2','DEFAULT',NULL,1547451000000,-1,5,'PAUSED','CRON',1547450885000,0,NULL,2,'��\0sr\0org.quartz.JobDataMap���迩��\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap�����](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap�.�(v\n�\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap���`�\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0\rJOB_PARAM_KEYsr\0io.frame.dao.entity.ScheduleJob\0\0\0\0\0\0\0\0L\0beanNamet\0Ljava/lang/String;L\0\ncreateTimet\0Ljava/util/Date;L\0cronExpressionq\0~\0	L\0jobIdt\0Ljava/lang/Long;L\0\nmethodNameq\0~\0	L\0paramsq\0~\0	L\0remarkq\0~\0	L\0statust\0Ljava/lang/Integer;xr\0io.frame.dao.base.BaseEntity�kv���\n\0	L\0	beginTimeq\0~\0\nL\0endTimeq\0~\0\nL\0idsq\0~\0	L\0mapq\0~\0L\0\npageNumberq\0~\0L\0pageSizeq\0~\0L\0sortNameq\0~\0	L\0	sortOrderq\0~\0	L\0tokenq\0~\0	xppppppppppt\0testTasksr\0java.util.Datehj�KYt\0\0xpw\0\0X�w�`xt\00 0/30 * * * ?sr\0java.lang.Long;��̏#�\0J\0valuexr\0java.lang.Number������\0\0xp\0\0\0\0\0\0\0t\0test2pt\0无参数测试sr\0java.lang.Integer⠤���8\0I\0valuexq\0~\0\0\0\0x\0');

/*Table structure for table `b_order` */

DROP TABLE IF EXISTS `b_order`;

CREATE TABLE `b_order` (
  `orderId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '购买订单主键Id',
  `parentId` bigint(20) DEFAULT NULL COMMENT '父级ID',
  `userId` bigint(20) DEFAULT NULL COMMENT '购买用户Id',
  `userName` varchar(50) DEFAULT NULL COMMENT '用户名称',
  `groupUserIds` varchar(3000) DEFAULT NULL COMMENT '用户组ID',
  `userMobile` varchar(50) DEFAULT NULL COMMENT '用户登录账号',
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
  `alreadyProfitMoney` decimal(15,4) DEFAULT '0.0000' COMMENT '以收益金额',
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='投资订单表';

/*Data for the table `b_order` */

insert  into `b_order`(`orderId`,`parentId`,`userId`,`userName`,`groupUserIds`,`userMobile`,`productTypeId`,`productTypeName`,`productId`,`productName`,`productImgurl`,`buyQuantity`,`buyMoney`,`rebateMoney`,`totalRebatePeriods`,`rebatePeriods`,`profitMoney`,`alreadyProfitMoney`,`startRebateTime`,`status`,`paymentId`,`randomCode`,`createTime`,`createUser`,`updateTime`,`updateUser`,`remark`) values (1,1,2,'fury','0,1,2,','13333333333',1,'共享汽车',1,'奔驰01','/home/images/20190119193604.JPG',1,'1000.0000','100.0000',10,10,'100.0000','0.0000',1,1,1,'123456','2019-01-21 11:11:11','admin','2019-01-21 21:05:41','admin','211');

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='商品表';

/*Data for the table `b_product` */

insert  into `b_product`(`productId`,`productName`,`productTypeId`,`saleMoney`,`saleQuantity`,`saleVolumes`,`rebateMoney`,`rebatePeriods`,`rebateTotals`,`purchaseRestriction`,`startRebateTime`,`status`,`productImgurl`,`sort`,`remark`,`createTime`,`createUser`,`updateTime`,`updateUser`) values (1,'奔驰01',2,'1000.0000',10,0,'200.0000',10,'2000.0000',-1,-1,1,'/home/images/20190119193604.JPG',1,'11a','2019-01-19 20:30:19','admin','2019-01-19 23:19:34','admin'),(2,'宝马x6',1,'2000.0000',10,0,'500.0000',5,'2500.0000',-1,-1,1,'/home/images/20190119193604.JPG',2,'1','2019-01-19 20:30:19','admin','2019-01-20 00:53:20','admin'),(3,'奥迪a8',1,'5000.0000',10,0,'500.0000',20,'10000.0000',2,-1,1,'/home/images/20190119193604.JPG',3,'1','2019-01-19 20:30:19','admin','2019-01-19 21:40:21','admin'),(4,'1112',2,'22222.0000',123,0,'123.0000',31,'3813.0000',312,312,1,'/home/images/20190120005502.JPG',31233,'3121','2019-01-19 23:16:41','admin','2019-01-20 00:55:04','admin'),(5,'1',1,'100.0000',100,0,'100.0000',10,'1000.0000',1,1,1,'/home/images/20190120005403.JPG',1,'1','2019-01-20 00:07:07','admin','2019-01-20 00:54:07','admin');

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='商品类别表';

/*Data for the table `b_product_type` */

insert  into `b_product_type`(`productTypeId`,`productTypeName`,`status`,`createTime`,`createUser`,`updateTime`,`updateUser`) values (1,'共享汽车',1,'2019-01-09 11:11:11','fury','2019-01-21 23:59:45','admin'),(2,'全民福利',1,'2019-01-09 11:11:11','fury','2019-01-21 23:59:47','admin');

/*Table structure for table `b_report` */

DROP TABLE IF EXISTS `b_report`;

CREATE TABLE `b_report` (
  `reportId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '报表统计主键ID',
  `userId` bigint(20) DEFAULT NULL COMMENT '用户Id',
  `userName` varchar(50) DEFAULT NULL COMMENT '用户名称',
  `userMobile` varchar(50) DEFAULT NULL COMMENT '用户登录账号',
  `parentId` bigint(20) DEFAULT NULL COMMENT '父级ID',
  `groupUserIds` varchar(3000) DEFAULT NULL COMMENT '用户组ID',
  `userLevel` int(11) DEFAULT NULL COMMENT '用户级别',
  `orderMoney` decimal(15,4) DEFAULT '0.0000' COMMENT '订单金额',
  `rechargeMoney` decimal(15,4) DEFAULT '0.0000' COMMENT '充值金额',
  `rechargeFee` decimal(15,4) DEFAULT '0.0000' COMMENT '充值手续费',
  `withdrawMoney` decimal(15,4) DEFAULT '0.0000' COMMENT '提现金额',
  `withdrawFee` decimal(15,4) DEFAULT '0.0000' COMMENT '提现手续费',
  `carProfitMoney` decimal(15,4) DEFAULT '0.0000' COMMENT '购买的汽车(商品)收益金额',
  `peopleWelfareMoney` decimal(15,4) DEFAULT '0.0000' COMMENT '全名福利金额',
  `globalBonusMoney` decimal(15,4) DEFAULT '0.0000' COMMENT '全球分红金额',
  `teamLeaderMoney` decimal(15,4) DEFAULT '0.0000' COMMENT '团队领导奖金额',
  `specialContributionMoney` decimal(15,4) DEFAULT '0.0000' COMMENT '特别贡献奖金额',
  `createTime` date DEFAULT NULL COMMENT '创建时间',
  `createUser` varchar(50) DEFAULT NULL COMMENT '创建者',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `updateUser` varchar(50) DEFAULT NULL COMMENT '修改者',
  PRIMARY KEY (`reportId`),
  KEY `userId_createdate` (`userId`,`createTime`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='盈亏统计报表';

/*Data for the table `b_report` */

insert  into `b_report`(`reportId`,`userId`,`userName`,`userMobile`,`parentId`,`groupUserIds`,`userLevel`,`orderMoney`,`rechargeMoney`,`rechargeFee`,`withdrawMoney`,`withdrawFee`,`carProfitMoney`,`peopleWelfareMoney`,`globalBonusMoney`,`teamLeaderMoney`,`specialContributionMoney`,`createTime`,`createUser`,`updateTime`,`updateUser`) values (11,2,'fury','13333333333',1,'0,1,2,',2,'1000.0000','100.0000','0.0000','100.0000','1.0000','1.0000','1.0000','1.0000','1.0000','1.0000','2019-01-21','admin','2019-01-21 21:05:42','admin'),(12,2,'fury','13333333333',1,'0,1,2,',2,'5000.0000','100.0000','0.0000','100.0000','1.0000','1.0000','1.0000','1.0000','1.0000','1.0000','2019-01-22',NULL,NULL,NULL),(13,1,'mark','18888888888',0,'0,1,',1,'1.0000','1.0000','1.0000','1.0000','1.0000','1.0000','1.0000','1.0000','1.0000','1.0000','2019-01-22',NULL,NULL,NULL);

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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='福利类型表';

/*Data for the table `b_welfare` */

insert  into `b_welfare`(`welfareId`,`welfareKey`,`welfareName`,`welfareValue`,`bonusPool`,`status`,`createTime`,`createUser`,`updateTime`,`updateUser`,`remark`) values (2,'GLOBAL_BONUS_KEY','全球分红','3',NULL,1,NULL,NULL,'2019-01-22 15:21:37','admin','每日直推3人'),(3,'GLOBAL_BONUS_KEY','全球分红','6',NULL,1,NULL,NULL,NULL,NULL,'每日直推6人'),(4,'TEAM_LEADERSHIP_AWARD_KEY','团队领导奖','5,3000',NULL,1,NULL,NULL,NULL,NULL,'当日直推5人,当日业绩达3000'),(5,'SPECIAL_CONTRIBUTION_AWARD_KEY','特别贡献奖','35,70,18888',NULL,1,NULL,NULL,NULL,NULL,'累计直推有效会员35人,团队人数70,累计团队业绩18888');

/*Table structure for table `s_config` */

DROP TABLE IF EXISTS `s_config`;

CREATE TABLE `s_config` (
  `configId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '配置主键ID',
  `configKey` varchar(200) DEFAULT NULL COMMENT '配置键',
  `configVal` varchar(3000) DEFAULT NULL COMMENT '配置值',
  `configName` varchar(200) DEFAULT NULL COMMENT '配置名称',
  `configType` varchar(200) DEFAULT NULL COMMENT '配置类型',
  `configStatus` int(1) DEFAULT '1' COMMENT '配置开关[0:关闭,1:开启]',
  `createTime` datetime DEFAULT NULL COMMENT '创建日期',
  `createUser` varchar(50) DEFAULT NULL COMMENT '创建者',
  `updateTime` datetime DEFAULT NULL COMMENT '修改日期',
  `updateUser` varchar(50) DEFAULT NULL COMMENT '修改者',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`configId`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COMMENT='常量配置表';

/*Data for the table `s_config` */

insert  into `s_config`(`configId`,`configKey`,`configVal`,`configName`,`configType`,`configStatus`,`createTime`,`createUser`,`updateTime`,`updateUser`,`remark`) values (1,'WITHDRAW_TIME_RANGE_KEY','-1','提现时间段','withdraw',1,'2019-01-07 17:50:00','fury',NULL,NULL,'提现时间段,-1不限制[00:00,23:59]'),(2,'WITHDRAW_MIN_KEY','-1','最小提现额','withdraw',1,'2019-01-07 17:50:00','fury',NULL,NULL,'最小提现额,-1不限制'),(3,'WITHDRAW_MAX_KEY','-1','最大提现额','withdraw',1,'2019-01-07 17:50:00','fury',NULL,NULL,'最大提现额,-1不限制'),(4,'WITHDRAW_FEE_KEY','-1','提现手续费','withdraw',1,'2019-01-07 17:50:00','fury',NULL,NULL,'最大提现额,-1不限制,如填1为百分子0.01'),(6,'WITHDRAW_SWITCH_KEY','1','提现开关','withdraw',1,'2019-01-07 17:50:00','fury',NULL,NULL,'提现开关[0:关,1:开],关闭之后用户无法提现'),(7,'RECHARGE_TIME_RANGE_KEY','-1','充值时间段','recharge',1,'2019-01-07 17:50:00','fury',NULL,NULL,'充值时间段,-1不限制[00:00,23:59]'),(10,'RECHARGE_FEE_KEY','-1','充值手续费','recharge',1,'2019-01-07 17:50:00','fury',NULL,NULL,'最大充值额,-1无手续费,如填1为百分之1'),(12,'RECHARGE_SWITCH_KEY','1','充值开关','recharge',1,'2019-01-07 17:50:00','fury',NULL,NULL,'充值开关[0:关,1:开],关闭之后用户无法充值'),(13,'CLOUD_STORAGE_CONFIG_KEY','{\"type\":1,\"qiniuDomain\":\"http://7xlij2.com1.z0.glb.clouddn.com\",\"qiniuPrefix\":\"upload\",\"qiniuAccessKey\":\"NrgMfABZxWLo5B-YYSjoE8-AZ1EISdi1Z3ubLOeZ\",\"qiniuSecretKey\":\"uIwJHevMRWU0VLxFvgy0tAcOdGqasdtVlJk','云存储配置','system',1,'2019-01-07 17:50:00','fury',NULL,NULL,'云存储配置信息,保存json格式'),(15,'GLOBAL_BONUS_KEY','0','全球分红开关','welfare',1,'2019-01-07 17:50:00','fury',NULL,NULL,'全球分红开关[0:关,1:开],关闭之后定时任务将停止'),(16,'TEAM_LEADERSHIP_AWARD_KEY','1','团队领导奖开关','welfare',1,'2019-01-07 17:50:00','fury','2019-01-22 15:21:25','admin','团队领导奖开关[0:关,1:开],关闭之后定时任务将停止'),(17,'SPECIAL_CONTRIBUTION_AWARD_KEY','1','特别贡献奖开关','welfare',1,'2019-01-07 17:50:00','fury',NULL,NULL,'特别贡献奖开关[0:关,1:开],关闭之后定时任务将停止'),(18,'WITHDRAW_COUNT_KEY','-1','提现次数','withdraw',1,'2019-01-07 17:50:00','fury',NULL,NULL,'提现次数,-1不限制'),(19,'RECHARGE_QRCODE_KEY','/home/images/20190122151158.jpg','充值收款二维码','recharge',1,'2019-01-07 17:50:00','fury','2019-01-22 15:12:12','admin','充值二维码收款图片'),(20,'SYSTEM_CUSTOMER_SERVICE_IMG_KEY','/home/images/20190122151113.jpg','客服二维码图片','system',1,'2019-01-07 17:50:00','fury','2019-01-22 15:11:15','admin','客服二维码图片'),(21,'SYSTEM_COMPANY_INTRODUCE_KEY','<p><img src=\"http://localhost:8082/admin/statics/common/layui/images/face/27.gif\" alt=\"[疑问]\"><img src=\"http://localhost:8082/admin/readImg?path=/home/images/20190122151312.jpg\" alt=\"/home/images/20190122151312.jpg\"></p><p><br></p><p>BBBB:</p><p>sad</p><p>czxcaasda</p>','公司介绍','system',1,'2019-01-07 17:50:00','fury','2019-01-22 15:38:55','admin','公司介绍'),(22,'WELFARE_SWITCH_DAILY_KEY','1','天返福利开关','welfare',1,'2019-01-07 17:50:00',NULL,NULL,NULL,'天返福利开关[0:关,1:开]'),(23,'WELFARE_SWITCH_HOUR_KEY','1','时返福利开关','welfare',1,'2019-01-07 17:50:00',NULL,NULL,NULL,'时返福利开关[0:关,1:开]'),(24,'SYSTEM_SPREAD_DOMAIN_KEY','http://localhost:8082/admin','推广域名','system',1,'2019-01-07 17:50:00',NULL,NULL,NULL,'推广域名'),(25,'SYSTEM_REGISTER_DOMAIN_KEY','http://zzzx','注册域名','system',1,'2019-01-07 17:50:00','fury',NULL,NULL,'注册域名');

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
) ENGINE=InnoDB AUTO_INCREMENT=162 DEFAULT CHARSET=utf8 COMMENT='系统日志';

/*Data for the table `s_log` */

insert  into `s_log`(`logId`,`userName`,`operation`,`method`,`params`,`time`,`ip`,`sources`,`createTime`) values (1,'admin','创建帐变','io.frame.modules.happytrip.service.impl.WalletChangeServiceImpl.createWalletChange()','2|-1000.0000|{\"userId\":2,\"operatorMoney\":1000.0000,\"relationId\":1}|\"PURCHASE_CAR_SPACE_KEY\"|',51,'0:0:0:0:0:0:0:1',1,'2019-01-21 20:50:23'),(2,'admin','更新报表','io.frame.modules.happytrip.service.impl.ReportServiceImpl.upsert()','2|1000.0000|0|\"MANUAL_DEDUCTION_KEY\"|',56,'0:0:0:0:0:0:0:1',1,'2019-01-21 20:50:23'),(3,'admin','更新推荐','io.frame.modules.happytrip.service.impl.RecommendServiceImpl.upsert()','1|null|1000.0000|',54,'0:0:0:0:0:0:0:1',1,'2019-01-21 20:50:23'),(4,'admin','修改订单状态','io.frame.modules.happytrip.controller.OrderController.status()','1|1|',465,'0:0:0:0:0:0:0:1',1,'2019-01-21 20:50:23'),(5,'admin','创建帐变','io.frame.modules.happytrip.service.impl.WalletChangeServiceImpl.createWalletChange()','2|90.0000|{\"userId\":2,\"operatorMoney\":90.0000,\"relationId\":1}|\"WITHDRAW_OUT_BACK_KEY\"|',46,'0:0:0:0:0:0:0:1',1,'2019-01-21 20:51:54'),(6,'admin','提现回退,加钱','io.frame.modules.happytrip.service.impl.WalletServiceImpl.subtractBack()','{\"userId\":2,\"operatorMoney\":90.0000,\"relationId\":1}|\"WITHDRAW_OUT_BACK_KEY\"|',132,'0:0:0:0:0:0:0:1',1,'2019-01-21 20:51:54'),(7,'admin','创建帐变','io.frame.modules.happytrip.service.impl.WalletChangeServiceImpl.createWalletChange()','2|10.0000|{\"userId\":2,\"operatorMoney\":10.0000,\"relationId\":1}|\"WITHDRAW_OUT_BACK_FEE_KEY\"|',42,'0:0:0:0:0:0:0:1',1,'2019-01-21 20:51:54'),(8,'admin','提现回退,加钱','io.frame.modules.happytrip.service.impl.WalletServiceImpl.subtractBack()','{\"userId\":2,\"operatorMoney\":10.0000,\"relationId\":1}|\"WITHDRAW_OUT_BACK_FEE_KEY\"|',126,'0:0:0:0:0:0:0:1',1,'2019-01-21 20:51:54'),(9,'admin','修改提现状态','io.frame.modules.happytrip.controller.WithdrawController.status()','1|2|',387,'0:0:0:0:0:0:0:1',1,'2019-01-21 20:51:54'),(10,'admin','更新报表','io.frame.modules.happytrip.service.impl.ReportServiceImpl.upsert()','2|100.0000|10.0000|\"WITHDRAW_OUT_KEY\"|',49,'0:0:0:0:0:0:0:1',1,'2019-01-21 20:52:57'),(11,'admin','修改提现状态','io.frame.modules.happytrip.controller.WithdrawController.status()','1|1|',158,'0:0:0:0:0:0:0:1',1,'2019-01-21 20:52:57'),(12,'admin','创建帐变','io.frame.modules.happytrip.service.impl.WalletChangeServiceImpl.createWalletChange()','2|-1000.0000|{\"userId\":2,\"operatorMoney\":1000.0000,\"relationId\":1}|\"PURCHASE_CAR_SPACE_KEY\"|',45,'0:0:0:0:0:0:0:1',1,'2019-01-21 20:53:39'),(13,'admin','更新报表','io.frame.modules.happytrip.service.impl.ReportServiceImpl.upsert()','2|1000.0000|0|\"MANUAL_DEDUCTION_KEY\"|',59,'0:0:0:0:0:0:0:1',1,'2019-01-21 20:53:39'),(14,'admin','更新推荐','io.frame.modules.happytrip.service.impl.RecommendServiceImpl.upsert()','1|null|1000.0000|',42,'0:0:0:0:0:0:0:1',1,'2019-01-21 20:53:39'),(15,'admin','修改订单状态','io.frame.modules.happytrip.controller.OrderController.status()','1|1|',395,'0:0:0:0:0:0:0:1',1,'2019-01-21 20:53:39'),(16,'admin','创建帐变','io.frame.modules.happytrip.service.impl.WalletChangeServiceImpl.createWalletChange()','2|90.0000|{\"userId\":2,\"operatorMoney\":90.0000,\"relationId\":1}|\"WITHDRAW_OUT_BACK_KEY\"|',38,'0:0:0:0:0:0:0:1',1,'2019-01-21 20:56:11'),(17,'admin','提现回退,加钱','io.frame.modules.happytrip.service.impl.WalletServiceImpl.subtractBack()','{\"userId\":2,\"operatorMoney\":90.0000,\"relationId\":1}|\"WITHDRAW_OUT_BACK_KEY\"|',103,'0:0:0:0:0:0:0:1',1,'2019-01-21 20:56:11'),(18,'admin','创建帐变','io.frame.modules.happytrip.service.impl.WalletChangeServiceImpl.createWalletChange()','2|10.0000|{\"userId\":2,\"operatorMoney\":10.0000,\"relationId\":1}|\"WITHDRAW_OUT_BACK_FEE_KEY\"|',28,'0:0:0:0:0:0:0:1',1,'2019-01-21 20:56:11'),(19,'admin','提现回退,加钱','io.frame.modules.happytrip.service.impl.WalletServiceImpl.subtractBack()','{\"userId\":2,\"operatorMoney\":10.0000,\"relationId\":1}|\"WITHDRAW_OUT_BACK_FEE_KEY\"|',83,'0:0:0:0:0:0:0:1',1,'2019-01-21 20:56:11'),(20,'admin','修改提现状态','io.frame.modules.happytrip.controller.WithdrawController.status()','1|2|',288,'0:0:0:0:0:0:0:1',1,'2019-01-21 20:56:11'),(21,'admin','创建帐变','io.frame.modules.happytrip.service.impl.WalletChangeServiceImpl.createWalletChange()','2|100.0000|{\"userId\":2,\"operatorMoney\":100.0000,\"relationId\":1}|\"RECHARGE_IN_KEY\"|',34,'0:0:0:0:0:0:0:1',1,'2019-01-21 20:56:26'),(22,'admin','更新报表','io.frame.modules.happytrip.service.impl.ReportServiceImpl.upsert()','2|100.0000|0|\"RECHARGE_IN_KEY\"|',97,'0:0:0:0:0:0:0:1',1,'2019-01-21 20:56:27'),(23,'admin','修改充值状态','io.frame.modules.happytrip.controller.RechargeController.status()','1|1|',272,'0:0:0:0:0:0:0:1',1,'2019-01-21 20:56:27'),(28,'admin','创建帐变','io.frame.modules.happytrip.service.impl.WalletChangeServiceImpl.createWalletChange()','2|-1000.0000|{\"userId\":2,\"operatorMoney\":1000.0000,\"relationId\":1}|\"PURCHASE_CAR_SPACE_KEY\"|',46,'0:0:0:0:0:0:0:1',1,'2019-01-21 21:01:41'),(29,'admin','更新报表','io.frame.modules.happytrip.service.impl.ReportServiceImpl.upsert()','2|1000.0000|0|\"PURCHASE_CAR_SPACE_KEY\"|',50,'0:0:0:0:0:0:0:1',1,'2019-01-21 21:01:41'),(30,'admin','更新推荐','io.frame.modules.happytrip.service.impl.RecommendServiceImpl.upsert()','1|null|1000.0000|',43,'0:0:0:0:0:0:0:1',1,'2019-01-21 21:01:41'),(31,'admin','修改订单状态','io.frame.modules.happytrip.controller.OrderController.status()','1|1|',326,'0:0:0:0:0:0:0:1',1,'2019-01-21 21:01:41'),(32,'admin','创建帐变','io.frame.modules.happytrip.service.impl.WalletChangeServiceImpl.createWalletChange()','2|100.0000|{\"userId\":2,\"operatorMoney\":100.0000,\"relationId\":1}|\"RECHARGE_IN_KEY\"|',59,'0:0:0:0:0:0:0:1',1,'2019-01-21 21:05:20'),(33,'admin','更新报表','io.frame.modules.happytrip.service.impl.ReportServiceImpl.upsert()','2|100.0000|0|\"RECHARGE_IN_KEY\"|',69,'0:0:0:0:0:0:0:1',1,'2019-01-21 21:05:20'),(34,'admin','修改充值状态','io.frame.modules.happytrip.controller.RechargeController.status()','1|1|',320,'0:0:0:0:0:0:0:1',1,'2019-01-21 21:05:20'),(35,'admin','创建帐变','io.frame.modules.happytrip.service.impl.WalletChangeServiceImpl.createWalletChange()','2|-1000.0000|{\"userId\":2,\"operatorMoney\":1000.0000,\"relationId\":1}|\"PURCHASE_CAR_SPACE_KEY\"|',54,'0:0:0:0:0:0:0:1',1,'2019-01-21 21:05:42'),(36,'admin','更新报表','io.frame.modules.happytrip.service.impl.ReportServiceImpl.upsert()','2|1000.0000|0|\"PURCHASE_CAR_SPACE_KEY\"|',86,'0:0:0:0:0:0:0:1',1,'2019-01-21 21:05:42'),(37,'admin','更新推荐','io.frame.modules.happytrip.service.impl.RecommendServiceImpl.upsert()','1|null|1000.0000|',92,'0:0:0:0:0:0:0:1',1,'2019-01-21 21:05:42'),(38,'admin','修改订单状态','io.frame.modules.happytrip.controller.OrderController.status()','1|1|',423,'0:0:0:0:0:0:0:1',1,'2019-01-21 21:05:42'),(39,'admin','保存菜单','io.frame.modules.sys.controller.SysMenuController.save()','{\"parentId\":53,\"name\":\"福利管理\",\"type\":0,\"icon\":\"larry-a536\",\"orderNum\":5,\"map\":{\"parentName\":\"Go系统管理\"}}',123,'0:0:0:0:0:0:0:1',1,'2019-01-21 21:12:19'),(40,'admin','修改菜单','io.frame.modules.sys.controller.SysMenuController.update()','{\"menuId\":60,\"parentId\":53,\"name\":\"帐变管理\",\"url\":\"modules/ht/walletChange.html\",\"perms\":\"ht:walletChange:list\",\"type\":1,\"icon\":\"larry-log\",\"spread\":0,\"orderNum\":8,\"map\":{\"parentName\":\"Go系统管理\"}}',146,'0:0:0:0:0:0:0:1',1,'2019-01-21 21:12:50'),(41,'admin','修改菜单','io.frame.modules.sys.controller.SysMenuController.update()','{\"menuId\":63,\"parentId\":53,\"name\":\"定时任务\",\"url\":\"modules/job/schedule.html\",\"type\":1,\"icon\":\"larry-renwu2\",\"spread\":0,\"orderNum\":9,\"map\":{\"parentName\":\"Go系统管理\"}}',114,'0:0:0:0:0:0:0:1',1,'2019-01-21 21:13:04'),(42,'admin','修改菜单','io.frame.modules.sys.controller.SysMenuController.update()','{\"menuId\":58,\"parentId\":53,\"name\":\"日志管理\",\"url\":\"modules/happytrip/log.html\",\"perms\":\"sys:log:list\",\"type\":1,\"icon\":\"larry-10109\",\"spread\":0,\"orderNum\":9,\"map\":{\"parentName\":\"Go系统管理\"}}',80,'0:0:0:0:0:0:0:1',1,'2019-01-21 21:13:43'),(43,'admin','保存菜单','io.frame.modules.sys.controller.SysMenuController.save()','{\"parentId\":82,\"name\":\"福利列表\",\"url\":\"modules/happytrip/welfare.html\",\"perms\":\"ht:walfare:list,ht:walfare:info,ht:walfare:update,ht:walfare:save,ht:walfare:delete\",\"type\":1,\"icon\":\"larry-xitong4\",\"orderNum\":0,\"map\":{\"parentName\":\"福利管理\"}}',75,'0:0:0:0:0:0:0:1',1,'2019-01-21 21:16:24'),(44,'mark','登录接口','io.frame.service.impl.UserServiceImpl.login()','{\"userMobile\":\"18888888888\",\"userPass\":\"8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918\"}',438,'113.116.116.131',0,'2019-01-21 22:26:04'),(45,'admin','修改菜单','io.frame.modules.sys.controller.SysMenuController.update()','{\"menuId\":83,\"parentId\":82,\"name\":\"福利列表\",\"url\":\"modules/happytrip/welfare.html\",\"perms\":\"ht:welfare:list,ht:welfare:info,ht:welfare:update,ht:welfare:save,ht:welfare:delete\",\"type\":1,\"icon\":\"larry-xitong4\",\"spread\":0,\"orderNum\":0,\"map\":{\"parentName\":\"福利管理\"}}',97,'0:0:0:0:0:0:0:1',1,'2019-01-21 22:39:22'),(46,'admin','修改福利状态','io.frame.modules.happytrip.controller.WelfareController.status()','2|0|',64,'0:0:0:0:0:0:0:1',1,'2019-01-21 22:39:38'),(47,'admin','修改福利状态','io.frame.modules.happytrip.controller.WelfareController.status()','2|1|',53,'0:0:0:0:0:0:0:1',1,'2019-01-21 22:39:43'),(48,'admin','修改福利状态','io.frame.modules.happytrip.controller.WelfareController.status()','2|0|',62,'0:0:0:0:0:0:0:1',1,'2019-01-21 22:49:57'),(49,'admin','修改福利状态','io.frame.modules.happytrip.controller.WelfareController.status()','2|1|',64,'0:0:0:0:0:0:0:1',1,'2019-01-21 22:50:28'),(50,'admin','修改福利状态','io.frame.modules.happytrip.controller.WelfareController.status()','2|1|',59,'0:0:0:0:0:0:0:1',1,'2019-01-21 22:50:34'),(51,'admin','修改福利状态','io.frame.modules.happytrip.controller.WelfareController.status()','2|1|',74,'0:0:0:0:0:0:0:1',1,'2019-01-21 22:50:42'),(52,'admin','修改福利状态','io.frame.modules.happytrip.controller.WelfareController.status()','2|1|',74,'0:0:0:0:0:0:0:1',1,'2019-01-21 22:51:01'),(53,'mark','登录接口','io.frame.service.impl.UserServiceImpl.login()','{\"userMobile\":\"18888888888\",\"userPass\":\"8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918\"}',21,'163.125.43.42',0,'2019-01-21 23:12:44'),(54,'mark','登录接口','io.frame.service.impl.UserServiceImpl.login()','{\"userMobile\":\"18888888888\",\"userPass\":\"8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918\"}',8,'163.125.43.42',0,'2019-01-21 23:27:08'),(55,'mark','登录接口','io.frame.service.impl.UserServiceImpl.login()','{\"userMobile\":\"18888888888\",\"userPass\":\"8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918\"}',11,'163.125.43.42',0,'2019-01-21 23:30:28'),(56,'mark','登录接口','io.frame.service.impl.UserServiceImpl.login()','{\"userMobile\":\"18888888888\",\"userPass\":\"8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918\"}',9,'163.125.43.42',0,'2019-01-21 23:38:47'),(57,'mark','登录接口','io.frame.service.impl.UserServiceImpl.login()','{\"userMobile\":\"18888888888\",\"userPass\":\"8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918\"}',588,'163.125.43.42',0,'2019-01-21 23:47:22'),(58,'admin','新增福利','io.frame.modules.happytrip.controller.WelfareController.save()','{\"welfareKey\":\"TEAM_LEADERSHIP_AWARD_KEY\",\"welfareValue\":\"1121\",\"bonusPool\":1222,\"createUser\":\"admin\",\"updateTime\":\"Jan 21, 2019 11:51:52 PM\",\"remark\":\"当日直推X人,当日业绩达X\"}',70,'0:0:0:0:0:0:0:1',1,'2019-01-21 23:51:52'),(59,'admin','新增福利','io.frame.modules.happytrip.controller.WelfareController.save()','{\"welfareKey\":\"TEAM_LEADERSHIP_AWARD_KEY\",\"welfareValue\":\"123\",\"bonusPool\":312,\"createUser\":\"admin\",\"updateTime\":\"Jan 21, 2019 11:52:32 PM\",\"remark\":\"当日直推X人,当日业绩达X\"}',87,'0:0:0:0:0:0:0:1',1,'2019-01-21 23:52:32'),(60,'mark','登录接口','io.frame.service.impl.UserServiceImpl.login()','{\"userMobile\":\"18888888888\",\"userPass\":\"8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918\"}',36,'163.125.43.42',0,'2019-01-21 23:52:54'),(61,'admin','删除福利','io.frame.modules.happytrip.controller.WelfareController.delete()','6',48,'0:0:0:0:0:0:0:1',1,'2019-01-21 23:58:40'),(62,'fury','登录接口','io.frame.service.impl.UserServiceImpl.login()','{\"userMobile\":\"13333333333\",\"userPass\":\"8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92\"}',28,'113.116.116.131',0,'2019-01-21 23:59:13'),(63,'admin','修改商品类型状态','io.frame.modules.happytrip.controller.ProductTypeController.status()','1|1|',43,'0:0:0:0:0:0:0:1',1,'2019-01-21 23:59:45'),(64,'admin','修改商品类型状态','io.frame.modules.happytrip.controller.ProductTypeController.status()','2|1|',76,'0:0:0:0:0:0:0:1',1,'2019-01-21 23:59:47'),(65,'admin','修改福利','io.frame.modules.happytrip.controller.WelfareController.update()','{\"welfareId\":7,\"welfareKey\":\"TEAM_LEADERSHIP_AWARD_KEY\",\"welfareValue\":\"1\",\"bonusPool\":1,\"createUser\":\"admin\",\"updateTime\":\"Jan 22, 2019 12:05:05 AM\",\"updateUser\":\"admin\",\"remark\":\"当日直推X人,当日业绩达X\"}',107,'0:0:0:0:0:0:0:1',1,'2019-01-22 00:05:06'),(66,'admin','修改福利','io.frame.modules.happytrip.controller.WelfareController.update()','{\"welfareId\":7,\"welfareKey\":\"TEAM_LEADERSHIP_AWARD_KEY\",\"welfareValue\":\"122\",\"bonusPool\":122,\"createUser\":\"admin\",\"updateTime\":\"Jan 22, 2019 12:05:52 AM\",\"updateUser\":\"admin\",\"remark\":\"当日直推X人,当日业绩达X\"}',46,'0:0:0:0:0:0:0:1',1,'2019-01-22 00:05:52'),(67,'mark','登录接口','io.frame.service.impl.UserServiceImpl.login()','{\"userMobile\":\"18888888888\",\"userPass\":\"8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918\"}',9,'163.125.43.42',0,'2019-01-22 00:10:45'),(68,'fury','登录接口','io.frame.service.impl.UserServiceImpl.login()','{\"userMobile\":\"13333333333\",\"userPass\":\"8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92\"}',29,'113.116.116.131',0,'2019-01-22 00:10:49'),(69,'fury','登出接口','io.frame.service.impl.TokenServiceImpl.expireToken()','2',3,'113.116.116.131',0,'2019-01-22 00:11:09'),(70,'mark','登录接口','io.frame.service.impl.UserServiceImpl.login()','{\"userMobile\":\"18888888888\",\"userPass\":\"8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918\"}',10,'163.125.43.42',0,'2019-01-22 00:15:02'),(71,'mark','登录接口','io.frame.service.impl.UserServiceImpl.login()','{\"userMobile\":\"18888888888\",\"userPass\":\"8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918\"}',18,'163.125.43.42',0,'2019-01-22 00:17:16'),(72,'mark','登录接口','io.frame.service.impl.UserServiceImpl.login()','{\"userMobile\":\"18888888888\",\"userPass\":\"8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918\"}',10,'163.125.43.42',0,'2019-01-22 00:21:00'),(73,'mark','登出接口','io.frame.service.impl.TokenServiceImpl.expireToken()','1',2,'163.125.43.42',0,'2019-01-22 00:21:35'),(74,'mark','登出接口','io.frame.service.impl.TokenServiceImpl.expireToken()','1',1,'163.125.43.42',0,'2019-01-22 00:22:47'),(75,'admin','修改福利','io.frame.modules.happytrip.controller.WelfareController.update()','{\"welfareId\":7,\"welfareKey\":\"TEAM_LEADERSHIP_AWARD_KEY\",\"welfareValue\":\"122\",\"bonusPool\":122,\"createUser\":\"admin\",\"updateTime\":\"Jan 22, 2019 12:29:23 AM\",\"updateUser\":\"admin\",\"remark\":\"当日直推X人,当日业绩达X\"}',55,'0:0:0:0:0:0:0:1',1,'2019-01-22 00:29:24'),(76,'mark','登录接口','io.frame.service.impl.UserServiceImpl.login()','{\"userMobile\":\"18888888888\",\"userPass\":\"8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918\"}',40,'163.125.43.42',0,'2019-01-22 00:32:07'),(77,'mark','登录接口','io.frame.service.impl.UserServiceImpl.login()','{\"userMobile\":\"18888888888\",\"userPass\":\"8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918\"}',29,'163.125.43.42',0,'2019-01-22 00:33:12'),(78,'mark','登出接口','io.frame.service.impl.TokenServiceImpl.expireToken()','1',2,'163.125.43.42',0,'2019-01-22 00:33:13'),(79,'admin','删除福利','io.frame.modules.happytrip.controller.WelfareController.delete()','7',81,'0:0:0:0:0:0:0:1',1,'2019-01-22 00:33:25'),(80,'mark','登录接口','io.frame.service.impl.UserServiceImpl.login()','{\"userMobile\":\"18888888888\",\"userPass\":\"8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918\"}',8,'163.125.43.42',0,'2019-01-22 00:35:09'),(81,'mark','登出接口','io.frame.service.impl.TokenServiceImpl.expireToken()','1',2,'163.125.43.42',0,'2019-01-22 00:35:11'),(82,'admin','修改福利','io.frame.modules.happytrip.controller.WelfareController.update()','{\"welfareId\":2,\"welfareKey\":\"GLOBAL_BONUS_KEY\",\"welfareName\":\"全球分红\",\"welfareValue\":\"3\",\"status\":1,\"updateTime\":\"Jan 22, 2019 12:41:30 AM\",\"updateUser\":\"admin\",\"remark\":\"每日直推3人1111x\"}',60,'0:0:0:0:0:0:0:1',1,'2019-01-22 00:41:30'),(83,'admin','修改福利','io.frame.modules.happytrip.controller.WelfareController.update()','{\"welfareId\":2,\"welfareKey\":\"GLOBAL_BONUS_KEY\",\"welfareName\":\"全球分红\",\"welfareValue\":\"3\",\"status\":1,\"updateTime\":\"Jan 22, 2019 12:41:35 AM\",\"updateUser\":\"admin\",\"remark\":\"每日直推3人1111xX\"}',120,'0:0:0:0:0:0:0:1',1,'2019-01-22 00:41:36'),(84,'admin','修改福利','io.frame.modules.happytrip.controller.WelfareController.update()','{\"welfareId\":2,\"welfareKey\":\"GLOBAL_BONUS_KEY\",\"welfareName\":\"全球分红\",\"welfareValue\":\"3\",\"status\":1,\"updateTime\":\"Jan 22, 2019 12:42:10 AM\",\"updateUser\":\"admin\",\"remark\":\"每日直推3人\"}',58,'0:0:0:0:0:0:0:1',1,'2019-01-22 00:42:11'),(85,'admin','修改福利','io.frame.modules.happytrip.controller.WelfareController.update()','{\"welfareId\":2,\"welfareKey\":\"GLOBAL_BONUS_KEY\",\"welfareName\":\"全球分红\",\"welfareValue\":\"3\",\"status\":1,\"updateTime\":\"Jan 22, 2019 12:42:19 AM\",\"updateUser\":\"admin\",\"remark\":\"每日直推3人\"}',75,'0:0:0:0:0:0:0:1',1,'2019-01-22 00:42:19'),(86,'admin','修改福利','io.frame.modules.happytrip.controller.WelfareController.update()','{\"welfareId\":2,\"welfareKey\":\"GLOBAL_BONUS_KEY\",\"welfareName\":\"全球分红\",\"welfareValue\":\"3\",\"status\":1,\"updateTime\":\"Jan 22, 2019 12:42:27 AM\",\"updateUser\":\"admin\",\"remark\":\"每日直推3人\"}',61,'0:0:0:0:0:0:0:1',1,'2019-01-22 00:42:27'),(87,'admin','修改福利状态','io.frame.modules.happytrip.controller.WelfareController.status()','2|0|',117,'0:0:0:0:0:0:0:1',1,'2019-01-22 00:48:59'),(88,'admin','修改福利信息','io.frame.modules.happytrip.controller.WelfareController.update()','{\"welfareId\":2,\"welfareKey\":\"GLOBAL_BONUS_KEY\",\"welfareName\":\"全球分红\",\"welfareValue\":\"3\",\"status\":0,\"updateTime\":\"Jan 22, 2019 12:49:25 AM\",\"updateUser\":\"admin\",\"remark\":\"每日直推3人111\"}',81,'0:0:0:0:0:0:0:1',1,'2019-01-22 00:49:25'),(89,'admin','修改福利信息','io.frame.modules.happytrip.controller.WelfareController.update()','{\"welfareId\":2,\"welfareKey\":\"GLOBAL_BONUS_KEY\",\"welfareName\":\"全球分红\",\"welfareValue\":\"3\",\"status\":0,\"updateTime\":\"Jan 22, 2019 12:49:29 AM\",\"updateUser\":\"admin\",\"remark\":\"每日直推3人111\"}',61,'0:0:0:0:0:0:0:1',1,'2019-01-22 00:49:30'),(90,'admin','修改福利信息','io.frame.modules.happytrip.controller.WelfareController.update()','{\"welfareId\":2,\"welfareKey\":\"GLOBAL_BONUS_KEY\",\"welfareName\":\"全球分红\",\"welfareValue\":\"311111111\",\"status\":0,\"updateTime\":\"Jan 22, 2019 12:53:43 AM\",\"updateUser\":\"admin\",\"remark\":\"每日直推3人111\"}',66,'0:0:0:0:0:0:0:1',1,'2019-01-22 00:53:44'),(91,'admin','修改福利信息','io.frame.modules.happytrip.controller.WelfareController.update()','{\"welfareId\":2,\"welfareKey\":\"GLOBAL_BONUS_KEY\",\"welfareName\":\"全球分红\",\"welfareValue\":\"311111111\",\"status\":1,\"updateTime\":\"Jan 22, 2019 12:55:00 AM\",\"updateUser\":\"admin\",\"remark\":\"每日直推3人111\"}',76,'0:0:0:0:0:0:0:1',1,'2019-01-22 00:55:00'),(92,'admin','修改福利信息','io.frame.modules.happytrip.controller.WelfareController.update()','{\"welfareId\":2,\"welfareKey\":\"GLOBAL_BONUS_KEY\",\"welfareName\":\"全球分红\",\"welfareValue\":\"31\",\"status\":1,\"updateTime\":\"Jan 22, 2019 12:55:07 AM\",\"updateUser\":\"admin\",\"remark\":\"每日直推3人111\"}',60,'0:0:0:0:0:0:0:1',1,'2019-01-22 00:55:07'),(93,'admin','修改福利信息','io.frame.modules.happytrip.controller.WelfareController.update()','{\"welfareId\":2,\"welfareKey\":\"GLOBAL_BONUS_KEY\",\"welfareName\":\"全球分红\",\"welfareValue\":\"3\",\"status\":1,\"updateTime\":\"Jan 22, 2019 12:55:13 AM\",\"updateUser\":\"admin\",\"remark\":\"每日直推3人111\"}',61,'0:0:0:0:0:0:0:1',1,'2019-01-22 00:55:14'),(94,'admin','修改福利信息','io.frame.modules.happytrip.controller.WelfareController.update()','{\"welfareId\":2,\"welfareKey\":\"GLOBAL_BONUS_KEY\",\"welfareName\":\"全球分红\",\"welfareValue\":\"3\",\"status\":0,\"updateTime\":\"Jan 22, 2019 12:56:55 AM\",\"updateUser\":\"admin\",\"remark\":\"每日直推3人\"}',74,'0:0:0:0:0:0:0:1',1,'2019-01-22 00:56:56'),(95,'admin','新增福利','io.frame.modules.happytrip.controller.WelfareController.save()','{\"welfareKey\":\"GLOBAL_BONUS_KEY\",\"welfareName\":\"全球分红\",\"welfareValue\":\"1,\",\"createTime\":\"Jan 22, 2019 12:57:58 AM\",\"createUser\":\"admin\",\"remark\":\"每日直推111人\"}',96,'0:0:0:0:0:0:0:1',1,'2019-01-22 00:57:58'),(96,'admin','修改福利信息','io.frame.modules.happytrip.controller.WelfareController.update()','{\"welfareId\":8,\"welfareKey\":\"GLOBAL_BONUS_KEY\",\"welfareName\":\"全球分红\",\"welfareValue\":\"1\",\"createUser\":\"admin\",\"updateTime\":\"Jan 22, 2019 12:58:17 AM\",\"updateUser\":\"admin\",\"remark\":\"每日直推111人\"}',72,'0:0:0:0:0:0:0:1',1,'2019-01-22 00:58:18'),(97,'admin','修改福利信息','io.frame.modules.happytrip.controller.WelfareController.update()','{\"welfareId\":8,\"welfareKey\":\"GLOBAL_BONUS_KEY\",\"welfareName\":\"全球分红\",\"welfareValue\":\"1,\",\"createUser\":\"admin\",\"updateTime\":\"Jan 22, 2019 12:58:41 AM\",\"updateUser\":\"admin\",\"remark\":\"每日直推111人\"}',25360,'0:0:0:0:0:0:0:1',1,'2019-01-22 00:59:07'),(98,'admin','修改福利信息','io.frame.modules.happytrip.controller.WelfareController.update()','{\"welfareId\":8,\"welfareKey\":\"GLOBAL_BONUS_KEY\",\"welfareName\":\"全球分红\",\"welfareValue\":\"1\",\"createUser\":\"admin\",\"updateTime\":\"Jan 22, 2019 12:59:18 AM\",\"updateUser\":\"admin\",\"remark\":\"每日直推111人\"}',93,'0:0:0:0:0:0:0:1',1,'2019-01-22 00:59:19'),(99,'admin','删除福利','io.frame.modules.happytrip.controller.WelfareController.delete()','8',74,'0:0:0:0:0:0:0:1',1,'2019-01-22 00:59:38'),(100,'admin','修改菜单','io.frame.modules.sys.controller.SysMenuController.update()','{\"menuId\":71,\"parentId\":61,\"name\":\"充值参数\",\"url\":\"modules/happytrip/rechargeConfig.html\",\"perms\":\"sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete\",\"type\":1,\"icon\":\"fa-array\",\"spread\":0,\"orderNum\":0,\"map\":{\"parentName\":\"参数管理\"}}',122,'0:0:0:0:0:0:0:1',1,'2019-01-22 01:00:41'),(101,'admin','保存菜单','io.frame.modules.sys.controller.SysMenuController.save()','{\"parentId\":61,\"name\":\"提现参数\",\"url\":\"modules/happytrip/withdrawConfig.html\",\"type\":1,\"icon\":\"larry-faxianhover\",\"orderNum\":1,\"map\":{\"parentName\":\"参数管理\"}}',110,'0:0:0:0:0:0:0:1',1,'2019-01-22 01:01:34'),(102,'admin','修改菜单','io.frame.modules.sys.controller.SysMenuController.update()','{\"menuId\":84,\"parentId\":61,\"name\":\"提现参数\",\"url\":\"modules/happytrip/withdrawConfig.html\",\"perms\":\"sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete\",\"type\":1,\"icon\":\"larry-faxianhover\",\"spread\":0,\"orderNum\":1,\"map\":{\"parentName\":\"参数管理\"}}',167,'0:0:0:0:0:0:0:1',1,'2019-01-22 01:01:56'),(103,'admin','保存菜单','io.frame.modules.sys.controller.SysMenuController.save()','{\"parentId\":61,\"name\":\"系统参数\",\"url\":\"modules/happytrip/systemConfig.html\",\"perms\":\"sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete\",\"type\":1,\"icon\":\"larry-system-extension\",\"orderNum\":2,\"map\":{\"parentName\":\"参数管理\"}}',122,'0:0:0:0:0:0:0:1',1,'2019-01-22 01:04:14'),(104,'admin','修改菜单','io.frame.modules.sys.controller.SysMenuController.update()','{\"menuId\":71,\"parentId\":61,\"name\":\"充值参数\",\"url\":\"modules/happytrip/rechargeConfig.html\",\"perms\":\"sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete\",\"type\":1,\"icon\":\"larry-tubiaozhizuomoban-copy\",\"spread\":0,\"orderNum\":0,\"map\":{\"parentName\":\"参数管理\"}}',133,'0:0:0:0:0:0:0:1',1,'2019-01-22 01:04:46'),(105,'admin','修改菜单','io.frame.modules.sys.controller.SysMenuController.update()','{\"menuId\":71,\"parentId\":61,\"name\":\"充值参数\",\"url\":\"modules/happytrip/rechargeConfig.html?configType\\u003drecharge\",\"perms\":\"sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete\",\"type\":1,\"icon\":\"larry-tubiaozhizuomoban-copy\",\"spread\":0,\"orderNum\":0,\"map\":{\"parentName\":\"参数管理\"}}',264,'0:0:0:0:0:0:0:1',1,'2019-01-22 10:20:39'),(106,'admin','修改菜单','io.frame.modules.sys.controller.SysMenuController.update()','{\"menuId\":84,\"parentId\":61,\"name\":\"提现参数\",\"url\":\"modules/happytrip/withdrawConfig.html?configType\\u003dwithdraw\",\"perms\":\"sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete\",\"type\":1,\"icon\":\"larry-faxianhover\",\"spread\":0,\"orderNum\":1,\"map\":{\"parentName\":\"参数管理\"}}',269,'0:0:0:0:0:0:0:1',1,'2019-01-22 10:22:41'),(107,'admin','修改菜单','io.frame.modules.sys.controller.SysMenuController.update()','{\"menuId\":85,\"parentId\":61,\"name\":\"系统参数\",\"url\":\"modules/happytrip/systemConfig.html?configType\\u003dsystem\",\"perms\":\"sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete\",\"type\":1,\"icon\":\"larry-system-extension\",\"spread\":0,\"orderNum\":2,\"map\":{\"parentName\":\"参数管理\"}}',1482,'0:0:0:0:0:0:0:1',1,'2019-01-22 10:23:15'),(108,'admin','保存菜单','io.frame.modules.sys.controller.SysMenuController.save()','{\"parentId\":61,\"name\":\"福利开关\",\"url\":\"modules/happytrip/systemConfig.html?configType\\u003dwelfare\",\"perms\":\"sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete\",\"type\":1,\"icon\":\"larry-anquanguanli\",\"orderNum\":3,\"map\":{\"parentName\":\"参数管理\"}}',2279,'0:0:0:0:0:0:0:1',1,'2019-01-22 10:27:16'),(109,'admin','修改配置','io.frame.modules.sys.controller.SysConfigController.update()','{\"configId\":19,\"configKey\":\"RECHARGE_QRCODE_KEY\",\"configVal\":\"http://localhost:8082/admin/readImg?path\\u003d/home/images/20190122113249.jpg\",\"configName\":\"充值收款二维码\",\"configType\":\"recharge\",\"configStatus\":1,\"createTime\":\"Jan 7, 2019 5:50:00 PM\",\"createUser\":\"fury\",\"remark\":\"充值二维码收款图片\"}',604,'0:0:0:0:0:0:0:1',1,'2019-01-22 11:32:57'),(110,'admin','修改配置','io.frame.modules.sys.controller.SysConfigController.update()','{\"configId\":19,\"configKey\":\"RECHARGE_QRCODE_KEY\",\"configVal\":\"http://localhost:8082/admin/readImg?path\\u003d/home/images/20190122113249.jpg\",\"configName\":\"充值收款二维码\",\"configType\":\"recharge\",\"configStatus\":1,\"createTime\":\"Jan 7, 2019 5:50:00 PM\",\"createUser\":\"fury\",\"remark\":\"充值二维码收款图片\"}',142,'0:0:0:0:0:0:0:1',1,'2019-01-22 11:32:58'),(111,'admin','修改配置','io.frame.modules.sys.controller.SysConfigController.update()','{\"configId\":19,\"configKey\":\"RECHARGE_QRCODE_KEY\",\"configVal\":\"/home/images/20190122113633.jpg\",\"configName\":\"充值收款二维码\",\"configType\":\"recharge\",\"configStatus\":1,\"createTime\":\"Jan 7, 2019 5:50:00 PM\",\"createUser\":\"fury\",\"remark\":\"充值二维码收款图片\"}',258,'0:0:0:0:0:0:0:1',1,'2019-01-22 11:36:35'),(112,'admin','修改配置','io.frame.modules.sys.controller.SysConfigController.update()','{\"configId\":20,\"configKey\":\"SYSTEM_CUSTOMER_SERVICE_IMG_KEY\",\"configVal\":\"\",\"configName\":\"客服二维码图片\",\"configType\":\"system\",\"configStatus\":1,\"createTime\":\"Jan 7, 2019 5:50:00 PM\",\"createUser\":\"fury\",\"remark\":\"客服二维码图片\"}',55,'0:0:0:0:0:0:0:1',1,'2019-01-22 11:41:05'),(113,'admin','修改配置','io.frame.modules.sys.controller.SysConfigController.update()','{\"configId\":7,\"configKey\":\"RECHARGE_TIME_RANGE_KEY\",\"configVal\":\"-1\",\"configName\":\"充值时间段\",\"configType\":\"recharge\",\"configStatus\":1,\"createTime\":\"Jan 7, 2019 5:50:00 PM\",\"createUser\":\"fury\",\"remark\":\"充值时间段,-1不限制[00:00,23:59]\"}',44,'0:0:0:0:0:0:0:1',1,'2019-01-22 13:06:00'),(114,'admin','修改配置','io.frame.modules.sys.controller.SysConfigController.update()','{\"configId\":7,\"configKey\":\"RECHARGE_TIME_RANGE_KEY\",\"configVal\":\"-1\",\"configName\":\"充值时间段\",\"configType\":\"recharge\",\"configStatus\":1,\"createTime\":\"Jan 7, 2019 5:50:00 PM\",\"createUser\":\"fury\",\"remark\":\"充值时间段,-1不限制[00:00,23:59]\"}',176,'0:0:0:0:0:0:0:1',1,'2019-01-22 13:06:08'),(115,'admin','修改配置','io.frame.modules.sys.controller.SysConfigController.update()','{\"configId\":19,\"configKey\":\"RECHARGE_QRCODE_KEY\",\"configVal\":\"\",\"configName\":\"充值收款二维码\",\"configType\":\"recharge\",\"configStatus\":1,\"createTime\":\"Jan 7, 2019 5:50:00 PM\",\"createUser\":\"fury\",\"remark\":\"充值二维码收款图片\"}',10097,'0:0:0:0:0:0:0:1',1,'2019-01-22 13:11:59'),(116,'admin','修改配置','io.frame.modules.sys.controller.SysConfigController.update()','{\"configId\":21,\"configKey\":\"SYSTEM_COMPANY_INTRODUCE_KEY\",\"configVal\":\"{}asda\",\"configName\":\"公司介绍\",\"configType\":\"system\",\"configStatus\":1,\"createTime\":\"Jan 7, 2019 5:50:00 PM\",\"createUser\":\"fury\",\"remark\":\"公司介绍\"}',7792,'0:0:0:0:0:0:0:1',1,'2019-01-22 13:30:53'),(117,'admin','修改配置','io.frame.modules.sys.controller.SysConfigController.update()','{\"configId\":13,\"configKey\":\"CLOUD_STORAGE_CONFIG_KEY\",\"configVal\":\"{\\\"type\\\":1,\\\"qiniuDomain\\\":\\\"http://7xlij2.com1.z0.glb.clouddn.com\\\",\\\"qiniuPrefix\\\":\\\"upload\\\",\\\"qiniuAccessKey\\\":\\\"NrgMfABZxWLo5B-YYSjoE8-AZ1EISdi1Z3ubLOeZ\\\",\\\"qiniuSecretKey\\\":\\\"uIwJHevMRWU0VLxFvgy0tAcOdGqasdtVlJk\",\"configName\":\"云存储配置\",\"configType\":\"system\",\"configStatus\":1,\"createTime\":\"Jan 7, 2019 5:50:00 PM\",\"createUser\":\"fury\",\"remark\":\"云存储配置信息,保存json格式\"}',44,'0:0:0:0:0:0:0:1',1,'2019-01-22 13:41:24'),(118,'admin','修改配置','io.frame.modules.sys.controller.SysConfigController.update()','{\"configId\":25,\"configKey\":\"SYSTEM_REGISTER_DOMAIN_KEY\",\"configVal\":\"http://zzzx\",\"configName\":\"注册域名\",\"configType\":\"system\",\"configStatus\":1,\"createTime\":\"Jan 7, 2019 5:50:00 PM\",\"createUser\":\"fury\",\"remark\":\"注册域名\"}',41,'0:0:0:0:0:0:0:1',1,'2019-01-22 13:42:40'),(119,'admin','修改配置','io.frame.modules.sys.controller.SysConfigController.update()','{\"configId\":21,\"configKey\":\"SYSTEM_COMPANY_INTRODUCE_KEY\",\"configVal\":\"BBD\\u0015\\u0012DFAM\\u0010\\u0016FGGC\\u0015FBM\\u0010\\u0011FM\\u0011@\\u0011EF\",\"configName\":\"公司介绍\",\"configType\":\"system\",\"configStatus\":1,\"createTime\":\"Jan 7, 2019 5:50:00 PM\",\"createUser\":\"fury\",\"updateTime\":\"Jan 22, 2019 2:17:35 PM\",\"updateUser\":\"admin\",\"remark\":\"公司介绍\"}',877,'0:0:0:0:0:0:0:1',1,'2019-01-22 14:17:36'),(120,'admin','修改配置','io.frame.modules.sys.controller.SysConfigController.update()','{\"configId\":21,\"configKey\":\"SYSTEM_COMPANY_INTRODUCE_KEY\",\"configVal\":\"FGDB\\u0016A@G@\\u0011\\u0015\\u0015@\\u0011BA\\u0010AMG@G\\u0015\\u0015G@\\u0010EC@\\u0010B\",\"configName\":\"公司介绍\",\"configType\":\"system\",\"configStatus\":1,\"createTime\":\"Jan 7, 2019 5:50:00 PM\",\"createUser\":\"fury\",\"updateTime\":\"Jan 22, 2019 2:19:29 PM\",\"updateUser\":\"admin\",\"remark\":\"公司介绍\"}',153072,'0:0:0:0:0:0:0:1',1,'2019-01-22 14:22:03'),(121,'admin','修改配置','io.frame.modules.sys.controller.SysConfigController.updateaa()',NULL,58,'0:0:0:0:0:0:0:1',1,'2019-01-22 14:59:09'),(122,'admin','修改配置','io.frame.modules.sys.controller.SysConfigController.updateaa()',NULL,57,'0:0:0:0:0:0:0:1',1,'2019-01-22 14:59:24'),(123,'admin','修改配置','io.frame.modules.sys.controller.SysConfigController.updateaa()',NULL,58,'0:0:0:0:0:0:0:1',1,'2019-01-22 14:59:51'),(124,'admin','修改配置','io.frame.modules.sys.controller.SysConfigController.update()','{\"configId\":20,\"configKey\":\"SYSTEM_CUSTOMER_SERVICE_IMG_KEY\",\"configVal\":\"/home/images/20190122151113.jpg\",\"configName\":\"客服二维码图片\",\"configType\":\"system\",\"configStatus\":1,\"createTime\":\"Jan 7, 2019 5:50:00 PM\",\"createUser\":\"fury\",\"updateTime\":\"Jan 22, 2019 3:11:14 PM\",\"updateUser\":\"admin\",\"remark\":\"客服二维码图片\"}',85,'0:0:0:0:0:0:0:1',1,'2019-01-22 15:11:15'),(125,'admin','修改配置','io.frame.modules.sys.controller.SysConfigController.update()','{\"configId\":19,\"configKey\":\"RECHARGE_QRCODE_KEY\",\"configVal\":\"/home/images/20190122151158.jpg\",\"configName\":\"充值收款二维码\",\"configType\":\"recharge\",\"configStatus\":1,\"createTime\":\"Jan 7, 2019 5:50:00 PM\",\"createUser\":\"fury\",\"updateTime\":\"Jan 22, 2019 3:11:59 PM\",\"updateUser\":\"admin\",\"remark\":\"充值二维码收款图片\"}',47,'0:0:0:0:0:0:0:1',1,'2019-01-22 15:12:00'),(126,'admin','修改状态','io.frame.modules.sys.controller.SysConfigController.status()','19|0|',40,'0:0:0:0:0:0:0:1',1,'2019-01-22 15:12:10'),(127,'admin','修改状态','io.frame.modules.sys.controller.SysConfigController.status()','19|1|',40,'0:0:0:0:0:0:0:1',1,'2019-01-22 15:12:12'),(128,'admin','修改配置','io.frame.modules.sys.controller.SysConfigController.updateaa()',NULL,47,'0:0:0:0:0:0:0:1',1,'2019-01-22 15:14:29'),(129,'admin','修改状态','io.frame.modules.sys.controller.SysConfigController.status()','16|0|',45,'0:0:0:0:0:0:0:1',1,'2019-01-22 15:21:19'),(130,'admin','修改状态','io.frame.modules.sys.controller.SysConfigController.status()','16|1|',64,'0:0:0:0:0:0:0:1',1,'2019-01-22 15:21:25'),(131,'admin','修改福利状态','io.frame.modules.happytrip.controller.WelfareController.status()','2|1|',46,'0:0:0:0:0:0:0:1',1,'2019-01-22 15:21:37'),(132,'admin','修改公司介绍','io.frame.modules.sys.controller.SysConfigController.updateCompany()',NULL,58,'0:0:0:0:0:0:0:1',1,'2019-01-22 15:38:31'),(133,'admin','修改公司介绍','io.frame.modules.sys.controller.SysConfigController.updateCompany()',NULL,63,'0:0:0:0:0:0:0:1',1,'2019-01-22 15:38:55'),(134,'admin','修改菜单','io.frame.modules.sys.controller.SysMenuController.update()','{\"menuId\":60,\"parentId\":53,\"name\":\"帐变管理\",\"url\":\"modules/happytrip/walletChange.html\",\"perms\":\"ht:walletChange:list\",\"type\":1,\"icon\":\"larry-log\",\"spread\":0,\"orderNum\":8,\"map\":{\"parentName\":\"Go系统管理\"}}',111,'0:0:0:0:0:0:0:1',1,'2019-01-22 17:05:40'),(135,'mark','登录接口','io.frame.service.impl.UserServiceImpl.login()','{\"userMobile\":\"18888888888\",\"userPass\":\"8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918\"}',32,'116.30.194.132',0,'2019-01-22 20:21:07'),(136,'admin','保存菜单','io.frame.modules.sys.controller.SysMenuController.save()','{\"parentId\":53,\"name\":\"报表统计\",\"url\":\"modules/happytrip/report.html\",\"perms\":\"ht:report:list,ht:report:info,ht:report:update,ht:report:delete,ht:report:save\",\"type\":1,\"icon\":\"larry-shujutongji2\",\"orderNum\":0,\"map\":{\"parentName\":\"Go系统管理\"}}',134,'0:0:0:0:0:0:0:1',1,'2019-01-22 21:02:13'),(137,'admin','修改菜单','io.frame.modules.sys.controller.SysMenuController.update()','{\"menuId\":87,\"parentId\":53,\"name\":\"报表统计\",\"url\":\"modules/happytrip/report.html\",\"perms\":\"ht:report:list,ht:report:info,ht:report:update,ht:report:delete,ht:report:save\",\"type\":1,\"icon\":\"larry-shujutongji2\",\"spread\":0,\"orderNum\":7,\"map\":{\"parentName\":\"Go系统管理\"}}',91,'0:0:0:0:0:0:0:1',1,'2019-01-22 21:02:49'),(138,'admin','修改菜单','io.frame.modules.sys.controller.SysMenuController.update()','{\"menuId\":62,\"parentId\":53,\"name\":\"文件上传\",\"url\":\"modules/sys/oss.html\",\"perms\":\"sys:oss:all\",\"type\":1,\"icon\":\"larry-friendLink\",\"spread\":0,\"orderNum\":10,\"map\":{\"parentName\":\"Go系统管理\"}}',116,'0:0:0:0:0:0:0:1',1,'2019-01-22 21:03:47'),(139,'mark','登录接口','io.frame.service.impl.UserServiceImpl.login()','{\"userMobile\":\"18888888888\",\"userPass\":\"8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918\"}',9,'116.30.194.132',0,'2019-01-22 21:21:18'),(140,'mark','登录接口','io.frame.service.impl.UserServiceImpl.login()','{\"userMobile\":\"18888888888\",\"userPass\":\"8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918\"}',6,'116.30.194.132',0,'2019-01-22 21:22:17'),(141,'mark','登录接口','io.frame.service.impl.UserServiceImpl.login()','{\"userMobile\":\"18888888888\",\"userPass\":\"8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918\"}',24,'116.30.194.132',0,'2019-01-22 22:05:46'),(142,'mark','登录接口','io.frame.service.impl.UserServiceImpl.login()','{\"userMobile\":\"18888888888\",\"userPass\":\"8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918\"}',11,'116.30.194.132',0,'2019-01-22 22:06:22'),(143,'mark','登录接口','io.frame.service.impl.UserServiceImpl.login()','{\"userMobile\":\"18888888888\",\"userPass\":\"8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918\"}',9,'116.30.194.132',0,'2019-01-22 22:20:38'),(144,'mark','登录接口','io.frame.service.impl.UserServiceImpl.login()','{\"userMobile\":\"18888888888\",\"userPass\":\"8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918\"}',12,'116.30.194.132',0,'2019-01-22 22:21:22'),(145,'mark','登录接口','io.frame.service.impl.UserServiceImpl.login()','{\"userMobile\":\"18888888888\",\"userPass\":\"8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918\"}',8,'116.30.194.132',0,'2019-01-22 22:24:30'),(146,'admin','登录接口','io.frame.service.impl.UserServiceImpl.login()','{\"userMobile\":\"18888888888\",\"userPass\":\"8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918\"}',5,'116.30.194.132',0,'2019-01-22 22:32:09'),(147,'admin','登录接口','io.frame.service.impl.UserServiceImpl.login()','{\"userMobile\":\"18888888888\",\"userPass\":\"8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918\"}',17,'116.30.194.132',0,'2019-01-22 22:33:12'),(148,'admin','登录接口','io.frame.service.impl.UserServiceImpl.login()','{\"userMobile\":\"18888888888\",\"userPass\":\"8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918\"}',4,'116.30.194.132',0,'2019-01-22 22:44:34'),(149,'admin','保存菜单','io.frame.modules.sys.controller.SysMenuController.save()','{\"parentId\":53,\"name\":\"支付接口\",\"url\":\"modules/happytrip/payment.html\",\"perms\":\"ht:payment:list,ht:payment:save,ht:payment:update,ht:payment:info,ht:payment:delete\",\"type\":1,\"icon\":\"larry-fold2\",\"orderNum\":8,\"map\":{\"parentName\":\"Go系统管理\"}}',92,'0:0:0:0:0:0:0:1',1,'2019-01-22 23:35:28'),(150,'admin','修改支付状态','io.frame.modules.happytrip.controller.PaymentController.status()','1|0|',49,'0:0:0:0:0:0:0:1',1,'2019-01-22 23:36:17'),(151,'admin','修改支付','io.frame.modules.happytrip.controller.PaymentController.update()','{\"paymentId\":1,\"paymentKey\":\"PAYMENT_ALIPAY_KEY\",\"paymentName\":\"支付宝\",\"status\":1,\"sort\":2,\"updateTime\":\"Jan 22, 2019 11:39:25 PM\",\"updateUser\":\"admin\"}',43,'0:0:0:0:0:0:0:1',1,'2019-01-22 23:39:26'),(152,'admin','修改支付','io.frame.modules.happytrip.controller.PaymentController.update()','{\"paymentId\":1,\"paymentKey\":\"PAYMENT_ALIPAY_KEY\",\"paymentName\":\"支付宝\",\"status\":0,\"sort\":2,\"updateTime\":\"Jan 22, 2019 11:44:54 PM\",\"updateUser\":\"admin\"}',66,'0:0:0:0:0:0:0:1',1,'2019-01-22 23:44:54'),(153,'admin','修改支付状态','io.frame.modules.happytrip.controller.PaymentController.status()','1|1|',67,'0:0:0:0:0:0:0:1',1,'2019-01-22 23:44:57'),(154,'admin','修改菜单','io.frame.modules.sys.controller.SysMenuController.update()','{\"menuId\":63,\"parentId\":53,\"name\":\"定时任务\",\"url\":\"modules/job/schedule.html\",\"type\":0,\"icon\":\"larry-renwu2\",\"spread\":0,\"orderNum\":9,\"map\":{\"parentName\":\"Go系统管理\"}}',75,'0:0:0:0:0:0:0:1',1,'2019-01-22 23:47:34'),(155,'admin','保存菜单','io.frame.modules.sys.controller.SysMenuController.save()','{\"parentId\":63,\"name\":\"任务列表\",\"url\":\"modules/job/schedule.html\",\"perms\":\"sys:schedule:list,sys:schedule:info,sys:schedule:save,sys:schedule:update,sys:schedule:delete,sys:schedule:pause,sys:schedule:resume,sys:schedule:run\",\"type\":1,\"icon\":\"larry-renwu\",\"orderNum\":0,\"map\":{\"parentName\":\"定时任务\"}}',122,'0:0:0:0:0:0:0:1',1,'2019-01-22 23:49:07'),(156,'admin','保存菜单','io.frame.modules.sys.controller.SysMenuController.save()','{\"parentId\":63,\"name\":\"任务日志\",\"url\":\"modules/happytrip/schedulelog.html\",\"perms\":\"sys:schedulelog:list,sys:schedulelog:info,sys:schedulelog:save,sys:schedulelog:update,sys:schedulelog:delete,sys:schedulelog:pause,sys:schedulelog:resume,sys:schedulelog:run\",\"type\":1,\"icon\":\"larry-caozuorizhi\",\"orderNum\":1,\"map\":{\"parentName\":\"定时任务\"}}',114,'0:0:0:0:0:0:0:1',1,'2019-01-22 23:52:01'),(157,'admin','修改菜单','io.frame.modules.sys.controller.SysMenuController.update()','{\"menuId\":63,\"parentId\":53,\"name\":\"定时任务\",\"url\":\"modules/job/schedule.html\",\"type\":0,\"icon\":\"larry-xitong10\",\"spread\":0,\"orderNum\":9,\"map\":{\"parentName\":\"Go系统管理\"}}',82,'0:0:0:0:0:0:0:1',1,'2019-01-22 23:52:56'),(158,'admin','保存菜单','io.frame.modules.sys.controller.SysMenuController.save()','{\"parentId\":59,\"name\":\"推荐记录\",\"url\":\"modules/happytrip/recommend.html\",\"perms\":\"ht:recommend:list,ht:recommend:info,ht:recommend:save,ht:recommend:update,ht:recommend:delete\",\"type\":1,\"icon\":\"larry-gerenxinxi\",\"orderNum\":11,\"map\":{\"parentName\":\"用户管理\"}}',143,'0:0:0:0:0:0:0:1',1,'2019-01-22 23:55:35'),(159,'admin','修改推荐','io.frame.modules.happytrip.controller.RecommendController.update()','{\"recommendId\":8,\"userId\":1,\"parentId\":1,\"groupUserIds\":\"0,1,\",\"recommendNumber\":0,\"teamAchievement\":30000,\"createUser\":\"admin\",\"updateTime\":\"Jan 23, 2019 12:27:12 AM\",\"updateUser\":\"admin\"}',38,'0:0:0:0:0:0:0:1',1,'2019-01-23 00:27:13'),(160,'admin','修改推荐','io.frame.modules.happytrip.controller.RecommendController.update()','{\"recommendId\":8,\"userId\":1,\"userName\":\"admin\",\"parentId\":0,\"groupUserIds\":\"0,1,\",\"recommendNumber\":1,\"teamAchievement\":30000,\"createUser\":\"admin\",\"updateTime\":\"Jan 23, 2019 12:28:27 AM\",\"updateUser\":\"admin\"}',43,'0:0:0:0:0:0:0:1',1,'2019-01-23 00:28:27'),(161,'admin','修改推荐','io.frame.modules.happytrip.controller.RecommendController.update()','{\"recommendId\":8,\"userId\":1,\"userName\":\"admin\",\"parentId\":0,\"groupUserIds\":\"0,1,\",\"recommendNumber\":12,\"teamAchievement\":30000,\"createUser\":\"admin\",\"updateTime\":\"Jan 23, 2019 12:34:47 AM\",\"updateUser\":\"admin\"}',56,'0:0:0:0:0:0:0:1',1,'2019-01-23 00:34:47');

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

insert  into `s_payment`(`paymentId`,`paymentKey`,`paymentName`,`status`,`sort`,`createTime`,`createUser`,`updateTime`,`updateUser`) values (1,'PAYMENT_ALIPAY_KEY','支付宝',1,2,NULL,NULL,'2019-01-22 23:44:57','admin'),(2,'PAYMENT_WALLET_KEY','余额',1,2,NULL,NULL,NULL,NULL);

/*Table structure for table `s_schedule_job` */

DROP TABLE IF EXISTS `s_schedule_job`;

CREATE TABLE `s_schedule_job` (
  `jobId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `beanName` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `methodName` varchar(100) DEFAULT NULL COMMENT '方法名',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `cronExpression` varchar(100) DEFAULT NULL COMMENT 'cron表达式',
  `status` int(1) DEFAULT NULL COMMENT '任务状态  0：正常  1：暂停',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`jobId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='定时任务';

/*Data for the table `s_schedule_job` */

insert  into `s_schedule_job`(`jobId`,`beanName`,`methodName`,`params`,`cronExpression`,`status`,`remark`,`createTime`) values (1,'testTask','test','frame','0 0/3 * * * ?',0,'有参数测试','2016-12-01 23:16:46'),(2,'testTask','test2',NULL,'0 0/30 * * * ?',1,'无参数测试','2016-12-03 14:55:56');

/*Table structure for table `s_schedule_job_log` */

DROP TABLE IF EXISTS `s_schedule_job_log`;

CREATE TABLE `s_schedule_job_log` (
  `logId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志id',
  `jobId` bigint(20) NOT NULL COMMENT '任务id',
  `beanName` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `methodName` varchar(100) DEFAULT NULL COMMENT '方法名',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `status` int(4) NOT NULL COMMENT '任务状态    0：成功    1：失败',
  `error` varchar(2000) DEFAULT NULL COMMENT '失败信息',
  `times` int(11) NOT NULL COMMENT '耗时(单位：毫秒)',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`logId`),
  KEY `job_id` (`jobId`)
) ENGINE=InnoDB AUTO_INCREMENT=706 DEFAULT CHARSET=utf8 COMMENT='定时任务日志';

/*Data for the table `s_schedule_job_log` */

insert  into `s_schedule_job_log`(`logId`,`jobId`,`beanName`,`methodName`,`params`,`status`,`error`,`times`,`createTime`) values (1,1,'testTask','test','frame',0,NULL,6905,'2019-01-15 11:00:44'),(2,1,'testTask','test','frame',0,NULL,1188,'2019-01-15 21:42:00'),(3,1,'testTask','test','frame',0,NULL,1053,'2019-01-15 21:45:00'),(4,1,'testTask','test','frame',0,NULL,1106,'2019-01-15 21:48:00'),(5,1,'testTask','test','frame',0,NULL,1091,'2019-01-15 21:51:00'),(6,1,'testTask','test','frame',0,NULL,1081,'2019-01-15 21:54:00'),(7,1,'testTask','test','frame',0,NULL,1051,'2019-01-15 21:57:00'),(8,1,'testTask','test','frame',0,NULL,1070,'2019-01-15 22:00:00'),(9,1,'testTask','test','frame',0,NULL,1075,'2019-01-15 22:03:00'),(10,1,'testTask','test','frame',0,NULL,1099,'2019-01-15 22:06:00'),(11,1,'testTask','test','frame',0,NULL,1048,'2019-01-15 22:09:00'),(12,1,'testTask','test','frame',0,NULL,1099,'2019-01-15 22:12:00'),(13,1,'testTask','test','frame',0,NULL,1064,'2019-01-15 22:15:00'),(14,1,'testTask','test','frame',0,NULL,1076,'2019-01-15 22:18:00'),(15,1,'testTask','test','frame',0,NULL,1071,'2019-01-15 22:21:00'),(16,1,'testTask','test','frame',0,NULL,1055,'2019-01-15 22:24:00'),(17,1,'testTask','test','frame',0,NULL,1050,'2019-01-15 22:27:00'),(18,1,'testTask','test','frame',0,NULL,1060,'2019-01-15 22:30:00'),(19,1,'testTask','test','frame',0,NULL,1043,'2019-01-15 22:33:00'),(20,1,'testTask','test','frame',0,NULL,1031,'2019-01-15 22:36:00'),(21,1,'testTask','test','frame',0,NULL,1066,'2019-01-15 22:39:00'),(22,1,'testTask','test','frame',0,NULL,1048,'2019-01-15 22:42:00'),(23,1,'testTask','test','frame',0,NULL,1037,'2019-01-15 22:45:00'),(24,1,'testTask','test','frame',0,NULL,1047,'2019-01-15 22:48:00'),(25,1,'testTask','test','frame',0,NULL,1059,'2019-01-15 22:51:00'),(26,1,'testTask','test','frame',0,NULL,1043,'2019-01-15 22:54:00'),(27,1,'testTask','test','frame',0,NULL,1072,'2019-01-15 22:57:00'),(28,1,'testTask','test','frame',0,NULL,1037,'2019-01-15 23:00:00'),(29,1,'testTask','test','frame',0,NULL,1081,'2019-01-15 23:06:00'),(30,1,'testTask','test','frame',0,NULL,1069,'2019-01-15 23:21:00'),(31,1,'testTask','test','frame',0,NULL,1030,'2019-01-15 23:24:00'),(32,1,'testTask','test','frame',0,NULL,1059,'2019-01-15 23:27:00'),(33,1,'testTask','test','frame',0,NULL,1039,'2019-01-15 23:30:00'),(34,1,'testTask','test','frame',0,NULL,1068,'2019-01-15 23:33:00'),(35,1,'testTask','test','frame',0,NULL,1041,'2019-01-15 23:36:00'),(36,1,'testTask','test','frame',0,NULL,1059,'2019-01-15 23:39:00'),(37,1,'testTask','test','frame',0,NULL,1049,'2019-01-15 23:42:00'),(38,1,'testTask','test','frame',0,NULL,1071,'2019-01-15 23:45:00'),(39,1,'testTask','test','frame',0,NULL,1053,'2019-01-15 23:48:01'),(40,1,'testTask','test','frame',0,NULL,1096,'2019-01-16 00:00:00'),(41,1,'testTask','test','frame',0,NULL,1102,'2019-01-16 00:03:00'),(42,1,'testTask','test','frame',0,NULL,1056,'2019-01-16 08:18:00'),(43,1,'testTask','test','frame',0,NULL,1087,'2019-01-16 08:24:00'),(44,1,'testTask','test','frame',0,NULL,1102,'2019-01-16 08:27:00'),(45,1,'testTask','test','frame',0,NULL,1101,'2019-01-16 08:30:00'),(46,1,'testTask','test','frame',0,NULL,1078,'2019-01-16 08:33:00'),(47,1,'testTask','test','frame',0,NULL,1078,'2019-01-16 08:36:00'),(48,1,'testTask','test','frame',0,NULL,2076,'2019-01-16 08:39:00'),(49,1,'testTask','test','frame',0,NULL,1184,'2019-01-16 08:42:00'),(50,1,'testTask','test','frame',0,NULL,1109,'2019-01-16 08:45:00'),(51,1,'testTask','test','frame',0,NULL,1068,'2019-01-16 08:48:00'),(52,1,'testTask','test','frame',0,NULL,1083,'2019-01-16 08:51:00'),(53,1,'testTask','test','frame',0,NULL,1082,'2019-01-16 08:54:00'),(54,1,'testTask','test','frame',0,NULL,1060,'2019-01-16 08:57:00'),(55,1,'testTask','test','frame',0,NULL,1085,'2019-01-16 09:00:00'),(56,1,'testTask','test','frame',0,NULL,1091,'2019-01-16 09:03:00'),(57,1,'testTask','test','frame',0,NULL,1048,'2019-01-16 09:06:00'),(58,1,'testTask','test','frame',0,NULL,1069,'2019-01-16 09:09:00'),(59,1,'testTask','test','frame',0,NULL,1080,'2019-01-16 09:12:00'),(60,1,'testTask','test','frame',0,NULL,1075,'2019-01-16 09:15:00'),(61,1,'testTask','test','frame',0,NULL,1069,'2019-01-16 09:18:00'),(62,1,'testTask','test','frame',0,NULL,1056,'2019-01-16 09:21:00'),(63,1,'testTask','test','frame',0,NULL,1053,'2019-01-16 09:24:00'),(64,1,'testTask','test','frame',0,NULL,1045,'2019-01-16 09:27:00'),(65,1,'testTask','test','frame',0,NULL,1053,'2019-01-16 09:30:00'),(66,1,'testTask','test','frame',0,NULL,1056,'2019-01-16 09:33:00'),(67,1,'testTask','test','frame',0,NULL,1075,'2019-01-16 09:36:00'),(68,1,'testTask','test','frame',0,NULL,1089,'2019-01-16 09:39:00'),(69,1,'testTask','test','frame',0,NULL,1042,'2019-01-16 09:42:00'),(70,1,'testTask','test','frame',0,NULL,1046,'2019-01-16 09:45:00'),(71,1,'testTask','test','frame',0,NULL,1075,'2019-01-16 09:48:00'),(72,1,'testTask','test','frame',0,NULL,1050,'2019-01-16 09:51:00'),(73,1,'testTask','test','frame',0,NULL,1060,'2019-01-16 09:54:00'),(74,1,'testTask','test','frame',0,NULL,1064,'2019-01-16 09:57:00'),(75,1,'testTask','test','frame',0,NULL,1056,'2019-01-16 10:00:00'),(76,1,'testTask','test','frame',0,NULL,1052,'2019-01-16 10:03:00'),(77,1,'testTask','test','frame',0,NULL,1077,'2019-01-16 10:06:00'),(78,1,'testTask','test','frame',0,NULL,1067,'2019-01-16 10:09:00'),(79,1,'testTask','test','frame',0,NULL,1075,'2019-01-16 10:12:00'),(80,1,'testTask','test','frame',0,NULL,1068,'2019-01-16 10:15:00'),(81,1,'testTask','test','frame',0,NULL,1072,'2019-01-16 10:18:00'),(82,1,'testTask','test','frame',0,NULL,1075,'2019-01-16 10:21:00'),(83,1,'testTask','test','frame',0,NULL,1070,'2019-01-16 10:24:00'),(84,1,'testTask','test','frame',0,NULL,1097,'2019-01-16 10:27:00'),(85,1,'testTask','test','frame',0,NULL,1069,'2019-01-16 10:30:00'),(86,1,'testTask','test','frame',0,NULL,1090,'2019-01-16 10:33:00'),(87,1,'testTask','test','frame',0,NULL,1065,'2019-01-16 10:36:00'),(88,1,'testTask','test','frame',0,NULL,1064,'2019-01-16 10:39:00'),(89,1,'testTask','test','frame',0,NULL,1074,'2019-01-16 10:42:00'),(90,1,'testTask','test','frame',0,NULL,1073,'2019-01-16 10:45:00'),(91,1,'testTask','test','frame',0,NULL,1064,'2019-01-16 10:48:00'),(92,1,'testTask','test','frame',0,NULL,1070,'2019-01-16 10:51:00'),(93,1,'testTask','test','frame',0,NULL,1068,'2019-01-16 10:54:00'),(94,1,'testTask','test','frame',0,NULL,1031,'2019-01-16 10:57:00'),(95,1,'testTask','test','frame',0,NULL,1047,'2019-01-16 11:00:00'),(96,1,'testTask','test','frame',0,NULL,1044,'2019-01-16 11:03:00'),(97,1,'testTask','test','frame',0,NULL,1042,'2019-01-16 11:06:00'),(98,1,'testTask','test','frame',0,NULL,1059,'2019-01-16 11:09:00'),(99,1,'testTask','test','frame',0,NULL,1061,'2019-01-16 11:12:00'),(100,1,'testTask','test','frame',0,NULL,1068,'2019-01-16 11:15:00'),(101,1,'testTask','test','frame',0,NULL,1075,'2019-01-16 11:18:00'),(102,1,'testTask','test','frame',0,NULL,1051,'2019-01-16 11:21:00'),(103,1,'testTask','test','frame',0,NULL,1055,'2019-01-16 11:24:00'),(104,1,'testTask','test','frame',0,NULL,1048,'2019-01-16 11:27:00'),(105,1,'testTask','test','frame',0,NULL,1045,'2019-01-16 11:30:00'),(106,1,'testTask','test','frame',0,NULL,1065,'2019-01-16 11:33:00'),(107,1,'testTask','test','frame',0,NULL,1084,'2019-01-16 11:36:00'),(108,1,'testTask','test','frame',0,NULL,1048,'2019-01-16 11:39:00'),(109,1,'testTask','test','frame',0,NULL,1131,'2019-01-16 11:42:02'),(110,1,'testTask','test','frame',0,NULL,1040,'2019-01-16 11:45:00'),(111,1,'testTask','test','frame',0,NULL,1049,'2019-01-16 11:48:00'),(112,1,'testTask','test','frame',0,NULL,1050,'2019-01-16 11:51:00'),(113,1,'testTask','test','frame',0,NULL,1043,'2019-01-16 11:54:00'),(114,1,'testTask','test','frame',0,NULL,1139,'2019-01-16 11:57:00'),(115,1,'testTask','test','frame',0,NULL,1083,'2019-01-16 12:00:00'),(116,1,'testTask','test','frame',0,NULL,1084,'2019-01-16 12:03:00'),(117,1,'testTask','test','frame',0,NULL,1060,'2019-01-16 12:06:00'),(118,1,'testTask','test','frame',0,NULL,1055,'2019-01-16 12:09:00'),(119,1,'testTask','test','frame',0,NULL,1047,'2019-01-16 12:12:00'),(120,1,'testTask','test','frame',0,NULL,1064,'2019-01-16 12:15:00'),(121,1,'testTask','test','frame',0,NULL,1036,'2019-01-16 12:18:00'),(122,1,'testTask','test','frame',0,NULL,1078,'2019-01-16 12:21:00'),(123,1,'testTask','test','frame',0,NULL,1078,'2019-01-16 12:24:00'),(124,1,'testTask','test','frame',0,NULL,1080,'2019-01-16 12:27:00'),(125,1,'testTask','test','frame',0,NULL,1096,'2019-01-16 12:30:00'),(126,1,'testTask','test','frame',0,NULL,1064,'2019-01-16 12:33:00'),(127,1,'testTask','test','frame',0,NULL,1069,'2019-01-16 12:36:00'),(128,1,'testTask','test','frame',0,NULL,1078,'2019-01-16 12:39:00'),(129,1,'testTask','test','frame',0,NULL,1064,'2019-01-16 12:42:00'),(130,1,'testTask','test','frame',0,NULL,1080,'2019-01-16 12:45:00'),(131,1,'testTask','test','frame',0,NULL,1082,'2019-01-16 12:48:00'),(132,1,'testTask','test','frame',0,NULL,1137,'2019-01-16 12:51:00'),(133,1,'testTask','test','frame',0,NULL,1069,'2019-01-16 12:54:00'),(134,1,'testTask','test','frame',0,NULL,1071,'2019-01-16 12:57:00'),(135,1,'testTask','test','frame',0,NULL,1069,'2019-01-16 13:00:00'),(136,1,'testTask','test','frame',0,NULL,1077,'2019-01-16 13:03:00'),(137,1,'testTask','test','frame',0,NULL,1080,'2019-01-16 13:06:00'),(138,1,'testTask','test','frame',0,NULL,1057,'2019-01-16 13:09:00'),(139,1,'testTask','test','frame',0,NULL,1095,'2019-01-16 13:12:00'),(140,1,'testTask','test','frame',0,NULL,1065,'2019-01-16 13:15:00'),(141,1,'testTask','test','frame',0,NULL,1043,'2019-01-16 13:18:00'),(142,1,'testTask','test','frame',0,NULL,1077,'2019-01-16 13:21:00'),(143,1,'testTask','test','frame',0,NULL,1058,'2019-01-16 13:24:00'),(144,1,'testTask','test','frame',0,NULL,1083,'2019-01-16 13:27:00'),(145,1,'testTask','test','frame',0,NULL,1054,'2019-01-16 13:30:00'),(146,1,'testTask','test','frame',0,NULL,1049,'2019-01-16 13:33:00'),(147,1,'testTask','test','frame',0,NULL,1058,'2019-01-16 13:36:00'),(148,1,'testTask','test','frame',0,NULL,1049,'2019-01-16 13:39:00'),(149,1,'testTask','test','frame',0,NULL,1079,'2019-01-16 13:42:00'),(150,1,'testTask','test','frame',0,NULL,1081,'2019-01-16 13:45:00'),(151,1,'testTask','test','frame',0,NULL,1083,'2019-01-16 13:48:00'),(152,1,'testTask','test','frame',0,NULL,1079,'2019-01-16 13:51:00'),(153,1,'testTask','test','frame',0,NULL,1062,'2019-01-16 13:54:00'),(154,1,'testTask','test','frame',0,NULL,1052,'2019-01-16 13:57:00'),(155,1,'testTask','test','frame',0,NULL,1047,'2019-01-16 14:00:00'),(156,1,'testTask','test','frame',0,NULL,1067,'2019-01-16 14:03:00'),(157,1,'testTask','test','frame',0,NULL,1085,'2019-01-16 14:06:00'),(158,1,'testTask','test','frame',0,NULL,1059,'2019-01-16 14:09:00'),(159,1,'testTask','test','frame',0,NULL,1052,'2019-01-16 14:12:00'),(160,1,'testTask','test','frame',0,NULL,1066,'2019-01-16 14:15:00'),(161,1,'testTask','test','frame',0,NULL,1063,'2019-01-16 14:18:00'),(162,1,'testTask','test','frame',0,NULL,1061,'2019-01-16 14:21:00'),(163,1,'testTask','test','frame',0,NULL,1057,'2019-01-16 14:24:00'),(164,1,'testTask','test','frame',0,NULL,1072,'2019-01-16 14:27:00'),(165,1,'testTask','test','frame',0,NULL,1060,'2019-01-16 14:30:00'),(166,1,'testTask','test','frame',0,NULL,1038,'2019-01-16 14:33:00'),(167,1,'testTask','test','frame',0,NULL,1064,'2019-01-16 14:36:00'),(168,1,'testTask','test','frame',0,NULL,1074,'2019-01-16 14:39:00'),(169,1,'testTask','test','frame',0,NULL,1082,'2019-01-16 14:42:00'),(170,1,'testTask','test','frame',0,NULL,1050,'2019-01-16 14:45:00'),(171,1,'testTask','test','frame',0,NULL,1056,'2019-01-16 14:48:00'),(172,1,'testTask','test','frame',0,NULL,1108,'2019-01-16 14:51:00'),(173,1,'testTask','test','frame',0,NULL,1128,'2019-01-16 14:54:00'),(174,1,'testTask','test','frame',0,NULL,1072,'2019-01-16 14:57:00'),(175,1,'testTask','test','frame',0,NULL,1066,'2019-01-16 15:00:00'),(176,1,'testTask','test','frame',0,NULL,1056,'2019-01-16 15:03:00'),(177,1,'testTask','test','frame',0,NULL,1062,'2019-01-16 15:06:00'),(178,1,'testTask','test','frame',0,NULL,1067,'2019-01-16 15:09:00'),(179,1,'testTask','test','frame',0,NULL,1075,'2019-01-16 15:12:00'),(180,1,'testTask','test','frame',0,NULL,1056,'2019-01-16 15:15:00'),(181,1,'testTask','test','frame',0,NULL,1062,'2019-01-16 15:18:00'),(182,1,'testTask','test','frame',0,NULL,1064,'2019-01-16 15:21:00'),(183,1,'testTask','test','frame',0,NULL,1086,'2019-01-16 15:24:00'),(184,1,'testTask','test','frame',0,NULL,1060,'2019-01-16 15:27:00'),(185,1,'testTask','test','frame',0,NULL,1084,'2019-01-16 15:30:00'),(186,1,'testTask','test','frame',0,NULL,1056,'2019-01-16 15:33:00'),(187,1,'testTask','test','frame',0,NULL,1077,'2019-01-16 15:36:00'),(188,1,'testTask','test','frame',0,NULL,1072,'2019-01-16 15:39:00'),(189,1,'testTask','test','frame',0,NULL,1083,'2019-01-16 15:42:00'),(190,1,'testTask','test','frame',0,NULL,1069,'2019-01-16 15:45:00'),(191,1,'testTask','test','frame',0,NULL,1065,'2019-01-16 15:48:00'),(192,1,'testTask','test','frame',0,NULL,1104,'2019-01-16 15:51:00'),(193,1,'testTask','test','frame',0,NULL,1062,'2019-01-16 15:54:00'),(194,1,'testTask','test','frame',0,NULL,1056,'2019-01-16 15:57:00'),(195,1,'testTask','test','frame',0,NULL,1079,'2019-01-16 16:00:00'),(196,1,'testTask','test','frame',0,NULL,1063,'2019-01-16 16:03:00'),(197,1,'testTask','test','frame',0,NULL,1072,'2019-01-16 16:06:00'),(198,1,'testTask','test','frame',0,NULL,1073,'2019-01-16 16:09:00'),(199,1,'testTask','test','frame',0,NULL,1061,'2019-01-16 16:12:00'),(200,1,'testTask','test','frame',0,NULL,1047,'2019-01-16 16:15:00'),(201,1,'testTask','test','frame',0,NULL,1052,'2019-01-16 16:18:00'),(202,1,'testTask','test','frame',0,NULL,1065,'2019-01-16 16:21:00'),(203,1,'testTask','test','frame',0,NULL,1040,'2019-01-16 16:24:00'),(204,1,'testTask','test','frame',0,NULL,1061,'2019-01-16 16:27:00'),(205,1,'testTask','test','frame',0,NULL,1120,'2019-01-16 16:30:00'),(206,1,'testTask','test','frame',0,NULL,1067,'2019-01-16 16:33:00'),(207,1,'testTask','test','frame',0,NULL,1068,'2019-01-16 16:36:00'),(208,1,'testTask','test','frame',0,NULL,1092,'2019-01-16 16:39:00'),(209,1,'testTask','test','frame',0,NULL,1063,'2019-01-16 16:42:00'),(210,1,'testTask','test','frame',0,NULL,1070,'2019-01-16 16:45:00'),(211,1,'testTask','test','frame',0,NULL,1059,'2019-01-16 16:48:00'),(212,1,'testTask','test','frame',0,NULL,1068,'2019-01-16 16:51:00'),(213,1,'testTask','test','frame',0,NULL,1070,'2019-01-16 16:54:00'),(214,1,'testTask','test','frame',0,NULL,1072,'2019-01-16 16:57:00'),(215,1,'testTask','test','frame',0,NULL,1059,'2019-01-16 17:00:00'),(216,1,'testTask','test','frame',0,NULL,1066,'2019-01-16 17:03:00'),(217,1,'testTask','test','frame',0,NULL,1071,'2019-01-16 17:06:00'),(218,1,'testTask','test','frame',0,NULL,1069,'2019-01-16 17:09:00'),(219,1,'testTask','test','frame',0,NULL,1101,'2019-01-16 17:12:00'),(220,1,'testTask','test','frame',0,NULL,1088,'2019-01-16 17:15:00'),(221,1,'testTask','test','frame',0,NULL,1095,'2019-01-16 17:18:00'),(222,1,'testTask','test','frame',0,NULL,1062,'2019-01-16 17:21:00'),(223,1,'testTask','test','frame',0,NULL,1055,'2019-01-16 17:24:00'),(224,1,'testTask','test','frame',0,NULL,1081,'2019-01-16 17:27:00'),(225,1,'testTask','test','frame',0,NULL,1073,'2019-01-16 17:30:00'),(226,1,'testTask','test','frame',0,NULL,1126,'2019-01-16 17:33:00'),(227,1,'testTask','test','frame',0,NULL,1061,'2019-01-16 17:36:00'),(228,1,'testTask','test','frame',0,NULL,1092,'2019-01-16 17:39:00'),(229,1,'testTask','test','frame',0,NULL,1072,'2019-01-16 17:42:00'),(230,1,'testTask','test','frame',0,NULL,1061,'2019-01-16 17:45:00'),(231,1,'testTask','test','frame',0,NULL,1059,'2019-01-16 17:48:00'),(232,1,'testTask','test','frame',0,NULL,1044,'2019-01-16 17:51:00'),(233,1,'testTask','test','frame',0,NULL,1048,'2019-01-16 17:54:00'),(234,1,'testTask','test','frame',0,NULL,1071,'2019-01-16 17:57:00'),(235,1,'testTask','test','frame',0,NULL,1045,'2019-01-16 18:00:00'),(236,1,'testTask','test','frame',0,NULL,1049,'2019-01-16 18:03:00'),(237,1,'testTask','test','frame',0,NULL,1053,'2019-01-16 18:06:00'),(238,1,'testTask','test','frame',0,NULL,1063,'2019-01-16 18:09:00'),(239,1,'testTask','test','frame',0,NULL,1079,'2019-01-16 18:12:00'),(240,1,'testTask','test','frame',0,NULL,1048,'2019-01-16 18:15:00'),(241,1,'testTask','test','frame',0,NULL,1063,'2019-01-16 18:18:00'),(242,1,'testTask','test','frame',0,NULL,1068,'2019-01-16 18:21:00'),(243,1,'testTask','test','frame',0,NULL,1062,'2019-01-16 18:24:01'),(244,1,'testTask','test','frame',0,NULL,1042,'2019-01-16 18:27:00'),(245,1,'testTask','test','frame',0,NULL,1064,'2019-01-16 18:30:00'),(246,1,'testTask','test','frame',0,NULL,1059,'2019-01-16 18:33:00'),(247,1,'testTask','test','frame',0,NULL,1062,'2019-01-16 18:36:00'),(248,1,'testTask','test','frame',0,NULL,1065,'2019-01-16 18:39:00'),(249,1,'testTask','test','frame',0,NULL,1080,'2019-01-16 18:42:00'),(250,1,'testTask','test','frame',0,NULL,1056,'2019-01-16 18:45:00'),(251,1,'testTask','test','frame',0,NULL,1054,'2019-01-16 18:48:00'),(252,1,'testTask','test','frame',0,NULL,1113,'2019-01-16 18:51:00'),(253,1,'testTask','test','frame',0,NULL,1053,'2019-01-16 18:54:00'),(254,1,'testTask','test','frame',0,NULL,1035,'2019-01-16 18:57:00'),(255,1,'testTask','test','frame',0,NULL,1064,'2019-01-16 19:00:00'),(256,1,'testTask','test','frame',0,NULL,1060,'2019-01-16 19:03:00'),(257,1,'testTask','test','frame',0,NULL,1055,'2019-01-16 19:06:00'),(258,1,'testTask','test','frame',0,NULL,1045,'2019-01-16 19:09:00'),(259,1,'testTask','test','frame',0,NULL,1041,'2019-01-16 19:12:00'),(260,1,'testTask','test','frame',0,NULL,1078,'2019-01-16 19:15:00'),(261,1,'testTask','test','frame',0,NULL,1051,'2019-01-16 19:18:00'),(262,1,'testTask','test','frame',0,NULL,1061,'2019-01-16 19:21:00'),(263,1,'testTask','test','frame',0,NULL,1063,'2019-01-16 19:24:00'),(264,1,'testTask','test','frame',0,NULL,1064,'2019-01-16 19:27:00'),(265,1,'testTask','test','frame',0,NULL,1080,'2019-01-16 19:30:00'),(266,1,'testTask','test','frame',0,NULL,1041,'2019-01-16 19:33:00'),(267,1,'testTask','test','frame',0,NULL,1040,'2019-01-16 19:36:00'),(268,1,'testTask','test','frame',0,NULL,1097,'2019-01-16 19:39:00'),(269,1,'testTask','test','frame',0,NULL,1060,'2019-01-16 19:42:00'),(270,1,'testTask','test','frame',0,NULL,1045,'2019-01-16 19:45:00'),(271,1,'testTask','test','frame',0,NULL,1060,'2019-01-16 19:48:00'),(272,1,'testTask','test','frame',0,NULL,1042,'2019-01-16 19:51:00'),(273,1,'testTask','test','frame',0,NULL,1048,'2019-01-16 19:54:00'),(274,1,'testTask','test','frame',0,NULL,1055,'2019-01-16 19:57:00'),(275,1,'testTask','test','frame',0,NULL,1053,'2019-01-16 20:00:00'),(276,1,'testTask','test','frame',0,NULL,1068,'2019-01-16 20:03:00'),(277,1,'testTask','test','frame',0,NULL,1046,'2019-01-16 20:06:00'),(278,1,'testTask','test','frame',0,NULL,1074,'2019-01-16 20:09:00'),(279,1,'testTask','test','frame',0,NULL,1053,'2019-01-16 20:12:00'),(280,1,'testTask','test','frame',0,NULL,1066,'2019-01-16 20:15:00'),(281,1,'testTask','test','frame',0,NULL,1073,'2019-01-16 20:18:00'),(282,1,'testTask','test','frame',0,NULL,1054,'2019-01-16 20:21:00'),(283,1,'testTask','test','frame',0,NULL,1042,'2019-01-16 20:24:00'),(284,1,'testTask','test','frame',0,NULL,1039,'2019-01-16 20:27:00'),(285,1,'testTask','test','frame',0,NULL,1061,'2019-01-16 20:30:00'),(286,1,'testTask','test','frame',0,NULL,1114,'2019-01-16 20:33:00'),(287,1,'testTask','test','frame',0,NULL,1057,'2019-01-16 20:36:00'),(288,1,'testTask','test','frame',0,NULL,1137,'2019-01-16 20:39:00'),(289,1,'testTask','test','frame',0,NULL,1086,'2019-01-17 21:33:00'),(290,1,'testTask','test','frame',0,NULL,1083,'2019-01-17 21:36:00'),(291,1,'testTask','test','frame',0,NULL,1103,'2019-01-17 21:45:00'),(292,1,'testTask','test','frame',0,NULL,1065,'2019-01-17 21:48:00'),(293,1,'testTask','test','frame',0,NULL,1151,'2019-01-17 21:51:00'),(294,1,'testTask','test','frame',0,NULL,1409,'2019-01-17 21:54:00'),(295,1,'testTask','test','frame',0,NULL,1070,'2019-01-17 21:57:00'),(296,1,'testTask','test','frame',0,NULL,1051,'2019-01-17 22:00:00'),(297,1,'testTask','test','frame',0,NULL,1083,'2019-01-17 22:03:00'),(298,1,'testTask','test','frame',0,NULL,1087,'2019-01-17 22:06:00'),(299,1,'testTask','test','frame',0,NULL,1126,'2019-01-17 22:12:01'),(300,1,'testTask','test','frame',0,NULL,1056,'2019-01-17 22:15:00'),(301,1,'testTask','test','frame',0,NULL,1063,'2019-01-17 22:18:00'),(302,1,'testTask','test','frame',0,NULL,1138,'2019-01-17 22:24:00'),(303,1,'testTask','test','frame',0,NULL,1070,'2019-01-17 22:27:00'),(304,1,'testTask','test','frame',0,NULL,1093,'2019-01-17 22:57:00'),(305,1,'testTask','test','frame',0,NULL,1094,'2019-01-19 12:39:00'),(306,1,'testTask','test','frame',0,NULL,1071,'2019-01-19 12:42:00'),(307,1,'testTask','test','frame',0,NULL,1105,'2019-01-19 12:45:00'),(308,1,'testTask','test','frame',0,NULL,1050,'2019-01-19 12:48:00'),(309,1,'testTask','test','frame',0,NULL,1096,'2019-01-19 12:51:00'),(310,1,'testTask','test','frame',0,NULL,1060,'2019-01-19 12:57:00'),(311,1,'testTask','test','frame',0,NULL,1079,'2019-01-19 13:00:00'),(312,1,'testTask','test','frame',0,NULL,1099,'2019-01-19 13:12:00'),(313,1,'testTask','test','frame',0,NULL,1041,'2019-01-19 13:15:00'),(314,1,'testTask','test','frame',0,NULL,1071,'2019-01-19 13:18:00'),(315,1,'testTask','test','frame',0,NULL,1046,'2019-01-19 13:21:00'),(316,1,'testTask','test','frame',0,NULL,1049,'2019-01-19 13:24:00'),(317,1,'testTask','test','frame',0,NULL,1105,'2019-01-19 13:27:00'),(318,1,'testTask','test','frame',0,NULL,1073,'2019-01-19 13:30:00'),(319,1,'testTask','test','frame',0,NULL,1056,'2019-01-19 13:33:00'),(320,1,'testTask','test','frame',0,NULL,1086,'2019-01-19 13:36:00'),(321,1,'testTask','test','frame',0,NULL,1080,'2019-01-19 13:39:00'),(322,1,'testTask','test','frame',0,NULL,1080,'2019-01-19 13:42:00'),(323,1,'testTask','test','frame',0,NULL,1044,'2019-01-19 13:45:00'),(324,1,'testTask','test','frame',0,NULL,1040,'2019-01-19 13:48:00'),(325,1,'testTask','test','frame',0,NULL,1033,'2019-01-19 13:51:00'),(326,1,'testTask','test','frame',0,NULL,1054,'2019-01-19 13:54:00'),(327,1,'testTask','test','frame',0,NULL,2074,'2019-01-19 13:57:00'),(328,1,'testTask','test','frame',0,NULL,1066,'2019-01-19 14:00:00'),(329,1,'testTask','test','frame',0,NULL,1112,'2019-01-19 14:03:00'),(330,1,'testTask','test','frame',0,NULL,1052,'2019-01-19 14:06:00'),(331,1,'testTask','test','frame',0,NULL,1064,'2019-01-19 14:12:00'),(332,1,'testTask','test','frame',0,NULL,1061,'2019-01-19 14:15:00'),(333,1,'testTask','test','frame',0,NULL,1061,'2019-01-19 14:18:00'),(334,1,'testTask','test','frame',0,NULL,1050,'2019-01-19 14:21:00'),(335,1,'testTask','test','frame',0,NULL,1070,'2019-01-19 14:24:00'),(336,1,'testTask','test','frame',0,NULL,1070,'2019-01-19 14:27:00'),(337,1,'testTask','test','frame',0,NULL,1053,'2019-01-19 14:30:00'),(338,1,'testTask','test','frame',0,NULL,1106,'2019-01-19 14:33:00'),(339,1,'testTask','test','frame',0,NULL,1057,'2019-01-19 14:36:00'),(340,1,'testTask','test','frame',0,NULL,1070,'2019-01-19 14:39:01'),(341,1,'testTask','test','frame',0,NULL,1081,'2019-01-19 15:00:00'),(342,1,'testTask','test','frame',0,NULL,1057,'2019-01-19 15:03:00'),(343,1,'testTask','test','frame',0,NULL,1055,'2019-01-19 15:09:00'),(344,1,'testTask','test','frame',0,NULL,1155,'2019-01-19 18:30:00'),(345,1,'testTask','test','frame',0,NULL,1082,'2019-01-19 18:33:00'),(346,1,'testTask','test','frame',0,NULL,1049,'2019-01-19 18:36:00'),(347,1,'testTask','test','frame',0,NULL,1080,'2019-01-19 18:39:00'),(348,1,'testTask','test','frame',0,NULL,1102,'2019-01-19 18:42:00'),(349,1,'testTask','test','frame',0,NULL,1095,'2019-01-19 18:48:00'),(350,1,'testTask','test','frame',0,NULL,1046,'2019-01-19 18:51:00'),(351,1,'testTask','test','frame',0,NULL,1072,'2019-01-19 18:54:00'),(352,1,'testTask','test','frame',0,NULL,1071,'2019-01-19 18:57:00'),(353,1,'testTask','test','frame',0,NULL,1069,'2019-01-19 19:00:00'),(354,1,'testTask','test','frame',0,NULL,1032,'2019-01-19 19:03:00'),(355,1,'testTask','test','frame',0,NULL,1086,'2019-01-19 19:09:00'),(356,1,'testTask','test','frame',0,NULL,1063,'2019-01-19 19:12:00'),(357,1,'testTask','test','frame',0,NULL,1111,'2019-01-19 19:36:01'),(358,1,'testTask','test','frame',0,NULL,1064,'2019-01-19 19:39:00'),(359,1,'testTask','test','frame',0,NULL,1088,'2019-01-19 19:42:00'),(360,1,'testTask','test','frame',0,NULL,1096,'2019-01-19 19:45:00'),(361,1,'testTask','test','frame',0,NULL,1075,'2019-01-19 19:48:00'),(362,1,'testTask','test','frame',0,NULL,1074,'2019-01-19 19:51:00'),(363,1,'testTask','test','frame',0,NULL,1055,'2019-01-19 19:54:00'),(364,1,'testTask','test','frame',0,NULL,1050,'2019-01-19 19:57:00'),(365,1,'testTask','test','frame',0,NULL,1112,'2019-01-19 20:06:00'),(366,1,'testTask','test','frame',0,NULL,1116,'2019-01-19 20:09:00'),(367,1,'testTask','test','frame',0,NULL,1064,'2019-01-19 20:12:00'),(368,1,'testTask','test','frame',0,NULL,1154,'2019-01-19 20:15:00'),(369,1,'testTask','test','frame',0,NULL,1097,'2019-01-19 20:18:00'),(370,1,'testTask','test','frame',0,NULL,1125,'2019-01-19 20:21:00'),(371,1,'testTask','test','frame',0,NULL,1119,'2019-01-19 20:24:00'),(372,1,'testTask','test','frame',0,NULL,1183,'2019-01-19 20:27:00'),(373,1,'testTask','test','frame',0,NULL,1046,'2019-01-19 20:30:00'),(374,1,'testTask','test','frame',0,NULL,1446,'2019-01-19 20:33:00'),(375,1,'testTask','test','frame',0,NULL,1062,'2019-01-19 20:36:00'),(376,1,'testTask','test','frame',0,NULL,1069,'2019-01-19 20:51:00'),(377,1,'testTask','test','frame',0,NULL,1071,'2019-01-19 20:54:00'),(378,1,'testTask','test','frame',0,NULL,1091,'2019-01-19 20:57:00'),(379,1,'testTask','test','frame',0,NULL,1094,'2019-01-19 21:06:00'),(380,1,'testTask','test','frame',0,NULL,1057,'2019-01-19 21:18:00'),(381,1,'testTask','test','frame',0,NULL,1090,'2019-01-19 21:24:00'),(382,1,'testTask','test','frame',0,NULL,1062,'2019-01-19 21:27:00'),(383,1,'testTask','test','frame',0,NULL,1075,'2019-01-19 21:30:00'),(384,1,'testTask','test','frame',0,NULL,1072,'2019-01-19 21:33:00'),(385,1,'testTask','test','frame',0,NULL,1049,'2019-01-19 21:36:00'),(386,1,'testTask','test','frame',0,NULL,1054,'2019-01-19 21:39:00'),(387,1,'testTask','test','frame',0,NULL,1039,'2019-01-19 21:42:00'),(388,1,'testTask','test','frame',0,NULL,1070,'2019-01-19 21:45:00'),(389,1,'testTask','test','frame',0,NULL,1069,'2019-01-19 21:51:00'),(390,1,'testTask','test','frame',0,NULL,1052,'2019-01-19 21:54:00'),(391,1,'testTask','test','frame',0,NULL,1038,'2019-01-19 21:57:00'),(392,1,'testTask','test','frame',0,NULL,1040,'2019-01-19 22:00:00'),(393,1,'testTask','test','frame',0,NULL,1101,'2019-01-19 22:06:00'),(394,1,'testTask','test','frame',0,NULL,1045,'2019-01-19 22:09:00'),(395,1,'testTask','test','frame',0,NULL,1098,'2019-01-19 22:15:00'),(396,1,'testTask','test','frame',0,NULL,1057,'2019-01-19 22:18:00'),(397,1,'testTask','test','frame',0,NULL,1102,'2019-01-19 22:21:00'),(398,1,'testTask','test','frame',0,NULL,1055,'2019-01-19 22:24:00'),(399,1,'testTask','test','frame',0,NULL,1050,'2019-01-19 22:27:00'),(400,1,'testTask','test','frame',0,NULL,1066,'2019-01-19 22:30:00'),(401,1,'testTask','test','frame',0,NULL,1049,'2019-01-19 22:33:00'),(402,1,'testTask','test','frame',0,NULL,1057,'2019-01-19 22:36:00'),(403,1,'testTask','test','frame',0,NULL,1075,'2019-01-19 22:39:00'),(404,1,'testTask','test','frame',0,NULL,1076,'2019-01-19 22:45:00'),(405,1,'testTask','test','frame',0,NULL,1067,'2019-01-19 22:48:00'),(406,1,'testTask','test','frame',0,NULL,1038,'2019-01-19 22:51:00'),(407,1,'testTask','test','frame',0,NULL,1038,'2019-01-19 22:54:00'),(408,1,'testTask','test','frame',0,NULL,1062,'2019-01-19 22:57:00'),(409,1,'testTask','test','frame',0,NULL,1058,'2019-01-19 23:00:00'),(410,1,'testTask','test','frame',0,NULL,1055,'2019-01-19 23:03:00'),(411,1,'testTask','test','frame',0,NULL,1081,'2019-01-19 23:06:00'),(412,1,'testTask','test','frame',0,NULL,1084,'2019-01-19 23:09:00'),(413,1,'testTask','test','frame',0,NULL,1087,'2019-01-19 23:12:00'),(414,1,'testTask','test','frame',0,NULL,1085,'2019-01-19 23:15:00'),(415,1,'testTask','test','frame',0,NULL,1060,'2019-01-19 23:18:00'),(416,1,'testTask','test','frame',0,NULL,1070,'2019-01-19 23:21:00'),(417,1,'testTask','test','frame',0,NULL,1082,'2019-01-19 23:27:00'),(418,1,'testTask','test','frame',0,NULL,1088,'2019-01-19 23:30:00'),(419,1,'testTask','test','frame',0,NULL,1069,'2019-01-19 23:36:00'),(420,1,'testTask','test','frame',0,NULL,1081,'2019-01-19 23:39:00'),(421,1,'testTask','test','frame',0,NULL,1048,'2019-01-19 23:42:00'),(422,1,'testTask','test','frame',0,NULL,1048,'2019-01-19 23:45:00'),(423,1,'testTask','test','frame',0,NULL,1055,'2019-01-19 23:48:00'),(424,1,'testTask','test','frame',0,NULL,1089,'2019-01-19 23:51:00'),(425,1,'testTask','test','frame',0,NULL,1058,'2019-01-19 23:54:00'),(426,1,'testTask','test','frame',0,NULL,1126,'2019-01-19 23:57:00'),(427,1,'testTask','test','frame',0,NULL,1078,'2019-01-20 00:03:00'),(428,1,'testTask','test','frame',0,NULL,1097,'2019-01-20 00:06:00'),(429,1,'testTask','test','frame',0,NULL,1079,'2019-01-20 00:09:00'),(430,1,'testTask','test','frame',0,NULL,1067,'2019-01-20 00:12:00'),(431,1,'testTask','test','frame',0,NULL,1057,'2019-01-20 00:15:00'),(432,1,'testTask','test','frame',0,NULL,1073,'2019-01-20 00:18:00'),(433,1,'testTask','test','frame',0,NULL,1041,'2019-01-20 00:21:00'),(434,1,'testTask','test','frame',0,NULL,1132,'2019-01-20 00:24:00'),(435,1,'testTask','test','frame',0,NULL,1183,'2019-01-20 00:27:00'),(436,1,'testTask','test','frame',0,NULL,1079,'2019-01-20 00:30:00'),(437,1,'testTask','test','frame',0,NULL,1087,'2019-01-20 00:33:00'),(438,1,'testTask','test','frame',0,NULL,1145,'2019-01-20 00:48:00'),(439,1,'testTask','test','frame',0,NULL,1335,'2019-01-20 00:51:01'),(440,1,'testTask','test','frame',0,NULL,1099,'2019-01-20 01:00:00'),(441,1,'testTask','test','frame',0,NULL,1074,'2019-01-20 01:03:00'),(442,1,'testTask','test','frame',0,NULL,1114,'2019-01-20 01:15:00'),(443,1,'testTask','test','frame',0,NULL,1050,'2019-01-20 01:18:00'),(444,1,'testTask','test','frame',0,NULL,1048,'2019-01-20 01:21:00'),(445,1,'testTask','test','frame',0,NULL,1079,'2019-01-20 01:24:00'),(446,1,'testTask','test','frame',0,NULL,1090,'2019-01-20 01:27:00'),(447,1,'testTask','test','frame',0,NULL,1050,'2019-01-20 01:30:00'),(448,1,'testTask','test','frame',0,NULL,1161,'2019-01-20 22:12:00'),(449,1,'testTask','test','frame',0,NULL,1073,'2019-01-20 22:15:00'),(450,1,'testTask','test','frame',0,NULL,1089,'2019-01-20 22:18:00'),(451,1,'testTask','test','frame',0,NULL,1448,'2019-01-20 22:21:03'),(452,1,'testTask','test','frame',0,NULL,1067,'2019-01-20 22:24:00'),(453,1,'testTask','test','frame',0,NULL,1044,'2019-01-20 22:27:00'),(454,1,'testTask','test','frame',0,NULL,1081,'2019-01-20 22:30:00'),(455,1,'testTask','test','frame',0,NULL,1056,'2019-01-20 22:33:00'),(456,1,'testTask','test','frame',0,NULL,1065,'2019-01-20 22:36:00'),(457,1,'testTask','test','frame',0,NULL,1078,'2019-01-20 22:39:00'),(458,1,'testTask','test','frame',0,NULL,1100,'2019-01-20 22:42:00'),(459,1,'testTask','test','frame',0,NULL,1066,'2019-01-20 22:45:00'),(460,1,'testTask','test','frame',0,NULL,1283,'2019-01-20 22:48:00'),(461,1,'testTask','test','frame',0,NULL,1136,'2019-01-20 22:51:00'),(462,1,'testTask','test','frame',0,NULL,1093,'2019-01-20 22:54:00'),(463,1,'testTask','test','frame',0,NULL,1120,'2019-01-20 22:57:00'),(464,1,'testTask','test','frame',0,NULL,1075,'2019-01-20 23:00:00'),(465,1,'testTask','test','frame',0,NULL,1064,'2019-01-20 23:03:00'),(466,1,'testTask','test','frame',0,NULL,1106,'2019-01-20 23:09:00'),(467,1,'testTask','test','frame',0,NULL,1047,'2019-01-20 23:12:00'),(468,1,'testTask','test','frame',0,NULL,1078,'2019-01-20 23:15:00'),(469,1,'testTask','test','frame',0,NULL,1117,'2019-01-20 23:18:00'),(470,1,'testTask','test','frame',0,NULL,1098,'2019-01-20 23:24:00'),(471,1,'testTask','test','frame',0,NULL,1157,'2019-01-20 23:27:01'),(472,1,'testTask','test','frame',0,NULL,1134,'2019-01-20 23:33:00'),(473,1,'testTask','test','frame',0,NULL,1105,'2019-01-20 23:42:00'),(474,1,'testTask','test','frame',0,NULL,1084,'2019-01-20 23:45:00'),(475,1,'testTask','test','frame',0,NULL,1119,'2019-01-20 23:51:00'),(476,1,'testTask','test','frame',0,NULL,1073,'2019-01-20 23:54:00'),(477,1,'testTask','test','frame',0,NULL,1071,'2019-01-20 23:57:00'),(478,1,'testTask','test','frame',0,NULL,1075,'2019-01-21 00:00:00'),(479,1,'testTask','test','frame',0,NULL,1069,'2019-01-21 00:03:00'),(480,1,'testTask','test','frame',0,NULL,1110,'2019-01-21 00:06:00'),(481,1,'testTask','test','frame',0,NULL,1080,'2019-01-21 00:09:00'),(482,1,'testTask','test','frame',0,NULL,1062,'2019-01-21 00:12:00'),(483,1,'testTask','test','frame',0,NULL,1055,'2019-01-21 00:15:00'),(484,1,'testTask','test','frame',0,NULL,1070,'2019-01-21 00:18:00'),(485,1,'testTask','test','frame',0,NULL,1075,'2019-01-21 14:18:01'),(486,1,'testTask','test','frame',0,NULL,1058,'2019-01-21 14:21:00'),(487,1,'testTask','test','frame',0,NULL,1171,'2019-01-21 14:27:00'),(488,1,'testTask','test','frame',0,NULL,1117,'2019-01-21 14:30:00'),(489,1,'testTask','test','frame',0,NULL,1055,'2019-01-21 14:33:01'),(490,1,'testTask','test','frame',0,NULL,1117,'2019-01-21 16:18:00'),(491,1,'testTask','test','frame',0,NULL,1302,'2019-01-21 16:27:01'),(492,1,'testTask','test','frame',0,NULL,1181,'2019-01-21 16:30:00'),(493,1,'testTask','test','frame',0,NULL,1074,'2019-01-21 16:33:01'),(494,1,'testTask','test','frame',0,NULL,1168,'2019-01-21 16:36:00'),(495,1,'testTask','test','frame',0,NULL,1232,'2019-01-21 16:48:00'),(496,1,'testTask','test','frame',0,NULL,1066,'2019-01-21 16:51:00'),(497,1,'testTask','test','frame',0,NULL,1276,'2019-01-21 16:54:00'),(498,1,'testTask','test','frame',0,NULL,1128,'2019-01-21 16:57:00'),(499,1,'testTask','test','frame',0,NULL,1160,'2019-01-21 17:00:00'),(500,1,'testTask','test','frame',0,NULL,1064,'2019-01-21 17:27:00'),(501,1,'testTask','test','frame',0,NULL,1052,'2019-01-21 17:30:00'),(502,1,'testTask','test','frame',0,NULL,1122,'2019-01-21 17:33:00'),(503,1,'testTask','test','frame',0,NULL,1122,'2019-01-21 17:36:00'),(504,1,'testTask','test','frame',0,NULL,1100,'2019-01-21 17:39:00'),(505,1,'testTask','test','frame',0,NULL,1126,'2019-01-21 17:42:00'),(506,1,'testTask','test','frame',0,NULL,1072,'2019-01-21 17:48:00'),(507,1,'testTask','test','frame',0,NULL,1174,'2019-01-21 17:51:01'),(508,1,'testTask','test','frame',0,NULL,1177,'2019-01-21 18:03:01'),(509,1,'testTask','test','frame',0,NULL,1126,'2019-01-21 18:36:00'),(510,1,'testTask','test','frame',0,NULL,1057,'2019-01-21 18:39:00'),(511,1,'testTask','test','frame',0,NULL,1061,'2019-01-21 18:42:00'),(512,1,'testTask','test','frame',0,NULL,1064,'2019-01-21 18:45:00'),(513,1,'testTask','test','frame',0,NULL,1080,'2019-01-21 18:48:00'),(514,1,'testTask','test','frame',0,NULL,1045,'2019-01-21 18:51:00'),(515,1,'testTask','test','frame',0,NULL,1269,'2019-01-21 18:54:00'),(516,1,'testTask','test','frame',0,NULL,1089,'2019-01-21 18:57:01'),(517,1,'testTask','test','frame',0,NULL,1206,'2019-01-21 19:00:01'),(518,1,'testTask','test','frame',0,NULL,4271,'2019-01-21 19:03:07'),(519,1,'testTask','test','frame',0,NULL,1142,'2019-01-21 19:06:02'),(520,1,'testTask','test','frame',0,NULL,1212,'2019-01-21 19:09:00'),(521,1,'testTask','test','frame',0,NULL,1225,'2019-01-21 19:12:00'),(522,1,'testTask','test','frame',0,NULL,1255,'2019-01-21 19:15:01'),(523,1,'testTask','test','frame',0,NULL,1290,'2019-01-21 19:18:01'),(524,1,'testTask','test','frame',0,NULL,2013,'2019-01-21 19:21:02'),(525,1,'testTask','test','frame',0,NULL,1360,'2019-01-21 19:24:02'),(526,1,'testTask','test','frame',0,NULL,1947,'2019-01-21 19:27:01'),(527,1,'testTask','test','frame',0,NULL,1641,'2019-01-21 19:30:02'),(528,1,'testTask','test','frame',0,NULL,2190,'2019-01-21 19:33:01'),(529,1,'testTask','test','frame',0,NULL,1957,'2019-01-21 19:36:02'),(530,1,'testTask','test','frame',0,NULL,1407,'2019-01-21 19:39:02'),(531,1,'testTask','test','frame',0,NULL,1928,'2019-01-21 19:42:01'),(532,1,'testTask','test','frame',0,NULL,1889,'2019-01-21 19:45:01'),(533,1,'testTask','test','frame',0,NULL,1147,'2019-01-21 19:54:00'),(534,1,'testTask','test','frame',0,NULL,1056,'2019-01-21 19:57:00'),(535,1,'testTask','test','frame',0,NULL,1049,'2019-01-21 20:00:00'),(536,1,'testTask','test','frame',0,NULL,1038,'2019-01-21 20:03:00'),(537,1,'testTask','test','frame',0,NULL,1149,'2019-01-21 20:12:00'),(538,1,'testTask','test','frame',0,NULL,1079,'2019-01-21 20:15:00'),(539,1,'testTask','test','frame',0,NULL,1074,'2019-01-21 20:18:00'),(540,1,'testTask','test','frame',0,NULL,1105,'2019-01-21 20:21:00'),(541,1,'testTask','test','frame',0,NULL,1066,'2019-01-21 20:24:00'),(542,1,'testTask','test','frame',0,NULL,1062,'2019-01-21 20:27:00'),(543,1,'testTask','test','frame',0,NULL,1070,'2019-01-21 20:30:00'),(544,1,'testTask','test','frame',0,NULL,1069,'2019-01-21 20:33:00'),(545,1,'testTask','test','frame',0,NULL,1082,'2019-01-21 20:36:00'),(546,1,'testTask','test','frame',0,NULL,1092,'2019-01-21 20:42:00'),(547,1,'testTask','test','frame',0,NULL,1106,'2019-01-21 20:51:00'),(548,1,'testTask','test','frame',0,NULL,1116,'2019-01-21 20:54:00'),(549,1,'testTask','test','frame',0,NULL,1084,'2019-01-21 20:57:00'),(550,1,'testTask','test','frame',0,NULL,1044,'2019-01-21 21:00:00'),(551,1,'testTask','test','frame',0,NULL,1056,'2019-01-21 21:03:00'),(552,1,'testTask','test','frame',0,NULL,1120,'2019-01-21 21:06:00'),(553,1,'testTask','test','frame',0,NULL,1086,'2019-01-21 21:09:00'),(554,1,'testTask','test','frame',0,NULL,1065,'2019-01-21 21:12:00'),(555,1,'testTask','test','frame',0,NULL,1083,'2019-01-21 21:15:00'),(556,1,'testTask','test','frame',0,NULL,1053,'2019-01-21 22:39:00'),(557,1,'testTask','test','frame',0,NULL,1086,'2019-01-21 22:42:00'),(558,1,'testTask','test','frame',0,NULL,1075,'2019-01-21 22:45:00'),(559,1,'testTask','test','frame',0,NULL,1077,'2019-01-21 22:48:00'),(560,1,'testTask','test','frame',0,NULL,1076,'2019-01-21 22:51:00'),(561,1,'testTask','test','frame',0,NULL,1090,'2019-01-21 22:54:00'),(562,1,'testTask','test','frame',0,NULL,1075,'2019-01-21 22:57:00'),(563,1,'testTask','test','frame',0,NULL,1067,'2019-01-21 23:00:00'),(564,1,'testTask','test','frame',0,NULL,1072,'2019-01-21 23:03:00'),(565,1,'testTask','test','frame',0,NULL,1074,'2019-01-21 23:06:00'),(566,1,'testTask','test','frame',0,NULL,1071,'2019-01-21 23:09:00'),(567,1,'testTask','test','frame',0,NULL,1095,'2019-01-21 23:12:00'),(568,1,'testTask','test','frame',0,NULL,1055,'2019-01-21 23:15:00'),(569,1,'testTask','test','frame',0,NULL,1072,'2019-01-21 23:18:00'),(570,1,'testTask','test','frame',0,NULL,1064,'2019-01-21 23:21:00'),(571,1,'testTask','test','frame',0,NULL,1100,'2019-01-21 23:27:00'),(572,1,'testTask','test','frame',0,NULL,1063,'2019-01-21 23:30:00'),(573,1,'testTask','test','frame',0,NULL,1085,'2019-01-21 23:36:00'),(574,1,'testTask','test','frame',0,NULL,1059,'2019-01-21 23:39:00'),(575,1,'testTask','test','frame',0,NULL,1087,'2019-01-21 23:48:00'),(576,1,'testTask','test','frame',0,NULL,1099,'2019-01-21 23:51:00'),(577,1,'testTask','test','frame',0,NULL,1109,'2019-01-21 23:54:00'),(578,1,'testTask','test','frame',0,NULL,1078,'2019-01-21 23:57:00'),(579,1,'testTask','test','frame',0,NULL,1049,'2019-01-22 00:00:00'),(580,1,'testTask','test','frame',0,NULL,1086,'2019-01-22 00:03:00'),(581,1,'testTask','test','frame',0,NULL,1070,'2019-01-22 00:06:00'),(582,1,'testTask','test','frame',0,NULL,1069,'2019-01-22 00:09:00'),(583,1,'testTask','test','frame',0,NULL,1107,'2019-01-22 00:12:00'),(584,1,'testTask','test','frame',0,NULL,1093,'2019-01-22 00:30:00'),(585,1,'testTask','test','frame',0,NULL,1108,'2019-01-22 00:36:00'),(586,1,'testTask','test','frame',0,NULL,1094,'2019-01-22 00:42:00'),(587,1,'testTask','test','frame',0,NULL,1089,'2019-01-22 00:51:00'),(588,1,'testTask','test','frame',0,NULL,1083,'2019-01-22 00:54:00'),(589,1,'testTask','test','frame',0,NULL,1082,'2019-01-22 01:00:00'),(590,1,'testTask','test','frame',0,NULL,1062,'2019-01-22 01:03:00'),(591,1,'testTask','test','frame',0,NULL,1104,'2019-01-22 01:06:00'),(592,1,'testTask','test','frame',0,NULL,1346,'2019-01-22 10:12:01'),(593,1,'testTask','test','frame',0,NULL,1237,'2019-01-22 10:15:02'),(594,1,'testTask','test','frame',0,NULL,21842,'2019-01-22 10:18:33'),(595,1,'testTask','test','frame',0,NULL,1535,'2019-01-22 10:21:00'),(596,1,'testTask','test','frame',0,NULL,1621,'2019-01-22 10:24:04'),(597,1,'testTask','test','frame',0,NULL,2604,'2019-01-22 10:27:03'),(598,1,'testTask','test','frame',0,NULL,7700,'2019-01-22 10:30:10'),(599,1,'testTask','test','frame',0,NULL,1091,'2019-01-22 11:33:00'),(600,1,'testTask','test','frame',0,NULL,1084,'2019-01-22 11:39:00'),(601,1,'testTask','test','frame',0,NULL,1060,'2019-01-22 11:42:00'),(602,1,'testTask','test','frame',0,NULL,1066,'2019-01-22 11:45:00'),(603,1,'testTask','test','frame',0,NULL,1069,'2019-01-22 11:48:00'),(604,1,'testTask','test','frame',0,NULL,1172,'2019-01-22 11:51:01'),(605,1,'testTask','test','frame',0,NULL,1073,'2019-01-22 11:54:00'),(606,1,'testTask','test','frame',0,NULL,1061,'2019-01-22 11:57:00'),(607,1,'testTask','test','frame',0,NULL,1067,'2019-01-22 12:00:01'),(608,1,'testTask','test','frame',0,NULL,1076,'2019-01-22 12:03:01'),(609,1,'testTask','test','frame',0,NULL,1079,'2019-01-22 12:06:01'),(610,1,'testTask','test','frame',0,NULL,1066,'2019-01-22 12:09:01'),(611,1,'testTask','test','frame',0,NULL,1063,'2019-01-22 12:12:02'),(612,1,'testTask','test','frame',0,NULL,1218,'2019-01-22 12:15:01'),(613,1,'testTask','test','frame',0,NULL,1242,'2019-01-22 12:18:00'),(614,1,'testTask','test','frame',0,NULL,1883,'2019-01-22 12:21:00'),(615,1,'testTask','test','frame',0,NULL,1239,'2019-01-22 12:24:00'),(616,1,'testTask','test','frame',0,NULL,1082,'2019-01-22 12:27:00'),(617,1,'testTask','test','frame',0,NULL,1067,'2019-01-22 12:30:00'),(618,1,'testTask','test','frame',0,NULL,1168,'2019-01-22 12:33:00'),(619,1,'testTask','test','frame',0,NULL,1063,'2019-01-22 12:36:00'),(620,1,'testTask','test','frame',0,NULL,1070,'2019-01-22 12:39:00'),(621,1,'testTask','test','frame',0,NULL,1079,'2019-01-22 12:42:00'),(622,1,'testTask','test','frame',0,NULL,1072,'2019-01-22 12:45:00'),(623,1,'testTask','test','frame',0,NULL,1157,'2019-01-22 12:48:00'),(624,1,'testTask','test','frame',0,NULL,1058,'2019-01-22 12:51:00'),(625,1,'testTask','test','frame',0,NULL,1055,'2019-01-22 12:54:00'),(626,1,'testTask','test','frame',0,NULL,1057,'2019-01-22 12:57:00'),(627,1,'testTask','test','frame',0,NULL,1055,'2019-01-22 13:00:00'),(628,1,'testTask','test','frame',0,NULL,1068,'2019-01-22 13:03:00'),(629,1,'testTask','test','frame',0,NULL,1045,'2019-01-22 13:06:00'),(630,1,'testTask','test','frame',0,NULL,1067,'2019-01-22 13:09:00'),(631,1,'testTask','test','frame',0,NULL,1065,'2019-01-22 13:12:00'),(632,1,'testTask','test','frame',0,NULL,1050,'2019-01-22 13:15:00'),(633,1,'testTask','test','frame',0,NULL,1054,'2019-01-22 13:18:00'),(634,1,'testTask','test','frame',0,NULL,1068,'2019-01-22 13:21:01'),(635,1,'testTask','test','frame',0,NULL,1038,'2019-01-22 13:24:00'),(636,1,'testTask','test','frame',0,NULL,1077,'2019-01-22 13:27:01'),(637,1,'testTask','test','frame',0,NULL,1326,'2019-01-22 13:33:01'),(638,1,'testTask','test','frame',0,NULL,1539,'2019-01-22 13:36:01'),(639,1,'testTask','test','frame',0,NULL,1421,'2019-01-22 13:39:00'),(640,1,'testTask','test','frame',0,NULL,1573,'2019-01-22 13:42:00'),(641,1,'testTask','test','frame',0,NULL,1582,'2019-01-22 13:45:00'),(642,1,'testTask','test','frame',0,NULL,1104,'2019-01-22 14:18:00'),(643,1,'testTask','test','frame',0,NULL,1095,'2019-01-22 14:21:00'),(644,1,'testTask','test','frame',0,NULL,1123,'2019-01-22 14:57:00'),(645,1,'testTask','test','frame',0,NULL,1064,'2019-01-22 15:00:00'),(646,1,'testTask','test','frame',0,NULL,1052,'2019-01-22 15:03:00'),(647,1,'testTask','test','frame',0,NULL,1059,'2019-01-22 15:06:00'),(648,1,'testTask','test','frame',0,NULL,1069,'2019-01-22 15:09:00'),(649,1,'testTask','test','frame',0,NULL,1052,'2019-01-22 15:12:00'),(650,1,'testTask','test','frame',0,NULL,1048,'2019-01-22 15:15:00'),(651,1,'testTask','test','frame',0,NULL,1047,'2019-01-22 15:18:00'),(652,1,'testTask','test','frame',0,NULL,1039,'2019-01-22 15:21:00'),(653,1,'testTask','test','frame',0,NULL,1040,'2019-01-22 15:24:00'),(654,1,'testTask','test','frame',0,NULL,1081,'2019-01-22 15:27:00'),(655,1,'testTask','test','frame',0,NULL,1093,'2019-01-22 15:33:00'),(656,1,'testTask','test','frame',0,NULL,1066,'2019-01-22 15:36:00'),(657,1,'testTask','test','frame',0,NULL,1056,'2019-01-22 15:39:00'),(658,1,'testTask','test','frame',0,NULL,1038,'2019-01-22 15:42:00'),(659,1,'testTask','test','frame',0,NULL,1093,'2019-01-22 17:06:00'),(660,1,'testTask','test','frame',0,NULL,1068,'2019-01-22 17:09:00'),(661,1,'testTask','test','frame',0,NULL,1042,'2019-01-22 17:15:00'),(662,1,'testTask','test','frame',0,NULL,1079,'2019-01-22 17:18:01'),(663,1,'testTask','test','frame',0,NULL,1069,'2019-01-22 17:21:01'),(664,1,'testTask','test','frame',0,NULL,1182,'2019-01-22 21:00:00'),(665,1,'testTask','test','frame',0,NULL,1039,'2019-01-22 21:03:00'),(666,1,'testTask','test','frame',0,NULL,1072,'2019-01-22 21:06:00'),(667,1,'testTask','test','frame',0,NULL,1115,'2019-01-22 21:12:00'),(668,1,'testTask','test','frame',0,NULL,1074,'2019-01-22 21:15:00'),(669,1,'testTask','test','frame',0,NULL,1066,'2019-01-22 21:18:00'),(670,1,'testTask','test','frame',0,NULL,1063,'2019-01-22 21:21:00'),(671,1,'testTask','test','frame',0,NULL,1055,'2019-01-22 21:24:00'),(672,1,'testTask','test','frame',0,NULL,1083,'2019-01-22 21:27:00'),(673,1,'testTask','test','frame',0,NULL,1072,'2019-01-22 21:30:00'),(674,1,'testTask','test','frame',0,NULL,1043,'2019-01-22 21:33:00'),(675,1,'testTask','test','frame',0,NULL,1187,'2019-01-22 21:36:00'),(676,1,'testTask','test','frame',0,NULL,1172,'2019-01-22 21:39:00'),(677,1,'testTask','test','frame',0,NULL,1178,'2019-01-22 21:42:01'),(678,1,'testTask','test','frame',0,NULL,1102,'2019-01-22 21:45:00'),(679,1,'testTask','test','frame',0,NULL,1058,'2019-01-22 21:48:00'),(680,1,'testTask','test','frame',0,NULL,1086,'2019-01-22 21:51:00'),(681,1,'testTask','test','frame',0,NULL,1076,'2019-01-22 21:57:00'),(682,1,'testTask','test','frame',0,NULL,1075,'2019-01-22 22:00:00'),(683,1,'testTask','test','frame',0,NULL,1052,'2019-01-22 22:06:00'),(684,1,'testTask','test','frame',0,NULL,1114,'2019-01-22 22:27:00'),(685,1,'testTask','test','frame',0,NULL,1043,'2019-01-22 22:30:00'),(686,1,'testTask','test','frame',0,NULL,1057,'2019-01-22 22:33:00'),(687,1,'testTask','test','frame',0,NULL,1080,'2019-01-22 22:36:00'),(688,1,'testTask','test','frame',0,NULL,1099,'2019-01-22 22:48:00'),(689,1,'testTask','test','frame',0,NULL,1082,'2019-01-22 22:51:00'),(690,1,'testTask','test','frame',0,NULL,1149,'2019-01-22 22:54:00'),(691,1,'testTask','test','frame',0,NULL,1073,'2019-01-22 22:57:00'),(692,1,'testTask','test','frame',0,NULL,1136,'2019-01-22 23:30:00'),(693,1,'testTask','test','frame',0,NULL,1089,'2019-01-22 23:33:00'),(694,1,'testTask','test','frame',0,NULL,1095,'2019-01-22 23:36:00'),(695,1,'testTask','test','frame',0,NULL,1086,'2019-01-22 23:39:00'),(696,1,'testTask','test','frame',0,NULL,1056,'2019-01-22 23:42:00'),(697,1,'testTask','test','frame',0,NULL,1079,'2019-01-22 23:45:00'),(698,1,'testTask','test','frame',0,NULL,1068,'2019-01-22 23:48:00'),(699,1,'testTask','test','frame',0,NULL,1053,'2019-01-22 23:51:00'),(700,1,'testTask','test','frame',0,NULL,1052,'2019-01-22 23:54:00'),(701,1,'testTask','test','frame',0,NULL,1068,'2019-01-22 23:57:00'),(702,1,'testTask','test','frame',0,NULL,1065,'2019-01-23 00:27:00'),(703,1,'testTask','test','frame',0,NULL,1056,'2019-01-23 00:30:00'),(704,1,'testTask','test','frame',0,NULL,1130,'2019-01-23 00:33:00'),(705,1,'testTask','test','frame',0,NULL,1099,'2019-01-23 00:36:00');

/*Table structure for table `s_sys_dept` */

DROP TABLE IF EXISTS `s_sys_dept`;

CREATE TABLE `s_sys_dept` (
  `deptId` bigint(20) NOT NULL AUTO_INCREMENT,
  `parentId` bigint(20) DEFAULT NULL COMMENT '上级部门ID，一级部门为0',
  `name` varchar(50) DEFAULT NULL COMMENT '部门名称',
  `orderNum` int(11) DEFAULT NULL COMMENT '排序',
  `deleteFlag` int(1) DEFAULT '0' COMMENT '是否删除  -1：已删除  0：正常',
  PRIMARY KEY (`deptId`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='部门管理';

/*Data for the table `s_sys_dept` */

insert  into `s_sys_dept`(`deptId`,`parentId`,`name`,`orderNum`,`deleteFlag`) values (12,0,'系统部门',0,0);

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
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8 COMMENT='菜单管理';

/*Data for the table `s_sys_menu` */

insert  into `s_sys_menu`(`menuId`,`parentId`,`name`,`url`,`perms`,`type`,`icon`,`spread`,`orderNum`) values (1,0,'系统管理',NULL,NULL,0,'larry-xitong',1,0),(2,46,'用户管理','modules/sys/user.html',NULL,1,'larry-yonghuliebiao',0,1),(3,46,'角色管理','modules/sys/role.html',NULL,1,'larry-jiaoseguanli',0,2),(4,49,'菜单管理','modules/sys/menu.html',NULL,1,'larry-lanmuguanli-copy',0,1),(5,47,'SQL监控','druid/sql.html',NULL,1,'larry-uicon_sql',0,1),(15,2,'查看',NULL,'sys:user:list,sys:user:info',2,NULL,0,0),(16,2,'新增',NULL,'sys:user:save,sys:role:select',2,NULL,0,0),(17,2,'修改',NULL,'sys:user:update,sys:role:select',2,NULL,0,0),(18,2,'删除',NULL,'sys:user:delete',2,NULL,0,0),(19,3,'查看',NULL,'sys:role:list,sys:role:info',2,NULL,0,0),(20,3,'新增',NULL,'sys:role:save,sys:menu:perms',2,NULL,0,0),(21,3,'修改',NULL,'sys:role:update,sys:menu:perms',2,NULL,0,0),(22,3,'删除',NULL,'sys:role:delete',2,NULL,0,0),(23,4,'查看',NULL,'sys:menu:list,sys:menu:info',2,NULL,0,0),(24,4,'新增',NULL,'sys:menu:save,sys:menu:select',2,NULL,0,0),(25,4,'修改',NULL,'sys:menu:update,sys:menu:select',2,NULL,0,0),(26,4,'删除',NULL,'sys:menu:delete',2,NULL,0,0),(27,42,'参数管理','modules/sys/config.html','sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete',1,'larry-xitong-pressed',0,2),(29,47,'系统日志','modules/sys/log.html','sys:log:list',1,'larry-10109',0,2),(30,42,'文件上传','modules/sys/oss.html','sys:oss:all',1,'larry-friendLink',0,4),(31,46,'部门管理','modules/sys/dept.html',NULL,1,'larry-Shape',0,3),(32,31,'查看',NULL,'sys:dept:list,sys:dept:info',2,NULL,0,0),(33,31,'新增',NULL,'sys:dept:save,sys:dept:select',2,NULL,0,0),(34,31,'修改',NULL,'sys:dept:update,sys:dept:select',2,NULL,0,0),(35,31,'删除',NULL,'sys:dept:delete',2,NULL,0,0),(41,48,'文章管理','modules/demo/news.html','demo:news:list,demo:news:info,demo:news:save,demo:news:update,demo:news:delete',1,'larry-neirongfabu',0,1),(42,1,'系统设置',NULL,NULL,0,'larry-wsmp-setting',0,2),(46,1,'系统用户管理',NULL,NULL,0,'larry-paikexitong_yonghuguanli',0,0),(47,1,'系统监控',NULL,NULL,0,'larry-shouye-anquanguanli',0,3),(48,0,'功能示例',NULL,NULL,0,'larry-diannao3',0,2),(49,1,'系统菜单',NULL,NULL,0,'larry-caidanguanli3',0,1),(50,49,'图标管理','modules/demo/font.html',NULL,1,'larry-qizhi',0,2),(51,2,'导出',NULL,'sys:user:export',2,NULL,0,0),(53,0,'Go系统管理',NULL,NULL,0,'larry-xiaolian',0,1),(54,53,'商品管理','modules/happytrip/product.html','ht:product:list,ht:product:info,ht:product:update,ht:product:save,ht:product:delete,ht:product:select,ht:productType:list,',0,'larry-lanmuguanli-copy',0,0),(58,53,'日志管理','modules/happytrip/log.html','sys:log:list',1,'larry-10109',0,9),(59,53,'用户管理','modules/happytrip/user.html','ht:USER:LIST,ht:USER:info,ht:USER:UPDATE,ht:USER:save,ht:USER:DELETE,ht:USER:SELECT,ht:walletChange:LIST,ht:wallet:LIST,ht:wallet:info,ht:USER:resetpass,ht:wallet:recharge,ht:wallet:subtract',0,'larry-yonghuliebiao',0,2),(60,53,'帐变管理','modules/happytrip/walletChange.html','ht:walletChange:list',1,'larry-log',0,8),(61,53,'参数管理','modules/sys/config.html','sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete',0,'larry-xitong-pressed',0,6),(62,53,'文件上传','modules/sys/oss.html','sys:oss:all',1,'larry-friendLink',0,10),(63,53,'定时任务','modules/job/schedule.html',NULL,0,'larry-xitong10',0,9),(71,61,'充值参数','modules/happytrip/config.html?configType=recharge','sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete',1,'larry-tubiaozhizuomoban-copy',0,0),(72,53,'订单管理','modules/order/order.html','ht:order:list,ht:order:info,ht:order:save,ht:order:update,ht:order:delete',0,'larry-fold4',0,1),(73,54,'商品列表','modules/happytrip/product.html','ht:product:list,ht:product:info,ht:product:update,ht:product:save,ht:product:delete,ht:product:select,ht:productType:list,',1,'larry-listpress',0,1),(74,54,'商品类型','modules/happytrip/productType.html','ht:productType:list,ht:productType:info,ht:productType:update,ht:productType:save,ht:productType:delete,ht:productType:select,ht:productTypeType:list,',1,'larry-mianban',0,0),(75,72,'订单列表','modules/happytrip/order.html','ht:order:list,ht:order:info,ht:order:save,ht:order:update,ht:order:delete,ht:order:select',1,'larry-caidan',0,0),(76,59,'用户列表','modules/happytrip/user.html','ht:USER:LIST,ht:USER:info,ht:USER:UPDATE,ht:USER:save,ht:USER:DELETE,ht:USER:SELECT,ht:walletChange:LIST,ht:wallet:LIST,ht:wallet:info,ht:USER:resetpass,ht:wallet:recharge,ht:wallet:subtract',1,'larry-Userlist',0,0),(77,53,'充值管理',NULL,NULL,0,'larry-chongzhi',0,3),(78,53,'提现管理',NULL,NULL,0,'larry-chongzhi2',0,4),(79,77,'充值列表','modules/happytrip/recharge.html','ht:recharge:list,ht:recharge:info,ht:recharge:update,ht:recharge:delete,ht:recharge:save',1,'larry-chongzh',0,0),(81,78,'提现列表','modules/happytrip/withdraw.html','ht:withdraw:list,ht:withdraw:save,ht:withdraw:update,ht:withdraw:info,ht:withdraw:select,ht:withdraw:delete',1,'larry-icon3',0,0),(82,53,'福利管理',NULL,NULL,0,'larry-a536',0,5),(83,82,'福利列表','modules/happytrip/welfare.html','ht:welfare:list,ht:welfare:info,ht:welfare:update,ht:welfare:save,ht:welfare:delete',1,'larry-xitong4',0,0),(84,61,'提现参数','modules/happytrip/config.html?configType=withdraw','sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete',1,'larry-faxianhover',0,1),(85,61,'系统参数','modules/happytrip/config.html?configType=system','sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete',1,'larry-system-extension',0,2),(86,61,'福利开关','modules/happytrip/config.html?configType=welfare','sys:config:list,sys:config:info,sys:config:save,sys:config:update,sys:config:delete',1,'larry-anquanguanli',0,3),(87,53,'报表统计','modules/happytrip/report.html','ht:report:list,ht:report:info,ht:report:update,ht:report:delete,ht:report:save',1,'larry-shujutongji2',0,7),(88,53,'支付接口','modules/happytrip/payment.html','ht:payment:list,ht:payment:save,ht:payment:update,ht:payment:info,ht:payment:delete',1,'larry-fold2',0,8),(89,63,'任务列表','modules/job/schedule.html','sys:schedule:list,sys:schedule:info,sys:schedule:save,sys:schedule:update,sys:schedule:delete,sys:schedule:pause,sys:schedule:resume,sys:schedule:run',1,'larry-renwu',0,0),(90,63,'任务日志','modules/happytrip/schedulelog.html','sys:schedulelog:list,sys:schedulelog:info,sys:schedulelog:save,sys:schedulelog:update,sys:schedulelog:delete,sys:schedulelog:pause,sys:schedulelog:resume,sys:schedulelog:run',1,'larry-caozuorizhi',0,1),(91,59,'推荐记录','modules/happytrip/recommend.html','ht:recommend:list,ht:recommend:info,ht:recommend:save,ht:recommend:update,ht:recommend:delete',1,'larry-gerenxinxi',0,11);

/*Table structure for table `s_sys_oss` */

DROP TABLE IF EXISTS `s_sys_oss`;

CREATE TABLE `s_sys_oss` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(200) DEFAULT NULL COMMENT 'URL地址',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8 COMMENT='文件上传';

/*Data for the table `s_sys_oss` */

insert  into `s_sys_oss`(`id`,`url`,`createTime`) values (4,'/home/images/20190119193515.JPG','2019-01-19 19:35:15'),(5,'/home/images/20190119193604.JPG','2019-01-19 19:36:05'),(6,'/home/images/20190119193713.JPG','2019-01-19 19:37:14'),(7,'/home/images/20190119202508.JPG','2019-01-19 20:25:08'),(8,'/home/images/20190119230717.JPG','2019-01-19 23:07:18'),(9,'/home/images/20190119231501.JPG','2019-01-19 23:15:02'),(10,'/home/images/20190120001106.JPG','2019-01-20 00:11:07'),(11,'/home/images/20190120001142.JPG','2019-01-20 00:11:42'),(12,'/home/images/20190120001859.JPG','2019-01-20 00:19:00'),(13,'/home/images/20190120002813.JPG','2019-01-20 00:28:13'),(14,'/home/images/20190120002828.JPG','2019-01-20 00:28:29'),(15,'/home/images/20190120002836.JPG','2019-01-20 00:28:37'),(16,'/home/images/20190120003132.JPG','2019-01-20 00:31:32'),(17,'/home/images/20190120003418.JPG','2019-01-20 00:34:18'),(18,'/home/images/20190120003441.JPG','2019-01-20 00:34:42'),(19,'/home/images/20190120004458.JPG','2019-01-20 00:44:58'),(20,'/home/images/20190120004602.JPG','2019-01-20 00:46:02'),(21,'/home/images/20190120004637.JPG','2019-01-20 00:46:37'),(22,'/home/images/20190120004706.JPG','2019-01-20 00:47:06'),(23,'/home/images/20190120005403.JPG','2019-01-20 00:54:04'),(24,'/home/images/20190120005502.JPG','2019-01-20 00:55:02'),(25,'/home/images/20190122113249.jpg','2019-01-22 11:32:50'),(26,'/home/images/20190122113408.jpg','2019-01-22 11:34:09'),(27,'/home/images/20190122113633.jpg','2019-01-22 11:36:34'),(28,'/home/images/20190122124653.jpg','2019-01-22 12:46:53'),(29,'/home/images/20190122124830.jpg','2019-01-22 12:48:30'),(30,'/home/images/20190122130146.jpg','2019-01-22 13:01:47'),(31,'/home/images/20190122130243.jpg','2019-01-22 13:02:43'),(32,'/home/images/20190122130259.jpg','2019-01-22 13:02:59'),(33,'/home/images/20190122130657.jpg','2019-01-22 13:06:58'),(34,'/home/images/20190122131033.jpg','2019-01-22 13:10:34'),(35,'/home/images/20190122131044.jpg','2019-01-22 13:10:45'),(36,'/home/images/20190122131253.jpg','2019-01-22 13:12:53'),(37,'/home/images/20190122133110.jpg','2019-01-22 13:31:10'),(38,'/home/images/20190122133117.jpg','2019-01-22 13:31:18'),(39,'/home/images/20190122134303.jpg','2019-01-22 13:43:04'),(40,'/home/images/20190122141717.jpg','2019-01-22 14:17:18'),(41,'/home/images/20190122141725.jpg','2019-01-22 14:17:26'),(42,'/home/images/20190122141920.jpg','2019-01-22 14:19:20'),(43,'/home/images/20190122145921.jpg','2019-01-22 14:59:22'),(44,'/home/images/20190122151113.jpg','2019-01-22 15:11:13'),(45,'/home/images/20190122151158.jpg','2019-01-22 15:11:59'),(46,'/home/images/20190122151312.jpg','2019-01-22 15:13:13'),(47,'/home/images/20190122152220.jpg','2019-01-22 15:22:21');

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

insert  into `s_sys_role`(`roleId`,`roleName`,`remark`,`deptId`,`createTime`) values (20,'管理员','管理员',12,'2018-05-17 13:56:21'),(21,'超级管理员','系统管理员',12,'2018-05-18 16:12:47');

/*Table structure for table `s_sys_role_dept` */

DROP TABLE IF EXISTS `s_sys_role_dept`;

CREATE TABLE `s_sys_role_dept` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `roleId` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `deptId` bigint(20) DEFAULT NULL COMMENT '部门ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COMMENT='角色与部门对应关系';

/*Data for the table `s_sys_role_dept` */

/*Table structure for table `s_sys_role_menu` */

DROP TABLE IF EXISTS `s_sys_role_menu`;

CREATE TABLE `s_sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `roleId` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menuId` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=302 DEFAULT CHARSET=utf8 COMMENT='角色与菜单对应关系';

/*Data for the table `s_sys_role_menu` */

insert  into `s_sys_role_menu`(`id`,`roleId`,`menuId`) values (239,21,1),(240,21,46),(241,21,2),(242,21,15),(243,21,16),(244,21,17),(245,21,18),(246,21,51),(247,21,3),(248,21,19),(249,21,20),(250,21,21),(251,21,22),(252,21,31),(253,21,32),(254,21,33),(255,21,34),(256,21,35),(257,21,49),(258,21,4),(259,21,23),(260,21,24),(261,21,25),(262,21,26),(263,21,50),(264,21,42),(265,21,27),(266,21,30),(267,21,47),(268,21,5),(269,21,29),(270,21,53),(271,21,54),(272,21,59),(273,21,63),(274,21,64),(275,21,65),(276,21,66),(277,21,67),(278,21,68),(279,21,69),(280,21,70),(281,21,60),(282,21,58),(283,21,61),(284,21,62),(285,21,48),(286,21,41),(287,20,53),(288,20,54),(289,20,59),(290,20,63),(291,20,64),(292,20,65),(293,20,66),(294,20,67),(295,20,68),(296,20,69),(297,20,70),(298,20,60),(299,20,58),(300,20,61),(301,20,62);

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='系统用户';

/*Data for the table `s_sys_user` */

insert  into `s_sys_user`(`userId`,`userName`,`userPass`,`salt`,`email`,`mobile`,`status`,`deptId`,`superFlag`,`createUser`,`createTime`) values (1,'admin','5f9c50b9d370e553b076ecf20870baab6dff1d061fb15868b62ca17f04b70a16','YzcmCZNvbXocrsz9dm8e','root@frame.io0','13888888888',1,12,1,'admin','2016-11-11 11:11:11'),(5,'fury','e5fe7755ddf29d9cbfe54854791f9278a013d1958a704b0c3fa7dfca6d5e1038','qtQy7QLTC5ytolveDnjc','fury@qq.com','18888888888',1,12,0,NULL,'2019-01-14 17:22:25');

/*Table structure for table `s_sys_user_role` */

DROP TABLE IF EXISTS `s_sys_user_role`;

CREATE TABLE `s_sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `roleId` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='用户与角色对应关系';

/*Data for the table `s_sys_user_role` */

insert  into `s_sys_user_role`(`id`,`userId`,`roleId`) values (10,4,20),(11,5,20),(12,1,21);

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='充值记录表';

/*Data for the table `u_recharge` */

insert  into `u_recharge`(`rechargeId`,`userId`,`userMobile`,`userName`,`parentId`,`groupUserIds`,`rechargeMoney`,`rechargeFee`,`alipayName`,`alipayMobile`,`rechargeCode`,`status`,`frontRemark`,`backRemark`,`createTime`,`createUser`,`updateTime`,`updateUser`) values (1,2,'13333333333','fury',1,'1,2,','100.0000','0.0000','fury','13333333333','123',1,'111',NULL,'2019-01-21 11:11:11','admin','2019-01-21 21:05:19','admin');

/*Table structure for table `u_recommend` */

DROP TABLE IF EXISTS `u_recommend`;

CREATE TABLE `u_recommend` (
  `recommendId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '前端团队显示主键Id',
  `userId` bigint(20) DEFAULT NULL COMMENT '用户Id',
  `userName` varchar(50) DEFAULT NULL COMMENT '用户名称',
  `parentId` bigint(20) DEFAULT NULL COMMENT '用户上级Id',
  `groupUserIds` varchar(3000) DEFAULT NULL COMMENT '用户组Id，逗号分隔',
  `recommendNumber` int(11) DEFAULT '0' COMMENT '今日推荐人数',
  `teamAchievement` decimal(15,4) DEFAULT '0.0000' COMMENT '今日团队业绩',
  `createTime` date DEFAULT NULL COMMENT '创建时间',
  `createUser` varchar(50) DEFAULT NULL COMMENT '创建人',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `updateUser` varchar(50) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`recommendId`),
  UNIQUE KEY `recommendId_createTime` (`recommendId`,`createTime`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='会员每日推荐表';

/*Data for the table `u_recommend` */

insert  into `u_recommend`(`recommendId`,`userId`,`userName`,`parentId`,`groupUserIds`,`recommendNumber`,`teamAchievement`,`createTime`,`createUser`,`updateTime`,`updateUser`) values (8,1,'admin',0,'0,1,',12,'30000.0000','2019-01-23','admin','2019-01-23 00:34:47','admin');

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

insert  into `u_token`(`userId`,`token`,`expireTime`,`updateTime`) values (1,'b60407f31a0f478a8576985158fafd32','2019-01-23 10:44:34','2019-01-22 22:44:34'),(2,'aa20eabbef884adf840c05281f5efd38','2019-01-22 12:11:09','2019-01-22 00:11:09');

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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='用户表';

/*Data for the table `u_user` */

insert  into `u_user`(`userId`,`userName`,`userMobile`,`userPass`,`alipayName`,`alipayMobile`,`userLevel`,`parentId`,`groupUserIds`,`status`,`recommendMobile`,`createTime`,`createUser`,`updateTime`,`updateUser`,`registerType`,`payPassWord`,`lastLoginTime`,`lastLoginIp`) values (1,'admin','18888888888','8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918','admin','18888888888',1,0,'0,1,',1,'18888888888','2017-03-23 22:37:41',NULL,'2019-01-15 21:46:39','admin',1,NULL,'2019-01-22 22:44:34','116.30.194.132'),(2,'fury','13333333333','8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92','fury','13333333333',2,1,'0,1,2,',1,'13333333333','2019-01-19 14:26:31','admin',NULL,NULL,1,NULL,'2019-01-22 00:10:49','113.116.116.131');

/*Table structure for table `u_wallet` */

DROP TABLE IF EXISTS `u_wallet`;

CREATE TABLE `u_wallet` (
  `walletId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '钱包主键',
  `userId` bigint(20) DEFAULT NULL COMMENT '用户Id',
  `balance` decimal(15,4) DEFAULT '0.0000' COMMENT '钱包余额',
  `profitMoney` decimal(15,4) DEFAULT '0.0000' COMMENT '总收益',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `createUser` varchar(50) DEFAULT NULL COMMENT '创建人',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `updateUser` varchar(50) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`walletId`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='用户钱包表';

/*Data for the table `u_wallet` */

insert  into `u_wallet`(`walletId`,`userId`,`balance`,`profitMoney`,`createTime`,`createUser`,`updateTime`,`updateUser`) values (1,1,'0.0000','0.0000','2019-01-16 00:01:45','admin',NULL,NULL),(2,2,'7300.0000','0.0000','2019-01-16 00:01:45','admin','2019-01-21 21:05:41','admin');

/*Table structure for table `u_wallet_change` */

DROP TABLE IF EXISTS `u_wallet_change`;

CREATE TABLE `u_wallet_change` (
  `historyId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '历史记录主键ID',
  `userId` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `userName` varchar(50) DEFAULT NULL COMMENT '用户名称',
  `operatorType` varchar(100) DEFAULT NULL COMMENT '操作类型,提现,充值,分红....',
  `operatorName` varchar(100) DEFAULT NULL COMMENT '操作名称,提现,充值,分红....',
  `operatorMoney` decimal(15,4) DEFAULT '0.0000' COMMENT '操作金额',
  `balance` decimal(15,4) DEFAULT '0.0000' COMMENT '钱包余额',
  `relationId` bigint(20) DEFAULT NULL COMMENT '关联相关的订单号',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注信息',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `createUser` varchar(50) DEFAULT NULL COMMENT '创建者',
  PRIMARY KEY (`historyId`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8 COMMENT='钱包操作记录表';

/*Data for the table `u_wallet_change` */

insert  into `u_wallet_change`(`historyId`,`userId`,`userName`,`operatorType`,`operatorName`,`operatorMoney`,`balance`,`relationId`,`remark`,`createTime`,`createUser`) values (31,2,'fury','PURCHASE_CAR_SPACE_KEY','购买车位','-1000.0000','9000.0000',1,NULL,'2019-01-21 20:53:39','admin'),(32,2,'fury','WITHDRAW_OUT_BACK_KEY','提现回退','90.0000','9090.0000',1,NULL,'2019-01-21 20:56:11','admin'),(33,2,'fury','WITHDRAW_OUT_BACK_FEE_KEY','提现回退手续费','10.0000','9100.0000',1,NULL,'2019-01-21 20:56:11','admin'),(34,2,'fury','RECHARGE_IN_KEY','充值入账','100.0000','9200.0000',1,NULL,'2019-01-21 20:56:26','admin'),(37,2,'fury','PURCHASE_CAR_SPACE_KEY','购买车位','-1000.0000','8200.0000',1,NULL,'2019-01-21 21:01:41','admin'),(38,2,'fury','RECHARGE_IN_KEY','充值入账','100.0000','8300.0000',1,NULL,'2019-01-21 21:05:20','admin'),(39,2,'fury','PURCHASE_CAR_SPACE_KEY','购买车位','-1000.0000','7300.0000',1,NULL,'2019-01-21 21:05:42','admin');

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
  `status` int(1) DEFAULT '0' COMMENT '提现状态[0:待审核,1:已完成,2:转账异常,3:已取消]',
  `frontRemark` varchar(200) DEFAULT NULL COMMENT '用于显示给前端的备注信息',
  `backRemark` varchar(200) DEFAULT NULL COMMENT '用于显示给后端的备注信息(如果有调用第三方接口的话)',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `createUser` varchar(50) DEFAULT NULL COMMENT '创建者',
  `updateTime` datetime DEFAULT NULL COMMENT '修改时间',
  `updateUser` varchar(50) DEFAULT NULL COMMENT '修改者',
  PRIMARY KEY (`withdrawId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='提现记录表';

/*Data for the table `u_withdraw` */

insert  into `u_withdraw`(`withdrawId`,`userId`,`userMobile`,`userName`,`parentId`,`groupUserIds`,`withdrawMoney`,`withdrawFee`,`withdrawRealMoney`,`alipayName`,`alipayMobile`,`transferAccounts`,`status`,`frontRemark`,`backRemark`,`createTime`,`createUser`,`updateTime`,`updateUser`) values (1,2,'13333333333','fury',1,'0,1,2,','100.0000','10.0000','90.0000','13333333333','13333333333','1233333',2,'123',NULL,'2019-01-21 11:11:11','admin','2019-01-21 20:56:11','admin');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
