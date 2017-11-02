<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="plug-in/tools/popup/userSelect.js"></script>
<script src="plug-in/tools/popup/departSelect.js"></script>
<div class="easyui-layout" fit="true">
<div region="center"  style="padding:0px;border:0px">
	<t:datagrid name="busStopInfoList" title="站点信息管理" autoLoadData="true" actionUrl="busStopInfoController.do?datagrid"  fitColumns="true"
	idField="id" fit="true" queryMode="group" >
	<t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	<t:dgCol title="站点名称" field="name" query="true"  align="center" width="80"></t:dgCol>
	<t:dgCol title="站点地址" field="stopLocation"  align="center" width="120"></t:dgCol>
	<t:dgCol title="站点类型" field="stationType" dictionary="sType" query="true" align="center" width="120"></t:dgCol>
	<%-- <t:dgCol title="状态" field="status" replace="启用_0,禁用_1" align="center" query="true"  width="60"></t:dgCol> --%>
	<t:dgCol title="创建时间" field="createTime" editor="datebox" formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group" align="center" width="100"></t:dgCol>
	<t:dgCol title="创建人" field="createPeople"  align="center" width="60"></t:dgCol>
	<t:dgCol title="所在城市" field="cityName" align="center" width="40"></t:dgCol>
	<t:dgCol title="备注" field="remark" align="center" width="120"></t:dgCol>
	<t:dgToolBar operationCode="add" title="添加站点" icon="icon-add" url="busStopInfoController.do?addorupdate" height="800" width="1200" funname="add"></t:dgToolBar>
	<t:dgToolBar operationCode="edit" title="修改站点" icon="icon-edit" url="busStopInfoController.do?addorupdate" width="1200" height="800" funname="update"></t:dgToolBar>
	<t:dgCol title="操作" field="opt" align="center" width="50"></t:dgCol>
	<t:dgFunOpt funname="stopStation(id)" title="下架" urlStyle="align:center" ></t:dgFunOpt>
	<%-- <t:dgToolBar title="批量下架" icon="icon-remove" url="busStopInfoController.do?doDeleteALLSelect" funname="deleteALLSelect"></t:dgToolBar> --%>
	<t:dgToolBar operationCode="detail" title="站点信息查看" icon="icon-search" url="busStopInfoController.do?addorupdate" funname="detail"></t:dgToolBar>
</t:datagrid>
</div>
</div>
<input type="hidden" value="${cityList}" id="citylie" />
<script type="text/javascript">
	$(document).ready(function(){
		$("input[name='createTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
		$("input[name='createTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
		$("input[name='birthday']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
	
	});
	
	$(function() {
		var json = $("#citylie").val();
		var a1 = '<span style="display:-moz-inline-box;display:inline-block; padding:10px 2px;"><span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;';
		var a2 = 'text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; "title="选择城市">选择城市：</span>';
		var a3 = '<select name="cityID" style="width: 150px">';
		var c1 = '<option value="">选择城市</option>';
		if(json.indexOf("cityID")>0){
			var obj = eval('(' + json + ')');
			for (var i = 0; i < obj.data.length; i++) {
				c1 += '<option value="'+obj.data[i].cityID+'">' + obj.data[i].cityName+ '</option>';
			}
		}
		var a4 = '</select></span>';
		$("#busStopInfoListForm").append(a1 + a2 + a3 + c1 + a4);
	});
	
	function stopStation(id){
		$.get(
			"busStopInfoController.do?checkStation&sId="+id,
			function(data){
				console.log(data);
				var obj = eval('(' + data + ')');
				if(obj.success){
					alertConfirm(id);
				}else{
					tip('已在线路中挂机过的站点不能被删除');
					return;
				}
			}
		);
	}
	
	function alertConfirm(id){
		$.dialog.confirm('确定要下架吗？',function(r){
		    if (r){
		    	$.post(
		    		"busStopInfoController.do?del&id="+id+"&deleteFlag=1",	
					function(data){
						//console.log(data);
						var obj = eval('(' + data + ')');
						//alert(obj.msg);
						tip(obj.msg);
						$('#busStopInfoList').datagrid('reload');
					}
				);
		    }
		});
	}
	
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
		if(isRestful!='undefined'&&isRestful){
			url += '/'+rowsData[0].id;
		}else{
			if(rowsData[0].id==null){
				rowsData[0].id = '';
			}
			url += '&id='+rowsData[0].id;
		}
		
		//检测站点是否已挂接过线路
		$.get(
			"busStopInfoController.do?checkStation&sId="+rowsData[0].id,
			function(data){
				console.log(data);
				var obj = eval('(' + data + ')');
				if(obj.success){
					createwindow(title,url,width,height);
				}else{
					tip('已在线路中挂机过的站点不能被删除');
					return;
				}
			}
		);
	}
</script>
