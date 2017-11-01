package com.yhy.lin.app.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.PasswordUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yhy.lin.app.entity.CarCustomerEntity;
import com.yhy.lin.app.exception.ParameterException;
import com.yhy.lin.app.service.AppCharteredInterfaceService;
import com.yhy.lin.app.util.AppGlobals;
import com.yhy.lin.app.util.AppUtil;

import net.sf.json.JSONObject;

/**
* Description : app包车接口
* @author Timer
* @date 2017年10月19日 下午4:08:28
*/
@Controller
@RequestMapping(value = "/charteredApp")
public class AppCharteredInterfaceContoller  extends AppBaseController {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AppInterfaceController.class);

	@Autowired
	private AppCharteredInterfaceService appCharteredService;
	
	@Autowired
	private SystemService systemService;
	
	// 登录接口
	@RequestMapping(params = "appDealerLogin")
	public void appDealerLogin(HttpServletRequest request, HttpServletResponse response) {
		AppUtil.responseUTF8(response);
		JSONObject returnJsonObj = new JSONObject();

		String msg = "";
		String statusCode = "";
		JSONObject data = new JSONObject();

		try {
			String param = AppUtil.inputToStr(request);
			
			System.out.println("appLogin    前端传递参数：" + param);
			
			//验证参数
			JSONObject jsondata = checkParam(param);
			
			String mobile = jsondata.getString("mobile");
			String password = jsondata.getString("password");
			System.out.println("用户登录信息>>手机号【" + mobile + "】密码【" + password + "】");
			if (StringUtil.isNotEmpty(mobile) && mobile.matches(AppGlobals.CHECK_PHONE)) {
				if (StringUtil.isNotEmpty(password)) {
					CarCustomerEntity user = systemService.findUniqueByProperty(CarCustomerEntity.class, "phone", mobile);
					if(user.getUserType().equals("1")){
						if (user != null && user.getPassword().equals(PasswordUtil.encrypt(mobile, password, PasswordUtil.getStaticSalt()))) {
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
	//							// 新注册用户 标识
	//							if(user.getTokenUpdateTime() == null){
	//								isNew = PasswordUtil.encrypt(mobile, token, PasswordUtil.getStaticSalt());
	//							}
							}
	
							String message = "app渠道商用户: " + user.getPhone() + "登录成功";
							systemService.addLog(message, Globals.Log_Type_LOGIN, Globals.Log_Leavel_INFO);
	
							msg = "登录成功!";
							data.put("token", token);
							data.put("userId", user.getUserId());
							// 如果value为空的话，这个键值对不会再json字符串中显示，所以将null转换成""
							data.put("userName", AppUtil.Null2Blank(user.getUserName()));
							data.put("customerImg", AppUtil.Null2Blank(user.getCustomerImg()));
							data.put("phone", user.getPhone());
							data.put("userType", user.getUserType());
							statusCode = AppGlobals.APP_SUCCESS;
						} else {
							msg = "用户名或密码不正确！";
							statusCode = "005";
						}
					} else {
						msg = "只限渠道商账号登录！";
						statusCode = "006";
					}
				} else {
					msg = "密码不能为空！";
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
	
	/**
	 * 渠道商用户修改密码
	 */
	@RequestMapping(params = "modifyPassword")
	public void modifyPassword(HttpServletRequest request, HttpServletResponse response) {
		AppUtil.responseUTF8(response);
		JSONObject returnJsonObj = new JSONObject();
		String msg = "";
		String statusCode = "";
		boolean success = false;
		
		JSONObject data = new JSONObject();
		
		try {
			String param = AppUtil.inputToStr(request); 
			
			System.out.println("modifyPassword    前端传递参数：" + param);
			
			//验证参数
			JSONObject jsondata = checkParam(param);
			
			String token = jsondata.getString("token");
			String password = jsondata.getString("password");
			String code = jsondata.getString("code");
			
			CarCustomerEntity user = systemService.findUniqueByProperty(CarCustomerEntity.class, "token", token);
			
			if (user.getSecurityCode().equals(code)) {
				String pwd = PasswordUtil.encrypt(user.getPhone(), password, PasswordUtil.getStaticSalt());
				logger.info("时间：" + AppUtil.getCurTime());
				logger.info("密码修改为：" + pwd);
				user.setPassword(pwd);
				
				statusCode = "000";
				msg = "密码修改成功";
				data.put("success", true);
			}else{
				statusCode = "766";
				msg = "无效的修改";
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
	
	// 获取包车线路信息
	@RequestMapping(params = "getCharteredInfo")
	public void getCharteredInfo(HttpServletRequest request, HttpServletResponse response) {
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

//				List<AppStationInfoEntity> lList = appCharteredService.getPTStation(serveType, cityId);

//				data.put("PTStation", lList);

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
	
	
}
