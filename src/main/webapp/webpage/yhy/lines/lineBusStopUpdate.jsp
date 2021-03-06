<%@ page language="java" import="java.util.*,java.text.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>站点顺序编号</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="lineInfoController.do?saveBusStopOrder">
	<input id="id" name="id" hidden="true" value="${id}">
	<input id="history" name="history" hidden="true" value="${history}">
	<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable" id = "formtableId">
		<tr>
			<td align="right" width="15%" nowrap>
				<label class="Validform_label"> 站点名称: </label>
			</td>
			<td class="value" width="85%">
				<c:if test="${id!=null }">
					     ${name }
				</c:if> 
			</td> 
		</tr>
		<tr>
			<td align="right" width="15%" nowrap>
				<label class="Validform_label"> 站点序号: </label>
			</td>
			
			<td class="value" width="85%">
				<select id="siteOrder" name="siteOrder" datatype="*" <c:if test="${siteOrder == '99' or siteOrder == '0'}">disabled="disabled"</c:if>>
						<option value="">--请选择序号--</option>
						<c:forEach var="b" items="${bussiteOrder}">
							<option value="${b.siteOrder}" <c:if test="${siteOrder == b.siteOrder}" >selected="selected"</c:if>>
								${b.siteOrder}
							</option>
						</c:forEach>
				</select> 
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		<tr>
			<td align="right" width="15%" nowrap>
				<label class="Validform_label"> 估计到站时间: </label>
			</td>
			<td class="value" width="85%">
			<%-- <input name="arrivalTime" class="Wdate" onClick="WdatePicker({dateFmt:'HH:mm'})" style="width: 150px" 
			value="<fmt:formatDate value='${arrivalTime}' type="date" pattern="hh:mm"/>" errormsg="日期格式不正确!"  datatype="*"> 
			 --%>
			<input class="Wdate" onClick="WdatePicker({dateFmt:'HH:mm'})"  style="width: 150px" id="arrivalTime" name="arrivalTime" ignore="ignore"
							     placeholder="时：分" value="<c:if test='${arrivalTime != null and arrivalTime != ""}'><fmt:formatDate value='<%=new SimpleDateFormat( "HH:mm" ).parse(request.getAttribute("arrivalTime").toString()) %>' type="time" pattern="HH:mm"/></c:if>">
						<span class="Validform_checktip"></span>
			</td>
		</tr>

	</table>
</t:formvalid>
<t:authFilter name="formtableId"></t:authFilter>
</body>