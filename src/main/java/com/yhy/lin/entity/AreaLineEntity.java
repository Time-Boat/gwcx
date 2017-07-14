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
import javax.persistence.SequenceGenerator;

/**   
 * @Title: Entity
 * @Description: 包车区域线路
 * @author zhangdaihao
 * @date 2017-07-14 09:23:49
 * @version V1.0   
 *
 */
@Entity
@Table(name = "area_line", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class AreaLineEntity implements java.io.Serializable {
	/**主键id*/
	private java.lang.String id;
	/**线路名称*/
	private java.lang.String name;
	/**机场或火车站站点*/
	private java.lang.String stationId;
	/**线路图片*/
	private java.lang.String imagePath;
	/**线路类型 2：接机 3：送机 4：接火车 5：送火车*/
	private java.lang.String lineType;
	/**线路状态0:启用  1:未启用*/
	private java.lang.String status;
	/**线路创建时间*/
	private java.util.Date createTime;
	/**线路创建人*/
	private java.lang.String createPeople;
	/**线路编号*/
	private java.lang.String lineNumber;
	/**部门id 用于权限过滤*/
	private java.lang.String departId;
	/**线路的单价*/
	private BigDecimal linePrice;
	/**线路时长*/
	private java.lang.String lineDuration;
	/**城市id*/
	private java.lang.String cityid;
	/**城市名称*/
	private java.lang.String cityname;
	/**出车时间段*/
	private java.lang.String dispath;
	/**车辆类型*/
	private java.lang.String carType;
	/**所属区域（高德）*/
	private java.lang.String district;
	/**区域id （高德）*/
	private java.lang.String districtId;
	/**备注*/
	private java.lang.String remark;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键id
	 */
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=36)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主键id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  线路名称
	 */
	@Column(name ="NAME",nullable=true,length=255)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  线路名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  机场或火车站站点
	 */
	@Column(name ="STATION_ID",nullable=true,length=32)
	public java.lang.String getStationId(){
		return this.stationId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  机场或火车站站点
	 */
	public void setStationId(java.lang.String stationId){
		this.stationId = stationId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  线路图片
	 */
	@Column(name ="IMAGE_PATH",nullable=true,length=255)
	public java.lang.String getImagePath(){
		return this.imagePath;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  线路图片
	 */
	public void setImagePath(java.lang.String imagePath){
		this.imagePath = imagePath;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  线路类型 2：接机 3：送机 4：接火车 5：送火车
	 */
	@Column(name ="LINE_TYPE",nullable=true,length=20)
	public java.lang.String getLineType(){
		return this.lineType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  线路类型 2：接机 3：送机 4：接火车 5：送火车
	 */
	public void setLineType(java.lang.String lineType){
		this.lineType = lineType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  线路状态0:启用  1:未启用
	 */
	@Column(name ="STATUS",nullable=true,length=255)
	public java.lang.String getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  线路状态0:启用  1:未启用
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  线路创建时间
	 */
	@Column(name ="CREATE_TIME",nullable=true)
	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  线路创建时间
	 */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  线路创建人
	 */
	@Column(name ="CREATE_PEOPLE",nullable=true,length=100)
	public java.lang.String getCreatePeople(){
		return this.createPeople;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  线路创建人
	 */
	public void setCreatePeople(java.lang.String createPeople){
		this.createPeople = createPeople;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  线路编号
	 */
	@Column(name ="LINE_NUMBER",nullable=true,length=100)
	public java.lang.String getLineNumber(){
		return this.lineNumber;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  线路编号
	 */
	public void setLineNumber(java.lang.String lineNumber){
		this.lineNumber = lineNumber;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  部门id 用于权限过滤
	 */
	@Column(name ="DEPART_ID",nullable=true,length=32)
	public java.lang.String getDepartId(){
		return this.departId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  部门id 用于权限过滤
	 */
	public void setDepartId(java.lang.String departId){
		this.departId = departId;
	}
	/**
	 *方法: 取得BigDecimal
	 *@return: BigDecimal  线路的单价
	 */
	@Column(name ="LINE_PRICE",nullable=true,precision=6,scale=2)
	public BigDecimal getLinePrice(){
		return this.linePrice;
	}

	/**
	 *方法: 设置BigDecimal
	 *@param: BigDecimal  线路的单价
	 */
	public void setLinePrice(BigDecimal linePrice){
		this.linePrice = linePrice;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  线路时长
	 */
	@Column(name ="LINE_DURATION",nullable=true,length=10)
	public java.lang.String getLineDuration(){
		return this.lineDuration;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  线路时长
	 */
	public void setLineDuration(java.lang.String lineDuration){
		this.lineDuration = lineDuration;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  城市id
	 */
	@Column(name ="CITYID",nullable=true,length=32)
	public java.lang.String getCityid(){
		return this.cityid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  城市id
	 */
	public void setCityid(java.lang.String cityid){
		this.cityid = cityid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  城市名称
	 */
	@Column(name ="CITYNAME",nullable=true,length=255)
	public java.lang.String getCityname(){
		return this.cityname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  城市名称
	 */
	public void setCityname(java.lang.String cityname){
		this.cityname = cityname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  出车时间段
	 */
	@Column(name ="DISPATH",nullable=true,length=55)
	public java.lang.String getDispath(){
		return this.dispath;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  出车时间段
	 */
	public void setDispath(java.lang.String dispath){
		this.dispath = dispath;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  车辆类型
	 */
	@Column(name ="CAR_TYPE",nullable=true,length=2)
	public java.lang.String getCarType(){
		return this.carType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  车辆类型
	 */
	public void setCarType(java.lang.String carType){
		this.carType = carType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属区域（高德）
	 */
	@Column(name ="DISTRICT",nullable=true,length=100)
	public java.lang.String getDistrict(){
		return this.district;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属区域（高德）
	 */
	public void setDistrict(java.lang.String district){
		this.district = district;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  区域id （高德）
	 */
	@Column(name ="DISTRICT_ID",nullable=true,length=32)
	public java.lang.String getDistrictId(){
		return this.districtId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  区域id （高德）
	 */
	public void setDistrictId(java.lang.String districtId){
		this.districtId = districtId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="REMARK",nullable=true,length=255)
	public java.lang.String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setRemark(java.lang.String remark){
		this.remark = remark;
	}
}
