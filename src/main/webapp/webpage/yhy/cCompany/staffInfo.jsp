<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>公司员工入驻信息</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="staffInfoController.do?save">
			<input id="id" name="id" type="hidden" value="${staffInfoPage.id }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							员工名称:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="name" name="name" datatype="*"
							   value="${staffInfoPage.name}">
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
					    <t:dictSelect field="sex" typeGroupCode="sex" hasLabel="false" defaultVal="${staffInfoPage.sex}" datatype="*"></t:dictSelect>
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
						<input class="inputxt" id="age" name="age" datatype="n1-3" errormsg="年龄格式不正确!"
							   value="${staffInfoPage.age}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							手机号:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="phone" name="phone" datatype="m" errormsg="手机号格式不正确!"
							   value="${staffInfoPage.phone}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							所属公司:
						</label>
					</td>
					<td class="value">
						<select id="companyId" name="companyId" datatype="*">
							<c:forEach items="${companyList}" var="cp">
								<option value="${cp.orgCode }" <c:if test="${cp.orgCode==staffInfoPage.companyId}">selected="selected"</c:if>>${cp.departname}</option>
							</c:forEach>
						</select> <span class="Validform_checktip">请选择部门</span></td>
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				
				<%-- <tr>
					<td align="right"><label class="Validform_label"> 部门: </label></td>
					<td class="value"><select id="companyId" name="companyId" datatype="*">
						<c:forEach items="${departList}" var="depart">
							<option value="${depart.id }" <c:if test="${depart.id==jgDemo.depId}">selected="selected"</c:if>>${depart.departname}</option>
						</c:forEach>
					</select> <span class="Validform_checktip">请选择部门</span></td>
				</tr> --%>
				
				<tr>
					<td align="right">
						<label class="Validform_label">
							部门:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="depart" name="depart" ignore="ignore"
							   value="${staffInfoPage.depart}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							员工职位:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="staffPosition" name="staffPosition" ignore="ignore"
							   value="${staffInfoPage.staffPosition}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
<%-- 				<tr>
					<td align="right">
						<label class="Validform_label">
							状态:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="status" name="status" ignore="ignore"
							   value="${staffInfoPage.status}">
						<span class="Validform_checktip"></span>
					</td>
				</tr> --%>
				<tr>
					<td align="right">
						<label class="Validform_label">
							备注:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="remark" name="remark" ignore="ignore"
							   value="${staffInfoPage.remark}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>