package com.yhy.lin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jeecgframework.core.common.entity.IdEntity;


/**   
 * @Title: Entity
 * @Description: 包车服务
 * @author zhangdaihao
 * @date 2017-10-20 10:55:22
 * @version V1.0   
 *
 */
@Entity
@Table(name = "chartered_append_service", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class CharteredAppendServiceEntity extends IdEntity implements java.io.Serializable {
	
	/**名称*/
	private java.lang.String serviceName;
	/**服务描述*/
	private java.lang.String serviceDescription;
	/**用户id*/
	private java.lang.String createUserId;
	/**创建时间*/
	private java.util.Date createTime;
	/**修改时间*/
	private java.util.Date midifyTime;
	/**是否上架 0：未上架 1：已上架*/
	private java.lang.String status;
	/**备注*/
	private java.lang.String remark;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名称
	 */
	@Column(name ="SERVICE_NAME",nullable=true,length=20)
	public java.lang.String getServiceName(){
		return this.serviceName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  名称
	 */
	public void setServiceName(java.lang.String serviceName){
		this.serviceName = serviceName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  服务描述
	 */
	@Column(name ="SERVICE_DESCRIPTION",nullable=true,length=255)
	public java.lang.String getServiceDescription(){
		return this.serviceDescription;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  服务描述
	 */
	public void setServiceDescription(java.lang.String serviceDescription){
		this.serviceDescription = serviceDescription;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户id
	 */
	@Column(name ="CREATE_USER_ID",nullable=true,length=32)
	public java.lang.String getCreateUserId(){
		return this.createUserId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户id
	 */
	public void setCreateUserId(java.lang.String createUserId){
		this.createUserId = createUserId;
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
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  修改时间
	 */
	@Column(name ="MIDIFY_TIME",nullable=true)
	public java.util.Date getMidifyTime(){
		return this.midifyTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  修改时间
	 */
	public void setMidifyTime(java.util.Date midifyTime){
		this.midifyTime = midifyTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否上架 0：未上架 1：已上架
	 */
	@Column(name ="STATUS",nullable=true,length=1)
	public java.lang.String getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否上架 0：未上架 1：已上架
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="REMARK",nullable=true,length=1000)
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
