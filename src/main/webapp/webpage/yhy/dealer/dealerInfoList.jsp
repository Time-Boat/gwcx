<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
   <script type="text/javascript">
		function generateQRCode(id){
			$.dialog.confirm("你确定要生成二维码吗?", function(r) {
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
			window.location.href = qrCodeUrl;
		}
		
		function lookQRCode(qrCodeUrl){
			window.open(qrCodeUrl);
		}
	</script>
  <t:datagrid name="dealerInfoList" title="渠道商信息" actionUrl="dealerInfoController.do?datagrid" idField="id" fit="true">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="二维码地址" field="qrCodeUrl" hidden="true" width="120"></t:dgCol>
   <t:dgCol title="渠道商账号" field="account"   width="120"></t:dgCol>
   <t:dgCol title="创建日期" field="createDate" editor="datebox" formatter="yyyy-MM-dd hh:mm:ss" queryMode="group" align="center" width="120"></t:dgCol>
   <t:dgCol title="被扫描次数" field="scanCount"   width="120"></t:dgCol>
   <t:dgCol title="联系电话" field="phone"   width="120"></t:dgCol>
   <t:dgCol title="负责人" field="manager"   width="120"></t:dgCol>
   <t:dgCol title="地址" field="position"   width="120"></t:dgCol>
   <t:dgCol title="银行账户" field="bankAccount"   width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <t:dgDelOpt title="删除" url="dealerInfoController.do?del&id={id}" />
   <t:dgToolBar title="录入" icon="icon-add" url="dealerInfoController.do?addorupdate" funname="add"></t:dgToolBar>
   <t:dgToolBar title="编辑" icon="icon-edit" url="dealerInfoController.do?addorupdate" funname="update"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="dealerInfoController.do?addorupdate" funname="detail"></t:dgToolBar>
   <t:dgFunOpt funname="generateQRCode(id)" title="生成二维码"></t:dgFunOpt>
   <t:dgFunOpt funname="lookQRCode(qrCodeUrl)" title="预览"></t:dgFunOpt>
   <t:dgFunOpt funname="downloadQRCode(qrCodeUrl)"  title="下载"></t:dgFunOpt> 
  </t:datagrid>
  </div>
 </div>
