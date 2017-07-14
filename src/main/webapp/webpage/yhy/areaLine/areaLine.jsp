<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>包车区域线路</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="areaLineController.do?save">
			<input id="id" name="id" type="hidden" value="${areaLinePage.id }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							线路名称:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="name" name="name" ignore="ignore"
							   value="${areaLinePage.name}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							机场或火车站站点:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="stationId" name="stationId" ignore="ignore"
							   value="${areaLinePage.stationId}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							线路类型 :
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="lineType" name="lineType" ignore="ignore"
							   value="${areaLinePage.lineType}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							线路状态:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="status" name="status" ignore="ignore"
							   value="${areaLinePage.status}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							城市id:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="cityId" name="cityId" ignore="ignore"
							   value="${areaLinePage.cityId}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							城市名称:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="cityName" name="cityName" ignore="ignore"
							   value="${areaLinePage.cityName}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							出车时间段:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="dispath" name="dispath" ignore="ignore"
							   value="${areaLinePage.dispath}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							车辆类型:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="carType" name="carType" ignore="ignore"
							   value="${areaLinePage.carType}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							所属区域（高德）:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="district" name="district" ignore="ignore"
							   value="${areaLinePage.district}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							区域id （高德）:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="districtId" name="districtId" ignore="ignore"
							   value="${areaLinePage.districtId}">
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
							   value="${areaLinePage.remark}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>