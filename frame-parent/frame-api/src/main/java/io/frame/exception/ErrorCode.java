package io.frame.exception;

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
	/** 推荐人账号不存在 */
	public static String RECOMMEND_MOBILE_NOT_EXIST = "推荐人账号不存在";
	/** 账号不存在 */
	public static String USERNAME_NOT_EXIST = "账号不存在";
	/** 用户钱包不存在 */
	public static String USER_ACCOUNT_NOT_EXIST = "用户钱包不存在";
	/** 金币不足,请先充值 */
	public static String GOLDCOIN_IS_NOT_ENOUGH = "金币不足,请先充值";
	/** 银币不足,无法进行试玩 */
	public static String SILVERCOIN_IS_NOT_ENOUGH = "银币不足";
	/** 两次新密码不一致 */
	public static String NEW_USERPASS_DIFFERENCE = "两次新密码不一致";
	/** 请输入手机号 */
	public static String PLEASE_INPUT_MOBILE = "请输入手机号";
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

	/** 新增帐变失败 */
	public static String ADD_ACCOUNTCHANGE_FAILED = "新增帐变失败";

	/** 新增用户账户失败 */
	public static String ADD_USERACCOUNT_FAILED = "新增用户账户失败";

	/** 获取用户账户失败 */
	public static String GET_USERACCOUNT_FAILED = "获取用户账户失败";

	/** 获取信息失败 */
	public static String GET_INFO_FAILED = "获取信息失败";

	/** 操作失败 */
	public static String OPERATE_FAILED = "操作失败,请联系管理员";

	/** 充值接口已不可用 */
	public static String PAY_CONFIG_IS_NOT_USE = "充值接口已不可用";

	/** 充值接口已被关闭 */
	public static String PAY_CONFIG_BE_CLOSED = "充值接口已被关闭";

	/** 提交充值申请失败 */
	public static String SUBMIT_RECHARGE_FAILED = "提交充值申请失败";

	/** 提交充值申请成功 */
	public static String SUBMIT_RECHARGE_SUCCESS = "提交充值申请成功";

}
