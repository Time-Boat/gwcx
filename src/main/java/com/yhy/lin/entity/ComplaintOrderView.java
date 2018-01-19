package com.yhy.lin.entity;

import java.util.Date;

public class ComplaintOrderView implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String id;//主键
	private String transferId;//订单id
	private String orderId;//订单编号
	private String orderStatus;//订单状态  0：订单已完成。1：已付款待审核。2：审核通过待发车3：取消订单待退款。4：取消订单完成退款。5：拒绝退款。6：未支付
	private String lineName;//线路名称
	private String startLocation;//起点站
	private String endLocation;//终点站名称
	private Date applicationTime;//下单时间
	private Date  orderStartime;//发车时间
	private String  orderTotalPrice;//订单金额
	private String orderUserType;//用户类型
	private Date complaintTime;//申诉时间
	private String  orderContactsname;//申诉人
	private String  orderContactsmobile;//申诉人手机号
	
	private String complaintReason;//申诉原因
	private String complaintContent;//申诉内容
	private String handleResult;//处理意见
	private Date handleTime;//处理时间
	private String handleContent;//处理详情
	private String reamek;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTransferId() {
		return transferId;
	}
	public void setTransferId(String transferId) {
		this.transferId = transferId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public String getStartLocation() {
		return startLocation;
	}
	public void setStartLocation(String startLocation) {
		this.startLocation = startLocation;
	}
	public String getEndLocation() {
		return endLocation;
	}
	public void setEndLocation(String endLocation) {
		this.endLocation = endLocation;
	}
	public Date getApplicationTime() {
		return applicationTime;
	}
	public void setApplicationTime(Date applicationTime) {
		this.applicationTime = applicationTime;
	}
	public Date getOrderStartime() {
		return orderStartime;
	}
	public void setOrderStartime(Date orderStartime) {
		this.orderStartime = orderStartime;
	}
	public String getOrderTotalPrice() {
		return orderTotalPrice;
	}
	public void setOrderTotalPrice(String orderTotalPrice) {
		this.orderTotalPrice = orderTotalPrice;
	}
	public String getOrderUserType() {
		return orderUserType;
	}
	public void setOrderUserType(String orderUserType) {
		this.orderUserType = orderUserType;
	}
	public Date getComplaintTime() {
		return complaintTime;
	}
	public void setComplaintTime(Date complaintTime) {
		this.complaintTime = complaintTime;
	}
	public String getOrderContactsname() {
		return orderContactsname;
	}
	public void setOrderContactsname(String orderContactsname) {
		this.orderContactsname = orderContactsname;
	}
	public String getOrderContactsmobile() {
		return orderContactsmobile;
	}
	public void setOrderContactsmobile(String orderContactsmobile) {
		this.orderContactsmobile = orderContactsmobile;
	}
	public String getComplaintReason() {
		return complaintReason;
	}
	public void setComplaintReason(String complaintReason) {
		this.complaintReason = complaintReason;
	}
	public String getComplaintContent() {
		return complaintContent;
	}
	public void setComplaintContent(String complaintContent) {
		this.complaintContent = complaintContent;
	}
	public String getHandleResult() {
		return handleResult;
	}
	public void setHandleResult(String handleResult) {
		this.handleResult = handleResult;
	}
	public String getHandleContent() {
		return handleContent;
	}
	public void setHandleContent(String handleContent) {
		this.handleContent = handleContent;
	}
	public String getReamek() {
		return reamek;
	}
	public void setReamek(String reamek) {
		this.reamek = reamek;
	}
	public Date getHandleTime() {
		return handleTime;
	}
	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}
}
