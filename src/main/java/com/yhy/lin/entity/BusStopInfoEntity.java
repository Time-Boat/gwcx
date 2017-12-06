package com.yhy.lin.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 站点信息基础表
 * @author linj
 *
 */
@Entity
@Table(name="busstopinfo")
public class BusStopInfoEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	/**主键id*/
	private String id;
	/**站点名称*/
	private String name;
	/**站点的经度（用于后面地图实现）*/
	private String x;
	/**站点的纬度（用于后面地图实现）*/
	private String y;
	/**备注*/
	private String remark;
	/**是否已挂接站点，0：未挂接      1：挂接公务车站点     2：挂接接送机站点 */
	//因为站点是公务车和接送机业务都能使用，公务车中一条线路使用了这个站点之后，其他公务车的线路就不能再使用了，但是机场线路可以使用，所以这里做了一个区分，看站点是挂在公务车类型下还是挂在接送机类型下
	private String status;
	/**用于逻辑删除 0正常  1已删除*/
	private Short deleteFlag;
	/**站点创建时间*/
	private Date createTime;
	/**站点创建人*/
	private String createPeople;
	/**站点地址*/
	private String stopLocation;
	/**站点挂接状态 0：未挂接站点  1：已挂接站点*/
//	private String hangingStatus;
	/**站点类型     站点类型 0：普通站点    1：火车站点    2：飞机站点    3：区域站点*/
	private String stationType;
	
	private String  cityId;
	
	private String  cityName;
	
	//主键生成策略：uuid 采用128位的uuid算法生成主键，uuid被编码为一个32位16进制数字的字符串。占用空间大（字符串类型）。   
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="id",nullable=false,length=32)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name ="name",nullable=true,length=50)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="x",nullable=true,length=100)
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	@Column(name="y",nullable=true,length=100)
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}
	
	@Column(name="remark",nullable=true,length=1000)
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name="status",nullable=true,length=10)
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name="deleteFlag",length=6)
	public Short getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Short deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	@Column(name="createTime",nullable=true)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name="createPeople",nullable=true,length=50)
	public String getCreatePeople() {
		return createPeople;
	}
	public void setCreatePeople(String createPeople) {
		this.createPeople = createPeople;
	}
	@Column(name="stopLocation",nullable=true,length=200)
	public String getStopLocation() {
		return stopLocation;
	}
	public void setStopLocation(String stopLocation) {
		this.stopLocation = stopLocation;
	}
//	@Column(name="hangingStatus",nullable=true,length=10)
//	public String getHangingStatus() {
//		return hangingStatus;
//	}
//	public void setHangingStatus(String hangingStatus) {
//		this.hangingStatus = hangingStatus;
//	}
	
	@Column(name="STATION_TYPE",nullable=true,length=10)
	public String getStationType() {
		return stationType;
	}
	public void setStationType(String stationType) {
		this.stationType = stationType;
	}
	@Column(name="cityId")
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	@Column(name="cityName")
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
}
