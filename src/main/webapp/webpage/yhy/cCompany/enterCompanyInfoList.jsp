<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="plug-in/tools/popup/userSelect.js"></script>
<script src="plug-in/tools/popup/departSelect.js"></script>

<div class="easyui-layout" fit="true">
<div region="center"  style="padding:0px;border:0px">
<t:datagrid name="enterCompanyInfoList" title="入驻公司信息" actionUrl="enterCompanyInfoController.do?datagrid" autoLoadData="true"  fitColumns="true"
	idField="id" fit="true" queryMode="group" >
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="组织机构id" field="deptId" hidden="true"></t:dgCol>
   <t:dgCol title="公司名称" field="departname" align="center" query="true" width="120"></t:dgCol>
   <t:dgCol title="负责人" field="director" align="center" width="120"></t:dgCol>
   <t:dgCol title="负责人手机号" field="directorPhone" align="center" width="120"></t:dgCol>
   <t:dgCol title="入驻时间" field="createDate" editor="datebox" align="center" formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group" width="200"></t:dgCol>
   <t:dgCol title="公司地址" field="address" align="center" width="120"></t:dgCol>
   <t:dgCol title="状态" field="status"  align="center" width="120"></t:dgCol>
   <t:dgCol title="备注" field="remark"  align="center" width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="enterCompanyInfoController.do?del&id={id}" />
   <%-- <t:dgToolBar title="录入" icon="icon-add" url="enterCompanyInfoController.do?addorupdate" funname="add"></t:dgToolBar> --%>
   <t:dgToolBar title="编辑" operationCode="edit" icon="icon-edit" url="enterCompanyInfoController.do?addorupdate" funname="update"></t:dgToolBar>
   <%-- <t:dgFunOpt  funname="updateCp(deptId,departname,id)" title="编辑" ></t:dgFunOpt> --%>
   <t:dgToolBar title="查看" icon="icon-search" url="enterCompanyInfoController.do?addorupdate" funname="detail"></t:dgToolBar>
  </t:datagrid>
  </div>
 </div>
 
  <script type="text/javascript">
	$(document).ready(function(){
		$("input[name='createDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
		$("input[name='createDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
		$("input[name='birthday']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
	});
	
	//编辑
	/*function updateCp(value,departname,id){
		//alert(value+departname);
		add("入驻公司信息编辑", "enterCompanyInfoController.do?addorupdate&deptId=" + value + "&departName=" + departname + "&id=" + id,"functionList","600px","350px");
	} */
	
	function update(title,url, id,width,height,isRestful) {
		gridname=id;
		var rowsData = $('#'+id).datagrid('getSelections');
		if (!rowsData || rowsData.length==0) {
			tip('请选择编辑项目');
			return;
		}
		if (rowsData.length>1) {
			tip('请选择一条记录再编辑');
			return;
		}
		if(isRestful!='undefined'&&isRestful){
			url += '/'+rowsData[0].id;
		}else{
			url += '&id='+rowsData[0].id + '&departName=' + rowsData[0].departname + '&deptId=' + rowsData[0].deptId;
		}
		createwindow(title,url,width,height);
	}
	
</script>