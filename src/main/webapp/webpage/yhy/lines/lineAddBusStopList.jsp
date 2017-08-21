<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
<div region="center" style="padding:0px;border:0px">
<t:datagrid onLoadSuccess="selectRowFun" name="roleUserListLB" title="已挂接的线路站点" 
            actionUrl="lineInfoController.do?busStopByLineDatagrid&lineInfoId=${lineInfoId}" fit="true" fitColumns="true" idField="id" >
	<t:dgCol title="站点ID" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="关联表ID" field="line_busstopId" hidden="true" ></t:dgCol>
	<t:dgCol title="站点名称" field="name" align="center"></t:dgCol>
	<t:dgCol title="站点序号" field="siteOrder" align="center" ></t:dgCol>
	<t:dgCol title="预计站点到站时间" field="arrivalTime" align="center" ></t:dgCol>
	<t:dgCol title="操作" field="opt"></t:dgCol>
	<t:dgFunOpt title="删除关联站点" funname="delZd(id,line_busstopId)" />
	<t:dgToolBar title="添加站点挂接"  operationCode="addSite" icon="icon-add" url="lineInfoController.do?busStopInfoList&lineInfoId=${lineInfoId}&lineType=${lineType}" funname="add"></t:dgToolBar>
	<t:dgToolBar title="站点时间、序号编辑" operationCode="editSite"  icon="icon-edit" url="lineInfoController.do?updateBusStopOrder" funname="update"></t:dgToolBar>
	<t:dgToolBar title="上移"  icon="icon-putout" url="lineInfoController.do?moveup" funname="moveup"></t:dgToolBar>
	<t:dgToolBar title="下移"  icon="icon-put" url="lineInfoController.do?movedown" funname="movedown"></t:dgToolBar>
</t:datagrid>
</div>
</div>
<script type="text/javascript">

//选中的行
var selectRowB;

//移动状态
var moveStatus;

//上移
function moveup(title,url,id,width,height,isRestful){
	
	moveStatus = 0;
	
	gridname=id;
	var rowsData = $('#'+id).datagrid('getSelections');
	
	//$('#roleUserListLB').datagrid('selectRow', 1);
	
	//被选中的行
	selectRowB = $('#'+id).datagrid('getRowIndex',rowsData[0].id);
	//$('#'+id).datagrid('selectRow',2);
	//return;
	if (!rowsData || rowsData.length==0) {
		tip('请选择要上移的站点');
		return;
	}
	
	var siteOrder = rowsData[0].siteOrder;
	//console.log("siteOrder=" + siteOrder);
	
	if(siteOrder == "0" || siteOrder == "99" || siteOrder == "1"){
		tip("不能替换起点和终点");
		return;
	}
	
	if(isRestful!='undefined'&&isRestful){
		url += '/'+rowsData[0].id;
	}else{
		if(rowsData[0].id==null){
			rowsData[0].id = '';
		}
		if(rowsData[0].line_busstopId==null){
			rowsData[0].line_busstopId = '';
		}
		if(rowsData[0].name==null){
			rowsData[0].name = '';
		}
		if(rowsData[0].siteOrder==null){
			rowsData[0].siteOrder = '';
		}
		if(rowsData[0].arrivalTime==null){
			rowsData[0].arrivalTime = '';
		}
		url += '&id='+rowsData[0].id + '&line_busstopId=' + rowsData[0].line_busstopId + '&name=' + rowsData[0].name+'&siteOrder='+rowsData[0].siteOrder+'&arrivalTime='+rowsData[0].arrivalTime;
	}
	
	$.ajax({
		url : url,
		type : "get",
		success : function(data) {
			//console.log(data);
			var d = $.parseJSON(data);
			//console.log(d);
			var msg = d.msg;
			//tip(d.description + '\n' + msg);
			//console.log(d.success);
			//tip(msg);
			
			if(!d.success){
				tip(msg);
			}
			
			reloadTable();
			
		}
	});
}

function selectRowFun(){
	//console.log('selectRowB=' + selectRowB + 'moveStatus=' + moveStatus);
	//console.log(selectRowB);
	
	if(typeof(selectRowB) == "undefined") return;
	
	if(moveStatus == 0){
		$("#roleUserListLB").datagrid('selectRow', selectRowB - 1);
	}else{
		$("#roleUserListLB").datagrid('selectRow', selectRowB + 1);
	}
	
	selectRowB = undefined;
}

//下移
function movedown(title,url,id,width,height,isRestful){
	
	moveStatus = 1;
	
	//被选中的行
	
	gridname=id;
	var rowsData = $('#'+id).datagrid('getSelections');
	
	selectRowB = $('#'+id).datagrid('getRowIndex',rowsData[0].id);
	
	//当前页的倒数第二行的下标
	var countRow = $('#'+id).datagrid('getRows').length - 2;
	//console.log(countRow);
	
	if (!rowsData || rowsData.length==0) {
		tip('请选择要下移的站点');
		return;
	}
	
	var siteOrder = rowsData[0].siteOrder;
	//console.log("siteOrder=" + siteOrder);
	
	if(siteOrder == "0" || siteOrder == "99" || siteOrder == countRow){
		tip("不能替换起点和终点");
		return;
	}
	
	if(isRestful!='undefined'&&isRestful){
		url += '/'+rowsData[0].id;
	}else{
		if(rowsData[0].id==null){
			rowsData[0].id = '';
		}
		if(rowsData[0].line_busstopId==null){
			rowsData[0].line_busstopId = '';
		}
		if(rowsData[0].name==null){
			rowsData[0].name = '';
		}
		if(rowsData[0].siteOrder==null){
			rowsData[0].siteOrder = '';
		}
		if(rowsData[0].arrivalTime==null){
			rowsData[0].arrivalTime = '';
		}
		url += '&id='+rowsData[0].id + '&line_busstopId=' + rowsData[0].line_busstopId + '&name=' + rowsData[0].name+'&siteOrder='+rowsData[0].siteOrder+'&arrivalTime='+rowsData[0].arrivalTime;
	}
	$.ajax({
		url : url,
		type : "get",
		success : function(data) {
			//console.log(data);
			var d = $.parseJSON(data);
			//console.log(d);
			var msg = d.msg;
			//tip(d.description + '\n' + msg);
			//console.log(d.success);
			//tip(msg);
			
			if(!d.success){
				tip(msg);
			}
			
			reloadTable();
		}
	});
}

function update(title,url,id,width,height,isRestful) {
	gridname=id;
	var rowsData = $('#'+id).datagrid('getSelections');
	if (!rowsData || rowsData.length==0) {
		tip('请选择编辑项目');
		return;
	}
	if (rowsData.lengt > 1) {
		tip('请选择一条记录再编辑');
		return;
	}
	if(isRestful!='undefined'&&isRestful){
		url += '/'+rowsData[0].id;
	}else{
		if(rowsData[0].id==null){
			rowsData[0].id = '';
		}
		if(rowsData[0].line_busstopId==null){
			rowsData[0].line_busstopId = '';
		}
		if(rowsData[0].name==null){
			rowsData[0].name = '';
		}
		if(rowsData[0].siteOrder==null){
			rowsData[0].siteOrder = '';
		}
		if(rowsData[0].arrivalTime==null){
			rowsData[0].arrivalTime = '';
		}
		url += '&id='+rowsData[0].id + '&line_busstopId=' + rowsData[0].line_busstopId + '&name=' + rowsData[0].name+'&siteOrder='+rowsData[0].siteOrder+'&arrivalTime='+rowsData[0].arrivalTime;
	}
	createwindow(title,url,width,height);
}

function delZd(id,line_busstopId){
	//alert(id+"-----"+line_busstopId);
	//url="lineInfoController.do?delLine_BusStop&id={line_busstopId}"
	
	$.dialog.confirm('确定要删除该记录吗？',function(r){
	    if (r){
	    	$.post(
	    		"lineInfoController.do?delLine_BusStop",	
				{'id':line_busstopId,'zdId':id},
				function(data){
					//console.log(data);
					var obj = eval('(' + data + ')');
					//alert(obj.msg);
					tip(obj.msg);
					$('#roleUserListLB').datagrid('reload');
				}
			);		
	    }
	});
	
}

</script>


