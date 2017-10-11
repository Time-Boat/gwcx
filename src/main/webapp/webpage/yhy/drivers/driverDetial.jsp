<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>司机详情</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  
 </head>
 <body >
 
 <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="">
 <input id="id" name="id" type="hidden" value="${driversInfoPage.id }">
			<table style="width: 700px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							司机姓名:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="name" name="name" value="${driversInfoPage.name}">
						<span class="Validform_checktip"></span>
					</td>
					<td align="right">
						<label class="Validform_label">
							年龄:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="age" name="age" value="${driversInfoPage.age}" >
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
						<input class="inputxt" id="phoneNumber" name="phoneNumber" value="${driversInfoPage.phoneNumber}" >
						<span id="dcheck_phone" class="Validform_checktip"></span>
					</td>
					<td align="right">
						<label class="Validform_label">
							身份证:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="idCard" name="idCard" value="${driversInfoPage.idCard}">
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
				
					<td align="right">
						<label class="Validform_label">
							驾照类型:
						</label>
					</td>
					<td class="value">
					    <t:dictSelect field="drivingLicense" typeGroupCode="drivingLic" hasLabel="false" defaultVal="${driversInfoPage.drivingLicense}" ></t:dictSelect>
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
						<t:dictSelect field="sex" typeGroupCode="sex" hasLabel="false" defaultVal="${driversInfoPage.sex}" ></t:dictSelect>
						<span class="Validform_checktip"></span>
					</td>
					<td align="right">
						<label class="Validform_label">
							司机状态:
						</label>
					</td>
					<td class="value">
						<t:dictSelect field="status" typeGroupCode="driver_status" hasLabel="false" defaultVal="${driversInfoPage.status}" ></t:dictSelect>
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							审核人:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="auditorUserName" name="auditorUserName" value="${auditor.userName}">
						<span class="Validform_checktip"></span>
					</td>
					<td align="right">
						<label class="Validform_label">
							审核时间:
						</label>
					</td>
					<td class="value">
					<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 150px" id="auditTime" name="auditTime" 
					value="<fmt:formatDate value='${driversInfoPage.auditTime}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>"> <span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							审核状态:
						</label>
					</td>
					<td class="value">
						<t:dictSelect field="applicationStatus" typeGroupCode="audit_status" hasLabel="false" defaultVal="${driversInfoPage.applicationStatus}" ></t:dictSelect>
						<span class="Validform_checktip"></span>
					</td>
					<td align="right">
						<label class="Validform_label">
							申请内容:
						</label>
					</td>
					<td class="value">
						<t:dictSelect field="applyContent" typeGroupCode="apply_type" hasLabel="false" defaultVal="${driversInfoPage.applyContent}" ></t:dictSelect>
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							申请人 :
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="applicationUserName" name="applicationUserName" value="${user.userName}">
						<span class="Validform_checktip"></span>
					</td>
					<td align="right">
						<label class="Validform_label">
							申请时间:
						</label>
					</td>
					<td class="value">
						<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 150px" id="applicationTime" name="applicationTime" 
							value="<fmt:formatDate value='${driversInfoPage.applicationTime}' type="date" pattern="yyyy-MM-dd HH:mm:ss"/>"> 
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							备注:
						</label>
					</td>
					<td class="value" colspan="3">
						<input class="inputxt" id="remark" name="remark" ignore="ignore"
							   value="${driversInfoPage.remark}">
						<span class="Validform_checktip"></span>
					</td>
					
				</tr>
				
			</table>
		</t:formvalid>
 </body>
 
