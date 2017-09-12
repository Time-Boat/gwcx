<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>业务开通城市</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <script type="text/javascript">
  	
  	$(function(){
  		$('#provinceName').val($('#provinceId option:selected').text());
  		$('#cityName').val($('#cityId option:selected').text());
  	});
	
  	function changeProvince(){
  		var  cateOne = $("#provinceId").find("option:selected").val();
  	    if(cateOne == '') {
  	        $("#cityId").empty().append("<option value=''>---请选择城市----</option>");
  	        return false;
  	    }
  	    
  	    console.log('provinceId:'+$('#provinceId option:selected').text());
  	    $('#provinceName').val($('#provinceId option:selected').text());
  	    
  	    $.ajax({
  	        url:'openCityController.do?getCitys&provinceId='+cateOne,
  	        type:"get",
  	        dataType:"json",
  	      	success: function (data) {
  	      		//console.log(data);
  	      		data = eval("("+data+")");
  	      		//console.log(data);
  	            $("#cityId").empty().append("<option value=''>---请选择城市----</option>");
  	          	var city = $("#cityId").val();
  	            for(var i=0,len=data.length;i<len;i++){
  	            	if(city == data[i].cityId){
  	            		$("#cityId").append($("<option value='"+data[i].cityId+"' selected='selected' >"+data[i].city+"</option>")); 
  	            	}else{
  	            		$("#cityId").append($("<option value='"+data[i].cityId+"'>"+data[i].city+"</option>")); 
  	            	}
  	            }
  	            //console.log($('#cityId option:selected').text());
  	      }
  	     }); 
  	}
  
  	function changeCity(){
  		$('#cityName').val($('#cityId option:selected').text());
  		console.log($('#cityId option:selected').text());
  	}
  	
  </script>
  <!-- 多选框样式 -->
  <style type="text/css">
  	.demo--label{margin:0 20px 0 0;display:inline-block}
	.demo--radio{display:none}
	.demo--radioInput{background-color:#fff;border:1px solid rgba(0,0,0,0.15);border-radius:100%;display:inline-block;height:16px;margin-right:10px;margin-top:-1px;vertical-align:middle;width:16px;line-height:1}
	.demo--radio:checked + .demo--radioInput:after{background-color:#57ad68;border-radius:100%;content:"";display:inline-block;height:12px;margin:2px;width:12px}
	.demo--checkbox.demo--radioInput,.demo--radio:checked + .demo--checkbox.demo--radioInput:after{border-radius:0}
  </style>
  
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="openCityController.do?save">
			<input id="id" name="id" type="hidden" value="${openCityPage.id }">
			<input id="provinceName" name="provinceName" type="hidden" value="${openCityPage.provinceName }">
			<input id="cityName" name="cityName" type="hidden" value="${openCityPage.cityName }">
			
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							省份:
						</label>
					</td>
					<td class="value">
						<select id="provinceId" name="provinceId" onchange="changeProvince();" datatype="*" >
							<option value="">---请选择省份----</option>
							<c:forEach items="${pList}" var="p" >
								<option value="${p.provinceId}" <c:if test="${p.provinceId == openCityPage.provinceId}" >selected="selected"</c:if> >${p.province}</option>
							</c:forEach>
						</select>
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							城市:
						</label>
					</td>
					<td class="value">
						<%-- <input class="inputxt" id="cityId" name="cityId" ignore="ignore"
							   value="${openCityPage.cityName}"> --%>
					    <select id="cityId" name="cityId" datatype="*" onchange="changeCity();">
							<option value="">---请选择城市----</option>
							<c:forEach items="${cities}" var="c" >
								<option value="${c.cityId}" <c:if test="${c.cityId == openCityPage.cityId}" >selected="selected"</c:if> >${c.city}</option>
							</c:forEach>
						</select>
						<span class="Validform_checktip"></span>
					</td>
				</tr>
<%-- 				<tr>
					<td align="right">
						<label class="Validform_label">
							省id:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="provinceId" name="provinceId" ignore="ignore"
							   value="${openCityPage.provinceId}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							城市id:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="cityId" name="cityId" ignore="ignore"
							   value="${openCityPage.cityId}">
						<span class="Validform_checktip"></span>
					</td>
				</tr> --%>
				<tr>
					<td align="right">
						<label class="Validform_label">
							城市开通业务:
						</label>
					</td>
					<td class="value">
						<%-- <input class="inputxt" id="cityBusiness" name="cityBusiness" ignore="ignore"
							   value="${openCityPage.cityBusiness}"> --%>
						<label class="demo--label">
							<c:choose>
								<c:when test="${fn:contains(openCityPage.cityBusiness,'0')}">
									<input class="demo--radio" name="cityBusiness" type="checkbox" checked="checked" value="0" />
								</c:when>
								<c:otherwise>
									<input class="demo--radio" name="cityBusiness" type="checkbox" value="0" />
								</c:otherwise>
							</c:choose>
							<span class="demo--checkbox demo--radioInput"></span>接送机 
						</label> 
						<label class="demo--label">
							<c:choose>
								<c:when test="${fn:contains(openCityPage.cityBusiness,'1')}">
									<input class="demo--radio" name="cityBusiness" type="checkbox" checked="checked" value="1" />
								</c:when>
								<c:otherwise>
									<input class="demo--radio" name="cityBusiness" type="checkbox" value="1" />
								</c:otherwise>
							</c:choose>
							<span class="demo--checkbox demo--radioInput"></span>接送火车 
						</label> 
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							城市开通状态:
						</label>
					</td>
					<td class="value">
					    <t:dictSelect field="status" typeGroupCode="openCity" hasLabel="false" defaultVal="${openCityPage.status}" datatype="*"></t:dictSelect>	
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
							   value="${openCityPage.remark}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>