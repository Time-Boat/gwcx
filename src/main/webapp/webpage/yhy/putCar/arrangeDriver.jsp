<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<div class="easyui-layout" fit="true">
<div region="center"  style="padding:0px;border:0px">
<t:datagrid name="driversInfoList" title="司机信息管理" autoLoadData="true" actionUrl="driversInfoController.do?datagrid"  fitColumns="true" 
	idField="id" fit="true" queryMode="group" >
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="司机姓名" field="name" query="true" frozenColumn="true" align="center" width="120"></t:dgCol>
	<t:dgCol title="驾照类型" field="drivingLicense" dictionary="drivingLic" align="center" width="80"></t:dgCol>
	<t:dgCol title="电话" field="phoneNumber" align="center" width="80"></t:dgCol>
	<t:dgCol title="状态" field="deleteFlag" dictionary="deleted" align="center" width="80"></t:dgCol>
	<%-- <t:dgDelOpt title="common.delete" url="driversInfoController.do?del&id={id}&deleteFlag=1" urlStyle="align:center" /> --%>
	<%-- <t:dgToolBar title="查看" icon="icon-search" url="driversInfoController.do?addorupdate" funname="detail"></t:dgToolBar> --%>

</t:datagrid></div>
</div>
