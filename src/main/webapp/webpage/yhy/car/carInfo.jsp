<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>车辆信息</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="carInfoController.do?save">
			<input id="id" name="id" type="hidden" value="${carInfoPage.id }">
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							车牌号:<c:if test="${carInfoPage.id != undefind }">11111</c:if>
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="licencePlate" name="licencePlate" datatype="*" <c:if test="${carInfoPage.id != undefind }">disabled="disabled"</c:if>
							   value="${carInfoPage.licencePlate}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							车辆品牌:
						</label>
					</td>
					<td class="value">
						<c:choose>
							<c:when test="${carInfoPage.id != undefind }">
								<t:dictSelect field="carBrand" typeGroupCode="carBrand" hasLabel="false" readonly="readonly" defaultVal="${carInfoPage.carBrand}" datatype="*"></t:dictSelect>
							</c:when>
							<c:otherwise>
								<t:dictSelect field="carBrand" typeGroupCode="carBrand" hasLabel="false" defaultVal="${carInfoPage.carBrand}" datatype="*"></t:dictSelect>
							</c:otherwise>
						</c:choose>
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							车辆型号:
						</label>
					</td>
					<td class="value">
					    <input class="inputxt" id="modelNumber" name="modelNumber" <c:if test="${carInfoPage.id != undefind }">disabled="disabled"</c:if> 
					    	datatype="*" value="${carInfoPage.modelNumber}">
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
						<c:choose>
							<c:when test="${carInfoPage.id != undefind }">
								<t:dictSelect field="carType" typeGroupCode="car_Type" hasLabel="false" readonly="readonly" defaultVal="${carInfoPage.carType}" datatype="*"></t:dictSelect>
							</c:when>
							<c:otherwise>
								<t:dictSelect field="carType" typeGroupCode="car_Type" hasLabel="false" defaultVal="${carInfoPage.carType}" datatype="*"></t:dictSelect>
							</c:otherwise>
						</c:choose>
						
						<%-- <input class="inputxt" id="carType" name="carType" datatype="*"
							   value="${carInfoPage.carType}"> --%>
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							座位数:
						</label>
					</td>
					<td class="value">
					    <input class="inputxt" id="seat" name="seat" datatype="n" <c:if test="${carInfoPage.id != undefind }">disabled="disabled"</c:if>
					   			 value="${carInfoPage.seat}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							车辆位置:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="stopPosition" name="stopPosition" ignore="ignore"
							   value="${carInfoPage.stopPosition}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							购置日期:
						</label>
					</td>
					<td class="value">
						<%-- <input class="inputxt" id="buyDate" name="buyDate" value="${carInfoPage.buyDate}"> --%>
						<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" <c:if test="${carInfoPage.id != undefind }">disabled="disabled"</c:if>
							style="width: 150px" value="${carInfoPage.buyDate}" id="buyDate" name="buyDate" >
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<%-- <tr>
					<td align="right">
						<label class="Validform_label">
							司机:
						</label>
					</td>
					<td class="value">
						<select id="driverId" name="driverId" datatype="*">
							<c:forEach items="${driversList}" var="d">
								<option value="${d.id}" <c:if test="${d.id==carInfoPage.driverId}">selected="selected"</c:if>>${d.name}&nbsp;&nbsp;&nbsp; ${d.drivingLicense}</option>
							</c:forEach>
						</select>
						<span class="Validform_checktip"></span>
					</td>
				</tr> --%>
				<tr>
				<td align="right">
					<label class="Validform_label">
						司机:
					</label>
				</td>
					<td class="value">					
						<input readonly="true" class="inputxt" id="licenceDriver" name="licenceDriver" value="${driverPage.name}">
						<input id="licenceDriverId" name="driverId" type="hidden" value="${carInfoPage.driverId}" >
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				
				<tr>
					<td align="right">
						<label class="Validform_label">
							车辆状态:
						</label>
					</td>
					<td class="value">
						<c:choose>
							<c:when test="${carInfoPage.status != null}">
								<t:dictSelect field="status" typeGroupCode="carType" hasLabel="false" defaultVal="${carInfoPage.status}" readonly="readonly" datatype="*"></t:dictSelect>	
							</c:when>
							<c:otherwise>
								<t:dictSelect field="status" typeGroupCode="carType" hasLabel="false" defaultVal="${carInfoPage.status}" datatype="*"></t:dictSelect>	
							</c:otherwise>
						</c:choose>
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							车辆业务类型:
						</label>
					</td>
					<td class="value">
						<t:dictSelect field="businessType" typeGroupCode="carBType" hasLabel="false" defaultVal="${carInfoPage.businessType}" datatype="*"></t:dictSelect>	
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
							   value="${carInfoPage.remark}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
			</table>
		</t:formvalid>
		<script type="text/javascript">
	$("#licenceDriver").click(function(){
		
			 	$.dialog.setting.zIndex = 9999;
			    $.dialog({content: 'url:carInfoController.do?addDriver&fromPage=1', zIndex: 2100, lock: true, width:1000, height:600, opacity: 0.4, button: [
			        {name:'确定',  
			        	callback:function(){
			        		var iframe = this.iframe.contentWindow;
			        	    var rowsData = iframe.$('#driversInfoList').datagrid('getSelections');
			        	    if (!rowsData || rowsData.length==0) {
			        	        return;
			        	    }else{
			        	    	var driverId = rowsData[0].id;//司机id
			        	    	var name = rowsData[0].name;
			        	    	$("#licenceDriver").val(name);
			        	    	$("#licenceDriverId").val(driverId);
			        	    }
			        	},
			        	focus:true
			        },
			        {name:'取消',callback:function (){}}
			    ]}).zindex();
	});
	
</script>
 </body>