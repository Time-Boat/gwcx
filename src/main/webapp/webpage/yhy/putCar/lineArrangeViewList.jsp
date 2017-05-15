<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="lineArrangeViewList" title="线路放票" actionUrl="lineArrangeViewController.do?datagrid" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="线路名称" field="name"   width="120"></t:dgCol>
   <t:dgCol title="线路ID" field="lineId"   width="150"></t:dgCol>
   <t:dgCol title="负责人" field="director"   width="120"></t:dgCol>
   <t:dgCol title="发车时间" field="departDate"   width="120"></t:dgCol>
   <t:dgCol title="放票开始日期" field="periodStart" formatter="yyyy-MM-dd hh:mm:ss" width="120"></t:dgCol>
   <t:dgCol title="放票结束日期" field="periodEnd" formatter="yyyy-MM-dd hh:mm:ss" width="120"></t:dgCol>
   <t:dgCol title="票价" field="ticketPrice"   width="120"></t:dgCol>
   <%-- <t:dgCol title="发车开始日期" field="startDate" formatter="yyyy-MM-dd hh:mm:ss"  width="120"></t:dgCol>
   <t:dgCol title="发车停止日期" field="endDate" formatter="yyyy-MM-dd hh:mm:ss"  width="120"></t:dgCol>
   <t:dgCol title="司机id" field="driverId"   width="120"></t:dgCol>
   <t:dgCol title="车牌id" field="licencePlateId"   width="120"></t:dgCol>
   <t:dgCol title="备注" field="remark"   width="120"></t:dgCol>
   <t:dgCol title="车牌号" field="licencePlate"   width="120"></t:dgCol>
   <t:dgCol title="座位数" field="seat"   width="120"></t:dgCol>
   <t:dgCol title="车辆类型" field="carType"   width="120"></t:dgCol>
   <t:dgCol title="司机姓名" field="driverName"   width="120"></t:dgCol> --%>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <%-- <t:dgDelOpt title="删除" url="lineArrangeViewController.do?del&id={id}" />
   <t:dgToolBar title="编辑" icon="icon-edit" url="lineArrangeViewController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="录入" icon="icon-add" url="lineArrangeViewController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="lineArrangeViewController.do?addorupdate" funname="detail"></t:dgToolBar> --%>
   <t:dgFunOpt funname="putRecord(id,lineId)" title="放票记录" ></t:dgFunOpt>
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 	function putRecord(id,lineId){
 		if(id == "lvId"){
 			tip("请先排班再放票");
 			return;
 		}
 		createwindow("放票记录", "lineArrangeViewController.do?putRecord&id="+id+"&lineId="+lineId,"1200px","800px");
 	}
 </script>