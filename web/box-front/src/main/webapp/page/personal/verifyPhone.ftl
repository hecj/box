<html>
	<head>
		<meta http-equiv="content-type" content="text/html;charset=utf-8">
		<#include "/page/common/base/include.ftl">
		<script type="text/javascript">
			function check(){
				
			var phone = $("#phone").val();
				$.ajax({
					type:"POST",
					url:"/personal/verifyPhone?phone="+phone,
					async: false,
					success:function(msg){							
							if(msg=="true"){
								alert("手机验证正确");
								return true;
								
							}else{
								alert("手机验证错误");	
								return false;
							}
						}
				});	
			};
		
		</script>
	</head>
	<body>
		<form name="frmPayPwd" action="/personal/changePhone" method="post" onsubmit="return check();">
			<table border="1px">
	
				<tr>
					<th>绑定的手机号</th>
					<td><input type="text" name="phone" id="phone"/></td>					
				</tr>

			</table>
				<button type="submit">提交</button>
		</body>
	</form>
</html>