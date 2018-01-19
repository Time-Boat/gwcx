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
import org.jeecgframework.core.common.entity.IdEntity;

import javax.persistence.SequenceGenerator;

/**   
 * @Title: Entity
 * @Description: 订单申诉表
 * @author zhangdaihao
 * @date 2018-01-17 10:05:38
 * @version V1.0   
 *
 */
@Entity
@Table(name = "complaint_order", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class ComplaintOrderEntity extends IdEntity implements java.io.Serializable {
	
	/**订单id*/
	private String transferId;
	/**申诉原因:0,未收到派车信息，1：订单无法退款，2：未按时发车，3：其他*/
	private String complaintReason;
	/**申诉内容*/
	private String complaintContent;
	/**申诉时间*/
	private Date complaintTime;
	/**处理意见   0：发起退款   1：拒绝退款*/
	private String handleResult;
	/**处理内容*/
	private String handleContent;
	/**备注*/
	private String remark;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  订单id
	 */
	@Column(name ="TRANSFER_ID",nullable=true,length=32)
	public java.lang.String getTransferId(){
		return this.transferId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单id
	 */
	public void setTransferId(java.lang.String transferId){
		this.transferId = transferId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  申诉原因:0,未收到派车信息，1：订单无法退款，2：未按时发车，3：其他
	 */
	@Column(name ="COMPLAINT_REASON",nullable=true,length=1)
	public java.lang.String getComplaintReason(){
		return this.complaintReason;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  申诉原因:0,未收到派车信息，1：订单无法退款，2：未按时发车，3：其他
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
	 *@return: java.lang.String  备注
	 */
	@Column(name ="REMARK",nullable=true,length=255)
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
