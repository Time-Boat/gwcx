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
 * @Description: 区域站点和线路中间表
 * @author zhangdaihao
 * @date 2017-08-01 10:19:08
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
	/**两点的距离    （接机）*/
	private java.lang.String distanceBack;
	/**价格    （接机）*/
	private BigDecimal priceBack;
	/**所需时长    （接机）*/
	private java.lang.String durationBack;
	/**区域线路id*/
	private java.lang.String areaLineId;
	/**区域站点id*/
	private java.lang.String areaStationId;
	/**备注*/
	private java.lang.String remark;
	/**状态*/
	private java.lang.String status;
	/**两点的距离    （送机）*/
	private java.lang.String distanceGo;
	/**价格    （送机）*/
	private BigDecimal priceGo;
	/**所需时长    （送机）*/
	private java.lang.String durationGo;
	
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
	 *@return: java.lang.String  两点的距离    （接机）
	 */
	@Column(name ="DISTANCE_BACK",nullable=true,length=10)
	public java.lang.String getDistanceBack(){
		return this.distanceBack;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  两点的距离    （接机）
	 */
	public void setDistanceBack(java.lang.String distanceBack){
		this.distanceBack = distanceBack;
	}
	/**
	 *方法: 取得BigDecimal
	 *@return: BigDecimal  价格    （接机）
	 */
	@Column(name ="PRICE_BACK",nullable=true,precision=10,scale=2)
	public BigDecimal getPriceBack(){
		return this.priceBack;
	}

	/**
	 *方法: 设置BigDecimal
	 *@param: BigDecimal  价格    （接机）
	 */
	public void setPriceBack(BigDecimal priceBack){
		this.priceBack = priceBack;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所需时长    （接机）
	 */
	@Column(name ="DURATION_BACK",nullable=true,length=10)
	public java.lang.String getDurationBack(){
		return this.durationBack;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所需时长    （接机）
	 */
	public void setDurationBack(java.lang.String durationBack){
		this.durationBack = durationBack;
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  两点的距离    （送机）
	 */
	@Column(name ="DISTANCE_GO",nullable=true,length=10)
	public java.lang.String getDistanceGo(){
		return this.distanceGo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  两点的距离    （送机）
	 */
	public void setDistanceGo(java.lang.String distanceGo){
		this.distanceGo = distanceGo;
	}
	/**
	 *方法: 取得BigDecimal
	 *@return: BigDecimal  价格    （送机）
	 */
	@Column(name ="PRICE_GO",nullable=true,precision=10,scale=2)
	public BigDecimal getPriceGo(){
		return this.priceGo;
	}

	/**
	 *方法: 设置BigDecimal
	 *@param: BigDecimal  价格    （送机）
	 */
	public void setPriceGo(BigDecimal priceGo){
		this.priceGo = priceGo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所需时长    （送机）
	 */
	@Column(name ="DURATION_GO",nullable=true,length=10)
	public java.lang.String getDurationGo(){
		return this.durationGo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所需时长    （送机）
	 */
	public void setDurationGo(java.lang.String durationGo){
		this.durationGo = durationGo;
	}
}
