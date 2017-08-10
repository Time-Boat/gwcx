<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="feedbackInfoList" title="乘客反馈信息" actionUrl="feedbackInfoController.do?datagrid" idField="id" fit="true" queryMode="group">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="用户id" field="userId" hidden="true"  width="120"></t:dgCol>
   <t:dgCol title="乘客名称" field="realName" query="true" align="center" width="50"></t:dgCol>
   <t:dgCol title="乘客手机" field="phone" query="true" align="center" width="50"></t:dgCol>
   <t:dgCol title="反馈内容" field="content"  width="180"></t:dgCol>
   <t:dgCol title="创建时间" field="createTime"  align="center" width="120"></t:dgCol>
   <t:dgCol title="反馈状态（备用）" field="status" align="center"  width="50"></t:dgCol>
   <t:dgCol title="备注（备用）" field="remark"  align="center" width="120"></t:dgCol>
   <t:dgToolBar title="查看反馈详细" icon="icon-search" url="feedbackInfoController.do?addorupdate" funname="detail" width="600" height="350"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>