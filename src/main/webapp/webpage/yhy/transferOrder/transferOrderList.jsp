<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="plug-in/tools/popup/userSelect.js"></script>
<script src="plug-in/tools/popup/departSelect.js"></script>
<div class="easyui-layout" fit="true">
<div region="center" style="padding:0px;border:0px">
	<t:datagrid name="transferOrderList" title="接送订单处理" autoLoadData="true" actionUrl="transferOrderController.do?datagrid" fitColumns="true"
			    idField="id" fit="true" queryMode="group" checkbox="true"  >
		<t:dgCol title="id" field="id" hidden="true"></t:dgCol>
		<t:dgCol title="订单编号" field="orderId" query="true"></t:dgCol>
		<t:dgCol title="订单类型" field="orderType" replace="接机_2,送机_3,接火车 _4,送火车_5" query="true" align="center"></t:dgCol>
		
		<t:dgCol title="航班号" field="orderFlightnumber" align="center"></t:dgCol>
		<t:dgCol title="起点站" field="orderStartingstation" query="true" align="center"></t:dgCol>
		<t:dgCol title="终点站" field="orderTerminusstation" query="true" align="center"></t:dgCol>
		<t:dgCol title="出发时间" field="orderStartime" editor="datebox" formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group" align="center"></t:dgCol>
		<t:dgCol title="预计到达时间" field="orderExpectedarrival" editor="datebox" formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group" align="center"></t:dgCol>
		<%-- <t:dgCol title="人数" field="orderNumberPeople" align="center"></t:dgCol> --%>
		<t:dgCol title="单价(人/元)" field="orderUnitprice" align="center"></t:dgCol>
		<t:dgCol title="总价(元)" field="orderTotalPrice" align="center"></t:dgCol>
		<t:dgCol title="车票数量" field="orderNumbers" align="center"></t:dgCol>
		<t:dgCol title="支付方式" field="orderPaytype" replace="微信_0,支付宝_1,银联_2" align="center"></t:dgCol>
		<t:dgCol title="联系人" field="orderContactsname" align="center"></t:dgCol>
		<t:dgCol title="联系人手机号" field="orderContactsmobile" align="center"></t:dgCol>
		<t:dgCol title="支付状态" field="orderPaystatus"  replace="已付款_0,退款中_1,已退款_2,未付款_3" align="center"></t:dgCol>
		<t:dgCol title="火车车次" field="orderTrainnumber" align="center"></t:dgCol>
		<t:dgCol title="所属线路名称" field="lineName" align="center"></t:dgCol>
		<%-- <t:dgCol title="所属线路id" field="lineId" align="center"></t:dgCol> --%>
		<t:dgCol title="司机姓名" field="name" align="center"></t:dgCol>
		<t:dgCol title="司机联系电话" field="phoneNumber" align="center"></t:dgCol>
		<t:dgCol title="车牌号" field="licencePlate" align="center"></t:dgCol>
		<t:dgCol title="订单状态" field="orderStatus" replace="订单已完成_0,已付款待审核_1,审核通过待发车 _2,取消订单待退款_3,取消订单完成退款_4" query="true" align="center"></t:dgCol>
		<%-- <t:dgCol title="操作" field="opt"  align="center"></t:dgCol> --%> 
		<t:dgToolBar title="司机车辆安排" icon="icon-edit" url="transferOrderController.do?editCarAndDriver" funname="editCarAndDriver"></t:dgToolBar>
	</t:datagrid>
</div>
</div>
<script type="text/javascript">
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
		console.log(slDate);
		
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
 
