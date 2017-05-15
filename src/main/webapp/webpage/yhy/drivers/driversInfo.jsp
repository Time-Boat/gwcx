<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>司机信息表</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  
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
  	
  </script>
 </head>
 <body >
 
  <t:formvalid formid="driversInfo" tabtitle="aaaa" dialog="true" usePlugin="password" layout="table" callback="@Override uploadFile" action="driversInfoController.do?save" >
			<input id="id" name="id" type="hidden" value="${driversInfoPage.id }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							司机姓名:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="name" name="name" ignore="ignore"
							   value="${driversInfoPage.name}">
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
							   value="${driversInfoPage.phoneNumber}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							身份证:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="idCard" name="idCard" datatype="/^[1-9][0-9]{5}(19[0-9]{2}|200[0-9]|2010)(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])[0-9]{3}[0-9xX]$/" errormsg="身份证号非法"
							   value="${driversInfoPage.idCard}">
						<span class="Validform_checktip"></span>
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
				<tr>
					<td align="right">
						<label class="Validform_label">
							图片:
						</label>
					</td>
					<td class="value">
						<div class="form" id="filediv"></div>
						<div class="form"><t:upload name="file_upload" uploader="driversInfoController.do?saveOrUpdate" extend="*.jpg;*.jpeg;*.png;*.jpg;" 
							id="file_upload" formId="driversInfo"></t:upload></div>
						<%-- <input type="image" id="drivingLicenseImgUrl" name="drivingLicenseImgUrl"  src="${driversInfoPage.drivingLicenseImgUrl}"> --%>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>
 
