package com.yhy.lin.app.entity;

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
 * @Description: 用户消息提醒列表
 * @author zhangdaihao
 * @date 2017-05-25 10:11:45
 * @version V1.0   
 *
 */
@Entity
@Table(name = "customer_message", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class AppMessageListEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**用户id*/
	private java.lang.String userId;
	/**消息内容*/
	private java.lang.String content;
	/**创建时间*/
	private java.lang.String createTime;
	/**消息是否被查看过 0：否   1：是*/
	private java.lang.String status;
	/** 订单id */
	private java.lang.String orderId;
	/** 消息类型   0：订单通知    1：普通消息通知   */
	private java.lang.String msgType;
	
	private String title;
	
	@Column(name ="ORDER_ID",nullable=true,length=32)
	public java.lang.String getOrderId() {
		return orderId;
	}

	public void setOrderId(java.lang.String orderId) {
		this.orderId = orderId;
	}
	
	@Column(name ="MSG_TYPE",nullable=true,length=1)
	public java.lang.String getMsgType() {
		return msgType;
	}

	public void setMsgType(java.lang.String msgType) {
		this.msgType = msgType;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
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
	 *@param: java.lang.String  id
	 */
	public void setId(java.lang.String id){
		this.id = id;
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  消息内容
	 */
	@Column(name ="CONTENT",nullable=true,length=300)
	public java.lang.String getContent(){
		return this.content;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  消息内容
	 */
	public void setContent(java.lang.String content){
		this.content = content;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建时间
	 */
	@Column(name ="CREATE_TIME",nullable=true,length=20)
	public java.lang.String getCreateTime(){
		return this.createTime;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建时间
	 */
	public void setCreateTime(java.lang.String createTime){
		this.createTime = createTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  消息是否被查看过 0：否   1：是
	 */
	@Column(name ="STATUS",nullable=true,length=1)
	public java.lang.String getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  消息是否被查看过 0：否   1：是
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
	}

	@Column(name ="title",nullable=true,length=100)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}
