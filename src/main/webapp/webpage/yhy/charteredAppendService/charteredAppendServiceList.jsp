<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="charteredAppendServiceList" title="包车服务" actionUrl="charteredAppendServiceController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="服务名称" field="serviceName" query="true" align="center"  width="120"></t:dgCol>
   <t:dgCol title="服务描述" field="serviceDescription" align="center"  width="120"></t:dgCol>
   <t:dgCol title="创建人" field="userName" align="center"  width="120"></t:dgCol>
   <t:dgCol title="创建时间" field="createTime" formatter="yyyy-MM-dd hh:mm:ss"  align="center" width="120"></t:dgCol>
   <t:dgCol title="修改时间" field="midifyTime" formatter="yyyy-MM-dd hh:mm:ss" align="center" width="120"></t:dgCol>
   <t:dgCol title="状态" field="status" query="true" dictionary="driver_status" align="center" width="120"></t:dgCol>
   <t:dgCol title="备注" field="remark" align="center"  width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100" align="center"></t:dgCol>
   <t:dgDelOpt title="删除" url="charteredAppendServiceController.do?del&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="charteredAppendServiceController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="charteredAppendServiceController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="charteredAppendServiceController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>