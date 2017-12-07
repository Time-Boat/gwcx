<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

	<head>
	  <meta charset="utf-8" />
	  <meta http-equiv="X-UA-Compatible" content="chrome=1">
	  <meta name="viewport" content="initial-scale=1.0, user-scalable=no, width=device-width">
	  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
	  
	  <style type="text/css">
	    
	    body {
            font-size: 12px;
        }
        
        .amap-marker .marker-route {
            position: absolute;
            width: 40px;
            height: 44px;
            color: #e90000;
            background: url(http://webapi.amap.com/theme/v1.3/images/newpc/poi-1.png) no-repeat;
            cursor: pointer;
        }
        
        .amap-marker .marker-marker-bus-from {
        	/* 起点图标   */
            background-position: -334px -180px;   
        }
        
        .amap-marker .amap-marker-background {
        	/* 终点图标   */
            background-position: -334px -135px;   
        }
        
        .pass-marker {
            position: absolute;
            width: 40px;
            height: 44px;
            background: url(http://webapi.amap.com/theme/v1.3/images/newpc/way_btn2.png) no-repeat;
            cursor: pointer;
            background-position: 0px -60px;   
        }
        
        #panel {
            position: fixed;
            background-color: white;
            max-height: 100%;
            overflow-y: auto;
            top: 36px;
            left: 58px;
            width: 280px;
            z-index: 9999;
        }
        
        .amap-lib-driving .planTitle {
		    position: relative;
		    padding: 2px 8px;
		    border-top: 1px solid #e6e6e6;
		    border-bottom: 1px solid #e6e6e6;
		    background: #fafafa;
		    font-size: 14px;
		    cursor: pointer;
		    border: 1px solid silver;
		}
		
	.tabtop13 td{
		background-color:#ffffff;
		height:25px;
		line-height:150%;
	}
	.font-center{ text-align:center}
	.btbg{background:#e9faff !important;}
	.btbg2{background:#f3f3f3 !important;}
	.titfont {
		
		font-family: 微软雅黑;
		font-size: 16px;
		font-weight: bold;
		color: #255e95;
		background: url(../images/ico3.gif) no-repeat 15px center;
		background-color:#e9faff;
	}

	  </style>
	  <title>站点信息</title>
	</head>
 <body style="overflow: hidden" onload="loadMapStation()" >
   	<input type="hidden" id="stations" name="stations" value="${stations}" />
   	<input type="hidden" id="lineAreaStation" name="lineAreaStation" value="${lineAreaStation}" />
    	
    <div id="container" tabindex="0" style="height:100%;" >
    	<div id="panel">
   				<table id="stationTable" width="100%" border="0" cellspacing="1" cellpadding="4" bgcolor="#cccccc" class="tabtop13" align="center">
				</table>
    	</div>
    </div>
	
    <!-- script必须放在body中。。 -->
    <script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=b911428c1074ac0db34529ec951bf123&plugin=AMap.Driving,AMap.Autocomplete,AMap.PlaceSearch" ></script>
    <script type="text/javascript" src="https://webapi.amap.com/demos/js/liteToolbar.js"></script>
    <script src="https://webapi.amap.com/js/marker.js"></script>
    <script src="https://webapi.amap.com/ui/1.0/main.js"></script>
    <script type="text/javascript" >
    
	    function loadTable(){
			var td = '<tr><td class="btbg font-center titfont" ></td><td class="btbg font-center titfont" >站点</td></tr>';
				td += '<tr><td class="btbg2 font-center">起点</td><td class="font-center">' + obj[0].name + '</td></tr>';
			for(var i=1;i<obj.length-1;i++){
				td += '<tr><td class="btbg2 font-center">途经点</td><td class="font-center">' + obj[i].name + '</td></tr>';
        	}
				td += '<tr><td class="btbg2 font-center">终点</td><td class="font-center">' + obj[obj.length-1].name + '</td></tr>';
			$('#stationTable').append(td);
		}
    
    	//地图对象
    	var map;
    	//窗体对象
    	var infoWindow = new AMap.InfoWindow({offset: new AMap.Pixel(5, -27)});
    	//构造路线导航类
    	var driving;
    	//管理marker的容器
    	var markerArr = [];
        
    	//绘制区域中所有点的经纬度
		var areaPoint = [];
		//区域对象
		var polygon;
		
		//站点对象
		var obj;
		
    	function loadMapStation(){
    		var stations = $('#stations').val();
    		obj = eval('(' + stations + ')');
        	
    		console.log(obj);
    		
    		if(initMap()){
    			tip('该线路没有站点');
    			return;
    		}
    		
    		var las = $('#lineAreaStation').val();
    		console.log(las);
        	if(las != ''){
        		initPolygon();
        	}else{
        		initDriving();
        	}
       	}
        
    	//初始化地图组件
    	function initMap(){
    		if(obj.length == 0){
    			map = new AMap.Map('container',{	
                    resizeEnable: true,             	//是否监控地图容器尺寸变化，默认值为false
                    zoom: 1,							//地图显示的缩放级别
                    center: ['116.397428', '39.90923'], //地图中心点
                	keyboardEnable: false  				//是否可以通过键盘来控制地图移动
                });
    			return true;
    		}
    		
        	map = new AMap.Map('container',{	
                resizeEnable: true,             //是否监控地图容器尺寸变化，默认值为false
                zoom: 12,						//地图显示的缩放级别
                center: [obj[0].x, obj[0].y],  	//地图中心点
            	keyboardEnable: false  			//是否可以通过键盘来控制地图移动
            });
        	return false;
    	}
    	
    	//初始化地图区域对象
    	function initPolygon(){
    		var data = $('#lineAreaStation').val();
    		var dxy = data.split(",");
    		var dx = dxy[0].split("&");
    		var dy = dxy[1].split("&");
    		
    		for(var i=0;i<dx.length;i++){
    			areaPoint[i] = [dx[i],dy[i]];
    		}
    		console.log(areaPoint);
    		polygon = new AMap.Polygon({
    	        path: areaPoint,//设置多边形边界路径
    	        strokeColor: "#FF33FF", //线颜色
    	        strokeOpacity: 0.2, //线透明度
    	        strokeWeight: 3,    //线宽
    	        fillColor: "#1791fc", //填充色
    	        fillOpacity: 0.35//填充透明度
    	    });
    	    polygon.setMap(map);
    	    
    	    var contentStyle;
    	  	//起点
        	if(obj[0].siteOrder == '0'){
        		contentStyle = '<div class="marker-route marker-marker-bus-from"></div>';
        	}else{//终点
        		contentStyle = '<div class="marker-route amap-marker-background"></div>';
        	}
        	
    	    createMarker(new AMap.LngLat(obj[0].x, obj[0].y), obj[0].name, contentStyle);
    	}
    	
    	//初始化路径组件
    	function initDriving(){
    		/* createMarkerForTP();
           	markerTP.setPosition(new AMap.LngLat(obj[0].x, obj[0].y));
           	
        	openInfoWin(obj[0].stopLocation, new AMap.LngLat(obj[0].x, obj[0].y)); */
        	
        	var drivingOption = {
       	        policy: AMap.DrivingPolicy.LEAST_DISTANCE,                 //最短距离模式。。。
       	     	hideMarkers: true,   //设置隐藏路径规划的起始点图标
        		showTraffic: false,  //设置是否显示实时路况信息
        		autoFitView: true,   //是否自动调整地图视野
       	        map: map
       	    	//,panel: 'panel'   	 //结果列表的HTML容器id或容器元素
       	    };
        	//起点坐标
        	var origin = new AMap.LngLat(obj[0].x, obj[0].y);
        	//终点坐标
        	var destination = new AMap.LngLat(obj[obj.length-1].x, obj[obj.length-1].y);
        	//途径坐标
        	var opts = {
       			waypoints:[]
        	};
        	
        	var icon = new AMap.Icon({            
                size: new AMap.Size(40, 50),  //图标大小
                image: "http://webapi.amap.com/theme/v1.3/images/newpc/way_btn2.png",
                imageOffset: new AMap.Pixel(0, -60)
            });
        	
        	//自定义图标样式(起点)
        	var qcontent = '<div class="marker-route marker-marker-bus-from"></div>';
        	//自定义图标样式(终点)
        	var zcontent = '<div class="marker-route amap-marker-background"></div>';
        	
        	//自定义图标(途经点)
        	var passContent = '<div class="pass-marker"></div>';
        	
        	console.log(opts);	
        	for(var i=1;i<obj.length-1;i++){
        		//opts.push(new AMap.LngLat(obj[i].x, obj[i].y));
        		opts.waypoints.push(new AMap.LngLat(obj[i].x, obj[i].y));
        		createMarker(new AMap.LngLat(obj[i].x, obj[i].y), obj[i].name, passContent);
        	}
        	
        	loadTable();
        	
        	console.log(opts);
        	
        	createMarker(new AMap.LngLat(obj[0].x, obj[0].y), obj[0].name, qcontent);
        	createMarker(new AMap.LngLat(obj[obj.length-1].x, obj[obj.length-1].y), obj[obj.length-1].name, zcontent);
        	
            //构造路线导航类
        	driving = new AMap.Driving(drivingOption); //构造驾车导航类
        	driving.search(
       			origin, 
       			destination,
       			opts, 
       			function(status, result){
       				console.log(status);
       				console.log(result);
       			}
        	);
			//afterLoad();
    	}
    	
       	//打开窗体
       	function openInfoWin(content, position) {
       		infoWindow.setContent('<p class="my-desc">' + content + '</p>');//点击以后窗口展示的内容
            infoWindow.open(map, position);
        }
        
       	//创建站点的marker对象
    	//var marker;
    	function createMarker(LngLat, content, style){
    		
    		//创建marker对象
   			var marker = new AMap.Marker({
           		title: "",
           		map: map,
           		//bubble: true,
           		//offset: new AMap.Pixel(-14, -35),
           		zIndex: 99999,
           		content: style,
           		topWhenClick: true,
       		});
    		
   			marker.setPosition(LngLat);
   			
   			marker.on('click', function(){
   				console.log(LngLat);
   				infoWindow.setContent('<p class="my-desc">' + content + '</p>');//点击以后窗口展示的内容
   	            infoWindow.open(map, marker.getPosition());
   			});
   			
   			markerArr.push(marker);
   			
    	}
    	
    	//创建机场或火车站点的marker对象
    	var markerTP;
    	function createMarkerForTP(){
    		//创建marker对象
   			markerTP = new AMap.Marker({
           		title: "",
           		map: map,
           		bubble: true,
           	 	content: '<div class="marker-route amap-marker-background"></div>'   //自定义点标记覆盖物内容
       		});
    		
    	}
    	
    	function markerClick(e){
    		var hs = e.target.extData;
            infoWindow.setContent(hs['address']);//点击以后窗口展示的内容
            infoWindow.open(map, e.target.getPosition());
            
            console.log(hs);
        }
        
    </script>
 </body>
