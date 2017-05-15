<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="plug-in/tools/popup/userSelect.js"></script>
<script src="plug-in/tools/popup/departSelect.js"></script>

<div class="easyui-layout" fit="true">
<div region="center"  style="padding:0px;border:0px">			
 		<t:datagrid name="roleList" title="公司班车信息查询" actionUrl="roleController.do?roleGrid"  fitColumns="true"
			idField="id" fit="true" queryMode="group"  >
			<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="公司名称" field="" query="true" frozenColumn="true" align="center" width="120"></t:dgCol>
			<t:dgCol title="线路编号"  field="" query="true" align="center" width="60"></t:dgCol>
			<t:dgCol title="线路名称"  field="" query="true" align="center" width="60"></t:dgCol>
			<t:dgCol title="合约时间"  field="" query="true" align="center" width="60"></t:dgCol>
			<t:dgCol title="发车时间"  field="" query="true" align="center" width="60"></t:dgCol>	
			<t:dgCol title="起始站"  field="" query="true" align="center" width="60"></t:dgCol>	
			<t:dgCol title="终点站"  field="" query="true" align="center" width="60"></t:dgCol>	
			<t:dgCol title="座位数"  field="" query="true" align="center" width="60"></t:dgCol>	
			<t:dgCol title="状态"  field="" query="true" align="center" width="60"></t:dgCol>	
			<t:dgCol title="支付方式"  field="" query="true" align="center" width="60"></t:dgCol>	
			<t:dgToolBar operationCode="detail" title="班车详情查看" icon="icon-search" url="" funname="detail"></t:dgToolBar>
		</t:datagrid> 
	</div>
</div>