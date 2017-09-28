<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="plug-in/tools/popup/userSelect.js"></script>
<script src="plug-in/tools/popup/departSelect.js"></script>

<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="RefundStatList" title="退款给统计" autoLoadData="true"
			actionUrl="transferStatisticsController.do?refunddatagrid"
			fitColumns="true" idField="id" fit="true" queryMode="group">
			<t:dgCol title="id" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="退款执行时间" field="refundCompletedTime" editor="datebox"
				formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group"
				align="center"></t:dgCol>
			<t:dgCol title="订单编号" field="orderId" query="true"></t:dgCol>
			<t:dgCol title="线路名称" field="lineName" align="center" query="true"></t:dgCol>
			<t:dgCol title="线路类型" field="ordertype"
				replace="接机_2,送机_3,接火车 _4,送火车_5" query="true" align="center"></t:dgCol>
			<t:dgCol title="退款申请时间" field="refundTime" editor="datebox" formatter="yyyy-MM-dd hh:mm:ss" align="center"></t:dgCol>
			<t:dgCol title="下单联系人" field="realName" align="center"></t:dgCol>
			<t:dgCol title="乘车联系人" field="orderContactsname" align="center"></t:dgCol>
			<t:dgCol title="联系人手机号" field="orderContactsmobile" align="center"></t:dgCol>
			<t:dgCol title="所属公司" field="departname" query="true" align="center"></t:dgCol>
			<t:dgCol title="司机名称" field="driverName" align="center"></t:dgCol>
			<t:dgCol title="车牌号" field="licencePlate" align="center"></t:dgCol>
			<t:dgCol title="退票数量" field="orderNumbers" align="center"></t:dgCol>
			<t:dgCol title="退款金额" field="refundPrice" align="center"></t:dgCol>
		</t:datagrid>
	</div>
</div>
<div id="total" hidden="true">
	<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;退票人数：</label><label
		id="orderTotal"></label> <label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;退票总额:</label><label
		id="totalPrice"></label>

</div>
<script type="text/javascript">
	$(document).ready(
			function() {
				$("input[name='refundCompletedTime_begin']").attr("class",
						"Wdate").click(function() {
					WdatePicker({
						dateFmt : 'yyyy-MM-dd HH:mm:ss'
					});
				});
				$("input[name='refundCompletedTime_end']").attr("class",
						"Wdate").click(function() {
					WdatePicker({
						dateFmt : 'yyyy-MM-dd HH:mm:ss'
					});
				});
				$(".datagrid-toolbar").append($("#total"));
				$("#total").show();
			});

	function gettotal() {
		var refundCompletedTime_begin = $(
				"input[name='refundCompletedTime_begin']").val();
		var refundCompletedTime_end = $("input[name='refundCompletedTime_end']")
				.val();
		var ordertype = $("select[name='ordertype']").val();
		var lineName = $("input[name='lineName']").val();
		var orderId = $("input[name='orderId']").val();

		$.ajax({
					url : "transferStatisticsController.do?getRefundTotal&refundCompletedTime_begin="
							+ refundCompletedTime_begin
							+ "&refundCompletedTime_end="
							+ refundCompletedTime_end
							+ "&ordertype="
							+ ordertype + "&lineName=" + lineName+"&orderId="+orderId,
					dataType : 'json',
					complete : function(data) {
						var message = data.responseText;
						var obj = eval('(' + message + ')');
						$("#orderTotal").html(obj.sumorder + "人");
						$("#totalPrice").html(obj.sumPrice + "元");
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
	function RefundStatListsearch() {
		try {
			if (!$("#RefundStatListForm").Validform({
				tiptype : 3
			}).check()) {
				return false;
			}
		} catch (e) {
		}
		if (true) {
			var queryParams = $('#RefundStatList').datagrid('options').queryParams;
			$('#RefundStatListtb').find('*').each(function() {
				queryParams[$(this).attr('name')] = $(this).val();
			});
			$('#RefundStatList')
					.datagrid(
							{
								url : 'transferStatisticsController.do?refunddatagrid&field=id,orderId,refundCompletedTime,refundCompletedTime_begin,refundCompletedTime_end,orderId,lineName,ordertype,refundTime,realName,orderContactsname,orderContactsmobile,driverName,licencePlate,orderNumbers,orderTotalPrice,',
								pageNumber : 1
							});
		}
		gettotal();
	}

	//重置
	function searchReset(name) {
		$("#" + name + "tb").find(":input").val("");
		var queryParams = $('#RefundStatList').datagrid('options').queryParams;
		$('#RefundStatListtb').find('*').each(function() {
			queryParams[$(this).attr('name')] = $(this).val();
		});
		$('#RefundStatList')
				.datagrid(
						{
							url : 'transferStatisticsController.do?refunddatagrid&field=id,orderId,refundCompletedTime,refundCompletedTime_begin,refundCompletedTime_end,orderId,lineName,ordertype,refundTime,realName,orderContactsname,orderContactsmobile,driverName,licencePlate,orderNumbers,orderTotalPrice,',
							pageNumber : 1
						});
		gettotal();
	}
</script>
