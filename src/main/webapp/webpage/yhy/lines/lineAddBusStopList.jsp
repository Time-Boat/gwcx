<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
<div region="center" style="padding:0px;border:0px">
<t:datagrid name="roleUserList" title="已挂接的线路站点"
            actionUrl="lineInfoController.do?busStopByLineDatagrid&lineInfoId=${lineInfoId}" fit="true" fitColumns="true" idField="id">
	<t:dgCol title="站点ID" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="关联表ID" field="line_busstopId" hidden="true" ></t:dgCol>
	<t:dgCol title="站点名称" field="name"   align="center"></t:dgCol>
	<t:dgCol title="站点序号" field="siteOrder" align="center" ></t:dgCol>
	<t:dgCol title="预计站点到站时间" field="arrivalTime" align="center" ></t:dgCol>
	<t:dgCol title="common.operation" field="opt"></t:dgCol>
	<t:dgFunOpt title="删除关联站点" funname="delZd(id,line_busstopId)" />
	<t:dgToolBar title="添加站点挂接"  icon="icon-add" url="lineInfoController.do?busStopInfoList&lineInfoId=${lineInfoId}&lineType=${lineType}" funname="add"></t:dgToolBar>
	<t:dgToolBar title="站点时间、序号编辑"  icon="icon-edit" url="lineInfoController.do?updateBusStopOrder" funname="update"></t:dgToolBar>

</t:datagrid>
</div>
</div>
<script type="text/javascript">

function update(title,url,id,width,height,isRestful) {
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
					$('#roleUserList').datagrid('reload');
				}
			);		
	    }
	});
	
}

</script>


