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
            background:url("resource/***.gif") no-repeat scroll right center transparent; 
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
        
        #keyword {
	        position: absolute;
	        z-index: 9999;
	        top: 50px;
	        right: 30px;
	        width: 300px;
	        background:url(plug-in/easyui/themes/metrole/icons/le_search.png) no-repeat scroll right center #fff;
	    }
        
        #panel {
            position: fixed;
            background-color: white;
            max-height: 90%;
            overflow-y: auto;
            top: 10px;
            right: 10px;
            width: 280px;
            z-index: 9999;
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
    <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" styleClass="form_head" action="areaLineController.do?saveAreaStation">
    	<input type="hidden" id="areaLineId" name="areaLineId" value="${areaLineId}" />
    	<input type="hidden" id="slId" name="slId" value="${asLine.id}" />
    	<input type="hidden" id="stationId" name="stationId" value="${aStation.id}" />
    	<input type="hidden" id="lineCity" name="lineCity" value="${lineCity}" />
    	<input type="hidden" id="stationInfo" name="stationInfo" value="${stationInfo}" />
    	
    	<table style="width: 100%;height: 100%" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="center">
					<label class="Validform_label">
						站点名称:
					</label>
				</td>
				<td class="value">
					<input class="inputxt" style="height: 30px;" id="name" name="name" ignore="ignore"
						   value="${aStation.name}">
					<span class="Validform_checktip"></span>
				</td>
				<td align="center">
					<label class="Validform_label">
						到该站点价格:
					</label>
				</td>
				<td class="value">
					<input class="inputxt" style="height: 30px;" id="price" name="price" ignore="ignore"
						   value="${asLine.price}"> 元/人
					<span class="Validform_checktip"></span>
				</td>
				<td align="center">
					<label class="Validform_label">
						所需时长:
					</label>
				</td>
				<td class="value">
					<input class="inputxt" style="height: 30px;" id="duration" name="duration" ignore="ignore"
						   value="${asLine.duration}"> 分钟
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
					<input class="inputxt" readonly="readonly" style="height: 30px;" id="location" name="location" ignore="ignore"
						   value="${aStation.location}">
					<span class="Validform_checktip"></span>
				</td>
				<td align="center">
					<label class="Validform_label">
						经度:
					</label>
				</td>
				<td class="value">
					<input class="inputxt" readonly="readonly" style="height: 30px;" id="areaStationX" name="areaStationX" ignore="ignore"
						   value="${aStation.areaStationX}">
					<span class="Validform_checktip"></span>
				</td>
				<td align="center">
					<label class="Validform_label">
						纬度:
					</label>
				</td>
				<td class="value">
					<input class="inputxt" readonly="readonly" style="height: 30px;" id="areaStationY" name="areaStationY" ignore="ignore"
						   value="${aStation.areaStationY}">
					<span class="Validform_checktip"></span>
				</td>
			</tr>
		</table>
    </t:formvalid>
    <div id="container" tabindex="0" style="height:80%;" >
    	<div >
	    	<input type="text" id="keyword" placeholder="请输入关键字" name="keyword" />
	    	<div id="panel"></div>
    	</div>
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
    		/* 
    		var asx = $('#areaStationX').val();
        	var asy = $('#areaStationY').val();
        	
        	if(asx != null && asx != '' && asy != null && asy != ''){
        		map = new AMap.Map('container',{	
                    resizeEnable: true,             //是否监控地图容器尺寸变化，默认值为false
                    zoom: 12,						//地图显示的缩放级别
                    center: [asx, asy],  //地图中心点
                	keyboardEnable: false  			//是否可以通过键盘来控制地图移动
                });
        		afterLoad()
        	}else{
        		
        		var city = $('#lineCity').val();
            	var cityX;
            	var cityY;
            	
            	if(city != null){
            		//获取区域信息
    		  		$.ajax({
    					url : "http://restapi.amap.com/v3/config/district?keywords=" + city + "&subdistrict=1&showbiz=false&key=ee95e52bf08006f63fd29bcfbcf21df0",
    					type : "get",
    					success : function(data) {
    						var arr = data.districts[0].center.split(",");
    						cityX = arr[0];
    						cityY = arr[1];
    						console.log(cityX+"----"+cityY);
    						map = new AMap.Map('container',{	
    		                    resizeEnable: true,             //是否监控地图容器尺寸变化，默认值为false
    		                    zoom: 13,						//地图显示的缩放级别
    		                    center: [cityX, cityY],  //地图中心点
    		                	keyboardEnable: false  			//是否可以通过键盘来控制地图移动
    		                });
    						afterLoad();
    					}
    				});
            	} 
        	}*/
        	
        	var json = $('#stationInfo').val();
        	console.log("aaaa:" + json);
        	var obj = eval('(' + json + ')');
        	
        	var asx = $('#areaStationX').val();
        	var asy = $('#areaStationY').val();
        	
        	var location = $('#location').val();
        	
        	//如果是新增操作，就定位到机场站点或火车站点，如果是修改操作，就定位到被修改的站点位置
        	if(asx != null && asx != '' && asy != null && asy != ''){
        	}else{
        		if(json != null && json != ""){
            		//var obj = $.parseJSON(json);
            		console.log("stationX" + obj.stationX);
            		asx = obj.stationX;
            		asy = obj.stationY;
            		location = obj.stationName;
            	}
        		
        		if(asx == null || asx == '' || asy == null || asy == ''){
            		asx = 116.397497;
            		asy = 39.908698;
            		location = "北京天安门"
            	}
        	}
        	map = new AMap.Map('container',{	
                resizeEnable: true,             //是否监控地图容器尺寸变化，默认值为false
                zoom: 12,						//地图显示的缩放级别
                center: [asx, asy],  //地图中心点
            	keyboardEnable: false  			//是否可以通过键盘来控制地图移动
            });
        	//创建机场或火车站点
        	createMarkerForTP(obj.stationX, obj.stationY);
        	openInfoWin(location, new AMap.LngLat(asx,asy));
			afterLoad();
       	}
        
       	//打开窗体
       	function openInfoWin(content, position) {
       		infoWindow.setContent('<p class="my-desc">' + content + '</p>');//点击以后窗口展示的内容
            infoWindow.open(map, position);
        }
        	
       	function afterLoad(){
       		//经纬度获取详细地址
       	    AMap.service('AMap.Geocoder',function(){//回调函数
       	        //实例化Geocoder
       	        geocoder = new AMap.Geocoder({
       	            city: '010'//城市，默认：“全国”
       	        });
       	        //TODO: 使用geocoder 对象完成相关功能
       	    })
       	    
	    	createMarker();
       		
	      	//点击事件
		    map.on('click', function(e) {
		    	createMarker();
		    	marker.setPosition(e.lnglat);
		    	openInfoWin("正在获取位置信息...", e.lnglat);
		    	//var lnglatXY=[116.396574, 39.992706];//地图上所标点的坐标
		    	
		    	if(typeof(driving) != "undefined"){
		    		driving.clear();
	    		}
		    	
		    	geocoder.getAddress(e.lnglat, function(status, result) {
	    		    if (status === 'complete' && result.info === 'OK') {
						//获得了有效的地址信息:
						//即，result.regeocode.formattedAddress
						console.log(result);
						
						openInfoWin(result.regeocode.formattedAddress, e.lnglat);
						         
						//给表单赋值
						$('#areaStationX').val(e.lnglat.getLng());
						$('#areaStationY').val(e.lnglat.getLat());
						$('#location').val(result.regeocode.formattedAddress);
	    		    }else{
	    		    	openInfoWin("获取地址失败", marker.getPosition());
	    		    }
	    		});
	    		drawLine();
		        //console.log('您在[ '+e.lnglat.getLng()+','+e.lnglat.getLat()+' ]的位置点击了地图！');
		    });
      		
	      	//插件在script的url头拼接了
	        //marker 点击时打开
	        AMap.event.addListener(marker, 'click', function() {
	        	openInfoWin("正在获取位置信息...", marker.getPosition());
	        });
	      	
	        var city = $('#lineCity').val();
	        console.log(city);
			var autoOptions = {
				city: city, //城市，默认全国
				input: "keyword"//使用联想输入的input的id
			};
			
			autocomplete= new AMap.Autocomplete(autoOptions);
			var placeSearch = new AMap.PlaceSearch({
				city: city,
				map: map
			});
			
			AMap.event.addListener(autocomplete, "select", function(e){
				//TODO 针对选中的poi实现自己的功能
				//placeSearch.search(e.poi.name)
				console.log(e);
				placeSearch.setCity(e.poi.adcode);
                   if (e.poi && e.poi.location) {
                        map.setZoom(10);
                        map.setCenter(e.poi.location);
                    }
                 //选择下拉列表中的一条信息时触发的方法
                 placeSearch.search(e.poi.name, function(status, result) {
                    if (status === 'complete' && result.info === 'OK') { 
                    	console.log(result);
                    }
                });  //关键字查询查询
                
                //切换点击marker时触发
                AMap.event.addListener(placeSearch, 'selectChanged', function(results) {
                     //获取当前选中的结果数据
                        //infoWindow.setContent(hs['address']);//点击以后窗口展示的内容
                        //infoWindow.open(map, e.target.getPosition());
                             
                     console.log(results.selected.data);
                        //给表单赋值
              	         $('#location').val(results.selected.data.name);
              	         $('#areaStationX').val(results.selected.data.location.lng);
              	         $('#areaStationY').val(results.selected.data.location.lat);
              	      	 map.remove(marker);
                });
			});
    	   	//画线路
    	    //drawLine();
       	}
       	
       	//创建站点的marker对象
    	var marker;
    	function createMarker(){
    		if(typeof(marker) != "undefined"){
    			map.remove(marker);
    		}
    		
    		//创建marker对象
   			marker = new AMap.Marker({
           		title: "点击测试",
           		map: map,
           		bubble: true,
           	 	content: '<div class="marker-route marker-marker-bus-from "></div>'   //自定义点标记覆盖物内容
       		});
    	}
    	
    	//创建机场或火车站点的marker对象
    	var markerTP;
    	function createMarkerForTP(x,y){
    		console.log("x:" + x + "----- y:" + y);
    		//创建marker对象
   			markerTP = new AMap.Marker({
           		title: "",
           	 	center: [x, y],
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
            
            //给表单赋值
	         $('#areaStationX').val(hs['getLat']);
	         $('#areaStationY').val(hs['getLng']);
	         $('#location').val(hs['address']);
             
            console.log(hs);
        }
    	
        function drawLine(){
        	
        	var drivingOption = {
       	        policy: AMap.DrivingPolicy.LEAST_FEE,                 //最经济模式。。。
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
        	driving.search(markerTP.getPosition(), marker.getPosition());
        	
        	//切换点击marker时触发
            AMap.event.addListener(driving, 'complete', function(result) {
                 console.log(result);
                 if(result.info == 'OK'){
                	 var route = result.routes[0];
                	 //公里数
                	 var distance = route.distance;
                	 //所需时间
                	 var time = route.time;
                 }
            });
        	
            //setTimeout("a()", 5000);
        }
        
        /* function a(){
        	$('#panel dl').css("display","none");
        } */
        
    </script>
    
    
    
    
    
    
    
    
    
 </body>
