<#macro layout>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xml:lang="zh-CN" xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link href="/static/css/demo.css" media="screen" rel="stylesheet" type="text/css" />
<script src="/static/js/jquery-1.8.2.js" type="text/javascript" ></script>
<script src="/static/js/common.js" type="text/javascript" ></script>
<#-- base href="${CPATH}" / -->
<title>PEON</title>
</head>
<body>
	<div class="top_">
		 <a href="/">首页</a>
		 <a href="/help">帮助</a>
		 
		<div class="main">
			<#nested>
		</div>
	</div>
</body>
</html>
</#macro>