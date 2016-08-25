<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" >
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>找回密码3</title>
	<#include "/page/common/base/include.ftl">
</head>
<body>
	<!-- 引用顶部开始 -->
	<#include "/page/common/n_head.ftl" />
	<!-- 引用顶部结束 -->
	<!-- 内容开始 -->
	<div class="retrieve-password-set bg_wrap">
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
						<form method="post">
							<span class="icon"></span>
								<input type ="text" name="id" id="id" hidden="true" value=${id!}>
								<input type ="text" name="uuid" id="uuid" hidden="true" value='${token!}'>
							<dl class="setpassword clearfix">
								<dt>新登录密码</dt>
								<dd><input class="name-input newpassword-input" type="password" value="" /></dd>
								<span class="empty" style="display:none">请输入密码</span>
								<span class="show" style="display:none">由6-16位字母加数字组成，区分大小写</span>
								<span class="error0" style="display:none">密码长度不符，请重新输入</span>
								<span class="error1" style="display:none">请设置有6-16位数字加字母组成的复杂密码</span>
							</dl>
							<dl class="surepassword clearfix">
								<dt>确定新密码</dt>
								<dd><input class="name-input surepassword-input" type="password" value="" /></dd>
								<span class="empty" style="display:none">请再次输入新密码</span>
								<span class="show" style="display:none">请再次输入新密码</span>
								<span class="error" style="display:none">两次密码输入不一致，请重新输入</span>
							</dl>
							<dl class="submit">
								<dd><input class="submit-btn" type="button" /></dd>
							</dl>														
						</form>
					</div>
				</div>
			</div>			
		</div>
	</div>
	<!-- 引用底部开始 -->
	<#include "/page/common/foot.ftl">
	<!-- 引用底部结束 -->
    <script type="text/javascript" src="static/js/common/jquery/jquery.js"></script>
    <!-- <script type="text/javascript" src="static/js/cf-retrieve-password-set.js"></script>  -->
    <script type="text/javascript" src="static/js/page/newPwd.js?v=20151208"></script>
    <script type="text/javascript" src="static/js/DD_roundies_min.js"></script>
	<script type="text/javascript" src="/static/js/login_common.js"></script>
	<script type="text/javascript">	
		DD_roundies.addRule('.b-radius-20', '20px', true);
	</script>		
</body>
</html>