<%@ page language="java" import="com.yhy.lin.entity.LineArrangeViewEntity" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
	<link href='plug-in/fullcalendar/fullcalendar/fullcalendar.css' rel='stylesheet' />
	<link href='plug-in/fullcalendar/fullcalendar/fullcalendar.print.css' rel='stylesheet' media='print' />
	<script src='plug-in/jquery/jquery-1.9.1.js'></script>
	<script src='plug-in/fullcalendar/fullcalendar/fullcalendar.js'></script>
	<script src="plug-in/kalendae/yui-min.js"></script>

<script>
/** 当天信息初始化   fullCalendar **/   
	$(function(){
		var dayDate = new Date();
		var d = $.fullCalendar.formatDate(dayDate,"dddd");
		var m = $.fullCalendar.formatDate(dayDate,"yyyy年MM月dd日");
		var lunarDate = lunar(dayDate);
		$(".alm_date").html(m + "&nbsp;" + d);
		$(".today_date").html(dayDate.getDate())
		$("#alm_cnD").html("农历"+ lunarDate.lMonth + "月" + lunarDate.lDate);
		$("#alm_cnY").html(lunarDate.gzYear+"年&nbsp;"+lunarDate.gzMonth+"月&nbsp;"+lunarDate.gzDate+"日");
		$("#alm_cnA").html("【"+lunarDate.animal+"年】");
		var fes = lunarDate.festival();
		if(fes.length>0){
			$(".alm_lunar_date").html($.trim(lunarDate.festival()[0].desc));
			$(".alm_lunar_date").show();
		}else{
			$(".alm_lunar_date").hide();
		}
	});
	$(document).ready(function() {
		
		var date = new Date();
		var d = date.getDate();
		var m = date.getMonth();
		var y = date.getFullYear();
		ticket(1);
		$('#J_DepDate').val($('#periodStart').val());
		$('#J_EndDate').val($('#periodEnd').val());
		$('#calendar').fullCalendar({
			header: {
				left: 'prev,next today',
				center: 'title',
				right: 'month,basicWeek,basicDay'
			},
			editable: false,
			events: [
				{
					title: '班车',
					start: new Date($('#periodStart').val()),
					end: new Date($('#periodEnd').val())
				}
			],
			dayClick : function(dayDate, allDay, jsEvent, view) { //点击单元格事件
				
				var d = $.fullCalendar.formatDate(dayDate,"dddd");
				var m = $.fullCalendar.formatDate(dayDate,"yyyy年MM月dd日");
				var lunarDate = lunar(dayDate);
				$(".alm_date").html(m + "&nbsp;" + d);
				$(".today_date").html(dayDate.getDate())
				$("#alm_cnD").html("测试"+ lunarDate.lMonth + "月" + lunarDate.lDate);
				$("#alm_cnY").html(lunarDate.gzYear+"年&nbsp;"+lunarDate.gzMonth+"月&nbsp;"+lunarDate.gzDate+"日");
				$("#alm_cnA").html("【"+lunarDate.animal+"年】");
				$("#alm_cnB").html("");
				$("#alm_cnC").html("");
				var fes = lunarDate.festival();
				if(fes.length>0){
					$(".alm_lunar_date").html($.trim(lunarDate.festival()[0].desc));
					$(".alm_lunar_date").show();
				}else{
					$(".alm_lunar_date").hide();
				}
				// 当天则显示“当天”标识
				var now = new Date();
				if (now.getDate() == dayDate.getDate() && now.getMonth() == dayDate.getMonth() && now.getFullYear() == dayDate.getFullYear()){
					$(".today_icon").show();
				}else{
					$(".today_icon").hide();
				}
			},
			eventClick: function(event, jsEvent, view) {
				var dayDate = event.start;
				var endDate = event.end;
				
				var m = $.fullCalendar.formatDate(dayDate,"yyyy年MM月dd日");
				
				var e = "";
				if(event.end != "" && event.end != null){
					e = "-" + $.fullCalendar.formatDate(endDate,"yyyy年MM月dd日");
				}else{
					e = "";
				}
				$(".alm_date").html(m + e);
				
				//var lunarDate = lunar(dayDate);
				
				$(".today_date").html(dayDate.getDate())
				$("#alm_cnD").html("司机：${lineArrangeViewPage.driverName}");
				$("#alm_cnY").html("车牌号：${lineArrangeViewPage.licencePlate}");
				$("#alm_cnA").html("手机号：${lineArrangeViewPage.phoneNumber}");
				$("#alm_cnC").html("座位数：${lineArrangeViewPage.seat}");
				var fes = lunarDate.festival();
				if(fes.length>0){
					$(".alm_lunar_date").html($.trim(lunarDate.festival()[0].desc));
					$(".alm_lunar_date").show();
				}else{
					$(".alm_lunar_date").hide();
				}
				// 当天则显示“当天”标识
				var now = new Date();
				if (now.getDate() == dayDate.getDate() && now.getMonth() == dayDate.getMonth() && now.getFullYear() == dayDate.getFullYear()){
					$(".today_icon").show();
				}else{
					$(".today_icon").hide();
				}
			},
			loading : function(bool) {   //日历加载完成之后的回调
				//但是并没有被回调
				//alert(1);
				if (bool){
					
					$("#msgTopTipWrapper").show();
				}else{
					$("#msgTopTipWrapper").fadeOut();
				}
			}
			
		});
		
		var tJson = eval('(' + $('#tickets').val() + ')');
		//console.log(tJson);
		//$("#calendar").fullCalendar('addEventSource',  [{title:'add',start:new Date(),end:d}] );
		for(var i=0;i<tJson.length;i++){
			$("#calendar").fullCalendar('addEventSource',  [{title:'已售票数：'+tJson[i].count,start:new Date(tJson[i].tDate),end:d}] );
		}
		
	});
	/** 绑定事件到日期下拉框 **/
	$(function(){
		//document.getElementById("J_DepDate").addEventListener("input", putTicket(this));
		$("#fc-dateSelect").delegate("select","change",function(){
			var fcsYear = $("#fcs_date_year").val();
			var fcsMonth = $("#fcs_date_month").val();
			$("#calendar").fullCalendar('gotoDate', fcsYear, fcsMonth);
		});
	});
	
	function putTicket(value){
		console.log(1);
	}
	
</script>

<!-- 选择开始时间和结束时间的日历 -->
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
        //console.log(e.date);//当前选择日期
        //console.log(e.dateInfo);//当前选择日期的日期信息
        
        ticket(2);
        
        /* console.log(date1);
        console.log(date2);
        console.log(d1);
        console.log(d2);
        console.log(o1);
        console.log(o2);
        console.log(d3); */
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
            } else {
            	var startDate = $('#startDate').val();
                var endDate = $('#endDate').val();
                var d1 = new Date(startDate.replace(/\-/g, "\/"));  
                var d2 = new Date(endDate.replace(/\-/g, "\/"));  
                
                var d3 = new Date($('#J_DepDate').val().replace(/\-/g, "\/"));  
                var d4 = new Date($('#J_EndDate').val().replace(/\-/g, "\/"));  
                
                //console.log('d1:'+d1+'  d2:'+d2+'  d3:'+d3+'  d4:'+d4);
                
                if(d3 >= d1 && d4 <= d2){
	            	formStatus = true;
                }else{
                	oDepDate.focus();
                	this.set('message', '放票日期必须在排班日期之内(排班日期：'+startDate.split(' ')[0]+'至'+endDate.split(' ')[0]+')').showMessage();
	                formStatus = false;
                }
                console.log(formStatus);
            }
    }, oCal);
});

	var formStatus = false;

	function bclick(){
		$("#J_Search").click();
		//console.log(formStatus);
		return formStatus;
	}

	/**计算放票日期*/
	function ticket(v1){
		
		//v1   判断是不是初次进来
	
		var date1 = $('#J_DepDate').val();
        var date2 = $('#J_EndDate').val();
        
        if(v1 == 1){
	        date1 = '${lineArrangeViewPage.periodStart}';
	        date2 = '${lineArrangeViewPage.periodEnd}';
        }
        if(date1 != '' && date2 != ''){
        	var o1 = date1.split('-');
        	var o2 = date2.split('-');
        	
        	if(v1 == 1){
        		//不生效$("#calendar").fullCalendar('gotoDate', o1[0], o1[1]);
            }
        	
        	var d1 = new Date(o1[0], o1[1]-1, o1[2]);
        	var d2 = new Date(o2[0], o2[1]-1, o2[2]);
        	
        	var d3 = (d2 - d1)/1000 / 60 / 60 /24;
        	
        	//console.log(d2-d1);
        	//console.log(d3);
        	
        	if(d3++ < 0){
        		d3 = 0;
        	}
        	
        	$('#putTicket').html("您选择了" + d3 + "个放票日期");
        	
        }
	}

</script>

<style>

	body {
		margin-top: 40px;
		text-align: center;
		font-size: 14px;
		font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
	}
	
	.dib{display: inline-block;}
	.fr{float: right;}
	.calendarWrapper {
	width: 1190px;
	margin: 0 auto 15px;
	}

#calendar {
	width: 885px;
	background: #fff;
	padding: 15px 10px;
}

.calendarWrapper .rightSidePanel {
	width: 240px;
	padding: 0px 15px;
}

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
<body>
	<div id="msgTopTipWrapper" style="display:none">
		<div id="msgTopTip">
			<span><i class="iconTip"></i>正在载入日历数据...</span>
		</div>
		<input type='hidden' id='periodEnd' value='${lineArrangeViewPage.periodEnd}' />
		<input type='hidden' id='periodStart' value='${lineArrangeViewPage.periodStart}' />
		<input id="startDate" name="startDate" value="${lineArrangeViewPage.startDate}" type="hidden" />
		<input id="endDate" name="endDate" value="${lineArrangeViewPage.endDate}" type="hidden" />
		<input type='text' id='tickets' value="${tickets}" />
	</div>
	<div class="calendarWrapper">
		<div class="rightSidePanel mb50 fr">
			<div id="div_day_detail" class="h_calendar_alm">
				<div class="alm_date"></div>
				<div class="alm_content nofestival">
					<div class="today_icon"></div>
					<div class="today_date"></div>
					<p id="alm_cnD"></p>
					<p id="alm_cnY"></p>
					<p id="alm_cnA"></p>
					<p id="alm_cnB"></p>
					<p id="alm_cnC"></p>
					<div class="alm_lunar_date"></div>
				</div>
			</div>
			<t:formvalid formid="formobj" dialog="true" usePlugin="password" layout="table" action="lineArrangeViewController.do?save" beforeSubmit="bclick()" >
				<input id="aId" name="aId" value="${lineArrangeViewPage.id}" type="hidden" />
				<div class="h_calendar_alm">
					<div id="search" class="alm_content nofestival">
						<div>
							<label class="f-text" for="J_DepDate">出发时间：</label>
			        		<input id="J_DepDate" name="J_DepDate" type="text" class="f-text" value=""  />
						</div>
						<div>
				        	<label class="f-text" for="J_EndDate">返程时间：</label>
				        	<input id="J_EndDate" name="J_EndDate" type="text" class="f-text" value=""  /> <!-- oninput="putTicket(this.value)" onpropertychange="putTicket(this.value)" -->
						</div>
	        			<div class="f-label" id="putTicket" >您选择了0个放票日期</div>
	        			<div class="f-label" >放票数量：${lineArrangeViewPage.seat}张/辆</div>
	        			<div class="f-label" >票价${lineArrangeViewPage.ticketPrice}（元）</div>
						<%-- <div>
							<input type="text" class="f-text" name="ticketPrice" value="${lineArrangeViewPage.ticketPrice}" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" />
						</div> --%>
						<input id="J_Search" type="hidden" value="跳转时间"  />
					</div>
				</div>
			</t:formvalid>
		</div>
		<div id="calendar" class="dib"></div>
	</div>
	
</body>
</html>
