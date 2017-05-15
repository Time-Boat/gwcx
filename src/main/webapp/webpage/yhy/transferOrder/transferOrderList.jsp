<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="plug-in/tools/popup/userSelect.js"></script>
<script src="plug-in/tools/popup/departSelect.js"></script>
<div class="easyui-layout" fit="true">
<div region="center" style="padding:0px;border:0px">
	<t:datagrid name="transferOrderList" title="接送订单处理" autoLoadData="true" actionUrl="transferOrderController.do?datagrid" fitColumns="true"
			    idField="id" fit="true" queryMode="group" checkbox="true">
		<t:dgCol title="id" field="id" hidden="true"></t:dgCol>
		<t:dgCol title="订单编号" field="orderId" query="true"></t:dgCol>
		<t:dgCol title="订单类型" field="orderType" replace="接机_0,送机_1,接火车 _2,送火车_3" query="true" align="center"></t:dgCol>
		
		<t:dgCol title="航班号" field="orderFlightnumber" align="center"></t:dgCol>
		<t:dgCol title="起点站" field="orderStartingstation" query="true" align="center"></t:dgCol>
		<t:dgCol title="终点站" field="orderTerminusstation" query="true" align="center"></t:dgCol>
		<t:dgCol title="出发时间" field="orderStartime" editor="datebox" formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group" align="center"></t:dgCol>
		<t:dgCol title="预计到达时间" field="orderExpectedarrival" editor="datebox" formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group" align="center"></t:dgCol>
		<t:dgCol title="人数" field="orderNumberPeople" align="center"></t:dgCol>
		<t:dgCol title="单价(人/元)" field="orderUnitprice" align="center"></t:dgCol>
		<t:dgCol title="总价(元)" field="orderTotalPrice" align="center"></t:dgCol>
		<t:dgCol title="车票数量" field="orderNumbers" align="center"></t:dgCol>
		<t:dgCol title="支付方式" field="orderPaytype" replace="微信_0,支付宝_1,银联_2" align="center"></t:dgCol>
		<t:dgCol title="联系人" field="orderContactsname" align="center"></t:dgCol>
		<t:dgCol title="联系人手机号" field="orderContactsmobile" align="center"></t:dgCol>
		<t:dgCol title="支付状态" field="orderPaystatus"  replace="已付款_0,退款中_1,已退款_2,未付款_3" align="center"></t:dgCol>
		<t:dgCol title="火车车次" field="orderTrainnumber" align="center"></t:dgCol>
		<t:dgCol title="所属线路名称" field="lineName" align="center"></t:dgCol>
		<t:dgCol title="所属线路id" field="lineId" align="center"></t:dgCol>
		<t:dgCol title="司机姓名" field="name" align="center"></t:dgCol>
		<t:dgCol title="司机联系电话" field="phoneNumber" align="center"></t:dgCol>
		<t:dgCol title="车牌号" field="licencePlate" align="center"></t:dgCol>
		<t:dgCol title="订单状态" field="orderStatus" replace="订单已完成_0,已付款待审核_1,审核通过待发车 _2,取消订单待退款_3,取消订单完成退款_4" query="true" align="center"></t:dgCol>
		<%-- <t:dgCol title="操作" field="opt"  align="center"></t:dgCol> --%> 
		<t:dgToolBar title="司机车辆安排" icon="icon-edit" url="transferOrderController.do?editCarAndDriver" funname="editCarAndDriver"></t:dgToolBar>
	</t:datagrid>
</div>
</div>
<script type="text/javascript">
	$(document).ready(function(){
		$("input[name='orderStartime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
		$("input[name='orderStartime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});	
		$("input[name='orderExpectedarrival_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
		$("input[name='orderExpectedarrival_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});			 
	});

	//安排车辆司机     里面可以做一个ajax请求，判断选中的订单是不是在同一条线路上
	function editCarAndDriver(title,url,id,width,height){
		var ids = '';
		var rows = $("#transferOrderList").datagrid("getSelections");
		//var lineId = rows[0].lineId;
		for(var i=0;i<rows.length;i++){
			
			//判断选中的订单是不是在同一条线路上
			/* if(lineId == rows[i].lineId){
				lineId = rows[i].lineId;
			}else{
				tip('请选择同一条线路');
				return;
			} */
			
			ids+=rows[i].id;
			ids+=',';
		}
		ids = ids.substring(0,ids.length-1);
		if(ids.length==0){
			tip('请选择项目');
			return;
		}
		url += '&ids='+ids;
		createwindow(title,url,width,height);
		/* $("#function-transferOrderAdd").panel(
			{
				title :'司机车辆信息',
				href: url
			}
		); */
	} 
</script>
 
