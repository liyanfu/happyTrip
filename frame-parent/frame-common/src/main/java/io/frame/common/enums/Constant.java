
package io.frame.common.enums;

/**
 * 常量
 * 
 * @author fury
 *
 */
public class Constant {

	/**
	 * 云配置类型
	 */
	public enum CloudEnum {
	/** 七牛云 */
	QINIU(1),
	/** 阿里云 */
	ALIYUN(2),
	/** 腾讯云 */
	QCLOUD(3);
		private int value;

		CloudEnum(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	/**
	 * 配置信息表键
	 */
	public enum ConfigEnum {
		/** 送给新创建的用户的银币数量 */
		NEW_CREATE_USER_GIVE_SILVERCOINS("NEW_CREATE_USER_GIVE_SILVERCOINS"), //
		/** 云储存配置信息 */
		CLOUD_STORAGE_CONFIG_KEY("CLOUD_STORAGE_CONFIG_KEY");//

		private String value;

		ConfigEnum(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	/**
	 * 排序使用
	 */
	public enum Sort {
		/** 升序 */
		ASC(" ASC "),
		/** 降序 */
		DESC(" DESC "),
		/** 空格 */
		SPACE("  ");
		private String value;

		Sort(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	/**
	 * 0:禁用,1:启用
	 */
	public enum Status {

		/** 0 */
		ZERO(0),
		/** 1 */
		ONE(1);
		private Integer value;

		Status(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}
	}

	/**
	 * 数字
	 */
	public enum Numbers {

		/** 0 */
		ZERO(0),
		/** 1 */
		ONE(1);
		private Integer value;

		Numbers(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}
	}

	/**
	 * 订单状态
	 */
	public enum OrderStatus {
		/** 0未支付 */
		ZERO("未支付", 0),
		/** 1 */
		ONE("收益中", 1),
		/** 2已完成 */
		TWO("已完成", 2),
		/** 3 已取消 */
		THREE("已取消", 3);
		private String name;
		private Integer value;

		OrderStatus(String name, Integer value) {
			this.name = name;
			this.value = value;
		}

		public static String getName(Integer value) {
			OrderStatus[] list = OrderStatus.values();
			for (OrderStatus orderStatus : list) {
				if (orderStatus.value == value) {
					return orderStatus.name;
				}
			}
			return null;
		}

		public Integer getValue() {
			return value;
		}

		public String getName() {
			return name;
		}
	}

	/**
	 * 金额类型
	 * 
	 */
	public enum ChangeType {
		/** 1:充值进账 */
		RECHARGE_IN_KEY("充值进账", "RECHARGE_IN_KEY"),
		/** 2:汽车收益 */
		CAR_PROFIT_KEY("汽车收益", "CAR_PROFIT_KEY"),
		/** 3.全球分红 */
		GLOBAL_BONUS_KEY("全球分红", "GLOBAL_BONUS_KEY"),
		/** 4:竞猜返还 */
		FOUR("竞猜返还", 4),
		/** 5:挑战支出 */
		FIVE("挑战支出", 5),
		/** 6:挑战盈利 */
		SIX("挑战盈利", 6),
		/** 7:挑战返还 */
		SEVEN("挑战返还", 7),
		/** 8:转账支出 */
		EIGHT("转账支出", 8),
		/** 9:转账收入 */
		NINE("转账收入", 9),
		/** 10:签到赠送 */
		TEN("签到赠送", 10),
		/** 11:手续费 */
		ELEVEN("手续费", 11);
		private String name;
		private String value;

		ChangeType(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public static String getName(Integer value) {
			ChangeType[] list = ChangeType.values();
			for (ChangeType changeType : list) {
				if (changeType.value.equals(value)) {
					return changeType.name;
				}
			}
			return null;
		}

		public String getValue() {
			return value;
		}

		public String getName() {
			return name;
		}
	}

	/*********************************************
	 * 下面的参数枚举后台使用
	 *********************************************/

	/** 超级管理员ID */
	public static final int SUPER_ADMIN = 1;
	/** 数据权限过滤 */
	public static final String SQL_FILTER = "sql_filter";

	/**
	 * 菜单类型
	 */
	public enum MenuType {
		/**
		 * 目录
		 */
		CATALOG(0),
		/**
		 * 菜单
		 */
		MENU(1),
		/**
		 * 按钮
		 */
		BUTTON(2);

		private int value;

		MenuType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

}
