<%@ page language="java" import="java.util.*,java.text.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>线路排班</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <!-- <script src="plug-in/tools/popup/driverSelect.js"></script> -->
  <script src="plug-in/tools/popup/carSelect.js"></script>
  <script src="plug-in/kalendae/yui-min.js"></script>
  <script>
	YUI({
	    modules: {
	        'trip-calendar': {
	            fullpath: 'plug-in/kalendae/trip-calendar.js',
	            type    : 'js',
	            requires: ['trip-calendar-css']
	        },
	        'trip-calendar-css': {
	            fullpath: 'plug-in/kalendae/trip-calendar.css',
	            type    : 'css'
	        }
	    }
	}).use('trip-calendar', function(Y) {
	    
	    var oCal = new Y.TripCalendar({
	        minDate         : new Date,     //最小时间限制
	        triggerNode     : '#J_DepDate', //第一个触节点
	        finalTriggerNode: '#J_EndDate'  //最后一个触发节点
	    });
	    
	    //api方法中的点击日历事件
	    oCal.on('dateclick', function(e) {
	        
	    });
	    
	    //校验
	    Y.one('#J_Search').on('click', function(e) {
	        e.halt();
	        var rDate    = /^((19|2[01])\d{2})-(0?[1-9]|1[012])-(0?[1-9]|[12]\d|3[01])$/;
	            oDepDate = Y.one('#J_DepDate'),
	            oEndDate = Y.one('#J_EndDate'),
	            sDepDate = oDepDate.get('value'),
	            sEndDate = oEndDate.get('value'), 
	            aMessage = ['请选择出发日期', '请选择返程日期', '返程时间不能早于出发时间，请重新选择', '日期格式错误'],
	            iError   = -1;   
	            switch(!0) {
	                case !sDepDate:
	                    oDepDate.focus();
	                    iError = 0;
	                    break;
	                case !rDate.test(sDepDate):
	                    oDepDate.focus();
	                    iError = 3;
	                    break;
	                case !sEndDate:
	                    oEndDate.focus();
	                    iError = 1;
	                    break;
	                case !rDate.test(sEndDate):
	                    oEndDate.focus();
	                    iError = 3;
	                    break;
	                case sDepDate.replace(/-/g, '') > sEndDate.replace(/-/g, ''):
	                    oEndDate.focus();
	                    iError = 2;
	                    break;
	            };
	            if(iError > -1) {
	                this.set('message', aMessage[iError]).showMessage();   
	                formStatus = false;
	            }
	            else {
	            	formStatus = true;
	            }
	    }, oCal);
	});
	
	var formStatus = false;
	
	function bclick(){
		$("#J_Search").click();
		//console.log(formStatus);
		
		return formStatus;
	}

</script>
<style>

#search .f-text {
	padding:3px;
	width:179px;
	height:18px;
	color:#666;
	line-height:18px;
	font-family:inherit;
	border-color:#AFAFAF #DCDCDC #DCDCDC #AFAFAF;
	border-width:0 1px 1px 0;
	background-position:0 -100px;
}

#search .f-label {
	margin-top:10px;
	padding:3px;
	color:#666;
	line-height:18px;
	font-family:inherit;
	border-color:#AFAFAF #DCDCDC #DCDCDC #AFAFAF;
	border-width:0 1px 1px 0;
	background-position:0 -100px;
}

</style>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="lineArrangeController.do?save" beforeSubmit="bclick()" >
			<input id="id" name="id" type="hidden" value="${lineArrangePage.id }" >
			<input id="lineId" name="lineId" type="hidden" value="${lineId }">
			<input id="prevLicencePlateId" name="prevLicencePlateId" type="hidden" value="${lineArrangePage.licencePlateId}">
			<input id="J_Search" type="hidden" value="跳转时间"  />
			<table style="width: 600px;" cellpadding="0" cellspacing="1" class="formtable">
				<tr>
					<td align="right">
						<label class="Validform_label">
							发车时间:
						</label>
					</td>
					<td class="value">
						<input class="Wdate" onClick="WdatePicker({dateFmt:'HH:mm'})"  style="width: 150px" id="departDate" name="departDate" ignore="ignore"
							     placeholder="时：分" value="<c:if test='${lineArrangePage.departDate != null and lineArrangePage.departDate != ""}'><fmt:formatDate value='<%=new SimpleDateFormat( "HH:mm" ).parse(request.getAttribute("dDate").toString()) %>' type="time" pattern="HH:mm"/></c:if>">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<%-- <tr>
					<td align="right">
						<label class="Validform_label">
							线路排班状态:
						</label>
					</td>
					<td class="value">
						<input class="inputxt" id="arrangeStatus" name="arrangeStatus" ignore="ignore"
							   value="${lineArrangePage.arrangeStatus}">
						<span class="Validform_checktip"></span>
					</td>
				</tr> --%>
				<tr>
					<td align="right">
						<label class="Validform_label">
							发车开始日期:
						</label>
					</td>
					<td class="value">
						<%-- <input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width: 150px" id="startDate" name="startDate" ignore="ignore"
							     value="<fmt:formatDate value='${lineArrangePage.startDate}' type="date" pattern="yyyy-MM-dd"/>"> --%>
					    <input id="J_DepDate" name="J_DepDate" type="text" class="f-text" value="${lineArrangePage.startDate}" style="width: 200px" />
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				<tr>
					<td align="right">
						<label class="Validform_label">
							发车停止日期:
						</label>
					</td>
					<td class="value">
						<%-- <input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width: 150px" id="endDate" name="endDate" ignore="ignore"
							     value="<fmt:formatDate value='${lineArrangePage.endDate}' type="date" pattern="yyyy-MM-dd"/>"> --%>
					     <input id="J_EndDate" name="J_EndDate" type="text" class="f-text" value="${lineArrangePage.endDate}" style="width: 200px" />
						<span class="Validform_checktip"></span>
					</td>
				</tr>
				
				<tr>
					<td align="right">
						<label class="Validform_label">
							车牌:
						</label>
					</td>
					<td class="value">
						<%-- <select id="licencePlateId" name="licencePlateId" datatype="*">
							<c:forEach items="${carList}" var="c">
								<option value="${c.id}" <c:if test="${c.id==lineArrangePage.licencePlateId}">selected="selected"</c:if>>${c.licencePlate}</option>
							</c:forEach>
						</select> --%>
						
						<c:choose>
						    <c:when test="${lineArrangePage.licencePlateId != null}">
						       <c:forEach items="${carList}" var="c">
									<c:if test="${c.id==lineArrangePage.licencePlateId}">
										<input readonly="true" class="inputxt" id="licencePlateName" name="licencePlateName" value="${c.licencePlate}" onclick="openCarSelect_car()">
									</c:if>
							   </c:forEach>
						    </c:when>
						    <c:otherwise>
						        <input readonly="true" class="inputxt" id="licencePlateName" name="licencePlateName" value="" datatype="*" onclick="openCarSelect_car()">
						    </c:otherwise>
						</c:choose>
						
						<input id="licencePlateId" name="licencePlateId" type="hidden" value="${lineArrangePage.licencePlateId}" >
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
						<%-- <select id="driverId" name="driverId" datatype="*" onchange="driverChange(this.value)" >
							<c:forEach items="${driversList}" var="d">
								<option value="${d.id}" <c:if test="${d.id==lineArrangePage.driverId}">selected="selected"</c:if>>${d.name}</option>
							</c:forEach>
						</select> --%>
						
						<c:choose>
						    <c:when test="${lineArrangePage.driverId != null}">
						       <c:forEach items="${driversList}" var="d">
									<c:if test="${d.id==lineArrangePage.driverId}">
										<input readonly="true" class="inputxt" id="driverName" name="driverName" value="${d.name}" ><!-- onclick="openDriverSelect_driver()" -->
						    			<!-- <label>请先清空司机列表  </label> -->
									</c:if>
								</c:forEach>
						    </c:when>
						    <c:otherwise>
						        <input readonly="true" class="inputxt" id="driverName" name="driverName" value="" datatype="*" ><!-- onclick="openDriverSelect_driver()" -->
						    </c:otherwise>
						</c:choose>
						
						<!-- <input readonly="true" type="text" id="driverName" name="driverName" style="width: 120px" value="" onclick="openDriverSelect_driver()"> -->
						<input id="driverId" name="driverId" type="hidden" value="${lineArrangePage.driverId}" >
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
						    <c:when test="${lineArrangePage.licencePlateId != null}">
						        <c:forEach items="${carList}" var="c">
									<c:if test="${c.id==lineArrangePage.licencePlateId}">
										<input readonly="true" class="inputxt" id="seat" name="seat" value="${c.seat}" >
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
							   value="${lineArrangePage.remark}">
						<span class="Validform_checktip"></span>
					</td>
				</tr>
			</table>
		</t:formvalid>
 </body>