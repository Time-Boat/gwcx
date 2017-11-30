<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="plug-in/tools/popup/userSelect.js"></script>
<script src="plug-in/tools/popup/departSelect.js"></script>
<div class="easyui-layout" fit="true">
<div region="center"  style="padding:0px;border:0px">
	<t:datagrid  name="transferOrderSearchList" title="接送订单查询" autoLoadData="true" actionUrl="transferOrderController.do?datagrid" fitColumns="true"
			    idField="id" fit="true" queryMode="group">
		<t:dgCol title="id" field="id" hidden="true"></t:dgCol>
		<t:dgCol title="订单编号" field="orderId" query="true"></t:dgCol>
		<t:dgCol title="申请人" field="orderContactsname" query="true"></t:dgCol>
		<t:dgCol title="申请人手机号" field="orderContactsmobile" align="center"></t:dgCol>
		<t:dgCol title="起点站" field="orderStartingstation" query="true" align="center"></t:dgCol>
		<t:dgCol title="终点站" field="orderTerminusstation" query="true" align="center"></t:dgCol>
	    <t:dgCol title="单价(元/人)" field="orderUnitprice" align="center"></t:dgCol>
		<t:dgCol title="总价(元)" field="orderTotalPrice" align="center"></t:dgCol>
		<t:dgCol title="申请时间" field="applicationTime" editor="datebox" formatter="yyyy-MM-dd hh:mm:ss" align="center"></t:dgCol>
		<t:dgCol title="发车时间" field="orderStartime" editor="datebox" formatter="yyyy-MM-dd hh:mm" query="true" queryMode="group" align="center"></t:dgCol>
		<t:dgCol title="预计到达时间" field="orderExpectedarrival" editor="datebox" formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group" align="center"></t:dgCol>
		<t:dgCol title="订单类型" field="orderType" replace="接机_2,送机_3,接火车 _4,送火车_5" query="true" align="center"></t:dgCol>
		<t:dgCol title="航班号" field="orderFlightnumber" align="center"></t:dgCol>
		<t:dgCol title="火车车次" field="orderTrainnumber" align="center"></t:dgCol>
		<t:dgCol title="订单状态" field="orderStatus" replace="订单已完成_0,待派车_1,待出行_2,取消订单待退款_3,取消订单完成退款_4,未支付_6" query="true" align="center"></t:dgCol>
		<t:dgToolBar operationCode="detail" title="查看详情" icon="icon-search" url="transferOrderController.do?addorupdate" funname="detail"></t:dgToolBar> 	 
	</t:datagrid>
</div>
</div>
<script type="text/javascript">
	$(document).ready(function(){
		$("input[name='orderStartime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'});});
		$("input[name='orderStartime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'});});
		$("input[name='orderExpectedarrival_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
		$("input[name='orderExpectedarrival_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});			 
	});

</script>
 
