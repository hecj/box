<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<title>个人中心--提现第一步</title>
	<#include "/page/common/base/include.ftl">
</head>
<body>
   <!-- 头部引用 -->
	<#include "/page/common/n_head.ftl" />
   <!-- 内容start！ -->
	<div class="content bg_wrap content-col-f">
	<#include "/page/common/personal_head.ftl" />
		<div class="withdrawl">
			<div class="withdrawl-head">
				<h3>提现详情</h3>
				<a href="/payment/recharge/">返回提现记录</a>
			</div>
			<div class="withdrawl-step-wrap">
				<div class="withdrawl-step-con">
					<p>提现金额： <span>¥${amount!} </span></p>
					<h3>提现申请已提交</h3>
					
				</div>
				<div class="withdrawl-step-bar">
					<span class="step-cur">1</span>
					 <i></i>
					<#if verify_at??>
						<span class="step-cur">2</span>
					<#else>
						<span class="step-no">2</span>
					</#if>
					<i></i>
					<span class="step-no no-ml">3</span>
				</div>
				<div class="withdrawl-step-static">
					<span>提现审核中</span> <i></i><span class="withdrawl-wrap">盒子提现处理</span> <i class="width-small"></i><span class="withdrawl-wrap">支付平台处理</span>

				</div>
				 <#setting datetime_format="yyyy-MM-ddHH:mm:ss"/>
				<div class="withdrawl-step-static no-color">
					<span>${withdrawal_at?number_to_datetime}</span> <i style="width:132px"></i><span><#if verify_at??>${verify_at?number_to_datetime}</#if></span> <i class="width-small"></i><span></span>
				</div>
			</div>
			<div class="withdrawl-reminder">
				<div class="withdrawl-reminder-head">
					<h3>温馨提示：</h3>
				</div>
				<div class="withdrawl-reminder-con">
					<div class="withdrawl-reminder-one">
						<h4><i></i>什么是支付平台？</h4>
						<p>将提现至您支付／充值时所选择的支付方，选择支付宝／财付通退回对应的余额，选择银行支付则退回对 应的银行。由于余额可能会有多个支付来源，提现时也会对应分多笔到账。</p>
					</div>
					<div class="withdrawl-reminder-one">
						<h4><i></i>如何查询提现是否已到账？</h4>
						<p>支付宝查询退款，请您登录支付宝，进入“最近交易记录－退款记录”查看；财付通查询退款，请您登录财付通，进入“交易查询－收支明细”查看。银行卡查询退款，请您登录网上银行查询或直接拨打银行客服电话。</p>
					</div>	
					<div class="withdrawl-reminder-one">
						<h4><i></i>提现是否有到帐通知短信？</h4>
						<p>目前暂无到账通知短信，请您谅解，需要您前往支付宝／财付通／银行自行查询。</p>
					</div>	
					<div class="withdrawl-reminder-one">
						<h4><i></i>为何会有多条提现申请？</h4>
						<p>由于该笔金额是分多笔充值的，提现时也会相应分为多笔，退至您支付宝／财付通／银行卡。</p>
					</div>
				</div>
			</div>
		</div>
		
	</div>
	<div class="pay-way-wrap"></div>	
   <!-- 内容end！ -->
   <#include "/page/common/foot.ftl" />
<script type="text/javascript" src="static/js/jquery.js"></script>   
<script type="text/javascript">
	$(".pay-way-wrap").height($(document).height());
	$(".return-letterBtn").click(function(event) {
		$(".pay-way-wrap").show();
		$(".personalsetting-send").show();
	});
	$(".popup-close").click(function(event) {
		$(".pay-way-wrap").hide();
		$(".personalsetting-send").hide();
	});

	$(".letter-send").keyup(function(event) {
           
                var count = $(this).val().length; 
				if(count>300){ 

				  var nr = $(this).val().substring(0,300); 
				 
				  $(this).val(nr); 
				  count=300; 
				}
		    });
     $(".close-letter").click(function(event) {
     		$(".pay-way-wrap").hide();
		$(".personalsetting-send").hide();
     });
	function limit(){
		var str = $(".word_limit");
		for (var i = 0; i < str.length; i++) {
			$(".word_limit").eq(i).text($(".word_limit").eq(i).text().substring(0,86)+"...");
		};
	}	
	limit();
</script>
</body>
</html>



