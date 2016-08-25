<html>
	<head>
		<meta charset="utf-8"/>
		<#include "/page/common/base/include.ftl">
		<script type="text/javascript" src="/static/common.js"></script>
		<link href="/static/css/style.css" rel="stylesheet" type="text/css" />
		<link href="/static/css/common.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript">
			jQuery(function(){
				<#if (VIEW_MSG_TYPE)?? >
				var op={msg:"${VIEW_MSG_MSG}"};
				peon.popx["${VIEW_MSG_TYPE}"](op);
				</#if>
			});
		
			function funcMoney(){
				var money=$("#money").val();
				
				
				var username = $("#username").val();
				
				
				var str='        <p>是否确认要申请体现 ？</p>'+
						'        <cite>如果是请点击确定按钮 ，否则请点取消。</cite>';
				var op={
					title:"操作提示",
					msg:str,
					confirm:function(v){v&&draw(money,username);}
				};
				peon.popx.confirm(op);
			}
			function draw(money,username){
				window.alert(money);
				window.alert(username);
				location="/balance_reg/enDrawMoney?money="+money+"&username="+username;
			}
		</script>
	</head>
	<body>
	<form>
		<input type="text" value=${user.username!} name="username" id="username" hidden="true"/>
		当前账户余额为:${user.balance!}元<br>
		可提现金额为：${user.balance!}元<br>
		
		请输入提现金额:
		<input type="text" name="money" id="money"/><br>
		到账账户：将返还到您的支付宝中
		预计到账日期：3-5个工作日
		<input type="button" onclick="funcMoney()" value="申请提现"/>   
		
	</form>
	</body>
</html>