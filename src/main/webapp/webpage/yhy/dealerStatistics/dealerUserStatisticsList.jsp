<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="dealerStatisticsList" title="用户汇总统计"
			actionUrl="dealerStatisticsController.do?dealerUserdatagrid"
			idField="id" fit="true" queryMode="group">
			<t:dgCol title="渠道商名称" field="account" width="120" align="center"></t:dgCol>
			<t:dgCol title="注册时间" field="createTime" editor="datebox" width="150"
				formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group"
				align="center"></t:dgCol>
			<t:dgCol title="用户名" field="realName" width="50" align="center"></t:dgCol>
			<t:dgCol title="手机号" field="phone" width="120" align="center"></t:dgCol>
			<t:dgCol title="身份证号" field="cardNumber" width="120" align="center"></t:dgCol>
			<t:dgCol title="居住地" field="address" width="120" align="center"></t:dgCol>
			<t:dgCol title="登录次数" field="loginCount" width="120" align="center"></t:dgCol>
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

	$(function() {
		var json = $("#accounts").val();
		
		var a1 = '<span style="display:-moz-inline-box;display:inline-block; padding:0px 10px;"><span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;';
 		var a2 = 'text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; "title="渠道商名称">渠道商名称：</span>';
		var a3 = '<select name="accountId" style="width: 150px">';
		var c1 = '<option value="">选择渠道商</option>';
		if(json.indexOf("account")>0){
			var obj = eval('(' + json + ')');
			for (var i = 0; i < obj.data.length; i++) {
				c1 += '<option value="'+obj.data[i].id+'">' + obj.data[i].account+ '</option>';
			}
		}
		var a4 = '</select></span>';
		$("#dealerStatisticsListForm").append(a1 + a2 + a3 + c1 + a4);
		
		$(".datagrid-toolbar")
				.append(
						"<div id='total' hidden='true'><label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;渠道商累计用户量：</label><label id='orderTotal'></label> </div>");
		$("#total").show();
		gettotal();
	});

	function gettotal() {
		var createTime_begin = $("input[name='createTime_begin']").val();
		var createTime_end = $("input[name='createTime_end']").val();
		var accountId = $("select[name='accountId']").val();
		//渠道商用户统计
		$.ajax({
					url : "dealerStatisticsController.do?getUserTotal&createTime_begin="
							+ createTime_begin
							+ "&createTime_end="
							+ createTime_end + "&accountId=" + accountId,
					dataType : 'json',
					complete : function(data) {
						var message = data.responseText;
						var obj = eval('(' + message + ')');
						$("#orderTotal").html(obj.sumorder + "人");
						//刷新当前窗体
						reloadTable();
					}
				});
	}

	function dealerStatisticsListsearch() {
		try {
			if (!$("#dealerStatisticsListForm").Validform({
				tiptype : 3
			}).check()) {
				return false;
			}
		} catch (e) {
		}
		if (true) {
			var queryParams = $('#dealerStatisticsList').datagrid('options').queryParams;
			$('#dealerStatisticsListtb').find('*').each(function() {
				queryParams[$(this).attr('name')] = $(this).val();
			});
			$('#dealerStatisticsList')
					.datagrid(
							{
								url : 'dealerStatisticsController.do?dealerUserdatagrid&field=account,createTime,createTime_begin,createTime_end,realName,phone,cardNumber,address,loginCount,',
								pageNumber : 1
							});
		}
		gettotal();
	}

	function searchReset(name) {
		$("#" + name + "tb").find(":input").val("");
		var queryParams = $('#dealerStatisticsList').datagrid('options').queryParams;
		$('#dealerStatisticsListtb').find('*').each(function() {
			queryParams[$(this).attr('name')] = $(this).val();
		});
		$('#dealerStatisticsList')
				.datagrid(
						{
							url : 'dealerStatisticsController.do?dealerUserdatagrid&field=account,createTime,createTime_begin,createTime_end,realName,phone,cardNumber,address,loginCount,',
							pageNumber : 1
						});
		gettotal();
	}
</script>






