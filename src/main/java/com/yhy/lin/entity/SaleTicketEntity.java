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
 * @Description: 售票订单表
 * @author zhangdaihao
 * @date 2017-04-27 16:14:35
 * @version V1.0   
 *
 */
@Entity
@Table(name = "sale_ticket", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class SaleTicketEntity implements java.io.Serializable {
	/**售票信息id*/
	private java.lang.String id;
	/**线路id*/
	private java.lang.String lineId;
	/**售票日期*/
	private java.sql.Date ticketDate;
	/**用户id*/
	private java.lang.String userId;
	/**订单状态*/
	private java.lang.String status;
	/**备注（备用字段）*/
	private java.lang.String remark;
	/**订单类型*/
	private java.lang.String ticketType;
	/**订单实际支付价格*/
	private java.math.BigDecimal realTicketPrice;
	
	
	@Column(name ="TICKET_TYPE",nullable=true,length=50)
	public java.lang.String getTicketType() {
		return ticketType;
	}

	public void setTicketType(java.lang.String ticketType) {
		this.ticketType = ticketType;
	}

	@Column(name ="REAL_TICKET_PRICE",nullable=true,length=50)
	public java.math.BigDecimal getRealTicketPrice() {
		return realTicketPrice;
	}

	public void setRealTicketPrice(java.math.BigDecimal realTicketPrice) {
		this.realTicketPrice = realTicketPrice;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  售票信息id
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
	 *@param: java.lang.String  售票信息id
	 */
	public void setId(java.lang.String id){
		this.id = id;
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
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  售票日期
	 */
	@Column(name ="TICKET_DATE",nullable=true)
	public java.sql.Date getTicketDate(){
		return this.ticketDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  售票日期
	 */
	public void setTicketDate(java.sql.Date ticketDate){
		this.ticketDate = ticketDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户id
	 */
	@Column(name ="USER_ID",nullable=true,length=36)
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
	 *@return: java.lang.String  订单状态
	 */
	@Column(name ="STATUS",nullable=true,length=1)
	public java.lang.String getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单状态
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
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
