package com.yhy.lin.entity;


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
 * @Description: 渠道商和客户关联表
 * @author zhangdaihao
 * @date 2017-07-03 12:01:36
 * @version V1.0   
 *
 */
@Entity
@Table(name = "dealer_customer", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class DealerCustomerEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**渠道商id*/
	private java.lang.String dealerId;
	/**订票用户微信的openid*/
	private java.lang.String openId;
	/**创建时间*/
	private java.util.Date createDate;
	/**状态*/
	private java.lang.String status;
	/**备注*/
	private java.lang.String remark;
	
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  渠道商id
	 */
	@Column(name ="DEALER_ID",nullable=true,length=32)
	public java.lang.String getDealerId(){
		return this.dealerId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  渠道商id
	 */
	public void setDealerId(java.lang.String dealerId){
		this.dealerId = dealerId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  订票用户微信的openid
	 */
	@Column(name ="OPEN_ID",nullable=true,length=32)
	public java.lang.String getOpenId(){
		return this.openId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订票用户微信的openid
	 */
	public void setOpenId(java.lang.String openId){
		this.openId = openId;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="CREATE_DATE",nullable=true)
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  状态
	 */
	@Column(name ="STATUS",nullable=true,length=32)
	public java.lang.String getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  状态
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="REMARK",nullable=true,length=32)
	public java.lang.String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setRemark(java.lang.String remark){
		this.remark = remark;
	}
}
