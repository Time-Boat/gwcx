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
 * @Description: 线路排班模块
 * @author zhangdaihao
 * @date 2017-04-26 10:58:02
 * @version V1.0   
 *
 */
@Entity
@Table(name = "lineinfo_line_arrange_view", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class LineArrangeViewEntity implements java.io.Serializable {
	/**线路名称*/
	private java.lang.String name;
	/**主键id*/
	private java.lang.String lineid;
	/**线路创建人*/
	private java.lang.String director;
	/**排班id*/
	private java.lang.String id;
	/**发车时间*/
	private java.lang.String departDate;
	/**发车开始日期*/
	private java.util.Date startDate;
	/**发车停止日期*/
	private java.util.Date endDate;
	/**司机id*/
	private java.lang.String driverId;
	/**车牌id*/
	private java.lang.String licencePlateId;
	/**放票开始日期*/
	private java.sql.Date periodStart;
	/**放票结束日期*/
	private java.sql.Date periodEnd;
	/**备注（备用字段）*/
	private java.lang.String remark;
	/**车牌号*/
	private java.lang.String licencePlate;
	/**座位数*/
	private java.lang.String seat;
	/**车辆类型*/
	private java.lang.String carType;
	/**司机姓名*/
	private java.lang.String driverName;
	/**价格*/
	private java.math.BigDecimal ticketPrice;
	/**手机号码*/
	private java.lang.String phoneNumber;
	
	@Column(name ="PHONENUMBER",nullable=true,length=50)
	public java.lang.String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(java.lang.String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Column(name ="TICKET_PRICE",nullable=true,precision=19,scale=2)
	public java.math.BigDecimal getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(java.math.BigDecimal ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  线路名称
	 */
	@Column(name ="NAME",nullable=true,length=50)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  线路名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键id
	 */
	@Column(name ="LINEID",nullable=false,length=36)
	public java.lang.String getLineid(){
		return this.lineid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主键id
	 */
	public void setLineid(java.lang.String lineid){
		this.lineid = lineid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  线路创建人
	 */
	@Column(name ="DIRECTOR",nullable=true,length=100)
	public java.lang.String getDirector(){
		return this.director;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  线路创建人
	 */
	public void setDirector(java.lang.String director){
		this.director = director;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  排班id
	 */
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=true,length=36)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  排班id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发车时间
	 */
	@Column(name ="DEPART_DATE",nullable=true,length=50)
	public java.lang.String getDepartDate(){
		return this.departDate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发车时间
	 */
	public void setDepartDate(java.lang.String departDate){
		this.departDate = departDate;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  发车开始日期
	 */
	@Column(name ="START_DATE",nullable=true)
	public java.util.Date getStartDate(){
		return this.startDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  发车开始日期
	 */
	public void setStartDate(java.util.Date startDate){
		this.startDate = startDate;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  发车停止日期
	 */
	@Column(name ="END_DATE",nullable=true)
	public java.util.Date getEndDate(){
		return this.endDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  发车停止日期
	 */
	public void setEndDate(java.util.Date endDate){
		this.endDate = endDate;
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  车牌id
	 */
	@Column(name ="LICENCE_PLATE_ID",nullable=true,length=50)
	public java.lang.String getLicencePlateId(){
		return this.licencePlateId;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  车牌id
	 */
	public void setLicencePlateId(java.lang.String licencePlateId){
		this.licencePlateId = licencePlateId;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  放票开始日期
	 */
	@Column(name ="PERIOD_START",nullable=true)
	public java.sql.Date getPeriodStart(){
		return this.periodStart;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  放票开始日期
	 */
	public void setPeriodStart(java.sql.Date periodStart){
		this.periodStart = periodStart;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  放票结束日期
	 */
	@Column(name ="PERIOD_END",nullable=true)
	public java.sql.Date getPeriodEnd(){
		return this.periodEnd;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  放票结束日期
	 */
	public void setPeriodEnd(java.sql.Date periodEnd){
		this.periodEnd = periodEnd;
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
	 *@return: java.lang.String  座位数
	 */
	@Column(name ="SEAT",nullable=true,length=50)
	public java.lang.String getSeat(){
		return this.seat;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  座位数
	 */
	public void setSeat(java.lang.String seat){
		this.seat = seat;
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  司机姓名
	 */
	@Column(name ="DRIVER_NAME",nullable=true,length=50)
	public java.lang.String getDriverName(){
		return this.driverName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  司机姓名
	 */
	public void setDriverName(java.lang.String driverName){
		this.driverName = driverName;
	}
}
