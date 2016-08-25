<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
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
		
		.buttonBlue{
			display:inline;
			cursor:pointer;
			background-color:#d5d5d5;
			text-align:center;
			color:white;
		}
		.buttonGray{
			display:inline;
			cursor:pointer;
			background-color:#aaa;
			text-align:center;
			color:white;
		}
  	</style>
</head>
<body>
<div class="place">
    <span>位置：</span>
    <ul class="placeul">
        <li><a href="javascript:top.location='/';">首页</a></li>
         <li><a href="/orders/deliveryList/">发货单列表</a></li>
        <li><a href="">发货单详情</a></li>
    </ul>
</div>
    
<div class="formbody">
    <div id="usual1" class="usual">
		<div>    	
    		<b><font size = "5">发货单信息</font></b>
    	</div>
  	</div>
	<div class="xline"/><br>
	<center><b>基本信息</b></center>
	<div class="xline"/>
    <ul class="forminfo">
    	<li>
    	    <cite>
    		<label class="label">发货单流水号：</label>
    		<label style="width:400px;">${delivery.delivery_flow_no!}</label>
    		<label class="label">订单状态：</label>
    		<label style="width:200px;">
    			${orders.getStatusName()!}
    		</label>
    		</cite>
   	    </li>
    	<li>
    	    <cite>
    		<label class="label">订单号：</label>
    		<label class="label"  style="width:400px;">${orders.order_num!}</label>
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
    		<label class="label">支持者：</label>
    		<label style="width:400px;">${user.nickname!}</label>
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
    		<label class="label">支付方式：</label>
    		<label style="display:inline;width:400px;">
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
    		<label class="label">发货时间：</label>
    		<label style="width:200px;">${send_at?number_to_datetime}</label>
    		</cite>
   	    </li>
    	<li>
    	    <cite>
    		<label class="label">配送方式：</label>
    		<label style="display:inline;width:400px">
    			<#if orderAddress.ship_mode ??>
						${orderAddress.getShipMode().name!}
				<#else>
					&nbsp;&nbsp;&nbsp;&nbsp;
				</#if>
    		</label>
    		<label class="label">发货单号：</label>
    		<#if delivery.delivery_no??>
    		<label style="width:200px;">${delivery.delivery_no!}</label>
    		<#else>
    		<input style="margin-top:9px;width:200px;border:1px solid #aaa;" id="delivery_num"></input>
    		</#if>
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
				<label style="width:170px;font-weight:bold;">备注：</label>
				<label>
					${delivery.remark!}
				</label>
			</cite>
		</li>
		<li>
			<cite>
				<label style="width:170px;font-weight:bold;">当前可执行操作：</label>
				<#if delivery.status?? && delivery.status==0>
				<label class="buttonBlue" onclick="finish()">发货</label>
				<#elseif delivery.status?? && delivery.status==1>
				<label class="buttonGray">发货</label>
				</#if>
			</cite>
		</li>
	</ul>
</div>
  	<script type="text/javascript">
  		function finish(){
  			var delivery_num = "${delivery.delivery_no!}";
  			if(delivery_num.length == 0){
  				delivery_num = $.trim($("#delivery_num").val());
	  			if(delivery_num.length == 0){
	  				alert("发货单号不能为空！");
	  				return;
	  			}
  			}
  			$.ajax({
		            type: "POST",
		            url: "/orders/delivery",
		            async:false,
		            data:"orderid=${orders.id!}&delivery_num=" + delivery_num + "&send_at=${send_at!}&orderAddressId=${orderAddress.id!}",
		            success: function(msg) {
		                if (msg == 'success') {
		                	location = "/orders/updateOrderStatus/${orders.id!}";
		                }else if(msg == 'failure'){
		                	alert("数据库保存失败");
		                } else if(msg == 'exist'){
		                	alert("该发货单号已存在");
		                } else{
		                	alert("生成发货单失败");		                	
		                }
		            }
		    });
  		}
  	</script>
</body>
</html>
