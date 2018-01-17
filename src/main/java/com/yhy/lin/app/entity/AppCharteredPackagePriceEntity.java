package com.yhy.lin.app.entity;

import java.math.BigDecimal;

/**
* Description : 
* @author Administrator
* @date 2017年5月24日 下午5:20:44
*/
public class AppCharteredPackagePriceEntity {

	/**车辆类型 */
	private String carType;
	/**起步价 */
	private BigDecimal initiatePrice;
	/**基础公里数*/
	private Integer kilometre;
	/**基础时长*/
	private Integer timeLength;
	/**超公里/元*/
	private BigDecimal exceedKmPrice;
	/**超时长/元*/
	private BigDecimal exceedTimePrice;
	/**空反费（公里/元）*/
	private BigDecimal emptyReturn;
	
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	public BigDecimal getInitiatePrice() {
		return initiatePrice;
	}
	public void setInitiatePrice(BigDecimal initiatePrice) {
		this.initiatePrice = initiatePrice;
	}
	public Integer getKilometre() {
		return kilometre;
	}
	public void setKilometre(Integer kilometre) {
		this.kilometre = kilometre;
	}
	public Integer getTimeLength() {
		return timeLength;
	}
	public void setTimeLength(Integer timeLength) {
		this.timeLength = timeLength;
	}
	public BigDecimal getExceedKmPrice() {
		return exceedKmPrice;
	}
	public void setExceedKmPrice(BigDecimal exceedKmPrice) {
		this.exceedKmPrice = exceedKmPrice;
	}
	public BigDecimal getExceedTimePrice() {
		return exceedTimePrice;
	}
	public void setExceedTimePrice(BigDecimal exceedTimePrice) {
		this.exceedTimePrice = exceedTimePrice;
	}
	public BigDecimal getEmptyReturn() {
		return emptyReturn;
	}
	public void setEmptyReturn(BigDecimal emptyReturn) {
		this.emptyReturn = emptyReturn;
	}
	
}
