package com.yhy.lin.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.jeecgframework.core.common.entity.IdEntity;

@Entity
@Table(name = "transferorder")
public class TransferorderEntity extends IdEntity implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String orderId;// 订单号
	private Integer orderType;// 订单类型 0:接机 1:送机 2:接火车 3:送火车
	private Integer orderStatus; //订单状态 0：订单已完成。1：已付款待审核。2：审核通过待发车3：取消订单待退款。4：取消订单完成退款。5：拒绝退款。6：未支付
	private String orderFlightnumber;// 航班号
	private String orderStartingStationId;// 起点站
	private String orderTerminusStationId;// 终点站
	private String orderStartime;// 发车时间
	private Date orderExpectedarrival;// 预计到达时间
	private BigDecimal orderUnitprice;// 单价
	private String orderNumbers;// 车票数量
	
	private String orderPaytype;// 支付方式0：微信 1：支付宝 2：银联
	private String orderContactsname;// 联系人
	private String orderContactsmobile;// 联系人手机号
	private String orderPaystatus;// 支付状态 0：已付款，1：退款中 2：已退款 3:未付款
	private String orderTrainnumber;// 火车车次
//	private Integer orderNumberPeople;// 人数
	private BigDecimal orderTotalPrice;// 总价

	private Date applicationTime;// 下达订单时间

	private String lineId;
	private String lineName;

	private String orderTerminusStationName;// 终点名称
	private String orderStartingStationName;// 起点名称

	private String userId; //用户id
	
	private String cityName; //城市名称
	private String cityId; //城市id
	
	private String refundTime; //申请退款时间
	
	private String rejectReason; //拒绝原因
	
	private String beforeStatus;  //申请退款之前的订单状态
	
	private String orderPayNumber;  //微信支付订单号
	
	private Date refundCompletedTime;   //退款完成时间
	
	private Date orderCompletedTime;   //订单完成时间
	
	private String refundPrice;    //退款金额
	
	private String lineOrderCode;//线路订单码
	
	private String firstAuditStatus;//初审审核状态   0：初审待审核     1：初审通过    2：初审未通过
	
	private String lastAuditStatus;//复审审核状态   0：复审待审核     1：复审通过    2：复审未通过
	
	private String firstAuditUser;//初审人id
	
	private String lastAuditUser;//复审人id
	
	private Date firstAuditDate;//初审时间
	
	private Date lastAuditDate;//复审时间
	
	private String orderHistory;//历史订单（就是该订单绑定的运营专员已经被注销了）   0：否    1：是
	
	private String remark;//订单备注
	
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "order_history")
	public String getOrderHistory() {
		return orderHistory;
	}

	public void setOrderHistory(String orderHistory) {
		this.orderHistory = orderHistory;
	}

	@Column(name = "first_audit_status")
	public String getFirstAuditStatus() {
		return firstAuditStatus;
	}

	public void setFirstAuditStatus(String firstAuditStatus) {
		this.firstAuditStatus = firstAuditStatus;
	}

	@Column(name = "last_audit_status")
	public String getLastAuditStatus() {
		return lastAuditStatus;
	}

	public void setLastAuditStatus(String lastAuditStatus) {
		this.lastAuditStatus = lastAuditStatus;
	}

	@Column(name = "first_audit_user")
	public String getFirstAuditUser() {
		return firstAuditUser;
	}

	public void setFirstAuditUser(String firstAuditUser) {
		this.firstAuditUser = firstAuditUser;
	}
	
	@Column(name = "last_audit_user")
	public String getLastAuditUser() {
		return lastAuditUser;
	}

	public void setLastAuditUser(String lastAuditUser) {
		this.lastAuditUser = lastAuditUser;
	}
	
	@Column(name = "first_audit_date")
	public Date getFirstAuditDate() {
		return firstAuditDate;
	}

	public void setFirstAuditDate(Date firstAuditDate) {
		this.firstAuditDate = firstAuditDate;
	}

	@Column(name = "last_audit_date")
	public Date getLastAuditDate() {
		return lastAuditDate;
	}

	public void setLastAuditDate(Date lastAuditDate) {
		this.lastAuditDate = lastAuditDate;
	}

	@Column(name = "refund_completed_time")
	public Date getRefundCompletedTime() {
		return refundCompletedTime;
	}

	public void setRefundCompletedTime(Date refundCompletedTime) {
		this.refundCompletedTime = refundCompletedTime;
	}
	
	@Column(name = "order_completed_time")
	public Date getOrderCompletedTime() {
		return orderCompletedTime;
	}

	public void setOrderCompletedTime(Date orderCompletedTime) {
		this.orderCompletedTime = orderCompletedTime;
	}

	@Column(name = "refund_price")
	public String getRefundPrice() {
		return refundPrice;
	}

	public void setRefundPrice(String refundPrice) {
		this.refundPrice = refundPrice;
	}

	@Column(name = "order_pay_number")
	public String getOrderPayNumber() {
		return orderPayNumber;
	}

	public void setOrderPayNumber(String orderPayNumber) {
		this.orderPayNumber = orderPayNumber;
	}

	@Column(name = "before_status")
	public String getBeforeStatus() {
		return beforeStatus;
	}

	public void setBeforeStatus(String beforeStatus) {
		this.beforeStatus = beforeStatus;
	}

	@Column(name = "reject_reason")
	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	@Column(name = "refund_time")
	public String getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(String refundTime) {
		this.refundTime = refundTime;
	}

	@Column(name = "city_name")
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	@Column(name = "city_id")
	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	@Column(name = "user_id")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "order_terminus_station_name")
	public String getOrderTerminusStationName() {
		return orderTerminusStationName;
	}

	public void setOrderTerminusStationName(String orderTerminusStationName) {
		this.orderTerminusStationName = orderTerminusStationName;
	}

	@Column(name = "order_starting_station_name")
	public String getOrderStartingStationName() {
		return orderStartingStationName;
	}

	public void setOrderStartingStationName(String orderStartingStationName) {
		this.orderStartingStationName = orderStartingStationName;
	}

	@Column(name = "order_id")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Column(name = "order_type")
	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	@Column(name = "order_status")
	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Column(name = "order_flightnumber")
	public String getOrderFlightnumber() {
		return orderFlightnumber;
	}

	public void setOrderFlightnumber(String orderFlightnumber) {
		this.orderFlightnumber = orderFlightnumber;
	}

	@Column(name = "order_starting_station_id")
	public String getOrderStartingStationId() {
		return orderStartingStationId;
	}

	public void setOrderStartingStationId(String orderStartingStationId) {
		this.orderStartingStationId = orderStartingStationId;
	}

	@Column(name = "order_terminus_station_id")
	public String getOrderTerminusStationId() {
		return orderTerminusStationId;
	}

	public void setOrderTerminusStationId(String orderTerminusStationId) {
		this.orderTerminusStationId = orderTerminusStationId;
	}

	@Column(name = "order_startime")
	public String getOrderStartime() {
		return orderStartime;
	}

	public void setOrderStartime(String orderStartime) {
		this.orderStartime = orderStartime;
	}

	@Column(name = "order_expectedarrival")
	public Date getOrderExpectedarrival() {
		return orderExpectedarrival;
	}

	public void setOrderExpectedarrival(Date orderExpectedarrival) {
		this.orderExpectedarrival = orderExpectedarrival;
	}

	@Column(name = "order_unitprice")
	public BigDecimal getOrderUnitprice() {
		return orderUnitprice;
	}

	public void setOrderUnitprice(BigDecimal orderUnitprice) {
		this.orderUnitprice = orderUnitprice;
	}

	@Column(name = "order_numbers")
	public String getOrderNumbers() {
		return orderNumbers;
	}

	public void setOrderNumbers(String orderNumbers) {
		this.orderNumbers = orderNumbers;
	}

	@Column(name = "order_paytype")
	public String getOrderPaytype() {
		return orderPaytype;
	}

	public void setOrderPaytype(String orderPaytype) {
		this.orderPaytype = orderPaytype;
	}

	@Column(name = "order_contactsname")
	public String getOrderContactsname() {
		return orderContactsname;
	}

	public void setOrderContactsname(String orderContactsname) {
		this.orderContactsname = orderContactsname;
	}

	@Column(name = "order_contactsmobile")
	public String getOrderContactsmobile() {
		return orderContactsmobile;
	}

	public void setOrderContactsmobile(String orderContactsmobile) {
		this.orderContactsmobile = orderContactsmobile;
	}

	@Column(name = "order_paystatus")
	public String getOrderPaystatus() {
		return orderPaystatus;
	}

	public void setOrderPaystatus(String orderPaystatus) {
		this.orderPaystatus = orderPaystatus;
	}

	@Column(name = "order_trainnumber")
	public String getOrderTrainnumber() {
		return orderTrainnumber;
	}

	public void setOrderTrainnumber(String orderTrainnumber) {
		this.orderTrainnumber = orderTrainnumber;
	}

//	@Column(name = "order_numberPeople")
//	public Integer getOrderNumberPeople() {
//		return orderNumberPeople;
//	}
//
//	public void setOrderNumberPeople(Integer orderNumberPeople) {
//		this.orderNumberPeople = orderNumberPeople;
//	}

	@Column(name = "order_totalPrice")
	public BigDecimal getOrderTotalPrice() {
		return orderTotalPrice;
	}

	public void setOrderTotalPrice(BigDecimal orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}

	@Column(name = "line_id")
	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	@Column(name = "line_name")
	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	@Column(name = "applicationTime")
	public Date getApplicationTime() {
		return applicationTime;
	}

	public void setApplicationTime(Date applicationTime) {
		this.applicationTime = applicationTime;
	}

	@Column(name = "lineOrderCode")
	public String getLineOrderCode() {
		return lineOrderCode;
	}

	public void setLineOrderCode(String lineOrderCode) {
		this.lineOrderCode = lineOrderCode;
	}
}
