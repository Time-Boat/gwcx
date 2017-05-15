<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="plug-in/tools/popup/userSelect.js"></script>
<script src="plug-in/tools/popup/departSelect.js"></script>
<div class="easyui-layout" fit="true">
<div region="center"  style="padding:0px;border:0px">
	<t:datagrid name="busStopInfoList" title="站点信息查询" autoLoadData="true" actionUrl="lineInfoController.do?nullTobusStopInfoList&lineInfoId=${lineInfoId}&lineType=${lineType}"  
		fitColumns="true" idField="id" fit="true" queryMode="group" checkbox="true" >
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="站点名称" field="name"  frozenColumn="true" query="true" align="center" width="120"></t:dgCol>
	<t:dgCol title="站点地址" field="stopLocation"  align="center" query="true" width="120"></t:dgCol>
	<t:dgCol title="创建时间" field="createTime" editor="datebox" formatter="yyyy-MM-dd hh:mm:ss" align="center" width="200"></t:dgCol>
	<t:dgCol title="创建人" field="createPeople"  align="center" width="60"></t:dgCol>
	<t:dgCol title="备注" field="remark" align="center" width="80"></t:dgCol>	
</t:datagrid>
</div>
</div>
<div style="display: none">
    <t:formvalid formid="formobj" layout="div" dialog="true" action="busStopInfoController.do?addSave&lineInfoId=${lineInfoId}&lineType=${lineType}" beforeSubmit="setIds">
         <input id="ids" name="ids"> 
    </t:formvalid>
</div>
<script type="text/javascript">
	$(document).ready(function(){
		$("input[name='createTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
		$("input[name='createTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
		$("input[name='birthday']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
	});
	function setIds() {
        $("#ids").val(getUserListSelections('id'));
        return true;
    }
	function getUserListSelections(field) {
        var ids = [];
        var rows = $('#busStopInfoList').datagrid('getSelections');
        for (var i = 0; i < rows.length; i++) {
            ids.push(rows[i][field]);
        }
        ids.join(',');
        return ids
    }
</script>


 