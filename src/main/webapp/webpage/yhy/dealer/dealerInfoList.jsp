<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link href="plug-in/tools/css/rejectReason.css" type="text/css" rel="stylesheet"/>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
   <script type="text/javascript">
		function generateQRCode(id){
			$.dialog.confirm("确定要生成二维码吗?", function(r) {
				if(r){
					$.ajax({
						url : "dealerInfoController.do?generateQRCode&id="+id,
						type : "get",
						success : function(data) {
							//console.log(data);
							var d = $.parseJSON(data);
							console.log(d);
							var msg = d.msg;
							//tip(d.description + '\n' + msg);
							console.log(d.success);
							tip(msg);
							reloadTable();
						}
					});
				}
			});
		}
		
		function downloadQRCode(qrCodeUrl){
			var path = "<%=request.getServerName() + ":" + request.getServerPort() %>";
			//console.log("path:" + path + qrCodeUrl);
			//console.log("qrCodeUrl:" + qrCodeUrl);
			
			var aObject = $("#downloadCode");
			aObject.attr("href", "http://" + path + qrCodeUrl);
			//aObject.click();
			document.getElementById("downloadCode").click();
			//window.location.href = path + qrCodeUrl;
			//window.open(qrCodeUrl);
		}
		
		function lookQRCode(qrCodeUrl){
			window.open(qrCodeUrl);	
		}
		
		function dealerDisable(id){
			$.dialog.confirm("确定要申请停用吗?", function(r) {
				if(r){
					$.ajax({
						url : "dealerInfoController.do?dealerDisable&id="+id,
						type : "get",
						success : function(data) {
							//console.log(data);
							var d = $.parseJSON(data);
							console.log(d);
							var msg = d.msg;
							//tip(d.description + '\n' + msg);
							console.log(d.success);
							tip(msg);
							reloadTable();
						}
					});
				}
			});
		}
		
		function dealerAgree(id){
			$.dialog.confirm("确定要同意审核吗?", function(r) {
				if(r){
					$.ajax({
						url : "dealerInfoController.do?dealerAgree&id="+id,
						type : "get",
						success : function(data) {
							//console.log(data);
							var d = $.parseJSON(data);
							console.log(d);
							var msg = d.msg;
							//tip(d.description + '\n' + msg);
							console.log(d.success);
							tip(msg);
							reloadTable();
						}
					});
				}
			});
		}
		
		function dealerReject(id){
			$('#dealerWin').empty().append(rejectWindow());
			$('#dealerWin').window('open'); // open a window
			$('#rejectReason').attr("readonly",false);
			$('#rejectReason').val("");
			$('#sub').show();
			$('#dialog_order_id').val(id);
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
		
		function commitReason(){
			var id = $('#dialog_order_id').val();
			var rejectReason = $('#rejectReason').val();
			if(rejectReason == ""){
				tip("请填写拒绝原因");
				return;
			}	
			$.ajax({
				url : "dealerInfoController.do?dealerReject&id="+id+"&rejectReason="+rejectReason,
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
		
		function lookRejectReason(id){
			$('#dealerWin').window('open');
			$('#rejectReason').attr("readonly",true);
			$('#sub').hide();
			$.ajax({
	            type:"get",
	            url:"dealerInfoController.do?getReason&id="+id,
	            dataType:'json',
	            success:function(d){
	           		var obj = eval('('+d.jsonStr+')');
	           		$('#rejectReason').val(obj.msg);
	            }
	        });
			
		}
		
	</script>
	<a hidden="true" id="downloadCode" download ></a>
  <t:datagrid name="dealerInfoList" title="渠道商信息" actionUrl="dealerInfoController.do?datagrid" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="二维码地址" field="qrCodeUrl" hidden="true" width="120"></t:dgCol>
   <t:dgCol title="渠道商账号" field="account" align="center" width="120"></t:dgCol>
   <t:dgCol title="合作状态" field="status" align="center" dictionary="dealerStatus" width="80"></t:dgCol>
   <t:dgCol title="创建日期" field="createDate" editor="datebox" formatter="yyyy-MM-dd hh:mm:ss" queryMode="group" align="center" width="120"></t:dgCol>
   <t:dgCol title="创建人" field="username" align="center" width="80"></t:dgCol>
   <t:dgCol title="推广用户数" field="scanCount" align="center" width="60"></t:dgCol>
   <t:dgCol title="联系电话" field="phone" align="center" width="120"></t:dgCol>
   <t:dgCol title="负责人" field="manager" align="center" width="70"></t:dgCol>
   <t:dgCol title="地址" field="position" align="center" width="120"></t:dgCol>
   <t:dgCol title="银行账户" field="bankAccount" align="center" width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" ></t:dgCol>
   <t:dgDelOpt title="删除" url="dealerInfoController.do?del&id={id}" exp="qrCodeUrl#empty#false"/>
   <t:dgToolBar title="录入" icon="icon-add" url="dealerInfoController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="dealerInfoController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="dealerInfoController.do?addorupdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="批量分配" icon="icon-redo" url="dealerInfoController.do?addorupdate" funname="detail" operationCode="allotAttache" ></t:dgToolBar> 
   
   <t:dgFunOpt funname="generateQRCode(id)" title="生成二维码" exp="qrCodeUrl#empty#true"></t:dgFunOpt>
   <t:dgFunOpt funname="lookQRCode(qrCodeUrl)" title="预览" exp="qrCodeUrl#empty#false"></t:dgFunOpt>
   <t:dgFunOpt funname="downloadQRCode(qrCodeUrl)" title="下载" exp="qrCodeUrl#empty#false"></t:dgFunOpt> 
   
   <!-- 权限按钮 -->
   <t:dgFunOpt funname="dealerDisable(id)"  title="申请停用" operationCode="dealerDisable" ></t:dgFunOpt> 
   <t:dgFunOpt funname="dealerReject(id)" title="拒绝原因" operationCode="rejectReason" ></t:dgFunOpt> 
   <t:dgFunOpt funname="dealerAgree(id)" title="同意" operationCode="dealerAgree" ></t:dgFunOpt> 
   <t:dgFunOpt funname="dealerReject(id)" title="拒绝" operationCode="dealerReject" ></t:dgFunOpt> 
   
  </t:datagrid>
  </div>
  <div id="dealerWin" class="easyui-window" title="退款" style="width:400px;height:300px"
    data-options="modal:true" closed="true" >
  </div>
 </div>
