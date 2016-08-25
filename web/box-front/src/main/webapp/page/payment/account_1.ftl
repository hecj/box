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
	<!-- 以下为账户余额主页面 -->
	<#include "/page/common/n_head.ftl" />
	<div class="bg_wrap content content-col-f">
		<#include "/page/common/personal_head.ftl" />
		<div class="personal-con">
			<!-- 账户管理主页面 -->
			<div class="personal-cont-account-manegement" style="display:block">
				<div class="pcam-menu">
					<ul>
						<li><a href="javascript:;" class="current">账户余额</a></li>
						<li class="last"><a href="javascript:;">优惠券</a></li>
					</ul>
				</div>
				<div class="pcam-top" style="display:block">
					<h6>您当前的余额：</h6>
					<div class="pcam-top-num"><em>￥</em>${user.balance!0}</div>
					<div class="pcam-top-option">
						<input class="floatL" type="button" value="充值" onclick="recharge()"/>
						<input class="floatR" type="button" value="提现" onclick="withdraw()"/>
					</div>
				</div>

					<div class="pcam-cont" style="display:block">
						<div class="pcam-cont-menu">
							<ul>
								<li><a href="javascript:;">余额记录</a></li>
								<li><a href="javascript:;">提现记录</a></li>
							</ul>
						</div>
						<!-- 余额记录 -->
						<div class="balance-record present-record pcam-cont0" style="display:block">
							<#if fundrecordPage??>
							<#assign fundrecordList=fundrecordPage.getList()/>
								<div class="balance-record-header">
									<span class="present-record-header-time">时间</span>
									<span class="present-record-header-amount">事件类型</span>
									<span class="present-record-header-state">变动金额(元)</span>
									<span class="present-record-header-operation">当前金额(元)</span>
								</div>
								  <#setting datetime_format="yyyy-MM-dd HH:mm:ss"/>
								  <#list fundrecordList as fundrecord>
										<div class="balance-record-block">
											<span class="present-record-header-time">${fundrecord.trad_at?number_to_datetime}</span>
											<span class="present-record-header-amount">${fundrecord.remark!}</span>
											<span class="present-record-header-state">${fundrecord.handle_amount!}</span>
											<span class="present-record-header-operation">${fundrecord.after_amount!}</span>
											<span class="balance-record-header-delete"></span>			
										</div>
								 </#list>
							<#else>
								<div class="no-record" style="display:block">
									<p>您还没有消费记录</p>
								</div>
							</#if>
							<div class="page-wrap-sub" style="display:none"><div id="sub_page_footer" class="page-div clearfix"></div></div>			
						</div>
						<!-- 提现记录 -->
						<div class="balance-record pcam-cont1" style="display:none">
							<#if withdrawalsPage??>
							<#assign withdrawalsList=withdrawalsPage.getList()/>
								<div class="balance-record-header">
									<span class="balance-record-header-time">申请提现时间</span>
									<span class="balance-record-header-amount">提现金额(元)</span>
									<span class="balance-record-header-state">提现状态</span>
									<span class="balance-record-header-operation">操作</span>
								</div>
								  <#setting datetime_format="yyyy-MM-dd HH:mm:ss"/>
								  <#list withdrawalsList as withdrawals>
										<div class="balance-record-block">
											<span class="balance-record-header-time">${withdrawals.withdrawals_at?number_to_datetime}</span>
											<span class="balance-record-header-amount">${withdrawals.amount!}</span>
											<span class="balance-record-header-state">
												<#if withdrawals.status == 0 || withdrawals.status == 1 || withdrawals.status == 3>
												申请提现中，等待系统审批
												<#elseif withdrawals.status == 2 || withdrawals.status == 5>
												提现失败
												<#elseif withdrawals.status == 4>
												提现成功
												</#if>
											</span>
											<span class="balance-record-header-operation"><a href="/withdrawals/lookDetail/${withdrawals.id!}">查看详情</a></span>
											<span class="balance-record-header-delete"></span>			
										</div>
								 </#list>
							<#else>
								<div class="no-record" style="display:block">
									<p>您还没有提现记录</p>
								</div>
							</#if>
							<div class="page-wrap-sub" style="display:none"><div id="sub_page_footer_withdrawals" class="page-div clearfix"></div></div>			
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
	
	$(".pcam-cont-menu a").click(function(){
		var key = $(this).parent().index();
		$(this).addClass("current").parent().siblings().find("a").removeClass("current");
		$(".pcam-cont"+key+"").show().siblings().hide();
		$(".pcam-cont-menu").show();
	});
	
		!function cut(){
			var tab = ${tab!0};
			if(tab == 1){			
				$(".pcam-cont1").show();
				$(".pcam-cont0").hide();
				$(".pcam-cont-menu a:eq(1)").addClass("current").parent().siblings().find("a").removeClass("current");
			}else{				
				$(".pcam-cont0").show();
				$(".pcam-cont1").hide();
				$(".pcam-cont-menu a:eq(0)").addClass("current").parent().siblings().find("a").removeClass("current");
			}
		}();
		paginateFn("sub_page_footer", ${fundrecordPage.getTotalPage()!0}, ${fundrecordPage.getPageNumber()!1}, ${withdrawalsPage.getPageNumber()!1});
		paginateFn("sub_page_footer_withdrawals",${withdrawalsPage.getTotalPage()!0}, ${withdrawalsPage.getPageNumber()!1}, ${fundrecordPage.getPageNumber()!1});
	</script>
</body>
</html>