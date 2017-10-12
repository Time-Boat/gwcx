package com.yhy.lin.entity;

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
 * @Description: 司机信息表
 * @author zhangdaihao
 * @date 2017-04-22 01:24:32
 * @version V1.0   
 *
 */
@Entity
@Table(name = "driversinfo", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class DriversInfoEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**司机姓名*/
	private String name;
	/**驾照类型*/
	private String drivingLicense;
	/**司机电话*/
	private String phoneNumber;
	/**身份证*/
	private String idCard;
	/**身份证照片*/
	private String idCardImgUrl;
	/**性别  0男  1女*/
	private String sex;
	/**年龄*/
	private Integer age;
	/**创建时间*/
	private Date createDate;
	/**用于逻辑删除 0正常  1已删除*/
	private Integer deleteFlag;
	/**司机状态  0:未启用，1：已启用*/
	private String status;
	/**备注（备用字段）*/
	private String remark;
	/**驾照图片（后期考虑进去）*/
	private String drivingLicenseImgUrl;
	//所属部门
	private String departId;
	//创建人
	private String createUserId;
	//审核状态
	private String applicationStatus;
	//拒绝原因
	private String refusalReason;
	//申请时间
	private Date applicationTime;
	//提交申请人
	private String applicationUserId;
	//申请内容
	private String applyContent;
	//审核人
	private String auditor;
	//审核时间
	private Date auditTime;
	
	/**
	 * 所在城市
	 * @return
	 */
	private String cityId;

	// 主键生成策略：uuid 采用128位的uuid算法生成主键，uuid被编码为一个32位16进制数字的字符串。占用空间大（字符串类型）。
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "id", nullable=false,length=32)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name="name",nullable=true,length=50)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="phoneNumber",nullable=true,length=50)
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	@Column(name="idCard",nullable=true,length=18)
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	@Column(name="idCardImgUrl",nullable=true,length=100)
	public String getIdCardImgUrl() {
		return idCardImgUrl;
	}
	public void setIdCardImgUrl(String idCardImgUrl) {
		this.idCardImgUrl = idCardImgUrl;
	}
	@Column(name="sex",nullable=true,length=10)
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	@Column(name="age",nullable=true)
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	@Column(name="createDate",nullable=true)
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Column(name="deleteFlag",nullable=true)
	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	@Column(name="status",nullable=true)
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name="remark",nullable=true)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name="drivingLicenseImgUrl",nullable=true)
	public String getDrivingLicenseImgUrl() {
		return drivingLicenseImgUrl;
	}
	public void setDrivingLicenseImgUrl(String drivingLicenseImgUrl) {
		this.drivingLicenseImgUrl = drivingLicenseImgUrl;
	}
	@Column(name="driving_license",nullable=true)
	public String getDrivingLicense() {
		return drivingLicense;
	}
	public void setDrivingLicense(String drivingLicense) {
		this.drivingLicense = drivingLicense;
	}
	@Column(name="cityId",nullable=true)
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	@Column(name = "departId")
	public String getDepartId() {
		return departId;
	}
	public void setDepartId(String departId) {
		this.departId = departId;
	}
	
	@Column(name = "create_user_id")
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	
	@Column(name = "application_status")
	public String getApplicationStatus() {
		return applicationStatus;
	}
	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
	
	@Column(name = "refusal_reason")
	public String getRefusalReason() {
		return refusalReason;
	}
	public void setRefusalReason(String refusalReason) {
		this.refusalReason = refusalReason;
	}
	
	@Column(name = "application_time")
	public Date getApplicationTime() {
		return applicationTime;
	}
	public void setApplicationTime(Date applicationTime) {
		this.applicationTime = applicationTime;
	}
	
	@Column(name = "application_user_id")
	public String getApplicationUserId() {
		return applicationUserId;
	}
	public void setApplicationUserId(String applicationUserId) {
		this.applicationUserId = applicationUserId;
	}
	
	@Column(name = "apply_content")
	public String getApplyContent() {
		return applyContent;
	}
	public void setApplyContent(String applyContent) {
		this.applyContent = applyContent;
	}
	
	@Column(name = "auditor")
	public String getAuditor() {
		return auditor;
	}
	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}
	@Column(name = "audit_time")
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	
}
