package com.yhy.lin.app.entity;

import java.math.BigDecimal;

/**
* Description : app订单列表类
* @author Administrator
* @date 2017年5月20日 下午7:03:49
*/
public class AppUserOrderEntity {
	
	private String id;
	private String orderStartime;//发车时间
	private String orderNumbers; //订单数量
	private String orderStartingStationId;// 起点站
	private String orderTerminusStationId;// 终点站
	private BigDecimal orderTotalPrice;// 总价
	private Integer orderStatus;// 订单状态    0：订单已完成。1：已付款待审核。2：审核通过待发车3：取消订单待退款。4：取消订单完成退款。
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
	public String getOrderStartime() {
		return orderStartime;
	}
	public void setOrderStartime(String orderStartime) {
		this.orderStartime = orderStartime;
	}
	public String getOrderNumbers() {
		return orderNumbers;
	}
	public void setOrderNumbers(String orderNumbers) {
		this.orderNumbers = orderNumbers;
	}
	public String getOrderStartingStationId() {
		return orderStartingStationId;
	}
	public void setOrderStartingStationId(String orderStartingStationId) {
		this.orderStartingStationId = orderStartingStationId;
	}
	public String getOrderTerminusStationId() {
		return orderTerminusStationId;
	}
	public void setOrderTerminusStationId(String orderTerminusStationId) {
		this.orderTerminusStationId = orderTerminusStationId;
	}
	public BigDecimal getOrderTotalPrice() {
		return orderTotalPrice;
	}
	public void setOrderTotalPrice(BigDecimal orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	
	
}
