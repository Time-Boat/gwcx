<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

	<head>
	  <meta charset="utf-8" />
	  <meta http-equiv="X-UA-Compatible" content="chrome=1">
	  <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
	  <style type="text/css">
	    body,html,#container{
	      height: 50%;
	      margin: 0px;
	    }
	  </style>
	  <title>快速入门</title>
	</head>
    
    <t:base type="jquery,easyui,tools,DatePicker"></t:base>
	<div id="container" tabindex="0"></div>
	<div class="easyui-layout" fit="true" >
	  <div region="center" style="padding-bottom:0px;border:10px">
	  <t:datagrid name="openCityList" title="业务开通城市" actionUrl="mapStationController.do?datagrid" idField="id" fit="true" style="height:100px" >
	   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
	   <t:dgCol title="省名称" field="provinceName" width="120"></t:dgCol>
	   <t:dgCol title="城市名称" field="cityName" width="120"></t:dgCol>
	   <t:dgCol title="城市开通业务id" field="cityBusiness" width="120"></t:dgCol>
	   <t:dgCol title="创建人" field="createPeople" width="120"></t:dgCol>
	   <t:dgCol title="城市开通状态" field="status" width="120" dictionary="openCity" ></t:dgCol>
	   <t:dgCol title="备注" field="remark"   width="120"></t:dgCol>
	   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
	   <t:dgDelOpt title="删除" url="openCityController.do?del&id={id}" />
	   <t:dgToolBar title="录入" icon="icon-add" url="openCityController.do?addorupdate" funname="add"></t:dgToolBar>
	   <t:dgToolBar title="编辑" icon="icon-edit" url="openCityController.do?addorupdate" funname="update"></t:dgToolBar>
	   <t:dgToolBar title="查看" icon="icon-search" url="openCityController.do?addorupdate" funname="detail"></t:dgToolBar>
	  </t:datagrid>
	  </div>
	</div>
	
    <script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=b911428c1074ac0db34529ec951bf123" ></script>
    <script type="text/javascript" >
        var map = new AMap.Map('container',{
            resizeEnable: true,
            zoom: 10,
            center: [116.480983, 40.0958]
        });
        
        AMap.plugin(['AMap.ToolBar','AMap.AdvancedInfoWindow'],function(){
            //创建并添加工具条控件
            var toolBar = new AMap.ToolBar();
            map.addControl(toolBar);
            //创建高级信息窗体并在指定位置打开
            var infowindow = new AMap.AdvancedInfoWindow({
              content: '<div class="info-title">高德地图</div><div class="info-content">'+
                    '<img src="http://webapi.amap.com/images/amap.jpg">'+
                    '高德是中国领先的数字地图内容、导航和位置服务解决方案提供商。<br>'+
                    '<a target="_blank" href="http://mobile.amap.com/">点击下载高德地图</a></div>',
              offset: new AMap.Pixel(0, -30)
            });
            infowindow.open(map,[116.480983, 39.989628]);
        })
    </script>