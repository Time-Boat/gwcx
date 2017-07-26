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
        
	  </style>
	  <title>站点信息</title>
	</head>
 <body style="overflow-y: hidden" scroll="no" onload="loadMapStation()" >
 
    <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" styleClass="form_head" action="areaLineController.do?saveAreaStation">
    	<input type="hidden" id="areaLineId" name="areaLineId" value="${areaLineId}" />
    	<input type="hidden" id="slId" name="slId" value="${asLine.id}" />
    	<input type="hidden" id="stationId" name="stationId" value="${aStation.id}" />
    	<input type="hidden" id="lineCity" name="lineCity" value="${lineCity}" />
    	
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
    	</div>
    </div>
    <!-- script必须放在body中。。 -->
    <script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=b911428c1074ac0db34529ec951bf123" ></script>
    <script type="text/javascript" src="https://webapi.amap.com/demos/js/liteToolbar.js"></script>
    <script src="https://webapi.amap.com/js/marker.js"></script>
    <script src="https://webapi.amap.com/ui/1.0/main.js"></script>
    <script type="text/javascript" >
    
    	//地图对象
    	var map;
    	
    	function loadMapStation(){
    		var asx = $('#areaStationX').val();
        	var asy = $('#areaStationY').val();
        	
        	if(asx != null && asx != '' && asy != null && asy != ''){
        		map = new AMap.Map('container',{	
                    resizeEnable: true,             //是否监控地图容器尺寸变化，默认值为false
                    zoom: 10,						//地图显示的缩放级别
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
    		                    zoom: 12,						//地图显示的缩放级别
    		                    center: [cityX, cityY],  //地图中心点
    		                	keyboardEnable: false  			//是否可以通过键盘来控制地图移动
    		                });
    						afterLoad();
    					}
    				});
            	}
        		
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
        	    
        	    AMapUI.loadUI(['overlay/SimpleInfoWindow'], function(SimpleInfoWindow) {
        			
        	        var infoWindow = new SimpleInfoWindow({
        	            //基点指向marker的头部位置
        	            offset: new AMap.Pixel(0, -31)
        	        });
        	        
        	      	//点击事件
        		    map.on('click', function(e) {
        		    	marker.setPosition(e.lnglat);
        		    	openInfoWin();
        		    	//var lnglatXY=[116.396574, 39.992706];//地图上所标点的坐标
        	    		geocoder.getAddress(e.lnglat, function(status, result) {
        	    		    if (status === 'complete' && result.info === 'OK') {
        	    		       //获得了有效的地址信息:
        	    		       //即，result.regeocode.formattedAddress
        	    		       console.log(result);
        	    		       infoWindow.setInfoTitle('<strong>' + result.regeocode.addressComponent.province + ' ' + result.regeocode.addressComponent.district + '</strong>');
        	    		       infoWindow.setInfoBody('<p class="my-desc"><strong>详细地址:</strong> <br/>' + result.regeocode.formattedAddress + '</p>');
        	    		       infoWindow.setPosition(e.lnglat);
        	    		       
        	    		       //给表单赋值
        	    		       $('#areaStationX').val(e.lnglat.getLng());
        	    		       $('#areaStationY').val(e.lnglat.getLat());
        	    		       $('#location').val(result.regeocode.formattedAddress);
        	    		    }else{
        	    		       infoWindow.setInfoTitle("获取地址失败");
        		    		   infoWindow.setInfoBody("");
        	    		    }
        	    		});
        		        //console.log('您在[ '+e.lnglat.getLng()+','+e.lnglat.getLat()+' ]的位置点击了地图！');
        		    });
        	      	
        	        function openInfoWin() {
        	            infoWindow.open(map, marker.getPosition());
        	        }
        			
        	        //marker 点击时打开
        	        AMap.event.addListener(marker, 'click', function() {
        	            openInfoWin();
        	        });
        			
        	        //openInfoWin();
        	    });
        	    
        	    var windowsArr = [];
	    	    AMap.plugin(['AMap.Autocomplete','AMap.PlaceSearch'],function(){
					var autoOptions = {
						city: city, //城市，默认全国
						input: "keyword"//使用联想输入的input的id
					};
					autocomplete= new AMap.Autocomplete(autoOptions);
					var placeSearch = new AMap.PlaceSearch({
						city:city,
						map:map
					})
					AMap.event.addListener(autocomplete, "select", function(e){
						//TODO 针对选中的poi实现自己的功能
						placeSearch.search(e.poi.name)
						console.log(e);
					});
	    	    });
        	}
       	}
    	
    	//如果已经创建marker，就不再创建   
    	var isFirst = false;
    	var marker;
    	function createMarker(){
    		//创建marker对象
    		if(!isFirst){
    			isFirst = true;
    			marker = new AMap.Marker({
            		title: "点击测试",
            		map: map,
            		bubble: true
        		});
    		}
	      	
        	//map.remove(marker);
    	}
    	
	    </script>
    </script>
    
    
    
    
    
    
    
    
    
 </body>
