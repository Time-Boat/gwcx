<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
	<!-- <div data-options="region:'east'" title="线路排班" style="width:600px;padding:0px;border:0px;">
		<div id="lacc" ></div>
	</div> -->
	<div id="p" data-options="region:'center'" title="" style="padding:0px;border:0px;">
	  <script type="text/javascript">
	/* $(document).ready(function(){
		$("input[name='departDate_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
		$("input[name='createDate_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
		$("input[name='birthday']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
	}); */
	
	function update(title,url, id,width,height,isRestful) {
		gridname=id;
		var rowsData = $('#'+id).datagrid('getSelections');
		if (!rowsData || rowsData.length==0) {
			tip('请选择编辑项目');
			return;
		}
		if (rowsData.length>1) {
			tip('请选择一条记录再编辑');
			return;
		}
		if(isRestful!='undefined'&&isRestful){
			url += '/'+rowsData[0].id;
		}else{
			url += '&id='+rowsData[0].id + '&lineId=' + rowsData[0].lineId + "";
		}
		
		createwindow(title,url,width,height);
	}
	
/* 	 $(function () {
		   //JS 加载调用
		   $('#lacc').calendar({
		    width : 600,
		    height : 600,
		    fit : false,
		    border : false,
		    firstDay : 0,
		    weeks : ['S','M','T','W','T','F','S'],
		    months : ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug',
		     'Sep', 'Oct', 'Nov', 'Dec'],
		    year : 2016,
		    month : 6,
		    current : new Date(2016,12,17)
		   });
	  }); 
	 
	 $('#lacc').calendar({
		onSelect: function(date){
			alert(date.getFullYear()+":"+(date.getMonth()+1)+":"+date.getDate());
			//$('#lacc').calendar('moveTo', new Date(2012, 6, 1));
			console.log($('#lacc').calendar('options'));
			$('#lacc').calendar({
				formatter: function (date) {
					alert(1);
		            return "<br/>" + date.getDate()+'<div style="position:absolute; top:5px; right:3px; width:60px;">Tip info.</div>';
	        }});
		}
	}); 
	
	 var d1 = Math.floor((Math.random()*30)+1);  
	 var d2 = Math.floor((Math.random()*30)+1);  
	 function formatDay(date){  
		alert(date+"aaaaaaaaaaaaaaaaa");
	 	var m = date.getMonth()+1;  
	 	var d = date.getDate();  
	 	var opts = $(this).calendar('options');  
	 	if (opts.month == m && d == d1){  
	 		return '<div class="icon-ok md">' + d + '</div>';  
	 	} else if (opts.month == m && d == d2){  
	 		return '<div class="icon-search md">' + d + '</div>';  
		}  
		return d;  
	 }  */
	 
	 function putTicket(id,lineId){
 		createwindow("线路放票", "lineArrangeController.do?addorupdate&id="+id+"&lineId="+lineId,"700px","400px");
 	}
	 
</script>
<style scoped="scoped">
	.md{
		height:16px;
		line-height:16px;
		background-position:2px center;
		text-align:right;
		font-weight:bold;
		padding:0 2px;
		color:red;
	}
</style>
  <t:datagrid name="lineArrangeList" title="线路排班" actionUrl="lineArrangeController.do?datagrid" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="线路id" field="lineId" width="120"></t:dgCol>
   <t:dgCol title="线路名称" field="name" width="120"></t:dgCol>
   <t:dgCol title="线路负责人" field="director" width="120"></t:dgCol>
   <t:dgCol title="发车时间" field="departDate" width="120"></t:dgCol>
   <%-- <t:dgCol title="线路排班状态" field="arrangeStatus" width="120"></t:dgCol> --%>
   <t:dgCol title="司机" field="driverName" width="120"></t:dgCol>
   <t:dgCol title="车牌" field="licencePlate" width="120"></t:dgCol>
   <t:dgCol title="备注" field="remark" width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <%-- <t:dgDelOpt title="删除" url="lineArrangeController.do?del&id={id}" /> --%>
   <%-- <t:dgToolBar title="录入" icon="icon-add" url="lineArrangeController.do?addorupdate" funname="add"></t:dgToolBar> --%>
   <%-- <t:dgToolBar title="排班" icon="icon-edit" url="lineArrangeController.do?addorupdate" funname="update"></t:dgToolBar> --%>
   <%-- <t:dgToolBar title="查看" icon="icon-search" url="lineArrangeController.do?addorupdate" funname="detail"></t:dgToolBar> --%>
   <t:dgFunOpt funname="putTicket(id,lineId)" title="排班"></t:dgFunOpt>
  </t:datagrid>
  </div>
 </div>


