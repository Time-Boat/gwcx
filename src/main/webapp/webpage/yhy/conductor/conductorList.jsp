<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="plug-in/tools/popup/userSelect.js"></script>
<script src="plug-in/tools/popup/departSelect.js"></script>

<div class="easyui-layout" fit="true">
<div region="center"  style="padding:0px;border:0px">
<t:datagrid name="conductorList" title="验票员信息管理" autoLoadData="true" actionUrl="conductorController.do?datagrid"  fitColumns="true"
	idField="id" fit="true" queryMode="group" checkbox="true" >
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="姓名" field="name" query="true" frozenColumn="true" align="center" width="100"></t:dgCol>
	<t:dgCol title="性别" sortable="true" field="sex" dictionary="sex" query="true" align="center" width="40"></t:dgCol>
	<t:dgCol title="年龄" sortable="true" editor="numberbox" field="age" align="center" width="60"></t:dgCol>
	<t:dgCol title="电话号码" sortable="false" field="phoneNumber" query="true" align="center"  width="60"></t:dgCol>
	<t:dgCol title="创建日期" field="createDate" editor="datebox" formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group" align="center" width="120"></t:dgCol>
	<t:dgCol title="创建人" field="username"  align="center" width="80" query="true"></t:dgCol>
	<t:dgCol title="验票线路" field="jurisdiction"  align="center" width="150" ></t:dgCol>
	<t:dgCol title="业务类型" field="status" dictionary="carBType" query="true" align="center" width="80"></t:dgCol>
	<t:dgCol title="验票员状态" sortable="true"  dictionary="driver_status" field="conductStatus" align="center" width="80"></t:dgCol>
	<t:dgCol title="审核状态" sortable="true"  dictionary="audit_status" field="applicationStatus" align="center" width="80"></t:dgCol>
	<t:dgCol title="申请内容" sortable="true"  dictionary="apply_type" field="applyContent" align="center" width="80"></t:dgCol>
	<%--<t:dgCol title="审核人" sortable="true"  field="auditorUserName" align="center" width="80"></t:dgCol>
	<t:dgCol title="审核时间" sortable="true"  field="auditTime" editor="datebox" formatter="yyyy-MM-dd hh:mm:ss" align="center" width="100"></t:dgCol>
	<t:dgCol title="申请人" sortable="true"  field="applicationUserName" align="center" width="80"></t:dgCol>
	<t:dgCol title="申请时间" sortable="true"  field="applicationTime" editor="datebox" formatter="yyyy-MM-dd hh:mm:ss" align="center" width="100"></t:dgCol>
	--%>
	<t:dgToolBar operationCode="add" title="录入" icon="icon-add" url="conductorController.do?addorupdate" funname="add"></t:dgToolBar>
	<t:dgToolBar operationCode="edit" title="编辑" icon="icon-edit" url="conductorController.do?addorupdate" funname="update"></t:dgToolBar>
	<t:dgToolBar title="批量删除" icon="icon-remove" url="conductorController.do?doDeleteALLSelect" funname="deleteALLSelect"></t:dgToolBar>
	<t:dgCol title="操作" field="opt" width="200"></t:dgCol>
	<t:dgDelOpt title="common.delete" url="conductorController.do?del&id={id}&deleteFlag=1" urlStyle="align:center" exp="conductStatus#eq#0"/>
	
	<t:dgToolBar operationCode="detail" title="查看" icon="icon-search" url="conductorController.do?conductordetail" funname="detail"></t:dgToolBar>
	<t:dgFunOpt funname="applyEnable(id,conductStatus)" title="申请启用" operationCode="applyEnable" exp="conductStatus#eq#0"></t:dgFunOpt>
	<t:dgFunOpt funname="applyEnable(id,conductStatus)" title="申请停用" operationCode="applyDisable" exp="conductStatus#eq#1"></t:dgFunOpt>
	<t:dgFunOpt funname="agree(id)" title="同意" operationCode="agree" exp="applicationStatus#eq#0"></t:dgFunOpt>
	<t:dgFunOpt funname="refuse(id)" title="拒绝" operationCode="refuse" exp="applicationStatus#eq#0"></t:dgFunOpt>
	<t:dgFunOpt funname="lookRejectReason(id)" title="拒绝原因" operationCode="refusalReason" exp="applicationStatus#eq#2"></t:dgFunOpt>
	
	<%-- 
	<t:dgDelOpt operationCode="del" title="删除" url="conductorController.do?del&id={id}" exp="status#eq#0" urlStyle="color: red; padding-left: 5px;"/>
	<t:dgDelOpt operationCode="del" title="删除" url="conductorController.do?del&id={id}" exp="status#eq#1"  urlStyle="color: green; padding-right: 5px;"/>
	--%>

</t:datagrid></div>
<div id="dealerWin" class="easyui-window" title="拒绝原因" style="width:400px;height:300px"
    data-options="modal:true" closed="true" >
  </div>
</div>
<input type="hidden" value="${lineNameList}" id="lineNames" />
 <script type="text/javascript">
 
	//进入触发 
	$(function() {
		var json = $("#lineNames").val();
		var a1 = '<span style="display:-moz-inline-box;display:inline-block; padding:10px 2px;"><span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;';
		var a2 = 'text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; "title="所属线路">所属线路：</span>';
		var a3 = '<select name="lineId" style="width: 150px">';
		var c1 = '<option value="">选择所属线路</option>';
		
		if(json.indexOf("lineId")>0){
			var obj = eval('(' + json + ')');
			for (var i = 0; i < obj.data.length; i++) {
				c1 += '<option value="'+obj.data[i].lineId+'">' + obj.data[i].lineName+ '</option>';
			}
		}
		var a4 = '</select></span>';
		$("#conductorListForm").append(a1 + a2 + a3 + c1 + a4);
	});
 
 
 
	function testReloadPage(){
		document.location = "http://www.baidu.com"; 
	}
	function szqm(id) {
		createwindow('审核', 'jeecgDemoController.do?doCheck&id=' + id);
	}
	function getListSelections(){
		var ids = '';
		var rows = $("#jeecgDemoList").datagrid("getSelections");
		for(var i=0;i<rows.length;i++){
			ids+=rows[i].id;
			ids+=',';
		}
		
		ids = ids.substring(0,ids.length-1);
		alert(ids);
		return ids;
	}	
	//表单 sql导出
	function doMigrateOut(title,url,id){
		url += '&ids='+ getListSelections();
		window.location.href= url;
	}
	function doMigrateIn(){
		openuploadwin('Xml导入', 'transdata.do?toMigrate', "jeecgDemoList");
	}
	$(document).ready(function(){
		$("input[name='createDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("input[name='createDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});
		$("input[name='birthday']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd'});});

		UserSelectApi.popup("#searchColumnsRealName");
		DepartSelectApi.popup("#searchColumnsDepartName");
	});

	function addMobile(title,addurl,gname,width,height){
		window.open(addurl);
	}
	
	function updateMobile(title,url, id,width,height){
		gridname=id;
		var rowsData = $('#'+id).datagrid('getSelections');
		if (!rowsData || rowsData.length==0) {
			tip('请选择编辑项目');
			return;
		}
		if (rowsData.length>1) {
			tip('请选择一条记录再编辑');
			return;
		}
		
		url += '&id='+rowsData[0].id;
		window.open(url);
	}
	
	//申请启用 
	function applyEnable(id,conductStatus) {
		
		$.dialog.confirm('确定要申请？',function(r){
		    if (r){
		    	$.post(
		    		"conductorController.do?applyEnable",	
					{'id':id,'conductStatus':conductStatus},
					function(data){
						var obj = eval('(' + data + ')');
						tip(obj.msg);
						$('#conductorList').datagrid('reload');
					}
				);		
		    }
		});
	}
	
	//同意
	function agree(id) {
		
		$.dialog.confirm('确定通过？',function(r){
		    if (r){
		    	$.post(
		    		"conductorController.do?agree",	
					{'id':id},
					function(data){
						var obj = eval('(' + data + ')');
						tip(obj.msg);
						$('#conductorList').datagrid('reload');
					}
				);		
		    }
		});
	}
	
	//拒绝
	function refuse(id) {
		$('#dealerWin').empty().append(rejectWindow());
		$('#dealerWin').window('open'); // open a window
		$('#rejectReason').attr("readonly",false);
		$('#rejectReason').val("");
		$('#sub').show();
		$('#dialog_order_id').val(id);
	}
	
	//填写拒绝原因窗口
	function rejectWindow(){
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
	
	function commitReason(){
		var id = $('#dialog_order_id').val();
		var rejectReason = $('#rejectReason').val();
		if(rejectReason == ""){
			tip("请填写拒绝原因");
			return;
		}	
		$.ajax({
			url : "conductorController.do?refuse&id="+id+"&rejectReason="+rejectReason,
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
	
	//查看拒绝原因
	function lookRejectReason(id){
		$('#dealerWin').window('open');
		$('#sub').hide();
		$.ajax({
            type:"get",
            url:"conductorController.do?getReason&id="+id,
            dataType:'json',
            success:function(d){
           		//var obj = eval('('+d.msg+')');
           		$('#dealerWin').empty().append(lookRejectReasonWindow());
           		$('#rejectReason').val(d.msg);
           		$('#rejectReason').attr("readonly",true);
            }
        });
	}
	
	//填写拒绝原因窗口
	function lookRejectReasonWindow(){
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
	
</script>