<%@ page language="java" import="java.util.*,java.text.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
 <head>
  <title>线路排班</title>
  <t:base type="jquery,easyui,tools,DatePicker"></t:base>
  <!-- <script src="plug-in/tools/popup/driverSelect.js"></script> -->
  <script src="plug-in/tools/popup/carSelect.js"></script>
  <script type="text/javascript">

  </script>
 </head>
 <body style="overflow-y: hidden" scroll="no">
  <t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="lineArrangeController.do?save">
			<input id="id" name="id" type="hidden" value="${lineArrangePage.id }">
			<input id="lineId" name="lineId" type="hidden" value="${lineId }">
			<input id="prevLicencePlateId" name="prevLicencePlateId" type="hidden" value="${lineArrangePage.licencePlateId}">
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
						<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width: 150px" id="startDate" name="startDate" ignore="ignore"
							     value="<fmt:formatDate value='${lineArrangePage.startDate}' type="date" pattern="yyyy-MM-dd"/>">
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
						<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})"  style="width: 150px" id="endDate" name="endDate" ignore="ignore"
							     value="<fmt:formatDate value='${lineArrangePage.endDate}' type="date" pattern="yyyy-MM-dd"/>">
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