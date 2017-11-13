<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="plug-in/tools/popup/userSelect.js"></script>
<script src="plug-in/tools/popup/departSelect.js"></script>

<div class="easyui-layout" fit="true">
<div region="center" style="padding:0px;border:0px">
 <t:datagrid name="driversInfoList" title="司机信息管理" autoLoadData="true" actionUrl="driversInfoController.do?datagrid" checkbox="true"  fitColumns="true"
	idField="id" fit="true" queryMode="group" >
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="图片地址" field="driverImgUrl" hidden="true" ></t:dgCol>
	<t:dgCol title="司机姓名" field="name" query="true" frozenColumn="true" align="center" width="80"></t:dgCol>
	<t:dgCol title="性别" sortable="true" field="sex" dictionary="sex" query="true" align="center" width="60"></t:dgCol>
	<t:dgCol title="所在城市" sortable="true" field="cityName" align="center" width="60"></t:dgCol>
	<t:dgCol title="年龄" sortable="true" editor="numberbox" field="age" align="center" width="50"></t:dgCol>
	<t:dgCol title="电话" sortable="true" query="true" field="phoneNumber" align="center" width="80"></t:dgCol>
	<%-- <t:dgCol title="身份证" field="idCard" align="center" width="120"></t:dgCol> --%>
	<t:dgCol title="创建人" field="username" align="center" width="60"></t:dgCol>
	<t:dgCol title="创建日期" field="createDate" editor="datebox" formatter="yyyy-MM-dd hh:mm:ss" queryMode="group" align="center" width="100"></t:dgCol>
	<t:dgCol title="使用状态" sortable="true"  dictionary="driver_use" field="useStatus" align="center" width="80"></t:dgCol>
	<t:dgCol title="司机状态" sortable="true"  query="true" dictionary="driver_status" field="status" align="center" width="80"></t:dgCol>
	<t:dgCol title="审核状态" sortable="true"  query="true" dictionary="audit_status" field="applicationStatus" align="center" width="80"></t:dgCol>
	<t:dgCol title="申请内容" sortable="true"  dictionary="apply_type" field="applyContent" align="center" width="80"></t:dgCol>
	<%--<t:dgCol title="审核人" sortable="true"  field="auditorUserName" align="center" width="80"></t:dgCol>
	<t:dgCol title="审核时间" sortable="true"  field="auditTime" editor="datebox" formatter="yyyy-MM-dd hh:mm:ss" align="center" width="100"></t:dgCol>
	<t:dgCol title="申请人" sortable="true"  field="applicationUserName" align="center" width="80"></t:dgCol>
	<t:dgCol title="申请时间" sortable="true"  field="applicationTime" editor="datebox" formatter="yyyy-MM-dd hh:mm:ss" align="center" width="100"></t:dgCol>
	--%>
	<t:dgCol title="备注" field="remark" align="center" width="50"></t:dgCol>	
	<t:dgToolBar operationCode="add" title="录入" icon="icon-add" url="driversInfoController.do?addorupdate&type=add" funname="add" height="450" ></t:dgToolBar>
	<t:dgToolBar operationCode="edit" title="编辑" icon="icon-edit" url="driversInfoController.do?addorupdate" funname="update" height="450"></t:dgToolBar>
	<t:dgToolBar title="批量分配" icon="icon-redo" url="driversInfoController.do?getAttacheList" operationCode="driverAllotAttache" funname="driverAllot" ></t:dgToolBar>
	<t:dgCol title="操作" field="opt" width="250"></t:dgCol>
	
	<%-- <t:dgFunOpt funname="applyEnable(id)" title="申请启用" ></t:dgFunOpt>
	<t:dgFunOpt funname="applyEnable(id)" title="申请停用" ></t:dgFunOpt> --%>
	
	<t:dgFunOpt funname="applyEnable(id)" title="申请启用" operationCode="applyEnable" exp="status#eq#0&&applicationStatus#eq#-1"></t:dgFunOpt>
	<t:dgFunOpt funname="applyEnable(id)" title="申请启用" operationCode="applyEnable" exp="status#eq#0&&applicationStatus#eq#2"></t:dgFunOpt>
	<t:dgFunOpt funname="applyEnable(id)" title="申请启用" operationCode="applyEnable" exp="status#eq#2&&applicationStatus#eq#1"></t:dgFunOpt>
	<t:dgFunOpt funname="applyEnable(id)" title="申请启用" operationCode="applyEnable" exp="status#eq#2&&applicationStatus#eq#2"></t:dgFunOpt>
	<t:dgFunOpt funname="applyEnable(id)" title="申请停用" operationCode="applyDisable" exp="status#eq#1&&applicationStatus#eq#1"></t:dgFunOpt>
	<t:dgFunOpt funname="applyEnable(id)" title="申请停用" operationCode="applyDisable" exp="status#eq#1&&applicationStatus#eq#2"></t:dgFunOpt>
	
	<t:dgFunOpt funname="agree(id)" title="同意" operationCode="agree" exp="applicationStatus#eq#0"></t:dgFunOpt>
	<t:dgFunOpt funname="refuse(id)" title="拒绝" operationCode="refuse" exp="applicationStatus#eq#0"></t:dgFunOpt>
	<t:dgFunOpt funname="lookRejectReason(id)" title="拒绝原因" operationCode="refusalReason" exp="applicationStatus#eq#2"></t:dgFunOpt>
	<t:dgDelOpt title="common.delete" url="driversInfoController.do?del&id={id}" urlStyle="align:center" exp="status#eq#0&&applicationStatus#eq#-1" />
	<t:dgDelOpt title="common.delete" url="driversInfoController.do?del&id={id}" urlStyle="align:center" exp="status#eq#0&&applicationStatus#eq#2" />
	<%-- <t:dgToolBar title="批量删除" icon="icon-remove" url="driversInfoController.do?doDeleteALLSelect" funname="deleteALLSelect"></t:dgToolBar> --%>
	
	<t:dgToolBar operationCode="detail" title="查看" icon="icon-search" url="driversInfoController.do?driverdetail" funname="detail" height="450"></t:dgToolBar>
	<t:dgFunOpt funname="UploadFile(id)" title="上传图片" operationCode="driverUpload" ></t:dgFunOpt>
	<t:dgFunOpt funname="lookQRCode(driverImgUrl)" title="预览" exp="driverImgUrl#empty#false"></t:dgFunOpt>
</t:datagrid> 

</div>
<div id="dealerWin" class="easyui-window" title="拒绝原因" style="width:400px;height:300px"
    data-options="modal:true" closed="true" >
  </div>
</div>
<input type="hidden" value="${cityList}" id="citylie" />
<script type="text/javascript">

	function update(title,url,id,width,height,isRestful) {
		gridname=id;
		var rowsData = $('#'+id).datagrid('getSelections');
		if (!rowsData || rowsData.length==0) {
			tip('请选择编辑项目！');
			return;
		}
		if (rowsData.length > 1) {
			tip('请选择一条记录再编辑！');
			return;
		}
		/* if (rowsData[0].status == 2 || rowsData[0].status == 1) {
			tip('停止和启用状态不能编辑！');
			return;
		}
		if (rowsData[0].applicationStatus == 0 ) {
			tip('审核状态中不能被编辑！');
			return;
		} */
		if(isRestful!='undefined'&&isRestful){
			url += '/'+rowsData[0].id;
		}else{
			if(rowsData[0].id==null){
				rowsData[0].id = '';
			}
			url += '&id='+rowsData[0].id;
		}
		createwindow(title,url,width,height);
	}

	$(function() {
		var json = $("#citylie").val();
		var a1 = '<span style="display:-moz-inline-box;display:inline-block; padding:10px 2px;"><span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;';
		var a2 = 'text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; "title="选择城市">选择城市：</span>';
		var a3 = '<select name="cityID" style="width: 150px">';
		var c1 = '<option value="">所在城市</option>';
		if(json.indexOf("cityID")>0){
			var obj = eval('(' + json + ')');
			for (var i = 0; i < obj.data.length; i++) {
				c1 += '<option value="'+obj.data[i].cityID+'">' + obj.data[i].cityName+ '</option>';
			}
		}
		var a4 = '</select></span>';
		$("#driversInfoListForm").append(a1 + a2 + a3 + c1 + a4);
	});
	
	//删除调用函数
	function delObj(url,name) {
		
		var id = url.substr(url.lastIndexOf("=") + 1);
		$.get(
			"driversInfoController.do?checkBinding&id="+id,
			function(data){
				var obj = eval("(" + data + ")");
				if(!obj.success){
					tip(obj.msg);
				}else{
					gridname=name;
					createdialog('删除确认 ', '确定删除该记录吗 ?', url,name);
				}
			}
		);
	}
	
	//申请启用 
	function applyEnable(id) {
		
		$.dialog.confirm('确定要申请？',function(r){
		    if (r){
		    	$.post(
		    		"driversInfoController.do?applyEnable",	
					{'id':id},
					function(data){
						var obj = eval('(' + data + ')');
						tip(obj.msg);
						$('#driversInfoList').datagrid('reload');
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
		    		"driversInfoController.do?agree",	
					{'id':id},
					function(data){
						var obj = eval('(' + data + ')');
						tip(obj.msg);
						$('#driversInfoList').datagrid('reload');
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
			url : "driversInfoController.do?refuse&id="+id+"&rejectReason="+rejectReason,
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
            url:"driversInfoController.do?getReason&id="+id,
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
	
	//上传文件
	function UploadFile(id){
		createdetailwindow("选择图片","driversInfoController.do?driverUploadFile&id="+id,"800px","500px");
	}
	
	function lookQRCode(driverImgUrl){
		window.open(driverImgUrl);	
	}
	
	function driverAllot(title,url,id,width,height){
		
		var ids = '';
		var rows = $("#driversInfoList").datagrid("getSelections");
		for(var i=0;i<rows.length;i++){
			
			ids+=rows[i].id;
			ids+=',';
		}
		ids = ids.substring(0,ids.length-1);
		if(ids.length==0){
			tip('请选择要分配的司机');
			return;
		}
		url += '&ids='+ids;
		createwindow(title,url,width,height);
	}
	
</script>
