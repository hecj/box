<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8">
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>找回密码1</title>
	<#include "/page/common/base/include.ftl">
</head>
<body>
	<!-- 引用顶部开始 -->
	<#include "/page/common/n_head.ftl">
	<!-- 引用顶部结束 -->
	<!-- 内容开始 -->
	<div class="retrieve-password-apply bg_wrap">
		<div class="retrieve-password-apply-in">
			<div class="rpa-top">
				<ul class="rpa-progressbar">
					<li class="step-1"></li>
					<li class="step-2"></li>
					<li class="step-3"></li>
					<li class="step-4"></li>
				</ul>
				<ol class="rpa-word">
					<li class="step-1">填写账户名</li>
					<li class="step-2">验证身份</li>
					<li class="step-3">设置新密码</li>
					<li class="step-4">完成</li>
				</ol>
			</div>
			<div class="rpa-cont">
				<div class="rpa-cont-in b-radius-20">
					<div class="rpa-form">
						<form method="post">
							<dl class="name clearfix">
								<dt>账号名</dt>
								<dd><input class="name-input" type="text" name="phoneAndEmail" id="phoneAndEmail" value="请输入注册手机号/邮箱" onkeyup="value=value.replace(/\s/g,'')"/></dd>
								<span class="error" style="display:none">账号不存在</span>
								<span class="empty" style="display:none">请输入您的手机号/邮箱</span>
							</dl>
							<dl class="captcha clearfix">
								<dt>验证码</dt>
								<dd><input class="captcha-input" type="text" id="randomCode" onkeyup="value=value.replace(/[^\w]|_/ig,'')" /></dd>
								<dd class="image">
									<!--<img src="static/images/cf-retrieve-password/img4.png" alt="" />-->
									<img id="createImage" src="image"  alt="验证码图片" />
									<!--<a onclick="myReload()">看不清？换一张</a> -->
								</dd>
								<dd class="btn"><span class="btn-in" onclick="myReload()"></span></dd>
								<span class="show" style="display:none">输入图中字符不分大小写</span>
								<span class="error" style="display:none">验证码错误</span>
								<span class="empty" style="display:none">请输入图形验证码</span>
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
	
	-->
	<!-- 引用底部结束 -->
    <script type="text/javascript" src="static/js/common/jquery/jquery.js"></script>
    <!-- <script type="text/javascript" src="static/js/cf-retrieve-password-apply.js"></script> -->
    <script type="text/javascript" src="static/js/page/findPassword.js"></script>
    <script type="text/javascript" src="static/js/DD_roundies_min.js"></script>
    <script type="text/javascript" src="/static/js/login_common.js"></script>
	<script type="text/javascript">	
		DD_roundies.addRule('.b-radius-8', '8px', true);
		DD_roundies.addRule('.b-radius-20', '20px', true);
	</script>   
</body>
</html>