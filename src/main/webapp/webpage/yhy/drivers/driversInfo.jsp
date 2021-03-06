<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>司机信息表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  	function getAge(){
  		var UUserCard = $("#idCard").val();
  		if(UUserCard != null && UUserCard != ''){
  			var myDate = new Date(); 
  			var month = myDate.getMonth() + 1; 
  			var day = myDate.getDate();

  			var age = myDate.getFullYear() - UUserCard.substring(6, 10) - 1; 
  			if (UUserCard.substring(10, 12) < month || UUserCard.substring(10, 12) == month && UUserCard.substring(12, 14) <= day) { 
  				age++; 
  			}
  			$("#age").val(age);
  		}
  	}

  	function uploadFile(data){
  		//alert($(".uploadify-queue-item").length);
  		if($(".uploadify-queue-item").length>0){
  			upload();
  		}else{
  			var win = frameElement.api.opener;//获取父窗口
  			//刷新主表单父窗口
  			win.reloadTable();
  			
  			//关闭当前弹出框
  			frameElement.api.close();
  			
  		//$('#driversInfoList').datagrid('reload');
  		}
  	}
  	
  	var pat = /^(\b17[0-9]{9}\b)|(\b13[0-9]{9}\b)|(\b14[7-7]\d{8}\b)|(\b15[0-9]\d{8}\b)|(\b18[0-9]\d{8}\b)|\b1[1-9]{2,4}\b$/;
  	var cardPat = /^[1-9][0-9]{5}(19[0-9]{2}|200[0-9]|2010)(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])[0-9]{3}[0-9xX]$/
  	
  	var driverb = false;
  	//如果验证过，提交的时候也需要验证
  	var drivera = false;
  	
  	//验证手机号是否已经被占用
  	function driverCheckPhone(phone){
		if(!pat.test(phone)){
			return;
		}
		$.ajax({
            type:"get",
            url:"driversInfoController.do?checkPhone&phone="+phone+"&id="+$("#id").val(),
            dataType:'json',
            success:function(d){
           		var obj = eval('('+d.jsonStr+')');
           		driverb = obj.success;
           		drivera = true;
           		if(!driverb){
           			tip(obj.msg);
           			$('#dcheck_phone').text(obj.msg).css({color:"red"});
           		}else{
           			$('#dcheck_phone').text('通过信息验证！').css({color:"#71b83d"});
           		}
            }
        });
	}
  
  	var driverc = false;
  	//如果验证过，提交的时候也需要验证
  	var driverd = false;
  	
  	//验证手机号是否已经被占用
  	function CheckIDCard(card){
  		if(!cardPat.test(card)){
			return;
		}
  		
		$.ajax({
            type:"get",
            url:"driversInfoController.do?checkCard&card="+card+"&id="+$("#id").val(),
            dataType:'json',
            success:function(d){
           		var obj = eval('('+d.jsonStr+')');
           		driverc = obj.success;
           		driverd = true;
           		if(!driverb){
           			tip(obj.msg);
           			$('#dcheck_card').text(obj.msg).css({color:"red"});
           		}else{
           			$('#dcheck_card').text('通过信息验证！').css({color:"#71b83d"});
           		}
            }
        });
	}
  
  	//提交前验证手机号
  	function dcp(){
	  	if(drivera && driverd){
	  		if(!driverb){
	  			$('#dcheck_phone').text("手机号已存在").css({color:"red"});
	  			return driverb;
	  		}
	  		if(!driverc){
	  			$('#dcheck_card').text("身份证号已存在").css({color:"red"});
	  			return driverc;
	  		}
	  	}
  		return true;
  	}
  
  </script>
 </head>
 <body >
 
  <t:formvalid formid="driversInfo" tabtitle="aaaa" dialog="true" usePlugin="password" layout="table" callback="@Override uploadFile" action="driversInfoController.do?save"
  				beforeSubmit="dcp()"  >
			<input id="id" name="id" type="hidden" value="${driversInfoPage.id }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							司机姓名:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="name" name="name" datatype="*" value="${driversInfoPage.name}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							电话:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="phoneNumber" name="phoneNumber" datatype="m" errormsg="手机号非法"
							   value="${driversInfoPage.phoneNumber}" onchange="driverCheckPhone(this.value)" >
						<span id="dcheck_phone" class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							身份证:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="idCard" name="idCard" datatype="/^[1-9][0-9]{5}(19[0-9]{2}|200[0-9]|2010)(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])[0-9]{3}[0-9xX]$/" 
							   errormsg="身份证号非法" onchange="CheckIDCard(this.value)" value="${driversInfoPage.idCard}" onblur="getAge()">
						<span id="dcheck_card" class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							驾照类型:
						</label>
					</td>
					<td class="value">
					    <t:dictSelect field="drivingLicense" typeGroupCode="drivingLic" hasLabel="false" defaultVal="${driversInfoPage.drivingLicense}" datatype="*"></t:dictSelect>
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">所在城市: </label>
					</td>
					<td class="value">
						<select id="city" name="city" datatype="*" >
							<option value="">--请选择城市--</option>
							<c:forEach var="c" items="${cities}">
								<option value="${c.cityId}" <c:if test="${driversInfoPage.cityId == c.cityId}" >selected="selected"</c:if> >
									${c.cityName}
								</option>
							</c:forEach>
						</select> 
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							性别:
						</label>
					</td>
					<td class="value">
						<t:dictSelect field="sex" typeGroupCode="sex" hasLabel="false" defaultVal="${driversInfoPage.sex}" datatype="*"></t:dictSelect>
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							年龄:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="age" name="age" ignore="ignore"
							   value="${driversInfoPage.age}" datatype="n">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							备注:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="remark" name="remark" ignore="ignore"
							   value="${driversInfoPage.remark}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				
			</table>
		</t:formvalid>
 </body>
 
