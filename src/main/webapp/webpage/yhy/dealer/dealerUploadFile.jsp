<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/context/mytags.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>渠道商材料上传</title>
    <!-- 图片上传js -->
	<script type="text/javascript" src="plug-in/fileUpload/js/jquery.js"></script>
    <script type="text/javascript" src="plug-in/fileUpload/js/webuploader.js"></script>
    <script type="text/javascript" src="plug-in/fileUpload/js/md5.js"></script>
    <script type="text/javascript" src="plug-in/fileUpload/js/upload.js"></script>
    <link rel="stylesheet" type="text/css" href="plug-in/fileUpload/css/style.css" />
    
</head>
<body>
	<input hidden="true" id="did" value="${dealerId}" />
    <div id="wrapper">
        <div id="container">
            <!--头部，相册选择和格式选择-->
            <div id="uploader">
                <div class="queueList">
                    <div id="dndArea" class="placeholder">
                        <div id="filePicker"></div>
                        <p>或将文件拖到这里</p>
                        <p>支持jpg,jpeg,png,bmp格式</p>
                    </div>
                </div>
                <div class="statusBar" style="display:none;">
                    <div class="progress">
                        <span class="text">0%</span>
                        <span class="percentage"></span>
                    </div><div class="info"></div>
                    <div class="btns">
                        <div id="filePicker2"></div><div class="uploadBtn">开始上传</div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
	    
	    /* $(function(){
	    	console.log(uploader);
	    }); */
	    
	    function fileSuccess(){
	    	var win = frameElement.api.opener;//获取父窗口
  			//刷新主表单父窗口
  			win.reloadTable();
  			
  			//关闭当前弹出框
  			frameElement.api.close();
	    }
	    
    </script>
</body>
</html>
