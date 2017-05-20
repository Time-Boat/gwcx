package com.yhy.lin.entity;

import java.util.Date;

public class TransferorderView implements java.io.Serializable {
	private String id;//主键
	private String  orderId;//订单id
	private String orderType;//订单类型 0:接机  1:送机  2:接火车 3:送火车
	private String orderStatus;//订单状态 0：订单已完成。1：已付款待审核。2：审核通过待发车3：取消订单待退款。4：取消订单完成退款。
	private String  orderFlightnumber;//航班号
	private String  orderStartingstation;//起点站
	private String  orderTerminusstation;//终点站
	private Date  orderStartime;//发车时间
	private String orderExpectedarrival;//预计到达时间
	private String  orderUnitprice;//单价
	private String  orderNumbers;//车票数量
    private String  orderPaytype;//支付方式0：微信 1：支付宝 2：银联
	private String  orderContactsname;//联系人，申请人
	private String  orderContactsmobile;//联系人手机号
	private String  orderPaystatus;//支付状态 0：已付款，1：退款中 2：已退款 3:：未付款
	private String  orderTrainnumber;//火车车次
//	private String orderNumberPeople;//人数
	private String  orderTotalPrice;//总价
	
	private Date applicationTime;//申请时间
	
	
	private String lineId;
	private String lineName;
	
	private String driverName;//司机姓名
	private String driverMobile;//司机手机号
	private String licencePlate;//车牌号
	private String carStatus;//车辆状态
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOrderFlightnumber() {
		return orderFlightnumber;
	}
	public void setOrderFlightnumber(String orderFlightnumber) {
		this.orderFlightnumber = orderFlightnumber;
	}
	public String getOrderStartingstation() {
		return orderStartingstation;
	}
	public void setOrderStartingstation(String orderStartingstation) {
		this.orderStartingstation = orderStartingstation;
	}
	public String getOrderTerminusstation() {
		return orderTerminusstation;
	}
	public void setOrderTerminusstation(String orderTerminusstation) {
		this.orderTerminusstation = orderTerminusstation;
	}
 
	public String getOrderExpectedarrival() {
		return orderExpectedarrival;
	}
	public void setOrderExpectedarrival(String orderExpectedarrival) {
		this.orderExpectedarrival = orderExpectedarrival;
	}
	public String getOrderUnitprice() {
		return orderUnitprice;
	}
	public void setOrderUnitprice(String orderUnitprice) {
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
//	public String getOrderNumberPeople() {
//		return orderNumberPeople;
//	}
//	public void setOrderNumberPeople(String orderNumberPeople) {
//		this.orderNumberPeople = orderNumberPeople;
//	}
	public String getOrderTotalPrice() {
		return orderTotalPrice;
	}
	public void setOrderTotalPrice(String orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
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
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getDriverMobile() {
		return driverMobile;
	}
	public void setDriverMobile(String driverMobile) {
		this.driverMobile = driverMobile;
	}
	public String getLicencePlate() {
		return licencePlate;
	}
	public void setLicencePlate(String licencePlate) {
		this.licencePlate = licencePlate;
	}
	
	public String getCarStatus() {
		return carStatus;
	}
	public void setCarStatus(String carStatus) {
		this.carStatus = carStatus;
	}
	public Date getOrderStartime() {
		return orderStartime;
	}
	public void setOrderStartime(Date orderStartime) {
		this.orderStartime = orderStartime;
	}
	public Date getApplicationTime() {
		return applicationTime;
	}
	public void setApplicationTime(Date applicationTime) {
		this.applicationTime = applicationTime;
	}
	
	
}
