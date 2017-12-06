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
        
        .search {
        	position: absolute;
        	top: 22%;
        	right: 50px;
        }
        
        .button-group {
			position: absolute;
			bottom: 20px;
			right: 20px;
			font-size: 12px;
			padding: 10px;
		}
		
		.button-group .button {
			height: 28px;
			line-height: 28px;
			background: #0D9BF2;
			color: #FFF;
			border: 0;
			outline: none;
			padding-left: 5px;
			padding-right: 5px;
			border-radius: 3px;
			margin-bottom: 4px;
			cursor: pointer;
		}

	  </style>
	  <!-- <link rel="stylesheet" href="http://cache.amap.com/lbs/static/main1119.css"/> -->
	<title>新增验票员</title>

	</head>
<body style="overflow-y: hidden" scroll="no" onload="loadMapStationBs()" >
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
					<input class="inputxt" style="height: 30px;" id="name" name="name" dataType="*"
						   value="${busStopInfo.name }">
					<!-- <span class="Validform_checktip"></span> -->
				</td>
				<td align="center">
					<label class="Validform_label">站点类型: </label>
				</td>
				<td class="value">
					<t:dictSelect extendJson="{onchange:changeSType(this.value)}" field="stationType" typeGroupCode="sType" hasLabel="false" defaultVal="${busStopInfo.stationType}" datatype="*" ></t:dictSelect>	
					<!-- <span class="Validform_checktip"></span> -->
				</td>
				<td align="center">
					<label class="Validform_label"> 选择线路城市: </label>
				</td>
				<td class="value">
					<select name="city" id="city" onchange="changeCity(this.value)" dataType="*" >
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
			<tr id="stationInfo" >
				<td align="center">
					<label class="Validform_label">
						所选地址:
					</label>
				</td>
				<td class="value">
					<input class="inputxt" readonly="readonly" style="height: 30px;" id="stopLocation" name="stopLocation" dataType="*"
						   value="${busStopInfo.stopLocation }">
					<!-- <span class="Validform_checktip"></span> -->
				</td>
				<td align="center">
					<label class="Validform_label">
						经度:
					</label>
				</td>
				<td class="value">
					<input class="inputxt" readonly="readonly" style="height: 30px;" id="x" name="x" dataType="*"
						   value="${busStopInfo.x}">
					<span class="Validform_checktip"></span>
				</td>
				<td align="center">
					<label class="Validform_label">
						纬度:
					</label>
				</td>
				<td class="value">
					<input class="inputxt" readonly="readonly" style="height: 30px;" id="y" name="y" dataType="*"
						   value="${busStopInfo.y}">
					<!-- <span class="Validform_checktip"></span> -->
				</td>
			</tr>
		</table>
		
	</t:formvalid>
	<t:authFilter name="formtableId"></t:authFilter>
	<div id="container" tabindex="0" style="height:80%" >
	</div>
	<div class="search">
    	<input type="text" id="keyword" placeholder="请输入关键字" name="keyword" />
    	<div id="panel"></div>
   	</div>
	<div class="button-group" id="bt_group" >
		<input type="button" class="button" value="清除" id="clear"/>
	    <%-- <input type="button" class="button" value="鼠标绘制点" id="point"/>
	    <input type="button" class="button" value="鼠标绘制线" id="line"/> --%>
	    <input type="button" class="button" value="鼠标绘制面" id="polygon"/>
    </div>
    <!-- script必须放在body中。。 -->
    <script type="text/javascript" src="http://webapi.amap.com/maps?v=1.3&key=b911428c1074ac0db34529ec951bf123&plugin=AMap.MouseTool" ></script>
    <script type="text/javascript" src="https://webapi.amap.com/demos/js/liteToolbar.js"></script>
    <script src="https://webapi.amap.com/js/marker.js"></script>
    <script src="https://webapi.amap.com/ui/1.0/main.js"></script>
    <script type="text/javascript" >
    
    	function changeSType(value){
    		console.log(value);
    		if(value == '3'){
    			$('#bt_group').show();
    			$('#stationInfo').hide();
    			$('#stopLocation').val('');
    			$('#x').val('');
    			$('#y').val('');
    			if(typeof(marker) != "undefined"){
        			map.remove(marker);
        		}
    			infoWindow.close();
    		}else{
    			$('#stationInfo').show();
    			$('#bt_group').hide();
    		}
    	}
    	
    	//跳转城市
    	function changeCity(value){
    		console.log(value);
    		cityCode = value;
    		var city = $("#city option:selected").text();
    		placeSearch.setCity(city);
    		console.log(city.trim());
    		map.setCity(city.trim());
    		map.setZoom(10);
    	}
    
    	//地图对象
    	var map;
    	//窗体对象
    	var infoWindow = new AMap.InfoWindow({offset: new AMap.Pixel(0, -30)});
    	//搜索对象
    	var placeSearch;
    	//城市编码
    	var cityCode = $('#city').val();
    	//绘制区域中所有点的经纬度
    	
    	
    	
    	function loadMapStationBs(){
    		
    		$('#bt_group').hide();
    		
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
        	
			afterLoadBs();
			
			//在地图中添加MouseTool插件
		    var mouseTool = new AMap.MouseTool(map);
		    /* AMap.event.addDomListener(document.getElementById('point'), 'click', function() {
		        mouseTool.marker({offset:new AMap.Pixel(-14,-11)});
		    }, false);
		    AMap.event.addDomListener(document.getElementById('line'), 'click', function() {
		        mouseTool.polyline();
		    }, false); */
		    AMap.event.addDomListener(document.getElementById('polygon'), 'click', function() {
		        mouseTool.polygon();
		    }, false);
		    AMap.event.addDomListener(document.getElementById('clear'), 'click', function() {
		    	//清除地图上的所有标记
		    	map.clearMap();
		    }, false);
		    
		    var drawPolygon = mouseTool.polygon(); //用鼠标工具画多边形
		    AMap.event.addListener( mouseTool,'draw',function(e){ //添加事件
		      console.log(e.obj.getPath());//获取路径/范围
		    });
    	}
    	
    	//打开窗体
    	function openInfoWin(content, position) {
    		infoWindow.setContent('<p class="my-desc">' + content + '</p>');//点击以后窗口展示的内容
            infoWindow.open(map, position);
        }
    	
       	function afterLoadBs(){
       		//经纬度获取详细地址
       	    AMap.service('AMap.Geocoder',function(){//回调函数
       	        //实例化Geocoder
       	        geocoder = new AMap.Geocoder({
       	            city: '010'//城市，默认：“全国”
       	        });
       	    })
       	    
    	    createMarkerBs();
      	    	
   	    	var location = $('#stopLocation').val();
   	    	if(location != null && location != ""){
   	    		openInfoWin(location, marker.getPosition());
   	    	}
   	    	
   	      	//点击事件
   		    map.on('click', function(e) {
   		    	
   		    	var st = $("select[name='stationType']").val()
   	   	    	console.log(st);
   	   	    	if(st == '3'){
   	   	    		return;
   	   	    	}
   	   	    	
   		    	createMarkerBs();
   		    	marker.setPosition(e.lnglat);
   		    	openInfoWin("正在获取位置信息...", e.lnglat);
   		    	//var lnglatXY=[116.396574, 39.992706];//地图上所标点的坐标
   	    		geocoder.getAddress(e.lnglat, function(status, result) {
   	    			
   	    			if(cityCode == "" || cityCode == null){
						   openInfoWin(" 请先选择城市 ", marker.getPosition());
				    }else{
	   	    		    if (status === 'complete' && result.info === 'OK') {
	   	    		       //获得了有效的地址信息:
	   	    		       //即，result.regeocode.formattedAddress
	   	    		       console.log(result);
	   	    		       
	                       openInfoWin(result.regeocode.formattedAddress, e.lnglat);
	   	    		       
	                       //城市编码
						   var adCode = result.regeocode.addressComponent.adcode;
						   console.log(adCode + "-----" + cityCode);
						   
						   //判断点击的marker是否在同一个城市内，通过城市编码的前四位来判断
		   	    		   if(cityCode.substr(0,4) == adCode.substr(0,4)){
						       openInfoWin(result.regeocode.formattedAddress, e.lnglat);
								
						       //给表单赋值
		   	    		       $('#x').val(e.lnglat.getLng());
		   	    		       $('#y').val(e.lnglat.getLat());
		   	    		       $('#stopLocation').val(result.regeocode.formattedAddress);
			   		       }else{
			   		    	   openInfoWin("只限于" + $("#city option:selected").text() + "地区", marker.getPosition());
			   		       }
	   	    		    }else{
	   	    		    	openInfoWin("获取地址失败", marker.getPosition());
	   	    		    }
				    }
   	    		});
   		        //console.log('您在[ '+e.lnglat.getLng()+','+e.lnglat.getLat()+' ]的位置点击了地图！');
   		    });
   	      	
   	        //marker 点击时打开
   	        AMap.event.addListener(marker, 'click', function() {
   	            openInfoWin("正在获取位置信息...", marker.getPosition());
   	        });
       		
    	    AMap.plugin(['AMap.Autocomplete','AMap.PlaceSearch'],function(){
				var autoOptions = {
					city: city, //城市，默认全国
					input: "keyword"//使用联想输入的input的id
				};
				autocomplete= new AMap.Autocomplete(autoOptions);
				placeSearch = new AMap.PlaceSearch({
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
                         
	                	if(cityCode == "" || cityCode == null){
							   openInfoWin(" 请先选择城市 ", marker.getPosition());
					    }else{
							if(cityCode.substr(0,4) == results.selected.data.adcode.substr(0,4)){
							 	console.log(results.selected.data);
							    //给表单赋值
							    $('#stopLocation').val(results.selected.data.name);
							    $('#x').val(results.selected.data.location.lng);
							    $('#y').val(results.selected.data.location.lat);
								map.remove(marker);
							}else{
							    openInfoWin("只限于" + $("#city option:selected").text() + "地区", marker.getPosition());
							}
					    }
	                });
				});
    	    });
       	}
    	
    	var marker;
    	function createMarkerBs(){
    		if(typeof(marker) != "undefined"){
    			map.remove(marker);
    		}
    		
    		//创建marker对象
   			marker = new AMap.Marker({
           		title: "点击测试",
           		map: map,
           		bubble: true
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