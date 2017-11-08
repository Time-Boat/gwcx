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
 * @Description: 验票员和线路的中间表
 * @author zhangdaihao
 * @date 2017-11-08 16:53:33
 * @version V1.0   
 *
 */
@Entity
@Table(name = "conductor_line_info", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class ConductorLineInfoEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**验票员id*/
	private java.lang.String conductorId;
	/**线路id*/
	private java.lang.String lineId;
	
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
	 *@return: java.lang.String  验票员id
	 */
	@Column(name ="CONDUCTOR_ID",nullable=true,length=32)
	public java.lang.String getConductorId(){
		return this.conductorId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  验票员id
	 */
	public void setConductorId(java.lang.String conductorId){
		this.conductorId = conductorId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  线路id
	 */
	@Column(name ="LINE_ID",nullable=true,length=32)
	public java.lang.String getLineId(){
		return this.lineId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  线路id
	 */
	public void setLineId(java.lang.String lineId){
		this.lineId = lineId;
	}
}
