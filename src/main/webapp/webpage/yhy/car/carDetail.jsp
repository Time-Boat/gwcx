<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>车辆信息</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="right">
					<label class="Validform_label">
						车牌号:
					</label>
				</td>
				<td class="value" colspan="3" >
					<input class="inputxt" id="licencePlate" name="licencePlate" datatype="*"
						   value="${carInfoPage.licencePlate}">
					<span class="Validform_checktip"></span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						车辆品牌:
					</label>
				</td>
				<td class="value" colspan="3" >
					<t:dictSelect field="carBrand" typeGroupCode="carBrand" hasLabel="false" defaultVal="${carInfoPage.carBrand}" datatype="*"></t:dictSelect>
					<span class="Validform_checktip"></span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						车辆型号:
					</label>
				</td>
				<td class="value" colspan="3" >
				    <input class="inputxt" id="modelNumber" name="modelNumber" datatype="*" value="${carInfoPage.modelNumber}">
					<span class="Validform_checktip"></span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						车辆类型:
					</label>
				</td>
				<td class="value" colspan="3" >
					<t:dictSelect field="carType" typeGroupCode="car_Type" hasLabel="false" defaultVal="${carInfoPage.carType}" datatype="*"></t:dictSelect>
					<%-- <input class="inputxt" id="carType" name="carType" datatype="*"
						   value="${carInfoPage.carType}"> --%>
					<span class="Validform_checktip"></span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						座位数:
					</label>
				</td>
				<td class="value" colspan="3" >
				    <input class="inputxt" id="seat" name="seat" datatype="n" 
				   			 value="${carInfoPage.seat}">
					<span class="Validform_checktip"></span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						车辆位置:
					</label>
				</td>
				<td class="value" colspan="3" >
					<input class="inputxt" id="stopPosition" name="stopPosition" ignore="ignore"
						   value="${carInfoPage.stopPosition}">
					<span class="Validform_checktip"></span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						购置日期:
					</label>
				</td>
				<td class="value" colspan="3" >
					<input class="inputxt" style="width: 150px" value="<fmt:formatDate value='${carInfoPage.buyDate}' type="date" pattern="yyyy-MM-dd"/>" >
					<span class="Validform_checktip"></span>
				</td>
			</tr>
			<tr>
			<td align="right">
				<label class="Validform_label">
					司机:
				</label>
			</td>
				<td class="value" colspan="3" >					
					<input readonly="true" class="inputxt" value="${driverName}">
					<span class="Validform_checktip"></span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						车辆状态:
					</label>
				</td>
				<td class="value" colspan="3" >
					<c:choose>
						<c:when test="${carInfoPage.status != null}">
							<t:dictSelect field="status" typeGroupCode="carType" hasLabel="false" defaultVal="${carInfoPage.status}" readonly="readonly" datatype="*"></t:dictSelect>	
						</c:when>
						<c:otherwise>
							<t:dictSelect field="status" typeGroupCode="carType" hasLabel="false" defaultVal="${carInfoPage.status}" datatype="*"></t:dictSelect>	
						</c:otherwise>
					</c:choose>
					<span class="Validform_checktip"></span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						车辆业务类型:
					</label>
				</td>
				<td class="value" colspan="3" >
					<t:dictSelect field="businessType" typeGroupCode="carBType" hasLabel="false" defaultVal="${carInfoPage.businessType}" datatype="*"></t:dictSelect>	
					<span class="Validform_checktip"></span>
				</td>
			</tr>
						
			<c:if test="${carInfoPage.auditStatus != -1}">
				<tr>	
					<td align="right">
						<label class="Validform_label">提交申请时间：</label>
					</td>
					<td class="value">
						<input class="inputxt" style="width: 150px" 
							value="<fmt:formatDate value='${carInfoPage.applyTime}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>"> 
						<span class="Validform_checktip"></span>
					</td>
					<td align="right">
						<label class="Validform_label">提交申请人：</label>
					</td>
					<td class="value">
						<input class="inputxt" style="width: 150px" value="${applyUser}"> 
						<span class="Validform_checktip"></span>
					</td>
				</tr>
			</c:if>
			<tr>
				<td align="right">
					<label class="Validform_label">审核状态：</label>
				</td>
				<td class="value" colspan="3">
					<t:dictSelect field="status" typeGroupCode="audit_status" hasLabel="false" defaultVal="${carInfoPage.auditStatus}"></t:dictSelect>				  
				</td>
			</tr>
			<c:choose>
				<c:when test="${carInfoPage.auditStatus == 1}">
					<tr>
						<td align="right">
							<label class="Validform_label">审核时间：</label>
						</td>
						<td class="value">
							<input class="inputxt" style="width: 150px" 
								value="<fmt:formatDate value='${carInfoPage.auditTime}' type='date' pattern='yyyy-MM-dd hh:mm:ss'/>" /> 
							<span class="Validform_checktip"></span>
						</td>
						<td align="right">
							<label class="Validform_label">审核人：</label>
						</td>
						<td class="value">
							<input class="inputxt" value="${auditUser}">
							<span class="Validform_checktip"></span>
						</td>
					</tr>
				</c:when>
				<c:when test="${carInfoPage.auditStatus == 2}">
					<tr>
						<td align="right">
							<label class="Validform_label">审核时间：</label>
						</td>
						<td class="value">
							<input class="inputxt" style="width: 150px" 
								value="<fmt:formatDate value='${carInfoPage.auditTime}' type='date' pattern='yyyy-MM-dd hh:mm:ss'/>" /> 
							<span class="Validform_checktip"></span>
						</td>
						<td align="right">
							<label class="Validform_label">审核人：</label>
						</td>
						<td class="value">
							<input class="inputxt" value="${auditUser}"> 
							<span class="Validform_checktip"></span>
						</td>
					</tr>
					<tr>
						<td align="right">
							<label class="Validform_label">拒绝原因：</label>
						</td>
						<td class="value" colspan="3">
							<textarea id="trialReason" name="trialReason" cols="82" rows="3">${carInfoPage.rejectReason}</textarea>						  
						</td>
					</tr>
				</c:when>
			</c:choose>
			<tr>
				<td align="right">
					<label class="Validform_label">
						备注:
					</label>
				</td>
				<td class="value" colspan="3" >
					<input class="inputxt" id="remark" name="remark" ignore="ignore"
						   value="${carInfoPage.remark}">
					<span class="Validform_checktip"></span>
				</td>
			</tr>

				
		</table>
	</t:formvalid>
 </body>