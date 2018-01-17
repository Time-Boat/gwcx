package com.yhy.lin.app.entity;

/**
 * Description :
 * 
 * @author Administrator
 * @date 2017年5月21日 上午1:18:21
 */
public class AppUserOrderDetailEntity extends AppBaseOrderEntity {
	
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
	private String orderType;// 订单类型 0:接机 1:送机 2:接火车 3:送火车
	private String orderId;// 订单编号
	
	private String licencePlate;// 车牌号
	private String carType;// 车辆类型

	private String driver;//司机
	private String driverPhone;//司机手机号

	private String stationStartTime;   //出发时间
	private String stationEndTime;     //到达时间
	
	private String servicePhone;     //客服电话
	
	private String isShowPhone;      //是否显示客服电话     0:不显示   1：显示
	
	private String isException;      //是否是异常订单   0：不是     1：是
	
	private String remark;     //用户订单备注
	
	public String getIsException() {
		return isException;
	}

	public void setIsException(String isException) {
		this.isException = isException;
	}

	public String getIsShowPhone() {
		return isShowPhone;
	}

	public void setIsShowPhone(String isShowPhone) {
		this.isShowPhone = isShowPhone;
	}

	public String getServicePhone() {
		return servicePhone;
	}

	public void setServicePhone(String servicePhone) {
		this.servicePhone = servicePhone;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStationStartTime() {
		return stationStartTime;
	}

	public void setStationStartTime(String stationStartTime) {
		this.stationStartTime = stationStartTime;
	}

	public String getStationEndTime() {
		return stationEndTime;
	}

	public void setStationEndTime(String stationEndTime) {
		this.stationEndTime = stationEndTime;
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
