package io.frame.common.exception;

/**
 * 错误信息
 * 
 * @author fury
 *
 */
public class ErrorCode {

	/** token不能为空 */
	public static String TOKEN_NOT_NULL = "请先登录";
	/** token失效,请重新登录 */
	public static String TOKEN_IS_EXPIRE = "登录超时,请重新登录";
	/** 账号或密码错误 */
	public static String USERNAME_OR_USERPASS_ERROR = "账号或密码错误";
	/** 账号被禁用,请联系管理员 */
	public static String USERNAME_DISABLE = "账号被禁用,请联系管理员";
	/** 账号已存在 */
	public static String USER_MOBILE_EXIST = "账号已存在";
	/** 用户名已存在 */
	public static String USERNAME_EXIST = "用户名已存在";
	/** 用户名不存在 */
	public static String USERNAME_NOT_EXIST = "用户名不存在";
	/** 用户账户不存在 */
	public static String USER_ACCOUNT_NOT_EXIST = "用户账户不存在";
	/** 验证码已失效 */
	public static String VERIFICATIONCODE_IS_EXPIRE = "验证码已失效";
	/** 验证码错误 */
	public static String VERIFICATIONCODE_IS_ERROR = "验证码错误";
	/** 原始密码错误 */
	public static String ORIGINAL_PASS_ERROR = "原始密码错误";
	/** 请选择图片 */
	public static String PLEASE_SELECT_IMAGE = "请选择图片";
	/** 获取参数失败 */
	public static String GET_PARAMS_ERROR = "获取参数失败";
	/** 参数不能为空 */
	public static String PARAMS_IS_NOT_EMPTY = "参数不能为空";
	/** 获取信息 */
	public static String GET_INFO_FAILED = "获取信息失败";
	/** 操作失败 */
	public static String OPERATE_FAILED = "操作失败,请联系管理员";
	/** 父级账号不存在或已禁用 */
	public static String RECOMMEND_MOBILE_NOT_EXIST = "父级账号不存在或已禁用";

	/** 金额不能为负数 */
	public static String THE_AMOUNT_CANNOT_BE_NEGATIVE = "金额不能为负数";

	/** 余额不足 */
	public static String INSUFFICIENT_BALANCE_OF_GOLDCOINS = "余额不足";

	/** 已存在类型和名称相同商品 */
	public static String PRODUCT_TYPE_ANDM_NAME_EXIST = "已存在类型和名称相同商品";

	/** 存在该类型的商品,无法删除 */
	public static String EXIST_THIS_PRODUCT = "存在该类型的商品,无法删除";

	/** 上传失败 */
	public static String UPLOAD_ERROR = "上传失败";

	/** 请先下架商品 */
	public static String PLEASE_DOWN_PRODUCT = "请先下架商品";

	/** 只能修改待支付的订单 */
	public static String ONLY_UPDATE_ZERO_ORDER_STATUS = "只能修改待支付的订单";

	/** 只能将待支付订单修改为收益中 */
	public static String ONLY_UPDATE_ONE_ORDER_STATUS = "只能将待支付订单修改为收益中 ";

	/** 订单正在收益中 无法删除 */
	public static String ORDER_IS_OPEN = " 订单正在收益中无法删除 ";

}
