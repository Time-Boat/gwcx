<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<t:datagrid name="userListNoPage" title="用户管理" actionUrl="userController.do?datagrid" fit="true" fitColumns="true" idField="id" queryMode="group" pagination="false" checkbox="true">
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="用户名" sortable="false" field="userName" query="true"></t:dgCol>
	<t:dgCol title="部门" field="TSDepart_departname" query="true" queryMode="single" replace="${departsReplace}"></t:dgCol>
	<t:dgCol title="真实姓名" field="realName" query="true"></t:dgCol>
	<t:dgCol title="状态" sortable="true" field="status" replace="正常_1,禁用_0,超级管理员_-1"></t:dgCol>
	<t:dgCol title="操作" field="opt" width="100"></t:dgCol>
	<t:dgDelOpt title="删除" url="userController.do?del&id={id}&userName={userName}" />
	<t:dgToolBar title="用户录入" icon="icon-add" url="userController.do?addorupdate" funname="add"></t:dgToolBar>
	<t:dgToolBar title="用户编辑" icon="icon-edit" url="userController.do?addorupdate" funname="update"></t:dgToolBar>
</t:datagrid>
<script type="text/javascript">
	function test(){
		alert();
		var ids = '';
		var rows = $("#userListNoPage").datagrid("getSelections");
		for(var i=0;i<rows.length;i++){
			ids+=rows[i].id;
			ids+=',';
		}
		ids = ids.substring(0,ids.length-1);
		if(ids.length==0){
			tip('请选择项目');
			return;
		}
		alert(ids);
	}
</script>
