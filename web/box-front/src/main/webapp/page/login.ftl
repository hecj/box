<html>
	<head>
		<meta charset="utf-8"/>
		<title>盒子众筹-中国最具影响力的创业众筹融资平台</title>
		<meta name="keywords" content="众筹,融资,众筹平台,创业融资"/>
		<script src="http://res.wx.qq.com/connect/zh_CN/htmledition/js/wxLogin.js"></script>
		<meta name="description" content="盒子众筹是中国最具影响力的创业众筹融资平台，专业为有梦想、有创意、有项目的朋友提供募资、投资、孵化、运营一站式综合众筹服务，协助您实现自己的创业梦想。好平台，好起点，尽在盒子众筹！"/>
	</head>
	<body>
		<h1>普通登录</h1> <a href="/wechatLogin">微信登陆</a>
	    <form action="/doLogin?back_url=${back_url!}" method="post" onsubmit="return check();">
	    	<table cellpadding="5">
		   		<tr>
		   			<td></td>
		    		<td>
		    			<p>${errorMsg!}</p>
		    		</td>
		   		</tr>
		   		<tr>
		    		<td>
		    			用户名:
		    		</td>
		    		<td>
		    			<input type="text" name="username" id="username"/><span id="usernameMsg"></span><br />
		    		</td>
		   		</tr>
		   		<tr>
		    		<td>
		    			密码:
		    		</td>
		    		<td>
		    			<input type="password" name="password" id="password"/> <span id="passwordMsg"></span><br />
		    		</td>
		   		</tr>
		   		<tr>
		   			<td></td>
		    		<td>
		    			<input type="submit" value="登录" />
		    		</td>
		   		</tr>
		    </table>
	    </form>
		<a href="/findPassword" >找回密码</a>
		<a href="/register">注册</a>
	    <br><br>
	    <#-- 第三方登录入口 -->
	    <a href="/OpenUserForQQ/login"><img alt="QQ登录" src="/static/images/icon/qq_logo_1.png"></a>  
	    <a href="/OpenUserForWeiBo/login"><img alt="微博登录" src="/static/images/icon/weibo_LOGO_16x16.png"></a>  
	    <a href="/OpenUserForWeChat/login"><img alt="微信登录" src="/static/images/icon/icon16_wx_logo.png"></a>  
	</body>
	<script type="text/javascript" src="/static/js/common/jquery/jquery.js"></script>
	<script type="text/javascript" src="../static/js/page/login.js"></script>
</html>