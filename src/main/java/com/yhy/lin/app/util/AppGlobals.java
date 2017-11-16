package com.yhy.lin.app.util;

/**
 * Description : app全局常量 (也包括web端的一些常量)
 * 
 * @author Timer
 * @date 2017年5月4日 上午9:34:30
 */
public interface AppGlobals {
	
	/** 服务器地址 */
	public static final String SERVER_BASE_URL = "www.youngloong.com";
	
	public static final boolean IS_TEST_ENVIRONMENT = true;
	
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
	public static final String TOKEN_ERROR_MSG = "token无效！";
	
	/** 参数异常 */
	public static final String PARAMETER_ERROR = "997";
	
	/** 参数为空 */
	public static final String PARAMETER_EMPTY_ERROR = "996";
	
	/** 验证码无效 */
	public static final String PARAMETER_MSG_CODE_INVALID = "995";
	
	/** 验证码无效 */
	public static final String PARAMETER_MSG_CODE_INVALID_MSG = "验证码无效";
	
	/** 参数为空 */
	public static final String PARAMETER_EMPTY_ERROR_MSG = "参数为空";

	// 自定义异常的时候传入进去了，所以这里暂时不需要定义
	// /** 参数异常消息 */
	// public static final String PARAMETER_ERROR_MSG = "参数为空！";

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

	/** windows图片base路径 */
	public static final String IMAGE_BASE_FILE_PATH = "C:/nginx";

	/** linux图片base路径 */
//	public static final String IMAGE_BASE_FILE_PATH = "/usr/local/nginx";

	/** app客户头像路径 */
	public static final String APP_USER_FILE_PATH = "/image/user/";

	/** 司机头像路径 */
	public static final String DRIVER_FILE_PATH = "/image/driver/";

	/** 二维码图片路径 */
	public static final String QR_CODE_FILE_PATH = "/image/QRCode/";
	
	/** 渠道商图片路径 */
	public static final String DEALER_FILE_PATH = "/image/dealer/";

	/** 阿里大鱼服务器url */
	//public static final String SERVCR_URL = "https://eco.taobao.com/router/rest";
	public static final String SERVCR_URL = "dysmsapi.aliyuncs.com";
	

	/** 阿里大鱼测试url */
	public static final String TEST_SERVCR_URL = "https://gw.api.tbsandbox.com/router/rest";

	/** 阿里大鱼appKey */
	//public static final String APP_KEY = "23850099"; // 个人23785248
	public static final String APP_KEY = "LTAIqLN1Q1cC2LWG"; // 个人23785248

	/** 阿里大鱼appSecret */
	//public static final String APP_SECRET = "5be5a4659944c0ce41129ad3fc2f3fad";// 个人fb8a26bf549a4d68082338d8a6391007
	public static final String APP_SECRET = "3nxySsYhljnlhSX68mKzIxK6XOHGdx";

	/** 手机号验证 */
	public static final String CHECK_PHONE = "^1[34578]\\d{9}$";

	/** 微信第三方支付 */
	/** 微信服务器验证token */
	public static final String SERVER_TOKEN = "longyouchuxing2017";
	
	/** 退款url */
	public static final String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";

	/** 获取access_token url */
	public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=%1&appid=%2&secret=%3";
	
	/** 微信appId */
//	public static final String WECHAT_ID = "wx1775577d8050cf73";
	public static final String WECHAT_ID = "wx8833558481d9167e";
	
	/** 微信第三方唯一凭证密码 */
//	public static final String WECHAT_APP_SECRET = "807e12ebcb667f66820d4b4eb9118467";
	public static final String WECHAT_APP_SECRET = "295837c0efff32c1bd73534458f9b45c";

	/** 收款商家商户号 */
//	public static final String MCH_ID = "1481189932";
	public static final String MCH_ID = "1489142572";

	/** 收款商户后台进行配置，登录微信商户平台–账户设置–安全设置–api安全，设置32位key值 */
//	public static final String WECHAT_KEY = "GZLYXNYYYYXGS20170601jiesongyewu";
	public static final String WECHAT_KEY = "xiaolongshenzhoukeji2017HONHHUOX";
	
	/** 证书路径 */
	public static final String CERT_PATH_LOCATION = "WEB-INF/cert/apiclient_cert.p12";

	/**
	 * 交易类型
	 */
	public static final String TRADE_TYPE = "JSAPI";

	public static final String SIGN_TYPE = "MD5";

	/**
	 * 商品名称
	 */
	public static final String SP_NAME = "小龙出行";
	
	
	/** 项目管理员名称（开发） */
	public static final String XM_ADMIN_NAME = "admin1";
	
	/**
	 * web平台角色权限
	 */
	/** 项目管理员（开发） */
	public static final String XM_ADMIN = "xmAdmin";
	
	/** 运营专员 */
	public static final String OPERATION_SPECIALIST = "operationS";

	/** 运营经理 */	
	public static final String OPERATION_MANAGER = "operationM";

	/** 商务专员 */
	public static final String COMMERCIAL_SPECIALIST = "commercialS";

	/** 商务经理 */
	public static final String COMMERCIAL_MANAGER = "commercialM";

	/** 技术专员 */
	public static final String TECHNICAL_SPECIALIST = "technicalS";

	/** 技术经理 */
	public static final String TECHNICAL_MANAGER = "technicalM";

	/** 平台线路审核员 */
	public static final String PLATFORM_LINE_AUDIT = "lineAudit";

	/** 平台退款审核员 */
	public static final String PLATFORM_REFUND_AUDIT = "refundAudit";
	
	/** 平台渠道商审核员 */
	public static final String PLATFORM_DEALER_AUDIT = "dealerAudit";
	
	/** 子公司管理员 */
	public static final String SUBSIDIARY_ADMIN = "companyAdmin";
	
	/**
	 * web平台组织机构类型
	 */
	/** 公司 */
	public static final String ORG_COMPANY_TYPE = "1";
	/** 子公司 */
	public static final String ORG_SUBSIDIARY_TYPE = "2";
	/** 部门 */
	public static final String ORG_DEPARTMENT_TYPE = "3";
	/** 岗位 */
	public static final String ORG_JOB_TYPE = "4";
	/** 入驻公司 */
	public static final String ORG_ENTER_COMPANY_TYPE = "5";
	
}
