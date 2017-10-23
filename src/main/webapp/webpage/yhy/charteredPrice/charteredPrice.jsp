<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>包车价格设置</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>

<script type="text/javascript">
  $(function() {
		<c:forEach items="${CharteredPriceAppendService}" var="item" varStatus="chartered" >  
        $("input[name='charteredAppendId']").each(function(){
            if($(this).val()=="${item}"){  
                $(this).attr("checked","checked");  
            }  
        }) 
    	</c:forEach>
  })
  </script>
</head>
<body style="overflow-y: hidden" scroll="no">
	<t:formvalid formid="formobj" dialog="true" usePlugin="password"
		layout="table" action="charteredPriceController.do?save">
		<input id="id" name="id" type="hidden" value="${charteredPrice.id }">
		
		<table style="width: 600px;" cellpadding="0" cellspacing="1"
			class="formtable">

			<tr>
				<td align="right"><label class="Validform_label"> 选择城市:
				</label></td>
				<td class="value"><select id="city" name="city" datatype="*">
						<option value="">--请选择城市--</option>
						<c:forEach var="c" items="${cities}">
							<option value="${c.cityId}"
								<c:if test="${charteredPrice.cityId == c.cityId}" >selected="selected"</c:if>>
								${c.cityName}</option>
						</c:forEach>
				</select> <span class="Validform_checktip"></span></td>
			</tr>

			<tr>
				<td align="right"><label class="Validform_label"> 车辆类型:
				</label></td>

				<td class="value"><t:dictSelect id="carType" field="carType"
						typeGroupCode="car_type" hasLabel="false"
						defaultVal="${charteredPrice.carType}" datatype="*"></t:dictSelect>
					<span class="Validform_checktip"></span></td>
			</tr>

			<tr>
				<td align="right"><label class="Validform_label"> 选择套餐:
				</label></td>

				<td class="value"><select id="packageId" name="packageId"
					datatype="*">
						<option value="">--请选择套餐--</option>
						<c:forEach var="c" items="${CharteredPackage}">
							<option value="${c.id}"
								<c:if test="${charteredPrice.packageId == c.id}" >selected="selected"</c:if>>
								${c.name}</option>
						</c:forEach>
				</select> <span class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						起步价（元）: </label></td>
				<td class="value"><input class="inputxt" id="initiatePrice"
					name="initiatePrice" ignore="ignore"
					value="${charteredPrice.initiatePrice}" datatype="d"> <span
					class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						超公里/元: </label></td>
				<td class="value"><input class="inputxt" id="exceedKmPrice"
					name="exceedKmPrice" ignore="ignore"
					value="${charteredPrice.exceedKmPrice}" datatype="d"> <span
					class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						超时长/元: </label></td>
				<td class="value"><input class="inputxt" id="exceedTimePrice"
					name="exceedTimePrice" ignore="ignore"
					value="${charteredPrice.exceedTimePrice}" datatype="d"> <span
					class="Validform_checktip"></span></td>
			</tr>

			<tr>
				<td align="right"><label class="Validform_label">
						空反费（公里/元）: </label></td>
				<td class="value"><input class="inputxt" id="emptyReturn"
					name="emptyReturn" ignore="ignore"
					value="${charteredPrice.emptyReturn}" datatype="d"> <span
					class="Validform_checktip"></span></td>
			</tr>
			<tr>
				<td align="right"><label class="Validform_label">
						附加服务(非必填): </label></td>
				<td>
					<div id="p" class="easyui-panel"
						style="width: 478px; height: 180px; padding: 10px;">

						<c:forEach var="c" items="${charteredAppendService}">
							<input type="checkbox" id="charteredAppendId"
								name="charteredAppendId" value="${c.id}" />

							<label id="serviceName" class="Validform_label"
								style="display: inline-block; width: 65px;">${c.serviceName}</label> &nbsp;&nbsp; <span
								id="serviceDescription"><label class="Validform_label">${c.serviceDescription}</label></span>
							<br>
						</c:forEach>

					</div>

				</td>

			</tr>
			<tr>
				<td align="right"><label class="Validform_label"> 备注: </label>
				</td>
				<td class="value"><input class="inputxt" id="remark"
					name="remark" ignore="ignore" value="${charteredPrice.remark}">
					<span class="Validform_checktip"></span></td>
			</tr>

		</table>
	</t:formvalid>
</body>