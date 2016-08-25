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
	<!-- 充值主页面点击充值跳到的充值方式选择页面，包括已完成付款或付款遇到问题的弹框 -->
	<#include "/page/common/n_head.ftl" />
	<div class="bg_wrap content content-col-f">
		<#include "/page/common/personal_head.ftl" />
		<div class="personal-con">
			<!-- 充值方式选择 -->
			<div class="personal-cont-recharge-payway" style="display:block">
				<span class="personal-cont-recharge-icon"></span>
				<div class="pcr-homepage pcr-homepage-payway">
					<p class="pcr-h-amount">充值金额：<span>${money}</span>&nbsp;&nbsp;元</p>
					<div class="pcr-h-payway">
						<div class="pcr-h-payway-block current">
							<label class="payway-block-icon">
								<input type="radio" />
								<span></span>
							</label>
							<span class="payway-block-bg"></span>
						</div>
					</div>
					<div class="pcr-payway-submit"><input type="button" name="pcr-payway-subbtn" value="去付款" /></div>
				</div>
				<div style="display:none">
					<form name="input" id="amountForm" target="_blank"  action="/payment/rechargesub/" method="post">
						<input type="text" name="amount" />
						<input type="text" name="ordernum" value="${order_num!}"/>
					</form>
				</div>
			</div>

			<div class="pub-mask-layer" style="display:none"></div>
			<div class="pub-popup" style="display:none">
				<!-- 付款成功或支付遇到问题 -->
				<div class="pub-popup-in pcf-gopay" style="display:block">
					<div class="pub-popup-top">
						<h6>提示</h6>
						<a href="javascript:;" class="popup-close"></a>
					</div>
					<div class="pub-popup-cont">
						<span class="first"><em></em>请您在新打开的页面上完成付款</span>
						<span class="second">付款完成前请不要关闭此窗口</span>
						<span class="second">完成付款后请根据您的情况点击下面的按钮</span>
						<div class="success-submit">
							<input type="button" name="pcf-gopay-over" value="已完成付款" />
							<input type="button" name="pcf-gopay-problem" value="付款遇到问题" />
						</div>
					</div>
				</div>	
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