package com.yhy.lin.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: Entity
 * @Description: 验证码实体类
 * @author zhangdaihao
 * @date 2017-10-31 09:57:19
 * @version V1.0   
 *
 */
@Entity
@Table(name = "message_code", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class MessageCodeEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**创建时间*/
	private java.util.Date createTime;
	/**手机号*/
	private java.lang.String phone;
	/**验证码*/
	private java.lang.String msgCode;
	/**是否已被使用  0: 未使用  1：已使用*/
	private java.lang.String isUsed;
	/**验证码类型      0：登录验证码    1：渠道商用户修改密码验证码     2：渠道商忘记密码验证码    3:申请渠道商验证码接口*/
	private java.lang.String codeType;
	
	@Column(name ="CODE_TYPE",nullable=true,length=2)
	public java.lang.String getCodeType() {
		return codeType;
	}

	public void setCodeType(java.lang.String codeType) {
		this.codeType = codeType;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
	 */
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=32)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="CREATE_TIME",nullable=true)
	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  手机号
	 */
	@Column(name ="PHONE",nullable=true,length=11)
	public java.lang.String getPhone(){
		return this.phone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  手机号
	 */
	public void setPhone(java.lang.String phone){
		this.phone = phone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  验证码
	 */
	@Column(name ="MSG_CODE",nullable=true,length=6)
	public java.lang.String getMsgCode(){
		return this.msgCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  验证码
	 */
	public void setMsgCode(java.lang.String msgCode){
		this.msgCode = msgCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否已被使用  0: 未使用  1：已使用
	 */
	@Column(name ="IS_USED",nullable=true,length=50)
	public java.lang.String getIsUsed(){
		return this.isUsed;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否已被使用  0: 未使用  1：已使用
	 */
	public void setIsUsed(java.lang.String isUsed){
		this.isUsed = isUsed;
	}
}
