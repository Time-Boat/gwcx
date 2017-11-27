<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>系统消息通知模板</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  
  <!-- 多选框样式 -->
  <style type="text/css">
  	.demo--label{display:inline-block;width:25%;}
	.demo--radio{display:none}
	.demo--radioInput{background-color:#fff;border:1px solid rgba(0,0,0,0.15);border-radius:100%;display:inline-block;height:16px;margin-right:10px;margin-top:-1px;vertical-align:middle;width:16px;line-height:1}
	.demo--radio:checked + .demo--radioInput:after{background-color:#57ad68;border-radius:100%;content:"";display:inline-block;height:12px;margin:2px;width:12px}
	.demo--checkbox.demo--radioInput,.demo--radio:checked + .demo--checkbox.demo--radioInput:after{border-radius:0}
  </style>
  
  <script type="text/javascript">
  		
	  $(function(){
		  var targetVal = $('#targetVal').val();
		  var list = jQuery.parseJSON('${roles}');
		  var html = '';
          $.each(list,function(key,value){
        	  html += '<label class="demo--label">';
        	  if(targetVal.indexOf(value[0]) != -1){
        		  html += '<input class="demo--radio" name="target" checked="checked" type="checkbox" value="' + value[0] + '" />';
        	  }else{
        		  html += '<input class="demo--radio" name="target" type="checkbox" value="' + value[0] + '" />';
        	  }
        	  html += '<span class="demo--checkbox demo--radioInput"></span>' + value[1] ;
        	  html += '</label>';
        	  if((key+1)%4 == 0){
        		  html += '</br>';
        	  }
	      });
		  $('#roles').append(html);
	  });
	  
  </script>
  
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="notificationModelController.do?save">
			<input id="id" name="id" type="hidden" value="${notificationModelPage.id }">
			<input class="inputxt" id="targetVal" name="targetVal" type="hidden" value="${notificationModelPage.target}">
			<table style="width: 100%;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							通知标题:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="title" name="title" ignore="ignore"
							   value="${notificationModelPage.title}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							通知内容:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="content" name="content" ignore="ignore"
							   value="${notificationModelPage.content}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							通知目标:
						</label>
					</td>
					<td class="value" id="roles">
						<%-- <input class="inputxt" id="target" name="target" ignore="ignore"
							   value="${notificationModelPage.target}">
						<span class="Validform_checktip"></span> --%>
						
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							通知方式:
						</label>
					</td>
					<%-- <td class="value">
						<input class="inputxt" id="nType" name="nType" ignore="ignore"
							   value="${notificationModelPage.nType}">
						<span class="Validform_checktip"></span>
					</td> --%>
					<td class="value">
						<label class="demo--label">
							<input class="demo--radio" name="nType" <c:if test="${fn:contains(nType,'1')}">checked="checked"</c:if> type="checkbox"  value="1" />
							<span class="demo--checkbox demo--radioInput"></span>邮件
						</label> 
						<label class="demo--label">
							<input class="demo--radio" name="nType" <c:if test="${fn:contains(nType,'2')}">checked="checked"</c:if> type="checkbox"  value="2" />
							<span class="demo--checkbox demo--radioInput"></span>短信
						</label> 
						<label class="demo--label">
							<input class="demo--radio" name="nType" <c:if test="${fn:contains(nType,'3')}">checked="checked"</c:if> type="checkbox"  value="3" />
							<span class="demo--checkbox demo--radioInput"></span>站内信
						</label> 
						<label class="demo--label">
							<input class="demo--radio" name="nType" <c:if test="${fn:contains(nType,'4')}">checked="checked"</c:if> type="checkbox"  value="4" />
							<span class="demo--checkbox demo--radioInput"></span>消息中心
						</label>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							是否启用:
						</label>
					</td>
					<td class="value">
					    <t:dictSelect id="status" field="status" typeGroupCode="notify_status" hasLabel="false" defaultVal="${notificationModelPage.status}" datatype="*" ></t:dictSelect>
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
							   value="${notificationModelPage.remark}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>