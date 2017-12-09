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
 * @Description: 渠道商订单用户选择站点信息
 * @author zhangdaihao
 * @date 2017-12-08 15:43:09
 * @version V1.0   
 *
 */
@Entity
@Table(name = "dealer_order_user_station", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class DealerOrderUserStationEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**站点id*/
	private java.lang.String orderId;
	/**x坐标*/
	private java.lang.String stationX;
	/**y坐标*/
	private java.lang.String stationY;
	
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
	 *@return: java.lang.String  站点id
	 */
	@Column(name ="ORDER_ID",nullable=true,length=32)
	public java.lang.String getOrderId(){
		return this.orderId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  站点id
	 */
	public void setOrderId(java.lang.String orderId){
		this.orderId = orderId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  x坐标
	 */
	@Column(name ="STATION_X",nullable=true,length=20)
	public java.lang.String getStationX(){
		return this.stationX;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  x坐标
	 */
	public void setStationX(java.lang.String stationX){
		this.stationX = stationX;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  y坐标
	 */
	@Column(name ="STATION_Y",nullable=true,length=20)
	public java.lang.String getStationY(){
		return this.stationY;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  y坐标
	 */
	public void setStationY(java.lang.String stationY){
		this.stationY = stationY;
	}
}
