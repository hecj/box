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
	<!-- 以下为优惠券主页面 -->
	<#include "/page/common/n_head.ftl" />
	<div class="bg_wrap content content-col-f">
		<#include "/page/common/personal_head.ftl" />
		<div class="personal-con">
			<!-- 账户管理主页面 -->
			<div class="personal-cont-account-manegement" style="display:block">
				<div class="pcam-menu">
					<ul>
						<li><a href="javascript:;">账户余额</a></li>
						<li class="last"><a href="javascript:;" class="current">优惠券</a></li>
					</ul>
				</div>
				<!-- 优惠券 -->
				<div class="pcam-home1" style="display:block">
					<div class="pcam-coupons" style="display:block">
						<h6>我的优惠券</h6>
						<div class="pcam-coupons-cont">
							<div class="balance-record-header coupons-record-header">
								<span class="coupons-record-header-id">券号</span>
								<span class="coupons-record-header-par">面值</span>
								<span class="coupons-record-header-validityperiod">有效期</span>
								<span class="coupons-record-header-rule">使用规则</span>
								<span class="coupons-record-header-limit">限制类别</span>
								<span class="coupons-record-header-state">状态</span>
							</div>
							<div class="balance-record-block">
								<span class="coupons-record-header-id">570040</span>
								<span class="coupons-record-header-par">￥10.00</span>
								<span class="coupons-record-header-validityperiod">2015-11-03至2015-12-03</span>
								<span class="coupons-record-header-rule">可用于所有项目</span>
								<span class="coupons-record-header-limit">通用</span>
								<span class="coupons-record-header-state">未使用</span>
								<span class="balance-record-header-delete"></span>	
							</div>
							<div class="balance-record-block">
								<span class="coupons-record-header-id">570040</span>
								<span class="coupons-record-header-par">￥10.00</span>
								<span class="coupons-record-header-validityperiod">2015-11-03至2015-12-03</span>
								<span class="coupons-record-header-rule">可用于所有项目</span>
								<span class="coupons-record-header-limit">通用</span>
								<span class="coupons-record-header-state">未使用</span>
								<span class="balance-record-header-delete"></span>	
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
	<script type="text/javascript">
		paginate(${totalPage}, ${pageNumber});
	</script>
</body>
</html>