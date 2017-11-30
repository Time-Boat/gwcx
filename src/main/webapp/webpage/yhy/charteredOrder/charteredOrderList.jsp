<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="charteredOrderList" title="包车订单" actionUrl="charteredOrderController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="订单编号" field="orderId" width="120"></t:dgCol>
   <t:dgCol title="出发时间" field="orderStartime" width="120" editor="datebox" formatter="yyyy-MM-dd hh:mm" query="true" queryMode="group" align="center"></t:dgCol>
   <t:dgCol title="用户订单类型 " field="orderUserType" dictionary="userType"  width="120"></t:dgCol>
   <t:dgCol title="起点站" field="startingStationName" width="120"></t:dgCol>
   <t:dgCol title="终点站" field="terminusStationName" width="120"></t:dgCol>
   <t:dgCol title="包车类型" field="orderType" dictionary="charteredType" query="true" width="120"></t:dgCol>
   <t:dgCol title="包车套餐" field="chartPackageName" width="120"></t:dgCol>
   <t:dgCol title="总里程" field="orderActualMileage" width="120"></t:dgCol>
   <t:dgCol title="总价" field="orderTotalprice" width="120"></t:dgCol>
   <t:dgCol title="联系人" field="orderContactsname" width="120"></t:dgCol>
   <t:dgCol title="联系人手机号" field="orderContactsmobile" width="120"></t:dgCol>
   <t:dgCol title="下达订单时间" field="applicationtime" formatter="yyyy-MM-dd hh:mm:ss"  width="120"></t:dgCol>
   <t:dgCol title="司机名称" field="driverName" width="120"></t:dgCol>
   <t:dgCol title="司机手机号" field="phoneNumber" width="120"></t:dgCol>
   <t:dgCol title="座位数" field="seat" width="120"></t:dgCol>
   <t:dgCol title="派车公司" field="departname" width="120"></t:dgCol>
   <t:dgCol title="订单状态 " field="orderPaystatus" dictionary="orderStatus" query="true" align="center" width="120"></t:dgCol>
   <t:dgCol title="负责人 " field="username" query="true"  width="120"></t:dgCol>
   
   <%-- <t:dgCol title="订单备注" field="remark"   width="120"></t:dgCol>
   <t:dgCol title="客户id" field="userId"   width="120"></t:dgCol>
   <t:dgCol title="申请退款时间" field="refundTime" formatter="yyyy-MM-dd hh:mm:ss"  width="120"></t:dgCol>
   <t:dgCol title="拒绝退款原因" field="rejectReason"   width="120"></t:dgCol>
   <t:dgCol title="微信商户单号" field="orderPayNumber"   width="120"></t:dgCol>
   <t:dgCol title="退款金额" field="refundPrice"   width="120"></t:dgCol>
   <t:dgCol title="退款时间" field="refundCompletedTime" formatter="yyyy-MM-dd hh:mm:ss"  width="120"></t:dgCol>
   <t:dgCol title="订单完成时间" field="orderCompletedTime" formatter="yyyy-MM-dd hh:mm:ss"  width="120"></t:dgCol>
   <t:dgCol title="是否已经被删除   0：未删除    1：删除" field="deleteFlag"   width="120"></t:dgCol>
   <t:dgCol title="线路订单码" field="lineordercode"   width="120"></t:dgCol>
   <t:dgCol title="初审审核状态   0：初审待审核     1：初审通过    2：初审未通过" field="firstAuditStatus"   width="120"></t:dgCol>
   <t:dgCol title="复审审核状态   0：复审待审核     1：复审通过    2：复审未通过" field="lastAuditStatus"   width="120"></t:dgCol>
   <t:dgCol title="初审人id" field="firstAuditUser"   width="120"></t:dgCol>
   <t:dgCol title="复审人id" field="lastAuditUser"   width="120"></t:dgCol>
   <t:dgCol title="初审时间" field="firstAuditDate" formatter="yyyy-MM-dd hh:mm:ss"  width="120"></t:dgCol>
   <t:dgCol title="复审时间" field="lastAuditDate" formatter="yyyy-MM-dd hh:mm:ss"  width="120"></t:dgCol>
   <t:dgCol title="历史订单（就是该订单绑定的运营专员已经被注销了）    0：否    1：是" field="orderHistory"   width="120"></t:dgCol> --%>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <%-- <t:dgDelOpt title="删除" url="charteredOrderController.do?del&id={id}" /> --%>
   <t:dgToolBar title="指派" icon="icon-edit" url="charteredOrderController.do?appoint" funname="appoint" ></t:dgToolBar>
   <%-- <t:dgFunOpt funname="appoint(id)" title="指派" operationCode="appoint" ></t:dgFunOpt> --%>
   <t:dgToolBar title="查看详情" url="charteredOrderController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 
 <script type="text/javascript">
 	$(document).ready(function(){
		$("input[name='orderStartime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'});});
		$("input[name='orderStartime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'});});	
	});
 
 	//安排车辆司机 
	function appoint(title,url,id,width,height){
		var rowsData = $('#'+id).datagrid('getSelections');
		/* if (!rowsData || rowsData.length==0) {
			tip('请选择订单！');
			return;
		}
		if (rowsData.length > 1) {
			tip('请选择一条记录再编辑！');
			return;
		} */ 
		url += '&id='+rowsData.id;
		createwindow(title,url,width,height);
		//判断当前订单用户是否已经被注销了
		/* $.get(
			"transferOrderController.do?checkUser&userIds="+userIds,
			function(data){
				console.log(data);
				var obj = eval('(' + data + ')');
				if(obj.success){
					url += '&ids='+ids+'&slDate='+slDate;
					createwindow(title,url,width,height);
				}else{
					tip('该条记录已有运营专员管理');
					return;
				}
			}
		); */ 
		
	}
 </script>