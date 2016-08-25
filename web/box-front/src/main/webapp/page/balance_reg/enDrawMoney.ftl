<html>
	<head>
		<meta charset="utf-8"/>
	</head>
	<body>
	<h2>提现详情</h2><a href="/balance_reg/drawRecord">返回提现记录</a>
	
		提现金额为${bal.change}元<br>
		当前提现状态<#if bal.status=0>
						未审核，请等待
					<#elseif bal.status=1>
						未通过，请核实
					<#else>
						已通过，请查收
					</#if>
		<br>
	
	</body>
</html>