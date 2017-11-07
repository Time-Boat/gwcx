package com.yhy.lin.app.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.PasswordUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.yhy.lin.app.entity.AppCheckTicket;
import com.yhy.lin.app.entity.AppCustomerEntity;
import com.yhy.lin.app.entity.AppLineStationInfoEntity;
import com.yhy.lin.app.entity.AppMessageListEntity;
import com.yhy.lin.app.entity.AppStationInfoEntity;
import com.yhy.lin.app.entity.AppUserOrderDetailEntity;
import com.yhy.lin.app.entity.AppUserOrderEntity;
import com.yhy.lin.app.entity.CarCustomerEntity;
import com.yhy.lin.app.entity.FeedbackEntity;
import com.yhy.lin.app.entity.MessageCodeEntity;
import com.yhy.lin.app.entity.UserInfo;
import com.yhy.lin.app.exception.ParameterException;
import com.yhy.lin.app.service.AppInterfaceService;
import com.yhy.lin.app.util.AppGlobals;
import com.yhy.lin.app.util.AppUtil;
import com.yhy.lin.app.util.Base64ImageUtil;
import com.yhy.lin.app.util.MakeOrderNum;
import com.yhy.lin.app.util.SendMessageUtil;
import com.yhy.lin.app.wechat.WeixinPayUtil;
import com.yhy.lin.entity.DealerApplyEntity;
import com.yhy.lin.entity.DealerInfoEntity;
import com.yhy.lin.entity.LineInfoEntity;
import com.yhy.lin.entity.OpenCityEntity;
import com.yhy.lin.entity.TransferorderEntity;

/**
 * Description : 接口处理类
 * 
 * @author Time
 * @date 2017年5月4日 上午9:34:30
 */

@Controller
@RequestMapping(value = "/app")
public class AppInterfaceController extends AppBaseController {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AppInterfaceController.class);

	@Autowired
	private AppInterfaceService appService;
	
	@Autowired
	private SystemService systemService;
	
	/*// 登录接口
	@RequestMapping(params = "appLogin")
	public void AppLogin(HttpServletRequest request, HttpServletResponse response) {
		AppUtil.responseUTF8(response);
		JSONObject returnJsonObj = new JSONObject();

		String msg = "";
		String statusCode = "";
		JSONObject data = new JSONObject();
		
		//是否是新用户，用于渠道商的绑定
		String isNew = "";
		
		try {
			String param = AppUtil.inputToStr(request);
			
			System.out.println("appLogin    前端传递参数：" + param);
			
			//验证参数
			JSONObject jsondata = checkParam(param);
			
			String mobile = jsondata.getString("mobile");
			String code = jsondata.getString("code");
			System.out.println("用户登录信息>>手机号【" + mobile + "】验证码【" + code + "】");
			if (StringUtil.isNotEmpty(mobile) && mobile.matches(AppGlobals.CHECK_PHONE)) {
				List<DealerInfoEntity> dealer = systemService.findHql("from DealerInfoEntity where status = 0 and phone = ? ", mobile);
				if(dealer.size() <= 0){
					if (StringUtil.isNotEmpty(code)) {
						CarCustomerEntity user = systemService.findUniqueByProperty(CarCustomerEntity.class, "phone", mobile);
						if (user != null) {
							Date date = user.getCodeUpdateTime();
							int m = AppUtil.compareDate(DateUtils.getDate(), date, 'm', "");
							// 30分钟的有效期验证
							if (m < 30 && user.getStatus().equals("0") && code.equals(user.getSecurityCode())) {
								// if (m > 30 && user.getStatus().equals("1") &&
								// code.equals(user.getSecurityCode())) { // 测试
								String sql = "";
								String curTime = AppUtil.getCurTime();
								String token = "";
								
								// 修改登录状态
								if (StringUtil.isNotEmpty(user.getToken())) {
									token = user.getToken();
									sql = "update car_customer set status = '1', token_update_time = ? where phone = ? ";
									systemService.executeSql(sql, curTime, mobile);
								} else {
									// 生成token
									token = AppUtil.generateToken(user.getId(), user.getPhone());
									sql = "update car_customer set status = '1', token_update_time = ? ,token = ? where phone = ? ";
									systemService.executeSql(sql, curTime, token, mobile);
									// 新注册用户 标识
									if(user.getTokenUpdateTime() == null){
										isNew = PasswordUtil.encrypt(mobile, token, PasswordUtil.getStaticSalt());
									}
								}
								// 添加登陆日志
								// String message = "app手机用户: " + user.getUserName()
								// + "[" + user.getPhone() + "]" + "登录成功";
								String message = "app手机用户: " + user.getPhone() + "登录成功";
								systemService.addLog(message, Globals.Log_Type_LOGIN, Globals.Log_Leavel_INFO);
	
								msg = "登录成功!";
								data.put("token", token);
								data.put("userId", user.getUserId());
								// 如果value为空的话，这个键值对不会再json字符串中显示，所以将null转换成""
								data.put("userName", AppUtil.Null2Blank(user.getUserName()));
								data.put("customerImg", AppUtil.Null2Blank(user.getCustomerImg()));
								data.put("phone", user.getPhone());
								data.put("isNew", isNew);
								data.put("userType", user.getUserType());
								statusCode = AppGlobals.APP_SUCCESS;
							} else {
								msg = "验证码不正确！";
								statusCode = "003";
							}
						} else {
							msg = "请先获得验证码！";
							statusCode = "005";
						}
					} else {
						msg = "验证码不能为空！";
						statusCode = "002";
					}
				} else {
					msg = "请从渠道商入口登录";
					statusCode = "010";
				}
			} else {
				msg = "手机号不能为空！";
				statusCode = "001";
			}
		}
		catch (ParameterException e) {
			statusCode = e.getCode();
			msg = e.getErrorMessage();
			logger.error(e.getErrorMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
			msg = "登陆异常";
			statusCode = AppGlobals.SYSTEM_ERROR;
		}

		returnJsonObj.put("code", statusCode);
		returnJsonObj.put("msg", msg);
		if (data.size() == 0) {
			returnJsonObj.put("data", "");
		} else {
			returnJsonObj.put("data", data.toString());
		}

		responseOutWrite(response, returnJsonObj);
	}*/
	
	// 登录接口
	@RequestMapping(params = "appLogin")
	public void AppLogin(HttpServletRequest request, HttpServletResponse response) {
		AppUtil.responseUTF8(response);
		JSONObject returnJsonObj = new JSONObject();

		String msg = "";
		String statusCode = "";
		JSONObject data = new JSONObject();
		
		//是否是新用户，用于渠道商的绑定
		String isNew = "";
		
		try {
			String param = AppUtil.inputToStr(request);
			
			System.out.println("appLogin    前端传递参数：" + param);
			
			//验证参数
			JSONObject jsondata = checkParam(param);
			
			String mobile = jsondata.getString("mobile");
			String code = jsondata.getString("code");
			System.out.println("用户登录信息>>手机号【" + mobile + "】验证码【" + code + "】");
			if (StringUtil.isNotEmpty(mobile) && mobile.matches(AppGlobals.CHECK_PHONE)) {
				List<MessageCodeEntity> mscodeList = systemService.findHql(
						" from MessageCodeEntity where phone=? and isUsed='0' and createTime = (select max(createTime) from MessageCodeEntity) ", mobile);
				if(mscodeList.size() > 0){
					MessageCodeEntity mscode = mscodeList.get(0);
					if("0".equals(mscode.getCodeType())){
						Date cDate = mscode.getCreateTime();
						int m = AppUtil.compareDate(DateUtils.getDate(), cDate, 'm', "");
						// 30分钟的有效期验证
						if (m < 30 && code.equals(mscode.getMsgCode())) {
							String token = "";
							Date date = AppUtil.getDate();
							
							CarCustomerEntity user = systemService.findUniqueByProperty(CarCustomerEntity.class, "phone", mobile);
							//是否是已注册用户
							if (user == null) {
								
								user = new CarCustomerEntity();
								user.setId(UUIDGenerator.generate());
								user.setUserType("0");
								user.setPhone(mobile);
								user.setCreateTime(date);
								user.setLoginCount(0);
								
								// 生成token
								token = AppUtil.generateToken(user.getId(), user.getPhone());
								
								user.setToken(token);
								user.setTokenUpdateTime(date);
								
								// 新注册用户 标识
								isNew = PasswordUtil.encrypt(mobile, token, PasswordUtil.getStaticSalt());
								
							} else {
								token = user.getToken();
								user.setTokenUpdateTime(date);
								user.setLoginCount(user.getLoginCount() + 1);
							}
							
							systemService.save(user);
							
							// 添加登陆日志
							String message = "app手机用户: " + user.getPhone() + "登录成功";
							systemService.addLog(message, Globals.Log_Type_LOGIN, Globals.Log_Leavel_INFO);

							msg = "登录成功!";
							data.put("token", token);
							data.put("userId", user.getUserId());
							// 如果value为空的话，这个键值对不会再json字符串中显示，所以将null转换成""
							data.put("userName", AppUtil.Null2Blank(user.getUserName()));
							data.put("customerImg", AppUtil.Null2Blank(user.getCustomerImg()));
							data.put("phone", user.getPhone());
							data.put("isNew", isNew);
							data.put("userType", user.getUserType());
							statusCode = AppGlobals.APP_SUCCESS;
						} else {
							msg = "用户不存在";
							statusCode = "011";
						}
					} else {
						msg = "请从渠道商入口登录";
						statusCode = "010";
					}
				} else {
					msg = "验证码无效！";
					statusCode = "003";
				}
			} else {
				msg = "手机号不能为空！";
				statusCode = "001";
			}
		}
		catch (ParameterException e) {
			statusCode = e.getCode();
			msg = e.getErrorMessage();
			logger.error(e.getErrorMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
			msg = "登陆异常";
			statusCode = AppGlobals.SYSTEM_ERROR;
		}

		returnJsonObj.put("code", statusCode);
		returnJsonObj.put("msg", msg);
		if (data.size() == 0) {
			returnJsonObj.put("data", "");
		} else {
			returnJsonObj.put("data", data.toString());
		}

		responseOutWrite(response, returnJsonObj);
	}
	
	// token登录接口
	@RequestMapping(params = "tokenLogin")
	public void TokenLogin(HttpServletRequest request, HttpServletResponse response) {
		AppUtil.responseUTF8(response);
		JSONObject returnJsonObj = new JSONObject();

		String msg = "";
		String statusCode = "";
		JSONObject data = new JSONObject();
		
		String isNew = "";
		
		try {
			String param = AppUtil.inputToStr(request);
			System.out.println("tokenLogin    前端传递参数：" + param);
			
			//验证参数
			JSONObject jsondata = checkParam(param);
			String token = jsondata.getString("token");
			String userType = jsondata.getString("userType");
			try {
				
				checkToken(token);//验证token
				
				if(StringUtil.isNotEmpty(token)){
					List<CarCustomerEntity> userList = systemService.findByQueryString("from CarCustomerEntity where token='" + token + "' and userType='" + userType + "'");
					
					if(userList.size() > 0){
						CarCustomerEntity user = userList.get(0);
						// 添加登陆日志
						// String message = "app手机用户: " + user.getUserName()
						// + "[" + user.getPhone() + "]" + "登录成功";
						String message = "app手机用户: " + user.getPhone() + "登录成功";
						systemService.addLog(message, Globals.Log_Type_LOGIN, Globals.Log_Leavel_INFO);
						
						msg = "登录成功!";
						data.put("token", token);
						data.put("userId", user.getUserId());
						// 如果value为空的话，这个键值对不会再json字符串中显示，所以将null转换成""
						data.put("userName", AppUtil.Null2Blank(user.getUserName()));
						data.put("customerImg", AppUtil.Null2Blank(user.getCustomerImg()));
						data.put("phone", user.getPhone());
						data.put("isNew", isNew);
						data.put("userType", user.getUserType());
						statusCode = AppGlobals.APP_SUCCESS;
						//添加登录次数
						StringBuffer updateSql = new StringBuffer("UPDATE car_customer set login_count=" + (user.getLoginCount() + 1) + " where token ='"+token+"'");
					    
					    this.systemService.updateBySqlString(updateSql.toString());
					}
				}
				
			} catch (ParameterException e) {
				statusCode = e.getCode();
				msg = e.getErrorMessage();
				logger.error(e.getErrorMessage());
			}
		}
		catch (ParameterException e) {
			statusCode = e.getCode();
			msg = e.getErrorMessage();
			logger.error(e.getErrorMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
			msg = "登陆异常";
			statusCode = AppGlobals.SYSTEM_ERROR;
		}

		returnJsonObj.put("code", statusCode);
		returnJsonObj.put("msg", msg);
		if (data.size() == 0) {
			returnJsonObj.put("data", "");
		} else {
			returnJsonObj.put("data", data.toString());
		}

		responseOutWrite(response, returnJsonObj);
	}
	
	/*// 短信发送接口
	@RequestMapping(params = "getSmscode")
	public void getSmscode(HttpServletRequest request, HttpServletResponse response) {
		AppUtil.responseUTF8(response);
		JSONObject returnJsonObj = new JSONObject();

		String msg = "";
		String statusCode = "";
		JSONObject data = new JSONObject();

		String mobile = request.getParameter("mobile");
		
		//验证码类型       0：登录验证码    1：渠道商用户修改密码验证码     2：渠道商忘记密码验证码    3:申请渠道商验证码接口
		String codeType = request.getParameter("codeType");
		
		// 验证参数
		try {
			checkParam(new String[] { "codeType", "mobile" }, codeType, mobile);
			
			List<DealerInfoEntity> dealer = systemService.findHql("from DealerInfoEntity where status = 0 and phone = ? ", mobile);
			
			logger.info("手机号: " + mobile);
			if (!mobile.matches(AppGlobals.CHECK_PHONE)) {
				msg = "手机号格式不正确";
				statusCode = "002";
			} else if(("1".equals(codeType) || "2".equals(codeType)) && dealer.size() <= 0){
				msg = "用户不存在";
				statusCode = "011";
			} else if ("0".equals(codeType) && dealer.size() > 0) {
				msg = "请从渠道商入口登录";
				statusCode = "010";
			} else {
				// 生成4位数的验证码
				String code = StringUtil.numRandom(4);
				logger.info("验证码: " + code);
				
				String templateCode = "";
				
				switch (codeType) {
				case "0":
					templateCode = SendMessageUtil.TEMPLATE_MODIFY_PASSWORD_SMS_CODE;
					break;
				case "1":
					templateCode = SendMessageUtil.TEMPLATE_SMS_CODE;
					break;
				case "2":
					templateCode = SendMessageUtil.TEMPLATE_SMS_CODE;
					break;
				default:
					break;
				}
				
				// 发送端短消息
				boolean b = SendMessageUtil.sendMessage(mobile, new String[] {"code"}, new String[] {code},
						templateCode , SendMessageUtil.TEMPLATE_SMS_CODE_SIGN_NAME);
//				boolean b = true;
				if (b) {
					
					// 判断用户是否在数据库中有记录用接口类方便扩展
					UserInfo user = systemService.findUniqueByProperty(CarCustomerEntity.class, "phone", mobile);
					
					// 当前时间
					String curTime = AppUtil.getCurTime();
					String sql = "";
					// 存储验证码相关信息
					if (user == null) {
						sql = "insert into car_customer set id='" + UUIDGenerator.generate()
								+ "',user_type = '0' ,phone = ? ,create_time = ? " + ",status = '0',login_count = 0,code_update_time = ? ,security_code = ?";
						systemService.executeSql(sql, mobile, curTime, curTime, code);
					} else {
						sql = "update car_customer set status = '0', code_update_time = ? ,security_code = ? where phone = ? ";
						systemService.executeSql(sql, curTime, code, mobile);
					}
	
					data.put("codemsg", code);
					msg = "短信发送成功";
					statusCode = AppGlobals.APP_SUCCESS;
				} else {
					//msg = "允许每分钟1条，累计每小时7条。每天10条";
					msg = "您的操作过于频繁，请稍后再试";
					statusCode = "003";
				}
			}
		} catch (ParameterException e) {
			e.printStackTrace();
			statusCode = e.getCode();
			msg = e.getErrorMessage();
			logger.error(e.getErrorMessage());
		}
		returnJsonObj.put("msg", msg);
		returnJsonObj.put("code", statusCode);
		returnJsonObj.put("data", data.toString());

		responseOutWrite(response, returnJsonObj);

	}*/
	
	// 短信发送接口
	@RequestMapping(params = "getSmscode")
	public void getSmscode(HttpServletRequest request, HttpServletResponse response) {
		AppUtil.responseUTF8(response);
		JSONObject returnJsonObj = new JSONObject();
		
		String msg = "";
		String statusCode = "";
		JSONObject data = new JSONObject();
		
		String param = "";
		try {
			param = AppUtil.inputToStr(request);
			
			System.out.println("getSmscode   前端传递参数：" + param);
			// 验证参数
			JSONObject jsondata = checkParam(param);
			
			String mobile = jsondata.getString("mobile");
			
			//验证码类型       0：登录验证码    1：渠道商用户修改密码验证码     2：渠道商忘记密码验证码    3:申请渠道商验证码接口
			String codeType = jsondata.getString("codeType");
			
			List<DealerInfoEntity> dealer = systemService.findHql("from DealerInfoEntity where status = 0 and phone = ? ", mobile);
			
			logger.info("手机号: " + mobile);
			if (!mobile.matches(AppGlobals.CHECK_PHONE)) {
				msg = "手机号格式不正确";
				statusCode = "002";
			} else if(("1".equals(codeType) || "2".equals(codeType)) && dealer.size() <= 0){
				msg = "用户不存在";
				statusCode = "011";
			} else if ("0".equals(codeType) && dealer.size() > 0) {
				msg = "请从渠道商入口登录";
				statusCode = "010";
			} else {
				// 生成4位数的验证码
				String code = StringUtil.numRandom(4);
				logger.info("验证码: " + code);
				
				String templateCode = "";
				
				boolean isApply = false;
				
				switch (codeType) {
				case "0":
					templateCode = SendMessageUtil.TEMPLATE_MODIFY_PASSWORD_SMS_CODE;
					break;
				case "1":
					templateCode = SendMessageUtil.TEMPLATE_SMS_CODE;
					break;
				case "2":
					templateCode = SendMessageUtil.TEMPLATE_SMS_CODE;
					break;
				case "3":
					//验证手机号是否重复
					DealerApplyEntity da = systemService.findUniqueByProperty(DealerApplyEntity.class, "phone", mobile);
					if(da != null){
						msg = "该手机号已申请，请勿重复提交";
						statusCode = "301";
						isApply = true;
					} else {
						long l = systemService.getCountForJdbcParam("select count(*) from dealer_info where phone=? and status='0' ", new Object[]{mobile});
						if(l > 0){
							msg = "该手机号已是渠道商，是否直接登录？";
							statusCode = "302";
							isApply = true;
						}
					}
					templateCode = SendMessageUtil.TEMPLATE_SMS_CODE;
					break;
				default:
					break;
				}
				
				//...
				if(!isApply){
					// 发送端短消息
					boolean b = SendMessageUtil.sendMessage(mobile, new String[] {"code"}, new String[] {code},
							templateCode , SendMessageUtil.TEMPLATE_SMS_CODE_SIGN_NAME);
					if (b) {
						// 存储验证码相关信息
						MessageCodeEntity mc = new MessageCodeEntity();
						mc.setCodeType(codeType);
						mc.setCreateTime(AppUtil.getDate());
						mc.setIsUsed("0");
						mc.setMsgCode(code);
						mc.setPhone(mobile);
						
						systemService.save(mc);
		
						data.put("codemsg", code);
						msg = "短信发送成功";
						statusCode = AppGlobals.APP_SUCCESS;
					} else {
						//msg = "允许每分钟1条，累计每小时7条。每天10条";
						msg = "您的操作过于频繁，请稍后再试";
						statusCode = "003";
					}
				}
			}
		} catch (ParameterException e) {
			e.printStackTrace();
			statusCode = e.getCode();
			msg = e.getErrorMessage();
			logger.error(e.getErrorMessage());
		} catch (Exception e) {
			statusCode = AppGlobals.SYSTEM_ERROR;
			msg = AppGlobals.SYSTEM_ERROR_MSG;
			e.printStackTrace();
		}
		returnJsonObj.put("msg", msg);
		returnJsonObj.put("code", statusCode);
		returnJsonObj.put("data", data.toString());

		responseOutWrite(response, returnJsonObj);

	}
		
	/*//短信验证码接口  (废弃)
	@RequestMapping(params = "getMsgCode")
	public void getMsgCode(HttpServletRequest request, HttpServletResponse response) {
		AppUtil.responseUTF8(response);
		JSONObject returnJsonObj = new JSONObject();

		String msg = "";
		String statusCode = "";
		JSONObject data = new JSONObject();

		String phone = request.getParameter("phone");
		
		//验证码类型       0：登录验证码    1：渠道商用户修改密码验证码     2：渠道商忘记密码验证码    3:申请渠道商验证码接口
		String codeType = request.getParameter("codeType");
		
		// 验证参数
		try {
			checkParam(new String[] { "codeType", "mobile" }, codeType, phone);
			
			logger.info("手机号: " + phone);
			if (!phone.matches(AppGlobals.CHECK_PHONE)) {
				msg = "手机号格式不正确";
				statusCode = "002";
			} else {
				// 生成4位数的验证码
				String code = StringUtil.numRandom(4);
				logger.info("验证码: " + code);
				
				String templateCode = "";
				
				templateCode = SendMessageUtil.TEMPLATE_SMS_CODE;
				
				// 发送端短消息
				boolean b = SendMessageUtil.sendMessage(phone, new String[] {"code"}, new String[] {code},
						templateCode , SendMessageUtil.TEMPLATE_SMS_CODE_SIGN_NAME);
				if (b) {
					
					MessageCodeEntity mc = new MessageCodeEntity();
					mc.setCodeType(codeType);
					mc.setCreateTime(AppUtil.getDate());
					mc.setIsUsed("0");
					mc.setMsgCode(code);
					mc.setPhone(phone);
					
					systemService.save(mc);
					
					data.put("codemsg", code);
					
					msg = "短信发送成功"; 
					statusCode = AppGlobals.APP_SUCCESS;
				} else {
					//msg = "允许每分钟1条，累计每小时7条。每天10条";
					msg = "您的操作过于频繁，请稍后再试";
					statusCode = "003";
				}
			}
		} catch (ParameterException e) {
			e.printStackTrace();
			statusCode = e.getCode();
			msg = e.getErrorMessage();
			logger.error(e.getErrorMessage());
		}
		returnJsonObj.put("msg", msg);
		returnJsonObj.put("code", statusCode);
		returnJsonObj.put("data", data.toString());

		responseOutWrite(response, returnJsonObj);

	}*/

	// 订单支付
	@RequestMapping(params = "createOrder")
	public void createOrder(HttpServletRequest request, HttpServletResponse response) {
		AppUtil.responseUTF8(response);
		JSONObject returnJsonObj = new JSONObject();

		String msg = "";
		String statusCode = "";
		JSONObject data = new JSONObject();
		boolean success = false;

		// 记录常用站点id
		String commonAddrId = "";

		// 订单前缀
		String orderPrefix = "";

		String param = "";
		try {
			param = AppUtil.inputToStr(request);
			
			System.out.println("createOrder   前端传递参数：" + param);
			
			// 验证参数
			JSONObject jsondata = checkParam(param);
			
			String token = jsondata.getString("token");
			// 验证token
			checkToken(token);

			String userPhone = jsondata.getString("userPhone");
			
			//在linux服务器中日期转换要指定格式
			Gson g = new GsonBuilder()  	
					  .setDateFormat("yyyy-MM-dd HH:mm:ss")  
					  .create();  
			
			TransferorderEntity t = g.fromJson(param, TransferorderEntity.class);
			
			// 和当前时间进行比较，出发时间和当前时间不能低于4个小时
			String startTime = t.getOrderStartime();
			Date curDate = AppUtil.getDate();
			Date departTime = DateUtils.str2Date(startTime, DateUtils.datetimeFormat);
			int m = AppUtil.compareDate(curDate, departTime, 'm', "abs");
			
			//暂时先判断24:00-5:30之间，以后可能会根据线路的运营时间来判断
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(departTime);
			
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			int minute = calendar.get(Calendar.MINUTE);
			
			int h = hour * 60 + minute;
			
			int maxMinute = 0;

			String msgHour = "";
			
			String userType = t.getOrderUserType();
			if("0".equals(userType)){
				maxMinute = 720;
				msgHour = "12";
			}else{
				maxMinute = 720 * 2;
				msgHour = "24";
			}
			
			// if (m > 240) { //测试
			if (m < maxMinute) {
				msg = "必须提前" + msgHour + "个小时才能下订单";
				statusCode = "888";
				success = false;
			} else if(h != 0 && h < 330){
				msg = "出发时间不能为24点到凌晨5点半之间";
				statusCode = "888";
				success = false;
			} else {
				
				//确保价格不被前端修改         使用微信平台的浏览器应该不会被修改数据，还是为了保险起见
				if("0".equals(userType)){
					LineInfoEntity l = appService.get(LineInfoEntity.class, t.getLineId());
					t.setOrderUnitprice(l.getPrice());
					BigDecimal tp = l.getPrice().multiply(new BigDecimal(t.getOrderNumbers()));
					t.setOrderTotalPrice(tp);
				} else if ("1".equals(userType)){
					List<DealerInfoEntity> dealer = systemService.findHql("from DealerInfoEntity where status = 0 and phone = ? ", userPhone);
					String tPrice = appService.getCarTypePrice(t.getOrderNumbers(), t.getLineId(), userPhone, dealer.get(0).getDealerDiscount());
					t.setOrderTotalPrice(new BigDecimal(tPrice));
				}
				
				String orderId = "";
				
				//如果是未支付的订单，进行修改之后，判断这个id是不是存在的如果是存在的，直接修改内容
				if(StringUtil.isNotEmpty(t.getId())){
					
					orderId = t.getId();
					appService.saveOrUpdate(t);
					
				} else {
					
					//订单状态 0：订单已完成。1：已付款待审核。2：审核通过待发车3：取消订单待退款。4：取消订单完成退款。5：拒绝退款。6：未支付
					//默认进来是未支付的
					t.setOrderStatus(6);
					
					//支付状态 0：已付款，1：退款中 2：已退款 3：未付款 4：已拒绝
					//默认进来是未付款
					t.setOrderPaystatus("3");
					
					t.setOrderHistory("0");
					
					String sId = t.getOrderStartingStationId();// 起始站
					String eId = t.getOrderTerminusStationId();// 终点站
					
					switch (t.getOrderType() + "") {
					case AppGlobals.AIRPORT_TO_DESTINATION_TYPE:
						commonAddrId = eId;
						orderPrefix = MakeOrderNum.AIRPORT_TO_DESTINATION_ORDER;
						break;
					case AppGlobals.DESTINATION_TO_AIRPORT_TYPE:
						commonAddrId = sId;
						orderPrefix = MakeOrderNum.AIRPORT_TO_DESTINATION_ORDER;
						break;
					case AppGlobals.TRAIN_TO_DESTINATION_TYPE:
						commonAddrId = eId;
						orderPrefix = MakeOrderNum.DESTINATION_TO_AIRPORT_ORDER;
						break;
					case AppGlobals.DESTINATION_TO_TRAIN_TYPE:
						commonAddrId = sId;
						orderPrefix = MakeOrderNum.DESTINATION_TO_AIRPORT_ORDER;
						break;
					default:
						break;
					}

					orderId = appService.saveOrder(t, orderPrefix, commonAddrId);
					logger.info("保存订单成功，订单状态为未支付");
				}
				
				data.put("orderId", orderId);
				
				statusCode = AppGlobals.APP_SUCCESS;
				msg = AppGlobals.APP_SUCCESS_MSG;
				success = true;
			}
		} catch (ParameterException e) {
			statusCode = e.getCode();
			msg = e.getErrorMessage();
			logger.error(e.getErrorMessage());
		} catch (Exception e) {
			statusCode = AppGlobals.SYSTEM_ERROR;
			msg = AppGlobals.SYSTEM_ERROR_MSG;
			e.printStackTrace();
		}
		
		logger.info("createOrder    orderId:" + data.toString());
		
		data.put("success", success);

		returnJsonObj.put("msg", msg);
		returnJsonObj.put("code", statusCode);
		returnJsonObj.put("data", data.toString());

		responseOutWrite(response, returnJsonObj);
	}

	// 获取机场站点或者火车站站点信息
	@RequestMapping(params = "getPTStation")
	public void getPTStation(HttpServletRequest request, HttpServletResponse response) {
		AppUtil.responseUTF8(response);
		JSONObject returnJsonObj = new JSONObject();
		String msg = "";
		String statusCode = "";
		JSONObject data = new JSONObject();

		try {
			// 2： 接机 3：送机 4：接火车 5：送火车
			String serveType = request.getParameter("serveType");
			// 所属城市
			String cityId = request.getParameter("cityId");
			//用户类型    0：普通用户    1：渠道商用户
			String userType = request.getParameter("userType");
						
			// 验证参数
			checkParam(new String[] { "serveType", "cityId", "userType" }, serveType, cityId, userType);
			
			List<AppStationInfoEntity> lList = appService.getPTStation(serveType, cityId, userType);
			
			data.put("PTStation", lList);
			
			statusCode = AppGlobals.APP_SUCCESS;
			msg = AppGlobals.APP_SUCCESS_MSG;
		} catch (ParameterException e) {
			statusCode = e.getCode();
			msg = e.getErrorMessage();
			logger.error(e.getErrorMessage());
		} catch (Exception e) {
			statusCode = AppGlobals.SYSTEM_ERROR;
			msg = AppGlobals.SYSTEM_ERROR_MSG;
			e.printStackTrace();
		}

		returnJsonObj.put("msg", msg);
		returnJsonObj.put("code", statusCode);
		returnJsonObj.put("data", data.toString());

		responseOutWrite(response, returnJsonObj);
	}
	
	// 获取线路站点信息
	@RequestMapping(params = "getStationList")
	public void getStationList(HttpServletRequest request, HttpServletResponse response) {
		AppUtil.responseUTF8(response);
		JSONObject returnJsonObj = new JSONObject();

		String msg = "";
		String statusCode = "";
		JSONObject data = new JSONObject();

		try {
			// 出行服务类型 2：接机 3：送机 4：接火车 5：送火车
			String serveType = request.getParameter("serveType");
			// 起点或终点id
			String stationId = request.getParameter("stationId");
			// 所属城市
			String cityId = request.getParameter("cityId");
			// token
			// String token = request.getParameter("token");
			
			String userId = request.getParameter("userId");
			
			//模糊查询站点条件
			String likeStation = request.getParameter("likeStation");

			//用户类型    0：普通用户    1：渠道商用户
			String userType = request.getParameter("userType");
			
			// 验证参数
			checkParam(new String[] { "serveType", "stationId", "cityId", "userType"}, serveType, stationId, cityId, userType);

			//线路信息
			List<AppLineStationInfoEntity> lList = new ArrayList<>();
			//用户常用站点信息
			List<AppStationInfoEntity> cList = new ArrayList<>();
			//站点信息
			List<AppStationInfoEntity> stationList = new ArrayList<>();
			//如果是渠道商用户，没有开通渠道商线路的，不要返回
			appService.getLinebyStation(serveType, cityId, stationId, userId, likeStation, lList, cList, stationList, userType);
			
			data.put("addrs", cList);
			data.put("lineInfo", lList);
			data.put("stationInfo", stationList);
			statusCode = AppGlobals.APP_SUCCESS;
			msg = AppGlobals.APP_SUCCESS_MSG;
		} catch (ParameterException e) {
			statusCode = e.getCode();
			msg = e.getErrorMessage();
			logger.error(e.getErrorMessage());
		} catch (Exception e) {
			statusCode = AppGlobals.SYSTEM_ERROR;
			msg = AppGlobals.SYSTEM_ERROR_MSG;
			e.printStackTrace();
		}

		returnJsonObj.put("msg", msg);
		returnJsonObj.put("code", statusCode);
		returnJsonObj.put("data", data.toString());

		responseOutWrite(response, returnJsonObj);
	}
	
	/** 获取开通业务城市列表 */
	@RequestMapping(params = "getCitys")
	public void getCitys(HttpServletRequest request, HttpServletResponse response) {
		AppUtil.responseUTF8(response);
		JSONObject returnJsonObj = new JSONObject();

		// String token = request.getParameter("token");

		String msg = "";
		String statusCode = "";
		// JSONObject data = new JSONObject();
		List<OpenCityEntity> oList = null;
		try {
			oList = systemService.findByProperty(OpenCityEntity.class, "status", "0");

			statusCode = AppGlobals.APP_SUCCESS;
			msg = AppGlobals.APP_SUCCESS_MSG;
		} catch (Exception e) {
			statusCode = AppGlobals.SYSTEM_ERROR;
			msg = AppGlobals.SYSTEM_ERROR_MSG;
			e.printStackTrace();
		}

		returnJsonObj.put("msg", msg);
		returnJsonObj.put("code", statusCode);
		if (oList != null && oList.size() > 0) {
			returnJsonObj.put("data", oList);
		} else {
			returnJsonObj.put("data", "");
		}

		responseOutWrite(response, returnJsonObj);
	}
	
	/**
	 * 删除订单
	 */
	@RequestMapping(params = "delOrder")
	public void delOrder(HttpServletRequest request, HttpServletResponse response) {
		AppUtil.responseUTF8(response);
		JSONObject returnJsonObj = new JSONObject();
		boolean success = false;
		String msg = "";
		String statusCode = "";
		try {
			String param = AppUtil.inputToStr(request); 
			//验证参数
			JSONObject jsondata = checkParam(param);
			
			String orderId = jsondata.getString("orderId");
			TransferorderEntity t = this.systemService.getEntity(TransferorderEntity.class, orderId);
			if (t!=null) {
				//订单已经完成、取消订单完成退款、拒绝退款的订单更改delete_flag
				if(t.getOrderStatus()==0 || t.getOrderStatus()==4 || t.getOrderStatus()==5){
					String sql = "update transferorder set delete_flag='1' where id in ('"+orderId+"')";
					systemService.updateBySqlString(sql.toString());
					statusCode = AppGlobals.APP_SUCCESS;
					returnJsonObj.put("success", true);
					msg = AppGlobals.APP_SUCCESS_MSG;
				}else if(t.getOrderStatus()==6){
					systemService.delete(t);
					returnJsonObj.put("success", true);
					statusCode = AppGlobals.APP_SUCCESS;
					msg = AppGlobals.APP_SUCCESS_MSG;
				}else{
					statusCode = "666";
					msg = "订单不能被删除";
					returnJsonObj.put("success", success);
				}
			}else{
				statusCode = AppGlobals.SYSTEM_ERROR;
				msg = AppGlobals.SYSTEM_ERROR_MSG;
			}
			
			
		} catch (Exception e) {
			statusCode = AppGlobals.SYSTEM_ERROR;
			msg = AppGlobals.SYSTEM_ERROR_MSG;
			e.printStackTrace();
		}

		returnJsonObj.put("msg", msg);
		returnJsonObj.put("code", statusCode);
		responseOutWrite(response, returnJsonObj);
	}

	/** 获取用户订单列表 get */
	@RequestMapping(params = "getOrderList")
	public void getOrderList(HttpServletRequest request, HttpServletResponse response) {
		AppUtil.responseUTF8(response);
		JSONObject returnJsonObj = new JSONObject();

		String msg = "";
		String statusCode = "";
		List<AppUserOrderEntity> auoList = null;
		try {
			String token = request.getParameter("token");
			String userId = request.getParameter("userId");

			// 订单支付状态 0：已付款，1：退款中 2：已退款 3: 未付款
			// String orderStatus = request.getParameter("orderStatus");

			// 验证参数
			// checkParam(new String[] { "token", "orderId", "orderStatus"},
			// token, userId, orderStatus);
			checkParam(new String[] { "token", "userId" }, token, userId);

			// 验证token
			checkToken(token);

			String pageNo = request.getParameter("pageNo");
			if (!StringUtil.isNotEmpty(pageNo)) {
				pageNo = "1";
			}

			String maxPageItem = request.getParameter("maxPageItem");
			if (!StringUtil.isNotEmpty(maxPageItem)) {
				maxPageItem = "150";
			}
			
			auoList = appService.getUserOrders(userId, null, pageNo, maxPageItem);
			
			statusCode = AppGlobals.APP_SUCCESS;
			msg = AppGlobals.APP_SUCCESS_MSG;
		} catch (ParameterException e) {
			statusCode = e.getCode();
			msg = e.getErrorMessage();
			logger.error(e.getErrorMessage());
		} catch (Exception e) {
			statusCode = AppGlobals.SYSTEM_ERROR;
			msg = AppGlobals.SYSTEM_ERROR_MSG;
			e.printStackTrace();
		}

		returnJsonObj.put("msg", msg);
		returnJsonObj.put("code", statusCode);
		if (auoList != null && auoList.size() > 0) {
			returnJsonObj.put("data", auoList);
		} else {
			returnJsonObj.put("data", "");
		}

		responseOutWrite(response, returnJsonObj);
	}

	/** 订单详情 */
	@RequestMapping(params = "getOrderDetail")
	public void getOrderDetail(HttpServletRequest request, HttpServletResponse response) {
		AppUtil.responseUTF8(response);
		JSONObject returnJsonObj = new JSONObject();

		String msg = "";
		String statusCode = "";

		String token = request.getParameter("token");
		String orderId = request.getParameter("orderId");
		String userType = request.getParameter("userType");
		
		AppUserOrderDetailEntity aod = null;

		try {

			// 验证参数
			checkParam(new String[] { "token", "orderId" }, token, orderId);

			// 验证token
			checkToken(token);

			String messageId = request.getParameter("messageId");
			
			if(messageId != null ){
				//进入消息详情页后就将所有的消息改为已读
				String sql = "update customer_message set status=1 where id=? ";
				systemService.executeSql(sql, messageId);
			}
			
			aod = appService.getOrderDetailById(orderId, userType);
			
			statusCode = AppGlobals.APP_SUCCESS;
			msg = AppGlobals.APP_SUCCESS_MSG;
		} catch (ParameterException e) {
			statusCode = e.getCode();
			msg = e.getErrorMessage();
			logger.error(e.getErrorMessage());
		} catch (Exception e) {
			statusCode = AppGlobals.SYSTEM_ERROR;
			msg = AppGlobals.SYSTEM_ERROR_MSG;
			e.printStackTrace();
		}

		returnJsonObj.put("msg", msg);
		returnJsonObj.put("code", statusCode);
		returnJsonObj.put("data", aod);

		responseOutWrite(response, returnJsonObj);
	}
	
	/** 订单所属线路的站点列表 */
	@RequestMapping(params = "getOrderStation")
	public void getOrderStation(HttpServletRequest request, HttpServletResponse response) {
		AppUtil.responseUTF8(response);
		JSONObject returnJsonObj = new JSONObject();
		
		JSONObject data = new JSONObject();
		
		String msg = "";
		String statusCode = "";
		String param = "";
		try {
			
			param = AppUtil.inputToStr(request);
			System.out.println("getOrderStation     前端传递参数：" + param);
			
			// 验证参数
			JSONObject jsondata = checkParam(param);
			
			String token = jsondata.getString("token");
			// 验证token
			checkToken(token);
			
			String orderId = jsondata.getString("orderId");
			
			List<Map<String,Object>> lm = systemService.findForJdbc(" select b.name,b.id,lb.siteOrder from transferorder t join lineinfo l on t.line_id = l.id join line_busstop lb on l.id = lb.lineId "
					+ " join busstopinfo b on b.id = lb.busStopsId "
					+ " where t.id = ? order by lb.siteOrder", orderId);
			
			data.put("stationInfo", lm);

			statusCode = AppGlobals.APP_SUCCESS;
			msg = AppGlobals.APP_SUCCESS_MSG;
		} catch (ParameterException e) {
			statusCode = e.getCode();
			msg = e.getErrorMessage();
			logger.error(e.getErrorMessage());
		} catch (Exception e) {
			statusCode = AppGlobals.SYSTEM_ERROR;
			msg = AppGlobals.SYSTEM_ERROR_MSG;
			e.printStackTrace();
		}

		returnJsonObj.put("msg", msg);
		returnJsonObj.put("code", statusCode);
		returnJsonObj.put("data", data.toString());

		responseOutWrite(response, returnJsonObj);
	}

	/** 修改订单 （未支付订单修改） */
	@RequestMapping(params = "modifyOrder")
	public void modifyOrder(HttpServletRequest request, HttpServletResponse response) {
		AppUtil.responseUTF8(response);
		JSONObject returnJsonObj = new JSONObject();

		String msg = "";
		String statusCode = "";

		String param = "";

		// List<Map<String,Object>> list = null;
		// List<TransferorderEntity> list = null;
		TransferorderEntity t = null;
		try {

			param = AppUtil.inputToStr(request);
			System.out.println("modifyOrder     前端传递参数：" + param);

			// 验证参数
			JSONObject jsondata = checkParam(param);

			String token = jsondata.getString("token");
			// 验证token
			checkToken(token);

			String orderId = jsondata.getString("orderId");

			t = systemService.getEntity(TransferorderEntity.class, orderId);
			
			if (t != null) {
				returnJsonObj.put("data", t);

				// 把json中的日期类型替换成字符串类型
				Date date = t.getApplicationTime();
				Date date1 = t.getOrderExpectedarrival();
				JSONObject str = (JSONObject) returnJsonObj.get("data");
				str.put("applicationTime", date.toString());
				str.put("orderExpectedarrival", date1.toString());

			} else {
				returnJsonObj.put("data", "");
				throw new Exception();
			}
			
			statusCode = AppGlobals.APP_SUCCESS;
			msg = AppGlobals.APP_SUCCESS_MSG;
		} catch (ParameterException e) {
			statusCode = e.getCode();
			msg = e.getErrorMessage();
			logger.error(e.getErrorMessage());
		} catch (Exception e) {
			statusCode = AppGlobals.SYSTEM_ERROR;
			msg = AppGlobals.SYSTEM_ERROR_MSG;
			e.printStackTrace();
		}

		returnJsonObj.put("msg", msg);
		returnJsonObj.put("code", statusCode);

		responseOutWrite(response, returnJsonObj);
	}
	
	/** 订单改期 */
	@RequestMapping(params = "modifyPayOrder")
	public void modifyPayOrder(HttpServletRequest request, HttpServletResponse response) {
		AppUtil.responseUTF8(response);
		JSONObject returnJsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		
		String msg = "";
		String statusCode = "";
		String param = "";

		boolean b = false;
		
		// List<Map<String,Object>> list = null;
		// List<TransferorderEntity> list = null;
		TransferorderEntity t = null;
		try {
			param = AppUtil.inputToStr(request);
			System.out.println("modifyPayOrder     前端传递参数：" + param);

			// 验证参数
			JSONObject jsondata = checkParam(param);

			String token = jsondata.getString("token");
			// 验证token
			checkToken(token);
			
			String orderId = jsondata.getString("orderId");
			
			String departTime = jsondata.getString("departTime");
			
			if(StringUtil.isNotEmpty(orderId)){
				
				t = systemService.getEntity(TransferorderEntity.class, orderId);
				if(StringUtil.isNotEmpty(t)){
					String lineId = t.getLineId();
					
					// 订单批次编号
					String lineOrderCodel = "";

					LineInfoEntity lineinfo= systemService.getEntity(LineInfoEntity.class, lineId);
					if(StringUtil.isNotEmpty(lineinfo)){
						String lin = lineinfo.getLineTimes();
						
						String str = lineinfo.getLineNumber();
						String orderstart = departTime;
						
						if (StringUtil.isNotEmpty(orderstart)) {
							Date w = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(orderstart);
							long nowLong = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmm").format(w));
							String s = nowLong + "";
							String a = s.substring(10);
							String c = s.substring(2, 10);
							System.out.println(c);
							if (Integer.parseInt(a) < 30) {
								lineOrderCodel = str + c + "A";
							} else {
								lineOrderCodel = str + c + "B";
							}
						}
						
						
						t.setLineOrderCode(lineOrderCodel);
						
						Calendar calendar = Calendar.getInstance();
						SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
						Date date = sdf.parse(departTime);
						calendar.setTime(date);
						if(StringUtil.isNotEmpty(lin)){
							calendar.add(Calendar.MINUTE,Integer.parseInt(lin));
							t.setOrderExpectedarrival(calendar.getTime());//设置预计到达时间
						}else{
							msg="没有得到线路时长";
						}
						
					}else{
						msg="没有得到线路";
					}
					
					t.setOrderStartime(departTime);
					
					//清空司机车辆信息和订单状态
					t.setOrderStatus(1);
					//删除和此订单关联的id
					String delSql = "delete from order_linecardiver where id = ?";
					systemService.executeSql(delSql, t.getId());
				}else{
					msg="无效的订单";
				}
			}else{
				msg="未获取到订单";
			}
			
			b = true;
			data.put("success", b);
			statusCode = AppGlobals.APP_SUCCESS;
			msg = AppGlobals.APP_SUCCESS_MSG;
		} catch (ParameterException e) {
			statusCode = e.getCode();
			msg = e.getErrorMessage();
			logger.error(e.getErrorMessage());
		} catch (Exception e) {
			statusCode = AppGlobals.SYSTEM_ERROR;
			msg = AppGlobals.SYSTEM_ERROR_MSG;
			e.printStackTrace();
		}

		returnJsonObj.put("msg", msg);
		returnJsonObj.put("code", statusCode);

		returnJsonObj.put("data", data);

		responseOutWrite(response, returnJsonObj);
	}
	
	/** 取消订单 */
	@RequestMapping(params = "cancelOrder")
	public void cancelOrder(HttpServletRequest request, HttpServletResponse response) {
		AppUtil.responseUTF8(response);
		JSONObject returnJsonObj = new JSONObject();

		JSONObject obj = new JSONObject();
		boolean success = false;

		String msg = "";
		String statusCode = "";

		String param = "";

		try {

			param = AppUtil.inputToStr(request);
			System.out.println("cancelOrder     前端传递参数：" + param);

			// 验证参数
			JSONObject jsondata = checkParam(param);

			String token = jsondata.getString("token");
			// 验证token
			checkToken(token);
			
			String orderId = jsondata.getString("orderId");
			
			// 需不需要做时间的判断 在发车1个小时之前才能取消订单 (是需要的...)
			TransferorderEntity t = systemService.getEntity(TransferorderEntity.class, orderId);
			
			Date curDate = AppUtil.getDate();
			String sTime = t.getOrderStartime();
			Date departTime = DateUtils.str2Date(sTime, DateUtils.datetimeFormat);
			int m = AppUtil.compareDate(departTime, curDate, 'm', "");

			if (m < 240) {
				msg = "距离发车时间不到4个小时，无法取消订单";
				success = false;
			} else {
				// 记录取消订单之前的状态
				t.setBeforeStatus(t.getOrderStatus() + "");
				System.out.println("取消订单之前的状态:" + t.getOrderStatus());
				
				// 0：订单已完成。1：已付款待审核。2：审核通过待发车3：取消订单待退款。4：取消订单完成退款。
				t.setOrderStatus(3);
				// 支付状态 0：已付款，1：退款中 2：已退款 3：未付款
				t.setOrderPaystatus("1");
				
				t.setRefundTime(AppUtil.getCurTime());
				systemService.updateEntitie(t);

				msg = AppGlobals.APP_SUCCESS_MSG;
				success = true;
			}
			statusCode = AppGlobals.APP_SUCCESS;

		} catch (ParameterException e) {
			statusCode = e.getCode();
			msg = e.getErrorMessage();
			logger.error(e.getErrorMessage());
		} catch (Exception e) {
			statusCode = AppGlobals.SYSTEM_ERROR;
			msg = AppGlobals.SYSTEM_ERROR_MSG;
			e.printStackTrace();
		}

		obj.put("success", success);

		returnJsonObj.put("msg", msg);
		returnJsonObj.put("code", statusCode);
		returnJsonObj.put("data", obj);

		responseOutWrite(response, returnJsonObj);
	}

	/** 确认完成 */
	@RequestMapping(params = "completeOrder")
	public void completeOrder(HttpServletRequest request, HttpServletResponse response) {
		AppUtil.responseUTF8(response);
		JSONObject returnJsonObj = new JSONObject();

		JSONObject data = new JSONObject();
		boolean success = false;

		String msg = "";
		String statusCode = "";

		String param = "";

		try {
			param = AppUtil.inputToStr(request);
			System.out.println("completeOrder    前端传递参数：" + param);

			// 验证参数
			JSONObject jsondata = checkParam(param);

			String token = jsondata.getString("token");
			// 验证token
			checkToken(token);

			String orderId = jsondata.getString("orderId");

			TransferorderEntity t = systemService.getEntity(TransferorderEntity.class, orderId);
			// 0：订单已完成。1：已付款待审核。2：审核通过待发车3：取消订单待退款。4：取消订单完成退款。
			t.setOrderStatus(0);
			systemService.updateEntitie(t);

			statusCode = AppGlobals.APP_SUCCESS;
			msg = AppGlobals.APP_SUCCESS_MSG;
			success = true;
		} catch (ParameterException e) {
			statusCode = e.getCode();
			msg = e.getErrorMessage();
			logger.error(e.getErrorMessage());
		} catch (Exception e) {
			statusCode = AppGlobals.SYSTEM_ERROR;
			msg = AppGlobals.SYSTEM_ERROR_MSG;
			e.printStackTrace();
		}

		data.put("success", success);

		returnJsonObj.put("msg", msg);
		returnJsonObj.put("code", statusCode);
		returnJsonObj.put("data", data.toString());

		responseOutWrite(response, returnJsonObj);
	}

	/** 验票界面 */
	@RequestMapping(params = "checkTicket")
	public void checkTicket(HttpServletRequest request, HttpServletResponse response) {
		AppUtil.responseUTF8(response);
		JSONObject returnJsonObj = new JSONObject();

		String msg = "";
		String statusCode = "";
		List<AppCheckTicket> actlist = new ArrayList<>();
		try {
			String token = request.getParameter("token");
			String userId = request.getParameter("userId");

			// 验证参数
			checkParam(new String[] { "token", "userId" }, token, userId);

			// 验证token
			checkToken(token);

			String pageNo = request.getParameter("pageNo");
			if (!StringUtil.isNotEmpty(pageNo)) {
				pageNo = "1";
			}

			String maxPageItem = request.getParameter("maxPageItem");
			if (!StringUtil.isNotEmpty(maxPageItem)) {
				maxPageItem = "15";
			}

			actlist = appService.getTicketListById(userId, pageNo, maxPageItem);

			statusCode = AppGlobals.APP_SUCCESS;
			msg = AppGlobals.APP_SUCCESS_MSG;
		} catch (ParameterException e) {
			statusCode = e.getCode();
			msg = e.getErrorMessage();
			logger.error(e.getErrorMessage());
		} catch (Exception e) {
			statusCode = AppGlobals.SYSTEM_ERROR;
			msg = AppGlobals.SYSTEM_ERROR_MSG;
			e.printStackTrace();
		}

		returnJsonObj.put("msg", msg);
		returnJsonObj.put("code", statusCode);
		if (actlist != null && actlist.size() > 0) {
			returnJsonObj.put("data", actlist);
		} else {
			returnJsonObj.put("data", "");
		}

		responseOutWrite(response, returnJsonObj);
	}

	/** 意见反馈 */
	@RequestMapping(params = "feedback")
	public void feedback(HttpServletRequest request, HttpServletResponse response) {
		AppUtil.responseUTF8(response);
		JSONObject returnJsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		
		String msg = "";
		String statusCode = "";
		boolean success = false;

		String param = "";

		try {
			param = AppUtil.inputToStr(request); 
			System.out.println("feedback    前端传递参数：" + param);

			// 验证参数
			JSONObject jsondata = checkParam(param);
			
			// 验证token
			String token = jsondata.getString("token");
			checkToken(token);

			Gson g = new Gson();
			FeedbackEntity t = g.fromJson(param, FeedbackEntity.class);
			t.setCreateTime(AppUtil.getCurTime());
			t.setId(UUIDGenerator.generate());

			systemService.save(t);
			
			success = true;
			statusCode = AppGlobals.APP_SUCCESS;
			msg = AppGlobals.APP_SUCCESS_MSG;
		} catch (ParameterException e) {
			statusCode = e.getCode();
			msg = e.getErrorMessage();
			logger.error(e.getErrorMessage());
		} catch (Exception e) {
			statusCode = AppGlobals.SYSTEM_ERROR;
			msg = AppGlobals.SYSTEM_ERROR_MSG;
			e.printStackTrace();
		}
		data.put("success", success);
		returnJsonObj.put("msg", msg);
		returnJsonObj.put("code", statusCode);
		returnJsonObj.put("data", data);

		responseOutWrite(response, returnJsonObj);
	}

	/** 用户个人信息 */
	@RequestMapping(params = "getUserInfo")
	public void personalInfo(HttpServletRequest request, HttpServletResponse response) {
		AppUtil.responseUTF8(response);
		JSONObject returnJsonObj = new JSONObject();

		JSONObject data = new JSONObject();

		String msg = "";
		String statusCode = "";

		String param = "";

		AppCustomerEntity a = null;

		try {
			param = AppUtil.inputToStr(request);
			System.out.println("getUserInfo     前端传递参数：" + param);

			// 验证参数
			JSONObject jsondata = checkParam(param);

			String token = jsondata.getString("token");
			// 验证token
			checkToken(token);

			String userId = jsondata.getString("userId");

			CarCustomerEntity cc = systemService.getEntity(CarCustomerEntity.class, userId);

			a = new AppCustomerEntity();
			a.setAddress(cc.getAddress());
			a.setCardNumber(cc.getCardNumber());
			a.setUserId(cc.getId());
			a.setCustomerImg(cc.getCustomerImg());
			a.setPhone(cc.getPhone());
			a.setRealName(cc.getRealName());
			a.setSex(cc.getSex());

			List<Map<String, Object>> map = systemService.findForJdbc(
					"select b.name,b.stopLocation from customer_common_addr c inner join busstopinfo b on c.station_id=b.id where user_id = ? ", userId);

			data.put("addrs", map);
			data.put("customerInfo", a);

			statusCode = AppGlobals.APP_SUCCESS;
			msg = AppGlobals.APP_SUCCESS_MSG;
		} catch (ParameterException e) {
			statusCode = e.getCode();
			msg = e.getErrorMessage();
			logger.error(e.getErrorMessage());
		} catch (Exception e) {
			statusCode = AppGlobals.SYSTEM_ERROR;
			msg = AppGlobals.SYSTEM_ERROR_MSG;
			e.printStackTrace();
		}

		returnJsonObj.put("msg", msg);
		returnJsonObj.put("code", statusCode);
		returnJsonObj.put("data", data.toString());

		responseOutWrite(response, returnJsonObj);
	}

	/** 修改用户个人 */
	@RequestMapping(params = "updateUserInfo")
	public void uploadPersonalInfo(HttpServletRequest request, HttpServletResponse response) {
		AppUtil.responseUTF8(response);
		JSONObject returnJsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		
		String msg = "";
		String statusCode = "";
		boolean success = false;
		//图片路径
		String imgUrl = "";
		String param = "";

		try {
			param = AppUtil.inputToStr(request);
//			logger.info("前端传递参数：" + param);

			// 验证参数
			JSONObject jsondata = checkParam(param);

			String token = jsondata.getString("token");
			// 验证token
			checkToken(token);

			String imagesBaseBM = jsondata.getString("header");
			String pName = jsondata.getString("imgName");
			String userId = jsondata.getString("userId");

			String idCard = jsondata.getString("idCard");
			String address = jsondata.getString("address");
			String userName = jsondata.getString("userName");
			
			logger.info("uploadPersonalInfo   前端传递参数：  imgName=" + pName + "    userId=" + userId + "    address=" + address 
					+ "    userName=" + userName + "    idCard=" + idCard);
			
			CarCustomerEntity cc = systemService.getEntity(CarCustomerEntity.class, userId);
			String path = AppGlobals.IMAGE_BASE_FILE_PATH;
			if(StringUtil.isNotEmpty(imagesBaseBM)){
				// 获取图片存储路径
				imgUrl = AppGlobals.APP_USER_FILE_PATH + userId //+ "_" + System.currentTimeMillis()
						+ pName.substring(pName.lastIndexOf("."), pName.length());
				imagesBaseBM = imagesBaseBM.replaceAll(" ", "+");
				Base64ImageUtil.generateImage(imagesBaseBM, path + imgUrl);
				
				cc.setCustomerImg(imgUrl);
			}
			cc.setAddress(address);
			cc.setCardNumber(idCard);
			cc.setUserName(userName);
			cc.setRealName(userName);
			
			systemService.save(cc);
			
			//查询用户信息，然后返回给前端
			data.put("customerImg", cc.getCustomerImg());
			data.put("phone", cc.getPhone());
			data.put("token", cc.getToken());
			data.put("userId", cc.getUserId());
			data.put("userName", cc.getUserName());
			
			success = true;
			statusCode = AppGlobals.APP_SUCCESS;
			msg = AppGlobals.APP_SUCCESS_MSG;
		} catch (ParameterException e) {
			statusCode = e.getCode();
			msg = e.getErrorMessage();
			logger.error(e.getErrorMessage());
		} catch (Exception e) {
			statusCode = AppGlobals.SYSTEM_ERROR;
			msg = AppGlobals.SYSTEM_ERROR_MSG;
			e.printStackTrace();
		}
		
		data.put("success", success);
		returnJsonObj.put("msg", msg);
		returnJsonObj.put("code", statusCode);
		returnJsonObj.put("data", data);

		responseOutWrite(response, returnJsonObj);
	}

	/** 当前用户是否有新消息 */
	@RequestMapping(params = "hasNewMessage")
	public void hasNewMessage(HttpServletRequest request, HttpServletResponse response) {
		AppUtil.responseUTF8(response);
		JSONObject returnJsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		
		String msg = "";
		String statusCode = "";
		boolean success = false;
		
		try {
			String token = request.getParameter("token");
			String userId = request.getParameter("userId");

			// 验证参数
			checkParam(new String[] { "token", "userId" }, token, userId);
			
			// 验证token
			checkToken(token);
			
			String sql = "select count(*) from car_customer where id='" + userId + "' and msg_status='0' ";
			long c = appService.getCountForJdbc(sql);
			if(c > 0){
				success = true;
			}
			
			data.put("success", success);
			statusCode = AppGlobals.APP_SUCCESS;
			msg = AppGlobals.APP_SUCCESS_MSG;
		} catch (ParameterException e) {
			statusCode = e.getCode();
			msg = e.getErrorMessage();
			logger.error(e.getErrorMessage());
		} catch (Exception e) {
			statusCode = AppGlobals.SYSTEM_ERROR;
			msg = AppGlobals.SYSTEM_ERROR_MSG;
			e.printStackTrace();
		}

		returnJsonObj.put("msg", msg);
		returnJsonObj.put("code", statusCode);
		returnJsonObj.put("data", data);

		responseOutWrite(response, returnJsonObj);
	}
	
	/** 获取消息通知 */
	@RequestMapping(params = "getMessage")
	public void getMessage(HttpServletRequest request, HttpServletResponse response) {
		AppUtil.responseUTF8(response);
		JSONObject returnJsonObj = new JSONObject();
		
		String msg = "";
		String statusCode = "";

		List<AppMessageListEntity> mList = null;
		try {

			String token = request.getParameter("token");
			String userId = request.getParameter("userId");
			
			// 验证参数
			checkParam(new String[] { "token", "userId" }, token, userId);
			
			// 验证token
			checkToken(token);
			
			String pageNo = request.getParameter("pageNo");
			if (!StringUtil.isNotEmpty(pageNo)) {
				pageNo = "1";
			}

			String maxPageItem = request.getParameter("maxPageItem");
			if (!StringUtil.isNotEmpty(maxPageItem)) {
				maxPageItem = "15";
			}

			mList = appService.getMessageListById(userId, pageNo, maxPageItem);
			systemService.updateBySqlString("update car_customer set msg_status='1' where id='" + userId + "'");
			
			statusCode = AppGlobals.APP_SUCCESS;
			msg = AppGlobals.APP_SUCCESS_MSG;
		} catch (ParameterException e) {
			statusCode = e.getCode();
			msg = e.getErrorMessage();
			logger.error(e.getErrorMessage());
		} catch (Exception e) {
			statusCode = AppGlobals.SYSTEM_ERROR;
			msg = AppGlobals.SYSTEM_ERROR_MSG;
			e.printStackTrace();
		}

		returnJsonObj.put("msg", msg);
		returnJsonObj.put("code", statusCode);
		if (mList != null && mList.size() > 0) {
			returnJsonObj.put("data", mList);
		} else {
			returnJsonObj.put("data", "");
		}

		responseOutWrite(response, returnJsonObj);
	}
	
	/** 删除消息通知 */
	@RequestMapping(params = "delMessage")
	public void delMessage(HttpServletRequest request, HttpServletResponse response) {
		AppUtil.responseUTF8(response);
		JSONObject returnJsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		
		boolean success = false;
		String msg = "";
		String statusCode = "";

		try {
//			param = AppUtil.inputToStr(request);
//
//			System.out.println("前端传递参数：" + param);
//
//			JSONObject jsondata = JSONObject.fromObject(param);
//
//			// 验证参数
//			checkParam(jsondata);
//
//			String token = jsondata.getString("token");
//			String messageId = request.getParameter("messageId");

			String token = request.getParameter("token");
			String messageId = request.getParameter("messageId");
			
			System.out.println("delMessage  前端传递参数：token:" + token + "---messageId:" + messageId);
			
			// 验证参数
			checkParam(new String[] { "token", "messageId" }, token, messageId);

			// 验证token
			checkToken(token);

			AppMessageListEntity a = appService.get(AppMessageListEntity.class, messageId);
			if(a != null){
				appService.delete(a);
			}
			
			success = true;
			statusCode = AppGlobals.APP_SUCCESS;
			msg = AppGlobals.APP_SUCCESS_MSG;
		} catch (ParameterException e) {
			statusCode = e.getCode();
			msg = e.getErrorMessage();
			logger.error(e.getErrorMessage());
		} catch (Exception e) {
			statusCode = AppGlobals.SYSTEM_ERROR;
			msg = AppGlobals.SYSTEM_ERROR_MSG;
			e.printStackTrace();
		}

		data.put("success", success);

		returnJsonObj.put("msg", msg);
		returnJsonObj.put("code", statusCode);
		returnJsonObj.put("data", data.toString());

		responseOutWrite(response, returnJsonObj);
	}

	/** 生成二维码接口 */
	@RequestMapping(params = "generateQRCode")
	public void generateQRCode(HttpServletRequest request, HttpServletResponse response) {
		AppUtil.responseUTF8(response);
		JSONObject returnJsonObj = new JSONObject();
		JSONObject data = new JSONObject();
		
		boolean success = false;
		String msg = "";
		String statusCode = "";
		
		try {
			
			String token = request.getParameter("token");
			String account = request.getParameter("account");
			
			System.out.println("generateQRCode    前端传递参数：token:" + token + "---messageId:" + account);
			
			// 验证参数
			checkParam(new String[] { "token", "account" }, token, account);

			// 验证token
			checkToken(token);
			
			JsonObject j = new JsonObject();
			j.addProperty("account", account);
			String path = AppGlobals.QR_CODE_FILE_PATH + account + ".jpg";
			WeixinPayUtil.getQRCode(j.toString(), AppGlobals.IMAGE_BASE_FILE_PATH + path);
			data.put("QRUrl", path);
			
			success = true;
			statusCode = AppGlobals.APP_SUCCESS;
			msg = AppGlobals.APP_SUCCESS_MSG;
		} catch (ParameterException e) {
			statusCode = e.getCode();
			msg = e.getErrorMessage();
			logger.error(e.getErrorMessage());
		} catch (Exception e) {
			statusCode = AppGlobals.SYSTEM_ERROR;
			msg = AppGlobals.SYSTEM_ERROR_MSG;
			e.printStackTrace();
		}

		data.put("success", success);

		returnJsonObj.put("msg", msg);
		returnJsonObj.put("code", statusCode);
		returnJsonObj.put("data", data.toString());

		responseOutWrite(response, returnJsonObj);
	}
	
}
