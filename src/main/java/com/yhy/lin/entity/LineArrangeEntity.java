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
 * @Description: 线路排班
 * @author zhangdaihao
 * @date 2017-04-21 18:00:03
 * @version V1.0   
 *
 */
@Entity
@Table(name = "line_arrange", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class LineArrangeEntity implements java.io.Serializable {
	/**排班id*/
	private java.lang.String id;
	/**发车时间*/
	private java.lang.String departDate;
	/**线路排班状态*/
	private java.lang.String arrangeStatus;
	/**发车开始日期*/
	private java.util.Date startDate;
	/**发车停止日期*/
	private java.util.Date endDate;
	/**司机id*/
	private java.lang.String driverId;
	/**车牌id*/
	private java.lang.String licencePlateId;
	/**排班负责人*/
	private java.lang.String director;
	/**放票开始日期*/
	private java.sql.Date periodStart;
	/**放票结束日期*/
	private java.sql.Date periodEnd;
	/**线路id*/
	private java.lang.String lineId;
	/**用于逻辑删除 0正常  1已删除*/
	private java.lang.Integer deleteFlag;
	/**备注（备用字段）*/
	private java.lang.String remark;
	/**价格*/
	private java.math.BigDecimal ticketPrice;
	
	@Column(name ="TICKET_PRICE",nullable=true,precision=19,scale=2)
	public java.math.BigDecimal getTicketPrice() {
		return ticketPrice;
	}
	public void setTicketPrice(java.math.BigDecimal ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  排班id
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
	 *@param: java.lang.String  排班id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	
	/**
	 *方法: 取得java.sql.Date
	 *@return: java.sql.Date  放票开始日期
	 */
	@Column(name ="PERIOD_START",nullable=true)
	public java.sql.Date getPeriodStart(){
		return this.periodStart;
	}

	/**
	 *方法: 设置java.sql.Date
	 *@param: java.sql.Date  放票开始日期
	 */
	public void setPeriodStart(java.sql.Date periodStart){
		this.periodStart = periodStart;
	}
	/**
	 *方法: 取得java.sql.Date
	 *@return: java.sql.Date  放票结束日期
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
	 *@return: java.lang.String  排班负责人
	 */
	@Column(name ="DIRECTOR",nullable=true,length=1)
	public java.lang.String getDirector() {
		return director;
	}
	
	public void setDirector(java.lang.String director) {
		this.director = director;
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  线路排班状态
	 */
	@Column(name ="ARRANGE_STATUS",nullable=true,length=1)
	public java.lang.String getArrangeStatus(){
		return this.arrangeStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  线路排班状态
	 */
	public void setArrangeStatus(java.lang.String arrangeStatus){
		this.arrangeStatus = arrangeStatus;
	}
	/**
	 *方法: 取得java.sql.Date
	 *@return: java.sql.Date  发车开始日期
	 */
	@Column(name ="START_DATE",nullable=true)
	public java.util.Date getStartDate(){
		return this.startDate;
	}

	/**
	 *方法: 设置java.sql.Date
	 *@param: java.sql.Date  发车开始日期
	 */
	public void setStartDate(java.util.Date startDate){
		this.startDate = startDate;
	}
	/**
	 *方法: 取得java.sql.Date
	 *@return: java.sql.Date  发车停止日期
	 */
	@Column(name ="END_DATE",nullable=true)
	public java.util.Date getEndDate(){
		return this.endDate;
	}

	/**
	 *方法: 设置java.sql.Date
	 *@param: java.sql.Date  发车停止日期
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
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  车牌id
	 */
	public void setLicencePlateId(java.lang.String licencePlateId){
		this.licencePlateId = licencePlateId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  线路id
	 */
	@Column(name ="LINE_ID",nullable=true,length=50)
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
