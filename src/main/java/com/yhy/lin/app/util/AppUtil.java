package com.yhy.lin.app.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AppUtil {
	// 将Json对象转String类型输出页面
	public static void convertStringToJSON(HttpServletResponse response, JSONObject jsonObj) {
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(jsonObj.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
		}
	}

	// 将Json对象转String类型输出页面
	public static void convertStringToJSONArr(HttpServletResponse response, JSONArray jsonArr) {
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(jsonArr.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
		}
	}

	// 在response返回时,定义文件的编码
	public static void responseUTF8(HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache");
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
	}

	public static void outPrint(HttpServletResponse response, String outPrintStr) {
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(outPrintStr);
			out.flush();
			out.close();
		} catch (Exception e) {
		}
	}

	//input流转成byte字节数组
	public static final byte[] readBytes(InputStream is, int contentLen) {
		try {
			int a = is.available();
			String b = a+"";
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (contentLen > 0) {
			int readLen = 0;
			int readLengthThisTime = 0;
			byte[] message = new byte[contentLen];
			try {
				while (readLen != contentLen) {
					readLengthThisTime = is.read(message);
					if (readLengthThisTime == -1) {// Should not happen.
						break;
					}
					readLen += readLengthThisTime;
				}
				return message;
			} catch (IOException e) {
				// Ignore
				e.printStackTrace();
			}finally{
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return new byte[] {};
	}
	
	//获取body中的流，将其转换成json字符串
	public static String inputToStr(HttpServletRequest request) throws IOException {
		InputStream is = request.getInputStream();
		request.setCharacterEncoding("UTF-8");
		int size = request.getContentLength();
		byte[] reqBodyBytes = readBytes(is, size);
		String param = new String(reqBodyBytes);
		return param;
	}
	
	/**
	 * 将值为null的字符串转为空字符串
	 *
	 * @param srcString
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public static String Null2Blank(String srcString) {
		if (srcString == null || srcString.trim().equals("") || srcString.trim().equalsIgnoreCase("null")) {
			return "";
		} else {
			return srcString.trim();
		}
	}
	
	//null转0
	public static float StrToZero(String srcString) {
		float f = 0;
		if (Null2Blank(srcString).length() > 0) {
			try {
				f = Float.parseFloat(srcString);
			} catch (Exception e) {
			}
		}
		return f;
	}
	
}
