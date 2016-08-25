<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<title>消息中心--我的私信--写私信</title>
	<#include "/page/common/base/include.ftl">
</head>
<body>
	<!-- 头部引用 -->
	<#include "/page/common/n_head.ftl" />
	<!-- 内容start！ -->
	<div class="content bg_wrap content-col-f">
		<#include "/page/common/personal_head.ftl" />
		<div class="personal-con personal-con02">
			<div class="personal-item-menu">
				<ul>
					<li>
						<#if (systemMessageCount>0)><span class="border-radiusBtn">${systemMessageCount!0}</span></#if>
						<a href="/personal/mySystemMessage">系统消息</a>
					</li>
					<li class="current">
						<#if (privateMessageCount>0)><span class="border-radiusBtn">${privateMessageCount!0}</span></#if>
						<a href="/personal/myPrivateMessage">我的私信</a>
					</li>
			   </ul>
			</div>
		   <p class="send-personal-letter-btn">
		   	<i></i><b>写私信</b>
		   	<a href="/personal/myPrivateMessage"><span class="send-personal-letter-gray">返回我的私信</span></a>
		   </p>
		   <div class="write-personal-letter-btn"></div>
 
			<div class="personal-letter-write-wrap">
				 <div class="position_rel">
				 <input type="text" class="personal-letter-write-name" />
				 <span class="letter-placeholder-01">收件人用户名</span>
			</div>
			 <span class="write-error-msg">收件人用户名不能为空</span>
			 <div class="position_rel">
			 <textarea class="wantSpeak-word" ></textarea>
			 <span class="letter-placeholder-02">请输入私信内容，最多300个字 </span>
			</div>
			 <span class="write-error-msg02">私信内容不能为空</span>
			 <p class="send-personal-letter-btn">
		   		<span class="sendemailBtn">发送私信</span>
		  	 </p>
			</div>
 
		</div>
	</div>
	<div class="pay-way-wrap"></div>	
	<!-- 内容end！ -->
	<!-- 页尾引用 -->
	<#include "/page/common/foot.ftl" />
	<script type="text/javascript" src="/static/js/page/personal/letterWrite.js"></script>
	<script type="text/javascript" src="/static/js/DD_roundies_min.js"></script> 

</body>
</html>



