<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
  	<#include "/page/common/base/include.ftl">
  	<style type="text/css">
  		table tr{
  			font-size:14px;
  			height:40px;
  			text-align:center;
  			border-bottom:solid 1px #EEE; 
  		}
  		
  		table th{
  			font-size:14px;
  			width:200px;
  			text-align:center;
  		}
  		
  		.button{
  			display:inline;
			cursor:pointer;
			background-color:#00A2F7;
			text-align:center;
			color:white;
  		}
  	</style>
</head>
<body>
	<div class="place">
	</div>
	<div class="formbody">
	    <div id="usual1" class="usual">
			<div>    	
	    		<b><font size = "5">请选择：</font></b>
	    	</div>
	  	</div>
	  	<div class="xline"/><br>
		<table>
			<tr>
				<th>&nbsp;</th>
				<th>名称</th>
				<!--
				<th>描述</th>
				<th>配送费</th>
				<th>免费配额</th>
				<th>保价费</th>
				-->
				<th>&nbsp;</th>
				<th>&nbsp;</th>
				<th>&nbsp;</th>
				<th>&nbsp;</th>
			</tr>
			<#if shipModeList??>
				<#list shipModeList as sm>
					<tr>
						<td>
							<#if sm.id == shipmode>
								<input type="radio" name="mode" value="${sm.id!}" checked="checked"></input>
							<#else>
								<input type="radio" name="mode" value="${sm.id!}"></input>
							</#if>
						</td>	
						<td>${sm.name!}</td>
						<!--
						<td style="text-align:left">${sm.description!}</td>
						<td>${sm.ship_fee!}</td>
						<td>${sm.free_limit!}</td>
						<td>${sm.price_insured!}</td>
						-->
						<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
					</tr>
				</#list>
			</#if>
		</table>
		<div>
			<br/>
			<br/>
			<ul class="forminfo">
				<li>
					<cite style="margin-left:50px;">
						<label class="button" onclick="chooseShipMode(true)">确定</label>
						<label>&nbsp;</label>
						<label class="button" onclick="chooseShipMode(false);">取消</label>
					</cite>
				</li>
			</ul>
		</div>
	</div>
</body>
	<script type="text/javascript">
  		function chooseShipMode(flag){
  			location = "/orders/shipmodeResult/" + $(":radio[name='mode']:checked").val() + "-${orderid}-${orderAddId!}-" + flag;
  		}
	</script>
</html>
