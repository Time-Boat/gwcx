<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="plug-in/tools/popup/userSelect.js"></script>
<script src="plug-in/tools/popup/departSelect.js"></script>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="dealerStatisticsList" title="订单收入统计" autoLoadData="true" actionUrl="dealerStatisticsController.do?dealerOrderdatagrid" 
			fitColumns="true" idField="id" fit="true" queryMode="group" checkbox="true">
			<t:dgCol title="id" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="渠道商名称" field="account" align="center"></t:dgCol>
			<t:dgCol title="订单完成时间" field="orderCompletedTime" editor="datebox" formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group" align="center"></t:dgCol>
			<t:dgCol title="订单编号" field="orderId" query="true"></t:dgCol>
			<t:dgCol title="线路名称" field="lineName" query="true" align="center"></t:dgCol>
			<t:dgCol title="线路类型" field="ordertype" replace="接机_2,送机_3,接火车 _4,送火车_5" query="true" align="center"></t:dgCol>
			<t:dgCol title="发车时间" field="orderStartime" align="center"></t:dgCol>
			<t:dgCol title="下单联系人" field="realName" align="center"></t:dgCol>
			<t:dgCol title="乘车联系人" field="orderContactsname" align="center"></t:dgCol>
			<t:dgCol title="联系人手机号" field="orderContactsmobile" align="center"></t:dgCol>
			<t:dgCol title="订单状态" field="orderStatus" replace="订单已完成_0,已付款待审核_1,审核通过待发车 _2,取消订单待退款_3" align="center"></t:dgCol>
			<t:dgCol title="订单数量" field="orderNumbers" align="center"></t:dgCol>
			<t:dgCol title="合计价格" field="orderTotalPrice" align="center"></t:dgCol>
		</t:datagrid>
	</div>

</div>
<div id="total" hidden="true">
	<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;订单总量：</label><label id="orderTotal"></label>
	<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;总收入:</label><label id="totalPrice"></label>

</div>
 <input type="text" value="${accountList}" id="accounts" type="hidden" />
<script type="text/javascript">

	$(document).ready(
			function() {
				$("input[name='orderCompletedTime_begin']").attr("class", "Wdate")
						.click(function() {
							WdatePicker({
								dateFmt : 'yyyy-MM-dd HH:mm:ss'
							});
						});
				$("input[name='orderCompletedTime_end']").attr("class", "Wdate")
						.click(function() {
							WdatePicker({
								dateFmt : 'yyyy-MM-dd HH:mm:ss'
							});
						});
				$(".datagrid-toolbar").append($("#total"));
				$("#total").show();
			});

	function gettotal() {
		var orderCompletedTime_begin = $("input[name='orderCompleteTimed_begin']").val();
		var orderCompletedTime_end = $("input[name='orderCompletedTimed_end']").val();
		var account = $("input[name='account']").val();
		var ordertype = $("select[name='ordertype']").val();

		var lineName = $("input[name='lineName']").val();
		
		//渠道商订单统计
		$.ajax({
			url : "dealerStatisticsController.do?getDealerTotal&orderCompletedTime_begin="
					+ orderCompletedTime_begin
					+ "&orderCompletedTime_end="
					+ orderCompletedTime_end
					+ "&ordertype="
					+ ordertype
					+ "&lineName="
					+ lineName + "&account=" + account,
			dataType : 'json',
			complete : function(data) {
				var message = data.responseText;
				var obj = eval('(' + message + ')');
				$("#orderTotal").html(obj.sumorder+"人");
				$("#totalPrice").html(obj.sumPrice+"元");
				//刷新当前窗体
				reloadTable();
			}
		});
	}
	
	//初始化查询条件
	$(function() {
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
 		$("#dealerStatisticsListForm").append(a1+a2+a3+c1+a4);
		gettotal();
	});

	//查询 
	function OrderStatListsearch() {
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
			$('#dealerStatisticsList').datagrid(
				{
					url : 'dealerStatisticsController.do?orderdatagrid&field=id,orderId,applicationTime,applicationTime_begin,applicationTime_end,orderId,lineName,ordertype,orderStartime,realName,orderContactsname,orderContactsmobile,driverName,licencePlate,orderFlightnumber,orderStatus,orderNumbers,orderTotalPrice,',
					pageNumber : 1
				});
		}
		gettotal();
	}
	
	//重置 
	function searchReset(name) {
		$("#" + name + "tb").find(":input").val("");
		var queryParams = $('#OrderStatList').datagrid('options').queryParams;
		$('#dealerStatisticsListtb').find('*').each(function() {
			queryParams[$(this).attr('name')] = $(this).val();
		});
		$('#dealerStatisticsList')
				.datagrid(
						{
							url : 'dealerStatisticsController.do?orderdatagrid&field=id,orderId,applicationTime,applicationTime_begin,applicationTime_end,orderId,lineName,ordertype,orderStartime,realName,orderContactsname,orderContactsmobile,driverName,licencePlate,orderFlightnumber,orderStatus,orderNumbers,orderTotalPrice,',
							pageNumber : 1
						});
		gettotal();
	}
	
</script>

