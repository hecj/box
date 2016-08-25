<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta http-equiv="Content-Type" content="textml;charset=UTF-8">
	<meta name="renderer" content="webkit">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>盒子众筹-${project.name!}</title>
	<#include "/page/common/base/include.ftl">
</head>
<body>
	 <div style='margin:0 auto;display:none;'>
	    <img src="${STATIC_URL!}${project.cover_image!}"/>
	 </div>
	<!-- 引用顶部开始 -->
	<#include "/page/common/n_head.ftl">
	<!-- 引用顶部结束 -->
	<!-- 内容开始 -->
	<div class="bg_wrap">
	<div class="project-detail-top">
		<div class="project-detail-top-in">
			<div class="pd-tittle clearfix">
				<div class="pd-tittle-top">
					<h3>${project.name!}</h3>
					<#if project.status == 7>
						<span class="pd-tittle-state pd-tittle-state1">预热中</span>
					<#elseif project.status == 8>
						<span class="pd-tittle-state pd-tittle-state2">众筹中</span>
					<#elseif project.status == 9>
						<span class="pd-tittle-state pd-tittle-state4">失败</span>
					<#elseif project.status == 10>
						<span class="pd-tittle-state pd-tittle-state3">成功</span>
					</#if>
				</div>
				<p>
					<#if project.status==7>
						<span>此项目正在预热中，敬请期待！</span>
					<#else>
						<span>发布日期：${project.startTimeFormat!}</span>
						<span>截止日期：${project.expiresTimeFormat!}</span>
					</#if>
					<span>${project.provincesName!}  ${project.cityNames!}</span>
				</p>
			</div>
			<div class="pd-img clearfix">
				<div class="pd-img-l" id="slider">
					<ul>
						<#list project.turnImages as image>
							<li><a href="javascript:;"><img src="${STATIC_URL}${image!}" alt=""></a></li>	
						</#list>
					</ul>
					<ol id="slider-circle">
						<#list project.turnImages as image>
							<li></li>
						</#list>
					</ol>
				</div>
				<div class="pd-img-r b-radius-8">
					<div class="pd-img-r-in b-radius-8">
						<div class="money">
							<p>￥${project.fundnow!0}<i>RMB</i></p>
							<span>已有${project.fundpcount!0}人进行了众筹</span>
						</div>
						<div class="pro">
							<p>目标${project.fundgoal!0}</p>
							<div class="pro-252">
								<div class="pro-reach-252" id="progress-bar">
									<div class="circle-8" id="progress-circle"></div>
								</div>
							</div>
							<p class="clearfix"><span class="floatL" id="progress-num">${project.progress!0}%</span><span class="floatR">${project.beLeftDays!0}天</span></p>
		    				<p class="clearfix"><span class="floatL">完成</span><span class="floatR">剩余(天数)</span></p>
						</div>
						<div class="attention">
							<#if true == issub >
								<button class="ever-attention">已关注</button>
								<i>${subscribeNum!0}</i>
							<#else>
								<button id="subButton" class="no-attention">关注</button>
								<i id="subNumber">${subscribeNum!0}</i>
							</#if>
						</div>
						<div class="share">
							<ul>
								<li class="one"><a class="QRcodeout" href="javascript:;"></a></li>
								<li class="two"><a target="_blank" href="http://service.weibo.com/share/share.php?url=${snsUrl!}&title=${snsTitle!}&source=${snsDesc!}&sourceUrl=${snsUrl!}&pic=${snsPic!}"></a></li>
								<li class="three"><a target="_blank" href="http://share.v.t.qq.com/index.php?c=share&a=index&title=${snsTitle!}&site=${snsUrl!}&pic=${snsPic!}&url=${snsUrl!}&title=${snsTitle!}&pics=${snsPic!}"></a></li>
								<li class="four"><a target="_blank" href="http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url=${snsUrl!}&title=${snsTitle!}&pics=${snsPic!}&summary=${snsTitle!}"></a></li>
								<li class="five"><a target="_blank" href="http://www.douban.com/share/service?image=${snsPic!}&href=${snsUrl!}&name=${snsTitle!}"></a></li>
							</ul>
							<img class="QRcode" src="http://s.jiathis.com/qrcode.php?url=${snsUrl!}" alt="二维码没有加载出来...">
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="project-detail-menu">
		<div class="project-detail-menu-in clearfix" id="project-detail-menu-in">
			<ul>
				<li>
					<a class="current" href="javascript:;">项目介绍</a>
				</li>
				<li>
					<a href="javascript:;">项目进展</a>
					<i class="pdm-num"><strong>${projectProgressDynamic}</strong><em></em></i>
				</li>
				<li>
					<a href="javascript:;">项目评论</a>
					<i class="pdm-num"><strong>${commentDynamic}</strong><em></em></i>
				</li>
				<li class="pdm-li-last">
					<a href="javascript:;">项目支持</a>
					<i class="pdm-num"><strong>${ordersDynamic}</strong><em></em></i>
				</li>	
			</ul>
		</div>
	</div>
	<div class="project-detail-content">
		<div class="project-detail-content-in clearfix">
			<div class="pd-subfield">
				<!-- 项目介绍 -->
				<div id="introduceDiv" class="pd-subfield-0" style="display:block;">
					<!-- 项目介绍视频 -->
					<div id="introduce_video" class="video">
					</div>
					<!-- 项目介绍内容 -->
					<div id="introduce_word" class="word">
					</div>
					<!-- 项目介绍最新动态 -->
					<div id="introduce_dynamic" class="dynamic">
					</div>
					<!-- 项目介绍快速评论 -->
					<div class="opinion">
						<div class="opinion-in b-radius-8">
							<textarea id="introduce_textarea" class="opinion-textarea" maxlength="300"></textarea>
							<span class="pb-ph unique2">发表评论</span>
							<p>只能输入300个字符</p>
						</div>
						<button id="introduce_button">发布</button>
					</div>
				</div>
				<!-- 项目进展 -->
				<div id="scheduleDiv" class="pd-subfield-1" style="display:none;">
					<!-- 项目进展 -->
					<div id="schedule_progress" class="pds1-progress">
					</div>
				</div>
				<!-- 项目评论 -->
				<div id="commentDiv" class="pd-subfield-2" style="display:none;">
					<!-- 发表评论 -->
					<div class="opinion pds2-opinion">
						<div class="opinion-in">
							<textarea id="comment_textarea" class="one-level opinion-textarea" maxlength="300"></textarea>
							<span class="pb-ph unique2">发表评论</span>
							<p>只能输入300个字符</p>
			 			</div>
						<button class="noe-level-btn" onclick="post()">发布</button>
					</div>
					<!-- 查看评论 -->
					<div id="comment_top" class="comment">
						<!-- 选择显示 -->
						<p id="comment_title" class="comment-title">
							<a class="current all" href="javascript:;" onclick="loadComment(1, '')">查看全部 </a>
							<a class="promoter" href="javascript:;" onclick="loadComment(1, 'only')">只看发起人 </a>
						</p>
						<!-- 评论内容 -->
						<div id="comment_area" class="comment-in">
						</div>
						<!-- 评论分页 -->
						<div id="comment_page" class="page-div clearfix">
						</div>
					</div>
				</div>
				<!-- 项目支持 -->
				<div id="supportDiv" class="pd-subfield-3" style="display:none;">
					<!-- 项目支持名单 -->
					<div id="support_area" class="support-area">
					</div>
					<!-- 项目支持名单分页 -->
					<div id="support_page" class="page-div clearfix">
					</div>
				</div>
			</div>
			<div class="pd-public">
				<div class="projectowner b-radius-8">
					<div class="projectowner-border b-radius-8">
						<div class="projectowner-in">
							<p class="clearfix"><span class="floatL">私信</span><span class="floatR fans-attention">${(fans_isSub)?string('已关注','关注')}</span></p>
		    				<p class="icon clearfix">
		    					<a class="floatL privateletter" href="javascript:;"></a>
		    					<a class="floatR" href="javascript:void(0);" onclick="addFans(${project.user_id}, ${(fans_isSub)?string('2', '1')});"></a>
		    				</p>
		    				<p class="p-name">${projectUser.nickname!}</p>
		    				<p class="c-a8 p-signature" id="p-signature">${projectUser.note!}</p>
		    				<p class="last clearfix"><span class="c-a8 floatL fans"><i>${projectUserFans!0}</i>粉丝</span><span class="floatR">北京</span></p>
		    				<img src="${projectUser.picture!}" alt="">
		    				<a class="p-img" href="javascript:;"></a>
						</div>
					</div>
				</div>
				<div class="private-letter" >
					<div class="private-letter-in">
						<div class="private-letter-top">
							<h6>发私信</h6>
							<a class="close" href="javascript:;" id="close"></a>
						</div>
						<div class="private-letter-cont">
							<h6>收件人：${projectUser.nickname!}</h6>
							<textarea id="message" class="private-letter-cont-textarea" maxlength="300"></textarea>
							<span class="pb-ph unique4">请输入私信内容，最多300个字</span>
							<span class="pb-empty">请输入内容</span>
							<button id="message_button">发布</button>
						</div>
					</div>
				</div>
				<div class="repay">
					<#list wayList as way>
						<div class="repay-pub b-radius-8">
							<div class="repay-pub-in b-radius-8">
								<img class="repay-img" src="${STATIC_URL}${way.picture!}" alt="">
								<div class="repay-word">
									<p>${way.desc!}</p>
									<b class="price">￥${way.fund!0}</b>
									<#if way.totalnum == 0>
										<p>不限名额</p>
									<#else>
										<p>(${way.totalnum!0}位参与，剩余${way.remainderNum!0}位)</p>
									</#if>
									<b class="time">回报时间：预计筹资结束后${way.send_days!0}天内</b>
									<#if way.postage == 0>
										<b class="way">邮寄方式：包邮(大陆地区)</b>
									<#else>
										<b class="way">邮寄方式：${way.postage!}元(大陆地区)</b>
									</#if>
								</div>
							</div>
						</div>
						<#if project.status==8 >
							<#if (way.totalnum!=0 && way.remainderNum>0) || (way.totalnum ==0) >
								<#if user?? && projectUser?? && user.id==projectUser.id>
								    <a href="javascript:;" name="no-support">立即支付</a>
								 <#else>
								 	<a href="/orders/generate/${way.id!}">立即支付</a>
							     </#if>
							<#else>
								<a class="repay-no" href="javascript:;">立即支付</a>
							</#if>
						<#else>
							<a class="repay-no" href="javascript:;">立即支付</a>
						</#if>
					</#list>
				</div>
			</div>
			<!-- 社区入口 -->
			<a class="entrance" href="#"></a>
		</div>
	</div>
	</div>
	<!-- <div class="pay-way-wrap"></div> -->
	<div id="projectId" style="display:none;">${project.id!-1}</div>
	<div id="projectUserId" style="display:none;">${projectUser.id!-1}</div>
	<div id="table" style="display:none;">${table!1}</div>
	
	<!-- 引用底部开始 -->
	<#include "/page/common/foot.ftl" />
	<!-- 引用底部结束 -->
	<script type="text/javascript" src="/static/js/common/jquery.cookie.js"></script>
	<script type="text/javascript" src="/static/js/page/projectDetail/slider.js"></script>
    <script type="text/javascript" src="/static/js/page/projectDetail/iContext.js"></script>
    <script type="text/javascript" src="/static/js/page/projectDetail/iSchedule.js"></script>
    <script type="text/javascript" src="/static/js/page/projectDetail/iComment.js?v=20151216"></script>
    <script type="text/javascript" src="/static/js/page/projectDetail/iSupport.js"></script>
    <script type="text/javascript" src="/static/js/page/sub/mysub.js?v=20151127"></script>
    <script type="text/javascript" src="/static/js/page/projectDetail/cf-project-detail.js?v=20151216"></script>
    <script type="text/javascript" src="/static/js/page/projectDetail/cf-project-detail-respond.js"></script>
    <script type="text/javascript" src="/static/js/page/full_paging.js"></script>
    <script type="text/javascript" src="/static/js/page/projectDetail/replay_paging.js"></script>

</body>
</html>