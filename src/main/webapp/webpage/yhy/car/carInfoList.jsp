<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="carInfoList" title="车辆信息" autoLoadData="true" actionUrl="carInfoController.do?datagrid" idField="id" fitColumns="true" queryMode="group" fit="true">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="车牌号" field="licencePlate" query="true" align="center" width="80"></t:dgCol>
   <t:dgCol title="车辆类型" field="carType" dictionary="car_Type" align="center" query="true" width="50"></t:dgCol>
   <t:dgCol title="座位数" field="seat" align="center" width="50"></t:dgCol>
   
   <t:dgCol title="购置日期" field="buyDate" formatter="yyyy-MM-dd" align="center" ></t:dgCol>
   <t:dgCol title="品牌" dictionary="carBrand" field="carBrand" align="center" ></t:dgCol>
   <t:dgCol title="型号" field="modelNumber" align="center" ></t:dgCol>
   
   <t:dgCol title="车辆位置" field="stopPosition" align="center" width="120"></t:dgCol>
   <t:dgCol title="司机" field="name" align="center"  width="50"></t:dgCol>
   <t:dgCol title="创建人" field="username" align="center"  width="50"></t:dgCol>
   <t:dgCol title="创建时间" field="createTime" editor="datebox" formatter="yyyy-MM-dd hh:mm:ss" align="center" ></t:dgCol>
   <t:dgCol title="车辆状态" field="status" align="center" query="true" dictionary="carType" width="50"></t:dgCol>
   <t:dgCol title="业务类型" field="businessType" align="center" query="true" dictionary="carBType" width="80"></t:dgCol>
   
   <t:dgCol title="运营状态" field="carStatus" dictionary="lineStatus" align="center" width="50"></t:dgCol>
   <t:dgCol title="申请状态" field="applicationStatus" dictionary="line_apply_status" align="center" width="50"></t:dgCol>
   <t:dgCol title="申请内容" field="applyContent" dictionary="apply_type" align="center"  width="50"></t:dgCol>
   
   <t:dgCol title="备注" field="remark"  align="center" width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" align="center" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="carInfoController.do?del&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="carInfoController.do?addorupdate" funname="add" width="600" height="500"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="carInfoController.do?addorupdate" funname="update" height="500" ></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="carInfoController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>