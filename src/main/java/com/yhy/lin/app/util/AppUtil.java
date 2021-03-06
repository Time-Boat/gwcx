package com.yhy.lin.app.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecgframework.core.util.DateUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class AppUtil {
	
	public static final String DATE_ABS = "abs";
	
	// 生成token
	public static String generateToken(String customerId, String phone) {
		return md5(customerId + phone + System.currentTimeMillis());
	}

	// jdk自带的md5加密
	private static String md5(String str) {
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

	// input流转成byte字节数组
	public static final byte[] readBytes(InputStream is, int contentLen) {
		if (contentLen > 0) {
			int readLen = 0;
			int readLengthThisTime = 0;
			byte[] message = new byte[contentLen];
			try {
				while (readLen != contentLen) {

					// 读取的方式不一样会出问题。。。。
					// readLengthThisTime = is.read(message);
					readLengthThisTime = is.read(message, readLen, contentLen - readLen);
					if (readLengthThisTime == -1) {// Should not happen.
						break;
					}
					readLen += readLengthThisTime;
				}
				return message;
			} catch (IOException e) {
				// Ignore
				e.printStackTrace();
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return new byte[] {};
	}

	// //获取body中的流，将其转换成json字符串
	// public static String inputToStr(HttpServletRequest request) throws
	// IOException {
	// request.setCharacterEncoding("UTF-8");
	// InputStream is = request.getInputStream();
	// int size = request.getContentLength();
	// byte[] reqBodyBytes = readBytes(is, size);
	// String param = new String(reqBodyBytes,"utf-8");
	// return param;
	// }

	// public static final byte[] readBytes(InputStream is, int contentLen) {
	// if (contentLen > 0) {
	// int readLen = 0;
	//
	// int readLengthThisTime = 0;
	//
	// byte[] message = new byte[contentLen];
	//
	// try {
	//
	// while (readLen != contentLen) {
	//
	// readLengthThisTime = is.read(message, readLen, contentLen
	// - readLen);
	//
	// if (readLengthThisTime == -1) {// Should not happen.
	// break;
	// }
	//
	// readLen += readLengthThisTime;
	// }
	//
	// return message;
	// } catch (IOException e) {
	// // Ignore
	// // e.printStackTrace();
	// }
	// }
	//
	// return new byte[] {};
	// }

	// 获取body中的流，将其转换成json字符串
	public static String inputToStr(HttpServletRequest request) throws IOException {
		request.setCharacterEncoding("UTF-8");
		InputStream is = request.getInputStream();
		String param1 = Base64Util.getFromBase64Input(is);
		// int size = request.getContentLength();
		// byte[] reqBodyBytes = readBytes(is, size);
		// String res = new String(reqBodyBytes);
		// String param = Base64Util.getFromBase64(res);
		// param1 = new String(param1.getBytes("iso-8859-1"),"utf-8");
		return param1;
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

	// null转0
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

	/**
	 * 判断字符是否是中文
	 *
	 * @param c
	 *            字符
	 * @return 是否是中文
	 */
	public static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字符串是否是乱码
	 *
	 * @param strName
	 *            字符串
	 * @return 是否是乱码
	 */
	public static boolean isMessyCode(String strName) {
		Pattern p = Pattern.compile("\\s*|t*|r*|n*");
		Matcher m = p.matcher(strName);
		String after = m.replaceAll("");
		String temp = after.replaceAll("\\p{P}", "");
		char[] ch = temp.trim().toCharArray();
		float chLength = ch.length;
		float count = 0;
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (!Character.isLetterOrDigit(c)) {
				if (!isChinese(c)) {
					count = count + 1;
				}
			}
		}
		float result = count / chLength;
		if (result > 0.4) {
			return true;
		} else {
			return false;
		}
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
	public static int compareDate(Date d1, Date d2, char flag, String isAbs) {

		long diff = 0;
		if ("abs".equals(isAbs)) {
			diff = Math.abs(d1.getTime() - d2.getTime());
		} else {
			diff = d1.getTime() - d2.getTime();
		}

		switch (flag) {
		case 'd':
			return (int) (diff / (24 * 3600 * 1000));
		case 'h':
			return (int) (diff / (3600 * 1000));
		case 'm':
			return (int) (diff / (60 * 1000));
		case 's':
			return (int) (diff / 1000);
		default:
			break;
		}

		return 0;
	}

	/** 获取当前时间(字符串) */
	public static String getCurTime() {
		return DateUtils.date2Str(DateUtils.datetimeFormat);
	}

	/** 获取当前时间(Date) */
	public static Date getDate() {
		return DateUtils.getDate();
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @param sdf
	 * @return
	 */
	public static Date str2Date(String str, SimpleDateFormat sdf) {
		return DateUtils.str2Date(str, sdf);
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return
	 */
	public static Date str2Date(String str) {
		return str2Date(str, DateUtils.datetimeFormat);
	}

	public static String dateformat(String date, String format) {
		return DateUtils.dateformat(date, format);
	}

	/**
	 * 站点类型转换 线路类型 转 站点类型
	 */
	public static String getStationType(String lineType) {
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
