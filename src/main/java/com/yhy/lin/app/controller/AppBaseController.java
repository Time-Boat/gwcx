package com.yhy.lin.app.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.web.system.service.SystemService;

import com.yhy.lin.app.entity.CarCustomerEntity;
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
		response.setHeader("Cache-Control", "no-cache");
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

	/**
	 * 站点类型转换 线路类型 转 站点类型
	 */
	public String getStationType(String lineType) {
		//
		switch (lineType) {
		case "2": // 接机
		case "3": // 送机
			return "2"; // 机场站点
		case "4": // 接火车
		case "5": // 送火车
			return "1"; // 火车站点
		default:
			return lineType;
		}
	}

}
