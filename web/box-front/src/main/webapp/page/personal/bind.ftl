<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>注册完善信息</title>
	<#include "/page/common/base/include.ftl">
</head>
<body>
	<!-- 引用顶部开始 -->
	<#include "/page/common/n_head.ftl">
	
	<!-- 引用顶部结束 -->
	<!-- 内容开始 -->
	<div class="register-supply-information bg_wrap">
		<div class="register-supply-information-in">
			<div class="ba-top">
				<div class="ba-top-in">
					<span class="bat-icon rsi-icon"></span>
					<div class="bat-tittle rsi-tittle">
						<span class="bat-tittle-name">完善信息</span>
						<span class="bat-tittle-more">已经注册过尖叫社区？<a href="/personal/relate" target="_blank">&nbsp;关联已有账号</a></span>
					</div>
				</div>
			</div>
			<div class="ba-cont rsi-cont">
				<div class="ba-cont-form b-radius-20 rsi-cont-form">
					<form method="post" action="">
						<dl class="clearfix">
							<dt>手机号</dt>
							<dd><input class="phone-input" type="text" /></dd>
							<span class="pb-empty" style="display:none">请输入手机号</span>
							<span class="pb-error" style="display:none">请输入正确的手机号</span>
							<span class="pb-error1" style="display:none">手机号已存在</span>
						</dl>
						<dl class="phone-captcha clearfix" style="display:block">
							<dt>验证码</dt>
							<dd class="short-width"><input class="phone-captcha-input" type="text"  /></dd>
							<dd class="btn">
								<span class="settimeout0" style="display:block" onclick="sendCode('',$('.phone-input').val())"></span>
								<span class="settimeout1" id="settimeout" style="display:none"></span>
							</dd>
							<span class="pb-show" style="display:none">手机验证码为6位数字</span>
							<span class="pb-error" style="display:none">手机验证码输入错误，请重新输入</span>
							<span class="pb-empty" style="display:none">请输入手机验证码</span>
							<span class="pb-show1" style="display:none">验证码已发送至手机，请在10分钟内输入</span>
						</dl>						
						<dl class="clearfix">
							<dt>密码</dt>
							<dd><input class="password-input" type="password" placeholder="" /></dd>
							<span class="pb-empty" style="display:none">请输入密码</span>
							<span class="pb-show" style="display:none">由6-16位字母加数字组成，区分大小写</span>
							<span class="pb-error0" style="display:none">密码长度不符，请重新输入</span>
							<span class="pb-error1" style="display:none">密码太弱，请设置有多种字符组成的复杂密码</span>
						</dl>						
						<dl class="clearfix">
							<dt>确认密码</dt>
							<dd><input class="surepassword-input" type="password" placeholder="" /></dd>
							<span class="pb-empty" style="display:none">请再次输入新密码</span>
							<span class="pb-show" style="display:none">请再次输入新密码</span>
							<span class="pb-error" style="display:none">两次密码输入不一致，请重新输入</span>
						</dl>
					</form>
				</div>
				<div class="rsi-cont-sure">
					<span class="rsics-word clearfix">
						<input id="checkbox-input" type="checkbox" value="agree" checked="checked" />注册并同意《用户注册服务协议》
					</span>
					<button class="ba-cont-sure rsics-btn">确定</button>
				</div>
				
			</div>
		</div>
	</div>
	<div id="back_url" style="display:none;">${back_url!}</div>
	<!-- 引用底部开始 -->

	<#include "/page/common/foot.ftl">
	
	<!-- 引用底部结束 -->
	<script type="text/javascript" src="../static/js/common/jquery/jquery.js"></script>
	<script type="text/javascript" src="../static/js/page/personal/bind.js?v=20151211"></script>
    <script type="text/javascript" src="/static/js/login_common.js"></script>
    <script type="text/javascript" src="../static/js/DD_roundies_min.js"></script>
	<script type="text/javascript">	
		DD_roundies.addRule('.b-radius-20', '20px', true);
	</script>  
</body>
</html>