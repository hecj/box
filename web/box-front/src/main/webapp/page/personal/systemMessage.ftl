<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<title>消息中心</title>
	<#include "/page/common/base/include.ftl">
</head>
<body>
   <!-- 头部引用 -->
   <#include "/page/common/n_head.ftl" />
   <!-- 内容start！ -->
   <div class="content bg_wrap content-col-f">
   		<#include "/page/common/personal_head.ftl" />
		<div class="personal-con  personal-con02">
			<div class="personal-item-menu">
				<ul>
					<li class="current">
						<#if (systemMessageCount>0)><span class="border-radiusBtn" id="news-noone">${systemMessageCount!0}</span></#if>
						<a href="/personal/mySystemMessage">系统消息</a>
					</li>
					<li>
						<#if (privateMessageCount>0)><span class="border-radiusBtn" id="news-notwo">${privateMessageCount!0}</span></#if>
						<a href="/personal/myPrivateMessage">我的私信</a>
					</li>
			   </ul>
			</div>
			<div class="newCenter-btn">
				<ul>
					<li><span class="green" onclick="getSystemMessage(0,1)">全部消息</span></li>
					<li><span onclick="getSystemMessage(1,1)">未读消息</span></li>
				</ul>
			</div>
			<div id="system-message" class="newCenter-con"></div>
		</div>
	</div>
	<div class="page-wrap-sub">
    	<div id="system-message-page" class="page-div clearfix"></div>
    </div>
    <div class="pay-way-wrap"></div>
   <!-- 内容end！ -->
   <!-- 页尾引用 -->
   <#include "/page/common/foot.ftl" />
</body> 
	
	<script type="text/javascript" src="/static/js/page/personal/systemMessage.js"></script>
	<script type="text/javascript" src="/static/js/page/full_paging.js"></script>
	
	<script type="text/javascript" src="/static/js/DD_roundies_min.js"></script> 
	
</html>



