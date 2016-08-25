<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="/static/uimaker/css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/static/js/common/jquery/jquery.js"></script>

<script type="text/javascript">
$(function(){	
	//导航切换
	$(".menuson li").click(function(){
		$(".menuson li.active").removeClass("active")
		$(this).addClass("active");
	});
	
	$('.title').click(function(){
		var $ul = $(this).next('ul');
		$('dd').find('ul').slideUp();
		if($ul.is(':visible')){
			$(this).next('ul').slideUp();
		}else{
			$(this).next('ul').slideDown();
		}
	});
})	
</script>

</head>

<body style="background:#f0f9fd;">
	<div class="lefttop"><span></span>菜单面板</div>
    <dl class="leftmenu">
    	<!--
    	<dd>
    		<div class="title">
    			<span><img src="/static/uimaker/images/leftico01.png" /></span>基础管理
    		</div>
    		<#include "/page/common/menu.ftl"/> 
    	</dd>
    	-->
    	<!--
    	<dd>
    		<div class="title">
    			<span><img src="/static/uimaker/images/leftico02.png" /></span>信息查询
   			</div>
		    <ul class="menuson">
		         <li><cite></cite><a href="/query" target="rightFrame">通用查询</a><i></i></li>
		         <li><cite></cite><a href="/statics" target="rightFrame">统计查询</a><i></i></li>
		    </ul>     
		</dd>
		-->
    	<dd>
        	<div class="title">
            	<span><img src="/static/uimaker/images/leftico02.png" /></span>运营
        	</div>
        	<ul class="menuson">
            	<li><cite></cite><a href="/project" target="rightFrame">项目管理</a><i></i></li>
            	<li><cite></cite><a href="/project/toProjectRecommList" target="rightFrame">推荐管理</a><i></i></li>
            	<li><cite></cite><a href="/user" target="rightFrame">用户管理</a><i></i></li>
            	<li><cite></cite><a href="/usercertify/" target="rightFrame">实名审核</a><i></i></li>
            	<li class="ordersList"><cite></cite><a href="/orders/" target="rightFrame">支持列表</a><i></i></li>
            	<li class="dShowList"><cite></cite><a href="/orders/deliveryList/" target="rightFrame">发货单列表</a><i></i></li>
            	<li><cite></cite><a href="/userFeedBack/" target="rightFrame">反馈信息列表</a><i></i></li>
        	</ul>
    	</dd>
    	
    	<dd>
        	<div class="title">
            	<span><img src="/static/uimaker/images/leftico02.png" /></span>资金管理
        	</div>
        	<ul class="menuson">
            	<li><cite></cite><a href="/alipayRecord/query" target="rightFrame">支付宝交易记录</a><i></i></li>
            	<li><cite></cite><a target="rightFrame" href="/pay/ordersRefund/query?status=1">订单退款管理</a><i></i></li>
            	<li><cite></cite><a target="rightFrame" href="/balanceUser/">用户余额管理</a><i></i></li>
            	<li><cite></cite><a target="rightFrame" href="/pay/withdrawals/manager?status=1">提现管理</a><i></i></li>
        	</ul>
    	</dd>
    	
    	<dd>
        	<div class="title">
            	<span><img src="/static/uimaker/images/leftico02.png" /></span>系统管理
        	</div>
        	<ul class="menuson">
            	<li><cite></cite><a href="/u" target="rightFrame">后台用户管理</a><i></i></li>
        	</ul>
    	</dd>
    	<dd>
        	<div class="title">
            	<span><img src="/static/uimaker/images/leftico02.png" /></span>消息管理
        	</div>
        	<ul class="menuson">
            	<li><cite></cite><a href="/message/sendSmsRecord" target="rightFrame">短信发送记录</a><i></i></li>
            	<li><cite></cite><a href="/message/sendEmailRecord" target="rightFrame">邮件发送记录</a><i></i></li>
            	<li><cite></cite><a href="/message/taskMessageQueue/toAddTask" target="rightFrame">发送通知</a><i></i></li>
            	<li><cite></cite><a href="/message/taskMessageQueue/taskList/" target="rightFrame">发送通知记录</a><i></i></li>
        	</ul>
    	</dd>
    	<dd>
        	<div class="title">
            	<span><img src="/static/uimaker/images/leftico02.png" /></span>评论管理
        	</div>
        	<ul class="menuson">
            	<li><cite></cite><a href="/message/comment/" target="rightFrame">用户评论</a><i></i></li>
        	</ul>
    	</dd>
    
    </dl>
</body>
</html>
