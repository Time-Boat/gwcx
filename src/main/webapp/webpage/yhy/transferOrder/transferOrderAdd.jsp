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
    
    function checkDepartTime(){
    	var date1 = $('#startDate').val();
    	var date2 = $('#slDate').val();     //选中的订单中最小的时间io
    	//date1 = Date.parse(new Date(date1)); 
    	date1 = date1.replace(/-/g,"/");//替换字符，变成标准格式 
    	date2 = date2.replace(/-/g,"/");//替换字符，变成标准格式 
    	var d1 = Date.parse(new Date(date1)); 
    	var d2 = Date.parse(new Date(date2)); 
    	console.log('d1:'+d1);
    	console.log('d2:'+d2);
    	var date3 = (d2-d1)/1000;
    	//var date3 = date2 - date1/1000;  //时间差的毫秒数
    	console.log('date1:'+date1);
    	console.log('date2:'+date2);
    	console.log('date3:'+date3);
    	if(date3 >= 0){
    		var leave1=date3%(24*3600);    //计算天数后剩余的毫秒数
    		var hours=Math.floor(leave1/(3600));
    		
    		//计算相差分钟数
    		var leave2=leave1%(3600)        //计算小时数后剩余的毫秒数
    		var minutes=Math.floor(leave2/(60))
    		//计算相差秒数
    		var leave3=leave2%(60)      //计算分钟数后剩余的毫秒数
    		var seconds=Math.round(leave3)
    		
    		console.log(hours+"--"+minutes+"--"+seconds);
    		var mm = hours*60+minutes;
    		console.log(hours*60+minutes);
    		if(mm < 15){
    			return true;
    		}
    		tip("相隔时间不能超过15分钟");
    	}else{
    		tip("时间必须早于订单的出发时间");
    	}
    	
    	return false;
    }
    
    $(function(){
    	var m = $('#slDate').val();     //选中的订单中最小的时间
    	if(m == '' || m == null) return;
    	var date =m;
    	console.log(date);
    	var s = date.length;
    	//var da = date.substring(0,date.length-3);
    	var da =new Date(date.replace(/-/g,"/")).Format("yyyy-MM-dd hh:mm")
    	$('#startDate').val(da);
    });
    
    //格式化日期
    Date.prototype.Format = function (fmt) {
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "h+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt))
            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o){
            if (new RegExp("(" + k + ")").test(fmt)) {
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            }
        }
        return fmt;
    }

	</script> 
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid  formid="transferOrderListInfo"  dialog="true" usePlugin="password" layout="table" beforeSubmit="checkDepartTime()" action="transferOrderController.do?saveCarAndDriver&lineOrderCode=${lineOrderCode}&ids=${ids}" callback="@Override noteSubmitCallback" >

	<input id="ids" name="ids" hidden="true" value="${ids}">
	<input id="slDate" hidden="true" value="${slDate}">
	<input id="lineOrderCode" hidden="true" value="${lineOrderCode}">
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
			<!--  <tr>
				<td align="right"><label class="Validform_label">
						发车停止日期: </label></td>
				<td class="value">
					<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 150px" id="endDate" name="endDate" ignore="ignore">
				</td>
			</tr> -->
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