<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" >
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>项目发布</title>
	
	<#include "/page/common/base/include.ftl">
	
</head>
<body>
	<!-- 头部引用 -->
	<#include "/page/common/n_head.ftl">
	
	<div class="content bg_wrap">
		<div class="flow-chart"></div>
		<div class="content-in">
			<div class="content-in-in">
				<img class="cont-logo" src="/static/images/cf-project-release/content-logo.png" alt="">
				<div class="cont-ad">
					<p>在这里发起您的梦想、</p>
					<p>创意和创业计划，</p>
					<p>便可面对公众集资，</p>
					<p>让有创造力的您获得所需要的资金帮助您的梦想实现。</p>
					<p class="cont-ad-agree">
						<input type="checkbox" value="agree" id="agree" checked="checked">
						<a href="/" target="_blank">我了解并阅读后同意盒子尖叫的《服务协议》</a>
					</p>
					<button class="cont-dream" id="cont-dream"></button>
					<p class="cont-hotline">您也可以直接拨打我们的客服热线</p>
					<p class="cont-hotline-num">400-000-0000</p>					
				</div>
			</div>
		</div>
	</div>
	
	<#include "/page/common/foot.ftl" />
	 
	<script type="text/javascript">
		 $(document).ready(function () {
		     $('#cont-dream').bind('click', function () {
		         if ($("#agree").prop('checked')) {
		             location.href = '/projectapply/createapply';
		         } else {
		            $("#box-alert .feedback-success-con p").text("请确认您了解并阅读后同意盒子尖叫的服务协议");
		            $("#box-alert .feedback-success-con p").css({
		            	"textAlign": "left"
		            });
		            $("#box-alert").show();
		         }
		     });
		     
		    $(".feedback-close ,.feedback-submit").click(function(event) {
				$("#box-alert").hide();
			});
		});
		 
	</script>
</body>
</html>