<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>渠道商信息</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  
	var b = true;
	//验证信用代码是否已经被占用
	function checkCreditCode(creditCode){
		
		$.ajax({
          type:"get",
          url:"dealerInfoController.do?checkCreditCode&creditCode="+creditCode,
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
	
	//提交前验证公司社会信用代码
  	function cp(){
  		return b;
  	}
  </script>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="dealerInfoController.do?save" beforeSubmit="cp()">
			<input id="id" name="id" type="hidden" value="${dealerInfoPage.id }">
			<!-- <a href="windows.open(https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQF97jwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyanBkSWwwWm1jemsxbFZlbDFwMW8AAgRZMlVZAwQgHAAA)" 
				download >下载</a> -->
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							渠道商账号:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="account"  name="account" <c:if test="${dealerInfoPage.status == 0 }">disabled="disabled"</c:if>
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
						<input class="inputxt" id="phone" name="phone" datatype="m" errormsg="手机号非法"
							   value="${dealerInfoPage.phone}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							负责人:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="manager" name="manager" 
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
						<input class="inputxt" id="position" name="position" <c:if test="${dealerInfoPage.status == 0 }">disabled="disabled"</c:if>
							   value="${dealerInfoPage.position}">
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
						<input class="inputxt" id="bankAccount" name="bankAccount" <c:if test="${dealerInfoPage.status == 0 }">disabled="disabled"</c:if>
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
						<input class="inputxt" id="creditCode" name="creditCode" onchange="checkCreditCode(this.value);" <c:if test="${dealerInfoPage.status == 0 }">disabled="disabled"</c:if>
							   value="${dealerInfoPage.creditCode}">
						<span id="checkCreditCode" class="Validform_checktip"></span>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>