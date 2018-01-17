package com.yhy.lin.app.entity;

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
 * @Description: 申诉订单详情
 * @author zhangdaihao
 * @date 2018-01-17 18:12:02
 * @version V1.0   
 *
 */
@Entity
@Table(name = "complaint_order_detail_view", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class ComplaintOrderDetailViewEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**起点站名称*/
	private java.lang.String orderStartingStationName;
	/**终点站名称（渠道商订单取的是用户自己选择点的名称）*/
	private java.lang.String orderTerminusStationName;
	/**订单编号*/
	private java.lang.String orderId;
	/**出发时间*/
	private java.lang.String orderStartime;
	/**人数乘单价 总价*/
	private BigDecimal orderTotalprice;
	/**申诉原因*/
	private java.lang.String complaintReason;
	/**申诉内容*/
	private java.lang.String complaintContent;
	/**申诉时间*/
	private java.util.Date complaintTime;
	/**处理意见   0：发起退款   1：拒绝退款*/
	private java.lang.String handleResult;
	/**处理内容*/
	private java.lang.String handleContent;
	/**mobilephone*/
	private java.lang.String mobilephone;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
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
	 *@param: java.lang.String  主键
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  起点站名称
	 */
	@Column(name ="ORDER_STARTING_STATION_NAME",nullable=true,length=255)
	public java.lang.String getOrderStartingStationName(){
		return this.orderStartingStationName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  起点站名称
	 */
	public void setOrderStartingStationName(java.lang.String orderStartingStationName){
		this.orderStartingStationName = orderStartingStationName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  终点站名称（渠道商订单取的是用户自己选择点的名称）
	 */
	@Column(name ="ORDER_TERMINUS_STATION_NAME",nullable=true,length=255)
	public java.lang.String getOrderTerminusStationName(){
		return this.orderTerminusStationName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  终点站名称（渠道商订单取的是用户自己选择点的名称）
	 */
	public void setOrderTerminusStationName(java.lang.String orderTerminusStationName){
		this.orderTerminusStationName = orderTerminusStationName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  订单编号
	 */
	@Column(name ="ORDER_ID",nullable=true,length=100)
	public java.lang.String getOrderId(){
		return this.orderId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单编号
	 */
	public void setOrderId(java.lang.String orderId){
		this.orderId = orderId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  出发时间
	 */
	@Column(name ="ORDER_STARTIME",nullable=true,length=30)
	public java.lang.String getOrderStartime(){
		return this.orderStartime;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  出发时间
	 */
	public void setOrderStartime(java.lang.String orderStartime){
		this.orderStartime = orderStartime;
	}
	/**
	 *方法: 取得BigDecimal
	 *@return: BigDecimal  人数乘单价 总价
	 */
	@Column(name ="ORDER_TOTALPRICE",nullable=true,precision=10,scale=2)
	public BigDecimal getOrderTotalprice(){
		return this.orderTotalprice;
	}

	/**
	 *方法: 设置BigDecimal
	 *@param: BigDecimal  人数乘单价 总价
	 */
	public void setOrderTotalprice(BigDecimal orderTotalprice){
		this.orderTotalprice = orderTotalprice;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  申诉原因
	 */
	@Column(name ="COMPLAINT_REASON",nullable=true,length=1)
	public java.lang.String getComplaintReason(){
		return this.complaintReason;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  申诉原因
	 */
	public void setComplaintReason(java.lang.String complaintReason){
		this.complaintReason = complaintReason;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  申诉内容
	 */
	@Column(name ="COMPLAINT_CONTENT",nullable=true,length=255)
	public java.lang.String getComplaintContent(){
		return this.complaintContent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  申诉内容
	 */
	public void setComplaintContent(java.lang.String complaintContent){
		this.complaintContent = complaintContent;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  申诉时间
	 */
	@Column(name ="COMPLAINT_TIME",nullable=true)
	public java.util.Date getComplaintTime(){
		return this.complaintTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  申诉时间
	 */
	public void setComplaintTime(java.util.Date complaintTime){
		this.complaintTime = complaintTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  处理意见   0：发起退款   1：拒绝退款
	 */
	@Column(name ="HANDLE_RESULT",nullable=true,length=1)
	public java.lang.String getHandleResult(){
		return this.handleResult;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  处理意见   0：发起退款   1：拒绝退款
	 */
	public void setHandleResult(java.lang.String handleResult){
		this.handleResult = handleResult;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  处理内容
	 */
	@Column(name ="HANDLE_CONTENT",nullable=true,length=255)
	public java.lang.String getHandleContent(){
		return this.handleContent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  处理内容
	 */
	public void setHandleContent(java.lang.String handleContent){
		this.handleContent = handleContent;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  mobilephone
	 */
	@Column(name ="MOBILEPHONE",nullable=true,length=30)
	public java.lang.String getMobilephone(){
		return this.mobilephone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  mobilephone
	 */
	public void setMobilephone(java.lang.String mobilephone){
		this.mobilephone = mobilephone;
	}
}
