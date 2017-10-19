<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>包车套餐设置</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="charteredPackageController.do?save">
			<input id="id" name="id" type="hidden" value="${charteredPackagePage.id }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							套餐名称:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="name" name="name" datatype="*" value="${charteredPackagePage.name}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							套餐内容:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="description" name="description" value="${charteredPackagePage.description}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							公里数:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="kilometre" name="kilometre" value="${charteredPackagePage.kilometre}" datatype="n1-4">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							时长(分钟):
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="timeLength" name="timeLength" value="${charteredPackagePage.timeLength}" datatype="n1-5">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">所属城市:</label>
					</td>
					<td class="value">
						<select id="cityId" name="cityId" datatype="*" >
							<option value="">--请选择城市--</option>
							<c:forEach var="c" items="${cities}">
								<option value="${c.cityId}" <c:if test="${charteredPackagePage.cityId == c.cityId}" >selected="selected"</c:if> >
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
							是否上架:
						</label>
					</td>
					<td class="value">
						<t:dictSelect field="status" typeGroupCode="oprType" hasLabel="false" defaultVal="${charteredPackagePage.status}" datatype="*"></t:dictSelect>	
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
						<input class="inputxt" id="remark" name="remark" ignore="ignore" value="${charteredPackagePage.remark}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>