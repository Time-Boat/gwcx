<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="plug-in/tools/popup/userSelect.js"></script>
<script src="plug-in/tools/popup/departSelect.js"></script>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="dealerStatisticsList" title="订单收入统计"
			autoLoadData="true"
			actionUrl="dealerStatisticsController.do?dealerOrderdatagrid"
			fitColumns="true" idField="id" fit="true" queryMode="group">
			<t:dgCol title="id" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="渠道商名称" field="account" align="center"></t:dgCol>
			<t:dgCol title="线路类型" field="ordertype"
				replace="接机_2,送机_3,接火车 _4,送火车_5" query="true" align="center"></t:dgCol>
			<t:dgCol title="订单完成时间" field="orderCompletedTime" editor="datebox"
				formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group"
				align="center"></t:dgCol>
			<t:dgCol title="订单编号" field="orderId" query="true"></t:dgCol>
			<t:dgCol title="线路名称" field="lineName" align="center"></t:dgCol>
			
			<t:dgCol title="发车时间" field="orderStartime" formatter="yyyy-MM-dd hh:mm:ss" align="center"></t:dgCol>
			<t:dgCol title="下单联系人" field="realName" align="center"></t:dgCol>
			<t:dgCol title="乘车联系人" field="orderContactsname" align="center"></t:dgCol>
			<t:dgCol title="所属公司" field="departname" align="center"></t:dgCol>
			<t:dgCol title="订单状态" field="orderStatus"
				dictionary="orderStatus" align="center"></t:dgCol>
			<t:dgCol title="订单数量" field="orderNumbers" align="center"></t:dgCol>
			<t:dgCol title="合计价格" field="orderTotalPrice" align="center"></t:dgCol>
		</t:datagrid>
	</div>

</div>
<!-- <div id="total" hidden="true">
	<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;订单总量：</label><label
		id="orderTotal"></label> <label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;总收入:</label><label
		id="totalPrice"></label>

</div> -->
<input type="text" value="${accountList}" id="accounts" type="hidden" />
<input type="hidden" value="${linelist}" id="linelies" />
<script type="text/javascript">
	$(document).ready(
			function() {
				$("input[name='orderCompletedTime_begin']").attr("class",
						"Wdate").click(function() {
					WdatePicker({
						dateFmt : 'yyyy-MM-dd HH:mm:ss'
					});
				});
				$("input[name='orderCompletedTime_end']")
						.attr("class", "Wdate").click(function() {
							WdatePicker({
								dateFmt : 'yyyy-MM-dd HH:mm:ss'
							});
						});
			});

	//初始化查询条件
	$(function() {
		$(".datagrid-toolbar").append("<div id='total' hidden='true'><label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;订单总量：</label><label id='orderTotal'></label> <label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;总收入:</label><label id='totalPrice'></label></div>");
		$("#total").show();
		
		//添加线路条件
		var json = $("#linelies").val();
		var json1 = $("#accounts").val();
		var a1 = '<span style="display:-moz-inline-box;display:inline-block; padding:10px 2px;"><span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;';
		var a2 = 'text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; "title="选择线路">选择线路：</span>';
		var a3 = '<select id ="linesId" name="linesId" style="width: 150px">';
		var c1 = '<option value="">选择线路</option>';
		
		if(json.indexOf("lineId")>0){
			var obj = eval('(' + json + ')');
			for (var i = 0; i < obj.lineinfo.length; i++) {
				c1 += '<option value="'+obj.lineinfo[i].lineId+'">' + obj.lineinfo[i].lineName+ '</option>';
			}
		}
		var a4 = '</select></span>';
		
		
		var a11 = '<span style="display:-moz-inline-box;display:inline-block; padding:0px 10px;"><span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;';
 		var a21 = 'text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; "title="渠道商名称">渠道商名称：</span>';
		var a31 = '<select name="accountId" style="width: 150px">';
		var c11 = '<option value="">选择渠道商</option>';
		if(json1.indexOf("account")>0){
			var obj1 = eval('(' + json1 + ')');
			for (var i = 0; i < obj1.data.length; i++) {
				c11 += '<option value="'+obj1.data[i].id+'">' + obj1.data[i].account+ '</option>';
			}
		}
		var a41 = '</select></span>';
		
		$("#dealerStatisticsListForm").append(a1 + a2 + a3 + c1 + a4+a11 + a21 + a31 + c11 + a41);
		getLineName();
		gettotal();
	});

	function gettotal() {
		var orderCompletedTime_begin = $(
				"input[name='orderCompletedTime_begin']").val();
		var orderCompletedTime_end = $("input[name='orderCompletedTime_end']")
				.val();
		var accountId = $("select[name='accountId']").val();

		var ordertype = $("select[name='ordertype']").val();
		var lineName = $("select[name='linesId']").val();

		//渠道商订单统计
		$.ajax({
					url : "dealerStatisticsController.do?getDealerTotal&orderCompletedTime_begin="
							+ orderCompletedTime_begin
							+ "&orderCompletedTime_end="
							+ orderCompletedTime_end
							+ "&ordertype="
							+ ordertype
							+ "&lineName="
							+ lineName
							+ "&accountId="
							+ accountId,
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
	
function getLineName() {
		
		$("select[name='ordertype']").change(
				function() {
					var ordertype = $(this).children('option:selected').val(); //当前选择项的值
					var url = "transferStatisticsController.do?getLineName&ordertype="+ ordertype;
					$.ajax({
						type : 'POST',
						url : url,
						success : function(ds) {
							var d1 = '<option value="">选择线路</option>';
							var obj = eval('(' + ds + ')');
							if(obj.indexOf("lineId")>0){
								var objs = eval('(' + obj + ')');
								for (var i = 0; i < objs.lineinfo.length; i++) {
									d1 += '<option value="'+objs.lineinfo[i].lineId+'">' + objs.lineinfo[i].lineName+ '</option>';
								}
							}
							$("#linesId").empty();//先置空 
							$("#linesId").append(d1);
						}
					});
				});
	}

	//查询 
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
			$('#dealerStatisticsList').datagrid(
							{
								url : 'dealerStatisticsController.do?dealerOrderdatagrid&field=id,account,orderCompletedTime,orderCompletedTime_begin,orderCompletedTime_end,orderId,lineName,ordertype,orderStartime,realName,orderContactsname,orderContactsmobile,orderStatus,orderNumbers,orderTotalPrice,',
								pageNumber : 1
							});
		}
		gettotal();
	}

	//重置 
	function searchReset(name) {
		$("#" + name + "tb").find(":input").val("");
		var queryParams = $('#dealerStatisticsList').datagrid('options').queryParams;
		$('#dealerStatisticsListtb').find('*').each(function() {
			queryParams[$(this).attr('name')] = $(this).val();
		});
		$('#dealerStatisticsList')
				.datagrid(
						{
							url : 'dealerStatisticsController.do?dealerOrderdatagrid&field=id,account,orderCompletedTime,orderCompletedTime_begin,orderCompletedTime_end,orderId,lineName,ordertype,orderStartime,realName,orderContactsname,orderContactsmobile,orderStatus,orderNumbers,orderTotalPrice,',
							pageNumber : 1
						});
		gettotal();
	}
</script>

