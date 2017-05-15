<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="plug-in/tools/popup/userSelect.js"></script>
<script src="plug-in/tools/popup/departSelect.js"></script>

<div class="easyui-layout" fit="true">
<div region="center"  style="padding:0px;border:0px">
<t:datagrid name="conductorList" title="包车订单管理" autoLoadData="true" actionUrl="roleController.do?roleGrid"  fitColumns="true"
	idField="id" fit="true" queryMode="group" >
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="公司名称" field="" query="true" frozenColumn="true" align="center" width="120"></t:dgCol>
	<t:dgCol title="申请人"  field="" query="true" align="center"  width="120"></t:dgCol>
	<t:dgCol title="申请人手机号"  field="" query="true" align="center"  width="120"></t:dgCol>
	<t:dgCol title="下单时间"  field="" query="true" align="center"  width="120"></t:dgCol>
	<t:dgCol title="订单类型"  field="" replace="单程_1,双程_2" query="true" align="center"  width="120"></t:dgCol>
	<t:dgCol title="座位数"  field="" query="true" align="center"  width="120"></t:dgCol>
	<t:dgCol title="起点位置1"  field="" query="true" align="center"  width="120"></t:dgCol>
	<t:dgCol title="起点位置2"  field="" query="true" align="center"  width="120"></t:dgCol>
	<t:dgCol title="终点位置"  field="" query="true" align="center"  width="120"></t:dgCol>
	<t:dgCol title="出发时间"  field="" query="true" align="center"  width="120"></t:dgCol>
	<t:dgCol title="包车时长"  field="" query="true" align="center"  width="120"></t:dgCol>
	<t:dgCol title="司机姓名"  field="" query="true" align="center"  width="120"></t:dgCol>
	<t:dgCol title="司机手机号"  field="" query="true" align="center"  width="120"></t:dgCol>
	<t:dgCol title="车牌号"  field="" query="true" align="center"  width="120"></t:dgCol>
	<t:dgCol title="状态" field="" replace="审核中_0,未出行_1,已出行_2" align="center" width="60" query="true"></t:dgCol>
	<t:dgToolBar operationCode="edit" title="信息处理" icon="icon-edit" url="" funname="update"></t:dgToolBar>
	<%-- <t:dgCol title="操作" field="opt" width="50"></t:dgCol>
	<t:dgDelOpt title="删除" url="" urlStyle="align:center" /> --%>
	<t:dgToolBar operationCode="detail" title="查看" icon="icon-search" url="" funname="detail"></t:dgToolBar>

</t:datagrid></div>
</div>
