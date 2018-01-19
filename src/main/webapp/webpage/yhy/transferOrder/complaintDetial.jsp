<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>订单详情</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="">
	<input id="id" name="id" type="hidden" value="${tView.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable" id="formtableId">
			<%-- <tr>${tView.id} 
			${tView.orderId}
			</tr> --%>
			<tr>
				<td align="right"><label class="Validform_label">订单编号：</label></td>
				<td class="value">						  
					<input  class="inputxt" id="orderId" name="orderId" value="${tView.orderId}">  
					<span class="Validform_checktip"></span>
				</td>
				<td align="right">
					<label class="Validform_label">订单状态：</label>
				</td>
				<td class="value">
					<t:dictSelect field="orderStatus" typeGroupCode="orderStatus" hasLabel="false" defaultVal="${tView.orderStatus}"></t:dictSelect>				  
					<span class="Validform_checktip"></span>
				</td>
			<tr>
			<tr>
				<td align="right">
					<label class="Validform_label">线路名称：</label>
				</td>
				<td class="value">						  
					<input  class="inputxt" id="lineName" name="lineName" value="${tView.lineName}">  
					<span class="Validform_checktip"></span>
				</td>
				<td align="right">
					<label class="Validform_label">订单金额：</label>
				</td>
				<td class="value">
					<input  class="inputxt" id="orderTotalPrice" name="orderTotalPrice" value="${tView.orderTotalPrice}">  		
					<span class="Validform_checktip"></span>
				</td>
			<tr>
			<tr>
				<td align="right">
					<label class="Validform_label">起始位置：</label>
				</td>
				<td class="value">						  
					<input  class="inputxt" id="startLocation" name="startLocation" value="${tView.startLocation}">  
					<span class="Validform_checktip"></span>
				</td>
				<td align="right">
					<label class="Validform_label">终点位置：</label>
				</td>
				<td class="value">
					<input  class="inputxt" id="endLocation" name="endLocation" value="${tView.endLocation}">  		
					<span class="Validform_checktip"></span>
				</td>
			<tr> 
			
			<tr>
				<td align="right">
					<label class="Validform_label">下单时间：</label>
				</td>
				<td class="value">
					<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 150px" id="applicationTime" name="applicationTime" ignore="ignore"
					value="<fmt:formatDate value='${tView.applicationTime}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>"> <span class="Validform_checktip"></span>
				</td>
				<td align="right">
					<label class="Validform_label">发车时间：</label>
				</td>
				<td class="value">
					<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 150px" id="orderStartime" name="orderStartime" ignore="ignore"
					value="<fmt:formatDate value='${tView.orderStartime}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>"> <span class="Validform_checktip"></span>
				</td>
			<tr>
			
			<tr>
				<td align="right">
					<label class="Validform_label">用户类型：</label>
				</td>
				<td class="value">
					<t:dictSelect field="orderUserType" typeGroupCode="userType" hasLabel="false" defaultVal="${tView.orderUserType}"></t:dictSelect>						  
					<span class="Validform_checktip"></span>
				</td>
				<td align="right">
					<label class="Validform_label">申诉时间：</label>
				</td>
				<td class="value">
					<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 150px" id="complaintTime" name="complaintTime" ignore="ignore"
					value="<fmt:formatDate value='${tView.complaintTime}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>"> <span class="Validform_checktip"></span>
				</td>
			<tr>
			
			<tr>
				<td align="right">
					<label class="Validform_label">申诉人：</label>
				</td>
				<td class="value">						  
					<input  class="inputxt" id="orderContactsname" name="orderContactsname" value="${tView.orderContactsname}">  
					<span class="Validform_checktip"></span>
				</td>
				<td align="right">
					<label class="Validform_label">申诉人手机号：</label>
				</td>
				<td class="value">
					<input  class="inputxt" id="orderContactsmobile" name="orderContactsmobile" value="${tView.orderContactsmobile}">  		
					<span class="Validform_checktip"></span>
				</td>
			</tr>
			<tr>
			<td colspan="4" height="30px"><label class="Validform_label">申诉详情：</label>
			</td>
			</tr>
			
			<tr>
				<td align="right">
					<label class="Validform_label">申诉原因：</label>
				</td>
				<td class="value" colspan="3">
					<t:dictSelect field="complaintReason" typeGroupCode="complaint_reason" hasLabel="false" defaultVal="${tView.complaintReason}"></t:dictSelect>
					<span class="Validform_checktip"></span>
				</td>
				
			<tr>
			<tr>
				<td align="right">
					<label class="Validform_label">申诉内容：</label>
				</td>
				<td class="value" colspan="3">
					<textarea id="complaintContent" name="complaintContent" rows="6" cols= "60" >${tView.complaintContent} </textarea>
					<span class="Validform_checktip"></span>
				</td>
				
			</tr>
			
			<tr>
				<td align="left" colspan="4" height="30px">
					<label class="Validform_label" style="height:30px;">处理 详情：</label>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">处理意见：</label></td>
				<td class="value">						  
					<t:dictSelect field="handleResult" typeGroupCode="handle_result" hasLabel="false" defaultVal="${tView.handleResult}"></t:dictSelect>
					<span class="Validform_checktip"></span>
				</td>
				<td align="right">
					<label class="Validform_label">处理时间：</label>
				</td>
				<td class="value">
					<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 150px" id="handleTime" name="handleTime"
						value="<fmt:formatDate value='${tView.handleTime}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>"> 
					<span class="Validform_checktip"></span>
				</td>
			</tr>
			
			<tr>
				<td align="right"><label class="Validform_label">处理详情：</label></td>
				<td class="value" colspan="3">
					<textarea id="handleContent" name="handleContent" rows="6" cols= "60" >${tView.handleContent} </textarea>
					<span class="Validform_checktip"></span>
				</td>
			<tr>
			
		</table>
	</t:formvalid>
</body>