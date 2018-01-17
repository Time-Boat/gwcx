<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="userStatList" title="用户汇总统计"
			actionUrl="transferStatisticsController.do?userdatagrid" idField="id"
			fit="true" queryMode="group">

			<t:dgCol title="注册时间" field="createTime" editor="datebox"
				formatter="yyyy-MM-dd hh:mm:ss" query="true" width="120"
				queryMode="group" align="center"></t:dgCol>
			<t:dgCol title="用户名" field="realName" width="80" align="center"></t:dgCol>
			<t:dgCol title="用户类型" field="userType" query="true"
				dictionary="userType" width="80" align="center"></t:dgCol>
			<t:dgCol title="手机号" field="phone" query="true" width="80"
				align="center"></t:dgCol>
			<t:dgCol title="身份证号" field="cardNumber" width="120" align="center"></t:dgCol>
			<t:dgCol title="居住地" field="address" width="120" align="center"></t:dgCol>
			<t:dgCol title="用户来源渠道" field="account" width="80" align="center"></t:dgCol>
			<t:dgCol title="常用地址" field="commonAddr" width="120" align="center"></t:dgCol>
			<t:dgCol title="上次登录时间" field="lastLoginTime" width="120" align="center"></t:dgCol>
			<t:dgCol title="登录次数" field="loginCount" width="40" align="center"></t:dgCol>
		</t:datagrid>
	</div>

	<input type="hidden" value="${cityList}" id="citylie" />
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

	//初始化查询条件
	$(function() {
		$(".datagrid-toolbar")
				.append(
						"<div id='total' hidden='true'><label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;累计用户量：</label><label id='orderTotal'></label> </div>");
		$("#total").show();
		var userType = $("select[name='userType']").val();;
		var phone = $("input[name='phone']").val();
		//用户汇总统计
		gettotal(userType,phone);
	});

	function gettotal(userType,phone) {

		var createTime_begin = $("input[name='createTime_begin']").val();
		var createTime_end = $("input[name='createTime_end']").val();
		 userType = $("select[name='userType']").val();;
		 phone = $("input[name='phone']").val();
		/* //用户汇总统计
		$.ajax({
					url : "transferStatisticsController.do?getUserTotal&createTime_begin="
							+ createTime_begin
							+ "&createTime_end="
							+ createTime_end,
					dataType : 'json',
					complete : function(data) {
						var message = data.responseText;
						var obj = eval('(' + message + ')');
						$("#orderTotal").html(obj.sumorder + "人");
						//刷新当前窗体
						reloadTable();
					}
				}); */

		$.ajax({
			url : "transferStatisticsController.do?userdatagrid&createTime_begin="
				+ createTime_begin+ "&createTime_end="+ createTime_end+"&userType="+userType+"&phone="+phone,
			dataType : 'json',
			complete : function(data) {
				var message = data.responseText;
				var obj = eval('(' + message + ')');
				$("#orderTotal").html(obj.total + "人");
				//刷新当前窗体
				reloadTable();
			}
		});
	}

	function userStatListsearch() {
		/* try {
			if (!$("#userStatListForm").Validform({
				tiptype : 3
			}).check()) {
				return false;
			}
		} catch (e) {
		}
		if (true) {
			var queryParams = $('#userStatList').datagrid('options').queryParams;
			$('#userStatListtb').find('*').each(function() {
				queryParams[$(this).attr('name')] = $(this).val();
			});
			$('#userStatList')
					.datagrid(
							{
								url : 'transferStatisticsController.do?userdatagrid&field=createTime,createTime_begin,createTime_end,realName,phone,cardNumber,address,wwwww,commonAddr,loginCount,',
								pageNumber : 1
							});
		} */
		//gettotal();
		try {
			if (!$("#userStatListForm").Validform({
				tiptype : 3
			}).check()) {
				return false;
			}
		} catch (e) {
		}
		if (true) {
			var queryParams = $('#userStatList').datagrid('options').queryParams;
			
			$('#userStatListtb').find('*').each(function() {
				queryParams[$(this).attr('name')] = $(this).val();
			});
			$('#userStatList').datagrid(
				{
					url : 'transferStatisticsController.do?userdatagrid&field=createTime,createTime_begin,createTime_end,realName,userType,phone,cardNumber,address,account,commonAddr,loginCount,',
					pageNumber : 1
				});
		}
		$("#orderTotal").html("");
		
		var userType = $("select[name='userType']").val();
		var phone = $("input[name='phone']").val();
		gettotal(userType,phone);
	}

	function searchReset(name) {
		$("#" + name + "tb").find(":input").val("");
		var queryParams = $('#userStatList').datagrid('options').queryParams;
		$('#userStatListtb').find('*').each(function() {
			queryParams[$(this).attr('name')] = $(this).val();
		});
		
		$('#userStatList')
				.datagrid(
						{
							url : 'transferStatisticsController.do?userdatagrid&field=createTime,createTime_begin,createTime_end,realName,phone,cardNumber,address,wwwww,commonAddr,loginCount,',
							pageNumber : 1
						});
		
		var userType = $("select[name='userType']").val();;
		var phone = $("input[name='phone']").val();
		gettotal(userType,phone);
	}
</script>