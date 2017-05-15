<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="plug-in/tools/popup/userSelect.js"></script>
<script src="plug-in/tools/popup/departSelect.js"></script>

<div class="easyui-layout" fit="true">
<div region="center"  style="padding:0px;border:0px">			
 		<t:datagrid name="roleList" title="入驻公司员工信息管理" actionUrl="roleController.do?roleGrid"  fitColumns="true"
			idField="id" fit="true" queryMode="group"  >
			<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="公司名称" field="" query="true" frozenColumn="true" align="center" width="120"></t:dgCol>
			<t:dgCol title="员工姓名"  field="" query="true" align="center" width="60"></t:dgCol>
			<t:dgCol title="员工手机号"  field="" query="true" align="center" width="60"></t:dgCol>
			<t:dgCol title="部门"  field="" query="true" align="center" width="60"></t:dgCol>
			<t:dgCol title="职位"  field="" query="true" align="center" width="60"></t:dgCol>
			<t:dgCol title="年龄"  field="" query="true" align="center" width="60"></t:dgCol>
			<t:dgCol title="性别"  field="" query="true" align="center" width="60"></t:dgCol>
			<t:dgCol title="居住位置"  field="" query="true" align="center" width="60"></t:dgCol>
			<t:dgCol title="状态"  field="" query="true" align="center" width="60"></t:dgCol>
			<t:dgToolBar operationCode="add" title="录入" icon="icon-add" url="" funname="add"></t:dgToolBar>
			<t:dgToolBar operationCode="edit" title="编辑" icon="icon-edit" url="" funname="update"></t:dgToolBar>
			<%--<t:dgCol title="操作" field="opt" width="50"></t:dgCol>
			 <t:dgDelOpt title="删除" url="" urlStyle="align:center" /> --%>
			<t:dgToolBar operationCode="detail" title="查看" icon="icon-search" url="" funname="detail"></t:dgToolBar>
		</t:datagrid> 
	</div>
</div>