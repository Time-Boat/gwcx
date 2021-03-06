<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="plug-in/tools/popup/userSelect.js"></script>
<script src="plug-in/tools/popup/departSelect.js"></script>
<div class="easyui-layout" fit="true">
<div region="center" style="padding:0px;border:0px">
	<t:datagrid name="transferDriverList" title="接送车司机安排" autoLoadData="true" actionUrl="transferOrderController.do?driverdatagrid" 
			    idField="id" fit="true" queryMode="group" onDblClick="transferOrderList">
		<t:dgCol title="id" field="id" hidden="true"></t:dgCol>
		<t:dgCol title="线路订单码" field="lineOrderCode" ></t:dgCol>
		<t:dgCol title="所属线路名称" field="lineName" align="center"></t:dgCol>
		<t:dgCol title="线路类型" field="lineType" dictionary="transferTy" align="center" query="true"></t:dgCol>
		<t:dgCol title="车票数量" field="orderNumber" align="center"></t:dgCol>
		<t:dgCol title="已安排车票数量" field="alreadyarranged" align="center"></t:dgCol>
		<t:dgCol title="未安排车票数量" field="notarranged" align="center"></t:dgCol>
		
		<t:dgCol title="所属城市" field="cityName" align="center"></t:dgCol>
		
		<%--<t:dgToolBar title="司机车辆安排" icon="icon-edit" url="transferOrderController.do?editCarAndDriver" funname="editCarAndDriver"></t:dgToolBar>--%> 
	</t:datagrid>
</div>
</div>
<input type="hidden" value="${lineNameList}" id="lineNames" />
<input type="hidden" value="${cityList}" id="citylie" />
<script type="text/javascript">
	//进入触发 
	$(function() {
		var json = $("#lineNames").val();
		var json1 = $("#citylie").val();
		
		var a1 = '<span style="display:-moz-inline-box;display:inline-block; padding:10px 2px;"><span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;';
 		var a2 = 'text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; "title="所属线路">所属线路：</span>';
		var a3 = '<select id="lineId" name="lineId" style="width: 150px">';
		var c1 = '<option value="">选择所属线路</option>';
		
		if(json.indexOf("lineId")>0){
			var obj = eval('(' + json + ')');
			for (var i = 0; i < obj.lineinfo.length; i++) {
				c1 += '<option value="'+obj.lineinfo[i].lineId+'">' + obj.lineinfo[i].lineName+ '</option>';
			}
		}
		
		var a4 = '</select></span>';
		
		var aa1 = '<span style="display:-moz-inline-box;display:inline-block; padding:10px 2px;"><span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;';
		var aa2 = 'text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; "title="选择城市">选择城市：</span>';
		var aa3 = '<select name="cityID" style="width: 150px">';
		var cc1 = '<option value="">选择城市</option>';
		
		if(json1.indexOf("cityID")>0){
			var obj1 = eval('(' + json1 + ')');
			for (var i = 0; i < obj1.data.length; i++) {
				cc1 += '<option value="'+obj1.data[i].cityID+'">' + obj1.data[i].cityName+ '</option>';
			}
		}
		
		var aa4 = '</select></span>';
		$("#transferDriverListForm").append(a1 + a2 + a3 + c1 + a4 + aa1 + aa2 + aa3 + cc1 + aa4);
			
	});
	
	$(function() {
		getLineName();
	});

	function getLineName() {
		
		$("select[name='lineType']").change(
				function() {
					var ordertype = $(this).children('option:selected').val(); //当前选择项的值
					var url = "transferStatisticsController.do?getLineName&ordertype="+ ordertype;
					$.ajax({
						type : 'POST',
						url : url,
						success : function(ds) {
							var d1 = '<option value="">选择线路</option>';
							var obj = eval('(' + ds + ')');
							if(obj.indexOf("lineId")>0){
								var objs = eval('(' + obj + ')');
								for (var i = 0; i < objs.lineinfo.length; i++) {
									d1 += '<option value="'+objs.lineinfo[i].lineId+'">' + objs.lineinfo[i].lineName+ '</option>';
								}
							}
							$("#lineId").empty();//先置空 
							$("#lineId").append(d1);
						}
					});
				});
	}

	// 安排车辆司机 
	function editCarAndDriver(title,url,id,width,height){
		
		var rows = $("#transferDriverList").datagrid("getSelections");
		if(rows.length!=0){
			var lineOrderCode = rows[0].lineOrderCode;
			var sdate=lineOrderCode.substring(11);
			console.log(lineOrderCode);
			url += '&lineOrderCode=' + lineOrderCode+'&sdate='+sdate;
			createwindow(title,url,width,height);
		}else{
			tip('请选择项目');
			return;
		}
	
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
	
	function addTab(title, url){
		if ($('.page-tabs J_menuTabs').tabs('exists', title)){
			$('.page-tabs J_menuTabs').tabs('select', title);
		} else {
			var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
			$('.page-tabs J_menuTabs').tabs('add',{
				title:title,
				content:content,
				closable:true
			});
		}
	}
	
	
	function transferOrderList(rowIndex,rowData) {
		///addTab("接送机订单处理","transferOrderController.do?getOrderdetail&lineOrderCode="+rowData.lineOrderCode+"&lineType="+rowData.lineType);
		if(rowData.lineType=="2" || rowData.lineType=="3"){
			addOneTab("接送机订单处理", "transferOrderController.do?getOrderdetail&lineOrderCode="+rowData.lineOrderCode+"&lineType="+rowData.lineType);
		}else if(rowData.lineType=="4" || rowData.lineType=="5"){
			addOneTab("接送火车订单处理", "transferOrderController.do?getOrderdetail&lineOrderCode="+rowData.lineOrderCode+"&lineType="+rowData.lineType); 
		}
	}
	
</script>

