package com.yhy.lin.entity;

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
 * @Description: 公司员工入驻信息
 * @author zhangdaihao
 * @date 2017-04-17 17:30:59
 * @version V1.0   
 *
 */
@Entity
@Table(name = "staff_info", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class StaffInfoEntity implements java.io.Serializable {
	/**员工id*/
	private java.lang.String id;
	/**员工名称*/
	private java.lang.String name;
	/**性别*/
	private java.lang.String sex;
	/**年龄*/
	private java.lang.String age;
	/**员工所属公司ID*/
	private java.lang.String companyId;
	/**员工所属部门*/
	private java.lang.String depart;
	/**员工职位*/
	private java.lang.String staffPosition;
	/**用于逻辑删除 0正常  1已删除*/
	private short deleteFlag;
	/**状态（备用字段）*/
	private java.lang.String status;
	/**备注（备用字段）*/
	private java.lang.String remark;
	
	/**手机号*/
	private java.lang.String phone;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  员工手机号
	 */
	@Column(name ="PHONE",nullable=true,length=20)
	public java.lang.String getPhone(){
		return this.phone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  员工手机号
	 */
	public void setPhone(java.lang.String phone){
		this.phone = phone;
	}
	
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  员工id
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
	 *@param: java.lang.String  员工id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  员工名称
	 */
	@Column(name ="NAME",nullable=true,length=50)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  员工名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	
	@Column(name ="SEX",nullable=true,length=1)
	public java.lang.String getSex() {
		return sex;
	}

	public void setSex(java.lang.String sex) {
		this.sex = sex;
	}
	
	@Column(name ="AGE",nullable=true,length=10)
	public java.lang.String getAge() {
		return age;
	}

	public void setAge(java.lang.String age) {
		this.age = age;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  员工所属公司
	 */
	@Column(name ="COMPANY_ID",nullable=true,length=100)
	public java.lang.String getCompanyId(){
		return this.companyId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  员工所属公司
	 */
	public void setCompanyId(java.lang.String companyId){
		this.companyId = companyId;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  员工所属部门
	 */
	@Column(name ="DEPART",nullable=true,length=50)
	public java.lang.String getDepart(){
		return this.depart;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  员工所属部门
	 */
	public void setDepart(java.lang.String depart){
		this.depart = depart;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  员工职位
	 */
	@Column(name ="STAFF_POSITION",nullable=true,length=100)
	public java.lang.String getStaffPosition(){
		return this.staffPosition;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  员工职位
	 */
	public void setStaffPosition(java.lang.String staffPosition){
		this.staffPosition = staffPosition;
	}
	/**
	 *方法: 取得java.lang.Short
	 *@return: java.lang.Short  用于逻辑删除 0正常  1已删除
	 */
	@Column(name ="DELETE_FLAG",nullable=true,precision=5,scale=0)
	public short getDeleteFlag(){
		return this.deleteFlag;
	}

	/**
	 *方法: 设置java.lang.Short
	 *@param: java.lang.Short  用于逻辑删除 0正常  1已删除
	 */
	public void setDeleteFlag(short deleteFlag){
		this.deleteFlag = deleteFlag;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  状态（备用字段）
	 */
	@Column(name ="STATUS",nullable=true,length=255)
	public java.lang.String getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  状态（备用字段）
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注（备用字段）
	 */
	@Column(name ="REMARK",nullable=true,length=1000)
	public java.lang.String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注（备用字段）
	 */
	public void setRemark(java.lang.String remark){
		this.remark = remark;
	}
}
