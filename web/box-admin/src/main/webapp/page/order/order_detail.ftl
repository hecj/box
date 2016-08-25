<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  	<#include "/page/common/base/include.ftl">
  	<style type="text/css">
		.label{
			width:60px;
			font-weight:bold;
			text-align:justify;
		}
		
		.tabtitle label{
			font-weight:bold;
			text-align:center;
		}
		
		.tabList label{
			text-align:center;
		}
		
		.buttonGrey{
			display:inline;
			cursor:pointer;
			background-color:#AAA;
			text-align:center;
			color:white;
		}
		
		.buttonBlue{
			display:inline;
			cursor:pointer;
			background-color:#2ec2f9;
			text-align:center;
			color:white;
		}
  	</style>
  	<script type="text/javascript">
  		function changeShipMode(){
  			location = "/orders/shipmode/${orders.id!}-${orderAddress.id!}-${orderAddress.ship_mode!-1}";
  		}
  		function createShipList(){
  			//先判断，是否已经有订单生成，如果有，则不能继续生成订单了
  			if(${delivery.id!-1001} != -1001){
  				alert("该订单已经生成了发货单，不能重复生成");
  				return;
  			}else{
				var remark = jQuery.trim($("#remark").val());
				 $.ajax({
		            type: "POST",
		            url: "/orders/pre_delivery",
		            data:"orderid=${orders.id!}" + "&remark=" + remark + "&orderAddressId=${orderAddress.id!}",
		            success: function(msg) {
		                if (msg) {		                	
				  			location = "/orders/renderPreDeliver/${orders.id!}";
				  			alert("发货单生成成功，点击确定查看发货单详情！");
		                } else{
		                	alert("生成发货单失败");
		                }
		            }
		        });
  			}
		}
  	</script>
</head>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="javascript:top.location='/';">首页</a></li>
        <li><a href="/orders">支持列表</a></li>
        <li><a href="">订单信息</a></li>
    </ul>
</div>
    
<div class="formbody">
    <div id="usual1" class="usual">
		<div>    	
    		<b><font size = "5">订单信息</font></b>
    	</div>
  	</div>
	<div class="xline"/><br>
	<center><b>基本信息</b></center>
	<div class="xline"/>
    <ul class="forminfo">
    	<li>
    	    <cite>
    		<label class="label">订单号：</label>
    		<label style="width:400px;">${orders.order_num!}</label>
    		<label class="label">订单状态：</label>
    		<label style="width:200px;">
    			${orders.getStatusName()!}
    		</label>
    		</cite>
   	    </li>
    	<li>
    	    <cite>
    		<label class="label">支持者：</label>
    		<label style="width:400px;">${user.nickname!}</label>
    		<label class="label">下单时间：</label>
    		<#setting datetime_format="yyyy-MM-dd HH:mm:ss"/>
    		<label style="width:200px;">
    			<#if orders.pay_at??>
    				${orders.create_at?number_to_datetime}
    			</#if>
    		</label>
    		</cite>
   	    </li>
    	<li>
    	    <cite>
    		<label class="label">支付方式：</label>
    		<label style="width:400px;">
    			<#if orders.pay_type ??>
    				<#if orders.pay_type == -1>
    				默认未知
    				<#elseif orders.pay_type == 1>
    				余额支付
    				<#elseif orders.pay_type == 2>
    				支付宝支付
    				</#if>
    			</#if>
    		</label>
    		<label class="label">支持时间：</label>
    		<label style="width:200px;">
    			<#if orders.pay_at??>
    				${orders.pay_at?number_to_datetime}
    			</#if>
    		</label>
    		</cite>
   	    </li>
    	<li>
    	    <cite>
    		<label class="label">配送方式：</label>
    		<#if orderAddress.ship_mode ??>
    		<label id="shipmodelabel" style="width:100px;display:inline">${orderAddress.getShipMode().name}</label>
    		<#else>
			<label id="shipmodelabel" style="width:100px;display:inline">&nbsp;</label>
			</#if>
			
			<#if delivery.delivery_no??>
				<label class="label" style="width:300px;">&nbsp;</label>
			<#else>
				<#if orders.status?? && orders.status==1 >
					<#if delivery.id??>
		    			<label class="label" style="width:300px;">&nbsp;</label>
		    		<#else>
			    		<label class="buttonBlue" onclick="changeShipMode();" style="width:50px;">编辑</label>
			    		<label class="label" style="width:250px;">&nbsp;</label>
		    		</#if>
	    		<#else>
	    		<label class="label" style="width:300px;">&nbsp;</label>
	    		</#if>
			</#if>
    		<label class="label">发货时间：</label>
    		<#if delivery.creat_at??>
    		<label style="width:200px;">${delivery.creat_at?number_to_datetime}</label>
    		</#if>
    		</cite>
   	    </li>
   	    <li>
    	    <cite>
    		<label class="label">发货单号：</label>
    		<#if delivery.delivery_no??><label style="width:200px;">${delivery.delivery_no}</label></#if>
    		</cite>
   	    </li>
   	</ul>
   	<br>
	<div class="xline"/><br>
	<center><b>收货人信息</b></center>
	<div class="xline"/>
    <ul class="forminfo">
    	<li>
    	    <cite>
    		<label class="label">收货人：</label>
    		<label style="width:400px;">${orderAddress.name!}</label>
    		<label class="label">手机号：</label>
    		<label style="width:200px;">${orderAddress.phone!}</label>
    		</cite>
   	    </li>
    	<li>
    	    <cite>
    		<label class="label">地址：</label>
    		<label style="width:800px;">${orderAddress.provinceName!}&nbsp;&nbsp;&nbsp;${orderAddress.cityName!}&nbsp;&nbsp;&nbsp;${orderAddress.areaName!}&nbsp;
    			&nbsp;&nbsp;${orderAddress.detail_address!}
    		</label>
    		</cite>
   	    </li>
   	</ul>
   	<br>
   	<div class="xline"/><br>
	<center><b>项目信息</b></center>
	<div class="xline"/>
    <ul class="forminfo">
    	<li>
    	    <cite class="tabtitle">
    		<label style="width:200px">项目名称</label>
    		<label style="width:200px">回报内容</label>
    		<label style="width:200px">支持金额</label>
    		<label style="width:200px">数据</label>
    		</cite>
   	    </li>
    	<li>
    	    <cite class="tabList">
    		<label style="width:200px">${project.name!}</label>
    		<label style="width:200px">${product.desc!}</label>
    		<label style="width:200px">${orders.money!}</label>
    		<label style="width:200px">${orders.number!}</label>
    		</cite>
   	    </li>
   	</ul>
   	<br/>
   	<div class="xline"/><br>
	<center><b>操作信息</b></center>
	<div class="xline"/>
	<ul class="forminfo">
		<li>
			<cite>
				<label style="width:170px;font-weight:bold;">操作：</label>
				<label>
					<textarea rows="5" cols="90" id="remark" style="margin-top:10px;border:1px solid #d5d5d5">${delivery.remark!}</textarea>
				</label>
			</cite>
		</li>
		<li>
			<cite>
				<label style="width:170px;font-weight:bold;">当前可执行操作：</label>
				<#if delivery.delivery_no??>
				<label class="buttonGrey">生成发货单</label>
				<#else>
					<#if orders.status?? && orders.status==1>
						<#if delivery.id??>
		    				<label class="buttonGrey">生成发货单</label>
			    		<#else>
				    		<label class="buttonBlue" onclick="createShipList();">生成发货单</label>
			    		</#if>
					<#else>
						<label class="buttonGrey">生成发货单</label>
					</#if>
				</#if>
			</cite>
		</li>
	</ul>
</div>
</body>
</html>
