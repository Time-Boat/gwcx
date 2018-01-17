<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="plug-in/tools/popup/userSelect.js"></script>
<script src="plug-in/tools/popup/departSelect.js"></script>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="OrderStatList" title="订单收入统计" autoLoadData="true"
			fit="true" actionUrl="transferStatisticsController.do?orderdatagrid"
			idField="id" queryMode="group">
			<t:dgCol title="id" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="下单时间" field="applicationTime" editor="datebox"
				formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group"
				width="100" align="center"></t:dgCol>
			<t:dgCol title="线路类型" field="ordertype" width="60"
				replace="接机_2,送机_3,接火车 _4,送火车_5" query="true" align="center"></t:dgCol>
			<t:dgCol title="订单编号" field="orderId" query="true" align="center"
				width="150"></t:dgCol>
			<t:dgCol title="线路名称" field="lineName"  align="center"
				width="80"></t:dgCol>

			<t:dgCol title="发车时间" field="orderStartime" editor="datebox"
				formatter="yyyy-MM-dd hh:mm:ss" align="center" width="120"></t:dgCol>
			<t:dgCol title="下单联系人" field="realName" align="center" width="80"></t:dgCol>
			<t:dgCol title="乘车联系人" field="orderContactsname" align="center"
				width="80"></t:dgCol>
			<t:dgCol title="联系人手机号" field="orderContactsmobile" align="center"
				width="80"></t:dgCol>
			<t:dgCol title="用户类型" field="userType" dictionary="userType"
				width="80" align="center"></t:dgCol>
			<t:dgCol title="所属公司" field="departname" align="center" width="80"></t:dgCol>
			<t:dgCol title="司机名称" field="driverName" align="center" width="80"></t:dgCol>
			<t:dgCol title="车牌号" field="licencePlate" align="center" width="80"></t:dgCol>
			<t:dgCol title="订单来源渠道" field="account" align="center" width="80"></t:dgCol>
			<t:dgCol title="订单状态" field="orderStatus" width="80"
				replace="订单已完成_0,待派车_1,待出行_2,取消订单待退款_3" dictionary="orderStatus"
				query="true" align="center"></t:dgCol>
			<t:dgCol title="订单数量" field="orderNumbers" align="center" width="80"></t:dgCol>
			<t:dgCol title="合计价格" field="orderTotalPrice" align="center"
				width="80"></t:dgCol>
		</t:datagrid>
	</div>

</div>
<input type="hidden" value="${linelist}" id="linelies" />
<input type="hidden" value="${companylist}" id="companies" />
<input type="hidden" value="${driverList}" id="driverlie" />

<div id="total" hidden="true">
	<label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;订单总量：</label><label
		id="orderTotal"></label> <label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;总收入:</label><label
		id="totalPrice"></label>

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
	
	
	$(function() {
		//添加线路条件
		var json = $("#linelies").val();
		var json1 = $("#companies").val();
		var json2 = $("#driverlie").val();
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
		
		var a11 = '<span style="display:-moz-inline-box;display:inline-block; padding:10px 2px;"><span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;';
		var a21 = 'text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; "title="选择公司">选择公司：</span>';
		var a31 = '<select id ="departname" name="departname" style="width: 150px">';
		var c11 = '<option value="">选择公司</option>';
		
		if(json1.indexOf("companyId")>0){
			var obj1 = eval('(' + json1 + ')');
			for (var i = 0; i < obj1.data.length; i++) {
				c11 += '<option value="'+obj1.data[i].departname+'">' + obj1.data[i].departname+ '</option>';
			}
		}
		var a41 = '</select></span>';
		
		var a111 = '<span style="display:-moz-inline-box;display:inline-block; padding:10px 2px;"><span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;';
		var a211 = 'text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; "title="选择司机">选择司机：</span>';
		var a311 = '<select name="driverId" style="width: 150px">';
		var c111 = '<option value="">--选择司机--</option>';
		if(json2.indexOf("driverId")>0){
			var obj1 = eval('(' + json2 + ')');
			for (var i = 0; i < obj1.data.length; i++) {
				c111 += '<option value="'+obj1.data[i].driverId+'">' + obj1.data[i].driverName+ '</option>';
			}
		}
		var a411 = '</select></span>';
		
		$("#OrderStatListForm").append(a1 + a2 + a3 + c1 + a4+a11 + a21 + a31 + c11 + a41+a111 + a211 + a311 + c111 + a411);
		gettotal();
		getLineName();
	});

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

	function gettotal() {
		var applicationTime_begin = $("input[name='applicationTime_begin']").val();
		var applicationTime_end = $("input[name='applicationTime_end']").val();
		var ordertype = $("select[name='ordertype']").val();
		var orderStatus = $("select[name='orderStatus']").val();
		var orderId = $("input[name='orderId']").val();
		var lineName = $("select[name='linesId']").val();
		//var driverName = "";
		var driverId = $("select[name='driverId']").val();

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
							+ lineName
							+ "&driverId="
							+ driverId
							+ "&orderId=" + orderId,
					dataType : 'json',
					complete : function(data) {
						var message = data.responseText;
						var obj = eval('(' + message + ')');
						$("#orderTotal").html(obj.sumorder + "张");
						$("#totalPrice").html(obj.sumPrice + "元");
						//刷新当前窗体
						reloadTable();
					}
				});
	}
	//进入触发 
	$(function() {
		
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

