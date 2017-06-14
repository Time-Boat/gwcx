package com.yhy.lin.app.util;

/**
 * Description : app全局常量
 * 
 * @author Time
 * @date 2017年5月4日 上午9:34:30
 */
public interface AppGlobals {

	/** app 返回状态 */
	/** 返回成功 */
	public static final String APP_SUCCESS = "000";
	/** 请求成功消息 */
	public static final String APP_SUCCESS_MSG = "请求成功";
	
	/** 系统异常 */
	public static final String SYSTEM_ERROR = "999";
	/** 系统异常消息 */
	public static final String SYSTEM_ERROR_MSG = "系统异常！";
	
	/** token异常 */
	public static final String TOKEN_ERROR = "998";
	/** token异常消息 */
	public static final String TOKEN_ERROR_MSG = "token为空！";
	
	/** 参数异常 */
	public static final String PARAMETER_ERROR = "997";
	
	//自定义异常的时候传入进去了，所以这里暂时不需要定义
//	/** 参数异常消息 */
//	public static final String PARAMETER_ERROR_MSG = "参数为空！";
	
	
	/** 订单类型 */
	/** 接机 */
	public static final String AIRPORT_TO_DESTINATION_TYPE = "2";
	/** 送机 */
	public static final String DESTINATION_TO_AIRPORT_TYPE = "3";
	/** 接火车 */
	public static final String TRAIN_TO_DESTINATION_TYPE = "4";
	/** 送火车 */
	public static final String DESTINATION_TO_TRAIN_TYPE = "5";

	/** 班车 */
	/** 包车 */

	/** web外部的文件存储路径 */
	public static final String EXTERNAL_FILE_PATH = "D:/javaWeb/demo/nginx/gwcx/image";
	
	/** app客户头像路径 */
	public static final String APP_USER_FILE_PATH = "userImage";
	
	/** web路径 */
	public static final String WEB_FILE_PATH = "image/";
	
	
	/** 阿里大鱼服务器url */
	public static final String SERVCR_URL = "https://eco.taobao.com/router/rest";

	/** 阿里大鱼测试url */
	public static final String TEST_SERVCR_URL = "https://gw.api.tbsandbox.com/router/rest";

	/** 阿里大鱼appKey */
	public static final String APP_KEY = "23850099"; //个人23785248

	/** 阿里大鱼appSecret */
	public static final String APP_SECRET = "5be5a4659944c0ce41129ad3fc2f3fad";//个人fb8a26bf549a4d68082338d8a6391007

	
	/** 手机号验证 */
	public static final String CHECK_PHONE = "^1[34578]\\d{9}$";
	
	
	
	/** 微信第三方支付*/
	/** appId*/
	public static final String WECHAT_ID = "wx1775577d8050cf73";
	
	/** 收款商家商户号*/
	public static final String MCH_ID = "1481189932";
	
	/** 第三方唯一凭证密码*/
	public static final String WECHAT_APP_SECRET = "807e12ebcb667f66820d4b4eb9118467";
	
	/** 收款商户后台进行配置，登录微信商户平台–账户设置–安全设置–api安全，设置32位key值*/
	public static final String WECHAT_KEY = "GZLYXNYYYYXGS20170601jiesongyewu";
	
	
	
	/**
	 * 交易类型
	 */
	public static final String TRADE_TYPE = "JSAPI";
	
	public static final String SIGN_TYPE = "MD5";
	
	
	
	
	
	
	
}
