package com.yhy.lin.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;

/**   
 * @Title: Entity
 * @Description: 包车套餐设置
 * @author zhangdaihao
 * @date 2017-10-17 17:05:06
 * @version V1.0   
 *
 */
@Entity
@Table(name = "chartered_package", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class CharteredPackageEntity implements java.io.Serializable {
	/**套餐id*/
	private java.lang.String id;
	/**套餐名称*/
	private java.lang.String name;
	/**公里数*/
	private java.lang.Integer kilometre;
	/**时长(分钟)*/
	private java.lang.Integer timeLength;
	/**所属城市*/
	private java.lang.String cityId;
	/**用户id*/
	private java.lang.String createUserId;
	/**创建时间*/
	private java.util.Date createTime;
	/**修改时间*/
	private java.util.Date updateTime;
	/**用于逻辑删除 0正常  1已删除*/
	private java.lang.Integer deleteFlag;
	/**是否上架*/
	private java.lang.String status;
	/**备注（备用字段）*/
	private java.lang.String remark;
	
	/**套餐内容*/
	private java.lang.String description;
	
	
	@Column(name ="DESCRIPTION",nullable=true,length=255)
	public java.lang.String getDescription() {
		return description;
	}

	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  套餐id
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
	 *@param: java.lang.String  套餐id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  套餐名称
	 */
	@Column(name ="NAME",nullable=true,length=255)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  套餐名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  公里数
	 */
	@Column(name ="KILOMETRE",nullable=true,precision=10,scale=0)
	public java.lang.Integer getKilometre(){
		return this.kilometre;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  公里数
	 */
	public void setKilometre(java.lang.Integer kilometre){
		this.kilometre = kilometre;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  时长(分钟)
	 */
	@Column(name ="TIME_LENGTH",nullable=true,precision=10,scale=0)
	public java.lang.Integer getTimeLength(){
		return this.timeLength;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  时长(分钟)
	 */
	public void setTimeLength(java.lang.Integer timeLength){
		this.timeLength = timeLength;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属城市
	 */
	@Column(name ="CITY_ID",nullable=true,length=32)
	public java.lang.String getCityId(){
		return this.cityId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属城市
	 */
	public void setCityId(java.lang.String cityId){
		this.cityId = cityId;
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
	@Column(name ="UPDATE_TIME",nullable=true)
	public java.util.Date getUpdateTime(){
		return this.updateTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  修改时间
	 */
	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  用于逻辑删除 0正常  1已删除
	 */
	@Column(name ="DELETE_FLAG",nullable=true,precision=3,scale=0)
	public java.lang.Integer getDeleteFlag(){
		return this.deleteFlag;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  用于逻辑删除 0正常  1已删除
	 */
	public void setDeleteFlag(java.lang.Integer deleteFlag){
		this.deleteFlag = deleteFlag;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否上架
	 */
	@Column(name ="STATUS",nullable=true,length=1)
	public java.lang.String getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否上架
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注（备用字段）
	 */
	@Column(name ="REMARK",nullable=true,length=1000)
	public java.lang.String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注（备用字段）
	 */
	public void setRemark(java.lang.String remark){
		this.remark = remark;
	}
}
