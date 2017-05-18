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
 * @Description: 业务开通城市
 * @author zhangdaihao
 * @date 2017-05-17 17:37:52
 * @version V1.0   
 *
 */
@Entity
@Table(name = "open_city", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class OpenCityEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**省名称*/
	private java.lang.String provinceName;
	/**城市名称*/
	private java.lang.String cityName;
	/**省id*/
	private java.lang.String provinceId;
	/**城市id*/
	private java.lang.String cityId;
	/**城市开通业务id*/
	private java.lang.String cityBusiness;
	/**城市开通状态*/
	private java.lang.String state;
	/**备注*/
	private java.lang.String remark;
	
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
	 *@return: java.lang.String  省名称
	 */
	@Column(name ="PROVINCE_NAME",nullable=true,length=20)
	public java.lang.String getProvinceName(){
		return this.provinceName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  省名称
	 */
	public void setProvinceName(java.lang.String provinceName){
		this.provinceName = provinceName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  城市名称
	 */
	@Column(name ="CITY_NAME",nullable=true,length=50)
	public java.lang.String getCityName(){
		return this.cityName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  城市名称
	 */
	public void setCityName(java.lang.String cityName){
		this.cityName = cityName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  省id
	 */
	@Column(name ="PROVINCE_ID",nullable=true,length=20)
	public java.lang.String getProvinceId(){
		return this.provinceId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  省id
	 */
	public void setProvinceId(java.lang.String provinceId){
		this.provinceId = provinceId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  城市id
	 */
	@Column(name ="CITY_ID",nullable=true,length=20)
	public java.lang.String getCityId(){
		return this.cityId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  城市id
	 */
	public void setCityId(java.lang.String cityId){
		this.cityId = cityId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  城市开通业务id
	 */
	@Column(name ="CITY_BUSINESS",nullable=true,length=20)
	public java.lang.String getCityBusiness(){
		return this.cityBusiness;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  城市开通业务id
	 */
	public void setCityBusiness(java.lang.String cityBusiness){
		this.cityBusiness = cityBusiness;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  城市开通状态
	 */
	@Column(name ="STATE",nullable=true,length=1)
	public java.lang.String getState(){
		return this.state;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  城市开通状态
	 */
	public void setState(java.lang.String state){
		this.state = state;
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
