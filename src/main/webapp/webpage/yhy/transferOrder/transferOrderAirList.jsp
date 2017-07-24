<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="plug-in/tools/popup/userSelect.js"></script>
<script src="plug-in/tools/popup/departSelect.js"></script>
<div class="easyui-layout" fit="true">
<div region="center" style="padding:0px;border:0px">
	<t:datagrid name="transferOrderList" title="接送订单处理" autoLoadData="true" actionUrl="transferOrderController.do?airdatagrid" fitColumns="true"
			    idField="id" fit="true" queryMode="group" checkbox="true"  >
		<t:dgCol title="id" field="id" hidden="true"></t:dgCol>
		<t:dgCol title="订单编号" field="orderId" query="true"></t:dgCol>
		<t:dgCol title="出发时间" field="orderStartime" editor="datebox" formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group" align="center"></t:dgCol>
		<t:dgCol title="所属线路名称" field="lineName" align="center"></t:dgCol>
		<t:dgCol title="司机姓名" field="name" align="center"></t:dgCol>
		<t:dgCol title="司机联系电话" field="phoneNumber" align="center"></t:dgCol>
		<t:dgCol title="车票数量" field="orderNumbers" align="center"></t:dgCol>
		<t:dgCol title="联系人" field="orderContactsname" align="center"></t:dgCol>
		<t:dgCol title="联系人手机号" field="orderContactsmobile" align="center"></t:dgCol>
		<t:dgCol title="起点站" field="orderStartingstation" query="true" align="center"></t:dgCol>
		<t:dgCol title="终点站" field="orderTerminusstation" query="true" align="center"></t:dgCol>
		<t:dgCol title="订单类型" field="orderType" replace="接机_2,送机_3" query="true" align="center"></t:dgCol>
		<t:dgCol title="订单状态" field="orderStatus" replace="订单已完成_0,已付款待审核_1,审核通过待发车 _2,待付款_6" query="true" align="center"></t:dgCol>
		<t:dgCol title="下单时间" field="applicationTime" formatter="yyyy-MM-dd hh:mm:ss" align="center"></t:dgCol>
		<t:dgCol title="单价(人/元)" field="orderUnitprice" align="center"></t:dgCol>
		<t:dgCol title="总价(元)" field="orderTotalPrice" align="center"></t:dgCol>
		<t:dgCol title="所属城市" field="cityName" align="center"></t:dgCol>
		<t:dgCol title="航班号" field="orderFlightnumber" align="center"></t:dgCol>
		<t:dgCol title="车牌号" field="licencePlate" align="center"></t:dgCol>
		
		<t:dgCol title="火车车次" field="orderTrainnumber" align="center"></t:dgCol>
		<t:dgCol title="购票人手机号" field="custphone" align="center"></t:dgCol>
		<t:dgCol title="支付方式" field="orderPaytype" replace="微信_0,支付宝_1,银联_2" align="center"></t:dgCol>
		<t:dgCol title="支付状态" field="orderPaystatus"  replace="已付款_0,未付款_3" align="center"></t:dgCol>
		<t:dgCol title="预计到达时间" field="orderExpectedarrival" editor="datebox" formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group" align="center"></t:dgCol>
		<%-- <t:dgCol title="人数" field="orderNumberPeople" align="center"></t:dgCol> --%>
		<%-- <t:dgCol title="操作" field="opt"  align="center"></t:dgCol> --%> 
		<t:dgToolBar title="司机车辆安排" icon="icon-edit" url="transferOrderController.do?editCarAndDriver" funname="editCarAndDriver"></t:dgToolBar>
	</t:datagrid>
</div>
</div>
<input type="hidden" value="${carplateList}" id="carpaltes" />
<input type="hidden" value="${driverList}" id="driverNames" />
<input type="hidden" value="${lineNameList}" id="lineNames" />
<script type="text/javascript">
	//进入触发 
	$(function() {
		$('#transferOrderList').datagrid({   
		    rowStyler:function(index,row){
		    	if (row.orderStatus=="0"){   
		            return 'color:#999';   
		        }
		        if (row.orderStatus=="1"){   
		            return 'color:red';   
		        }
		        if (row.orderStatus=="2"){   
		            return 'color:#5400FF';   
		        }
		        
		    }   
		});
			var json1 = $("#carpaltes").val();
			var json2= $("#driverNames").val();
			var json = $("#lineNames").val();
			var obj = eval('(' + json + ')');
			var obj1 = eval('(' + json1 + ')');
			var obj2 = eval('(' + json2 + ')');
			
			var a1 = '<span style="display:-moz-inline-box;display:inline-block; padding:10px 2px;"><span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;';
	 		var a2 = 'text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; "title="所属线路">所属线路：</span>';
			var a3 = '<select name="lineId" style="width: 150px">';
			var c1 = '<option value="">选择所属线路</option>';
			for (var i = 0; i < obj.data.length; i++) {
				c1 += '<option value="'+obj.data[i].lineId+'">' + obj.data[i].lineName
						+ '</option>';
			}
			var a4 = '</select></span>';
			
			var aa1 = '<span style="display:-moz-inline-box;display:inline-block; padding:0px 10px;"><span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;';
	 		var aa2 = 'text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; "title="司机">司机：</span>';
			var aa3 = '<select name="driverId" style="width: 150px">';
			var cc1 = '<option value="">选择司机</option>';
			for (var i = 0; i < obj2.data.length; i++) {
				cc1 += '<option value="'+obj2.data[i].driverId+'">' + obj2.data[i].driverName
						+ '</option>';
			}
			var aa4 = '</select></span>';
			
			var aaa1 = '<span style="display:-moz-inline-box;display:inline-block; padding:0px 10px;"><span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;';
	 		var aaa2 = 'text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; "title="车票号">车牌号：</span>';
			var aaa3 = '<select name="carId" style="width: 150px">';
			var ccc1 = '<option value="">选择车牌号</option>';
			for (var i = 0; i < obj1.data.length; i++) {
				ccc1 += '<option value="'+obj1.data[i].carId+'">' + obj1.data[i].licencePlate
						+ '</option>';
			}
			var aaa4 = '</select></span>';
			
			$("#transferOrderListForm").append(a1 + a2 + a3 + c1 + a4 + aa1 + aa2+aa3 + cc1 + a4 +aaa1 + aaa2+ aaa3 + ccc1 + aaa4);
	});

	$(document).ready(function(){
		$("input[name='orderStartime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
		$("input[name='orderStartime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});	
		$("input[name='orderExpectedarrival_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
		$("input[name='orderExpectedarrival_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});			 
	});

	//安排车辆司机 
	function editCarAndDriver(title,url,id,width,height){
		var ids = '';
		var rows = $("#transferOrderList").datagrid("getSelections");
		var lineId = rows[0].lineId;
		var ds = rows[0].orderStartime;
		for(var i=0;i<rows.length;i++){
			
			//判断订单状态，如果是未支付就提示不让安排司机车辆
			if(rows[i].orderStatus == '6'){
				tip('未付款的订单不能安排车辆');
				return;
			}
			
			//判断选中的订单是不是在同一条线路上
			if(lineId == rows[i].lineId){
				lineId = rows[i].lineId;
			}else{
				tip('请选择同一条线路');
				return;
			}
			if(i != 0){
				//console.log(better_time(ds,rows[i].orderStartime));
				if(!better_time(ds,rows[i].orderStartime)){
					tip('批量处理的订单相差时间不能超过1个小时');
					return;
				}
			}else{
				/* console.log(rows[i].orderStartime);
				console.log(rows[i].orderStartime);
				console.log(new Date(rows[i].orderStartime));
				console.log(Date.parse(new Date(rows[i].orderStartime))); */
				slDate = Date.parse(new Date(rows[i].orderStartime)); 
			}
			
			ids+=rows[i].id;
			ids+=',';
		}
		ids = ids.substring(0,ids.length-1);
		if(ids.length==0){
			tip('请选择项目');
			return;
		}
		//console.log(slDate);
		
		url += '&ids='+ids+'&slDate='+(slDate/1000);
		//console.log(url);
		createwindow(title,url,width,height);
		/* $("#function-transferOrderAdd").panel(
			{
				title :'司机车辆信息',
				href: url
			}
		); */
	}
	
	//把最小的时间发到后台，在新增填写发车时间的时候和这个最小时间进行比较
	var slDate = '';
	
	function better_time(strDateStart,strDateEnd){
		var date1 = Date.parse(new Date(strDateStart));  
		var date2 = Date.parse(new Date(strDateEnd));    
		//console.log(date1);
		//console.log(date2);
		var date3 = date2 - date1;  //时间差的毫秒数
		
		if(date3 >= 0){
			slDate = date1;
		}else{
			slDate = date2;
		}
		
		date3 = Math.abs(date3);
		
		console.log(date3);
		
		//计算出相差天数
		var days=Math.floor(date3/(24*3600*1000));
		
		//计算出小时数

		var leave1=date3%(24*3600*1000);    //计算天数后剩余的毫秒数
		var hours=Math.floor(leave1/(3600*1000));
		//计算相差分钟数
		var leave2=leave1%(3600*1000);        //计算小时数后剩余的毫秒数
		var minutes=Math.floor(leave2/(60*1000));
		//计算相差秒数
		var leave3=leave2%(60*1000);      //计算分钟数后剩余的毫秒数
		var seconds=Math.round(leave3/1000);
		//console.log(" 相差 "+days+"天 "+hours+"小时 "+minutes+" 分钟"+seconds+" 秒");
		
		if(days == 0 && hours == 0){
			return true;
		}
		
	  	return false;
	}
	
</script>
