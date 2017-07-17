<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="areaLineList" title="包车区域线路" actionUrl="areaLineController.do?datagrid" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="线路名称" field="name" width="120"></t:dgCol>
   <t:dgCol title="机场或火车站站点" field="stationId" width="120"></t:dgCol>
   <%-- <t:dgCol title="线路图片" field="imagePath" width="120"></t:dgCol> --%>
   <t:dgCol title="线路类型" field="lineType"  width="120"></t:dgCol>
   <t:dgCol title="线路状态" field="status" dictionary="lineStatus" width="120"></t:dgCol>
   <t:dgCol title="线路创建时间" field="createTime" formatter="yyyy-MM-dd hh:mm:ss" width="120"></t:dgCol>
   <t:dgCol title="线路创建人" field="createPeople" width="120"></t:dgCol>
   <%-- <t:dgCol title="线路编号" field="lineNumber" width="120"></t:dgCol> --%>
   <%-- <t:dgCol title="部门id 用于权限过滤" field="departId"   width="120"></t:dgCol> --%>
   <%-- <t:dgCol title="线路的单价" field="linePrice" width="120"></t:dgCol> --%>
   <%-- <t:dgCol title="线路时长" field="lineDuration" width="120"></t:dgCol> --%>
   <%-- <t:dgCol title="城市id" field="cityId" width="120"></t:dgCol> --%>
   <t:dgCol title="城市名称" field="cityName" width="120"></t:dgCol>
   <t:dgCol title="出车时间段" field="dispath" dictionary="dispathtime" width="120"></t:dgCol>
   <t:dgCol title="车辆类型" field="carType" width="120" ></t:dgCol>
   <t:dgCol title="所属区域（高德）" field="district" width="120"></t:dgCol>
   <%-- <t:dgCol title="区域id （高德）" field="districtId" width="120"></t:dgCol> --%>
   <t:dgCol title="备注" field="remark" width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="areaLineController.do?del&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="areaLineController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="areaLineController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="areaLineController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
