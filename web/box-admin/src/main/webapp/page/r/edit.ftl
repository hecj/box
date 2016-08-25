<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>修改r</title>
	<link href="/static/uimaker/css/style.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="/static/uimaker/js/jquery.js"></script>
	<script>
		jQuery(function(){
			jQuery(".tip_error").eq(0).parents("li").find(".dfinput").focus();
		});
	</script>
</head>

<body>

	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="#">首页</a></li>
    <li><a href="/r">r列表</a></li>
    <li><a href="#">修改r</a></li>
    </ul>
    </div>
    
    <div class="formbody">
    
    <div class="formtitle"><span>修改r</span></div>
    
    <form action="/r/update" method="post">
		<#include "_form.ftl" />
	</form>
    
    
    
    </div>


</body>

</html>
