package com.yhy.lin.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.jeecgframework.core.common.entity.IdEntity;

/**
 * 订单和 线路、车辆、司机的中间表
 * @author HSAEE
 *
 */
@Entity
@Table(name="order_linecardiver")
public class Order_LineCarDiverEntity  implements Serializable {
	private String id;//id即为订单id
	private String departureTime;//发车时刻
	private String startTime;//开始时间
	private String endTime;//结束时间
    private String driverId;//司机id
	private String licencePlateId;//车牌id
    private String lineId;//线路id 
    //private String orderId;//订单id
	private String remark;//备注
	
	@Id
	@Column(name ="id",nullable=false,length=32)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name = "departureTime")
	public String getDepartureTime(){
		return departureTime;
	}
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	@Column(name = "startTime")
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	@Column(name = "endTime")
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	@Column(name = "driverId")
	public String getDriverId() {
		return driverId;
	}
	public void setDriverId(String driverId) {
		this.driverId = driverId;
	}
	@Column(name = "licencePlateId")
	public String getLicencePlateId() {
		return licencePlateId;
	}
	public void setLicencePlateId(String licencePlateId) {
		this.licencePlateId = licencePlateId;
	}
	@Column(name = "lineId")
	public String getLineId() {
		return lineId;
	}
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/*@Column(name = "orderId")
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}*/
	
	
	
}
