<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>渠道商信息</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  
  </script>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" >
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							渠道商账号:
						</label>
					</td>
					<td class="value" colspan="3">
						<input class="inputxt" id="account"  name="account" value="${dealerInfoPage.account}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							联系电话:
						</label>
					</td>
					<td class="value" colspan="3">
						<input class="inputxt" id="phone" name="phone" value="${dealerInfoPage.phone}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							负责人:
						</label>
					</td>
					<td class="value" colspan="3">
						<input class="inputxt" id="manager" name="manager" value="${dealerInfoPage.manager}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							地址:
						</label>
					</td>
					<td class="value" colspan="3">
						<input class="inputxt" id="position" name="position" value="${dealerInfoPage.position}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							银行账户:
						</label>
					</td>
					<td class="value" colspan="3">
						<input class="inputxt" id="bankAccount" name="bankAccount" value="${dealerInfoPage.bankAccount}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							公司社会信用代码:
						</label>
					</td>
					<td class="value" colspan="3">
						<input class="inputxt" id="creditCode" name="creditCode" value="${dealerInfoPage.creditCode}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<c:if test="${dealerInfoPage.auditStatus != -1}">
					<tr>	
						<td align="right">
							<label class="Validform_label">提交申请时间：</label>
						</td>
						<td class="value">
							<input class="inputxt" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 150px" id="commitApplyDate" name="commitApplyDate" 
								value="<fmt:formatDate value='${dealerInfoPage.commitApplyDate}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>"> 
							<span class="Validform_checktip"></span>
						</td>
						<td align="right">
							<label class="Validform_label">提交申请人：</label>
						</td>
						<td class="value">
							<input class="inputxt" style="width: 150px" value="${userName}"> 
							<span class="Validform_checktip"></span>
						</td>
					</tr>
				</c:if>
				<c:choose>
					<c:when test="${dealerInfoPage.auditStatus != 2 }">
						<tr>
							<td align="right">
								<label class="Validform_label">初审状态：</label>
							</td>
							<td class="value" colspan="3">
								<t:dictSelect field="status" typeGroupCode="audit_status" hasLabel="false" defaultVal="${dealerInfoPage.auditStatus}"></t:dictSelect>				  
							</td>
						</tr>
					</c:when>
					<c:otherwise>
						<tr>
							<td align="right">
								<label class="Validform_label">初审时间：</label>
							</td>
							<td class="value">
								<input class="inputxt" value="${dealerInfoPage.auditDate}">
								<span class="Validform_checktip"></span>
							</td>
							<td align="right">
								<label class="Validform_label">初审人：</label>
							</td>
							<td class="value">
								<input class="inputxt" value="${dealerInfoPage.auditUser}"> 
								<span class="Validform_checktip"></span>
							</td>
						</tr>
						<tr>
							<td align="right">
								<label class="Validform_label">初审被拒绝的原因：</label>
							</td>
							<td class="value" colspan="3">
								<textarea id="trialReason" name="trialReason" cols="82" rows="3">${dealerInfoPage.rejectReason}</textarea>						  
							</td>
						</tr>
					</c:otherwise>
				</c:choose>
				<c:choose>
					<c:when test="${dealerInfoPage.lastAuditStatus != 2 and dealerInfoPage.lastAuditStatus != '' and dealerInfoPage.lastAuditStatus != null}">
						<tr>
							<td align="right">
								<label class="Validform_label">复审状态：</label>
							</td>
							<td class="value" colspan="3">
								<t:dictSelect field="status" typeGroupCode="audit_status" hasLabel="false" defaultVal="${dealerInfoPage.lastAuditStatus}"></t:dictSelect>				  
							</td>
						</tr>
					</c:when>
					<c:when test="${dealerInfoPage.lastAuditStatus == 2}">
						<tr>
							<td align="right">
								<label class="Validform_label">复审时间：</label>
							</td>
							<td class="value">
								<input class="inputxt" value="${dealerInfoPage.lastAuditDate}"> <span class="Validform_checktip"></span>
							</td>
							<td align="right">
								<label class="Validform_label">复审人：</label>
							</td>
							<td class="value">
								<input class="inputxt" style="width: 150px" value="${dealerInfoPage.lastAuditUser}"> 
								<span class="Validform_checktip"></span>
							</td>
						</tr>
						<tr>
							<td align="right">
								<label class="Validform_label">复审被拒绝的原因：</label>
							</td>
							<td class="value" colspan="3">
								<textarea id="reviewReason" name="reviewReason" cols="82" rows="3">${dealerInfoPage.lastRejectReason}</textarea>							  
							</td>
						</tr>
					</c:when>
				</c:choose>
				<tr>
					<td align="right">
						<label class="Validform_label">备注：</label>
					</td>
					<td class="value" colspan="3">
						<textarea id="remark" name="remark" cols="82" rows="3">${dealerInfoPage.remark}</textarea>					  
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>