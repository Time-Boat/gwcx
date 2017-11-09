<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>验票员详情</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

</head>
<body style="overflow-y: hidden" scroll="no">
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table">

		<table style="width: 700px;" cellpadding="0" cellspacing="1"
			class="formtable" id="formtableId">
			<tr>
				<td align="right" width="15%" nowrap><label
					class="Validform_label"> 用户名: </label></td>
				<td class="value"><input id="name" name="name"
					value="${conductor.name }" type="text" style="width: 150px"
					class="inputxt" /> <span class="Validform_checktip"></span></td>

				<td align="right" nowrap><label class="Validform_label">
						手机号码: </label></td>
				<td class="value"><input class="inputxt" id="phoneNumber"
					name="phoneNumber" value="${conductor.phoneNumber}" /> <span
					id="check_phone" class="Validform_checktip"></span></td>
			</tr>

			<tr>
				<td align="right"><label class="Validform_label"> 年龄: </label></td>
				<td class="value"><input class="inputxt" name="age"
					value="${conductor.age}"> <span class="Validform_checktip"></span></td>
				<td align="right"><label class="Validform_label"> 性别: </label></td>
				<td class="value"><t:dictSelect field="sex" typeGroupCode="sex"
						hasLabel="false" defaultVal="${conductor.sex}"></t:dictSelect> <span
					class="Validform_checktip"></span></td>

			</tr>

			<tr>
				<td align="right"><label class="Validform_label"> 业务类型:
				</label></td>
				<td class="value"><t:dictSelect id="status" field="status"
						typeGroupCode="carBType" hasLabel="false"
						defaultVal="${conductor.status}"></t:dictSelect> <span
					class="Validform_checktip"></span></td>

				<td align="right"><label class="Validform_label">验票员状态:
				</label></td>
				<td class="value"><t:dictSelect id="conductStatus"
						field="conductStatus" typeGroupCode="driver_status"
						hasLabel="false" defaultVal="${conductor.conductStatus}"></t:dictSelect>
					<span class="Validform_checktip"></span></td>
			</tr>

			<tr>
				<td align="right"><label class="Validform_label">申请人: </label></td>
				<td class="value"><input class="inputxt" name="applicationUser"
					value="${user.userName}"> <span class="Validform_checktip"></span></td>

				<td align="right"><label class="Validform_label">申请时间:
				</label></td>
				<td class="value"><input class="Wdate"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
					style="width: 150px" id="applicationTime" name="applicationTime"
					value="<fmt:formatDate value='${conductor.applicationTime}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>">
					<span class="Validform_checktip"></span></td>
			</tr>

			<tr>
				<td align="right"><label class="Validform_label"> 审核人:
				</label></td>
				<td class="value"><input class="inputxt" id="auditorUserName"
					name="auditorUserName" value="${auditor.userName}"> <span
					class="Validform_checktip"></span></td>
				<td align="right"><label class="Validform_label"> 审核时间:
				</label></td>
				<td class="value"><input class="Wdate"
					onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
					style="width: 150px" id="auditTime" name="auditTime"
					value="<fmt:formatDate value='${conductor.auditTime}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>">
					<span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 审核状态:
				</label></td>
				<td class="value"><t:dictSelect field="applicationStatus"
						typeGroupCode="audit_status" hasLabel="false"
						defaultVal="${conductor.applicationStatus}"></t:dictSelect>
					<span class="Validform_checktip"></span></td>
				<td align="right"><label class="Validform_label"> 申请内容:
				</label></td>
				<td class="value"><t:dictSelect field="applyContent"
						typeGroupCode="apply_type" hasLabel="false"
						defaultVal="${conductor.applyContent}"></t:dictSelect> <span
					class="Validform_checktip"></span></td>
			</tr>

			<tr>
				<td align="right"><label class="Validform_label"> 验票线路:
				</label></td>
				<td class="value" align="left" colspan="3"><textarea 
						style=" width: 500px; height: 66px;"
						readonly="readonly" rows="2">${lineNames}</textarea>
					<span class="Validform_checktip"></span></td>
			</tr>

		</table>
	</t:formvalid>
	<t:authFilter name="formtableId"></t:authFilter>
</body>