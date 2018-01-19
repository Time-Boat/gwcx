package com.yhy.lin.app.entity;

/**
* Description : 订单实体类中共有的属性
* @author Administrator
* @date 2017年5月22日 下午4:15:46
*/
public class AppBaseOrderEntity {

	private String orderStartingStationName;// 起点站
	private String orderTerminusStationName;// 终点站
	private String orderStatus;// 订单状态
	// 0：订单已完成。1：已付款待审核。2：审核通过待发车3：取消订单待退款。4：取消订单完成退款。
	private String orderNumbers;// 车票数量
	private String orderTotalPrice;// 总价
	private String orderStartime;// 发车时间
	
	private String whichOrderPage;// 跳转页面    0：跳转订单详情    1：跳转申诉详情
	
	public String getWhichOrderPage() {
		return whichOrderPage;
	}
	public void setWhichOrderPage(String whichOrderPage) {
		this.whichOrderPage = whichOrderPage;
	}
	public String getOrderStartingStationName() {
		return orderStartingStationName;
	}
	public void setOrderStartingStationName(String orderStartingStationName) {
		this.orderStartingStationName = orderStartingStationName;
	}
	public String getOrderTerminusStationName() {
		return orderTerminusStationName;
	}
	public void setOrderTerminusStationName(String orderTerminusStationName) {
		this.orderTerminusStationName = orderTerminusStationName;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOrderNumbers() {
		return orderNumbers;
	}
	public void setOrderNumbers(String orderNumbers) {
		this.orderNumbers = orderNumbers;
	}
	public String getOrderTotalPrice() {
		return orderTotalPrice;
	}
	public void setOrderTotalPrice(String orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}
	public String getOrderStartime() {
		return orderStartime;
	}
	public void setOrderStartime(String orderStartime) {
		this.orderStartime = orderStartime;
	}
	
	
}
