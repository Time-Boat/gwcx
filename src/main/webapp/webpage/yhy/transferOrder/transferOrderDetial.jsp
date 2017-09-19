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
				<td align="right">
					<label class="Validform_label">订单编号：</label>
				</td>
				<td class="value">						  
					<input  class="inputxt" id="orderId" name="orderId" value="${tView.orderId}">  
					<span class="Validform_checktip"></span>
				</td>
				<td align="right">
					<label class="Validform_label">功能类型：</label>
				</td>
				<td class="value">
					<t:dictSelect field="orderType" typeGroupCode="transferTy" hasLabel="false" defaultVal="${tView.orderType}"></t:dictSelect>				  
					<span class="Validform_checktip"></span>
				</td>
			<tr>
			<tr>
				<td align="right">
					<label class="Validform_label">申请人：</label>
				</td>
				<td class="value">						  
					<input  class="inputxt" id="orderContactsname" name="orderContactsname" value="${tView.orderContactsname}">  
					<span class="Validform_checktip"></span>
				</td>
				<td align="right">
					<label class="Validform_label">申请人手机号：</label>
				</td>
				<td class="value">
					<input  class="inputxt" id="orderContactsmobile" name="orderContactsmobile" value="${tView.orderContactsmobile}">  		
					<span class="Validform_checktip"></span>
				</td>
			<tr>
			<tr>
				<td align="right">
					<label class="Validform_label">申请时间：</label>
				</td>
				<td class="value">
					<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 150px" id="applicationTime" name="applicationTime" ignore="ignore"
					value="<fmt:formatDate value='${tView.applicationTime}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>"> <span class="Validform_checktip"></span>
				</td>
				<td align="right">
					<label class="Validform_label">发车时间：</label>
				</td>
				<td class="value">
					<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 150px" id="orderStartime" name="orderStartime" ignore="ignore"
					value="<fmt:formatDate value='${tView.orderStartime}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>"> <span class="Validform_checktip"></span>
				</td>
			<tr>
			<tr>
				<td align="right">
					<label class="Validform_label">起始位置：</label>
				</td>
				<td class="value">						  
					<input  class="inputxt" id="orderStartingstation" name="orderStartingstationName" value="${tView.orderStartingstationName}">  
					<span class="Validform_checktip"></span>
				</td>
				<td align="right">
					<label class="Validform_label">终点位置：</label>
				</td>
				<td class="value">
					<input  class="inputxt" id="orderTerminusstation" name="orderTerminusstationName" value="${tView.orderTerminusstationName}">  		
					<span class="Validform_checktip"></span>
				</td>
			<tr> 
			<tr>
				<td align="right">
					<label class="Validform_label">费用单价(元/人)：</label>
				</td>
				<td class="value">						  
					<input  class="inputxt" id="orderUnitprice" name="orderUnitprice" value="${tView.orderUnitprice}">  
					<span class="Validform_checktip"></span>
				</td>
				<%-- <td align="right">
					<label class="Validform_label">人数(不需要)：</label>
				</td>
				<td class="value">
					<input  class="inputxt" id="orderNumberPeople" name="orderNumberPeople" value="${tView.orderNumberPeople}">  		
					<span class="Validform_checktip"></span>
				</td> --%>
				<td align="right">
					<label class="Validform_label">城市：</label>
				</td>
				<td class="value">
					<input  class="inputxt" id="cityName" name="cityName" value="${tView.cityName}">		
					<span class="Validform_checktip"></span>
				</td>
			<tr>
			<tr>
				<td align="right">
					<label class="Validform_label">付费方式：</label>
				</td>
				<td class="value">						  
					<t:dictSelect field="orderPaytype" typeGroupCode="payMethod" hasLabel="false" defaultVal="${tView.orderPaytype}"></t:dictSelect>				  
					<span class="Validform_checktip"></span>
				</td>
				<td align="right">
					<label class="Validform_label">费用合计：</label>
				</td>
				<td class="value">
					<input  class="inputxt" id="orderTotalPrice"name="orderTotalPrice" value="${tView.orderTotalPrice}">  		
					<span class="Validform_checktip"></span>
				</td>
			<tr>
			<tr>
				<td align="right">
					<label class="Validform_label">航班号：</label>
				</td>
				<td class="value">
					<input  class="inputxt" id="orderFlightnumber"name="orderFlightnumber" value="${tView.orderFlightnumber}">  		
					<span class="Validform_checktip"></span>
				</td>
				<td align="right">
					<label class="Validform_label">火车车次：</label>
				</td>
				<td class="value">
					<input  class="inputxt" id="orderTrainnumber"name="orderTrainnumber" value="${tView.orderTrainnumber}">  		
					<span class="Validform_checktip"></span>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">购票人手机号：</label>
				</td>
				<td class="value">
					<input  class="inputxt" id="orderTotalPrice"name="customerPhone" value="${tView.customerPhone}">  		
					<span class="Validform_checktip"></span>
				</td>
				<td align="right">
					<label class="Validform_label">预计到达时间：</label>
				</td>
				<td class="value">
					<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 150px" id="orderExpectedarrival" name="orderExpectedarrival" ignore="ignore"
					value="<fmt:formatDate value='${tView.orderExpectedarrival}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>"> <span class="Validform_checktip"></span>
				</td>
			</tr>
			<tr>
				<td align="left" colspan="4">
					<label class="Validform_label">司机车辆安排</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">司机姓名：</label>
				</td>
				<td class="value">						  
					<input  class="inputxt" id="driverName" name="driverName" value="${tView.driverName}">  
					<span class="Validform_checktip"></span>
				</td>
				<td align="right">
					<label class="Validform_label">司机手机号：</label>
				</td>
				<td class="value">
					<input  class="inputxt" id="driverMobile" name="driverMobile" value="${tView.driverMobile}">  		
					<span class="Validform_checktip"></span>
				</td>
			<tr>
			<tr>
				<td align="right">
					<label class="Validform_label">车牌号：</label>
				</td>
				<td class="value">						  
					<input  class="inputxt" id="licencePlate" name="licencePlate" value="${tView.licencePlate}">  
					<span class="Validform_checktip"></span>
				</td>
				<td align="right">
					<label class="Validform_label">车辆状态：</label>
				</td>
				<td class="value">
					<input  class="inputxt" id="carStatus" name="carStatus" value="${tView.carStatus}">  		
					<span class="Validform_checktip"></span>
				</td>
			<tr>
			<%-- <tr>
				<td align="right">
					<label class="Validform_label">：</label>
				</td>
				<td class="value">						  
					<input  class="inputxt" id="" name="" value="${tView.}">  
					<span class="Validform_checktip"></span>
				</td>
				<td align="right">
					<label class="Validform_label">：</label>
				</td>
				<td class="value">
					<input  class="inputxt" id="" name="" value="${tView.}">  		
					<span class="Validform_checktip"></span>
				</td>
			<tr> --%>
			
		</table>
	</t:formvalid>
</body>