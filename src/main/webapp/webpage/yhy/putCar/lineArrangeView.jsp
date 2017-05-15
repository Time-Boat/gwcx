<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>线路排班模块</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="lineArrangeViewController.do?save">
			<input id="id" name="id" type="hidden" value="${lineArrangeViewPage.id }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<%-- <tr>
					<td align="right">
						<label class="Validform_label">
							发车开始日期:
						</label>
					</td>
					<td class="value">
						<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  style="width: 150px" id="startDate" name="startDate" ignore="ignore"
							     value="<fmt:formatDate value='${lineArrangeViewPage.startDate}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							发车停止日期:
						</label>
					</td>
					<td class="value">
						<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  style="width: 150px" id="endDate" name="endDate" ignore="ignore"
							     value="<fmt:formatDate value='${lineArrangeViewPage.endDate}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							司机id:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="driverId" name="driverId" ignore="ignore"
							   value="${lineArrangeViewPage.driverId}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							车牌id:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="licencePlateId" name="licencePlateId" ignore="ignore"
							   value="${lineArrangeViewPage.licencePlateId}">
						<span class="Validform_checktip"></span>
					</td>
				</tr> --%>
				<tr>
					<td align="right">
						<label class="Validform_label">
							放票开始日期:
						</label>
					</td>
					<td class="value">
						<%-- <input class="inputxt" id="periodStart" name="periodStart" ignore="ignore"
							   value="${lineArrangeViewPage.periodStart}"> --%>
						<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width: 150px" id="periodStart" name="periodStart" ignore="ignore"
							     value="<fmt:formatDate value='${lineArrangeViewPage.periodStart}' type="date" pattern="yyyy-MM-dd"/>">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							放票结束日期:
						</label>
					</td>
					<td class="value">
						<%-- <input class="inputxt" id="periodEnd" name="periodEnd" ignore="ignore"
							   value="${lineArrangeViewPage.periodEnd}"> --%>
						<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width: 150px" id="periodEnd" name="periodEnd" ignore="ignore"
							     value="<fmt:formatDate value='${lineArrangeViewPage.periodEnd}' type="date" pattern="yyyy-MM-dd"/>">
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
							   value="${lineArrangeViewPage.remark}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							车牌号:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="licencePlate" name="licencePlate" ignore="ignore"
							   value="${lineArrangeViewPage.licencePlate}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							座位数:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="seat" name="seat" ignore="ignore"
							   value="${lineArrangeViewPage.seat}">
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
							   value="${lineArrangeViewPage.carType}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<%-- <tr>
					<td align="right">
						<label class="Validform_label">
							司机姓名:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="driverName" name="driverName" ignore="ignore"
							   value="${lineArrangeViewPage.driverName}">
						<span class="Validform_checktip"></span>
					</td>
				</tr> --%>
			</table>
		</t:formvalid>
 </body>