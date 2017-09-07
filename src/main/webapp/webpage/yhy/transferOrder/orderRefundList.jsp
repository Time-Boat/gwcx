<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<script src="plug-in/tools/popup/userSelect.js"></script>
<script src="plug-in/tools/popup/departSelect.js"></script>
<link href="plug-in/tools/css/rejectReason.css" type="text/css" rel="stylesheet"/>
<div class="easyui-layout" fit="true">
<div region="center" style="padding:0px;border:0px">
	<t:datagrid name="orderRefundList" title="接送机退款管理" autoLoadData="true" actionUrl="orderRefundController.do?datagrid" fitColumns="true"
			    idField="id" fit="true" queryMode="group" checkbox="true">
		<t:dgCol title="id" field="id" hidden="true"></t:dgCol>
		<t:dgCol title="订单编号" field="orderId" query="true"></t:dgCol>
		<t:dgCol title="订单类型" field="orderType" dictionary="transferTy" query="true" align="center"></t:dgCol>
		<t:dgCol title="起点站" field="orderStartingstation" query="true" align="center"></t:dgCol>
		<t:dgCol title="终点站" field="orderTerminusstation" query="true" align="center"></t:dgCol>
		<t:dgCol title="下单时间" field="applicationTime" formatter="yyyy-MM-dd hh:mm:ss" align="center"></t:dgCol>
		<t:dgCol title="出发时间" field="orderStartime" editor="datebox" formatter="yyyy-MM-dd hh:mm:ss" query="true" queryMode="group" align="center"></t:dgCol>
		<t:dgCol title="总价(元)" field="orderTotalPrice" align="center"></t:dgCol>
		<t:dgCol title="车票数量" field="orderNumbers" align="center" ></t:dgCol>
		<t:dgCol title="支付方式" field="orderPaytype" replace="微信_0,支付宝_1,银联_2" align="center"></t:dgCol>
		<t:dgCol title="联系人" field="orderContactsname" align="center"></t:dgCol>
		<t:dgCol title="联系人手机号" field="orderContactsmobile" align="center"></t:dgCol>
		<t:dgCol title="支付状态" field="orderPaystatus" replace="已付款_0,退款中_1,已退款_2,已拒绝_4" align="center"></t:dgCol>
		<t:dgCol title="申请退款时间" field="refundTime" formatter="yyyy-MM-dd hh:mm:ss" align="center" query="true" queryMode="group" ></t:dgCol>
		<t:dgCol title="退款金额" field="refundPrice" align="center"></t:dgCol>
		<t:dgCol title="所属城市" field="cityName" align="center"></t:dgCol>
		<t:dgCol title="初审时间" field="firstAuditDate" align="center"></t:dgCol>
		<t:dgCol title="初审人" field="firstAuditUser" align="center"></t:dgCol>
		<t:dgCol title="初审状态" field="firstAuditStatus" dictionary="first_audit_status" align="center"></t:dgCol>
		<t:dgCol title="复审时间" field="lastAuditDate" align="center"></t:dgCol>
		<t:dgCol title="复审人" field="lastAuditUser" align="center"></t:dgCol>
		<t:dgCol title="复审状态" field="lastAuditStatus"  dictionary="last_audit_status" align="center"></t:dgCol>
		<%-- <t:dgCol title="司机姓名" field="name" align="center"></t:dgCol>
		<t:dgCol title="司机联系电话" field="phoneNumber" align="center"></t:dgCol> --%>
		<t:dgCol title="订单状态" field="orderStatus" replace="取消订单待退款_3,取消订单完成退款_4,拒绝退款_5" dictionary="orderStatus" query="true" align="center"></t:dgCol>
		<%-- <t:dgCol title="操作" field="opt" align="center"></t:dgCol>
		<t:dgFunOpt exp="orderStatus#eq#3" funname="agreeRefund(id,orderTotalPrice)" title="同意" ></t:dgFunOpt>
		<t:dgFunOpt exp="orderStatus#eq#3" funname="rejectRefund(id)" title="拒绝" ></t:dgFunOpt>
		<t:dgFunOpt exp="orderStatus#eq#5" funname="lookRejectReason(id)" title="查看原因" ></t:dgFunOpt> --%>
		<t:dgToolBar operationCode="detail" title="查看详情" icon="icon-search" url="transferOrderController.do?addorupdate" funname="detail"></t:dgToolBar>
		<t:dgToolBar title="批量同意" icon="icon-edit" url="orderRefundController.do?doAgreeALLSelect" funname="AgreeALLSelect"></t:dgToolBar>
		<%-- <t:dgToolBar title="批量拒绝" icon="icon-edit" url="" funname="RefuseALLSelect"></t:dgToolBar> --%>
	</t:datagrid>
</div>

<div id="win" class="easyui-window" title="退款" style="width:400px;height:150px"
    data-options="modal:true" closed="true" >

<!-- <div id="win" class="easyui-window" title="确认退款" style="width:400px;height:150px;" data-options="modal:true" closed="true" ></div> -->

</div>
<script type="text/javascript">
//进入触发 
	$(function() {
		$('#orderRefundList').datagrid({   
		    rowStyler:function(index,row){
		        if (row.orderStatus=="3"){
		            return 'color:#901622';
		        }
		    }
		});
	});

	$(document).ready(function(){
		$("input[name='orderStartime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
		$("input[name='orderStartime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});	
		$("input[name='refundTime_begin']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});
		$("input[name='refundTime_end']").attr("class","Wdate").click(function(){WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'});});	
	});

	function agreeWindow(){
		var rwin = '';
		rwin += '<div class="easyui-layout" data-options="fit:true">';
		rwin += '<div style="text-align: center; position:relative;" data-options="region:\'center\'" >';
		rwin += '<h5 id="reTitle" >确定要同意退款吗？</h5>';
		rwin += '<input id="sub" type="button" class="button white" value="确定" style="margin-right: 50px;width:50px;height:30px" onclick="agreeRefund()" />';
		rwin += '<input id="cal" type="button" class="button white" value="取消" style="width:50px;height:30px" onclick="javascript:$(\'#win\').window(\'close\');"/>';
		rwin += '<div id="refund_loading" style="display:none;position:absolute;cursor1:wait;left:50%;top:20px;transform: translateX(-50%);width:200px;height:32px;color:#000;">';
		rwin += '</div></div></div>';
		
		return rwin;
	}
	
	/* function refundWindow(){
		var rwin = '';
		rwin += '<div class="easyui-layout" data-options="fit:true">';
		rwin += '<div style="text-align: center; " data-options="region:' + 'center' + '">';
		rwin += '<input type="hidden" id="dialog_order_id" value="" />';
		rwin += '<h5>请填写拒绝退款原因</h5>';
		rwin += '<textarea id="rejectReason" type="text" style="width:70%;height:40%;resize:none;" rows="5" cols= "7"></textarea>';
		rwin += '<input id="terefuse" type="hidden" />';
		rwin += '<div style="margin-top: 30px">';
		rwin += '<input id="sub" type="button" class="button white" value="提交" style="margin-right: 50px;width:50px;height:30px" onclick="refuseRefund()" />';
		rwin += '<input id="cal" type="button" class="button white" value="取消" style="width:50px;height:30px" onclick="javascript:$(\'#win\').window(\'close\');"/>';
		rwin += '</div></div></div>';
		
		return rwin;
	} */
	
	function agreeRefund(id, orderTotalPrice){
		
		refundBegin();
	    
		refundUrl = "orderRefundController.do?doAgreeALLSelect";
		refundGname = "orderRefundList";
		
		var obj = new Object();
		obj.id = id;
		obj.orderTotalPrice = orderTotalPrice;
		rows = [];
		
		rows.push(obj);
		
		$('#win').window('open');
		
		/* $.dialog.confirm('确定同意退款吗？',function(r){
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
		}); */
	} 
	
	/* function rejectRefund(id){
		$('#win').empty().append(refundWindow());
		$('#win').height("263px")
		$('#win').window('open'); // open a window
		$('#rejectReason').attr("readonly",false);
		$('#rejectReason').val("");
		$('#sub').show();
		$('#dialog_order_id').val(id);
	} 
	
	function refuseRefund(){
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
	*/
	
	var refundUrl;
	var refundGname;
	var rows;
	
	//批量退款
	function AgreeALLSelect(title,url,gname) {
		
		rows = $("#"+gname).datagrid('getSelections');
		console.log(rows);
	    if (rows.length <= 0) {
			tip("请选择需要批量同意退款的数据！");
			return;
		}
	    
	    for(var i=0;i<rows.length;i++){
	    	if (rows[i].orderStatus == 4) {
				tip("已退款的订单不能再进行退款操作！");
				return;
			}
	    }
	    
	    refundBegin();
	    
		refundUrl = url;
		refundGname = gname;
		
		$('#win').empty().append(agreeWindow());
		$('#win').height("150px")
		$('#win').window('open');
		
	}
	
	//同意退款
	function agreeRefund(){
		console.log(rows);
		$('#refund_loading').show();
		$('#sub').hide();
		$('#cal').hide();
		$('#reTitle').hide();
		$('#refund_loading').html("正在退款，请等待...");
		$('#refund_loading').css('background','#fff url(plug-in/ace/assets/css/images/loading.gif) no-repeat scroll 11px -4px');
		
		var ids = [];
		var fees = [];
	    
		for ( var i = 0; i < rows.length; i++) {
			ids.push(rows[i].id);
			fees.push(rows[i].orderTotalPrice);
		}
		
		$.ajax({
			url : refundUrl,
			type : 'post',
			data : {
				ids : ids.join(','),
				fees: fees.join(',')
			},
			//async : false,
			success : function(data) {
				//console.log(data);
				var d = $.parseJSON(data);
				console.log(d);
				var msg = d.msg;
				//tip(d.description + '\n' + msg);
				console.log(d.success);
				$('#refund_loading').html(d.description);
				
				$('#refund_loading').css('background','Transparent');
				reloadTable();
				$("#"+refundGname).datagrid('unselectAll');
				ids='';
			}
		});
	}
	
	//弹出退款窗口前先让所有控件展示出来
	function refundBegin(){
		$('#refund_loading').html("");
		$('#sub').show();
		$('#cal').show();
		$('#reTitle').show();
		$('#refund_loading').hide();
	}
	
	
	/**
	 * 批量拒绝退款
	 * @param title
	 * @param url
	 * @param gname
	 * @return
	 */
	/* function RefuseALLSelect(title,url,gname) {
	    var ids = [];
	    var rows = $("#"+gname).datagrid('getSelections');
	    if (rows.length > 0) {
	    	$.dialog.setting.zIndex = getzIndex(true);
	    	$.dialog.confirm('你确定拒绝退款吗?', function(r) {
			   if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					ts = ids.join(',');
					$('#win').window('open'); // open a window
					$('#rejectReason').attr("readonly",false);
					$('#rejectReason').val("");
					$('#sub').show();
					$('#terefuse').val(ts);
					
				}
			});
		} else {
			tip("请选择需要批量拒绝退款的数据！");
		}
	}
	
	function submitRefuse(){
		var ids = $('#terefuse').val();
		var rejectReason = $('#rejectReason').val();
		var gname = $('#gaga').val();
		if(rejectReason == ""){
			tip("请填写拒绝原因");
			return;
		}
		$.ajax({
            type:"post",
            url:"orderRefundController.do?doRefuseALLSelect&ids="+ids+"&rejectReason="+rejectReason,
            dataType:'json',
            success:function(data){
            	 var t = eval('('+data.jsonStr+')');
					tip(t.msg);
           		$('#win').window('close');
	           	//刷新当前窗体
	           	$('#orderRefundList').datagrid('reload');
				$("#orderRefundList").datagrid('unselectAll');
				ids='';
            //}
            }
        });
	} */
	
</script>
 
