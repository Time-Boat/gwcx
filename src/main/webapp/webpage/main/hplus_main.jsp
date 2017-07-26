<%--
  Created by IntelliJ IDEA.
  User: wangkun
  Date: 2016/4/23
  Time: 10:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/context/mytags.jsp"%>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="renderer" content="webkit">

    <title><t:mutiLang langKey="jeect.platform"/></title>

    <meta name="keywords" content="JEECG,后台bootstrap框架,会员中心主题,后台HTML,响应式后台">
    <meta name="description" content="JEECG是一个完全响应式，基于Bootstrap3最新版本开发的扁平化主题，她采用了主流的左右两栏式布局，使用了Html5+CSS3等现代技术">

    <link rel="shortcut icon" href="images/favicon.ico">
    <link href="plug-in-ui/hplus/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="plug-in-ui/hplus/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
    <link rel="stylesheet" href="plug-in/ace/assets/css/font-awesome.min.css" />
    <link rel="stylesheet" href="plug-in/jquery/jquery.toast.css" />
    <!--[if IE 7]>
    <link rel="stylesheet" href="plug-in/ace/assets/css/font-awesome-ie7.min.css" />
    <![endif]-->
    <!-- Sweet Alert -->
    <link href="plug-in-ui/hplus/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
    <link href="plug-in-ui/hplus/css/animate.css" rel="stylesheet">
    <link href="plug-in-ui/hplus/css/style.css?v=4.1.0" rel="stylesheet">
</head>

<body class="fixed-sidebar full-height-layout gray-bg" style="overflow:hidden">
<div id="wrapper">
    <!--左侧导航开始-->
    <nav class="navbar-default navbar-static-side" role="navigation" style="z-index: 1991;">
        <div class="nav-close"><i class="fa fa-times-circle"></i>
        </div>
        <div class="sidebar-collapse">
            <ul class="nav" id="side-menu">
                <li class="nav-header">
                    <div class="dropdown profile-element">
                        <span><img alt="image" class="img-circle" src="plug-in/login/images/default2.png" /></span>
                        <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                                <span class="clear">
                               <span class="block m-t-xs"><strong class="font-bold">${userName }</strong></span>
                                <span class="text-muted text-xs block">${roleName }<b class="caret"></b></span>
                                </span>
                        </a>
                        <ul class="dropdown-menu animated fadeInRight m-t-xs">
                            <li>
                                <a href="javascript:add('<t:mutiLang langKey="common.change.password"/>','userController.do?changepassword','',550,200)">
                                    <t:mutiLang langKey="common.change.password"/>
                                </a>
                            </li>
                            <li><a href="javascript:openwindow('<t:mutiLang langKey="common.profile"/>','userController.do?userinfo')"><t:mutiLang langKey="common.profile"/></a></li>
                            <li><a href="javascript:openwindow('<t:mutiLang langKey="common.ssms.getSysInfos"/>','tSSmsController.do?getSysInfos')"><t:mutiLang langKey="common.ssms.getSysInfos"/></a></li>
                            <li><a href="javascript:add('<t:mutiLang langKey="common.change.style"/>','userController.do?changestyle','',550,250)"><t:mutiLang langKey="common.my.style"/></a></li>
                            <li><a href="javascript:clearLocalstorage()"><t:mutiLang langKey="common.clear.localstorage"/></a></li>
                            <li><a href="http://yun.jeecg.org" target="_blank">云应用中心</li>
                            <li class="divider"></li>
                            <li><a href="javascript:logout()">注销</a></li>
                        </ul>
                    </div>
                    <div class="logo-element">公务出行管理平台
                    </div>
                </li>

                <t:menu style="hplus" menuFun="${menuMap}"></t:menu>

            </ul>
        </div>
    </nav>
    <!--左侧导航结束-->
    <!--右侧部分开始-->
    <div id="page-wrapper" class="gray-bg dashbard-1">
        <div class="row border-bottom">
            <nav class="navbar navbar-static-top" role="navigation" style="margin-bottom: 0">
                <div class="navbar-header" style="height: 60px;"><a class="navbar-minimalize minimalize-styl-2 btn btn-primary " href="#"><i class="fa fa-bars"></i> </a>
                    <form role="search" class="navbar-form-custom" method="post" action="search_results.html">
                        <div class="form-group">
                            <input type="text" placeholder="公务出行管理平台" class="form-control" name="top-search" id="top-search">
                        </div>
                    </form>
                </div>
                
               <%--  <ul class="nav navbar-top-links navbar-right">
                    <li class="dropdown">
                        <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                            <i class="fa fa-envelope"></i> <span class="label label-warning">0</span>
                        </a>
                        <ul class="dropdown-menu dropdown-alerts">
                            <li>
                                <a>
                                    <div>
                                        <i class="fa fa-envelope fa-fw"></i> 您有0条未读消息
                                        <span class="pull-right text-muted small">4分钟前</span>
                                    </div>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <div class="text-center link-block">
                                    <a class="" href="javascript:goAllNotice();">
                                        <i class="fa fa-envelope"></i> <strong> 查看所有消息</strong>
                                    </a>
                                </div>
                            </li>
                        </ul>
                    </li>
                    <li class="dropdown">
                        <a class="dropdown-toggle count-info" data-toggle="dropdown" href="#">
                            <i class="fa fa-bell"></i> <span class="label label-primary">0</span>
                        </a>
                        <ul class="dropdown-menu dropdown-alerts">
                            <li>
                                <a>
                                    <div>
                                        <i class="fa fa-envelope fa-fw"></i> 您有0条未读消息
                                        <span class="pull-right text-muted small">4分钟前</span>
                                    </div>
                                </a>
                            </li>
                            <li class="divider"></li>
                            <li>
                                <div class="text-center link-block">
                                    <a class="" href="javascript:goAllMessage();">
                                        <strong>查看所有 </strong>
                                        <i class="fa fa-angle-right"></i>
                                    </a>
                                </div>
                            </li>
                        </ul>
                    </li>
                    <li class="dropdown hidden-xs">
                        <a class="right-sidebar-toggle" aria-expanded="false">
                            <i class="fa fa-tasks"></i> 主题
                        </a>
                    </li>
                </ul> --%>
            </nav>
        </div>
        <div class="row content-tabs">
            <button class="roll-nav roll-left J_tabLeft"><i class="fa fa-backward"></i>
            </button>
            <nav class="page-tabs J_menuTabs">
                <div class="page-tabs-content">
                    <a href="javascript:;" class="active J_menuTab" data-id="loginController.do?hplushome">首页</a>
                </div>
            <button class="roll-nav roll-right J_tabRight"><i class="fa fa-forward"></i>
            </button>
            <div class="btn-group roll-nav roll-right">
                <button class="dropdown J_tabClose" data-toggle="dropdown">关闭操作<span class="caret"></span>

                </button>
                <ul role="menu" class="dropdown-menu dropdown-menu-right">
                    <li class="J_tabShowActive"><a>定位当前选项卡</a>
                    </li>
                    <li class="divider"></li>
                    <li class="J_tabCloseAll"><a>关闭全部选项卡</a>
                    </li>
                    <li class="J_tabCloseOther"><a>关闭其他选项卡</a>
                    </li>
                </ul>
            </div>
            <a href="javascript:logout()" class="roll-nav roll-right J_tabExit"><i class="fa fa fa-sign-out"></i> 退出</a>
        </div>
        <div class="row J_mainContent" id="content-main">
            <iframe class="J_iframe" name="iframe0" width="100%" height="100%" src="loginController.do?hplushome" frameborder="0" data-id="loginController.do?hplushome" seamless></iframe>
        </div>
        <div class="footer">
            <div class="pull-right">&copy; <%-- <t:mutiLang langKey="system.version.number"/> --%> <a href="https://www.baidu.com" target="_blank">baidu</a>
            </div>
        </div>
    </div>
    <!--右侧部分结束-->
    <!--右侧边栏开始-->
    <div id="right-sidebar">
        <div class="sidebar-container">

            <ul class="nav nav-tabs navs-3">

                <li class="active">
                    <a data-toggle="tab" href="#tab-1">
                        <i class="fa fa-gear"></i> 主题
                    </a>
                </li>
                <li class=""><a data-toggle="tab" href="#tab-2">
                    通知
                </a>
                </li>
                <li><a data-toggle="tab" href="#tab-3">
                    项目进度
                </a>
                </li>
            </ul>

            <div class="tab-content">
                <div id="tab-1" class="tab-pane active">
                    <div class="sidebar-title">
                        <h3> <i class="fa fa-comments-o"></i> 主题设置</h3>
                        <small><i class="fa fa-tim"></i> 你可以从这里选择和预览主题的布局和样式，这些设置会被保存在本地，下次打开的时候会直接应用这些设置。</small>
                    </div>
                    <div class="skin-setttings">
                        <div class="title">主题设置</div>
                        <div class="setings-item">
                            <span>收起左侧菜单</span>
                            <div class="switch">
                                <div class="onoffswitch">
                                    <input type="checkbox" name="collapsemenu" class="onoffswitch-checkbox" id="collapsemenu">
                                    <label class="onoffswitch-label" for="collapsemenu">
                                        <span class="onoffswitch-inner"></span>
                                        <span class="onoffswitch-switch"></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="setings-item">
                            <span>固定顶部</span>

                            <div class="switch">
                                <div class="onoffswitch">
                                    <input type="checkbox" name="fixednavbar" class="onoffswitch-checkbox" id="fixednavbar">
                                    <label class="onoffswitch-label" for="fixednavbar">
                                        <span class="onoffswitch-inner"></span>
                                        <span class="onoffswitch-switch"></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="setings-item">
                                <span>
                        固定宽度
                    </span>

                            <div class="switch">
                                <div class="onoffswitch">
                                    <input type="checkbox" name="boxedlayout" class="onoffswitch-checkbox" id="boxedlayout">
                                    <label class="onoffswitch-label" for="boxedlayout">
                                        <span class="onoffswitch-inner"></span>
                                        <span class="onoffswitch-switch"></span>
                                    </label>
                                </div>
                            </div>
                        </div>
                        <div class="title">皮肤选择</div>
                        <div class="setings-item default-skin nb">
                                <span class="skin-name ">
                         <a href="#" class="s-skin-0">
                             默认皮肤
                         </a>
                    </span>
                        </div>
                        <div class="setings-item blue-skin nb">
                                <span class="skin-name ">
                        <a href="#" class="s-skin-1">
                            蓝色主题
                        </a>
                    </span>
                        </div>
                        <div class="setings-item yellow-skin nb">
                                <span class="skin-name ">
                        <a href="#" class="s-skin-3">
                            黄色/紫色主题
                        </a>
                    </span>
                        </div>
                    </div>
                </div>
                <div id="tab-2" class="tab-pane">

                    <div class="sidebar-title">
                        <h3> <i class="fa fa-comments-o"></i> 最新通知</h3>
                        <small><i class="fa fa-tim"></i> 您当前有10条未读信息</small>
                    </div>

                    <div>

                        <div class="sidebar-message">
                            <a href="#">
                                <div class="pull-left text-center">
                                    <img alt="image" class="img-circle message-avatar" src="plug-in-ui/hplus/img/a1.jpg">

                                    <div class="m-t-xs">
                                        <i class="fa fa-star text-warning"></i>
                                        <i class="fa fa-star text-warning"></i>
                                    </div>
                                </div>
                                <div class="media-body">

                                    据天津日报报道：瑞海公司董事长于学伟，副董事长董社轩等10人在13日上午已被控制。
                                    <br>
                                    <small class="text-muted">今天 4:21</small>
                                </div>
                            </a>
                        </div>
                        <div class="sidebar-message">
                            <a href="#">
                                <div class="pull-left text-center">
                                    <img alt="image" class="img-circle message-avatar" src="plug-in-ui/hplus/img/a2.jpg">
                                </div>
                                <div class="media-body">
                                    HCY48之音乐大魔王会员专属皮肤已上线，快来一键换装拥有他，宣告你对华晨宇的爱吧！
                                    <br>
                                    <small class="text-muted">昨天 2:45</small>
                                </div>
                            </a>
                        </div>
                        <div class="sidebar-message">
                            <a href="#">
                                <div class="pull-left text-center">
                                    <img alt="image" class="img-circle message-avatar" src="plug-in-ui/hplus/img/a3.jpg">

                                    <div class="m-t-xs">
                                        <i class="fa fa-star text-warning"></i>
                                        <i class="fa fa-star text-warning"></i>
                                        <i class="fa fa-star text-warning"></i>
                                    </div>
                                </div>
                                <div class="media-body">
                                    写的好！与您分享
                                    <br>
                                    <small class="text-muted">昨天 1:10</small>
                                </div>
                            </a>
                        </div>
                        <div class="sidebar-message">
                            <a href="#">
                                <div class="pull-left text-center">
                                    <img alt="image" class="img-circle message-avatar" src="plug-in-ui/hplus/img/a4.jpg">
                                </div>

                                <div class="media-body">
                                    国外极限小子的炼成！这还是亲生的吗！！
                                    <br>
                                    <small class="text-muted">昨天 8:37</small>
                                </div>
                            </a>
                        </div>
                        <div class="sidebar-message">
                            <a href="#">
                                <div class="pull-left text-center">
                                    <img alt="image" class="img-circle message-avatar" src="plug-in-ui/hplus/img/a8.jpg">
                                </div>
                                <div class="media-body">

                                    一只流浪狗被收留后，为了减轻主人的负担，坚持自己觅食，甚至......有些东西，可能她比我们更懂。
                                    <br>
                                    <small class="text-muted">今天 4:21</small>
                                </div>
                            </a>
                        </div>
                        <div class="sidebar-message">
                            <a href="#">
                                <div class="pull-left text-center">
                                    <img alt="image" class="img-circle message-avatar" src="plug-in-ui/hplus/img/a7.jpg">
                                </div>
                                <div class="media-body">
                                    这哥们的新视频又来了，创意杠杠滴，帅炸了！
                                    <br>
                                    <small class="text-muted">昨天 2:45</small>
                                </div>
                            </a>
                        </div>
                        <div class="sidebar-message">
                            <a href="#">
                                <div class="pull-left text-center">
                                    <img alt="image" class="img-circle message-avatar" src="plug-in-ui/hplus/img/a3.jpg">

                                    <div class="m-t-xs">
                                        <i class="fa fa-star text-warning"></i>
                                        <i class="fa fa-star text-warning"></i>
                                        <i class="fa fa-star text-warning"></i>
                                    </div>
                                </div>
                                <div class="media-body">
                                    最近在补追此剧，特别喜欢这段表白。
                                    <br>
                                    <small class="text-muted">昨天 1:10</small>
                                </div>
                            </a>
                        </div>
                        <div class="sidebar-message">
                            <a href="#">
                                <div class="pull-left text-center">
                                    <img alt="image" class="img-circle message-avatar" src="plug-in-ui/hplus/img/a4.jpg">
                                </div>
                                <div class="media-body">
                                    我发起了一个投票 【你认为下午大盘会翻红吗？】
                                    <br>
                                    <small class="text-muted">星期一 8:37</small>
                                </div>
                            </a>
                        </div>
                    </div>

                </div>
                <div id="tab-3" class="tab-pane">

                    <div class="sidebar-title">
                        <h3> <i class="fa fa-cube"></i> 最新任务</h3>
                        <small><i class="fa fa-tim"></i> 您当前有14个任务，10个已完成</small>
                    </div>

                    <ul class="sidebar-list">
                        <li>
                            <a href="#">
                                <div class="small pull-right m-t-xs">9小时以后</div>
                                <h4>市场调研</h4> 按要求接收教材；

                                <div class="small">已完成： 22%</div>
                                <div class="progress progress-mini">
                                    <div style="width: 22%;" class="progress-bar progress-bar-warning"></div>
                                </div>
                                <div class="small text-muted m-t-xs">项目截止： 4:00 - 2015.10.01</div>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <div class="small pull-right m-t-xs">9小时以后</div>
                                <h4>可行性报告研究报上级批准 </h4> 编写目的编写本项目进度报告的目的在于更好的控制软件开发的时间,对团队成员的 开发进度作出一个合理的比对

                                <div class="small">已完成： 48%</div>
                                <div class="progress progress-mini">
                                    <div style="width: 48%;" class="progress-bar"></div>
                                </div>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <div class="small pull-right m-t-xs">9小时以后</div>
                                <h4>立项阶段</h4> 东风商用车公司 采购综合综合查询分析系统项目进度阶段性报告武汉斯迪克科技有限公司

                                <div class="small">已完成： 14%</div>
                                <div class="progress progress-mini">
                                    <div style="width: 14%;" class="progress-bar progress-bar-info"></div>
                                </div>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <span class="label label-primary pull-right">NEW</span>
                                <h4>设计阶段</h4>
                                <!--<div class="small pull-right m-t-xs">9小时以后</div>-->
                                项目进度报告(Project Progress Report)
                                <div class="small">已完成： 22%</div>
                                <div class="small text-muted m-t-xs">项目截止： 4:00 - 2015.10.01</div>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <div class="small pull-right m-t-xs">9小时以后</div>
                                <h4>拆迁阶段</h4> 科研项目研究进展报告 项目编号: 项目名称: 项目负责人:

                                <div class="small">已完成： 22%</div>
                                <div class="progress progress-mini">
                                    <div style="width: 22%;" class="progress-bar progress-bar-warning"></div>
                                </div>
                                <div class="small text-muted m-t-xs">项目截止： 4:00 - 2015.10.01</div>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <div class="small pull-right m-t-xs">9小时以后</div>
                                <h4>建设阶段 </h4> 编写目的编写本项目进度报告的目的在于更好的控制软件开发的时间,对团队成员的 开发进度作出一个合理的比对

                                <div class="small">已完成： 48%</div>
                                <div class="progress progress-mini">
                                    <div style="width: 48%;" class="progress-bar"></div>
                                </div>
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                <div class="small pull-right m-t-xs">9小时以后</div>
                                <h4>获证开盘</h4> 编写目的编写本项目进度报告的目的在于更好的控制软件开发的时间,对团队成员的 开发进度作出一个合理的比对

                                <div class="small">已完成： 14%</div>
                                <div class="progress progress-mini">
                                    <div style="width: 14%;" class="progress-bar progress-bar-info"></div>
                                </div>
                            </a>
                        </li>

                    </ul>

                </div>
            </div>

        </div>
    </div>
    <!--右侧边栏结束-->
    <!--mini聊天窗口开始-->
    <%--<div class="small-chat-box fadeInRight animated">

        <div class="heading" draggable="true">
            <small class="chat-date pull-right">
                2015.9.1
            </small> 与 Beau-zihan 聊天中
        </div>

        <div class="content">

            <div class="left">
                <div class="author-name">
                    Beau-zihan <small class="chat-date">
                    10:02
                </small>
                </div>
                <div class="chat-message active">
                    你好
                </div>

            </div>
            <div class="right">
                <div class="author-name">
                    游客
                    <small class="chat-date">
                        11:24
                    </small>
                </div>
                <div class="chat-message">
                    你好，请问H+有帮助文档吗？
                </div>
            </div>
            <div class="left">
                <div class="author-name">
                    Beau-zihan
                    <small class="chat-date">
                        08:45
                    </small>
                </div>
                <div class="chat-message active">
                    有，购买的H+源码包中有帮助文档，位于docs文件夹下
                </div>
            </div>
            <div class="right">
                <div class="author-name">
                    游客
                    <small class="chat-date">
                        11:24
                    </small>
                </div>
                <div class="chat-message">
                    那除了帮助文档还提供什么样的服务？
                </div>
            </div>
            <div class="left">
                <div class="author-name">
                    Beau-zihan
                    <small class="chat-date">
                        08:45
                    </small>
                </div>
                <div class="chat-message active">
                    1.所有源码(未压缩、带注释版本)；
                    <br> 2.说明文档；
                    <br> 3.终身免费升级服务；
                    <br> 4.必要的技术支持；
                    <br> 5.付费二次开发服务；
                    <br> 6.授权许可；
                    <br> ……
                    <br>
                </div>
            </div>


        </div>
        <div class="form-chat">
            <div class="input-group input-group-sm">
                <input type="text" class="form-control"> <span class="input-group-btn"> <button
                    class="btn btn-primary" type="button">发送
            </button> </span>
            </div>
        </div>

    </div>--%>
    <%--<div id="small-chat">
        <span class="badge badge-warning pull-right">5</span>
        <a class="open-small-chat">
            <i class="fa fa-comments"></i>

        </a>
    </div>--%>
    <!--mini聊天窗口结束-->
</div>

<!-- 全局js -->
<script src="plug-in-ui/hplus/js/jquery.min.js?v=2.1.4"></script>
<script src="plug-in-ui/hplus/js/bootstrap.min.js?v=3.3.6"></script>
<script src="plug-in-ui/hplus/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="plug-in-ui/hplus/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script src="plug-in-ui/hplus/js/plugins/layer/layer.min.js"></script>

<!-- 自定义js -->
<script src="plug-in-ui/hplus/js/hplus.js?v=4.1.0"></script>
<script type="text/javascript" src="plug-in-ui/hplus/js/contabs.js"></script>
<t:base type="tools,DatePicker"></t:base>
<!-- 第三方插件 -->
<script src="plug-in-ui/hplus/js/plugins/pace/pace.min.js"></script>
<!-- Sweet alert -->
<script src="plug-in-ui/hplus/js/plugins/sweetalert/sweetalert.min.js"></script>
<script src="plug-in/jquery-plugs/storage/jquery.storageapi.min.js"></script>
<!-- 弹出TAB -->
<script type="text/javascript" src="plug-in/hplus/hplus-tab.js"></script>
<script>
    function logout(){
        /*bootbox.confirm("<t:mutiLang langKey="common.exit.confirm"/>", function(result) {
            if(result)
                location.href="loginController.do?logout";
        });*/
        /*swal({
            title: "您确定要注销吗？",
            text: "注销后需要重新登录！",
            type: "warning",
            showCancelButton: true,
            confirmButtonColor: "#DD6B55",
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            closeOnConfirm: false,
            closeOnCancel: true
        },
        function (isConfirm) {
            if (isConfirm) {
                //swal("注销成功！", "您已经成功注销。", "success");
                location.href="loginController.do?logout";
            } else {
                return false;
            }
        });*/
        layer.confirm('您确定要注销吗？', {
            btn: ['确定','取消'], //按钮
            shade: false //不显示遮罩
        }, function(){
            location.href="loginController.do?logout";
        }, function(){
            return;
        });
    }
    function clearLocalstorage(){
        var storage=$.localStorage;
        if(!storage)
            storage=$.cookieStorage;
        storage.removeAll();
        //bootbox.alert( "浏览器缓存清除成功!");
        layer.msg("浏览器缓存清除成功!");
    }
    function goAllNotice(){
        var addurl = "noticeController.do?noticeList";
        createdetailwindow("公告", addurl, 800, 400);
    }
    function goAllMessage(){
        var addurl = "tSSmsController.do?getSysInfos";
        createdetailwindow("消息", addurl, 800, 400);
    }
  
    if ( typeof Object.create !== 'function' ) {
        Object.create = function( obj ) {
            function F() {}
            F.prototype = obj;
            return new F();
        };
    }

    (function( $, window, document, undefined ) {

        "use strict";
        
        var Toast = {

            _positionClasses : ['bottom-left', 'bottom-right', 'top-right', 'top-left', 'bottom-center', 'top-center', 'mid-center'],
            _defaultIcons : ['success', 'error', 'info', 'warning'],

            init: function (options, elem) {
                this.prepareOptions(options, $.toast.options);
                this.process();
            },

            prepareOptions: function(options, options_to_extend) {
                var _options = {};
                if ( ( typeof options === 'string' ) || ( options instanceof Array ) ) {
                    _options.text = options;
                } else {
                    _options = options;
                }
                this.options = $.extend( {}, options_to_extend, _options );
            },

            process: function () {
                this.setup();
                this.addToDom();
                this.position();
                this.bindToast();
                this.animate();
            },

            setup: function () {
                
                var _toastContent = '';
                
                this._toastEl = this._toastEl || $('<div></div>', {
                    class : 'jq-toast-single'
                });

                // For the loader on top
                _toastContent += '<span class="jq-toast-loader"></span>';            

                if ( this.options.allowToastClose ) {
                    _toastContent += '<span class="close-jq-toast-single">&times;</span>';
                };

                if ( this.options.text instanceof Array ) {

                    if ( this.options.heading ) {
                        _toastContent +='<h2 class="jq-toast-heading">' + this.options.heading + '</h2>';
                    };

                    _toastContent += '<ul class="jq-toast-ul">';
                    for (var i = 0; i < this.options.text.length; i++) {
                        _toastContent += '<li class="jq-toast-li" id="jq-toast-item-' + i + '">' + this.options.text[i] + '</li>';
                    }
                    _toastContent += '</ul>';

                } else {
                    if ( this.options.heading ) {
                        _toastContent +='<h2 class="jq-toast-heading">' + this.options.heading + '</h2>';
                    };
                    _toastContent += this.options.text;
                }

                this._toastEl.html( _toastContent );

                if ( this.options.bgColor !== false ) {
                    this._toastEl.css("background-color", this.options.bgColor);
                };

                if ( this.options.textColor !== false ) {
                    this._toastEl.css("color", this.options.textColor);
                };

                if ( this.options.textAlign ) {
                    this._toastEl.css('text-align', this.options.textAlign);
                }
                if(this.options.textHeight){
                	this._toastEl.css('text-height', this.options.textHeight);
                }

                if ( this.options.icon !== false ) {
                    this._toastEl.addClass('jq-has-icon');

                    if ( $.inArray(this.options.icon, this._defaultIcons) !== -1 ) {
                        this._toastEl.addClass('jq-icon-' + this.options.icon);
                    };
                };
            },

            position: function () {
                if ( ( typeof this.options.position === 'string' ) && ( $.inArray( this.options.position, this._positionClasses) !== -1 ) ) {

                    if ( this.options.position === 'bottom-center' ) {
                        this._container.css({
                            left: ( $(window).outerWidth() / 2 ) - this._container.outerWidth()/2,
                            bottom: 20
                        });
                    } else if ( this.options.position === 'top-center' ) {
                        this._container.css({
                            left: ( $(window).outerWidth() / 2 ) - this._container.outerWidth()/2,
                            top: 20
                        });
                    } else if ( this.options.position === 'mid-center' ) {
                        this._container.css({
                            left: ( $(window).outerWidth() / 2 ) - this._container.outerWidth()/2,
                            top: ( $(window).outerHeight() / 2 ) - this._container.outerHeight()/2
                        });
                    } else {
                        this._container.addClass( this.options.position );
                    }

                } else if ( typeof this.options.position === 'object' ) {
                    this._container.css({
                        top : this.options.position.top ? this.options.position.top : 'auto',
                        bottom : this.options.position.bottom ? this.options.position.bottom : 'auto',
                        left : this.options.position.left ? this.options.position.left : 'auto',
                        right : this.options.position.right ? this.options.position.right : 'auto'
                    });
                } else {
                    this._container.addClass( 'bottom-left' );
                }
            },

            bindToast: function () {

                var that = this;

                this._toastEl.on('afterShown', function () {
                    that.processLoader();
                });

                this._toastEl.find('.close-jq-toast-single').on('click', function ( e ) {

                    e.preventDefault();

                    if( that.options.showHideTransition === 'fade') {
                        that._toastEl.trigger('beforeHide');
                        that._toastEl.fadeOut(function () {
                            that._toastEl.trigger('afterHidden');
                        });
                    } else if ( that.options.showHideTransition === 'slide' ) {
                        that._toastEl.trigger('beforeHide');
                        that._toastEl.slideUp(function () {
                            that._toastEl.trigger('afterHidden');
                        });
                    } else {
                        that._toastEl.trigger('beforeHide');
                        that._toastEl.hide(function () {
                            that._toastEl.trigger('afterHidden');
                        });
                    }
                });

                if ( typeof this.options.beforeShow == 'function' ) {
                    this._toastEl.on('beforeShow', function () {
                        that.options.beforeShow();
                    });
                };

                if ( typeof this.options.afterShown == 'function' ) {
                    this._toastEl.on('afterShown', function () {
                        that.options.afterShown();
                    });
                };

                if ( typeof this.options.beforeHide == 'function' ) {
                    this._toastEl.on('beforeHide', function () {
                        that.options.beforeHide();
                    });
                };

                if ( typeof this.options.afterHidden == 'function' ) {
                    this._toastEl.on('afterHidden', function () {
                        that.options.afterHidden();
                    });
                };          
            },

            addToDom: function () {

                 var _container = $('.jq-toast-wrap');
                 
                 if ( _container.length === 0 ) {
                    
                    _container = $('<div></div>',{
                        class: "jq-toast-wrap"
                    });

                    $('body').append( _container );

                 } else if ( !this.options.stack || isNaN( parseInt(this.options.stack, 10) ) ) {
                    _container.empty();
                 }

                 _container.find('.jq-toast-single:hidden').remove();

                 _container.append( this._toastEl );

                if ( this.options.stack && !isNaN( parseInt( this.options.stack ), 10 ) ) {
                    
                    var _prevToastCount = _container.find('.jq-toast-single').length,
                        _extToastCount = _prevToastCount - this.options.stack;

                    if ( _extToastCount > 0 ) {
                        $('.jq-toast-wrap').find('.jq-toast-single').slice(0, _extToastCount).remove();
                    };

                }

                this._container = _container;
            },

            canAutoHide: function () {
                return ( this.options.hideAfter !== false ) && !isNaN( parseInt( this.options.hideAfter, 10 ) );
            },

            processLoader: function () {
                // Show the loader only, if auto-hide is on and loader is demanded
                if (!this.canAutoHide() || this.options.loader === false) {
                    return false;
                }

                var loader = this._toastEl.find('.jq-toast-loader');

                // 400 is the default time that jquery uses for fade/slide
                // Divide by 1000 for milliseconds to seconds conversion
                var transitionTime = (this.options.hideAfter - 400) / 1000 + 's';
                var loaderBg = this.options.loaderBg;

                var style = loader.attr('style') || '';
                style = style.substring(0, style.indexOf('-webkit-transition')); // Remove the last transition definition

                style += '-webkit-transition: width ' + transitionTime + ' ease-in; \
                          -o-transition: width ' + transitionTime + ' ease-in; \
                          transition: width ' + transitionTime + ' ease-in; \
                          background-color: ' + loaderBg + ';';


                loader.attr('style', style).addClass('jq-toast-loaded');
            },

            animate: function () {

                var that = this;

                this._toastEl.hide();

                this._toastEl.trigger('beforeShow');

                if ( this.options.showHideTransition.toLowerCase() === 'fade' ) {
                    this._toastEl.fadeIn(function ( ){
                        that._toastEl.trigger('afterShown');
                    });
                } else if ( this.options.showHideTransition.toLowerCase() === 'slide' ) {
                    this._toastEl.slideDown(function ( ){
                        that._toastEl.trigger('afterShown');
                    });
                } else {
                    this._toastEl.show(function ( ){
                        that._toastEl.trigger('afterShown');
                    });
                }

                if (this.canAutoHide()) {

                    var that = this;

                    window.setTimeout(function(){
                        
                        if ( that.options.showHideTransition.toLowerCase() === 'fade' ) {
                            that._toastEl.trigger('beforeHide');
                            that._toastEl.fadeOut(function () {
                                that._toastEl.trigger('afterHidden');
                            });
                        } else if ( that.options.showHideTransition.toLowerCase() === 'slide' ) {
                            that._toastEl.trigger('beforeHide');
                            that._toastEl.slideUp(function () {
                                that._toastEl.trigger('afterHidden');
                            });
                        } else {
                            that._toastEl.trigger('beforeHide');
                            that._toastEl.hide(function () {
                                that._toastEl.trigger('afterHidden');
                            });
                        }

                    }, this.options.hideAfter);
                };
            },

            reset: function ( resetWhat ) {

                if ( resetWhat === 'all' ) {
                    $('.jq-toast-wrap').remove();
                } else {
                    this._toastEl.remove();
                }

            },

            update: function(options) {
                this.prepareOptions(options, this.options);
                this.setup();
                this.bindToast();
            }
        };
        
        $.toast = function(options) {
            var toast = Object.create(Toast);
            toast.init(options, this);

            return {
                
                reset: function ( what ) {
                    toast.reset( what );
                },

                update: function( options ) {
                    toast.update( options );
                }
            }
        };

        $.toast.options = {
            text: '',
            heading: '',
            showHideTransition: 'fade',
            allowToastClose: true,
            hideAfter: 3000,
            loader: true,
            loaderBg: '#9EC600',
            stack: 5,
            position: 'bottom-left',
            bgColor: false,
            textColor: false,
            textAlign: 'left',
            icon: false,
            beforeShow: function () {},
            afterShown: function () {},
            beforeHide: function () {},
            afterHidden: function () {}
        };

    })( jQuery, window, document );
    
    /* function popup(){
    	addOneTab("弹窗", "transferOrderController.do?transferOrderList"); 
    } */
    
    function getOrder(){
    	
    	$.ajax({
			url : "transferOrderController.do?getOrder",
			dataType : 'json',
			complete : function(data) {
				var message = data.responseText;
				var obj = eval('(' + message + ')');
				if(obj.ordairs>0 && obj.ordtrs>0){
					$.toast({
			    	    heading: '消息提醒',
			    	    icon: 'info',
			    	    text: ['&nbsp;','&nbsp;',
					    	    '有'+obj.ordairs+'份最新的接送机订单，'+obj.ordtrs+'份最新的接送火车订单，请及时处理！</a> ','&nbsp;','&nbsp;','&nbsp;'],
			    	    position: 'bottom-right',
			    	    bgColor: '#2F4050',
			    	    hideAfter: false,
			    	    stack: true
			    	})
				}else if(obj.ordairs>0 && obj.ordtrs==0){
					$.toast({
			    	    heading: '消息提醒',
			    	    icon: 'info',
			    	    text: ['&nbsp;','&nbsp;',
					    	    '有'+obj.ordairs+'份最新的接送机订单，请及时处理！</a> ','&nbsp;','&nbsp;','&nbsp;'],
			    	    position: 'bottom-right',
			    	    bgColor: '#2F4050',
			    	    hideAfter: false,
			    	    stack: true
			    	})
				}else if(obj.ordairs==0 && obj.ordtrs>0){
					$.toast({
			    	    heading: '消息提醒',
			    	    icon: 'info',
			    	    text: ['&nbsp;','&nbsp;',
					    	    '有'+obj.ordtrs+'份最新的接送火车订单，请及时处理！</a> ','&nbsp;','&nbsp;','&nbsp;'],
			    	    position: 'bottom-right',
			    	    bgColor: '#2F4050',
			    	    hideAfter: false,
			    	    stack: true
			    	})
				}
				//刷新当前窗体
				//reloadTable();
			}
		});
    }
    $(function() {
    	getOrder();
    });
 
  setInterval("getOrder()",600000);
</script>
 
</body>

</html>