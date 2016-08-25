<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta name="renderer" content="webkit">
    <title>项目详情</title>
    <META http-equiv=Content-Type content="text/html; charset=UTF-8">
    <style>
		li {list-style-type:none;}
		tr { width:50%; }
		td { Vertical-align:Top;}
	</style>
    <#--<#include "/page/common/base/include.ftl">-->
    <script type="text/javascript" src="/static/js/common/jquery/jquery.js"></script>
    
</head>
<body>

	<#include "/page/common/head.ftl" />
	
	<table width="1400" cellpadding="10" style="table-layout:fixed;">
  		<tr>
  			<div>
  				<td colspan="2">
	  				项目标题:${project.name!} <br><br>
	  				发布时间:${project.startTimeFormat!} &nbsp;&nbsp;
	  				截至时间:${project.expiresTimeFormat!} &nbsp;&nbsp;
	  				地点:${project.provincesName!} &nbsp;&nbsp;<br><br>
	  			</td>
  			</div>
  		</tr>
  		<tr>
  			<td>
  				<div>
					轮播图:<br>
					<#list project.turnImages as image>
						<img src="${image!}"/><br>
					</#list>
				</div>
  			</td>
  			<td>
  				<div>
					<#if true == issub >
						已关注(${subscribeNum!0})
					<#else>
						<button id="subButton" onclick="subscribeFun(${project.id!-1},1)">关注(${subscribeNum!0})</button>
					</#if>
					<br>
			        <a target="_blank" href="http://service.weibo.com/share/share.php?url=${snsUrl!}&title=${snsDesc!}&source=京东金融&sourceUrl=${snsUrl!}&pic=${snsPic!}">分享到新浪微博</a>
			        <a target="_blank" href="http://share.v.t.qq.com/index.php?c=share&a=index&title=${snsDesc!}&site=http://www.duomeidai.com/&pic=${snsPic!}&url=${snsUrl!}&title=${snsTitle!}&pics=${snsPic!}&summary=${snsDesc!}">分享到腾讯微博</a>
			        <a target="_blank" href="http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url=${snsUrl!}&title=${snsTitle!}&pics=${snsPic!}&summary=${snsDesc!}">分享到QZONE</a>
			        <a target="_blank" href="http://www.douban.com/share/service?image=${snsPic!}&href=${snsUrl!}&name=${snsDesc!}">分享到豆瓣</a>
			        <a target="_blank" href="http://widget.renren.com/dialog/share?resourceUrl=${snsUrl!}&amp;title=${snsTitle!}&amp;images=${snsPic!}&amp;description=${snsDesc!}">分享到人人</a>
				</div>
		        <br><br>
		        <div>
	  				已筹到:￥${project.fundnow!0} RMB <br>
	  				已有 ${project.fundpcount!0} 人进行了众筹 <br>
	  				完成: ${project.progress!0} % <br>
	  				目标: ${project.fundgoal!0} <br>
	  				剩余天数: ${project.beLeftDays!0}天 <br><br>
  				</div>
  			</td>
  		</tr>
  		<tr>
  			<td>
  				<a href="javascript:;" onclick="switchDetailTab('contextDiv',${projectId!-1})">项目介绍</a> &nbsp;&nbsp;
				<a href="javascript:;" onclick="switchDetailTab('scheduleDiv',${projectId!-1})">项目进展</a> &nbsp;&nbsp;
				<a href="javascript:;" onclick="switchDetailTab('commentDiv',${projectId!-1})">评论</a> &nbsp;&nbsp;
				<a href="javascript:;" onclick="switchDetailTab('supportDiv',${projectId!-1})">支持</a><br><br>
				<div id="contextDiv">
					<#include "/page/projectDetail/iContext.ftl"/>
				</div>
				<div id="scheduleDiv" style="display:none" >
					<#include "/page/projectDetail/iSchedule.ftl"/>
				</div>
				<div id="commentDiv" style="display:none" >
					<#include "/page/projectDetail/iComment.ftl"/>
				</div>
				<div id="supportDiv" style="display:none" >
					<#include "/page/projectDetail/iSupport.ftl"/>
				</div>
  			</td>
  			<td>
  				<div>
					发布人:${projectUser.nickname!} <br/>
					发私信:<input type="button" value="发私信" onclick="showInput('privateMessageDiv')"/> <br/>
					加关注:
					<div id="privateMessageDiv" style="display:none">
						<br/>
						收件人:${projectUser.nickname!} 
						<br/><br/>
						<form id="messageForm" method="post">
						    <textarea id="message" name="content" rows="3" cols="20" placeholder="内容不超过300个字符"></textarea> <br/>
						    <input type="button" value="发送" onclick="sendPrivateMessage(${projectUser.id!-1},'messageForm','privateMessageDiv','message')"/>  
							<input type="button" value="取消" onclick="hideInput('privateMessageDiv', 'message')"/>
						</form>
					</div>
				</div>
				<br><br>
				<div>
				众筹方式:<br><br>
				<#list wayList as way>
			    	图片: <img src="${way.pic0!}"/> <br>
				    ¥ ${way.fund!0}元 (${way.totalnum!0}位参与, 剩余${way.remainderNum!0}位) <br>
			    	回报内容:${way.desc!} <br>
			    	邮寄方式:${way.postage!} <br>
			    	回报时间:项目成功结束后<b>${way.send_days!0}</b>天内 <br>
			    	<a href="/orders/generate/${way.id!}">立即支付</a> <br>
			    	<br>
				</#list>
			</div>
  			</td>
  		</tr>
  	</table>

	<br/><br/>
	
	<#include "/page/common/foot.ftl" />
	<script type="text/javascript" src="/static/js/page/project/detail.js"></script>
	<script type="text/javascript" src="/static/js/page/sub/mysub.js"></script>
    <script type="text/javascript" src="/static/js/page/projectDetail/iContext.js"></script>
    <script type="text/javascript" src="/static/js/page/projectDetail/iSchedule.js"></script>
    <script type="text/javascript" src="/static/js/page/projectDetail/iComment.js"></script>
    <script type="text/javascript" src="/static/js/page/projectDetail/iSupport.js"></script>
    <script type="text/javascript" src="/static/js/page/personal/privateMessage.js"></script>
    <script type="text/javascript" src="/static/js/common/paging/paging.js"></script>
	<script type="text/javascript">
    	$(document).ready(function(){
    		//加载数据-项目介绍
			loadContext(${projectId});
		});
	</script>
</html>