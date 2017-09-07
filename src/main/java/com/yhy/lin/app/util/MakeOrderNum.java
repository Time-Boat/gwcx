package com.yhy.lin.app.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Description : 订单生成序列
 * 
 * @author Administrator
 * @date 2017年5月16日 下午8:01:26
 */
public class MakeOrderNum {
	/**
	 * 锁对象，可以为任意对象
	 */
	private static Object lockObj = "lockerOrder";
	/**
	 * 订单号生成计数器
	 */
	private static long orderNumCount = 0L;
	/**
	 * 每毫秒生成订单号数量最大值
	 */
	private static int maxPerMSECSize = 1000;
	
	/**订单号生成前缀*/
	/** 接机*/
	public static final String AIRPORT_TO_DESTINATION_ORDER = "JA";
	/** 送机*/
	public static final String DESTINATION_TO_AIRPORT_ORDER = "SA";
	/** 接火车*/
	public static final String TRAIN_TO_DESTINATION_ORDER = "JR";
	/** 送火车*/
	public static final String DESTINATION_TO_TRAIN_ORDER = "SR";

	// 班车
	// 包车

	/**
	 * 生成非重复订单号，理论上限1毫秒1000个，可扩展
	 * 
	 * @param type
	 */
	public static String makeOrderNum(String type) {
		// 最终生成的订单号
		String finOrderNum = "";
		try {
			synchronized (lockObj) {
				// 取系统当前时间作为订单号变量前半部分，精确到毫秒
				long nowLong = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
				// 计数器到最大值归零，可扩展更大，目前1毫秒处理峰值1000个，1秒100万
				if (orderNumCount >= maxPerMSECSize) {
					orderNumCount = 0L;
				}
				// 组装订单号
				String countStr = maxPerMSECSize + orderNumCount + "";
				finOrderNum = nowLong + countStr.substring(1);
				orderNumCount++;
				// System.out.println(finOrderNum + "--" +
				// Thread.currentThread().getName() + "::" + tname );
				// Thread.sleep(1000);
			}
		} catch (Exception e) {
			finOrderNum = type + System.currentTimeMillis() + "";
			e.printStackTrace();
		}
		return type + finOrderNum;
	}
}

// enum CodeType {
// /**
// * 采购申请
// */
// SQ01("SQ01","采购申请"),
// /**
// * 采购
// */
// CG01("CG01","采购");
// /**
// *
// * @param type 类型
// * @param headCode 头信息前缀
// * @param name 名称
// */
// private CodeType(String type,String name){
// this.type=type;
// this.name=name;
// }
// private String type;
// private String name;
// public String getType() {
// return type;
// }
// public void setType(String type) {
// this.type = type;
// }
// public String getName() {
// return name;
// }
// public void setName(String name) {
// this.name = name;
// }
//
// }