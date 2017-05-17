package com.yhy.lin.app.controller;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import com.yhy.lin.app.entity.CarCustomerEntity;
import com.yhy.lin.app.entity.UserInfo;
import com.yhy.lin.app.util.AppGlobals;
import com.yhy.lin.app.util.AppUtil;
import com.yhy.lin.app.util.MakeOrderNum;
import com.yhy.lin.entity.BusStopInfoEntity;
import com.yhy.lin.entity.LineInfoEntity;
import com.yhy.lin.entity.Line_busStopEntity;
import com.yhy.lin.entity.TransferorderEntity;

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
						if (m < 30 && user.getStatus().equals("0") && code.equals(user.getSecurityCode())) {

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
							//
							// // 修改登陆用户手机类型
							// try {
							// // 登陆手机类型
							// if (StringUtil.Null2Blank(OSType).length() > 0) {
							// // String sql = "update t_s_user set
							// // OSType='" +
							// // OSType + "' where id='" +
							// // user.getId() + "'";
							// // infService.executeSql(sql);
							// }
							// } catch (Exception e) {
							// }

							// 添加登陆日志
							// String message = "app手机用户: " + user.getUserName()
							// + "[" + user.getPhone() + "]" + "登录成功";
							String message = "app手机用户: " + user.getPhone() + "登录成功";
							systemService.addLog(message, Globals.Log_Type_LOGIN, Globals.Log_Leavel_INFO);

							msg = "登录成功!";
							data.put("token", token);
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
			statusCode = AppGlobals.APP_FAILED;
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

	// 接送机订单
	@RequestMapping(params = "createOrder")
	public void createOrder(HttpServletRequest request, HttpServletResponse response) {
		AppUtil.responseUTF8(response);
		JSONObject returnJsonObj = new JSONObject();

		String msg = "";
		String statusCode = "";
		JSONObject data = new JSONObject();

		String param;
		try {
			param = AppUtil.inputToStr(request);

			System.out.println("前端传递参数：" + param);

			Gson g = new Gson();
			TransferorderEntity t = g.fromJson(param, TransferorderEntity.class);
			String sId = t.getOrderStartingStationId();// 起始站
			String eId = t.getOrderTerminusStationId();// 终点站

			List<Line_busStopEntity> list = null;
			// 如果是接机或者接火车
			if (AppGlobals.AIRPORT_TO_DESTINATION_TYPE == (t.getOrderType()+"") || AppGlobals.TRAIN_TO_DESTINATION_TYPE == (t.getOrderType()+"")) {
				list = systemService.findHql(" from Line_busStopEntity where busStopsId=? ", eId);
				t.setOrderId(MakeOrderNum.makeOrderNum(MakeOrderNum.AIRPORT_TO_DESTINATION_ORDER));
			} else if (AppGlobals.DESTINATION_TO_AIRPORT_TYPE == (t.getOrderType()+"") || AppGlobals.DESTINATION_TO_TRAIN_TYPE == (t.getOrderType()+"")) { // 送机
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
			t.setOrderType(1);

			//order_paystatus
			//order_numberPeople
			//是不是要新增起点名称和终点名称两个字段
			
			systemService.save(t);

			statusCode = AppGlobals.APP_SUCCESS;
			msg = "添加成功";
		} catch (IOException e) {
			statusCode = AppGlobals.APP_FAILED;
			msg = "系统异常";
			e.printStackTrace();
		}

		returnJsonObj.put("msg", msg);
		returnJsonObj.put("code", statusCode);
		returnJsonObj.put("data", data.toString());

		responseOutWrite(response, returnJsonObj);
	}
	
	// 获取站点信息  
	@RequestMapping(params = "getStationList")
	public void getStationList(HttpServletRequest request, HttpServletResponse response) {
		AppUtil.responseUTF8(response);
		JSONObject returnJsonObj = new JSONObject();

		String msg = "";
		String statusCode = "";
		JSONObject data = new JSONObject();

		try {
			
			String serveType = request.getParameter("serveType");  //出行服务类型    0：接机     1：送机      2：接火车     3：送货车
			String stationId = request.getParameter("stationId");      //起点或终点id
			
			// 如果是接机或者接火车
			if (StringUtil.isNotEmpty(serveType) && StringUtil.isNotEmpty(stationId)) {
//				List<BusStopInfoEntity> list = systemService.findByProperty(BusStopInfoEntity.class, "station_type", stationType);
				
				switch(serveType){
					case AppGlobals.AIRPORT_TO_DESTINATION_TYPE:     //接机
						
						List<Map<String,Object>> lineList = systemService.findForJdbc(
								"select lf.id,lf.name,lf.price,lf.lineTimes from Line_busStop lb INNER JOIN lineinfo lf on lb.lineId = lf.id "
								+ "where busStopsId=? and siteOrder=?", stationId,"0");
						for(Map<String,Object> a : lineList){
							a.get("id");
							a.get("name");
							a.get("price");
							a.get("lineTimes");
							
							List<BusStopInfoEntity> stationList = systemService.findHql(" from BusStopInfoEntity where busStopsId=? and siteOrder='0' ", stationId);
						}
						
						
						break;
					case AppGlobals.DESTINATION_TO_AIRPORT_TYPE:	 //送机
						break;
					case AppGlobals.TRAIN_TO_DESTINATION_TYPE:		 //接火车
						break;
					case AppGlobals.DESTINATION_TO_TRAIN_TYPE:	 	 //送火车
						break;
				}
				
				
				statusCode = AppGlobals.APP_SUCCESS;
				msg = "添加成功";
			}else{
				statusCode = "001";
				msg = "参数不能为空";
			}
		} catch (Exception e) {
			statusCode = AppGlobals.APP_FAILED;
			msg = "系统异常";
			e.printStackTrace();
		}

		returnJsonObj.put("msg", msg);
		returnJsonObj.put("code", statusCode);
		returnJsonObj.put("data", data.toString());

		responseOutWrite(response, returnJsonObj);
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

		long diff = d1.getTime() - d2.getTime();

		if (flag == 'd') {
			return (int) (diff / 24 * 3600 * 1000);
		}

		if (flag == 'h') {
			return (int) (diff / 3600 * 1000);
		}

		if (flag == 'm') {
			return (int) (diff / 60 * 1000);
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
