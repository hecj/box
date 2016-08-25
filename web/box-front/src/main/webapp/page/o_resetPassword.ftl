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
				<form class="frmReset" action ="" method="post" onsubmit="return check();">
					<table class="tabRegister" border="1">
						<tr>
							<th>账户名</th>
							<td>
								<select id="phoneOrEmail" name="phoneOrEmail" onChange="changeType()">
									<#if resultJson.code == 2>
										<option value="0" <#if type==0>selected="true"</#if>>通过手机找回密码</option>
										<option value="1" <#if type==1>selected="true"</#if>>通过邮箱找回密码</option>
									<#elseif resultJson.code == 1>
										<option value="'+resultJson.code+'">通过邮箱找回密码</option>
									<#else>
										<option value="'+resultJson.code+'">通过手机找回密码</option>
									</#if>
								</select>
							</td>
						</tr>

						<tr>
							<th>昵称</th>
							<td>
								<input type="nickname" id="nickname" name="'+${resultJson.data.nickname!}+'" value=${resultJson.data.nickname!"匿名用户"} readonly="true">
							</td>
						</tr>
												
						<#if resultJson.code == 1>
							<tr>
								<th>邮箱：</th>
								<td>
									<input type="email" id="email" name="'+${resultJson.data.email!}+'"  readonly="true" value=${resultJson.data.email!}>
								</td>
								<td>
									<input type="button" id="sendEmailCode" value="发送验证邮件"/>
								</td>
							</tr>
						</#if>
						
						<#if resultJson.code == 0>
							<tr>
								<th>手机：</th>
								<td>
									<input type="phone" id="phone" name="'+${resultJson.data.phone!}+'" readonly="true" value=${resultJson.data.phone!}>
									<input type="button" id="sendPhoneCode" value="发送验证码" onclick="settime(this)"/>
								</td>
							</tr>
							<tr>
								<th>手机验证码</th>
								<td>
									<input type="text" id="code" name="code"/>
								</td>
							</tr>
							<tr>
								<input type="button" id = "btnPhone" value="提交"/>
							</tr>
						</#if>
					</table>
						<div id="selectPhone" style="display: none;">
							<table>
								<tr>
									<input type="button" id="sendPhoneCode" value="发送验证码" onclick="settime(this)"/>
								</tr>
								<tr>
									<input type="text" id="code" name="code"/>
								</tr>
								<tr>
									<input type="button" id="btnPhone" value="提交"/>
								</tr>
							</table>
							
							
						</div>
						
						<div id="selectEmail" style="display: none;">
							<table>
								<tr>
									<input type="button" id="sendEmailCode" value="发送验证邮件"/>
								</tr>
							</table>
						</div>
					
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
	<script type="text/javascript" src="../static/js/page/resetPassword.js"></script>
	<script>
		$(document).ready(function(){
			<#if type==0&&resultJson.code==2>
				$("#selectPhone").show();
				$("#selectEmail").hide();
			<#elseif type==1&&resultJson.code==2>
				$("#selectEmail").show();
				$("#selectPhone").hide();
			<#else>
			</#if>	
		})
	</script>
</html>