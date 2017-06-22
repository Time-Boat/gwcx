<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新增验票员</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script type="text/javascript">
    function getAddr(){
    	
		var city = $('#city option:selected').val();//获取选中城市
    	var type = $('#type option:selected').val();//获取选中线路类型 
    	var ids=  $("#id").val();
    	var startLocation = $('#startLocation option:selected').val();//获取选中起点 
    	var endLocation = $('#endLocation option:selected').val();//获取选中起点 
    	var starts =  $("#starts").val();//获取起点
		var ends =  $("#ends").val();//获取终点 
    	$("#startLocation").empty();//先置空 
    	$("#endLocation").empty();//先置空 
    	
    		$.ajax({
     		   url: 'lineInfoController.do?getProvinceJson&city='+city+'&type='+type+'&starts='+starts+'&ends='+ends+'&ids='+ids,
     		   dataType: 'json',
     		   complete: function(data,status) {
     			   var message=data.responseText;
     			   var info = eval(message);
     			   var arr = new  Array();
     			  var arr1 = new  Array();
     			   for(var i=0;i<info.length;i++){
     				  var sta = info[i].statype;
     				  //接机  
     				  if(type=="2"){
     					 if(sta=="0"){
         					 arr.push(info[i]); 
         				  }
         				 if(sta=="2"){
         					 arr1.push(info[i]); 
         				  }
     				  }
     				//送机  
      				 if(type=="3"){
      					if(sta=="0"){
         					 arr1.push(info[i]); 
         				  }
         				 if(sta=="2"){
         					 arr.push(info[i]); 
         				  }
      				 }
     				  
      				//接火车
     				 if(type=="4"){
     					 if(sta=="0"){
         					 arr.push(info[i]); 
         				  }
         				 if(sta=="1"){
         					 arr1.push(info[i]); 
         				  }
     				  }
     				 
     				 //送火车 
     				if(type=="5"){
    					 if(sta=="0"){
        					 arr1.push(info[i]); 
        				  }
        				 if(sta=="1"){
        					 arr.push(info[i]); 
        				  }
    				  }
     				 }
     			    if(ids!="" && ids!=null){
     			    	
     			    
     			 //修改的时候给下拉框赋值 
     			 if(type=="2" || type=="4"){
     				  if(arr1.length>0){
         				  for(var i=0;i<arr1.length;i++){
             				  if(arr1[i].name==arr1[i].startname){
             					 $("#startLocation").append($('<option value="'+arr1[i].stopid+'">'+arr1[i].startname+'</option>'));//后台数据加到下拉框
             					 $("#endLocation").append($('<option value="'+arr1[i].stopid+'">'+arr1[i].endname+'</option>'));//后台数据加到下拉框
             				  }
             			  }
         			   }
     			   }
     				//修改的时候给下拉框赋值 
     			   if(type=="3" || type=="5"){
     				  if(arr.length>0){
         				  for(var i=0;i<arr.length;i++){
             				  if(arr[i].name==arr[i].endname){
             					 $("#startLocation").append($('<option value="'+arr[i].stopid+'">'+arr[i].startname+'</option>'));//后台数据加到下拉框
             					 $("#endLocation").append($('<option value="'+arr[i].stopid+'">'+arr[i].endname+'</option>'));//后台数据加到下拉框
             				  }
             			  }
         			   }
     			   }
     			    }else{
     			    	if(startLocation=="" || startLocation==null){
     			    		$("#startLocation").append($('<option value="">'+"--请选择--"+'</option>'));
     			    	}
						if(endLocation=="" || endLocation==null){
							$("#endLocation").append($('<option value="">'+"--请选择--"+'</option>'));
						}
     			    }
     			   
     			  $.each(arr1, function(i,n){
     				  if(starts!=n.stopid){//去掉重复的数据
     					 $("#startLocation").append($('<option value="'+n['stopid']+'">'+n['name']+'</option>'));//后台数据加到下拉框
     				  }
   				});
      			 $.each(arr, function(i,n){
      				 if(ends!=n.stopid){//去掉重复的数据
      					$("#endLocation").append($('<option value="'+n['stopid']+'">'+n['name']+'</option>'));//后台数据加到下拉框
      				 }
 				});
     			   }
     		   });
    }
    //进入触发 
    $(function(){
    	getAddr();
    });
    
</script>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="lineInfoController.do?save">
	<input id="id" name="id" type="hidden" value="${lineInfo.id }">
	<input id="ends" name="endLocations" type="hidden" value="${lineInfo.endLocation}">
	<input id="starts" name="startLocations" type="hidden" value="${lineInfo.startLocation}">
	<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable" id = "formtableId">
		
		<tr>
			<td align="right" width="15%" nowrap>
				<label class="Validform_label"> 线路名称: </label>
			</td>
			<td class="value" width="85%">
			<input id="name" name="name" value="${lineInfo.name}" type="text" style="width: 60%" class="inputxt"  datatype="*"> 
				<span class="Validform_checktip"></span> 
			</td>
		</tr>
		
		<tr>
			<td align="right">
				<label class="Validform_label"> 选择线路城市: </label>
			</td>
			<td class="value">
				<select id="city" name="city" datatype="*" onChange="getAddr()">
						<option value="">--请选择城市--</option>
						<c:forEach var="c" items="${cities}">
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
				
				<t:dictSelect id="type" field="type" extendJson="{onchange:getAddr()}" typeGroupCode="transferTy" hasLabel="false" defaultVal="${lineInfo.type}" datatype="*" ></t:dictSelect>	
				
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		
		<tr>
			<td align="right">
				<label class="Validform_label"> 出车时间段: </label>
			</td>
			<td class="value">
				
				<t:dictSelect field="dispath" typeGroupCode="dispathtime" hasLabel="false" defaultVal="${lineInfo.dispath}" datatype="*"></t:dictSelect>	
				
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		
		<tr>
			<td align="right">
				<label class="Validform_label"> 起始发车地址: </label>
			</td>
			<td class="value">
				<select id="startLocation" style="width: 152px" class="select_field" name="startLocation" >  
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
		<tr>
			<td align="right">
				<label class="Validform_label"> 起始位置X坐标: </label>
			</td>
			<td class="value">
				<input class="inputxt" name="startX" value="${lineInfo.startX}" datatype="*"> 
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label"> 起始位置Y坐标: </label>
			</td>
			<td class="value">
				<input class="inputxt" name="startY" value="${lineInfo.startY}" datatype="*"> 
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label"> 终点位置X坐标: </label>
			</td>
			<td class="value">
				<input class="inputxt" name="endX" value="${lineInfo.endX}" datatype="*"> 
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		<tr>
			<td align="right">
				<label class="Validform_label"> 终点位置Y坐标: </label>
			</td>
			<td class="value">
				<input class="inputxt" name="endY" value="${lineInfo.endY}" datatype="*"> 
				<span class="Validform_checktip"></span>
			</td>
		</tr>
		 --%>
	</table>
</t:formvalid>
<t:authFilter name="formtableId"></t:authFilter>
</body>