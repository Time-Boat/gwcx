<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/context/mytags.jsp" %>
<%--非当前组织机构的用户列表--%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div id="main_depart_list" class="easyui-layout" fit="true">
    <div region="center"  style="padding:0px;border:0px">
        <t:datagrid name="dealerCurDepartUserList" title="common.operation"
                    actionUrl="departController.do?addUserToOrgList" fit="true" fitColumns="true"
                    idField="id" queryMode="group">
            <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
            <t:dgCol title="用户名" sortable="false" field="userName" width="120" ></t:dgCol>
            <t:dgCol title="真是姓名" field="realName" width="120" ></t:dgCol>
            <t:dgCol title="状态" sortable="true" field="status" replace="common.active_1,common.inactive_0,super.admin_-1" width="120" ></t:dgCol>
        </t:datagrid>
    </div>
</div>

<div style="display: none">
    <t:formvalid formid="formobj" layout="div" dialog="true" action="dealerInfoController.do?allotAttache&ids=${ids}" beforeSubmit="setUserId">
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
        var rows = $('#dealerCurDepartUserList').datagrid('getSelections');
        for (var i = 0; i < rows.length; i++) {
            ids.push(rows[i][field]);
        }
        ids.join(',');
        return ids
    }
</script>
