<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>部门信息</title>
<t:base type="jquery,easyui,tools"></t:base>
<script type="text/javascript">

var pat = /^(\b13[0-9]{9}\b)|(\b14[7-7]\d{8}\b)|(\b15[0-9]\d{8}\b)|(\b18[0-9]\d{8}\b)|\b1[1-9]{2,4}\b$/;
	
	var b = true;
	
	//验证手机号是否已经被占用
	function checkPhone(phone){
	if(!pat.test(phone)){
		
		return;
	}
	$.ajax({
        type:"get",
        url:"conductorController.do?checkPhone&phone="+phone+"&id="+$("#id").val(),
        dataType:'json',
        success:function(d){
       		var obj = eval('('+d.jsonStr+')');
       		b = obj.success;
       		if(!b){
       			tip(obj.msg);
       			$('#check_phone').text(obj.msg).css({color:"red"});
       		}else{
       			$('#check_phone').text('通过信息验证！').css({color:"#71b83d"});
       		}
        }
    });
}
	
	//提交前验证手机号
	function cp(){
		return b;
	}



	$(function() {
		$('#cc').combotree({
			url : 'departController.do?setPFunction&selfId=${depart.id}',
            width: 155,
            onSelect : function(node) {
//                alert(node.text);
                changeOrgType();
            }
        });
        if(!$('#cc').val()) { // 第一级，只显示公司选择项
            var orgTypeSelect = $("#orgType");
            var companyOrgType = '<option value="1" <c:if test="${orgType=='1'}">selected="selected"</c:if>><t:mutiLang langKey="common.company"/></option>';
            orgTypeSelect.empty();
            orgTypeSelect.append(companyOrgType);
        } else { // 非第一级，不显示公司选择项
            $("#orgType option:first").remove();
        }
        if($("#id").val()) {
            $('#cc').combotree('disable');
        }
        if('${empty pid}' == 'false') { // 设置新增页面时的父级
            $('#cc').combotree('setValue', '${pid}');
        }
	});
    function changeOrgType() { // 处理组织类型，不显示公司选择项
        var orgTypeSelect = $("#orgType");
        var optionNum = orgTypeSelect.get(0).options.length;
        if(optionNum == 1) {
            $("#orgType option:first").remove();
            var zigongsi = '<option value="2" <c:if test="${orgType=='2'}">selected="selected"</c:if>><t:mutiLang langKey="子公司"/></option>';
            var bumen = '<option value="3" <c:if test="${orgType=='3'}">selected="selected"</c:if>><t:mutiLang langKey="部门"/></option>';
            var gangwei = '<option value="4" <c:if test="${orgType=='4'}">selected="selected"</c:if>><t:mutiLang langKey="岗位"/></option>';
            var ruzhu = '<option value="5" <c:if test="${orgType=='5'}">selected="selected"</c:if>><t:mutiLang langKey="入驻公司"/></option>';
            orgTypeSelect.append(zigongsi).append(bumen).append(gangwei).append(ruzhu);
        }
    }
</script>
</head>
<body style="overflow-y: hidden" scroll="no">
<t:formvalid formid="formobj" layout="div" dialog="true" action="systemController.do?saveDepart" beforeSubmit="cp()">
	<input id="id" name="id" type="hidden" value="${depart.id }">
	<fieldset class="step">
        <div class="form">
            <label class="Validform_label"> <t:mutiLang langKey="common.department.name"/>: </label>
            <input name="departname" class="inputxt" type="text" value="${depart.departname }">
            <span class="Validform_checktip"></span>
        </div>
        <div class="form">
            <label class="Validform_label"> <t:mutiLang langKey="position.desc"/>: </label>
            <input name="description" class="inputxt" value="${depart.description }">
        </div>
        <div class="form">
            <label class="Validform_label"> <t:mutiLang langKey="parent.depart"/>: </label>
            <input id="cc" name="TSPDepart.id" value="${depart.TSPDepart.id}">
        </div>
        <div class="form">
            <input type="hidden" name="orgCode" value="${depart.orgCode }">
            <label class="Validform_label"> <t:mutiLang langKey="common.org.type"/>: </label>
            <select name="orgType" id="orgType">
                <option value="1" <c:if test="${depart.orgType=='1'}">selected="selected"</c:if>><t:mutiLang langKey="common.company"/></option>
                <option value="2" <c:if test="${depart.orgType=='2'}">selected="selected"</c:if>><t:mutiLang langKey="子公司"/></option>
                <option value="3" <c:if test="${depart.orgType=='3'}">selected="selected"</c:if>><t:mutiLang langKey="部门"/></option>
                <option value="4" <c:if test="${depart.orgType=='4'}">selected="selected"</c:if>><t:mutiLang langKey="岗位"/></option>
                <option value="5" <c:if test="${depart.orgType=='5'}">selected="selected"</c:if>><t:mutiLang langKey="入驻公司"/></option>
            </select>
        </div>
        <div class="form">
            <label class="Validform_label"> <t:mutiLang langKey="common.mobile"/>: </label>
            <input name="mobile" class="inputxt" value="${depart.mobile }" datatype="m" errormsg="手机号码不正确"  onchange="checkPhone(this.value);" />
            <span class="Validform_checktip" id="check_phone"></span>
        </div>
        <div class="form">
            <label class="Validform_label"> <t:mutiLang langKey="common.fax"/>: </label>
            <input name="fax" class="inputxt" value="${depart.fax }">
        </div>
        <div class="form">
            <label class="Validform_label"> <t:mutiLang langKey="common.address"/>: </label>
            <input name="address" class="inputxt" value="${depart.address }" datatype="s1-50">
            <span class="Validform_checktip"><t:mutiLang langKey="departmentaddress.rang1to50"/></span>
        </div>
	</fieldset>
</t:formvalid>
</body>
</html>
