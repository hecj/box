
<html>
	<head>
		<meta charset="utf-8"/>
		<#include "/page/common/base/include.ftl">
		
		<script type="text/javascript">
		
			function checkPassword(){
				var newPwd=$("#newPassword").val();
				var newPwd2 = $("#newPassword2").val();
				var oldPwd = $("#oldPassword").val();
				
				if(oldPwd==""){
					window.alert("旧密码不能为空");
				}
				if(newPwd!=newPwd2){
					var str="两次密码输入不一致!";	
					window.alert(str);
				}else{
					location="/user/updatePwd"
				}
				
				
			
			}
			
		</script>
	</head>
	<body>
		<form action="">
			<table border="1px">
				
				<tr>
					<th>旧密码:</th>
					<td><input type="text" name="oldPassword" id="oldPassword"/></td>
				</tr>	
				
				<tr>
					<th>新密码:</th>
					<td><input type="text" name="newPassword" id="newPassword"/></td>
				</tr>
				
				<tr>
					<th>再次输入新密码:</th>
					<td><input type="text" name="newPassword2" id="newPassword2"/></td>
				</tr>
			</table>
			<input type="button" value="提交" onclick="checkPassword()"/>
		</form>
	</body>
</html>