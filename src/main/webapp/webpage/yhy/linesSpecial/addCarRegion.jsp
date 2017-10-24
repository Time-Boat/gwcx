<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>添加座位区间价格</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">

$(function() {
	<c:forEach items="${carlist}" var="car" >  
    $("input[name='typeid']").each(function(){
    	var a = $("#typeid").val();
    	var c= $(this).val()
    	var b= "${car.carTypeId}";
        if(c=="${car.carTypeId}"){ 
        	$("#${car.carTypeId}").val("${car.carTypePrice}");
        }  
    }) 
	</c:forEach>
})
</script>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="lineInfoSpecializedController.do?savePrice" >
	<input id="lineId" name="lineId" type="hidden" value="${lineId}">
		<table style="width: 500px;" cellpadding="0" cellspacing="1" class="formtable" id="formtableId">
			
			<c:forEach var="c" items="${typelist}">
				<tr>
				<td align="right">
					<label class="Validform_label">${c.typename}:</label>
				</td>
				<td class="value">
					<input id="typeid" name="typeid" type="hidden" value="${c.id}">				  
					<input  class="inputxt" id="${c.id}" name="price" required="required" >&nbsp;&nbsp;元  
					<span class="Validform_checktip"></span>
				</td>
				</tr>
					
			</c:forEach>
			
		</table>
	</t:formvalid>
</body>