package com.yhy.lin.app.entity;

/**
* Description : 
* @author Administrator
* @date 2017年5月24日 下午5:20:44
*/
public class AppCustomerEntity {

	/** 主键 */
	private java.lang.String userId;
	/** 真实姓名 */
	private java.lang.String realName;
	/** 性别 */
	private java.lang.String sex;
	/** 手机号码（正常使用的唯一） */
	private java.lang.String phone;
	/** 身份证号码 */
	private java.lang.String cardNumber;
	/** 家的地址 */
	private java.lang.String address;
	/** 用户头像  */
	private java.lang.String customerImg;
	
	public java.lang.String getUserId() {
		return userId;
	}
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}
	public java.lang.String getRealName() {
		return realName;
	}
	public void setRealName(java.lang.String realName) {
		this.realName = realName;
	}
	public java.lang.String getSex() {
		return sex;
	}
	public void setSex(java.lang.String sex) {
		this.sex = sex;
	}
	public java.lang.String getPhone() {
		return phone;
	}
	public void setPhone(java.lang.String phone) {
		this.phone = phone;
	}
	public java.lang.String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(java.lang.String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public java.lang.String getAddress() {
		return address;
	}
	public void setAddress(java.lang.String address) {
		this.address = address;
	}
	public java.lang.String getCustomerImg() {
		return customerImg;
	}
	public void setCustomerImg(java.lang.String customerImg) {
		this.customerImg = customerImg;
	}
	
}
