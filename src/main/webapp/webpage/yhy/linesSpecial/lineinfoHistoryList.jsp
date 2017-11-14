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
		<t:datagrid name="applyEditList" title="线路修改管理" autoLoadData="true"
			actionUrl="lineinfoHistoryController.do?datagrid&&linetype=2&&status=0"
			fitColumns="true" idField="id" fit="true" queryMode="group"
			checkbox="true">
			<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
			<t:dgCol title="渠道商" field="isDealerLine" query="true" dictionary="is_dealer"
				align="center"></t:dgCol>
			<t:dgCol title="线路名称" field="name" query="true" frozenColumn="true"
				align="center" width="80"></t:dgCol>
			<t:dgCol title="起点地址" field="startLocation" query="true"
				align="center" width="100"></t:dgCol>
			<t:dgCol title="终点地址" field="endLocation" query="true" align="center"
				width="100"></t:dgCol>

			<t:dgCol title="线路类型" field="type" query="true"
				replace="接机_2,送机 _3,接火车_4,送火车_5" align="center" width="50"></t:dgCol>
			<%--<t:dgCol title="出车时间段" field="dispath" dictionary="dispathtime" align="center" width="90"></t:dgCol> --%>
			<%--
	<t:dgCol title="线路图片" field="imageurl"  align="center" width="60"></t:dgCol>
	<t:dgCol title="线路状态" field="status" replace="启用_0,未启用_1"  align="center" width="60"></t:dgCol> 
	<t:dgCol title="创建时间" field="createTime" editor="datebox" formatter="yyyy-MM-dd hh:mm:ss"   align="center" width="120"></t:dgCol>--%>
			<t:dgCol title="创建人" field="username" query="true" align="center"
				width="60"></t:dgCol>
			<t:dgCol title="所属公司" field="departname" query="true" align="center"
				width="100"></t:dgCol>
			<t:dgCol title="线路状态" field="status" hidden="true" ></t:dgCol>
			<t:dgCol title="申请内容" field="applyContent" dictionary="apply_type"
				align="center" width="70"></t:dgCol>
			<t:dgCol title="申请修改状态" field="applicationEditStatus"
				dictionary="apply_edit_status" query="true" align="center"
				width="70"></t:dgCol>
			<t:dgCol title="申请修改时间" field="applicationEditTime" editor="datebox"
				formatter="yyyy-MM-dd hh:mm:ss" align="center" width="120"></t:dgCol>
			<t:dgCol title="线路时长（分）" field="lineTimes" align="center" width="50"></t:dgCol>

			<t:dgCol title="线路定价(元/人)" field="price" align="center" width="50"></t:dgCol>
			<t:dgCol title="所在城市" field="city" align="center" width="60"></t:dgCol>
			<%--<t:dgCol title="线路备注" field="remark"  align="center" width="50"></t:dgCol>--%>
			<t:dgCol title="操作" field="opt" width="150"></t:dgCol>
			<t:dgToolBar operationCode="editLine" title="修改线路" icon="icon-edit"
				url="lineinfoHistoryController.do?addorupdate" funname="update"
				height="500"></t:dgToolBar>
			<t:dgToolBar operationCode="detail" title="查看详情" icon="icon-search"
				url="lineInfoSpecializedController.do?linedetail&history=1" funname="detail"></t:dgToolBar>
			<t:dgFunOpt
				funname="addBusStop(id,name,status,applicationEditStatus)"
				title="站点管理" operationCode="addBusStop"></t:dgFunOpt>
				
			<t:dgFunOpt funname="applyEdit(id,applicationEditStatus,isDealerLine)" title="申请修改"
				operationCode="applyEdit" exp="applicationEditStatus#eq#0"></t:dgFunOpt>
			<t:dgFunOpt funname="applyEdit(id,applicationEditStatus,isDealerLine)" title="申请修改"
				operationCode="applyEdit" exp="applicationEditStatus#eq#3"></t:dgFunOpt>
			<t:dgFunOpt funname="applyEdit(id,applicationEditStatus,isDealerLine)" title="申请修改"
				operationCode="applyEdit" exp="applicationEditStatus#eq#4"></t:dgFunOpt>
			<t:dgFunOpt funname="applyEdit(id,applicationEditStatus,isDealerLine)" title="申请修改"
				operationCode="applyEdit" exp="applicationEditStatus#eq#5"></t:dgFunOpt>

			<t:dgFunOpt funname="agreeEdit(id)" title="同意"
				operationCode="firstagreeEdit" exp="applicationEditStatus#eq#1"></t:dgFunOpt>
			<t:dgFunOpt funname="agreeEdit(id)" title="同意"
				operationCode="agreeEdit" exp="applicationEditStatus#eq#2"></t:dgFunOpt>
			<t:dgFunOpt funname="refuseEdit(id)" title="拒绝"
				operationCode="firstrefuseEdit" exp="applicationEditStatus#eq#1"></t:dgFunOpt>
			<t:dgFunOpt funname="refuseEdit(id)" title="拒绝"
				operationCode="refuseEdit" exp="applicationEditStatus#eq#2"></t:dgFunOpt>

			<t:dgFunOpt funname="lookEditRejectReason(id)" title="初审拒绝原因"
				operationCode="fristrejectReason" exp="applicationEditStatus#eq#4"></t:dgFunOpt>
			<t:dgFunOpt funname="lookEditRejectReason(id)" title="复审拒绝原因"
				operationCode="rejectReason" exp="applicationEditStatus#eq#5"></t:dgFunOpt>
			<t:dgFunOpt funname="lookLine(id,name)" title="查看"></t:dgFunOpt>
			<t:dgToolBar funname="addCarRegion(id)" icon="icon-edit"
				title="编辑座位区间价格"></t:dgToolBar>
			<t:dgToolBar funname="detailCarRegion(id)" icon="icon-search"
				title="查看座位区间价格"></t:dgToolBar>

		</t:datagrid>
	</div>
	<div id="dealerWin" class="easyui-window" title="拒绝原因"
		style="width: 400px; height: 300px" data-options="modal:true"
		closed="true"></div>
</div>
<input type="hidden" value="${cityList}" id="citylie" />
<%-- <input type="hidden" value="${companyList}" id="companyList" /> --%>
<div data-options="region:'east',
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
    <div class="easyui-panel" style="padding:0px;border:0px" fit="true" border="false" id="function-panelAddBusStop"></div>
</div>
<div id="tt"></div>

<script type="text/javascript">
	$(function() {
		$('#applyEditList').datagrid({
			rowStyler : function(index, row) {
				if (row.applicationEditStatus == "0") {
					return 'color:red';
				}
				if (row.applicationEditStatus == "1") {
					return 'color:#29B6F6';
				}
				if (row.applicationEditStatus == "2") {
					return 'color:#5400FF';
				}
			}
		});
	});

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
		//添加城市条件
		var json = $("#citylie").val();
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
		$("#applyEditListForm").append(a1 + a2 + a3 + c1 + a4);//....

	});

	function lookLine(id, name) {
		createdetailwindow(name, "lineInfoSpecializedController.do?historyLineMap&id="
				+ id, "1200px", "800px");
	}

	function commitReason() {
		var id = $('#dialog_order_id').val();
		var rejectReason = $('#rejectReason').val();
		if (rejectReason == "") {
			tip("请填写拒绝原因");
			return;
		}
		$.ajax({
			url : "lineInfoSpecializedController.do?refuseEdit&id=" + id
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
	function refuseEdit(id) {
		$('#dealerWin').empty().append(rejectWindow());
		$('#dealerWin').window('open'); // open a window
		$('#rejectReason').attr("readonly", false);
		$('#rejectReason').val("");
		$('#sub').show();
		$('#dialog_order_id').val(id);
	}

	//查看拒绝原因
	function lookEditRejectReason(id) {
		$('#dealerWin').window('open');
		$('#sub').hide();
		$.ajax({
			type : "get",
			url : "lineInfoSpecializedController.do?getEditReason&id=" + id,
			dataType : 'json',
			success : function(d) {

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

	function addCarRegion(id) {
		var rows = $("#applyEditList").datagrid("getSelections");
		if (rows.length == 0) {
			tip('请选择一条要编辑区间价格的线路');
			return;
		}
		if (rows.length > 1) {
			tip('只能编辑一条线路的区间价格');
			return;
		}
		if (rows[0].isDealerLine != '1') {
			tip('该线路没有渠道商，不能编辑区间价格');
			return;
		}
		
		if (rows[0].status == '1') {
			if (rows[0].applicationStatus == '1') {
				tip('该线路初审，不能编辑区间价格');
				return;
			}
			if (rows[0].applicationStatus == '2') {
				tip('该线路复审，不能编辑区间价格');
				return;
			}
		}
		
		if (rows[0].applicationEditStatus == 1) {
			tip('初审不能编辑区间价格');
			return;
		}
		if (rows[0].applicationEditStatus == 2) {
			tip('复审不能编辑区间价格');
			return;
		}
		add("编辑座位区间价格", "lineInfoSpecializedController.do?addCarRegion&history=1&id="
				+ rows[0].id, "500px", "470px");
	}

	function detailCarRegion(id) {
		var rows = $("#applyEditList").datagrid("getSelections");
		var url = "lineInfoSpecializedController.do?addCarRegion"
		url += '&load=detail&id=' + rows[0].id;
		createdetailwindow("查看座位区间价格", url, 500, 470);
	}

	function applyEdit(id, applicationEditStatus,isDealerLine) {
		
		$.dialog.confirm('确定要申请修改？',function(r){
		    if (r){
		    	$.post(
		    		"lineinfoHistoryController.do?applyEdit",	
					{'id':id,'applicationEditStatus':applicationEditStatus,'isDealerLine':isDealerLine},
					function(data){
						var obj = eval('(' + data + ')');
		           		tip(obj.msg);
						$('#applyEditList').datagrid('reload');
					}
				);		
		    }
		});
		/* add("修改座位区间价格", "lineInfoSpecializedController.do?applyEdit&id=" + id,
				"500px", "470px"); */
	}

	//同意
	function agreeEdit(id) {

		$.dialog.confirm('确定同意？', function(r) {
			if (r) {
				$.post("lineinfoHistoryController.do?agreeEdit", {
					'id' : id
				}, function(data) {
					var obj = eval('(' + data + ')');
					tip(obj.msg);
					$('#applyEditList').datagrid('reload');
				});
			}
		});
	}

	function addBusStop(id, name, status, applicationEditStatus) {

		if (applicationEditStatus == 1) {
			tip('初审不能进行站点管理');
			return;
		}
		if (applicationEditStatus == 2) {
			tip('复审不能进行站点管理');
			return;
		}

		var title = '<t:mutiLang langKey="站点名称"/>: ' + name;
        if(li_east == 0){
            $('#main_typegroup_list').layout('expand','east');
        }
        $('#main_typegroup_list').layout('panel','east').panel('setTitle', title);
		
		$("#function-panelAddBusStop").panel("refresh","lineinfoHistoryController.do?addBusStop&lineInfoId="+id+"&lineType=2");
	}
	function update(title, url, id, width, height) {
		var rows = $("#applyEditList").datagrid("getSelections");
		if (rows.length == 0) {
			tip('请选择一条要修改的线路');
			return;
		}
		if (rows[0].applicationEditStatus == 1) {
			tip('初审不能修改线路');
			return;
		}
		if (rows[0].applicationEditStatus == 2) {
			tip('复审不能修改线路');
			return;
		}

		if (rows.length > 1) {
			tip('只能修改一条线路');
			return;
		}

		url = url + "&id=" + rows[0].id;

		createwindow(title, url, width, height);

	}
</script>
