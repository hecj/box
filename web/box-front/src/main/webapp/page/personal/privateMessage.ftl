<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<title>消息中心--我的私信</title>
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
			<a href="/personal/letterWrite"><p class="send-personal-letter-btn"><span>发送私信</span></p></a>
			
			<!-- 会话内容 -->
			<div class="personal-letter-wrap">
				<#list dialogs as dialog>
					<div class="personal-letter-one">
			     		<div class="personal-letter-img">
			     			<img src="${dialog.picture!}" alt="">
			     		</div>
			     		<div class="personal-letter-one-con">
			     			<div class="personal-letter-one-con-top">
			     				<b>${dialog.nickname!}</b>
			     				<span>${dialog.send_at!}</span>
			     			</div>
			     			<p class="word_limit">${dialog.message!}</p>
			     		</div>
			     		<div class="personal-letter-operation">
		     				<a href="javascript:;" class="return-letterBtn">回复</a>	
		     				<a href="/personal/letterChatRecord/${dialog.from_usesr_id!-1}-${dialog.to_user_id!-1}-${dialog.id!-1}">查看更多<i></i></a>
			     		</div>
			     		<div class="del-personal-letter"></div>
			     		<div class="personal-letter-user-id" style="display:none;">${dialog.to_user_id!}</div>
			     		<div class="personal-letter-dialog-id" style="display:none;">${dialog.id!}</div>
			     	</div>
				</#list>
				<#if (dialogs.size() < 1)>
					<p class='private-no-massages'>暂无消息</p>
				</#if>
			</div>
 				
 			<!-- 回报弹框 -->
			<div class="personalsetting-send pub-popup-in sc-set-paypassword">
				<div class="pub-popup-top">
					<h6>回复</h6>
					<a href="javascript:;" class="popup-close"></a>
				</div>
				<div class="pub-popup-cont position_rel">
					<textarea class="letter-send" maxlength="300"></textarea>
				    <span class="letter-placeholder">请输入私信内容，最多300个字</span>
					<dl class="personal-add-submit personal-add-submit02 position_rel">	
						<p class="null-tip">私信内容不能为空</p>
						<input type="button"  value="发送" class="submit-green no-green-one">
					<!-- 	<input type="button"  value="取消" class="close-letter"> -->
						<input type="hidden" name="receive-id"  value="">
					</dl>
				</div>
	   	    </div>
 			<!-- 回报弹框 -->
		</div>
	</div>
	<div class="pay-way-wrap"></div>	
	<!-- 内容end！ -->
	<!-- 页尾引用 -->
	<#include "/page/common/foot.ftl" />
	<script type="text/javascript" src="/static/js/page/personal/privateMessage.js"></script>
	<script type="text/javascript" src="/static/js/DD_roundies_min.js"></script> 

</body>
</html>