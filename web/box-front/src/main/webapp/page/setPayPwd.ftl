
<html>
	<head>
		<meta http-equiv="content-type" content="text/html;charset=utf-8">
		<#include "/page/common/base/include.ftl">
		
	</head>
	<body>
		<form name="frmPayPwd" action="/personal/setPayPwd" method="post" onsubmit="return check();">
			<table border="1px">
				<tr>
					<th>设置支付密码</th>
					<td><input type="password" name="paypwd" id="paypwd"/></td>
				</tr>
				
				<tr>
					<th>确认支付密码</th>
					<td><input type="password" name="paypwd2" id="paypwd2"/></td>
				</tr>
				
				<tr>
					<th>绑定的手机号</th>
					<td><input type="text" name="phone" id="phone"/>
						<a href="/page/personal/verifyPhone.ftl">更换手机号</a>
					</td>
					
				</tr>
				
				<tr>					
					<input type="button" id="sendCode" value="免费获取验证码" onclick="settime(this)"/>
				</tr>
				
				<tr>
					<th>验证码</th>
					<td><input type="text" name="code" id="code"/></td>
				</tr>
				
				<tr>
					<button type="submit">提交</button>
				</tr>
				
			</table>
		</form>
	</body>
	<script type="text/javascript" src="/static/js/common/jquery/jquery.js"></script>
	<script type="text/javascript" src="../static/js/page/reg.js"></script>
</html>