<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新增线路</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    function getAddrBC(){
    	
		//var city = $('#city option:selected').val();//获取选中城市
    	//var type = $('#type option:selected').val();//获取选中线路类型 
    	//var startLocation = $('#startLocation option:selected').val();//获取选中起点 
    	//var endLocation = $('#endLocation option:selected').val();//获取选中起点 
    	
    	//var ids=  $("#id").val();
    	//var starts =  $("#starts").val();//获取起点
		//var ends =  $("#ends").val();//获取终点 
    	//$("#startLocation").empty();//先置空 
    	//$("#endLocation").empty();//先置空 
    	
    	getStartLocationBC();
    	getEndlocationBC();
    }
    
  //给起点站点赋值
    function getStartLocationBC(){
    	
    	var city = $('#city option:selected').val();//获取选中城市
    	//var type = $('#type option:selected').val();//获取选中线路类型 
    	var endLocation = $('#endLocation option:selected').val();//获取选中起点 
    	var startLocation = $('#startLocation option:selected').val();//获取选中起点 
    	//var ids= $("#id").val();
    	var starts = $("#starts").val();//获取起点
		var ends = $("#ends").val();//获取终点 
		$("#startLocation").empty();//先置空 
		//$("#endLocation").empty();//先置空 
		
		
    	$.ajax({
   		   url: 'lineInfoController.do?getStartLocation&city='+city+'&type=0',   //0：班车类型
   		   dataType: 'json',
   		   complete: function(data,status) {
   			   var message=data.responseText;
   			   var info = eval(message);
   				/* if(ids!=""){
   					$("#startLocation").append($('<option value="'+startLocation+'">'+info[0].startname+'</option>'));
   					$("#endLocation").append($('<option value="'+endLocation+'">'+info[0].endname+'</option>'));
   				}else{
   					$("#endLocation").append($('<option value="">'+"--请选择--"+'</option>'));
   					$("#startLocation").append($('<option value="">'+"--请选择--"+'</option>'));
   				} */
   				
   			 	if(info.length>0){
   			 		$("#startLocation").append($('<option value="">'+"--请选择--"+'</option>'));
   			 		//$("#endLocation").append($('<option value="">'+"--请选择--"+'</option>'));
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
    function getEndlocationBC(){
    	var city = $('#city option:selected').val();//获取选中城市
    	//var type = $('#type option:selected').val();//获取选中线路类型 
    	var startLocation = $('#startLocation option:selected').val();//获取选中起点 
    	var starts =  $("#starts").val();//获取起点
    	var ends =  $("#ends").val();//获取终点 
    	$("#endLocation").empty();//先置空 
    	$.ajax({
  		   url: 'lineInfoController.do?getEndlocation&city='+city+'&type=0&startLocation='+startLocation,
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
  	
    //进入触发 
    $(function(){
    	//getAddrBC();
    });
    
</script>
</head>
<body scroll="no">
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="lineInfoController.do?serviceLineSave">
	<input id="id" name="id" type="hidden" value="${lineInfo.id }">
	<input id="ends" name="endLocations" type="hidden" value="${lineInfo.endLocation}">
	<input id="starts" name="startLocations" type="hidden" value="${lineInfo.startLocation}">
	<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable" id = "formtableId">
		<%-- <tr>
		${lineInfo.id}
		${lineInfo.name}
		${lineInfo.startLocation}
		${lineInfo.endLocation}
		${lineInfo.remark}
		${lineInfo.imageurl}
		${lineInfo.type}
		${lineInfo.status}
		${lineInfo.deleteFlag}
		</tr> --%>
		<input type="button" onclick="javascript:window.open('www.baidu.com','_self');" value="测试" />
		<tr>
			<td align="right" width="15%" nowrap>
				<label class="Validform_label"> 线路名称: </label>
			</td>
			<td class="value" width="85%">
			<input id="name" name="name" value="${lineInfo.name}" type="text" style="width: 60%" class="inputxt"  datatype="*"> 
				<span class="Validform_checktip"></span> 
			</td>
			
			<%-- <td class="value" width="85%">
				<c:if test="${lineInfo.id!=null }">
					     ${lineInfo.name }
				</c:if> 
				<c:if test="${lineInfo.id==null }">
					<input id="name" class="inputxt" name="name" value="${lineInfo.name}" datatype="*">
					<span class="Validform_checktip">范围在2~10位字符</span>
				</c:if>
			</td> --%>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label"> 选择线路城市: </label>
			</td>
			<td class="value">
				<select id="city" name="city" datatype="*" onChange="getAddrBC()">
						<option value="">--请选择城市--</option>
						<c:forEach var="c" items="${cities}" >
							<option value="${c.cityId}" <c:if test="${lineInfo.cityId == c.cityId}" >selected="selected"</c:if> >
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
				<!-- 这里面写死，因为这个线路添加中只有班车类型的添加 -->
				
				<!-- extendJson="{onchange:getAddrBC()}" --><t:dictSelect id="type" field="type" typeGroupCode="linetype" hasLabel="false" defaultVal="${lineInfo.type}" datatype="*" ></t:dictSelect>	
				
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		
		<tr>
			<td align="right">
				<label class="Validform_label"> 起始发车地址: </label>
			</td>
			<td class="value">
				<select id="startLocation" style="width: 152px" class="select_field" name="startLocation" >  
					<option value="">--请选择发车地址--</option>
                </select>
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label"> 终点位置地址:  </label>
			</td>
			<td class="value">
				<select id="endLocation" style="width: 152px" class="select_field" name="endLocation" >  
					<option value="">--请选择发车地址--</option>
                </select>
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		<%-- 
		
		<tr>
			<td align="right">
				<label class="Validform_label"> 起始发车地址: </label>
			</td>
			<td class="value">
				<input class="inputxt" name="startLocation" value="${lineInfo.startLocation}" style="width: 60%" datatype="*"> 
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label"> 终点位置地址: </label>
			</td>
			<td class="value">
				<input class="inputxt" name="endLocation" value="${lineInfo.endLocation}" style="width: 60%" datatype="*"> 
				<span class="Validform_checktip"></span>
			</td>
		</tr> --%>
		<tr>
			<td align="right">
				<label class="Validform_label"> 线路状态: </label>
			</td>
			<td class="value">
				
					<t:dictSelect field="status" typeGroupCode="lineStatus" hasLabel="false" defaultVal="${lineInfo.status}" datatype="*"></t:dictSelect>	
				
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		
		<tr>
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
		</tr>
		
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
				<label class="Validform_label"> 线路定价(人/元): </label>
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