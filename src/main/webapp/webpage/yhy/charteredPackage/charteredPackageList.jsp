<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="charteredPackageList" title="包车套餐设置" actionUrl="charteredPackageController.do?datagrid" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="套餐名称" field="name" align="center" width="120"></t:dgCol>
   <t:dgCol title="套餐内容" field="description" align="center" width="120"></t:dgCol>
   <t:dgCol title="公里数" field="kilometre" align="center" width="120"></t:dgCol>
   <t:dgCol title="时长(分钟)" field="timeLength" align="center" width="120"></t:dgCol>
   <t:dgCol title="所属城市" field="cityName" align="center" width="120"></t:dgCol>
   <t:dgCol title="创建人" field="userName" align="center" width="120"></t:dgCol>
   <t:dgCol title="创建时间" field="createTime" formatter="yyyy-MM-dd hh:mm:ss" align="center"  width="120"></t:dgCol>
   <t:dgCol title="最近修改时间" field="updateTime" formatter="yyyy-MM-dd hh:mm:ss" align="center"  width="120"></t:dgCol>
   <t:dgCol title="是否上架" field="status" align="center" dictionary="oprType" width="120"></t:dgCol>
   <t:dgCol title="备注" field="remark" width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="charteredPackageController.do?del&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="charteredPackageController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="charteredPackageController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="charteredPackageController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>