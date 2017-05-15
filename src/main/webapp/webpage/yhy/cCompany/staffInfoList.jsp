<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
<div region="center"  style="padding:0px;border:0px">
  <t:datagrid name="staffInfoList" title="公司员工入驻信息" actionUrl="staffInfoController.do?datagrid" idField="id" fit="true" fitColumns="true" autoLoadData="true" queryMode="group" >
   <t:dgCol title="编号" field="id" hidden="true" ></t:dgCol>
   <t:dgCol title="员工名称" field="name" query="true" align="center" width="120"></t:dgCol>
   <t:dgCol title="性别" field="sex" dictionary="sex" align="center" width="120"></t:dgCol>
   <t:dgCol title="手机号" field="phone" query="true" align="center" width="120"></t:dgCol>
   <t:dgCol title="年龄" field="age" width="120" align="center" ></t:dgCol>
   <t:dgCol title="所属部门" field="depart" width="120" align="center" ></t:dgCol>
   <t:dgCol title="员工职位" field="staffPosition" width="120" align="center" ></t:dgCol>
   <%-- <t:dgCol title="状态" field="status" width="120"></t:dgCol> --%>
   <t:dgCol title="备注" field="remark" width="120" align="center" ></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="staffInfoController.do?del&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="staffInfoController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="staffInfoController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="staffInfoController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
</div>
 
 <input type="text" value="${companyList}" id="companys" />
  
 <script type="text/javascript">
 
 	$(function(){
 		var json = $("#companys").val();
 		var obj = eval('(' + json + ')');
 		var a1 = '<span style="display:-moz-inline-box;display:inline-block;"><span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;';
 		var a2 = 'text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; "title="公司名称">公司名称：</span>';
 		var a3 = '<select name="companyId" style="width: 150px">';
 		var c1 = '<option value="">选择入住公司</option>';
		for(var i=0;i<obj.data.length;i++){
			c1 += '<option value="'+obj.data[i].id+'">'+obj.data[i].departname+'</option>';
		}
 		var a4 = '</select></span>';
 		$("#staffInfoListForm").append(a1+a2+a3+c1+a4);
 	});
 	
 </script>
 
 
 
 
 