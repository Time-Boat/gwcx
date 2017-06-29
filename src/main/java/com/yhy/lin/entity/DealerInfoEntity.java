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
 * @Description: 渠道商信息
 * @author zhangdaihao
 * @date 2017-06-29 17:51:19
 * @version V1.0   
 *
 */
@Entity
@Table(name = "dealer_info", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class DealerInfoEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**渠道商账号*/
	private java.lang.String account;
	/**创建时间*/
	private Date createDate;
	/**二维码地址*/
	private java.lang.String qrCodeUrl;
	/**被扫描次数*/
	private java.lang.String scanCount;
	/**联系电话*/
	private java.lang.String phone;
	/**负责人*/
	private java.lang.String manager;
	/**地址*/
	private java.lang.String position;
	/**银行账户*/
	private java.lang.String bankAccount;
	/**状态（备用字段）*/
	private String status;
	/**备注（备用字段）*/
	private String remark;
	
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
	 *@return: java.lang.String  渠道商账号
	 */
	@Column(name ="ACCOUNT",nullable=true,length=50)
	public java.lang.String getAccount(){
		return this.account;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  渠道商账号
	 */
	public void setAccount(java.lang.String account){
		this.account = account;
	}
	
	@Column(name="CREATE_DATE",nullable=true)
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  二维码地址
	 */
	@Column(name ="QR_CODE_URL",nullable=true,length=500)
	public java.lang.String getQrCodeUrl(){
		return this.qrCodeUrl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  二维码地址
	 */
	public void setQrCodeUrl(java.lang.String qrCodeUrl){
		this.qrCodeUrl = qrCodeUrl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  被扫描次数
	 */
	@Column(name ="SCAN_COUNT",nullable=true,length=10)
	public java.lang.String getScanCount(){
		return this.scanCount;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  被扫描次数
	 */
	public void setScanCount(java.lang.String scanCount){
		this.scanCount = scanCount;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系电话
	 */
	@Column(name ="PHONE",nullable=true,length=20)
	public java.lang.String getPhone(){
		return this.phone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系电话
	 */
	public void setPhone(java.lang.String phone){
		this.phone = phone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  负责人
	 */
	@Column(name ="MANAGER",nullable=true,length=20)
	public java.lang.String getManager(){
		return this.manager;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  负责人
	 */
	public void setManager(java.lang.String manager){
		this.manager = manager;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  地址
	 */
	@Column(name ="POSITION",nullable=true,length=50)
	public java.lang.String getPosition(){
		return this.position;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  地址
	 */
	public void setPosition(java.lang.String position){
		this.position = position;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  银行账户
	 */
	@Column(name ="BANK_ACCOUNT",nullable=true,length=50)
	public java.lang.String getBankAccount(){
		return this.bankAccount;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  银行账户
	 */
	public void setBankAccount(java.lang.String bankAccount){
		this.bankAccount = bankAccount;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  城市开通状态
	 */
	@Column(name ="STATUS",nullable=true,length=1)
	public java.lang.String getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  城市开通状态
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
