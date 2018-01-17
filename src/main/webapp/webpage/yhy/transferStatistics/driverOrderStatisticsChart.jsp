<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<html>
<head>
    <title>司机统计图表</title>
      <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <script src="plug-in/tools/popup/echarts.min.js"></script>
<script src="plug-in/tools/popup/userSelect.js"></script>
<script src="plug-in/tools/popup/departSelect.js"></script>

<style type="text/css">
#formtables{
padding-left: 30px;
}
#driverinfo{
float:left;
}
#datainfo{
float:left;
}
.button:hover {background-color: #cccccc}
.button:focus{
  background-color: #cccccc;
}
</style>

</head>

<body>
    <div region="center" border="false" style="height: 80px; overflow: hidden;" id="southDiv">
		<table align="center" cellpadding="10" cellspacing="0" class="formtable" id="formtables">
			<tr>
			<td align="right" colspan="5" >
				<div id="datainfo" >
					<label class="Validform_label"> 时间段： </label>
					<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}'})" style="width: 100px;height: 27px"  id="startDate" name="startDate" >&nbsp;&nbsp;
					<label class="Validform_label">至 </label>&nbsp;&nbsp;
					<input class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}'})" style="width: 100px;height: 27px"  id="endDate" name="endDate" >&nbsp;&nbsp;
					
					<!-- <span id="two-inputs"><input id="date-range200" size="20" value=""> 至 <input id="date-range201" size="20" value=""></span> -->
				</div>
				
				<div id="driverinfo">
					<label class="Validform_label"> &nbsp;&nbsp;司机名称: </label>
					<select id="driver" name="driver" onchange="getDriverOrderStatisticsChart()">
						<option value="">--请选择司机--</option>
						<c:forEach var="c" items="${driverList}">
							<option value="${c.id}">
								${c.name}
							</option>
						</c:forEach> 
					</select> 
					<%-- <input type="text" value="${accountList}" id="accounts" type="hidden" /> --%>
				</div>
			</td>
			
			<td align="center">&nbsp;&nbsp;
				<a href="#" class="button" id="dayStatistic" onclick="dayStatistic()">&nbsp;&nbsp;日统计&nbsp;&nbsp;</a>
				<a href="#" class="button" id="weekStatistic" onclick="weekStatistic()">&nbsp;&nbsp;周统计&nbsp;&nbsp;</a>
				<a href="#" class="button" id="minStatistic" onclick="minStatistic()">&nbsp;&nbsp;月统计&nbsp;&nbsp;</a>
				<a href="#" class="button" id="yearStatistic" onclick="yearStatistic()">&nbsp;&nbsp;年统计&nbsp;&nbsp;</a>
			</td>
		</tr>
		</table>
	</div>
	
    <div region="south" border="false" style="height: 600px; overflow: hidden;" id="southDiv">
		<div id="main" style="width:100%;height:600px;"></div>
	</div>
	
	
     <script type="text/javascript">
     
     var timestamp="0";
     
     function dayStatistic(){
    	 timestamp="1";
    	 getDriverOrderStatisticsChart();
     }
     
     function weekStatistic(){
    	 timestamp="2";
    	 getDriverOrderStatisticsChart();
     }
     
     function minStatistic(){
    	 timestamp="3";
    	 getDriverOrderStatisticsChart();
     }
     
     function yearStatistic(){
    	 timestamp="4";
    	 getDriverOrderStatisticsChart();
     }
     
     $(function() {
    	 getDriverOrderStatisticsChart();
     });
     
     function getDriverOrderStatisticsChart(){
    	 var driver = $("#driver").val();
    	 var startDate = $("#startDate").val();
    	 var endDate = $("#endDate").val();
    	 
    	 $.ajax({
				type : 'POST',
				url : "transferStatisticsController.do?getDriverOrderStatisticsChart&driver="+driver+"&startDate="+startDate+"&endDate="+endDate+"&timestamp="+timestamp,
				success : function(ds) {
					var obj = eval('(' + ds + ')');
					var orederTime = new Array();
					var orederPrice = new Array();
					var orderNum =  new Array();
					if(obj.indexOf("orderTime")>0){
						var objs = eval('(' + obj + ')');
						for (var i = 0; i < objs.data.length; i++) {
							orederTime.push(objs.data[i].orderTime);
							orederPrice.push(objs.data[i].orderTotalPrice);
							orderNum.push(objs.data[i].orderNum);
						}
					}
					
					var legendData = "日统计";
					var xAxisName = "时间/日";
					var xAxisData =orederTime;
					var seriesData1 = orederPrice;
					var seriesData2 = orderNum;
					
					if(timestamp=='0' || timestamp=='1' ){
						legendData ="日统计";
						xAxisName = "时间/日";
					}else if(timestamp=='2'){
						legendData ="周统计";
						xAxisName = "时间/日";
					}else if(timestamp=='3'){
						legendData ="月统计";
						xAxisName = "时间/月";
					}else if(timestamp=='4'){
						legendData ="年统计";
						xAxisName = "时间/年";
					}
					
					var myChart = echarts.init(document.getElementById('main'));
			        var colors = ['#5793f3', '#d14a61', '#675bba'];
			        option = {
			        	    tooltip : {
			        	        trigger: 'axis'
			        	    },
			        	    noDataLoadingOption: {
		                        text: '暂无数据',
		                        effect: 'bubble',
		                        effectOption: {
		                            effect: {
		                                n: 0
		                            }
		                        }
		 					},
			        	    legend: {
			        	        data:['订单收入','订单数量']
			        	    },
			        	    toolbox: {
			        	        show : true,
			        	        feature : {
			        	        	dataZoom: {
			                            yAxisIndex: 'none'
			                        },
			        	            mark : {show: true},
			        	            dataView : {show: true, readOnly: false},
			        	            magicType : {show: true, type: ['line', 'bar']},
			        	            restore : {show: true},
			        	            saveAsImage : {show: true}
			        	        }
			        	    }, 
			        	    calculable : true,
			        	    xAxis : [
			        	        {
			        	        	name : xAxisName,
			        	            type : 'category',
			        	            data : xAxisData
			        	        }
			        	    ],
			        	    yAxis : [
			        	        {
			        	        	name : '收入/元--张 ',
			        	            type : 'value',
			        	            axisLabel : {
			        	                formatter: '{value}'
			        	            }
			        	        }
			        	    ],
			        	    series : [
			        	        {
			        	            name:'订单收入',
			        	            type:'line',
			        	            data:seriesData1
			        	        },
			        	        {
			        	            name:'订单数量',
			        	            type:'line',
			        	            data:seriesData2
			        	        }
			        	    ]
			        	};

			        myChart.setOption(option);
				}
			});
     }
        
    </script>
</body>
</html>