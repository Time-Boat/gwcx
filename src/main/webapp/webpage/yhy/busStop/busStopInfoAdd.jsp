<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
        
	  </style>
	<title>新增验票员</title>

	</head>
<body style="overflow-y: hidden" scroll="no" onload="loadMapStation()" >
	<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" styleClass="form_head" action="busStopInfoController.do?save">
		<input id="id" name="id" type="hidden" value="${busStopInfo.id }">
		
		<table style="width: 100%;height: 100%" cellpadding="0" cellspacing="1" class="formtable">
			<tr>
				<td align="center">
					<label class="Validform_label">
						站点名称:
					</label>
				</td>
				<td class="value">
					<input class="inputxt" style="height: 30px;" id="name" name="name" ignore="ignore"
						   value="${busStopInfo.name }">
					<!-- <span class="Validform_checktip"></span> -->
				</td>
				<td align="center">
					<label class="Validform_label">站点类型: </label>
				</td>
				<td class="value">
					<t:dictSelect field="stationType" typeGroupCode="sType" hasLabel="false" defaultVal="${busStopInfo.stationType}" ></t:dictSelect>	
					<!-- <span class="Validform_checktip"></span> -->
				</td>
				<td align="center">
					<label class="Validform_label"> 选择线路城市: </label>
				</td>
				<td class="value">
					<select name="city" id="city" onchange="changeCity(this.value)" >
							<option value="">--请选择城市--</option>
							<c:forEach var="c" items="${cities}">
								<option value="${c.cityId}" <c:if test="${busStopInfo.cityId == c.cityId}" >selected="selected"</c:if>  >
									${c.cityName}
								</option>
							</c:forEach>
					</select> 
					<!-- <span class="Validform_checktip"></span> -->
				</td>
			</tr>
			<tr>
				<td align="center">
					<label class="Validform_label">
						所选地址:
					</label>
				</td>
				<td class="value">
					<input class="inputxt" readonly="readonly" style="height: 30px;" id="stopLocation" name="stopLocation" ignore="ignore"
						   value="${busStopInfo.stopLocation }">
					<!-- <span class="Validform_checktip"></span> -->
				</td>
				<td align="center">
					<label class="Validform_label">
						经度:
					</label>
				</td>
				<td class="value">
					<input class="inputxt" readonly="readonly" style="height: 30px;" id="x" name="x" ignore="ignore"
						   value="${busStopInfo.x}">
					<span class="Validform_checktip"></span>
				</td>
				<td align="center">
					<label class="Validform_label">
						纬度:
					</label>
				</td>
				<td class="value">
					<input class="inputxt" readonly="readonly" style="height: 30px;" id="y" name="y" ignore="ignore"
						   value="${busStopInfo.y}">
					<!-- <span class="Validform_checktip"></span> -->
				</td>
			</tr>
		</table>
		
		
	</t:formvalid>
	<t:authFilter name="formtableId"></t:authFilter>

	<div id="container" tabindex="0" style="height:80%" >
    	<div >
	    	<input type="text" id="keyword" placeholder="请输入关键字" name="keyword" />
	    	<div id="panel"></div>
    	</div>
    </div>
    <!-- script必须放在body中。。 -->
    <script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=b911428c1074ac0db34529ec951bf123&plugin=AMap.Driving" ></script>
    <script type="text/javascript" src="https://webapi.amap.com/demos/js/liteToolbar.js"></script>
    <script src="https://webapi.amap.com/js/marker.js"></script>
    <script src="https://webapi.amap.com/ui/1.0/main.js"></script>
    <script type="text/javascript" >
    
    	//跳转城市
    	function changeCity(value){
    		console.log(value);
    		var city = $("#city option:selected").text();
    		console.log(city.trim());
    		map.setCity(city.trim());
    		map.setZoom(10);
    	}
    
    	//地图对象
    	var map;
    	//窗体对象
    	var infoWindow = new AMap.InfoWindow({offset: new AMap.Pixel(0, -30)});
    	
    	function loadMapStation(){
    		
    		var asx = $('#x').val();
        	var asy = $('#y').val();
        	if(asx != null && asx != '' && asy != null && asy != ''){
        	}else{
        		asx = 116.397497;
        		asy = 39.908698;
        	}
        	map = new AMap.Map('container',{	
                resizeEnable: true,             //是否监控地图容器尺寸变化，默认值为false
                zoom: 10,						//地图显示的缩放级别
                center: [asx, asy],  //地图中心点
            	keyboardEnable: false  			//是否可以通过键盘来控制地图移动
            });
        	
			afterLoad();
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
       	    
       	    /* var infoWindow = new SimpleInfoWindow({
       	            //基点指向marker的头部位置
       	            offset: new AMap.Pixel(0, -31)
       	        }); */
       	    
       	    //AMapUI.loadUI(['overlay/SimpleInfoWindow'], function(SimpleInfoWindow) {
       	    	createMarker();
       	        
       	      	//点击事件
       		    map.on('click', function(e) {
       		    	createMarker();
       		    	marker.setPosition(e.lnglat);
       		    	openInfoWin();
       		    	//var lnglatXY=[116.396574, 39.992706];//地图上所标点的坐标
       	    		geocoder.getAddress(e.lnglat, function(status, result) {
       	    		    if (status === 'complete' && result.info === 'OK') {
       	    		       //获得了有效的地址信息:
       	    		       //即，result.regeocode.formattedAddress
       	    		       console.log(result);
       	    		       //infoWindow.setInfoTitle('<strong>' + result.regeocode.addressComponent.province + ' ' + result.regeocode.addressComponent.district + '</strong>');
       	    		       //infoWindow.setInfoBody('<p class="my-desc"><strong>详细地址:</strong> <br/>' + result.regeocode.formattedAddress + '</p>');
       	    		       infoWindow.setPosition(e.lnglat);
       	    		       
       	    		       infoWindow.setContent('<p class="my-desc">' + result.regeocode.formattedAddress + '</p>');//点击以后窗口展示的内容
                              infoWindow.open(map, e.lnglat);
       	    		       
       	    		       //给表单赋值
       	    		       $('#x').val(e.lnglat.getLng());
       	    		       $('#y').val(e.lnglat.getLat());
       	    		       $('#stopLocation').val(result.regeocode.formattedAddress);
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
       	    //});
       	    
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
                  	         $('#stopLocation').val(results.selected.data.name);
                  	         $('#x').val(results.selected.data.location.lat);
                  	         $('#y').val(results.selected.data.location.lng);
                  	      	 map.remove(marker);
	                });
				});
    	    });
       	}
    	
    	var marker;
    	function createMarker(){
    		if(typeof(marker) != "undefined"){
    			map.remove(marker);
    		}
    		
    		//创建marker对象
   			marker = new AMap.Marker({
           		title: "点击测试",
           		map: map,
           		bubble: false
       		});
    	}
    	
    	function markerClick(e){
    		var hs = e.target.extData;
            infoWindow.setContent(hs['address']);//点击以后窗口展示的内容
            infoWindow.open(map, e.target.getPosition());
            
            //给表单赋值
	         $('#x').val(hs['getLat']);
	         $('#y').val(hs['getLng']);
	         $('#stopLocation').val(hs['address']);
             
            console.log(hs);
        }
        
    </script>
    
</body>