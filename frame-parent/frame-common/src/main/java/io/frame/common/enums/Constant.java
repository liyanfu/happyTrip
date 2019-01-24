
package io.frame.common.enums;

/**
 * 常量
 * 
 * @author fury
 *
 */
public class Constant {

	/**
	 * 读图片方法名
	 */
	public static final String readImg = "/readImg?path=";

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
		ONE(1),
		/** 2 */
		TWO(2),
		/** 3 */
		THREE(3);
		private Integer value;

		Status(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}
	}

	/**
	 * 0:禁用,1:启用
	 */
	public enum PaymentKey {

		/** 0未支付 */
		PAYMENT_ALIPAY_KEY("支付宝", 1L),
		/** 1 */
		PAYMENT_WALLET_KEY("余额", 2L);
		private String name;
		private Long value;

		PaymentKey(String name, Long value) {
			this.name = name;
			this.value = value;
		}

		public static String getName(Long value) {
			PaymentKey[] list = PaymentKey.values();
			for (PaymentKey paymentKey : list) {
				if (paymentKey.value.equals(value)) {
					return paymentKey.name;
				}
			}
			return null;
		}

		public Long getValue() {
			return value;
		}

		public String getName() {
			return name;
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
	 * 提现状态
	 */
	public enum WithdrawStatus {
		/** 0待审核 */
		ZERO("待审核", 0),
		/** 1 已完成 */
		ONE("已完成", 1),
		/** 2异常 */
		TWO("提现异常", 2),
		/** 已取消 */
		THREE("已取消", 3);
		private String name;
		private Integer value;

		WithdrawStatus(String name, Integer value) {
			this.name = name;
			this.value = value;
		}

		public static String getName(Integer value) {
			WithdrawStatus[] list = WithdrawStatus.values();
			for (WithdrawStatus withdrawStatus : list) {
				if (withdrawStatus.value == value) {
					return withdrawStatus.name;
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
	 * 钱包帐变类型
	 * 
	 */
	public enum ChangeType {
		/** 充值入账 */
		RECHARGE_IN_KEY("充值入账", "RECHARGE_IN_KEY"),
		/** 汽车收益 */
		CAR_PROFIT_KEY("汽车收益", "CAR_PROFIT_KEY"),
		/** 全民福利 */
		ALL_PEOPLE_WELFARE_KEY("全民福利", "ALL_PEOPLE_WELFARE_KEY"),
		/** 全球分红 */
		GLOBAL_BONUS_KEY("全球分红", "GLOBAL_BONUS_KEY"),
		/** 领导团队奖 */
		TEAM_LEADERSHIP_AWARD_KEY("领导团队奖", "TEAM_LEADERSHIP_AWARD_KEY"),
		/** 特别贡献奖 */
		SPECIAL_CONTRIBUTION_AWARD_KEY("特别贡献奖", "SPECIAL_CONTRIBUTION_AWARD_KEY"),
		/** 提现出款 */
		WITHDRAW_OUT_KEY("提现出款", "WITHDRAW_OUT_KEY"),
		/** 购买车位 下单 */
		PURCHASE_CAR_SPACE_KEY("购买车位", "PURCHASE_CAR_SPACE_KEY"),
		/** 人工充值 */
		ARTIFICIAL_RECHARGE_KEY("人工充值", "ARTIFICIAL_RECHARGE_KEY"),
		/** 人工扣款 */
		MANUAL_DEDUCTION_KEY("人工扣款", "MANUAL_DEDUCTION_KEY"),
		/** 提现手续费 */
		WITHDRAW_OUT_FEE_KEY("提现手续费", "WITHDRAW_OUT_FEE_KEY"),
		/** 提现回退 */
		WITHDRAW_OUT_BACK_KEY("提现回退", "WITHDRAW_OUT_BACK_KEY"),
		/** 提现回退手续费 */
		WITHDRAW_OUT_BACK_FEE_KEY("提现回退手续费", "WITHDRAW_OUT_BACK_FEE_KEY");
		private String name;
		private String value;

		ChangeType(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public static String getName(String value) {
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

	/**
	 * 配置项Type类型
	 * 
	 */
	public enum ConfigType {
		/** 充值时间段 */
		WITHDRAW("充值时间段", "withdraw"),
		/** 最小充值额 */
		SYSTEM("最小充值额", "system"),
		/** 最大充值额 */
		RECHARGE("最大充值额", "recharge"),
		/** 充值手续费 */
		WELFARE("福利", "welfare");
		private String name;
		private String value;

		ConfigType(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public static String getName(String value) {
			ConfigType[] list = ConfigType.values();
			for (ConfigType configType : list) {
				if (configType.value.equals(value)) {
					return configType.name;
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

	/**
	 * 充值Key类型 ,name 为返回的提示信息
	 */
	public enum RechargeKey {
		/** 充值时间段 */
		RECHARGE_TIME_RANGE_KEY("不在充值时间段", "RECHARGE_TIME_RANGE_KEY"),
		/** 最小充值额 */
		RECHARGE_MIN_KEY("小于最小充值额", "RECHARGE_MIN_KEY"),
		/** 最大充值额 */
		RECHARGE_MAX_KEY("超过最大充值额", "RECHARGE_MAX_KEY"),
		/** 充值手续费 */
		RECHARGE_FEE_KEY("充值手续费", "RECHARGE_FEE_KEY"),
		/** 充值开关 */
		RECHARGE_SWITCH_KEY("充值接口关闭", "RECHARGE_SWITCH_KEY"),
		/** 充值二维码收款图片 */
		RECHARGE_QRCODE_KEY("充值二维码收款图片", "RECHARGE_QRCODE_KEY");
		private String name;
		private String value;

		RechargeKey(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public static String getName(String value) {
			RechargeKey[] list = RechargeKey.values();
			for (RechargeKey rechargeKey : list) {
				if (rechargeKey.value.equals(value)) {
					return rechargeKey.name;
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

	/**
	 * 提现Key类型 ,name 为返回的提示信息
	 */
	public enum WithdrawKey {
		/** 提现时间段 */
		WITHDRAW_TIME_RANGE_KEY("不在提现时间段", "WITHDRAW_TIME_RANGE_KEY"),
		/** 最小提现额 */
		WITHDRAW_MIN_KEY("小于最小提现额", "WITHDRAW_MIN_KEY"),
		/** 最大提现额 */
		WITHDRAW_MAX_KEY("超过最大提现额", "WITHDRAW_MAX_KEY"),
		/** 提现手续费 */
		WITHDRAW_FEE_KEY("提现手续费", "WITHDRAW_FEE_KEY"),
		/** 提现开关 */
		WITHDRAW_SWITCH_KEY("提现接口关闭", "WITHDRAW_SWITCH_KEY"),
		/** 提现次数 */
		WITHDRAW_COUNT_KEY("超过提现次数", "WITHDRAW_COUNT_KEY");
		private String name;
		private String value;

		WithdrawKey(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public static String getName(String value) {
			WithdrawKey[] list = WithdrawKey.values();
			for (WithdrawKey withdrawKey : list) {
				if (withdrawKey.value.equals(value)) {
					return withdrawKey.name;
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

	/**
	 * 系统Key类型
	 */
	public enum SystemKey {
		/** 客服二维码 */
		SYSTEM_CUSTOMER_SERVICE_IMG_KEY("客服二维码", "SYSTEM_CUSTOMER_SERVICE_IMG_KEY"),
		/** 注册域名 */
		SYSTEM_REGISTER_DOMAIN_KEY("注册域名", "SYSTEM_REGISTER_DOMAIN_KEY"),
		/** 推广域名 */
		SYSTEM_SPREAD_DOMAIN_KEY("推广域名", "SYSTEM_SPREAD_DOMAIN_KEY"),
		/** 公司介绍 */
		SYSTEM_COMPANY_INTRODUCE_KEY("公司介绍", "SYSTEM_COMPANY_INTRODUCE_KEY");
		private String name;
		private String value;

		SystemKey(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public static String getName(String value) {
			SystemKey[] list = SystemKey.values();
			for (SystemKey systemKey : list) {
				if (systemKey.value.equals(value)) {
					return systemKey.name;
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

	/**
	 * 福利Key
	 */
	public enum WelfareKey {
		/** 全球分红奖 */
		GLOBAL_BONUS_KEY("全球分红", "GLOBAL_BONUS_KEY"),
		/** 领导团队奖 */
		TEAM_LEADERSHIP_AWARD_KEY("领导团队奖", "TEAM_LEADERSHIP_AWARD_KEY"),
		/** 特别贡献奖 */
		SPECIAL_CONTRIBUTION_AWARD_KEY("特别贡献奖", "SPECIAL_CONTRIBUTION_AWARD_KEY");
		private String name;
		private String value;

		WelfareKey(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public static String getName(String value) {
			WelfareKey[] list = WelfareKey.values();
			for (WelfareKey welfareKey : list) {
				if (welfareKey.value.equals(value)) {
					return welfareKey.name;
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

	/**
	 * 定时任务福利派发开关
	 */
	public enum WelfareSwitch {

		/** 汽车收益福利天返开关 */
		WELFARE_SWITCH_DAILY_KEY("福利天返开关", "WELFARE_SWITCH_DAILY_KEY"),
		/** 全民福利时返开关 */
		WELFARE_SWITCH_HOUR_KEY("福利天返开关", "WELFARE_SWITCH_HOUR_KEY"),
		/** 全球分红奖开关 */
		GLOBAL_BONUS_KEY("全球分红开关", "GLOBAL_BONUS_KEY"),
		/** 领导团队奖开关 */
		TEAM_LEADERSHIP_AWARD_KEY("领导团队奖开关", "TEAM_LEADERSHIP_AWARD_KEY"),
		/** 特别贡献奖开关 */
		SPECIAL_CONTRIBUTION_AWARD_KEY("特别贡献奖开关", "SPECIAL_CONTRIBUTION_AWARD_KEY");
		private String name;
		private String value;

		WelfareSwitch(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public static String getName(String value) {
			WelfareSwitch[] list = WelfareSwitch.values();
			for (WelfareSwitch bean : list) {
				if (bean.value.equals(value)) {
					return bean.name;
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

	/**
	 * 定时任务状态
	 */
	public enum ScheduleStatus {
		/**
		 * 正常
		 */
		NORMAL(0),
		/**
		 * 暂停
		 */
		PAUSE(1);

		private int value;

		ScheduleStatus(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}
}
