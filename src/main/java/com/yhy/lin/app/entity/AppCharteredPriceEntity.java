package com.yhy.lin.app.entity;

import java.util.List;

/**
* Description : 
* @author Administrator
* @date 2017年5月24日 下午5:20:44
*/
public class AppCharteredPriceEntity {

	/**车辆类型 */
	private String carType;
	/** 车辆品牌 */
	private String carBrand;
	/** 价格*/
	private String price;
	/** 公司名称 */
	private String companyName;
	/** 部门编码 */
	private String departCode;
	/** 服务id */
	private List<AppAppendServiceEntity> serviceId;
	
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public String getCarBrand() {
		return carBrand;
	}
	public void setCarBrand(String carBrand) {
		this.carBrand = carBrand;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public List<AppAppendServiceEntity> getServiceId() {
		return serviceId;
	}
	public void setServiceId(List<AppAppendServiceEntity> serviceId) {
		this.serviceId = serviceId;
	}
	public String getDepartCode() {
		return departCode;
	}
	public void setDepartCode(String departCode) {
		this.departCode = departCode;
	}
	
}
