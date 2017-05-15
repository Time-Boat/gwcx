<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="plug-in/tools/popup/userSelect.js"></script>
<script src="plug-in/tools/popup/departSelect.js"></script>
<div class="easyui-layout" fit="true">
<div region="center"  style="padding:0px;border:0px">
	<t:datagrid name="busStopInfoList" title="站点信息管理(接送机)" autoLoadData="true" actionUrl="busStopInfoController.do?datagrid"  fitColumns="true"
	idField="id" fit="true" queryMode="group" >
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="站点名称" field="name" query="true" frozenColumn="true" align="center" width="120"></t:dgCol>
	<t:dgCol title="站点地址" field="stopLocation"  align="center" width="120"></t:dgCol>
	<%-- <t:dgCol title="状态" field="status" replace="启用_0,禁用_1" align="center" query="true"  width="60"></t:dgCol> --%>
	<t:dgCol title="创建时间" field="createTime" editor="datebox" formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group" align="center" width="200"></t:dgCol>
	<t:dgCol title="创建人" field="createPeople"  align="center" width="60"></t:dgCol>
	<t:dgCol title="备注" field="remark" align="center" width="80"></t:dgCol>
	<t:dgToolBar operationCode="add" title="添加站点" icon="icon-add" url="busStopInfoController.do?addorupdate" funname="add"></t:dgToolBar>
	<t:dgToolBar operationCode="edit" title="修改站点" icon="icon-edit" url="busStopInfoController.do?addorupdate" funname="update"></t:dgToolBar>
	<t:dgCol title="操作" field="opt" width="50"></t:dgCol>
	<t:dgDelOpt title="下架" url="busStopInfoController.do?del&id={id}&deleteFlag=1" urlStyle="align:center" />
	<%-- <t:dgToolBar title="批量下架" icon="icon-remove" url="busStopInfoController.do?doDeleteALLSelect" funname="deleteALLSelect"></t:dgToolBar> --%>
	<t:dgToolBar operationCode="detail" title="站点信息查看" icon="icon-search" url="busStopInfoController.do?addorupdate" funname="detail"></t:dgToolBar>
</t:datagrid>
</div>
</div>
<script type="text/javascript">
	$(document).ready(function(){
		$("input[name='createTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
		$("input[name='createTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
		$("input[name='birthday']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
	
	});
</script>
