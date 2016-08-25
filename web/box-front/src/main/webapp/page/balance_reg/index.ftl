<html>

	<head>
	</head>

	<body>
		
		<div>
			<a>我的账户</a>
		</div>
		<div>
			当前账户可用余额为${balance}元
			<a href="/payment/recharge">充值</a>
			<a href="/balance_reg/drawMoney">提现</a>
		</div>
		<div>
			<a href="">余额记录</a>
			<a href="/balance_reg/drawRecord">提现记录</a>
			<table>
				<#list balList as bal>
					<tr>
						<th>时间：</th>
						<td>${bal.regtime}</td>
						
						<th>事件：</th>
						<td>${bal.content}</td>
						
						<th>钱数：</th>
						<td>${bal.change!}</td>
						
						<th>余额：</th>
						<td>${bal.balance!}</td>
							
						<th>状态：</th>
						<#if bal.contype=1>
							<td><#if bal.sta=0>
									未付款
								<#else>
									已付款
								</#if>
							</td>
						<#elseif bal.contype=0>
							<td><#if bal.status=0>
									未审核
								<#elseif bal.status=1>
									未通过
								<#else>
									已通过
								</#if>
							</td>
						<#else>
							<td><#if bal.sta=0>
									未付款
								<#else>
									已付款
								</#if>
							</td>
						</#if>
					</tr>
				</#list>
				
			</table>
			
		</div>
		
	</body>
</html>