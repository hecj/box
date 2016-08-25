<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
  	<#include "/page/common/base/include.ftl">
</head>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="javascript:top.location='/';">首页</a></li>
        <li><a href="/message/taskMessageQueue/toAddTask">发送通知</a></li>
    </ul>
</div>
    
	<div class="formbody">
	    <div class="formtitle"><span>发送通知</span></div>
	    <ul class="forminfo">
		    <li><label>手机号：</label>
		    	<cite><input name="phones" type="text" class="dfinput" /><i>多个手机号用英文半角逗号“,”隔开</i>
		    	</cite></li>
		    <li><label>用户UID：</label><cite><input name="userIds" type="text" class="dfinput" /><i>多个用户UID用英文半角逗号“,”隔开</i></cite></li>
		    <li><label>用户分组：</label>
		    	<cite>
		    		<select class="dfinput" name="user_type">
						<option value="-1">--请选择--</option>
						<option value="1">全部用户</option>		    		
						<option value="2">注册用户</option>		    		
						<option value="3">认证用户</option>		    		
						<option value="4">第三方游客</option>		    		
		    		</select>	
			    </cite>
			</li>
		    <li><label>通知标题：</label>
		    	<cite><input name="title" type="text" class="dfinput" value="" />
		    	</cite>	
		    </li>
		    <li><label>通知内容：</label>
		    	<cite><textarea name="content" cols="" rows="" class="textinput"></textarea>
		    	</cite>
		    </li>
		    <li><label>通知方式：</label>
		    	<cite>
				<input type="checkbox" value="system" name="message_type" style="width:50px"/>系统消息&nbsp;&nbsp;&nbsp;	
				<!-- 	    
				<input type="checkbox" value="email" name="message_type"  style="width:50px"/>Email&nbsp;&nbsp;&nbsp;		    
				-->
				<input type="checkbox" value="phone" name="message_type"  style="width:50px"/>手机短信	
				</cite>	    
		    </li>
		    <li><label>&nbsp;</label><input type="button" class="btn" value="发送" id="sendBtn"/></li>
	    </ul>
    </div>

<script type="text/javascript" src="/static/js/page/message/taskMessageQueue/addTask.js?v=20151214"></script>
</body>

</html>
