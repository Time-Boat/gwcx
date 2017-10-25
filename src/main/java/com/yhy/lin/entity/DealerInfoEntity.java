package com.yhy.lin.entity;

import java.math.BigDecimal;
import java.util.Date;

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
	private java.lang.Integer scanCount;
	/**联系电话*/
	private java.lang.String phone;
	/**负责人*/
	private java.lang.String manager;
	/**地址*/
	private java.lang.String position;
	/**银行账户*/
	private java.lang.String bankAccount;
	/**状态      0：合作中    1：未合作    2：已终止 */
	private String status;
	/**备注（备用字段）*/
	private String remark;
	/**公司社会信用代码 */
	private String creditCode;
	/**创建用户 */
	private String createUserId;
	/**组织机构id */
	private String departId;
	
	/**审核人 */
	private String auditUser;
	/**审核时间 */
	private Date auditDate;
	/**审核状态 */
	private String auditStatus;
	/**提交申请时间 */
	private Date commitApplyDate;
	/**提交申请人 */
	private String commitApplyUser;
	/**申请类型 */
	private String applyType;
	/**拒绝原因 */
	private String rejectReason;
	/**复审审核状态   0：复审待审核     1：复审通过    2：复审未通过*/
	private String lastAuditStatus;
	/**复审人id*/
	private String lastAuditUser;
	/**复审时间*/
	private Date lastAuditDate;
	/**复审拒绝原因*/
	private String lastRejectReason;
	/**渠道商文件路径*/
	private String dealerFilePath;
	/**渠道商折扣*/
	private BigDecimal dealerDiscount;
	
	@Column(name ="DEALER_DISCOUNT",nullable=true,precision=2,scale=1)
	public BigDecimal getDealerDiscount() {
		return dealerDiscount;
	}

	public void setDealerDiscount(BigDecimal dealerDiscount) {
		this.dealerDiscount = dealerDiscount;
	}

	@Column(name ="DEALER_FILE_PATH",nullable=true,length=255)
	public String getDealerFilePath() {
		return dealerFilePath;
	}

	public void setDealerFilePath(String dealerFilePath) {
		this.dealerFilePath = dealerFilePath;
	}

	@Column(name ="LAST_AUDIT_STATUS",nullable=true,length=1)
	public String getLastAuditStatus() {
		return lastAuditStatus;
	}

	public void setLastAuditStatus(String lastAuditStatus) {
		this.lastAuditStatus = lastAuditStatus;
	}
	
	@Column(name ="LAST_AUDIT_USER",nullable=true,length=32)
	public String getLastAuditUser() {
		return lastAuditUser;
	}

	public void setLastAuditUser(String lastAuditUser) {
		this.lastAuditUser = lastAuditUser;
	}

	@Column(name ="LAST_AUDIT_DATE",nullable=true)
	public Date getLastAuditDate() {
		return lastAuditDate;
	}

	public void setLastAuditDate(Date lastAuditDate) {
		this.lastAuditDate = lastAuditDate;
	}

	@Column(name ="LAST_REJECT_REASON",nullable=true,length=500)
	public String getLastRejectReason() {
		return lastRejectReason;
	}

	public void setLastRejectReason(String lastRejectReason) {
		this.lastRejectReason = lastRejectReason;
	}

	@Column(name ="REJECT_REASON",nullable=true,length=500)
	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	@Column(name ="APPLY_TYPE",nullable=true,length=1)
	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	@Column(name ="COMMIT_APPLY_USER",nullable=true,length=32)
	public String getCommitApplyUser() {
		return commitApplyUser;
	}

	public void setCommitApplyUser(String commitApplyUser) {
		this.commitApplyUser = commitApplyUser;
	}

	@Column(name ="COMMIT_APPLY_DATE",nullable=true)
	public Date getCommitApplyDate() {
		return commitApplyDate;
	}

	public void setCommitApplyDate(Date commitApplyDate) {
		this.commitApplyDate = commitApplyDate;
	}

	@Column(name ="AUDIT_USER",nullable=true,length=32)
	public String getAuditUser() {
		return auditUser;
	}

	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}

	@Column(name ="AUDIT_DATE",nullable=true)
	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}
	
	@Column(name ="AUDIT_STATUS",nullable=true,length=2)
	public String getAuditStatus() {
		return auditStatus;
	}
	
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	
	@Column(name ="DEPARTID",nullable=true,length=32)
	public String getDepartId() {
		return departId;
	}
	
	public void setDepartId(String departId) {
		this.departId = departId;
	}
	
	@Column(name ="CREATE_USER_ID",nullable=true,length=32)
	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  被扫描次数
	 */
	@Column(name ="SCAN_COUNT",nullable=true,precision=10,scale=0)
	public java.lang.Integer getScanCount(){
		return this.scanCount;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  被扫描次数
	 */
	public void setScanCount(java.lang.Integer scanCount){
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

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  公司社会信用代码
	 */
	@Column(name ="credit_code",nullable=true,length=32)
	public String getCreditCode() {
		return creditCode;
	}

	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
	}
	
}
