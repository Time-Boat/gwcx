<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="channelUserStatList" title="渠道用户统计"
			actionUrl="transferStatisticsController.do?channelUserdatagrid" idField="id"
			fit="true" checkbox="true" queryMode="group">
			<t:dgCol title="渠道商名称" field="account" width="120" align="center" ></t:dgCol>
			<t:dgCol title="注册时间" field="createTime" editor="datebox"
				formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group"
				align="center"></t:dgCol>
			<t:dgCol title="用户名" field="realName" width="120" align="center"></t:dgCol>
			<t:dgCol title="手机号" field="phone" width="120" align="center"></t:dgCol>
			<t:dgCol title="身份证号" field="cardNumber" width="120" align="center"></t:dgCol>
			<t:dgCol title="居住地" field="address" width="120" align="center"></t:dgCol>
			<t:dgCol title="登录次数" field="carType" width="120" align="center"></t:dgCol>
		</t:datagrid>
	</div>
</div>
 <input type="text" value="${accountList}" id="accounts" />
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
	
	
	 
 	$(function(){
 		var json = $("#accounts").val();
 		var obj = eval('(' + json + ')');
 		var a1 = '<span style="display:-moz-inline-box;display:inline-block;"><span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;';
 		var a2 = 'text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; "title="渠道商名称">渠道商名称：</span>';
 		var a3 = '<select name="accountId" style="width: 150px">';
 		var c1 = '<option value="">选择渠道商</option>';
		for(var i=0;i<obj.data.length;i++){
			c1 += '<option value="'+obj.data[i].id+'">'+obj.data[i].account+'</option>';
		}
 		var a4 = '</select></span>';
 		$("#channelUserStatListForm").append(a1+a2+a3+c1+a4);
 	});
 	
	
</script>



  


