package com.yhy.lin.app.controller;

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
import com.yhy.lin.app.entity.UserInfo;
import com.yhy.lin.app.exception.ParameterException;
import com.yhy.lin.app.service.AppInterfaceService;
import com.yhy.lin.app.util.AppGlobals;
import com.yhy.lin.app.util.AppUtil;
import com.yhy.lin.app.util.Base64ImageUtil;
import com.yhy.lin.app.util.MakeOrderNum;
import com.yhy.lin.app.wechat.WeixinPayUtil;
import com.yhy.lin.entity.OpenCityEntity;
import com.yhy.lin.entity.TransferorderEntity;

import ch.qos.logback.classic.net.SyslogAppender;

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

	// 登录接口
	@RequestMapping(params = "appLogin")
	public void AppLogin(HttpServletRequest request, HttpServletResponse response) {
		AppUtil.responseUTF8(response);
		JSONObject returnJsonObj = new JSONObject();

		String msg = "";
		String statusCode = "";
		JSONObject data = new JSONObject();
		
		String isNew = "";

		try {
			// String mobile = request.getParameter("mobile");// 用户名
			// String code = request.getParameter("code");// 验证码
			
//			String mobile = request.getParameter("mobile");		// 授权参数
//			String code = request.getParameter("code");		// 授权参数
//			String openId = request.getParameter("openId");		// 授权参数
			
			String param = AppUtil.inputToStr(request);
			System.out.println("前端传递参数：" + param);
//			
			JSONObject jsondata = JSONObject.fromObject(param);
//			
			//验证参数
			checkParam(jsondata);
			
			String mobile = jsondata.getString("mobile");
			String code = jsondata.getString("code");
			System.out.println("用户登录信息>>手机号【" + mobile + "】验证码【" + code + "】");
			if (StringUtil.isNotEmpty(mobile) && mobile.matches(AppGlobals.CHECK_PHONE)) {
				if (StringUtil.isNotEmpty(code)) {
					CarCustomerEntity user = systemService.findUniqueByProperty(CarCustomerEntity.class, "phone",
							mobile);

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
								token = generateToken(user.getId(), user.getPhone());
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

	// 短信发送接口
	@RequestMapping(params = "getSmscode")
	public void getSmscode(HttpServletRequest request, HttpServletResponse response) {
		AppUtil.responseUTF8(response);
		JSONObject returnJsonObj = new JSONObject();

		String msg = "";
		String statusCode = "";
		JSONObject data = new JSONObject();

		String mobile = request.getParameter("mobile");

		if (!StringUtil.isNotEmpty(mobile)) {
			msg = "手机号不能为空";
			statusCode = "001";
		} else if (!mobile.matches(AppGlobals.CHECK_PHONE)) {
			msg = "手机号格式不正确";
			statusCode = "002";
		} else {
			// 生成4位数的验证码
			String code = StringUtil.numRandom(4);
			
			// 发送端短消息
//			String body = SendMessageUtil.sendMessage(mobile, new String[] { "code" , "product" }, new String[] { code , "龙游出行" },
//					SendMessageUtil.TEMPLATE_SMS_CODE , SendMessageUtil.TEMPLATE_SMS_CODE_SIGN_NAME);
			String body = "true";
			if (body.contains("true")) {

				// 判断用户是否在数据库中有记录 用接口类方便扩展
				UserInfo user = systemService.findUniqueByProperty(CarCustomerEntity.class, "phone", mobile);
				
				// 当前时间
				String curTime = AppUtil.getCurTime();
				String sql = "";
				// 存储验证码相关信息
				if (user == null) {
					sql = "insert into car_customer set id='" + UUIDGenerator.generate()
							+ "',phone = ? ,create_time = ? " + ",status = '0',code_update_time = ? ,security_code = ?";
					systemService.executeSql(sql, mobile, curTime, curTime, code);
				} else {
					sql = "update car_customer set status = '0',code_update_time = ? ,security_code = ? where phone = ? ";
					systemService.executeSql(sql, curTime, code, mobile);
				}

				data.put("codemsg", code);
				msg = "获取验证码成功";
				statusCode = AppGlobals.APP_SUCCESS;
			} else {
				msg = "允许每分钟1条，累计每小时7条。每天10条";
				statusCode = "003";
			}
		}
		returnJsonObj.put("msg", msg);
		returnJsonObj.put("code", statusCode);
		returnJsonObj.put("data", data.toString());

		responseOutWrite(response, returnJsonObj);

	}

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
			
			System.out.println("前端传递参数：" + param);
			
			JSONObject jsondata = JSONObject.fromObject(param);
			
			// 验证参数
			checkParam(jsondata);
			
			String token = jsondata.getString("token");
			// 验证token
			checkToken(token);

			Gson g = new Gson();
			TransferorderEntity t = g.fromJson(param, TransferorderEntity.class);
			
			// 和当前时间进行比较，出发时间和当前时间不能低于4个小时
			String startTime = t.getOrderStartime();
			Date curDate = AppUtil.getDate();
			Date departTime = DateUtils.str2Date(startTime, DateUtils.datetimeFormat);
			int m = AppUtil.compareDate(curDate, departTime, 'm', "abs");
			
			//暂时先判断23:00-5：00之间，以后可能会根据线路的运营时间来判断
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(departTime);
			int hour = calendar.get(Calendar.HOUR_OF_DAY);
			
			// if (m > 240) { //测试
			if (m < 720) {
				msg = "必须提前12个小时才能下订单";
				statusCode = "888";
				success = false;
			} else if(hour > 23 || hour < 5){
				msg = "出发时间不能为23点到凌晨5点之间";
				statusCode = "888";
				success = false;
			} else {
				//如果是未支付的订单，进行修改之后，判断这个id是不是存在的如果是存在的，直接修改内容
				if(StringUtil.isNotEmpty(t.getId())){
					
					appService.saveOrUpdate(t);
					
				} else {
					
					//订单状态 0：订单已完成。1：已付款待审核。2：审核通过待发车3：取消订单待退款。4：取消订单完成退款。5：拒绝退款。6：未支付
					//默认进来是未支付的，当进行微信付款之后，将状态改为已支付
					//这边是模拟数据
					t.setOrderStatus(6);
					
					//支付状态 0：已付款，1：退款中 2：已退款 3：未付款 4：已拒绝
					//默认进来是未付款，当进行微信付款之后，将状态改为已付款
					//这边是模拟数据
					t.setOrderPaystatus("3");
					
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
	
					appService.saveOrder(t, orderPrefix, commonAddrId);
					System.out.println("保存订单成功，订单状态为未支付");
					data.put("orderId", t.getId());
				}
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

			// 验证参数
			checkParam(new String[] { "serveType", "cityId" }, serveType, cityId);

			List<AppStationInfoEntity> lList = appService.getPTStation(serveType, cityId);

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

			// 验证参数
			checkParam(new String[] { "serveType", "stationId", "cityId"}, serveType, stationId, cityId);

			//线路信息
			List<AppLineStationInfoEntity> lList = new ArrayList<>();
			//用户常用站点信息
			List<AppStationInfoEntity> cList = new ArrayList<>();
			//站点信息
			List<AppStationInfoEntity> stationList = new ArrayList<>();

			appService.getLinebyStation(serveType, cityId, stationId, userId, likeStation, lList, cList, stationList);

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

	/** 获取用户订单详情页 */
	@RequestMapping(params = "getOrderDetail")
	public void getOrderDetail(HttpServletRequest request, HttpServletResponse response) {
		AppUtil.responseUTF8(response);
		JSONObject returnJsonObj = new JSONObject();

		String msg = "";
		String statusCode = "";

		String token = request.getParameter("token");
		String orderId = request.getParameter("orderId");

		AppUserOrderDetailEntity aod = null;

		try {

			// 验证参数
			checkParam(new String[] { "token", "orderId" }, token, orderId);

			// 验证token
			checkToken(token);

			String isMessage = request.getParameter("isMessage");
			
			if(isMessage != null && "1".equals(isMessage)){
				//进入消息详情页后就将所有的消息改为已读
				String sql = "update customer_message set status=1 where order_id=? ";
				systemService.executeSql(sql, orderId);
			}
			
			aod = appService.getOrderDetailById(orderId);

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

	/** 修改订单 */
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
			System.out.println("前端传递参数：" + param);

			JSONObject jsondata = JSONObject.fromObject(param);

			// 验证参数
			checkParam(jsondata);

			String token = jsondata.getString("token");
			// 验证token
			checkToken(token);

			String orderId = jsondata.getString("orderId");

			t = systemService.getEntity(TransferorderEntity.class, orderId);
			
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
		}

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
			System.out.println("前端传递参数：" + param);

			JSONObject jsondata = JSONObject.fromObject(param);
			// 验证参数
			checkParam(jsondata);

			String token = jsondata.getString("token");
			// 验证token
			checkToken(token);

			String orderId = jsondata.getString("orderId");

			// 需不需要做时间的判断 在发车4个小时之前才能取消订单 (是需要的...)
			TransferorderEntity t = systemService.getEntity(TransferorderEntity.class, orderId);
			// 关联表，获取发车时间 (可以建个视图)
			//Order_LineCarDiverEntity o = systemService.getEntity(Order_LineCarDiverEntity.class, orderId);

			Date curDate = AppUtil.getDate();
			String sTime = t.getOrderStartime();
			Date departTime = DateUtils.str2Date(sTime, DateUtils.datetimeFormat);
			int m = AppUtil.compareDate(curDate, departTime, 'm', "");

			if (m > 240) {
				msg = "发车时间超过四个小时，无法取消订单";
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
			System.out.println("前端传递参数：" + param);

			JSONObject jsondata = JSONObject.fromObject(param);

			// 验证参数
			checkParam(jsondata);

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
			System.out.println("前端传递参数：" + param);

			JSONObject jsondata = JSONObject.fromObject(param);
			
			// 验证参数
			checkParam(jsondata);
			
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
			System.out.println("前端传递参数：" + param);

			JSONObject jsondata = JSONObject.fromObject(param);

			// 验证参数
			checkParam(jsondata);

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
//			System.out.println("前端传递参数：" + param);

			JSONObject jsondata = JSONObject.fromObject(param);

			// 验证参数
			checkParam(jsondata);

			String token = jsondata.getString("token");
			// 验证token
			checkToken(token);

			String imagesBaseBM = jsondata.getString("header");
			String pName = jsondata.getString("imgName");
			String userId = jsondata.getString("userId");

			String idCard = jsondata.getString("idCard");
			String address = jsondata.getString("address");
			String userName = jsondata.getString("userName");
			
			CarCustomerEntity cc = systemService.getEntity(CarCustomerEntity.class, userId);
			String path = AppGlobals.IMAGE_BASE_FILE_PATH;
			if(StringUtil.isNotEmpty(imagesBaseBM)){
				// 获取图片存储路径
				imgUrl = AppGlobals.APP_USER_FILE_PATH + userId + "_" + System.currentTimeMillis()
						+ pName.substring(pName.lastIndexOf("."), pName.length());
				imagesBaseBM = imagesBaseBM.replaceAll(" ", "+");
				boolean b = Base64ImageUtil.generateImage(imagesBaseBM, path + imgUrl);
				
				cc.setCustomerImg(imgUrl);
			}
			cc.setAddress(address);
			cc.setCardNumber(idCard);
			cc.setUserName(userName);
			cc.setRealName(userName);
			
			systemService.save(cc);
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
		data.put("imgUrl", imgUrl);
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
			
			String sql = "select count(*) from customer_message where user_id='" + userId + "'";
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

		String param = "";

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
			
			System.out.println("前端传递参数：token:" + token + "---messageId:" + messageId);
			
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
			
			System.out.println("前端传递参数：token:" + token + "---messageId:" + account);
			
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
	
	/** 验证token是否有效 */
	public void checkToken(String token) throws ParameterException {

		CarCustomerEntity cc = systemService.findUniqueByProperty(CarCustomerEntity.class, "token", token);
		if (cc == null)
			throw new ParameterException(AppGlobals.TOKEN_ERROR_MSG, AppGlobals.TOKEN_ERROR);

		Date date = cc.getTokenUpdateTime();
		int day = AppUtil.compareDate(date, new Date(), 'd', "");
		if (day > 30)
			throw new ParameterException(AppGlobals.TOKEN_ERROR_MSG, AppGlobals.TOKEN_ERROR);
	}
	
}
