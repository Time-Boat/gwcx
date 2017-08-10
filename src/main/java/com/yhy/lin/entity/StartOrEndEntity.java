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
 * @Description: 起点和终点关联表
 * @author zhangdaihao
 * @date 2017-08-08 16:22:36
 * @version V1.0   
 *
 */
@Entity
@Table(name = "start_end", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class StartOrEndEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**机场或者火车站点*/
	private java.lang.String startid;
	/**普通站点*/
	private java.lang.String endid;
	/**线路类型*/
	private java.lang.String linetype;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
	 */
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=50)
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
	 *@return: java.lang.String  机场或者火车站点
	 */
	@Column(name ="STARTID",nullable=true,length=50)
	public java.lang.String getStartid(){
		return this.startid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  机场或者火车站点
	 */
	public void setStartid(java.lang.String startid){
		this.startid = startid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  普通站点
	 */
	@Column(name ="ENDID",nullable=true,length=50)
	public java.lang.String getEndid(){
		return this.endid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  普通站点
	 */
	public void setEndid(java.lang.String endid){
		this.endid = endid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  线路类型
	 */
	@Column(name ="LINETYPE",nullable=true,length=1)
	public java.lang.String getLinetype(){
		return this.linetype;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  线路类型
	 */
	public void setLinetype(java.lang.String linetype){
		this.linetype = linetype;
	}
}
