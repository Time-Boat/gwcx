<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新增验票员</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body style="" scroll="no">
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="lineInfoController.do?save">
	<input id="id" name="id" type="hidden" value="${lineInfo.id }">
	<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable" id = "formtableId">
		<%-- <tr>
		${lineInfo.id}
		${lineInfo.name}
		${lineInfo.startLocation}
		${lineInfo.startX}
		${lineInfo.startY}
		${lineInfo.endLocation}
		${lineInfo.endX}
		${lineInfo.endY}
		${lineInfo.remark}
		${lineInfo.imageurl}
		${lineInfo.type}
		${lineInfo.status}
		${lineInfo.deleteFlag}
		</tr> --%>
		<tr>
			<td align="right" width="15%" nowrap>
				<label class="Validform_label"> 线路名称: </label>
			</td>
			<td class="value" width="85%">
			<input id="name" name="name" value="${lineInfo.name}" type="text" style="width: 60%" class="inputxt"  datatype="*"> 
				<span class="Validform_checktip"></span> 
			</td>
			
			<%-- <td class="value" width="85%">
				<c:if test="${lineInfo.id!=null }">
					     ${lineInfo.name }
				</c:if> 
				<c:if test="${lineInfo.id==null }">
					<input id="name" class="inputxt" name="name" value="${lineInfo.name}" datatype="*">
					<span class="Validform_checktip">范围在2~10位字符</span>
				</c:if>
			</td> --%>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label"> 起始发车地址: </label>
			</td>
			<td class="value">
				<input class="inputxt" name="startLocation" value="${lineInfo.startLocation}" style="width: 60%" datatype="*"> 
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label"> 终点位置地址: </label>
			</td>
			<td class="value">
				<input class="inputxt" name="endLocation" value="${lineInfo.endLocation}" style="width: 60%" datatype="*"> 
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label"> 线路状态: </label>
			</td>
			<td class="value">
				
					<t:dictSelect field="status" typeGroupCode="lineStatus" hasLabel="false" defaultVal="${lineInfo.status}" datatype="*"></t:dictSelect>	
				
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label"> 线路类型: </label>
			</td>
			<td class="value">
				<!-- 这里面写死，因为这个线路添加中只有班车类型的添加 -->
				<t:dictSelect field="type" typeGroupCode="busType" hasLabel="false" defaultVal="0" datatype="*" readonly="readonly" ></t:dictSelect>	
				
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label"> 选择入驻公司: </label>
			</td>
			<td class="value">
				<select name="settledCompany">
						<c:choose>
							<c:when test="${lineInfo.settledCompanyId !=null }">
								<option value="${lineInfo.settledCompanyId}">${lineInfo.settledCompanyName}</option>
							</c:when>
							<c:otherwise>
								<option value="">--请选择入驻公司--</option>
							</c:otherwise>
						</c:choose>
						<c:forEach var="settledCompany" items="${list}">
							<option value="${settledCompany.id}">
								${settledCompany.departname}
							</option>
						</c:forEach>
				</select> 
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label"> 选择线路城市: </label>
			</td>
			<td class="value">
				<select name="city" datatype="*">
						<option value="">--请选择城市--</option>
						<c:forEach var="c" items="${cities}" >
							<option value="${c.cityId}" <c:if test="${lineInfo.cityId == c.cityId}" >selected="selected"</c:if> >
								${c.cityName}
							</option>
						</c:forEach>
				</select> 
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		
		
		<%-- <tr>
			<td align="right">
				<label class="Validform_label"> 发车时间: </label>
			</td>
			<td class="value">
				<input name="lstartTime" class="Wdate" onClick="WdatePicker({dateFmt:'HH:mm:ss'})" style="width: 150px" value="<fmt:formatDate value='${lineInfo.lstartTime }' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>" errormsg="日期格式不正确!"  datatype="*"> 
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label"> 预计到达时间: </label>
			</td>
			<td class="value">
				<input name="lendTime" class="Wdate" onClick="WdatePicker({dateFmt:'HH:mm:ss'})" style="width: 150px" value="<fmt:formatDate value='${lineInfo.lendTime }' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>" errormsg="日期格式不正确!"  datatype="*"> 
				<span class="Validform_checktip"></span>
			</td>
		</tr> --%>
		
		<tr>
			<td align="right">
				<label class="Validform_label"> 线路定价(人/元): </label>
			</td>
			<td class="value">
				<input class="inputxt" name="price" value="${lineInfo.price}" style="width: 60%" datatype="d"> 
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		
		<tr>
			<td align="right">
				<label class="Validform_label"> 线路备注: </label>
			</td>
			<td class="value">
				<textarea id="remark" name="remark" cols="60" rows="6">${lineInfo.remark}</textarea>
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		
		
		
		<%-- 
		<tr>
			<td align="right">
				<label class="Validform_label"> 线路图片: </label>
			</td>
			<td class="value">
				<input class="inputxt" name="endLocation" value="${lineInfo.endLocation}" datatype="*"> 
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label"> 线路类型: </label>
			</td>
			<td class="value">
				<input class="inputxt" name="endLocation" value="${lineInfo.endLocation}" datatype="*"> 
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label"> 起始位置X坐标: </label>
			</td>
			<td class="value">
				<input class="inputxt" name="startX" value="${lineInfo.startX}" datatype="*"> 
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label"> 起始位置Y坐标: </label>
			</td>
			<td class="value">
				<input class="inputxt" name="startY" value="${lineInfo.startY}" datatype="*"> 
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label"> 终点位置X坐标: </label>
			</td>
			<td class="value">
				<input class="inputxt" name="endX" value="${lineInfo.endX}" datatype="*"> 
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label"> 终点位置Y坐标: </label>
			</td>
			<td class="value">
				<input class="inputxt" name="endY" value="${lineInfo.endY}" datatype="*"> 
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		 --%>
	</table>
</t:formvalid>
<t:authFilter name="formtableId"></t:authFilter>
</body>