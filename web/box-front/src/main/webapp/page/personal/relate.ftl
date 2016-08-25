<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>绑定盒子账号</title>
	<#include "/page/common/base/include.ftl">
</head>
<body>
	<!-- 引用顶部开始 -->
	<#include "/page/common/n_head.ftl">
	
	<!-- 引用顶部结束 -->
	<!-- 内容开始 -->
	<div class="bind-account bg_wrap">
		<div class="bind-account-in">
			<div class="ba-top">
				<div class="ba-top-in">
					<span class="bat-icon"></span>
					<h6 class="bat-tittle"><span class="bat-tittle-name">绑定盒子尖叫账号</span></h6>
				</div>
			</div>
			<div class="ba-cont">
				<div class="ba-cont-form b-radius-20">
					<form method="post" action="">
						<dl class="clearfix">
							<dt>账户名</dt>
							<dd><input class="name-input" type="text" value="请输入注册手机号/邮箱" onkeyup="value=value.replace(/\s/g,'')"/></dd>
							<!-- <span class="pb-show" style="display:none">请正确输入账号</span> -->
							<span class="pb-empty" style="display:none">请输入您的手机号/邮箱</span>
							<span class="pb-error" style="display:none">账号不存在</span>
							<span class="pb-error1" style="display:none">账号已绑定</span>
						</dl> 
						<dl class="clearfix">
							<dt>确认密码</dt>
							<dd><input class="password-input" type="password" onkeyup="value=value.replace(/\s/g,'')"/></dd>
							<span class="pb-show" style="display:none">由6-16位字母加数字或符号组成，区分大小写</span>
							<span class="pb-empty" style="display:none">请输入密码</span>
							<span class="pb-error" style="display:none">密码错误</span>
							<span class="pb-error0" style="display:none">密码长度不符，请重新输入</span>
							<span class="pb-error1" style="display:none">密码太弱，请设置有多种字符组成的复杂密码</span>
						</dl>
					</form>
				</div>
				<button class="ba-cont-sure">确定</button>
			</div>
		</div>
	</div>
	<div id="back_url" style="display:none;">${back_url!}</div>
	<!-- 引用底部开始 -->
	<#include "/page/common/foot.ftl">
	
	<!-- 引用底部结束 -->
	<script type="text/javascript" src="../static/js/common/jquery/jquery.js"></script>
	<!-- <script type="text/javascript" src="static/js/cf-register-supply-information.js"></script> -->
	<script type="text/javascript" src="../static/js/page/personal/relate.js">
    <script type="text/javascript" src="/static/js/login_common.js"></script>
    <script type="text/javascript" src="../static/js/DD_roundies_min.js"></script>
	<script type="text/javascript">	
		DD_roundies.addRule('.b-radius-20', '20px', true);
	</script>  
</body>
</html>