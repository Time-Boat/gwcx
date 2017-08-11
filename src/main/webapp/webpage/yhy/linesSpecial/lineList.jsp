<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="plug-in/tools/popup/userSelect.js"></script>
<script src="plug-in/tools/popup/departSelect.js"></script>
<div class="easyui-layout" fit="true">
<div region="center" style="padding:0px;border:0px">
<t:datagrid name="lineList2" title="线路管理" autoLoadData="true" actionUrl="lineInfoSpecializedController.do?datagrid&linetype=2"  fitColumns="true"
	idField="id" fit="true" queryMode="group" >
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="线路名称" field="name" query="true" frozenColumn="true" align="center" width="150"></t:dgCol>
	<t:dgCol title="起点地址" field="startLocation" query="true" align="center" width="120"></t:dgCol>
	<t:dgCol title="终点地址" field="endLocation" query="true" align="center" width="120"></t:dgCol>
	
	<t:dgCol title="线路类型" field="type" replace="公务班车_0,接送包车_1,接机_2,送机 _3,接火车_4,送火车_5"  align="center" width="60"></t:dgCol>
	<t:dgCol title="出车时间段" field="dispath" dictionary="dispathtime" align="center" width="90"></t:dgCol>
	<%--
	<t:dgCol title="线路图片" field="imageurl"  align="center" width="60"></t:dgCol>
	<t:dgCol title="线路状态" field="status" replace="启用_0,未启用_1"  align="center" width="60"></t:dgCol> --%>
	<t:dgCol title="创建时间" field="createTime" editor="datebox" formatter="yyyy-MM-dd hh:mm:ss"   align="center" width="150"></t:dgCol>
	<t:dgCol title="创建人" field="createPeople"  align="center" width="60"></t:dgCol>
	<t:dgCol title="线路状态" field="status" dictionary="lineStatus"  align="center" width="60"></t:dgCol>
	<%-- <t:dgCol title="发车时间" field="lstartTime" editor="datebox" formatter="hh:mm:ss" query="true" queryMode="group" align="center" width="120"></t:dgCol>
	<t:dgCol title="预计到达时间" field="lendTime" editor="datebox" formatter="hh:mm:ss" query="true" queryMode="group"  align="center" width="120"></t:dgCol>
	 --%>
	 <t:dgCol title="线路时长（分）" field="lineTimes"  align="center" width="80"></t:dgCol>
	 
	 <t:dgCol title="线路定价(元/人)" field="price"  align="center" width="80"></t:dgCol>
	<t:dgCol title="所在城市" field="city"  align="center"></t:dgCol>
	 <t:dgCol title="线路备注" field="remark"  align="center" width="60"></t:dgCol>
	<t:dgCol title="操作" field="opt" width="100"></t:dgCol>

	<t:dgToolBar operationCode="addLine" title="添加线路" icon="icon-add" url="lineInfoSpecializedController.do?addorupdate" funname="add" height="500"></t:dgToolBar>
	<t:dgToolBar operationCode="editLine" title="修改线路" icon="icon-edit" url="lineInfoSpecializedController.do?addorupdate" funname="update" height="500" ></t:dgToolBar>
	<%-- <t:dgToolBar operationCode="edit" title="站点挂接" icon="icon-edit" url="lineInfoController.do?addBusStop" funname="update"></t:dgToolBar>
	 --%>
	<%-- <t:dgDelOpt title="下架" url="lineInfoController.do?del&id={id}&deleteFlag=1" /> --%>
	
	<t:dgFunOpt funname="addBusStop(id,name)" title="站点管理"></t:dgFunOpt>
	<t:dgFunOpt funname="lookLine(id,name)" title="查看"></t:dgFunOpt>
</t:datagrid> </div>
</div>
<input type="hidden" value="${cityList}" id="citylie" />
<div region="east" style="width: 490px;" split="true">
<div tools="#tt" class="easyui-panel" title="站点管理" style="padding: 10px;" fit="true" border="false" id="function-panelAddBusStop"></div>
</div>
<div id="tt"></div>
</div>
<script type="text/javascript">
	$(document).ready(function(){
		$("input[name='createTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
		$("input[name='createTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
		$("input[name='birthday']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
		$("input[name='lstartTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
		$("input[name='lstartTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
		$("input[name='lendTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
		$("input[name='lendTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
	});
	function addBusStop(id,name) {
		$("#function-panelAddBusStop").panel(
			{
				title :'线路名称：'+name,
				href:"lineInfoController.do?addBusStop&lineInfoId="+id+"&lineType=2"   //lineType=2为接送机跳转到的站点挂接界面
			}
		);
	}
	$(function() {
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
		$("#lineList2Form").append(a1 + a2 + a3 + c1 + a4);
	});
	
	
	function lookLine(id, name) {
		createdetailwindow(name, "lineInfoSpecializedController.do?lineMap&id=" + id,"1200px","800px");
	}
</script>
