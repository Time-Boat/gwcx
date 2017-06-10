package com.yhy.lin.app.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.util.StringUtil;
import org.json.JSONObject;

/**
 * Description : 微信支付
 * 
 * @author Administrator
 * @date 2017年6月10日 上午11:01:24
 */
public class WxPayUtil {
	private static Logger logger = Logger.getLogger(WxPayUtil.class);

	/**
	 * 根据code获取openid
	 * 
	 * @param code
	 * @return
	 * @throws IOException
	 */
	public static Map<String, Object> getOpenIdByCode(String code) throws IOException {
		// 请求该链接获取access_token
		HttpPost httppost = new HttpPost("https://api.weixin.qq.com/sns/oauth2/access_token");
		// 组装请求参数
		String reqEntityStr = "appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		reqEntityStr = reqEntityStr.replace("APPID", AppGlobals.WECHAT_ID);
		reqEntityStr = reqEntityStr.replace("SECRET", AppGlobals.WECHAT_APP_SECRET);
		reqEntityStr = reqEntityStr.replace("CODE", code);
		StringEntity reqEntity = new StringEntity(reqEntityStr);
		// 设置参数
		httppost.setEntity(reqEntity);
		// 设置浏览器
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 发起请求
		CloseableHttpResponse response = httpclient.execute(httppost);
		// 获得请求内容
		String strResult = EntityUtils.toString(response.getEntity());
		// 获取openid
		JSONObject jsonObject = new JSONObject(strResult);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("openid", jsonObject.get("openid"));
		map.put("access_token", jsonObject.get("access_token"));
		map.put("refresh_token", jsonObject.get("refresh_token"));
		return map;
	}

	/**
	 * 根据统一下单返回预支付订单的id和其他信息生成签名并拼装为map（调用微信支付）
	 * 
	 * @param prePayInfoXml
	 * @return
	 */
	public static Map<String, Object> getPayMap(String prePayInfoXml) {
		Map<String, Object> map = new HashMap<String, Object>();

		String prepay_id = getXmlPara(prePayInfoXml, "prepay_id");// 统一下单返回xml中prepay_id
		String timeStamp = String.valueOf((System.currentTimeMillis() / 1000));// 1970年到现在的秒数
		String nonceStr = getNonceStr().toUpperCase();// 随机数据字符串
		String packageStr = "prepay_id=" + prepay_id;
		String signType = "MD5";
		String paySign = "appId=" + AppGlobals.WECHAT_ID + "&nonceStr=" + nonceStr + "&package=prepay_id=" + prepay_id
				+ "&signType=" + signType + "&timeStamp=" + timeStamp + "&key=" + AppGlobals.WECHAT_KEY;// 注意这里的参数要根据ASCII码
																										// 排序
		paySign = MD5Util.MD5(paySign).toUpperCase();// 将数据MD5加密

		map.put("appId", AppGlobals.WECHAT_ID);
		map.put("timeStamp", timeStamp);
		map.put("nonceStr", nonceStr);
		map.put("packageStr", packageStr);
		map.put("signType", signType);
		map.put("paySign", paySign);
		map.put("prepay_id", prepay_id);
		return map;
	}

	/**
	 * 修改订单状态，获取微信回调结果
	 * 
	 * @param request
	 * @return
	 */
	public static String getNotifyResult(HttpServletRequest request) {
		String inputLine;
		String notifyXml = "";
		String resXml = "";
		try {
			while ((inputLine = request.getReader().readLine()) != null) {
				notifyXml += inputLine;
			}
			request.getReader().close();
		} catch (Exception e) {
			logger.debug("xml获取失败：" + e);
			e.printStackTrace();
		}
		System.out.println("接收到的xml：" + notifyXml);
		logger.debug("收到微信异步回调：");
		logger.debug(notifyXml);
		if (!StringUtil.isNotEmpty(notifyXml)) {
			logger.debug("xml为空：");
		}

		String appid = getXmlPara(notifyXml, "appid");
		;
		String bank_type = getXmlPara(notifyXml, "bank_type");
		String cash_fee = getXmlPara(notifyXml, "cash_fee");
		String fee_type = getXmlPara(notifyXml, "fee_type");
		String is_subscribe = getXmlPara(notifyXml, "is_subscribe");
		String mch_id = getXmlPara(notifyXml, "mch_id");
		String nonce_str = getXmlPara(notifyXml, "nonce_str");
		String openid = getXmlPara(notifyXml, "openid");
		String out_trade_no = getXmlPara(notifyXml, "out_trade_no");
		String result_code = getXmlPara(notifyXml, "result_code");
		String return_code = getXmlPara(notifyXml, "return_code");
		String sign = getXmlPara(notifyXml, "sign");
		String time_end = getXmlPara(notifyXml, "time_end");
		String total_fee = getXmlPara(notifyXml, "total_fee");
		String trade_type = getXmlPara(notifyXml, "trade_type");
		String transaction_id = getXmlPara(notifyXml, "transaction_id");

		// 根据返回xml计算本地签名
		String localSign = "appid=" + appid + "&bank_type=" + bank_type + "&cash_fee=" + cash_fee + "&fee_type="
				+ fee_type + "&is_subscribe=" + is_subscribe + "&mch_id=" + mch_id + "&nonce_str=" + nonce_str
				+ "&openid=" + openid + "&out_trade_no=" + out_trade_no + "&result_code=" + result_code
				+ "&return_code=" + return_code + "&time_end=" + time_end + "&total_fee=" + total_fee + "&trade_type="
				+ trade_type + "&transaction_id=" + transaction_id + "&key=" + AppGlobals.WECHAT_KEY;// 注意这里的参数要根据ASCII码
																									// 排序
		localSign = MD5Util.MD5(localSign).toUpperCase();// 将数据MD5加密

		System.out.println("本地签名是：" + localSign);
		logger.debug("本地签名是：" + localSign);
		logger.debug("微信支付签名是：" + sign);

		// 本地计算签名与微信返回签名不同||返回结果为不成功
		if (!sign.equals(localSign) || !"SUCCESS".equals(result_code) || !"SUCCESS".equals(return_code)) {
			System.out.println("验证签名失败或返回错误结果码");
			logger.error("验证签名失败或返回错误结果码");
			resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
					+ "<return_msg><![CDATA[FAIL]]></return_msg>" + "</xml> ";
		} else {
			System.out.println("支付成功");
			logger.debug("公众号支付成功，out_trade_no(订单号)为：" + out_trade_no);
			resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
					+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
		}
		return resXml;
	}

	/**
	 * 获取32位随机字符串
	 * 
	 * @return
	 */
	public static String getNonceStr() {
		String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		StringBuilder sb = new StringBuilder();
		Random rd = new Random();
		for (int i = 0; i < 32; i++) {
			sb.append(str.charAt(rd.nextInt(str.length())));
		}
		return sb.toString();
	}

	/**
	 * 插入XML标签
	 * 
	 * @param sb
	 * @param Key
	 * @param value
	 * @return
	 */
	public static StringBuilder setXmlKV(StringBuilder sb, String Key, String value) {
		sb.append("<");
		sb.append(Key);
		sb.append(">");

		sb.append(value);

		sb.append("</");
		sb.append(Key);
		sb.append(">");

		return sb;
	}

	/**
	 * 解析XML 获得名称为para的参数值
	 * 
	 * @param xml
	 * @param para
	 * @return
	 */
	public static String getXmlPara(String xml, String para) {
		int start = xml.indexOf("<" + para + ">");
		int end = xml.indexOf("</" + para + ">");

		if (start < 0 && end < 0) {
			return null;
		}
		return xml.substring(start + ("<" + para + ">").length(), end).replace("<![CDATA[", "").replace("]]>", "");
	}

	/**
	 * 获取ip地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			return request.getRemoteAddr();
		}
		byte[] ipAddr = addr.getAddress();
		String ipAddrStr = "";
		for (int i = 0; i < ipAddr.length; i++) {
			if (i > 0) {
				ipAddrStr += ".";
			}
			ipAddrStr += ipAddr[i] & 0xFF;
		}
		return ipAddrStr;
	}
}
