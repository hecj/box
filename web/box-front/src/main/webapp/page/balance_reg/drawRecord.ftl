 
<html>
	<head>
		<script type="text/javascript">
			function funcDetail(id){
				var id = document.getElementById("myvalue"+id).value;
				window.alert(id);
				location ="/balance_reg/findDetail?id="+id;
			}
		</script>
	</head>
	<body>
	
		<form>
			<div>
			当前账户可用余额为${money}元
			<a href="/page/balance_reg/charge.ftl">充值</a>
			<a href="/balance_reg/drawMoney">提现</a>
		</div>
			<table>
				<#list record as a>
					<tr>
						
						<td><input type="text" id="myvalue${a.id}" value=${a.id}></td>
						<th>时间:</th>
						<td>${a.regtime}</td>
					
						<th>提现金额:</th>
						<td>${a.change}</td>
					
						<th>动态:</th>
						<td><#if a.status=0>
								待审核
							<#elseif a.status=1>
								未通过
							<#else>
								已通过
							</#if>
						</td>
					
						<th>操作:</th>
						<td><a onclick="funcDetail(${a.id})">查看详情</a></td>
					</tr>
				</#list>
			</table>	
		</form>
	<body>
</html>