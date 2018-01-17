<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="plug-in/tools/popup/userSelect.js"></script>
<script src="plug-in/tools/popup/departSelect.js"></script>
<link href="plug-in/tools/css/rejectReason.css" type="text/css"
	rel="stylesheet" />
<div class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="orderRefundList" title="接送机退款管理" autoLoadData="true"
			actionUrl="orderRefundController.do?datagrid" fitColumns="true"
			idField="id" fit="true" queryMode="group" checkbox="true">
			<t:dgCol title="id" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="用户类型" field="orderUserType" dictionary="userType"
				query="true" defaultVal="1" align="center"></t:dgCol>
			<t:dgCol title="订单类型" field="orderType" dictionary="transferTy"
				query="true" align="center"></t:dgCol>
			<t:dgCol title="订单编号" field="orderId"></t:dgCol>
			<c:if test="true">
				<t:dgCol title="所属公司" field="departname" align="center"></t:dgCol>
			</c:if>
			<t:dgCol title="起点站" field="orderStartingstation" align="center"></t:dgCol>
			<t:dgCol title="终点站" field="orderTerminusstation" align="center"></t:dgCol>
			<%--<t:dgCol title="下单时间" field="applicationTime" formatter="yyyy-MM-dd hh:mm:ss" align="center"></t:dgCol> --%>
			<t:dgCol title="出发时间" field="orderStartime" editor="datebox"
				formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group"
				align="center"></t:dgCol>
			<t:dgCol title="总价(元)" field="orderTotalPrice" align="center"></t:dgCol>
			<t:dgCol title="车票数量" field="orderNumbers" align="center"></t:dgCol>
			<%-- <t:dgCol title="支付方式" field="orderPaytype" replace="微信_0,支付宝_1,银联_2" align="center"></t:dgCol> --%>
			<t:dgCol title="联系人" field="orderContactsname" align="center"></t:dgCol>
			<t:dgCol title="联系人手机号" field="orderContactsmobile" align="center"></t:dgCol>
			<%--<t:dgCol title="支付状态" field="orderPaystatus" replace="已付款_0,退款中_1,已退款_2,已拒绝_4" align="center"></t:dgCol> --%>
			<t:dgCol title="申请退款时间" field="refundTime"
				formatter="yyyy-MM-dd hh:mm:ss" align="center" query="true"
				queryMode="group"></t:dgCol>
			<t:dgCol title="退款金额" field="refundPrice" align="center"></t:dgCol>
			<t:dgCol title="所属城市" field="cityName" align="center"></t:dgCol>
			<t:dgCol title="初审时间" field="firstAuditDate" editor="datebox"
				formatter="yyyy-MM-dd hh:mm:ss" align="center"></t:dgCol>
			<t:dgCol title="初审人" field="firstAuditUser" align="center"></t:dgCol>
			<t:dgCol title="初审状态" field="firstAuditStatus"
				dictionary="audit_status" align="center"></t:dgCol>
			<t:dgCol title="复审时间" field="lastAuditDate" editor="datebox"
				formatter="yyyy-MM-dd hh:mm:ss" align="center"></t:dgCol>
			<t:dgCol title="复审人" field="lastAuditUser" align="center"></t:dgCol>
			<t:dgCol title="复审状态" field="lastAuditStatus"
				dictionary="audit_status" align="center"></t:dgCol>
			<%-- <t:dgCol title="司机姓名" field="name" align="center"></t:dgCol>
		<t:dgCol title="司机联系电话" field="phoneNumber" align="center"></t:dgCol> --%>
			<t:dgCol title="订单状态" field="orderStatus"
				replace="取消订单待退款_3,取消订单完成退款_4" dictionary="orderStatus" query="true"
				align="center"></t:dgCol>
			<%-- <t:dgCol title="操作" field="opt" align="center"></t:dgCol>
		<t:dgFunOpt exp="orderStatus#eq#3" funname="agreeRefund(id,orderTotalPrice)" title="同意" ></t:dgFunOpt>
		<t:dgFunOpt exp="orderStatus#eq#3" funname="rejectRefund(id)" title="拒绝" ></t:dgFunOpt>
		<t:dgFunOpt exp="orderStatus#eq#5" funname="lookRejectReason(id)" title="查看原因" ></t:dgFunOpt> --%>
			<t:dgToolBar operationCode="detail" title="查看详情" icon="icon-search"
				url="transferOrderController.do?addorupdate" funname="detail"></t:dgToolBar>
			<t:dgToolBar title="批量同意" icon="icon-edit"
				url="orderRefundController.do?doAgreeALLSelect"
				funname="AgreeALLSelect" operationCode="agreeRefund"></t:dgToolBar>
			<%-- <t:dgToolBar title="批量拒绝" icon="icon-edit" url="" funname="RefuseALLSelect"></t:dgToolBar> --%>
		</t:datagrid>
	</div>

	<div id="win" class="easyui-window" title="退款"
		style="width: 400px; height: 150px" data-options="modal:true"
		closed="true">

		<!-- <div id="win" class="easyui-window" title="确认退款" style="width:400px;height:150px;" data-options="modal:true" closed="true" ></div> -->

	</div>
	<input type="hidden" value="${linelist}" id="linelies" /> <input
		type="hidden" value="${companylist}" id="companies" /> <input
		type="hidden" value="${startlist}" id="busStartinfoLies" /> <input
		type="hidden" value="${endlist}" id="busEndinfoLies" />

	<div id="showStation" hidden="true">
		<span
			style="display: -moz-inline-box; display: inline-block; padding: 10px 2px;"><span
			style="vertical-align: middle; display: -moz-inline-box; display: inline-block; width: 80px; text-align: right; text-overflow: ellipsis; -o-text-overflow: ellipsis; overflow: hidden; white-space: nowrap;"
			title="选择起点站">选择起点站：</span> <select id="startLocation"
			name="startLocation" style="width: 150px">
				<option value="">选择起点站</option>
		</select> </span> <span
			style="display: -moz-inline-box; display: inline-block; padding: 10px 2px;"><span
			style="vertical-align: middle; display: -moz-inline-box; display: inline-block; width: 80px; text-align: right; text-overflow: ellipsis; -o-text-overflow: ellipsis; overflow: hidden; white-space: nowrap;"
			title="选择终点站">选择终点站：</span> <select id="orderTerminusstation"
			name="orderTerminusstation" style="width: 150px">
				<option value="">选择终点站</option>
		</select> </span>
	</div>
	<script type="text/javascript">
		//进入触发 
		$(function() {
			$('#orderRefundList').datagrid({
				rowStyler : function(index, row) {
					if (row.orderStatus == "3") {
						return 'color:#901622';
					}
				}
			});
		});

		$(function() {
			//添加城市条件
			var json1 = $("#linelies").val();
			var json2 = $("#companies").val();

			//添加线路条件
			var a11 = '<span style="display:-moz-inline-box;display:inline-block; padding:10px 2px;"><span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;';
			var a21 = 'text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; "title="选择线路">选择线路：</span>';
			var a31 = '<select id ="lineId" name="lineId" style="width: 150px">';
			var c11 = '<option value="">选择线路</option>';

			if (json1.indexOf("lineId") > 0) {
				var obj = eval('(' + json1 + ')');
				for (var i = 0; i < obj.lineinfo.length; i++) {
					c11 += '<option value="'+obj.lineinfo[i].lineId+'">'
							+ obj.lineinfo[i].lineName + '</option>';
				}
			}
			var a41 = '</select></span>';

			var a111 = '<span style="display:-moz-inline-box;display:inline-block; padding:10px 2px;"><span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;';
			var a211 = 'text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; "title="选择公司">选择公司：</span>';
			var a311 = '<select id ="departname" name="departname" style="width: 150px">';
			var c111 = '<option value="">选择公司</option>';

			if (json2.indexOf("companyId") > 0) {
				var obj1 = eval('(' + json2 + ')');
				for (var i = 0; i < obj1.data.length; i++) {
					c111 += '<option value="'+obj1.data[i].departname+'">'
							+ obj1.data[i].departname + '</option>';
				}
			}
			var a411 = '</select></span>';

			$("#orderRefundListForm").append(
					a11 + a21 + a31 + c11 + a41 + a111 + a211 + a311 + c111
							+ a411);
			getLineName();
			getUserLineName();
			getStartAndEndStartion();
			getStartLocation();
			getEndLocation();
		});

		//根据线路类型获取线路和起点站、终点站
		function getLineName() {

			$("select[name='orderType']")
					.change(
							function() {
								var userType = $("select[name='orderUserType']").val();
								var ordertype = $(this).children('option:selected').val(); //当前选择项的值
								var url = "lineInfoSpecializedController.do?getLineName&ordertype="
										+ ordertype + "&userType=" + userType;
								$.ajax({
											type : 'POST',
											url : url,
											success : function(ds) {
												
												var d1 = '<option value="">选择线路</option>';
												var obj = eval('(' + ds + ')');
												var objLine = obj.lineinfo.lineinfo;
												if (objLine.length > 0) {
													
													for (var i = 0; i < objLine.length; i++) {
														d1 += '<option value="'+objLine[i].lineId+'">'+objLine[i].lineName+ '</option>';
													}
												}
												$("#lineId").empty();//先置空 
												$("#lineId").append(d1);
												
												var d2 = '<option value="">选择起点站</option>';
												var startstation = obj.startStation.data;
												if (startstation.length > 0) {
													for (var i = 0; i < startstation.length; i++) {
														d2 += '<option value="'+startstation[i].busid+'">'+ startstation[i].busName+ '</option>';
													}
												}
												$("#startLocation").empty();//先置空 
												$("#startLocation").append(d2);
												
												var d3 = '<option value="">选择终点站</option>';
												var endStation = obj.endStation.data;
												if (endStation.length > 0) {
													for (var i = 0; i < endStation.length; i++) {
														d3 += '<option value="'+endStation[i].busid+'">'+ endStation[i].busName+ '</option>';
													}
												}
												$("#orderTerminusstation").empty();//先置空 
												$("#orderTerminusstation").append(d3);
												
											}
										});
							});
		}

		//根据用户类型获取线路和起点站、终点站
		function getUserLineName() {

			$("select[name='orderUserType']")
					.change(
							function() {
								var ordertype = $("select[name='orderType']")
										.val();
								var isDealerLine = $(this).children(
										'option:selected').val(); //当前选择项的值
								var url = "lineInfoSpecializedController.do?getLineName&userType="
										+ isDealerLine
										+ "&ordertype="
										+ ordertype;
								$.ajax({
											type : 'POST',
											url : url,
											success : function(ds) {
												var d1 = '<option value="">选择线路</option>';
												var obj = eval('(' + ds + ')');
												var objLine = obj.lineinfo.lineinfo;
												if (objLine.length > 0) {
													
													for (var i = 0; i < objLine.length; i++) {
														d1 += '<option value="'+objLine[i].lineId+'">'+objLine[i].lineName+ '</option>';
													}
												}
												$("#lineId").empty();//先置空 
												$("#lineId").append(d1);
												
												var d2 = '<option value="">选择起点站</option>';
												var startstation = obj.startStation.data;
												if (startstation.length > 0) {
													for (var i = 0; i < startstation.length; i++) {
														d2 += '<option value="'+startstation[i].busid+'">'+ startstation[i].busName+ '</option>';
													}
												}
												$("#startLocation").empty();//先置空 
												$("#startLocation").append(d2);
												
												var d3 = '<option value="">选择终点站</option>';
												var endStation = obj.endStation.data;
												if (endStation.length > 0) {
													for (var i = 0; i < endStation.length; i++) {
														d3 += '<option value="'+endStation[i].busid+'">'+ endStation[i].busName+ '</option>';
													}
												}
												$("#orderTerminusstation").empty();//先置空 
												$("#orderTerminusstation").append(d3);
												
												
											}
										});
							});
		}

		//根据线路获取起点站
		function getStartLocation() {

			$("select[name='lineId']")
					.change(
							function() {
								var lineType = $("select[name='orderType']")
										.val();
								var userType = $("select[name='orderUserType']")
										.val();
								var lineId = $(this)
										.children('option:selected').val(); //当前选择项的值
								var url = "lineInfoSpecializedController.do?getTypeStartLocation&lineId="
										+ lineId
										+ "&lineType="
										+ lineType
										+ "&userType=" + userType;
								$.ajax({
											type : 'POST',
											url : url,
											success : function(ds) {
												var d1 = '<option value="">选择起点站</option>';
												var obj = eval('(' + ds + ')');
												if (obj.indexOf("busid") > 0) {
													var objs = eval('(' + obj
															+ ')');
													for (var i = 0; i < objs.data.length; i++) {
														d1 += '<option value="'+objs.data[i].busid+'">'
																+ objs.data[i].busName
																+ '</option>';
													}
												}
												$("#startLocation").empty();//先置空 
												$("#startLocation").append(d1);
											}
										});
							});
		}

		//根据线路获取终点站 
		function getEndLocation() {

			$("select[name='lineId']")
					.change(
							function() {

								var lineType = $("select[name='orderType']")
										.val();
								var userType = $("select[name='orderUserType']")
										.val();
								var lineId = $(this)
										.children('option:selected').val(); //当前选择项的值
								var url = "lineInfoSpecializedController.do?getTypeEndLocation&lineId="
										+ lineId
										+ "&lineType="
										+ lineType
										+ "&userType=" + userType;
								$.ajax({
											type : 'POST',
											url : url,
											success : function(ds) {
												var d1 = '<option value="">选择终点站</option>';
												var obj = eval('(' + ds + ')');
												if (obj.indexOf("busid") > 0) {
													var objs = eval('(' + obj
															+ ')');
													for (var i = 0; i < objs.data.length; i++) {
														d1 += '<option value="'+objs.data[i].busid+'">'
																+ objs.data[i].busName
																+ '</option>';
													}
												}
												$("#orderTerminusstation").empty();//先置空 
												$("#orderTerminusstation").append(d1);
											}
										});
							});
		}

		//添加起点和终点查询 
		function getStartAndEndStartion() {

			$("select[name='orderUserType']")
					.change(
							function() {
								var orderUserType = $("select[name='orderUserType']")
										.val();
								if (orderUserType == '0') {
									var json1 = $("#busStartinfoLies").val();
									var json2 = $("#busEndinfoLies").val();

									if (json1.indexOf("busid") > 0) {
										var obj = eval('(' + json1 + ')');
										var b1 = '<option value="">选择起点站</option>';
										for (var i = 0; i < obj.data.length; i++) {
											b1 += '<option value="'+obj.data[i].busid+'">'
													+ obj.data[i].busName
													+ '</option>';
										}
									}
									$("#startLocation").empty();//先置空 
									$("#startLocation").append(b1);
									if (json2.indexOf("busid") > 0) {
										var obj = eval('(' + json2 + ')');
										var e1 = '<option value="">选择终点站</option>';
										for (var i = 0; i < obj.data.length; i++) {
											e1 += '<option value="'+obj.data[i].busid+'">'
													+ obj.data[i].busName
													+ '</option>';
										}
									}
									$("#orderTerminusstation").empty();//先置空 
									$("#orderTerminusstation").append(e1);
									$("#orderRefundListForm").append(
											$("#showStation"));
									$("#showStation").show();
								} else {
									$("#showStation").hide();
								}
							});
		}

		$(document).ready(
				function() {
					$("input[name='orderStartime_begin']").attr("class",
							"Wdate").click(function() {
						WdatePicker({
							dateFmt : 'yyyy-MM-dd HH:mm:ss'
						});
					});
					$("input[name='orderStartime_end']").attr("class", "Wdate")
							.click(function() {
								WdatePicker({
									dateFmt : 'yyyy-MM-dd HH:mm:ss'
								});
							});
					$("input[name='refundTime_begin']").attr("class", "Wdate")
							.click(function() {
								WdatePicker({
									dateFmt : 'yyyy-MM-dd HH:mm:ss'
								});
							});
					$("input[name='refundTime_end']").attr("class", "Wdate")
							.click(function() {
								WdatePicker({
									dateFmt : 'yyyy-MM-dd HH:mm:ss'
								});
							});
				});

		function agreeWindow() {
			var rwin = '';
			rwin += '<div class="easyui-layout" data-options="fit:true">';
			rwin += '<div style="text-align: center; position:relative;" data-options="region:\'center\'" >';
			rwin += '<h5 id="reTitle" >确定要同意退款吗？</h5>';
			rwin += '<input id="sub" type="button" class="button white" value="确定" style="margin-right: 50px;width:50px;height:30px" onclick="agreeRefund()" />';
			rwin += '<input id="cal" type="button" class="button white" value="取消" style="width:50px;height:30px" onclick="javascript:$(\'#win\').window(\'close\');"/>';
			rwin += '<div id="refund_loading" style="display:none;position:absolute;cursor1:wait;left:50%;top:20px;transform: translateX(-50%);width:200px;height:32px;color:#000;">';
			rwin += '</div></div></div>';

			return rwin;
		}

		function agreeRefund(id, orderTotalPrice) {

			refundBegin();

			refundUrl = "orderRefundController.do?doAgreeALLSelect";
			refundGname = "orderRefundList";

			var obj = new Object();
			obj.id = id;
			obj.orderTotalPrice = orderTotalPrice;
			rows = [];

			rows.push(obj);

			$('#win').window('open');

		}

		var refundUrl;
		var refundGname;
		var rows;

		//批量退款
		function AgreeALLSelect(title, url, gname) {

			rows = $("#" + gname).datagrid('getSelections');
			console.log(rows);
			if (rows.length <= 0) {
				tip("请选择需要批量同意退款的数据！");
				return;
			}

			for (var i = 0; i < rows.length; i++) {
				if (rows[i].orderStatus == 4) {
					tip("已退款的订单不能再进行退款操作！");
					return;
				}
			}

			refundBegin();

			refundUrl = url;
			refundGname = gname;

			$('#win').empty().append(agreeWindow());
			$('#win').height("150px")
			$('#win').window('open');

		}

		//同意退款
		function agreeRefund() {
			console.log(rows);
			$('#refund_loading').show();
			$('#sub').hide();
			$('#cal').hide();
			$('#reTitle').hide();
			$('#refund_loading').html("正在退款，请等待...");
			$('#refund_loading')
					.css(
							'background',
							'#fff url(plug-in/ace/assets/css/images/loading.gif) no-repeat scroll 11px -4px');

			var ids = [];
			var fees = [];

			for (var i = 0; i < rows.length; i++) {
				ids.push(rows[i].id);
				fees.push(rows[i].orderTotalPrice);
			}

			$.ajax({
				url : refundUrl,
				type : 'post',
				data : {
					ids : ids.join(','),
					fees : fees.join(',')
				},
				//async : false,
				success : function(data) {
					//console.log(data);
					var d = $.parseJSON(data);
					console.log(d);
					var msg = d.msg;
					//tip(d.description + '\n' + msg);
					console.log(d.success);
					$('#refund_loading').html(d.description);

					$('#refund_loading').css('background', 'Transparent');
					reloadTable();
					$("#" + refundGname).datagrid('unselectAll');
					ids = '';
				}
			});
		}

		//弹出退款窗口前先让所有控件展示出来
		function refundBegin() {
			$('#refund_loading').html("");
			$('#sub').show();
			$('#cal').show();
			$('#reTitle').show();
			$('#refund_loading').hide();
		}
		
	</script>