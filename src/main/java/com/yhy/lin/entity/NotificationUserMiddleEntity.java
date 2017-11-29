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
 * @Description: 系统消息通知中间表
 * @author zhangdaihao
 * @date 2017-11-29 17:37:04
 * @version V1.0   
 *
 */
@Entity
@Table(name = "notification_user_middle", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class NotificationUserMiddleEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**通知记录id*/
	private java.lang.String recordId;
	/**用户id*/
	private java.lang.String userId;
	/**接收时间*/
	private java.util.Date receiveTime;
	
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
	 *@return: java.lang.String  通知记录id
	 */
	@Column(name ="RECORD_ID",nullable=true,length=32)
	public java.lang.String getRecordId(){
		return this.recordId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  通知记录id
	 */
	public void setRecordId(java.lang.String recordId){
		this.recordId = recordId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户id
	 */
	@Column(name ="USER_ID",nullable=true,length=32)
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
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  接收时间
	 */
	@Column(name ="RECEIVE_TIME",nullable=true)
	public java.util.Date getReceiveTime(){
		return this.receiveTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  接收时间
	 */
	public void setReceiveTime(java.util.Date receiveTime){
		this.receiveTime = receiveTime;
	}
}
