<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>消息详情</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<!-- 多选框样式 -->
<style type="text/css">
.demo--label {
	margin: 10px 20px 0 10px;
	display: inline-block;
	height: 30px
}

.demo--radio {
	display: none
}

.demo--radioInput {
	background-color: #fff;
	border: 1px solid rgba(0, 0, 0, 0.15);
	border-radius: 100%;
	display: inline-block;
	height: 16px;
	margin-right: 10px;
	margin-top: -1px;
	vertical-align: middle;
	width: 16px;
	line-height: 1
}

.demo--radio:checked+.demo--radioInput:after {
	background-color: #57ad68;
	border-radius: 100%;
	content: "";
	display: inline-block;
	height: 12px;
	margin: 2px;
	width: 12px
}

.demo--checkbox.demo--radioInput, .demo--radio:checked+.demo--checkbox.demo--radioInput:after
	{
	border-radius: 0
}
</style>
</head>
<body style="overflow-y: hidden" scroll="no">
	<t:formvalid formid="formobj" dialog="true" usePlugin="password" refresh="true" 
		layout="table" action="NotificationRecordController.do?save">
		<input id="id" name="id" type="hidden"
			value="${notificationRecordPage.id }">
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">
			<tr>
				<td align="right"><label class="Validform_label"> 通知标题:
				</label></td>
				<td class="value"><input class="inputxt" id="title"
					name="title" ignore="ignore"
					value="${notificationRecordPage.title}"> <span
					class="Validform_checktip"></span></td>
			</tr>
			
			<tr>
				<td align="right"><label class="Validform_label"> 发送时间:
				</label></td>
				<td class="value"><input class="Wdate"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
					style="width: 150px" id="sendTime" name="sendTime" ignore="ignore"
					value="<fmt:formatDate value='${notificationRecordPage.sendTime}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>">
					<span class="Validform_checktip"></span></td>
			</tr>
			<tr>
					<td align="right">
						<label class="Validform_label">
							通知方式:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="nType" name="nType" ignore="ignore"
							   value="${notification}">
					</td>
			</tr>
			
			<tr>
				<td align="right"><label class="Validform_label"> 创建时间:
				</label></td>
				<td class="value"><input class="Wdate"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
					style="width: 150px" id="createTime" name="createTime"
					ignore="ignore"
					value="<fmt:formatDate value='${notificationRecordPage.createTime}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>">
					<span class="Validform_checktip"></span></td>
			</tr>
			<%-- <tr>
				<td align="right"><label class="Validform_label"> 状态: </label>
				</td>
				<td class="value">
				<input class="inputxt" id="status"
					name="status" ignore="ignore"
					value="${notificationRecordPage.status}">
				<t:dictSelect id="status" field="status" typeGroupCode="read_status" hasLabel="false" defaultVal="${notificationRecordPage.status}" ></t:dictSelect>
				<span class="Validform_checktip"></span></td>
			</tr> --%>
			<tr>
				<td align="right"><label class="Validform_label"> 通知内容:
				</label></td>
				<td class="value">
				<textarea  cols="60" rows="6" id="content"
					name="content">${notificationRecordPage.content}</textarea>
				 <span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 备注: </label>
				</td>
				<td class="value">
				<textarea cols="60" rows="6" id="remark"
					name="remark">${notificationRecordPage.remark}</textarea>
				 <span class="Validform_checktip"></span></td>
				
			</tr>
		</table>
	</t:formvalid>
</body>