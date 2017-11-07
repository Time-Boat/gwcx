<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<%--非当前组织机构的用户列表--%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="main_depart_list" class="easyui-layout" fit="true">
    <div region="center"  style="padding:0px;border:0px">
        <t:datagrid name="applyDealerDepartUserList" title="common.operation"
                    actionUrl="dealerApplyController.do?getAttacheDatagrid" fit="true" fitColumns="true"
                    idField="id" queryMode="group">
           	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="用户名" field="username" frozenColumn="true" align="center" width="120"></t:dgCol>
			<t:dgCol title="真实姓名" field="realname" dictionary="sex" align="center" width="60"></t:dgCol>
			<t:dgCol title="组织机构" field="departname"  align="center"  width="120"></t:dgCol>
			<t:dgCol title="角色" field="rolename"  align="center"  width="120"></t:dgCol>
        </t:datagrid>
    </div>
</div>

<div style="display: none">
    <t:formvalid formid="formobj" layout="div" dialog="true" action="dealerApplyController.do?allotAttache&ids=${ids}" beforeSubmit="setUserId">
        <input id="userId" name="userId">
    </t:formvalid>
</div>
<script>
    function setUserId() {
        $("#userId").val(getUserListSelection('id'));
        return true;
    }
	
    function getUserListSelection(field) {
        var ids = [];
        var rows = $('#applyDealerDepartUserList').datagrid('getSelections');
        for (var i = 0; i < rows.length; i++) {
            ids.push(rows[i][field]);
        }
        ids.join(',');
        return ids
    }
</script>
