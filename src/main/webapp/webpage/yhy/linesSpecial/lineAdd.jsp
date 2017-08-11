<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新增验票员</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
   
    //进入触发 
    $(function(){
    	var types= $("#types").val();
    	var dispaths= $("#dispaths").val();
    	if(types!=""){
    		 $("#type").attr("disabled", "disabled");
    	}
    	if(dispaths!=""){
    		$("#dispath").attr("disabled", "disabled");
    	}
  		$('#ends').val($('#endLocation option:selected').text());
  		$('#starts').val($('#startLocation option:selected').text());
    });
    
    //给起点站点赋值
    function getStartLocation(){
    	
    	var city = $('#city option:selected').val();//获取选中城市
    	var type = $('#type option:selected').val();//获取选中线路类型 
    	var endLocation = $('#endLocation option:selected').val();//获取选中起点 
    	var startLocation = $('#startLocation option:selected').val();//获取选中起点 
    	var ids= $("#id").val();
    	var starts = $("#starts").val();//获取起点
		var ends = $("#ends").val();//获取终点 
		$("#startLocation").empty();//先置空 
		$("#endLocation").empty();//先置空 
		
		
    	$.ajax({
   		   url: 'lineInfoController.do?getStartLocation&city='+city+'&type='+type,
   		   dataType: 'json',
   		   complete: function(data,status) {
   			   var message=data.responseText;
   			   var info = eval(message);
   				
   			 	if(info.length>0){
   			 	 $("#startLocation").append($('<option value="">'+"--请选择--"+'</option>'));
   			 	$("#endLocation").append($('<option value="">'+"--请选择--"+'</option>'));
				 for(var i=0;i<info.length;i++){
					 if(starts!=info[i].stopid){
						 $("#startLocation").append($('<option value="'+info[i].stopid+'">'+info[i].name+'</option>'));//后台数据加到下拉框
					 }
 			  		}
			  	}
   		   }
     	});
    }
    
  	//给终点站点赋值
    function getEndlocation(){
    	var city = $('#city option:selected').val();//获取选中城市
    	var type = $('#type option:selected').val();//获取选中线路类型 
    	var startLocation = $('#startLocation option:selected').val();//获取选中起点 
    	var starts =  $("#starts").val();//获取起点
    	var ends =  $("#ends").val();//获取终点 
    	$("#endLocation").empty();//先置空 
    	$.ajax({
  		   url: 'lineInfoController.do?getEndlocation&city='+city+'&type='+type+'&startLocation='+startLocation,
  		   dataType: 'json',
  		   complete: function(data,status) {
  			   var message=data.responseText;
  			   var info = eval(message);
  			 $("#endLocation").append($('<option value="">'+"--请选择--"+'</option>'));
  			 if(info.length>0){
				 for(var i=0;i<info.length;i++){
						 $("#endLocation").append($('<option value="'+info[i].stopid+'">'+info[i].name+'</option>'));//后台数据加到下拉框
  			  	 }
			   }
  		   }
    	});
    }
    
</script>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="lineInfoController.do?save">
	<input id="id" name="id" type="hidden" value="${lineInfo.id }">
	<input id="ends" name="endLocations" type="hidden" value="${lineInfo.endLocation}">
	<input id="starts" name="startLocations" type="hidden" value="${lineInfo.startLocation}">
	<input id="types" name="types" type="hidden" value="${lineInfo.type}">
	<input id="dispaths" name="dispaths" type="hidden" value="${lineInfo.dispath}">
	<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable" id = "formtableId">
		
		<tr>
			<td align="right" width="15%" nowrap>
				<label class="Validform_label"> 线路名称: </label>
			</td>
			<td class="value" width="85%">
				
                    <input id="name" name="name" <c:if test="${lineInfo.name!=null }"> readonly='readonly' </c:if> value="${lineInfo.name}" type="text" style="width: 60%" class="inputxt"  datatype="*" /> 
					<span class="Validform_checktip"></span> 
			
			</td>
		</tr>
		
		<tr>
			<td align="right">
				<label class="Validform_label"> 选择线路城市: </label>
			</td>
			<td class="value">
				<select id="city" name="city" datatype="*" onchange="getStartLocation()" <c:if test="${lineInfo.cityId!=null }">  disabled="disabled" </c:if>>
						<option value="">--请选择城市--</option>
						<c:forEach var="c" items="${cities}">
							<option value="${c.cityId}" <c:if test="${lineInfo.cityId == c.cityId}" >selected="selected"</c:if>>
								${c.cityName}
							</option>
						</c:forEach>
				</select> 
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		
		<tr>
			<td align="right">
				<label class="Validform_label"> 线路类型: </label>
			</td>
			<td class="value">
				
				<t:dictSelect id="type" field="type" extendJson="{onchange:getStartLocation()}" typeGroupCode="transferTy" hasLabel="false" defaultVal="${lineInfo.type}" datatype="*" ></t:dictSelect>	
				
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		
		<tr>
			<td align="right">
				<label class="Validform_label"> 出车时间段: </label>
			</td>
			<td class="value">
				
				<t:dictSelect id="dispath" field="dispath" typeGroupCode="dispathtime" hasLabel="false" defaultVal="${lineInfo.dispath}" datatype="*"></t:dictSelect>	
				
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		
		<tr>
			<td align="right">
				<label class="Validform_label"> 起始发车地址: </label>
			</td>
			<td class="value">
				<select id="startLocation" style="width: 152px" class="select_field" name="startLocation" onChange="getEndlocation()" <c:if test="${lineInfo.startLocation!=null }">  disabled="disabled" </c:if>>  
                <option value="">--请选择发车地址--</option>
						<c:forEach var="b" items="${startList}">
							<option value="${b.id}" <c:if test="${lineInfo.startLocation == b.id}" >selected="selected"</c:if> >
								${b.name}
							</option>
						</c:forEach>
                </select>
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label"> 终点位置地址:  </label>
			</td>
			<td class="value">
				<select id="endLocation" style="width: 152px" class="select_field" name="endLocation" <c:if test="${lineInfo.endLocation!=null }">  disabled="disabled" </c:if>> 
				<option value="">--请选择终点位置地址--</option>
						<c:forEach var="b" items="${endList}">
							<option value="${b.id}" <c:if test="${lineInfo.endLocation == b.id}" >selected="selected"</c:if> >
								${b.name}
							</option>
						</c:forEach> 
                </select>
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		
		<tr>
			<td align="right">
				<label class="Validform_label"> 线路时长: </label>
			</td>
			<td class="value">
				<input class="inputxt" name="lineTimes" value="${lineInfo.lineTimes}" style="width: 10%" datatype="n1-3"> 
				<span >分</span>
			</td>
		</tr>
		
		<%--
		<tr>
			<td align="right">
				<label class="Validform_label"> 终点位置地址: </label>
			</td>
			<td class="value">
				<input class="inputxt" name="endLocation" value="${lineInfo.endLocation}" style="width: 60%" datatype="*"> 
				<span class="Validform_checktip"></span>
			</td>
		</tr>--%>
		<tr>
			<td align="right">
				<label class="Validform_label"> 线路状态: </label>
			</td>
			<td class="value">
				
					<t:dictSelect field="status" typeGroupCode="lineStatus" hasLabel="false" defaultVal="${lineInfo.status}" datatype="*"></t:dictSelect>	
				
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		
		
		<%-- <tr>
			<td align="right">
				<label class="Validform_label"> 选择入驻公司: </label>
			</td>
			<td class="value">
				<select name="settledCompany">
						<c:choose>
							<c:when test="${lineInfo.settledCompanyId !=null }">
								<option value="${lineInfo.settledCompanyId}">${lineInfo.settledCompanyName}</option>
							</c:when>
							<c:otherwise>
								<option value="">--请选择入驻公司--</option>
							</c:otherwise>
						</c:choose>
						<c:forEach var="settledCompany" items="${list}">
							<option value="${settledCompany.id}">
								${settledCompany.departname}
							</option>
						</c:forEach>
				</select> 
				<span class="Validform_checktip"></span>
			</td>
		</tr> --%>
		
		<%-- <tr>
			<td align="right">
				<label class="Validform_label"> 发车时间: </label>
			</td>
			<td class="value">
				<input name="lstartTime" class="Wdate" onClick="WdatePicker({dateFmt:'HH:mm:ss'})" style="width: 150px" value="<fmt:formatDate value='${lineInfo.lstartTime }' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>" errormsg="日期格式不正确!"  datatype="*"> 
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label"> 预计到达时间: </label>
			</td>
			<td class="value">
				<input name="lendTime" class="Wdate" onClick="WdatePicker({dateFmt:'HH:mm:ss'})" style="width: 150px" value="<fmt:formatDate value='${lineInfo.lendTime }' type="date" pattern="yyyy-MM-dd hh:mm:ss"/>" errormsg="日期格式不正确!"  datatype="*"> 
				<span class="Validform_checktip"></span>
			</td>
		</tr> --%>
		
		<tr>
			<td align="right">
				<label class="Validform_label"> 线路定价(元/人): </label>
			</td>
			<td class="value">
				<input class="inputxt" name="price" value="${lineInfo.price}" style="width: 60%" datatype="d"> 
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		
		<tr>
			<td align="right">
				<label class="Validform_label"> 线路备注: </label>
			</td>
			<td class="value">
				<textarea id="remark" name="remark" cols="60" rows="6">${lineInfo.remark}</textarea>
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		
		
		
		<%-- 
		<tr>
			<td align="right">
				<label class="Validform_label"> 线路图片: </label>
			</td>
			<td class="value">
				<input class="inputxt" name="endLocation" value="${lineInfo.endLocation}" datatype="*"> 
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label"> 线路类型: </label>
			</td>
			<td class="value">
				<input class="inputxt" name="endLocation" value="${lineInfo.endLocation}" datatype="*"> 
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		 --%>
	</table>
</t:formvalid>
<t:authFilter name="formtableId"></t:authFilter>
</body>