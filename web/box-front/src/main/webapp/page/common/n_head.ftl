<div class="top">
	<div class="top-in">
		<ul class="top-left">
			<li><a class="top-logo" href="/"><img src="/static/images/icon/logo.png" alt="" /></a></li>			
			
			<li><a class="logo-left" href="/project">众筹项目</a></li>
		</ul>
		<div class="top-search">
			<input type="text" placeholder="搜索..." id="searchText"/>
			<button id="btnSearch" onclick="funSelect()">Go</button>
			<i></i>
		</div>
		
		<#if user??>
			<div class="top-right">
				<a href="/sub/mysub" class="top-p">
					<img src="${user.picture!}" alt="" class="top-photo" />
					<span class="top-circle"></span>
					<#if (user.messageCount>0) >
						<a href="/personal/mySystemMessage">
							<i class="top-photo-num">${user.messageCount!0}</i>
						</a>
					</#if>
				</a>
				<span class="icon"></span>
				<ul class="top-more">
					<li class="nickname"><a href="/sub/mysub" id="nickname">${user.nickname!}</a></li>
					<li><a href="/orders/">我的支持</a></li>
					<li><a href="/projectapply/myapply">我的发起</a></li>
					<li><a href="/personal/getPersonalDetail">账号设置</a></li>
					<li><a href="javascript:;" class="feedback">意见反馈</a></li>
					<li class="exitsign"><a href="/logOut">退出登录</a></li>
				</ul>
			</div>
		<#else>
			<div class="cf-login">
				<i></i>
				<a href="javascript:;" onclick="loginApp.login()">登录</a>
			</div>
		</#if>
	</div>
</div>

<!--提示框-->
<div class="feedback-success" id="box-alert">
	<div class="feedback-head">
		<h6>提示</h6>
		<a href="javascript:;" class="feedback-close feedback-close-small">X</a>
	</div>
	<div class="feedback-success-con">
		<p>操作成功！</p>
	</div>
	<div class="feedback-submit-wrap">
		<input type="submit" value="确定" class="feedback-submit feedback-submit-small">
	</div>
</div>

<!--确认提示框-->
<div class="feedback-success" id="box-confirm">
	<div class="feedback-head">
		<h6>提示</h6>
		<a href="javascript:;" class="feedback-close feedback-close-small">X</a>
	</div>
	<div class="feedback-success-con">
		<p>请选择操作！</p>
	</div>
	<div class="feedback-submit-wrap">
		<input type="submit" value="确定" class="feedback-submit feedback-submit-small">
		<input type="submit" value="取消" class="feedback-cancel feedback-submit-small">  
	</div>
</div>

<#include "/page/userfeedback/index.ftl"> 