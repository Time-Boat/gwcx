<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="conductorTolines" title="线路排班模块" actionUrl="conductorController.do?datagridLines&ywlx=${ywlx}" fit="true" fitColumns="true" 
  		idField="id" queryMode="group" pagination="false" checkbox="true">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="线路名称" field="name" query="true" width="120" ></t:dgCol>
  </t:datagrid>
  </div>
 </div>
