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
 * @Description: 乘客常用站点
 * @author zhangdaihao
 * @date 2017-05-24 18:32:28
 * @version V1.0   
 *
 */
@Entity
@Table(name = "customer_common_addr", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class CustomerCommonAddrEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**用户id*/
	private java.lang.String userId;
	/**站点id*/
	private java.lang.String stationId;
	/**站点使用次数*/
	private java.lang.Integer count;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
	 */
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=36)
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
	 *@return: java.lang.String  用户id
	 */
	@Column(name ="USER_ID",nullable=true,length=32)
	public java.lang.String getUserId(){
		return this.userId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户id
	 */
	public void setUserId(java.lang.String userId){
		this.userId = userId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  站点id
	 */
	@Column(name ="STATION_ID",nullable=true,length=32)
	public java.lang.String getStationId(){
		return this.stationId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  站点id
	 */
	public void setStationId(java.lang.String stationId){
		this.stationId = stationId;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  站点使用次数
	 */
	@Column(name ="COUNT",nullable=true,length=20)
	public java.lang.Integer getCount(){
		return this.count;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  站点使用次数
	 */
	public void setCount(java.lang.Integer count){
		this.count = count;
	}
}
