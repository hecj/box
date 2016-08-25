<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<head>
	<meta charset="UTF-8">
	<title>支付页面--提交订单--支付方式</title>
	<#include "/page/common/base/include.ftl">
<body>
	<!-- 引用顶部开始 -->
	<#include "/page/common/n_head.ftl">
	<!-- 引用顶部结束 -->
	<!-- 内容开始 -->
	<div class="pay-content-wrap bg_wrap">
	    <div class="static-tiltle static-tiltle02"><span></span></div>
		<!-- 收货地址start.. -->
		<#if product.type == 0>
		 <div class="pay-content-one">
				<div class="pay-itemone pay-itemone-auto">
					<div class="pay-state pay-state03"></div>
			
					<div class="pay-address">
					  	<div class="pay-address-row">
					  		<div class="pay-address-one pay-address-one02">
					  			 <div class="shipping-address">
					  			 	<p class="fontsize16">收货地址:</p>
					  			 	<div class="pay-address-one">
				                        <div class="address-sure">
				                        	<p>
				                        		<span>${receiveAddress.provinceName!}&nbsp;${receiveAddress.cityName!}&nbsp;${receiveAddress.areaName!}&nbsp;
				                        			${receiveAddress.detail_address!}
				                        		</span>
				                        		<em>${receiveAddress.phone!}&nbsp;${receiveAddress.name!}</em>
				                        	</p>
				                        	<a href="/orders/generate/${product.id!}?order_num=${orders.order_num!}&default_address_id=${default_address_id!}"><span>修改</span></a>	
				                        </div>
				  					</div>
					  			 </div> 
					  		</div>
					  	</div>
				    </div>
				</div>				
		 </div>	
		 </#if>
		<!-- 收货地址end.. -->
		 <div class="border-dashed"></div>
		<form method="post" action="/payment/order/${orders.order_num!}" target="_blank">
		<#if product.type == 0>
		<!--收货地址-->
		<input type="hidden" name="receiveAddressId" value="${receiveAddress.id!}"/>
		</#if>
		<!-- 使用薪金全或者红包start. -->
		 <div class="pay-content-one no-pt">
				<div class="pay-itemone pay-itemone-auto">
					<div class="pay-state pay-state04"></div>
			
					<div class="pay-address">
					  	<div class="pay-address-row">
					  		<div class="pay-address-one pay-address-one02 cash">
								<p>
									<a href="javascript:;" class="cash-use"><i></i>使用现金券或者红包</a>
								</p>	
								<div class="cash-con cash-con01">
								    <div class="form-group02">
									    <label class="pay-radio">
									    	<input type="radio" class="form-control" name="coupons_type" checked="checked" value="0">
									    </label>
									    <select name="" id="">
									    	<option value="">暂无可用优惠券</option>
									    	<option value="">请选择</option>
									    	<option value="">请选择</option>
									    	<option value="">请选择</option>
									    	<option value="">请选择</option>
									    </select>
							  		</div>
							  	    <div class="form-group02">
									    <label class="pay-radio">
									    	<input type="radio" class="form-control" name="coupons_type" value="1">
									    </label>
									   <input type="text" value="输入优惠码" name="coupons_code">
									   <input type="button" class="cash-save-h" value="保存"/>
							  		</div>
							  		<!-- <h3><span>-¥0.00</span></h3> -->
								</div>
					  		</div>
					  	</div>
				    </div>
				</div>				
		 </div>	
		<!-- 使用薪金全或者红包end. -->
	<!-- 	<div class="border-dashed"></div>
	 -->
		<!-- 付款方式start. -->	

	    <div class="pay-content-one no-pt">
	     	     <p class="pay-way-title">付款方式</p>
				<div class="pay-itemone pay-itemone-auto no-mt">
					<div class="pay-state pay-state05"></div>
				
					<div class="pay-address pay-way">
					
					  	<div class="pay-address-row">
					  		<div class="pay-address-one pay-address-one02 cash">
		
								<div class="cash-con pay-way-con">
								    <div class="form-group02 no-pt">
									    <label class="pay-radio">
									    	<#if (user.balance < (product.fund+product.postage)) >
									    	     <input type="checkbox" disabled class="form-control pay-radio01 no-padding" name="payment_type" value="1">
											<#else>
											     <input type="checkbox" class="form-control pay-radio01 no-padding" name="payment_type" value="1">
											</#if>
									    </label>
									    <span>盒子余额:</span><i class="fontweight">¥${user.balance?string("#.##")}</i>
									    <em id="balance_payment_em" style="display:none">支付:<b>¥${(product.fund+product.postage)?string("#.##")}</b></em>
							  		</div>
							  		<div class="pay-parssword">
								  		<div class="form-group02 no-pt">
										    <label class="pay-radio">请输入支付密码:</label>
										    <input type="password" class="form-control" name="payment_password">
										    <span><a href="javascript:;">忘记支付密码？</a></span>
								  		</div>
							  		</div>
							  	    <div class="form-group02 pay-way-alipay">
							  	    	<div class="pay-way-alipay-tit">支付宝</div>
									    <label class="pay-radio">
									    	<input type="radio" class="form-control" checked name="payment_type" value="2" checked="checked">
									    </label>
									   <span class="pay-wayicon"></span>
							  		</div>
								</div>
					  		</div>
					  	</div>
				    </div>
				</div>				
		 </div>	
		<!-- 付款方式end. -->	
		<div class="border-dashed"></div>

		<!--风险提示start. -->	

	     <div class="pay-content-one no-pt">
	     	     <p class="pay-way-title">风险提示</p>
				<div class="pay-itemone pay-itemone-auto no-mt">
					<div class="pay-state pay-state06"></div>
				
					<div class="pay-address pay-way">
					
					  	<div class="pay-address-row">
					  		<div class="pay-address-one pay-address-one02 cash">
		
								<div class="cash-con pay-way-con pay-danger-con">
						            <p class="pay-dangerp">1.项目的发展可能会遇到延期或未达到预期效果，甚至失败等风险，在全面了解和认同发起人的项目及理念之后确认支持。</p>
						            <p>2. 为了保证项目筹款和制作的顺利进行，您成功付款后不能直接申请退款哦。如果筹款失败，项目取消，我们会把支持款退还给您！</p>
							  
								</div>
					  		</div>
					  	</div>
				    </div>
				</div>				
		 </div>	
		<!--风险提示end. -->

		<!-- 应付金额. -->	
	     <div class="pay-money-p">
		 		<div class="pay-itemone-money">
		 			<p class="pay-wm">支付金额: <span class="color_money">¥${(product.fund+product.postage)?string("#.##")}<!--<em>.00</em>--></span></p>
		 			<p><input type="submit" class="btn_money btn_paysure" value="确定，去付款"></p>
		 		</div>
		 </div>
		 <!-- 应付金额. -->	
		</form>
	</div> 
	 <!-- 支付跳转start. -->	
	<div class="pay-way-wrap"></div>
   
    <div class="pay-way-jump-con">
         	 		<div class="pay-jump-wrap">
         	 		   <div class="pay-jump-head">
         	 		   	 <div class="goback goback_top">X</div>
         	 		   </div>
         	 		   <div class="pay-jump-con">
         	 		   		<h3>跳转页面完成支付</h3>
         	 		   		<p>付款前请不要关闭此窗口</p>
						    <p>付款完成后根据您的情况点击下面的按钮</p>
						    <div class="jump-other">
						    	<a href="javascript:;" class="goback">返回其他支付方式？</a>
						    	<a href="javascript:;" onclick="finish_pay('${orders.order_num!}')"><span>已完成支付</span></a>
						    </div>
         	 		   </div>
         	 	
    </div>
	  <!-- 支付跳转end. -->	
	</div>
	<!-- 引用底部开始 -->
	<#include "/page/common/foot.ftl">
	<!-- 引用底部结束 -->
	<script type="text/javascript" src="/static/js/page/orders/cf-pay-way.js?v=20151203"></script>
</body>
</html>
