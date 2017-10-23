package com.yhy.lin.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jeecgframework.core.common.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 包车价格设置
 * @author zhangdaihao
 * @date 2017-10-18 17:24:05
 * @version V1.0   
 *
 */
@Entity
@Table(name = "chartered_price", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class CharteredPriceEntity extends IdEntity implements java.io.Serializable {
	
	/**车辆类型*/
	private String carType;
	/**套餐id*/
	private String packageId;
	/**起步价（元）*/
	private BigDecimal initiatePrice;
	/**超公里/元*/
	private BigDecimal exceedKmPrice;
	/**超时长/元*/
	private BigDecimal exceedTimePrice;
	/**所在城市*/
	private String cityId;
	/**空反费（公里/元）*/
	private BigDecimal emptyReturn;
	/**用户id*/
	private String createUserId;
	/**创建时间*/
	private Date createTime;
	/**修改时间*/
	private Date midifyTime;
	/**修改用户*/
	private String modifyUser;
	/**用于逻辑删除 0正常 1已删除*/
	private int deleteFlag;
	/**是否上架 0：未上架 1：已上架*/
	private String status;
	/**备注*/
	private String remark;
	/**提交申请人*/
	private String applyUser;
	/**提交申请时间*/
	private Date applyDate;
	/**申请类型   0：启用     1：停用*/
	private String applyType;
	/**审核人*/
	private String auditUser;
	/**审核时间*/
	private Date auditDate;
	/**审核状态   -1：未提交申请    0：待审核     1：审核通过    2：审核未通过*/
	private String auditStatus;
	/**拒绝原因*/
	private String rejectReason;
	/**复审用户*/
	private String lastAuditUser;
	/**复审时间*/
	private Date lastAuditDate;
	/**复审拒绝原因*/
	private String lastRejectReason;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  车辆类型
	 */
	@Column(name ="CAR_TYPE",nullable=true,length=2)
	public String getCarType(){
		return this.carType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  车辆类型
	 */
	public void setCarType(String carType){
		this.carType = carType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  套餐id
	 */
	@Column(name ="PACKAGE_ID",nullable=true,length=32)
	public String getPackageId(){
		return this.packageId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  套餐id
	 */
	public void setPackageId(String packageId){
		this.packageId = packageId;
	}
	/**
	 *方法: 取得BigDecimal
	 *@return: BigDecimal  起步价（元）
	 */
	@Column(name ="INITIATE_PRICE",nullable=true,precision=6,scale=2)
	public BigDecimal getInitiatePrice(){
		return this.initiatePrice;
	}

	/**
	 *方法: 设置BigDecimal
	 *@param: BigDecimal  起步价（元）
	 */
	public void setInitiatePrice(BigDecimal initiatePrice){
		this.initiatePrice = initiatePrice;
	}
	/**
	 *方法: 取得BigDecimal
	 *@return: BigDecimal  超公里/元
	 */
	@Column(name ="EXCEED_KM_PRICE",nullable=true,precision=5,scale=2)
	public BigDecimal getExceedKmPrice(){
		return this.exceedKmPrice;
	}

	/**
	 *方法: 设置BigDecimal
	 *@param: BigDecimal  超公里/元
	 */
	public void setExceedKmPrice(BigDecimal exceedKmPrice){
		this.exceedKmPrice = exceedKmPrice;
	}
	/**
	 *方法: 取得BigDecimal
	 *@return: BigDecimal  超时长/元
	 */
	@Column(name ="EXCEED_TIME_PRICE",nullable=true,precision=5,scale=2)
	public BigDecimal getExceedTimePrice(){
		return this.exceedTimePrice;
	}

	/**
	 *方法: 设置BigDecimal
	 *@param: BigDecimal  超时长/元
	 */
	public void setExceedTimePrice(BigDecimal exceedTimePrice){
		this.exceedTimePrice = exceedTimePrice;
	}
	/**
	 *方法: 取得String
	 *@return: String  所在城市
	 */
	@Column(name ="CITY_ID",nullable=false,length=20)
	public String getCityId(){
		return this.cityId;
	}

	/**
	 *方法: 设置String
	 *@param: String  所在城市
	 */
	public void setCityId(String cityId){
		this.cityId = cityId;
	}
	/**
	 *方法: 取得BigDecimal
	 *@return: BigDecimal  空反费（公里/元）
	 */
	@Column(name ="EMPTY_RETURN",nullable=true,precision=5,scale=2)
	public BigDecimal getEmptyReturn(){
		return this.emptyReturn;
	}

	/**
	 *方法: 设置BigDecimal
	 *@param: BigDecimal  空反费（公里/元）
	 */
	public void setEmptyReturn(BigDecimal emptyReturn){
		this.emptyReturn = emptyReturn;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户id
	 */
	@Column(name ="CREATE_USER_ID",nullable=true,length=32)
	public String getCreateUserId(){
		return this.createUserId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户id
	 */
	public void setCreateUserId(String createUserId){
		this.createUserId = createUserId;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="CREATE_TIME",nullable=true)
	public Date getCreateTime(){
		return this.createTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreateTime(Date createTime){
		this.createTime = createTime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  修改时间
	 */
	@Column(name ="MIDIFY_TIME",nullable=true)
	public Date getMidifyTime(){
		return this.midifyTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  修改时间
	 */
	public void setMidifyTime(Date midifyTime){
		this.midifyTime = midifyTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  修改用户
	 */
	@Column(name ="MODIFY_USER",nullable=true,length=32)
	public String getModifyUser(){
		return this.modifyUser;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  修改用户
	 */
	public void setModifyUser(String modifyUser){
		this.modifyUser = modifyUser;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  用于逻辑删除 0正常 1已删除
	 */
	@Column(name ="DELETE_FLAG",nullable=true,precision=3,scale=0)
	public int getDeleteFlag(){
		return this.deleteFlag;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  用于逻辑删除 0正常 1已删除
	 */
	public void setDeleteFlag(int deleteFlag){
		this.deleteFlag = deleteFlag;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否上架 0：未上架 1：已上架
	 */
	@Column(name ="STATUS",nullable=true,length=1)
	public String getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否上架 0：未上架 1：已上架
	 */
	public void setStatus(String status){
		this.status = status;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="REMARK",nullable=true,length=500)
	public String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  提交申请人
	 */
	@Column(name ="APPLY_USER",nullable=true,length=32)
	public String getApplyUser(){
		return this.applyUser;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  提交申请人
	 */
	public void setApplyUser(String applyUser){
		this.applyUser = applyUser;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  提交申请时间
	 */
	@Column(name ="APPLY_DATE",nullable=true)
	public Date getApplyDate(){
		return this.applyDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  提交申请时间
	 */
	public void setApplyDate(Date applyDate){
		this.applyDate = applyDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  申请类型   0：启用     1：停用
	 */
	@Column(name ="APPLY_TYPE",nullable=true,length=1)
	public String getApplyType(){
		return this.applyType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  申请类型   0：启用     1：停用
	 */
	public void setApplyType(String applyType){
		this.applyType = applyType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  审核人
	 */
	@Column(name ="AUDIT_USER",nullable=true,length=32)
	public String getAuditUser(){
		return this.auditUser;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  审核人
	 */
	public void setAuditUser(String auditUser){
		this.auditUser = auditUser;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  审核时间
	 */
	@Column(name ="AUDIT_DATE",nullable=true)
	public Date getAuditDate(){
		return this.auditDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  审核时间
	 */
	public void setAuditDate(Date auditDate){
		this.auditDate = auditDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  审核状态   -1：未提交申请    0：待审核     1：审核通过    2：审核未通过
	 */
	@Column(name ="AUDIT_STATUS",nullable=true,length=2)
	public String getAuditStatus(){
		return this.auditStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  审核状态   -1：未提交申请    0：待审核     1：审核通过    2：审核未通过
	 */
	public void setAuditStatus(String auditStatus){
		this.auditStatus = auditStatus;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  拒绝原因
	 */
	@Column(name ="REJECT_REASON",nullable=true,length=500)
	public String getRejectReason(){
		return this.rejectReason;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  拒绝原因
	 */
	public void setRejectReason(String rejectReason){
		this.rejectReason = rejectReason;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  复审用户
	 */
	@Column(name ="LAST_AUDIT_USER",nullable=true,length=32)
	public String getLastAuditUser(){
		return this.lastAuditUser;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  复审用户
	 */
	public void setLastAuditUser(String lastAuditUser){
		this.lastAuditUser = lastAuditUser;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  复审时间
	 */
	@Column(name ="LAST_AUDIT_DATE",nullable=true)
	public Date getLastAuditDate(){
		return this.lastAuditDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  复审时间
	 */
	public void setLastAuditDate(Date lastAuditDate){
		this.lastAuditDate = lastAuditDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  复审拒绝原因
	 */
	@Column(name ="LAST_REJECT_REASON",nullable=true,length=500)
	public String getLastRejectReason(){
		return this.lastRejectReason;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  复审拒绝原因
	 */
	public void setLastRejectReason(String lastRejectReason){
		this.lastRejectReason = lastRejectReason;
	}
}
