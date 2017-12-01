<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="notificationRecordList" title="系统消息发送记录" actionUrl="notificationRecordController.do?datagrid" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="通知标题" field="title"   width="120"></t:dgCol>
   <t:dgCol title="通知内容" field="content"   width="120"></t:dgCol>
   <%-- <t:dgCol title="通知目标" field="target"   width="120"></t:dgCol> --%>
   <t:dgCol title="发送时间" field="sendTime" formatter="yyyy-MM-dd hh:mm:ss"  width="120"></t:dgCol>
   <t:dgCol title="通知方式" field="nType" width="120"></t:dgCol>
   <t:dgCol title="用户" field="userName" width="120"></t:dgCol>
   <t:dgCol title="创建时间" field="createTime" formatter="yyyy-MM-dd hh:mm:ss"  width="120"></t:dgCol>
   <t:dgCol title="备注" field="remark"   width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="notificationRecordController.do?del&id={id}" />
   <t:dgToolBar title="查看" icon="icon-search" url="notificationRecordController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>