package com.yhy.lin.app.entity;

/**
* Description : app订单列表类
* @author Administrator
* @date 2017年5月20日 下午7:03:49
*/
public class AppUserOrderEntity extends AppBaseOrderEntity{
	
	private String id;
	private String orderId;// 订单编号
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
