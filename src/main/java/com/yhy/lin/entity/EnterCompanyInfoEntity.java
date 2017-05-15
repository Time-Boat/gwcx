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
 * @Description: 入驻公司信息
 * @author zhangdaihao
 * @date 2017-04-18 17:26:36
 * @version V1.0   
 *
 */
@Entity
@Table(name = "enter_company_info", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class EnterCompanyInfoEntity implements java.io.Serializable {
	/**公司id*/
	private java.lang.String id;
	/**负责人*/
	private java.lang.String director;
	/**负责人手机号*/
	private java.lang.String directorPhone;
	/**入驻时间*/
	private java.util.Date createDate;
	/**公司地址*/
	private java.lang.String address;
	/**关联组织机构ID*/
	private java.lang.String departid;
	/**用于逻辑删除 0正常  1已删除*/
	private java.lang.Integer deleteFlag;
	/**状态（备用字段）*/
	private java.lang.String status;
	/**备注（备用字段）*/
	private java.lang.String remark;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  公司id
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
	 *@param: java.lang.String  公司id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  负责人
	 */
	@Column(name ="DIRECTOR",nullable=true,length=50)
	public java.lang.String getDirector(){
		return this.director;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  负责人
	 */
	public void setDirector(java.lang.String director){
		this.director = director;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  负责人手机号
	 */
	@Column(name ="DIRECTOR_PHONE",nullable=true,length=50)
	public java.lang.String getDirectorPhone(){
		return this.directorPhone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  负责人手机号
	 */
	public void setDirectorPhone(java.lang.String directorPhone){
		this.directorPhone = directorPhone;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  入驻时间
	 */
	@Column(name ="CREATE_DATE",nullable=true)
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  入驻时间
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.util.String
	 *@return: java.util.String  公司地址
	 */
	@Column(name ="ADDRESS",nullable=true,length=255)
	public java.lang.String getAddress(){
		return this.address;
	}

	/**
	 *方法: 设置java.util.String
	 *@param: java.util.String  公司地址
	 */
	public void setAddress(java.lang.String address){
		this.address = address;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  关联组织机构ID
	 */
	@Column(name ="DEPARTID",nullable=true,length=50)
	public java.lang.String getDepartid(){
		return this.departid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  关联组织机构ID
	 */
	public void setDepartid(java.lang.String departid){
		this.departid = departid;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  用于逻辑删除 0正常  1已删除
	 */
	@Column(name ="DELETE_FLAG",nullable=true,precision=5,scale=0)
	public java.lang.Integer getDeleteFlag(){
		return this.deleteFlag;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  用于逻辑删除 0正常  1已删除
	 */
	public void setDeleteFlag(java.lang.Integer deleteFlag){
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
