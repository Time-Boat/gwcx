<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>接送订单处理</title>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <script type="text/javascript">
    
  //重写了回调,然后自己控制关闭以及刷新
    function noteSubmitCallback(data) {
	  	var win = frameElement.api.opener;//获取父窗口
		//刷新主表单父窗口
		win.reloadTable();
	    //win.reloadtransferOrderList();
		//关闭当前弹出框
		frameElement.api.close();
    }//@ sourceURL=test.js
    
	</script> 
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid  formid="transferOrderListInfo"  dialog="true" usePlugin="password" layout="table" beforeSubmit="" action="charteredOrderController.do?" >

	<input id="ids" name="ids" hidden="true" value="${ids}">
	<input id="slDate" hidden="true" value="${slDate}">
	<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable" id = "formtableId">
			<tr>
				<td align="center" colspan="2"> 
					<label class="Validform_label"> 请安排发车时间:</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						发车时间: 
					</label>
				</td>
				<td class="value">
					<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" style="width: 150px" id="startDate" name="startDate" ignore="ignore">
					<span class="Validform_checktip"></span>
				</td>
			</tr>
			
			<tr>
				<td align="center" colspan="2">
					<label class="Validform_label">
						请选择车辆安排: 
					</label>
				</td>
			</tr>
			<tr>
				<td align="right">
					<label class="Validform_label">
						车牌:
					</label>
				</td>
					<td class="value">					
						<input readonly="true" class="inputxt" id="licencePlateName" name="licencePlateName" value="">
						<input id="licencePlateId" name="licencePlateId" type="hidden" value="" >
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				
				<tr>
					<td align="right">
						<label class="Validform_label">
							司机:
						</label>
					</td>
					<td class="value">						  
						<input readonly="true" class="inputxt" id="driverName" name="driverName" value="" datatype="*" ><!-- onclick="openDriverSelect_driver()" -->
						<input id="driverId" name="driverId" type="hidden" value="" >
						</div>
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				
				<tr>
					<td align="right">
						<label class="Validform_label">
							座位:
						</label>
					</td>
					<td class="value">
						<c:choose>
						    <c:when test="">
						        <c:forEach items="" var="c">
									<c:if test="">
										<input readonly="true" class="inputxt" id="seat" name="seat" value="" >
									</c:if>
								</c:forEach>
						    </c:when>
						    <c:otherwise>
						        <input readonly="true" class="inputxt" id="seat" name="seat" value="" datatype="*">
						    </c:otherwise>
						</c:choose>
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
							   value="">
						<span class="Validform_checktip"></span>
					</td>
				</tr>

		</table>
</t:formvalid>
<script type="text/javascript">
	$("#licencePlateName").click(function(){
		var startTime = $("#startDate").val();
		//var endTime = $("#endDate").val();
		//if(startTime!=""&&endTime!=""){
		if(startTime!=""){
			 	$.dialog.setting.zIndex = 9999;
			    $.dialog({content: 'url:transferOrderController.do?addCar', zIndex: 2100, lock: true, width:1000, height:600, opacity: 0.4, button: [
			        {name:'确定',  
			        	callback:function(){
			        		var iframe = this.iframe.contentWindow;
			        	    var rowsData = iframe.$('#carInfoList').datagrid('getSelections');
			        	    if (!rowsData || rowsData.length==0) {
			        	        return;
			        	    }else{
			        	    	var licencePlate = rowsData[0].licencePlate;//车牌
			        	    	var licencePlateId = rowsData[0].id;//车牌id
			        	    	var seat = rowsData[0].seat;//座位数
			        	    	//司机姓名和id
			        	    	var name = rowsData[0].name;
			        	    	var driverId = rowsData[0].driverId;
			        	    	$("#driverName").val(name);
			        	    	$("#driverId").val(driverId);
			        	    	
			        	    	
			        	    	$("#licencePlateName").val(licencePlate);
			        	    	$("#licencePlateId").val(licencePlateId);
			        	    	$("#seat").val(seat);
			        	    }
			        	},
			        	focus:true
			        },
			        {name:'取消',callback:function (){}}
			    ]}).zindex();
		}else{
			tip('请填发车时间');
			return;
		}
	});
	
</script>
</body>