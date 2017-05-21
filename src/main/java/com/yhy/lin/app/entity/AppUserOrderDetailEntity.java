package com.yhy.lin.app.entity;

/**
 * Description :
 * 
 * @author Administrator
 * @date 2017年5月21日 上午1:18:21
 */
public class AppUserOrderDetailEntity extends AppUserOrderEntity {

	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private String orderContactsname; // 联系人
	private String orderContactsmobile; // 联系人手机号
	private String applicationTime; // 下单时间
	private String orderStartingStationName;// 起点站
	private String orderTerminusStationName;// 终点站
	private String orderStatus;// 订单状态
								// 0：订单已完成。1：已付款待审核。2：审核通过待发车3：取消订单待退款。4：取消订单完成退款。
	private String orderType;// 订单类型 0:接机 1:送机 2:接火车 3:送火车
	private String orderId;// 订单id
	private String orderStartime;// 发车时间
	private String orderNumbers;// 车票数量
	private String orderTotalPrice;// 总价

	private String licencePlate;// 车牌号
	private String carType;// 车辆状态

	private String driver;
	private String driverPhone;

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

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public String getOrderTotalPrice() {
		return orderTotalPrice;
	}

	public void setOrderTotalPrice(String orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}

	public String getLicencePlate() {
		return licencePlate;
	}

	public void setLicencePlate(String licencePlate) {
		this.licencePlate = licencePlate;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getDriverPhone() {
		return driverPhone;
	}

	public void setDriverPhone(String driverPhone) {
		this.driverPhone = driverPhone;
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

	public String getApplicationTime() {
		return applicationTime;
	}

	public void setApplicationTime(String applicationTime) {
		this.applicationTime = applicationTime;
	}

}
