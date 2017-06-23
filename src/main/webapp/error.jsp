<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<title>预定成功</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no">
<script>
	(function() {
		document.addEventListener('DOMContentLoaded', function() {
			var html = document.documentElement;
			var windowWidth = html.clientWidth;
			html.style.fontSize = windowWidth / 7.5 + 'px';
			// 等价于html.style.fontSize = windowWidth / 750 * 100 + 'px';
		}, false);
	})();
	// 这个7.5就是根据设计稿的横向宽度来确定的，假如你的设计稿是640
	// 那么 html.style.fontSize = windowWidth / 6.4 + 'px';
</script>
<style>
@charset "utf-8";

/*移动端全局样式*/
html, body, div, span, applet, object, iframe, h1, h2, h3, h4, h5, h6, p,
	blockquote, pre, a, abbr, acronym, address, big, cite, code, del, dfn,
	em, font, img, ins, kbd, q, s, samp, small, strike, strong, sub, sup,
	tt, var, b, u, i, center, dl, dt, dd, ol, ul, li, fieldset, form, label,
	legend, table, caption, tbody, tfoot, thead, tr, th, td, header, input
	{
	margin: 0;
	padding: 0;
}

article, aside, details, figcaption, figure, footer, header, hgroup,
	menu, nav, section {
	display: block;
}

img {
	display: block;
	max-width: 100%;
	height: auto;
	width: auto\9;
	/* ie9以下 */
}

/*点击阴影*/
*, ::before, ::after {
	padding: 0;
	margin: 0;
	/*在移动端特殊的设置 */
	/*点击高亮效果*/
	-webkit-tap-highlight-color: transparent;
	/*设置所有的盒子的宽度以边框开始计算*/
	-webkit-box-sizing: border-box;
	/*要兼容webket浏览器内核厂商  这种情况一般是老的移动端浏览器*/
	box-sizing: border-box;
}

a {
	text-decoration: none;
	cursor: pointer;
	color: #555756;
	background: transparent;
}

li {
	list-style: none;
}

a img {
	border: none;
}

.clearfix:before, .clearfix:after {
	content: "";
	display: table;
}

.clearfix:after {
	clear: both;
}

.clearfix {
	*zoom: 1;
	/*IE/7/6*/
}

input {
	outline: none;
	border: 0;
}

input, textarea, button {
	font-family: 'Microsoft YaHei', 'Hiragino Sans GB', Helvetica, Arial,
		'Lucida Grande', sans-serif;
}

input {
	-webkit-appearance: none; /*去除input默认样式*/
}

input, button {
	transition: all 0s !important;
}

/* 禁用iPhone中Safari的字号自动调整 */
body, html {
	-webkit-text-size-adjust: none;
	font-family: 'Microsoft YaHei', 'Hiragino Sans GB', Helvetica, Arial,
		'Lucida Grande', sans-serif;
}

body {
	background: #f2f2f2;
}

.fl {
	float: left;
}

.fr {
	float: right;
}

.show {
	display: block;
}

.hide {
	display: none;
}

.box {
	padding: 0 .6rem;
}

.inner li {
	text-align: center;
}

.inner .pic {
	margin: 0 auto;
	padding-top: 2rem;
	width: 25%;
}

.inner .tit {
	font-size: .34rem;
	color: #666;
	line-height: 1rem;
	margin-top: .3rem;
}

.inner .msg {
	font-size: .3rem;
	color: #999;
	line-height: .5rem;
	text-align: center;
}

.inner .look a {
	display: block;
	width: 80%;
	margin: 0 auto;
	margin-top: 1rem;
	background: #e8b82d;
	line-height: .8rem;
	color: #fff;
	font-size: .3rem;
	border-radius: 5px;
}

@media only screen and (min-width: 400px) {
	html {
		font-size: 55px ! important;
	}
}
</style>
</head>

<body >
	<div class="box">
		<!--提交失败-->
		<ul class="inner">
			<li class="pic"><img src="userfiles/images/SB.png" alt="" /></li>
			<li class="tit">服务器异常</li>
			<li class="msg">服务器发生故障，请稍后再试...</li>
			<li class="look"><a href="/job/index.html">知道了</a></li>
		</ul>
	</div>
	<script src='plug-in/jquery/jquery-1.9.1.js'></script>
	<script>
		$(function() {

			//网页正文全文高
			var height = document.body.scrollHeight;
			document.getElementById('body')
			$("body").height(height);

		});
	</script>
</body>

</html>
