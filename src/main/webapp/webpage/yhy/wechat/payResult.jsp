<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>微信支付成功页面</title>
	<script src='plug-in/jquery/jquery-1.9.1.js'></script>
	<script type="text/javascript">
		$(function(){
			var status = $("#status").val();
			if(status == "0"){
				window.location.href = "/job/payStateYes.html";
			}else{
				window.location.href = "/job/payStateNo.html";
			}
		});
	</script>
</head>
<body>
	<input id="status" value="${status}" hidden="true" />
</body>
</html>