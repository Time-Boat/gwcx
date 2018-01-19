<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="plug-in/tools/popup/userSelect.js"></script>
<script src="plug-in/tools/popup/departSelect.js"></script>
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="transferComplaintOrderList" title="订单申诉列表"
			autoLoadData="true"
			actionUrl="transferOrderController.do?complaintdatagrid"
			fitColumns="true" idField="id" fit="true" queryMode="group"
			checkbox="true">
			<t:dgCol title="id" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="订单编号" field="orderId"></t:dgCol>
			<t:dgCol title="申诉时间" field="complaintTime" editor="datebox"
				formatter="yyyy-MM-dd hh:mm" query="true" queryMode="group"
				align="center"></t:dgCol>
			<t:dgCol title="线路名称" field="lineName" align="center"></t:dgCol>
			<t:dgCol title="用户类型" field="orderUserType" dictionary="userType"
				query="true" align="center"></t:dgCol>
			<t:dgCol title="申述人" field="orderContactsname" query="true"
				align="center"></t:dgCol>
			<t:dgCol title="申述人手机号" field="orderContactsmobile" query="true"
				align="center"></t:dgCol>
			<t:dgCol title="申诉原因" field="complaintReason"
				dictionary="complaint_reason" align="center"></t:dgCol>
			<t:dgCol title="订单金额" field="orderTotalPrice" align="center"></t:dgCol>

			<t:dgCol title="处理意见" field="handleResult" dictionary="handle_result"
				align="center"></t:dgCol>
			<t:dgCol title="订单状态" field="orderStatus" replace="订单已完成_0,取消订单待退款_3,取消订单完成退款_4,申诉中_8" 
				dictionary="orderStatus" query="true" align="center"></t:dgCol>

			<t:dgCol title="操作" field="opt" width="150" align="center"></t:dgCol>
			<t:dgFunOpt operationCode="details" title="查看详情" funname="detail(id)"></t:dgFunOpt>
			<t:dgFunOpt operationCode="handles" title="处理"
				funname="handle(id,orderStatus)"></t:dgFunOpt>
		</t:datagrid>
	</div>
	</div>
	<script type="text/javascript">
		//进入触发 
		$(function() {

			$('#transferComplaintOrderList').datagrid({
				rowStyler : function(index, row) {
					if (row.orderStatus == "8") {
						return 'color:red';
					}

				}
			});
		});

		$(document).ready(
				function() {
					$("input[name='complaintTime_begin']").attr("class",
							"Wdate").click(function() {
						WdatePicker({
							dateFmt : 'yyyy-MM-dd HH:mm'
						});
					});
					$("input[name='complaintTime_end']").attr("class", "Wdate")
							.click(function() {
								WdatePicker({
									dateFmt : 'yyyy-MM-dd HH:mm'
								});
							});

				});

		function detail(id, title) {
			var url = "transferOrderController.do?getComplaintDetail&load=detail&id="
					+ id;
			createdetailwindow("申诉订单详情", url, 700, 670);
		}
		function handle(id, orderStatus) {
			var url = "transferOrderController.do?getComplaintOrder&id=" + id;
			var permission =<%=request.getAttribute("permission")%>;
			if (orderStatus != '8' || permission != '1') {
				url += "&load=detail";
				createdetailwindow("处理异常订单", url, 500, 250);
			}else{
				createwindow("处理异常订单", url, 500, 250);
			}
			
		}
	</script>