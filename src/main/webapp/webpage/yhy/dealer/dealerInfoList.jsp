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
		
		function downloadFile(id){
			window.location.href="dealerInfoController.do?fileDown&did="+id;
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
							var d = $.parseJSON(data);
							var msg = d.msg;
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
							var d = $.parseJSON(data);
							var msg = d.msg;
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
			$('#reason').html("请填写拒绝原因");
			$('#sub').show();
			$('#dialog_order_id').val(id);
		}
		
		//填写拒绝原因窗口
		function rejectWindow(){
			var rwin = '';
			rwin += '<div class="easyui-layout" data-options="fit:true">';
			rwin += '<div style="text-align: center; " data-options="region:' + 'center' + '">';
			rwin += '<input type="hidden" id="dialog_order_id" value="" />';
			rwin += '<h5 id="reason"></h5>';
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
					var msg = d.msg;
					$('#dealerWin').window('close');
					tip(msg);
					reloadTable();
				}
			});
		}
		
		function dealerApply(id,dealerFilePath){
			if (dealerFilePath == '') {
				tip('请先上传附件！');
				return;
			}
			$.dialog.confirm("确定要提交申请吗?", function(r) {
				if(r){
					$.ajax({
						url : "dealerInfoController.do?dealerApply&id="+id,
						type : "get",
						success : function(data) {
							var d = $.parseJSON(data);
							var msg = d.msg;
							tip(msg);
							reloadTable();
						}
					});
				}
			});
		}
		
		function lookRejectReason(id){
			$('#dealerWin').empty().append(rejectWindow());
			$('#dealerWin').window('open');
			$('#rejectReason').attr("readonly",true);
			$('#reason').html("拒绝原因");
			$('#sub').hide();
			$.ajax({
	            type:"get",
	            url:"dealerInfoController.do?getReason&id="+id,
	            dataType:'json',
	            success:function(d){
	           		var obj = eval('('+d.jsonStr+')');
	           		console.log(obj);
	           		$('#rejectReason').val(obj.msg);
	            }
	        });
		}
		
		function update(title,url,id,width,height,isRestful) {
			gridname=id;
			var rowsData = $('#'+id).datagrid('getSelections');
			if (!rowsData || rowsData.length==0) {
				tip('请选择编辑项目！');
				return;
			}
			if (rowsData.length > 1) {
				tip('请选择一条记录再编辑！');
				return;
			}
			if (rowsData[0].auditStatus == 0 || rowsData[0].lastAuditStatus == 0) {
				tip('审核状态中不能被编辑！');
				return;
			}
			if (rowsData[0].status == 2) {
				tip('已终止的渠道商不能进行编辑！');
				return;
			}
			if(isRestful!='undefined'&&isRestful){
				url += '/'+rowsData[0].id;
			}else{
				if(rowsData[0].id==null){
					rowsData[0].id = '';
				}
				url += '&id='+rowsData[0].id;
			}
			createwindow(title,url,width,height);
		}
		
		function dealerAllot(title,url,id,width,height){
			
			var ids = '';
			var rows = $("#dealerInfoList").datagrid("getSelections");
			for(var i=0;i<rows.length;i++){
				if(rows[i].status != '0' || rows[i].auditStatus == '0'){
					tip('只有合作中的渠道商，并且不是待审核状态才能被分配！');
					return;
				}
				ids+=rows[i].id;
				ids+=',';
			}
			ids = ids.substring(0,ids.length-1);
			if(ids.length==0){
				tip('请选择要分配的渠道商');
				return;
			}
			url += '&ids='+ids;
			createwindow(title,url,width,height);
		}
		
		//上传文件
		function dealerUploadFile(id){
			createdetailwindow("选择文件","dealerInfoController.do?dealerUploadFile&id="+id,"800px","500px");
		}
		
		/* //初始化查询条件
		$(function() {
			var json = $("#accounts").val();
			
			var a1 = '<span style="display:-moz-inline-box;display:inline-block; padding:0px 10px;"><span style="vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;';
	 		var a2 = 'text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; "title="渠道商名称">渠道商名称：</span>';
			var a3 = '<select name="accountId" style="width: 150px">';
			var c1 = '<option value="">选择渠道商</option>';
			if(json.indexOf("account")>0){
				var obj = eval('(' + json + ')');
				for (var i = 0; i < obj.data.length; i++) {
					c1 += '<option value="'+obj.data[i].id+'">' + obj.data[i].account+ '</option>';
				}
			}
			var a4 = '</select></span>';
			
			$("#dealerInfoListForm").append(a1 + a2 + a3 + c1 + a4);
		}); */
		
		
	</script>
	<a hidden="true" id="downloadCode" download ></a>
	<a hidden="true" id="downloadFile" download ></a>
  <t:datagrid name="dealerInfoList" title="渠道商信息" actionUrl="dealerInfoController.do?datagrid" idField="id" fit="true" checkbox="true" queryMode="group">
   <t:dgCol title="编号" field="id" hidden="true" ></t:dgCol>
   <t:dgCol title="二维码地址" field="qrCodeUrl" hidden="true" width="120"></t:dgCol>
   <t:dgCol title="附件地址" field="dealerFilePath" hidden="true" width="120"></t:dgCol>
   <t:dgCol title="渠道商名称" field="account" query="true" align="center" width="120"></t:dgCol>
   <t:dgCol title="合作状态" field="status" query="true" align="center" dictionary="dealerStatus" width="80"></t:dgCol>
   <t:dgCol title="所属公司" field="departname" query="true" align="center" width="120"></t:dgCol>
   <t:dgCol title="创建日期" field="createDate" editor="datebox" formatter="yyyy-MM-dd hh:mm:ss" queryMode="group" align="center" width="120"></t:dgCol>
   <t:dgCol title="创建人" field="username" query="true" align="center" width="80"></t:dgCol>
   <t:dgCol title="推广人数" field="scanCount" align="center" width="60"></t:dgCol>
   <t:dgCol title="联系电话" field="phone" align="center" width="120"></t:dgCol>
   <t:dgCol title="负责人" field="manager" align="center" width="70"></t:dgCol>
   <t:dgCol title="地址" field="position" align="center" width="120"></t:dgCol>
   
   <t:dgCol title="初审状态" field="auditStatus" dictionary="audit_status" query="true" align="center"></t:dgCol>
   <t:dgCol title="申请内容" field="applyType" dictionary="apply_type" align="center" width="120"></t:dgCol>
   <t:dgCol title="复审状态" field="lastAuditStatus"  dictionary="last_audit_status" query="true" align="center"></t:dgCol>
   <%-- <t:dgCol title="初审时间" field="auditDate" editor="datebox" formatter="yyyy-MM-dd hh:mm:ss" align="center"></t:dgCol>
   <t:dgCol title="提交申请时间" field="commitApplyDate" align="center" width="120" formatter="yyyy-MM-dd hh:mm:ss"></t:dgCol>
   <t:dgCol title="初审人" field="auditUser" align="center"></t:dgCol>
   <t:dgCol title="复审时间" field="lastAuditDate" editor="datebox" formatter="yyyy-MM-dd hh:mm:ss" align="center"></t:dgCol>
   <t:dgCol title="复审人" field="lastAuditUser" align="center"></t:dgCol> --%>
   
   <t:dgCol title="操作" field="opt" ></t:dgCol>
   
   <t:dgDelOpt title="删除" url="dealerInfoController.do?del&id={id}" exp="status#eq#1&&auditStatus#eq#-1"/>
   <t:dgDelOpt title="删除" url="dealerInfoController.do?del&id={id}" exp="status#eq#1&&auditStatus#eq#2"/>
   <t:dgDelOpt title="删除" url="dealerInfoController.do?del&id={id}" exp="status#eq#1&&lastAuditStatus#eq#2"/>
   
   <t:dgToolBar title="录入" icon="icon-add" url="dealerInfoController.do?addorupdate" funname="add" operationCode="dealerAdd" ></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="dealerInfoController.do?addorupdate" funname="update" operationCode="dealerUpdate" ></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="dealerInfoController.do?dealerDetail" funname="detail" height="600"></t:dgToolBar>
   <t:dgToolBar title="批量分配" icon="icon-redo" url="dealerInfoController.do?getAttacheList" funname="dealerAllot" operationCode="allotAttache" ></t:dgToolBar> 
   
   <t:dgFunOpt funname="generateQRCode(id)" title="生成二维码" exp="qrCodeUrl#empty#true&&status#eq#0"></t:dgFunOpt>
   <t:dgFunOpt funname="lookQRCode(qrCodeUrl)" title="预览" exp="qrCodeUrl#empty#false&&status#eq#0"></t:dgFunOpt>
   <t:dgFunOpt funname="downloadQRCode(qrCodeUrl)" title="二维码下载" exp="qrCodeUrl#empty#false&&status#eq#0"></t:dgFunOpt> 
   <t:dgFunOpt funname="downloadFile(id)"  title="附件下载" operationCode="dealerDownload" exp="dealerFilePath#empty#false"></t:dgFunOpt> 
   
   <!-- 权限按钮 -->
   <t:dgFunOpt funname="dealerApply(id,dealerFilePath)"  title="提交申请" operationCode="dealerApply" exp="status#eq#1&&auditStatus#eq#2"></t:dgFunOpt>
   <t:dgFunOpt funname="dealerApply(id,dealerFilePath)"  title="提交申请" operationCode="dealerApply" exp="status#eq#1&&auditStatus#eq#-1"></t:dgFunOpt>
   <t:dgFunOpt funname="dealerApply(id,dealerFilePath)"  title="提交申请" operationCode="dealerApply" exp="status#eq#1&&lastAuditStatus#eq#2"></t:dgFunOpt>
   
   <%-- <t:dgFunOpt funname="dealerDisable(id)"  title="申请停用" operationCode="dealerDisable" exp="status#eq#0&&lastAuditStatus#eq#2"></t:dgFunOpt> 
   <t:dgFunOpt funname="lookRejectReason(id)" title="拒绝原因" operationCode="rejectReason" exp="auditStatus#eq#2"></t:dgFunOpt> 
   <t:dgFunOpt funname="lookRejectReason(id)" title="拒绝原因" operationCode="rejectReason" exp="lastAuditStatus#eq#2"></t:dgFunOpt>  --%>
   
   <t:dgFunOpt funname="dealerAgree(id)" title="同意" operationCode="dealerAgreeMA" exp="auditStatus#eq#0"></t:dgFunOpt>
   <t:dgFunOpt funname="dealerAgree(id)" title="同意" operationCode="dealerAgreePA" exp="lastAuditStatus#eq#0"></t:dgFunOpt>
   <t:dgFunOpt funname="dealerReject(id)" title="拒绝" operationCode="dealerRejectMA" exp="auditStatus#eq#0"></t:dgFunOpt>
   <t:dgFunOpt funname="dealerReject(id)" title="拒绝" operationCode="dealerRejectPA" exp="lastAuditStatus#eq#0"></t:dgFunOpt>
   
   <t:dgFunOpt funname="dealerUploadFile(id)" title="上传" operationCode="dealerUpload" exp="status#eq#1&&auditStatus#eq#2" ></t:dgFunOpt>
   <t:dgFunOpt funname="dealerUploadFile(id)" title="上传" operationCode="dealerUpload" exp="status#eq#1&&auditStatus#eq#-1" ></t:dgFunOpt>
   <t:dgFunOpt funname="dealerUploadFile(id)" title="上传" operationCode="dealerUpload" exp="status#eq#1&&lastAuditStatus#eq#2" ></t:dgFunOpt>
   
  </t:datagrid>
  </div>
  <div id="dealerWin" class="easyui-window" title="拒绝原因" style="width:400px;height:300px"
    data-options="modal:true" closed="true" >
  </div>
 </div>
