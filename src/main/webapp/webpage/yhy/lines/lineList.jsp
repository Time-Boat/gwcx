<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="plug-in/tools/popup/userSelect.js"></script>
<script src="plug-in/tools/popup/departSelect.js"></script>
<div class="easyui-layout" fit="true">
<div region="center" style="padding:0px;border:0px">
<t:datagrid name="lineList" title="线路管理" autoLoadData="true" actionUrl="lineInfoController.do?datagrid"  fitColumns="true"
	idField="id" fit="true" queryMode="group" checkbox="true" >
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="线路名称" field="name" query="true" frozenColumn="true" align="center" width="120"></t:dgCol>
	<t:dgCol title="起点地址" field="startLocation" query="true" align="center" width="60"></t:dgCol>
	<t:dgCol title="终点地址" field="endLocation" query="true" align="center" width="60"></t:dgCol>
	<t:dgCol title="线路备注" field="remark"  align="center" width="60"></t:dgCol>
	
	<t:dgCol title="线路类型" field="type" replace="公务班车_0,接送包车_1,接送机_2"  align="center" width="60"></t:dgCol>
	<%--
	<t:dgCol title="线路图片" field="imageurl"  align="center" width="60"></t:dgCol>
	<t:dgCol title="线路状态" field="status" replace="启用_0,未启用_1"  align="center" width="60"></t:dgCol> --%>
	<t:dgCol title="创建时间" field="createTime" editor="datebox" formatter="yyyy-MM-dd hh:mm:ss"   align="center" width="120"></t:dgCol>
	<t:dgCol title="创建人" field="createPeople"  align="center" width="60"></t:dgCol>
	<t:dgCol title="线路状态" field="status" replace="启用_0,未启用_1"  align="center" width="60"></t:dgCol>

	<%-- <t:dgCol title="发车时间" field="lstartTime" editor="datebox" formatter="hh:mm:ss" query="true" queryMode="group" align="center" width="120"></t:dgCol>
	<t:dgCol title="预计到达时间" field="lendTime" editor="datebox" formatter="hh:mm:ss" query="true" queryMode="group"  align="center" width="120"></t:dgCol>
	 --%>
	<t:dgCol title="线路定价(人/元)" field="price"  align="center"></t:dgCol>
	<t:dgCol title="入驻公司名称" field="settledCompanyName"  align="center"></t:dgCol>
	<t:dgCol title="所在城市" field="city"  align="center"></t:dgCol>
	<t:dgToolBar operationCode="add" title="添加线路" icon="icon-add" url="lineInfoController.do?addorupdate" funname="add" height="500"></t:dgToolBar>
	<t:dgToolBar operationCode="edit" title="修改线路" icon="icon-edit" url="lineInfoController.do?addorupdate" funname="update"></t:dgToolBar>
	<%-- <t:dgToolBar operationCode="edit" title="站点挂接" icon="icon-edit" url="lineInfoController.do?addBusStop" funname="update"></t:dgToolBar>
	 --%>
	<t:dgCol title="操作" field="opt" width="100"></t:dgCol>
	
	<%-- <t:dgDelOpt title="下架" url="lineInfoController.do?del&id={id}&deleteFlag=1" urlStyle="align:center" /> --%>
	<t:dgFunOpt funname="addBusStop(id,name,type)" title="站点管理"></t:dgFunOpt>
	<t:dgToolBar title="批量下架" icon="icon-remove" url="lineInfoController.do?doDeleteALLSelect" funname="deleteALLSelect"></t:dgToolBar>
	<t:dgToolBar operationCode="detail" title="线路查看" icon="icon-search" url="lineInfoController.do?addorupdate" funname="detail"></t:dgToolBar>
</t:datagrid> </div>
</div>
<div region="east" style="width: 450px;" split="true">
<div tools="#tt" class="easyui-panel" title="站点管理" style="padding: 10px;" fit="true" border="false" id="function-panelAddBusStop"></div>
</div>
<div id="tt"></div>
</div>
<script type="text/javascript">
$(document).ready(function(){
	$("input[name='createTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
	$("input[name='createTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
	$("input[name='birthday']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
	$("input[name='lstartTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
	$("input[name='lstartTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
	$("input[name='lendTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
	$("input[name='lendTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
});
function addBusStop(id,name,type) {
	$("#function-panelAddBusStop").panel(
		{
			title :'线路名称：'+name,
			href:"lineInfoController.do?addBusStop&lineInfoId=" + id + "&lineType=" + type
		}
	);
}
</script>
