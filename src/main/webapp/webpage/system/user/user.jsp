<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>用户信息</title>
<t:base type="jquery,easyui,tools"></t:base>
    <script>
<%-- //        update-start--Author:zhangguoming  Date:20140826 for：将combobox修改为combotree
        function setOrgIds() {
//            var orgIds = $("#orgSelect").combobox("getValues");
            var orgIds = $("#orgSelect").combotree("getValues");
            $("#orgIds").val(orgIds);
        }
        $(function() {
            $("#orgSelect").combotree({
                onChange: function(n, o) {
                    if($("#orgSelect").combotree("getValues") != "") {
                        $("#orgSelect option").eq(1).attr("selected", true);
                    } else {
                        $("#orgSelect option").eq(1).attr("selected", false);
                    }
                }
            });
            $("#orgSelect").combobox("setValues", ${orgIdList});
            $("#orgSelect").combotree("setValues", ${orgIdList});
        }); --%>
        
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
	  	function cpUser(){
	  		
	  		//var a = true;
	  		
	  		if(roleType != ""){
		  		var lineOrgCodes = "";
		  		$("input:checkbox[name='lineOrgCode']:checked").each(function() { // 遍历name=lineOrgCode的多选框
		  			lineOrgCodes += $(this).val() + ",";  // 每一个被选中项的值
		  		});
		  		
		  		if(lineOrgCodes == ""){
		  			tip("至少选择一个责任公司");
		  			a = false;
		  		}
		  		console.log("lineOrgCode : " + lineOrgCodes + "------------" + "roleType : " + roleType);
		  		//修改要提交的action
		  		$('#userController').attr("action","userController.do?saveUser&lineOrgCode=" + lineOrgCodes.substring(0,lineOrgCodes.length-1) + "&roleType=" + roleType);
	  		}
	  		/* console.log(a);
	  		if(a){
	  			$.dialog.confirm("确定要激活用户吗?", function(r) {
		  			if(r){
		  				$('#aaab').click();
		  			}
				});
	  		} */
	  	}

		function openDepartmentSelect() {
			$.dialog.setting.zIndex = getzIndex(); 
			var orgIds = $("#orgIds").val();
			
			$.dialog({content: 'url:departController.do?departSelect&orgIds='+orgIds, zIndex: 2100, title: '组织机构列表', lock: true, width: '400px', height: '350px', opacity: 0.4, button: [
			   {name: '<t:mutiLang langKey="common.confirm"/>', callback: callbackDepartmentSelect, focus: true},
			   {name: '<t:mutiLang langKey="common.cancel"/>', callback: function (){}}
		   ]}).zindex();
		}
			
		function callbackDepartmentSelect() {
			  var iframe = this.iframe.contentWindow;
			  var treeObj = iframe.$.fn.zTree.getZTreeObj("departSelect");
			  var nodes = treeObj.getCheckedNodes(true);
			  if(nodes.length>0){
			  var ids='',names='';
			  for(i=0;i<nodes.length;i++){
			     var node = nodes[i];
			     ids += node.id+',';
			    names += node.name+',';
			 }
			  
			 $('#departname').val(names);
			 $('#departname').blur();		
			 $('#orgIds').val(ids);		
			}
		}
		
		function callbackClean(){
			$('#departname').val('');
			$('#orgIds').val('');	
		}
		
		function setOrgIds() {}
		$(function(){
			$("#departname").prev().hide();
		});
		
		//开关
		var oc = false;
		
		//是否已经加载过数据,不重复加载
		var isLoad = false;
		
		//角色类型    0：线路审核员   1：渠道商审核员   2：退款审核员
		var roleType = "";
		
		//弹出框确定之后的回调函数
		function roleSuccess(){
			roleType = "";
			var names = $('#roleName').val();
			if(typeof(names) != 'undefined' && names != null && names != ''){
				var arr = names.split(",");
				for(var i=0;i<arr.length;i++){
					switch (arr[i]) {
				        case "平台线路审核员":
				        	roleType += "0";
				        	oc = true;
				            break;
				        case "平台渠道商审核员":
				        	roleType += "1";
				        	oc = true;
				            break;
				        case "平台退款审核员":
				        	roleType += "2";
				        	oc = true;
				            break;
					}
				}
				if(oc){
					$('#company_tr').show();
				}else{
					$('#company_tr').hide();
					roleType = "";
				}
			}
			
			if(oc){
				$.get(
					"userController.do?getCompany&id="+$('#id').val()+"&roleType="+roleType,
					function(data){
						console.log(data);
						if(data.success){
							var obj = data.obj;
							obj = eval('(' + obj + ')');
							var td = "";
							var ocs = $('#orgCompanys').val();
							for(var i=0;i<obj.length;i++){
								td += '<label class="demo--label">';
								if(ocs.indexOf(obj[i].org_code) >= 0){
									td += '<input class="demo--radio" name="lineOrgCode" type="checkbox" checked="checked" value="' + obj[i].org_code + '" />';
								}else{
									td += '<input class="demo--radio" name="lineOrgCode" type="checkbox" value="' + obj[i].org_code + '" />';
								}
									
								td += '<span class="demo--checkbox demo--radioInput"></span> ' + obj[i].departname;
								td += '</label> ';
							}
							$("#company_td").empty().append(td);
							console.log(roleType);
							//<span class="Validform_checktip"></span>
						}
						isLoad = true;
					},
					"json"
				);
			}
			
		}
		
		$(function(){
			roleSuccess();
		});
		
    </script>
      <!-- 多选框样式 -->
  <style type="text/css">
  	.demo--label{margin:5px 30px 5px 0;display:inline-block}
	.demo--radio{display:none}
	.demo--radioInput{background-color:#fff;border:1px solid rgba(0,0,0,0.15);border-radius:100%;display:inline-block;height:16px;margin-right:10px;margin-top:-1px;vertical-align:middle;width:16px;line-height:1}
	.demo--radio:checked + .demo--radioInput:after{background-color:#57ad68;border-radius:100%;content:"";display:inline-block;height:12px;margin:2px;width:12px}
	.demo--checkbox.demo--radioInput,.demo--radio:checked + .demo--checkbox.demo--radioInput:after{border-radius:0}
  </style>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="userController" dialog="true" usePlugin="password" layout="table" action="userController.do?saveUser" beforeSubmit="cpUser()" ><%-- btnsub="aaab"  --%>
	<input id="id" name="id" type="hidden" value="${user.id }">
	<input id="orgCompanys" name="orgCompanys" type="hidden" value="${user.orgCompany }">
	<!-- <input id="aaab" name="aaab" value="提交按钮"> -->
	<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
		<tr>
			<td align="right" width="25%" nowrap>
                <label class="Validform_label">  <t:mutiLang langKey="common.username"/>:  </label>
            </td>
			<td class="value" width="85%">
                <c:if test="${user.id!=null }"> ${user.userName } </c:if>
                <c:if test="${user.id==null }">
                    <input id="userName" class="inputxt" name="userName" validType="t_s_base_user,userName,id" value="${user.userName }" datatype="s2-10" />
                    <span class="Validform_checktip"> <t:mutiLang langKey="username.rang2to10"/></span>
                </c:if>
            </td>
		</tr>
		<tr>
			<td align="right" width="10%" nowrap><label class="Validform_label"> <t:mutiLang langKey="common.real.name"/>: </label></td>
			<td class="value" width="10%">
                <input id="realName" class="inputxt" name="realName" value="${user.realName }" datatype="s2-10">
                <span class="Validform_checktip"><t:mutiLang langKey="fill.realname"/></span>
            </td>
		</tr>
		<c:if test="${user.id==null }">
			<tr>
				<td align="right"><label class="Validform_label"> <t:mutiLang langKey="common.password"/>: </label></td>
				<td class="value">
                    <input type="password" class="inputxt" value="" name="password" plugin="passwordStrength" datatype="*6-18" errormsg="" />
                    <span class="passwordStrength" style="display: none;">
                        <span><t:mutiLang langKey="common.weak"/></span>
                        <span><t:mutiLang langKey="common.middle"/></span>
                        <span class="last"><t:mutiLang langKey="common.strong"/></span>
                    </span>
                    <span class="Validform_checktip"> <t:mutiLang langKey="password.rang6to18"/></span>
                </td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> <t:mutiLang langKey="common.repeat.password"/>: </label></td>
				<td class="value">
                    <input id="repassword" class="inputxt" type="password" value="${user.password}" recheck="password" datatype="*6-18" errormsg="两次输入的密码不一致！">
                    <span class="Validform_checktip"><t:mutiLang langKey="common.repeat.password"/></span>
                </td>
			</tr>
		</c:if>
		<tr>
			<td align="right"><label class="Validform_label"> <t:mutiLang langKey="common.department"/>: </label></td>
			<td class="value">
                <%--<select class="easyui-combobox" data-options="multiple:true, editable: false" id="orgSelect" datatype="*">--%>
                <%--<select class="easyui-combotree" data-options="url:'departController.do?getOrgTree', multiple:true, cascadeCheck:false"
                        id="orgSelect" name="orgSelect" datatype="select1">
                update-end--Author:zhangguoming  Date:20140826 for：将combobox修改为combotree
                    <c:forEach items="${departList}" var="depart">
                        <option value="${depart.id }">${depart.departname}</option>
                    </c:forEach>
                </select> --%>
                <%--  <t:departSelect departId="${tsDepart.id }" departName="${tsDepart.departname }"></t:departSelect>--%>
                
                <input id="departname" name="departname" type="text" readonly="readonly" class="inputxt" datatype="*" value="${departname}">
                <input id="orgIds" name="orgIds" type="hidden" value="${orgIds}">
                <a href="#" class="easyui-linkbutton" plain="true" icon="icon-search" id="departSearch" onclick="openDepartmentSelect()">选择</a>
                <a href="#" class="easyui-linkbutton" plain="true" icon="icon-redo" id="departRedo" onclick="callbackClean()">清空</a>
                <span class="Validform_checktip"><t:mutiLang langKey="please.muti.department"/></span>
            </td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> <t:mutiLang langKey="common.role"/>: </label></td>
			<td class="value" nowrap>
                <input name="roleid" name="roleid" type="hidden" value="${id}" id="roleid">
                <input name="roleName" class="inputxt" value="${roleName }" id="roleName" readonly="readonly" datatype="*" />
                <t:choose hiddenName="roleid" hiddenid="id" url="userController.do?roles" name="roleList"
                          icon="icon-search" title="common.role.list" textname="roleName" isclear="true" isInit="true" fun="roleSuccess" ></t:choose>
                <span class="Validform_checktip"><t:mutiLang langKey="role.muti.select"/></span>
            </td>
		</tr>
		<tr id="company_tr" hidden="true">
			<td align="right">
				<label class="Validform_label">
					选择责任公司:
				</label>
			</td>
			<td class="value" id="company_td">
			</td>
		</tr>
		<tr>
			<td align="right" nowrap><label class="Validform_label">  <t:mutiLang langKey="common.phone"/>: </label></td>
			<td class="value">
                <input class="inputxt" name="mobilePhone" value="${user.mobilePhone}" datatype="m" errormsg="手机号码不正确"  onchange="checkPhone(this.value);" >
                <span class="Validform_checktip" id="check_phone"></span>
            </td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label">  <t:mutiLang langKey="common.tel"/>: </label></td>
			<td class="value">
                <input class="inputxt" name="officePhone" value="${user.officePhone}" datatype="n" errormsg="办公室电话不正确" ignore="ignore">
                <span class="Validform_checktip"></span>
            </td>
		</tr>
		<tr>
			<td align="right"><label class="Validform_label"> <t:mutiLang langKey="common.common.mail"/>: </label></td>
			<td class="value">
                <input class="inputxt" name="email" value="${user.email}" datatype="e" errormsg="邮箱格式不正确!" ignore="ignore">
                <span class="Validform_checktip"></span>
            </td>
		</tr>
	</table>
</t:formvalid>
</body>