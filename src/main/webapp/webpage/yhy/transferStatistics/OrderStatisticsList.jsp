<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="plug-in/tools/popup/userSelect.js"></script>
<script src="plug-in/tools/popup/departSelect.js"></script>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="OrderStatList" title="订单收入统计" autoLoadData="true"
			actionUrl="transferStatisticsController.do?orderdatagrid"
			fitColumns="true" idField="id" fit="true" queryMode="group"
			checkbox="true">
			<t:dgCol title="id" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="下单时间" field="applicationTime" editor="datebox"
				formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group"
				align="center"></t:dgCol>
			<t:dgCol title="订单编号" field="orderId" query="true"></t:dgCol>
			<t:dgCol title="线路名称" field="lineName" query="true" align="center"></t:dgCol>
			<t:dgCol title="线路类型" field="ordertype"
				replace="接机_2,送机_3,接火车 _4,送火车_5" query="true" align="center"></t:dgCol>
			<t:dgCol title="发车时间" field="orderStartime" align="center"></t:dgCol>
			<t:dgCol title="下单联系人" field="realName" align="center"></t:dgCol>
			<t:dgCol title="乘车联系人" field="orderContactsname" align="center"></t:dgCol>
			<t:dgCol title="联系人手机号" field="orderContactsmobile" align="center"></t:dgCol>
			<t:dgCol title="司机名称" field="driverName" align="center" query="true"></t:dgCol>
			<t:dgCol title="车牌号" field="licencePlate" align="center"></t:dgCol>
			<t:dgCol title="订单来源渠道" field="orderFlightnumber" align="center"></t:dgCol>
			<t:dgCol title="订单状态" field="orderStatus"
				replace="订单已完成_0,已付款待审核_1,审核通过待发车 _2,取消订单待退款_3" query="true"
				align="center"></t:dgCol>
			<t:dgCol title="订单数量" field="orderNumbers" align="center"></t:dgCol>
			<t:dgCol title="合计价格" field="orderTotalPrice" align="center"></t:dgCol>
		</t:datagrid>
	</div>

</div>
<div id="total" hidden="true">
	<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;订单总量：</label><label id="orderTotal"></label>
	<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;总收入:</label><label id="totalPrice"></label>

</div>
<script type="text/javascript">
	$(document).ready(
			function() {
				$("input[name='applicationTime_begin']").attr("class", "Wdate")
						.click(function() {
							WdatePicker({
								dateFmt : 'yyyy-MM-dd HH:mm:ss'
							});
						});
				$("input[name='applicationTime_end']").attr("class", "Wdate")
						.click(function() {
							WdatePicker({
								dateFmt : 'yyyy-MM-dd HH:mm:ss'
							});
						});
				$(".datagrid-toolbar").append($("#total"));
				$("#total").show();
			});

	function gettotal() {
		var applicationTime_begin = $("input[name='applicationTime_begin']")
				.val();
		var applicationTime_end = $("input[name='applicationTime_end']").val();
		var ordertype = $("select[name='ordertype']").val();
		var orderStatus = $("select[name='orderStatus']").val();
		var orderId = $("input[name='orderId']").val();

		var lineName = $("input[name='lineName']").val();
		var driverName = $("input[name='driverName']").val();

		$.ajax({
					url : "transferStatisticsController.do?getOrderTotal&applicationTime_begin="
							+ applicationTime_begin
							+ "&applicationTime_end="
							+ applicationTime_end
							+ "&ordertype="
							+ ordertype
							+ "&orderStatus="
							+ orderStatus
							+ "&lineName="
							+ lineName + "&driverName=" + driverName+"&orderId="+orderId,
					dataType : 'json',
					complete : function(data) {
						var message = data.responseText;
						var obj = eval('(' + message + ')');
						$("#orderTotal").html(obj.sumorder+"张");
						$("#totalPrice").html(obj.sumPrice+"元");
						//刷新当前窗体
						reloadTable();
					}
				});
	}
	//进入触发 
	$(function() {
		gettotal();
	});

	//查询 
	function OrderStatListsearch() {
		try {
			if (!$("#OrderStatListForm").Validform({
				tiptype : 3
			}).check()) {
				return false;
			}
		} catch (e) {
		}
		if (true) {
			var queryParams = $('#OrderStatList').datagrid('options').queryParams;
			$('#OrderStatListtb').find('*').each(function() {
				queryParams[$(this).attr('name')] = $(this).val();
			});
			$('#OrderStatList')
					.datagrid(
							{
								url : 'transferStatisticsController.do?orderdatagrid&field=id,orderId,applicationTime,applicationTime_begin,applicationTime_end,orderId,lineName,ordertype,orderStartime,realName,orderContactsname,orderContactsmobile,driverName,licencePlate,orderFlightnumber,orderStatus,orderNumbers,orderTotalPrice,',
								pageNumber : 1
							});
		}
		gettotal();
	}
	//重置 
	function searchReset(name) {
		$("#" + name + "tb").find(":input").val("");
		var queryParams = $('#OrderStatList').datagrid('options').queryParams;
		$('#OrderStatListtb').find('*').each(function() {
			queryParams[$(this).attr('name')] = $(this).val();
		});
		$('#OrderStatList')
				.datagrid(
						{
							url : 'transferStatisticsController.do?orderdatagrid&field=id,orderId,applicationTime,applicationTime_begin,applicationTime_end,orderId,lineName,ordertype,orderStartime,realName,orderContactsname,orderContactsmobile,driverName,licencePlate,orderFlightnumber,orderStatus,orderNumbers,orderTotalPrice,',
							pageNumber : 1
						});
		gettotal();
	}
</script>

