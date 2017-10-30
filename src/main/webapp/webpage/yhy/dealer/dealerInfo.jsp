<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>渠道商信息</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  
	$(function(){
		console.log(1);
		var id = $('#id').val();
		if(id != ''){
			checkPhone($('#phone').val());
			checkCreditCode($('#creditCode').val());
		}
	});
  
	var b = false;
	//验证信用代码和手机号是否已经被占用
	function checkCreditCode(creditCode){
		
		var id = $('#id').val();
		
		$.ajax({
          type:"get",
          url:"dealerInfoController.do?checkCreditCode&creditCode="+creditCode+"&id="+id,
          dataType:'json',
          success:function(d){
	       		var obj = eval('('+d.jsonStr+')');
	       		b = obj.success;
	       		if(!b){
	       			tip(obj.msg);
	       			$('#checkCreditCode').text(obj.msg).css({color:"red"});
	       		}else{
	       			$('#checkCreditCode').text('通过信息验证！').css({color:"#71b83d"});
	       		}
          }
      });
	}
	
	var a = false;
	//验证手机号是否已经被占用
	function checkPhone(phone){
		
		var id = $('#id').val();
		
		$.ajax({
          type:"get",
          url:"dealerInfoController.do?checkPhone&phone="+phone+"&id="+id,
          dataType:'json',
          success:function(d){
	       		var obj = eval('('+d.jsonStr+')');
	       		a = obj.success;
	       		if(!a){
	       			tip(obj.msg);
	       			$('#checkPhone').text(obj.msg).css({color:"red"});
	       		}else{
	       			$('#checkPhone').text('通过信息验证！').css({color:"#71b83d"});
	       		}
          }
      });
	}
	
	//提交前验证公司社会信用代码
  	function cp(){
		if(!a){
			tip('手机号已经存在，请重新输入');
			$('#checkPhone').text('');
			return a;
		}
		if(!b){
			tip('公司社会信用代码已经存在，请重新输入');
			$('#checkCreditCode').text('');
			return b;
		}
  		return true;
  	}
	
  </script>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="dealerInfoController.do?save" beforeSubmit="cp()">
			<input id="id" name="id" type="hidden" value="${dealerInfoPage.id }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							渠道商名称:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="account"  name="account" datatype="*" 
							   value="${dealerInfoPage.account}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							联系电话:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="phone" name="phone" datatype="m" errormsg="手机号非法" onchange="checkPhone(this.value)" 
							   value="${dealerInfoPage.phone}">
						<span id="checkPhone" class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							负责人:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="manager" name="manager" datatype="*"
							   value="${dealerInfoPage.manager}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							地址:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="position" name="position" datatype="*" 
							   value="${dealerInfoPage.position}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							折扣:
						</label>
					</td>
					<td class="value">
						<select class="select" id="dealerDiscount" name="dealerDiscount" datatype="*" >
							<option value="10">无折扣</option>
							<c:forEach begin="1" end="9" varStatus="s">
								<option value="${s.index}" <c:if test="${dealerInfoPage.dealerDiscount == s.index }">selected="selected"</c:if> >${s.index}折</option>
							</c:forEach>
						</select>
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							银行账户:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="bankAccount" name="bankAccount" datatype="*" 
							   value="${dealerInfoPage.bankAccount}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							公司社会信用代码:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="creditCode" name="creditCode" datatype="*" onchange="checkCreditCode(this.value)" 
							<c:if test="${dealerInfoPage.status == 0 }">disabled="disabled"</c:if> value="${dealerInfoPage.creditCode}">
						<span id="checkCreditCode" class="Validform_checktip"></span>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>