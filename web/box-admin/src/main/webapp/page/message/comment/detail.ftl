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
        <li><a href="/message/comment/">用户评论</a></li>
        <li><a href="/message/comment/detail/${comment.id!}">查看详情</a></li>
    </ul>
</div>


<div class="formbody">
		<!--
	    <div class="formtitle"><span>查看详情</span></div>
	    --> 
	    <ul class="forminfo">
		    <li><label></label>
		    	<cite>
		    		${user.nickname!}于${comment.create_at?number_to_datetime}对&nbsp;${project.name!}&nbsp;发表评论
		    	</cite>
		    </li>
		    <li><label></label>
		    	<cite><textarea readonly name="content" cols="" rows="" class="textinput">${comment.content!}</textarea>
		    	</cite>
		    </li>
		    <li><label>IP地址：</label>
		    	<cite>
				${comment.ip!}
				</cite>	    
		    </li>
		    <li>
		    	<div class="xline"/>
		    </li>
		    <li>
		        <label>&nbsp;</label>
	   		 	<#if comment.is_delete = 0>
	       				<input name="" type="button" class="btn" id="submit_btn" value="禁止显示" onclick="hiddenCommnent(${comment.id!})"/>
	       		<#elseif comment.is_delete = 1>
						<input name="" type="button" class="btn" id="submit_btn" value="允许显示" onclick="showCommnent(${comment.id!})"/>
	       		</#if>
		    </li>
	    </ul>
    </div>
    <script type="text/javascript" src="/static/js/page/message/comment/index.js"></script>
</body>

</html>
