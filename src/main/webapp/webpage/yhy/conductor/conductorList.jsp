<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="plug-in/tools/popup/userSelect.js"></script>
<script src="plug-in/tools/popup/departSelect.js"></script>

<div class="easyui-layout" fit="true">
<div region="center"  style="padding:0px;border:0px">
<t:datagrid name="conductorList" title="验票员信息管理" autoLoadData="true" actionUrl="conductorController.do?datagrid"  fitColumns="true"
	idField="id" fit="true" queryMode="group" checkbox="true" >
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="姓名" field="name" query="true" frozenColumn="true" align="center" width="120"></t:dgCol>
	<t:dgCol title="性别" sortable="true" field="sex" dictionary="sex" query="true" align="center" width="60"></t:dgCol>
	<t:dgCol title="年龄" sortable="true" editor="numberbox" field="age" align="center" width="80"></t:dgCol>
	<t:dgCol title="电话号码" sortable="false" field="phoneNumber" query="true" align="center"  width="120"></t:dgCol>
	<t:dgCol title="创建日期" field="createDate" editor="datebox" formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group" align="center" width="200"></t:dgCol>
	<t:dgCol title="验票线路" field="jurisdiction"  align="center" width="200" query="true"></t:dgCol>
	<t:dgCol title="业务类型" field="status" dictionary="carBType" query="true" align="center" width="120"></t:dgCol>
	<t:dgToolBar operationCode="add" title="录入" icon="icon-add" url="conductorController.do?addorupdate" funname="add"></t:dgToolBar>
	<t:dgToolBar operationCode="edit" title="编辑" icon="icon-edit" url="conductorController.do?addorupdate" funname="update"></t:dgToolBar>
	<t:dgCol title="操作" field="opt" width="50"></t:dgCol>
	<t:dgDelOpt title="common.delete" url="conductorController.do?del&id={id}&deleteFlag=1" urlStyle="align:center" />
	<t:dgToolBar title="批量删除" icon="icon-remove" url="conductorController.do?doDeleteALLSelect" funname="deleteALLSelect"></t:dgToolBar>
	<t:dgToolBar operationCode="detail" title="查看" icon="icon-search" url="conductorController.do?addorupdate" funname="detail"></t:dgToolBar>
	<%-- 
	<t:dgDelOpt operationCode="del" title="删除" url="conductorController.do?del&id={id}" exp="status#eq#0" urlStyle="color: red; padding-left: 5px;"/>
	<t:dgDelOpt operationCode="del" title="删除" url="conductorController.do?del&id={id}" exp="status#eq#1"  urlStyle="color: green; padding-right: 5px;"/>
	--%>

</t:datagrid></div>
</div>
 <script type="text/javascript">
	function testReloadPage(){
		document.location = "http://www.baidu.com"; 
	}
	function szqm(id) {
		createwindow('审核', 'jeecgDemoController.do?doCheck&id=' + id);
	}
	function getListSelections(){
		var ids = '';
		var rows = $("#jeecgDemoList").datagrid("getSelections");
		for(var i=0;i<rows.length;i++){
			ids+=rows[i].id;
			ids+=',';
		}
		
		ids = ids.substring(0,ids.length-1);
		alert(ids);
		return ids;
	}	
	//表单 sql导出
	function doMigrateOut(title,url,id){
		url += '&ids='+ getListSelections();
		window.location.href= url;
	}
	function doMigrateIn(){
		openuploadwin('Xml导入', 'transdata.do?toMigrate', "jeecgDemoList");
	}
	$(document).ready(function(){
		$("input[name='createDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("input[name='createDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("input[name='birthday']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});

		UserSelectApi.popup("#searchColumnsRealName");
		DepartSelectApi.popup("#searchColumnsDepartName");
	});

	function addMobile(title,addurl,gname,width,height){
		window.open(addurl);
	}
	
	function updateMobile(title,url, id,width,height){
		gridname=id;
		var rowsData = $('#'+id).datagrid('getSelections');
		if (!rowsData || rowsData.length==0) {
			tip('请选择编辑项目');
			return;
		}
		if (rowsData.length>1) {
			tip('请选择一条记录再编辑');
			return;
		}
		
		url += '&id='+rowsData[0].id;
		window.open(url);
	}
</script>