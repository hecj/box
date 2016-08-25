<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" >
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>复审提交</title>
	<#include "/page/common/base/include.ftl">
</head>
<body>
	<!-- 头部引用 -->
	<#include "/page/common/n_head.ftl">
	<!-- 引用顶部结束 -->
	<!-- 内容开始 -->
	<div class="project-trial-submit-content bg_wrap">
		<div class="flow-chart prsc-more"></div>	
		<div class="ptsc-in">
			<img class="banner" src="/static/images/cf-project-trial-submit/img.png" alt="">
			<p class="first">您的项目信息已经提交成功 !</p>
			<p class="second">您可以在项目管理-我的发起的项目中查看审核结果</p>
			<p class="third"><a class="left" href="/">返回首页</a><a class="right" href="/projectapply/myapply">个人中心</a></p>
			<span class="jump b-radius-4"><strong id="jump1">5</strong>秒自动跳转至首页</span>
		</div>
	</div>

	<!-- 引用底部开始 -->
	<#include "/page/common/foot.ftl">
	<!-- 引用底部结束 -->
    <script type="text/javascript" src="/static/js/page/projectapply/cf-project-trial-submit.js"></script>
</body>
</html>