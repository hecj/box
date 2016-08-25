<div class="personal-head">
	<div class="head-con">
		<div class="login-head">
			<#if user.picture?? && user.picture!="">
        		<img src="${user.picture!}" alt="" />
        	<#else>
        		<img src="${WEBROOT_URL}static/images/top-footer/userphoto.png" alt="" />
        	</#if>
        	<h3>${user.nickname!}</h3>
        	<p>${user.note!}</p>
        	<h3 class="fans"><b>${user.fansNum!0}</b>粉丝
        		<#if user.provincesName!="">
        			<i>${user.provincesName!}<#if user.cityNames!="">/${user.cityNames!}</#if></i>
        		</#if>
        	</h3>
        </div>
    </div>
</div>
<div class="personal-menu" id="personal-menu-here">
	<ul>
		<li class="current"><a href="/sub/mysub">项目管理</a></li>
		<li><a href="/payment/recharge">账户管理</a></li>
		<li><a href="/personal/getPersonalDetail">个人设置</a></li>
		<li>
			<#if (user.messageCount>0) >
				<span>${user.messageCount!0}</span>
			</#if>
			<a href="/personal/mySystemMessage">消息中心</a>
		</li>
	</ul>
</div>

<script type="text/javascript">
	$(function(){
		$(document).scrollTop($('#personal-menu-here').offset().top);
	});
</script>