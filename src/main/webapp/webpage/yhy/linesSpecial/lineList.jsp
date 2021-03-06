<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="plug-in/tools/popup/userSelect.js"></script>
<link href="plug-in/tools/css/rejectReason.css" type="text/css"
	rel="stylesheet" />
<script src="plug-in/tools/popup/departSelect.js"></script>
<div id="main_typegroup_list" class="easyui-layout" fit="true">
	<div region="center" style="padding: 0px; border: 0px">
		<t:datagrid name="lineList2" title="线路管理" autoLoadData="true"
			actionUrl="lineInfoSpecializedController.do?datagrid&linetype=2"
			fitColumns="true" idField="id" fit="true" queryMode="group"
			checkbox="true">
			<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="线路类型" field="type" query="true"
				replace="接机_2,送机 _3,接火车_4,送火车_5,接送服务_6" align="center" width="50"></t:dgCol>
			<t:dgCol title="用户类型" field="isDealerLine" query="true"
				dictionary="userType" align="center"></t:dgCol>
			<t:dgCol title="线路名称" field="name" frozenColumn="true" align="center"
				width="120"></t:dgCol>
			<t:dgCol title="起点地址" field="startLocation" align="center"
				width="100"></t:dgCol>
			<t:dgCol title="终点地址" field="endLocation" align="center" width="100"></t:dgCol>

			<%--<t:dgCol title="出车时间段" field="dispath" dictionary="dispathtime" align="center" width="90"></t:dgCol> --%>
			<%--
	<t:dgCol title="线路图片" field="imageurl"  align="center" width="60"></t:dgCol>
	<t:dgCol title="线路状态" field="status" replace="启用_0,未启用_1"  align="center" width="60"></t:dgCol> 
	<t:dgCol title="创建时间" field="createTime" editor="datebox" formatter="yyyy-MM-dd hh:mm:ss"   align="center" width="120"></t:dgCol>--%>
			<t:dgCol title="创建人" field="username" align="center" width="60"></t:dgCol>
			<t:dgCol title="所属公司" field="departname" align="center" width="100"></t:dgCol>
			<t:dgCol title="线路状态" field="status" dictionary="lineStatus"
				query="true" align="center" width="70"></t:dgCol>
			<t:dgCol title="申请内容" field="applyContent" dictionary="apply_type"
				align="center" width="50"></t:dgCol>
			<t:dgCol title="申请状态" field="applicationStatus"
				dictionary="line_apply_status" query="true" align="center"
				width="70"></t:dgCol>
			<t:dgCol title="申请时间" field="applicationTime" editor="datebox"
				formatter="yyyy-MM-dd hh:mm:ss" align="center" width="120"></t:dgCol>
			<t:dgCol title="线路时长（分）" field="lineTimes" align="center" width="50"></t:dgCol>

			<t:dgCol title="线路定价(元/人)" field="price" align="center" width="50"></t:dgCol>
			<t:dgCol title="所在城市" field="city" align="center" width="60"></t:dgCol>
			<%--<t:dgCol title="线路备注" field="remark"  align="center" width="50"></t:dgCol>--%>
			<t:dgCol title="操作" field="opt" width="230"></t:dgCol>

			<t:dgToolBar operationCode="addLine" title="添加线路" icon="icon-add"
				url="lineInfoSpecializedController.do?addorupdate" funname="add"
				height="500"></t:dgToolBar>
			<t:dgToolBar operationCode="editLine" title="修改线路" icon="icon-edit"
				url="lineInfoSpecializedController.do?addorupdate" funname="update"
				height="500"></t:dgToolBar>
			<t:dgToolBar operationCode="allot" title="批量分配" icon="icon-edit"
				url="lineInfoSpecializedController.do?lineAllot" funname="lineAllot"
				height="500"></t:dgToolBar>
			<t:dgToolBar operationCode="detail" title="查看详情" icon="icon-search"
				url="lineInfoSpecializedController.do?linedetail" funname="detail"></t:dgToolBar>
			<t:dgToolBar funname="coerceShelves(id)" title="强制下架" icon="icon-put"
				url="lineInfoSpecializedController.do?coerceShelves"
				operationCode="coerceShelves"></t:dgToolBar>

			<t:dgFunOpt title="删除" operationCode="delLine" funname="delLine(id)"
				exp="status#eq#1&&applicationStatus#eq#0" />
			<t:dgFunOpt title="删除" operationCode="delLine" funname="delLine(id)"
				exp="status#eq#1&&applicationStatus#eq#5" />
			<t:dgFunOpt title="删除" operationCode="delLine" funname="delLine(id)"
				exp="status#eq#1&&applicationStatus#eq#6" />
			<t:dgFunOpt
				funname="addBusStop(id,name,status,applicationStatus,isDealerLine)"
				title="站点管理" operationCode="addBusStop"></t:dgFunOpt>
			<t:dgFunOpt funname="applyShelves(id,status,isDealerLine)"
				title="申请上架" operationCode="applyShelves"
				exp="status#eq#1&&applicationStatus#eq#0"></t:dgFunOpt>
			<t:dgFunOpt funname="applyShelves(id,status,isDealerLine)"
				title="申请上架" operationCode="applyShelves"
				exp="status#eq#1&&applicationStatus#eq#5"></t:dgFunOpt>
			<t:dgFunOpt funname="applyShelves(id,status,isDealerLine)"
				title="申请上架" operationCode="applyShelves"
				exp="status#eq#1&&applicationStatus#eq#6"></t:dgFunOpt>

			<t:dgFunOpt funname="applyShelves(id,status,isDealerLine)"
				title="申请上架" operationCode="applyShelves"
				exp="status#eq#2&&applicationStatus#eq#5"></t:dgFunOpt>
			<t:dgFunOpt funname="applyShelves(id,status,isDealerLine)"
				title="申请上架" operationCode="applyShelves"
				exp="status#eq#2&&applicationStatus#eq#6"></t:dgFunOpt>
			<t:dgFunOpt funname="applyShelves(id,status,isDealerLine)"
				title="申请上架" operationCode="applyShelves"
				exp="status#eq#2&&applicationStatus#eq#4"></t:dgFunOpt>

			<t:dgFunOpt funname="applyShelves(id,status,isDealerLine)"
				title="申请下架" operationCode="applicationShelf"
				exp="status#eq#0&&applicationStatus#eq#0"></t:dgFunOpt>
			<t:dgFunOpt funname="applyShelves(id,status,isDealerLine)"
				title="申请下架" operationCode="applicationShelf"
				exp="status#eq#0&&applicationStatus#eq#5"></t:dgFunOpt>
			<t:dgFunOpt funname="applyShelves(id,status,isDealerLine)"
				title="申请下架" operationCode="applicationShelf"
				exp="status#eq#0&&applicationStatus#eq#6"></t:dgFunOpt>
			<t:dgFunOpt funname="applyShelves(id,status,isDealerLine)"
				title="申请下架" operationCode="applicationShelf"
				exp="status#eq#0&&applicationStatus#eq#3"></t:dgFunOpt>

			<t:dgFunOpt funname="agree(id)" title="同意" operationCode="firstagree"
				exp="applicationStatus#eq#1"></t:dgFunOpt>
			<t:dgFunOpt funname="agree(id)" title="同意" operationCode="agrees"
				exp="applicationStatus#eq#2"></t:dgFunOpt>

			<t:dgFunOpt funname="refuse(id)" title="拒绝"
				operationCode="firstrefuse" exp="applicationStatus#eq#1"></t:dgFunOpt>
			<t:dgFunOpt funname="refuse(id)" title="拒绝" operationCode="refuses"
				exp="applicationStatus#eq#2"></t:dgFunOpt>
			<t:dgFunOpt funname="lookRejectReason(id)" title="初审拒绝原因"
				operationCode="rejectReason" exp="applicationStatus#eq#5"></t:dgFunOpt>
			<t:dgFunOpt funname="lookRejectReason(id)" title="复审拒绝原因"
				operationCode="rejectReason" exp="applicationStatus#eq#6"></t:dgFunOpt>
			<t:dgFunOpt funname="lookLine(id,name,isDealerLine,type)" title="查看"></t:dgFunOpt>
			<t:dgToolBar funname="addCarRegion()" icon="icon-edit"
				title="编辑座位区间价格" operationCode="editSitPrice"></t:dgToolBar>
			<t:dgToolBar funname="detailCarRegion(id)" icon="icon-search"
				title="查看座位区间价格"></t:dgToolBar>

		</t:datagrid>
	</div>
	<div id="dealerWin" class="easyui-window" title="拒绝原因"
		style="width: 400px; height: 300px" data-options="modal:true"
		closed="true"></div>
</div>
<input type="hidden" value="${cityList}" id="citylie" />
<input type="hidden" value="${linelist}" id="linelies" />
<input type="hidden" value="${companylist}" id="companies" />
<input type="hidden" value="${createPeoplelist}" id="createPeoplelies" />
<input type="hidden" value="${startlist}" id="startlies" />
<input type="hidden" value="${endlist}" id="endlies" />


<div
	data-options="region:'east',
	title:'站点名称',
	collapsed:true,
	split:true,
	border:false,
	onExpand : function(){
		li_east = 1;
	},
	onCollapse : function() {
	    li_east = 0;
	}"
	style="width: 490px; overflow: hidden;" id="eastPanel">
	<div class="easyui-panel" style="padding: 0px; border: 0px" fit="true"
		border="false" id="function-panelAddBusStop"></div>
</div>


<div id="tt"></div>

<script type="text/javascript">
	$(function() {
		$('#lineList2').datagrid({
			rowStyler : function(index, row) {
				if (row.applicationStatus == "0") {
					return 'color:red';
				}
				if (row.applicationStatus == "1") {
					return 'color:#29B6F6';
				}
				if (row.applicationStatus == "2") {
					return 'color:#5400FF';
				}
			}
		});
	});
	
	//根据线路类型获取线路和起点站、终点站
	function getLineName() {

		$("select[name='type']")
				.change(
						function() {
							var userType = $("select[name='isDealerLine']").val();
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
											$("#endLocation").empty();//先置空 
											$("#endLocation").append(d3);
											
										}
									});
						});
	}

	//根据用户类型获取线路和起点站、终点站
	function getUserLineName() {

		$("select[name='isDealerLine']")
				.change(
						function() {
							var ordertype = $("select[name='type']")
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
											$("#endLocation").empty();//先置空 
											$("#endLocation").append(d3);
											
											
										}
									});
						});
	}

	function getStartLocation() {

		$("select[name='lineId']")
				.change(
						function() {
							var lineType = $("select[name='type']").val();
							var lineId = $(this).children('option:selected')
									.val(); //当前选择项的值
							var url = "lineInfoSpecializedController.do?getStartLocation&lineId="
									+ lineId + "&lineType=" + lineType;
							$
									.ajax({
										type : 'POST',
										url : url,
										success : function(ds) {
											var d1 = '<option value="">选择起点站</option>';
											var obj = eval('(' + ds + ')');
											if (obj.indexOf("busid") > 0) {
												var objs = eval('(' + obj + ')');
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

	function getEndLocation() {

		$("select[name='lineId']")
				.change(
						function() {
							
							var lineType = $("select[name='type']").val();
							var lineId = $(this).children('option:selected')
									.val(); //当前选择项的值
							var url = "lineInfoSpecializedController.do?getEndLocation&lineId="
									+ lineId + "&lineType=" + lineType;
							$
									.ajax({
										type : 'POST',
										url : url,
										success : function(ds) {
											var d1 = '<option value="">选择终点站</option>';
											var obj = eval('(' + ds + ')');
											if (obj.indexOf("busid") > 0) {
												var objs = eval('(' + obj + ')');
												for (var i = 0; i < objs.data.length; i++) {
													d1 += '<option value="'+objs.data[i].busid+'">'
															+ objs.data[i].busName
															+ '</option>';
												}
											}
											$("#endLocation").empty();//先置空 
											$("#endLocation").append(d1);
										}
									});
						});
	}

	$(document).ready(
			function() {
				$("input[name='createTime_begin']").attr("class", "Wdate")
						.click(function() {
							WdatePicker({
								dateFmt : 'yyyy-MM-dd HH:mm:ss'
							});
						});
				$("input[name='createTime_end']").attr("class", "Wdate").click(
						function() {
							WdatePicker({
								dateFmt : 'yyyy-MM-dd HH:mm:ss'
							});
						});
				$("input[name='birthday']").attr("class", "Wdate").click(
						function() {
							WdatePicker({
								dateFmt : 'yyyy-MM-dd HH:mm:ss'
							});
						});
				$("input[name='lstartTime_begin']").attr("class", "Wdate")
						.click(function() {
							WdatePicker({
								dateFmt : 'yyyy-MM-dd HH:mm:ss'
							});
						});
				$("input[name='lstartTime_end']").attr("class", "Wdate").click(
						function() {
							WdatePicker({
								dateFmt : 'yyyy-MM-dd HH:mm:ss'
							});
						});
				$("input[name='lendTime_begin']").attr("class", "Wdate").click(
						function() {
							WdatePicker({
								dateFmt : 'yyyy-MM-dd HH:mm:ss'
							});
						});
				$("input[name='lendTime_end']").attr("class", "Wdate").click(
						function() {
							WdatePicker({
								dateFmt : 'yyyy-MM-dd HH:mm:ss'
							});
						});
			});

	$(function() {
		var li_east = 0;
	});

	function addBusStop(id, name, status, applicationStatus, isDealerLine) {

		if (applicationStatus == 1) {
			tip('初审不能进行站点管理');
			return;
		}
		if (applicationStatus == 2) {
			tip('复审不能进行站点管理');
			return;
		}
		if (status == 0) {
			tip('已上架线路不能进行站点管理');
			return;
		}
		var title = '<t:mutiLang langKey="站点名称"/>: ' + name;
		if (li_east == 0) {
			$('#main_typegroup_list').layout('expand', 'east');
		}
		$('#main_typegroup_list').layout('panel', 'east').panel('setTitle',
				title);

		$("#function-panelAddBusStop").panel(
				"refresh",
				"lineInfoController.do?addBusStop&lineInfoId=" + id
						+ "&lineType=2&isDealerLine=" + isDealerLine);

	}
	$(function() {
		//添加城市条件
		var json = $("#citylie").val();
		var json1 = $("#linelies").val();
		var json2 = $("#companies").val();
		var json3 = $("#createPeoplelies").val();
		var json5 = $("#startlies").val();
		var json6 = $("#endlies").val();

		var a1 = '<span style="display:-moz-inline-box;display:inline-block; padding:10px 2px;"><span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;';
		var a2 = 'text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; "title="选择城市">选择城市：</span>';
		var a3 = '<select name="cityID" style="width: 150px">';
		var c1 = '<option value="">选择城市</option>';

		if (json.indexOf("cityID") > 0) {
			var obj = eval('(' + json + ')');
			for (var i = 0; i < obj.data.length; i++) {
				c1 += '<option value="'+obj.data[i].cityID+'">'
						+ obj.data[i].cityName + '</option>';
			}
		}
		var a4 = '</select></span>';

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

		//添加起点条件
		var st1 = '<span style="display:-moz-inline-box;display:inline-block; padding:10px 2px;"><span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;';
		var st2 = 'text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; "title="选择起点站">选择起点站：</span>';
		var st3 = '<select id ="startLocation" name="startLocation" style="width: 150px">';
		var st4 = '<option value="">选择起点站</option>';

		if (json5.indexOf("busid") > 0) {
			var obj = eval('(' + json5 + ')');
			for (var i = 0; i < obj.data.length; i++) {
				st4 += '<option value="'+obj.data[i].busid+'">'
						+ obj.data[i].busName + '</option>';
			}
		}
		var st5 = '</select></span>';

		//添加起点条件
		var en1 = '<span style="display:-moz-inline-box;display:inline-block; padding:10px 2px;"><span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;';
		var en2 = 'text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; "title="选择终点站">选择终点站：</span>';
		var en3 = '<select id ="endLocation" name="endLocation" style="width: 150px">';
		var en4 = '<option value="">选择终点站</option>';

		if (json6.indexOf("busid") > 0) {
			var obj = eval('(' + json6 + ')');
			for (var i = 0; i < obj.data.length; i++) {
				en4 += '<option value="'+obj.data[i].busid+'">'
						+ obj.data[i].busName + '</option>';
			}
		}
		var en5 = '</select></span>';

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

		var cr1 = '<span style="display:-moz-inline-box;display:inline-block; padding:10px 2px;"><span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;';
		var cr2 = 'text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; "title="选择创建人">选择创建人：</span>';
		var cr3 = '<select id ="userId" name="userId" style="width: 150px">';
		var cr4 = '<option value="">选择创建人</option>';

		if (json3.indexOf("userId") > 0) {
			var obj = eval('(' + json3 + ')');
			for (var i = 0; i < obj.data.length; i++) {
				cr4 += '<option value="'+obj.data[i].userId+'">'
						+ obj.data[i].userName + '</option>';
			}
		}
		var cr5 = '</select></span>';

		$("#lineList2Form").append(
				a1 + a2 + a3 + c1 + a4 + a111 + a211 + a311 + c111 + a411 + cr1
						+ cr2 + cr3 + cr4 + cr5 + a11 + a21 + a31 + c11 + a41
						+ st1 + st2 + st3 + st4 + st5 + en1 + en2 + en3 + en4
						+ en5);//....
		getLineName();
		getUserLineName();
		getStartLocation();
		getEndLocation();
		//getTypeStartLocation(); 
		//getTypeEndLocation();
	});

	function lookLine(id, name, isDealerLine, type) {
		//console.log(id + '------' + name);
		createdetailwindow(name, "lineInfoSpecializedController.do?lineMap&id="
				+ id + "&isDealerLine=" + isDealerLine + "&type=" + type,
				"1200px", "800px");
	}

	//申请上、下架
	function applyShelves(id, status, isDealerLine) {
		var b = true;

		$.dialog.confirm('确定要申请？', function(r) {
			if (r) {
				$.post("lineInfoSpecializedController.do?applyShelves", {
					'id' : id,
					'status' : status,
					'isDealerLine' : isDealerLine
				}, function(data) {
					var obj = eval('(' + data + ')');
					b = obj.success;
					if (!b) {
						tip(obj.msg);
						return;
					} else {
						tip(obj.msg);
					}

					$('#lineList2').datagrid('reload');
				});
			}
		});
	}

	//强制下架
	function coerceShelves(id) {

		var ids = '';
		var rows = $("#lineList2").datagrid("getSelections");
		for (var i = 0; i < rows.length; i++) {
			ids += rows[i].id;
			ids += ',';
		}
		ids = ids.substring(0, ids.length - 1);
		if (ids.length == 0) {
			tip('请选择强制下架的线路');
			return;
		}
		if (rows[0].status == '1') {
			tip('未上架线路不能下架！');
			return;
		}
		//url += '&ids='+ids;

		$.dialog.confirm('确定要强制下架？', function(r) {
			if (r) {
				$.post("lineInfoSpecializedController.do?coerceShelves", {
					'id' : ids
				}, function(data) {
					var obj = eval('(' + data + ')');
					tip(obj.msg);
					$('#lineList2').datagrid('reload');
				});
			}
		});
	}

	//同意
	function agree(id) {

		$.dialog.confirm('确定同意？', function(r) {
			if (r) {
				$.post("lineInfoSpecializedController.do?agree", {
					'id' : id
				}, function(data) {
					var obj = eval('(' + data + ')');
					tip(obj.msg);
					$('#lineList2').datagrid('reload');
				});
			}
		});
	}

	function commitReason() {
		var id = $('#dialog_order_id').val();
		var rejectReason = $('#rejectReason').val();
		if (rejectReason == "") {
			tip("请填写拒绝原因");
			return;
		}
		$.ajax({
			url : "lineInfoSpecializedController.do?refuse&id=" + id
					+ "&rejectReason=" + rejectReason,
			type : "get",
			success : function(data) {
				//console.log(data);
				var d = $.parseJSON(data);
				console.log(d);
				var msg = d.msg;
				//tip(d.description + '\n' + msg);
				console.log(d.success);
				$('#dealerWin').window('close');
				tip(msg);
				reloadTable();
			}
		});
	}

	//填写拒绝原因窗口
	function rejectWindow() {
		var rwin = '';
		rwin += '<div class="easyui-layout" data-options="fit:true">';
		rwin += '<div style="text-align: center; " data-options="region:' + 'center' + '">';
		rwin += '<input type="hidden" id="dialog_order_id" value="" />';
		rwin += '<h5>请填写拒绝原因</h5>';
		rwin += '<textarea id="rejectReason" type="text" style="width:70%;height:40%;resize:none;" rows="5" cols= "7"></textarea>';
		rwin += '<input id="terefuse" type="hidden" />';
		rwin += '<div style="margin-top: 30px">';
		rwin += '<input id="sub" type="button" class="button white" value="提交" style="margin-right: 50px;width:50px;height:30px" onclick="commitReason()" />';
		rwin += '<input id="cal" type="button" class="button white" value="取消" style="width:50px;height:30px" onclick="javascript:$(\'#dealerWin\').window(\'close\');"/>';
		rwin += '</div></div></div>';

		return rwin;
	}

	//拒绝
	function refuse(id) {
		$('#dealerWin').empty().append(rejectWindow());
		$('#dealerWin').window('open'); // open a window
		$('#rejectReason').attr("readonly", false);
		$('#rejectReason').val("");
		$('#sub').show();
		$('#dialog_order_id').val(id);
	}

	function lookRejectReason(id) {
		$('#dealerWin').window('open');
		$('#sub').hide();
		$.ajax({
			type : "get",
			url : "lineInfoSpecializedController.do?getReason&id=" + id,
			dataType : 'json',
			success : function(d) {
				//var obj = eval('('+d.msg+')');
				$('#dealerWin').empty().append(lookRejectReasonWindow());
				$('#rejectReason').val(d.msg);
				$('#rejectReason').attr("readonly", true);
			}
		});
	}

	//填写拒绝原因窗口
	function lookRejectReasonWindow() {
		var rwin = '';
		rwin += '<div class="easyui-layout" data-options="fit:true">';
		rwin += '<div style="text-align: center; " data-options="region:' + 'center' + '">';
		rwin += '<input type="hidden" id="dialog_order_id" value="" />';
		rwin += '<h5>请填写拒绝原因</h5>';
		rwin += '<textarea id="rejectReason" type="text" style="width:70%;height:40%;resize:none;" rows="5" cols= "7"></textarea>';
		rwin += '<input id="terefuse" type="hidden" />';
		rwin += '<div style="margin-top: 30px">';
		rwin += '<input id="cal" type="button" class="button white" value="关闭" style="width:50px;height:30px" onclick="javascript:$(\'#dealerWin\').window(\'close\');"/>';
		rwin += '</div></div></div>';

		return rwin;
	}

	function lineAllot(title, url, id, width, height) {

		var ids = '';
		var rows = $("#lineList2").datagrid("getSelections");
		for (var i = 0; i < rows.length; i++) {
			ids += rows[i].id;
			ids += ',';
		}
		ids = ids.substring(0, ids.length - 1);
		if (ids.length == 0) {
			tip('请选择要分配的线路');
			return;
		}
		url += '&ids=' + ids;
		createwindow(title, url, width, height);
	}

	function update(title, url, id, width, height) {
		var rows = $("#lineList2").datagrid("getSelections");
		if (rows.length == 0) {
			tip('请选择一条要修改的线路');
			return;
		}
		if (rows[0].applicationStatus == 1) {
			tip('初审不能修改线路');
			return;
		}
		if (rows[0].applicationStatus == 2) {
			tip('复审不能修改线路');
			return;
		}
		if (rows[0].status == 0) {
			tip('已上架线路不能修改');
			return;
		}
		if (rows.length > 1) {
			tip('只能修改一条线路');
			return;
		}

		url = url + "&id=" + rows[0].id;

		createwindow(title, url, width, height);

	}

	function detail(title, url, id, width, height) {
		var rowsData = $('#' + id).datagrid('getSelections');

		if (!rowsData || rowsData.length == 0) {
			tip('请选择查看项目');
			return;
		}
		if (rowsData.length > 1) {
			tip('请选择一条记录再查看');
			return;
		}
		url += '&load=detail&id=' + rowsData[0].id;
		createdetailwindow(title, url, 700, 670);
	}

	function addCarRegion() {
		var rows = $("#lineList2").datagrid("getSelections");
		if (rows.length == 0) {
			tip('请选择一条要编辑区间价格的线路');
			return;
		}
		if (rows.length > 1) {
			tip('只能编辑一条线路的区间价格');
			return;
		}
		var array = new Array();
		var isDealerLine = rows[0].isDealerLine;
		array = isDealerLine;
		var arr = $.inArray("1", array);
		if (arr == '-1') {
			tip('该线路没有渠道商，不能编辑区间价格');
			return;
		}

		if (rows[0].status == '0') {
			tip('该线路已上架，不能编辑区间价格');
			return;
		}
		if (rows[0].status == '1' || rows[0].status == '2') {
			if (rows[0].applicationStatus == '1') {
				tip('该线路初审，不能编辑区间价格');
				return;
			}
			if (rows[0].applicationStatus == '2') {
				tip('该线路复审，不能编辑区间价格');
				return;
			}
		}

		add("编辑座位区间价格", "lineInfoSpecializedController.do?addCarRegion&id="
				+ rows[0].id, "500px", "470px");
	}

	function detailCarRegion(id) {
		var rows = $("#lineList2").datagrid("getSelections");
		var url = "lineInfoSpecializedController.do?addCarRegion"
		url += '&load=detail&id=' + rows[0].id;
		createdetailwindow("查看座位区间价格", url, 500, 470);
	}

	function delLine(id) {

		$.dialog.confirm('确定要删除该记录吗？', function(r) {
			if (r) {
				$.post("lineInfoSpecializedController.do?delLine", {
					'id' : id
				}, function(data) {
					var obj = eval('(' + data + ')');
					tip(obj.msg);
					$('#lineList2').datagrid('reload');
				});
			}
		});

	}
</script>
