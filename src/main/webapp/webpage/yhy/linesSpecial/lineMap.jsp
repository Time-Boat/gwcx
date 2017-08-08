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
            /* 终点图标  */
            /* background-position: -334px -135px; */
        }
        
        .amap-marker .amap-marker-background {
        	/* 终点图标   */
            background-position: -334px -135px;   
        }
        
	  </style>
	  <title>站点信息</title>
	</head>
 <body style="overflow-y: hidden" scroll="no" onload="loadMapStation()" >
   	<input type="hidden" id="areaLineId" name="areaLineId" value="${areaLineId}" />
   	<input type="hidden" id="slId" name="slId" value="${asLine.id}" />
   	<input type="hidden" id="stationId" name="stationId" value="${aStation.id}" />
   	<input type="hidden" id="lineCity" name="lineCity" value="${lineCity}" />
   	<input type="hidden" id="stationInfo" name="stationInfo" value="${stationInfo}" />
   	<input type="hidden" id="cityCode" name="cityCode" value="${cityCode}" />
    	
    <div id="container" tabindex="0" style="height:80%;" >
    </div>
	
    <!-- script必须放在body中。。 -->
    <script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=b911428c1074ac0db34529ec951bf123&plugin=AMap.Driving,AMap.Autocomplete,AMap.PlaceSearch" ></script>
    <script type="text/javascript" src="https://webapi.amap.com/demos/js/liteToolbar.js"></script>
    <script src="https://webapi.amap.com/js/marker.js"></script>
    <script src="https://webapi.amap.com/ui/1.0/main.js"></script>
    <script type="text/javascript" >
    
    	//地图对象
    	var map;
    	//窗体对象
    	var infoWindow = new AMap.InfoWindow({offset: new AMap.Pixel(0, -30)});
    	//构造路线导航类
    	var driving;
    	
        
    	function loadMapStation(){
        	var json = $('#stationInfo').val();
        	console.log("aaaa:" + json);
        	var obj = eval('(' + json + ')');
        	
        	var asx = $('#areaStationX').val();
        	var asy = $('#areaStationY').val();
        	
        	var location = $('#location').val();
        	
        	console.log("objX  " + obj.stationX + "     objY" + obj.stationY);
           	console.log("areaStationX  " + asx + "     areaStationY" + asy);
           	
           	var b = false;
        	//如果是新增操作，就定位到机场站点或火车站点，如果是修改操作，就定位到被修改的站点位置
        	if(asx != null && asx != '' && asy != null && asy != ''){
        		b = true;
        	}else{
        		asx = obj.stationX;
        		asy = obj.stationY;
        		location = obj.stationName;
        	}
        	
        	map = new AMap.Map('container',{	
                resizeEnable: true,             //是否监控地图容器尺寸变化，默认值为false
                zoom: 12,						//地图显示的缩放级别
                center: [asx, asy],  //地图中心点
            	keyboardEnable: false  			//是否可以通过键盘来控制地图移动
            });
        	
           	createMarkerForTP();
           	markerTP.setPosition(new AMap.LngLat(obj.stationX, obj.stationY));
           	
           	console.log('--' + b + '--');
           	if(b){
	           	createMarker();
	       	 	marker.setPosition(new AMap.LngLat(asx,asy));
	       	 	
	       	 	drawLine(new AMap.LngLat(asx,asy));
           	} 
       	 	
        	openInfoWin(location, new AMap.LngLat(asx,asy));
			afterLoad();
       	}
        
       	//打开窗体
       	function openInfoWin(content, position) {
       		infoWindow.setContent('<p class="my-desc">' + content + '</p>');//点击以后窗口展示的内容
            infoWindow.open(map, position);
        }
        
       	//创建站点的marker对象
    	var marker;
    	function createMarker(){
    		if(typeof(marker) != "undefined"){
    			map.remove(marker);
    		}
    		
    		var content;
    		if (isVisible) {
            	content = '<div class="marker-route marker-marker-bus-from"></div>';
            } else {
    			content = '<div class="marker-route amap-marker-background"></div>';
            }
    		
    		//创建marker对象
   			marker = new AMap.Marker({
           		title: "",
           		map: map,
           		bubble: true,
           		offset: new AMap.Pixel(-14, -35),
           		zIndex: 99999,
           		topWhenClick: true,
           	 	content: content   //自定义点标记覆盖物内容
       		});
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
    		
    		//markerTP.setPosition(new AMap.LngLat(x,y));
    	}
    	
    	function markerClick(e){
    		var hs = e.target.extData;
            infoWindow.setContent(hs['address']);//点击以后窗口展示的内容
            infoWindow.open(map, e.target.getPosition());
            
            console.log(hs);
        }
    	
        function drawLine(lnglat){
        	
        	var drivingOption = {
       	        policy: AMap.DrivingPolicy.LEAST_DISTANCE,                 //最短距离模式。。。
       	     	hideMarkers: true,   //设置隐藏路径规划的起始点图标
        		showTraffic: false,  //设置是否显示实时路况信息
        		autoFitView: true,   //是否自动调整地图视野
       	        map: map,
       	    	panel: 'panel'   	 //结果列表的HTML容器id或容器元素
       	    };
        	
            //构造路线导航类
        	driving = new AMap.Driving(drivingOption); //构造驾车导航类
        	
            // 根据起终点经纬度规划驾车导航路线
            //driving.search(new AMap.LngLat(116.379028, 39.865042), new AMap.LngLat(116.427281, 39.903719));
        	
        	if(isVisible){ 
				driving.search(lnglat, markerTP.getPosition());
			}else{
				driving.search(markerTP.getPosition(), lnglat);
			}
			
			/* clock = window.setInterval("computeInfo()",500); 
			
			completed = false; */
			
        	//切换点击marker时触发   (线路绘制成功时触发)
            AMap.event.addListener(driving, 'complete', function(result) {
            	 $('#panel dl').css("display","none");
                 //console.log(result);
                 if(result.info == 'OK'){
                	 var route = result.routes[0];
                	 //console.log(route);
                	 //公里数
                	 var distance = (route.distance/1000).toFixed(2);
                	 //所需时间
                	 var time = (route.time/60).toFixed(0);
                	 if(isVisible){
                		 console.log(2222);
                		 $('#durationGo').val(time);
                    	 $('#distanceGo').val(distance);
                	 }else{
                		 console.log(1111);
                		 $('#durationBack').val(time);
                    	 $('#distanceBack').val(distance);
                	 }
                	 /* if(completed){
                		 isVisible = !isVisible;
                		 completed = false;
                	 } 
                	 completed = true; */
                	 console.log(distance + " ----- " + time);
                 }
            });
        }
        
    </script>
 </body>
