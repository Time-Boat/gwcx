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
import org.jeecgframework.core.common.entity.IdEntity;

import javax.persistence.SequenceGenerator;

/**   
 * @Title: Entity
 * @Description: 线路历史记录表
 * @author zhangdaihao
 * @date 2017-10-30 15:59:01
 * @version V1.0   
 *
 */
@Entity
@Table(name = "lineinfo_history", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class LineinfoHistoryEntity extends IdEntity implements java.io.Serializable {
	
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
	
	private String departId;
	
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
	
	//申请时间
	private Date firstApplicationTime;
	
	//申请时间
	private Date lastApplicationTime;
	
	//初审人
	private String firstApplicationUser;
	//复审人
	private String lastApplicationUser;
	//是否有渠道商线路
	private String isDealerLine;
	/**申请修改状态：0：未启动，1：待审核，2:初审，3：复审,4：申请通过*/
	private java.lang.String applicationEditStatus;
	/**初审修改拒绝原因*/
	private java.lang.String trialEditReason;
	/**复审修改拒绝原因*/
	private java.lang.String reviewEditReason;
	/**申请修改时间*/
	private java.util.Date applicationEditTime;
	/**申请修改人*/
	private java.lang.String applicationEditUserId;
	/**初审修改时间*/
	private java.util.Date firstApplicationEditTime;
	/**复审修改时间*/
	private java.util.Date lastApplicationEditTime;
	/**初审修改人*/
	private java.lang.String firstApplicationEditUserId;
	/**复审修改人*/
	private java.lang.String lastApplicationEditUserId;
	/**版本号*/
	private java.lang.String version;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  线路名称
	 */
	@Column(name ="NAME",nullable=true,length=50)
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
	 *@return: java.lang.String  起始位置
	 */
	@Column(name ="STARTLOCATION",nullable=true,length=32)
	public java.lang.String getStartLocation(){
		return this.startLocation;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  起始位置
	 */
	public void setStartLocation(java.lang.String startLocation){
		this.startLocation = startLocation;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  终点位置
	 */
	@Column(name ="ENDLOCATION",nullable=true,length=32)
	public java.lang.String getEndLocation(){
		return this.endLocation;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  终点位置
	 */
	public void setEndLocation(java.lang.String endLocation){
		this.endLocation = endLocation;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  线路图片
	 */
	@Column(name ="IMAGEURL",nullable=true,length=255)
	public java.lang.String getImageurl(){
		return this.imageurl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  线路图片
	 */
	public void setImageurl(java.lang.String imageurl){
		this.imageurl = imageurl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  线路状态  0:已上架  1:未上架
	 */
	@Column(name ="STATUS",nullable=true,length=1)
	public java.lang.String getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  线路状态  0:已上架  1:未上架
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  线路创建时间
	 */
	@Column(name ="CREATETIME",nullable=true)
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
	@Column(name ="createPeople",nullable=true,length=100)
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
	@Column(name ="LINENUMBER",nullable=true,length=100)
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
	@Column(name ="DEPARTID",nullable=true,length=32)
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
	@Column(name ="PRICE",nullable=true,precision=10,scale=2)
	public BigDecimal getPrice(){
		return this.price;
	}

	/**
	 *方法: 设置BigDecimal
	 *@param: BigDecimal  线路的单价
	 */
	public void setPrice(BigDecimal price){
		this.price = price;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  线路时长
	 */
	@Column(name ="LINETIMES",nullable=true,length=10)
	public java.lang.String getLineTimes(){
		return this.lineTimes;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  线路时长
	 */
	public void setLineTimes(java.lang.String lineTimes){
		this.lineTimes = lineTimes;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  出车时间段
	 */
	@Column(name ="DISPATH",nullable=true,length=2)
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
	 *@return: java.lang.String  城市id
	 */
	@Column(name ="CITYID",nullable=true,length=50)
	public java.lang.String getCityId(){
		return this.cityId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  城市id
	 */
	public void setCityId(java.lang.String cityId){
		this.cityId = cityId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  线路类型 0：班车 1：包车 2：接机 3：送机 4：接火车 5：送火车  6：接送机  7：接送火车
	 */
	@Column(name ="TYPE",nullable=true,length=20)
	public java.lang.String getType(){
		return this.type;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  线路类型 0：班车 1：包车 2：接机 3：送机 4：接火车 5：送火车  6：接送机  7：接送火车
	 */
	public void setType(java.lang.String type){
		this.type = type;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  线路发车时间
	 */
	@Column(name ="LSTARTTIME",nullable=true)
	public java.util.Date getLstartTime(){
		return this.lstartTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  线路发车时间
	 */
	public void setLstartTime(java.util.Date lstartTime){
		this.lstartTime = lstartTime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  线路预计到达时间
	 */
	@Column(name ="LENDTIME",nullable=true)
	public java.util.Date getLendTime(){
		return this.lendTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  线路预计到达时间
	 */
	public void setLendTime(java.util.Date lendTime){
		this.lendTime = lendTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  入驻公司名称
	 */
	@Column(name ="SETTLEDCOMPANYNAME",nullable=true,length=100)
	public java.lang.String getSettledCompanyName(){
		return this.settledCompanyName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  入驻公司名称
	 */
	public void setSettledCompanyName(java.lang.String settledCompanyName){
		this.settledCompanyName = settledCompanyName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  入驻公司id
	 */
	@Column(name ="SETTLEDCOMPANYID",nullable=true,length=36)
	public java.lang.String getSettledCompanyId(){
		return this.settledCompanyId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  入驻公司id
	 */
	public void setSettledCompanyId(java.lang.String settledCompanyId){
		this.settledCompanyId = settledCompanyId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  城市名称
	 */
	@Column(name ="CITYNAME",nullable=true,length=50)
	public java.lang.String getCityName(){
		return this.cityName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  城市名称
	 */
	public void setCityName(java.lang.String cityName){
		this.cityName = cityName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  线路创建人id
	 */
	@Column(name ="CREATEUSERID",nullable=true,length=32)
	public java.lang.String getCreateUserId(){
		return this.createUserId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  线路创建人id
	 */
	public void setCreateUserId(java.lang.String createUserId){
		this.createUserId = createUserId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  线路备注
	 */
	@Column(name ="REMARK",nullable=true,length=500)
	public java.lang.String getRemark(){
		return this.remark;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  线路备注
	 */
	public void setRemark(java.lang.String remark){
		this.remark = remark;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  用于逻辑删除 0正常  1已删除
	 */
	@Column(name ="DELETEFLAG",nullable=true,precision=5,scale=0)
	public java.lang.Short getDeleteFlag(){
		return this.deleteFlag;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  用于逻辑删除 0正常  1已删除
	 */
	public void setDeleteFlag(java.lang.Short deleteFlag){
		this.deleteFlag = deleteFlag;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  线路申请状态：0：未启动，1：待审核，2:初审，3：复审,4：申请通过
	 */
	@Column(name ="APPLICATION_STATUS",nullable=true,length=1)
	public java.lang.String getApplicationStatus(){
		return this.applicationStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  线路申请状态：0：未启动，1：待审核，2:初审，3：复审,4：申请通过
	 */
	public void setApplicationStatus(java.lang.String applicationStatus){
		this.applicationStatus = applicationStatus;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  初审拒绝原因
	 */
	@Column(name ="TRIAL_REASON",nullable=true,length=255)
	public java.lang.String getTrialReason(){
		return this.trialReason;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  初审拒绝原因
	 */
	public void setTrialReason(java.lang.String trialReason){
		this.trialReason = trialReason;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  复审拒绝的原因
	 */
	@Column(name ="REVIEW_REASON",nullable=true,length=255)
	public java.lang.String getReviewReason(){
		return this.reviewReason;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  复审拒绝的原因
	 */
	public void setReviewReason(java.lang.String reviewReason){
		this.reviewReason = reviewReason;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  申请时间
	 */
	@Column(name ="APPLICATION_TIME",nullable=true)
	public java.util.Date getApplicationTime(){
		return this.applicationTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  申请时间
	 */
	public void setApplicationTime(java.util.Date applicationTime){
		this.applicationTime = applicationTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  申请人
	 */
	@Column(name ="APPLICATION_USER_ID",nullable=true,length=32)
	public java.lang.String getApplicationUserid(){
		return this.applicationUserid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  申请人
	 */
	public void setApplicationUserid(java.lang.String applicationUserid){
		this.applicationUserid = applicationUserid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  申请内容
	 */
	@Column(name ="APPLY_CONTENT",nullable=true,length=1)
	public java.lang.String getApplyContent(){
		return this.applyContent;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  申请内容
	 */
	public void setApplyContent(java.lang.String applyContent){
		this.applyContent = applyContent;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  初审
	 */
	@Column(name ="FIRST_APPLICATION_TIME",nullable=true)
	public java.util.Date getFirstApplicationTime(){
		return this.firstApplicationTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  初审
	 */
	public void setFirstApplicationTime(java.util.Date firstApplicationTime){
		this.firstApplicationTime = firstApplicationTime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  复审
	 */
	@Column(name ="LAST_APPLICATION_TIME",nullable=true)
	public java.util.Date getLastApplicationTime(){
		return this.lastApplicationTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  复审
	 */
	public void setLastApplicationTime(java.util.Date lastApplicationTime){
		this.lastApplicationTime = lastApplicationTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  初审人
	 */
	@Column(name ="FIRST_APPLICATION_USER",nullable=true,length=32)
	public java.lang.String getFirstApplicationUser(){
		return this.firstApplicationUser;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  初审人
	 */
	public void setFirstApplicationUser(java.lang.String firstApplicationUser){
		this.firstApplicationUser = firstApplicationUser;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  复审人
	 */
	@Column(name ="LAST_APPLICATION_USER",nullable=true,length=32)
	public java.lang.String getLastApplicationUser(){
		return this.lastApplicationUser;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  复审人
	 */
	public void setLastApplicationUser(java.lang.String lastApplicationUser){
		this.lastApplicationUser = lastApplicationUser;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否有渠道商线路        0：没有       1：有
	 */
	@Column(name ="IS_DEALER_LINE",nullable=true,length=1)
	public java.lang.String getIsDealerLine(){
		return this.isDealerLine;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否有渠道商线路        0：没有       1：有
	 */
	public void setIsDealerLine(java.lang.String isDealerLine){
		this.isDealerLine = isDealerLine;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  申请修改状态：0：未启动，1：待审核，2:初审，3：复审,4：申请通过
	 */
	@Column(name ="APPLICATION_EDIT_STATUS",nullable=true,length=2)
	public java.lang.String getApplicationEditStatus(){
		return this.applicationEditStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  申请修改状态：0：未启动，1：待审核，2:初审，3：复审,4：申请通过
	 */
	public void setApplicationEditStatus(java.lang.String applicationEditStatus){
		this.applicationEditStatus = applicationEditStatus;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  初审修改拒绝原因
	 */
	@Column(name ="TRIAL_EDIT_REASON",nullable=true,length=255)
	public java.lang.String getTrialEditReason(){
		return this.trialEditReason;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  初审修改拒绝原因
	 */
	public void setTrialEditReason(java.lang.String trialEditReason){
		this.trialEditReason = trialEditReason;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  复审修改拒绝原因
	 */
	@Column(name ="REVIEW_EDIT_REASON",nullable=true,length=255)
	public java.lang.String getReviewEditReason(){
		return this.reviewEditReason;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  复审修改拒绝原因
	 */
	public void setReviewEditReason(java.lang.String reviewEditReason){
		this.reviewEditReason = reviewEditReason;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  申请修改时间
	 */
	@Column(name ="APPLICATION_EDIT_TIME",nullable=true)
	public java.util.Date getApplicationEditTime(){
		return this.applicationEditTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  申请修改时间
	 */
	public void setApplicationEditTime(java.util.Date applicationEditTime){
		this.applicationEditTime = applicationEditTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  申请修改人
	 */
	@Column(name ="APPLICATION_EDIT_USER_ID",nullable=true,length=32)
	public java.lang.String getApplicationEditUserId(){
		return this.applicationEditUserId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  申请修改人
	 */
	public void setApplicationEditUserId(java.lang.String applicationEditUserId){
		this.applicationEditUserId = applicationEditUserId;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  初审修改时间
	 */
	@Column(name ="FIRST_APPLICATION_EDIT_TIME",nullable=true)
	public java.util.Date getFirstApplicationEditTime(){
		return this.firstApplicationEditTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  初审修改时间
	 */
	public void setFirstApplicationEditTime(java.util.Date firstApplicationEditTime){
		this.firstApplicationEditTime = firstApplicationEditTime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  复审修改时间
	 */
	@Column(name ="LAST_APPLICATION_EDIT_TIME",nullable=true)
	public java.util.Date getLastApplicationEditTime(){
		return this.lastApplicationEditTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  复审修改时间
	 */
	public void setLastApplicationEditTime(java.util.Date lastApplicationEditTime){
		this.lastApplicationEditTime = lastApplicationEditTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  初审修改人
	 */
	@Column(name ="FIRST_APPLICATION_EDIT_USER_ID",nullable=true,length=32)
	public java.lang.String getFirstApplicationEditUserId(){
		return this.firstApplicationEditUserId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  初审修改人
	 */
	public void setFirstApplicationEditUserId(java.lang.String firstApplicationEditUserId){
		this.firstApplicationEditUserId = firstApplicationEditUserId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  复审修改人
	 */
	@Column(name ="LAST_APPLICATION_EDIT_USER_ID",nullable=true,length=32)
	public java.lang.String getLastApplicationEditUserId(){
		return this.lastApplicationEditUserId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  复审修改人
	 */
	public void setLastApplicationEditUserId(java.lang.String lastApplicationEditUserId){
		this.lastApplicationEditUserId = lastApplicationEditUserId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  版本号
	 */
	@Column(name ="VERSION",nullable=true,length=2)
	public java.lang.String getVersion(){
		return this.version;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  版本号
	 */
	public void setVersion(java.lang.String version){
		this.version = version;
	}
}
