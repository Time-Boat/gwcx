<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

	<head>
	  <meta charset="utf-8" />
	  <meta http-equiv="X-UA-Compatible" content="chrome=1">
	  <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
	  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
	  
	  <style type="text/css">
	    /* body,html,#container{
	      height: 100%;
	      margin: 0px;
	    } */
	    
	    .form_head{
	    	height:20%;
	    }
	    
	    body {
            font-size: 12px;
        }
        #tip {
            background-color: #ddf;
            color: #333;
            border: 1px solid silver;
            box-shadow: 3px 4px 3px 0px silver;
            position: absolute;
            top: 10px;
            right: 10px;
            border-radius: 5px;
            overflow: hidden;
            line-height: 20px;
        }
        #tip input[type="text"] {
            height: 25px;
            border: 0;
            padding-left: 5px;
            width: 280px;
            border-radius: 3px;
            outline: none;
        }
        
	  </style>
	  <title>快速入门</title>
	</head>
 <body style="overflow-y: hidden" scroll="no">
 
    <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" styleClass="form_head" action="areaLineController.do?save">
    	<table style="width: 100%;height: 100%" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="center">
					<label class="Validform_label">
						站点名称:
					</label>
				</td>
				<td class="value">
					<input class="inputxt" style="height: 30px;" id="name" name="name" ignore="ignore"
						   value="${areaLinePage.name}">
					<span class="Validform_checktip"></span>
				</td>
				<td align="center">
					<label class="Validform_label">
						到该站点价格:
					</label>
				</td>
				<td class="value">
					<input class="inputxt" style="height: 30px;" id="name" name="name" ignore="ignore"
						   value="${areaLinePage.name}"> 元/人
					<span class="Validform_checktip"></span>
				</td>
				<td align="center">
					<label class="Validform_label">
						所需时长:
					</label>
				</td>
				<td class="value">
					<input class="inputxt" style="height: 30px;" id="name" name="name" ignore="ignore"
						   value="${areaLinePage.name}"> 分钟
					<span class="Validform_checktip"></span>
				</td>
			</tr>
			<tr>
				<td align="center">
					<label class="Validform_label">
						所选地址:
					</label>
				</td>
				<td class="value">
					<input class="inputxt" style="height: 30px;" id="name" name="name" ignore="ignore"
						   value="${areaLinePage.name}">
					<span class="Validform_checktip"></span>
				</td>
				<td align="center">
					<label class="Validform_label">
						经度:
					</label>
				</td>
				<td class="value">
					<input class="inputxt" style="height: 30px;" id="name" name="name" ignore="ignore"
						   value="${areaLinePage.name}"> 
					<span class="Validform_checktip"></span>
				</td>
				<td align="center">
					<label class="Validform_label">
						纬度:
					</label>
				</td>
				<td class="value">
					<input class="inputxt" style="height: 30px;" id="name" name="name" ignore="ignore"
						   value="${areaLinePage.name}"> 
					<span class="Validform_checktip"></span>
				</td>
			</tr>
		</table>
    </t:formvalid>
    <div id="container" tabindex="0" style="height:80%;" >
    	<input type="text" id="keyword" placeholder="请输入关键字" style="z-index: 9999;position:absolute;margin:10px;" name="keyword" />
    </div>
    <!-- script必须放在body中。。 -->
    <script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=b911428c1074ac0db34529ec951bf123" ></script>
    <script type="text/javascript" src="https://webapi.amap.com/demos/js/liteToolbar.js"></script>
    <script type="text/javascript" >
        var map = new AMap.Map('container',{
            resizeEnable: true,             //是否监控地图容器尺寸变化，默认值为false
            zoom: 10,						//地图显示的缩放级别
            center: [116.480983, 40.0958],  //地图中心点
        	keyboardEnable: false  			//是否可以通过键盘来控制地图移动
        });
        
        /* AMap.plugin(['AMap.ToolBar','AMap.AdvancedInfoWindow'],function(){
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
        }) */
        
        //搜索框
        var windowsArr = [];
	    var marker = [];
	    AMap.plugin(['AMap.Autocomplete','AMap.PlaceSearch'],function(){
	      var autoOptions = {
	        city: "北京", //城市，默认全国
	        input: "keyword"//使用联想输入的input的id
	      };
	      autocomplete= new AMap.Autocomplete(autoOptions);
	      var placeSearch = new AMap.PlaceSearch({
	            city:'北京',
	            map:map
	      })
	      AMap.event.addListener(autocomplete, "select", function(e){
	         //TODO 针对选中的poi实现自己的功能
	         placeSearch.search(e.poi.name)
	      });
	    });
    </script>
    
    
    
    
    
    
    
    
    
 </body>
