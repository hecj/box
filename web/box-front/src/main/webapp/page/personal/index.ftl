<html>
	<head>
		<#--<#include "/page/common/base/include.ftl">-->
    	<script type="text/javascript" src="/static/js/common/jquery/jquery.js"></script>
    	
    	<style>
			li {list-style-type:none;}	
		</style>
	</head>

	<body>
	
		<#include "/page/common/head.ftl" />
		
		<#if user??>
			<div>
				用户名：${user.username!}<br>
				用户昵称：${user.nickname!}<br>
				用户头像：<img src="${user.picture!}" width="50px" height="50px" /><br>
			</div>
		</#if>
		
		<br><br>

		项目管理
		<ul>
			<li><a href="/sub/mysub">我的关注</a></li><br>
			<li><a href="/orders/">我的支持</a></li><br>
			<li><a href="/projectapply/myapply">我的发起</a></li><br>
		</ul>
		账户管理
		<ul>
			<li><a href="/balance_reg">账户余额</a></li><br>
			<li><a href="javascript:;">优惠券</a></li><br>
		</ul>
		个人设置
		<ul>
			<li><a href="/personal/getPersonalDetail">个人资料</a></li><br>
			<li><a href="/personal/security">安全中心</a></li><br>
			<li><a href="/usercertify/certify">认证</a></li><br>
			<li><a href="/personal/receiveAddress">收货地址</a></li><br>	
		</ul>
		消息中心
		<ul>
			<li><a href="/personal/myPrivateMessage">我的私信</a></li><br>
			<li><a href="/personal/mySystemMessage">系统消息</a></li><br>
		</ul>

		<br><hr><br>
		
		<a href="/personal/getPersonalDetail">个人资料</a><br><br>
		<a href="/user/bind">基本信息</a><br><br>
		<a href="/user/security">安全中心</a><br><br>
		<a href="">身份认证</a><br><br>
		
		<a href="">个人主页</a>
		<a href="/orders/">我的订单</a>
		<a href="/balance_reg">余额</a>

        <h4><a href="/projectapply/agreement">发起一个新项目</a></h4>
        <h4><a href="/sub/mysub">个人中心-关注项目</a></h4>
        <h4><a href="/projectapply/myapply">个人中心-我的发起</a></h4>
        <h4><a href="/usercertify/certify">我的实名认证</a></h4>
        
        <h4><a href="/personal/myPrivateMessage">我的私信</a></h4>
        <#include "/page/common/foot.ftl" />
	</body>
</html>