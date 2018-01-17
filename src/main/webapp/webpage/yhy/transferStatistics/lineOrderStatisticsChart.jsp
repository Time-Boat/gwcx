<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<html>
<head>
    <title>线路统计图表</title>
      <t:base type="jquery,easyui,tools,DatePicker"></t:base>
    <script src="plug-in/tools/popup/echarts.min.js"></script>
<script src="plug-in/tools/popup/userSelect.js"></script>
<script src="plug-in/tools/popup/departSelect.js"></script>

<style type="text/css">
#formtables{
padding-left: 30px;
}
#lineinfoModel{
float:left;
}
#linetypeModel{
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
				<div id="linetypeModel"> 
					<label class="Validform_label"> 线路类型: </label>
					<t:dictSelect id="lineType" field="type" extendJson="{onchange:getLineName()}" typeGroupCode="transferTy" hasLabel="false"   ></t:dictSelect>	
				</div>
				<div id="lineinfoModel">
					<label class="Validform_label"> &nbsp;&nbsp;线路名称: </label>
					<select id="lineinfo" name="lineinfo" onchange="getLineOrderStatisticsChart()">
						<option value="">--请选择线路--</option>
						<c:forEach var="c" items="${linelist}">
							<option value="${c.id}">
								${c.name}
							</option>
						</c:forEach> 
					</select> 
					<%-- <input type="text" value="${accountList}" id="accounts" type="hidden" /> --%>
				</div>
				<input type="hidden" value="${linelist}" id="linelies" />
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
     
    /*  $(function() {
    	 getLineName();
     }); */
     
   //根据线路类型获取线路和起点站、终点站
 	function getLineName() {
	   
 		var lineType = $('#lineType option:selected').val();//获取选中线路类型 
 		$.ajax({
    		   url: 'transferStatisticsController.do?getLineName&ordertype='+lineType,
    		   dataType: 'json',
    		   success : function(ds) {
					
					var d1 = '<option value="">--请选择线路--</option>';
					var obj = eval('(' + ds + ')');
					var objs = obj.lineinfo;
					
					if(objs.length > 0){
						for (var i = 0; i < objs.length; i++) {
							d1 += '<option value="'+objs[i].lineId+'">'+objs[i].lineName+ '</option>';
						}
					}
					
					$("#lineinfo").empty();//先置空 
					$("#lineinfo").append(d1);
    		   }
      	});
 		getLineOrderStatisticsChart();
   	}
     
     var timestamp="0";
     
     function getDealerinfo(){
    	 var userType = $("#userType").val();
    	 if(userType=='1'){
    		 $("#dealerinfo").show();
    	 }else{
    		 $("#dealerinfo").hide();
    	 }
    	 getLineOrderStatisticsChart();
     }
     
     function dayStatistic(){
    	 timestamp="1";
    	 getLineOrderStatisticsChart();
     }
     
     function weekStatistic(){
    	 timestamp="2";
    	 getLineOrderStatisticsChart();
     }
     
     function minStatistic(){
    	 timestamp="3";
    	 getLineOrderStatisticsChart();
     }
     
     function yearStatistic(){
    	 timestamp="4";
    	 getLineOrderStatisticsChart();
     }
     
     $(function() {
    	 getLineOrderStatisticsChart();
     });
     
     function getLineOrderStatisticsChart(){
    	 var lineType = $("#lineType").val();
    	 var lineId = $("#lineinfo").val();
    	 var startDate = $("#startDate").val();
    	 var endDate = $("#endDate").val();
    	 
    	 $.ajax({
				type : 'POST',
				url : "transferStatisticsController.do?getLineOrderStatisticsChart&lineType="+lineType+"&lineId="+lineId+"&startDate="+startDate+"&endDate="+endDate+"&timestamp="+timestamp,
				success : function(ds) {
					var obj = eval('(' + ds + ')');
					var orederTime = new Array();
					var sumcount = new Array();
					var seriesList = new Array();
					if(obj.indexOf("orderTime")>0){
						var objs = eval('(' + obj + ')');
						for (var i = 0; i < objs.data.length; i++) {
							orederTime.push(objs.data[i].orderTime);
							sumcount.push(objs.data[i].sumcount);
							var seriesData = objs.data[i].seriesData;
							var seriesLists = eval('(' + seriesData + ')');
							seriesList.push(seriesLists);
						}
					}
					
					var legendData = "日统计";
					var xAxisName = "时间/日";
					var xAxisData =orederTime;
					
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
			        
			        var colorList = ["#FFFF99","#B5FF91","#94DBFF","#FFBAFF","#FFBD9D"]; 
			        
			          option = {
			        	    tooltip : {
			        	        trigger: 'axis',
			        	        	formatter: function(datas){
			        	                  var res = datas[0].name +'<br/>', val;
			        	                  res +='线路名称 &nbsp;&nbsp;&nbsp;&nbsp;成交次数'+'<br/>';
			        	                  res +='总次数：共'+datas[0].value+'次(只显示前5条线路)'+'<br/>';
			        	                  for(var i = 0;i<datas.length;i++){
			        	                	   val=datas[i].value;
			        	                	   var index = datas[i].dataIndex;
			        	                	   
			        	                	  if(val>0){
			        	                		  var lienList = seriesList[index];
			        	                		  var lineLength = 0;
			        	                		  
			        	                		  if(lienList.length>0){
			        	                			  if(lienList.length>5){
				        	                			  lineLength=5;
				        	                		  }else{
				        	                			  lineLength=lienList.length;
				        	                		  }
			        	                			  for(var j = 0;j<lineLength;j++){
			        	                				  var lineName =lienList[j].name;
			        	                				  var lineData = lienList[j].data;
			        	                				  res += '<span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:'+colorList[j]+'"></span>'+lineName + '：' + lineData + '<br/>';
				        	                		  }
			        	                		  }
			        	                		 
			        	                		  
			        	                	  }
			        	                  }
			        	                  return res;
			        	               }
			        	
			        	    },
			        	    legend: {
			        	    	x: 'left', 
			        	        data:'线路统计',
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
			        	   
			        	    toolbox: {
			                    show: false,
			                    feature: {
			                        dataZoom: {
			                            yAxisIndex: 'none'
			                        },
			                        dataView: {readOnly: false},
			                        magicType: {type: ['line', 'bar']},
			                        restore: {},
			                        saveAsImage: {}
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
			        	        	name : '使用频率/次数',
			        	            type : 'value',
			        	            axisLabel : {
			        	                formatter: '{value}'
			        	            }
			        	        }
			        	    ],
			        	    
			        	    series : [
					        	        {
					        	            name: '线路统计',
					 			           type: 'line',
					 			           data: sumcount
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