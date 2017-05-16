<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="carInfoList" title="车辆信息" actionUrl="carInfoController.do?datagrid&businessType=1" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="编号" field="driverId" hidden="true"></t:dgCol>
   <t:dgCol title="车牌号" field="licencePlate"   width="120"></t:dgCol>
   <t:dgCol title="车辆类型" field="carType"   width="120"></t:dgCol>
   <t:dgCol title="司机" field="name" width="120"></t:dgCol>
   <t:dgCol title="驾照类型" field="drivingLicense" dictionary="drivingLic" align="center" width="120"></t:dgCol>
   <t:dgCol title="座位数" field="seat" width="120"></t:dgCol>
   <t:dgCol title="车辆位置" field="stopPosition" width="120"></t:dgCol>
   <t:dgCol title="车辆状态" field="status" dictionary="carType" width="120"></t:dgCol>
   <%-- <t:dgToolBar title="查看" icon="icon-search" url="carInfoController.do?addorupdate" funname="detail"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>