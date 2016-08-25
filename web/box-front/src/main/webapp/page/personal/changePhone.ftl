
<html>
	<head>
		<meta http-equiv="content-type" content="text/html;charset=utf-8">
		<#include "/page/common/base/include.ftl">
		<script type="text/javascript">
			function check(){
				var time = new Date().getTime();
				var phone = $("#phone").val();
				//window.alert(phone);
				
				var code = $("#code").val();
				//window.alert(code);
				$.ajax({
					type:"POST",
					url:"/verifyCode?phone="+phone+"&time="+time+"&code="+code,
					async: false,
					success:function(msg){							
							if(msg=="true"){
								alert("验证码正确");
								return true;
								
							}else{
								alert("验证码错误");	
								return false;
							}
						}
				});	
			};
			
			jQuery(function(){
				
				$("#sendCode").click(function(){
					var time = new Date().getTime();
					//window.alert(time);
					$.ajax({
						type:"POST",
						url:"/sendCode?phone="+$("#phone").val()+"&time="+time,						
						success:function(msg){
							if(msg){
								alert("已发送");
							}else{
								alert("发送失败");	
							}
						}
					})
					
				});
			});
				
			var countdown = 60;
			
			function settime(val){
				if(countdown==0){
					val.removeAttribute("disabled");
					val.value="免费获取验证码";
					countdown = 60;
				}else{
					val.setAttribute("disabled",true);
					val.value="重新发送("+countdown+")";
					countdown--;
					setTimeout(function() {
               			settime(val)
            		},
           			1000)
				}	
				
			}
			
			
		</script>
	</head>
	<body>
		<form name="frmPayPwd" action="/personal/resetPhone" method="post" onsubmit="return check();">
			<table border="1px">
				
				<tr>
					<th>旧的手机号</th>
					<td><input type="text" name="oldphone" id="oldphone" value=${oldPhone!}></td>					
				</tr>
				
				
				<tr>
					<th>新的手机号</th>
					<td><input type="text" name="phone" id="phone"/></td>					
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
		</body>
	</form>
</html>