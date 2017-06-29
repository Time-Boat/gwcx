<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="carCustomerList" title="乘客管理" actionUrl="carCustomerController.do?datagrid" fitColumns="true" queryMode="group" idField="id" fit="true">
   <t:dgCol title="主键" field="id" hidden="true"  width="120"></t:dgCol>
   <t:dgCol title="真实姓名" field="realName" query="true"  width="120"></t:dgCol>
   <t:dgCol title="性别" field="sex"  width="120"></t:dgCol>
   <t:dgCol title="手机号码" field="phone" query="true"  width="120"></t:dgCol>
   <t:dgCol title="身份证号码" field="cardNumber"   width="120"></t:dgCol>
   <t:dgCol title="居住地址" field="address"   width="120"></t:dgCol>
   <t:dgCol title="注册时间" field="createTime" formatter="yyyy-MM-dd hh:mm:ss"  width="120"></t:dgCol>
   <t:dgCol title="备注信息" field="remark"   width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgFunOpt funname="checkcommonAddr(id,realName)" title="常用地址"></t:dgFunOpt>
  </t:datagrid>
  </div>
 </div>
 <div region="east" style="width: 390px;" split="true">
<div tools="#tt" class="easyui-panel" title="常用地址" style="padding: 10px;" fit="true" border="false" id="function-panelcommonAddr"></div>

<div id="tt"></div>
</div>
<script type="text/javascript">
function checkcommonAddr(id,realName) {
	$("#function-panelcommonAddr").panel(
			{
				title :'用户名：'+realName,
				href:"carCustomerController.do?check&id="+id   //查看用户常用地址
			}
		);
}
</script>