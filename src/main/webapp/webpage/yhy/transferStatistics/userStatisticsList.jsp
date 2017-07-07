<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="userStatList" title="用户汇总统计"
			actionUrl="transferStatisticsController.do?userdatagrid" idField="id"
			fit="true" checkbox="true" queryMode="group">

			<t:dgCol title="注册时间" field="createTime" editor="datebox"
				formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group"
				align="center"></t:dgCol>
			<t:dgCol title="用户名" field="realName" width="120" align="center"></t:dgCol>
			<t:dgCol title="手机号" field="phone" width="120" align="center"></t:dgCol>
			<t:dgCol title="身份证号" field="cardNumber" width="120" align="center"></t:dgCol>
			<t:dgCol title="居住地" field="address" width="120" align="center"></t:dgCol>
			<t:dgCol title="用户来源渠道" field="wwwww" width="120" align="center"></t:dgCol>
			<t:dgCol title="常用地址" field="commonAddr" width="120" align="center"></t:dgCol>
			<t:dgCol title="登录次数" field="carType" width="120" align="center"></t:dgCol>
		</t:datagrid>
	</div>
</div>
<script type="text/javascript">
	$(document).ready(
			function() {
				$("input[name='createTime_begin']").attr("class", "Wdate")
						.click(function() {
							WdatePicker({
								dateFmt : 'yyyy-MM-dd HH:mm:ss'
							});
						});
				$("input[name='createTime_end']").attr("class", "Wdate").click(
						function() {
							WdatePicker({
								dateFmt : 'yyyy-MM-dd HH:mm:ss'
							});
						});
			});
	
</script>

