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
 * @Description: 车辆信息
 * @author zhangdaihao
 * @date 2017-04-22 13:37:21
 * @version V1.0   
 *
 */
@Entity
@Table(name = "car_info", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class CarInfoEntity implements java.io.Serializable {
	/**车辆id*/
	private java.lang.String id;
	/**车牌号*/
	private java.lang.String licencePlate;
	/**车辆类型*/
	private java.lang.String carType;
	/**座位数*/
	private java.lang.String seat;
	/**停放位置*/
	private java.lang.String stopPosition;
	/**司机id*/
	private java.lang.String driverId;
	/**用于逻辑删除 0正常  1已删除*/
	private java.lang.Integer deleteFlag;
	/**车辆状态*/
	private java.lang.String status;
	/**备注*/
	private java.lang.String remark;
	
	/**业务类型，公务车路线还是接送机路线   0:公务车路线 1：接送机路线*/
	private java.lang.String businessType;
	/**经度*/
	private java.lang.String stopY;
	/**纬度*/
	private java.lang.String stopX;
	
	@Column(name ="BUSINESS_TYPE",nullable=true,length=1)
	public java.lang.String getBusinessType() {
		return businessType;
	}

	public void setBusinessType(java.lang.String businessType) {
		this.businessType = businessType;
	}

	@Column(name ="STOP_Y",nullable=true,length=20)
	public java.lang.String getStopY() {
		return stopY;
	}

	public void setStopY(java.lang.String stopY) {
		this.stopY = stopY;
	}

	@Column(name ="STOP_X",nullable=true,length=20)
	public java.lang.String getStopX() {
		return stopX;
	}

	public void setStopX(java.lang.String stopX) {
		this.stopX = stopX;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  车辆id
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
	 *@param: java.lang.String  车辆id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  车牌号
	 */
	@Column(name ="LICENCE_PLATE",nullable=true,length=20)
	public java.lang.String getLicencePlate(){
		return this.licencePlate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  车牌号
	 */
	public void setLicencePlate(java.lang.String licencePlate){
		this.licencePlate = licencePlate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  车辆类型
	 */
	@Column(name ="CAR_TYPE",nullable=true,length=50)
	public java.lang.String getCarType(){
		return this.carType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  车辆类型
	 */
	public void setCarType(java.lang.String carType){
		this.carType = carType;
	}
	/**
	 *方法: 取得java.util.String
	 *@return: java.util.String  座位数
	 */
	@Column(name ="SEAT",nullable=true,length=50)
	public java.lang.String getSeat(){
		return this.seat;
	}

	/**
	 *方法: 设置java.util.String
	 *@param: java.util.String  座位数
	 */
	public void setSeat(java.lang.String seat){
		this.seat = seat;
	}
	/**
	 *方法: 取得java.util.String
	 *@return: java.util.String  停放位置
	 */
	@Column(name ="STOP_POSITION",nullable=true,length=255)
	public java.lang.String getStopPosition(){
		return this.stopPosition;
	}

	/**
	 *方法: 设置java.util.String
	 *@param: java.util.String  停放位置
	 */
	public void setStopPosition(java.lang.String stopPosition){
		this.stopPosition = stopPosition;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  司机id
	 */
	@Column(name ="DRIVER_ID",nullable=true,length=50)
	public java.lang.String getDriverId(){
		return this.driverId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  司机id
	 */
	public void setDriverId(java.lang.String driverId){
		this.driverId = driverId;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  用于逻辑删除 0正常  1已删除
	 */
	@Column(name ="DELETE_FLAG",nullable=true,precision=5,scale=0)
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
	 *@return: java.lang.String  车辆状态
	 */
	@Column(name ="STATUS",nullable=true,length=20)
	public java.lang.String getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  车辆状态
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
