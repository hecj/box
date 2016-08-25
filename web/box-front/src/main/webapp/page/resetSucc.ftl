<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" >
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>找回密码4</title>
	<#include "/page/common/base/include.ftl">
</head>
<body>
	<!-- 引用顶部开始 -->
	<#include "/page/common/n_head.ftl">
	
	<!-- 引用顶部结束 -->
	<!-- 内容开始 -->
	<div class="retrieve-password-success bg_wrap">
		<div class="retrieve-password-success-in">
			<div class="rpa-top rp4-top">
				<ul class="rpa-progressbar rp4-progressbar">
					<li class="step-1"></li>
					<li class="step-2"></li>
					<li class="step-3"></li>
					<li class="step-4"></li>
				</ul>
				<ol class="rpa-word rp4-word">
					<li class="step-1">填写账户名</li>
					<li class="step-2">验证身份</li>
					<li class="step-3">设置新密码</li>
					<li class="step-4">完成</li>
				</ol>
			</div>
			<div class="rpa-cont rp4-cont">
				<div class="rpa-cont-in rp4-cont-in b-radius-20">
					<div class="rp4-success">
						<span class="icon"></span>
						<p class="first">新密码设置成功</p>
						<p class="second">请您牢记您新设置的密码</p>
						<span class="timeout"><strong id="jump1">5&nbsp;</strong>秒自动跳转至首页</span>
						<button id="directBtn"></button>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 引用底部开始 -->
	<#include "/page/common/foot.ftl">
	
	<!-- 引用底部结束 -->
    <script type="text/javascript" src="static/js/common/jquery/jquery.js"></script>
    <!-- <script type="text/javascript" src="static/js/cf-retrieve-password-apply.js"></script> -->
    <script type="text/javascript" src="static/js/page/resetSucc.js"></script>   
    <script type="text/javascript" src="static/js/DD_roundies_min.js"></script>
	<script type="text/javascript" src="/static/js/login_common.js"></script>
	<script type="text/javascript">	
		DD_roundies.addRule('.b-radius-20', '20px', true);
	</script>			
</body>
</html>