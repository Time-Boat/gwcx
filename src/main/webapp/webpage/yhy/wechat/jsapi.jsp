<%@ page contentType="text/html; charset=UTF-8"%>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
System.out.println("in jsapi.jsp");
%>
<!DOCTYPE html>
<html>
	<head>
		<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
		<script src='plug-in/jquery/jquery-1.9.1.js'></script>
		<script type="text/javascript">
		
		function onBridgeReady(){
		   WeixinJSBridge.invoke(
		       'getBrandWCPayRequest', {
		           "appId":"${appid}",     //公众号名称，由商户传入     
		           "timeStamp":"${timeStamp}",         //时间戳，自1970年以来的秒数     
		           "nonceStr":"${nonceStr}", //随机串     
		           "package":"${packageValue}",     
		           "signType":"MD5",         //微信签名方式：     
		           "paySign":"${sign}" //微信签名 
		       },
		       function(res){
		    	// 使用以下方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。  
		    		var url = "${pageContext.request.contextPath}/wx.do?success&orderPayNumber=${orderPayNumber}&orderId=${orderId}&status=";
		    		if(res.err_msg == "get_brand_wcpay_request:ok") {
		        	    //alert("支付成功");
		        	    url += "0";//"${pageContext.request.contextPath}/wx.do?success&orderId=${orderId}";
		            }else if(res.err_msg == "get_brand_wcpay_request:fail"){
		            	url += "1"
		       			//alert('支付失败');
		       		}else if(res.err_msg == "get_brand_wcpay_request:cancel"){
		       			url += "2"
		       			//alert('支付取消');
		       		}else{
		       			url += "1"
		       			
		       		}
		    		window.location.replace(url);
		       }
		   ); 
		}
		
	/* 	if (typeof window.WeixinJSBridge == "undefined"){
			$(document).on('WeixinJSBridgeReady',function(){ 
			  $('#weiXinPay').click(); 
			  }); } else { 
			  $('#weiXinPay').click();
			}    
			    */
		
		
	    function callpay(){
			if (typeof WeixinJSBridge == "undefined"){
			   if( document.addEventListener ){
			       document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
			   }else if (document.attachEvent){
			       document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
			       document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
			   }
			}else{
			   onBridgeReady();
			}	
	    }
		</script>	
	    <title>订单-支付</title>
	</head>
<body>
	<script type="text/javascript">
		$(document).ready(function () {
			callpay();
	    });
		
	</script>
</body>
</html>
