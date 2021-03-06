package com.yhy.lin.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class LineInfoView implements Serializable {
	
	private String id;//主键
	/**线路名称*/
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
	//所属公司name
	private String companyName;
	
	private String departId;
	
	private String startName;//起点站点名称
	
	private String endName;//终点站点名称
	//初审审核人
	private String firstApplicationUser;
	//初审时间
	private Date firstAuditDate;
	//复审审核人
	private String lastApplicationUser;
	//复审时间
	private Date lastAuditDate;
	//是否有渠道商
	private String isDealerLine;
	//版本号
	private String version;
	//申请修改状态
	private String applicationEditStatus;
	//初审修改拒绝原因
	private String trialEditReason;
	//复审拒绝修改原因
	private String reviewEditReason;
	//申请修改时间
	private Date applicationEditTime;
	//申请修改人
	private String applicationEditUserId;
	//初审修改时间
	private Date firstApplicationEditTime;
	//复审修改时间
	private Date lastApplicationEditTime;
	//初审修改人
	private String firstApplicationEditUserId;
	//复审修改人
	private String lastApplicationEditUserId;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getImageurl() {
		return imageurl;
	}
	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Short getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Short deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreatePeople() {
		return createPeople;
	}
	public void setCreatePeople(String createPeople) {
		this.createPeople = createPeople;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Date getLstartTime() {
		return lstartTime;
	}
	public void setLstartTime(Date lstartTime) {
		this.lstartTime = lstartTime;
	}
	public Date getLendTime() {
		return lendTime;
	}
	public void setLendTime(Date lendTime) {
		this.lendTime = lendTime;
	}
	public String getLineTimes() {
		return lineTimes;
	}
	public void setLineTimes(String lineTimes) {
		this.lineTimes = lineTimes;
	}
	public String getSettledCompanyId() {
		return settledCompanyId;
	}
	public void setSettledCompanyId(String settledCompanyId) {
		this.settledCompanyId = settledCompanyId;
	}
	public String getSettledCompanyName() {
		return settledCompanyName;
	}
	public void setSettledCompanyName(String settledCompanyName) {
		this.settledCompanyName = settledCompanyName;
	}
	public String getLineNumber() {
		return lineNumber;
	}
	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getDispath() {
		return dispath;
	}
	public void setDispath(String dispath) {
		this.dispath = dispath;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public String getApplicationStatus() {
		return applicationStatus;
	}
	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
	public String getTrialReason() {
		return trialReason;
	}
	public void setTrialReason(String trialReason) {
		this.trialReason = trialReason;
	}
	public String getReviewReason() {
		return reviewReason;
	}
	public void setReviewReason(String reviewReason) {
		this.reviewReason = reviewReason;
	}
	public String getApplicationUserid() {
		return applicationUserid;
	}
	public void setApplicationUserid(String applicationUserid) {
		this.applicationUserid = applicationUserid;
	}
	public Date getApplicationTime() {
		return applicationTime;
	}
	public void setApplicationTime(Date applicationTime) {
		this.applicationTime = applicationTime;
	}
	public String getApplyContent() {
		return applyContent;
	}
	public void setApplyContent(String applyContent) {
		this.applyContent = applyContent;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getDepartId() {
		return departId;
	}
	public void setDepartId(String departId) {
		this.departId = departId;
	}
	public String getStartName() {
		return startName;
	}
	public void setStartName(String startName) {
		this.startName = startName;
	}
	public String getEndName() {
		return endName;
	}
	public void setEndName(String endName) {
		this.endName = endName;
	}
	public Date getFirstAuditDate() {
		return firstAuditDate;
	}
	public void setFirstAuditDate(Date firstAuditDate) {
		this.firstAuditDate = firstAuditDate;
	}
	public Date getLastAuditDate() {
		return lastAuditDate;
	}
	public void setLastAuditDate(Date lastAuditDate) {
		this.lastAuditDate = lastAuditDate;
	}
	public String getFirstApplicationUser() {
		return firstApplicationUser;
	}
	public void setFirstApplicationUser(String firstApplicationUser) {
		this.firstApplicationUser = firstApplicationUser;
	}
	public String getLastApplicationUser() {
		return lastApplicationUser;
	}
	public void setLastApplicationUser(String lastApplicationUser) {
		this.lastApplicationUser = lastApplicationUser;
	}
	public String getIsDealerLine() {
		return isDealerLine;
	}
	public void setIsDealerLine(String isDealerLine) {
		this.isDealerLine = isDealerLine;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getApplicationEditStatus() {
		return applicationEditStatus;
	}
	public void setApplicationEditStatus(String applicationEditStatus) {
		this.applicationEditStatus = applicationEditStatus;
	}
	public String getTrialEditReason() {
		return trialEditReason;
	}
	public void setTrialEditReason(String trialEditReason) {
		this.trialEditReason = trialEditReason;
	}
	public String getReviewEditReason() {
		return reviewEditReason;
	}
	public void setReviewEditReason(String reviewEditReason) {
		this.reviewEditReason = reviewEditReason;
	}
	public Date getApplicationEditTime() {
		return applicationEditTime;
	}
	public void setApplicationEditTime(Date applicationEditTime) {
		this.applicationEditTime = applicationEditTime;
	}
	public String getApplicationEditUserId() {
		return applicationEditUserId;
	}
	public void setApplicationEditUserId(String applicationEditUserId) {
		this.applicationEditUserId = applicationEditUserId;
	}
	public Date getFirstApplicationEditTime() {
		return firstApplicationEditTime;
	}
	public void setFirstApplicationEditTime(Date firstApplicationEditTime) {
		this.firstApplicationEditTime = firstApplicationEditTime;
	}
	public Date getLastApplicationEditTime() {
		return lastApplicationEditTime;
	}
	public void setLastApplicationEditTime(Date lastApplicationEditTime) {
		this.lastApplicationEditTime = lastApplicationEditTime;
	}
	public String getFirstApplicationEditUserId() {
		return firstApplicationEditUserId;
	}
	public void setFirstApplicationEditUserId(String firstApplicationEditUserId) {
		this.firstApplicationEditUserId = firstApplicationEditUserId;
	}
	public String getLastApplicationEditUserId() {
		return lastApplicationEditUserId;
	}
	public void setLastApplicationEditUserId(String lastApplicationEditUserId) {
		this.lastApplicationEditUserId = lastApplicationEditUserId;
	}
}
