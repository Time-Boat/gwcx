package com.yhy.lin.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.jeecgframework.core.common.entity.IdEntity;

/**
 * 线路信息实体类表
 * @author linj
 *
 */
@Entity
@Table(name="lineinfo")
public class LineInfoEntity extends IdEntity implements java.io.Serializable{	
	private static final long serialVersionUID = 1L;
	/**姓名*/
	private String name;
	/**起始位置*/
	private String startLocation;
	/**终点位置*/
	private String endLocation;
	/**线路备注*/
	private String remark;
	/**线路图片*/
	private String imageurl;
	/**线路类型*/
	private String type;//线路类型 0：班车 1：包车 2：接机 3：送机 4：接火车 5：送火车
	/**线路状态*/
	private String status;
	/**逻辑删除0是未删除 1 是已删除*/
	private Short deleteFlag;// 状态: 0:不删除  1：删除
	/**创建时间*/
	private Date createTime;
	/**线路创建人名字*/
	private String createPeople;
	/**线路价格 */
	private BigDecimal price;
	/**线路发车时间*/
	private Date lstartTime;
	/**线路预计到达时间*/
	private Date lendTime;
	/*线路时长**/
	
	private String lineTimes;
	
	//入驻公司id
	private String  settledCompanyId;
	//入驻公司姓名
	private String  settledCompanyName;
	//线路编号
	private String lineNumber;

	private String  cityId;
	
	private String  cityName;
	
	private String dispath;
	
	//线路创建人id
	private String createUserId;
	
	//申请状态
	private String applicationStatus;
	
	//初审被拒绝的原因
	private String trialReason;
	
	//复审被拒绝的原因
	private String reviewReason;
	
	//申请人
	private String applicationUserid;
	
	//申请时间
	private Date applicationTime;
	//申请内容
	private String applyContent;
	
	@Column(name = "price")
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	private String departId;
	@Column(name = "name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "startLocation")
	public String getStartLocation() {
		return startLocation;
	}
	public void setStartLocation(String startLocation) {
		this.startLocation = startLocation;
	}
	@Column(name = "endLocation")
	public String getEndLocation() {
		return endLocation;
	}
	public void setEndLocation(String endLocation) {
		this.endLocation = endLocation;
	}
	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name = "imageurl")
	public String getImageurl() {
		return imageurl;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	@Column(name = "type")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Column(name = "status")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name = "deleteFlag")
	public Short getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Short deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	@Column(name = "createTime" ,nullable=true)
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name = "createPeople")
	public String getCreatePeople() {
		return createPeople;
	}
	public void setCreatePeople(String createPeople) {
		this.createPeople = createPeople;
	}
	@Column(name = "departId")
	public String getDepartId() {
		return departId;
	}
	public void setDepartId(String departId) {
		this.departId = departId;
	}
	@Column(name = "lstartTime" ,nullable=true)
	public Date getLstartTime() {
		return lstartTime;
	}
	public void setLstartTime(Date lstartTime) {
		this.lstartTime = lstartTime;
	}
	@Column(name = "lendTime" ,nullable=true)
	public Date getLendTime() {
		return lendTime;
	}
	public void setLendTime(Date lendTime) {
		this.lendTime = lendTime;
	}
	@Column(name = "lineTimes" ,nullable=true)
	public String getLineTimes() {
		return lineTimes;
	}
	public void setLineTimes(String lineTimes) {
		this.lineTimes = lineTimes;
	}
	@Column(name = "settledCompanyId" ,nullable=true)
	public String getSettledCompanyId() {
		return settledCompanyId;
	}
	public void setSettledCompanyId(String settledCompanyId) {
		this.settledCompanyId = settledCompanyId;
	}
	@Column(name = "settledCompanyName" ,nullable=true)
	public String getSettledCompanyName() {
		return settledCompanyName;
	}
	public void setSettledCompanyName(String settledCompanyName) {
		this.settledCompanyName = settledCompanyName;
	}
	@Column(name = "cityId" )
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	@Column(name = "cityName")
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	@Column(name = "dispath")
	public String getDispath() {
		return dispath;
	}
	public void setDispath(String dispath) {
		this.dispath = dispath;
	}
	
	@Column(name = "createUserId")
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	
	@Column(name = "lineNumber")
	public String getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}
	
	@Column(name = "application_status")
	public String getApplicationStatus() {
		return applicationStatus;
	}
	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
	
	@Column(name = "trial_reason")
	public String getTrialReason() {
		return trialReason;
	}
	public void setTrialReason(String trialReason) {
		this.trialReason = trialReason;
	}
	
	@Column(name = "review_reason")
	public String getReviewReason() {
		return reviewReason;
	}
	public void setReviewReason(String reviewReason) {
		this.reviewReason = reviewReason;
	}
	
	@Column(name = "application_user_id")
	public String getApplicationUserid() {
		return applicationUserid;
	}
	public void setApplicationUserid(String applicationUserid) {
		this.applicationUserid = applicationUserid;
	}
	@Column(name = "application_time")
	public Date getApplicationTime() {
		return applicationTime;
	}
	public void setApplicationTime(Date applicationTime) {
		this.applicationTime = applicationTime;
	}
	@Column(name = "apply_content")
	public String getApplyContent() {
		return applyContent;
	}
	public void setApplyContent(String applyContent) {
		this.applyContent = applyContent;
	}
	
}
