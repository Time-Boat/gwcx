<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>订单详情</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
	function complaint(){
		var res = "客服经理已发起退款，款项最迟在3-5个工作日返回原支付账户，给您造成了不便。深感抱歉！";
		var handleResult = $("#handleResult").val();
		if(handleResult=='0'){
			$("#handleContent").val(res);
		}else{
			$("#handleContent").empty();//置空 
		}
	}

</script>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="transferOrderController.do?saveComplaint">
	<input id="id" name="id" type="hidden" value="${complaint.id }">
		<table style="width: 500px;" cellpadding="0" cellspacing="1" class="formtable" id="formtableId">
			
			<tr>
				<td align="right"><label class="Validform_label">处理意见：</label></td>
				<td class="value">						  
					<t:dictSelect id="handleResult" field="handleResult" typeGroupCode="handle_result" hasLabel="false" 
					defaultVal="${complaint.handleResult}" extendJson="{onchange:complaint()}"></t:dictSelect>
					<span class="Validform_checktip"></span>
				</td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">处理详情：</label></td>
				<td class="value">
					<textarea id="handleContent" name="handleContent" rows="7" cols= "50" datatype="*">${complaint.handleContent} </textarea>
					<span class="Validform_checktip"></span>
				</td>
			<tr>
			
		</table>
	</t:formvalid>
	
</body>

