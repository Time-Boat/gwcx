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
 * @Description: 区域站点和线路中间表 
 * @author zhangdaihao
 * @date 2017-07-18 15:53:50
 * @version V1.0   
 *
 */
@Entity
@Table(name = "area_station_line", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class AreaStationLineEntity implements java.io.Serializable {
	/**主键id*/
	private java.lang.String id;
	/**两点的距离*/
	private java.lang.String distance;
	/**价格*/
	private java.lang.String price;
	/**所需时长*/
	private java.lang.String duration;
	/**区域线路id*/
	private java.lang.String areaLineId;
	/**区域站点id*/
	private java.lang.String areaStationId;
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
	 *@return: java.lang.String  两点的距离
	 */
	@Column(name ="DISTANCE",nullable=true,length=5)
	public java.lang.String getDistance(){
		return this.distance;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  两点的距离
	 */
	public void setDistance(java.lang.String distance){
		this.distance = distance;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  价格
	 */
	@Column(name ="PRICE",nullable=true,length=100)
	public java.lang.String getPrice(){
		return this.price;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  价格
	 */
	public void setPrice(java.lang.String price){
		this.price = price;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所需时长
	 */
	@Column(name ="DURATION",nullable=true,length=10)
	public java.lang.String getDuration(){
		return this.duration;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所需时长
	 */
	public void setDuration(java.lang.String duration){
		this.duration = duration;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  区域线路id
	 */
	@Column(name ="AREA_LINE_ID",nullable=true,length=32)
	public java.lang.String getAreaLineId(){
		return this.areaLineId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  区域线路id
	 */
	public void setAreaLineId(java.lang.String areaLineId){
		this.areaLineId = areaLineId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  区域站点id
	 */
	@Column(name ="AREA_STATION_ID",nullable=true,length=32)
	public java.lang.String getAreaStationId(){
		return this.areaStationId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  区域站点id
	 */
	public void setAreaStationId(java.lang.String areaStationId){
		this.areaStationId = areaStationId;
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
