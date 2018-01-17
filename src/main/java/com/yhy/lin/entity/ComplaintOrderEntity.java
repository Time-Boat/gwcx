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
 * @Description: 订单申诉表
 * @author zhangdaihao
 * @date 2018-01-17 10:05:38
 * @version V1.0   
 *
 */
@Entity
@Table(name = "exception_order", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class ComplaintOrderEntity extends IdEntity implements java.io.Serializable {
	
	/**订单id*/
	private String transferId;
	/**申诉原因*/
	private String complaintReason;
	/**申诉时间*/
	private Date complaintTime;
	/**申诉内容*/
	private String complaintContent;
	/**处理意见   0：发起退款   1：拒绝退款*/
	private String handleResult;
	/**处理内容*/
	private String handleContent;
	/**申诉原因备注*/
	private String remark;
	
	public String getTransferId() {
		return transferId;
	}
	public void setTransferId(String transferId) {
		this.transferId = transferId;
	}
	public String getComplaintReason() {
		return complaintReason;
	}
	public void setComplaintReason(String complaintReason) {
		this.complaintReason = complaintReason;
	}
	public Date getComplaintTime() {
		return complaintTime;
	}
	public void setComplaintTime(Date complaintTime) {
		this.complaintTime = complaintTime;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
