<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>包车区域线路</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
		
		//线路类型和区域
	  	function getLineType(){
	  		var lt = $('#lineType');
	  		var district = $('#district');
	  		var city = $("#cityId").find("option:selected");
	  		var business = city.attr("business");
	  		if(business == null || business == ''){
	  			lt.empty().append("<option value=''>--请选择线路类型--</option>");
	  			district.empty().append("<option value=''>--请选择区域--</option>");
	  			$('#stationId').empty().append("<option value=''>--请选择站点--</option>");
	  		}else{
		  		if(business.indexOf('0') != -1){	
		  			lt.append("<option value='0'>接送机</option>");
		  		}
		  		if(business.indexOf('1') != -1){
		  			lt.append("<option value='1'>接送火车</option>");
		  		}
		  		
		  		//获取区域信息
		  		$.ajax({
					url : "http://restapi.amap.com/v3/config/district?keywords=" + city.html().trim() + "&subdistrict=1&showbiz=false&key=ee95e52bf08006f63fd29bcfbcf21df0",
					type : "get",
					success : function(data) {
						//console.log(data);
						for(var i=0;i<data.districts[0].districts.length;i++){
							var name = data.districts[0].districts[i].name;
							var id = data.districts[0].districts[i].id;
							district.append("<option value='" + id + "'>" + name + "</option>");
						}
					}
				});
	  		}
	  	}
  		
	  	//获取站点信息
	  	function getStation(value){
	  		console.log(value);
	  		$('#stationId').empty().append("<option value=''>--请选择站点--</option>");
	  		if(value == '' || value == null){
	  		}else{
	  			$.ajax({
					url : "areaLineController.do?getStation&type="+value,
					type : "get",
					success : function(data) {
						var json = $.parseJSON(data);
						var obj = json.data;
						console.log(obj);
						for(var i=0;i<obj.length;i++){
							$('#stationId').append("<option value='" + obj[i].id + "'>" + obj[i].name + "</option>");
						}
					}
				});
	  		}
	  	}
  	
  </script>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="areaLineController.do?save">
			<input id="id" name="id" type="hidden" value="${areaLinePage.id }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							线路名称:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="name" name="name" ignore="ignore"
							   value="${areaLinePage.name}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label"> 选择线路城市: </label>
					</td>
					<td class="value">
						<select id="cityId" name="cityId" datatype="*" onChange="getLineType()">  <!-- this.options[this.options.selectedIndex].getAttribute('business') -->
								<option value="">--请选择城市--</option>
								<c:forEach var="c" items="${cities}">
									<option value="${c.cityId}" business="${c.cityBusiness}" <c:if test="${areaLinePage.cityId == c.cityId}" >selected="selected"</c:if> >
										${c.cityName}
									</option>
								</c:forEach>
						</select> 
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							所属区域（高德）:
						</label>
					</td>
					<td class="value">
						<%-- <select id="district" name="district" datatype="*" value="${areaLinePage.district}"> --%>
						<select id="district" name="district" datatype="*" >  <!-- this.options[this.options.selectedIndex].getAttribute('business') -->
								<option value="">--请选择区域--</option>
						</select> 
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							线路类型 :
						</label>
					</td>
					<td class="value">
						<%-- <input class="inputxt" id="lineType" name="lineType" ignore="ignore"
							   value="${areaLinePage.lineType}"> --%>
						<select id="lineType" name="lineType" datatype="*" onChange="getStation(this.value)">
								<option value="">--请选择类型--</option>
						</select> 
						<%-- <t:dictSelect field="lineType" typeGroupCode="area_line_type" hasLabel="false" defaultVal="${areaLinePage.lineType}" datatype="*"></t:dictSelect> --%>
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							机场或火车站站点:
						</label>
					</td>
					<td class="value">
						<%-- <input class="inputxt" id="stationId" name="stationId" ignore="ignore"
							   value="${areaLinePage.stationId}"> --%>
						<select id="stationId" name="stationId" datatype="*" >
							<option value="">--请选择站点--</option>
						</select> 
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							出车时间段:
						</label>
					</td>
					<td class="value">
					    <t:dictSelect field="dispath" typeGroupCode="dispathtime" hasLabel="false" defaultVal="${areaLinePage.dispath}" datatype="*"></t:dictSelect>	
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							车辆类型:
						</label>
					</td>
					<td class="value">
					    <t:dictSelect field="carType" typeGroupCode="car_type" hasLabel="false" defaultVal="${areaLinePage.carType}" datatype="*"></t:dictSelect>	
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							线路状态:
						</label>
					</td>
					<td class="value">
						<t:dictSelect field="status" typeGroupCode="lineStatus" hasLabel="false" defaultVal="${areaLinePage.status}" datatype="*" ></t:dictSelect>	
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							备注:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="remark" name="remark" ignore="ignore"
							   value="${areaLinePage.remark}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>