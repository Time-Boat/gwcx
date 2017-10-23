<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="charteredPriceList" title="包车价格设置" actionUrl="charteredPriceController.do?datagrid" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="包车套餐" field="packageName" align="center"  width="100"></t:dgCol>
   <t:dgCol title="车辆类型" field="carType" align="center" dictionary="car_type"  width="100"></t:dgCol>
   <t:dgCol title="起步价（元）" field="initiatePrice" align="center"  width="100"></t:dgCol>
   <t:dgCol title="超公里/元" field="exceedKmPrice" align="center"  width="100"></t:dgCol>
   <t:dgCol title="超时长/元" field="exceedTimePrice" align="center"  width="100"></t:dgCol>
   <t:dgCol title="所在城市" field="city" align="center" width="100"></t:dgCol>
   <t:dgCol title="空反费（公里/元）" field="emptyReturn" align="center"  width="120"></t:dgCol>
   <t:dgCol title="创建人" field="username" align="center"  width="100"></t:dgCol>
   <t:dgCol title="创建时间" field="createTime" formatter="yyyy-MM-dd hh:mm:ss" align="center" width="150"></t:dgCol>
   <t:dgCol title="修改用户" field="modifyUserName" align="center" width="100"></t:dgCol>
   <t:dgCol title="修改时间" field="midifyTime" formatter="yyyy-MM-dd hh:mm:ss" align="center" width="150"></t:dgCol>
  
   <t:dgCol title="状态 " field="status" dictionary="driver_status"  align="center"  width="100"></t:dgCol>
   <t:dgCol title="审核状态 " field="auditStatus" dictionary="audit_status" align="center"  width="100"></t:dgCol>
   <t:dgCol title="申请类型 " field="applyType" align="center"  width="100"></t:dgCol>
   <t:dgCol title="备注" field="remark" align="center"  width="120"></t:dgCol>
   
   <%-- <t:dgCol title="提交申请人" field="applyUser"   width="120"></t:dgCol>
   <t:dgCol title="提交申请时间" field="applyDate" formatter="yyyy-MM-dd hh:mm:ss"  width="120"></t:dgCol>
   <t:dgCol title="审核人" field="auditUser"   width="120"></t:dgCol>
   <t:dgCol title="审核时间" field="auditDate" formatter="yyyy-MM-dd hh:mm:ss"  width="120"></t:dgCol>
   <t:dgCol title="拒绝原因" field="rejectReason"   width="120"></t:dgCol>
   <t:dgCol title="复审用户" field="lastAuditUser"   width="120"></t:dgCol>
   <t:dgCol title="复审时间" field="lastAuditDate" formatter="yyyy-MM-dd hh:mm:ss"  width="120"></t:dgCol>
   <t:dgCol title="复审拒绝原因" field="lastRejectReason"   width="120"></t:dgCol> --%>
   <t:dgCol title="操作" field="opt" width="250"></t:dgCol>
   <t:dgDelOpt title="删除" url="charteredPriceController.do?del&id={id}" />
   <t:dgToolBar title="添加报价" icon="icon-add" url="charteredPriceController.do?addorupdate" funname="add" height="600"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="charteredPriceController.do?addorupdate" funname="update" height="600"></t:dgToolBar>
   <t:dgToolBar title="查看详情" icon="icon-search" url="charteredPriceController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>