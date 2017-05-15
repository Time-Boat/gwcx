<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="carInfoList" title="车辆信息" autoLoadData="true" actionUrl="carInfoController.do?datagrid" idField="id" fitColumns="true" queryMode="group" fit="true">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="车牌号" field="licencePlate" query="true" width="120"></t:dgCol>
   <t:dgCol title="车辆类型" field="carType" query="true" width="120"></t:dgCol>
   <t:dgCol title="座位数" field="seat" width="120"></t:dgCol>
   <t:dgCol title="车辆位置" field="stopPosition" width="120"></t:dgCol>
   <t:dgCol title="司机" field="name"   width="120"></t:dgCol>
   <t:dgCol title="车辆状态" field="status" query="true" dictionary="carType" width="120"></t:dgCol>
   <t:dgCol title="业务类型" field="businessType" query="true" dictionary="carBType" width="120"></t:dgCol>
   <t:dgCol title="备注" field="remark"   width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="carInfoController.do?del&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="carInfoController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="carInfoController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="carInfoController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>