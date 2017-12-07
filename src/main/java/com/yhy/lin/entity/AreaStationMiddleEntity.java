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
 * @Description: 区域站点中间表
 * @author zhangdaihao
 * @date 2017-12-07 09:36:39
 * @version V1.0   
 *
 */
@Entity
@Table(name = "area_station_middle", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class AreaStationMiddleEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**站点id*/
	private java.lang.String stationId;
	/**x坐标*/
	private java.lang.String areaX;
	/**y坐标*/
	private java.lang.String areaY;
	/**坐标顺序*/
	private java.lang.String xySeq;
	
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  x坐标
	 */
	@Column(name ="AREA_X",nullable=true,length=20)
	public java.lang.String getAreaX(){
		return this.areaX;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  x坐标
	 */
	public void setAreaX(java.lang.String areaX){
		this.areaX = areaX;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  y坐标
	 */
	@Column(name ="AREA_Y",nullable=true,length=20)
	public java.lang.String getAreaY(){
		return this.areaY;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  y坐标
	 */
	public void setAreaY(java.lang.String areaY){
		this.areaY = areaY;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  坐标顺序
	 */
	@Column(name ="xy_seq",nullable=true,length=4)
	public java.lang.String getXySeq(){
		return this.xySeq;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  坐标顺序
	 */
	public void setXySeq(java.lang.String xySeq){
		this.xySeq = xySeq;
	}
}
