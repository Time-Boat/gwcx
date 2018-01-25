<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="carNumberList" title="上车人数" actionUrl="carNumberController.do?datagrid" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="上车人数" field="number"   width="120"></t:dgCol>
   <t:dgToolBar title="编辑" icon="icon-edit" url="carNumberController.do?addorupdate" funname="update" height="200" width="400"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>