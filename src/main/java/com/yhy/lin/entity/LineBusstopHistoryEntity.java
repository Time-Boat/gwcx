package com.yhy.lin.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.core.common.entity.IdEntity;

import javax.persistence.SequenceGenerator;

/**   
 * @Title: Entity
 * @Description: 线路和站点关联历史记录表
 * @author zhangdaihao
 * @date 2017-10-31 10:40:15
 * @version V1.0   
 *
 */
@Entity
@Table(name = "line_busstop_history", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class LineBusstopHistoryEntity extends IdEntity implements java.io.Serializable {
	
	/**版本号*/
	private java.lang.String version;
	
	/**线路id*/
	private String lineId;
	/**站点id*/
	private String busStopsId;
	/**站点顺序*/
	private Integer siteOrder =0;
	/**预计到站时间*/
	private String arrivalTime;
	/**机场或火车站点id*/
	private String siteId;
	
	@Column(name = "version")
	public java.lang.String getVersion() {
		return version;
	}
	public void setVersion(java.lang.String version) {
		this.version = version;
	}
	@Column(name = "lineId")
	public String getLineId() {
		return lineId;
	}
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	@Column(name = "busStopsId")
	public String getBusStopsId() {
		return busStopsId;
	}
	public void setBusStopsId(String busStopsId) {
		this.busStopsId = busStopsId;
	}
	@Column(name = "siteOrder")
	public Integer getSiteOrder() {
		return siteOrder;
	}
	public void setSiteOrder(Integer siteOrder) {
		this.siteOrder = siteOrder;
	}
	@Column(name = "arrivalTime")
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	@Column(name = "siteId")
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
}
