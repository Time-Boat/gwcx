package com.yhy.lin.app.entity;

/**
* Description : 
* @author Administrator
* @date 2017年5月21日 上午1:18:21
*/
public class AppUserOrderDetailEntity extends AppUserOrderEntity{

	private String orderContactsname;//联系人
	private String orderContactsmobile; //联系人手机号
	private String applicationTime;		//下单时间
	
	private String driver;
	private String driverPhone;
	
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
