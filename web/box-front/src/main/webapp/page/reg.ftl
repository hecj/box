<html>
	<head>
		<meta http-equiv="content-type" content="text/html;charset=utf-8">
		<title>盒子众筹-中国最具影响力的创业众筹融资平台</title>
		<meta name="keywords" content="众筹,融资,众筹平台,创业融资"/>
		
		<meta name="description" content="盒子众筹是中国最具影响力的创业众筹融资平台，专业为有梦想、有创意、有项目的朋友提供募资、投资、孵化、运营一站式综合众筹服务，协助您实现自己的创业梦想。好平台，好起点，尽在盒子众筹！"/>
		<#--<#include "/page/common/base/include.ftl">-->
	</head>
<body>
	<div class="upper">
		欢迎注册
	</div>
	<div class="left">
		无关紧要
	</div>
	<div class="main">
		<div class="main_left">
			<h1>注册用户</h1>
			<div class="main_left_div">
				<form class="frmRegister">
					<table class="tabRegister" border="1">
						<tr>
							<th>手机号</th>
							<td>
								<input type="text" id="phone" name="phone"/>
								<input type="button" id="sendCode" value="免费获取验证码" onclick="settime(this,60)"/>
							</td>
						</tr>
						<tr>
							<th>手机验证码</th>
							<td>
								<input type="text" id="code" name="code"/>
							</td>
						</tr>
						<tr>
							<th>密码</th>
							<td>
								<input type="password" id="password" name="password"/>
							</td>
						</tr>
						
						<tr>
							<th>确认密码</th>
							<td>
								<input type="password" id="password2" name="password2"/>
							</td>
						</tr>
					</table>
						
							<button type="button" id="ensureBtn">确认申请</button>
						
				</form>
			</div>
		</div>
		<div class="main_right">
			<div class="main_right_upper">				
					<h3>已有账号</h3>
					<a href="/page/login.ftl" value="登录"></a>			
			</div> 
			<div class="main_right_footer">
				<h3>合作伙伴账号登录</h3>
			</div>
		</div>
	</div>
   
</body>
	<script type="text/javascript" src="/static/js/common/jquery/jquery.js"></script>
	<script type="text/javascript" src="../static/js/page/reg.js"></script>
</html>