<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<div class="easyui-layout" fit="true">
<div region="center" style="padding:0px;border:0px">
<t:datagrid name="reaLineStationList" title="区域线路站点"
            actionUrl="areaLineController.do?areaStationDatagrid&areaLineId=${areaLineId}" fit="true" fitColumns="true" idField="id">
	<t:dgCol title="站点ID" field="stationId" hidden="true"></t:dgCol>
	<t:dgCol title="关联表ID" field="id" hidden="true" ></t:dgCol>
	<t:dgCol title="站点名称" field="name"   align="center"></t:dgCol>
	<t:dgCol title="位置" field="location" align="center" width="150" ></t:dgCol>
	<%-- <t:dgCol title="创建时间" field="createTime" align="center" ></t:dgCol> --%>
	<t:dgCol title="common.operation" field="opt"></t:dgCol>
	<t:dgFunOpt title="删除" funname="delAreaStation(stationId,id)" />
	<t:dgToolBar title="添加站点挂接" icon="icon-add" url="areaLineController.do?addOrUpdateStation&areaLineId=${areaLineId}" funname="add" width="1200" height="800"  ></t:dgToolBar><%-- operationCode="addSite" --%>
	<t:dgToolBar title="编辑" icon="icon-edit" url="areaLineController.do?addOrUpdateStation&areaLineId=${areaLineId}" funname="update" width="1200" height="800" ></t:dgToolBar>
</t:datagrid>
</div>
</div>
<script type="text/javascript">

/* function add(title,addurl,gname,width,height) {
	gridname=gname;
	createwindow(title, addurl,"1200px","800px");
} */

/* function update(title,url,id,width,height,isRestful) {
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
		url += '&id='+rowsData[0].id + '&line_busstopId=' + rowsData[0].line_busstopId + '&name=' + rowsData[0].name+'&siteOrder='+rowsData[0].siteOrder+'&arrivalTime='+rowsData[0].arrivalTime;
	}
	createwindow(title,url,width,height);
}
*/
function delAreaStation(stationId,id){
	$.dialog.confirm('确定要删除该记录吗？',function(r){
	    if (r){
	    	$.post(
	    		"areaLineController.do?delAreaStation",	
				{'id':id,'stationId':stationId},
				function(data){
					//console.log(data);
					var obj = eval('(' + data + ')');
					//alert(obj.msg);
					tip(obj.msg);
					$('#reaLineStationList').datagrid('reload');
				}
			);
	    }
	});
}

</script>


