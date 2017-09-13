<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="plug-in/tools/popup/userSelect.js"></script>
<link href="plug-in/tools/css/rejectReason.css" type="text/css" rel="stylesheet"/>
<script src="plug-in/tools/popup/departSelect.js"></script>
<div class="easyui-layout" fit="true">
<div region="center" style="padding:0px;border:0px">
<t:datagrid name="lineList2" title="线路管理" autoLoadData="true" actionUrl="lineInfoSpecializedController.do?datagrid&linetype=2"  fitColumns="true"
	idField="id" fit="true" queryMode="group" checkbox="true">
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="线路名称" field="name" query="true" frozenColumn="true" align="center" width="120"></t:dgCol>
	<t:dgCol title="起点地址" field="startLocation" query="true" align="center" width="100"></t:dgCol>
	<t:dgCol title="终点地址" field="endLocation" query="true" align="center" width="100"></t:dgCol>
	
	<t:dgCol title="线路类型" field="type" replace="公务班车_0,接送包车_1,接机_2,送机 _3,接火车_4,送火车_5"  align="center" width="50"></t:dgCol>
	<%--<t:dgCol title="出车时间段" field="dispath" dictionary="dispathtime" align="center" width="90"></t:dgCol> --%>
	<%--
	<t:dgCol title="线路图片" field="imageurl"  align="center" width="60"></t:dgCol>
	<t:dgCol title="线路状态" field="status" replace="启用_0,未启用_1"  align="center" width="60"></t:dgCol> 
	<t:dgCol title="创建时间" field="createTime" editor="datebox" formatter="yyyy-MM-dd hh:mm:ss"   align="center" width="120"></t:dgCol>--%>
	<t:dgCol title="创建人" field="username"  query="true" align="center" width="60"></t:dgCol>
	<t:dgCol title="所属公司" field="departname"  query="true" align="center" width="100"></t:dgCol>
	<t:dgCol title="线路状态" field="status" dictionary="lineStatus" query="true" align="center" width="50"></t:dgCol>
	<t:dgCol title="申请状态" field="applicationStatus" dictionary="line_apply_status" query="true" align="center" width="50"></t:dgCol>
	<t:dgCol title="申请内容" field="applyContent" dictionary="apply_type"  align="center" width="50"></t:dgCol>
	<%-- <t:dgCol title="发车时间" field="lstartTime" editor="datebox" formatter="hh:mm:ss" query="true" queryMode="group" align="center" width="120"></t:dgCol>
	<t:dgCol title="预计到达时间" field="lendTime" editor="datebox" formatter="hh:mm:ss" query="true" queryMode="group"  align="center" width="120"></t:dgCol>
	 --%>
	 <t:dgCol title="线路时长（分）" field="lineTimes"  align="center" width="50"></t:dgCol>
	 
	 <t:dgCol title="线路定价(元/人)" field="price"  align="center" width="50"></t:dgCol>
	<t:dgCol title="所在城市" field="city"  align="center" width="60"></t:dgCol>
	 <%--<t:dgCol title="线路备注" field="remark"  align="center" width="50"></t:dgCol>--%>
	<t:dgCol title="操作" field="opt" width="230"></t:dgCol>

	<t:dgToolBar operationCode="addLine" title="添加线路" icon="icon-add" url="lineInfoSpecializedController.do?addorupdate" funname="add" height="500"></t:dgToolBar>
	<t:dgToolBar operationCode="editLine" title="修改线路" icon="icon-edit" url="lineInfoSpecializedController.do?addorupdate" funname="update" height="500" ></t:dgToolBar>
	<t:dgToolBar operationCode="allot" title="批量分配" icon="icon-edit" url="lineInfoSpecializedController.do?lineAllot" funname="lineAllot" height="500" ></t:dgToolBar>
	<t:dgToolBar operationCode="detail" title="查看详情" icon="icon-search" url="lineInfoSpecializedController.do?linedetail" funname="detail"></t:dgToolBar>
	
	<t:dgFunOpt funname="addBusStop(id,name,status,applicationStatus)" title="站点管理"></t:dgFunOpt>
	<t:dgFunOpt funname="applyShelves(id,lineStatus)" title="申请上架" operationCode="applyShelves" exp="status#eq#1&&applicationStatus#eq#0"></t:dgFunOpt>
	<t:dgFunOpt funname="applyShelves(id,lineStatus)" title="申请上架" operationCode="applyShelves" exp="status#eq#1&&applicationStatus#eq#5"></t:dgFunOpt>
	<t:dgFunOpt funname="applyShelves(id,lineStatus)" title="申请上架" operationCode="applyShelves" exp="status#eq#1&&applicationStatus#eq#6"></t:dgFunOpt>
	<t:dgFunOpt funname="applyShelves(id,lineStatus)" title="申请上架" operationCode="applyShelves" exp="status#eq#1&&applicationStatus#eq#4"></t:dgFunOpt>
	<t:dgFunOpt funname="applyShelves(id,lineStatus)" title="申请下架" operationCode="applicationShelf" exp="status#eq#0&&applicationStatus#eq#0"></t:dgFunOpt>
	<t:dgFunOpt funname="applyShelves(id,lineStatus)" title="申请下架" operationCode="applicationShelf" exp="status#eq#0&&applicationStatus#eq#5"></t:dgFunOpt>
	<t:dgFunOpt funname="applyShelves(id,lineStatus)" title="申请下架" operationCode="applicationShelf" exp="status#eq#0&&applicationStatus#eq#6"></t:dgFunOpt>
	<t:dgFunOpt funname="applyShelves(id,lineStatus)" title="申请下架" operationCode="applicationShelf" exp="status#eq#0&&applicationStatus#eq#3"></t:dgFunOpt>
	<t:dgFunOpt funname="agree(id)" title="同意" operationCode="firstagree" exp="applicationStatus#eq#1"></t:dgFunOpt>
	<t:dgFunOpt funname="agree(id)" title="同意" operationCode="agrees" exp="applicationStatus#eq#2"></t:dgFunOpt>
	<t:dgFunOpt funname="refuse(id)" title="拒绝" operationCode="firstrefuse" exp="applicationStatus#eq#1"></t:dgFunOpt>
	<t:dgFunOpt funname="refuse(id)" title="拒绝" operationCode="refuses" exp="applicationStatus#eq#2"></t:dgFunOpt>
	<t:dgFunOpt funname="lookRejectReason(id)" title="初审拒绝原因" operationCode="rejectReason" exp="applicationStatus#eq#5"></t:dgFunOpt>
	<t:dgFunOpt funname="lookRejectReason(id)" title="复审拒绝原因" operationCode="rejectReason" exp="applicationStatus#eq#6"></t:dgFunOpt> 
	<t:dgFunOpt funname="lookLine(id,name)" title="查看"></t:dgFunOpt>
</t:datagrid> </div>
<div id="dealerWin" class="easyui-window" title="拒绝原因" style="width:400px;height:300px"
    data-options="modal:true" closed="true" >
  </div>
</div>
<input type="hidden" value="${cityList}" id="citylie" />
<input type="hidden" value="${companyList}" id="companyList" />
<div region="east" style="width: 490px;" split="true">
<div tools="#tt" class="easyui-panel" title="站点管理" style="padding: 10px;" fit="true" border="false" id="function-panelAddBusStop"></div>
</div>
<div id="tt"></div>
</div>

<script type="text/javascript">

	$(function() {
		$('#lineList2').datagrid({   
	    	rowStyler:function(index,row){
	    		if (row.applicationStatus=="0"){   
	            	return 'color:red';   
	        	}
	        	if (row.applicationStatus=="1"){   
	            	return 'color:#29B6F6';   
	        	}
	        	if (row.applicationStatus=="2"){   
	            	return 'color:#5400FF';   
	        	}
	    	}   
		});
	});

	$(document).ready(function(){
		$("input[name='createTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
		$("input[name='createTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
		$("input[name='birthday']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
		$("input[name='lstartTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
		$("input[name='lstartTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
		$("input[name='lendTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
		$("input[name='lendTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
	});
	function addBusStop(id,name,status,applicationStatus) {
		
		if(applicationStatus==1){
			tip('初审不能进行站点管理');
			return;
		}
		if(applicationStatus==2){
			tip('复审不能进行站点管理');
			return;
		}
		if(status==0){
			tip('已上架线路不能进行站点管理');
			return;
		}
		
		$("#function-panelAddBusStop").panel(
			{
				title :'线路名称：'+name,
				href:"lineInfoController.do?addBusStop&lineInfoId="+id+"&lineType=2"   //lineType=2为接送机跳转到的站点挂接界面
			}
		);
	}
	$(function() {
		//添加城市条件
		var json = $("#citylie").val();
		var obj = eval('(' + json + ')');
		var a1 = '<span style="display:-moz-inline-box;display:inline-block; padding:10px 2px;"><span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;';
		var a2 = 'text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; "title="选择城市">选择城市：</span>';
		var a3 = '<select name="cityID" style="width: 150px">';
		var c1 = '<option value="">选择城市</option>';
		for (var i = 0; i < obj.data.length; i++) {
			c1 += '<option value="'+obj.data[i].cityID+'">' + obj.data[i].cityName
					+ '</option>';
		}
		var a4 = '</select></span>';
		$("#lineList2Form").append(a1 + a2 + a3 + c1 + a4);//....
		
		//添加所属公司条件
		var json = $("#companyList").val();
		console.log(json);
		var obj1 = "";
		var ss = '<span style="display:-moz-inline-box;display:inline-block; padding:10px 2px;"><span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;';
		ss += 'text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; "title="所属公司">选择公司：</span>';
		ss += '<select name="company" style="width: 150px">';
		ss += '<option value="">选择公司</option>';
		if(json != null && json != ""){
			obj1 = eval('(' + json + ')');
			for (var i = 0; i < obj1.data.length; i++) {
				ss += '<option value="'+obj1.data[i].dCode+'">' + obj1.data[i].dName + '</option>';
			}
		}
		ss += '</select></span>';
		$("#lineList2Form").append(ss);//....
		
	});
	
	function lookLine(id, name) {
		createdetailwindow(name, "lineInfoSpecializedController.do?lineMap&id=" + id,"1200px","800px");
	}
	
	//申请上、下架
	function applyShelves(id,lineStatus) {
		
		$.dialog.confirm('确定要申请？',function(r){
		    if (r){
		    	$.post(
		    		"lineInfoSpecializedController.do?applyShelves",	
					{'id':id,'lineStatus':lineStatus},
					function(data){
						var obj = eval('(' + data + ')');
						tip(obj.msg);
						$('#lineList2').datagrid('reload');
					}
				);		
		    }
		});
	}
	
	//申请下架 
	function applicationShelf(id) {
		
		$.ajax({
            type:"post",
            url:"lineInfoSpecializedController.do?agree&id="+id,
            dataType:'json',
            success:function(data){
            	 var t = eval('('+data.jsonStr+')');
					tip(t.msg);
           		$('#win').window('close');
	           	//刷新当前窗体
	           	$('#lineList2').datagrid('reload');
            }
        });
	}
	
	//同意
	function agree(id) {
		
		$.dialog.confirm('确定通过？',function(r){
		    if (r){
		    	$.post(
		    		"lineInfoSpecializedController.do?agree",	
					{'id':id},
					function(data){
						var obj = eval('(' + data + ')');
						tip(obj.msg);
						$('#lineList2').datagrid('reload');
					}
				);		
		    }
		});
	}
	
	function commitReason(){
		var id = $('#dialog_order_id').val();
		var rejectReason = $('#rejectReason').val();
		if(rejectReason == ""){
			tip("请填写拒绝原因");
			return;
		}	
		$.ajax({
			url : "lineInfoSpecializedController.do?refuse&id="+id+"&rejectReason="+rejectReason,
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
	
	//拒绝
	function refuse(id) {
		$('#dealerWin').empty().append(rejectWindow());
		$('#dealerWin').window('open'); // open a window
		$('#rejectReason').attr("readonly",false);
		$('#rejectReason').val("");
		$('#sub').show();
		$('#dialog_order_id').val(id);
	}
	
	function lookRejectReason(id){
		$('#dealerWin').window('open');
		$('#sub').hide();
		$.ajax({
            type:"get",
            url:"lineInfoSpecializedController.do?getReason&id="+id,
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
	
	function lineAllot(title,url,id,width,height){
		
		var ids = '';
		var rows = $("#lineList2").datagrid("getSelections");
		for(var i=0;i<rows.length;i++){
			ids+=rows[i].id;
			ids+=',';
		}
		ids = ids.substring(0,ids.length-1);
		if(ids.length==0){
			tip('请选择要分配的线路');
			return;
		}
		url += '&ids='+ids;
		createwindow(title,url,width,height);
	}
	
	function update(title,url,id,width,height){
		var rows = $("#lineList2").datagrid("getSelections");
		if(rows.length==0){
			tip('请选择一条要修改的线路');
			return;
		}
		if(rows[0].applicationStatus==1){
			tip('初审不能修改线路');
			return;
		}
		if(rows[0].applicationStatus==2){
			tip('复审不能修改线路');
			return;
		}
		if(rows[0].status==0){
			tip('已上架线路不能修改');
			return;
		}
		if(rows.length>1){
			tip('只能修改一条线路');
			return;
		}
		
		url=url+"&id="+rows[0].id;
		
		createwindow(title,url,width,height);
		
	}
	/* 
	function detail(title,url,id,width,height){
		/* var rows = $("#lineList2").datagrid("getSelections");
		url=url+"&id="+rows[0].id;
		createwindow(title,url,'800px','600px'); 
		detail(title,url,'lineList2',null,600)
	} 
	*/
	function detail(title,url, id,width,height) {
		var rowsData = $('#'+id).datagrid('getSelections');
		
		if (!rowsData || rowsData.length == 0) {
			tip('请选择查看项目');
			return;
		}
		if (rowsData.length > 1) {
			tip('请选择一条记录再查看');
			return;
		}
	    url += '&load=detail&id='+rowsData[0].id;
		createdetailwindow(title,url,700,600);
	}
	
	
	
</script>
