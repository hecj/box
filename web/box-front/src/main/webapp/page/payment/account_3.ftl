<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<title>盒子众筹-中国最具影响力的创业众筹融资平台</title>
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="keywords" content="众筹,融资,众筹平台,创业融资"/>
	<meta name="description" content="盒子众筹是中国最具影响力的创业众筹融资平台，专业为有梦想、有创意、有项目的朋友提供募资、投资、孵化、运营一站式综合众筹服务，协助您实现自己的创业梦想。好平台，好起点，尽在盒子众筹！"/>
	<#include "/page/common/base/include.ftl">
</head>
<body>
	<!-- 账户余额页面点击充值后的充值输入框主页面，即充值主页面，包括设置支付密码、支付密码设置成功的弹窗 -->
	<#include "/page/common/n_head.ftl" />
	<div class="bg_wrap content content-col-f">
		<#include "/page/common/personal_head.ftl" />
		<div class="personal-con">
			<!-- 充值金额输入框 -->
			<div class="personal-cont-recharge" style="display:block">
				<span class="personal-cont-recharge-icon"></span>
				<div class="pcr-homepage">
					<dl class="clearfix">
						<dt>充值金额：</dt>
						<dd><input type="text" id="money" onpaste="return false" onfocus="this.style.imeMode='disabled'" ondrop="return false"/><span>元</span></dd>
						<span class="pb-empty"></span>
					</dl>
					<dl class="pcr-submit"><input type="button" name="pcr-subbtn" value="确定，去付款" onclick="gotopay(${exist_pay_pwd})"/></dl>
					<span class="pb-sign pb-empty"></span>
				</div>
				<div style="display:none">
					<form name="input" id="amountForm"  action="/payment/choosePayType/" method="post">
						<input type="text" name="amount" />
					</form>
				</div>
			</div>
			<#if exist_pay_pwd != 1>
			<div class="pub-mask-layer" style="display:block"></div>
			<div class="pub-popup" style="display:block">
				<!-- 设置支付密码 -->
				<div class="pub-popup-in pcr-set-paypassword" style="display:block">
					<div class="pub-popup-top">
						<h6>设置支付密码</h6>
						<a href="javascript:;" class="popup-close"></a>
					</div>
					<div class="pub-popup-cont">
						<div class="pcr-sp-reminder">
							<span class="first"><em></em>为了保障您的账户安全，您需要设置支付密码</span>
							<span class="second">设置后，在余额支付时需要直接输入支付密码</span>
						</div>
						<dl class="paypassword">
							<dt>设置支付密码</dt>
							<dd>
								<input type="password" name="paypassword"  style="ime-mode:disabled"  class="limitinput" />
								<span class="pb-ph unique2">请输入要设置的密码</span>
							</dd>
							<span class="pb-sign pb-empty"></span>
						</dl>
						<dl class="surepaypassword">
							<dt>确定支付密码</dt>
							<dd>
								<input type="password" name="surepaypassword"  style="ime-mode:disabled"  class="limitinput" />
								<span class="pb-ph unique2">请再次输入设置的密码</span>
							</dd>
							<span class="pb-sign pb-empty"></span>
						</dl>
						<#if exist_phone == 1>
						<dl class="bindphone everbindphone">
						<dt>绑定手机号</dt>
							<dd>
								<span id="phoneval">${phone!}</span>
							</dd>
							<span class="pb-sign pb-empty"></span>
						</dl>
						<#else>
						<dl class="bindphone">
						<dt>绑定手机号</dt>
							<dd>
								<input type="text" name="bindphone"  style="ime-mode:disabled" />
								<span class="pb-ph unique2">请再次输入手机号</span>
							</dd>
							<span class="pb-sign pb-empty"></span>
						</dl>
						</#if>
						<dl class="dynamic-code">
							<dt>验证码</dt>
							<dd><input type="text" name="dynamic-code" class="limitinput"/></dd>
							<dd><button class="settimeout" onclick="sc_tellphone(this, ${exist_phone})">获取短信验证码</button></dd>
							<span class="pb-sign pb-empty"></span>
						</dl>																			
						<dl class="submit paypassword-submit"><input type="button" name="paypassword-subbtn" value="确定提交" /></dl>
					</div>
				</div>
				</#if>
				<!-- 成功页面 -->
				<div class="pub-popup-in pcf-sp-success" style="display:none">
					<div class="pub-popup-top">
						<h6>提示</h6>
						<a href="javascript:;" class="popup-close-submit"></a>
					</div>
					<div class="pub-popup-cont">
						<span class="pcf-sp-success-icon"></span>
						<span class="first">您的支付密码设置成功，请继续您的付款操作</span>
						<span class="second">在下次余额支付时需要您输入支付密码</span>
						<dl class="submit success-submit"><input type="button" name="submit-btn" value="确定" /></dl>
					</div>
				</div>
			</div>
		</div>
		<!-- 内容结束 -->
	</div>
	<#include "/page/common/foot.ftl" />
	<script type="text/javascript" src="/static/js/page/payment/cf-personalcenter-account.js"></script>
	<script type="text/javascript" src="/static/js/page/full_paging.js"></script>
	<script type="text/javascript" src="/static/js/page/payment/payment.js"></script>	
</body>
</html>