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
	<!-- 充值成功页面 -->
	<#include "/page/common/n_head.ftl" />
	<div class="bg_wrap content content-col-f">
		<#include "/page/common/personal_head.ftl" />
		<div class="personal-con">
			<!-- 充值成功 -->
			<div class="personal-cont-recharge-success" style="display:block">
				<span class="personal-cont-recharge-success-icon"></span>
				<p>恭喜您充值成功！</p>
				<div class="pcr-success">
					<span>您已成功充值<em>${amount!0}</em>元</span>
					<a href="/payment/recharge/">查看余额</a>
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