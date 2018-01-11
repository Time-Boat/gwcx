package com.yhy.lin.app.util;

import org.apache.log4j.Logger;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import com.yhy.lin.app.entity.AppMessageListEntity;


/**
 * Description :短信发送工具类
 * 
 * @author Administrator
 * @date 2017年5月31日 上午11:50:11
 */
public class SendMessageUtil {

	private static final Logger logger = Logger.getLogger(SendMessageUtil.class);
	// 普通用户登录验证码信息模板
	//public static final String TEMPLATE_SMS_CODE = "SMS_66640251";//个人SMS_63766002
	public static final String TEMPLATE_SMS_CODE = "SMS_103040027";//个人SMS_103040027
	// 验证码信息模板对应签名
	//public static final String TEMPLATE_SMS_CODE_SIGN_NAME = "登录验证";
	public static final String TEMPLATE_SMS_CODE_SIGN_NAME = "小龙出行";
	// 修改密码的验证码信息模板
	public static final String TEMPLATE_MODIFY_PASSWORD_SMS_CODE = "SMS_105955007";
	//订单提醒
	//public static final String TEMPLATE_SMS_CODE_SIGN_ORDER = "大鱼测试";
	public static final String TEMPLATE_SMS_CODE_SIGN_ORDER = "小龙出行";
	//接送机、接送火车订单提醒信息模板
	//public static final String TEMPLATE_ARRANGE_ORDER = "SMS_78580203";//SMS_78570092
	public static final String TEMPLATE_ARRANGE_ORDER = "SMS_102350065";//SMS_102350065
	//接送机订单提醒
	//public static final String TEMPLATE_ARRANGE_AIRORDER = "SMS_78745200";//SMS_78570092
	public static final String TEMPLATE_ARRANGE_AIRORDER = "SMS_110835253";//SMS_102220081
	//接送火车订单提醒
	//public static final String TEMPLATE_ARRANGE_TRORDER = "SMS_78935036";//SMS_78570092
	public static final String TEMPLATE_ARRANGE_TRORDER = "SMS_110845263";//SMS_102205078

	// 安排车辆信息模板
	//public static final String TEMPLATE_ARRANGE_CAR = "SMS_70420532";//SMS_69620002
	public static final String TEMPLATE_ARRANGE_CARS = "SMS_113120029";//SMS_102500006
	
	public static final String TEMPLATE_ARRANGE_CAR = "SMS_113090140";//SMS_102500006
	
	// 安排车辆信息模板对应签名
	//public static final String TEMPLATE_ARRANGE_CAR_SIGN_NAME = "变更验证";
	public static final String TEMPLATE_ARRANGE_CAR_SIGN_NAME = "小龙出行";
	
	//申请渠道商审核通过短信提醒
	public static final String TEMPLATE_ARRANGE_DEALER = "SMS_112470631";//SMS_110830263

	//订单退款通知
	public static final String TEMPLATE_ORDER_REFUND = "SMS_115675032";//SMS_110830263
	
	/**
	 * 发送短信
	 * @param mobile 		用户手机号
	 * @param paramKey		短信模板对应的key值
	 * @param paramValue	短信模板对应的value值
	 * @param templateCode	阿里大于上配置的模板id
	 * @param signName		模板签名
	 * @return boolean		用于判断短信是否发送成功
	 */
//	public static boolean sendMessages(String mobile, String[] paramKey, String[] paramValue, String templateCode , String signName) {
//
//		boolean b = false;
//
//		// 生成4位数的验证码
//		//		String code = StringUtil.numRandom(4);
//
//		TaobaoClient client = new DefaultTaobaoClient(AppGlobals.SERVCR_URL, AppGlobals.APP_KEY, AppGlobals.APP_SECRET);
//		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
//		req.setExtend("123456"); // 短信回传时的可以携带的信息 ？？？
//		req.setSmsType("normal"); // 短信类型
//		req.setSmsFreeSignName(signName); // 模板签名
//		StringBuffer ps = new StringBuffer("{");
//		for (int i = 0; i < paramKey.length; i++) {
//			ps.append("'" + paramKey[i] + "':'" + paramValue[i] + "',");
//		}
//		if (ps.length() > 1) {
//			ps.deleteCharAt(ps.length() - 1);
//		}
//		ps.append("}");
//		req.setSmsParamString(ps.toString());
//		req.setRecNum(mobile);
//		req.setSmsTemplateCode(templateCode);
//		AlibabaAliqinFcSmsNumSendResponse rsp = null;
//		try {
//			rsp = client.execute(req);
//			b = rsp.getResult().getSuccess();
//		} catch (ApiException e) {
//			e.printStackTrace();
//		}
//		return b;
//	}

	/**
	 * 把消息推送到用户的消息中心
	 * @param userId	用户id
	 * @param content	消息内容
	 * @param status	消息状态 0:否	 1:是
	 * @param type		消息类型 0：订单通知    1：普通消息通知
	 * @param orderId	订单id
	 */
	public static AppMessageListEntity buildAppMessage(String userId,String content, String status, String type, String orderId) {

		// 如果购买成功，将消息添加到消息中心
		AppMessageListEntity app = new AppMessageListEntity();
		app.setContent(content);
		app.setCreateTime(AppUtil.getCurTime());
		app.setStatus(status);
		app.setUserId(userId);
		app.setMsgType(type);
		app.setOrderId(orderId);
		if("0".equals(type)){
			app.setTitle(AppGlobals.ORDER_MESSAGE);
		}else if("1".equals(type)){
			app.setTitle(AppGlobals.BASIC_MESSAGE);
		}else if("2".equals(type)){
			app.setTitle(AppGlobals.REFUND_MESSAGE);
		}

		return app;

	}
	
	/**
	 * 发送短信
	 * @param mobile 		用户手机号
	 * @param paramKey		短信模板对应的key值
	 * @param paramValue	短信模板对应的value值
	 * @param templateCode	阿里大于上配置的模板id
	 * @param signName		模板签名
	 * @return boolean		用于判断短信是否发送成功
	 */
	public static boolean sendMessage(String mobile, String[] paramKey, String[] paramValue, String templateCode , String signName) {

		boolean b = false;
		
		if("1".equals(AppGlobals.IS_TEST_ENVIRONMENT)){
			return true;
		}
		
		final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
		final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）

		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", AppGlobals.APP_KEY,AppGlobals.APP_SECRET);
		try {
			DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product,domain);

			IAcsClient acsClient = new DefaultAcsClient(profile);
			//组装请求对象
			SendSmsRequest request = new SendSmsRequest();
			//使用post提交
			request.setMethod(MethodType.POST);
			//必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
			request.setPhoneNumbers(mobile);
			//必填:短信签名-可在短信控制台中找到
			request.setSignName(signName);
			//必填:短信模板-可在短信控制台中找到
			request.setTemplateCode(templateCode);
			//可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
			//友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
			StringBuffer ps = new StringBuffer("{");
			for (int i = 0; i < paramKey.length; i++) {
				ps.append("\""+paramKey[i]+"\":"+"\""+ paramValue[i]+"\",");
			}
			if (ps.length() > 1) {
				ps.deleteCharAt(ps.length() - 1);
			}
			ps.append("}");
			request.setTemplateParam(ps.toString());
			//request.setTemplateParam("{\"ordair\":\"3\", \"ordtr\":\"5\"}");
			//可选-上行短信扩展码(无特殊需求用户请忽略此字段)
			//request.setSmsUpExtendCode("90997");
			//可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
			//request.setOutId("yourOutId");
			//请求失败这里会抛ClientException异常
			SendSmsResponse sendSmsResponse;
			sendSmsResponse = acsClient.getAcsResponse(request);
			logger.info("发送反馈信息: " + sendSmsResponse.getMessage());
			logger.info("code:" + sendSmsResponse.getCode());
			if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
				//请求成功
				b=true;
			}
		} catch (ClientException e) {
			e.printStackTrace();
		}

		return b;
	}
	
	public static void main(String[] args) {
		//sendMessage("15159732758", new String[] {"ordair","ordtr"}, new String[] {"3","5"},SendMessageUtil.TEMPLATE_ARRANGE_ORDER , SendMessageUtil.TEMPLATE_SMS_CODE_SIGN_NAME);
		SendMessageUtil.sendMessage("15527916902",new String[]{"code"},new String[]{"1111"},
				SendMessageUtil.TEMPLATE_SMS_CODE , SendMessageUtil.TEMPLATE_SMS_CODE_SIGN_ORDER);
	}
}
