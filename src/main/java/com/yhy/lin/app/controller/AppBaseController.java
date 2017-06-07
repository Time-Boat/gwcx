package com.yhy.lin.app.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;

import com.yhy.lin.app.entity.CarCustomerEntity;
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

	// 生成token
	public String generateToken(String customerId, String phone) {
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

	// app
	public void responseOutWrite(HttpServletResponse response, JSONObject jObject) {
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
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

	/** 验证参数是否为空 (json) */
	public static void checkParam(String strJson) throws ParameterException {
		JSONObject json = JSONObject.fromObject(strJson);
		checkParam(json);
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
	public static String checkParam(JSONObject param) throws ParameterException {
		Set<String> set = param.keySet();
		for (String p : set) {
			if (StringUtil.isNotEmpty(param.get(p) + "")) {
				throw new ParameterException("参数" + p + "为空", AppGlobals.PARAMETER_ERROR);
			}
		}
		return "";
	}

}
