<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>right</title>
	<link href="/static/uimaker/css/style.css" rel="stylesheet" type="text/css" />
	<link href="/static/css/common.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="/static/js/common/jquery/jquery.js"></script>
	<script type="text/javascript" src="/static/js/common.js"></script>
	
	<script type="text/javascript">
	
	</script>
</head>
<body>
	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="#">首页</a></li>
    </ul>
    </div>
    <div class="mainindex">
    
    <div class="welinfo">
    <span><img src="/static/uimaker/images/sun.png" alt="天气" /></span>
    <b>${__u.username}(${__r.n})
    <#if ((.now?substring(5,7)?number) <= 12) >
    	上午好
    <#else>
    	下午好
    </#if>
        ，欢迎登录盒子众筹管理系统</b>
  	  技术支持
    </div>
    
    <div class="welinfo">
    <span><img src="/static/uimaker/images/time.png" alt="时间" /></span>
    <i>${.now}</i>
    </div>

    <div class="xline"></div>
  
    </div>
</body>
</html>
