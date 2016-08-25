<html>
	<head>
		<meta http-equiv="content-type" content="text/html;charset=utf-8">		
		<title>盒子众筹-中国最具影响力的创业众筹融资平台</title>
		<meta name="keywords" content="众筹,融资,众筹平台,创业融资"/>
		<meta name="description" content="盒子众筹是中国最具影响力的创业众筹融资平台，专业为有梦想、有创意、有项目的朋友提供募资、投资、孵化、运营一站式综合众筹服务，协助您实现自己的创业梦想。好平台，好起点，尽在盒子众筹！"/>
	
	</head>
<body>
	<div class="upper">
		找回密码
	</div>
	<div class="left">
		无关紧要
	</div>
	<div class="main">
		<div class="main_left">
			<h1>找回密码</h1>
			<div class="main_left_div">
				<form class="findPwd">
					<table class="tabRegister" border="1">
						<tr>
							<th>账户名</th>
							<td><input type="text" name="phoneAndEmail" id="phoneAndEmail"/></td>									
						</tr>
						<tr>
							<th>随机验证码</th>
							<td>
								<input type="text" id="randomCode"/>
								<!-- <input type="button" id="checkCode" value="" readonly="readonly" onclick="createCode('')"/>  -->
								<img id="createImage" src="image"  alt="验证码图片" />
                             <input onclick="myReload()" value="看不清楚,换一个"/>
							</td>						
						</tr>
					</table>
						<button type="button" id="findPwdBtn">提交</button>
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
	<script type="text/javascript" src="../static/js/page/findPassword.js"></script>
</html>