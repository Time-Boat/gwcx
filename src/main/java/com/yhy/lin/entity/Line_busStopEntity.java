package com.yhy.lin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.jeecgframework.core.common.entity.IdEntity;

@Entity
@Table(name="line_busstop")
public class Line_busStopEntity extends IdEntity implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
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
