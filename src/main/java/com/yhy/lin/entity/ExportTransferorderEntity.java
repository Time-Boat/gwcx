package com.yhy.lin.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.SequenceGenerator;

/**   
 * @Title: Entity
 * @Description: 订单导出列表
 * @author zhangdaihao
 * @date 2017-11-02 14:06:22
 * @version V1.0   
 *
 */
@Entity
@Table(name = "export_transferorder", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class ExportTransferorderEntity implements java.io.Serializable {
	/**站点顺序*/
	private java.lang.Integer siteorder;
	/**历史订单（就是该订单绑定的运营专员已经被注销了）    0：否    1：是*/
	private java.lang.String orderHistory;
	/**预计到达时间*/
	private java.util.Date orderExpectedarrival;
	/**线路创建人id*/
	private java.lang.String createuserid;
	/**orgCode*/
	private java.lang.String orgCode;
	/**主键*/
	private java.lang.String id;
	/**订单编号*/
	private java.lang.String orderId;
	/**订单类型 2:接机  3:送机  4:接火车 5:送火车*/
	private java.lang.Integer orderType;
	/**订单状态 0：订单已完成。1：待派车。2：待出行。3：取消订单待退款。4：取消订单完成退款。5：拒绝退款。6：未支付。7：已审核。*/
	private java.lang.Integer orderStatus;
	/**序号*/
	@Excel(name="序号")
	private java.lang.String num;
	/**申请人*/
	@Excel(name="申请人")
	private java.lang.String realName;
	/**起点站名称*/
	@Excel(name="起点站名称")
	private java.lang.String orderStartingStationName;
	/**终点站名称*/
	@Excel(name="终点站名称")
	private java.lang.String orderTerminusStationName;
	/**出发时间*/
	@Excel(name="出发时间")
	private java.lang.String orderStartime;
	/**车票数量*/
	@Excel(name="车票数量")
	private java.lang.String orderNumbers;
	/**线路名称*/
	@Excel(name="线路名称")
	private java.lang.String lineName;
	/**车牌号*/
	@Excel(name="车牌号")
	private java.lang.String licencePlate;
	/**司机电话*/
	@Excel(name="司机电话")
	private java.lang.String phonenumber;
	/**司机姓名*/
	@Excel(name="司机姓名")
	private java.lang.String driverName;
	/**航班号*/
	@Excel(name="航班号")
	private java.lang.String orderFlightnumber;
	/**火车车次*/
	@Excel(name="火车车次")
	private java.lang.String orderTrainnumber;
	/**联系人姓名*/
	//@Excel(name="联系人姓名")
	private java.lang.String orderContactsname;
	/**联系人手机号*/
	//@Excel(name="联系人手机号")
	private java.lang.String orderContactsmobile;
	@Excel(name="联系人手机号")
	private java.lang.String contactsMobileAndName;
	/**订单备注*/
	@Excel(name="订单备注")
	private java.lang.String remark;
	
	@Transient
	public java.lang.String getNum() {
		return num;
	}

	public void setNum(java.lang.String num) {
		this.num = num;
	}

	@Column(name ="REAL_NAME",nullable=true,length=255)
	public java.lang.String getRealName() {
		return realName;
	}

	public void setRealName(java.lang.String realName) {
		this.realName = realName;
	}

	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  站点顺序
	 */
	@Column(name ="SITEORDER",nullable=true,precision=10,scale=0)
	public java.lang.Integer getSiteorder(){
		return this.siteorder;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  站点顺序
	 */
	public void setSiteorder(java.lang.Integer siteorder){
		this.siteorder = siteorder;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  历史订单（就是该订单绑定的运营专员已经被注销了）    0：否    1：是
	 */
	@Column(name ="ORDER_HISTORY",nullable=true,length=1)
	public java.lang.String getOrderHistory(){
		return this.orderHistory;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  历史订单（就是该订单绑定的运营专员已经被注销了）    0：否    1：是
	 */
	public void setOrderHistory(java.lang.String orderHistory){
		this.orderHistory = orderHistory;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  预计到达时间
	 */
	@Column(name ="ORDER_EXPECTEDARRIVAL",nullable=true)
	public java.util.Date getOrderExpectedarrival(){
		return this.orderExpectedarrival;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  预计到达时间
	 */
	public void setOrderExpectedarrival(java.util.Date orderExpectedarrival){
		this.orderExpectedarrival = orderExpectedarrival;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  线路创建人id
	 */
	@Column(name ="CREATEUSERID",nullable=true,length=32)
	public java.lang.String getCreateuserid(){
		return this.createuserid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  线路创建人id
	 */
	public void setCreateuserid(java.lang.String createuserid){
		this.createuserid = createuserid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  orgCode
	 */
	@Column(name ="ORG_CODE",nullable=true,length=64)
	public java.lang.String getOrgCode(){
		return this.orgCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  orgCode
	 */
	public void setOrgCode(java.lang.String orgCode){
		this.orgCode = orgCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
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
	 *@param: java.lang.String  主键
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  订单编号
	 */
	@Column(name ="ORDER_ID",nullable=true,length=100)
	public java.lang.String getOrderId(){
		return this.orderId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单编号
	 */
	public void setOrderId(java.lang.String orderId){
		this.orderId = orderId;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  订单类型 2:接机  3:送机  4:接火车 5:送火车
	 */
	@Column(name ="ORDER_TYPE",nullable=true,precision=10,scale=0)
	public java.lang.Integer getOrderType(){
		return this.orderType;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  订单类型 2:接机  3:送机  4:接火车 5:送火车
	 */
	public void setOrderType(java.lang.Integer orderType){
		this.orderType = orderType;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  订单状态 0：订单已完成。1：待派车。2：待出行。3：取消订单待退款。4：取消订单完成退款。5：拒绝退款。6：未支付。7：已审核。
	 */
	@Column(name ="ORDER_STATUS",nullable=true,precision=10,scale=0)
	public java.lang.Integer getOrderStatus(){
		return this.orderStatus;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  订单状态 0：订单已完成。1：待派车。2：待出行。3：取消订单待退款。4：取消订单完成退款。5：拒绝退款。6：未支付。7：已审核。
	 */
	public void setOrderStatus(java.lang.Integer orderStatus){
		this.orderStatus = orderStatus;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  起点站名称
	 */
	@Column(name ="ORDER_STARTING_STATION_NAME",nullable=true,length=255)
	public java.lang.String getOrderStartingStationName(){
		return this.orderStartingStationName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  起点站名称
	 */
	public void setOrderStartingStationName(java.lang.String orderStartingStationName){
		this.orderStartingStationName = orderStartingStationName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  终点站名称
	 */
	@Column(name ="ORDER_TERMINUS_STATION_NAME",nullable=true,length=255)
	public java.lang.String getOrderTerminusStationName(){
		return this.orderTerminusStationName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  终点站名称
	 */
	public void setOrderTerminusStationName(java.lang.String orderTerminusStationName){
		this.orderTerminusStationName = orderTerminusStationName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  出发时间
	 */
	@Column(name ="ORDER_STARTIME",nullable=true,length=30)
	public java.lang.String getOrderStartime(){
		return this.orderStartime;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  出发时间
	 */
	public void setOrderStartime(java.lang.String orderStartime){
		this.orderStartime = orderStartime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  车票数量
	 */
	@Column(name ="ORDER_NUMBERS",nullable=true,length=100)
	public java.lang.String getOrderNumbers(){
		return this.orderNumbers;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  车票数量
	 */
	public void setOrderNumbers(java.lang.String orderNumbers){
		this.orderNumbers = orderNumbers;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  线路名称
	 */
	@Column(name ="LINE_NAME",nullable=true,length=50)
	public java.lang.String getLineName(){
		return this.lineName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  线路名称
	 */
	public void setLineName(java.lang.String lineName){
		this.lineName = lineName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  车牌号
	 */
	@Column(name ="LICENCE_PLATE",nullable=true,length=20)
	public java.lang.String getLicencePlate(){
		return this.licencePlate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  车牌号
	 */
	public void setLicencePlate(java.lang.String licencePlate){
		this.licencePlate = licencePlate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  司机电话
	 */
	@Column(name ="PHONENUMBER",nullable=true,length=50)
	public java.lang.String getPhonenumber(){
		return this.phonenumber;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  司机电话
	 */
	public void setPhonenumber(java.lang.String phonenumber){
		this.phonenumber = phonenumber;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  司机姓名
	 */
	@Column(name ="DRIVER_NAME",nullable=true,length=50)
	public java.lang.String getDriverName(){
		return this.driverName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  司机姓名
	 */
	public void setDriverName(java.lang.String driverName){
		this.driverName = driverName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  航班号
	 */
	@Column(name ="ORDER_FLIGHTNUMBER",nullable=true,length=100)
	public java.lang.String getOrderFlightnumber(){
		return this.orderFlightnumber;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  航班号
	 */
	public void setOrderFlightnumber(java.lang.String orderFlightnumber){
		this.orderFlightnumber = orderFlightnumber;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  火车车次
	 */
	@Column(name ="ORDER_TRAINNUMBER",nullable=true,length=100)
	public java.lang.String getOrderTrainnumber(){
		return this.orderTrainnumber;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  火车车次
	 */
	public void setOrderTrainnumber(java.lang.String orderTrainnumber){
		this.orderTrainnumber = orderTrainnumber;
	}
	
	@Column(name ="ORDER_CONTACTSNAME",nullable=true,length=100)
	public java.lang.String getOrderContactsname() {
		return orderContactsname;
	}

	public void setOrderContactsname(java.lang.String orderContactsname) {
		this.orderContactsname = orderContactsname;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系人手机号
	 */
	@Column(name ="ORDER_CONTACTSMOBILE",nullable=true,length=11)
	public java.lang.String getOrderContactsmobile(){
		return this.orderContactsmobile;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系人手机号
	 */
	public void setOrderContactsmobile(java.lang.String orderContactsmobile){
		this.orderContactsmobile = orderContactsmobile;
	}
	
	@Transient
	public java.lang.String getContactsMobileAndName() {
		return getOrderContactsname() + "\n" + getOrderContactsmobile();
	}

	public void setContactsMobileAndName(java.lang.String contactsMobileAndName) {
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  订单备注
	 */
	@Column(name ="REMARK",nullable=true,length=500)
	public java.lang.String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单备注
	 */
	public void setRemark(java.lang.String remark){
		this.remark = remark;
	}
}
