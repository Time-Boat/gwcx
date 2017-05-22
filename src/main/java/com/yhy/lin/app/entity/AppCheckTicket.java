package com.yhy.lin.app.entity;

/**
* Description : app验票实体类
* @author Administrator
* @date 2017年5月22日 下午4:22:00
*/
public class AppCheckTicket extends AppBaseOrderEntity {

	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	private String licencePlate;// 车牌号
	private String carType;// 车辆类型

	private String driver;//司机
	private String driverPhone;//司机手机号

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
	
}
