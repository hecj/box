<script type="text/javascript">
			function funSelect(){
				var info = $("#selectValue").val();
				if(info.length==0){
					alert("请输入要查询内容");
				}else{
					location="/project/findValue/"+info;
				}
			}
</script>

<span>
	<a href="/">Logo</a> | <a href="/">盒子众筹</a> | <a href="/project">众筹项目</a>|  盒子社区 | 
	<input id="selectValue" type="text" name="selectValue" placeholder="请输入要查询内容">
	<input type="button" value="搜索" onclick="funSelect();">
</span>

<span style="float:right">
	<#if user??>
		欢迎您,<a href="/personal">${user.nickname!}</a> <a href="/logOut">退出</a>
	<#else>
		<a href="/login"> 登录</a> | <a href="/register">注册</a>
	</#if>
</span>

