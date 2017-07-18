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
 * @Description: 区域站点信息
 * @author zhangdaihao
 * @date 2017-07-18 15:53:27
 * @version V1.0   
 *
 */
@Entity
@Table(name = "area_station", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class AreaStationEntity implements java.io.Serializable {
	/**主键id*/
	private java.lang.String id;
	/**站点名称*/
	private java.lang.String name;
	/**站点的经度*/
	private java.lang.String x;
	/**站点的纬度*/
	private java.lang.String y;
	/**所属区域*/
	private java.lang.String areaId;
	/**站点创建时间*/
	private java.util.Date createTime;
	/**站点创建人*/
	private java.lang.String createPeople;
	/**站点地址*/
	private java.lang.String location;
	/**备注*/
	private java.lang.String remark;
	/**状态*/
	private java.lang.String status;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键id
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
	 *@param: java.lang.String  主键id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  站点名称
	 */
	@Column(name ="NAME",nullable=true,length=50)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  站点名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  站点的经度
	 */
	@Column(name ="X",nullable=true,length=100)
	public java.lang.String getX(){
		return this.x;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  站点的经度
	 */
	public void setX(java.lang.String x){
		this.x = x;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  站点的纬度
	 */
	@Column(name ="Y",nullable=true,length=100)
	public java.lang.String getY(){
		return this.y;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  站点的纬度
	 */
	public void setY(java.lang.String y){
		this.y = y;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属区域
	 */
	@Column(name ="AREA_ID",nullable=true,length=100)
	public java.lang.String getAreaId(){
		return this.areaId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属区域
	 */
	public void setAreaId(java.lang.String areaId){
		this.areaId = areaId;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  站点创建时间
	 */
	@Column(name ="CREATE_TIME",nullable=true)
	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  站点创建时间
	 */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  站点创建人
	 */
	@Column(name ="CREATE_PEOPLE",nullable=true,length=50)
	public java.lang.String getCreatePeople(){
		return this.createPeople;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  站点创建人
	 */
	public void setCreatePeople(java.lang.String createPeople){
		this.createPeople = createPeople;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  站点地址
	 */
	@Column(name ="LOCATION",nullable=true,length=200)
	public java.lang.String getLocation(){
		return this.location;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  站点地址
	 */
	public void setLocation(java.lang.String location){
		this.location = location;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="REMARK",nullable=true,length=500)
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  状态
	 */
	@Column(name ="STATUS",nullable=true,length=10)
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
}
