<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta charset="UTF-8">
	<title>支付页面--提交订单--支付成功</title>
	<#include "/page/common/base/include.ftl">
<body>
	<!-- 引用顶部开始 -->
	<#include "/page/common/n_head.ftl">
	<!-- 引用顶部结束 -->
	<!-- 内容开始 -->
	<div class="pay-content-wrap">
	    <div class="static-tiltle static-tiltle03"><span></span></div>
         <div class="pay-success-wrap">
         	 <div class="pay-success-con">
         	 	<div class="pay-success-icon"><img src="/static/images/cf-pay/success.png" alt=""></div>
         	 	<div class="pay-success-icon">支付成功</div>
	 	     <div class="pay-success-find">您已经成功支持该项目，请您及时关注<a href="/orders/">个人中心</a><a href="/orders/">－我的支持</a></div>

         	 	<div class="pay-back"><a href="/">返回首页</a></div>
         	 </div>
         </div>
	 
	</div>

	<!-- 引用底部开始 -->
	<#include "/page/common/foot.ftl">
	<!-- 引用底部结束 -->
    <script type="text/javascript" src="static/js/jquery.js"></script> 
    <script type="text/javascript">
    $(function() {
     $(".pay-success-wrap").height($(document).height()-610);
    	
    });
    </script>  
</body>
</html>