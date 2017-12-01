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
 * @Description: 系统消息发送记录
 * @author zhangdaihao
 * @date 2017-11-28 10:39:33
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
	/**通知标题*/
	private java.lang.String title;
	/**通知内容*/
	private java.lang.String content;
	/**通知目标*/
	private java.lang.String target;
	/**发送时间*/
	private java.util.Date sendTime;
	/**通知方式   1：邮件   2：站内信  */
	private java.lang.String nType;
	/**用户id*/
	private java.lang.String createUserId;
	/**创建时间*/
	private java.util.Date createTime;
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
	 *@return: java.lang.String  通知标题
	 */
	@Column(name ="TITLE",nullable=true,length=32)
	public java.lang.String getTitle(){
		return this.title;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  通知标题
	 */
	public void setTitle(java.lang.String title){
		this.title = title;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  通知内容
	 */
	@Column(name ="CONTENT",nullable=true,length=255)
	public java.lang.String getContent(){
		return this.content;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  通知内容
	 */
	public void setContent(java.lang.String content){
		this.content = content;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  通知目标
	 */
	@Column(name ="TARGET",nullable=true,length=255)
	public java.lang.String getTarget(){
		return this.target;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  通知目标
	 */
	public void setTarget(java.lang.String target){
		this.target = target;
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
	 *@return: java.lang.String  通知方式   1：邮件   2：短信   3：站内信   4：消息中心
	 */
	@Column(name ="N_TYPE",nullable=true,length=10)
	public java.lang.String getNType(){
		return this.nType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  通知方式   1：邮件   2：短信   3：站内信   4：消息中心
	 */
	public void setNType(java.lang.String nType){
		this.nType = nType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户id
	 */
	@Column(name ="CREATE_USER_ID",nullable=true,length=32)
	public java.lang.String getCreateUserId(){
		return this.createUserId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户id
	 */
	public void setCreateUserId(java.lang.String createUserId){
		this.createUserId = createUserId;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="CREATE_TIME",nullable=true)
	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
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
