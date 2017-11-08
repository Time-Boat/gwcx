package com.yhy.lin.entity;

import org.jeecgframework.core.common.entity.IdEntity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 验票员信息表
 * @author linj
 *
 */
@Entity
@Table(name="conductor")
public class ConductorEntity extends IdEntity implements java.io.Serializable  {
	private static final long serialVersionUID = 1L;
	/**姓名*/
	private String name;
	/**性别*/
	private String sex;
	/**年龄*/
	private int age;
	/**电话*/
	private String phoneNumber;
	
	/**验票员业务类型    1:接送机业务    2:包车业务*/
	private String status;
	/**创建时间*/
	private Date createDate;
	/**逻辑删除0是未删除 1 是已删除*/
	private Short deleteFlag;// 状态: 0:不删除  1：删除
	//所属部门
	private String departId;
	
	//创建人
	private String createUserId;
	//申请状态
	private String applicationStatus;
	//拒绝原因
	private String refusalReason;
	//申请时间
	private Date applicationTime;
	//申请人
	private String applicationUserId;
	//申请内容
	private String applyContent;
	//审核人
	private String auditor;
	//审核时间
	private Date auditTime;
	//验票员状态
	private String conductStatus;
	
	@Column(name = "delete_flag")
	public Short getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Short deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
	@Column(name ="create_date",nullable=true)
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@Column(name="sex",nullable=true)
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name="age",nullable=true)
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	@Column(name ="name",nullable=true)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name ="phoneNumber",nullable=true)
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	@Column(name ="status",nullable=true)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	@Column(name = "conduct_status")
	public String getConductStatus() {
		return conductStatus;
	}
	public void setConductStatus(String conductStatus) {
		this.conductStatus = conductStatus;
	}
	
}
