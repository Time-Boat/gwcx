<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="plug-in/tools/popup/userSelect.js"></script>
<script src="plug-in/tools/popup/departSelect.js"></script>

<div class="easyui-layout" fit="true">
<div region="center" style="padding:0px;border:0px">
 <t:datagrid name="driversInfoList" title="司机信息管理" autoLoadData="true" actionUrl="driversInfoController.do?datagrid"  fitColumns="true"
	idField="id" fit="true" queryMode="group" >
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="司机姓名" field="name" query="true" frozenColumn="true" align="center" width="120"></t:dgCol>
	<t:dgCol title="性别" sortable="true" field="sex" dictionary="sex" query="true" align="center" width="60"></t:dgCol>
	<t:dgCol title="所在城市" sortable="true" field="cityName" align="center" width="60"></t:dgCol>
	<t:dgCol title="年龄" sortable="true" editor="numberbox" field="age" align="center" width="80"></t:dgCol>
	<t:dgCol title="电话" sortable="true" query="true" field="phoneNumber" align="center" width="80"></t:dgCol>
	<t:dgCol title="身份证" field="idCard" align="center" width="80"></t:dgCol>
	<t:dgCol title="创建人" field="username" align="center" width="80"></t:dgCol>
	<t:dgCol title="创建日期" field="createDate" editor="datebox" formatter="yyyy-MM-dd hh:mm:ss" queryMode="group" align="center" width="100"></t:dgCol>
	<t:dgCol title="备注" field="remark" align="center" width="80"></t:dgCol>	
	<t:dgToolBar operationCode="add" title="录入" icon="icon-add" url="driversInfoController.do?addorupdate&type=add" funname="add"></t:dgToolBar>
	<t:dgToolBar operationCode="edit" title="编辑" icon="icon-edit" url="driversInfoController.do?addorupdate" funname="update"></t:dgToolBar>
	<t:dgCol title="操作" field="opt" width="50"></t:dgCol>
	<t:dgDelOpt title="common.delete" url="driversInfoController.do?del&id={id}" urlStyle="align:center" />
	<%-- <t:dgToolBar title="批量删除" icon="icon-remove" url="driversInfoController.do?doDeleteALLSelect" funname="deleteALLSelect"></t:dgToolBar> --%>
	<t:dgToolBar operationCode="detail" title="查看" icon="icon-search" url="driversInfoController.do?addorupdate" funname="detail"></t:dgToolBar>

</t:datagrid> 

</div>
</div>
<input type="hidden" value="${cityList}" id="citylie" />
<script type="text/javascript">
	$(function() {
		var json = $("#citylie").val();
		var obj = eval('(' + json + ')');
		var a1 = '<span style="display:-moz-inline-box;display:inline-block; padding:10px 2px;"><span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;';
		var a2 = 'text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; "title="选择城市">选择城市：</span>';
		var a3 = '<select name="cityID" style="width: 150px">';
		var c1 = '<option value="">所在城市</option>';
		for (var i = 0; i < obj.data.length; i++) {
			c1 += '<option value="'+obj.data[i].cityID+'">' + obj.data[i].cityName
					+ '</option>';
		}
		var a4 = '</select></span>';
		$("#driversInfoListForm").append(a1 + a2 + a3 + c1 + a4);
	});
	
	//删除调用函数
	function delObj(url,name) {
		
		var id = url.substr(url.lastIndexOf("=") + 1);
		$.get(
			"driversInfoController.do?checkBinding&id="+id,
			function(data){
				var obj = eval("(" + data + ")");
				if(!obj.success){
					tip(obj.msg);
				}else{
					gridname=name;
					createdialog('删除确认 ', '确定删除该记录吗 ?', url,name);
				}
			}
		);
	}
	
</script>
