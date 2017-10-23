<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>包车服务</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="charteredAppendServiceController.do?save">
			<input id="id" name="id" type="hidden" value="${charteredAppendServicePage.id }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							服务名称:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="serviceName" name="serviceName" 
							   value="${charteredAppendServicePage.serviceName}" datatype="s1-6">
						<span class="Validform_checktip"><t:mutiLang langKey="名称输入1到6个字"/></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							服务描述:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="serviceDescription" name="serviceDescription" ignore="ignore"
							   value="${charteredAppendServicePage.serviceDescription}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				
				<tr>
					<td align="right">
						<label class="Validform_label">
							状态:
						</label>
					</td>
					
					<td class="value">
					<t:dictSelect id="status" field="status"  typeGroupCode="driver_status" hasLabel="false" defaultVal="${charteredAppendServicePage.status}" datatype="*" ></t:dictSelect>	
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
							   value="${charteredAppendServicePage.remark}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>