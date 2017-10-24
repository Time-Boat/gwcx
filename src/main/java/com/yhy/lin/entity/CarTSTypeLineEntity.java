package com.yhy.lin.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jeecgframework.core.common.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 车辆类型区间价格和线路中间实体类
 * @author zhangdaihao
 * @date 2017-10-24 09:55:56
 * @version V1.0   
 *
 */
@Entity
@Table(name = "car_t_s_type_line", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class CarTSTypeLineEntity extends IdEntity implements java.io.Serializable {
	
	/**车辆类型数据字典id*/
	private java.lang.String carTypeId;
	/**线路id*/
	private java.lang.String lineId;
	/**车辆类型区间价格*/
	private BigDecimal carTypePrice;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  车辆类型数据字典id
	 */
	@Column(name ="CAR_TYPE_ID",nullable=true,length=32)
	public java.lang.String getCarTypeId(){
		return this.carTypeId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  车辆类型数据字典id
	 */
	public void setCarTypeId(java.lang.String carTypeId){
		this.carTypeId = carTypeId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  线路id
	 */
	@Column(name ="LINE_ID",nullable=true,length=32)
	public java.lang.String getLineId(){
		return this.lineId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  线路id
	 */
	public void setLineId(java.lang.String lineId){
		this.lineId = lineId;
	}
	/**
	 *方法: 取得BigDecimal
	 *@return: BigDecimal  车辆类型区间价格
	 */
	@Column(name ="CAR_TYPE_PRICE",nullable=true,precision=6,scale=2)
	public BigDecimal getCarTypePrice(){
		return this.carTypePrice;
	}

	/**
	 *方法: 设置BigDecimal
	 *@param: BigDecimal  车辆类型区间价格
	 */
	public void setCarTypePrice(BigDecimal carTypePrice){
		this.carTypePrice = carTypePrice;
	}
}
