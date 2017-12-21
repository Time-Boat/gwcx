<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<t:base type="jquery,easyui,tools,DatePicker"></t:base>
<div class="easyui-layout" fit="true">
  <div region="center" style="padding:0px;border:0px">
  <t:datagrid name="notificationRecordList" title="系统消息发送记录" actionUrl="notificationRecordController.do?datagrid" idField="id" fit="true" checkbox="true">
   <t:dgCol title="编号" field="id" hidden="true"></t:dgCol>
   <t:dgCol title="通知标题" field="title" align="center"  width="120"></t:dgCol>
   <t:dgCol title="通知内容" field="content"   width="120"></t:dgCol>
   <%-- <t:dgCol title="通知目标" field="target"   width="120"></t:dgCol> --%>
   <t:dgCol title="发送时间" field="sendTime" align="center" formatter="yyyy-MM-dd hh:mm:ss"  width="120"></t:dgCol>
   <t:dgCol title="通知方式" field="nType" align="center" width="120"></t:dgCol>
   <t:dgCol title="状态" field="status" align="center" dictionary="read_status" width="120"></t:dgCol>
   <t:dgCol title="创建时间" field="createTime" align="center" formatter="yyyy-MM-dd hh:mm:ss"  width="120"></t:dgCol>
   <t:dgCol title="备注" field="remark"   width="120"></t:dgCol>
   <t:dgCol title="操作" field="opt" width="100"></t:dgCol>
   <%-- <t:dgDelOpt title="删除" url="notificationRecordController.do?del&id={id}" /> --%>
   <t:dgToolBar title="批量删除" icon="icon-remove" url="notificationRecordController.do?doDeleteALLSelect" funname="deleteALLSelect"></t:dgToolBar>
   <t:dgToolBar title="查看" icon="icon-search" url="notificationRecordController.do?addorupdate" funname="detail"></t:dgToolBar>
   <t:dgToolBar title="全部标为已读" icon="icon-search" url="notificationRecordController.do?makeMessageRead" funname="makeMessageRead"></t:dgToolBar>
  
  </t:datagrid>
  </div>
 </div>
 <script type="text/javascript">
 $(function() {
		$('#notificationRecordList').datagrid({   
		    rowStyler:function(index,row){
		    	 if (row.status=="1"){   
			            return 'color:#999';   
			        }
		    }
		});
 });
 
 
function detail(title, url, id, width, height) {
		var rowsData = $('#' + id).datagrid('getSelections');

		if (!rowsData || rowsData.length == 0) {
			tip('请选择查看项目');
			return;
		}
		if (rowsData.length > 1) {
			tip('请选择一条记录再查看');
			return;
		}
		
		$.ajax({
			url : url += '&load=detail&id=' + rowsData[0].id,
			type : "get",
			success : function(data) {
				reloadTable();
				createdetailwindow(title, url, 600, 470);
				
			}
		});
	}
	
function makeMessageRead(title, url, id, width, height) {
	$.ajax({
		url : url,
		type : "get",
		success : function(data) {
			var d = $.parseJSON(data);
			var msg = d.msg;
			tip(msg);
			reloadTable();
		}
	});
}
</script>