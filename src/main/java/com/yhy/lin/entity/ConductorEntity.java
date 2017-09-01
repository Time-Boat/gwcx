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
	/**验票员权限*/
	private String jurisdiction;
	/**验票员业务类型*/
	private String status;
	/**创建时间*/
	private Date createDate;
	/**逻辑删除0是未删除 1 是已删除*/
	private Short deleteFlag;// 状态: 0:不删除  1：删除
	//所属部门
	private String departId;
	
	private String lineIds;
	
	//创建人
	private String createUserId;
	
	@Column(name = "line_ids")
	public String getLineIds() {
		return lineIds;
	}
	public void setLineIds(String lineIds) {
		this.lineIds = lineIds;
	}
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
	
	@Column(name ="jurisdiction",nullable=true)
	public String getJurisdiction() {
		return jurisdiction;
	}
	public void setJurisdiction(String jurisdiction) {
		this.jurisdiction = jurisdiction;
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
	
}
