// ~ CopyRight © 2012 USTC SINOVATE  SOFTWARE CO.LTD All Rights Reserved.
package org.jeecgframework.web.system.sms.util;



import org.jeecgframework.web.system.sms.util.msg.util.MsgContainer;

/**
 *   描述：CMPPSenderUtil CMPP3.0协议发送短信所有前台调用的短信发送方法在此 <br />
 *         版本:1.0.0 <br /
 * @author skycc
 *
 */
public class CMPPSenderUtil {

	/**
	 * 向单个号码发送短信.
	 * 
	 * @param phone
	 *            手机号码
	 * @param content
	 *            短信内容
	 * @return String 返回类型：String
	 */
	public static String sendMsg(String phone, String content) {
		return null;
	}

	/**
	 * 短信群发.
	 * 
	 * @param phone
	 *            手机号码,多个号码以“,”分隔
	 * @param content
	 *            消息内容
	 * @return 发送失败 返回"false";部分号码失败,返回发送失败的号码;全部成功,返回"" 返回类型：String
	 */
	public static String sendTMsgs(String phone, String content) {
		String[] phoneAddress = phone.split(",");
		String sendResult = "";
		try {
			for (int i = 0; i < phoneAddress.length; i++) {
				boolean result = MsgContainer.sendMsg(content, phoneAddress[i]);
				if (!result) {
					sendResult += "-号码" + phoneAddress[i] + "发送失败";
					continue;
				}
			}
			return sendResult;
		} catch (Exception e) {
			e.printStackTrace();
			return "fasle";
		}
	}

	/**
	 * 异网的短信网关.
	 * 
	 * @param phone
	 *            手机号码
	 * @param content
	 *            短信内容
	 * @return String
	 */
	public static String sendDifferenceNetMsg(String phone, String content) {
		return null;
	}

	/**
	 * 异网的短信网关带扩展码.
	 * 
	 * @param phone
	 *            手机号码
	 * @param content
	 *            短信内容
	 * @param exCode
	 *            扩展码
	 * @return String
	 */
	public static String sendMessage(String phone, String content, String exCode) {
		return null;
	}

}
