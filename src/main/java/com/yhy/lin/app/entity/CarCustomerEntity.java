package com.yhy.lin.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**
 * @Title: Entity
 * @Description: 乘客表
 * @author zhangdaihao
 * @date 2017-05-03 09:42:12
 * @version V1.0
 *
 */
@Entity
@Table(name = "car_customer", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class CarCustomerEntity implements java.io.Serializable, UserInfo {
	/** 主键 */
	private java.lang.String id;
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
	/** 常用地址 */
	private java.lang.String commonAddr;
	/** 注册时间 */
	private java.util.Date createTime;
	/** Token号 */
	private java.lang.String token;
	/** Token创建日期 */
	private java.util.Date tokenUpdateTime;
	/** 备注信息 */
	private java.lang.String remark;

	/** 验证码 */
	private java.lang.String securityCode;
	/** 更新时间 */
	private java.util.Date codeUpdateTime;
	/** 登录类型（保留） */
	private java.lang.String loginType;
	/** 状态：0.未校验；1.已校验； */
	private java.lang.String status;
	
	/** 用户头像  */
	private java.lang.String customerImg;
	
	/** 此客户的微信唯一识别码  */
	private java.lang.String openId;
	
	/** 渠道商绑定用户时间  */
	private java.util.Date dealerBandingTime;
	
	/** 用户登录次数*/
	private int loginCount;
	
	/**
	 * 登录次数
	 * @return: 
	 */
	@Column(name = "LOGIN_COUNT", nullable = true,precision=6,scale=0)
	public int getLoginCount() {
		return loginCount;
	}
	
	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}
	
	@Column(name = "DEALER_BANDING_TIME", nullable = true)
	public java.util.Date getDealerBandingTime() {
		return dealerBandingTime;
	}
	
	public void setDealerBandingTime(java.util.Date dealerBandingTime) {
		this.dealerBandingTime = dealerBandingTime;
	}
	
	@Column(name = "OPEN_ID", nullable = true, length = 32)
	public java.lang.String getOpenId() {
		return openId;
	}
	public void setOpenId(java.lang.String openId) {
		this.openId = openId;
	}
	
	@Column(name = "CUSTOMER_IMG", nullable = true, length = 50)
	public java.lang.String getCustomerImg() {
		return customerImg;
	}
	public void setCustomerImg(java.lang.String customerImg) {
		this.customerImg = customerImg;
	}
	
	@Column(name = "SECURITY_CODE", nullable = true, length = 4)
	public java.lang.String getSecurityCode() {
		return securityCode;
	}
	public void setSecurityCode(java.lang.String securityCode) {
		this.securityCode = securityCode;
	}

	@Column(name = "CODE_UPDATE_TIME", nullable = true)
	public java.util.Date getCodeUpdateTime() {
		return codeUpdateTime;
	}
	public void setCodeUpdateTime(java.util.Date codeUpdateTime) {
		this.codeUpdateTime = codeUpdateTime;
	}

	@Column(name = "LOGIN_TYPE", nullable = true, length = 1)
	public java.lang.String getLoginType() {
		return loginType;
	}

	public void setLoginType(java.lang.String loginType) {
		this.loginType = loginType;
	}

	@Column(name = "STATUS", nullable = true, length = 1)
	public java.lang.String getStatus() {
		return status;
	}

	public void setStatus(java.lang.String status) {
		this.status = status;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "ID", nullable = false, length = 36)
	public String getId() {
		return this.id;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String
	 *             主键
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 真实姓名
	 */
	@Column(name = "REAL_NAME", nullable = true, length = 32)
	public java.lang.String getRealName() {
		return this.realName;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String
	 *             真实姓名
	 */
	public void setRealName(java.lang.String realName) {
		this.realName = realName;
	}

	/**
	 * 方法: 取得java.lang.Object
	 * 
	 * @return: java.lang.Object 性别
	 */
	@Column(name = "SEX", nullable = true, length = 1)
	public java.lang.String getSex() {
		return this.sex;
	}

	/**
	 * 方法: 设置java.lang.Object
	 * 
	 * @param: java.lang.Object
	 *             性别
	 */
	public void setSex(java.lang.String sex) {
		this.sex = sex;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 手机号码（正常使用的唯一）
	 */
	@Column(name = "PHONE", nullable = true, length = 16)
	public java.lang.String getPhone() {
		return this.phone;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String
	 *             手机号码（正常使用的唯一）
	 */
	public void setPhone(java.lang.String phone) {
		this.phone = phone;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 身份证号码
	 */
	@Column(name = "CARD_NUMBER", nullable = true, length = 24)
	public java.lang.String getCardNumber() {
		return this.cardNumber;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String
	 *             身份证号码
	 */
	public void setCardNumber(java.lang.String cardNumber) {
		this.cardNumber = cardNumber;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 家的地址
	 */
	@Column(name = "ADDRESS", nullable = true, length = 128)
	public java.lang.String getAddress() {
		return this.address;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String
	 *             家的地址
	 */
	public void setAddress(java.lang.String address) {
		this.address = address;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 常用地址
	 */
	@Column(name = "COMMON_ADDR", nullable = true, length = 128)
	public java.lang.String getCommonAddr() {
		return this.commonAddr;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String
	 *             常用地址
	 */
	public void setCommonAddr(java.lang.String commonAddr) {
		this.commonAddr = commonAddr;
	}

	/**
	 * 方法: 取得java.util.Date
	 * 
	 * @return: java.util.Date 注册时间
	 */
	@Column(name = "CREATE_TIME", nullable = true)
	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	/**
	 * 方法: 设置java.util.Date
	 * 
	 * @param: java.util.Date
	 *             注册时间
	 */
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String Token号
	 */
	@Column(name = "TOKEN", nullable = true, length = 100)
	public java.lang.String getToken() {
		return this.token;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String
	 *             Token号
	 */
	public void setToken(java.lang.String token) {
		this.token = token;
	}

	/**
	 * 方法: 取得java.util.Date
	 * 
	 * @return: java.util.Date Token创建日期
	 */
	@Column(name = "TOKEN_UPDATE_TIME", nullable = true)
	public java.util.Date getTokenUpdateTime() {
		return this.tokenUpdateTime;
	}

	/**
	 * 方法: 设置java.util.Date
	 * 
	 * @param: java.util.Date
	 *             Token创建日期
	 */
	public void setTokenUpdateTime(java.util.Date tokenUpdateTime) {
		this.tokenUpdateTime = tokenUpdateTime;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 备注信息
	 */
	@Column(name = "REMARK", nullable = true, length = 1000)
	public java.lang.String getRemark() {
		return this.remark;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String
	 *             备注信息
	 */
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	
	@Override
	@Transient
	public String getUserId() {
		return id;
	}

	public void setUserId(String userId) {
		
	}

	@Override
	@Transient
	public String getUserName() {
		return realName;
	}

	public void setUserName(String userName) {

	}
	
	
}
