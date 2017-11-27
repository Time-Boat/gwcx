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
 * @Description: 发送的系统消息记录
 * @author zhangdaihao
 * @date 2017-11-24 11:00:54
 * @version V1.0   
 *
 */
@Entity
@Table(name = "notification_record", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class NotificationRecordEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**真正发送的内容*/
	private java.lang.String realContent;
	/**发送时间*/
	private java.util.Date sendTime;
	/**通知模板id*/
	private java.lang.String modelId;
	/**（备用）*/
	private java.lang.String status;
	/**备注*/
	private java.lang.String remark;
	
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
	 *@return: java.lang.String  真正发送的内容
	 */
	@Column(name ="REAL_CONTENT",nullable=true,length=255)
	public java.lang.String getRealContent(){
		return this.realContent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  真正发送的内容
	 */
	public void setRealContent(java.lang.String realContent){
		this.realContent = realContent;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  发送时间
	 */
	@Column(name ="SEND_TIME",nullable=true)
	public java.util.Date getSendTime(){
		return this.sendTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  发送时间
	 */
	public void setSendTime(java.util.Date sendTime){
		this.sendTime = sendTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  通知模板id
	 */
	@Column(name ="MODEL_ID",nullable=true,length=32)
	public java.lang.String getModelId(){
		return this.modelId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  通知模板id
	 */
	public void setModelId(java.lang.String modelId){
		this.modelId = modelId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  （备用）
	 */
	@Column(name ="STATUS",nullable=true,length=1)
	public java.lang.String getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  （备用）
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
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
