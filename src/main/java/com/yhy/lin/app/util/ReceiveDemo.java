package com.yhy.lin.app.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alicom.mns.tools.MessageListener;
import com.aliyun.mns.model.Message;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.google.gson.Gson;

/**
 * 只能用于接收云通信的消息，不能用于接收其他业务的消息
 * 短信上行消息接收demo
 */
public class ReceiveDemo {

	private static Log logger=LogFactory.getLog(ReceiveDemo.class);
	
	static class MyMessageListener implements MessageListener{
		private Gson gson=new Gson();

		@Override
		public boolean dealMessage(Message message) {

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//消息的几个关键值
			System.out.println("message receiver time from mns:" + format.format(new Date()));
			System.out.println("message handle: " + message.getReceiptHandle());
            System.out.println("message body: " + message.getMessageBodyAsString());
            System.out.println("message id: " + message.getMessageId());
            System.out.println("message dequeue count:" + message.getDequeueCount());
            System.out.println("Thread:" + Thread.currentThread().getName());
            try{
            	Map<String,Object> contentMap=gson.fromJson(message.getMessageBodyAsString(), HashMap.class);

				//TODO 根据文档中具体的消息格式进行消息体的解析
                String arg = (String) contentMap.get("arg");

                //TODO 这里开始编写您的业务代码

            }catch(com.google.gson.JsonSyntaxException e){
            	logger.error("error_json_format:"+message.getMessageBodyAsString(),e);
				//理论上不会出现格式错误的情况，所以遇见格式错误的消息，只能先delete,否则重新推送也会一直报错
				return true;
            } catch (Throwable e) {
				//您自己的代码部分导致的异常，应该return false,这样消息不会被delete掉，而会根据策略进行重推
				return false;
			}

			//消息处理成功，返回true, SDK将调用MNS的delete方法将消息从队列中删除掉
			return true;
		}

	}

	/*public static void main(String[] args) throws Exception, ParseException {

		DefaultAlicomMessagePuller puller=new DefaultAlicomMessagePuller();

		//设置异步线程池大小及任务队列的大小，还有无数据线程休眠时间
		puller.setConsumeMinThreadSize(6);
		puller.setConsumeMaxThreadSize(16);
		puller.setThreadQueueSize(200);
		puller.setPullMsgThreadSize(1);
		//和服务端联调问题时开启,平时无需开启，消耗性能
		puller.openDebugLog(false);

		//TODO 此处需要替换成开发者自己的AK信息
		String accessKeyId="accessKeyId";
		String accessKeySecret="accessKeySecret";
		
		* TODO 将messageType和queueName替换成您需要的消息类型名称和对应的队列名称
		*云通信产品下所有的回执消息类型:
		*1:短信回执：SmsReport，
		*2:短息上行：SmsUp
		*3:语音呼叫：VoiceReport
		*4:流量直冲：FlowReport
		
		String messageType="SmsReport";//此处应该替换成相应产品的消息类型
		String queueName="queueName";//在云通信页面开通相应业务消息后，就能在页面上获得对应的queueName,格式类似Alicom-Queue-xxxxxx-SmsReport
		puller.startReceiveMsg(AppGlobals.APP_KEY,AppGlobals.APP_SECRET, messageType, queueName, new MyMessageListener());
    }
*/
	public static void main(String[] args) throws ClientException {
		//设置超时时间-可自行调整
		//System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		//System.setProperty("sun.net.client.defaultReadTimeout", "10000");
		//初始化ascClient需要的几个参数
		final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
		final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
		//替换成你的AK
		final String accessKeyId =AppGlobals.APP_KEY;//你的accessKeyId,参考本文档步骤2
		final String accessKeySecret = AppGlobals.APP_SECRET;//你的accessKeySecret，参考本文档步骤2
		//初始化ascClient,暂时不支持多region
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
		accessKeySecret);
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		IAcsClient acsClient = new DefaultAcsClient(profile);
		 //组装请求对象
		 SendSmsRequest request = new SendSmsRequest();
		 //使用post提交
		 request.setMethod(MethodType.POST);
		 //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
		 request.setPhoneNumbers("15159732758");
		 //必填:短信签名-可在短信控制台中找到
		 request.setSignName("阿里云短信测试专用");
		 //必填:短信模板-可在短信控制台中找到
		 request.setTemplateCode("SMS_102350065");
		 //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		 //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
		 request.setTemplateParam("{\"ordair\":\"3\", \"ordtr\":\"5\"}");
		 //可选-上行短信扩展码(无特殊需求用户请忽略此字段)
		 //request.setSmsUpExtendCode("90997");
		 //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		 //request.setOutId("yourOutId");
		//请求失败这里会抛ClientException异常
		SendSmsResponse sendSmsResponse;
		sendSmsResponse = acsClient.getAcsResponse(request);
		if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
			//请求成功
			}
		
	}
	
}
