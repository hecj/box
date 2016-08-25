<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" >
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>找回密码2</title>
	<#include "/page/common/base/include.ftl">
</head>
<body>
	<!-- 引用顶部开始 -->
	<#include "/page/common/n_head.ftl">
	
	<!-- 引用顶部结束 -->
	<!-- 内容开始 -->
	<div class="bg_wrap .bg_wrap">
	<div class="retrieve-password-authenticate">
		<div class="retrieve-password-authenticate-in">
			<div class="rpa-top rp2-top">
				<ul class="rpa-progressbar rp2-progressbar">
					<li class="step-1"></li>
					<li class="step-2"></li>
					<li class="step-3"></li>
					<li class="step-4"></li>
				</ul>
				<ol class="rpa-word rp2-word">
					<li class="step-1">填写账户名</li>
					<li class="step-2">验证身份</li>
					<li class="step-3">设置新密码</li>
					<li class="step-4">完成</li>
				</ol>
			</div>
			<div class="rpa-cont rp2-cont">
				<div class="rpa-cont-in rp2-cont-in b-radius-20">
					<div class="rpa-form rp2-form" style="display:block">
						<form method="post" action="">
							<dl class="way clearfix">
								<dt>请选择验证身份方式</dt>
								<dd>
									<select class="way-select">
										<#if resultJson.code==2>
											<option value="1" <#if type==0>selected="true"</#if> >已验证手机</option>
											<option value="2" <#if type==1>selected="true"</#if> >邮箱</option>
										</#if>
										<#if resultJson.code==0>
											<option value="1">已验证手机</option>
										</#if>
										<#if resultJson.code==1>
											<option value="2">邮箱</option>
										</#if>
									</select>
								</dd>
							</dl>
							<dl class="nickname clearfix">
								<dt>昵称</dt>
								<dd><span>${resultJson.data.get("nickname")!}</span></dd>
							</dl>
							
							<dl class="cellphone s-phone clearfix" style="display:block">
								<dt>已验证手机</dt>
								<dd><span id="phone"></span></dd>
							</dl>							
							<dl class="phone-captcha s-phone clearfix" style="display:block">
								<dt>请填写手机验证码</dt>
								<dd><input class="phone-captcha-input" type="text" onkeyup="value=this.value.replace(/\D+/g,'')" /></dd>
								<dd class="btn">
									<span class="settimeout0" style="display:block"></span>
									<span class="settimeout1" id="settimeout" style="display:none"></span>
								</dd>
								<span class="show" style="display:none">手机验证码为6位数字</span>
								<span class="error" style="display:none">手机验证码输入错误，请重新输入</span>
								<span class="empty" style="display:none">请输入手机验证码</span>
							</dl>
							<dl class="submit s-phone cellphone-submit" style="display:block">
								<dd><input class="submit-btn cellphone-submit-btn" id="submit-phone" type="button"/></dd>
							</dl>
						
							<dl class="email s-email clearfix" style="display:none">
								<dt>邮箱地址</dt>
								<dd><span id="email"></span></dd>
							</dl>													
							<dl class="submit s-email email-submit" style="display:none">
								<dd><input class="submit-btn" id="submit-email" type="button"/></dd>
							</dl>						
						</form>
					</div>
					<div class="rp2-send" style="display:none">
						<span></span>
						<p class="first">验证邮件已发送成功</p>
						<p class="second">（请立即完成验证，邮件验证不通过则修改邮箱失败）</p>
						<p class="third">验证邮件24小时内有效，请尽快登录您的邮箱点击验证链接完成验证</p>
						<button id="btnSendEmail"></button>
					</div>
					
				</div>
			</div>
		</div>
	</div>
	<div id="type" style="display:none;">${type}</div>
	<div style="display:none;" id="hidden_email">${resultJson.data.get("email")!}</div>
	<div style="display:none;" id="hidden_phone">${resultJson.data.get("phone")!}</div>
	
	</div>
	<!-- 引用底部开始 -->
	<#include "/page/common/foot.ftl">
	
	<!-- 引用底部结束 -->
    <script type="text/javascript" src="static/js/common/jquery/jquery.js"></script>
    <!-- <script type="text/javascript" src="static/js/cf-retrieve-password-authenticate.js"></script> -->
    <script type="text/javascript" src="static/js/page/resetPassword.js?v=20151216"></script>
    <script type="text/javascript" src="static/js/DD_roundies_min.js"></script>
	<script type="text/javascript" src="/static/js/login_common.js"></script>
	<script type="text/javascript">	
		DD_roundies.addRule('.b-radius-20', '20px', true);
		
		jQuery(function(){
			if(${resultJson.data.get('id')}){
				<#if resultJson.data.get('email') ??>
					var semail = "${resultJson.data.get('email')}";
					if(semail!=""&&semail.length>=0){
						semail = subStr(semail,1);
					}
					$("#email").text(semail);
				</#if>
				
				<#if resultJson.data.get('phone') ??>
					var sphone = "${resultJson.data.get('phone')}";
					if(sphone!=""&&sphone.length>=0){
						sphone = subStr(sphone,0);
					}
					$("#phone").text(sphone);
				</#if>
			}
		});
		
		function subStr(val,num){
			var objStr = "";
			if(num==0){
				objStr = val.substring(0,3)+"****"+val.substring (7);
			}else if(num==1){
				var len = val.length;
				var len_1 = val.indexOf("@"); 
				if(len_1<=2){
					objStr = val;
				}else{
					objStr = val.substring(0,1);
					for(var i=0;i<len_1-2;i++){
						objStr += "*";
					}
					objStr += val.substring(len_1-1);
				}
			}
			return objStr;
		}
	</script>    
</body>
</html>