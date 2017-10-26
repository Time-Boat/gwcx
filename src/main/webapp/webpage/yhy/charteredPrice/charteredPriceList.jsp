<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="charteredPriceList" title="包车价格设置" actionUrl="charteredPriceController.do?datagrid" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="包车套餐" field="packageName" align="center"  width="100"></t:dgCol>
   <t:dgCol title="车辆类型" field="carType" align="center" dictionary="car_type"  width="100"></t:dgCol>
   <t:dgCol title="起步价（元）" field="initiatePrice" align="center"  width="100"></t:dgCol>
   <t:dgCol title="超公里/元" field="exceedKmPrice" align="center"  width="100"></t:dgCol>
   <t:dgCol title="超时长/元" field="exceedTimePrice" align="center"  width="100"></t:dgCol>
   <t:dgCol title="所在城市" field="city" align="center" width="100"></t:dgCol>
   <t:dgCol title="空反费（公里/元）" field="emptyReturn" align="center"  width="120"></t:dgCol>
   <t:dgCol title="创建人" field="username" align="center"  width="100"></t:dgCol>
   <t:dgCol title="创建时间" field="createTime" formatter="yyyy-MM-dd hh:mm:ss" align="center" width="150"></t:dgCol>
   <t:dgCol title="修改用户" field="modifyUserName" align="center" width="100"></t:dgCol>
   <t:dgCol title="修改时间" field="midifyTime" formatter="yyyy-MM-dd hh:mm:ss" align="center" width="150"></t:dgCol>
  
   <t:dgCol title="状态 " field="status" dictionary="driver_status"  align="center"  width="100"></t:dgCol>
   <t:dgCol title="审核状态 " field="auditStatus" dictionary="line_apply_status" align="center"  width="100"></t:dgCol>
   <t:dgCol title="申请类型 " field="applyType" dictionary="apply_type" align="center"  width="100"></t:dgCol>
   <t:dgCol title="备注" field="remark" align="center"  width="120"></t:dgCol>
   
   <%-- <t:dgCol title="提交申请人" field="applyUser"   width="120"></t:dgCol>
   <t:dgCol title="提交申请时间" field="applyDate" formatter="yyyy-MM-dd hh:mm:ss"  width="120"></t:dgCol>
   <t:dgCol title="审核人" field="auditUser"   width="120"></t:dgCol>
   <t:dgCol title="审核时间" field="auditDate" formatter="yyyy-MM-dd hh:mm:ss"  width="120"></t:dgCol>
   <t:dgCol title="拒绝原因" field="rejectReason"   width="120"></t:dgCol>
   <t:dgCol title="复审用户" field="lastAuditUser"   width="120"></t:dgCol>
   <t:dgCol title="复审时间" field="lastAuditDate" formatter="yyyy-MM-dd hh:mm:ss"  width="120"></t:dgCol>
   <t:dgCol title="复审拒绝原因" field="lastRejectReason"   width="120"></t:dgCol> --%>
   <t:dgCol title="操作" field="opt" width="250"></t:dgCol>
   <t:dgDelOpt title="删除" url="charteredPriceController.do?del&id={id}" />
   <t:dgToolBar title="添加报价" icon="icon-add" url="charteredPriceController.do?addorupdate" funname="add" height="600"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="charteredPriceController.do?addorupdate" funname="update" height="600"></t:dgToolBar>
   <t:dgToolBar title="查看详情" icon="icon-search" url="charteredPriceController.do?addorupdate" funname="detail" height="600"></t:dgToolBar>
  
  	<t:dgFunOpt funname="applyShelves(id)" title="申请上架" operationCode="applyShelves" exp="status#eq#0&&auditStatus#eq#0"></t:dgFunOpt>
	<t:dgFunOpt funname="applyShelves(id)" title="申请上架" operationCode="applyShelves" exp="status#eq#0&&auditStatus#eq#5"></t:dgFunOpt>
	<t:dgFunOpt funname="applyShelves(id)" title="申请上架" operationCode="applyShelves" exp="status#eq#0&&auditStatus#eq#6"></t:dgFunOpt>
	<t:dgFunOpt funname="applyShelves(id)" title="申请上架" operationCode="applyShelves" exp="status#eq#0&&auditStatus#eq#4"></t:dgFunOpt>
	<t:dgFunOpt funname="applyShelves(id)" title="申请下架" operationCode="applicationShelf" exp="status#eq#1&&auditStatus#eq#0"></t:dgFunOpt>
	<t:dgFunOpt funname="applyShelves(id)" title="申请下架" operationCode="applicationShelf" exp="status#eq#1&&auditStatus#eq#5"></t:dgFunOpt>
	<t:dgFunOpt funname="applyShelves(id)" title="申请下架" operationCode="applicationShelf" exp="status#eq#1&&auditStatus#eq#6"></t:dgFunOpt>
	<t:dgFunOpt funname="applyShelves(id)" title="申请下架" operationCode="applicationShelf" exp="status#eq#1&&auditStatus#eq#3"></t:dgFunOpt>
	<t:dgFunOpt funname="agree(id)" title="同意" operationCode="firstagree" exp="auditStatus#eq#1"></t:dgFunOpt>
	<t:dgFunOpt funname="agree(id)" title="同意" operationCode="agrees" exp="auditStatus#eq#2"></t:dgFunOpt>
	<t:dgFunOpt funname="refuse(id)" title="拒绝" operationCode="firstrefuse" exp="auditStatus#eq#1"></t:dgFunOpt>
	<t:dgFunOpt funname="refuse(id)" title="拒绝" operationCode="refuses" exp="auditStatus#eq#2"></t:dgFunOpt>
	<t:dgFunOpt funname="lookRejectReason(id)" title="初审拒绝原因" operationCode="rejectReason" exp="auditStatus#eq#5"></t:dgFunOpt>
	<t:dgFunOpt funname="lookRejectReason(id)" title="复审拒绝原因" operationCode="rejectReason" exp="auditStatus#eq#6"></t:dgFunOpt> 
	
  
  </t:datagrid>
  </div>
  <div id="dealerWin" class="easyui-window" title="拒绝原因" style="width:400px;height:300px"
    data-options="modal:true" closed="true" >
  </div>
 </div>
 <script type="text/javascript">
//申请上、下架
	function applyShelves(id) {
		
		$.dialog.confirm('确定要申请？',function(r){
		    if (r){
		    	$.post(
		    		"charteredPriceController.do?applyShelves",	
					{'id':id},
					function(data){
						var obj = eval('(' + data + ')');
						tip(obj.msg);
						$('#charteredPriceList').datagrid('reload');
					}
				);		
		    }
		});
	}
	
	//同意
	function agree(id) {
		
		$.dialog.confirm('确定通过？',function(r){
		    if (r){
		    	$.post(
		    		"charteredPriceController.do?agree",	
					{'id':id},
					function(data){
						var obj = eval('(' + data + ')');
						tip(obj.msg);
						$('#charteredPriceList').datagrid('reload');
					}
				);		
		    }
		});
	}
	
	function commitReason(){
		var id = $('#dialog_order_id').val();
		var rejectReason = $('#rejectReason').val();
		if(rejectReason == ""){
			tip("请填写拒绝原因");
			return;
		}	
		$.ajax({
			url : "charteredPriceController.do?refuse&id="+id+"&rejectReason="+rejectReason,
			type : "get",
			success : function(data) {
				//console.log(data);
				var d = $.parseJSON(data);
				console.log(d);
				var msg = d.msg;
				//tip(d.description + '\n' + msg);
				console.log(d.success);
				$('#dealerWin').window('close');
				tip(msg);
				reloadTable();
			}
		});
	}
	
	//填写拒绝原因窗口
	function rejectWindow(){
		var rwin = '';
		rwin += '<div class="easyui-layout" data-options="fit:true">';
		rwin += '<div style="text-align: center; " data-options="region:' + 'center' + '">';
		rwin += '<input type="hidden" id="dialog_order_id" value="" />';
		rwin += '<h5>请填写拒绝原因</h5>';
		rwin += '<textarea id="rejectReason" type="text" style="width:70%;height:40%;resize:none;" rows="5" cols= "7"></textarea>';
		rwin += '<input id="terefuse" type="hidden" />';
		rwin += '<div style="margin-top: 30px">';
		rwin += '<input id="sub" type="button" class="button white" value="提交" style="margin-right: 50px;width:50px;height:30px" onclick="commitReason()" />';
		rwin += '<input id="cal" type="button" class="button white" value="取消" style="width:50px;height:30px" onclick="javascript:$(\'#dealerWin\').window(\'close\');"/>';
		rwin += '</div></div></div>';
		
		return rwin;
	}
	
	//拒绝
	function refuse(id) {
		$('#dealerWin').empty().append(rejectWindow());
		$('#dealerWin').window('open'); // open a window
		$('#rejectReason').attr("readonly",false);
		$('#rejectReason').val("");
		$('#sub').show();
		$('#dialog_order_id').val(id);
	}
	
	function lookRejectReason(id){
		$('#dealerWin').window('open');
		$('#sub').hide();
		$.ajax({
            type:"get",
            url:"charteredPriceController.do?getReason&id="+id,
            dataType:'json',
            success:function(d){
           		//var obj = eval('('+d.msg+')');
           		$('#dealerWin').empty().append(lookRejectReasonWindow());
           		$('#rejectReason').val(d.msg);
           		$('#rejectReason').attr("readonly",true);
            }
        });
	}
	
	//填写拒绝原因窗口
	function lookRejectReasonWindow(){
		var rwin = '';
		rwin += '<div class="easyui-layout" data-options="fit:true">';
		rwin += '<div style="text-align: center; " data-options="region:' + 'center' + '">';
		rwin += '<input type="hidden" id="dialog_order_id" value="" />';
		rwin += '<h5>请填写拒绝原因</h5>';
		rwin += '<textarea id="rejectReason" type="text" style="width:70%;height:40%;resize:none;" rows="5" cols= "7"></textarea>';
		rwin += '<input id="terefuse" type="hidden" />';
		rwin += '<div style="margin-top: 30px">';
		rwin += '<input id="cal" type="button" class="button white" value="关闭" style="width:50px;height:30px" onclick="javascript:$(\'#dealerWin\').window(\'close\');"/>';
		rwin += '</div></div></div>';
		
		return rwin;
	}
 </script>