package com.yhy.lin.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.jeecgframework.core.common.entity.IdEntity;

/**   
 * @Title: Entity
 * @Description: 包车订单
 * @author zhangdaihao
 * @date 2017-11-23 11:48:37
 * @version V1.0   
 *
 */
@Entity
@Table(name = "chartered_order", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class CharteredOrderEntity extends IdEntity implements java.io.Serializable {
	
	/**订单id*/
	private String orderId;
	/**订单状态 0：订单已完成。1：待派车。2：待出行。3：取消订单待退款。4：取消订单完成退款。5：拒绝退款。6：未支付。7：已审核。*/
	private Integer orderStatus;
	/**起点站*/
	private String startingStationName;
	/**终点站*/
	private String terminusStationName;
	/**出发时间*/
	private String orderStartime;
	/**包车定价id*/
	private String charteredPriceId;
	/**支付方式：微信 1：支付宝 2：银联*/
	private String orderPaytype;
	/**联系人*/
	private String orderContactsname;
	/**联系人手机号*/
	private String orderContactsmobile;
	/**起点站 X*/
	private String startingStationX;
	/**终点站  Y*/
	private String terminusStationY;
	/**支付状态 0：已付款，1：退款中 2：已退款 3：未付款 4：已拒绝*/
	private String orderPaystatus;
	/**总价*/
	private BigDecimal orderTotalprice;
	/**订单的实际里程*/
	private String orderActualMileage;
	/**包车类型  0：单程   1：往返*/
	private String orderType;
	/**用户订单类型    0：普通用户订单    1：渠道商用户订单*/
	private  String orderUserType;
	/**订单备注*/
	private String remark;
	/**下达订单时间*/
	private java.util.Date applicationtime;
	/**客户id*/
	private String userId;
	/**申请退款时间*/
	private Date refundTime;
	/**拒绝退款原因*/
	private String rejectReason;
	/**微信商户单号*/
	private String orderPayNumber;
	/**退款金额*/
	private BigDecimal refundPrice;
	/**退款时间*/
	private Date refundCompletedTime;
	/**订单完成时间*/
	private Date orderCompletedTime;
	/**是否已经被删除   0：未删除    1：删除*/
	private String deleteFlag;
	/**线路订单码*/
	private String lineordercode;
	/**初审审核状态   0：初审待审核     1：初审通过    2：初审未通过*/
	private String firstAuditStatus;
	/**复审审核状态   0：复审待审核     1：复审通过    2：复审未通过*/
	private String lastAuditStatus;
	/**初审人id*/
	private String firstAuditUser;
	/**复审人id*/
	private String lastAuditUser;
	/**初审时间*/
	private Date firstAuditDate;
	/**复审时间*/
	private Date lastAuditDate;
	/**历史订单（就是该订单绑定的运营专员已经被注销了）    0：否    1：是*/
	private String orderHistory;
	
	/**
	 *方法: 取得 String
	 *@return:  String  订单id
	 */
	@Column(name ="ORDER_ID",nullable=true,length=100)
	public String getOrderId(){
		return this.orderId;
	}

	/**
	 *方法: 设置 String
	 *@param:  String  订单id
	 */
	public void setOrderId(String orderId){
		this.orderId = orderId;
	}
	/**
	 *方法: 取得 Integer
	 *@return:  Integer  订单状态 0：订单已完成。1：待派车。2：待出行。3：取消订单待退款。4：取消订单完成退款。5：拒绝退款。6：未支付。7：已审核。
	 */
	@Column(name ="ORDER_STATUS",nullable=true,precision=10,scale=0)
	public Integer getOrderStatus(){
		return this.orderStatus;
	}

	/**
	 *方法: 设置 Integer
	 *@param:  Integer  订单状态 0：订单已完成。1：待派车。2：待出行。3：取消订单待退款。4：取消订单完成退款。5：拒绝退款。6：未支付。7：已审核。
	 */
	public void setOrderStatus(Integer orderStatus){
		this.orderStatus = orderStatus;
	}
	/**
	 *方法: 取得 String
	 *@return:  String  起点站
	 */
	@Column(name ="STARTING_STATION_NAME",nullable=true,length=100)
	public String getStartingStationName(){
		return this.startingStationName;
	}

	/**
	 *方法: 设置 String
	 *@param:  String  起点站
	 */
	public void setStartingStationName(String startingStationName){
		this.startingStationName = startingStationName;
	}
	/**
	 *方法: 取得 String
	 *@return:  String  终点站
	 */
	@Column(name ="TERMINUS_STATION_NAME",nullable=true,length=100)
	public String getTerminusStationName(){
		return this.terminusStationName;
	}

	/**
	 *方法: 设置 String
	 *@param:  String  终点站
	 */
	public void setTerminusStationName(String terminusStationName){
		this.terminusStationName = terminusStationName;
	}
	/**
	 *方法: 取得 String
	 *@return:  String  出发时间
	 */
	@Column(name ="ORDER_STARTIME",nullable=true,length=30)
	public String getOrderStartime(){
		return this.orderStartime;
	}

	/**
	 *方法: 设置 String
	 *@param:  String  出发时间
	 */
	public void setOrderStartime(String orderStartime){
		this.orderStartime = orderStartime;
	}
	/**
	 *方法: 取得 String
	 *@return:  String  包车定价id
	 */
	@Column(name ="CHARTERED_PRICE_ID",nullable=true,length=100)
	public String getCharteredPriceId(){
		return this.charteredPriceId;
	}

	/**
	 *方法: 设置 String
	 *@param:  String  包车定价id
	 */
	public void setCharteredPriceId(String charteredPriceId){
		this.charteredPriceId = charteredPriceId;
	}
	/**
	 *方法: 取得 String
	 *@return:  String  支付方式：微信 1：支付宝 2：银联
	 */
	@Column(name ="ORDER_PAYTYPE",nullable=true,length=1)
	public String getOrderPaytype(){
		return this.orderPaytype;
	}

	/**
	 *方法: 设置 String
	 *@param:  String  支付方式：微信 1：支付宝 2：银联
	 */
	public void setOrderPaytype(String orderPaytype){
		this.orderPaytype = orderPaytype;
	}
	/**
	 *方法: 取得 String
	 *@return:  String  联系人
	 */
	@Column(name ="ORDER_CONTACTSNAME",nullable=true,length=100)
	public String getOrderContactsname(){
		return this.orderContactsname;
	}

	/**
	 *方法: 设置 String
	 *@param:  String  联系人
	 */
	public void setOrderContactsname(String orderContactsname){
		this.orderContactsname = orderContactsname;
	}
	/**
	 *方法: 取得 String
	 *@return:  String  联系人手机号
	 */
	@Column(name ="ORDER_CONTACTSMOBILE",nullable=true,length=11)
	public String getOrderContactsmobile(){
		return this.orderContactsmobile;
	}

	/**
	 *方法: 设置 String
	 *@param:  String  联系人手机号
	 */
	public void setOrderContactsmobile(String orderContactsmobile){
		this.orderContactsmobile = orderContactsmobile;
	}
	/**
	 *方法: 取得 String
	 *@return:  String  起点站 X
	 */
	@Column(name ="STARTING_STATION_X",nullable=true,length=100)
	public String getStartingStationX(){
		return this.startingStationX;
	}

	/**
	 *方法: 设置 String
	 *@param:  String  起点站 X
	 */
	public void setStartingStationX(String startingStationX){
		this.startingStationX = startingStationX;
	}
	/**
	 *方法: 取得 String
	 *@return:  String  终点站  Y
	 */
	@Column(name ="TERMINUS_STATION_Y",nullable=true,length=100)
	public String getTerminusStationY(){
		return this.terminusStationY;
	}

	/**
	 *方法: 设置 String
	 *@param:  String  终点站  Y
	 */
	public void setTerminusStationY(String terminusStationY){
		this.terminusStationY = terminusStationY;
	}
	/**
	 *方法: 取得 String
	 *@return:  String  支付状态 0：已付款，1：退款中 2：已退款 3：未付款 4：已拒绝
	 */
	@Column(name ="ORDER_PAYSTATUS",nullable=true,length=1)
	public String getOrderPaystatus(){
		return this.orderPaystatus;
	}

	/**
	 *方法: 设置 String
	 *@param:  String  支付状态 0：已付款，1：退款中 2：已退款 3：未付款 4：已拒绝
	 */
	public void setOrderPaystatus(String orderPaystatus){
		this.orderPaystatus = orderPaystatus;
	}
	/**
	 *方法: 取得BigDecimal
	 *@return: BigDecimal  总价
	 */
	@Column(name ="ORDER_TOTALPRICE",nullable=true,precision=10,scale=2)
	public BigDecimal getOrderTotalprice(){
		return this.orderTotalprice;
	}

	/**
	 *方法: 设置BigDecimal
	 *@param: BigDecimal  总价
	 */
	public void setOrderTotalprice(BigDecimal orderTotalprice){
		this.orderTotalprice = orderTotalprice;
	}
	/**
	 *方法: 取得 String
	 *@return:  String  订单的实际里程
	 */
	@Column(name ="ORDER_ACTUAL_MILEAGE",nullable=true,length=10)
	public String getOrderActualMileage(){
		return this.orderActualMileage;
	}

	/**
	 *方法: 设置 String
	 *@param:  String  订单的实际里程
	 */
	public void setOrderActualMileage(String orderActualMileage){
		this.orderActualMileage = orderActualMileage;
	}
	/**
	 *方法: 取得 String
	 *@return:  String  包车类型  0：单程   1：往返
	 */
	@Column(name ="ORDER_TYPE",nullable=true,length=1)
	public String getOrderType(){
		return this.orderType;
	}

	/**
	 *方法: 设置 String
	 *@param:  String  包车类型  0：单程   1：往返
	 */
	public void setOrderType(String orderType){
		this.orderType = orderType;
	}
	/**
	 *方法: 取得 String
	 *@return:  String  用户订单类型    0：普通用户订单    1：渠道商用户订单
	 */
	@Column(name ="ORDER_USER_TYPE",nullable=true,length=1)
	public String getOrderUserType(){
		return this.orderUserType;
	}

	/**
	 *方法: 设置 String
	 *@param:  String  用户订单类型    0：普通用户订单    1：渠道商用户订单
	 */
	public void setOrderUserType(String orderUserType){
		this.orderUserType = orderUserType;
	}
	/**
	 *方法: 取得 String
	 *@return:  String  订单备注
	 */
	@Column(name ="REMARK",nullable=true,length=500)
	public  String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置 String
	 *@param:  String  订单备注
	 */
	public void setRemark(String remark){
		this.remark = remark;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  下达订单时间
	 */
	@Column(name ="APPLICATIONTIME",nullable=true)
	public Date getApplicationtime(){
		return this.applicationtime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  下达订单时间
	 */
	public void setApplicationtime(Date applicationtime){
		this.applicationtime = applicationtime;
	}
	/**
	 *方法: 取得 String
	 *@return:  String  客户id
	 */
	@Column(name ="USER_ID",nullable=true,length=32)
	public String getUserId(){
		return this.userId;
	}

	/**
	 *方法: 设置 String
	 *@param:  String  客户id
	 */
	public void setUserId(String userId){
		this.userId = userId;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  申请退款时间
	 */
	@Column(name ="REFUND_TIME",nullable=true)
	public Date getRefundTime(){
		return this.refundTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  申请退款时间
	 */
	public void setRefundTime(Date refundTime){
		this.refundTime = refundTime;
	}
	/**
	 *方法: 取得 String
	 *@return:  String  拒绝退款原因
	 */
	@Column(name ="REJECT_REASON",nullable=true,length=500)
	public String getRejectReason(){
		return this.rejectReason;
	}

	/**
	 *方法: 设置 String
	 *@param:  String  拒绝退款原因
	 */
	public void setRejectReason(String rejectReason){
		this.rejectReason = rejectReason;
	}
	/**
	 *方法: 取得 String
	 *@return:  String  微信商户单号
	 */
	@Column(name ="ORDER_PAY_NUMBER",nullable=true,length=32)
	public String getOrderPayNumber(){
		return this.orderPayNumber;
	}

	/**
	 *方法: 设置 String
	 *@param:  String  微信商户单号
	 */
	public void setOrderPayNumber(String orderPayNumber){
		this.orderPayNumber = orderPayNumber;
	}
	/**
	 *方法: 取得BigDecimal
	 *@return: BigDecimal  退款金额
	 */
	@Column(name ="REFUND_PRICE",nullable=true,precision=10,scale=2)
	public BigDecimal getRefundPrice(){
		return this.refundPrice;
	}

	/**
	 *方法: 设置BigDecimal
	 *@param: BigDecimal  退款金额
	 */
	public void setRefundPrice(BigDecimal refundPrice){
		this.refundPrice = refundPrice;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  退款时间
	 */
	@Column(name ="REFUND_COMPLETED_TIME",nullable=true)
	public Date getRefundCompletedTime(){
		return this.refundCompletedTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  退款时间
	 */
	public void setRefundCompletedTime(Date refundCompletedTime){
		this.refundCompletedTime = refundCompletedTime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  订单完成时间
	 */
	@Column(name ="ORDER_COMPLETED_TIME",nullable=true)
	public Date getOrderCompletedTime(){
		return this.orderCompletedTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  订单完成时间
	 */
	public void setOrderCompletedTime(Date orderCompletedTime){
		this.orderCompletedTime = orderCompletedTime;
	}
	/**
	 *方法: 取得 String
	 *@return:  String  是否已经被删除   0：未删除    1：删除
	 */
	@Column(name ="DELETE_FLAG",nullable=true,length=1)
	public String getDeleteFlag(){
		return this.deleteFlag;
	}

	/**
	 *方法: 设置 String
	 *@param:  String  是否已经被删除   0：未删除    1：删除
	 */
	public void setDeleteFlag(String deleteFlag){
		this.deleteFlag = deleteFlag;
	}
	/**
	 *方法: 取得 String
	 *@return:  String  线路订单码
	 */
	@Column(name ="LINEORDERCODE",nullable=true,length=255)
	public String getLineordercode(){
		return this.lineordercode;
	}

	/**
	 *方法: 设置 String
	 *@param:  String  线路订单码
	 */
	public void setLineordercode(String lineordercode){
		this.lineordercode = lineordercode;
	}
	/**
	 *方法: 取得 String
	 *@return:  String  初审审核状态   0：初审待审核     1：初审通过    2：初审未通过
	 */
	@Column(name ="FIRST_AUDIT_STATUS",nullable=true,length=1)
	public String getFirstAuditStatus(){
		return this.firstAuditStatus;
	}

	/**
	 *方法: 设置 String
	 *@param:  String  初审审核状态   0：初审待审核     1：初审通过    2：初审未通过
	 */
	public void setFirstAuditStatus(String firstAuditStatus){
		this.firstAuditStatus = firstAuditStatus;
	}
	/**
	 *方法: 取得 String
	 *@return:  String  复审审核状态   0：复审待审核     1：复审通过    2：复审未通过
	 */
	@Column(name ="LAST_AUDIT_STATUS",nullable=true,length=1)
	public String getLastAuditStatus(){
		return this.lastAuditStatus;
	}

	/**
	 *方法: 设置 String
	 *@param:  String  复审审核状态   0：复审待审核     1：复审通过    2：复审未通过
	 */
	public void setLastAuditStatus(String lastAuditStatus){
		this.lastAuditStatus = lastAuditStatus;
	}
	/**
	 *方法: 取得 String
	 *@return:  String  初审人id
	 */
	@Column(name ="FIRST_AUDIT_USER",nullable=true,length=32)
	public String getFirstAuditUser(){
		return this.firstAuditUser;
	}

	/**
	 *方法: 设置 String
	 *@param:  String  初审人id
	 */
	public void setFirstAuditUser(String firstAuditUser){
		this.firstAuditUser = firstAuditUser;
	}
	/**
	 *方法: 取得 String
	 *@return:  String  复审人id
	 */
	@Column(name ="LAST_AUDIT_USER",nullable=true,length=32)
	public String getLastAuditUser(){
		return this.lastAuditUser;
	}

	/**
	 *方法: 设置 String
	 *@param:  String  复审人id
	 */
	public void setLastAuditUser(String lastAuditUser){
		this.lastAuditUser = lastAuditUser;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  初审时间
	 */
	@Column(name ="FIRST_AUDIT_DATE",nullable=true)
	public Date getFirstAuditDate(){
		return this.firstAuditDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  初审时间
	 */
	public void setFirstAuditDate(Date firstAuditDate){
		this.firstAuditDate = firstAuditDate;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  复审时间
	 */
	@Column(name ="LAST_AUDIT_DATE",nullable=true)
	public Date getLastAuditDate(){
		return this.lastAuditDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  复审时间
	 */
	public void setLastAuditDate(Date lastAuditDate){
		this.lastAuditDate = lastAuditDate;
	}
	/**
	 *方法: 取得 String
	 *@return:  String  历史订单（就是该订单绑定的运营专员已经被注销了）    0：否    1：是
	 */
	@Column(name ="ORDER_HISTORY",nullable=true,length=1)
	public  String getOrderHistory(){
		return this.orderHistory;
	}

	/**
	 *方法: 设置 String
	 *@param:  String  历史订单（就是该订单绑定的运营专员已经被注销了）    0：否    1：是
	 */
	public void setOrderHistory(String orderHistory){
		this.orderHistory = orderHistory;
	}
}
