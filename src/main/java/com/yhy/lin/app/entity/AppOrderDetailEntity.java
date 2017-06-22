package com.yhy.lin.app.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
* Description : 
* @author Administrator
* @date 2017年6月22日 下午7:10:22
*/
public class AppOrderDetailEntity {

	private String orderId;// 订单号
	private Integer orderType;// 订单类型 0:接机 1:送机 2:接火车 3:送火车
	private Integer orderStatus; //订单状态 0：订单已完成。1：已付款待审核。2：审核通过待发车3：取消订单待退款。4：取消订单完成退款。5：拒绝退款。6：未支付
	private String orderFlightnumber;// 航班号
	private String orderStartingStationId;// 起点站
	private String orderTerminusStationId;// 终点站
	private String orderStartime;// 发车时间
	private Date orderExpectedarrival;// 预计到达时间
	private BigDecimal orderUnitprice;// 单价
	private String orderNumbers;// 车票数量
	
	private String orderPaytype;// 支付方式0：微信 1：支付宝 2：银联
	private String orderContactsname;// 联系人
	private String orderContactsmobile;// 联系人手机号
	private String orderPaystatus;// 支付状态 0：已付款，1：退款中 2：已退款 3:未付款
	private String orderTrainnumber;// 火车车次

	private BigDecimal orderTotalPrice;// 总价

	private Date applicationTime;// 下达订单时间

	private String lineId;
	private String lineName;

	private String orderTerminusStationName;// 终点名称
	private String orderStartingStationName;// 起点名称

	private String userId; //用户id
	
	private String cityName; //城市名称
	private String cityId; //城市id
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOrderFlightnumber() {
		return orderFlightnumber;
	}
	public void setOrderFlightnumber(String orderFlightnumber) {
		this.orderFlightnumber = orderFlightnumber;
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
	public String getOrderStartime() {
		return orderStartime;
	}
	public void setOrderStartime(String orderStartime) {
		this.orderStartime = orderStartime;
	}
	public Date getOrderExpectedarrival() {
		return orderExpectedarrival;
	}
	public void setOrderExpectedarrival(Date orderExpectedarrival) {
		this.orderExpectedarrival = orderExpectedarrival;
	}
	public BigDecimal getOrderUnitprice() {
		return orderUnitprice;
	}
	public void setOrderUnitprice(BigDecimal orderUnitprice) {
		this.orderUnitprice = orderUnitprice;
	}
	public String getOrderNumbers() {
		return orderNumbers;
	}
	public void setOrderNumbers(String orderNumbers) {
		this.orderNumbers = orderNumbers;
	}
	public String getOrderPaytype() {
		return orderPaytype;
	}
	public void setOrderPaytype(String orderPaytype) {
		this.orderPaytype = orderPaytype;
	}
	public String getOrderContactsname() {
		return orderContactsname;
	}
	public void setOrderContactsname(String orderContactsname) {
		this.orderContactsname = orderContactsname;
	}
	public String getOrderContactsmobile() {
		return orderContactsmobile;
	}
	public void setOrderContactsmobile(String orderContactsmobile) {
		this.orderContactsmobile = orderContactsmobile;
	}
	public String getOrderPaystatus() {
		return orderPaystatus;
	}
	public void setOrderPaystatus(String orderPaystatus) {
		this.orderPaystatus = orderPaystatus;
	}
	public String getOrderTrainnumber() {
		return orderTrainnumber;
	}
	public void setOrderTrainnumber(String orderTrainnumber) {
		this.orderTrainnumber = orderTrainnumber;
	}
	public BigDecimal getOrderTotalPrice() {
		return orderTotalPrice;
	}
	public void setOrderTotalPrice(BigDecimal orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}
	public Date getApplicationTime() {
		return applicationTime;
	}
	public void setApplicationTime(Date applicationTime) {
		this.applicationTime = applicationTime;
	}
	public String getLineId() {
		return lineId;
	}
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public String getOrderTerminusStationName() {
		return orderTerminusStationName;
	}
	public void setOrderTerminusStationName(String orderTerminusStationName) {
		this.orderTerminusStationName = orderTerminusStationName;
	}
	public String getOrderStartingStationName() {
		return orderStartingStationName;
	}
	public void setOrderStartingStationName(String orderStartingStationName) {
		this.orderStartingStationName = orderStartingStationName;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	
	
	
}
