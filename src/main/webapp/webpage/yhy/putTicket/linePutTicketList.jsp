<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<div class="easyui-layout" fit="true">
		<div data-options="region:'east'" title="" style="width:600px;padding:0px;border:0px;">
			<div class="easyui-calendar" fit="true"></div>
		</div>
		<div id="p" data-options="region:'center'" title="" style="padding:0px;border:0px;">
			<t:datagrid name="roleList" title="线路放票管理" actionUrl="roleController.do?roleGrid" 
			    idField="id" fit="true" queryMode="group">
				<t:dgCol title="id" field="" hidden="true"></t:dgCol>
				<t:dgCol title="线路编号" field="" query="true"></t:dgCol>
				<t:dgCol title="线路名称" field="" query="true"></t:dgCol>
				<t:dgCol title="发车时间" field="" formatter="yyyy-MM-dd" query="true"></t:dgCol>
				<t:dgCol title="状态" field="" query="true"></t:dgCol>
				<t:dgCol title="操作" field="opt" width="50"></t:dgCol>
				<t:dgDelOpt title="放票" url="" urlStyle="align:center" />				
				<t:dgFunOpt funname="delRole(id)" title=""></t:dgFunOpt>
				<t:dgFunOpt funname="userListbyrole(id,roleName)" title="common.user"></t:dgFunOpt>
				<t:dgFunOpt funname="setfunbyrole(id,roleName)" title="permission.set"></t:dgFunOpt>
			</t:datagrid>
		</div>
	</div>

