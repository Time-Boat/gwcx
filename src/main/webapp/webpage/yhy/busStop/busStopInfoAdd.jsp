<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新增验票员</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="busStopInfoController.do?save">
	<input id="id" name="id" type="hidden" value="${busStopInfo.id }">
	<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable" id = "formtableId">
		<tr>
			<td align="right" width="15%" nowrap>
				<label class="Validform_label"> 站点名称: </label>
			</td>
			<td class="value" width="85%">
			<input id="name" name="name" value="${busStopInfo.name }" type="text" style="width: 60%" class="inputxt"  datatype="*"> 
				<span class="Validform_checktip"></span> 
			</td>
		</tr>
		<tr>
			<td align="right" width="15%" nowrap>
				<label class="Validform_label"> 站点地址: </label>
			</td>
			<td class="value" width="85%">
			<input id="stopLocation" name="stopLocation" value="${busStopInfo.stopLocation }" type="text" style="width: 60%" class="inputxt"  datatype="*"> 
				<span class="Validform_checktip"></span> 
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label">站点类型: </label>
			</td>
			<td class="value">
				<t:dictSelect field="stationType" typeGroupCode="sType" hasLabel="false" defaultVal="${busStopInfo.stationType}" datatype="*"></t:dictSelect>	
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label"> 选择线路城市: </label>
			</td>
			<td class="value">
				<select name="city">
						<option value="">--请选择城市--</option>
						<c:forEach var="c" items="${cities}">
							<option value="${c.cityId}" <c:if test="${busStopInfo.cityId == c.cityId}" >selected="selected"</c:if>  >
								${c.cityName}
							</option>
						</c:forEach>
				</select> 
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label"> 站点备注: </label>
			</td>
			<td class="value">
				<textarea id="esContent" name="remark" cols="60" rows="6">${busStopInfo.remark}</textarea>
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		
	</table>
</t:formvalid>
<t:authFilter name="formtableId"></t:authFilter>
</body>