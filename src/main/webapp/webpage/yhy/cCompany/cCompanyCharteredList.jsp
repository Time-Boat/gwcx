<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="plug-in/tools/popup/userSelect.js"></script>
<script src="plug-in/tools/popup/departSelect.js"></script>

<div class="easyui-layout" fit="true">
<div region="center"  style="padding:0px;border:0px">			
 		<t:datagrid name="roleList" title="公司包车信息查询" actionUrl="roleController.do?roleGrid"  fitColumns="true"
			idField="id" fit="true" queryMode="group"  >
			<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="公司名称" field="" query="true" frozenColumn="true" align="center" width="120"></t:dgCol>
			<t:dgCol title="申请人"  field="" query="true" align="center" width="60"></t:dgCol>
			<t:dgCol title="申请人手机号"  field="" query="true" align="center" width="60"></t:dgCol>
			<t:dgCol title="申请时间"  field="" query="true" align="center" width="60"></t:dgCol>
			<t:dgCol title="预约起始时间"  field="" query="true" align="center" width="60"></t:dgCol>
			<t:dgCol title="预约结束时间"  field="" query="true" align="center" width="60"></t:dgCol>
			<t:dgCol title="起始站"  field="" query="true" align="center" width="60"></t:dgCol>
			<t:dgCol title="终点站"  field="" query="true" align="center" width="60"></t:dgCol>
			<t:dgCol title="乘车人数"  field="" query="true" align="center" width="60"></t:dgCol>
			<t:dgCol title="预约车辆类型"  field="" query="true" align="center" width="60"></t:dgCol>
			<t:dgCol title="付费方式"  field="" query="true" align="center" width="60"></t:dgCol>
			<t:dgCol title="状态"  field="" query="true" align="center" width="60"></t:dgCol>	
			<t:dgToolBar operationCode="detail" title="包车详情查看" icon="icon-search" url="" funname="detail"></t:dgToolBar>
		</t:datagrid> 
	</div>
</div>