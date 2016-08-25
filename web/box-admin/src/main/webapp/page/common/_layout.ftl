<#macro layout>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xml:lang="zh-CN" xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<link href="/static/css/layout.css" media="screen" rel="stylesheet" type="text/css" />
<script src="/static/js/jquery-1.7.1.js" type="text/javascript" ></script>
<#-- base href="${CPATH}" / -->
</head>
<body>
	<div class="manage_container">
		<div class="manage_head">
			<div id="nav">
				<ul>
					<li><a href="/"><b>目录</b></a></li>
				</ul>
			</div>
		</div>
		<div class="main">
			<#nested>
		</div>
	</div>
</body>
</html>
</#macro>