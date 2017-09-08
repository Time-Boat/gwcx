<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>线路详情</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="">
	<input id="id" name="id" type="hidden" value="${View.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable" id="formtableId">
			<%-- <tr>${tView.id} 
			${tView.orderId}
			</tr> --%>
			<tr>
				<td align="right">
					<label class="Validform_label">线路名称：</label>
				</td>
				<td class="value">						  
					<input  class="inputxt" id="name" name="name" value="${View.name}">  
					<span class="Validform_checktip"></span>
				</td>
				
				<td align="right">
					<label class="Validform_label">线路类型：</label>
				</td>
				<td class="value">
					<t:dictSelect field="type" typeGroupCode="transferTy" hasLabel="false" defaultVal="${View.type}"></t:dictSelect>				  
					<span class="Validform_checktip"></span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">所在城市：</label>
				</td>
				<td class="value">						  
					<input  class="inputxt" id="cityId" name="cityId" value="${View.cityName}">  
					<span class="Validform_checktip"></span>
				</td>
				
				<td align="right">
					<label class="Validform_label">所属公司：</label>
				</td>
				<td class="value">
					<input  class="inputxt" id="companyName" name="companyName" value="${View.companyName}">  		
					<span class="Validform_checktip"></span>
				</td>
			</tr>
			
			<tr>
				<td align="right">
					<label class="Validform_label">创建人：</label>
				</td>
				<td class="value">						  
					<input  class="inputxt" id="createUserId" name="createUserId" value="${View.createPeople}">  
					<span class="Validform_checktip"></span>
				</td>
				
				<td align="right">
					<label class="Validform_label">创建时间：</label>
				</td>
				<td class="value">
					<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 150px" id="createTime" name="createTime" 
					value="<fmt:formatDate value='${View.createTime}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>"> <span class="Validform_checktip"></span>
				</td>
				
			</tr>
			<tr>
			
				<td align="right">
					<label class="Validform_label">起始位置：</label>
				</td>
				<td class="value">						  
					<input  class="inputxt" id="startName" name="startName" value="${View.startName}">  
					<span class="Validform_checktip"></span>
				</td>
				<td align="right">
					<label class="Validform_label">终点位置：</label>
				</td>
				<td class="value">
					<input  class="inputxt" id="endName" name="endName" value="${View.endName}">  		
					<span class="Validform_checktip"></span>
				</td>
			</tr> 
			<tr>
				
				<td align="right">
					<label class="Validform_label">申请状态：</label>
				</td>
				
				<td class="value">
					<t:dictSelect field="applicationStatus" typeGroupCode="line_apply_status" hasLabel="false" defaultVal="${View.applicationStatus}"></t:dictSelect>				  
				</td>
				
				<td align="right">
					<label class="Validform_label">申请内容：</label>
				</td>
				
				<td class="value">
					<t:dictSelect field="applyContent" typeGroupCode="apply_type" hasLabel="false" defaultVal="${View.applyContent}"></t:dictSelect>				  
				</td>
			</tr>
			
			<tr>
				
				<td align="right">
					<label class="Validform_label">线路编号：</label>
				</td>
				<td class="value">
					<input  class="inputxt" id="lineNumber" name="lineNumber" value="${View.lineNumber}">  		
					<span class="Validform_checktip"></span>
				</td>
				
				<td align="right">
					<label class="Validform_label">线路时长：</label>
				</td>
				<td class="value">
					<input  class="inputxt" id="lineTimes" name="lineTimes" value="${View.lineTimes}">  		
					<span class="Validform_checktip"></span>
				</td>
				
			<tr> 
			<tr>
				<td align="right">
					<label class="Validform_label">线路状态：</label>
				</td>
				<td class="value">
					<t:dictSelect field="status" typeGroupCode="lineStatus" hasLabel="false" defaultVal="${View.status}"></t:dictSelect>	 		
					<span class="Validform_checktip"></span>
				</td>
			
				<td align="right">
					<label class="Validform_label">发车时间段：</label>
				</td>
				
				<td class="value">
					<t:dictSelect field="dispath" typeGroupCode="dispathtime" hasLabel="false" defaultVal="${View.dispath}"></t:dictSelect>				  
				</td>

			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">线路发车时间：</label>
				</td>
				
				<td class="value">
					<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 150px" id="lstartTime" name="lstartTime" 
					value="<fmt:formatDate value='${View.lstartTime}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>"> <span class="Validform_checktip"></span>
				</td>
				
				<td align="right">
					<label class="Validform_label">线路预计到达时间：</label>
				</td>
				
				<td class="value">
					<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 150px" id="lendTime" name="lendTime" 
					value="<fmt:formatDate value='${View.lendTime}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>"> <span class="Validform_checktip"></span>
				</td>
				
			</tr>
			
			<tr>	
				
				<td align="right">
					<label class="Validform_label">申请时间：</label>
				</td>
				
				<td class="value" colspan="3">
					<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 150px" id="applicationTime" name="applicationTime" 
					value="<fmt:formatDate value='${View.applicationTime}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>"> <span class="Validform_checktip"></span>
				</td>
				
			<tr>
			<tr>
				<td align="right">
					<label class="Validform_label">初审被拒绝的原因：</label>
				</td>
				<td colspan="3">
					<textarea id="trialReason" name="trialReason" cols="82" rows="3">${View.trialReason}</textarea>						  
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">复审被拒绝的原因：</label>
				</td>
				<td class="value" colspan="3">
					<textarea id="reviewReason" name="reviewReason" cols="82" rows="3">${View.reviewReason}</textarea>							  
				</td>
				
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">备注：</label>
				</td>
				<td class="value" colspan="3">
					<textarea id="remark" name="remark" cols="82" rows="3">${View.remark}</textarea>					  
				</td>
				
			</tr>
			
		</table>
	</t:formvalid>
</body>