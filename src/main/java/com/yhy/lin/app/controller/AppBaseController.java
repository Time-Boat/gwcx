package com.yhy.lin.app.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;

import com.yhy.lin.app.entity.CarCustomerEntity;
import com.yhy.lin.app.entity.MessageCodeEntity;
import com.yhy.lin.app.exception.ParameterException;
import com.yhy.lin.app.util.AppGlobals;
import com.yhy.lin.app.util.AppUtil;

import net.sf.json.JSONObject;

/**
 * Description :
 * 
 * @author Administrator
 * @date 2017年5月25日 上午11:23:15
 */
public class AppBaseController extends BaseController {

	@Autowired
	private SystemService systemService;

	// app
	public void responseOutWrite(HttpServletResponse response, JSONObject jObject) {
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		// 这个，是设置页面缓存的
		// 防止JSP或者Servlet中的输出被浏览器保存在缓冲区中。
		// 在代理服务器(Nginx)端防止缓冲
		response.setDateHeader("Expires", 0);
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {

			PrintWriter pw = response.getWriter();
			pw.write(jObject.toString());
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** 获取验证码信息 */
	public MessageCodeEntity getMsgCodeInfo(String phone) {
		//获取日期最近的一条验证码信息
		List<MessageCodeEntity> mscodeList = systemService.findHql(
				" from MessageCodeEntity where phone=? and isUsed='0' and create_time = (select max(createTime) from MessageCodeEntity)", phone);
		
		return mscodeList.get(0);
	}
	
	/** 验证短信验证码是否有效 */
	public String checkMsgCode(String phone, String code) {

		//验证码id
		String id = "";
		
		List<Map<String, Object>> mc = systemService.findForJdbc(
				" select max(create_time) as time,id from message_code where phone = ? and msg_code = ? and is_used = '0' ",
				phone, code);

		System.out.println(mc.get(0).get("time"));
		
		if (mc.size() > 0 && StringUtil.isNotEmpty(mc.get(0).get("time"))) {
			Date date = AppUtil.str2Date(mc.get(0).get("time") + "");
			int m = AppUtil.compareDate(DateUtils.getDate(), date, 'm', "");
			// 30分钟的有效期验证
			if (m <= 30) {
				id = mc.get(0).get("time") + "";
			}
		}
		return id;
	}
	
	/** 验证参数是否为空 (json) */
	public static JSONObject checkParam(String strJson) throws ParameterException {
		if (!StringUtil.isNotEmpty(strJson)) {
			throw new ParameterException(AppGlobals.PARAMETER_EMPTY_ERROR_MSG, AppGlobals.PARAMETER_EMPTY_ERROR);
		}
		JSONObject json = JSONObject.fromObject(strJson);
		checkParam(json);
		return json;
	}

	/** 验证参数是否为空 (param) */
	public static void checkParam(String[] fileds, String... param) throws ParameterException {
		for (int i = 0; i < param.length; i++) {
			if (!StringUtil.isNotEmpty(param[i])) {
				throw new ParameterException("参数" + fileds[i] + "为空", AppGlobals.PARAMETER_ERROR);
			}
		}
	}

	/** 验证参数是否为空 (JSONObject) */
	public static void checkParam(JSONObject param) throws ParameterException {
		Set<String> set = param.keySet();
		for (String p : set) {
			// System.out.println(param.get(p));
			if (!StringUtil.isNotEmpty(param.get(p) + "") && !"orderFlightnumber".equals(p) && !"orderUnitprice".equals(p)
					&& !"orderTrainnumber".equals(p) && !"remark".equals(p) && !"likeStation".equals(p)
					&& !"header".equals(p) && !"imgName".equals(p) && !"idCard".equals(p) && !"refundPrice".equals(p)) {
				throw new ParameterException("参数" + p + "为空", AppGlobals.PARAMETER_ERROR);
			}
		}
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
