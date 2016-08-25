<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<title>找回密码3</title>
	<link rel="stylesheet" type="text/css" href="static/css/public.css">
	<link rel="stylesheet" type="text/css" href="static/css/content.css"></head>	
</head>
<body>
	<!-- 引用顶部开始 -->
	<!--
	<div class="top">
		<div class="top-in">
			<ul>
				<li><a class="top-logo" href="#"><img src="static/images/top-footer/logo.png" alt=""></a></li>
				<li><a class="top-font top-list" href="#">盒子·众筹</a></li>
				<li><a class="top-font top-menu" href="#">众筹项目</a></li>
			</ul>
			<div class="top-search">
				<input type="text" value="Search...">
				<button>Go</button>
				<i></i>
			</div>
			<a href="#" class="top-photo">
				<i class="top-photo-num">99</i>
			</a>
		</div>
	</div>
	-->
	<!-- 引用顶部结束 -->
	<!-- 内容开始 -->
	<div class="retrieve-password-set">
		<div class="retrieve-password-set-in">
			<div class="rpa-top rp3-top">
				<ul class="rpa-progressbar rp3-progressbar">
					<li class="step-1"></li>
					<li class="step-2"></li>
					<li class="step-3"></li>
					<li class="step-4"></li>
				</ul>
				<ol class="rpa-word rp3-word">
					<li class="step-1">填写账户名</li>
					<li class="step-2">验证身份</li>
					<li class="step-3">设置新密码</li>
					<li class="step-4">完成</li>
				</ol>
			</div>
			<div class="rpa-cont rp3-cont">
				<div class="rpa-cont-in rp3-cont-in b-radius-20">					
					<div class="rpa-form rp3-form">
						<form method="post" action="">
							<span class="icon"></span>
							<dl class="setpassword clearfix">
								<dt>新登录密码</dt>
								<dd><input class="name-input newpassword-input" type="password" value="" /></dd>
								<span class="empty" style="display:none">请输入密码</span>
								<span class="show" style="display:none">由6-16位字母加数字组成，区分大小写</span>
								<span class="error0" style="display:none">密码长度不符，请重新输入</span>
								<span class="error1" style="display:none">密码太弱，请设置有多种字符组成的复杂密码</span>
							</dl>
							<dl class="surepassword clearfix">
								<dt>确定新密码</dt>
								<dd><input class="name-input surepassword-input" type="password" value="" /></dd>
								<span class="empty" style="display:none">请再次输入新密码</span>
								<span class="show" style="display:none">请再次输入新密码</span>
								<span class="error" style="display:none">两次密码输入不一致，请重新输入</span>
							</dl>
							<dl class="submit">
								<dd><input class="submit-btn" type="button"/></dd>
							</dl>														
						</form>
					</div>
				</div>
			</div>			
		</div>
	</div>
	<!-- 引用底部开始 -->
	<#include "/page/common/foot.ftl">
	<!--
	<div class="footer">
		<div class="footer-in">
			<div class="footer-contact">
				<p>
					<a href="#">关于我们</a><b>|</b>
					<a href="#">联系我们</a><b>|</b>
					<a href="#">服务协议</a><b>|</b>
					<a href="#">新手帮助</a><b>|</b>
					<a href="#">合作机构</a><b>|</b>
					<a href="#">法律声明</a>
				</p>
				<p>
					<span>客服电话：4000-0000-000</span><span>版权信息：京icp28381823</span>
				</p>
			</div>
			<div class="footer-attention">
				<p>关注我们：</p>
				<a class="wechat" href="#">微信</a>
				<a class="qq" href="#">腾讯</a>
				<a class="douban" href="#">豆瓣</a>
				<a class="microblog" href="#">微博</a>
			</div>
		</div>
	</div>
	 -->
	 
	<!-- 引用底部结束 -->
    <script type="text/javascript" src="static/js/jquery.js"></script>
    <script type="text/javascript" src="static/js/cf-retrieve-password-set.js"></script>
    <script type="text/javascript" src="static/js/DD_roundies_min.js"></script>
	<script type="text/javascript">	
		DD_roundies.addRule('.b-radius-20', '20px', true);
	</script>		
</body>
</html>