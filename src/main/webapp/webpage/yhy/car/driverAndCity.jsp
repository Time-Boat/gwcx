<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="plug-in/tools/popup/userSelect.js"></script>
<script src="plug-in/tools/popup/departSelect.js"></script>

<div class="easyui-layout" fit="true">
<div region="center"  style="padding:0px;border:0px">
 <t:datagrid name="driversInfoList" title="司机信息管理" autoLoadData="true" actionUrl="driversInfoController.do?driverdatagrid&status=0"  fitColumns="true"
	idField="id" fit="true" queryMode="group" >
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="司机姓名" field="name" query="true" frozenColumn="true" align="center" width="120"></t:dgCol>
	<t:dgCol title="性别"  field="sex" dictionary="sex" query="true" align="center" width="60"></t:dgCol>
	<t:dgCol title="所在城市"  field="cityName" align="center" width="60"></t:dgCol>
	<t:dgCol title="驾照类型"  field="drivingLicense" replace="A1_0,A2_1,A3_2,B1_3,B2_4,C1_5,C2_6" query="true" align="center" width="60"></t:dgCol>
	<t:dgCol title="年龄"  editor="numberbox" field="age" align="center" width="80"></t:dgCol>
	<t:dgCol title="电话"  query="true" field="phoneNumber" align="center" width="80"></t:dgCol>
	<t:dgCol title="身份证" field="idCard" align="center" width="80"></t:dgCol>
	<t:dgCol title="创建日期" field="createDate" editor="datebox" formatter="yyyy-MM-dd hh:mm:ss" queryMode="group" align="center" width="100"></t:dgCol>
	<t:dgCol title="状态" field="deleteFlag" dictionary="deleted" align="center" width="80"></t:dgCol>
	<t:dgCol title="备注" field="remark" align="center" width="80"></t:dgCol>	
	
</t:datagrid> 

</div>
</div>
<input type="hidden" value="${cityList}" id="citylie" />

<script type="text/javascript">
	$(function() {
		var json = $("#citylie").val();
		var a1 = '<span style="display:-moz-inline-box;display:inline-block; padding:10px 2px;"><span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;';
		var a2 = 'text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; "title="选择城市">选择城市：</span>';
		var a3 = '<select name="cityID" style="width: 150px">';
		var c1 = '<option value="">所在城市</option>';
		if(json.indexOf("cityID")>0){
			var obj = eval('(' + json + ')');
			for (var i = 0; i < obj.data.length; i++) {
				c1 += '<option value="'+obj.data[i].cityID+'">' + obj.data[i].cityName+ '</option>';
			}
		}
		
		var a4 = '</select></span>';
		$("#driversInfoListForm").append(a1 + a2 + a3 + c1 + a4);
	});
</script>
