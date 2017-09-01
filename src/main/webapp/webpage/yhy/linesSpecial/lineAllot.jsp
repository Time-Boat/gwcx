<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="plug-in/tools/popup/userSelect.js"></script>
<script src="plug-in/tools/popup/departSelect.js"></script>

<div class="easyui-layout" fit="true">
<div region="center"  style="padding:0px;border:0px">
<t:datagrid name="allotuserList" title="分配用户" autoLoadData="true" actionUrl="lineInfoSpecializedController.do?userdatagrid"  fitColumns="true"
	idField="id" fit="true" queryMode="group">
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="用户名" field="username" frozenColumn="true" align="center" width="120"></t:dgCol>
	<t:dgCol title="真实姓名" field="realname" dictionary="sex" align="center" width="60"></t:dgCol>
	<t:dgCol title="组织机构" field="departname"  align="center"  width="120"></t:dgCol>
	<t:dgCol title="角色" field="rolename"  align="center"  width="120"></t:dgCol>

</t:datagrid></div>
</div>
<div style="display: none">
    <t:formvalid formid="formobj" layout="div" dialog="true" action="lineInfoSpecializedController.do?allot&lineid=${ids}" beforeSubmit="setIds">
         <input id="ids" name="ids">
         <input id="username" name="username">  
    </t:formvalid>
</div>
 <script type="text/javascript">
 function setIds() {
	 var rows = $('#allotuserList').datagrid('getSelections');
     $("#ids").val(rows[0].id);
     $("#username").val(rows[0].username);
     return true;
 }
</script>