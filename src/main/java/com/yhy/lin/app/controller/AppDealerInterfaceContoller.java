package com.yhy.lin.app.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.PasswordUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.pojo.base.TSDepart;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yhy.lin.app.entity.CarCustomerEntity;
import com.yhy.lin.app.entity.MessageCodeEntity;
import com.yhy.lin.app.exception.ParameterException;
import com.yhy.lin.app.service.AppInterfaceService;
import com.yhy.lin.app.util.AppGlobals;
import com.yhy.lin.app.util.AppUtil;
import com.yhy.lin.entity.DealerApplyEntity;
import com.yhy.lin.entity.DealerInfoEntity;
import com.yhy.lin.entity.LineInfoEntity;

import net.sf.json.JSONObject;

/**
 * Description : 渠道商接口
 * @author Timer
 * @date 2017年10月19日 下午4:08:28
 */
@Controller
@RequestMapping(value = "/dealerApp")
public class AppDealerInterfaceContoller  extends AppBaseController {

	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(AppInterfaceController.class);

	@Autowired
	private AppInterfaceService appService;

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
				CarCustomerEntity user = systemService.findUniqueByProperty(CarCustomerEntity.class, "phone", mobile);
				if(user != null) {
					if("1".equals(user.getUserType())){
						if(StringUtil.isNotEmpty(user.getPassword()) 
								&& user.getPassword().equals(PasswordUtil.encrypt(mobile, password, PasswordUtil.getStaticSalt()))){
							String sql = "";
							String curTime = AppUtil.getCurTime();
							String token = "";

							// 修改登录状态
							if (StringUtil.isNotEmpty(user.getToken())) {
								token = user.getToken();
								sql = "update car_customer set status = '1', token_update_time = ?,last_login_time = ? where phone = ? ";
								systemService.executeSql(sql, curTime,curTime, mobile);
							} else {
								// 生成token
								token = AppUtil.generateToken(user.getId(), user.getPhone());
								sql = "update car_customer set status = '1', token_update_time = ? ,last_login_time = ?,token = ? where phone = ? ";
								systemService.executeSql(sql, curTime, curTime,token, mobile);
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
					msg = "用户不存在！";
					statusCode = "011";
				}
			} else {
				msg = "手机号不能为空！";
				statusCode = "001";
			}
		} catch (ParameterException e) {
			statusCode = e.getCode();
			msg = e.getErrorMessage();
			logger.error(e.getErrorMessage());
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

	/**
	 * 渠道商用户修改密码
	 */
	@RequestMapping(params = "modifyPassword")
	public void modifyPassword(HttpServletRequest request, HttpServletResponse response) {
		AppUtil.responseUTF8(response);
		JSONObject returnJsonObj = new JSONObject();
		String msg = "";
		String statusCode = "";

		String token = "";

		JSONObject data = new JSONObject();

		try {
			String param = AppUtil.inputToStr(request); 

			System.out.println("modifyPassword    前端传递参数：" + param);

			//验证参数
			JSONObject jsondata = checkParam(param);

			String phone = jsondata.getString("phone");
			String password = jsondata.getString("password");
			String code = jsondata.getString("code");

			int pwdLength = password.trim().length();
			if(pwdLength <= 18 && pwdLength >= 6){

				MessageCodeEntity mscode = getMsgCodeInfo(phone);

				if (mscode != null && mscode.getMsgCode().equals(code)) {

					CarCustomerEntity user = systemService.findUniqueByProperty(CarCustomerEntity.class, "phone", phone);

					//修改密码要重新生成token
					token = AppUtil.generateToken(user.getId(), user.getPhone());

					String pwd = PasswordUtil.encrypt(user.getPhone(), password, PasswordUtil.getStaticSalt());
					logger.info("时间：" + AppUtil.getCurTime());
					logger.info("密码修改为：" + pwd);
					user.setPassword(pwd);
					user.setToken(token);
					user.setTokenUpdateTime(AppUtil.getDate());
					systemService.save(user);

					statusCode = "000";
					msg = "密码修改成功";
				}else{
					statusCode = "766";
					msg = "验证码无效";
				}
			}else{
				statusCode = "601";
				msg = "密码超出范围限制";
			}
		}
		catch (ParameterException e) {
			statusCode = e.getCode();
			msg = e.getErrorMessage();
			logger.error(e.getErrorMessage());
		}
		catch (Exception e) {
			statusCode = AppGlobals.SYSTEM_ERROR;
			msg = AppGlobals.SYSTEM_ERROR_MSG;
			e.printStackTrace();
		}

		data.put("token", AppUtil.Null2Blank(token));

		returnJsonObj.put("msg", msg);
		returnJsonObj.put("code", statusCode);
		returnJsonObj.put("data", data.toString());
		responseOutWrite(response, returnJsonObj);
	}


	/** 订票人数确定总价 */
	@RequestMapping(params = "getPeoplesPrice")
	public void getPeoplesPrice(HttpServletRequest request, HttpServletResponse response) {
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

			String sumPeople = jsondata.getString("sumPeople");
			String lineId = jsondata.getString("lineId");
			String phone = jsondata.getString("phone");

			List<Map<String,Object>> dealerList = systemService.findForJdbc(
					"select dealer_discount,substring(t.org_code,1,6) as org_code from dealer_info d join t_s_depart t on d.departId = t.id where d.status = 0 and d.phone = ? ", phone);
			if(dealerList.size() > 0){

				Map<String,Object> map = dealerList.get(0);
				String dCode = String.valueOf(map.get("org_code"));

				List<Map<String,Object>> lineMap = systemService.findForJdbc(
						"select substring(t.org_code,1,6) as org_code from lineinfo l join t_s_depart t on l.departId = t.id where l.id=? ", lineId);

				String lCode = String.valueOf(lineMap.get(0).get("org_code"));

				BigDecimal discount = null;
				//如果是这个渠道商选择的线路在他的子公司下，就打折
				if(dCode.equals(lCode)){
					discount = new BigDecimal(String.valueOf(map.get("dealer_discount")));
				} else {
					discount = new BigDecimal(10);
				}
				String tPrice = appService.getCarTypePrice(sumPeople, lineId, phone, discount);

				logger.info("总价为：" + tPrice);

				data.put("tPrice", tPrice);

				statusCode = AppGlobals.APP_SUCCESS;
				msg = AppGlobals.APP_SUCCESS_MSG;
			}else{
				statusCode = "501";
				msg = "此账号不是渠道商账号,请联系管理员";
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

		returnJsonObj.put("msg", msg);
		returnJsonObj.put("code", statusCode);
		returnJsonObj.put("data", data.toString());

		responseOutWrite(response, returnJsonObj);
	}

	public static boolean isEmail(String str) {  
		//String regex = "[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}" ; 
		//String regex = "^[A-Za-z]{1,40}@[A-Za-z0-9]{1,40}\\.[A-Za-z]{2,3}$";
		//String regex ="^([a-z0-9A-Z]+[-|//.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?//.)+[a-zA-Z]{2,}$";
		// String regex = "[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}" ;
		String regex = "^([\\w-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		return match( regex ,str );  
	} 

	/**  
	 * @param regex 正则表达式字符串 
	 * @param str   要匹配的字符串 
	 * @return 如果str 符合 regex的正则表达式格式,返回true, 否则返回 false; 
	 */  
	private static boolean match( String regex ,String str ){  
		Pattern pattern = Pattern.compile(regex);  
		Matcher  matcher = pattern.matcher( str );  
		return matcher.matches();  
	}

	/** 申请成为渠道商 */
	@RequestMapping(params = "applyDealer")
	public void applyDealer(HttpServletRequest request, HttpServletResponse response) {
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

			String companyName = jsondata.getString("companyName");
			String address = jsondata.getString("address");
			String phone = jsondata.getString("phone");
			String applyPeople = jsondata.getString("applyPeople");
			String code = jsondata.getString("code");
			String email = jsondata.getString("email");

			//验证邮箱
			if(StringUtil.isNotEmpty(email)){
				boolean flag = isEmail(email);
				if(flag == false){
					 throw new ParameterException("邮箱格式有误，请输入正确的邮箱格式！","520");
				}
				List<DealerApplyEntity> dealerlist  = this.systemService.findByProperty(DealerApplyEntity.class, "email", email);
				if(dealerlist.size()>0){
					throw new ParameterException("该邮箱已提交申请，请重新输入！","530");
				}
				List<DealerInfoEntity> dealerinfolist = this.systemService.findHql("from DealerInfoEntity where email=? and status=?", email,"0");
				if(dealerinfolist.size()>0){
					throw new ParameterException("该邮箱已绑定渠道商用户，请重新输入！","540");
				}
			}
			

			String codeId = checkMsgCode(phone, code);
			if(StringUtil.isNotEmpty(codeId)){
				DealerApplyEntity da = new DealerApplyEntity();
				da.setCreateTime(AppUtil.getDate());
				da.setAddress(address);
				da.setApplyPeople(applyPeople);
				da.setCompanyName(companyName);
				da.setPhone(phone);
				da.setEmail(email);

				//默认给项目管理员
				List<Map<String,Object>> mapList = systemService.findForJdbc(
						"select id from t_s_base_user where username=?", AppGlobals.XM_ADMIN_NAME);

				da.setResponsibleUserId(String.valueOf(mapList.get(0).get("id")));
				appService.save(da);

				systemService.executeSql(" update message_code set is_used = '1' where id = ? ", codeId);

				statusCode = AppGlobals.APP_SUCCESS;
				msg = "申请成功";
			}else{
				statusCode = AppGlobals.PARAMETER_MSG_CODE_INVALID;
				msg = AppGlobals.PARAMETER_MSG_CODE_INVALID_MSG;
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

		returnJsonObj.put("msg", msg);
		returnJsonObj.put("code", statusCode);
		returnJsonObj.put("data", data.toString());

		responseOutWrite(response, returnJsonObj);
	}

}
