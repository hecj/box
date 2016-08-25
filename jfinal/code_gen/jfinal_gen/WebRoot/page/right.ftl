<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>无标题文档</title>
	<link href="/static/uimaker/css/style.css" rel="stylesheet" type="text/css" />
	<link href="/static/css/common.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="/static/uimaker/js/jquery.js"></script>
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
    <b>Admin 
    <#if ((.now?substring(5,7)?number) <= 12) >
    	上午好
    <#else>
    	下午好
    </#if>
      ，欢迎使用自动代码生成系统</b>(peoncn@163.com)
    <a href="/static/help/quick.html" target="blank">快速文档</a>
    </div>
    
    <div class="welinfo">
    <span><img src="/static/uimaker/images/time.png" alt="时间" /></span>
    <i>${.now}</i>
    </div>
    
    
    <div class="xline"></div>
    
    <div class="welinfo">
	    <span><img src="/static/uimaker/images/dp.png" alt="示例" /></span>
	    <b>常用功能示例：</b>
    </div>
    
    
    <ul class="iconlist">
    	<!--
	    <li><img class="tip_icon_smile" src="/static/uimaker/images/ico01.png" /><p><a href="#">管理设置</a></p></li>
	    <li><img class="tip_icon_smile" src="/static/uimaker/images/ico02.png" /><p><a href="#">发布文章</a></p></li>
	    <li><img class="tip_icon_smile" src="/static/uimaker/images/ico03.png" /><p><a href="#">数据统计</a></p></li>
	    <li><img class="tip_icon_smile" src="/static/uimaker/images/ico05.png" /><p><a href="#">目录管理</a></p></li>
	    -->
	    <li><img class="tip_icon_smile" src="/static/image/login1.png" /><p><a href="#">登录</a></p></li>
	    <li><img class="tip_icon_smile" src="/static/image/down1.png" /><p><a href="/downFile">文件下载</a></p></li>
	    <li><img class="tip_icon_smile" src="/static/uimaker/images/ico04.png" /><p><a href="/static/help/fileup.html" target="blank">文件上传</a></p></li>
	    <li><img class="tip_icon_smile" src="/static/uimaker/images/ico06.png" /><p><a href="/static/help/index.html" target="blank">帮助文档</a></p></li> 
            
    </ul>
    
    <div class="ibox">
    	<a class="ibtn marginR10" onclick='peon.popx.warn({msg:"提示内容！"});'><img  class="tip_icon_tiny" src="/static/uimaker/images/iadd.png"  />普通提示框</a> 
    	<a class="ibtn marginR10" onclick='peon.popx.error({msg:"提示内容！"});'><img  class="tip_icon_tiny" src="/static/uimaker/images/close1.png" />错误提示框</a> &nbsp; &nbsp;
    	<a class="ibtn marginR10" onclick='peon.popx.tip({msg:"提示内容！"});'><img  class="tip_icon_tiny" src="/static/image/ok2.png" />成功提示框</a> &nbsp; &nbsp;
    </div>
    
    <div class="xline"></div>
    <div class="box"></div>
    
     
    
    
    <div class="uimakerinfo"><b>more...</b>(<a href="http://www.peon.cn" target="_blank">www.peon.cn</a>)</div>
    <!--
	    <ul class="umlist">
		    <li><a href="#">如何发布文章</a></li>
		    <li><a href="#">如何访问网站</a></li>
		    <li><a href="#">如何管理广告</a></li>
		    <li><a href="#">后台用户设置(权限)</a></li>
		    <li><a href="#">系统设置</a></li>
	    </ul>
	-->   
    
    </div>
    
    
    
    
    
    
</body>

</html>
