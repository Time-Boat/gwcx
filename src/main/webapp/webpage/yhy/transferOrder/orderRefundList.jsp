<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="plug-in/tools/popup/userSelect.js"></script>
<script src="plug-in/tools/popup/departSelect.js"></script>
<style>
	/* white */ 
	.white { 
		color: #606060; 
		border: solid 1px #b7b7b7; 
		background: #fff; 
		background: -webkit-gradient(linear, left top, left bottom, from(#fff), to(#ededed)); 
		background: -moz-linear-gradient(top, #fff, #ededed); 
		filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ffffff', endColorstr='#ededed'); 
	} 
	.white:hover { 
		background: #ededed; 
		background: -webkit-gradient(linear, left top, left bottom, from(#fff), to(#dcdcdc)); 
		background: -moz-linear-gradient(top, #fff, #dcdcdc); 
		filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ffffff', endColorstr='#dcdcdc'); 
	} 
	.white:active { 
		color: #999; 
		background: -webkit-gradient(linear, left top, left bottom, from(#ededed), to(#fff)); 
		background: -moz-linear-gradient(top, #ededed, #fff); 
		filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ededed', endColorstr='#ffffff'); 
	} 

</style>
<div class="easyui-layout" fit="true">
<div region="center" style="padding:0px;border:0px">
	<t:datagrid name="orderRefundList" title="接送订单处理" autoLoadData="true" actionUrl="orderRefundController.do?datagrid" fitColumns="true"
			    idField="id" fit="true" queryMode="group"  >
		<t:dgCol title="id" field="id" hidden="true"></t:dgCol>
		<t:dgCol title="订单编号" field="orderId" query="true"></t:dgCol>
		<t:dgCol title="起点站" field="orderStartingstation" query="true" align="center"></t:dgCol>
		<t:dgCol title="终点站" field="orderTerminusstation" query="true" align="center"></t:dgCol>
		<t:dgCol title="出发时间" field="orderStartime" editor="datebox" formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group" align="center"></t:dgCol>
		<t:dgCol title="总价(元)" field="orderTotalPrice" align="center"></t:dgCol>
		<t:dgCol title="车票数量" field="orderNumbers" align="center"></t:dgCol>
		<t:dgCol title="支付方式" field="orderPaytype" replace="微信_0,支付宝_1,银联_2" align="center"></t:dgCol>
		<t:dgCol title="联系人" field="orderContactsname" align="center"></t:dgCol>
		<t:dgCol title="联系人手机号" field="orderContactsmobile" align="center"></t:dgCol>
		<t:dgCol title="支付状态" field="orderPaystatus" replace="退款中_1,已退款_2,已拒绝_4" align="center"></t:dgCol>
		<t:dgCol title="所属城市" field="cityName" align="center"></t:dgCol>
		<t:dgCol title="司机姓名" field="name" align="center"></t:dgCol>
		<t:dgCol title="司机联系电话" field="phoneNumber" align="center"></t:dgCol>
		<t:dgCol title="申请退款时间" field="refundTime" align="center"></t:dgCol>
		<t:dgCol title="拒绝原因" field="rejectReason" align="center"></t:dgCol>
		<t:dgCol title="订单状态" field="orderStatus" replace="取消订单待退款_3,取消订单完成退款_4,拒绝退款_5" query="true" align="center"></t:dgCol>
		<t:dgCol title="操作" field="opt" align="center"></t:dgCol>
		<t:dgFunOpt exp="orderStatus#eq#3" funname="agreeRefund(id)" title="同意" ></t:dgFunOpt>
		<t:dgFunOpt exp="orderStatus#eq#3" funname="rejectRefund(id)" title="拒绝" ></t:dgFunOpt>
		<t:dgFunOpt exp="orderStatus#eq#5" funname="lookRejectReason(id)" title="查看原因" ></t:dgFunOpt>
	</t:datagrid>
</div>

<div id="win" class="easyui-window" title="退款原因" style="width:400px;height:300px"
    data-options="modal:true" closed="true" >
    <div class="easyui-layout" data-options="fit:true">
		<div style="text-align: center; " data-options="region:'center'">
			<input type="hidden" id="dialog_order_id" value="" />
			<h5>请填写拒绝退款原因</h5>
			<textarea id="rejectReason" type="text" style="width:70%;height:40%;resize:none;" rows="5" cols="7"></textarea>
			<div style="margin-top: 30px">
				<input id="sub" type="button" class="button white" value="提交" style="margin-right: 50px;width:50px;height:30px" onclick="submitRefund()" />
				<input id="cal" type="button" class="button white" value="取消" style="width:50px;height:30px" onclick="javascript:$('#win').window('close');"/>
			</div>
		</div>
    </div>
</div>

</div>
<script type="text/javascript">
	$(document).ready(function(){
		$("input[name='orderStartime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
		$("input[name='orderStartime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});	
		$("input[name='orderExpectedarrival_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
		$("input[name='orderExpectedarrival_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});			 
	});

	function agreeRefund(id){
		$.dialog.confirm('确定同意退款吗？',function(r){
			if(!r){
				return;
			}else{
				$.ajax({
		            type:"post",
		            url:"orderRefundController.do?agreeRefund",
		            data:"id="+id,
		            async: false,
		            dataType:'json',
		            success:function(d){
		           		var json = eval('('+d.jsonStr+')');
		           		tip(json.msg);
			           	//刷新当前窗体
			           	$('#orderRefundList').datagrid('reload');
		            }
		        });
			}
		});
	}
	
	function rejectRefund(id){
		$('#win').window('open'); // open a window
		$('#rejectReason').attr("readonly",false);
		$('#rejectReason').val("");
		$('#sub').show();
		$('#dialog_order_id').val(id);
	}
	
	function submitRefund(){
		var id = $('#dialog_order_id').val();
		var rejectReason = $('#rejectReason').val();
		if(rejectReason == ""){
			tip("请填写拒绝原因");
			return;
		}
		$.ajax({
            type:"post",
            url:"orderRefundController.do?rejectRefund",
            data:"id="+id+"&rejectReason="+rejectReason,
            dataType:'json',
            success:function(d){
           		var json = eval('('+d.jsonStr+')');
           		tip(json.msg);
           		$('#win').window('close');
	           	//刷新当前窗体
	           	$('#orderRefundList').datagrid('reload');
            }
        });
	}
	
	function lookRejectReason(id){
		$('#win').window('open');
		$('#rejectReason').attr("readonly",true);
		$('#sub').hide();
		$.ajax({
            type:"get",
            url:"orderRefundController.do?getReason&id="+id,
            dataType:'json',
            success:function(d){
           		var obj = eval('('+d.jsonStr+')');
           		$('#rejectReason').val(obj.msg);
            }
        });
		
	}
	
</script>
 
