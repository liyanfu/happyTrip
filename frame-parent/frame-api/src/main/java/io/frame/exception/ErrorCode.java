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
	/** 用户名已存在 */
	public static String USERNAME_EXIST = "用户名已存在";
	/** 用户名不存在 */
	public static String USERNAME_NOT_EXIST = "用户名不存在";
	/** 用户账户不存在 */
	public static String USER_ACCOUNT_NOT_EXIST = "用户账户不存在";
	/** 金币不足,请先充值 */
	public static String GOLDCOIN_IS_NOT_ENOUGH = "金币不足,请先充值";
	/** 金币不足,交易失败 */
	public static String GOLDCOIN_IS_NOT_ENOUGH_TRANSACTION_FAILURE = "金币不足";
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
	/** 挑战金额不能小于0 */
	public static String CHALLENGEMONEY_NOT_LESSTHAN_ZERO = "挑战金额不能小于0";
	/** 头像修改失败 */
	public static String UPDATE_HEAD_IMAGE_ERROR = "头像修改失败";

	/** 赛事不存在 */
	public static String MATCH_NOT_EXIST = "赛事不存在";

	/** 赛事已开始 */
	public static String MATCH_STARTED = "赛事已开始";

	/** 赛事已结束 */
	public static String MATCH_END = "赛事已结束";

	/** 赛事已取消 */
	public static String MATCH_CANCEL = "赛事已取消";

	/** 竞猜金额已满 */
	public static String GUESS_MONEY_IS_FULL = "竞猜金额已满";

	/** 挑战金额大于可竞猜金额 */
	public static String CHALLENGEMONEY_GREATERTHAN_GUESSMONEY = "挑战金额大于可竞猜金额";

	/** 不能挑战自己的竞猜 */
	public static String NOT_CHALLENGE_MYSELF_GUSEE = "不能挑战自己的竞猜";

	/** 竞猜金额与赔率之积至少大于等于1 */
	public static String GUESSMONEY_AND_MULTIPLE_MUST_GREATERTHAN_EQ_ONE = "竞猜金额与赔率之积至少大于等于1";

	/** 竞猜赔率必须>=0 */
	public static String GUESSMULTIPLE_MUST_GREATERTHAN_EQ_ZERO = "竞猜赔率必须大于等于0";

	/** 新增帐变失败 */
	public static String ADD_ACCOUNTCHANGE_FAILED = "新增帐变失败";

	/** 新增用户账户失败 */
	public static String ADD_USERACCOUNT_FAILED = "新增用户账户失败";

	/** 获取用户账户失败 */
	public static String GET_USERACCOUNT_FAILED = "获取用户账户失败";

	/** 充值金币失败 */
	public static String REACHRGE_GOLDCOIN_FAILED = "充值金币失败";

	/** 增加银币失败 */
	public static String ADD_SILVERCOIN_FAILED = "增加银币失败";

	/** 增加金币失败 */
	public static String ADD_GOLDCOIN_FAILED = "增加金币失败";

	/** 获取金币失败 */
	public static String GET_GOLDCOIN_FAILED = "获取金币失败";

	/** 获取银币失败 */
	public static String GET_SILVERCOIN_FAILED = "获取银币失败";

	/** 转账失败 */
	public static String TRANSFER_FAILED = "转账失败";

	/** 获取信息 */
	public static String GET_INFO_FAILED = "获取信息失败";

	/** 操作失败 */
	public static String OPERATE_FAILED = "操作失败,请联系管理员";

	/** 收款用户不存在 */
	public static String RECEIVE_USER_NOT_EXIST = "收款用户不存在";

	/** 收款用户被禁用 */
	public static String RECEIVE_USER_DISABLE = "收款用户被禁用";

	/** 不能转账给自己 */
	public static String TRANSFER_NOT_MYSELF = "不能转账给自己";

	/** 不能转账给自己 */
	public static String GUESS_NOT_EXIST = "竞猜不存在";

	/** 请求支付接口失败 */
	public static String HTTP_PAY_ERROR = "请求支付接口失败";

	/** 充值接口已不可用 */
	public static String PAY_CONFIG_IS_NOT_USE = "充值接口已不可用";

	/** 充值接口已被关闭 */
	public static String PAY_CONFIG_BE_CLOSED = "充值接口已被关闭";

	/** 提交充值申请失败 */
	public static String SUBMIT_RECHARGE_FAILED = "提交充值申请失败";

	/** 提交充值申请成功 */
	public static String SUBMIT_RECHARGE_SUCCESS = "提交充值申请成功";

}
