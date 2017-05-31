package com.yhy.lin.app.util;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

/**
 * Description :短信发送工具类
 * 
 * @author Administrator
 * @date 2017年5月31日 上午11:50:11
 */
public class SendMessageUtil {

	// 验证码信息模板
	public static final String TEMPLATE_SMS_CODE = "SMS_63766002";

	// 安排车辆信息模板
	public static final String TEMPLATE_ARRANGE_CAR = "SMS_69620002";

	public static String sendMessage(String mobile, String[] paramKey, String[] paramValue, String templateCode) {

		String body = "";

		// 生成4位数的验证码
//		String code = StringUtil.numRandom(4);

		TaobaoClient client = new DefaultTaobaoClient(AppGlobals.SERVCR_URL, AppGlobals.APP_KEY, AppGlobals.APP_SECRET);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend("123456"); // 短信回传时的可以携带的信息 ？？？
		req.setSmsType("normal"); // 短信类型
		req.setSmsFreeSignName("刘航"); // 模板签名
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
		req.setSmsTemplateCode(templateCode); // 阿里大于上配置的模板id
		AlibabaAliqinFcSmsNumSendResponse rsp = null;
		try {
			rsp = client.execute(req);
			body = rsp.getBody();
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return body;
	}
}
