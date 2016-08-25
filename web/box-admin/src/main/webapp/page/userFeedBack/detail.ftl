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
		        <li><a href="/userFeedBack">用户反馈列表</a></li>
		        <li><a href="/userFeedBack/detail/${userFeedBack.id!}">反馈信息</a></li>
		    </ul>
		</div>
    
		<div class="formbody">
			<#setting datetime_format="yyyy-MM-dd  HH:mm:ss"/>
		    <ul class="forminfo">
		    	<li>
		    		<label>id：</label>
		    		<cite>
		    			${userFeedBack.id!}
		   	    	</cite>
		   	    </li>
		    	<li>
		    		<label>用户id：</label>
		    		<cite>
		    			${userFeedBack.user_id!}
		   	    	</cite>
		   	    </li>
		    	<li>
		    		<label>反馈内容：</label>
		    		<cite>
		    			${userFeedBack.content!}
		   	    	</cite>
		   	    </li>
		   	    <li>
		    		<label>联系方式：</label>
		    		<cite>
		    			${userFeedBack.remark!}
		   	    	</cite>
		   	    </li>
		   	    <li>
		    		<label>反馈时间：</label>
		    		<cite>
		    			${(userFeedBack.create_at?number_to_datetime)!}
		   	    	</cite>
		   	    </li>
		   	</ul>
	    </div>
	</body>
</html>
