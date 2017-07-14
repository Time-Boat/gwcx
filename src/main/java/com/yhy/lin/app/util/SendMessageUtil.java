package com.yhy.lin.app.util;

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

	// 验证码信息模板
	public static final String TEMPLATE_SMS_CODE = "SMS_66640251";//个人SMS_63766002
	// 验证码信息模板对应签名
	public static final String TEMPLATE_SMS_CODE_SIGN_NAME = "登录验证";
	
	// 安排车辆信息模板
	public static final String TEMPLATE_ARRANGE_CAR = "SMS_70420532";//SMS_69620002
	// 安排车辆信息模板对应签名
	public static final String TEMPLATE_ARRANGE_CAR_SIGN_NAME = "变更验证";
	
	/**
	 * 发送短信
	 * @param mobile 		用户手机号
	 * @param paramKey		短信模板对应的key值
	 * @param paramValue	短信模板对应的value值
	 * @param templateCode	阿里大于上配置的模板id
	 * @param signName		模板签名
	 * @return body			用于判断短信是否发送成功
	 */
	public static String sendMessage(String mobile, String[] paramKey, String[] paramValue, String templateCode , String signName) {

		String body = "";

		// 生成4位数的验证码
//		String code = StringUtil.numRandom(4);

		TaobaoClient client = new DefaultTaobaoClient(AppGlobals.SERVCR_URL, AppGlobals.APP_KEY, AppGlobals.APP_SECRET);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend("123456"); // 短信回传时的可以携带的信息 ？？？
		req.setSmsType("normal"); // 短信类型
		req.setSmsFreeSignName(signName); // 模板签名
		StringBuffer ps = new StringBuffer("{");
		for (int i = 0; i < paramKey.length; i++) {
			ps.append("'" + paramKey[i] + "':'" + paramValue[i] + "',");
		}
		if (ps.length() > 1) {
			ps.deleteCharAt(ps.length() - 1);
		}
		ps.append("}");
		req.setSmsParamString(ps.toString());
		req.setRecNum(mobile);
		req.setSmsTemplateCode(templateCode);
		AlibabaAliqinFcSmsNumSendResponse rsp = null;
		try {
			rsp = client.execute(req);
			body = rsp.getBody();
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return body;
	}
	
	/**
	 * 把消息推送到用户的消息中心
	 * @param userId	用户id
	 * @param content	消息内容
	 * @param status	消息状态 0:否	 1:是
	 * @param type		消息类型 0:新增    1:修改
	 * @param orderId	订单id
	 */
	public static AppMessageListEntity buildAppMessage(String userId, String content, String status, String type, String orderId) {
		
		// 如果购买成功，将消息添加到消息中心
		AppMessageListEntity app = new AppMessageListEntity();
		app.setContent(content);
		app.setCreateTime(AppUtil.getCurTime());
		app.setStatus(status);
		app.setUserId(userId);
		app.setMsgType(type);
		app.setOrderId(orderId);
		
		return app;
		
	}
	
}
