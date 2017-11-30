<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>包车订单</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="charteredOrderController.do?save">
			<input id="id" name="id" type="hidden" value="${charteredOrderPage.id }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							订单id:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="orderId" name="orderId" ignore="ignore"
							   value="${charteredOrderPage.orderId}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							订单状态 0：订单已完成。1：待派车。2：待出行。3：取消订单待退款。4：取消订单完成退款。5：拒绝退款。6：未支付。7：已审核。:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="orderStatus" name="orderStatus" ignore="ignore"
							   value="${charteredOrderPage.orderStatus}" datatype="n">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							起点站:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="startingStationName" name="startingStationName" ignore="ignore"
							   value="${charteredOrderPage.startingStationName}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							终点站:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="terminusStationName" name="terminusStationName" ignore="ignore"
							   value="${charteredOrderPage.terminusStationName}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							出发时间:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="orderStartime" name="orderStartime" ignore="ignore"
							   value="${charteredOrderPage.orderStartime}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							包车定价id:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="charteredPriceId" name="charteredPriceId" ignore="ignore"
							   value="${charteredOrderPage.charteredPriceId}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							支付方式：微信 1：支付宝 2：银联:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="orderPaytype" name="orderPaytype" ignore="ignore"
							   value="${charteredOrderPage.orderPaytype}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							联系人:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="orderContactsname" name="orderContactsname" ignore="ignore"
							   value="${charteredOrderPage.orderContactsname}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							联系人手机号:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="orderContactsmobile" name="orderContactsmobile" ignore="ignore"
							   value="${charteredOrderPage.orderContactsmobile}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							起点站 X:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="startingStationX" name="startingStationX" ignore="ignore"
							   value="${charteredOrderPage.startingStationX}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							终点站  Y:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="terminusStationY" name="terminusStationY" ignore="ignore"
							   value="${charteredOrderPage.terminusStationY}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							支付状态 0：已付款，1：退款中 2：已退款 3：未付款 4：已拒绝:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="orderPaystatus" name="orderPaystatus" ignore="ignore"
							   value="${charteredOrderPage.orderPaystatus}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							总价:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="orderTotalprice" name="orderTotalprice" ignore="ignore"
							   value="${charteredOrderPage.orderTotalprice}" datatype="d">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							订单的实际里程:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="orderActualMileage" name="orderActualMileage" ignore="ignore"
							   value="${charteredOrderPage.orderActualMileage}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							包车类型  0：单程   1：往返:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="orderType" name="orderType" ignore="ignore"
							   value="${charteredOrderPage.orderType}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							用户订单类型    0：普通用户订单    1：渠道商用户订单:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="orderUserType" name="orderUserType" ignore="ignore"
							   value="${charteredOrderPage.orderUserType}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							订单备注:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="remark" name="remark" ignore="ignore"
							   value="${charteredOrderPage.remark}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							下达订单时间:
						</label>
					</td>
					<td class="value">
						<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  style="width: 150px" id="applicationtime" name="applicationtime" ignore="ignore"
							     value="<fmt:formatDate value='${charteredOrderPage.applicationtime}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							客户id:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="userId" name="userId" ignore="ignore"
							   value="${charteredOrderPage.userId}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							申请退款时间:
						</label>
					</td>
					<td class="value">
						<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  style="width: 150px" id="refundTime" name="refundTime" ignore="ignore"
							     value="<fmt:formatDate value='${charteredOrderPage.refundTime}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							拒绝退款原因:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="rejectReason" name="rejectReason" ignore="ignore"
							   value="${charteredOrderPage.rejectReason}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							微信商户单号:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="orderPayNumber" name="orderPayNumber" ignore="ignore"
							   value="${charteredOrderPage.orderPayNumber}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							退款金额:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="refundPrice" name="refundPrice" ignore="ignore"
							   value="${charteredOrderPage.refundPrice}" datatype="d">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							退款时间:
						</label>
					</td>
					<td class="value">
						<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  style="width: 150px" id="refundCompletedTime" name="refundCompletedTime" ignore="ignore"
							     value="<fmt:formatDate value='${charteredOrderPage.refundCompletedTime}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							订单完成时间:
						</label>
					</td>
					<td class="value">
						<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  style="width: 150px" id="orderCompletedTime" name="orderCompletedTime" ignore="ignore"
							     value="<fmt:formatDate value='${charteredOrderPage.orderCompletedTime}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							是否已经被删除   0：未删除    1：删除:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="deleteFlag" name="deleteFlag" ignore="ignore"
							   value="${charteredOrderPage.deleteFlag}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							线路订单码:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="lineordercode" name="lineordercode" ignore="ignore"
							   value="${charteredOrderPage.lineordercode}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							初审审核状态   0：初审待审核     1：初审通过    2：初审未通过:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="firstAuditStatus" name="firstAuditStatus" ignore="ignore"
							   value="${charteredOrderPage.firstAuditStatus}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							复审审核状态   0：复审待审核     1：复审通过    2：复审未通过:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="lastAuditStatus" name="lastAuditStatus" ignore="ignore"
							   value="${charteredOrderPage.lastAuditStatus}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							初审人id:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="firstAuditUser" name="firstAuditUser" ignore="ignore"
							   value="${charteredOrderPage.firstAuditUser}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							复审人id:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="lastAuditUser" name="lastAuditUser" ignore="ignore"
							   value="${charteredOrderPage.lastAuditUser}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							初审时间:
						</label>
					</td>
					<td class="value">
						<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  style="width: 150px" id="firstAuditDate" name="firstAuditDate" ignore="ignore"
							     value="<fmt:formatDate value='${charteredOrderPage.firstAuditDate}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							复审时间:
						</label>
					</td>
					<td class="value">
						<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"  style="width: 150px" id="lastAuditDate" name="lastAuditDate" ignore="ignore"
							     value="<fmt:formatDate value='${charteredOrderPage.lastAuditDate}' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							历史订单（就是该订单绑定的运营专员已经被注销了）    0：否    1：是:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="orderHistory" name="orderHistory" ignore="ignore"
							   value="${charteredOrderPage.orderHistory}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>