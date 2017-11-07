<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <script type="text/javascript">
  
  	function applyDealerAllot(title,url,id,width,height){
		var ids = '';
		var rows = $("#dealerApplyList").datagrid("getSelections");
		for(var i=0;i<rows.length;i++){
			ids+=rows[i].id;
			ids+=',';
		}
		if(ids.length==0){
			tip('请选择要分配的渠道商');
			return;
		}
		ids = ids.substring(0,ids.length-1);
		url += '&ids='+ids;
		createwindow(title,url,width,height);
	}
  
  </script>
  <t:datagrid name="dealerApplyList" title="渠道商申请模块" actionUrl="dealerApplyController.do?datagrid" idField="id" fit="true" queryMode="group" checkbox="true" >
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="申请时间" field="createTime" formatter="yyyy-MM-dd hh:mm:ss" align="center" width="120"></t:dgCol>
   <t:dgCol title="公司名称" field="companyName" align="center" query="true" width="120"></t:dgCol>
   <t:dgCol title="地址" field="address" align="center" width="120"></t:dgCol>
   <t:dgCol title="手机号" field="phone" align="center" query="true" width="120"></t:dgCol>
   <t:dgCol title="申请人" field="applyPeople" align="center" query="true" width="120"></t:dgCol>
   <t:dgCol title="负责人" field="username" align="center" query="true" width="120"></t:dgCol>
   <c:if test="${del == 1}">
   	<t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   	<t:dgDelOpt title="删除" url="dealerApplyController.do?del&id={id}" />
   </c:if>
   <t:dgToolBar title="批量分配" icon="icon-redo" url="dealerApplyController.do?getAttacheList" funname="applyDealerAllot" ></t:dgToolBar> 
   <%-- <t:dgToolBar title="录入" icon="icon-add" url="dealerApplyController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="dealerApplyController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="dealerApplyController.do?addorupdate" funname="detail"></t:dgToolBar> --%>
  </t:datagrid>
  </div>
 </div>