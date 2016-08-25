<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>提现</title>
	<#include "/page/common/base/include.ftl">
</head>
<body>
	<!-- 引用顶部开始 -->
	<#include "/page/common/n_head.ftl" />
	<!-- 引用顶部结束 -->
	<!-- 内容开始 -->
	<div class="bg_wrap content content-col-f">
		<#include "/page/common/personal_head.ftl" />
		<div class="personal-con">
			<div class="personal-cont-account-withdrawcash">
				<h6>提现<a class="return-withdrawcash" href="/payment/recharge/">返回提现记录</a></h6>
				<div class="pcaw-cont">
					<span class="pcaw-cont-icon"></span>
					<dl class="amount-money">
						<dt>余额：</dt>
						<dd>￥<span id="balance">${balance!0}</span></dd>
					</dl>
					<dl class="withdraw-money">
						<dt>提现金额：</dt>
						<dd><input type="text" name="withdrawcash" id="money" onpaste="return false" onfocus="this.style.imeMode='disabled'" ondrop="return false"/><span>元</span></dd>
						<span class="pb-sign pb-empty"></span>
					</dl>
					<dl>
						<dt>到账账户：</dt>
						<dd>将提现至您支付/充值时所选的支付方<em>(可能分多笔依次到账)</em></dd>
					</dl>
					<dl>
						<dt>到账时间：</dt>
						<dd>3-10个工作日</dd>
					</dl>
					<dl class="submit"><input type="button" name="withdraw-subbtn" value="申请提现" /></dl>
				</div>
			</div>
			<div class="pcaw-prompt">
				<h6>温馨提示：</h6>
				<div class="account">
					<h5>到账账户</h5>
					<p>将提现至您支付／充值时所选择的支付方，选择支付宝／财付通退回对应的余额，选择银行支付则退回对应的银行。由于余额可能会有多个支付来源，提现时也会对应分多笔到账。</p>
				</div>
				<div class="time">
					<h5>到账时间</h5>
					<p>3 - 10个工作日内可退回您的支付账户，由于银行处理可能有延迟，具体以账户的到账时间为准。</p>				
				</div>
				<div class="how-delete">
					<h5>申请提现后是否可以取消？</h5>
					<p>由于申请后，我们会立即向第三方提出申请，所以提现无法取消，请您谅解。</p>	
				</div>
			</div>
			<!-- 提示是否提现弹窗 -->
			<div class="pub-mask-layer" style="display:none"></div>
			<div class="pub-popup" style="display:none">
				<div class="pub-popup-in pcaw-sure">
					<div class="pub-popup-top">
						<h6>提示</h6>
						<a href="javascript:;" class="popup-close"></a>
					</div>
					<div class="pub-popup-cont">
						<span class="first">您申请提现金额为：￥<span id="withdrawValue" style="  display: inline-block;"/></span>
						<span class="second">确定申请吗？</span>
						<dl class="submit">
							<input class="sure" type="button" name="submit-btn" value="确定" />
							<input class="cancel" type="button" name="submit-btn" value="取消" />
						</dl>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- 内容结束 -->
	<!-- 引用底部开始 -->
	<#include "/page/common/foot.ftl" />
	<!-- 引用底部结束 -->
	<script type="text/javascript" src="/static/js/page/personal/withdrawcash.js"></script>	
</body>
</html>