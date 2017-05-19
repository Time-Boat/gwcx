<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="openCityList" title="业务开通城市" actionUrl="openCityController.do?datagrid" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="省名称" field="provinceName" width="120"></t:dgCol>
   <t:dgCol title="城市名称" field="cityName" width="120"></t:dgCol>
   <t:dgCol title="城市开通业务id" field="cityBusiness" width="120"></t:dgCol>
   <t:dgCol title="城市开通状态" field="status" width="120" dictionary="openCity" ></t:dgCol>
   <t:dgCol title="备注" field="remark"   width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="openCityController.do?del&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="openCityController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="openCityController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="openCityController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>