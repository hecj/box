<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>欢迎登录红包管理系统</title>
<link href="/static/uimaker/css/style.css" rel="stylesheet" type="text/css" />
<script language="JavaScript" src="/static/uimaker/js/jquery.js"></script>
<script src="/static/uimaker/js/cloud.js" type="text/javascript"></script>

<script language="javascript">
	$(function(){
    $('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
	$(window).resize(function(){  
    $('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
    })  
    
});  
	
	var msg="${msg!}";
	if(msg!==""&&msg){
		alert(msg);
	}
</script> 

</head>

<body style="background-color:#1c77ac; background-image:url(/static/uimaker/images/light.png); background-repeat:no-repeat; background-position:center top; overflow:hidden;">



    <div id="mainBody">
      <div id="cloud1" class="cloud"></div>
      <div id="cloud2" class="cloud"></div>
    </div>  


<div class="logintop">    
    <span>欢迎登录后台管理界面平台</span>    
    <ul>
    </ul>    
    </div>
    
    <div class="loginbody">
    
    <span class="systemlogo"></span> 
       
    <div class="loginbox">
    
    
    	
  	<form action="/dologin" method="post">
  	
    <ul>
      <li><input name="u" type="text" class="loginuser"></li>
      <li><input name="p" type="password" class="loginpwd"><br></li>
  	   <li><input type="submit" value="登录"  type="button" class="loginbtn" ></li>
    </ul>
  	</form>
    
    
    </div>
    
    </div>
    
    
    
</body>
