<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="notificationModelList" title="系统消息通知模板" actionUrl="notificationModelController.do?datagrid" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="通知标题" field="title" width="120"></t:dgCol>
   <t:dgCol title="通知内容" field="content" width="120"></t:dgCol>
   <%-- <t:dgCol title="通知目标" field="target" width="120"></t:dgCol> --%>
   <t:dgCol title="通知方式" field="nType" width="120"></t:dgCol>
   <t:dgCol title="用户" field="userName" width="120"></t:dgCol>
   <t:dgCol title="创建时间" field="createTime" formatter="yyyy-MM-dd hh:mm:ss" width="120"></t:dgCol>
   <t:dgCol title="修改时间" field="midifyTime" formatter="yyyy-MM-dd hh:mm:ss" width="120"></t:dgCol>
   <t:dgCol title="是否启用" dictionary="notify_status" field="status" width="120"></t:dgCol>
   <t:dgCol title="备注" field="remark" width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="notificationModelController.do?del&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="notificationModelController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="notificationModelController.do?addorupdate" funname="update"></t:dgToolBar>
   <%-- <t:dgToolBar title="查看" icon="icon-search" url="notificationModelController.do?addorupdate" funname="detail"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>