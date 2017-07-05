package com.yhy.lin.app.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import oracle.net.aso.MD5;
import sun.misc.BASE64Decoder;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.util.PasswordUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.yhy.lin.app.entity.AppMessageListEntity;
import com.yhy.lin.app.entity.CarCustomerEntity;
import com.yhy.lin.app.entity.RefundReqData;
import com.yhy.lin.app.service.WeixinPayService;
import com.yhy.lin.app.util.AppGlobals;
import com.yhy.lin.app.util.AppUtil;
import com.yhy.lin.app.util.MD5Util;
import com.yhy.lin.app.wechat.CommonUtil;
import com.yhy.lin.app.wechat.MobiMessage;
import com.yhy.lin.app.wechat.RequestHandler;
import com.yhy.lin.app.wechat.Sha1Util;
import com.yhy.lin.app.wechat.TxtUtil;
import com.yhy.lin.app.wechat.WeixinPayUtil;
import com.yhy.lin.app.wechat.menu.MenuContext;
import com.yhy.lin.entity.DealerCustomerEntity;
import com.yhy.lin.entity.TransferorderEntity;

/**
 * 微信支付Controller
 * 
 * @author Jeff Xu
 * @since 2016-08-11
 */

@Controller
@RequestMapping("/wx")
@SuppressWarnings("rawtypes")
public class WeixinPayController extends AppBaseController{

	@Autowired
	private SystemService systemService;

	@Autowired
	private WeixinPayService wxService;
	
	// private stxatic String baseUrl = "http://localhost:8080/gwcx";
	private static String baseUrl = "http://car.cywtrip.com/gwcx";

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AppInterfaceController.class);
	
	/**
	 * 微信网页授权获取用户基本信息，先获取 code，跳转 url 通过 code 获取 openId    (支付)
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(params = "userAuth")
	public String userAuth(HttpServletRequest request, HttpServletResponse response) {
		try {
			// String orderId =
			// MakeOrderNum.makeOrderNum("tx");//AppGlobals.SP_NAME;

			String orderId = request.getParameter("orderId");
			String totalFee = request.getParameter("totalFee");
			logger.info("in userAuth,orderId:" + orderId);
			// 授权后要跳转的链接
			String backUri = baseUrl + "/wx.do?toPay";
			
			backUri = backUri + "&orderId=" + orderId + "&totalFee=" + totalFee;
			// URLEncoder.encode 后可以在backUri 的url里面获取传递的所有参数
			backUri = URLEncoder.encode(backUri, "utf-8");
			// scope 参数视各自需求而定，这里用scope=snsapi_base
			// 不弹出授权页面直接授权目的只获取统一支付接口的openid
			String url = "https://open.weixin.qq.com/connect/oauth2/authorize?" + "appid=" + AppGlobals.WECHAT_ID
					+ "&redirect_uri=" + backUri
					+ "&response_type=code&scope=snsapi_userinfo&state=1234#wechat_redirect";
			logger.info("url:" + url);
			response.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(params = "toPay")
	public ModelAndView toPay(HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			String orderId = request.getParameter("orderId");
			System.out.println("in toPay,orderId:" + orderId);

			String totalFeeStr = request.getParameter("totalFee");
			System.out.println("totalFeeStr:" + totalFeeStr);
			Float totalFee = 0.0f;

			if (StringUtils.isNotBlank(totalFeeStr)) {
				totalFee = new Float(totalFeeStr);
			}

			// 网页授权后获取传递的参数
			String userId = request.getParameter("userId");
			String code = request.getParameter("code");
			System.out.println("code:" + code);
			if (!StringUtil.isNotEmpty(code)) {
				return null;
			}
			System.out.println("userId:" + userId);
			
			String URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + AppGlobals.WECHAT_ID + "&secret="
					+ AppGlobals.WECHAT_APP_SECRET + "&code=" + code + "&grant_type=authorization_code";
			// 获取统一下单需要的openid
			String openId = "";
			System.out.println("URL:" + URL);
			JSONObject jsonObject = CommonUtil.httpsRequest(URL, "GET", null);
			System.out.println(jsonObject);
			if (null != jsonObject) {
				openId = jsonObject.getString("openid");
				System.out.println("openid:" + openId);
			}
			
			//微信订单号    要存在订单表中
			String orderPayNumber = UUIDGenerator.generate();
			
			// 获取openId后调用统一支付接口https://api.mch.weixin.qq.com/pay/unifiedorder
			// 随机数
			// String nonce_str = "1add1a30ac87aa2db72f57a2375d8fec";
			String nonce_str = UUID.randomUUID().toString().replaceAll("-", "");
			// 商品描述
			String body = "龙游出行";
			// 商户订单号
			//String out_trade_no = orderId;
			String out_trade_no = orderPayNumber;
			// 订单生成的机器 IP
			String spbill_create_ip = request.getRemoteAddr();
			// 总金额
			// TODO
			Integer total_fee = Math.round(totalFee * 100);
			// Integer total_fee = 1;

			// 商户号
			// String mch_id = partner;
			// 子商户号 非必输
			// String sub_mch_id="";
			// 设备号 非必输
			// String device_info="";
			// 附加数据
			// String attach = userId;
			// 总金额以分为单位，不带小数点
			// int total_fee = intMoney;
			// 订 单 生 成 时 间 非必输
			// String time_start ="";
			// 订单失效时间 非必输
			// String time_expire = "";
			// 商品标记 非必输
			// String goods_tag = "";
			// 非必输
			// String product_id = "";

			// 这里notify_url是 支付完成后微信发给该链接信息，可以判断会员是否支付成功，改变订单状态等。
//			String notify_url = baseUrl + "/wx/notifyUrl.do";
			String notify_url = "http://car.cywtrip.com:8080/gwcx/wx/notifyUrl.do";

			SortedMap<String, String> packageParams = new TreeMap<String, String>();
			packageParams.put("appid", AppGlobals.WECHAT_ID);
			packageParams.put("mch_id", AppGlobals.MCH_ID);
			packageParams.put("nonce_str", nonce_str);
			packageParams.put("body", body);
			packageParams.put("out_trade_no", out_trade_no);
			packageParams.put("total_fee", total_fee + "");
			packageParams.put("spbill_create_ip", spbill_create_ip);
			packageParams.put("notify_url", notify_url);
			packageParams.put("trade_type", AppGlobals.TRADE_TYPE);
			packageParams.put("openid", openId);
			
			RequestHandler reqHandler = new RequestHandler(request, response);
			reqHandler.init(AppGlobals.WECHAT_ID, AppGlobals.WECHAT_APP_SECRET, AppGlobals.WECHAT_KEY);
			
			String sign = reqHandler.createSign(packageParams);
			System.out.println("sign:" + sign);
			String xml = "<xml>" + "<appid>" + AppGlobals.WECHAT_ID + "</appid>" + "<mch_id>" + AppGlobals.MCH_ID
					+ "</mch_id>" + "<nonce_str>" + nonce_str + "</nonce_str>" + "<sign>" + sign + "</sign>"
					+ "<body><![CDATA[" + body + "]]></body>" + "<out_trade_no>" + out_trade_no + "</out_trade_no>"
					+ "<total_fee>" + total_fee + "" + "</total_fee>" + "<spbill_create_ip>" + spbill_create_ip
					+ "</spbill_create_ip>" + "<notify_url>" + notify_url + "</notify_url>" + "<trade_type>"
					+ AppGlobals.TRADE_TYPE + "</trade_type>" + "<openid>" + openId + "</openid>" + "</xml>";
			System.out.println("xml：" + xml);

			String createOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
			String prepay_id = "";
			try {
				prepay_id = WeixinPayUtil.getPayNo(createOrderURL, xml);
				System.out.println("prepay_id:" + prepay_id);
				if (prepay_id.equals("")) {
					request.setAttribute("ErrorMsg", "统一支付接口获取预支付订单出错");
					response.sendRedirect("error.jsp");
					return null;
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			SortedMap<String, String> finalpackage = new TreeMap<String, String>();
			String timestamp = Sha1Util.getTimeStamp();
			String packages = "prepay_id=" + prepay_id;
			finalpackage.put("appId", AppGlobals.WECHAT_ID);
			finalpackage.put("timeStamp", timestamp);
			finalpackage.put("nonceStr", nonce_str);
			finalpackage.put("package", packages);
			finalpackage.put("signType", AppGlobals.SIGN_TYPE);
			String finalsign = reqHandler.createSign(finalpackage); // 生成签名
			System.out.println("/jsapi?appid=" + AppGlobals.WECHAT_ID + "&timeStamp=" + timestamp + "&nonceStr="
					+ nonce_str + "&package=" + packages + "&sign=" + finalsign);

			model.addAttribute("appid", AppGlobals.WECHAT_ID);
			model.addAttribute("timeStamp", timestamp);
			model.addAttribute("nonceStr", nonce_str);
			model.addAttribute("packageValue", packages);
			model.addAttribute("sign", finalsign);

			model.addAttribute("bizOrderId", orderId);
			model.addAttribute("orderId", orderId);
			model.addAttribute("orderPayNumber", orderPayNumber);
			model.addAttribute("payPrice", total_fee);
			return new ModelAndView("yhy/wechat/jsapi");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 微信异步回调，不会跳转页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/notifyUrl")
	public String weixinReceive(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("==开始进入h5支付回调方法==");
		String xml_review_result = WeixinPayUtil.getXmlRequest(request);
		System.out.println("微信支付结果:" + xml_review_result);
		Map resultMap = null;
		try {
			resultMap = WeixinPayUtil.doXMLParse(xml_review_result);
			System.out.println("resultMap:" + resultMap);
			if (resultMap != null && resultMap.size() > 0) {
				String sign_receive = (String) resultMap.get("sign");
				System.out.println("sign_receive:" + sign_receive);
				resultMap.remove("sign");
				String checkSign = WeixinPayUtil.getSign(resultMap, AppGlobals.WECHAT_KEY);
				System.out.println("checkSign:" + checkSign);

				// 签名校验成功
				if (checkSign != null && sign_receive != null && checkSign.equals(sign_receive.trim())) {
					System.out.println("weixin receive check Sign sucess");
					try {
						// 获得返回结果
						String return_code = (String) resultMap.get("return_code");

						if ("SUCCESS".equals(return_code)) {
							String out_trade_no = (String) resultMap.get("out_trade_no");
							System.out.println("weixin pay sucess,out_trade_no:" + out_trade_no);
							// 处理支付成功以后的逻辑，这里是写入相关信息到文本文件里面，如果有订单的处理订单
							try {
								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh24:mi:ss");
								String content = out_trade_no + "        " + sdf.format(new Date());
								String fileUrl = System.getProperty("user.dir") + File.separator + "WebContent"
										+ File.separator + "data" + File.separator + "order.txt";
								TxtUtil.writeToTxt(content, fileUrl);
							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							model.addAttribute("payResult", "0");
							model.addAttribute("err_code_des", "通信错误");
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					// 签名校验失败
					System.out.println("weixin receive check Sign fail");
					String checkXml = "<xml><return_code><![CDATA[FAIL]]></return_code>"
							+ "<return_msg><![CDATA[check sign fail]]></return_msg></xml>";
					WeixinPayUtil.getTradeOrder("http://weixin.xinfor.com/wx/notifyUrl", checkXml);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 页面js返回支付成功后，查询微信后台是否支付成功，然后跳转结果页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(params = "success")
	public ModelAndView toWXPaySuccess(HttpServletRequest request, HttpServletResponse response, Model model)
			throws IOException {

		String id = request.getParameter("orderId");
		String status = request.getParameter("status");
		String orderPayNumber = request.getParameter("orderPayNumber");
		System.out.println("toWXPaySuccess, orderId: " + id);
		System.out.println("toWXPaySuccess, orderPayNumber: " + orderPayNumber);
		try {
			Map resultMap = WeixinPayUtil.checkWxOrderPay(id);
			System.out.println("resultMap:" + resultMap);
			String return_code = (String) resultMap.get("return_code");
			String result_code = (String) resultMap.get("result_code");
			System.out.println("return_code:" + return_code + ",result_code:" + result_code);
			if ("SUCCESS".equals(return_code)) {
				if ("SUCCESS".equals(result_code)) {
					model.addAttribute("orderId", id);
					model.addAttribute("payResult", "1");
				} else {
					String err_code = (String) resultMap.get("err_code");
					String err_code_des = (String) resultMap.get("err_code_des");
					System.out.println("weixin resultCode:" + result_code + ",err_code:" + err_code + ",err_code_des:"
							+ err_code_des);

					model.addAttribute("err_code", err_code);
					model.addAttribute("err_code_des", err_code_des);
					model.addAttribute("payResult", "0");
				}

				// 如果成功，处理业务逻辑
				request.setAttribute("status", status);

				System.out.println("toWXPaySuccess   status:" + status);
				System.out.println("toWXPaySuccess   id:" + id);

				TransferorderEntity t = systemService.getEntity(TransferorderEntity.class, id);
				if ("0".equals(status)) {
					t.setOrderStatus(1);
					t.setOrderPaystatus("0");
					t.setOrderPayNumber(orderPayNumber);

					// 如果购买成功，将消息添加到消息中心
					AppMessageListEntity app = new AppMessageListEntity();
					app.setContent("您已购买 " + t.getOrderStartingStationName() + "-" + t.getOrderTerminusStationName()
							+ " 的车票，请等待管理员审核。");
					app.setCreateTime(AppUtil.getCurTime());
					app.setStatus("0"); // 消息状态 0：否 1：是
					app.setUserId(t.getUserId());
					app.setMsgType("0"); // 消息类型 0:新增 1:修改
					app.setOrderId(t.getId());
					systemService.save(app);

				} else {
					t.setOrderStatus(6);
					t.setOrderPaystatus("3");
				}
				systemService.save(t);

			} else {
				model.addAttribute("payResult", "0");
				model.addAttribute("err_code_des", "通信错误");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ModelAndView("yhy/wechat/payResult");
	}
	
	/**
	 * 微信消息推送回调
	 * 
	 */
	@RequestMapping(params = "eventPush")
	public ModelAndView eventPush(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		logger.info("进入eventPush回调url");
		String url = "";
		try {
			
			String xml = WeixinPayUtil.getXmlRequest(request);
			if(StringUtil.isNotEmpty(xml)){
				Map map = WeixinPayUtil.doXMLParse(xml);
				String type = (map.get("Event") + "").toLowerCase();
				
				String eventKey = map.get("EventKey") + "";
				
				String fromUser = map.get("FromUserName") + "";
				logger.info("fromUser: " + fromUser);
				logger.info("partnerId: " + eventKey);
				
				switch (type) {
				case "subscribe":
				case "scan":
					logger.info("用户扫描二维码进入");
//					String partnerId = map.get("EventKey") + "";   //合作渠道商id
					
					//如果该用户已经注册并绑定了渠道商
					CarCustomerEntity cc = systemService.findUniqueByProperty(CarCustomerEntity.class, "openId", fromUser);
					if(cc != null){
						return null;
					}
					
					DealerCustomerEntity d = systemService.findUniqueByProperty(DealerCustomerEntity.class, "openId", fromUser);
					if(d == null){
						d = new DealerCustomerEntity();
						d.setOpenId(fromUser);
					}
					
					d.setCreateDate(AppUtil.getDate());
					d.setDealerId(eventKey);
					
					systemService.saveOrUpdate(d);
					
					break;
				case "click":
					logger.info("用户点击菜单(click)进入");
					//策略模式
					MenuContext context = new MenuContext(eventKey, type, fromUser);  
					url = context.execute();
					break;
				case "view":
					logger.info("用户点击菜单(view)进入");
					//策略模式
					url = eventKey + "&openId=" + fromUser;
					break;

				default:
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
//		PrintWriter out = response.getWriter();  
//        out.print("");
//        out.close();
        
		logger.info("跳转的url" + url);
        if(StringUtil.isNotEmpty(url)){
//        	return new ModelAndView(new RedirectView(url));
        	return null;
        }else{
        	return null;
        }
        
	}
	
	/**
	 * 微信view菜单按钮授权跳转
	 * 
	 */
	@RequestMapping(params = "wxOauth2")
	public String viewMenu(HttpServletRequest request, HttpServletResponse response){
		logger.info("进入wxOauth2");
		try {
			// String orderId =
			// MakeOrderNum.makeOrderNum("tx");//AppGlobals.SP_NAME;

			// 授权后要跳转的链接
			String backUri = baseUrl + "/wx.do?toIndex";
			
			String reUrl = request.getParameter("redirect_uri");
			String phone = request.getParameter("phone");
			//新用户判断
			String isNew = request.getParameter("isNew");
			
			logger.info("wxOauth2    phone:" + phone);
			logger.info("wxOauth2    reUrl:" + reUrl);
			logger.info("wxOauth2    isNew:" + isNew);
			
			if(!StringUtil.isNotEmpty(isNew)){
				return "redirect:http://car.cywtrip.com/job/" + reUrl + ".html";
			}
			
			//如果这个用户已经绑定了渠道商，则不需要再去获得他的openId
			CarCustomerEntity car = systemService.findUniqueByProperty(CarCustomerEntity.class, "phone", phone);
			String openId = car.getOpenId();
			if(StringUtil.isNotEmpty(openId)){
				return "redirect:http://car.cywtrip.com/job/" + reUrl + ".html";
			}
			
			//解密的结果是不是正确的       解密耗时久，所以放在两个条件之后
			String plaintext = PasswordUtil.decrypt(isNew, car.getToken(), PasswordUtil.getStaticSalt());
			logger.info("wxOauth2    plaintext:" + plaintext);
			if(!plaintext.equals(phone)){
				return "redirect:http://car.cywtrip.com/job/" + reUrl + ".html";
			}
			
			backUri = backUri + "&redirect_uri=" + reUrl + "&phone=" + phone + "&isNew" + plaintext;
			logger.info("wxOauth2    backUri:" + backUri);
			
			// URLEncoder.encode 后可以在backUri 的url里面获取传递的所有参数
			backUri = URLEncoder.encode(backUri, "utf-8");
			// scope 参数视各自需求而定，这里用scope=snsapi_base
			// 不弹出授权页面直接授权目的只获取统一支付接口的openid
			String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + AppGlobals.WECHAT_ID
					+ "&redirect_uri=" + backUri
					+ "&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
			logger.info("url:" + url);
			response.sendRedirect(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 微信view菜单按钮授权跳转
	 * 
	 */
	@RequestMapping(params = "toIndex")
	public String toIndex(HttpServletRequest request, HttpServletResponse response){
		logger.info("进入toIndex");
		// 获取统一下单需要的openid
		String openId = "";
		
		String code = request.getParameter("code");
		String phone = request.getParameter("phone");
		String redirect_uri = request.getParameter("redirect_uri");
		
		logger.info("code:" + code);
		logger.info("toIndex    redirect_uri:" + redirect_uri);
		try {
			if (!StringUtil.isNotEmpty(code)) {
				return "redirect:http://car.cywtrip.com/job/" + redirect_uri + ".html";
			}
			
			String URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + AppGlobals.WECHAT_ID + "&secret="
					+ AppGlobals.WECHAT_APP_SECRET + "&code=" + code + "&grant_type=authorization_code";
			
			logger.info("URL:" + URL);
			//获取openId
			JSONObject jsonObject = CommonUtil.httpsRequest(URL, "GET", null);
			logger.info(jsonObject);
			if (null != jsonObject) {
				openId = jsonObject.getString("openid");
				logger.info("openid:" + openId);
				
				wxService.updateBandingInfo(openId, phone);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:http://car.cywtrip.com/job/" + redirect_uri + ".html";
	}
	
//	/**
//	 * 微信消息推送回调
//	 * 
//	 */
//	@RequestMapping(params = "eventPush")
//	public void eventPush(HttpServletRequest request, HttpServletResponse response, Model model)
//			throws IOException {
//		
//		logger.info("进入eventPush回调url");
//		
//		String signature = request.getParameter("signature");  
//        String timestamp = request.getParameter("timestamp");  
//        String nonce = request.getParameter("nonce");  
//        String echostr = request.getParameter("echostr");  
//        
//        PrintWriter out = response.getWriter();  
//        if (checkSignature(signature, timestamp, nonce)){  
//        	logger.info("eventPush: echostr=" + echostr);
//        	out.print(echostr);
//        }  
//        out.close();
//	}
//
//	public static boolean checkSignature(String signature, String timestamp,  
//            String nonce) {
//        String[] arr = new String[] { "longyouchuxing2017", timestamp, nonce };  
//        // sort  
//        Arrays.sort(arr);  
//  
//        // generate String  
//        String content = arr[0]+arr[1]+arr[2];  
//          
//        // shal code  
//        String temp = new Sha1Util().getSha1(content);  
//        logger.info("eventPush: code=" + temp);
//        logger.info("eventPush: signature=" + signature);
//        
//        return temp.equalsIgnoreCase(signature);  
//    }  
	
}
