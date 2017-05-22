package com.yhy.lin.app.controller;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.web.system.service.SystemService;
import org.jeecgframework.web.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import com.yhy.lin.app.entity.AppCheckTicket;
import com.yhy.lin.app.entity.AppLineStationInfoEntity;
import com.yhy.lin.app.entity.AppStationInfoEntity;
import com.yhy.lin.app.entity.AppUserOrderDetailEntity;
import com.yhy.lin.app.entity.AppUserOrderEntity;
import com.yhy.lin.app.entity.CarCustomerEntity;
import com.yhy.lin.app.entity.UserInfo;
import com.yhy.lin.app.util.AppGlobals;
import com.yhy.lin.app.util.AppUtil;
import com.yhy.lin.app.util.MakeOrderNum;
import com.yhy.lin.entity.Line_busStopEntity;
import com.yhy.lin.entity.OpenCityEntity;
import com.yhy.lin.entity.Order_LineCarDiverEntity;
import com.yhy.lin.entity.TransferorderEntity;

import freemarker.template.utility.DateUtil;

/**
 * Description : 接口处理类
 * 
 * @author Time
 * @date 2017年5月4日 上午9:34:30
 */
@Controller
@RequestMapping(value = "/app")
public class AppInterfaceController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AppInterfaceController.class);

	@Autowired
	private UserService userService;

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

		try {
			// String mobile = request.getParameter("mobile");// 用户名
			// String code = request.getParameter("code");// 验证码

			String param = AppUtil.inputToStr(request);
			System.out.println("前端传递参数：" + param);

			// param = param.replaceAll(":null", ":\"\"");
			JSONObject jsondata = JSONObject.fromObject(param);
			String mobile = jsondata.getString("mobile");
			String code = jsondata.getString("code");

			System.out.println("用户登录信息>>手机号【" + mobile + "】验证码【" + code + "】");
			if (StringUtil.isNotEmpty(mobile) && mobile.matches(AppGlobals.CHECK_PHONE)) {
				if (StringUtil.isNotEmpty(code)) {
					CarCustomerEntity user = systemService.findUniqueByProperty(CarCustomerEntity.class, "phone",
							mobile);

					if (user != null) {
						Date date = user.getCodeUpdateTime();
						int m = compareDate(DateUtils.getDate(), date, 'm');
						// 30分钟的有效期验证
						// if (m < 30 && user.getStatus().equals("0") &&
						// code.equals(user.getSecurityCode())) {
						if (m > 30 && user.getStatus().equals("1") && code.equals(user.getSecurityCode())) { // 测试
							String sql = "";
							String curTime = getCurTime();
							String token = "";

							// 把token的修改时间拿出来与当前时间进行比较 登录的时候不用比较。。。
							// int d = compareDate(DateUtils.getDate(),
							// user.getTokenUpdateTime(), 'd');

							// 如果小于一个月就进行只修改token的更新时间
							if (StringUtil.isNotEmpty(user.getToken())) {
								token = user.getToken();
								sql = "update car_customer set status = '1', token_update_time = '" + curTime
										+ "' where phone = '" + mobile + "' ";
							} else {
								// 生成token
								token = generateToken(user.getCustomerId(), user.getPhone());
								sql = "update car_customer set status = '1', token_update_time = '" + curTime
										+ "',token = '" + token + "' where phone = '" + mobile + "' ";
							}
							systemService.executeSql(sql);

							// // 加密后密码
							// String pwd =
							// PasswordUtil.encrypt(user.getUserName(), userPwd,
							// PasswordUtil.getStaticSalt());
							// // 此时属于平台用户登录
							// if (null != user &&
							// user.getPassword().equals(pwd)) {
							// sign = "userName";
							// } else {
							// // 手机用户登录记录
							// user =
							// userService.getUserByMobilePhone(userName);
							// if (null != user &&
							// user.getPassword().equals(pwd)) {
							// sign = "phone";
							// }
							// }
							// if (user != null &&
							// user.getPassword().equals(pwd) && sign != null) {

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
		} catch (Exception e) {
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

			TaobaoClient client = new DefaultTaobaoClient(AppGlobals.SERVCR_URL, AppGlobals.APP_KEY,
					AppGlobals.APP_SECRET);
			AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
			req.setExtend("123456");
			req.setSmsType("normal");
			req.setSmsFreeSignName("刘航");
			req.setSmsParamString("{code:'" + code + "'}");
			req.setRecNum(mobile);
			req.setSmsTemplateCode("SMS_63766002");
			AlibabaAliqinFcSmsNumSendResponse rsp = null;
			try {
				rsp = client.execute(req);
			} catch (ApiException e) {
				e.printStackTrace();
			}

			String body = rsp.getBody();
			if (body.contains("true")) {

				// 判断用户是否在数据库中有记录 用接口类方便扩展
				UserInfo user = systemService.findUniqueByProperty(CarCustomerEntity.class, "phone", mobile);

				// 当前时间
				String curTime = getCurTime();
				String sql = "";
				if (user == null) {
					sql = "insert into car_customer set customer_id='" + UUIDGenerator.generate() + "',phone = '"
							+ mobile + "',create_time = '" + curTime + "',status = '0',code_update_time = '" + curTime
							+ "',security_code = '" + code + "'";
				} else {
					sql = "update car_customer set status = '0',code_update_time = '" + curTime + "',security_code = '"
							+ code + "' where phone = '" + mobile + "' ";
				}

				systemService.executeSql(sql);
				data.put("codemsg", code);
				msg = "获取验证码成功";
				statusCode = AppGlobals.APP_SUCCESS;
			} else {
				msg = "允许每分钟1条，累计每小时7条。每天50条";
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

		String param;
		try {
			param = AppUtil.inputToStr(request);

			System.out.println("前端传递参数：" + param);

			Gson g = new Gson();
			TransferorderEntity t = g.fromJson(param, TransferorderEntity.class);

			String sId = t.getOrderStartingStationId();// 起始站
			String eId = t.getOrderTerminusStationId();// 终点站

			String startTime = t.getOrderStartime();

			// 和当前时间进行比较，出发时间和当前时间不能低于4个小时
			Date curDate = getDate();
			Date departTime = DateUtils.str2Date(startTime, DateUtils.datetimeFormat);
			int m = compareDate(curDate, departTime, 'm');

			if (m > 240) {
				msg = "必须提前4个小时才能下订单，无法取消订单";
				success = false;
			} else {
				List<Line_busStopEntity> list = null;
				// 如果是接机或者接火车
				if (AppGlobals.AIRPORT_TO_DESTINATION_TYPE.equals(t.getOrderType() + "")
						|| AppGlobals.TRAIN_TO_DESTINATION_TYPE.equals(t.getOrderType() + "")) {

					list = systemService.findHql(" from Line_busStopEntity where busStopsId=? ", eId);
					t.setOrderId(MakeOrderNum.makeOrderNum(MakeOrderNum.AIRPORT_TO_DESTINATION_ORDER));

				} else if (AppGlobals.DESTINATION_TO_AIRPORT_TYPE.equals(t.getOrderType() + "")
						|| AppGlobals.DESTINATION_TO_TRAIN_TYPE.equals(t.getOrderType() + "")) { // 送机||送火车

					list = systemService.findHql(" from Line_busStopEntity where busStopsId=? ", sId);
					t.setOrderId(MakeOrderNum.makeOrderNum(MakeOrderNum.DESTINATION_TO_AIRPORT_ORDER));

				}

				String lId = list.get(0).getLineId();
				List<String> lList = systemService.findListbySql("select name from lineinfo where id='" + lId + "'");
				if (lList.size() > 0) {
					t.setLineName(lList.get(0));
				}

				t.setLineId(lId);
				t.setApplicationTime(getDate());
				// t.setOrderType(1);

				// 订单的城市要不要加一个

				systemService.save(t);

				statusCode = AppGlobals.APP_SUCCESS;
				msg = AppGlobals.APP_SUCCESS_MSG;
				success = true;
			}
		} catch (IOException e) {
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
			// token
			String token = request.getParameter("token");

			if (StringUtil.isNotEmpty(serveType) && StringUtil.isNotEmpty(cityId)) {

				List<AppStationInfoEntity> lList = new ArrayList<AppStationInfoEntity>();

				// 查找指定类型的线路
				List<Map<String, Object>> lineList = systemService.findForJdbc(
						" select bi.id,bi.name,lb.lineId,bi.stopLocation,bi.x,bi.y "
								+ " from Line_busStop lb INNER JOIN lineinfo lf on lb.lineId = lf.id INNER JOIN busstopinfo bi on bi.id=lb.busStopsId "
								+ " where lf.cityId=? and lf.type=? and bi.station_type=? and lf.deleteFlag=0 and bi.deleteFlag=0 group by lb.busStopsId ",
						cityId, serveType, getStationType(serveType));

				for (Map<String, Object> a : lineList) {
					AppStationInfoEntity asi = new AppStationInfoEntity();
					asi.setId(a.get("id") + "");
					asi.setName(a.get("name") + "");
					asi.setLineId(a.get("lineId") + "");
					asi.setStopLocation(AppUtil.Null2Blank(a.get("stopLocation") + ""));
					asi.setX(AppUtil.Null2Blank(a.get("x") + ""));
					asi.setY(AppUtil.Null2Blank(a.get("y") + ""));

					lList.add(asi);
				}

				data.put("PTStation", lList);

				statusCode = AppGlobals.APP_SUCCESS;
				msg = AppGlobals.APP_SUCCESS_MSG;
			} else {
				statusCode = "001";
				msg = "参数不能为空";
			}
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
			String token = request.getParameter("token");

			//
			if (StringUtil.isNotEmpty(serveType) && StringUtil.isNotEmpty(stationId)) {

				List<AppLineStationInfoEntity> lList = new ArrayList<AppLineStationInfoEntity>();

				// 根据起点id城市查找线路信息
				List<Map<String, Object>> lineList = systemService
						.findForJdbc(
								" select lf.id,lf.name,lf.price,lf.lineTimes "
										+ " from Line_busStop lb INNER JOIN lineinfo lf on lb.lineId = lf.id "
										+ " where busStopsId=? and lf.cityId=? and lf.deleteFlag=0  ",
								stationId, cityId);

				StringBuffer sbf = new StringBuffer();
				for (Map<String, Object> a : lineList) {

					AppLineStationInfoEntity asi = new AppLineStationInfoEntity();

					asi.setId(a.get("id") + "");
					asi.setName(a.get("name") + "");
					asi.setPrice(AppUtil.Null2Blank(a.get("price") + ""));
					asi.setLineTimes(AppUtil.Null2Blank(a.get("lineTimes") + ""));
					// asi.setStationType(a.get("station_type") + "");
					lList.add(asi);

					sbf.append("'");
					sbf.append(asi.getId());
					sbf.append("',");
					// // 不知道出了什么问题 lineId没改动 下面手动的去改变一下
					// for (AppStationInfoEntity b : stationList) {
					// b.setLineId(asi.getId());
					// }
				}
				String t = getStationType(serveType);
				if (sbf.length() > 0)
					sbf.deleteCharAt(sbf.length() - 1);

				// 查询指定id线路中的所有站点
				List<AppStationInfoEntity> stationList = systemService.findHql(
						"from AppStationInfoEntity where lineId in (" + sbf.toString() + ") and station_type=? ", t);

				data.put("lineInfo", lList);
				data.put("stationInfo", stationList);

				statusCode = AppGlobals.APP_SUCCESS;
				msg = AppGlobals.APP_SUCCESS_MSG;
			} else {
				statusCode = "001";
				msg = "参数不能为空";
			}
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

		String token = request.getParameter("token");

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
		List<Map<String, Object>> list = null;
		List<AppUserOrderEntity> list1 = new ArrayList<>();
		try {
			String token = request.getParameter("token");
			String userId = request.getParameter("userId");

			// 订单支付状态 0：已付款，1：退款中 2：已退款 3: 未付款
			String orderStatus = request.getParameter("orderStatus");

			String pageNo = request.getParameter("pageNo");
			if (!StringUtil.isNotEmpty(pageNo)) {
				pageNo = "1";
			}

			String maxPageItem = request.getParameter("maxPageItem");
			if (!StringUtil.isNotEmpty(maxPageItem)) {
				maxPageItem = "15";
			}

			StringBuffer sql = new StringBuffer();
			sql.append(
					"select a.id,a.order_status,order_id,a.order_starting_station_name,a.order_terminus_station_name,a.order_totalPrice,a.order_numbers,b.startTime "
							+ " from transferorder a left join order_linecardiver b on a.id = b.id where user_id=? ");

			if (StringUtil.isNotEmpty(orderStatus)) {
				sql.append(" and order_paystatus = '" + orderStatus + "'");
			}

			list = systemService.findForJdbcParam(sql.toString(), Integer.parseInt(pageNo),
					Integer.parseInt(maxPageItem), userId);

			for (Map<String, Object> map : list) {
				// Date date = (Date) map.get("startTime");

				AppUserOrderEntity auo = new AppUserOrderEntity();
				auo.setId(map.get("id") + "");
				auo.setOrderId(map.get("order_id") + "");
				auo.setOrderNumbers(map.get("order_numbers") + "");
				auo.setOrderStartime(map.get("startTime") + "");
				auo.setOrderStartingStationName(map.get("order_starting_station_name") + "");
				// 要做一下为空的判断，这个字段不会为空
				auo.setOrderStatus(map.get("order_status") + "");
				auo.setOrderTerminusStationName(map.get("order_terminus_station_name") + "");
				auo.setOrderTotalPrice(map.get("order_totalPrice") + "");
				list1.add(auo);
			}

			statusCode = AppGlobals.APP_SUCCESS;
			msg = AppGlobals.APP_SUCCESS_MSG;
		} catch (Exception e) {
			statusCode = AppGlobals.SYSTEM_ERROR;
			msg = AppGlobals.SYSTEM_ERROR_MSG;
			e.printStackTrace();
		}

		returnJsonObj.put("msg", msg);
		returnJsonObj.put("code", statusCode);
		if (list1 != null && list1.size() > 0) {
			returnJsonObj.put("data", list1);
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

		List<Map<String, Object>> list = null;
		AppUserOrderDetailEntity aod = new AppUserOrderDetailEntity();
		try {

			list = systemService
					.findForJdbc("select a.id,a.order_type,a.order_status,a.order_id,a.order_starting_station_name, "
							+ "	a.order_terminus_station_name,a.order_totalPrice,a.order_numbers,a.order_startime,a.order_contactsname,a.order_contactsmobile, "
							+ " a.applicationTime,c.licence_plate,c.car_type,d.name as driver_name,d.phoneNumber as driver_phone,l.lineTimes "
							+ " from transferorder a left join order_linecardiver b on a.id = b .id left join car_info c on b.licencePlateId =c.id "
							+ " left join driversinfo d on b.driverId = d.id left join lineInfo l on a.line_id = l.id where a.id=? ", orderId);

			for (Map<String, Object> map : list) {
				//会出现空指针么...
				Date date = (Date) map.get("order_startime");
				Date date1 = (Date) map.get("applicationTime");

				aod.setId(map.get("id") + "");
				aod.setOrderType(map.get("order_type") + "");
				aod.setOrderStatus(map.get("order_status") + "");
				aod.setOrderId(map.get("order_id") + "");
				aod.setOrderStartingStationName(map.get("order_starting_station_name") + "");
				aod.setOrderTerminusStationName(map.get("order_terminus_station_name") + "");
				aod.setOrderTotalPrice(map.get("order_totalPrice") + "");
				aod.setOrderNumbers(map.get("order_numbers") + "");

				aod.setOrderStartime(DateUtils.date2Str(date, DateUtils.datetimeFormat));
				aod.setApplicationTime(DateUtils.date2Str(date1, DateUtils.datetimeFormat));

				aod.setOrderContactsname(map.get("order_contactsname") + "");
				aod.setOrderContactsmobile(map.get("order_contactsmobile") + "");
				aod.setLicencePlate(AppUtil.Null2Blank(map.get("licence_plate")+""));
				aod.setCarType(AppUtil.Null2Blank(map.get("car_type") + ""));

				aod.setDriver(AppUtil.Null2Blank(map.get("driver_name") + ""));
				aod.setDriverPhone(AppUtil.Null2Blank(map.get("driver_phone") + ""));
				
				//发车时间
				aod.setStationStartTime(DateUtils.date2Str(date, DateUtils.short_time_sdf));
				//线路时长
				String lineTime = map.get("lineTimes")+"";
				
				//在发车时间的基础上加上线路所用时长
				if(StringUtil.isNotEmpty(lineTime)){
					double lt = Double.parseDouble(lineTime);
					
					long a = (long) (date.getTime() + (lt * 60 * 60 * 1000));
					
					aod.setStationEndTime(DateUtils.date2Str(new Date(a), DateUtils.short_time_sdf));
				}else{
					aod.setStationEndTime("");
				}
			}
			
			statusCode = AppGlobals.APP_SUCCESS;
			msg = AppGlobals.APP_SUCCESS_MSG;
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
			String token = jsondata.getString("token");
			String orderId = jsondata.getString("orderId");

			t = systemService.getEntity(TransferorderEntity.class, orderId);

			statusCode = AppGlobals.APP_SUCCESS;
			msg = AppGlobals.APP_SUCCESS_MSG;
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

	/** 如果订单id不为空，说明是未支付订单继续支付/取消订单/确认完成 的订单,只需要改变一下状态就可以了 */
	// 三个状态单独拿出写三个接口
	// 未支付订单继续支付 （废弃）
	// @RequestMapping(params = "continuePayment")
	// public void changeOrderStatus(HttpServletRequest request,
	// HttpServletResponse response) {
	// AppUtil.responseUTF8(response);
	// JSONObject returnJsonObj = new JSONObject();
	//
	// String msg = "";
	// String statusCode = "";
	//
	// String token = request.getParameter("token");
	// String orderId = request.getParameter("orderId");
	//
	// List<Map<String, Object>> list = null;
	//
	// try {
	// list = systemService.findForJdbc(
	// "select
	// a.id,a.order_status,a.order_id,a.order_unitprice,a.order_starting_station_name,a.order_starting_station_id,
	// "
	// + "
	// a.order_terminus_station_name,order_terminus_station_id,a.order_totalPrice,a.order_numbers,a.order_startime,
	// "
	// + "
	// a.order_contactsname,a.order_contactsmobile,a.applicationTime,c.licence_plate,c.car_type,d.name
	// as driver_name,d.phoneNumber as driver_phone "
	// + " from transferorder a left join order_linecardiver b on a.id = b .id
	// left join car_info c on b.licencePlateId =c.id "
	// + " left join driversinfo d on b.driverId = d.id where a.id=? ",
	// orderId);
	//
	// for (Map<String, Object> map : list) {
	// Date date = (Date) map.get("order_startime");
	// map.put("order_startime", DateUtils.date2Str(date,
	// DateUtils.datetimeFormat));
	//
	// Date date1 = (Date) map.get("applicationTime");
	// map.put("applicationTime", DateUtils.date2Str(date1,
	// DateUtils.datetimeFormat));
	// }
	//
	// statusCode = AppGlobals.APP_SUCCESS;
	// msg = AppGlobals.APP_SUCCESS_MSG;
	// } catch (Exception e) {
	// statusCode = AppGlobals.SYSTEM_ERROR;
	// msg = AppGlobals.SYSTEM_ERROR_MSG;
	// e.printStackTrace();
	// }
	//
	// returnJsonObj.put("msg", msg);
	// returnJsonObj.put("code", statusCode);
	// if (list != null && list.size() > 0) {
	// returnJsonObj.put("data", list);
	// } else {
	// returnJsonObj.put("data", "");
	// }
	//
	// responseOutWrite(response, returnJsonObj);
	// }

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
			String token = jsondata.getString("token");
			String orderId = jsondata.getString("orderId");

			// 需不需要做时间的判断 在发车2个小时之前才能取消订单 (是需要的...)
			TransferorderEntity t = systemService.getEntity(TransferorderEntity.class, orderId);
			// 关联表，获取发车时间 (可以建个视图)
			Order_LineCarDiverEntity o = systemService.getEntity(Order_LineCarDiverEntity.class, orderId);

			Date curDate = getDate();
			String sTime = o.getStartTime();
			Date departTime = DateUtils.str2Date(sTime, DateUtils.datetimeFormat);
			int m = compareDate(curDate, departTime, 'm');

			if (m > 120) {
				msg = "发车时间超过两个小时，无法取消订单";
				success = false;
			} else {
				// 0：订单已完成。1：已付款待审核。2：审核通过待发车3：取消订单待退款。4：取消订单完成退款。
				t.setOrderStatus(3);
				// 支付状态 0：已付款，1：退款中 2：已退款 3:：未付款
				t.setOrderPaystatus("1");
				systemService.updateEntitie(t);

				msg = AppGlobals.APP_SUCCESS_MSG;
				success = true;
			}
			statusCode = AppGlobals.APP_SUCCESS;

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
			String token = jsondata.getString("token");
			String orderId = jsondata.getString("orderId");

			TransferorderEntity t = systemService.getEntity(TransferorderEntity.class, orderId);
			// 0：订单已完成。1：已付款待审核。2：审核通过待发车3：取消订单待退款。4：取消订单完成退款。
			t.setOrderStatus(0);
			systemService.updateEntitie(t);

			statusCode = AppGlobals.APP_SUCCESS;
			msg = AppGlobals.APP_SUCCESS_MSG;
			success = true;
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

	/** 验票界面  */
	@RequestMapping(params = "checkTicket")
	public void checkTicket(HttpServletRequest request, HttpServletResponse response) {
		AppUtil.responseUTF8(response);
		JSONObject returnJsonObj = new JSONObject();

		String msg = "";
		String statusCode = "";
		List<Map<String, Object>> list = null;
		List<AppCheckTicket> list1 = new ArrayList<>();
		try {
			String token = request.getParameter("token");
			String userId = request.getParameter("userId");
			
			String pageNo = request.getParameter("pageNo");
			if (!StringUtil.isNotEmpty(pageNo)) {
				pageNo = "1";
			}

			String maxPageItem = request.getParameter("maxPageItem");
			if (!StringUtil.isNotEmpty(maxPageItem)) {
				maxPageItem = "15";
			}
			
			StringBuffer sql = new StringBuffer();
			sql.append(" select a.id,a.order_status,a.order_starting_station_name,a.order_terminus_station_name,a.order_totalPrice,a.order_numbers,b.startTime "  
				+ " ,c.licence_plate,c.car_type,d.name as driver_name,d.phoneNumber "
				+ " from transferorder a left join order_linecardiver b on a.id = b .id left join car_info c on b.licencePlateId =c.id "
				+ " left join driversinfo d on b.driverId =d.id where a.user_id=? and a.order_status='2' and order_paystatus='0' ");

			list = systemService.findForJdbcParam(sql.toString(), Integer.parseInt(pageNo),
					Integer.parseInt(maxPageItem), userId);

			for (Map<String, Object> map : list) {

				AppCheckTicket auo = new AppCheckTicket();
				auo.setId(map.get("id") + "");
				auo.setOrderNumbers(map.get("order_numbers") + "");
				auo.setOrderStartime(map.get("startTime")+"");
				auo.setOrderStartingStationName(map.get("order_starting_station_name") + "");
				auo.setOrderTerminusStationName(map.get("order_terminus_station_name") + "");
				// 要做一下为空的判断，这个字段不会为空
				auo.setOrderStatus(map.get("order_status") + "");
				auo.setOrderTotalPrice(map.get("order_totalPrice") + "");
				auo.setLicencePlate(map.get("licence_plate") + "");
				auo.setCarType(map.get("car_type") + "");
				auo.setDriver(map.get("driver_name") + "");
				auo.setDriverPhone(map.get("phoneNumber") + "");
				
				list1.add(auo);
			}

			statusCode = AppGlobals.APP_SUCCESS;
			msg = AppGlobals.APP_SUCCESS_MSG;
		} catch (Exception e) {
			statusCode = AppGlobals.SYSTEM_ERROR;
			msg = AppGlobals.SYSTEM_ERROR_MSG;
			e.printStackTrace();
		}

		returnJsonObj.put("msg", msg);
		returnJsonObj.put("code", statusCode);
		if (list1 != null && list1.size() > 0) {
			returnJsonObj.put("data", list1);
		} else {
			returnJsonObj.put("data", "");
		}

		responseOutWrite(response, returnJsonObj);
	}

	
	
	
	/**
	 * 参数检测是否为空 (封装一个统一的参数检测为空的方法，后面在做吧)
	 */
	// public String checkParameter(JSONObject returnJsonObj, Object
	// ...objects){
	// for(Object i : objects){
	//
	// }
	// returnJsonObj.put("msg", "缺少参数:" + );
	// returnJsonObj.put("code", statusCode);
	// if (list != null && list.size() > 0) {
	// returnJsonObj.put("data", list);
	// } else {
	// returnJsonObj.put("data", "");
	// }
	//
	// responseOutWrite(response, returnJsonObj);
	// }

	/**
	 * 站点类型转换 线路类型 转 站点类型
	 */
	public String getStationType(String lineType) {
		//
		switch (lineType) {
		case "2":
		case "3":
			return "2";
		case "4":
		case "5":
			return "1";
		default:
			return lineType;
		}
	}

	/** 验证token是否有效 */
	public boolean checkToken(String token) {

		CarCustomerEntity cc = systemService.findUniqueByProperty(CarCustomerEntity.class, "token", token);
		if (cc == null)
			return false;

		Date date = cc.getTokenUpdateTime();
		int day = compareDate(date, new Date(), 'd');
		if (day <= 30)
			return true;

		return false;
	}

	/**
	 * 计算两个时间之间的差值，根据标志的不同而不同
	 * 
	 * @param flag
	 *            计算标志，表示按照年/月/日/时/分/秒等计算
	 * @param calSrc
	 *            减数
	 * @param calDes
	 *            被减数
	 * @return 两个日期之间的差值
	 */
	public static int compareDate(Date d1, Date d2, char flag) {

		long diff = Math.abs(d1.getTime() - d2.getTime());

		if (flag == 'd') {
			return (int) (diff / (24 * 3600 * 1000));
		}

		if (flag == 'h') {
			return (int) (diff / (3600 * 1000));
		}

		if (flag == 'm') {
			return (int) (diff / (60 * 1000));
		}

		if (flag == 's') {
			return (int) (diff / 1000);
		}

		return 0;
	}

	// 获取当前时间(字符串)
	public String getCurTime() {
		return DateUtils.date2Str(DateUtils.datetimeFormat);
	}

	// 获取当前时间(Date)
	public Date getDate() {
		return DateUtils.getDate();
	}

	// 生成token
	private String generateToken(String customerId, String phone) {
		return md5(customerId + phone);
	}

	// jdk自带的md5加密
	private String md5(String str) {
		MessageDigest md;
		StringBuffer sb = new StringBuffer();
		try {
			// 生成一个MD5加密计算摘要
			md = MessageDigest.getInstance("MD5");
			// 计算md5函数
			md.update(str.getBytes());
			byte[] data = md.digest();
			int index;
			for (byte b : data) {
				index = b;
				if (index < 0)
					index += 256;
				if (index < 16)
					sb.append("0");
				sb.append(Integer.toHexString(index));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
