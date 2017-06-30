<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>渠道商信息</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="dealerInfoController.do?save">
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
						<input class="inputxt" id="account" name="account" ignore="ignore"
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
						<input class="inputxt" id="manager" name="manager" ignore="ignore"
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
						<input class="inputxt" id="position" name="position" ignore="ignore"
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
						<input class="inputxt" id="bankAccount" name="bankAccount" ignore="ignore"
							   value="${dealerInfoPage.bankAccount}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>