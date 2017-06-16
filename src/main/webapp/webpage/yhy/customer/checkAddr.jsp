<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
<div region="center" style="padding:0px;border:0px">
<t:datagrid name="roleUserList" title="常用地址"
            actionUrl="carCustomerController.do?addrDatagrid&id=${id}" fit="true" fitColumns="true" idField="id">
	<t:dgCol title="id" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="地址名称" field="name"   align="center"></t:dgCol>
	<t:dgCol title="详细地址" field="stopLocation" align="center" ></t:dgCol>
	<t:dgCol title="选择次数" field="count" align="center" ></t:dgCol>

</t:datagrid>
</div>
</div>
