package com.yhy.lin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jeecgframework.core.common.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 包车定价和附加服务中间表
 * @author zhangdaihao
 * @date 2017-10-20 16:27:35
 * @version V1.0   
 *
 */
@Entity
@Table(name = "chartered_price_append_service", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class CharteredPriceAppendServiceEntity extends IdEntity implements java.io.Serializable {
	
	/**服务项id*/
	private java.lang.String appendServiceId;
	/**包车价格表id*/
	private java.lang.String charteredPriceId;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  服务项id
	 */
	@Column(name ="APPEND_SERVICE_ID",nullable=true,length=32)
	public java.lang.String getAppendServiceId(){
		return this.appendServiceId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  服务项id
	 */
	public void setAppendServiceId(java.lang.String appendServiceId){
		this.appendServiceId = appendServiceId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  包车价格表id
	 */
	@Column(name ="CHARTERED_PRICE_ID",nullable=true,length=32)
	public java.lang.String getCharteredPriceId(){
		return this.charteredPriceId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  包车价格表id
	 */
	public void setCharteredPriceId(java.lang.String charteredPriceId){
		this.charteredPriceId = charteredPriceId;
	}
}
