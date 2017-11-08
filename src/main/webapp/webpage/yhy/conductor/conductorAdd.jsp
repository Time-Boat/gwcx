<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新增验票员</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
 	<script type="text/javascript">
	 	function openDepartmentSelect() {
			//$.dialog.setting.zIndex = getzIndex(); 
			
			//var orgIds = $("#orgIds").val();
			var s = $('#status option:selected').val();
			if(s == '' || s == null){
				tip("请先选择业务类型");
				return;
			}
			$.dialog({content: 'url:conductorController.do?conductorTolinesList&ywlx='+s, zIndex: 2100, title: '线路列表', lock: true, width: '400px', height: '480px', opacity: 0.4, button: [
			   {name: '<t:mutiLang langKey="common.confirm"/>', callback: callbackSelect, focus: true},
			   {name: '<t:mutiLang langKey="common.cancel"/>', callback: function (){}}
		   ]}).zindex();
		}
	 	
	 	function callbackSelect() {
	 		var ids ="";
	 		var names ="";
	 		var iframe = this.iframe.contentWindow;
	 		var rows = iframe.$('#conductorTolines').datagrid('getSelections');
	 		if(rows.length ==0){
	 			tip('请选择项目');
				return;
	 		}
	 		for(var i=0;i<rows.length;i++){
	 			ids += rows[i].id;
	 			ids += ",";
	 			names +=rows[i].name;
	 			names +=",";
	 		}
	 		$("#lineNames").val(names);
	 		$("#lineIds").val(ids);
		}
	 	
	  	var pat = /^(\b13[0-9]{9}\b)|(\b14[7-7]\d{8}\b)|(\b15[0-9]\d{8}\b)|(\b18[0-9]\d{8}\b)|\b1[1-9]{2,4}\b$/;
	  	
	  	var b = true;
	  	
	  	//验证手机号是否已经被占用
	  	function checkPhone(phone){
			if(!pat.test(phone)){
				return;
			}
			$.ajax({
	            type:"get",
	            url:"conductorController.do?checkPhone&phone="+phone+"&id="+$("#id").val(),
	            dataType:'json',
	            success:function(d){
	           		var obj = eval('('+d.jsonStr+')');
	           		b = obj.success;
	           		if(!b){
	           			tip(obj.msg);
	           			$('#check_phone').text(obj.msg).css({color:"red"});
	           		}else{
	           			$('#check_phone').text('通过信息验证！').css({color:"#71b83d"});
	           		}
	            }
	        });
		}
	  	
	  	//提交前验证手机号
	  	function cp(){
	  		return b;
	  	}
	  	
	  	$(function(){
	  		$('#status').change(function(){
	  			$('#jurisdiction').text("");
	  			$('#jurisdiction').val("");
	  		});
	  	});
	  	
 	</script>
 	
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="conductorController.do?save" beforeSubmit="cp()" >
	<input id="id" name="id" type="hidden" value="${conductor.id}">
	<input id="lineIds" name="lineIds" type="hidden" value="${lineIds}">
	<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable" id = "formtableId">
		<tr>
			<td align="right" width="15%" nowrap><label class="Validform_label"> 用户名: </label></td>
			<td class="value">
				<input id="name" name="name" value="${conductor.name}" type="text" style="width: 150px" class="inputxt" datatype="*"/>
				<span class="Validform_checktip"></span> 
			</td>
				<%-- <td class="value" width="85%"><c:if test="${conductor.id!=null }">
					     ${conductor.name }
					     </c:if> <c:if test="${conductor.id==null }">
				<input id="name" class="inputxt" name="name" value="${conductor.name }">
				 <span class="Validform_checktip">用户名范围在2~10位字符</span> 
			</c:if></td> --%>
		</tr>
		<tr id= "add_phnoe">
			<td align="right" nowrap><label class="Validform_label"> 手机号码: </label></td>
			<td class="value">
				<input class="inputxt" id="phoneNumber" name="phoneNumber" value="${conductor.phoneNumber}" datatype="m" errormsg="手机号码不正确!" onchange="checkPhone(this.value);" >
				<span id="check_phone" class="Validform_checktip"></span>
			</td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> 年龄: </label></td>
			<td class="value"><input class="inputxt" name="age" value="${conductor.age}" datatype="n" errormsg="年龄格式不正确!"> <span class="Validform_checktip"></span></td>
		</tr>
		
		<tr>
			<td align="right"><label class="Validform_label"> 性别: </label></td>
			<td class="value"><t:dictSelect field="sex" typeGroupCode="sex" hasLabel="false" defaultVal="${conductor.sex}" datatype="*"></t:dictSelect> <span class="Validform_checktip"></span></td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> 业务类型: </label></td>
			<td class="value"><t:dictSelect id="status" field="status" typeGroupCode="carBType" hasLabel="false" defaultVal="${conductor.status}" datatype="*"  ></t:dictSelect> <span class="Validform_checktip"></span></td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> 验票线路: </label></td>
			<td class="value" align="center">
				<textarea  id="lineNames" name="lineNames" cols="60" style="margin: 0px; width: 416px; height: 66px;" readonly="readonly" datatype="*" rows="2">${lineNames}</textarea>
				<a href="#" class="easyui-linkbutton" plain="true" icon="icon-search" id="departSearch" onclick="openDepartmentSelect()">选择线路</a>
				<span class="Validform_checktip"></span>
			</td>
		</tr>
	</table>
</t:formvalid>
<t:authFilter name="formtableId"></t:authFilter>
</body>