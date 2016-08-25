<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<link href="/static/uimaker/css/style.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="/static/uimaker/js/jquery.js"></script>
<script type="text/javascript">
$(function(){	
	//顶部导航切换
	$(".nav li a").click(function(){
		$(".nav li a.selected").removeClass("selected")
		$(this).addClass("selected");
	})	
})	
</script>


</head>

<body style="background:url(/static/uimaker/images/topbg.gif) repeat-x;">

    <div class="topleft">
    <a href="main" target="_parent"><img src="/static/uimaker/images/logo.png" title="系统首页" /></a>
    </div>
        
    <ul class="nav">
    <li><a href="/main" target="top" class="selected"><img src="/static/uimaker/images/icon01.png" title="工作台" /><h2>工作台</h2></a></li>
    <li><a href="imgtable.html" target="rightFrame"><img src="/static/uimaker/images/icon02.png" title="模型管理" /><h2>模型管理</h2></a></li>
    <li><a href="imglist.html"  target="rightFrame"><img src="/static/uimaker/images/icon03.png" title="模块设计" /><h2>模块设计</h2></a></li>
    <li><a href="tools.html"  target="rightFrame"><img src="/static/uimaker/images/icon04.png" title="常用工具" /><h2>常用工具</h2></a></li>
    <li><a href="computer.html" target="rightFrame"><img src="/static/uimaker/images/icon05.png" title="文件管理" /><h2>文件管理</h2></a></li>
    <li><a href="tab.html"  target="rightFrame"><img src="/static/uimaker/images/icon06.png" title="系统设置" /><h2>系统设置</h2></a></li>
    </ul>
            
    <div class="topright">    
    <ul>
    <li><span><img src="/static/uimaker/images/help.png" title="动态"  class="helpimg"/></span><a href="/dongtai"  target="rightFrame">动态</a></li>
    <li><a href="/u/updatepwd" target="rightFrame">修改密码</a></li>
    <li><a href="/logout" target="_parent">退出</a></li>
    </ul>
     
    <div class="user">
    <span>${__u.u}</span>
    <a href="/msg"  target="rightFrame" onclick="setTimeout('refresh()',1000)">
    <i>消息</i>
    <b id="mccc">${msg_count.get("count(id)")}</b>
    </a>
    </div>    
    
    </div>
	<script>
		function refresh(){
			jQuery("#mccc").load("/getmsgcount",function(){
					
			});
		}
		var timer=setInterval("refresh()",10000);
		
		jQuery(function(){
		
			setInterval(function(){
				if(jQuery("#mccc").text()>0){
					$("#mccc").fadeOut(500).fadeIn(500);
				}
			},1000);
		});
	</script>
</body>
</html>
