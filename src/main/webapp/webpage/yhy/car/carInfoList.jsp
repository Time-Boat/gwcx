<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<link href="plug-in/tools/css/rejectReason.css" type="text/css" rel="stylesheet"/>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <script type="text/javascript">
	  function carDisable(id){
			$.dialog.confirm("确定要申请停用吗?", function(r) {
				if(r){
					$.ajax({
						url : "carInfoController.do?carDisable&id="+id,
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
		
		function carAgree(id){
			$.dialog.confirm("确定要同意审核吗?", function(r) {
				if(r){
					$.ajax({
						url : "carInfoController.do?carAgree&id="+id,
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
		
		function carReject(id){
			$('#carWin').empty().append(carRejectWindow());
			$('#carWin').window('open'); // open a window
			$('#carRejectReason').attr("readonly",false);
			$('#carRejectReason').val("");
			$('#carReason').html("请填写拒绝原因");
			$('#carSub').show();
			$('#car_dialog_order_id').val(id);
		}
		
		//填写拒绝原因窗口
		function carRejectWindow(){
			var rwin = '';
			rwin += '<div class="easyui-layout" data-options="fit:true">';
			rwin += '<div style="text-align: center; " data-options="region:' + 'center' + '">';
			rwin += '<input type="hidden" id="car_dialog_order_id" value="" />';
			rwin += '<h5 id="carReason"></h5>';
			rwin += '<textarea id="carRejectReason" type="text" style="width:70%;height:40%;resize:none;" rows="5" cols= "7"></textarea>';
			rwin += '<input id="carTerefuse" type="hidden" />';
			rwin += '<div style="margin-top: 30px">';
			rwin += '<input id="carSub" type="button" class="button white" value="提交" style="margin-right: 50px;width:50px;height:30px" onclick="carCommitReason()" />';
			rwin += '<input id="carCal" type="button" class="button white" value="取消" style="width:50px;height:30px" onclick="javascript:$(\'#carWin\').window(\'close\');"/>';
			rwin += '</div></div></div>';
			
			return rwin;
		}
		
		function carCommitReason(){
			var id = $('#car_dialog_order_id').val();
			var rejectReason = $('#carRejectReason').val();
			if(rejectReason == ""){
				tip("请填写拒绝原因");
				return;
			}	
			$.ajax({
				url : "carInfoController.do?carReject&id="+id+"&rejectReason="+rejectReason,
				type : "get",
				success : function(data) {
					//console.log(data);
					var d = $.parseJSON(data);
					var msg = d.msg;
					$('#carWin').window('close');
					tip(msg);
					reloadTable();
				}
			});
		}
		
		function carApply(id){
			$.dialog.confirm("确定要提交申请吗?", function(r) {
				if(r){
					$.ajax({
						url : "carInfoController.do?carApply&id="+id,
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
		
		function carLookRejectReason(id){
			$('#carWin').empty().append(carRejectWindow());
			$('#carWin').window('open');
			$('#carRejectReason').attr("readonly",true);
			$('#carReason').html("拒绝原因");
			$('#carSub').hide();
			$.ajax({
	          type:"get",
	          url:"carInfoController.do?getReason&id="+id,
	          dataType:'json',
	          success:function(d){
	         		var obj = eval('('+d.jsonStr+')');
	         		console.log(obj);
	         		$('#carRejectReason').val(obj.msg);
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
			/* if (rowsData[0].carStatus == 2) {
				tip('已停止状态不能编辑！');
				return;
			} */
			if (rowsData[0].auditStatus == 0) {
				tip('审核状态中不能被编辑！');
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
		
		function carAllot(title,url,id,width,height){
			var ap = <%=request.getAttribute("ap")%>;
			console.log(ap);
			var ids = '';
			var rows = $("#carInfoList").datagrid("getSelections");
			for(var i=0;i<rows.length;i++){
				/* if(ap != '1'){
					if(rows[i].status != '0' || rows[i].auditStatus == '0'){
						tip('只有合作中的渠道商，并且不是待审核状态才能被分配！');
						return;
					}
				} */
				ids+=rows[i].id;
				ids+=',';
			}
			ids = ids.substring(0,ids.length-1);
			if(ids.length==0){
				tip('请选择要分配的车辆');
				return;
			}
			url += '&ids='+ids;
			createwindow(title,url,width,height);
		}
		
  </script>
  <t:datagrid name="carInfoList" title="车辆信息" autoLoadData="true" actionUrl="carInfoController.do?datagrid" checkbox="true" idField="id" fitColumns="true" queryMode="group" fit="true">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="车牌号" field="licencePlate" query="true" align="center" width="80"></t:dgCol>
   <t:dgCol title="车辆类型" field="carType" dictionary="car_Type" align="center" query="true" width="50"></t:dgCol>
   <t:dgCol title="座位数" field="seat" align="center" width="50"></t:dgCol>
   
   <t:dgCol title="购置日期" field="buyDate" width="80" formatter="yyyy-MM-dd" align="center" ></t:dgCol>
   <t:dgCol title="品牌" dictionary="carBrand" width="50" field="carBrand" align="center" ></t:dgCol>
   <t:dgCol title="型号" field="modelNumber" width="50" align="center" ></t:dgCol>
   
   <t:dgCol title="车辆位置" field="stopPosition" align="center" width="120"></t:dgCol>
   <t:dgCol title="司机" field="name" align="center"  width="50"></t:dgCol>
   <t:dgCol title="创建人" field="username" align="center"  width="50"></t:dgCol>
   <t:dgCol title="创建时间" field="createTime" editor="datebox" formatter="yyyy-MM-dd hh:mm:ss" align="center" ></t:dgCol>
   <t:dgCol title="车辆状态" field="status" align="center" query="true" dictionary="carType" width="50"></t:dgCol>
   <t:dgCol title="业务类型" field="businessType" align="center" query="true" dictionary="carBType" width="80"></t:dgCol>
   
   <t:dgCol title="运营状态" field="carStatus" dictionary="carOType" align="center" query="true" width="50"></t:dgCol>
   <t:dgCol title="审核状态" field="auditStatus" dictionary="audit_status" query="true" align="center" width="50"></t:dgCol>
   <t:dgCol title="申请内容" field="applyContent" dictionary="apply_type" align="center"  width="50"></t:dgCol>
   
   <t:dgCol title="备注" field="remark"  align="center" width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" align="center" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="carInfoController.do?del&id={id}" exp="carStatus#eq#1&&auditStatus#ne#0"/>
   
   <t:dgToolBar title="录入" icon="icon-add" url="carInfoController.do?addorupdate" operationCode="carAdd" funname="add" width="600" height="500"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="carInfoController.do?addorupdate" operationCode="carUpdate" funname="update" height="500" ></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="carInfoController.do?carDetail" funname="detail" height="700" ></t:dgToolBar>
   <t:dgToolBar title="批量分配" icon="icon-redo" url="carInfoController.do?getAttacheList" operationCode="carAllotAttache" funname="carAllot" ></t:dgToolBar> 
   
   <t:dgFunOpt funname="carAgree(id)" title="同意" operationCode="carAgreeMA" exp="auditStatus#eq#0"></t:dgFunOpt>
   <t:dgFunOpt funname="carReject(id)" title="拒绝" operationCode="carRejectMA" exp="auditStatus#eq#0"></t:dgFunOpt>
   
   <t:dgFunOpt funname="carDisable(id)"  title="申请停用" operationCode="carDisable" exp="carStatus#eq#0&&auditStatus#eq#1"></t:dgFunOpt> 
   <t:dgFunOpt funname="carDisable(id)"  title="申请停用" operationCode="carDisable" exp="carStatus#eq#0&&auditStatus#eq#2"></t:dgFunOpt> 
   
   <t:dgFunOpt funname="carApply(id)"  title="提交申请" operationCode="carApply" exp="carStatus#eq#1&&auditStatus#eq#-1"></t:dgFunOpt>
   <t:dgFunOpt funname="carApply(id)"  title="提交申请" operationCode="carApply" exp="carStatus#eq#1&&auditStatus#eq#1"></t:dgFunOpt>
   <t:dgFunOpt funname="carApply(id)"  title="提交申请" operationCode="carApply" exp="carStatus#eq#1&&auditStatus#eq#2"></t:dgFunOpt>
   <t:dgFunOpt funname="carApply(id)"  title="提交申请" operationCode="carApply" exp="carStatus#eq#2&&auditStatus#eq#-1"></t:dgFunOpt>
   <t:dgFunOpt funname="carApply(id)"  title="提交申请" operationCode="carApply" exp="carStatus#eq#2&&auditStatus#eq#1"></t:dgFunOpt>
   <t:dgFunOpt funname="carApply(id)"  title="提交申请" operationCode="carApply" exp="carStatus#eq#2&&auditStatus#eq#2"></t:dgFunOpt>
   
  </t:datagrid>
  </div>
  <div id="carWin" class="easyui-window" title="拒绝原因" style="width:400px;height:300px"
    data-options="modal:true" closed="true" >
  </div>
 </div>